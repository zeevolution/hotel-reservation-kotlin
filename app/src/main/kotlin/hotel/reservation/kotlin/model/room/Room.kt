package hotel.reservation.kotlin.model.room

import hotel.reservation.kotlin.model.room.enums.RoomType

open class Room(
    open val number: String,
    open val price: Double,
    open val enumeration: RoomType
) : IRoom {

    override fun getRoomNumber(): String {
        return number
    }

    override fun getRoomPrice(): Double {
        TODO("Not yet implemented")
    }

    override fun getRoomType(): RoomType {
        TODO("Not yet implemented")
    }

    override fun isFree() = this.price.equals(0.0)

    override fun toString(): String {
        return "Room Number: $number Price: $$price Enumeration: $enumeration"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Room) return false
        return number == other.number
    }

    override fun hashCode(): Int {
        return number.hashCode()
    }
}
