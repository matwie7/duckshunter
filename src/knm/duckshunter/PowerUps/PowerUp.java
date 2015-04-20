package knm.duckshunter.PowerUps;

import knm.duckshunter.GameView;
import knm.duckshunter.LvlManager;
import knm.duckshunter.Enemies.Enemy;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

public class PowerUp {

	protected double x;
	protected double y;
	protected Paint pFill, pText;
	protected boolean active;
	protected LvlManager lvlMng;
	protected double radius;
	
	public PowerUp(Enemy enemy, LvlManager lvlMng) {
		this.x = enemy.getX() + (0.5 * enemy.getWidth() * 0.8);
		this.y = enemy.getY() + enemy.getWidth();
		this.active = true;
		this.lvlMng = lvlMng;
		this.radius = GameView.height() * 0.04;
		
		pText = new Paint(); 
		pText.setColor(Color.WHITE); 
		pText.setTextSize(20); 
		pText.setTextAlign(Align.CENTER);
		
		pFill = new Paint();
	    pFill.setStyle(Paint.Style.FILL);
	    pFill.setARGB(100, 0, 200, 0);
	}

	public void draw(Canvas canvas) {
		canvas.drawCircle((int)x, (int)y, (int)radius, pFill);
		canvas.drawText("+10$", (int)x, (int)y + 7, pText);
	}
	
	public void move(){
		y += lvlMng.getPowerUpsSpeed();
	}
	
	public boolean ifActive(){
		return this.active;
	}

	public void checkIfDestroyed() {
		if (this.y > GameView.height())
			this.active = false;
	}

	public boolean destroyed() {
		return !this.active;
	}

	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getRadius() {
		return this.radius;
	}

	public void destroy() {
		active = false;
	}
}
