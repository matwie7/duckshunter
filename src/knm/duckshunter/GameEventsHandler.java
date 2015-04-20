package knm.duckshunter;

import java.util.Stack;

import knm.duckshunter.Effects.Effects;
import knm.duckshunter.Enemies.Enemies;
import knm.duckshunter.PowerUps.PowerUps;

public class GameEventsHandler {

	private Stack<TouchEvent> touchEvents;
	private UI ui;
	private LvlManager lvlMng;
	private Enemies enemies;
	private Effects effects;
	private PowerUps powUps;
	private MainActivity mainActivity;

	public GameEventsHandler(UI ui, LvlManager lvlMng, Enemies enemies,
			Effects effects, MainActivity theGameActivity, PowerUps powUps) {
		touchEvents = new Stack<TouchEvent>();
		this.ui = ui;
		this.powUps = powUps;
		this.lvlMng = lvlMng;
		this.enemies = enemies;
		this.effects = effects;
		this.mainActivity = theGameActivity;
	}

	public void doEvents() {
		TouchEvent e;
		while (!touchEvents.isEmpty()) {
			e = touchEvents.pop();

			ui.touch(e.getX(), e.getY());
			if (lvlMng.shotPossible()) {
				effects.vibrateShot();
				enemies.touch(e.getX(), e.getY());
			} else if (!lvlMng.isReloading()) 
				effects.pustyMagazynek();
			if (powUps.collision(e.getX(), e.getY()))
				lvlMng.powUpBazooka();
		}
	}

	public void addEvent(float x, float y) {
		if (!mainActivity.getDialogPauseActivity())
			touchEvents.push(new TouchEvent((int) x, (int) y));
	}

	private class TouchEvent {

		private int touchX;
		private int touchY;

		public TouchEvent(int touchX, int touchY) {
			this.touchX = touchX;
			this.touchY = touchY;
		}

		public int getX() {
			return touchX;
		}

		public int getY() {
			return touchY;
		}
	}
}
