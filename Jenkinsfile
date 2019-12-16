pipeline {
    agent {
        label 'maven'
    }
    environment {
        REGISTRY = "registry.projectweek.be"
        PROJECT_NAME = env.GIT_URL.replaceFirst(/^.*\/(?:pw2-)?([^\/]+?).git$/, '$1')
        DOCKER_IMAGE = ''
    }
    stages {
        
        stage('Build') {
        parallel {
            stage('Build') {
            steps {
               sh 'mvn -B -DskipTests clean package'
            }
            }
            stage('Build Docker Image'){
            agent {
                    label 'docker'
            }
            steps{
                script {
                    DOCKER_IMAGE = docker.build "${REGISTRY}/${PROJECT_NAME}:dev"
                    DOCKER_IMAGE.push()
                }
            }
            }
        }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Publish over SSH') {
            steps {
                sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: 'projectweek',
                            transfers: [
                                sshTransfer(
                                    cleanRemote: false,
                                    excludes: '',
                                    execCommand: "echo 'PROJECT_NAME=${PROJECT_NAME}' > .${PROJECT_NAME}${BUILD_NUMBER}/.env && cd .${PROJECT_NAME}${BUILD_NUMBER} && cp ../template/docker-compose.yml . && docker-compose config | docker stack deploy --compose-file - ${PROJECT_NAME} && cd ../ && rm -rf .${PROJECT_NAME}${BUILD_NUMBER}",
                                    execTimeout: 120000,
                                    flatten: false,
                                    makeEmptyDirs: false,
                                    noDefaultExcludes: false,
                                    patternSeparator: '[, ]+',
                                    remoteDirectory: ".${PROJECT_NAME}${BUILD_NUMBER}",
                                    remoteDirectorySDF: false,
                                    removePrefix: '',
                                    sourceFiles: '**'
                                )
                            ],
                            usePromotionTimestamp: false,
                            useWorkspaceInPromotion: false,
                            verbose: false
                        )
                    ]
                )
            }
        }
    }
    post {
        always {

                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
                junit 'target/surefire-reports/*.xml'

        }
    }
}
