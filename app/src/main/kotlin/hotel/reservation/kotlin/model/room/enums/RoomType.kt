package hotel.reservation.kotlin.model.room.enums

enum class RoomType(
    val label: String
) {
    SINGLE("1"),
    DOUBLE("2");

    companion object {
        fun valueOfLabel(label: String): RoomType {
            for (roomType in values()) {
                if (roomType.label == label) {
                    return roomType
                }
            }
            throw IllegalArgumentException()
        }
    }
}
