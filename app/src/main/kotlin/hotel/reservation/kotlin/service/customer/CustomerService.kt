package hotel.reservation.kotlin.service.customer

import hotel.reservation.kotlin.model.customer.Customer

class CustomerService private constructor() {

    companion object {
        val SERVICE_INSTANCE = CustomerService()
    }

    private val customers = mutableMapOf<String, Customer>()

    fun addCustomer(email: String, firstName: String, lastName: String) {
        customers[email] = Customer(firstName, lastName, email)
    }

    fun getCustomer(customerEmail: String): Customer? {
        return customers[customerEmail]
    }

    fun getAllCustomers(): Collection<Customer> {
        return customers.values
    }
}
