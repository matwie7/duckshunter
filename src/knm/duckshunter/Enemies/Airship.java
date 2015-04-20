package knm.duckshunter.Enemies;

import knm.duckshunter.GameView;
import knm.duckshunter.LvlManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Airship extends Enemy {
	
	private Rect destine;
	private Rect source;

	public Airship(GameView gameView, Enemies enemies, LvlManager lvlMng,
			Bitmap bmp, boolean directionR, int spawnHeight) {
		super(enemies, lvlMng, bmp, directionR, spawnHeight);
		
		
		
		this.width = enemies.getSizeWidth(4);
		this.height = (int)(this.width / 2.63);
		this.HP = -1;
		
		if (directionR){
			this.x = 0 - this.width;
			speed = 8;
		}
		else{
			this.x = gameView.getWidth();
			speed = -8;
		}
		
        source = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
	}
	
	@Override
	public void draw(Canvas canvas){
		destine = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, source, destine, null); 
	}
}
