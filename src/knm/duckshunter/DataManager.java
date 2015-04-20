package knm.duckshunter;

import android.content.Context;
import android.content.SharedPreferences;

public class DataManager {

	private int money;
	private int speed;
	private int maxSpeed;
	private int costsSpeedSmall;
	private int costsSpeedBig;
	private Context context;
	private SharedPreferences preferences; 
	private SharedPreferences.Editor editor;
	private boolean vibrations;
	private boolean sound;
	
	public DataManager(Context context){
		this.context = context;
		preferences = context.getSharedPreferences("SAVE_STATE", Context.MODE_WORLD_WRITEABLE);
		editor = preferences.edit();
		
		costsSpeedSmall = Integer.parseInt(context.getResources().getString(R.string.upgrade_costs_speed_small));
		costsSpeedBig = Integer.parseInt(context.getResources().getString(R.string.upgrade_costs_speed_big));
		
		updateSharedData();
		loadSettings();
	}
	
	public String getMoneyString(){
		return money + "zl";
	}

	public CharSequence getSpeedString() {
		return speed + "/" + (int)maxSpeed;
	}
	
	public void saveSettings(){
		editor.putBoolean("vibrations", vibrations);
		editor.putBoolean("sound", sound);
		editor.commit();
	}
	
	public void loadSettings(){
		vibrations = preferences.getBoolean("vibrations", true);
		sound = preferences.getBoolean("sound", true);
	}
	
	public void updateSharedData(){
		this.speed = preferences.getInt("SPEED", Integer.parseInt(context.getResources().getString(R.string.default_speed)));
		this.maxSpeed = Integer.parseInt(context.getResources().getString(R.string.max_speed));
		this.money = preferences.getInt("MONEY", 0);
	}
	
	

	public void buySpeed(double d) {
		if (d == 0.1 && speed + 1 <= maxSpeed && money >= costsSpeedSmall){
			editor.putInt("SPEED", this.speed + 1);
			editor.putInt("MONEY", this.money - costsSpeedSmall);
			editor.commit();
		}
		else if (d == 1 && speed + 10 <= maxSpeed && money >= costsSpeedBig){
			editor.putInt("SPEED", this.speed + 10);
			editor.putInt("MONEY", this.money - costsSpeedBig);
			editor.commit();
		}
	}
	
	public void reset(){
		editor.clear().commit();
		preferences.edit().clear().commit();
		updateSharedData();
	}
	
	public void cheat(){
		editor.putInt("MONEY", this.money + 1000).commit();
	}

	public void vibrations() {
		vibrations = !vibrations;
		saveSettings();
	}
	
	public void sound() {
		sound = !sound;
		saveSettings();
	}
	
	public boolean getVibrations(){
		return vibrations;
	}
	
	public boolean getSound(){
		return sound;
	}
}
