
def call(Map config = [:]) {
    defaultMap = [moduleName: 'działa', environment: 'bar', repoName: 'baz']
    config = defaultMap << config

    node {
        stage('Cleanup Workspace') {
            steps {
                cleanWs()
                sh 'echo "Cleaned Up Workspace For Project"'
            }
        }

        stage('Code Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: 'origin/main']],
                    userRemoteConfigs: [[url: 'https://github.com/spring-projects/spring-petclinic.git/']]
                ])
            }
        }
        stage('Test') {
            steps {
                sh 'mvn verify'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Installing Artifacts') {
            sh 'mvn install -DskipTests'
        }
    }
}
