def call(Map config, Closure body) {

    sonar = config.sonar ?: "Local Sonar"
    withSonarQubeEnv(sonar) {
        body()
    }
}