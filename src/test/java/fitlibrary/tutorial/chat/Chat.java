/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.tutorial.chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat {
	private Map<String, Room> roomMap = new HashMap<String, Room>();
	private Map<String, User> userMap = new HashMap<String, User>();

	public void givenIsAConnectedUser(String name) {
		userMap.put(name, new User(name));
	}

	public void givenIsAChatRoom(String room) {
		roomMap.put(room, new Room(room));
	}

	public void whenEntersRoom(String user, String room) {
		roomMap.get(room).add(userMap.get(user));
	}

	public List<User> thenOccupantsOfAre(String room) {
		return roomMap.get(room).getUsers();
	}
}
