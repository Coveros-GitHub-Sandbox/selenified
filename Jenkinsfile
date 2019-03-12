def branch
def pullRequest
def refspecs

node {
    cleanWs()
    ansiColor('xterm') {
        branch = env.BRANCH_NAME.replaceAll(/\//, "-")
        env.PROJECT = "selenified"
        def branchCheckout
        pullRequest = env.CHANGE_ID
        if (pullRequest) {
            branchCheckout = "pr/${pullRequest}"
            refspecs = '+refs/pull/*/head:refs/remotes/origin/pr/*'
        } else {
            branchCheckout = env.BRANCH_NAME
            refspecs = '+refs/heads/*:refs/remotes/origin/*'
        }
        stage('Checkout Selenified') {
            // Get the test code from GitHub repository
            checkout([
                    $class           : 'GitSCM',
                    branches         : [[name: "*/${branchCheckout}"]],
                    userRemoteConfigs: [[
                                                url    : 'https://github.com/Coveros/selenified.git',
                                                refspec: "${refspecs}"
                                        ]]
            ])
            sh "mkdir results"
        }
        try {
            stage('Run Unit Tests') {
                try {
                    sh "mvn clean test"
                } catch (e) {
                    throw e
                } finally {
                    sh "cat target/coverage-reports/jacoco-ut.exec >> jacoco-ut.exec"
                    sh "mkdir -p results/unit; mv target results/unit/"
                    archiveArtifacts artifacts: 'results/unit/target/surefire-reports/**'
                    junit 'results/unit/target/surefire-reports/TEST-*.xml'
                }
            }
            wrap([$class: 'Xvfb']) {
                stage('Execute HTMLUnit Tests') {
                    try {
                        // commenting out coveros tests, as site is too slow to run properly in htmlunit
                        sh 'mvn clean verify -Dskip.unit.tests -Ddependency-check.skip -Dfailsafe.groups.exclude="browser,coveros"'
                    } catch (e) {
                        throw e
                    } finally {
                        sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec"
                        sh "mkdir -p results/htmlunit; mv target results/htmlunit/"
                        archiveArtifacts artifacts: 'results/htmlunit/target/failsafe-reports/**'
                        junit 'results/htmlunit/target/failsafe-reports/TEST-*.xml'
                    }
                }
                stage('Execute Local Tests') {
                    try {
                        sh 'mvn clean verify -Dskip.unit.tests -Ddependency-check.skip -Dbrowser=chrome -Dfailsafe.groups.exclude="service" -Dheadless -DgeneratePDF'
                    } catch (e) {
                        throw e
                    } finally {
                        sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec"
                        sh "mkdir -p results/browserLocal; mv target results/browserLocal/"
                        archiveArtifacts artifacts: 'results/browserLocal/target/failsafe-reports/**'
                        junit 'results/browserLocal/target/failsafe-reports/TEST-*.xml'
                    }
                }
            }
            stage('Execute Dependency Check') {
                try {
                    sh 'mvn clean verify -Dskip.unit.tests -Dskip.integration.tests'
                } catch (e) {
                    throw e
                } finally {
                    sh "mv target/dependency-check-report.* ."
                    archiveArtifacts artifacts: 'dependency-check-report.html'
                    dependencyCheckPublisher canComputeNew: false, defaultEncoding: '', healthy: '', pattern: 'dependency-check-report.xml', unHealthy: ''
                }
            }
            withCredentials([
                    usernamePassword(
                            credentialsId: 'saucelabs',
                            usernameVariable: 'sauceusername',
                            passwordVariable: 'saucekey'
                    )
            ]) {
                stage('Update Test Site') {
                    sh 'scp public/* ec2-user@34.233.135.10:/var/www/noindex/'
                }
                stage('Execute Hub Tests') {
                    try {
//                      sh "mvn clean verify -Dskip.unit.tests -Dbrowser='name=Chrome&platform=Windows&screensize=maximum,name=Chrome&platform=Mac,name=Firefox&platform=Windows,name=Firefox&platform=Mac&screensize=1920x1440,InternetExplorer,Edge,Safari' -Dfailsafe.threads=30 -Dfailsafe.groups.exclude='service,local' -DappURL=http://34.233.135.10/ -Dhub=https://${sauceusername}:${saucekey}@ondemand.saucelabs.com"
                        sh "mvn clean verify -Dskip.unit.tests -Ddependency-check.skip -Dbrowser='name=Chrome&platform=Windows&screensize=maximum,name=Chrome&platform=Mac' -Dfailsafe.threads=30 -Dfailsafe.groups.exclude='service,local,coveros' -DappURL=http://34.233.135.10/ -Dhub=https://${sauceusername}:${saucekey}@ondemand.saucelabs.com"
                    } catch (e) {
                        throw e
                    } finally {
                        sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec"
                        sh "mkdir -p results/browserRemote; mv target results/browserRemote/"
                        archiveArtifacts artifacts: 'results/browserRemote/target/failsafe-reports/**'
                        junit 'results/browserRemote/target/failsafe-reports/TEST-*.xml'
                    }
                }
            }
        } finally {
            withCredentials([
                    string(
                            credentialsId: 'sonar-token',
                            variable: 'sonartoken'
                    ),
                    string(
                            credentialsId: 'sonar-github',
                            variable: 'SONAR_GITHUB_TOKEN'
                    )
            ]) {
                stage('Perform SonarQube Analysis') {
                    def sonarCmd = "mvn clean compile sonar:sonar -Dsonar.login=${env.sonartoken}"
                    if (branch == 'develop' || branch == 'master') {
                        sh "${sonarCmd} -Dsonar.branch=${branch}"
                    } else {
                        if (pullRequest) {
                            sh "${sonarCmd} -Dsonar.analysis.mode=preview -Dsonar.branch=${branch} -Dsonar.github.pullRequest=${pullRequest} -Dsonar.github.repository=Coveros/${env.PROJECT} -Dsonar.github.oauth=${SONAR_GITHUB_TOKEN}"
                        } else {
                            sh "${sonarCmd}"
                        }
                    }
                }
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
                    if (branch == 'develop' || branch == 'master' || (pullRequest && currentBuild.currentResult == "SUCCESS")) {
                        def message = "Selenified%20build%20completed%20for%20`${env.BRANCH_NAME}`.%20It%20was%20a%20${currentBuild.currentResult}.%20Find%20the%20details%20at%20${env.BUILD_URL}."
                        sh "curl -s -X POST 'https://slack.com/api/chat.postMessage?token=${env.botToken}&channel=%23selenified&text=${message}'"
                    }
                }
            }
        }
    }
}