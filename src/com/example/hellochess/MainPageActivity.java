package com.example.hellochess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);

		playButton();
		replayButton();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
		return true;
	}
	public void playButton(){
		Button message = (Button) findViewById(R.id.button1);
		message.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainPageActivity.this,MainActivity.class));
				
			}
		});
		
	}
	public void replayButton(){
		Button message = (Button) findViewById(R.id.button3);
		message.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainPageActivity.this,ListGames.class));
				
			}
		});
		
	}


}
