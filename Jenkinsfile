pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'Java-17'
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

        stage('Archive Jar') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo 'Grampanchayat Backend Pipeline Successful'
        }
        failure {
            echo 'Pipeline Failed. Check console output.'
        }
    }
}