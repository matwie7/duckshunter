package knm.duckshunter;

import android.graphics.Canvas;

public class GameDrawingLoopThread extends Thread 
{
	static final long FPS = 30;
	private GameView theView;
	private boolean isRunning = false;
	
	public GameDrawingLoopThread(GameView theView) {
        this.theView = theView;
    }
	
	public void setRunning(boolean run) {
        isRunning = run;
    }
	
	@Override
    public void run() {
		long TPS = 1000 / FPS;
		long startTime, sleepTime;
        while (isRunning) {
            Canvas theCanvas = null;
            startTime = System.currentTimeMillis();
            try {
                theCanvas = theView.getHolder().lockCanvas();
                synchronized (theView.getHolder()) {
                    theView.draw(theCanvas);
                }
            } finally {
                if (theCanvas != null) {
                    theView.getHolder().unlockCanvasAndPost(theCanvas);
                }
            }
            sleepTime = TPS - (System.currentTimeMillis() - startTime);
            try {
            	if (sleepTime > 0)
            		sleep(sleepTime);
            	else
            		sleep(10);
            }catch (Exception e)
            {}       
        }
    }
}
