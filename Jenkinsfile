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
        try {
            stage('Run Unit Tests') {
                try {
                    sh "mvn clean test"
                } catch (e) {
                    throw e;
                } finally {
                    sh "cat target/coverage-reports/jacoco-ut.exec >> jacoco-ut.exec;"
                    sh "mkdir -p results/unit; mv target results/unit/"
                    junit 'results/unit/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts artifacts: 'results/unit/target/surefire-reports/**'
                }
            }
            wrap([$class: 'Xvfb']) {
                stage('Execute HTMLUnit Tests') {
                    try {
                        sh 'mvn clean verify -Dskip.unit.tests -Dbrowser=htmlunit -Dheadless'
                    } catch (e) {
                        throw e;
                    } finally {
                        sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec;"
                        sh "mkdir -p results/htmlunit; mv target results/htmlunit/";
                        junit 'results/htmlunit/target/failsafe-reports/TEST-*.xml'
                        archiveArtifacts artifacts: 'results/htmlunit/target/failsafe-reports/**'
                    }
                }
                stage('Execute Local Tests') {
                    try {
                        sh 'mvn clean verify -Dskip.unit.tests -Dbrowser=chrome -Dfailsafe.groups.exclude="" -Dheadless'
                    } catch (e) {
                        throw e;
                    } finally {
                        sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec;"
                        sh "mkdir -p results/browserLocal; mv target results/browserLocal/";
                        junit 'results/browserLocal/target/failsafe-reports/TEST-*.xml'
                        archiveArtifacts artifacts: 'results/browserLocal/target/failsafe-reports/**'
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
                        throw e;
                    } finally {
                        sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec;"
                        sh "mkdir -p results/browserRemote; mv target results/browserRemote/";
                        junit 'results/browserRemote/target/failsafe-reports/TEST-*.xml'
                        archiveArtifacts artifacts: 'results/browserRemote/target/failsafe-reports/**'
                    }
                }
            }
        } finally {
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
            stage('Publish Coverage Results') {
                jacoco()
            }
            withCredentials([
                usernamePassword(
                    credentialsId: 'saperstone-slack',
                    usernameVariable: '',
                    passwordVariable: 'botToken'
                )
            ]) {
                stage('Send Notifications') {
                    emailextrecipients([culprits(), developers(), requestor()])
                    // send slack notifications
                    def message = "Selenified%20build%20completed%20for%20`${env.BRANCH_NAME}`.%20It%20was%20a%20${currentBuild.currentResult}.%20Find%20the%20details%20at%20${env.BUILD_URL}."
                    sh "curl -s -X POST 'https://slack.com/api/chat.postMessage?token=${env.botToken}&channel=%23selenified&text=${message}'"
                }
            }
        }
    }
}