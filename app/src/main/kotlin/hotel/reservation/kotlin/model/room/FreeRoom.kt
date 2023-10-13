package hotel.reservation.kotlin.model.room

import hotel.reservation.kotlin.model.room.enums.RoomType

class FreeRoom(
    override val number: String,
    override val enumeration: RoomType
) : Room(number, 0.0, enumeration) {

    override fun toString(): String {
        return "FreeRoom => ${super.toString()}"
    }
}
