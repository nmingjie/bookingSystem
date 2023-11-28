# Show Booking System

This repository contains a simple command-line-based Show Booking System implemented in Java. The system allows administrators to set up shows and view seat allocations, and buyers to check seat availability, book tickets, and cancel bookings.

## Setup

0. **Note:**
   Please delete ShowBookingTest.java. This is just junit test which throws error as the package org.junit does not exist.


1. **Compilation:**

   Compile the Java source files:

   ```bash
   javac *.java
   ```

2. **Run the Booking System:**

   Execute the main class:

   ```bash
   java BookingSystem
   ```

## Suggested Inputs

### Set up a Show:

```bash
admin setup 1 5 5 2
```

### Set up Again (Fail Due to Exceeding Limits):

```bash
admin setup 1 30 5 2
```

### Set up Again (Fail Due to Existing Show):

```bash
admin setup 1 5 5 2
```

### Check Availability:

```bash
buyer availability 1
```

### Book a Ticket:

```bash
buyer book 1 91234567 A1
```

Or book multiple seats:

```bash
buyer book 1 91234567 A1,A2
```

### Fail to Book (Show or Seats Don't Exist):

```bash
buyer book #ShowNumThatDoesntExist 91234567 #SeatsThatDoesntExist
```

### View Show Details:

```bash
admin view 1
```

### Book (Cancel Within 2 Minutes):

```bash
buyer cancel #ticketNum 91234567
```

### Book (Cancel Without Proper Inputs):

```bash
buyer cancel #RandomTicketNum #RandomPhoneNum
```

### Book (Cancel After 2 Minutes):

```bash
buyer cancel #ticketNum 91234567
```

### Set up Another Show:

```bash
admin setup 2 5 5 2
```

### Book Show 2:

```bash
buyer book 2 91234567 A1
```

### View Show 2:

```bash
admin view 2
```

Feel free to customize the inputs based on your specific implementation.