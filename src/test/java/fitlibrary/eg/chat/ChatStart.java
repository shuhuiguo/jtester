/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.eg.chat;

import java.util.List;

import fitlibrary.DoFixture;

public class ChatStart extends DoFixture {
	private static ChatSystem chat;

	public ChatStart() {
		super(chat = new ChatSystem());
	}
	public List<User> usersInRoom(String roomName) {
		return chat.findRoom(roomName).getUsers();
	}
	public boolean roomIsEmpty(String roomName) { 
		return chat.findRoom(roomName).getUsers().isEmpty();
	}
	public List<User> usersInLotr() {
		return usersInRoom("lotr");
	}
}
