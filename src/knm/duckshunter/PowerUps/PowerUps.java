package knm.duckshunter.PowerUps;

import java.util.ArrayList;
import java.util.List;

import knm.duckshunter.GameView;
import knm.duckshunter.LvlManager;
import knm.duckshunter.Enemies.Enemies;
import knm.duckshunter.Enemies.Enemy;
import android.graphics.Canvas;

public class PowerUps {

	private LvlManager lvlMng;
	private GameView gameView;
	private Enemies enemies;
	
	private List<PowerUp> powUps = new ArrayList<PowerUp>();
	
	public PowerUps (LvlManager lvlMng, GameView gameView){
		this.lvlMng = lvlMng;
		this.gameView = gameView;
	}
	
	public void drawPowerUps(Canvas canvas) {
		for(PowerUp powUp: powUps){
			powUp.draw(canvas);
		}
	}

	public void createPowerUp(Enemy enemy) {
		powUps.add(new PowUpBazooka(enemy, lvlMng));
	}
	
	public void calculatePowerUps(){
		for(PowerUp powUp: powUps){
			powUp.move();
			powUp.checkIfDestroyed();
			if (powUp.destroyed())
				powUps.remove(powUp);
		}
	}
	
	public boolean collision(int x, int y){
		for(PowerUp powUp: powUps)
			if (Math.sqrt(((powUp.getX() - x) * (powUp.getX() - x)) + 
			((powUp.getY() - y) * (powUp.getY() - y))) < powUp.getRadius()){
				powUp.destroy();
				return true;
			}
		return false;
	}

	public int getPowUpsAmount() {
		return powUps.size();
	}

	public PowerUp getPowUp(int location) {
		return powUps.get(location);
	}

	public void removePowUp(int location) {
		powUps.remove(location);
	}

}