package knm.duckshunter;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener, OnDismissListener {
	
	private Dialog dialogPause;
	private boolean firstStart = true;
	private boolean dialogPauseIsActive = false;
	private GameView theGameView;
	private DataManager dataMng;
	
	private Button btnResume;
	private Button btnExit;
	private Button btnVibrations;
	private Button btnSound;
	
	
	@Override
    protected void onResume() {
        super.onResume();
        if(!firstStart){
            dialogPause.show();
            dialogPauseIsActive=true;
        }
    }
	
	@Override
    protected void onPause() {
        super.onPause();
        theGameView.pauseThread();
        firstStart = false;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		theGameView = new GameView(this);
		setContentView(theGameView);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		dialogPause = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialogPause.setContentView(R.layout.gamedialog);
		dialogPause.hide();
		
		btnResume = (Button) dialogPause.findViewById(R.id.btnResume);
		btnExit = (Button) dialogPause.findViewById(R.id.btnExit);
		btnVibrations = (Button) dialogPause.findViewById(R.id.btnVibrations);
		btnSound = (Button) dialogPause.findViewById(R.id.btnSound);
		
		dialogPause.setOnDismissListener(this);
		initialize();
	}
	
	public void onGameOver() {
        Intent theNextIntent = new Intent(getApplicationContext(), GameOverActivity.class);
        startActivity(theNextIntent);
        this.finish();
    }
	
	public void onDialog(boolean pause) {
		dialogState();
    }
	
	private void initialize(){
		dataMng = new DataManager(this);
		
		btnResume.setOnClickListener(this);
		btnExit.setOnClickListener(this);
		btnSound.setOnClickListener(this);
		btnVibrations.setOnClickListener(this);
		
		updateUI();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
	    case R.id.btnResume:
	    	dialogState();
	        break;
	    case R.id.btnExit:
	    	finish();
	    	break;
	    case R.id.btnVibrations:
	    	dataMng.vibrations();
	    	updateUI();
	    	break;
	    case R.id.btnSound:
	    	dataMng.sound();
	    	updateUI();
	    	break;
	     
//	    case R.id.btnSpeed01:
//	    	upgradesMng.buySpeed(0.1);
//	    	updateUI();
//	    	break;
//	    case R.id.btnSpeed1:
//	    	upgradesMng.buySpeed(1);
//	    	updateUI();
//	    	break;
//	    	
//	    case R.id.reset:
//	    	upgradesMng.reset();
//	    	updateUI();
//	    	break;
//	    	
//	    case R.id.cheat:
//	    	upgradesMng.cheat();
//	    	updateUI();
//	    	break;
	    }
	}
	
	public void dialogState() {
        if (dialogPauseIsActive) {
            dialogPause.hide();
            dialogPauseIsActive = false;
            theGameView.resumeThread();
        } else if (!dialogPauseIsActive) {
                        theGameView.pauseThread();
            dialogPause.show();
            dialogPauseIsActive = true;
        }
        else{
            theGameView.pauseThread();
            updateUI();
        }
    }

	@Override
	public void onDismiss(DialogInterface dialog) {
	    dialogState();
	}
	
	private void updateUI(){
		if (dataMng.getSound()) 
			btnSound.setText("sounds: on");
		else
			btnSound.setText("sounds: off");
		
		if (dataMng.getVibrations()) 
			btnVibrations.setText("vibrations: on");
		else
			btnVibrations.setText("vibrations: off");
	}
	
	public boolean getDialogPauseActivity(){
		return dialogPauseIsActive;
	}
	
	@Override
	public void onBackPressed() {
		dialogState();
	}
}