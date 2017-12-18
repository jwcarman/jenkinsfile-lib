def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    node {
        mavenVersion = config.mavenVersion ?: "Maven 3.5.x"
        mavenGoals = config.mavenGoals ?: "clean install"

        cfCredentials = config.cfCredentials ?: "pcf"
        cfManifest = config.cfManifest ?: "target/classes/manifest.yml"
        cfOrg = config.cfOrg ?: "microbule"
        cfSpace = config.cfSpace ?: "development"
        cfTarget = config.cfTarget ?: "https://api.run.pivotal.io"

        mvnHome = tool mavenVersion

        stage('Checkout') {
            git "git@github.com:jwcarman/${config.repo}.git"
        }
        stage('Maven Build') {
            sh "'${mvnHome}/bin/mvn' ${mavenGoals}"
        }
        stage('Results') {
            junit '**/target/surefire-reports/TEST-*.xml'
            archive 'target/*.jar'
        }
        stage('PWS') {
            pushToCloudFoundry cloudSpace: cfSpace, credentialsId: cfCredentials, manifestChoice: [manifestFile: cfManifest], organization: cfOrg, target: cfTarget
        }
    }
}
