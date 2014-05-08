package com.example.hellochess;

import model.Game;
import view.AndroidView;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import controller.Controller;
import controller.GamePlayer;

public class PlaybackActivity extends Activity {
	
	Game game;
	Controller controller;
	AndroidView view;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playback);
		
		Bundle b = this.getIntent().getExtras();
		if (b != null) {
		    game = (Game) b.getSerializable("GAME");
		}
		
		GridLayout chessGrid = (GridLayout) findViewById(R.id.BoardGridLayout2);
		
		this.view = new AndroidView(this, null, game, chessGrid);
		this.controller = new GamePlayer(this, game, this.view);
		this.view.controller = this.controller;
		
		Button button = (Button) findViewById(R.id.backButton);
		button.setEnabled(false);
		
		for (int i = 0; i < chessGrid.getChildCount(); i++){
			chessGrid.getChildAt(i).setOnClickListener(null);
		}
		
		view.printBoard();
	}
	
	public void setBackButtonEnabled(boolean enabled){
		Button button = (Button) findViewById(R.id.backButton);
		button.setEnabled(enabled);
	}
	
	public void setForwardButtonEnabled(boolean enabled){
		Button button = (Button) findViewById(R.id.forwardButton);
		button.setEnabled(enabled);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.playback, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_playback,
					container, false);
			return rootView;
		}
	}
	
	public void goForward(View view){
		((GamePlayer) controller).goForward();
		this.view.printBoard();
	}
	
	public void goBackward(View view){
		((GamePlayer) controller).goBackward();
		this.view.printBoard();
	}

}
