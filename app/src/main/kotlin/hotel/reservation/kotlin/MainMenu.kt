package hotel.reservation.kotlin

import java.util.Scanner

class MainMenu {

    private val userInputSize = 1

    fun menu() {
        var userInput: String
        val inputScanner = Scanner(System.`in`)

        printMenu()

        try {
            do {
                userInput = inputScanner.nextLine()
                if (userInput.length == userInputSize) {
                    when {
                        userInput[0] == '1' -> println("findAndReserveRoom()")
                        userInput[0] == '2' -> println("seeMyReservation()")
                        userInput[0] == '3' -> println("createAccount()")
                        userInput[0] == '4' -> {
                            AdminMenu().menu()
                            break
                        }
                        userInput[0] == '5' -> {
                            print("Exiting program...")
                            break
                        }
                        else -> println("Unknown action")
                    }
                } else throw StringIndexOutOfBoundsException()
            } while (userInput[0] != '5')
        } catch (exception: StringIndexOutOfBoundsException) {
            println("Invalid input received. Exiting program...")
        }
    }

    private fun printMenu() = println(
        "\nWelcome to the Hotel Reservation Application\n" +
        "--------------------------------------------\n" +
        "1. Find and reserve a room\n" +
        "2. See my reservations\n" +
        "3. Create an Account\n" +
        "4. Admin\n" +
        "5. Exit\n" +
        "--------------------------------------------\n" +
        "Please select a number for the menu option:"
    )
}
