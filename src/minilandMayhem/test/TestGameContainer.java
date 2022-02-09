package minilandMayhem.test;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class TestGameContainer extends AppGameContainer {

	TestInput input;
	Input input2;
	
	public TestGameContainer(Game game) throws SlickException {
		super(game,800,600,false);
		input2 = new Input(0);
	}
	
	
	
	/**
	 * Wie bei AppGameContainer.start() wird das StateBasedGame
	 * gestartet, jedoch ohne ein Fenster/UI anzuzeigen
	 * 
	 * @param delta Verzoegerung zwischen Frames
	 * 
	 * @throws SlickException
	 */
	public void start(int delta) throws SlickException {

		this.input = new TestInput(delta);
		game.update(this, delta);
		//((StateBasedGame)game).initStatesList(this);
		game.init(this);
			
	}
	
	@Override
	public Input getInput() {
		return input;
	}
	
	public TestInput getTestInput(){
		return input;
	}
	
	public void updateGame(int delta) throws SlickException{
		game.update(this, delta);
		game.update(this, delta);// why 2?
	}

}
