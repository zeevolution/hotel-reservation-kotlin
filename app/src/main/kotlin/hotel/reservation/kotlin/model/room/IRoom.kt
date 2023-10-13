package hotel.reservation.kotlin.model.room

import hotel.reservation.kotlin.model.room.enums.RoomType

interface IRoom {

    fun getRoomNumber(): String

    fun getRoomPrice(): Double

    fun getRoomType(): RoomType

    fun isFree(): Boolean
}
