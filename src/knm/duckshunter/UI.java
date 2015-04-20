package knm.duckshunter;

import knm.duckshunter.Enemies.Enemies;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;

public class UI {

	private Paint p;
	private MainActivity mainActivity;
	private LvlManager lvlMng;
	private long test;
	private Paint txtPaint;
	private Enemies enemies;
	
	private int yReloadButton;
	private int widthReloadButton;
	private Bitmap bmpReload;
	
	private int xNaboj;
	private int xNabojCurr;
	private int yNaboj;
	private int widthNaboj;
	private int heightNaboj;
	private Bitmap bmpNaboj;
	
	private int xPauseButton;
	private int heightPauseButton;
	private Bitmap bmpPause;
	
	private int xBazooka;
	private int yBazooka;
	private int widthBazooka;
	private int heightBazooka;
	
	public UI(GameView gameView, LvlManager lvlMng, Enemies enemies, MainActivity theGameActivity) {
		this.mainActivity = theGameActivity;
		this.lvlMng = lvlMng;
		this.enemies = enemies;
		p = new Paint();
		p.setARGB(255, 255, 255, 255);
		txtPaint = new Paint();
		txtPaint.setColor(Color.WHITE);
		txtPaint.setTextSize(20 * GameView.getDensity());
		
		yReloadButton = gameView.getHeight() - (int)(gameView.getHeight() * 0.15);
		widthReloadButton = (int)(gameView.getHeight() * 0.15) * 3;
		
		
		yNaboj = yReloadButton + (int)(gameView.getHeight() * 0.03);
		heightNaboj = (int)(gameView.getHeight() * 0.12);
		widthNaboj = (int)(0.3 * heightNaboj);
		xNaboj = gameView.getWidth() - 5 - widthNaboj;
		
		bmpNaboj = BitmapFactory.decodeResource(gameView.getResources(),R.drawable.naboj);
		bmpReload = BitmapFactory.decodeResource(gameView.getResources(),R.drawable.reload);
		bmpPause = BitmapFactory.decodeResource(gameView.getResources(),R.drawable.pause);
		
		xPauseButton = gameView.getWidth() - (int)(gameView.getWidth() * 0.1);
		heightPauseButton = (int)(gameView.getWidth() * 0.1 * 1.22);
		
		xBazooka = widthReloadButton + 10;
		yBazooka = yReloadButton + (int)(0.5 * (GameView.height() - yReloadButton));
		heightBazooka = GameView.height() - yBazooka;
		widthBazooka = heightBazooka;
	}
	
	public void drawUI(Canvas canvas){
		drawReloadButton(canvas);
		drawPauseButton(canvas);
		drawTestValues(canvas);
		drawNaboje(canvas);
		if (lvlMng.getPowUpsBazookas() > 0)
			drawBazookaButton(canvas);
	}
	
	private void drawPauseButton(Canvas canvas){
		Rect destine = new Rect(xPauseButton, 0, GameView.width(), heightPauseButton);
        Rect source = new Rect(0, 0, bmpPause.getWidth(), bmpPause.getHeight());

        canvas.drawBitmap(bmpPause, source, destine, null);
	}
	
	private void drawReloadButton(Canvas canvas){
		Rect destine = new Rect(0, yReloadButton, widthReloadButton, GameView.height());
        Rect source = new Rect(0, 0, bmpReload.getWidth(), bmpReload.getHeight());

        canvas.drawBitmap(bmpReload, source, destine, null);
	}
	
	private void drawTestValues(Canvas canvas){
		String text = lvlMng.getControlText();
		if (!lvlMng.isReloading())
			text += "  nabojow: " + lvlMng.getLoadedBullets();
		else
			text += "  reloading (" + lvlMng.getLoadedBullets() + ")";
		canvas.drawText(text, 10, 50, txtPaint);
		
		text = (1000/(SystemClock.elapsedRealtime() - test)) + "fps   enemies: " + enemies.getEnemiesAmount() 
				+ "  time: " + lvlMng.getTimeLeft() + "  combo: " + lvlMng.getComboKills();
		canvas.drawText(text, 10, 80, txtPaint);
		test = SystemClock.elapsedRealtime();
	}
	
	private void drawNaboje(Canvas canvas){
		xNabojCurr = xNaboj;
		for(int i = 0; i < lvlMng.getLoadedBullets(); i++){
			Rect destine = new Rect(xNabojCurr, yNaboj, xNabojCurr + widthNaboj, yNaboj + heightNaboj);
	        Rect source = new Rect(0, 0, bmpNaboj.getWidth(), bmpNaboj.getHeight());

	        canvas.drawBitmap(bmpNaboj, source, destine, null);
	        
	        xNabojCurr -= (widthNaboj + 5);
		}
	}
	
	private void drawBazookaButton(Canvas canvas){
		Rect destine = new Rect(xBazooka, yBazooka, xBazooka + widthBazooka, yBazooka + heightBazooka);
        //Rect source = new Rect(0, 0, bmpNaboj.getWidth(), bmpNaboj.getHeight());
        
        canvas.drawRect(destine, p);
	}

	public void touch(float x, float y) {
		if (y > yReloadButton && x < widthReloadButton){
			lvlMng.reloadBullets();
		}
	}
	
	public void pauseTouch(float x, float y){
		if (x > xPauseButton && y < heightPauseButton){
			mainActivity.onDialog(true);
		}
	}
}
