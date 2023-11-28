import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Seat {
    private String rowNum;
    private int seatNum;

    // public Seat(String rowNum, int seatNum) {
    //     this.rowNum = rowNum;
    //     this.seatNum = seatNum;
    // }
    public Seat(String seatString) {
        // Assuming seatString is in the format "A4"
        this.rowNum = seatString.substring(0, 1); // Extract the first character as rowNum
        this.seatNum = Integer.parseInt(seatString.substring(1)); // Extract the remaining characters as seatNum
    }

    public void printSeat(Seat seat) {
        System.out.println(seat.rowNum+seat.seatNum);
    }

    // Copy constructor
    public Seat(Seat other) {
        this.rowNum = other.rowNum;
        this.seatNum = other.seatNum;
    }

    public String getRowNum() {
        return rowNum;
    }

    public int getSeatNum() {
        return seatNum;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Seat seat = (Seat) obj;
        return rowNum.equals(seat.rowNum) && seatNum == seat.seatNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowNum, seatNum);
    }
}
