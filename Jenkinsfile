pipeline {
    agent any

    tools {
        jdk 'java-21'
    }

    environment {
        DOCKER_EXE = 'C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main',
                        url: 'https://github.com/dhiraj-jadhav3121/GRAMPANCHAT-WEBSITE-BACKEND.git'
            }
        }

        stage('Check Maven') {
            steps {
                bat 'mvn -version'
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

        stage('Check Docker') {
            steps {
                bat "\"${DOCKER_EXE}\" --version"
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "\"${DOCKER_EXE}\" build -t grampanchayat-backend:latest ."
            }
        }

        stage('Stop Old Container') {
            steps {
                bat "\"${DOCKER_EXE}\" stop grampanchayat-backend || exit 0"
                bat "\"${DOCKER_EXE}\" rm grampanchayat-backend || exit 0"
            }
        }

        stage('Run Docker Container') {
            steps {
                bat "\"${DOCKER_EXE}\" run -d --name grampanchayat-backend -p 8082:8080 grampanchayat-backend:latest"
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