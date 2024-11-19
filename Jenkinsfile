pipeline {
    agent any

    tools {
        // exact tool name config in Manage Jenkins > Tools
        maven "MAVEN3"
        jdk "OracleJDK11"
    }

    stages {
        stage('Fetch code') {
            steps {
                git branch: 'main', url: 'https://github.com/anhnm215/forum-demo.git'
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
    }
}