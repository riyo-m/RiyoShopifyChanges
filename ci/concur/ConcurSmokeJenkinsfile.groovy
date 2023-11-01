node('e2e') {
    stage('checkout') {
        git credentialsId: 'basic-ssh', url: 'git@github.com:vertexinc/connector-quality-java.git'
    }
    stage('smoke-test') {
        container('mvn3-jdk11') {
            try {
                sh """
          cd ConnectorQuality
          mvn test -Dgroups=concur_smoke -e -Dservices.webdriver.provisioned=true -Dservices.webdriver.headless=true
        """
            } catch (e) {
                archiveArtifacts artifacts: 'ConnectorQuality/errorScreenshots/*.png'
                currentBuild.result = 'FAILURE'
                throw e
            }
        }
    }
    stage('invoke_regression_tests') {
        build job: 'concur-regression', propagate: false, wait: false
    }
}