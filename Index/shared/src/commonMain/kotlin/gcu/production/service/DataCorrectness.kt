package gcu.production.service


object DataCorrectness {

    infix fun checkCorrectPassword(data: String?) =
        data?.trim()?.run {
            this.length >= 6
        } ?: false

    infix fun checkCorrectLogin(data: String?) =
        data?.trim()?.run {
            this.length >= 8 && contains("@")
        } ?: false

    infix fun checkCorrectOrg(data: String?) =
        data?.trim()?.run {
            this.length >= 2
        } ?: false

    infix fun checkCorrectName(data: String?) =
        data?.run {
            length >= 6
        } ?: false

    infix fun checkCorrectPhone(data: String?) =
        data?.run {
            length == 10 || length == 11
        } ?: false
}