def call(Map config) {
    gitUserName = config.gitUserName ?: "Jenkins"
    gitUserEmail = config.gitUserEmail ?: "jenkins@caramnconsulting.com"
    sh "git config --local user.name  ${gitUserName}"
    sh "git config --local user.email ${gitUserEmail}"
    doCheckout scm
}