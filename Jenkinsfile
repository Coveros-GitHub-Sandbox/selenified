def urlBranch
def workspace
def branch
def branchType
def baseVersion
def version
def pullRequest
def refspecs

node {
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
            sh "rm -rf target*"
            sh "rm -rf jacoco*"
        }
        stage('Update Test Site') {
            sh 'scp public/* ec2-user@34.233.135.10:/var/www/noindex/'
        }
        stage('Run Unit Tests') {
            sh "mvn clean test"
            sh "mv target target-unit"
        }
        wrap([$class: 'Xvfb']) {
            stage('Execute HTMLUnit Tests') {
                try {
                    sh 'mvn clean verify -Dskip.unit.tests -Dbrowser=htmlunit'
                } catch (e) {
                    echo "ERROR: ${e}"
                } finally {
                    sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec;"
                    sh "mv target target-htmlunit";
                }
            }
            stage('Execute Browser Tests') {
                try {
                    sh 'mvn clean verify -Dskip.unit.tests -Dbrowser=chrome,firefox -Dfailsafe.exclude.groups="" -Dheadless'
                } catch (e) {
                    echo "ERROR: ${e}"
                } finally {
                    sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec;"
                    sh "mv target target-browser";
                }
            }
        }
        stage('Perform SonarQube Analysis') {
            sh """
                mvn clean compile sonar:sonar \
                        -Dsonar.projectKey=selenified:${branch} \
                        -Dsonar.projectName="Selenified - ${env.BRANCH_NAME}" \
                        -Dsonar.host.url=http://localhost:9000/sonar \
                        -Dsonar.junit.reportPaths="target-unit/surefire-reports,target-htmlunit/failsafe-reports,target-browser/failsafe-reports" \
                        -Dsonar.jacoco.reportPath=target-unit/coverage-reports/jacoco-ut.exec \
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