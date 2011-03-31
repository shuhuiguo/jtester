/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 2/09/2006
 */

package fitlibrary.eg.chat;

import java.util.ArrayList;
import java.util.List;

public class Room {
	private String name;
	private List<User> users = new ArrayList<User>();
	private User owner;

	public Room(String roomName, User owner) {
		this.name = roomName;
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public void addUser(User user) {
		if (!users.contains(user))
			users.add(user);
	}
	public boolean remove() {
		return false;
	}
	public boolean isEmpty() {
		return users.isEmpty();
	}
	@Override
	public String toString() {
		return name;
	}
	public void removeUser(User user) {
		users.remove(user);
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
}
