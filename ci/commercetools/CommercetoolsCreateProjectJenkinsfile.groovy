node('e2e') {
    stage('checkout') {
        git credentialsId: 'basic-ssh', url: 'git@github.com:vertexinc/connector-quality-java.git', branch: '${BranchName}'
    }

    stage('smoke-test') {
        container('mvn3-jdk11') {
            try {
                sh """
      cd ConnectorQuality
      mvn clean test -Dgroups="${GroupName}" -DprojectName="${projectName}" -DprojectKey="${projectKey}" -DcustAppName="${custAppName}" -DappUrl="${appUrl}" -DapiClientName="${apiClientName}" -DapiClientScope="${apiClientScope}" -DtrustedId="${trustedId}" -Dusername="${username}" -Dpassword="${password}" -DaddCleanseEndPt="${addCleanseEndPt}" -DtaxCalcEndPt="${taxCalcEndPt}" -DcompanyCode="${companyCode}" -DdStreet1="${dStreet1}" -DdCity="${dCity}" -DdState="${dState}" -DdCounty="${dCounty}" -DdZipCode="${dZipCode}" -DdCountry="${dCountry}" -DsStreet1="${sStreet1}" -DsCity="${sCity}" -DsState="${sState}" -DsCounty="${sCounty}" -DsZipCode="${sZipCode}" -DsCountry="${sCountry}" -Dhost="${host}" -DproductTypeName="${productTypeName}" -DproductTypeDesc="${productTypeDesc}" -DcustGroupName="${custGroupName}" -e -Dservices.webdriver.provisioned=true -Dservices.webdriver.headless=true -Dmaven.test.failure.ignore=true
    """
            } catch (e) {
                archiveArtifacts artifacts: 'ConnectorQuality/errorScreenshots/*.png'
                currentBuild.result = 'FAILURE'
                throw e
            }
        }
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
                to: "${EmailIds}",
                subject: "${env.JOB_NAME} Build# ${env.BUILD_NUMBER} ${currentBuild.currentResult}"
    }

}