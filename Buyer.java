
import java.util.ArrayList;
import java.util.List;

public class Buyer {
    private String phoneNumber;
    private List<Ticket> tickets;
    private SeatManager seatManager;

    public Buyer(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.seatManager = new SeatManager();
        this.tickets = new ArrayList<>();
    }

    public void buyTicket(Show show, List<Seat> seats) {
        // Check if the buyer already has a ticket for the given show
        if (hasTicketForShow(show)) {
            throw new IllegalArgumentException("Only one booking per phone# is allowed per show.");
        }

        List<Seat> showSeats = seatManager.getAvailableSeatNumbers(show);
        // If any of the selected seats is not in available
        boolean canBuy = seats.parallelStream().allMatch(s -> showSeats.contains(s));
        if (!canBuy) {
            throw new IllegalArgumentException("Invalid Input: Seats already reserved or it does not exist ");
        }

        Ticket ticket = new Ticket(seats, show.getCancellationWindowMillis(), show);
        show.reserveSeats(seats, phoneNumber, ticket);
        tickets.add(ticket);
        System.out.println("Ticket purchased successfully. Ticket number: " + ticket.getTicketNum());
    }

    private boolean hasTicketForShow(Show show) {
        // Check if the buyer already has a ticket for the given show
        return tickets.stream()
                .anyMatch(ticket -> ticket.getShow() == show);
    }

    public void cancelTicket(Ticket ticket, String phoneNumber) {
        if (ticket.isCancellationAllowed()) {
            Show show = ticket.getShow();
            show.cancelReservation(ticket);
            tickets.remove(ticket);
            System.out.println("Ticket canceled successfully.");
        } else {
            System.out.println("Cancellation not allowed after the time window.");
        }
    }

    public List<Seat> checkAvailability(Show show) {
        List<Seat> showSeats = seatManager.getAvailableSeatNumbers(show);
        for (int i = 0; i < showSeats.size(); i++) {
            // printSeat(showSeats[i])
            System.out.printf(showSeats.get(i).getRowNum() + showSeats.get(i).getSeatNum() + " ");
        }
        System.out.printf("\n");
        return showSeats;
    }

    public List<Ticket> getTickets() {
        return new ArrayList<>(tickets);
    }

}
