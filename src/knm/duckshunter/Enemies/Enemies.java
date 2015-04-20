package knm.duckshunter.Enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import knm.duckshunter.GameView;
import knm.duckshunter.LvlManager;
import knm.duckshunter.R;
import knm.duckshunter.Effects.Effects;
import knm.duckshunter.PowerUps.PowerUps;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Enemies {

	private LvlManager lvlMng;
	private GameView gameView;
	private Effects effects;
	private PowerUps powUps;
	private long lastBorn;
	private int delay;
	private Bitmap bmpSheet;
	private Bitmap bmpRocketL;
	private Bitmap bmpRocketP;
	private Bitmap bmpAirshipL;
	private Bitmap bmpAirshipP;
	private long lastRocketSpownTime;
	private long lastAirshipSpownTime;
	private long lastKill;
	private int rocketDelay;
	private Random rand;
	private int spawnHeight;
	private int frameWidth;
	private int[] sizeWidth;
	private int[] sizeHeight;
	private double[] speedMultiplier;
	private int airshipDelay;
	
	private List<Enemy> enemies = new ArrayList<Enemy>();
	
	public Enemies(GameView gameView, LvlManager lvlMng, Effects effects, PowerUps powUps){
		this.gameView = gameView;
		this.lvlMng = lvlMng;
		this.effects = effects;
		this.rand = new Random();
		this.spawnHeight = (int)(gameView.getHeight() * 0.66);
		this.powUps = powUps;
		
		BitmapFactory.Options opts = new BitmapFactory.Options();
	    opts.inPreferredConfig = Config.RGB_565;
		bmpSheet = BitmapFactory.decodeResource(gameView.getResources(),R.drawable.sheet, opts);
		bmpAirshipL = BitmapFactory.decodeResource(gameView.getResources(),R.drawable.sterowiec_l, opts);
		bmpAirshipP = BitmapFactory.decodeResource(gameView.getResources(),R.drawable.sterowiec_p, opts);
		bmpRocketL = BitmapFactory.decodeResource(gameView.getResources(),R.drawable.rocket_l, opts);
		bmpRocketP = BitmapFactory.decodeResource(gameView.getResources(),R.drawable.rocket_p, opts);
		
		sizeWidth = new int[5];
		sizeHeight = new int[4];
		speedMultiplier = new double[4];
		sizeWidth[0] = (int) (gameView.getHeight() * 0.09);
		sizeWidth[1] = (int) (gameView.getHeight() * 0.15);
		sizeWidth[2] = (int) (gameView.getHeight() * 0.2);
		sizeWidth[3] = (int) (gameView.getHeight() * 0.25);
		sizeWidth[4] = (int) (gameView.getHeight() * 0.5);
		
		for(int i = 0; i < 4; i++){
			sizeHeight[i] = (int)(sizeWidth[i] / 1.35);
			speedMultiplier[i] = ((i + 1d) * 0.3);
		}
				
		frameWidth = bmpSheet.getWidth() / 60;
		lastRocketSpownTime = System.currentTimeMillis();	
		rocketDelay = rand.nextInt(30000) + 10000;
		lastAirshipSpownTime = System.currentTimeMillis();	
		airshipDelay = rand.nextInt(15000) + 5000;
	}
	
	public int getFrameWidth() {
		return frameWidth;
	}
	
	public double getSpeedMultiplier(int size) {
		return speedMultiplier[size];
	}
	
	public int getSizeWidth(int size) {
		return sizeWidth[size];
	}
	
	public int getSizeHeight(int size) {
		return sizeHeight[size];
	}
	
	public void drawEnemyObjects(Canvas canvas) {
		for(Enemy enemy: enemies)
			enemy.draw(canvas);
	}
	
	public void calculateEnemies(){
		if (delay < System.currentTimeMillis() - lastBorn && enemies.size() < 10){	// new bird
			if (rand.nextBoolean())
				enemies.add(new Enemy(this, lvlMng, bmpSheet, true, spawnHeight));
			else
				enemies.add(new Enemy(this, lvlMng, bmpSheet, false, spawnHeight));
			lastBorn = System.currentTimeMillis();
			delay = (int)((Math.random() * 1000) + 100);
		}
		
		if (System.currentTimeMillis() - lastRocketSpownTime > rocketDelay){		//new VladimirRocket
			if (rand.nextBoolean())
				enemies.add(new VladRocket(gameView, this, lvlMng, bmpRocketP, true, spawnHeight));
			else
				enemies.add(new VladRocket(gameView, this, lvlMng, bmpRocketL, false, spawnHeight));
			
			lastRocketSpownTime = System.currentTimeMillis();	
			rocketDelay = rand.nextInt(50000) + 40000;
		}
		
		if (System.currentTimeMillis() - lastAirshipSpownTime > airshipDelay){		//new Airship
			if (rand.nextBoolean())
				enemies.add(new Airship(gameView, this, lvlMng, bmpAirshipP, true, spawnHeight));
			else 
				enemies.add(new Airship(gameView, this, lvlMng, bmpAirshipL, false, spawnHeight));
			
			lastAirshipSpownTime = System.currentTimeMillis();	
			airshipDelay = rand.nextInt(15000) + 20000;
		}
		
		for(Enemy enemy: enemies){
			if (enemy.isAlive())
				enemy.move();
			else {
				if(enemy.wasKilled()){
					if (System.currentTimeMillis() - lastKill < 200 && lastKill != 0)
						lvlMng.incrementComboKills();
					else
						lvlMng.clearComboKills();
					effects.addExplosion(enemy);
					effects.addTextScore(enemy);
					lastKill = System.currentTimeMillis();
					if (rand.nextInt(100) % 2 == 0){
						powUps.createPowerUp(enemy);
					}
				}
				enemies.remove(enemy);
			}
		}
	}
	
	public int getEnemiesAmount(){
		return enemies.size();
	}

	public Enemy getEnemy(int index) {
		return enemies.get(index);
	}

	public void touch(float x, float y) {
		for (Enemy enemy: enemies)
			if (collision(enemy, x, y) && enemy.getHP() > 0)
				killEnemy(enemy);
			else if(collision(enemy, x, y) && enemy.getHP() == 0)
				rocketDestroyed(enemy);
			else if(collision(enemy, x, y))
				gameView.gameOver();
		
		lvlMng.shot();
	}
	

//	private boolean collision(Enemy enemy, float x, float y){
//		return (Math.sqrt((((enemy.getX()) + (enemy.getWidth() * 0.5)) - x) 
//				* (((enemy.getX()) + (enemy.getWidth() * 0.5)) - x)
//				+  (((enemy.getY()) + (enemy.getHeight() * 0.5)) - y) 
//				* (((enemy.getY()) + (enemy.getHeight() * 0.5)) - y)) 
//				< (enemy.getHeight() * 0.5) + 10);
//	}
	
	private boolean collision(Enemy enemy, float x, float y){
		return (x > enemy.getX() && x < enemy.getX() + enemy.getWidth()
				&& y > enemy.getY() && y < enemy.getY() + enemy.getHeight());
	}
	
	private void killEnemy(Enemy enemy) {
		lvlMng.addScore(enemy.getHP());
		effects.playGolomb();
		enemy.kill();	
	}
	
	private void rocketDestroyed(Enemy enemy){
		effects.addExplosion(enemy);
		for (Enemy killEnemy: enemies){
			killEnemy.kill();
			lvlMng.addScore(killEnemy.HP);
		}	
		delay = 1500;
	}
}
