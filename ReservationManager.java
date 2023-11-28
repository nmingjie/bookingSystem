import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationManager {
    // List<Seat>>
    private Map<Ticket, ReservationInfo> reservations;

    public ReservationManager() {
        this.reservations = new HashMap<>();
    }

    public void reserveSeats(Ticket ticket, String buyerPhone, List<Seat> seats) {
        reservations.put(ticket, new ReservationInfo(ticket.getTicketNum(), buyerPhone, new ArrayList<>(seats)));
    }

    public void cancelReservation(Ticket ticket) {
        reservations.remove(ticket);
    }

    public List<Seat> getReservedSeats(Ticket ticket) {
        // return reservations.getOrDefault(ticket, new ArrayList<>());
        ReservationInfo reservation =  reservations.get(ticket);
        
        if (reservations == null){
            return new ArrayList<>();
        }
        else {
           return reservation.getReservedSeats();
        }
    }

    public Map<Ticket, ReservationInfo> getReservations() {
        return new HashMap<>(reservations);
        // return reservations;
    }

    public boolean hasReservation(Ticket ticket) {
        return reservations.containsKey(ticket);
    }

    public void clearReservations() {
        reservations.clear();
    }
}
