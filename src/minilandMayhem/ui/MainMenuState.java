package minilandMayhem.ui;

import java.io.File;

import javax.swing.JFileChooser;

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
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import minilandMayhem.highscore.Highscore;
import minilandMayhem.model.mapParser.Parser;


public class MainMenuState extends BasicGameState{
	
	private int stateID = 0;
    private StateBasedEntityManager entityManager;
    
    private final int distance = 100;
    private final int start_Position = 180;
	
	public MainMenuState(int stateID) {
		this.stateID = stateID;
		this.entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// Hintergrund laden
    	Entity background = new Entity("menu");	
    	background.setPosition(new Vector2f(400,300));	
    	background.addComponent(new ImageRenderComponent(new Image("/assets/mainbackground.png"))); 
    	entityManager.addEntity(stateID, background);
    	
    	//Spiel starten
    	String new_Game = "Neues Spiel starten";
    	Entity new_Game_Entity = new Entity(new_Game);
    	new_Game_Entity.setPosition(new Vector2f(170, 190));
    	new_Game_Entity.setScale(0.28f);
    	new_Game_Entity.addComponent(new ImageRenderComponent(new Image("assets/entry.png")));
    	
    	ANDEvent newGameEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action new_Game_Action = new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				if(Parser.map == null || (Parser.map != null && Parser.check())) {
					Action a = new ChangeStateInitAction(MinilandMayhem.GAMEPLAYSTATE);
					a.update(gc, game, delta, newGameEvent);
					
				}else {
					System.out.println("Das gewählte Level ist nicht rechteckig!");
				}
			}
    		
    	};
    	newGameEvent.addAction(new_Game_Action);
    	new_Game_Entity.addComponent(newGameEvent);
    	entityManager.addEntity(this.stateID, new_Game_Entity);
    	
    	//Spiel Beenden
    	Entity quit_Entity = new Entity("Beenden");
    	quit_Entity.setPosition(new Vector2f(170, 490));
    	quit_Entity.setScale(0.28f);
    	quit_Entity.addComponent(new ImageRenderComponent(new Image("assets/entry.png")));
    	ANDEvent quitEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action quit_Action = new QuitAction();
    	quitEvent.addAction(quit_Action);
    	quit_Entity.addComponent(quitEvent);
    	entityManager.addEntity(this.stateID, quit_Entity);
    	
    	
    	
    	
    	//Level waehlen
    	Entity choose_level = new Entity("Choose_Level");
    	
    	choose_level.setPosition(new Vector2f(170,290));
    	choose_level.setScale(0.28f);
    	choose_level.addComponent(new ImageRenderComponent(new Image("assets/entry.png")));
    	
    	ANDEvent choose = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action choose_a = new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(null);
				File file = fc.getSelectedFile();
				Parser.readMap(file);
			}
    		
    	};
    	choose.addAction(choose_a);
    	choose_level.addComponent(choose);
    	entityManager.addEntity(this.stateID, choose_level);
		
    	
    	
    	//Highscore anzeigen
    	Entity highscore = new Entity("Highscore");
    	highscore.setPosition(new Vector2f(170,390));
    	highscore.setScale(0.28f);
    	highscore.addComponent(new ImageRenderComponent(new Image("assets/entry.png")));
    	ANDEvent score = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	
    	score.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(null);
				File file = fc.getSelectedFile();
				if(file != null && Highscore.readFile(file)) { 
					Action a = new ChangeStateInitAction(MinilandMayhem.HIGHSCORESTATE);
					a.update(gc, game, delta, event);
				}else {
					System.out.println("Keine Datei ausgewählt oder Datei hat falsches Format");
				}
			}
    		
    	});
    	
    	highscore.addComponent(score);
    	entityManager.addEntity(stateID, highscore);	
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		entityManager.renderEntities(container, game, g);
		
		int counter = 0;
		
		g.drawString("Neues Spiel", 110, start_Position+counter*distance); counter++;
		g.drawString("Level: "+ Parser.levelname, 110,start_Position+counter*distance);counter++;
		g.drawString("Highscore", 110, start_Position+counter*distance); counter++;
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
