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
                    sh "mvn clean test -Dalt.build.dir=results/unit"
                } catch (e) {
                    throw e
                } finally {
                    sh "cat results/unit/coverage-reports/jacoco-ut.exec >> jacoco-ut.exec"
                    junit 'results/unit/surefire-reports/TEST-*.xml'
                    publishHTML([
                            allowMissing         : false,
                            alwaysLinkToLastBuild: true,
                            keepAll              : true,
                            reportDir            : 'results/unit/site/jacoco-ut',
                            reportFiles          : 'index.html',
                            reportName           : 'Unit Test Coverage'
                    ])
                    publishHTML([
                            allowMissing         : false,
                            alwaysLinkToLastBuild: true,
                            keepAll              : true,
                            reportDir            : 'results/unit/surefire-reports',
                            reportFiles          : 'index.html',
                            reportName           : 'Unit Test Report'
                    ])
                }
            }
            parallel(
                    "Execute Local Tests": {
                        stage('Execute HTMLUnit Tests') {
                            try {
                                // commenting out coveros tests, as site is too slow to run properly in htmlunit
                                sh 'mvn clean verify -DmockPort=0 -Dalt.build.dir=results/htmlunit -Dskip.unit.tests -Ddependency-check.skip -Dfailsafe.groups.exclude="service,browser,coveros,hub"'
                            } catch (e) {
                                throw e
                            } finally {
                                sh "cat results/htmlunit/coverage-reports/jacoco-it.exec >> jacoco-it.exec"
                                junit 'results/htmlunit/failsafe-reports/TEST-*.xml'
                                publishHTML([
                                        allowMissing         : false,
                                        alwaysLinkToLastBuild: true,
                                        keepAll              : true,
                                        reportDir            : 'results/htmlunit/site/jacoco-it',
                                        reportFiles          : 'index.html',
                                        reportName           : 'HTMLUnit Test Coverage'
                                ])
                                publishHTML([
                                        allowMissing         : false,
                                        alwaysLinkToLastBuild: true,
                                        keepAll              : true,
                                        reportDir            : 'results/htmlunit/failsafe-reports',
                                        reportFiles          : 'report.html',
                                        reportName           : 'HTMLUnit Test Report'
                                ])
                            }
                        }
                    },
                    "Execute Services Tests": {
                        stage('Execute Service Tests') {
                            try {
                                sh 'mvn clean verify -DmockPort=1 -Dalt.build.dir=results/service -Dskip.unit.tests -Ddependency-check.skip -Dfailsafe.groups.include="service" -Dfailsafe.groups.exclude=""'
                            } catch (e) {
                                throw e
                            } finally {
                                sh "cat results/service/coverage-reports/jacoco-it.exec >> jacoco-it.exec"
                                junit 'results/service/failsafe-reports/TEST-*.xml'
                                publishHTML([
                                        allowMissing         : false,
                                        alwaysLinkToLastBuild: true,
                                        keepAll              : true,
                                        reportDir            : 'results/service/site/jacoco-it',
                                        reportFiles          : 'index.html',
                                        reportName           : 'Service Test Coverage'
                                ])
                                publishHTML([
                                        allowMissing         : false,
                                        alwaysLinkToLastBuild: true,
                                        keepAll              : true,
                                        reportDir            : 'results/service/failsafe-reports',
                                        reportFiles          : 'report.html',
                                        reportName           : 'Service Test Report'
                                ])
                            }
                        }
                    },
                    "Execute Dependency Check": {
                        stage('Execute Dependency Check') {
                            try {
                                sh 'mvn verify -Dalt.build.dir=depcheck -Dskip.unit.tests -Dskip.integration.tests'
                            } catch (e) {
                                throw e
                            } finally {
                                sh "mv depcheck/dependency-check-report.* ."
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
                                    sh 'mvn clean verify -Dalt.build.dir=results/chrome -Dskip.unit.tests -Ddependency-check.skip -Dbrowser=chrome -Dproxy=localhost:9092 -Dfailsafe.groups.exclude="https,hub,service" -DgeneratePDF'
                                } catch (e) {
                                    throw e
                                } finally {
                                    sh "cat results/chrome/coverage-reports/jacoco-it.exec >> jacoco-it.exec"
                                    junit 'results/chrome/failsafe-reports/TEST-*.xml'
                                    publishHTML([
                                            allowMissing         : false,
                                            alwaysLinkToLastBuild: true,
                                            keepAll              : true,
                                            reportDir            : 'results/chrome/site/jacoco-it',
                                            reportFiles          : 'index.html',
                                            reportName           : 'Chrome Test Coverage'
                                    ])
                                    publishHTML([
                                            allowMissing         : false,
                                            alwaysLinkToLastBuild: true,
                                            keepAll              : true,
                                            reportDir            : 'results/chrome/failsafe-reports',
                                            reportFiles          : 'report.html',
                                            reportName           : 'Chrome Test Report'
                                    ])
                                }
                            }
                        } finally {
                            stage('Get ZAP Results') {
                                sh 'mkdir -p results/zap'
                                sh 'wget -q -O results/zap/report.html http://localhost:9092/OTHER/core/other/htmlreport'
                                sh 'wget -q -O results/zap/report.xml http://localhost:9092/OTHER/core/other/xmlreport'
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
                            usernameVariable: 'SAUCE_USER',
                            passwordVariable: 'SAUCE_PASS'
                    ),
                    usernamePassword(
                            credentialsId: 'lambdatest',
                            usernameVariable: 'LAMBDA_USER',
                            passwordVariable: 'LAMBDA_PASS'
                    )
            ]) {
                parallel(
                        "Test Site": {
                            stage('Update Test Site') {
                                sh "ssh -oStrictHostKeyChecking=no ec2-user@${publicIp} 'sudo rm /var/www/noindex/*; sudo chown ec2-user.ec2-user /var/www/noindex/'"
                                sh "scp -oStrictHostKeyChecking=no public/* ec2-user@${publicIp}:/var/www/noindex/"
                            }
                        },
                        "Sauce Labs": {
                            stage('Verify Sauce Reporting') {
                                try {
                                    sh "mvn clean verify -DmockPort=0 -Dalt.build.dir=results/sauce -Dskip.unit.tests -Dbrowser='firefox' -Dheadless=false -Dfailsafe.threads=30 -Dfailsafe.groups.exclude='' -Dfailsafe.groups.include='sauce' -Dhub=https://${SAUCE_USER}:${SAUCE_PASS}@ondemand.saucelabs.com"
                                } catch (e) {
                                    throw e
                                } finally {
                                    sh "cat results/sauce/coverage-reports/jacoco-it.exec >> jacoco-it.exec"
                                    junit 'results/sauce/failsafe-reports/TEST-*.xml'
                                    publishHTML([
                                            allowMissing         : false,
                                            alwaysLinkToLastBuild: true,
                                            keepAll              : true,
                                            reportDir            : 'results/sauce/site/jacoco-it',
                                            reportFiles          : 'index.html',
                                            reportName           : 'Sauce Test Coverage'
                                    ])
                                    publishHTML([
                                            allowMissing         : false,
                                            alwaysLinkToLastBuild: true,
                                            keepAll              : true,
                                            reportDir            : 'results/sauce/failsafe-reports',
                                            reportFiles          : 'report.html',
                                            reportName           : 'Sauce Test Report'
                                    ])
                                }
                            }
                        },
                        "Lambda Test": {
                            stage('Verify Lambda Reporting') {
                                try {
                                    sh "mvn clean verify -DmockPort=1 -Dalt.build.dir=results/lambda -Dskip.unit.tests -Dbrowser='chrome' -Dheadless=false -Dfailsafe.threads=2 -Dfailsafe.groups.exclude='' -Dfailsafe.groups.include='lambda' -Dhub=https://${LAMBDA_USER}:${LAMBDA_PASS}@hub.lambdatest.com"
                                } catch (e) {
                                    throw e
                                } finally {
                                    sh "cat results/lambda/coverage-reports/jacoco-it.exec >> jacoco-it.exec"
                                    junit 'results/lambda/failsafe-reports/TEST-*.xml'
                                    publishHTML([
                                            allowMissing         : false,
                                            alwaysLinkToLastBuild: true,
                                            keepAll              : true,
                                            reportDir            : 'results/lambda/site/jacoco-it',
                                            reportFiles          : 'index.html',
                                            reportName           : 'Lamdba Test Coverage'
                                    ])
                                    publishHTML([
                                            allowMissing         : false,
                                            alwaysLinkToLastBuild: true,
                                            keepAll              : true,
                                            reportDir            : 'results/lambda/failsafe-reports',
                                            reportFiles          : 'report.html',
                                            reportName           : 'Lamdba Test Report'
                                    ])
                                }
                            }
                        }
                )
            }
            withCredentials([
                    usernamePassword(
                            credentialsId: 'saucelabs',
                            usernameVariable: 'HUB_USER',
                            passwordVariable: 'HUB_PASS'
                    ),
            ]) {
                // this will be replaced by 'Execute Hub Tests' once #103 is completed. This is temporary to ensure all browser types can in fact run successfully
                stage('Execute Compatibility Tests') {
                    try {
                        String buildName = branchCheckout.replaceAll(/\//, " ") + " build " + env.BUILD_NUMBER + " Compatibility Tests"
                        sh "mvn clean verify -Dalt.build.dir=results/compatibility -Dskip.unit.tests -DbuildName='${buildName}' -Dbrowser='name=Chrome&platform=Windows&screensize=maximum,name=Chrome&platform=Mac,name=Firefox&platform=Windows,name=Firefox&platform=Mac&screensize=1920x1440,IE,Edge,Safari' -Dheadless=false -Dfailsafe.threads=30 -Dfailsafe.groups.include='is' -DappURL=http://${publicIp}/ -Dhub=https://ondemand.saucelabs.com"
                    } catch (e) {
                        throw e
                    } finally {
                        sh "cat results/compatibility/coverage-reports/jacoco-it.exec >> jacoco-it.exec"
                        junit 'results/compatibility/failsafe-reports/TEST-*.xml'
                        publishHTML([
                                allowMissing         : false,
                                alwaysLinkToLastBuild: true,
                                keepAll              : true,
                                reportDir            : 'results/compatibility/site/jacoco-it',
                                reportFiles          : 'index.html',
                                reportName           : 'Compatibility Test Coverage'
                        ])
                        publishHTML([
                                allowMissing         : false,
                                alwaysLinkToLastBuild: true,
                                keepAll              : true,
                                reportDir            : 'results/compatibility/failsafe-reports',
                                reportFiles          : 'report.html',
                                reportName           : 'Compatibility Test Report'
                        ])
                    }
                }
                stage('Execute Hub Tests') {
                    try {
//                      sh "mvn clean verify -Dskip.unit.tests -Dbrowser='name=Chrome&platform=Windows&screensize=maximum,name=Chrome&platform=Mac,name=Firefox&platform=Windows,name=Firefox&platform=Mac&screensize=1920x1440,InternetExplorer,Edge,Safari' -Dfailsafe.threads=30 -Dfailsafe.groups.exclude='service,local' -DappURL=http://${publicIp}/ -Dhub=https://${sauceusername}:${saucekey}@ondemand.saucelabs.com"
                        sh "mvn clean verify -Dalt.build.dir=results/hub -Dskip.unit.tests -Ddependency-check.skip -Dbrowser='name=Chrome&platform=Windows&screensize=maximum,name=Chrome&platform=Mac' -Dheadless=false -Dfailsafe.threads=30 -Dfailsafe.groups.exclude='service,local,coveros,wait,hub' -DappURL=http://${publicIp}/ -Dhub=https://ondemand.saucelabs.com"
                    } catch (e) {
                        throw e
                    } finally {
                        sh "cat results/hub/coverage-reports/jacoco-it.exec >> jacoco-it.exec"
                        junit 'results/hub/failsafe-reports/TEST-*.xml'
                        publishHTML([
                                allowMissing         : false,
                                alwaysLinkToLastBuild: true,
                                keepAll              : true,
                                reportDir            : 'results/hub/site/jacoco-it',
                                reportFiles          : 'index.html',
                                reportName           : 'Hub Test Coverage'
                        ])
                        publishHTML([
                                allowMissing         : false,
                                alwaysLinkToLastBuild: true,
                                keepAll              : true,
                                reportDir            : 'results/hub/failsafe-reports',
                                reportFiles          : 'report.html',
                                reportName           : 'Hub Test Report'
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
                                GString sonarCmd = "mvn clean compile sonar:sonar -Dsonar.login=${env.sonartoken} -Dsonar.branch=${branch}"
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
                            stage('Terminate Selenified Test Servier') {
                                sh "aws ec2 terminate-instances --instance-ids ${instanceId}"
                            }
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
                        GString message = "Selenified%20build%20completed%20for%20`${env.BRANCH_NAME}`.%20It%20was%20a%20${currentBuild.currentResult}.%20Find%20the%20details%20at%20${env.BUILD_URL}."
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
