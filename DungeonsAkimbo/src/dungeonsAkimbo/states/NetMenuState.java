package dungeonsAkimbo.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import dungeonsAkimbo.DungeonsAkimboGame;

public class NetMenuState extends BasicGameState {
	
	
	private TextField name, address, port;
	private Rectangle host, join;
	private DungeonsAkimboGame dag;
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		dag = (DungeonsAkimboGame) game;
		
		name = new TextField(container, container.getDefaultFont(), 450, 200, 200, 20);
		address = new TextField(container, container.getDefaultFont(), 400, 400, 200, 20);
		port = new TextField(container, container.getDefaultFont(), 650, 400, 100, 20);
		
		name.setBackgroundColor(Color.white);
		address.setBackgroundColor(Color.white);
		port.setBackgroundColor(Color.white);
		
		name.setTextColor(Color.black);
		address.setTextColor(Color.black);
		port.setTextColor(Color.black);
		
		name.setText("DaUser");
		address.setText("localhost");
		port.setText("8989");
		
		host = new Rectangle(495,230,110,20);
		join = new Rectangle(495,440,110,20);
		
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		name.render(container, g);
		address.render(container, g);
		port.render(container, g);
		
		g.drawString("Player name: ", 450, 175);
		g.drawString("Host address: ", 400, 375);
		g.drawString("Host port: ", 650, 375);
		g.drawString("Host server", 500, 230);
		g.drawString("Join Server", 500, 440);
		g.draw(host);
		g.draw(join);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = container.getInput();
		float mouseX = input.getMouseX();
		float mouseY = input.getMouseY();
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if(host.contains(mouseX, mouseY)) {
				dag.startServer();
				dag.startClient(name.getText(),"localhost",8989);
				game.enterState(DungeonsAkimboGame.PLAYTESTSTATE);
			}
			
			if(join.contains(mouseX, mouseY)) {
				dag.startClient(name.getText(), address.getText(), Integer.parseInt(port.getText()));
				game.enterState(DungeonsAkimboGame.PLAYTESTSTATE);
			}
		}
		

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return DungeonsAkimboGame.NETMENUSTATE;
	}

}
