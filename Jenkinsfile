pipeline {
    agent {
        label 'selenium'
    }
    environment {
        REGISTRY = "registry.projectweek.be"
        PROJECT_NAME = env.GIT_URL.replaceFirst(/^.*\/(?:pw2-)?([^\/]+?).git$/, '$1')
        DOCKER_IMAGE = ''
    }
    stages {
            stage('Build Docker Image'){
            agent {
                    label 'docker'
            }
            steps{
                script {
                    DOCKER_IMAGE = docker.build "${REGISTRY}/${PROJECT_NAME}:dev"
                    DOCKER_IMAGE.push()
                    sh 'sleep 30'
                }
            }
            }
        stage('Deploying test version') {
                    steps {
                        sshPublisher(
                            publishers: [
                                sshPublisherDesc(
                                    configName: 'projectweek',
                                    transfers: [
                                        sshTransfer(
                                            cleanRemote: false,
                                            excludes: '',
                                            execCommand: "echo 'PROJECT_NAME=${PROJECT_NAME}' > .${PROJECT_NAME}${BUILD_NUMBER}/.env && cd .${PROJECT_NAME}${BUILD_NUMBER} && cp ../templatedev/docker-compose.yml . && docker-compose config | docker stack deploy --compose-file - ${PROJECT_NAME} && cd ../ && rm -rf .${PROJECT_NAME}${BUILD_NUMBER}",
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

                junit 'target/surefire-reports/*.xml'

        }
    }
}
