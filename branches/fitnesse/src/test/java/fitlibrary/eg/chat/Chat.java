/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.eg.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fitlibrary.exception.FitLibraryException;

// Minimalistic code to show how FitLibrary works	
public class Chat {
	private List<User> users = new ArrayList<User>();
	private List<Room> rooms = new ArrayList<Room>();
	private Map<String, Room> roomsMap = new HashMap<String, Room>();
	private Map<String, User> usersMap = new HashMap<String, User>();

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		for (User user : users)
			addUser(user);
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		for (Room room : rooms)
			addRoom(room);
	}

	public User getUser(String userName) {
		return usersMap.get(userName);
	}

	public Room getRoom(String roomName) {
		return roomsMap.get(roomName);
	}

	public boolean connectUser(String userName) {
		addUser(new User(userName));
		return true;
	}

	public void disconnectUser(String userName) {
		User user = getUser(userName);
		for (Room room : roomsMap.values())
			room.removeUser(user);
		users.remove(user);
		usersMap.remove(userName);
	}

	public boolean userCreatesRoom(User user, String roomName) {
		addRoom(new Room(roomName, user));
		return true;
	}

	public boolean userEntersRoom(User user, Room room) {
		room.addUser(user);
		return true;
	}

	public boolean userRemovesRoom(User user, Room room) {
		if (room.isEmpty()) {
			rooms.remove(room);
			roomsMap.remove(room.getName());
		}
		throw new RuntimeException("Unable to remove room " + room.getName() + " because it's not empty");
	}

	private void addUser(User user) {
		if (usersMap.get(user.getName()) != null)
			throw new FitLibraryException("User already exists");
		users.add(user);
		usersMap.put(user.getName(), user);
	}

	private void addRoom(Room room) {
		if (roomsMap.get(room.getName()) != null)
			throw new FitLibraryException("Room already exists");
		rooms.add(room);
		roomsMap.put(room.getName(), room);
	}
}
