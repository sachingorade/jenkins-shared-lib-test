def call(serviceName) {

    pipeline {
        agent any
        environment {
            CI_CD_BRANCH = "develop"
        }
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
            
            stage('CI CD Branch check') {
                when{
                    branch env.CI_CD_BRANCH
                }
                steps {
                    print 'Performing task in CI_CD_BRANCH'
                    print env.CI_CD_BRANCH
                }
            }
            
            stage('Non CI CD Branch check') {
                when {
                    not {
                        branch env.CI_CD_BRANCH
                    }
                }
                steps {
                    print 'Performing task in NON CI_CD_BRANCH'
                    print env.CI_CD_BRANCH
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
