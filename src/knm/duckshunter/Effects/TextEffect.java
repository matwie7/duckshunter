package knm.duckshunter.Effects;

import knm.duckshunter.Enemies.Enemy;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

public class TextEffect extends VisualEffect{

	private Paint pText;
	private int transparency;
	private float size;
	private float fY;
	private String mssg;
	
	public TextEffect(Enemy enemy) {
		super(null);
		this.x = enemy.getX() + (int)(0.5 * enemy.getWidth());
		this.fY = enemy.getY() + (float)(0.5 * enemy.getHeight());
		this.size = 30;
		this.transparency = 255;
		this.mssg = "+" + enemy.getHP();
		this.lifeTime = 1500;
		
		pText = new Paint();
		pText.setARGB(transparency, 255, 255, 255);
		pText.setTextSize(this.size); 
		pText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
		pText.setTextAlign(Align.CENTER);
		pText.setAntiAlias(true);
	}

	@Override
	public void draw(Canvas canvas) {
		pText.setTextSize(this.size += 0.6); 
		pText.setARGB(transparency -= 5, 255, 255, 255);
		canvas.drawText(mssg, x, (int)(fY += 0.1) + 7, pText);
	}
}
