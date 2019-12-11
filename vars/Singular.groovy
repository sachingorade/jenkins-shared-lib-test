def call(serviceName) {

    pipeline {
        agent any
        stages {
            stage('Checkout github') {
                steps {
                    checkout scm
                }
            }

            stage('Build') {
                steps {
                    sh 'mvn clean package -DskipTests=true'
                }
            }

            stage('Test') {
                steps {
                    sh 'mvn clean verify'
                }
            }

        }
    }

}