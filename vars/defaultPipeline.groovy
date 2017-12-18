def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    node {
        def mvnHome
        stage('Preparation') {
            git "git@github.com:jwcarman/${config.repo}.git"
            mvnHome = tool 'Maven 3.5.x'
        }
        stage('Build') {
            sh "'${mvnHome}/bin/mvn' clean install""
        }
        stage('Results') {
            junit '**/target/surefire-reports/TEST-*.xml'
            archive 'target/*.jar'
        }
        stage('PWS') {
            pushToCloudFoundry cloudSpace: 'development', credentialsId: 'pcf', manifestChoice: [manifestFile: 'target/classes/manifest.yml'], organization: 'microbule', target: 'https://api.run.pivotal.io'
        }
    }
}
