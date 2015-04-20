package knm.duckshunter;

import knm.duckshunter.Effects.Effects;
import android.content.Context;
import android.content.SharedPreferences;

public class LvlManager {
	private long score;
	private long neededScore;
	private int level;
	private int money;
	private int loadedBullets;
	private long reloadingStartTime;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private Context context;
	private boolean reloading;
	private Effects effects;
	private int addNextBulletIn;
	private long startTime;
	private int gameTime;
	private int mode;
	private int comboKills = 1;
	private int powerUpsSpeed;
	private int puBazooka;

	public LvlManager(float density, Context context, Effects effects, int mode) {
		this.context = context;
		preferences = context.getSharedPreferences("SAVE_STATE",
				Context.MODE_WORLD_WRITEABLE);
		editor = preferences.edit();
		this.score = 0;
		this.loadedBullets = 8;
		this.effects = effects;
		this.level = 0;
		this.neededScore = 0;
		reloading = false;
		updateSharedData(false);
		gameTime = 15000;
		this.mode = mode;
		this.powerUpsSpeed = 8;
		this.puBazooka = 0;
	}

	public void manageLvl() {
		if (reloading) {
			if (System.currentTimeMillis() - reloadingStartTime > 1000) {
				loadedBullets = 8;
				reloading = false;
			} 
			else if (System.currentTimeMillis() - reloadingStartTime >= addNextBulletIn) {
				loadedBullets++;
				addNextBulletIn += 124;
			}
		}
		
		if(neededScore <= score)
			nextLevel();
		else if(startTime + gameTime < System.currentTimeMillis()){
			((MainActivity) context).onGameOver();
		}
	}

	public void addScore(int points) {
		this.score += points;
	}

	public int getLoadedBullets() {
		return loadedBullets;
	}

	public long getScore() {
		return this.score;
	}

	public void addMoney(int money) {
		this.money += money;
	}

	public int getMoney() {
		return this.money;
	}

	public void saveData() {
		editor.putInt("MONEY", this.money);
		editor.commit();
	}

	public void updateSharedData(boolean expectMoney) {
		if (!expectMoney)
			this.money = preferences.getInt("MONEY", 0);
	}

	public void shot() {
		loadedBullets--;
		if (!reloading)
			effects.playShot();
	}

	public boolean shotPossible() {
		return loadedBullets > 0 && !reloading;
	}

	public void reloadBullets() {
		if (!reloading) {
			reloadingStartTime = System.currentTimeMillis();
			addNextBulletIn = 124;
			loadedBullets = 0;
			effects.reloading();
			reloading = true;
		}
	}

	public boolean isReloading() {
		return reloading;
	}
	
	public int getTimeLeft(){
		return (int)((gameTime - (System.currentTimeMillis() - startTime)) / 1000);
	}
	
	public int getGameTime(){
		return gameTime;
	}
	
	public String getControlText(){
		return "level: " + level + ",  score: " + score + "/" + neededScore + " jeszcze: " + (neededScore - score);	
	}
	
	private void nextLevel(){
		neededScore += (long)(neededScore * 0.05) + 2000;
		level++;
		loadedBullets = 8;
		startTime = System.currentTimeMillis();
	}
	
	public void incrementComboKills(){
		comboKills++;
	}
	
	public void clearComboKills(){
		comboKills = 1;
	}
	
	public int getComboKills(){
		return comboKills;
	}
	
	public int getPowerUpsSpeed(){
		return powerUpsSpeed;
	}

	public void powUpBazooka() {
		puBazooka++;
	}
	
	public int getPowUpsBazookas(){
		return puBazooka;
	}
}
