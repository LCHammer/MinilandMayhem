package minilandMayhem.render;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.component.RenderComponent;
import eea.engine.entity.StateBasedEntityManager;

public class MyAnimationRenderComponent extends RenderComponent{
	
	private Vector2f size;
	protected Animation animation;

	public MyAnimationRenderComponent(Image[] frames, float speed ,float width, float height, boolean looping) {
		super("AnimationRenderComponent");
		this.size = new Vector2f(width, height);
		animation = new Animation(frames, 1);
		animation.setSpeed(speed);
		animation.setLooping(looping);
		animation.start();
		
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		if(animation.isStopped()) StateBasedEntityManager.getInstance().removeEntity(sb.getCurrentStateID(), getOwnerEntity());
	}

	@Override
	public Vector2f getSize() {
		return size;
	}

	
	
	//die "normale" AnimationrenderComponent setzt die obere linke ecke der Bilder in die Mitte der entsprechenden Entity.
	//deshalb wurde eine kleine Änderung vorgenommen, damit die Mitte der Bilder (frames) mit der Mitte der Entity uebereinstimmt 
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		if(!animation.isStopped()) {
			float x = getOwnerEntity().getPosition().x;
			float y = getOwnerEntity().getPosition().y;			
			animation.draw(x-(size.x/2), y-(size.y/2), size.x, size.y);
		}
	}
	
	

}
