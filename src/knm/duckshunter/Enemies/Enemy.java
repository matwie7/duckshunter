package knm.duckshunter.Enemies;

import java.util.Random;

import knm.duckshunter.GameView;
import knm.duckshunter.LvlManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Enemy {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int size;
	protected float speed;
	protected boolean alive;
	protected Random rand;
	protected Bitmap bmp;
	protected int currentFrame;
	protected int frameOffset;
	protected boolean framesGrowing = true;
	protected boolean wasKilled = true;				//// jak ucieknie poza ekran to false
	protected boolean directionR;
	protected int bmpWidth;
	protected int HP;
	protected int spawnHeight;
	
	protected Enemies enemies;
	protected LvlManager lvlMng;
	
	public Enemy(Enemies enemies, LvlManager lvlMng, Bitmap bmp, boolean directionR, int spawnHeight) {
		this.rand = new Random();
		this.enemies = enemies;
		this.lvlMng = lvlMng;
		this.spawnHeight = spawnHeight;
		this.bmp = bmp;		
		this.bmpWidth = enemies.getFrameWidth();
		this.directionR = directionR;
		
		relocate();	
	}
	
	private void relocate(){
		this.size = rand.nextInt(4);
		this.width = enemies.getSizeWidth(size);
		this.height = enemies.getSizeHeight(size);
		this.HP = (5 - size) * 100;
		
		this.y = rand.nextInt(spawnHeight);
		
		speed = rand.nextInt(12) + 4;

		if (!directionR)
			speed *= -1;
			
		speed *= enemies.getSpeedMultiplier(size);
		
		if (speed > 0){
			x = 0 - width;
			frameOffset = 30;//15;
		}else{
			x = GameView.width();
			frameOffset = 0;
		}
		alive = true;
		wasKilled = true;
	}
	
	public void move(){
		x += speed;
		
		if (speed > 0 && x > GameView.width()){
			alive = false;
			wasKilled = false;
			if (HP > 0)
				relocate();
		}
		else if (speed < 0 && x + width < 0){
			alive = false;
			wasKilled = false;
			if (HP > 0)
				relocate();
		}
	}
	
	public void draw(Canvas canvas){
		Rect destine = new Rect(x, y, x + width, y + height);

        int sourceX = (frameOffset + currentFrame) * bmpWidth;
        Rect source = new Rect(sourceX, 0, sourceX + bmpWidth, bmp.getHeight());
        canvas.drawBitmap(bmp, source, destine, null);

        if (currentFrame == 29)
        	framesGrowing = false;
        else if(currentFrame == 0)
        	framesGrowing = true;
        
        if (framesGrowing)
        	currentFrame++;
        else
        	currentFrame--;    
	}

	public boolean isAlive() {
		return alive;
	}

	public int getSize() {
		return size;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	public void kill() {
		this.alive = false;
	}
	
	public int getHP(){
		return HP;
	}

	public boolean wasKilled() {
		return wasKilled;
	}
}
