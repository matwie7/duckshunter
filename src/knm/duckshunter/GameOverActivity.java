package knm.duckshunter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameOverActivity extends Activity implements OnClickListener{

	private Button bReplay, bExit;

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameoverscreen);
        bReplay = (Button) findViewById(R.id.bReplay);
        bExit = (Button) findViewById(R.id.bExit);
        bReplay.setOnClickListener(this);
        bExit.setOnClickListener(this);
    }
	
	public void onClick(View v) {
        switch (v.getId()) {
        case R.id.bReplay:
            Intent newGameScreen = new Intent(this, MainActivity.class);
            startActivity(newGameScreen);
            this.finish();
            break;
        case R.id.bExit:
            this.finish();
            break;
        }
    }


}
