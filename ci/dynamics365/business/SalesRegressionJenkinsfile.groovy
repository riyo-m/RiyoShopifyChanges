node('e2e') {
    stage('checkout') {
        git credentialsId: 'basic-ssh',branch: 'master', url: 'git@github.com:vertexinc/connector-quality-java.git'
    }
    stage('regression-test') {
        container('mvn3-jdk11') {
            try {
                sh """
          cd ConnectorQuality
          mvn test -Dgroups=D365_Business_Central_Sales_Regression -e -Dservices.webdriver.provisioned=true -Dservices.webdriver.headless=true
        """
            } catch (e) {
                //archiveArtifacts artifacts: 'ConnectorQuality/errorScreenshots/*.png'
                //currentBuild.result = 'FAILURE'
                //throw e
            }
        }
    }
    stage('Import Results') {
        step([$class: 'XrayImportBuilder', endpointName: '/junit', importFilePath: 'ConnectorQuality/target/surefire-reports/junitreports/TEST-com.vertex.quality.connectors.dynamics365.business.tests.*xml', importToSameExecution: 'true', projectKey: 'CDBC', serverInstance: 'CLOUD-47847935-53f5-4e62-b39a-16a3e619ed4a', testExecKey: 'CDBC-764'])
    }

    stage('report test results') {
        archiveArtifacts artifacts: 'ConnectorQuality/target/surefire-reports/**.xml', followSymlinks: false, allowEmptyArchive: true
        archiveArtifacts artifacts: 'ConnectorQuality/target/surefire-reports/**.html', followSymlinks: false, allowEmptyArchive: true
        archiveArtifacts artifacts: 'ConnectorQuality/errorScreenshots/*.png', followSymlinks: false, allowEmptyArchive: true
        step([$class: 'Publisher'])
    }
    stage('Email Report') {
        emailext attachLog: true, attachmentsPattern: 'ConnectorQuality/target/surefire-reports/**.xml, ConnectorQuality/target/surefire-reports/**.html, ConnectorQuality/errorScreenshots/*.png',
                body: '${FILE,path="ConnectorQuality/target/surefire-reports/emailable-report.html"}',
                to: "OSeriesConnectorsTesting@vertexinc.com",
                subject: "${env.JOB_NAME} Build# ${env.BUILD_NUMBER} ${currentBuild.currentResult}"
    }
}
