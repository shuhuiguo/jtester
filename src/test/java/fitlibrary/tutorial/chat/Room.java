/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.tutorial.chat;

import java.util.ArrayList;
import java.util.List;

public class Room {
	private String room;
	private List<User> users = new ArrayList<User>();

	public String getRoom() {
		return room;
	}

	public Room(String room) {
		this.room = room;
	}

	public void add(User user) {
		users.add(user);
	}

	public List<User> getUsers() {
		return users;
	}
}
