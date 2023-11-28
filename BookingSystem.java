import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingSystem {
    private static final String EXIT_COMMAND = "exit";
    // private Map<Integer, Show> shows;
    private ShowManager showManager;

    private Map<String, Buyer> buyers;
    private Admin admin;

    public BookingSystem() {
        this.showManager = new ShowManager();
        this.buyers = new HashMap<>();
        this.admin = new Admin(showManager);

    }

    private void handleAdminCommand(String[] commandAndArgs) {
        String command = commandAndArgs[1].toLowerCase();
        int showNumber;

        switch (command) {
            case "setup":
                if (commandAndArgs.length != 6) {
                    System.out.println(
                            "Invalid setup command. Usage: setup <Show Number> <Number of Rows> <Number of Seats per Row> <Cancellation Window in Minutes>");
                    return;
                }

                try {
                    // System.out.println("Invalid input. Please enter valid numbers."+
                    // commandAndArgs[2]+commandAndArgs[5]);

                    // System.out.println("Invalid input. Please enter valid numbers.", showNumber,
                    // numrRows, seatsPerRow, cancellationWindow);

                    showNumber = Integer.parseInt(commandAndArgs[2]);
                    int numRows = Integer.parseInt(commandAndArgs[3]);
                    int seatsPerRow = Integer.parseInt(commandAndArgs[4]);
                    int cancellationWindow = Integer.parseInt(commandAndArgs[5]);

                    admin.setupShow(showNumber, numRows, seatsPerRow, cancellationWindow);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter valid numbers.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case "view":
                if (commandAndArgs.length != 3) {
                    System.out.println("Invalid view command. Usage: view <Show Number>");
                    return;
                }

                try {

                    showNumber = Integer.parseInt(commandAndArgs[2]);
                    // System.out.println(admin.viewShow(showNumber));
                    admin.viewShow(showNumber);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid Show Number.");
                }
                break;

            default:
                System.out.println("Unknown admin command: " + command);
        }
    }

    private void handleBuyerCommand(String[] commandAndArgs) {
        String command = commandAndArgs[1].toLowerCase();

        switch (command) {
            case "availability":
                if (commandAndArgs.length != 3) {
                    System.out.println("Invalid availability command. Usage: availability <Show Number>");
                    return;
                }

                try {
                    Show showNumber = showManager.getShows().get(Integer.parseInt(commandAndArgs[2]));
                    if (showNumber == null) {
                        System.out.println("Invalid input. Please enter valid show numbers");
                        break;
                    }
                    Buyer buyer = new Buyer("");
                    buyer.checkAvailability(showNumber);
                    // System.out.println("Available seats: " + availableSeats);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid Show Number.");
                }
                break;

            case "book":
                if (commandAndArgs.length != 5) {
                    System.out.println(
                            "Invalid book command. Usage: book <Show Number> <Phone#> <Comma separated list of seats>");
                    return;
                }

                try {
                    // int showNumber = Integer.parseInt(commandAndArgs[1]);
                    Show showNumber = showManager.getShows().get(Integer.parseInt(commandAndArgs[2]));
                    if (showNumber == null) {
                        System.out.println("Invalid input. Please enter valid show numbers");
                        break;
                    }
                    String phone = commandAndArgs[3];
                    // List<Seat> seatsToBook = Arrays.asList(commandAndArgs[3].split(","));

                    List<Seat> seatsToBook = Arrays.stream(commandAndArgs[4].split(","))
                            .map(Seat::new) // Use the new Seat constructor
                            .collect(Collectors.toList());

                    // Check if Buyer with the given phone number already exists
                    Buyer buyer = buyers.get(phone);
                    if (buyer == null) {
                        System.out.println("Welcome new buyer, your phone number is now registered");
                        buyer = new Buyer(phone);
                        buyers.put(phone, buyer);
                    } else {
                        System.out.println("Welcome existing buyer");

                    }

                    buyer.buyTicket(showNumber, seatsToBook);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter valid numbers.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case "cancel":
                if (commandAndArgs.length != 4) {
                    System.out.println("Invalid cancel command. Usage: cancel <Ticket#> <Phone#>");
                    return;
                }

                try {
                    String ticketNumber = commandAndArgs[2];
                    String phone = commandAndArgs[3];
                    Ticket myTicket = null;

                    Buyer buyer = buyers.get(phone);

                    if (buyer == null) {
                        System.out.println("Buyer does not exist, therefore you have no ticket");
                    } else {
                        // buyer.cancelTicket(buyer.getTickets().get(ticketNumber));
                        List<Ticket> tickets = buyer.getTickets();

                        for (Ticket ticket : tickets) {
                            // Typed Ticket exist by buyer
                            System.out.printf("%s,  %s\n",ticket.getTicketNum(), ticketNumber);
                            if (ticket.getTicketNum().equals(ticketNumber)) { 
                                myTicket = ticket; // Found the ticket with the specified number
                                buyer.cancelTicket(myTicket, phone);
                                break;
                            }
                        }
                        if (myTicket == null) {
                            System.out.println("Ticket not found");
                        }

                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid Ticket Number.");
                }
                break;

            default:
                System.out.println("Unknown buyer command: " + command);
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookingSystem bookingSystem = new BookingSystem();

        while (true) {
            System.out.print("Enter command (type 'exit' to end): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase(EXIT_COMMAND)) {
                break;
            }

            String[] commandAndArgs = input.split("\\s+");
            String userType = commandAndArgs[0].toLowerCase();

            if ("admin".equals(userType)) {
                bookingSystem.handleAdminCommand(commandAndArgs);
            } else if ("buyer".equals(userType)) {
                bookingSystem.handleBuyerCommand(commandAndArgs);
            } else {
                System.out.println("Unknown user type: " + userType);
            }
        }

        scanner.close();
    }
}
