def call() {
    def pom = readMavenPom file: 'pom.xml'
    def currentVersion = pom.version
    def releaseVersion = currentVersion.replace("-SNAPSHOT", "")
    def lastDotIndex = releaseVersion.lastIndexOf('.')
    def releaseRoot = releaseVersion.substring(0, lastDotIndex)
    def lastDigit = Integer.parseInt(releaseVersion.substring(lastDotIndex + 1))
    def devVersion = releaseRoot + "." + (lastDigit + 1) + "-SNAPSHOT"
    echo "Releasing ${releaseVersion} with development version ${devVersion}..."
    sh "mvn release:clean release:prepare release:perform -DdeployAtEnd=true -Dresume=false -DautoVersionSubmodules=true -DreleaseVersion=${releaseVersion} -DdevelopmentVersion=${devVersion}"
}