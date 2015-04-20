package knm.duckshunter.Enemies;

import knm.duckshunter.GameView;
import knm.duckshunter.LvlManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class VladRocket extends Enemy {

	public VladRocket(GameView gameView, Enemies enemies, LvlManager lvlMng,
			Bitmap bmp, boolean richtungR, int spawnHeight) {
		super(enemies, lvlMng, bmp, richtungR, spawnHeight);
		
		bmpWidth = bmp.getWidth() / 3;
		
		this.width = enemies.getSizeWidth(4);
		this.height = (int)(this.width / 4.86);
		this.HP = 0;
		
		if(richtungR){
			x = 0 - width;
			speed = 16;
		}
		else {
			x = gameView.getWidth();
			speed = -16;
		}
	}
	
	@Override
	public void draw(Canvas canvas){
		Rect destine = new Rect(x, y, x + width, y + height);
        int sourceX = (currentFrame++ % 3) * bmpWidth;
        Rect source = new Rect(sourceX, 0, sourceX + bmpWidth, bmp.getHeight());
        canvas.drawBitmap(bmp, source, destine, null);  
	}
}
