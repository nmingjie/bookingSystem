import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Show {
    private int showNumber;
    private int numRows;
    private int seatsPerRow;
    private long cancellationWindowMillis;
    private List<Seat> availableSeats;
    private ReservationManager reservationManager;

    public Show(int showNumber, int numRows, int seatsPerRow, long cancellationWindowMinutes) {
        this.showNumber = showNumber;
        this.numRows = numRows;
        this.seatsPerRow = seatsPerRow;
        this.cancellationWindowMillis = TimeUnit.MINUTES.toMillis(cancellationWindowMinutes);
        this.availableSeats = generateAvailableSeats();
        this.reservationManager = new ReservationManager();
    }

    private List<Seat> generateAvailableSeats() {
        List<Seat> seats = new ArrayList<>();

        for (int row = 0; row < numRows; row++) {
            char rowLabel = (char) ('A' + row);

            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                String seatId = String.valueOf(rowLabel) + seatNum;
                seats.add(new Seat(seatId));
            }
        }

        return seats;
    }

    public void displayShowInformation() {
        System.out.println("Show Number: " + showNumber);
        // Display other show information as needed
    }

    public List<Seat> getAvailableSeats() {
        return new ArrayList<>(availableSeats);
    }

    public long getCancellationWindowMillis() {
        return cancellationWindowMillis;
    }

    public int getShowNumber() {
        return showNumber;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public long getCancellationWindowMinutes() {
        return TimeUnit.MILLISECONDS.toMinutes(cancellationWindowMillis);
    }

    public void reserveSeats(List<Seat> seats, String phoneNumber, Ticket ticket) {
        reservationManager.reserveSeats(ticket, phoneNumber, seats);
        availableSeats.removeAll(seats);
    }

    public void cancelReservation(Ticket ticket) {
        List<Seat> reservedSeats = reservationManager.getReservedSeats(ticket);
        availableSeats.addAll(reservedSeats);
        reservationManager.cancelReservation(ticket);
    }

    public List<ReservationInfo> getAllReservations() {
        List<ReservationInfo> allReservations = new ArrayList<>();

        for (Map.Entry<Ticket, ReservationInfo> entry : reservationManager.getReservations().entrySet()) {
            // Ticket ticket = entry.getKey();
            ReservationInfo info = entry.getValue();
            // List<Seat> reservedSeats = entry.getValue();
            // String buyerPhone = getBuyerPhone(ticket);

            // ReservationInfo reservationInfo = new ReservationInfo(ticket.getTicketNum(), info.getBuyerPhone(), reservationManager.getReservedSeats());
            allReservations.add(info);
        }

        return allReservations;
        
    }

    // public static class ReservationInfo {
    //     private final String ticketNumber;
    //     private final String buyerPhone;
    //     private final List<Seat> reservedSeats;

    //     public ReservationInfo(String ticketNumber, String buyerPhone, List<Seat> reservedSeats) {
    //         this.ticketNumber = ticketNumber;
    //         this.buyerPhone = buyerPhone;
    //         this.reservedSeats = new ArrayList<>(reservedSeats);
    //     }

    //     public String getTicketNumber() {
    //         return ticketNumber;
    //     }

    //     public String getBuyerPhone() {
    //         return buyerPhone;
    //     }

    //     public List<Seat> getReservedSeats() {
    //         return new ArrayList<>(reservedSeats);
    //     }
    // }

}
