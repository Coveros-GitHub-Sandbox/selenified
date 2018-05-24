def urlBranch
def workspace
def branch
def branchType
def baseVersion
def version
def pullRequest
def refspecs

node {
    cleanWs()
    ansiColor('xterm') {
        workspace = pwd()
        branch = env.BRANCH_NAME.replaceAll(/\//, "-")
        branchType = env.BRANCH_NAME.split(/\//)[0]
        urlBranch = env.BRANCH_NAME.replaceAll(/\//, "%252F")
        baseVersion = "${env.BUILD_NUMBER}"
        version = "$branch-$baseVersion"
        env.PROJECT = "Selenified"
        def branchCheckout
        pullRequest = env.CHANGE_ID
        if (pullRequest) {
            branchCheckout = "pr/${pullRequest}"
            refspecs = '+refs/pull/*/head:refs/remotes/origin/pr/*'
        }
        else {
            branchCheckout = env.BRANCH_NAME
            refspecs = '+refs/heads/*:refs/remotes/origin/*'
        }
        stage('Checkout Selenified') {
            // Get the test code from GitHub repository
            checkout([
                $class           : 'GitSCM',
                branches: [[ name: "*/${branchCheckout}"]],
                userRemoteConfigs: [[
                    url          : 'https://github.com/Coveros/selenified.git',
                    refspec      : "${refspecs}"
                ]]
            ])
            sh "mkdir results"
        }
        stage('Update Test Site') {
            sh 'scp public/* ec2-user@34.233.135.10:/var/www/noindex/'
        }
        stage('Run Unit Tests') {
            sh "mvn clean test"
            sh "cat target/coverage-reports/jacoco-ut.exec >> jacoco-ut.exec;"
            sh "mkdir -p results/unit; mv target results/unit/"
        }
        wrap([$class: 'Xvfb']) {
            stage('Execute HTMLUnit Tests') {
                try {
                    sh 'mvn clean verify -Dskip.unit.tests -Dbrowser=htmlunit -Dheadless -Dthreads=3'
                } catch (e) {
                    echo "ERROR: ${e}"
                } finally {
                    sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec;"
                    sh "mkdir -p results/htmlunit; mv target results/htmlunit/";
                }
            }
            stage('Execute Local Tests') {
                try {
                    sh 'mvn clean verify -Dskip.unit.tests -Dbrowser=chrome -Dfailsafe.groups.exclude="" -Dheadless'
                } catch (e) {
                    echo "ERROR: ${e}"
                } finally {
                    sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec;"
                    sh "mkdir -p results/browserLocal; mv target results/browserLocal/";
                }
            }
        }
        withCredentials([
            usernamePassword(
                credentialsId: 'saucelabs',
                usernameVariable: 'sauceusername',
                passwordVariable: 'saucekey'
            )
        ]) {
            stage('Execute Hub Tests') {
                try {
                    sh "mvn clean verify -Dskip.unit.tests -Dbrowser='browserName=Firefox,browserName=InternetExplorer,browserName=Edge&devicePlatform=Windows 10,browserName=Safari' -Dfailsafe.threads=30 -Dfailsafe.groups.exclude='' -Dhub=https://${sauceusername}:${saucekey}@ondemand.saucelabs.com"
                } catch (e) {
                    echo "ERROR: ${e}"
                } finally {
                    sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec;"
                    sh "mkdir -p results/browserRemote; mv target results/browserRemote/";
                }
            }
        }
        stage('Perform SonarQube Analysis') {
            sh """
                mvn clean compile sonar:sonar \
                        -Dsonar.projectKey=selenified:${branch} \
                        -Dsonar.projectName="Selenified - ${env.BRANCH_NAME}" \
                        -Dsonar.host.url=http://localhost:9000/sonar \
                        -Dsonar.junit.reportPaths="results/unit/target/surefire-reports,results/htmlunit/target/failsafe-reports,results/browserLocal/target/failsafe-reports,results/browserRemote/target/failsafe-reports" \
                        -Dsonar.jacoco.reportPath=jacoco-ut.exec \
                        -Dsonar.jacoco.itReportPath=jacoco-it.exec
            """
        }
        stage('Store Test Results') {
            junit '**/target*/failsafe-reports/TEST-*.xml'
            junit '**/target*/surefire-reports/TEST-*.xml'
            jacoco()
            archiveArtifacts artifacts: '**/target*/*-reports/**'
        }
        stage('Send Notifications') {
            step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: 'max.saperstone@coveros.com,matt.grasberger@coveros.com', sendToIndividuals: true])
        }
    }
}