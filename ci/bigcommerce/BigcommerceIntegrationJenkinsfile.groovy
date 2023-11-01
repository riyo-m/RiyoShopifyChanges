node('e2e') {
    stage('checkout') {
        git credentialsId: 'basic-ssh', url: 'git@github.com:vertexinc/connector-quality-java.git', branch: '${BranchName}'
    }

    stage('integration-test') {
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

    stage('checkout app repo') {
        checkout([
                $class           : 'GitSCM',
                branches         : [[name: "qa"]],
                userRemoteConfigs: [[
                                            url          : 'git@github.com:vertexinc/connector-tradeshift.git',
                                            credentialsId: 'basic-ssh'
                                    ]]
        ])
    }

    stage('Pull connector-tradeshift app docker image') {
        container('jnlp') {
            sh """
            eval \$(aws ecr get-login --no-include-email --region us-east-1)
            docker pull 497397011574.dkr.ecr.us-east-1.amazonaws.com/connector-tradeshift:qa
        """
        }
    }

    stage('Extracting class files') {
        container('jnlp') {
            sh """
        mkdir -p image_connector-tradeshift connector-tradeshift jar_connector-tradeshift
        docker save 497397011574.dkr.ecr.us-east-1.amazonaws.com/connector-tradeshift:qa -o image_connector-tradeshift.tar
        cd image_connector-tradeshift
        tar -xf ../image_connector-tradeshift.tar
        cd ../connector-tradeshift 
        find ../image_connector-tradeshift/ -type f -name \"layer.tar\" -exec tar xf {} \\;
        unzip -o connector-tradeshift.jar -d ../jar_connector-tradeshift
        cd ..
      """
        }
    }

    stage('Fetching JaCoCo dump') {
        container('jnlp') {
            sh """
      mvn jacoco:dump@pull-test-data -Djacoco.address=tradeshift.qa.svc.cluster.local -Djacoco.port=36320 -Djacoco.skip=false
      """
        }
    }

    stage('Publishing Jacoco report') {
        container('jnlp') {
            sh """
      cd /home/jenkins/agent/workspace/bigcommerce/bigcommerce-integration-old
      ls -lah
      """
            jacoco(
                    execPattern: 'target/jacoco.exec',
                    classPattern: 'jar_connector-bigcommerce/BOOT-INF/classes',
                    sourcePattern: 'src/main/java',
                    exclusionPattern: '**/*Spec.class'
            )
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