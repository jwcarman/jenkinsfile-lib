def call(Map config, Closure body) {

    jdkTool = config.jdk ?: "JDK8"
    mavenTool = config.maven ?: "Maven 3.5.x"
    mavenSettings = config.mavenSettings ?: "doWithMaven-settings"

    withMaven(jdk: jdkTool, maven: mavenTool, mavenSettingsConfig: mavenSettings) {
        body()
    }
}