node {
    ansiColor('xterm') {
        stage('Checkout Selenified') { // for display purposes
            // Get the test code from GitHub repository
            git(
                url: 'https://github.com/Coveros/selenified.git',
                branch: 'feature/selenium3'
            )
            sh "rm -rf target*"
            sh "rm -rf jacoco*"
        }
        stage('Update Test Site') {
            sh 'scp public/* ec2-user@172.31.2.65:/var/www/noindex/'
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
            step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: 'max.saperstone@coveros.com', sendToIndividuals: true])
        }
    }
}