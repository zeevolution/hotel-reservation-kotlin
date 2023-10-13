package hotel.reservation.kotlin

import hotel.reservation.kotlin.api.HotelResource
import hotel.reservation.kotlin.model.reservation.Reservation
import hotel.reservation.kotlin.model.room.IRoom
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Scanner

class MainMenu {

    companion object {
        private const val DEFAULT_DATE_FORMAT = "MM/dd/yyyy"
        private val hotelResource = HotelResource.RESOURCE_INSTANCE
    }

    fun menu() {
        var userInput: String
        val userInputSize = 1
        val inputScanner = Scanner(System.`in`)

        printMenu()

        try {
            do {
                userInput = inputScanner.nextLine()
                if (userInput.length == userInputSize) {
                    when {
                        userInput[0] == '1' -> findAndReserveRoom()
                        userInput[0] == '2' -> seeMyReservation()
                        userInput[0] == '3' -> createAccount()
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
                } else {
                    throw StringIndexOutOfBoundsException()
                }
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

    private fun findAndReserveRoom() {
        val scanner = Scanner(System.`in`)

        println("Enter Check-In Date mm/dd/yyyy example 02/01/2020")
        val checkIn = getInputDate(scanner)

        println("Enter Check-Out Date mm/dd/yyyy example 02/21/2020")
        val checkOut = getInputDate(scanner)

        if (checkIn != null && checkOut != null) {
            val availableRooms = hotelResource.findARoom(checkIn, checkOut)

            if (availableRooms.isEmpty()) {
                val alternativeRooms = hotelResource.findAlternativeRooms(checkIn, checkOut)

                if (alternativeRooms.isEmpty()) {
                    println("No rooms found.")
                } else {
                    val alternativeCheckIn = hotelResource.addDefaultPlusDays(checkIn)
                    val alternativeCheckOut = hotelResource.addDefaultPlusDays(checkOut)
                    println(
                        """
                        We've only found rooms on alternative dates:
                        Check-In Date: $alternativeCheckIn
                        Check-Out Date: $alternativeCheckOut
                        """.trimIndent()
                    )

                    printRooms(alternativeRooms)
                    reserveRoom(scanner, alternativeCheckIn, alternativeCheckOut, alternativeRooms)
                }
            } else {
                printRooms(availableRooms)
                reserveRoom(scanner, checkIn, checkOut, availableRooms)
            }
        }
    }

    private fun getInputDate(scanner: Scanner): Date? {
        return try {
            SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(scanner.nextLine())
        } catch (ex: ParseException) {
            println("Error: Invalid date.")
            findAndReserveRoom()
            null
        }
    }

    private fun reserveRoom(scanner: Scanner, checkInDate: Date, checkOutDate: Date, rooms: Collection<IRoom>) {
        println("Would you like to book? y/n")
        val bookRoom = scanner.nextLine()

        when {
            "y" == bookRoom -> {
                println("Do you have an account with us? y/n")
                val haveAccount = scanner.nextLine()

                if ("y" == haveAccount) {
                    println("Enter Email format: name@domain.com")
                    val customerEmail = scanner.nextLine()

                    if (hotelResource.getCustomer(customerEmail) == null) {
                        println("Customer not found.\nYou may need to create a new account.")
                    } else {
                        println("What room number would you like to reserve?")
                        val roomNumber = scanner.nextLine()

                        if (rooms.any { room -> room.getRoomNumber() == roomNumber }) {
                            val room = hotelResource.getRoom(roomNumber)
                            val reservation = hotelResource.bookARoom(customerEmail, room!!, checkInDate, checkOutDate)
                            println("Reservation created successfully!")
                            println(reservation)
                        } else {
                            println("Error: room number not available.\nStart reservation again.")
                        }
                    }
                    printMainMenu()
                } else {
                    println("Please, create an account.")
                    printMainMenu()
                }
            }
            "n" == bookRoom -> printMainMenu()
            else -> reserveRoom(scanner, checkInDate, checkOutDate, rooms)
        }
    }

    private fun printRooms(rooms: Collection<IRoom>) {
        if (rooms.isEmpty()) {
            println("No rooms found.")
        } else {
            rooms.forEach { println(it) }
        }
    }

    private fun seeMyReservation() {
        val scanner = Scanner(System.`in`)

        println("Enter your Email format: name@domain.com")
        val customerEmail = scanner.nextLine()

        printReservations(hotelResource.getCustomersReservations(customerEmail))
    }

    private fun printReservations(reservations: Collection<Reservation>?) {
        if (reservations.isNullOrEmpty()) {
            println("No reservations found.")
        } else {
            reservations.forEach { println("\n$it") }
        }
    }

    private fun createAccount() {
        val scanner = Scanner(System.`in`)

        println("Enter Email format: name@domain.com")
        val email = scanner.nextLine()

        println("First Name:")
        val firstName = scanner.nextLine()

        println("Last Name:")
        val lastName = scanner.nextLine()

        try {
            hotelResource.createACustomer(email, firstName, lastName)
            println("Account created successfully!")

            printMainMenu()
        } catch (ex: IllegalArgumentException) {
            println(ex.localizedMessage)
            createAccount()
        }
    }

    private fun printMainMenu() {
        println(
            """
            Welcome to the Hotel Reservation Application
            --------------------------------------------
            1. Find and reserve a room
            2. See my reservations
            3. Create an Account
            4. Admin
            5. Exit
            --------------------------------------------
            Please select a number for the menu option:
            """.trimIndent()
        )
    }
}
