package fitbook;

import java.util.Iterator;

import fitbook.chat.ChatServer;
import fitbook.chat.Room;
import fitbook.chat.User;
import fitlibrary.DoFixture;
import fitlibrary.parser.graphic.DotGraphic;

/*
 * @author Rick Mugridge 13/07/2004
 * Copyright (c) 2004 Rick Mugridge, University of Auckland, NZ
 * Released under the terms of the GNU General Public License version 2 or later.
 */

/**
 * 
 */
public class UsersAndRooms extends DoFixture { //COPY:ALL
    private ChatServer chat = new ChatServer(); //COPY:ALL
    //COPY:ALL
	public DotGraphic users() { //COPY:ALL
	    String dot = "digraph G {\n"; //COPY:ALL
	    for (Iterator<Room> itRoom = chat.getRooms(); itRoom.hasNext(); ) { //COPY:ALL
	        Room room = itRoom.next(); //COPY:ALL
	        for (Iterator<User> itUser = room.users(); itUser.hasNext(); ) { //COPY:ALL
	            User user = itUser.next(); //COPY:ALL
	            dot += room.getName()+"->"+user.getName()+";\n"; //COPY:ALL
	        } //COPY:ALL
	    } //COPY:ALL
		return new DotGraphic(dot+"}\n"); //COPY:ALL
	} //COPY:ALL
} //COPY:ALL
