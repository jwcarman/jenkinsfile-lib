def call(Closure body) {
    config = parseConfig() {
        body()
    }

    node {
        stage('Checkout') {
            doCheckout(config)
        }

        def branchName = env.BRANCH_NAME
        echo "Building branch ${branchName}..."
        if("master".equals(branchName)) {
            stage('Build') {
                doWithSonar(config) {
                    doWithMaven(config) {
                        sh "mvn clean jacoco:prepare-agent test sonar:sonar deploy -DdeployAtEnd=true"
                    }
                }
            }
        }
        else if (branchName.startsWith("releases/")) {
            stage('Build') {
                doWithSonar(config) {
                    doWithMaven(config) {
                        sh "mvn clean jacoco:prepare-agent test sonar:sonar deploy -DdeployAtEnd=true"
                    }
                }
            }
            stage('Release') {
                doWithSshAgent(config) {
                    doWithMaven(config) {
                        doMavenRelease()
                    }
                }
            }
        }

        stage('Results') {
            junit '**/target/surefire-reports/TEST-*.xml'
            archive '**/target/*.jar'
        }
    }
}