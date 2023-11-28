import java.util.ArrayList;
import java.util.List;

public class ReservationInfo {
    private String ticketNumber;
    private String buyerPhone;
    private List<Seat> reservedSeats;

    public ReservationInfo(String ticketNumber, String buyerPhone, List<Seat> reservedSeats) {
        this.ticketNumber = ticketNumber;
        this.buyerPhone = buyerPhone;
        this.reservedSeats = new ArrayList<>(reservedSeats);
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public List<Seat> getReservedSeats() {
        return new ArrayList<>(reservedSeats);
    }
}