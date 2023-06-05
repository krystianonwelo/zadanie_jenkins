env = [MODULE_NAME: 'działa', ENVIRONMENT: 'bar', REPO_NAME: 'baz']

def call(Map config = [:]) {
    defaultMap = [moduleName: env.MODULE_NAME, environment: env.ENVIRONMENT, repoName: env.REPO_NAME]
    config = defaultMap << config
    
    stage('demo') {
        echo '${config.MODULE_NAME}'
    }
}

