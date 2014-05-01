package com.example.hellochess;

import model.Game;
import view.AndroidView;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import controller.Controller;

public class MainActivity extends ActionBarActivity {
	
	TextView selected;
	int oldColor;
	String last;
	int count = 0;
	AndroidView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GridLayout gl = (GridLayout) findViewById(R.id.GridLayout1);
		
		Game game = new Game();
		Controller controller = new Controller(game, null);
		view = new AndroidView(controller, this, gl);
		controller.view = view;
		
		
//		TextView btn = (TextView) findViewById(R.id.A1);
//		TextView a = new TextView(this);
//		btn.addView(child, width, height);
		
		System.out.println("Child count: " + gl.getChildCount());
		System.out.println("hello");
		
		GridLayout.LayoutParams pl = new GridLayout.LayoutParams();
		
		view.printBoard();
		
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

}
