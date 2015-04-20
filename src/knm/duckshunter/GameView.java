package knm.duckshunter;

import knm.duckshunter.Effects.Effects;
import knm.duckshunter.Enemies.Enemies;
import knm.duckshunter.PowerUps.PowerUps;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
	private SurfaceHolder surfaceHolder;
	private GameDrawingLoopThread theGameDrawingLoopThread;
	private GameLoopThread theGameLoopThread;
	private boolean initialize = true;
	private static float density;

	private MainActivity theGameActivity = new MainActivity();
	private LvlManager lvlMng;
	private Enemies enemies;
	private UI ui;
	private Effects effects;
	private GameEventsHandler events;
	private PowerUps powerUps;
	
	private Bitmap bmpBg;
	private Rect destine;
    private Rect source;
    
    private static int height;
    private static int width;

	public GameView(Context context) {
		super(context);
		theGameActivity = (MainActivity) context;
		effects = new Effects(theGameActivity, theGameActivity, this);
		Intent intent = theGameActivity.getIntent();
		lvlMng = new LvlManager(getDensity(), theGameActivity, effects, intent.getIntExtra("mode", 0));	
		bmpBg = BitmapFactory.decodeResource(getResources(),R.drawable.test_bg);
		theGameDrawingLoopThread = new GameDrawingLoopThread(this);
		theGameLoopThread = new GameLoopThread(this);
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(new SurfaceHolder.Callback() {

			public void surfaceDestroyed(SurfaceHolder holder) {
				theGameDrawingLoopThread.setRunning(false);
				theGameLoopThread.setRunning(false);
				boolean retry = true;
				while (retry) {
					try {
						theGameDrawingLoopThread.interrupt();
						theGameDrawingLoopThread.join();
						theGameLoopThread.interrupt();
						theGameLoopThread.join();
						retry = false;
					} catch (InterruptedException e) {}
				}
			}

			public void surfaceCreated(SurfaceHolder holder) {
				gameLoop();
				theGameDrawingLoopThread.setRunning(true);
				if (theGameDrawingLoopThread.getState() == theGameDrawingLoopThread
						.getState().NEW) {
					theGameDrawingLoopThread.start();
				}
				theGameLoopThread.setRunning(true);
				if (theGameLoopThread.getState() == theGameLoopThread
						.getState().NEW) {
					theGameLoopThread.start();
				}
			}

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}
		});
	}

	private void initialize() {
		density = getResources().getDisplayMetrics().density;
		height = this.getHeight();
		width = this.getWidth();
		powerUps = new PowerUps(lvlMng, this);
		enemies = new Enemies(this, lvlMng, effects, powerUps);
		ui = new UI(this, lvlMng, enemies, theGameActivity);
		events = new GameEventsHandler(ui, lvlMng, enemies, effects, theGameActivity, powerUps);
		destine = new Rect(0, 0, getWidth(), getHeight());
		source = new Rect(0, 0, bmpBg.getWidth(), bmpBg.getHeight());

		initialize = false;
	}

	public void gameLoop() {
		long time = System.currentTimeMillis();
		if (initialize)
			initialize();
		
		events.doEvents();
		enemies.calculateEnemies();
		effects.calculateEffects();
		lvlMng.manageLvl();
		powerUps.calculatePowerUps();
		Log.i("performance", "gameLoop czas: " + (System.currentTimeMillis() - time));
	}

	public void draw(Canvas canvas) {
		long time = System.currentTimeMillis();
        canvas.drawBitmap(bmpBg, source, destine, null);
		
        enemies.drawEnemyObjects(canvas);
		effects.drawEffects(canvas);
		powerUps.drawPowerUps(canvas);
		ui.drawUI(canvas);
		Log.i("performance", "draowingLoop czas: " + (System.currentTimeMillis() - time));
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
			synchronized (getHolder()) {
				ui.pauseTouch(e.getX(), e.getY());
				events.addEvent(e.getX(), e.getY());
			}
		} 		
//		else if (e.getAction() == MotionEvent.ACTION_UP) {
//			synchronized (getHolder()) {
//			}
//		} else if (e.getAction() == MotionEvent.ACTION_MOVE) {
//			synchronized (getHolder()) {
//			}
//		}
		return true;
	}

	public static float getDensity() {
		return density;
	}
	
	public static int width(){
		return width;
	}
	
	public static int height(){
		return height;
	}

	public void pauseThread() {
		theGameDrawingLoopThread.setRunning(false);
		theGameLoopThread.setRunning(false);
	}

	public void resumeThread() {
		theGameDrawingLoopThread = new GameDrawingLoopThread(this);
		theGameDrawingLoopThread.setRunning(true);
		theGameDrawingLoopThread.start();
		theGameLoopThread = new GameLoopThread(this);
		theGameLoopThread.setRunning(true);
		theGameLoopThread.start();
		lvlMng.updateSharedData(false);
		effects.reloadSettings();
	}

	public void gameOver() {
		theGameActivity.onGameOver();
	}
}
