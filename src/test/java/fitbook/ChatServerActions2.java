package fitbook;

import fitbook.chat.ChatRoom;

/*
 * @author Rick Mugridge 8/12/2003
 *
 * Copyright (c) 2003 Rick Mugridge, University of Auckland, NZ
 * Released under the terms of the GNU General Public License version 2 or later.
 *
 */

/**
  * Test some simple changes to a chat room
*/
public class ChatServerActions2 extends fit.Fixture { //COPY:ALL
	public static ChatRoom CHAT; //COPY:ALL
	private String userName = ""; //COPY:ALL
	private String roomName = ""; //COPY:ALL
	private boolean userEnteredRoom = false;
	//COPY:ALL
	public ChatServerActions2() { //COPY:ALL
		CHAT = new ChatRoom(); //COPY:ALL
	} //COPY:ALL
	public void user(String userName2) { //COPY:ALL
		this.userName = userName2; //COPY:ALL
	} //COPY:ALL
	public void connect() { //COPY:ALL
		CHAT.connectUser(userName); //COPY:ALL
	} //COPY:ALL
	public void room(String roomName2) { //COPY:ALL
		this.roomName = roomName2; //COPY:ALL
	} //COPY:ALL
	public void newRoom() { //COPY:ALL
	    userEnteredRoom = CHAT.userCreatesRoom(userName,roomName); //COPY:ALL
	} //COPY:ALL
	public void enterRoom() { //COPY:ALL
		CHAT.userEntersRoom(userName,roomName); //COPY:ALL
	} //COPY:ALL
	public int occupantCount() { //COPY:ALL
		return CHAT.occupants(roomName); //COPY:ALL
	} //COPY:ALL
	public void disconnect() { //COPY:ALL
		CHAT.disconnectUser(userName); //COPY:ALL
	} //COPY:ALL
	public boolean roomEntered() {
	    return userEnteredRoom;
	}
} //COPY:ALL
