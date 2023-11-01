node('e2e') {
    stage('checkout') {
        git credentialsId: 'basic-ssh', url: 'git@github.com:vertexinc/connector-quality-java.git', branch: '${BranchName}'
    }
    stage('regression-test') {
        container('mvn3-jdk11') {
            try {

                sh """
          cd ConnectorQuality
          mvn clean test -Dgroups="mirakl_regression" -DApiUrl="${ApiUrl}" -e -Dservices.webdriver.provisioned=true -Dservices.webdriver.headless=true -Dmaven.test.failure.ignore=true
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
                                                url          : 'git@github.com:vertexinc/connector-mirakl.git',
                                                credentialsId: 'basic-ssh'
                                        ]]
            ])
        }
        stage('Pull connector-mirakl app docker image') {
            container('jnlp') {
                sh """
            eval \$(aws ecr get-login --no-include-email --region us-east-1)
            docker pull 497397011574.dkr.ecr.us-east-1.amazonaws.com/connector-mirakl:qa
        """
            }
        }
        stage('Extracting class files') {
            container('jnlp') {
                sh """
        mkdir -p image_connector-mirakl connector-mirakl jar_connector-mirakl
        docker save 497397011574.dkr.ecr.us-east-1.amazonaws.com/connector-mirakl:qa -o image_connector-mirakl.tar
        cd image_connector-mirakl
        tar -xf ../image_connector-mirakl.tar
        cd ../connector-mirakl 
        find ../image_connector-mirakl/ -type f -name \"layer.tar\" -exec tar xf {} \\;
        unzip -o connector-mirakl.jar -d ../jar_connector-mirakl
        cd ..
      """
            }
        }
        stage('Fetching JaCoCo dump') {
            container('jnlp') {
                sh """
      mvn jacoco:dump@pull-test-data -Djacoco.address=mirakl.qa.svc.cluster.local -Djacoco.port=36320 -Djacoco.skip=false
      """
            }
        }
        stage('Publishing Jacoco report') {
            container('jnlp') {
                sh """
      cd /home/jenkins/agent/workspace/mirakl/mirakl_regression
      ls -lah
      """
                jacoco(
                        execPattern: 'target/jacoco.exec',
                        classPattern: 'jar_connector-mirakl/BOOT-INF/classes',
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
        step([$class: 'XrayImportBuilder', endpointName: '/junit', importFilePath: 'ConnectorQuality/target/surefire-reports/junitreports/TEST-com.vertex.quality.connectors.mirakl.api.tests.*xml', importToSameExecution: 'true', projectKey: 'MIR', serverInstance: 'CLOUD-47847935-53f5-4e62-b39a-16a3e619ed4a', testExecKey: 'MIR-74'])
    }

    stage('Email Report') {
        emailext attachLog: true, attachmentsPattern: 'ConnectorQuality/target/surefire-reports/**.xml, ConnectorQuality/target/surefire-reports/**.html, ConnectorQuality/errorScreenshots/*.png',
                body: '${FILE,path="ConnectorQuality/target/surefire-reports/emailable-report.html"}',
                to: "${EmailIds}",
                subject: "${env.JOB_NAME} Build# ${env.BUILD_NUMBER} ${currentBuild.currentResult}"
    }

}