def call(serviceName) {

    pipeline {
        agent any
        environment {
            SERVICE_NAME = env.JOB_BASE_NAME;
        }
        stages {
            stage('$SERVICE_NAME Checkout github') {
                steps {
                    checkout scm
                }
            }

            stage('$SERVICE_NAME Build') {
                steps {
                    sh './mvnw clean package -DskipTests=true'
                }
            }

            stage('$SERVICE_NAME Test') {
                steps {
                    sh './mvnw clean verify'
                }
            }

        }
    }

}
