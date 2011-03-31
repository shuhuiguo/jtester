package fitbook;

import fitlibrary.SequenceFixture;

public class ChatStartSequence extends SequenceFixture {
	private ChatStart chat = new ChatStart();
	
	@Override
	public Object getSystemUnderTest() {
		return chat;
	}
}
