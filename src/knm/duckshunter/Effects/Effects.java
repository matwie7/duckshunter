package knm.duckshunter.Effects;

import java.util.ArrayList;
import java.util.List;

import knm.duckshunter.DataManager;
import knm.duckshunter.GameView;
import knm.duckshunter.MainActivity;
import knm.duckshunter.R;
import knm.duckshunter.Enemies.Enemy;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Vibrator;

public class Effects {

	private Sounds sounds;
	private Vibrator vb;
	private DataManager dataMng;
	private boolean sound;
	private boolean vibrations;
	private Bitmap bmpExplosion;
	
	private List<VisualEffect> effects = new ArrayList<VisualEffect>();
	
	public Effects(MainActivity gameActivity, Context context, GameView gameView) {
		sounds = new Sounds(gameActivity);
		vb = (Vibrator) gameActivity.getSystemService(Context.VIBRATOR_SERVICE);
		dataMng = new DataManager(context);
		sound = dataMng.getSound();
		vibrations = dataMng.getVibrations();
		bmpExplosion = BitmapFactory.decodeResource(gameView.getResources(),R.drawable.boom_sheet);
	}
	
	public void drawEffects(Canvas canvas){
		for(VisualEffect vf: effects){
			vf.draw(canvas);
		}
	}
	
	public void calculateEffects(){
		for(VisualEffect vf: effects){
			if (!vf.isActive())
				effects.remove(vf);
			}
	}
	
	public void addExplosion(Enemy enemy){
		effects.add(new Explosion(bmpExplosion, enemy));
	}
	
	public void addTextScore(Enemy enemy) {
		effects.add(new TextEffect(enemy));
	}
	
	public void playExplode() {
		if (sound)
			sounds.playExplode();
	} 
	
	private void playPustyMagazynek(){
			sounds.playPustyMagazynek();
	}
	
	private void playReloading(){
			sounds.playReloading();
	}
	
	public void playShot(){
		if (sound)
			sounds.playShot();
	}
	
	public void playGolomb(){
		if (sound)
			sounds.playGolomb();
	}
	
	public void vibrateShot(){
		if (vibrations)
			vb.vibrate(80);
	}
	
	private void vibratePustyMagazynek(){
			long[] pattern = { 0, 30, 50, 30 };
			vb.vibrate(pattern, -1);
	}
	
	private void vibrateReloading(){
			long[] pattern = {0, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62};
			vb.vibrate(pattern, -1);
	}

	public void pustyMagazynek() {
		if (sound)
			playPustyMagazynek();
		if (vibrations)
			vibratePustyMagazynek();
	}

	public void reloading() {
		if (sound)
			playReloading();
		if (vibrations)
			vibrateReloading();
	}
	
	public void reloadSettings(){
		dataMng.loadSettings();
		sound = dataMng.getSound();
		vibrations = dataMng.getVibrations();
	}
}
