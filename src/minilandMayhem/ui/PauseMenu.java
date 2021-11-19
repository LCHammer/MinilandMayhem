package minilandMayhem.ui;



import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.action.basicactions.QuitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class PauseMenu extends BasicGameState {
	
	private int stateID;
	private StateBasedEntityManager entityManager;
	
	  //werte aus DOW, ändern!
    private final int distance = 100;
    private final int start_Position = 180;
	
	public PauseMenu(int stateID) {
		this.stateID = stateID;
		this.entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
		// Hintergrund laden
    	Entity background = new Entity("menu");	// Entitaet fuer Hintergrund
    	background.setPosition(new Vector2f(400,300));	// Startposition des Hintergrunds
    	background.addComponent(new ImageRenderComponent(new Image("/assets/background.png"))); // Bildkomponente
    	
    	entityManager.addEntity(stateID, background);
		
		Entity resume = new Entity("resume");
		resume.setPosition(new Vector2f(218, 190));
		resume.setScale(0.28f);
		resume.addComponent(new ImageRenderComponent(new Image("assets/entry.png")));
		ANDEvent resume_event = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		
		Action a = new ChangeStateInitAction(MinilandMayhem.GAMEPLAYSTATE);
		resume_event.addAction(a);
		resume.addComponent(resume_event);
		entityManager.addEntity(stateID, resume);
		
		Entity main = new Entity("Hauptmenü");
		main.setPosition(new Vector2f(218, 290));
		main.setScale(0.28f);
		main.addComponent(new ImageRenderComponent(new Image("assets/entry.png")));
		ANDEvent main_event = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		Action main_act = new ChangeStateInitAction(MinilandMayhem.MAINMENUSTATE);
		main_event.addAction(main_act);
		main.addComponent(main_event);
		entityManager.addEntity(stateID, main);
		
		Entity end = new Entity("Beenden");
		end.setPosition(new Vector2f(218,390));
		end.setScale(0.28f);
		end.addComponent(new ImageRenderComponent(new Image("assets/entry.png")));
		ANDEvent end_event = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		Action end_act = new QuitAction();
		end_event.addAction(end_act);
		end.addComponent(end_event);
		entityManager.addEntity(stateID, end);
		
		
		
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		entityManager.renderEntities(container, game, g);
		
		int counter = 0;
		
		g.drawString("Weiter", 110, start_Position+counter*distance); counter++;
		g.drawString("Hauptmenü", 110, start_Position+counter*distance); counter++;
		g.drawString("Beenden", 110, start_Position+counter*distance); counter++;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		entityManager.updateEntities(container, game, delta);
		
	}

	@Override
	public int getID() {
		return stateID;
	}

}
