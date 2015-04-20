package knm.duckshunter.Effects;

import knm.duckshunter.GameView;
import knm.duckshunter.Enemies.Enemy;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class BulletHits extends VisualEffect {

	public BulletHits(GameView gameView, Bitmap bmp, Enemy enemy) {
		super(bmp);
		this.x = enemy.getX() + (int)(enemy.getWidth() * 0.25);
		this.y = enemy.getY() + (int)(enemy.getHeight() * 0.25);
		this.height = enemy.getHeight();
		this.width = this.height;
		this.lifeTime = 300;
	}
	
	@Override
	public void draw(Canvas canvas){
		Rect destine = new Rect(x, y, x + width, y + height);
        Rect source = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        canvas.drawBitmap(bmp, source, destine, null);
	}

}
