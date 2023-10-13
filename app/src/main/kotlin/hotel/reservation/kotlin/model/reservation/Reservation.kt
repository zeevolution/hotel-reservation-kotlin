package hotel.reservation.kotlin.model.reservation

import hotel.reservation.kotlin.model.customer.Customer
import hotel.reservation.kotlin.model.room.IRoom
import java.util.Date

data class Reservation(
    val customer: Customer,
    val room: IRoom,
    val checkInDate: Date,
    val checkOutDate: Date
) {
    override fun toString(): String {
        return "Customer: $customer" +
            "\nRoom: $room" +
            "\nCheckIn Date: $checkInDate" +
            "\nCheckOut Date: $checkOutDate"
    }
}
