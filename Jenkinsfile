pipeline {
    agent any

    tools {
        jdk 'java-21'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main',
                        url: 'https://github.com/dhiraj-jadhav3121/GRAMPANCHAT-WEBSITE-BACKEND.git'
            }
        }

        stage('Run Test Cases') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Build Jar File') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t grampanchayat-backend:latest .'
            }
        }

        stage('Stop Old Container') {
            steps {
                bat 'docker stop grampanchayat-backend || exit 0'
                bat 'docker rm grampanchayat-backend || exit 0'
            }
        }

        stage('Run Docker Container') {
            steps {
                bat 'docker run -d --name grampanchayat-backend -p 8080:8080 grampanchayat-backend:latest'
            }
        }
    }

    post {
        success {
            echo 'Grampanchayat Backend Docker Deployment Successful'
        }
        failure {
            echo 'Pipeline Failed. Check console output.'
        }
    }
}