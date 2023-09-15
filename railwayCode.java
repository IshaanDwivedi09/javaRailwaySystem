import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Train {
	private int trainNumber;
	private String source;
	private String destination;
	private String departureTime;
	private int passengerStrength;

	public Train(int trainNumber, String source, String destination, String departureTime, int passengerStrength) {
		this.trainNumber = trainNumber;
		this.source = source;
		this.destination = destination;
		this.departureTime = departureTime;
		this.passengerStrength = passengerStrength;
	}

	public int getTrainNumber() {
		return trainNumber;
	}

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public int getPassengerStrength() {
		return passengerStrength;
	}
}

class Ticket {
	private Train train;
	private String passengerName;
	private int seatNumber;

	public Ticket(Train train, String passengerName, int seatNumber) {
		this.train = train;
		this.passengerName = passengerName;
		this.seatNumber = seatNumber;
	}

	public Train getTrain() {
		return train;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public int getSeatNumber() {
		return seatNumber;
	}
}

class ReservationSystem {
	private List<Train> trainsList = new ArrayList<>();
	private List<Ticket> bookedTicketsList = new ArrayList<>();

	public List<Ticket> getBookedTicketsList() {
		return bookedTicketsList;
	}

	public void addTrain(Train train) {
		trainsList.add(train);
	}

	public List<Train> getAvailableTrains(String source, String destination) {
		List<Train> availableTrainsList = new ArrayList<>();

		for (Train train : trainsList) {
			if (train.getSource().equalsIgnoreCase(source) && train.getDestination().equalsIgnoreCase(destination)) {
				availableTrainsList.add(train);
			}
		}

		return availableTrainsList;
	}

	public int checkSeatAvailability(Train train) {
		int bookedSeats = 0;

		for (Ticket ticket : bookedTicketsList) {
			if (ticket.getTrain() == train) {
				bookedSeats++;
			}
		}

		return train.getPassengerStrength() - bookedSeats;
	}

	public void bookTicket(Train train, String passengerName, int seatNumber) {
		int availableSeats = checkSeatAvailability(train);

		if (availableSeats > 0) {
			Ticket ticket = new Ticket(train, passengerName, seatNumber);
			bookedTicketsList.add(ticket);
			System.out.println("Ticket booked successfully!");
		} else {
			System.out.println("Sorry, no seats available on this train.");
		}
	}

	public void cancelTicket(Ticket ticket) {
		if (bookedTicketsList.contains(ticket)) {
			bookedTicketsList.remove(ticket);
			System.out.println("Ticket canceled successfully!");
		} else {
			System.out.println("Ticket not found. Cannot cancel.");
		}
	}

	public void displayTicketDetails(Ticket ticket) {
		if (bookedTicketsList.contains(ticket)) {
			System.out.println("Ticket Details:");
			System.out.println("Train Number: " + ticket.getTrain().getTrainNumber());
			System.out.println("Passenger Name: " + ticket.getPassengerName());
			System.out.println("Seat Number: " + ticket.getSeatNumber());
		} else {
			System.out.println("Ticket not found. Cannot display details.");
		}
	}

}

public class railwayCode {
	public static void main(String[] args) {
		ReservationSystem reservationSystem = new ReservationSystem();

		Train mumbaiDelhiTrain = new Train(1010, "Mumbai", "Delhi", "13:05", 50);
		Train delhiJaipurTrain = new Train(2013, "Delhi", "Jaipur", "7:00", 50);
		Train prayagrajDelhiTrain = new Train(3045, "Prayagraj", "Delhi", "10:00", 50);

		reservationSystem.addTrain(mumbaiDelhiTrain);
		reservationSystem.addTrain(delhiJaipurTrain);
		reservationSystem.addTrain(prayagrajDelhiTrain);

		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter source station: ");
		String source = scanner.nextLine();

		System.out.println("Enter destination station: ");
		String destination = scanner.nextLine();

		List<Train> availableTrains = reservationSystem.getAvailableTrains(source, destination);

		if (availableTrains.isEmpty()) {
			System.out.println("No trains available for the given source and destination.");
			return;
		}

		System.out.println("Available Trains:");
		for (Train train : availableTrains) {
			System.out.println("Train Number: " + train.getTrainNumber());
			System.out.println("Departure Time: " + train.getDepartureTime());
			System.out.println("Passenger Strength: " + train.getPassengerStrength());
			System.out.println();
		}

		System.out.println("Enter the Train Number to perform an action:");
		int trainNumber = scanner.nextInt();

		Train selectedTrain = null;
		for (Train train : availableTrains) {
			if (train.getTrainNumber() == trainNumber) {
				selectedTrain = train;
			}
		}

		if (selectedTrain == null) {
			System.out.println("Invalid Train Number. Please try again.");
			return;
		}

		// Now, provide options for user actions:
		System.out.println("Select an action:");
		System.out.println("1. Check Seat Availability");
		System.out.println("2. Book a Ticket");
		System.out.println("3. Cancel a Ticket");
		System.out.println("4. Display Ticket Details");
		int actionChoice = scanner.nextInt();

		switch (actionChoice) {
			case 1:
				int availableSeats = reservationSystem.checkSeatAvailability(selectedTrain);
				System.out.println("Available seats on this train: " + availableSeats);
				break;
			case 2:
				System.out.println("Enter passenger name: ");
				String passengerName = scanner.next();
				System.out.println("Enter seat number: ");
				int seatNumber = scanner.nextInt();
				reservationSystem.bookTicket(selectedTrain, passengerName, seatNumber);
				break;
			case 3:
				System.out.println("Enter passenger name for ticket cancellation: ");
				String cancelPassengerName = scanner.next();
				System.out.println("Enter seat number for ticket cancellation: ");
				int cancelSeatNumber = scanner.nextInt();

				// Search for the ticket to cancel
				Ticket ticketToCancel = null;
				for (Ticket ticket : reservationSystem.getBookedTicketsList()) { // Access bookedTicketsList directly
					if (ticket.getTrain() == selectedTrain &&
							ticket.getPassengerName().equalsIgnoreCase(cancelPassengerName) &&
							ticket.getSeatNumber() == cancelSeatNumber) {
						ticketToCancel = ticket;
						break; // Found the ticket, exit the loop
					}
				}

				if (ticketToCancel != null) {
					reservationSystem.cancelTicket(ticketToCancel);
					System.out.println("Ticket canceled successfully!");
				} else {
					System.out.println("Ticket not found. Cancellation failed.");
				}
				break;
			case 4:
				System.out.println("Enter passenger name for ticket details: ");
				String displayPassengerName = scanner.next();
				System.out.println("Enter seat number for ticket details: ");
				int displaySeatNumber = scanner.nextInt();

				// Search for the ticket to display details
				Ticket ticketToDisplay = null;
				for (Ticket ticket : reservationSystem.getBookedTicketsList()) { // Access bookedTicketsList directly
					if (ticket.getTrain() == selectedTrain &&
							ticket.getPassengerName().equalsIgnoreCase(displayPassengerName) &&
							ticket.getSeatNumber() == displaySeatNumber) {
						ticketToDisplay = ticket;
						break; // Found the ticket, exit the loop
					}
				}

				if (ticketToDisplay != null) {
					reservationSystem.displayTicketDetails(ticketToDisplay);
				} else {
					System.out.println("Ticket not found. Unable to display details.");
				}
				break;

			default:
				System.out.println("Invalid action choice. Please try again.");
				break;
		}
	}

}