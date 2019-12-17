def call(productConfig, flag) {

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
                      print productConfig
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
                    expression { flag }
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
                when {
                    not {
                        expression { productConfig.deploymentFrozen }
                    }
                }
                steps {
                    sh './mvnw clean package -DskipTests=true'
                }
            }

            stage('Test') {
                when {
                    not {
                        expression { productConfig.skipTests }
                    }
                }
                steps {
                    sh './mvnw clean verify'
                }
            }
            
            stage('Parameter check') {
                when {
                    expression { productConfig == null || productConfig.version == null }
                }
                steps {
                    error('Invalid build configuration provided.')
                }
            }
            
            stage('Deploy At AT') {
                when {
                    expression { productConfig.countryConfig.deployAT }
                    expression { flag }
                }
                steps {
                    print 'Deploying at AT'
                }
            }
            
            stage('Deploy At DE') {
                when {
                    expression { productConfig.countryConfig.deployDE }
                }
                steps {
                    print 'Deploying at DE'
                }
            }
            
            stage('Deploy At IN') {
                when {
                    expression { productConfig.countryConfig.deployIN }
                }
                steps {
                    print 'Deploying at IN'
                }
            }

        }
    }

}
