def call(Map config) {
    gitUserName = config.gitUserName ?: "Jenkins"
    gitUserEmail = config.gitUserEmail ?: "jenkins@caramnconsulting.com"
    sh "git config user.name  ${gitUserName}"
    sh "git config user.email ${gitUserEmail}"
    doCheckout scm
}