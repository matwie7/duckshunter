package knm.duckshunter.Effects;

import knm.duckshunter.Enemies.Enemy;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Explosion extends VisualEffect {
	private int currentFrame;
	private int bmpWidth;
	private final int FRAMES_COUNT = 21;

	public Explosion(Bitmap bmp, Enemy enemy) {
		super(bmp);
		
		this.width = (int) (enemy.getWidth() * 1.5);
		this.height = this.width;
		
		this.x = (enemy.getX() + (int)(enemy.getWidth() * 0.5)) - (int) (width * 0.5);
		this.y = (enemy.getY() + (int)(enemy.getHeight() * 0.5)) - (int) (height * 0.5);
		
		this.lifeTime = 1500;
		this.currentFrame = 0;
		this.bmpWidth = bmp.getWidth() / FRAMES_COUNT;
	}

	@Override
	public void draw(Canvas canvas) {
		int sourceX = currentFrame++ * bmpWidth;
        Rect source = new Rect(sourceX, 0, sourceX + bmpWidth, bmp.getHeight());
		Rect destine = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, source, destine, null);
	}
}
