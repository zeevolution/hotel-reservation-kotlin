package hotel.reservation.kotlin.api

import hotel.reservation.kotlin.model.customer.Customer
import hotel.reservation.kotlin.model.reservation.Reservation
import hotel.reservation.kotlin.model.room.IRoom
import hotel.reservation.kotlin.service.customer.CustomerService
import hotel.reservation.kotlin.service.reservation.ReservationService
import java.util.Date

class HotelResource private constructor() {

    companion object {
        val RESOURCE_INSTANCE = HotelResource()
    }

    private val customerService = CustomerService.SERVICE_INSTANCE
    private val reservationService = ReservationService.SERVICE_INSTANCE

    fun getCustomer(email: String): Customer? {
        return customerService.getCustomer(email)
    }

    fun createACustomer(email: String, firstName: String, lastName: String) {
        customerService.addCustomer(email, firstName, lastName)
    }

    fun getRoom(roomNumber: String): IRoom? {
        return reservationService.getARoom(roomNumber)
    }

    fun bookARoom(customerEmail: String, room: IRoom, checkInDate: Date, checkOutDate: Date): Reservation {
        return reservationService.reserveARoom(getCustomer(customerEmail)!!, room, checkInDate, checkOutDate)
    }

    fun getCustomersReservations(customerEmail: String): Collection<Reservation> {
        val customer = getCustomer(customerEmail) ?: return emptyList()
        return reservationService.getCustomersReservation(customer)!!
    }

    fun findARoom(checkIn: Date, checkOut: Date): Collection<IRoom> {
        return reservationService.findRooms(checkIn, checkOut)
    }

    fun findAlternativeRooms(checkIn: Date, checkOut: Date): Collection<IRoom> {
        return reservationService.findAlternativeRooms(checkIn, checkOut)
    }

    fun addDefaultPlusDays(date: Date): Date {
        return reservationService.addDefaultPlusDays(date)
    }
}
