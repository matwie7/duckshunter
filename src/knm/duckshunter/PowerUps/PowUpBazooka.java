package knm.duckshunter.PowerUps;

import android.graphics.Canvas;
import knm.duckshunter.LvlManager;
import knm.duckshunter.Enemies.Enemy;

public class PowUpBazooka extends PowerUp {

	public PowUpBazooka(Enemy enemy, LvlManager lvlMng) {
		super(enemy, lvlMng);
		

	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle((int)x, (int)y, (int)radius, pFill);
		canvas.drawText("B", (int)x, (int)y + 7, pText);
	}

}
