def call(Map config, Closure body) {
    sshCredentials = config.sshCredentials ?: "8e1d97c5-7949-489a-b28c-54b17e7006e8"

    sshagent([sshCredentials]) {
        body()
    }
}
