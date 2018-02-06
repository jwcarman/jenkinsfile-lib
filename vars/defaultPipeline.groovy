def call(body) {
    Map config = parseConfig(body)

    node {
        mavenVersion = config.mavenVersion ?: "Maven 3.5.x"
        mavenGoals = config.mavenGoals ?: "clean install"


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
    }
}

private Map parseConfig(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    config
}
