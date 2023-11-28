import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ShowBookingTest {

    private Show createDefaultShow() {
        return new Show(1, 5, 5, 2);
    }

    @Test
    public void testSetupShow() {
        ShowManager showManager = new ShowManager();
        Admin admin = new Admin(showManager);
        admin.setupShow(1, 5, 5, 2);

        assertNotNull(showManager.getShows().get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetupShowExceedMaxRows() {
        ShowManager showManager = new ShowManager();
        Admin admin = new Admin(showManager);
        // Attempt to setup a show with more than the allowed maximum rows (26)
        admin.setupShow(1, 30, 5, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetupShowExceedMaxSeatsPerRow() {
        ShowManager showManager = new ShowManager();
        Admin admin = new Admin(showManager);
        // Attempt to setup a show with more than the allowed maximum seats per row (10)
        admin.setupShow(1, 5, 15, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetupShowDuplicate() {
        ShowManager showManager = new ShowManager();
        Admin admin = new Admin(showManager);

        // Attempt to set up a duplicate show with the same show number
        admin.setupShow(1, 5, 5, 2);
        System.out.println("First setupShow succeeded");

        // The second setupShow should throw an exception, and the test will pass if it
        // does
        admin.setupShow(1, 3, 3, 1);

        // If no exception is thrown, fail the test
        fail("Expected IllegalArgumentException was not thrown");
    }

    @Test
    public void testViewShow() {
        ShowManager showManager = new ShowManager();
        Admin admin = new Admin(showManager);
        admin.setupShow(1, 5, 5, 2);

        Show show = showManager.getShows().get(1);

        assertNotNull(show);
        assertEquals(1, show.getShowNumber());
        // Add assertions for other show properties as needed
    }

    @Test
    public void testCheckAvailability() {
        Show show = createDefaultShow();
        List<Seat> availableSeats = show.getAvailableSeats();
        assertNotNull(availableSeats);
        assertTrue(availableSeats.size() > 0);
    }

    @Test
    public void testBookTicket() {
        Show show = createDefaultShow();
        List<Seat> availableSeats = show.getAvailableSeats();

        Buyer buyer = new Buyer("1234567890");
        buyer.buyTicket(show, Arrays.asList(availableSeats.get(0)));

        assertEquals(1, buyer.getTickets().size());
    }

    @Test
    public void testCancelTicketWithinWindow() {
        Show show = createDefaultShow();
        List<Seat> availableSeats = show.getAvailableSeats();

        Buyer buyer = new Buyer("1234567890");
        buyer.buyTicket(show, Arrays.asList(availableSeats.get(0)));

        Ticket ticket = buyer.getTickets().get(0);

        buyer.cancelTicket(ticket,"1234567890");
        assertFalse(buyer.getTickets().contains(ticket));
    }

    @Test
    public void testCancelTicketWithinWindowSleepWithinWindow() {
        Show show = createDefaultShow();
        List<Seat> availableSeats = show.getAvailableSeats();

        Buyer buyer = new Buyer("1234567890");
        buyer.buyTicket(show, Arrays.asList(availableSeats.get(0)));

        sleepForDuration(1 * 60 * 1000); // 1 minute

        buyer.cancelTicket(buyer.getTickets().get(0), "1234567890");

        assertTrue("Ticket should be canceled within the window", buyer.getTickets().isEmpty());
    }

    @Test
    public void testCancelTicketOutsideWindow() {
        Show show = createDefaultShow();
        List<Seat> availableSeats = show.getAvailableSeats();

        Buyer buyer = new Buyer("1234567890");
        buyer.buyTicket(show, Arrays.asList(availableSeats.get(0)));

        long originalCreatedDate = buyer.getTickets().get(0).getCreatedDate();

        long currentTime = System.currentTimeMillis();
        long adjustedCreatedDate = currentTime - (3 * 60 * 1000);

        buyer.getTickets().get(0).setCreatedDate(adjustedCreatedDate);

        buyer.cancelTicket(buyer.getTickets().get(0), "1234567890");

        assertFalse("Ticket should not be canceled outside the window", buyer.getTickets().isEmpty());
    }

    @Test
    public void testCancelTicketOutsideWindowSleep() {
        Show show = createDefaultShow();
        List<Seat> availableSeats = show.getAvailableSeats();

        Buyer buyer = new Buyer("1234567890");
        buyer.buyTicket(show, Arrays.asList(availableSeats.get(0)));

        sleepForDuration(3 * 60 * 1000); // 3 minutes

        buyer.cancelTicket(buyer.getTickets().get(0), "1234567890");

        assertFalse("Ticket should not be canceled outside the window", buyer.getTickets().isEmpty());
    }

    private void sleepForDuration(long durationMillis) {
        try {
            Thread.sleep(durationMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void testMultipleBookingsPerPhone() {
        Show show = createDefaultShow();
        List<Seat> availableSeats = show.getAvailableSeats();

        Buyer buyer = new Buyer("1234567890");
        buyer.buyTicket(show, Arrays.asList(availableSeats.get(0)));

        assertThrows(IllegalArgumentException.class, () -> buyer.buyTicket(show, Arrays.asList(availableSeats.get(1))));
    }

    @Test
    public void testMultipleBookingsDifferentShows() {
        Show show1 = createDefaultShow();
        List<Seat> availableSeats1 = show1.getAvailableSeats();

        Show show2 = new Show(2, 5, 5, 2);
        List<Seat> availableSeats2 = show2.getAvailableSeats();

        Buyer buyer = new Buyer("1234567890");
        buyer.buyTicket(show1, Arrays.asList(availableSeats1.get(0)));
        buyer.buyTicket(show2, Arrays.asList(availableSeats2.get(0)));

        assertEquals(2, buyer.getTickets().size());
    }

    @Test
    public void testBuyerAvailability() {
        Show show = createDefaultShow();
        Buyer buyer = new Buyer("1234567890");

        List<Seat> availableSeats = buyer.checkAvailability(show);

        assertEquals(25, availableSeats.size());
        assertTrue(availableSeats.contains(new Seat("A1")));
        assertTrue(availableSeats.contains(new Seat("E5")));
    }

    @Test
    public void testBuyerBookAndCancel() {
        Show show = createDefaultShow();
        List<Seat> availableSeats = show.getAvailableSeats();
        Buyer buyer = new Buyer("1234567890");

        buyer.buyTicket(show, Arrays.asList(availableSeats.get(0)));
        buyer.cancelTicket(buyer.getTickets().get(0), "1234567890");

        assertTrue("Ticket should be canceled", buyer.getTickets().isEmpty());
    }
}
