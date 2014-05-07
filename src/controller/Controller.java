package controller;

import java.util.ArrayList;
import java.util.List;

import model.ChessPiece;
import model.Game;
import model.Move;
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
	
	private ChessPiece selected;
	Square original;
	Square destination;
	
	ChessPiece destinationPiece;
	
	
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
			
			selected = clickedSquare.chessPiece;	// remember it
			view.printBoard();
			view.highlight(clickedSquare);	// let the view highlight it
			view.hintAt(getPossibleMoves(selected));
			return;
		
		}
		
		if (selected != null){
			
			// something was already selected before this, so this is an attempt at moving a piece
			original = selected.loc;
			destination = clickedSquare;
			destinationPiece = clickedSquare.chessPiece;
			
			Move move;
			
			if (game.currentPlayer == selected.player && (move = selected.tryMoveTo(clickedSquare)) != null){
				// successful!
				game.moves.add(move);	// record the move
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
			if (cp.loc == null) continue; 
			for (Square[] row : game.board){
				for (Square square : row){
					if (cp.tryMoveTo(square) != null){
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
				if (cp.canMoveTo(square)){
					result.add(square);
				}
			}
		}
		
		return result;
	}
	
	public void undoMove(){
		if (game.moves.size() == 0) return;
		
		Move move = game.moves.remove(game.moves.size() - 1);	// get the most recent move
		
		if (move.type == Move.Type.NORMAL){
			move.destination.chessPiece = move.capture;
			if (move.capture != null) move.capture.loc = move.destination;
			
			move.chessPiece.moves--;
			move.chessPiece.loc = move.source;
			move.source.chessPiece = move.chessPiece;
		} else if (move.type == Move.Type.CASTLE){
			move.destination.chessPiece = null;
			move.chessPiece.loc = move.source;
			move.source.chessPiece = move.chessPiece;
			move.chessPiece.moves--;
			
			Square rookFrom, rookTo;
			
			if (move.destination.x == 6){
				rookFrom = game.board[7][move.source.y];
				rookTo = game.board[5][move.source.y];
			} else {
				rookFrom = game.board[0][move.source.y];
				rookTo = game.board[2][move.source.y];
			}
			
			rookFrom.chessPiece = rookTo.chessPiece;
			rookFrom.chessPiece.loc = rookFrom;
			rookTo.chessPiece = null;
		}
		
		game.currentPlayer = game.currentPlayer.opponent;
		
		view.printBoard();
	}
}
