import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShowManager {
    private Map<Integer, Show> shows;

    public ShowManager() {
        this.shows = new HashMap<>();
    }

    public void setupShow(int showNumber, int numRows, int seatsPerRow, int cancellationWindowMinutes) {
        if (numRows > 26 || seatsPerRow > 10) {
            throw new IllegalArgumentException("Invalid show setup. Rows or seats per row exceed maximum limits.");
        }

        if (shows.containsKey(showNumber)) {
            throw new IllegalArgumentException("Show with the same showNumber already exists.");
        }

        shows.putIfAbsent(showNumber, new Show(showNumber, numRows, seatsPerRow, cancellationWindowMinutes));
        System.out.printf(
                "Created show <Show Number>:%d <Number of Rows>:%d <Number of Seats per Row>:%d <Cancellation Window in Minutes>:%d \n",
                showNumber, numRows, seatsPerRow, cancellationWindowMinutes);

    }

    private String getBuyerPhone(Ticket ticket) {
        // Implement logic to retrieve buyer phone based on the ticket
        // You may need to modify the data structure to efficiently map tickets to
        // buyers
        return ""; // Placeholder, replace with your logic
    }

    private String seatsToString(List<Seat> seats) {
        return seats.stream()
                .map(Seat::toString)
                .collect(Collectors.joining(", "));
    }

    public Map<Integer, Show> getShows() {
        return Collections.unmodifiableMap(shows);
    }

    // public String viewShow(int showNumber) {
    // return Optional.ofNullable(shows.get(showNumber))
    // .map(show -> String.format(
    // "Show Number: %d\nNumber of Rows: %d\nNumber of Seats per Row:
    // %d\nCancellation Window: %d minutes",
    // show.getShowNumber(), show.getNumRows(), show.getSeatsPerRow(),
    // show.getCancellationWindowMinutes()))
    // .orElse("Show not found");
    // }

    public void viewShow(int showNumber) {
        Show show = shows.get(showNumber);

        System.out.println(Optional.ofNullable(shows.get(showNumber))
                .map(myShow -> String.format(
                        "Show Number: %d\nNumber of Rows: %d\nNumber of Seats per Row:%d\nCancellation Window: %d minutes",
                        myShow.getShowNumber(), myShow.getNumRows(), myShow.getSeatsPerRow(),
                        myShow.getCancellationWindowMinutes()))
                .orElse("Show not found"));

        if (show != null) {
            System.out.println("Show Number: " + show.getShowNumber());

            List<ReservationInfo> reservations = show.getAllReservations();

            if (reservations.size() == 0) {
                System.out.println("No reservations for this show");
            }

            for (ReservationInfo info : reservations) {
                // Ticket ticket = entry.getKey();
                String ticket = info.getTicketNumber();
                String buyerPhone = info.getBuyerPhone();
                List<Seat> seats = info.getReservedSeats();

                System.out.println("Ticket#: " + ticket);
                System.out.println("Buyer Phone#: " + buyerPhone);
                System.out.println("Seat Numbers allocated to the buyer: " );
                for (int i = 0; i < seats.size(); i++) {
                    // printSeat(showSeats[i])
                    System.out.printf(seats.get(i).getRowNum() + seats.get(i).getSeatNum() + " ");
                }
                System.out.println();
            }
        }
    }
}
