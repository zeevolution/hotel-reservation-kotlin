package hotel.reservation.kotlin.model.customer

data class Customer(
    val firstName: String,
    val lastName: String,
    val email: String
) {
    companion object {
        const val EMAIL_REGEX_PATTERN = "^(.+)@(.+).(.+)$"
    }

    init {
        require(this.isValidEmail(email)) { "Invalid email" }
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern = Regex(EMAIL_REGEX_PATTERN)

        return pattern.matches(email)
    }

    override fun toString(): String {
        return "First Name: $firstName Last Name: $lastName Email: $email"
    }
}
