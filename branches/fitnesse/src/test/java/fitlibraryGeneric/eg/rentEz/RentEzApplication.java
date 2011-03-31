package fitlibraryGeneric.eg.rentEz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentEzApplication {
	private List<RentalItemType> rentalItemTypes = new ArrayList<RentalItemType>();
	private List<Client> clients = new ArrayList<Client>();
	private List<StaffMember> staffMembers = new ArrayList<StaffMember>();
	
	private Map<String, RentalItemType> rentalItemTypeMap = new HashMap<String, RentalItemType>();
	private Map<String, Client> clientMap = new HashMap<String, Client>();
	private Map<String, StaffMember> staffMemberMap = new HashMap<String, StaffMember>();
	
	public List<Client> getClients() {
		return clients;
	}
	public void setClients(List<Client> clients) {
		for (Client client : clients)
			clientMap.put(client.getName(), client);
		this.clients = clients;
	}
	public List<RentalItemType> getRentalItemTypes() {
		return rentalItemTypes;
	}
	public void setRentalItemTypes(List<RentalItemType> rentalItemTypes) {
		for (RentalItemType item : rentalItemTypes)
			rentalItemTypeMap.put(item.getName(), item);
		this.rentalItemTypes = rentalItemTypes;
	}
	public List<StaffMember> getStaffMembers() {
		return staffMembers;
	}
	public void setStaffMembers(List<StaffMember> staffMembers) {
		for (StaffMember staff : staffMembers)
			staffMemberMap.put(staff.getName(), staff);
		this.staffMembers = staffMembers;
	}

	public Client findClient(String key) { 
		return clientMap .get(key);
	}
	public StaffMember findStaffMember(String key) {
		return staffMemberMap .get(key);
	}
	public RentalItemType findRentalItemType(String key) {
		return rentalItemTypeMap.get(key);
	}
}
