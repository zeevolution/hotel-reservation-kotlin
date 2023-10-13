package hotel.reservation.kotlin.service.reservation

import hotel.reservation.kotlin.model.customer.Customer
import hotel.reservation.kotlin.model.reservation.Reservation
import hotel.reservation.kotlin.model.room.IRoom
import java.util.Calendar
import java.util.Date
import java.util.LinkedList

class ReservationService private constructor() {

    companion object {
        val SERVICE_INSTANCE = ReservationService()
        const val RECOMMENDED_ROOMS_DEFAULT_PLUS_DAYS = 7
    }

    private val rooms = mutableMapOf<String, IRoom>()
    private val reservations = mutableMapOf<String, MutableCollection<Reservation>>()

    fun addRoom(room: IRoom) {
        rooms[room.getRoomNumber()] = room
    }

    fun getARoom(roomNumber: String): IRoom? {
        return rooms[roomNumber]
    }

    fun getAllRooms(): Collection<IRoom> {
        return rooms.values
    }

    fun reserveARoom(customer: Customer, room: IRoom, checkInDate: Date, checkOutDate: Date): Reservation {
        val reservation = Reservation(customer, room, checkInDate, checkOutDate)

        var customerReservations = getCustomersReservation(customer)
        if (customerReservations == null) {
            customerReservations = LinkedList()
        }

        customerReservations.add(reservation)
        reservations[customer.email] = customerReservations

        return reservation
    }

    fun findRooms(checkInDate: Date, checkOutDate: Date): Collection<IRoom> {
        return findAvailableRooms(checkInDate, checkOutDate)
    }

    fun findAlternativeRooms(checkInDate: Date, checkOutDate: Date): Collection<IRoom> {
        return findAvailableRooms(addDefaultPlusDays(checkInDate), addDefaultPlusDays(checkOutDate))
    }

    private fun findAvailableRooms(checkInDate: Date, checkOutDate: Date): Collection<IRoom> {
        val allReservations = getAllReservations()
        val notAvailableRooms = LinkedList<IRoom>()

        for (reservation in allReservations) {
            if (reservationOverlaps(reservation, checkInDate, checkOutDate)) {
                notAvailableRooms.add(reservation.room)
            }
        }

        return rooms.values.filter { room -> notAvailableRooms.none { notAvailableRoom -> notAvailableRoom == room } }
    }

    fun addDefaultPlusDays(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DATE, RECOMMENDED_ROOMS_DEFAULT_PLUS_DAYS)

        return calendar.time
    }

    private fun reservationOverlaps(reservation: Reservation, checkInDate: Date, checkOutDate: Date) =
        checkInDate.before(reservation.checkOutDate) && checkOutDate.after(reservation.checkInDate)

    fun getCustomersReservation(customer: Customer): MutableCollection<Reservation>? =
        reservations[customer.email]

    fun printAllReservation() {
        val allReservations = getAllReservations()

        if (allReservations.isEmpty()) {
            println("No reservations found.")
        } else {
            for (reservation in allReservations) {
                println("$reservation\n")
            }
        }
    }

    private fun getAllReservations(): Collection<Reservation> {
        val allReservations = LinkedList<Reservation>()

        for (reservationList in reservations.values) {
            allReservations.addAll(reservationList)
        }

        return allReservations
    }
}
