package dungeonsAkimbo.gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import org.newdawn.slick.gui.TextField;

public class ChatGUI {
	int chatLogHeight;
	int chatBarY;
	
	GameContainer chatLogContainer;
	
	private TextField chatBar, chatLog;
	
	List<String> chatHistory = new ArrayList<String>();
	
	public ChatGUI(int x, int y, int width, int height, GameContainer container) {
		int chatLogHeight = height - 20;
		int chatBarY = y + chatLogHeight;
			
		chatLog = new TextField(container, container.getDefaultFont(), x, y, width, chatLogHeight);
		chatBar = new TextField(container, container.getDefaultFont(), x, chatBarY, width , 20);
		
		chatLog.setBackgroundColor(Color.transparent);
		chatLog.setBorderColor(Color.cyan);
		chatLog.setTextColor(Color.black);
		chatLog.deactivate();
		chatLog.setConsumeEvents(false);
		chatLog.setAcceptingInput(false);
		
		
		chatBar.setBackgroundColor(Color.transparent);
		chatBar.setBorderColor(Color.cyan);
		chatBar.setCursorVisible(true);
		chatBar.setTextColor(Color.black);
		chatBar.setMaxLength(112);
		chatBar.setConsumeEvents(false);
		chatBar.setAcceptingInput(false);
	}
	
	public TextField getChatBar() {
		return chatBar;
	}
	
	public TextField getChatLog() {
		return chatLog;
	}
	
	public void addNewChat(String chat) {
		chatHistory.add(chat);		
	}
	
	public void updateChatLog() {
		chatLog.setText("");
		if(chatHistory.size() < 10) {
			for(String message : chatHistory) {
				chatLog.setText(chatLog.getText() + "\n" + message);
			}
		}else {
			List<String> chatUpdate = chatHistory.subList(chatHistory.size()-10, chatHistory.size());
			for(String message : chatUpdate) {
				chatLog.setText(chatLog.getText() + "\n" + message);
			}
		}
		
	}
	
	public void activateChatBar() {
		chatBar.setFocus(true);
		chatBar.setAcceptingInput(true);
		chatBar.setConsumeEvents(true);
	}
	
	public String getChatBarContents() {
		String message = chatBar.getText();
		chatBar.setText("");
		chatBar.setFocus(false);
		chatBar.setConsumeEvents(false);
		chatBar.setAcceptingInput(false);
		return message;
	}

}
