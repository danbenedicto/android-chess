package com.example.hellochess;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import model.Game;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListGames extends Activity {
	
	List<Game> games;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_games);

		try {
			FileInputStream fis = openFileInput("games.bin");
			ObjectInputStream ois = new ObjectInputStream(fis);
			this.games = (List<Game>) ois.readObject();
			fis.close();
			ois.close();
		} catch (Exception e){
			
		}
		
		if (games == null){
			games = new ArrayList<Game>();
		}
		
		Game[] gameArray = new Game[games.size()];
		
		ArrayAdapter<Game> adapter = new ArrayAdapter<Game>(this, android.R.layout.simple_list_item_1, (Game[]) games.toArray(gameArray));
		
		ListView lv = (ListView) findViewById(R.id.gamesList);
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent i = new Intent(ListGames.this, PlaybackActivity.class);
				Bundle b = new Bundle();
				Game game = games.get(position);
				b.putSerializable("GAME", game);
				i.putExtras(b);
				startActivity(i);
				System.out.println("Clicked");
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_games, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_list_games,
					container, false);
			return rootView;
		}
	}

}
