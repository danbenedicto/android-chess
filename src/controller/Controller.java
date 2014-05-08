package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.ChessPiece;
import model.Game;
import model.Move;
import model.Square;
import view.AndroidView;
import android.content.Context;
import android.widget.Toast;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class Controller {
	
	public Game game;
	public AndroidView view;
	
	private ChessPiece selected;	
	
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
			
			Move move;
			
			if (game.currentPlayer == selected.player && (move = selected.tryMoveTo(clickedSquare)) != null){
				// successful!
				game.moves.add(move);	// record the move
				game.currentPlayer = game.currentPlayer.opponent;	// change turns
				
				boolean inCheck = game.currentPlayer.king.isInCheck();
				
				view.setUndoEnabled(true);
					
				if (anyMoves()){
					if (inCheck){
						view.printCheck();
					}
				}else{
					if (inCheck){
						view.printCheckmate();
					} else {
						view.printStalemate();
					}
				}
				
			} else {
				// anything here will execute when an attempted move is invalid
			}
			
			selected = null;	// clear selection whether the move was successful or not 
		}
		
		view.printBoard();
	}
	
	public boolean anyMoves(){
		for (ChessPiece cp: game.currentPlayer.pieces){
			if (cp.loc == null) continue;
			if (getPossibleMoves(cp).size() > 0){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Attempts to make a move for the player whose turn it is.
	 */
	public void autoMove(){
		
		List<ChessPiece> tempPieces = new ArrayList<ChessPiece>(16);
		for (ChessPiece cp: game.currentPlayer.pieces){
			tempPieces.add(cp);
		}
		Collections.shuffle(tempPieces);
		
		for (ChessPiece cp: tempPieces){
			if (cp.loc != null){
				List<Square> moves = getPossibleMoves(cp);
				if (moves.size() > 0){
					Move move;
					int rand = (int) (Math.random() * moves.size());
					if ((move = cp.tryMoveTo(moves.get(rand))) != null){
						game.moves.add(move);
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
		
		move.chessPiece.moves--;
		move.chessPiece.loc = move.source;
		move.source.chessPiece = move.chessPiece;
		
		if (move.type == Move.Type.NORMAL){
			move.destination.chessPiece = move.capture;
			if (move.capture != null){
				move.capture.loc = move.destination;
			}
		} else if (move.type == Move.Type.CASTLE){
			move.destination.chessPiece = null;
			
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
		} else if (move.type == Move.Type.ENPASSANT){			
			move.destination.chessPiece = null;
			
			game.board[move.destination.x][move.source.y].chessPiece = move.capture;
			move.capture.loc = game.board[move.destination.x][move.source.y];
		}
		
		game.currentPlayer = game.currentPlayer.opponent;
		
		view.setUndoEnabled(false);
		
		view.printBoard();
	}
	
	@SuppressWarnings("unchecked")
	public void save(){
		List<Game> games = null;
		try {
			FileInputStream fis = view.activity.openFileInput("games.bin");
			ObjectInputStream ois = new ObjectInputStream(fis);
			games = (List<Game>) ois.readObject();
			fis.close();
			ois.close();
		} catch (Exception e){
			games = new ArrayList<Game>();
		}
		
		if (games == null){
			games = new ArrayList<Game>();
		}
		
		games.add(game);
		
		try {
			FileOutputStream fileOut = view.activity.openFileOutput("games.bin", Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(games);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in games.bin");
			Toast toast = Toast.makeText(view.activity, "Game successfully saved.", Toast.LENGTH_SHORT);
			toast.show();
		} catch(IOException i) {
			Toast toast = Toast.makeText(view.activity, "Error saving game.", Toast.LENGTH_SHORT);
			toast.show();
			System.out.println(i.getMessage());
		}
	}
}
