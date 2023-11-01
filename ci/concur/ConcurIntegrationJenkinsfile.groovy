node('e2e') {
    stage('checkout') {
        git credentialsId: 'basic-ssh', url: 'git@github.com:vertexinc/connector-quality-java.git'
    }
    stage('smoke-test') {
        container('mvn3-jdk11') {
            try {
                sh """
          cd ConnectorQuality
          mvn test -Dgroups=concurAPI -e -Dservices.webdriver.provisioned=true -Dservices.webdriver.headless=true
        """
            } catch (e) {
                // archiveArtifacts artifacts: 'ConnectorQuality/errorScreenshots/*.png',
                // currentBuild.result = 'FAILURE'
                // throw e
            }
        }
    }
    stage('report test results') {
        archiveArtifacts artifacts: 'ConnectorQuality/target/surefire-reports/**.xml', followSymlinks: false
        archiveArtifacts artifacts: 'ConnectorQuality/target/surefire-reports/**.html', followSymlinks: false
        step([$class: 'Publisher'])
    }

    stage('invoke_smoke_tests') {
        build 'concur-smoke'
    }
}