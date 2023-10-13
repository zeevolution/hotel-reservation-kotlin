package hotel.reservation.kotlin

import hotel.reservation.kotlin.api.AdminResource
import hotel.reservation.kotlin.model.room.Room
import hotel.reservation.kotlin.model.room.enums.RoomType
import java.util.Scanner

class AdminMenu {

    companion object {
        private val adminResource = AdminResource.RESOURCE_INSTANCE
    }

    private val userInputSize = 1

    fun menu() {
        var userInput: String
        val inputScanner = Scanner(System.`in`)

        printMenu()

        try {
            do {
                userInput = inputScanner.nextLine()

                when (userInput.length) {
                    userInputSize -> {
                        when (setOf(userInput[0])) {
                            setOf('1') -> displayAllCustomers()
                            setOf('2') -> displayAllRooms()
                            setOf('3') -> displayAllReservations()
                            setOf('4') -> addRoom()
                            setOf('5') -> MainMenu().menu()
                        }
                    }
                    else -> throw StringIndexOutOfBoundsException()
                }
            } while (userInput[0] != '5')
        } catch (exception: StringIndexOutOfBoundsException) {
            println("Invalid input received. Exiting program...")
        }
    }

    private fun printMenu() = println(
        "\nAdmin Menu\n" +
            "--------------------------------------------\n" +
            "1. See all Customers\n" +
            "2. See all Rooms\n" +
            "3. See all Reservations\n" +
            "4. Add a Room\n" +
            "5. Back to Main Menu\n" +
            "--------------------------------------------\n" +
            "Please select a number for the menu option:"
    )

    private fun addRoom() {
        val scanner = Scanner(System.`in`)

        println("Enter room number:")
        val roomNumber = scanner.nextLine()

        println("Enter price per night:")
        val roomPrice = enterRoomPrice(scanner)

        println("Enter room type: 1 for single bed, 2 for double bed:")
        val roomType = enterRoomType(scanner)

        val room = Room(roomNumber, roomPrice, roomType)

        adminResource.addRoom(listOf(room))
        println("Room added successfully!")

        println("Would like to add another room? Y/N")
        addAnotherRoom()
    }

    private fun enterRoomPrice(scanner: Scanner): Double {
        return try {
            scanner.nextLine().toDouble()
        } catch (exp: NumberFormatException) {
            println(
                "Invalid room price! Please, enter a valid double number. " +
                    "Decimals should be separated by point (.)"
            )
            enterRoomPrice(scanner)
        }
    }

    private fun enterRoomType(scanner: Scanner): RoomType {
        return try {
            RoomType.valueOfLabel(scanner.nextLine())
        } catch (exp: IllegalArgumentException) {
            println("Invalid room type! Please, choose 1 for single bed or 2 for double bed:")
            enterRoomType(scanner)
        }
    }

    private fun addAnotherRoom() {
        val scanner = Scanner(System.`in`)

        try {
            var anotherRoom = scanner.nextLine()

            while ((anotherRoom[0] != 'Y' && anotherRoom[0] != 'N') ||
                anotherRoom.length != 1
            ) {
                println("Please enter Y (Yes) or N (No)")
                anotherRoom = scanner.nextLine()
            }

            when (anotherRoom[0]) {
                'Y' -> addRoom()
                'N' -> printMenu()
                else -> addAnotherRoom()
            }
        } catch (ex: StringIndexOutOfBoundsException) {
            addAnotherRoom()
        }
    }

    private fun displayAllRooms() {
        val rooms = adminResource.getAllRooms()

        if (rooms.isEmpty()) {
            println("No rooms found.")
        } else {
            rooms.forEach { println(it) }
        }
    }

    private fun displayAllCustomers() {
        val customers = adminResource.getAllCustomers()

        if (customers.isEmpty()) {
            println("No customers found.")
        } else {
            customers.forEach { println(it) }
        }
    }

    private fun displayAllReservations() {
        adminResource.displayAllReservations()
    }
}
