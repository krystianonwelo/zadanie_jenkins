
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
        stage("Tools initialization") {
            steps {
                sh "mvn --version"
                sh "java -version"
            }
        }
        stage('Code Checkout') {
            steps {
                checkout scm
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
