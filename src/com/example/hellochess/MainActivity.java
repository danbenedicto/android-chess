package com.example.hellochess;

import model.Game;
import view.AndroidView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;
import controller.Controller;

public class MainActivity extends ActionBarActivity {
	
	Game game;
	Controller controller;
	AndroidView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GridLayout chessGrid = (GridLayout) findViewById(R.id.BoardGridLayout);
		
		this.game = new Game();
		this.view = new AndroidView(this, null, game, chessGrid);
		this.controller = new Controller(game, this.view);
		this.view.controller = this.controller;
		
		view.printBoard();
		ressignButton();
		drawButton();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	public void undoMove(View v){
		controller.undoMove();
	}
	public void drawButton(){
		Button message = (Button) findViewById(R.id.button2);
		message.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CharSequence text="Do you want to draw?";
				Context context = getApplicationContext();
				int duration = Toast.LENGTH_LONG;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();	
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

			    builder.setTitle("Confirm");
			    builder.setMessage("Are you sure?");

			    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

			        public void onClick(DialogInterface dialog, int which) {
			        	GridLayout chessGrid = (GridLayout) findViewById(R.id.BoardGridLayout);
						chessGrid.removeAllViews();	MainActivity.this.game = new Game();
						MainActivity.this.view = new AndroidView(MainActivity.this, null, game, chessGrid);
					
						MainActivity.this.controller = new Controller(game, MainActivity.this.view);
						MainActivity.this.view.controller = MainActivity.this.controller;
						view.printBoard();
			        	

			            dialog.dismiss();
			        }

			    });

			    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

			        @Override
			        public void onClick(DialogInterface dialog, int which) {
			            // Do nothing
			            dialog.dismiss();
			        }
			    });

			    AlertDialog alert = builder.create();
			    alert.show();
			}
		});
		
	}
	public void ressignButton(){
		Button message = (Button) findViewById(R.id.button3);
		message.setOnClickListener(new View.OnClickListener() {
		

			@Override
			public void onClick(View v) {
				CharSequence text;
				Context context = getApplicationContext();
				if(game.currentPlayer.toString().equals("w")){
					text = "White resigns!";
				}else{
					text ="Black Resigns!";
				}
				int duration = Toast.LENGTH_LONG;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				GridLayout chessGrid = (GridLayout) findViewById(R.id.BoardGridLayout);
				chessGrid.removeAllViews();	MainActivity.this.game = new Game();
				MainActivity.this.view = new AndroidView(MainActivity.this, null, game, chessGrid);
			
				MainActivity.this.controller = new Controller(game, MainActivity.this.view);
				MainActivity.this.view.controller = MainActivity.this.controller;
				view.printBoard();
				

			}
			
		}
		);

	}

}
