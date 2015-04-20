package knm.duckshunter.Effects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class VisualEffect {

	protected int x;
	protected int y;
	protected int height;
	protected int width;
	protected long createTime;
	protected int lifeTime;
	protected Bitmap bmp;
	protected boolean present;
	
	public VisualEffect(Bitmap bmp) {
		this.bmp = bmp;
		this.present = true;
		this.createTime = System.currentTimeMillis();
	}
	
	abstract public void draw(Canvas canvas);
	
	public boolean isActive(){
		return System.currentTimeMillis() - createTime < lifeTime;
	}

}
