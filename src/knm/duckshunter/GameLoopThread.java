package knm.duckshunter;


public class GameLoopThread extends Thread 
{
	static final long FPS = 30;
	private GameView theView;
	private boolean isRunning = false;
	
	public GameLoopThread(GameView theView) {
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
            
            startTime = System.currentTimeMillis();
            try {
                synchronized (theView.getHolder()) {
                	theView.gameLoop();
                }
            }catch (Exception e){}
            
            sleepTime = TPS - (System.currentTimeMillis() - startTime);
            try {
            	if (sleepTime > 0)
            		sleep(sleepTime);
            }catch (Exception e)
            {} 
        }
     }
}
