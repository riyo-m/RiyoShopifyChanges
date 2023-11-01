node('e2e') {
    stage('checkout') {
        git credentialsId: 'basic-ssh', branch: 'master', url: 'git@github.com:vertexinc/connector-quality-java.git'
    }
    stage('smoke-test') {
        container('mvn3-jdk11') {
            try {
                sh """
          cd ConnectorQuality
          mvn test -Dgroups=sfcrm_smoke -e -Dservices.webdriver.provisioned=true -Dservices.webdriver.headless=true
        """
            } catch (e) {
                // archiveArtifacts artifacts: 'ConnectorQuality/errorScreenshots/*.png',
                // currentBuild.result = 'FAILURE'
                // throw e
            }
        }
    }
    stage('report test results') {
        archiveArtifacts artifacts: 'ConnectorQuality/target/surefire-reports/**.xml', followSymlinks: false, allowEmptyArchive: true
        archiveArtifacts artifacts: 'ConnectorQuality/target/surefire-reports/**.html', followSymlinks: false, allowEmptyArchive: true
        archiveArtifacts artifacts: 'ConnectorQuality/errorScreenshots/*.png', followSymlinks: false, allowEmptyArchive: true
        step([$class: 'Publisher'])
    }

    stage('Import Results') {
        step([$class: 'XrayImportBuilder', endpointName: '/junit', importFilePath: 'ConnectorQuality/target/surefire-reports/junitreports/TEST-com.vertex.quality.connectors.salesforce.tests.crm.*xml', importToSameExecution: 'true', projectKey: 'CSFCRM', serverInstance: 'CLOUD-47847935-53f5-4e62-b39a-16a3e619ed4a', testExecKey: 'CSFCRM-528'])
    }

    stage('Email Report') {
        emailext attachLog: true, attachmentsPattern: 'ConnectorQuality/target/surefire-reports/**.xml, ConnectorQuality/target/surefire-reports/**.html, ConnectorQuality/errorScreenshots/*.png',
                body: '${FILE,path="ConnectorQuality/target/surefire-reports/emailable-report.html"}',
                to: "OSeriesConnectorsTesting@vertexinc.com",
                subject: "${env.JOB_NAME} Build# ${env.BUILD_NUMBER} ${currentBuild.currentResult}"
    }
    stage('invoke_regression_tests') {
        build job: 'Salesforce_CRM_Regression', propagate: false, wait: false
    }
}