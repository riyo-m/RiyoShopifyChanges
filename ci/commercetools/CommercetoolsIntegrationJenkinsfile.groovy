node('e2e') {
    stage('checkout') {
        git credentialsId: 'basic-ssh', url: 'git@github.com:vertexinc/connector-quality-java.git', branch: '${BranchName}'
    }

    stage('smoke-test') {
        container('mvn3-jdk11') {
            try {
                sh """
      cd ConnectorQuality
      mvn clean test -Dgroups="${GroupName}" -e -Dservices.webdriver.provisioned=true -Dservices.webdriver.headless=true -Dmaven.test.failure.ignore=true
    """
            } catch (e) {
                archiveArtifacts artifacts: 'ConnectorQuality/errorScreenshots/*.png'
                currentBuild.result = 'FAILURE'
                throw e
            }
        }
    }

    if (params.JacocoReport.toBoolean() == true) {
        stage('checkout app repo') {
            checkout([
                    $class           : 'GitSCM',
                    branches         : [[name: "qa"]],
                    userRemoteConfigs: [[
                                                url          : 'git@github.com:vertexinc/connector-commercetools2.git',
                                                credentialsId: 'basic-ssh'
                                        ]]
            ])
        }
        stage('Pull connector-commercetools2 app docker image') {
            container('jnlp') {
                sh """
            eval \$(aws ecr get-login --no-include-email --region us-east-1)
            docker pull 497397011574.dkr.ecr.us-east-1.amazonaws.com/connector-commercetools2:qa
        """
            }
        }
        stage('Extracting class files') {
            container('jnlp') {
                sh """
        mkdir -p image_connector-commercetools2 connector-commercetools2 jar_connector-commercetools2
        docker save 497397011574.dkr.ecr.us-east-1.amazonaws.com/connector-commercetools2:qa -o image_connector-commercetools2.tar
        cd image_connector-commercetools2
        tar -xf ../image_connector-commercetools2.tar
        cd ../connector-commercetools2 
        find ../image_connector-commercetools2/ -type f -name \"layer.tar\" -exec tar xf {} \\;
        unzip -o connector-commercetools2.jar -d ../jar_connector-commercetools2
        cd ..
      """
            }
        }
        stage('Fetching JaCoCo dump') {
            container('jnlp') {
                sh """
      mvn jacoco:dump@pull-test-data -Djacoco.address=commercetools.qa.svc.cluster.local -Djacoco.port=36320 -Djacoco.skip=false
      """
            }
        }
        stage('Publishing Jacoco report') {
            container('jnlp') {
                sh """
      cd /home/jenkins/agent/workspace/commercetools/commercetools_regression
      ls -lah
      """
                jacoco(
                        execPattern: 'target/jacoco.exec',
                        classPattern: 'jar_connector-commercetools2/BOOT-INF/classes',
                        sourcePattern: 'src/main/java',
                        exclusionPattern: '**/*Spec.class'
                )
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
        step([$class: 'XrayImportBuilder', endpointName: '/junit', importFilePath: 'ConnectorQuality/target/surefire-reports/junitreports/TEST-com.vertex.quality.connectors.commercetools.api.tests.*xml', importToSameExecution: 'true', projectKey: 'CSAPCT', serverInstance: 'CLOUD-47847935-53f5-4e62-b39a-16a3e619ed4a', testExecKey: "${TestExecKey}"])
    }

    stage('Email Report') {
        emailext attachLog: true, attachmentsPattern: 'ConnectorQuality/target/surefire-reports/**.xml, ConnectorQuality/target/surefire-reports/**.html, ConnectorQuality/errorScreenshots/*.png',
                body: '${FILE,path="ConnectorQuality/target/surefire-reports/emailable-report.html"}',
                to: "${EmailIds}",
                subject: "${env.JOB_NAME} Build# ${env.BUILD_NUMBER} ${currentBuild.currentResult}"
    }

}