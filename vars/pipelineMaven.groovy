
def call(Map config = [skipTests: 1, skipInstallingArtifacts: 1]) {
    node {
        stage('Cleanup Workspace') {
            cleanWs()
            sh 'echo "Cleaned Up Workspace For Project"'
        }
        stage('Tools initialization') {
            sh 'mvn --version'
            sh 'java -version'
        }

        stage('Code Checkout') {
                checkout scm
        }
        stage('Build') {
            sh 'mvn package -DskipTests'
        }
        stage('Test') {
            if (config.skipTests == 0) {
                sh 'mvn verify'
                junit   'target/surefire-reports/*.xml'
            } else {
                echo 'Skipped Testing'
            }
        }

        stage('Installing Artifacts') {
            if (config.skipInstallingArtifacts == 0) {
                sh 'mvn install -DskipTests'
            } else {
                echo 'Skipped Installing Artifacts'
            }
        }
    }
}
