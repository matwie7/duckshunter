package knm.duckshunter.Effects;

import java.util.HashMap;

import knm.duckshunter.MainActivity;
import knm.duckshunter.R;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sounds {

	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	private static final int SOUND_EXPLOSION = 1;
	private static final int SOUND_PUSTY_MAGAZYNEK = 3;
	private static final int SOUND_RELOADING = 4;
	private static final int SOUND_SHOOT = 5;
	private static final int SOUND_GOLOMB = 6;
	private MainActivity gameActivity;

	public Sounds(MainActivity gameActivity) {
		this.gameActivity = gameActivity;
		initSounds();
	}

	private void initSounds() {
	     soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	     soundPoolMap = new HashMap<Integer, Integer>();
	     soundPoolMap.put(SOUND_EXPLOSION, soundPool.load(gameActivity, R.raw.boom, 1));
	     soundPoolMap.put(SOUND_PUSTY_MAGAZYNEK, soundPool.load(gameActivity, R.raw.pusty_magazynek, 3));
	     soundPoolMap.put(SOUND_RELOADING, soundPool.load(gameActivity, R.raw.reload, 4));
	     soundPoolMap.put(SOUND_SHOOT, soundPool.load(gameActivity, R.raw.shoot, 5));
	     soundPoolMap.put(SOUND_GOLOMB, soundPool.load(gameActivity, R.raw.golomb, 5));
	}
	          
	public void playSound(int sound) {
	    /* Updated: The next 4 lines calculate the current volume in a scale of 0.0 to 1.0 */
	    AudioManager mgr = (AudioManager)gameActivity.getSystemService(Context.AUDIO_SERVICE);
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);    
	    float volume = streamVolumeCurrent / streamVolumeMax;
	    
	    /* Play the sound with the correct volume */
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, 0, 1f);     
	}

	public void playExplode() {
	    playSound(SOUND_EXPLOSION);
	} 

	public void playPustyMagazynek(){
		playSound(SOUND_PUSTY_MAGAZYNEK);
	}
	
	public void playReloading(){
		playSound(SOUND_RELOADING);
	}
	
	public void playShot(){
		playSound(SOUND_SHOOT);
	}
	
	public void playGolomb(){
		playSound(SOUND_GOLOMB);
	}
}
