package fitlibraryGeneric.eg.rentEz;

import java.util.ArrayList;
import java.util.List;

public class Client {
	private String name;
	private List<Rental> rentals = new ArrayList<Rental>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Rental> getRentals() {
		return rentals;
	}
	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}
	public void addRentals(List<RentalItem> rentalItems) {
		for (@SuppressWarnings("unused") RentalItem item : rentalItems)
			rentals.add(new Rental());
	}
	@Override
	public String toString() {
		return "Client["+name+"]";
	}
}
