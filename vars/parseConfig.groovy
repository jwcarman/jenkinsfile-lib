def call(Closure body) {
    def config = [:]
    if (body == null) {
        return config
    }
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    config
}