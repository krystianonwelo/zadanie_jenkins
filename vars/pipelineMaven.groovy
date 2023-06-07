
def call(Map config = [:]) {
    defaultMap = [moduleName: 'działa', environment: 'bar', repoName: 'baz']
    config = defaultMap << config

    node {
        stage('Cleanup Workspace') {

            cleanWs()
            sh 'echo "Cleaned Up Workspace For Project"'
            
        }

        stage("Tools initialization") {

            sh "mvn --version"
            sh "java -version"
 
        }

        stage('Code Checkout') {
                checkout scm
        }
        stage('Test') {
                sh 'mvn verify'

                junit 'target/surefire-reports/*.xml'

        }
        
        stage('Installing Artifacts') {
            sh 'mvn install -DskipTests'
        }
    }
}
