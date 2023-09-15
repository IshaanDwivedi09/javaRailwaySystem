public int checkSeatAvailability(Train train) {
		int bookedSeats = 0;

		for (Ticket ticket : getBookedTicketsList()) {
			if (ticket.getTrain() == train) {
				bookedSeats++;
			}
		}

		return train.getPassengerStrength() - bookedSeats;
	}
