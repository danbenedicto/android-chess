package view;

import java.util.Collection;
import java.util.HashMap;

import model.Game;
import model.Square;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.hellochess.MainActivity;

import controller.Controller;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class AndroidView {
	
	private Controller controller;
	public MainActivity activity;
	private TextView[][] textViews;
	private Game game;
	private HashMap<TextView, Square> viewSquares;
	private HashMap<Square, TextView> squareViews;
	
	public AndroidView(Controller controller, MainActivity activity, GridLayout gridLayout){
		this.controller = controller;
		this.activity = activity;
		
		this.game = controller.game;
		this.textViews = new TextView[8][8];
		
		this.viewSquares = new HashMap<TextView, Square>();
		this.squareViews = new HashMap<Square, TextView>();
		
		OnClickListener ocl = new OnClickListener() {
			public void onClick(View v){
				processTouch((TextView) v);
			}
		};
		
		for (int y = 0; y < 8; y++){
			for (int x = 0; x < 8; x++){
				TextView tv = new TextView(activity);
				tv.setOnClickListener(ocl);
				tv.setText(x + "" + y);
				tv.setWidth(40);
				tv.setHeight(40);
				tv.setGravity(Gravity.CENTER);
				tv.setTextSize(25);
				textViews[x][y] = tv;
				gridLayout.addView(tv);
				
				viewSquares.put(textViews[x][y], game.board[x][y]);
				squareViews.put(game.board[x][y], textViews[x][y]);
			}
		}
	}
	
	/**
	 * Migrated from CmdView chess - asked a certain player for a move. Not necessary, but could be used to display current player.
	 */
	public void getMove(){
		
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
					textViews[x][y].setBackgroundColor(Color.parseColor("#FFFF00"));
				}
				else {
					textViews[x][y].setBackgroundColor(Color.parseColor("#FF0000"));
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
			squareViews.get(s).setBackgroundColor(Color.MAGENTA);
		}
	}
	
	/**
	 * Highlights a single special chess board square. Use this to indicate which piece has been selected.
	 * @param selected
	 */
	public void highlight(Square selected){
		squareViews.get(selected).setBackgroundColor(Color.GREEN);
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
		// to-do
	}
}