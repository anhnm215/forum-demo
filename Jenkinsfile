// color code for Slack noti
def COLOR_MAP = [
    'SUCCESS': 'good',
    'FAILURE': 'danger',
]

pipeline {
    agent any

    stages {
        stage('Fetch code') {
            steps {
                git branch: 'tomcat', url: 'https://github.com/anhnm215/forum-demo.git'
            }
        }

        stage('Build') {
            steps {
                // run shell commands
                sh 'mvn install -DskipTests'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        // code analysis through checkstyle, this will generate checkstyle report
        stage('Checkstyle Analysis') {
            steps {
                sh 'mvn checkstyle:checkstyle'
            }
        }

        // code analysis with sonarqube
        // upload the results and checkstyle report, unit test report,... to sonarqube server
        stage('Sonar Analysis') {
            environment {
                // tool from global tool configuration
                scannerHome = tool 'sonar6.2'
            }

            steps {
                withSonarQubeEnv('sonar') {
                    sh '''${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=forum-demo \
                    -Dsonar.projectName=forum-demo \
                    -Dsonar.projectVersion=1.0 \
                    -Dsonar.sources=src/ \
                    -Dsonar.java.binaries=target/classes/com/demo/forum/ \
                    -Dsonar.junit.reportsPath=target/surefire-reports/ \
                    -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml'''
                    // ${scannerHome} value is the location of the 'sonar4.7' tool
                    // -Dsonar.sources: directory to scan in source code
                    // -Dsonar.junit.reportsPath: unit test report path
                    // -Dsonar.java.checkstyle.reportPaths : checkstyle report path
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        // upload the artifact to Nexus server
        stage('UploadArtifact') {
            steps {
                    nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    nexusUrl: '172.31.25.6:8081',
                    groupId: 'QA',
                    version: "${env.BUILD_ID}-${env.BUILD_TIMESTAMP}",
                    repository: 'forum-repo', // repo name on Nexus server
                    credentialsId: 'nexuslogin', // Nexus credentials saved in Jenkins
                    artifacts: [
                        [artifactId: 'forumapp',
                        classifier: '',
                        file: 'target/forum-0.0.1-SNAPSHOT.war',
                        type: 'war']
                    ]
                )
            }
        }
        
        stage("Deploy artifact to tomcat") {
            steps {
                ansiblePlaybook credentialsId: 'ansible',   // credentials for ansible to connect to tomcat server saved in Jenkins
                    disableHostKeyChecking: true,
                    installation: 'ansible',                // Ansible installation name in Jenkins Tools config
                    inventory: 'inventory.yaml',
                    playbook: 'tomcat-deploy.yaml',
                    extraVars   : [
                        mvn_artifact_version: "${env.BUILD_ID}-${env.BUILD_TIMESTAMP}",
                    ]
            }    
        }
    }

    // post installation step that sends the notification
    post {
        always {
            echo 'Slack notifications.'
            slackSend channel: '#all-cicd-demo',
                color: COLOR_MAP[currentBuild.currentResult],
                // currentBuild: a global variable that gets generated in runtime
                // currentBuild.currentResult typically returns "SUCCESS", "UNSTABLE" or "FAILURE"
                message: "*${currentBuild.currentResult}:* Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} \n More info at: ${env.BUILD_URL}"
        }
    }
}