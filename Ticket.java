import java.util.List;
import java.util.UUID;

public class Ticket {
    private String ticketNum;
    private long createdDate;
    private long cancellationWindowMillis;
    private List<Seat> seats;
    private Show show; // Reference to the associated show

    public Ticket(List<Seat> seats, long cancellationWindowMillis, Show show) {
        this.ticketNum = generateTicketNumber();
        this.createdDate = System.currentTimeMillis();
        this.cancellationWindowMillis = cancellationWindowMillis;
        this.seats = seats;
        this.show = show;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public Show getShow() {
        return show;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    // Using a static method to generate a random UUID
    private static String generateTicketNumber() {
        return UUID.randomUUID().toString();
    }

    // Using Instant for time-related operations
    public boolean isCancellationAllowed() {
        long currentTime = System.currentTimeMillis();
        long cancellationWindowMillis = getShow().getCancellationWindowMillis();
        return currentTime - getCreatedDate() <= cancellationWindowMillis;
    }

}
