
def call(Map config = [:]) {
    defaultMap = [moduleName: 'działa', environment: 'bar', repoName: 'baz']
    config = defaultMap << config
    
    stage('demo') {
        echo "${config.moduleName}"
    }
}

