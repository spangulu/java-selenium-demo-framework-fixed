pipeline {
    agent any
    tools { jdk 'JDK25'; maven 'Maven3' }
    stages {
        stage('Checkout') { steps { checkout scm } }
        stage('Test') {
            steps { sh 'mvn clean test -Pci' }
        }
    }
    post {
        always {
            archiveArtifacts artifacts: 'target/reports/**,target/screenshots/**,target/logs/**', allowEmptyArchive: true
            junit 'target/surefire-reports/*.xml'
        }
    }
}
