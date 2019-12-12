def call(serviceName) {

    pipeline {
        agent any
        stages {
            stage(env.JOB_BASE_NAME + ' Checkout github') {
                steps {
                    checkout scm
                }
            }

            stage(env.JOB_BASE_NAME + ' Build') {
                steps {
                    sh './mvnw clean package -DskipTests=true'
                }
            }

            stage(env.JOB_BASE_NAME + ' Test') {
                steps {
                    sh './mvnw clean verify'
                }
            }

        }
    }

}
