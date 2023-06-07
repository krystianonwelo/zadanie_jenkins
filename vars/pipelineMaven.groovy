
def call(Map config = [:]) {
    defaultMap = [moduleName: 'działa', environment: 'bar', repoName: 'baz']
    config = defaultMap << config

    node {
        stage('Cleanup Workspace') {
            step {
                cleanWs()
                sh 'echo "Cleaned Up Workspace For Project"'
            }
        }
        stage("Tools initialization") {
            step {
                sh "mvn --version"
                sh "java -version"
            }
        }
        stage('Code Checkout') {
            step {
                checkout scm
            }
        }
        stage('Test') {
            step {
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
