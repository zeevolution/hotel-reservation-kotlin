package hotel.reservation.kotlin

import java.util.Scanner

class AdminMenu {

    private val userInputSize = 1

    fun adminMenu() {
        var userInput: String
        val scanner = Scanner(System.`in`)

        printMenu()

        try {
            do {
                userInput = scanner.nextLine()

                when (userInput.length) {
                    userInputSize -> {
                        when (setOf(userInput[0])) {
                            setOf('1') -> println("displayAllCustomers()")
                            setOf('2') -> println("displayAllRooms()")
                            setOf('3') -> println("displayAllReservations()")
                            setOf('4') -> println("addRoom()")
                            setOf('5') -> println("MainMenu.printMainMenu()")
                        }
                    }
                    else -> throw StringIndexOutOfBoundsException()
                }
            } while (userInput[0] != '5' || userInput.length != 1)
        } catch (exception: StringIndexOutOfBoundsException) {
            println("Invalid input received. Exiting program...")
        }
    }

    private fun printMenu() = print(
        "\nAdmin Menu\n" +
        "--------------------------------------------\n" +
        "1. See all Customers\n" +
        "2. See all Rooms\n" +
        "3. See all Reservations\n" +
        "4. Add a Room\n" +
        "5. Back to Main Menu\n" +
        "--------------------------------------------\n" +
        "Please select a number for the menu option:\n"
    )
}
