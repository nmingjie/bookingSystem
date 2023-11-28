
public class Admin {
    private ShowManager showManager;

    public Admin(ShowManager showManager) {
        // this.showManager = new ShowManager();
        this.showManager =  showManager;
    }

    public void setupShow(int showNumber, int numRows, int seatsPerRow, int cancellationWindowMinutes) {
        showManager.setupShow(showNumber, numRows, seatsPerRow, cancellationWindowMinutes);
    }

    public void viewShow(int showNumber) {
        // (To display Show Number, Ticket#, Buyer Phone#, Seat Numbers allocated to the buyer)
        showManager.viewShow(showNumber);

    }

    public ShowManager getShowManager() {
        return showManager;
    }
}
