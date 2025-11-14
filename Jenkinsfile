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
        bat 'mvn -B clean test -DsuiteXmlFile=SanityTest.xml'
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
          archiveArtifacts artifacts: '**/target/*.jar, **/target/*.zip', allowEmptyArchive: true
        }
      }
    }

    stage('Publish Allure (optional)') {
      when { expression { fileExists('target/allure-results') } }
      steps {
        allure includeProperties: false, results: [[path: 'target/allure-results']]
      }
    }
  }

  post {
    success { echo 'Sanity suite passed' }
    failure { echo 'Sanity suite failed' }
  }
}