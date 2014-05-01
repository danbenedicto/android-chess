package controller;

import java.util.ArrayList;

import android.widget.Toast;
import model.ChessPiece;
import model.Game;
import model.Player;
import model.Square;
import view.AndroidView;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class Controller {
	
	public Game game;
	public AndroidView view;
	private boolean offerToDraw = false;
	private Player player;
	private Square selected;
	
	public Controller(Game game, AndroidView view){
		this.game = game;
		this.player = game.white;
		this.view = view;
	}
	
	public void processMove(String... tokens){
		if (tokens.length < 2){
			if (tokens.length == 1 &&
					(tokens[0].equalsIgnoreCase("resign") ||
							(offerToDraw && (tokens[0].equalsIgnoreCase("draws") || tokens[0].equalsIgnoreCase("draw"))))){
				return;	// game over
			}
			view.printIllegal();
			view.getMove(player);	// ask again
			return;
		}
		
		Square from = getSquare(tokens[0]);
		Square to = getSquare(tokens[1]);
		
		if (from == null || to == null || (tokens.length == 3 && !tokens[2].equalsIgnoreCase("draw?")) || !player.move(from, to)){
			view.printIllegal();
			view.getMove(player);
			return;
		}
		
		offerToDraw = tokens.length == 3 && tokens[2].equalsIgnoreCase("draw?");
		view.printBoard();
		
		if (player.opponent.king.isInCheck()){
			view.printCheck();
		}
		
		player = player.opponent;
		autoMove();
		view.getMove(player);
	}
	
	public void processTouch(Square square){
		if (selected == null){
			if (square.chessPiece != null){
				selected = square;
			} else {
				selected = null;
			}
		} else {
			if (player.move(selected, square)){
				player = player.opponent;
				if (player.king.isInCheck()){
					Toast toast = Toast.makeText(view.activity.getApplicationContext(), "ayy", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
			selected = null; 
		}
		
		view.printBoard();
		if (selected != null) {
			view.highlight(selected);
		}
	}
	
	public void autoMove(){
		for (ChessPiece cp : player.pieces){
			for (Square[] row : game.board){
				for (Square square : row){
					if (cp.loc != null && player.move(cp.loc, square)){
						view.printBoard();
						player = player.opponent;
						return;
					}
				}
			}
		}
	}
	
	private Square getSquare(String algebraic){
		if (algebraic.length() != 2){
			return null;
		}
		char file = algebraic.toLowerCase().charAt(0);
		char rank = algebraic.charAt(1);
		if (file < 'a' || file > 'h' || rank < '1' || rank > '8'){
			return null;
		}
		
		int x = file - 97;
		int y = 8 - (rank - 48);
		
		return game.board[x][y];
	}
	
}
