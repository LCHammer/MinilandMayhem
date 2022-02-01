package minilandMayhem.model.entities;

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import minilandMayhem.ui.GamePlayState;
import minilandMayhem.ui.MinilandMayhem;

public class BeamSocket extends Entity{

	private LinkedList<Beam> beams = new LinkedList<Beam>();
	
	public BeamSocket(String entityID) {
		super(entityID);
		
		try {
			this.addComponent(new ImageRenderComponent(new Image("/assets/Socket.png")));
		}
		catch(SlickException e) {
			System.out.println("Sockelbild konnte nicht geladen werden");
		}
		this.setSize(new Vector2f(48,48));
		this.setRotation(90f);
		
		ANDEvent e = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		e.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				BeamSocket thisSocket = (BeamSocket) event.getOwnerEntity();
					
				if(game.getCurrentState() instanceof GamePlayState) {
					GamePlayState g = (GamePlayState) game.getCurrentState();
					
				
					if( GamePlayState.selectedSocket == null) {
						//Es wurde vorher noch kein anderer Sockel ausgewählt
						GamePlayState.selectedSocket = thisSocket;
						thisSocket.setRotation(180f);
					}
					else if(!GamePlayState.selectedSocket.equals(thisSocket)) {
						//Es wurden auf 2 unterschiedliche Sockel geklickt -> baue Traeger
						g.createBeam(thisSocket,GamePlayState.selectedSocket);
						GamePlayState.selectedSocket.setRotation(90f);
						thisSocket.setRotation(90f);
						GamePlayState.selectedSocket=null;
						
					}
					else
					{
						//es wurde 2 mal auf den selben Sockel geklickt -> entferne alle Traeger
						thisSocket.removeBeams(thisSocket.beams);
						GamePlayState.selectedSocket.setRotation(90f);
						GamePlayState.selectedSocket = null;
						g.dropRobotFromBeams(thisSocket);
					}
				}
			
			}	
		});
		this.addComponent(e);
		
	}
	
	
	/**
	 * zerstoert alle Stahltraeger, die in der uebergebenen Liste sind und erstattet Ressourcen für jeden zerstoerten Traeger.
	 * @param beamList zu zuerstoerende Stahltraeger
	 */
	public void removeBeams(LinkedList<Beam> beamList) {
		int pre = beamList.size();
		for (int i =0; i<beamList.size();) {
			Beam b = beamList.get(i);
			b.getFirst().removeSingleBeam(b);
			b.getSecond().removeSingleBeam(b);
			StateBasedEntityManager.getInstance().removeEntity(MinilandMayhem.GAMEPLAYSTATE, b);
		}
		int post = beamList.size();
		GamePlayState.ressources+=(pre-post);
	}
	
	
	/**
	 * fuegt diesem Sockel alle Stahltraeger der uebergebenen Liste hinzu
	 * @param beamList Liste von Stahltraegern, die an diesen Sockel gebaut sind
	 */
	public void addBeams(LinkedList<Beam> beamList) {
		beams.addAll(beamList);
	}
	
	
	/**
	 * entfernt einen einzelnen Stahltraeger aus der Liste. 
	 * Wird aufgerufen, wenn ein anderer Sockel seine Traeger entfernt und Bestandteile davon an diesen Sockel angebaut sind. 
	 * @param b zu erntfernender Traeger.
	 */
	public void removeSingleBeam(Beam b) {
		this.beams.remove(b);
	}

}
