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
                    sh "cat target/coverage-reports/jacoco-ut.exec >> jacoco-ut.exec;"
                    sh "mkdir -p results/unit; mv target results/unit/"
                    archiveArtifacts artifacts: 'results/unit/target/surefire-reports/**'
                    junit 'results/unit/target/surefire-reports/TEST-*.xml'
                }
            }
            wrap([$class: 'Xvfb']) {
                stage('Execute HTMLUnit Tests') {
                    try {
                        sh 'mvn clean verify -Dskip.unit.tests -Dbrowser=htmlunit -Dheadless'
                    } catch (e) {
                        throw e
                    } finally {
                        sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec;"
                        sh "mkdir -p results/htmlunit; mv target results/htmlunit/"
                        archiveArtifacts artifacts: 'results/htmlunit/target/failsafe-reports/**'
                        junit 'results/htmlunit/target/failsafe-reports/TEST-*.xml'
                    }
                }
                stage('Execute Local Tests') {
                    try {
                        sh 'mvn clean verify -Dskip.unit.tests -Dbrowser=chrome -Dfailsafe.groups.exclude="service" -Dheadless'
                    } catch (e) {
                        throw e
                    } finally {
                        sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec;"
                        sh "mkdir -p results/browserLocal; mv target results/browserLocal/"
                        archiveArtifacts artifacts: 'results/browserLocal/target/failsafe-reports/**'
                        junit 'results/browserLocal/target/failsafe-reports/TEST-*.xml'
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
                stage('Update Test Site') {
                    sh 'scp public/* ec2-user@34.233.135.10:/var/www/noindex/'
                }
                stage('Execute Hub Tests') {
                    try {
                        // need to keep updating safari, as version is hard coded due to sauce defaulting to old 8.0 version
                        sh "mvn clean verify -Dskip.unit.tests -Dbrowser='browserName=Chrome,browserName=Firefox,browserName=InternetExplorer,browserName=Edge&devicePlatform=Windows 10,browserName=Safari&browserVersion=12.0&devicePlatform=macOS 10.14' -Dfailsafe.threads=30 -Dfailsafe.groups.exclude='service,local' -DappURL=http://34.233.135.10/ -Dhub=https://${sauceusername}:${saucekey}@ondemand.saucelabs.com"
                    } catch (e) {
                        throw e
                    } finally {
                        sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec;"
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
                    def sonarCmd = "mvn clean compile sonar:sonar -Dsonar.login=${env.sonartoken} -Dsonar.junit.reportPaths='results/unit/target/surefire-reports,results/htmlunit/target/failsafe-reports,results/browserLocal/target/failsafe-reports,results/browserRemote/target/failsafe-reports' -Dsonar.jacoco.reportPaths=jacoco-ut.exec,jacoco-it.exec"
                    if (branch == 'develop' || branch == 'master') {
                        sh "${sonarCmd} -Dsonar.branch=${branch}"
                    } else {
                        if (pullRequest) {
                            sh "${sonarCmd} -Dsonar.analysis.mode=preview -Dsonar.branch=${branch} -Dsonar.github.pullRequest=${pullRequest} -Dsonar.github.repository=Coveros/${env.PROJECT} -Dsonar.github.oauth=${SONAR_GITHUB_TOKEN}"
                        } else {
                            sh "${sonarCmd} -Dsonar.analysis.mode=preview"
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
                    if (branch == 'develop' || branch == 'master' || pullRequest) {
                        def message = "Selenified%20build%20completed%20for%20`${env.BRANCH_NAME}`.%20It%20was%20a%20${currentBuild.currentResult}.%20Find%20the%20details%20at%20${env.BUILD_URL}."
                        sh "curl -s -X POST 'https://slack.com/api/chat.postMessage?token=${env.botToken}&channel=%23selenified&text=${message}'"
                    }

                }
            }
        }
    }
}

def notify(String email, String project, String rawBranchName, String urlBranch) {
    withCredentials(
            [
                    usernamePassword(credentialsId: VibrentConstants.SLACK_USER_TOKEN, passwordVariable: 'USERSTOKEN', usernameVariable: ''),
                    usernamePassword(credentialsId: VibrentConstants.SLACK_BOT_TOKEN, passwordVariable: 'BOTTOKEN', usernameVariable: '')
            ]
    ) {
        def channel
        // Notify the user that broke the build.
        echo "Notifying ${email} that they broke the build."

        // Get the list of users that currently exist in Slack

        def results = sh script: "curl -XPOST 'https://slack.com/api/users.list?token=${USERSTOKEN}' | tr -d '\n'", returnStdout: true
        def resultsObject = readJSON text: results

        //Filter the list for users who's emails match the change author
        def users = resultsObject['members'].findAll {
            it['profile']['email'] == email
        }

        // Message users that match. This will usually only be one or zero users.
        // However, if we don't know which of several users to message,
        // it's easier to just message all of them than to figure out which one is correct.
        users.each {
            results = sh script: "curl -XPOST 'https://slack.com/api/im.open?token=${BOTTOKEN}&user=${it['id']}'", returnStdout: true
            channel = readJSON(text: results)['channel']['id']

            sh "curl -IL -XPOST 'https://slack.com/api/chat.postMessage?token=${BOTTOKEN}&channel=${channel}&text=Build+Failed+-+${project}+-+${rawBranchName}+-+${env.BUILD_NUMBER}'"
            sh "curl -IL -XPOST 'https://slack.com/api/chat.postMessage?token=${BOTTOKEN}&channel=${channel}&text=Please+see+the+build+log+for+more+details%3A+${URLEncoder.encode(env.RUN_DISPLAY_URL, "UTF-8")}'"
        }
    }
}