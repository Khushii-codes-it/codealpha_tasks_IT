import java.util.*;
import java.io.*;

public class HotelReservationSystem {

    static final String FILE_NAME = "bookings.dat";

    static ArrayList<Booking> bookings = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    static int nextBookingId = 1001;

    // Booking class
    static class Booking implements Serializable {

        private static final long serialVersionUID = 1L;

        int bookingId;
        String guestName;
        String roomType;
        int nights;
        double amount;
        boolean paid;
        boolean active;

        Booking(int id, String name, String type,
                int nights, double amount) {

            this.bookingId = id;
            this.guestName = name;
            this.roomType = type;
            this.nights = nights;
            this.amount = amount;
            this.paid = true;
            this.active = true;
        }
    }

    // Room information
    static String getRoomType(int choice) {

        switch (choice) {
            case 1: return "Standard";
            case 2: return "Deluxe";
            case 3: return "Suite";
            default: return "";
        }
    }

    static int getRoomPrice(int choice) {

        switch (choice) {
            case 1: return 2000;
            case 2: return 3500;
            case 3: return 5000;
            default: return 0;
        }
    }

    static int getTotalRooms(int choice) {

        switch (choice) {
            case 1: return 5;
            case 2: return 3;
            case 3: return 2;
            default: return 0;
        }
    }

    // Calculate available rooms
    static int getAvailableRooms(int choice) {

        String type = getRoomType(choice);
        int booked = 0;

        for (Booking b : bookings) {
            if (b.active && b.roomType.equals(type)) {
                booked++;
            }
        }

        return getTotalRooms(choice) - booked;
    }

    // Search rooms
    static void searchRooms() {

        System.out.println("\n AVAILABLE ROOMS");

        for (int i = 1; i <= 3; i++) {

            System.out.println(
                i + ". " + getRoomType(i) +
                " | Price: Rs." + getRoomPrice(i) +
                " per night | Available: " +
                getAvailableRooms(i)
            );
        }
    }

    // Save booking information
    static void saveBookings() {

        try {
            ObjectOutputStream out =
                new ObjectOutputStream(
                    new FileOutputStream(FILE_NAME));

            out.writeObject(bookings);
            out.close();

        } catch (IOException e) {
            System.out.println("Error saving booking data!");
        }
    }

    // Load booking information
    @SuppressWarnings("unchecked")
    static void loadBookings() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try {
            ObjectInputStream in =
                new ObjectInputStream(
                    new FileInputStream(FILE_NAME));

            bookings = (ArrayList<Booking>) in.readObject();
            in.close();

            for (Booking b : bookings) {
                if (b.bookingId >= nextBookingId) {
                    nextBookingId = b.bookingId + 1;
                }
            }

            System.out.println("Previous bookings loaded.");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load previous bookings.");
        }
    }

    // Read integer input
    static int readInt(String message) {

        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(sc.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Book a room
    static void bookRoom() {

        searchRooms();

        int choice = readInt("\nSelect room type (1-3): ");

        if (choice < 1 || choice > 3) {
            System.out.println("Invalid room selection!");
            return;
        }

        if (getAvailableRooms(choice) <= 0) {
            System.out.println("Sorry! No rooms available.");
            return;
        }

        System.out.print("Enter guest name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Guest name cannot be empty.");
            return;
        }

        int nights = readInt("Enter number of nights: ");

        if (nights <= 0) {
            System.out.println("Invalid number of nights!");
            return;
        }

        double amount = (double) getRoomPrice(choice) * nights;

        System.out.println("\n===== BOOKING DETAILS =====");
        System.out.println("Guest Name: " + name);
        System.out.println("Room Type: " + getRoomType(choice));
        System.out.println("Number of Nights: " + nights);
        System.out.println("Total Amount: Rs." + amount);

        // Payment simulation
        System.out.print("\nConfirm simulated payment? (yes/no): ");
        String payment = sc.nextLine().trim();

        if (!payment.equalsIgnoreCase("yes")) {
            System.out.println("Booking cancelled. Payment not completed.");
            return;
        }

        Booking b = new Booking(
            nextBookingId++,
            name,
            getRoomType(choice),
            nights,
            amount
        );

        bookings.add(b);
        saveBookings();

        System.out.println("\nPayment successful!");
        System.out.println("Room booked successfully!");
        System.out.println("Booking ID: " + b.bookingId);
    }

    // Display booking information
    static void viewBookings() {

        System.out.println("\n===== BOOKING DETAILS =====");

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking b : bookings) {

            System.out.println("\nBooking ID: " + b.bookingId);
            System.out.println("Guest Name: " + b.guestName);
            System.out.println("Room Type: " + b.roomType);
            System.out.println("Nights: " + b.nights);
            System.out.println("Amount: Rs." + b.amount);
            System.out.println("Payment: " +
                (b.paid ? "Paid" : "Not Paid"));
            System.out.println("Status: " +
                (b.active ? "Active" : "Cancelled"));
        }
    }

    // Cancel reservation
    static void cancelBooking() {

        int id = readInt("\nEnter booking ID to cancel: ");

        for (Booking b : bookings) {

            if (b.bookingId == id && b.active) {

                b.active = false;
                saveBookings();

                System.out.println("Booking cancelled successfully!");
                return;
            }
        }

        System.out.println("Active booking not found!");
    }

    // Main menu
    public static void main(String[] args) {

        loadBookings();

        int choice;

        do {

            System.out.println("\n================================");
            System.out.println("    HOTEL RESERVATION SYSTEM");
            System.out.println("================================");

            System.out.println("1. Search Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. View Booking Details");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. Exit");

            choice = readInt("\nEnter your choice: ");

            switch (choice) {

                case 1:
                    searchRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    viewBookings();
                    break;

                case 4:
                    cancelBooking();
                    break;

                case 5:
                    saveBookings();
                    System.out.println("Thank you for using our system!");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 5);

        sc.close();
    }
}