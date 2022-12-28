package hotel.reservation.kotlin

import java.util.Scanner

class AdminMenu {

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
                            setOf('1') -> println("displayAllCustomers()")
                            setOf('2') -> println("displayAllRooms()")
                            setOf('3') -> println("displayAllReservations()")
                            setOf('4') -> println("addRoom()")
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
}
