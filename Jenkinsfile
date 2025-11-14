pipeline {
  agent any

  options { timestamps(); buildDiscarder(logRotator(numToKeepStr: '30')) }

  stages {
    stage('Checkout') {
      steps {
        checkout([$class: 'GitSCM',
          branches: [[name: '*/main']],
          userRemoteConfigs: [[url: 'https://github.com/abhinavdagarpolestarllp/PlaformOne.git']]
        ])
      }
    }

    stage('Build & Run Sanity') {
      steps {
        // run sanity suite
        bat 'mvn -B clean test -DsuiteXmlFile=SanityTest.xml'
      }
    }

    stage('Archive Extent Reports') {
      steps {
        // archive so you can download the raw report later
        archiveArtifacts artifacts: '**target/extent-reports/', fingerprint: true, allowEmptyArchive: true
      }
    }

    stage('Publish Extent HTML') {
      steps {
        script {
          // publishHTML step requires HTML Publisher plugin
          publishHTML(target: [
            reportDir: 'target/extent-reports',
            reportFiles: 'index.html',
            reportName: 'Extent Report',
            allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: true
          ])
        }
      }
    }
  }

  post {
    always {
      junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
      // optional: keep extent results archived (already archived)
    }
  }
}
