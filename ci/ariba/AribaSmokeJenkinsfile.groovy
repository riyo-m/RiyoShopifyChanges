node('e2e') {
    stage('checkout') {
        git credentialsId: 'basic-ssh', url: 'git@github.com:vertexinc/connector-quality-java.git'
    }
    stage('integration-test') {
        container('mvn3-jdk11') {
            try {
                sh """
          cd ConnectorQuality
          mvn test -Dgroups=ariba_ui -e -Dservices.webdriver.provisioned=true -Dservices.webdriver.headless=true
        """
            } catch (e) {
                archiveArtifacts artifacts: 'ConnectorQuality/errorScreenshots/*.png'
                currentBuild.result = 'FAILURE'
                throw e
            }
        }
    }
}