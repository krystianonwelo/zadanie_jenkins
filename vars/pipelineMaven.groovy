
def call(Map config = [:]) {
    defaultMap = [moduleName: 'działa', environment: 'bar', repoName: 'baz']
    config = defaultMap << config

    node {
        stage('Downloading Source Code') {
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], userRemoteConfigs: [[ url: 'https://github.com/krystianonwelo/spring-petclinic.git']]])
            }
        }
        stage('Building Source Code') {
            steps {
                sh 'mvn -B -DskipTests clean package'
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
