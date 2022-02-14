package minilandMayhem.ui;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.OREvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import minilandMayhem.highscore.Highscore;

public class HighScoreState extends BasicGameState {
	
	private int stateID;
	private StateBasedEntityManager entityManager;
	private static String score;
	
	public HighScoreState(int stateID) {
		this.stateID = stateID;
		this.entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		
		Entity background = new Entity("menu");	// Entitaet fuer Hintergrund
    	background.setPosition(new Vector2f(400,300));	// Startposition des Hintergrunds
    	background.addComponent(new ImageRenderComponent(new Image("/assets/background.png")));
    	entityManager.addEntity(stateID, background);
    	
	
		score = Highscore.score();
		
		
		Entity mainmenu = new Entity("zurück");
		mainmenu.setPosition(new Vector2f(170,390));
    	mainmenu.setScale(0.28f);
    	mainmenu.addComponent(new ImageRenderComponent(new Image("assets/entry.png")));
    	
		Event back =new OREvent(new KeyPressedEvent(Input.KEY_ESCAPE),new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent()));
    	back.addAction(new ChangeStateAction(MinilandMayhem.MAINMENUSTATE));
    	mainmenu.addComponent(back);    	
    	entityManager.addEntity(stateID, mainmenu);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		entityManager.renderEntities(gc, game, g);
		if(score!=null) {
			g.drawString(score, 120, 150);
		
		}
		String numbers = "1."+System.lineSeparator()+"2."+System.lineSeparator()+
				"3."+System.lineSeparator()+"4."+System.lineSeparator()+"5.";
		g.drawString(numbers, 100, 150);
		g.drawString("Zurück", 110, 380);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		entityManager.updateEntities(gc, game, delta);
		
	}

	@Override
	public int getID() {
		return stateID;
	}

}
