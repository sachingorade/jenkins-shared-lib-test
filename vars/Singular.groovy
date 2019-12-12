def call(serviceName) {

    pipeline {
        agent any
        stages {
            stage('Boot') {
                  steps {
                      script {
                            def allJob = env.JOB_NAME.tokenize('/') as String[];
                            env.CURRENT_PROJECT_NAME = allJob[0];
                      }
                      print env.CURRENT_PROJECT_NAME
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
