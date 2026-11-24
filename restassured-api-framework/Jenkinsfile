pipeline {
    agent any

    tools {
        maven 'Maven3'   // name configured in Jenkins > Global Tool Configuration
        jdk 'JDK17'
    }

    triggers {
        cron('H 2 * * 1-5')   // nightly run on weekdays
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Run API Tests') {
            steps {
                sh 'mvn clean test'
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/surefire-reports/**', allowEmptyArchive: true
        }
    }
}
