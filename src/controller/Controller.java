package controller;

import java.util.ArrayList;
import java.util.List;

import model.ChessPiece;
import model.Game;
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
	
	private Square selected;
	Square original;
	Square destination;
	
	
	public Controller(Game game, AndroidView view){
		this.game = game;
		this.view = view;
	}
	
	/**
	 * Handles click events from the view and incorporates them in the context of the chess game.
	 * Might prepares a piece to be moved, or execute a chess move. 
	 * @param clickedSquare
	 */
	public void processTouch(Square clickedSquare){
		
		if (selected == null && clickedSquare.chessPiece != null && clickedSquare.chessPiece.player == game.currentPlayer){
			// the player is preparing to move the piece at this square (destination is given by the next touch if valid)
			
			selected = clickedSquare;	// remember it
			view.printBoard();
			view.highlight(selected);	// let the view highlight it
			view.hintAt(getPossibleMoves(selected.chessPiece));
			return;
		
		}
		
		if (selected != null){
			
			// something was already selected before this, so this is an attempt at moving a piece
			original = selected;
			destination = clickedSquare;
			if (game.currentPlayer.move(selected, clickedSquare, true)){
				// successful!
				game.currentPlayer = game.currentPlayer.opponent;	// change turns
				
				if (game.currentPlayer.king.isInCheck()){
					view.printCheck();
				}
				
			} else {
				// anything here will execute when an attempted move is invalid
			}
			
			selected = null;	// clear selection whether the move was successful or not 
		}
		
		view.printBoard();
	}
	
	/**
	 * Attempts to make a move for the player whose turn it is.
	 */
	public void autoMove(){
		
		// to do: make this more random
		
		for (ChessPiece cp : game.currentPlayer.pieces){
			for (Square[] row : game.board){
				for (Square square : row){
					if (cp.loc != null && game.currentPlayer.move(cp.loc, square, true)){
						view.printBoard();
						game.currentPlayer = game.currentPlayer.opponent;
						return;
					}
				}
			}
		}
		
		// no moves!
	}
	
	public List<Square> getPossibleMoves(ChessPiece cp){
		ArrayList<Square> result = new ArrayList<Square>();
		
		for (Square[] row : game.board){
			for (Square square : row){
				if (cp.canMoveTo(square, false)){
					result.add(square);
				}
			}
		}
		
		return result;
	}
	
	public void undoMove(){
		original.chessPiece=destination.chessPiece;
		destination.chessPiece=original.chessPiece;		
	}
}
