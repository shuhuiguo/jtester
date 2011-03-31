package fitlibraryGeneric.eg.rentEz;

import java.util.List;

public class ClientTransaction {
	private Client client;

	public ClientTransaction(Client client) {
		this.client = client;
	}

	public boolean rent(List<RentalItem> rentals) {
		client.addRentals(rentals);
		return true;
	}

	public void payWithCash(double cash) {
		//
	}

	public boolean complete() {
		return true;
	}
}
