package knm.duckshunter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends Activity implements OnClickListener {

	private Button btnPlay;
	private TextView lblLoading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.mainmenu_layout);
		
		lblLoading = (TextView) findViewById(R.id.lblLoading);
		btnPlay = (Button) findViewById(R.id.btnPlay);
		btnPlay.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnPlay:
			btnPlay.setVisibility(View.GONE);
			lblLoading.setVisibility(View.VISIBLE);
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra("mode", 1);
			startActivity(intent);
			break;
		}
	}
	
	@Override
	public void onResume(){
		btnPlay.setVisibility(View.VISIBLE);
		lblLoading.setVisibility(View.GONE);
	    super.onResume();
	}
}
