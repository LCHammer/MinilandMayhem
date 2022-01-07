package minilandMayhem.model.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import minilandMayhem.ui.GamePlayState;

public class BeamSocket extends Entity{

	public boolean hasBeam = false;
	
	public BeamSocket(String entityID) {
		super(entityID);
		
		try {
			this.addComponent(new ImageRenderComponent(new Image("/assets/Wall.png")));
		}
		catch(SlickException e) {
			System.out.println("Sockelbild konnte nicht geladen werden");
		}
		
		
		ANDEvent e = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		e.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				BeamSocket thisSocket = (BeamSocket) event.getOwnerEntity();
				//if(!thisSocket.hasBeam) {
					if( GamePlayState.selectedSocket == null) {
						GamePlayState.selectedSocket = thisSocket;
						System.out.println("socket set");
					}
					else if(!GamePlayState.selectedSocket.equals(thisSocket)) {
						if(game.getCurrentState() instanceof GamePlayState) {
						GamePlayState g = (GamePlayState) game.getCurrentState();
						g.createBeam(thisSocket,GamePlayState.selectedSocket);
						}else {
							System.out.println("nö");
						}
						
						GamePlayState.selectedSocket.hasBeam = true;
						GamePlayState.selectedSocket=null;
						thisSocket.hasBeam=true;
					
					}
					else
					{
						System.out.println("gleicher sockel");
						GamePlayState.selectedSocket = null;
					
					}
				//}
				//else {
					//System.out.println("TODO: remove beam");
			//	}
			}
			
		});
		this.addComponent(e);
		
	}

}
