package view;

import java.util.Collection;
import java.util.HashMap;

import model.Game;
import model.Square;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hellochess.MainActivity;

import controller.Controller;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class AndroidView {
	
	private static final int boardColor1 = Color.parseColor("#FAEBD7");		// bisque
	private static final int boardColor2 = Color.parseColor("#CD853F");		// sandy brown
	private static final int hintColor = Color.GREEN;
	private static final int selectedColor = Color.CYAN;
	
	public Controller controller;
	public Activity activity;
	
	private TextView[][] textViews;
	private Game game;
	private HashMap<TextView, Square> viewSquares;
	
	/**
	 * Creates an AndroidView object that creates a visual experience for a chess game.
	 * Populates the given GridLayout with 64 text views, each representing a square on a chess board.
	 * @param activity The android activity where the GridLayout exists.
	 * @param controller The controller related to this object.
	 * @param gridLayout The GridLayout to fill with TextViews	
	 */
	public AndroidView(Activity activity, Controller controller, Game game, GridLayout boardGridLayout){
		this.activity = activity;
		this.controller = controller;		
		this.game = game;
		
		this.textViews = new TextView[8][8];
		
		this.viewSquares = new HashMap<TextView, Square>();
		
		// an OnClickListener that all views will share
		OnClickListener ocl = new OnClickListener() {
			public void onClick(View v){
				processTouch((TextView) v);
			}
		};
		
		Display display = activity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		
		// make all the views		
		for (int y = 0; y < 8; y++){
			for (int x = 0; x < 8; x++){
				// create a new TextView instance
				TextView tv = new TextView(activity);
				tv.setOnClickListener(ocl);
				tv.setGravity(Gravity.CENTER);
				tv.setTextSize(26);
				textViews[x][y] = tv;
				
				// add it to the GridLayout with a LayoutParams object
				GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
				lp.width = width / 8;
				lp.height = lp.width;
				lp.setGravity(Gravity.CENTER_HORIZONTAL);
				boardGridLayout.addView(tv, lp);
				
				// store it in our HashMaps
				viewSquares.put(textViews[x][y], game.board[x][y]);
			}
		}
	}
	
	/**
	 * Recognize that a particular TextView (square) was clicked, then tell the controller which Square it corresponds to.
	 * @param view
	 */
	public void processTouch(TextView view){
		controller.processTouch(viewSquares.get(view));
	}

	/**
	 * Updates the TextViews to reflect the game's current board.
	 */
	public void printBoard(){
		for (int y = 0; y < 8; y++){
			for (int x = 0; x < 8; x++){
				// set the text for this square 
				if (game.board[x][y].chessPiece != null){
					// text is some unicode character stored in values/strings.xml whose id is a two-character code, like bp or wK
					String pieceId = game.board[x][y].chessPiece.toString();	// get piece code
					int resourceId = activity.getResources().getIdentifier(pieceId, "string", activity.getPackageName());	// get the resourceId (we can't simply use R.string.id)
					String unicode = (String) activity.getResources().getText(resourceId); // the unicode symbol for the chessPiece
					textViews[x][y].setText(unicode);
				} else {
					textViews[x][y].setText("");	// no piece on this square
				}
				
				// color the board (un-highlight any highlighted squares)
				if ((x + y) % 2 == 0){
					textViews[x][y].setBackgroundColor(boardColor1);
				}
				else {
					textViews[x][y].setBackgroundColor(boardColor2);
				}
			}
		}
	}
	
	/**
	 * Highlights the given chess board squares. Perhaps this method can be used to indicate possible moves. 
	 * @param squares The squares to be highlighted
	 */
	public void hintAt(Collection<Square> squares){
		for (Square s : squares){
			textViews[s.x][s.y].setBackgroundColor(hintColor);
		}
	}
	
	/**
	 * Highlights a single special chess board square. Use this to indicate which piece has been selected.
	 * @param selected
	 */
	public void highlight(Square selected){
		textViews[selected.x][selected.y].setBackgroundColor(selectedColor);
	}
	
	/**
	 * Does something (or nothing) when a user attempts an illegal move.
	 */
	public void printIllegal(){
		// to-do, maybe?
	}
	
	/**
	 * Does something (perhaps makes a Toast) when a player is put in check.
	 */
	public void printCheck(){
		Toast toast = Toast.makeText(activity.getApplicationContext(), "ayy", Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public String printCheckmate(){
		Toast toast = Toast.makeText(activity.getApplicationContext(), "Checkmate!", Toast.LENGTH_SHORT);
		toast.show();
		return "hello";
	}
	
	public String printStalemate(){
		Toast toast = Toast.makeText(activity.getApplicationContext(), "Stalemate", Toast.LENGTH_SHORT);
		toast.show();
		return "goodbye";
	}
}