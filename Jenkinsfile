pipeline {
    agent any

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