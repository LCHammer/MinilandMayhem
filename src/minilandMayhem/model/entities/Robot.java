package minilandMayhem.model.entities;

import org.newdawn.slick.geom.Vector2f;

import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.action.basicactions.MoveDownAction;
import eea.engine.action.basicactions.MoveUpAction;
import eea.engine.entity.Entity;
import eea.engine.event.Event;
import eea.engine.event.basicevents.LoopEvent;

public abstract class Robot extends Entity{

	protected PhysicsHitbox pHitbox;
	protected boolean looksRight;
	protected boolean isActive;
	protected float speed;
	
	protected LoopEvent fall;
	protected int fallings = 0;
	protected boolean isFalling = false;
	
	protected LoopEvent walkUp;
	protected boolean isWalkingUp=false;
	protected BeamSocket start;
	protected BeamSocket end;
	protected LoopEvent walkDown;
	protected boolean isWalkingDown = false;
	
	
	public Robot(String entityID) {
		super(entityID);
		looksRight = true;
		speed = 0.125f;
		pHitbox = new PhysicsHitbox("Phyisc"+entityID, this.getPosition(), this);
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * Ändert die Blickrichtung dieses Roboters und somit auch die Richtung, in die er sich bewegt.
	 */
	public void changeDirection() { 
		if(!isFalling) {
			if(this.looksRight) {
				//this.removeComponent(imageR);
				//this.addComponent(imageL);
			}else {
				//this.removeComponent(imageL);
				//this.addComponent(imageR);
			}
			looksRight = !looksRight;
			
		}
	}
	
	/**
	 * Zerstört diesen Roboter
	 */
	public void destroy() {
		Event l = new LoopEvent();
		l.addAction(new DestroyEntityAction());
		this.pHitbox.destroy();
		this.addComponent(l);
		
	}
	

	/**
	 * laesst den Roboter fallen. Dabei wird er enstsprechend 1/10 der Erdgravitation beschleunigt.
	 * Um Echtzeitverhalten zu simulieren, werden die aktuellen FPS miteinbezogen
	 */
	public void fall(int fps) {
		isFalling = true;
		/*
		if(this.getIsWalkingUp()) {
			this.setRotation(0);
			this.pHitbox.setRotation(0);
			this.isWalkingUp = false;
			this.walkUp.removeAction(0);
		}
		if(this.getIsWalkingDown()) {
			this.setRotation(0f);
			this.pHitbox.setRotation(0f);
			this.isWalkingDown = false;
			this.walkDown.removeAction(0);	
		}
		*/
		if(isActive) {
		fallings +=1;
		isFalling = true;
		fall.addAction(new MoveDownAction(0.981f/fps));
		}
	}
	
	/**
	 * beendet die Fallbewegung und laesst den Roboter landen
	 */
	public void land() {
		for(int i =0; i<fallings; i++) {
		fall.removeAction(0);
		}
		fallings = 0;
		isFalling = false;
	}
	
	
	
	/**
	 * laesst den uebergebenen Roboter den Stahltraeger hochlaufen
	 * @param b Stahltraeger, den der Roboter hochlaeuft
	 */
	public void walkOnBeam(Beam b) {
		//System.out.println(isFalling);
		if(!getIsWalkingUp() && !getIsWalkingDown()) {
			BeamSocket first = b.getFirst();
			BeamSocket second = b.getSecond();
			if(this.looksRight && first.getPosition().x < second.getPosition().x
					|| !this.looksRight && first.getPosition().x > second.getPosition().x) {
				this.start=first;
				this.end=second;
			}else {
				this.start = second;
				this.end = first;
			}
			this.setRotation(b.getRotation());
			this.pHitbox.setRotation(b.getRotation());
			double angle =Math.toRadians(Math.abs((double)b.getRotation()));
			double up_distance = Math.tan(angle)*(double)speed;
			
			if(this.getLooksRight() == b.getUpRight()) {
				this.walkUp.addAction(new MoveUpAction((float)up_distance));
				this.isWalkingUp =true;
			}else {
				this.walkDown.addAction(new MoveDownAction((float)up_distance));
				this.isWalkingDown=true;
				//this.setPosition(new Vector2f(this.getPosition().x,this.getPosition().y+5));
			}
			
		}
	}
	
	
	/**
	 * beendet das Hochlaufen auf einem Stahlträger.
	 * @param Sockel, an dem der Roboter sein Hochlaufen beendet
	 */
	public void endWalkingUp(BeamSocket socket) {
		
		Vector2f socketpos = socket.getPosition();
		Vector2f pos = this.getPosition();
	
		if(socket.equals(end) && pos.y < socketpos.y-50) {
			this.setRotation(0f);
			this.pHitbox.setRotation(0f);
			this.isWalkingUp=false;
			this.walkUp.removeAction(0);
			this.end=null;
			this.start=null;
		}
	}
	
	/**
	 * beendet das Herunterlaufen auf Stahlträgern
	 * @param s Endsockel des Herunterlaufens (=das Ziel des Marios)
	 */
	public void endWalkingDown(BeamSocket s) {
		if(s.equals(end) && s.getPosition().y -55 < this.getPosition().y) {
			this.setRotation(0f);
			this.pHitbox.setRotation(0f);
			this.isWalkingDown=false;
			this.walkDown.removeAction(0);
			this.smoothLanding();
			this.end=null;
			this.start=null;
		}
	}
	

	
	//TODO: nur ausführen, wenn Wall unter den füßen/Beam mit rotation 0/ sockel
	public void smoothLanding() {
		float height = this.getPosition().y;
		double smoothHeight = Math.round(height/50)*50;
		this.setPosition(new Vector2f(this.getPosition().x,(float)smoothHeight));
		
	}
	
	

	/**
	 * stoppt das Hoch-/Runterlaufen auf einem Träger. 
	 * Wird aufgerufen, wenn der Stahlträger, auf dem der Roboter laeuft, abgebaut wird.
	 */
	public void stopWalkOnBeam() {
		
		if(this.getIsWalkingUp()) {	
			this.setRotation(0);
			this.pHitbox.setRotation(0);
			this.isWalkingUp = false;
			this.walkUp.removeAction(0);
		}
		
		if(this.getIsWalkingDown()) {
			this.setRotation(0f);
			this.pHitbox.setRotation(0f);
			this.isWalkingDown = false;
			this.walkDown.removeAction(0);
		}
		this.isFalling=true;
		this.end=null;
		this.start=null;
	}
	
	
	
	
	
	/**
	 * 
	 * @return true wenn dieser Roboter nach rechts schaut und somit auch nach rechts läuft.
	 *		   Schaut er nach links, wird false zurückgegeben.
	 */
	public boolean getLooksRight() {
		return looksRight;
	}
	
	
	/**
	 * 
	 * @return die PhysicsHitbox von diesem Roboter
	 */
	public PhysicsHitbox getHitbox() {
		return this.pHitbox;
	}
	
	
	/**
	 * 
	 * @return true wenn der Mario gerade faellt, sonst false.
	 */
	public boolean getFalling() {
		return isFalling;
	}
	
	
	/**
	 * 
	 * @return true wenn dieser Roboter bereits aktiviert wurde, sonst false
	 */
	public boolean getIsActive() {
		return this.isActive;
	}
	

	/**
	 * 
	 * @return true, wenn der Roboter gerade einen Stahltraeger hochlaeuft, sonst false. 
	 */
	public boolean getIsWalkingUp() {
		return this.isWalkingUp;
	}
	
	/**
	 * 
	 * @return startsockel des Hochlaufens
	 */
	public BeamSocket getStart() {
		return this.start;
	}
	
	/**
	 * 
	 * @return Endsockel des Hochlaufens
	 */
	public BeamSocket getEnd() {
		return this.end;
	}
	
	/**
	 * 
	 * @return true, wenn der Roboter aktuell herunterlaeuft
	 */
	public boolean getIsWalkingDown() {
		return this.isWalkingDown;
	}
	
	/**
	 * 
	 * @return true, wenn der Roboter aktuell hoch- oder runterlaeuft, sonst false.
	 */
	public boolean getWalkingUpDown() {
		return this.getIsWalkingDown() || this.getIsWalkingUp();
	}
	
	
	
}
