node('e2e') {

    stage('checkout') {
        git credentialsId: 'basic-ssh', url: 'git@github.com:vertexinc/connector-quality-java.git'
    }
   stage('regression-test') {
     container('mvn3-jdk11') {
       try {
         sh """
           cd ConnectorQuality

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
                $class: 'GitSCM',
                branches: [[name: "dev"]],
                userRemoteConfigs: [[
                                            url: 'git@github.com:vertexinc/connector-coupa.git',
                                            credentialsId: 'basic-ssh'
                                    ]]
        ])
    }
    stage('Pull connector-coupa app docker image'){
        container('jnlp') {
            sh """
            eval \$(aws ecr get-login --no-include-email --region us-east-1)
            docker pull 497397011574.dkr.ecr.us-east-1.amazonaws.com/connector-coupa:qa
        """
        }
    }
    stage('Extracting class files'){
        container('jnlp') {
            sh """
        mkdir -p image_connector-coupa connector-coupa jar_connector-coupa
        docker save 497397011574.dkr.ecr.us-east-1.amazonaws.com/connector-coupa:qa -o image_connector-coupa.tar
        cd image_connector-coupa
        tar -xf ../image_connector-coupa.tar
        cd ../connector-coupa 
        find ../image_connector-coupa/ -type f -name \"layer.tar\" -exec tar xf {} \\;
        unzip -o connector-coupa.jar -d ../jar_connector-coupa
        cd ..
      """
        }
    }
    stage('Fetching JaCoCo dump') {
        container('jnlp') {
            sh """
      mvn jacoco:dump@pull-test-data -Djacoco.address=coupa.qa.svc.cluster.local -Djacoco.port=36320 -Djacoco.skip=false
      """
        }
    }
    stage('Publishing Jacoco report'){
        container ('jnlp'){
            sh """
      cd /home/jenkins/agent/workspace/coupa/coupa_regression
      ls -lah
      """
            jacoco(
                    execPattern:'target/jacoco.exec',
                    classPattern:'jar_connector-coupa/BOOT-INF/classes',
                    sourcePattern:'src/main/java',
                    exclusionPattern: '**/*Test.class'
            )
        }
    }
    stage ('Cleanup'){
        container ('jnlp'){
            sh "rm -rf /home/jenkins/agent/workspace/coupa/coupa_regression/*"
        }
    }
//   stage('compile coupa app') {
//     container('mvn3-jdk8') {
// 	  sh """
// 	    mvn clean compile -DskipTests
// 	  """
//     }
//   }
//         stage('Import Results') {
//       step([$class: 'XrayImportBuilder', endpointName: '/junit', importFilePath: 'ConnectorQuality/target/surefire-reports/junitreports/TEST-com.vertex.quality.connectors.coupa.tests.*xml', importToSameExecution: 'true', projectKey: 'CCOUPA', serverInstance: 'CLOUD-47847935-53f5-4e62-b39a-16a3e619ed4a', testExecKey: 'CCOUPA-1701 	'])
//   }
}