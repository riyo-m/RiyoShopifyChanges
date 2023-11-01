#!groovy
node('e2e') {
    stage('checkout') {
        checkout scm
    }
    stage('verify build') {
        container('mvn3-jdk11') {
            sh """
                cd ConnectorQuality
                mvn clean compile
            """
        }
    }
    stage('sonar qube') {
        script {
            withSonarQubeEnv('sonarqube.devops.vtxdev.net') {
                container('mvn3-jdk11') {
                    sh """
                        cd ConnectorQuality
                        mvn compile sonar:sonar -Dsonar.projectName=connector-quality-java -Dsonar.projectKey=connector-quality-java -Dsonar.pullrequest.branch=${env.GIT_BRANCH} -Dsonar.pullrequest.key=${env.ghprbPullId}
                    """
                }
            }
        }
    }
}
