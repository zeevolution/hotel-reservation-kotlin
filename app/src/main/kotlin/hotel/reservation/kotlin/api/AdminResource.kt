package hotel.reservation.kotlin.api

import hotel.reservation.kotlin.model.customer.Customer
import hotel.reservation.kotlin.model.room.IRoom
import hotel.reservation.kotlin.service.customer.CustomerService
import hotel.reservation.kotlin.service.reservation.ReservationService

class AdminResource private constructor() {

    companion object {
        val RESOURCE_INSTANCE = AdminResource()
    }

    private val customerService = CustomerService.SERVICE_INSTANCE
    private val reservationService = ReservationService.SERVICE_INSTANCE

    fun getCustomer(email: String): Customer? {
        return customerService.getCustomer(email)
    }

    fun addRoom(rooms: List<IRoom>) {
        rooms.forEach { reservationService.addRoom(it) }
    }

    fun getAllRooms(): Collection<IRoom> {
        return reservationService.getAllRooms()
    }

    fun getAllCustomers(): Collection<Customer> {
        return customerService.getAllCustomers()
    }

    fun displayAllReservations() {
        reservationService.printAllReservation()
    }
}
