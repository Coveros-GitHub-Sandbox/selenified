def branch
def pullRequest
def refspecs
def instanceId
def publicIp

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
            parallel(
                    "Execute Local Tests": {
                        stage('Execute HTMLUnit Tests') {
                            try {
                                // commenting out coveros tests, as site is too slow to run properly in htmlunit
                                sh 'mvn clean verify -Dskip.unit.tests -Ddependency-check.skip -Dfailsafe.groups.exclude="browser"'
                            } catch (e) {
                                throw e
                            } finally {
                                sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec"
                                sh "mkdir -p results/htmlunit; mv target results/htmlunit/"
                                archiveArtifacts artifacts: 'results/htmlunit/target/failsafe-reports/**'
                                junit 'results/htmlunit/target/failsafe-reports/TEST-*.xml'
                                publishHTML([
                                        allowMissing         : false,
                                        alwaysLinkToLastBuild: true,
                                        keepAll              : true,
                                        reportDir            : 'results/htmlunit/target/failsafe-reports',
                                        reportFiles          : 'report.html',
                                        reportName           : 'HTMLUnit Report'
                                ])
                            }
                        }
                    },
                    "Execute Dependency Check": {
                        stage('Execute Dependency Check') {
                            try {
                                sh 'sleep 60'
                                sh 'mvn verify -Dskip.unit.tests -Dskip.integration.tests'
                            } catch (e) {
                                throw e
                            } finally {
                                sh "mv target/dependency-check-report.* ."
                                archiveArtifacts artifacts: 'dependency-check-report.html'
                                dependencyCheckPublisher canComputeNew: false, defaultEncoding: '', healthy: '', pattern: 'dependency-check-report.xml', unHealthy: ''
                            }
                        }
                    },
            )
            parallel(
                    "Execute Chrome Proxy Tests": {
                        try {
                            stage('Start ZAP') {
                                startZap(
                                        host: "localhost",
                                        port: 9092,
                                        zapHome: "/var/lib/zap"
                                )
                            }
                            stage('Execute Chrome Tests Through Proxy') {
                                try {
                                    sh 'mvn clean verify -Dskip.unit.tests -Ddependency-check.skip -Dbrowser=chrome -Dproxy=localhost:9092 -Dfailsafe.groups.exclude="https" -DgeneratePDF'
                                } catch (e) {
                                    throw e
                                } finally {
                                    sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec"
                                    sh "mkdir -p results/chrome; mv target results/chrome/"
                                    archiveArtifacts artifacts: 'results/chrome/target/failsafe-reports/**'
                                    junit 'results/chrome/target/failsafe-reports/TEST-*.xml'
                                    publishHTML([
                                            allowMissing         : false,
                                            alwaysLinkToLastBuild: true,
                                            keepAll              : true,
                                            reportDir            : 'results/chrome/target/failsafe-reports',
                                            reportFiles          : 'report.html',
                                            reportName           : 'Chrome Report'
                                    ])
                                }
                            }
                        } finally {
                            stage('Get ZAP Results') {
                                sh 'mkdir -p results/zap'
                                sh 'wget -q -O results/zap/report.html http://localhost:9092/OTHER/core/other/htmlreport'
                                sh 'wget -q -O results/zap/report.xml http://localhost:9092/OTHER/core/other/xmlreport'
                                archiveArtifacts artifacts: 'results/zap/**'
                                publishHTML([
                                        allowMissing         : false,
                                        alwaysLinkToLastBuild: true,
                                        keepAll              : true,
                                        reportDir            : 'results/zap',
                                        reportFiles          : 'report.html',
                                        reportName           : 'ZAP Report'
                                ])
                                archiveZap()
                            }
                        }
                    },
                    "Launch Selenified Test Server": {
                        stage("Launch Test Site") {
                            sh """
                                aws ec2 run-instances \
                                    --image-id ami-ede06892 \
                                    --instance-type t2.micro \
                                    --key-name jenkins-secureci \
                                    --security-group-ids sg-6171a029 \
                                    --associate-public-ip-address \
                                    --subnet-id subnet-bfb2c2f7 > instance.json
                            """
                            instanceId = sh(
                                    script: "cat instance.json | grep 'InstanceId' | cut -d '\"' -f 4",
                                    returnStdout: true
                            ).trim()
                            sh "aws ec2 create-tags --resources ${instanceId} --tags Key=Name,Value='Selenified Test Instance'"
                            sh "aws ec2 create-tags --resources ${instanceId} --tags Key=Owner,Value=Jenkins"
                            sh "aws ec2 describe-instances --instance-ids ${instanceId} >> instance.json"
                            publicIp = sh(
                                    script: "cat instance.json | grep 'PublicIpAddress' | cut -d '\"' -f 4",
                                    returnStdout: true
                            ).trim()
                        }
                    },
            )
            withCredentials([
                    usernamePassword(
                            credentialsId: 'saucelabs',
                            usernameVariable: 'sauceusername',
                            passwordVariable: 'saucekey'
                    )
            ]) {
                stage('Update Test Site') {
                    sh "ssh -oStrictHostKeyChecking=no ec2-user@${publicIp} 'sudo rm /var/www/noindex/*; sudo chown ec2-user.ec2-user /var/www/noindex/'"
                    sh "scp -oStrictHostKeyChecking=no public/* ec2-user@${publicIp}:/var/www/noindex/"
                }
                // this will be replaced by 'Execute Hub Tests' once #103 is completed. This is temporary to ensure all browser types can in fact run successfully
                stage('Execute Some Hub Tests') {
                    try {
                        String buildName = branchCheckout.replaceAll(/\//, " ") + " build " + env.BUILD_NUMBER + " Compatibility Tests"
                        sh "mvn clean verify -Dskip.unit.tests -DbuildName='${buildName}' -Dbrowser='name=Chrome&platform=Windows&screensize=maximum,name=Chrome&platform=Mac,name=Firefox&platform=Windows,name=Firefox&platform=Mac&screensize=1920x1440,IE,Edge,Safari' -Dheadless=false -Dfailsafe.threads=30 -Dfailsafe.groups.include='is' -DappURL=http://${publicIp}/ -Dhub=https://${sauceusername}:${saucekey}@ondemand.saucelabs.com"
                    } catch (e) {
                        throw e
                    } finally {
                        sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec"
                        sh "mkdir -p results/compatibility; mv target results/compatibility/"
                        archiveArtifacts artifacts: 'results/compatibility/target/failsafe-reports/**'
                        junit 'results/compatibility/target/failsafe-reports/TEST-*.xml'
                        publishHTML([
                                allowMissing         : false,
                                alwaysLinkToLastBuild: true,
                                keepAll              : true,
                                reportDir            : 'results/compatibility/target/failsafe-reports',
                                reportFiles          : 'report.html',
                                reportName           : 'Compatibility Report'
                        ])
                    }
                }
                stage('Execute Hub Tests') {
                    try {
//                      sh "mvn clean verify -Dskip.unit.tests -Dbrowser='name=Chrome&platform=Windows&screensize=maximum,name=Chrome&platform=Mac,name=Firefox&platform=Windows,name=Firefox&platform=Mac&screensize=1920x1440,InternetExplorer,Edge,Safari' -Dfailsafe.threads=30 -Dfailsafe.groups.exclude='service,local' -DappURL=http://${publicIp}/ -Dhub=https://${sauceusername}:${saucekey}@ondemand.saucelabs.com"
                        sh "mvn clean verify -Dskip.unit.tests -Ddependency-check.skip -Dbrowser='name=Chrome&platform=Windows&screensize=maximum,name=Chrome&platform=Mac' -Dheadless=false -Dfailsafe.threads=30 -Dfailsafe.groups.exclude='service,local,coveros,wait' -DappURL=http://${publicIp}/ -Dhub=https://${sauceusername}:${saucekey}@ondemand.saucelabs.com"
                    } catch (e) {
                        throw e
                    } finally {
                        sh "cat target/coverage-reports/jacoco-it.exec >> jacoco-it.exec"
                        sh "mkdir -p results/sauce; mv target results/sauce/"
                        archiveArtifacts artifacts: 'results/sauce/target/failsafe-reports/**'
                        junit 'results/sauce/target/failsafe-reports/TEST-*.xml'
                        publishHTML([
                                allowMissing         : false,
                                alwaysLinkToLastBuild: true,
                                keepAll              : true,
                                reportDir            : 'results/sauce/target/failsafe-reports',
                                reportFiles          : 'report.html',
                                reportName           : 'Sauce Report'
                        ])
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
                parallel(
                        "Sonar Analysis": {
                            stage('Perform SonarQube Analysis') {
                                def sonarCmd = "mvn clean compile sonar:sonar -Dsonar.login=${env.sonartoken} -Dsonar.branch=${branch}"
                                if (branch != 'develop' && branch != 'master') {
                                    sonarCmd += " -Dsonar.analysis.mode=preview"
                                    if (pullRequest) {
                                        sonarCmd += " -Dsonar.github.pullRequest=${pullRequest} -Dsonar.github.repository=Coveros/${env.PROJECT} -Dsonar.github.oauth=${SONAR_GITHUB_TOKEN}"
                                    }
                                }
                                sh "${sonarCmd}"
                            }
                        },
                        "Terminate Selenified Test Server": {
                            sh "aws ec2 terminate-instances --instance-ids ${instanceId}"
                        },
                )
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
        if (branch == 'develop' || branch == 'master') {
            stage('Deploy to Maven Central') {
                sh "mvn clean deploy -Dskip.unit.tests -Ddependency-check.skip -Dskip.integration.tests"
            }
        }
    }
}
