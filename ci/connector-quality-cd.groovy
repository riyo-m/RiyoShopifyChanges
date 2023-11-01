#!groovy
node('e2e') {
    stage('checkout') {
        checkout scm
    }
    stage('sonar qube') {
        script {
            withSonarQubeEnv('sonarqube.devops.vtxdev.net') {
                container('mvn3-jdk11') {
                    sh """
                        cd ConnectorQuality
                        mvn compile sonar:sonar -Dsonar.projectName=connector-quality-java -Dsonar.projectKey=connector-quality-java
                    """
                }
            }
        }
    }
}