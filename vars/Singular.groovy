def call(serviceName) {

    pipeline {
        agent any
        stages {
            stage('Boot) {
                  steps {
                      print env.JOB_BASE_NAME
                  }
            }
            stage('Checkout github') {
                steps {
                    checkout scm
                }
            }

            stage('Build') {
                steps {
                    sh './mvnw clean package -DskipTests=true'
                }
            }

            stage('Test') {
                steps {
                    sh './mvnw clean verify'
                }
            }

        }
    }

}
