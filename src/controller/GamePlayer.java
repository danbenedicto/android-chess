package controller;

import model.ChessPiece;
import model.Game;
import model.Move;
import view.AndroidView;

public class GamePlayer {

	private Game game;
	private AndroidView view;
	
	private int currentFrame;
	
	public GamePlayer(Game game, AndroidView view){
		this.game = game;
		this.view = view;
		this.currentFrame = 0;
	}
	
	public void goForward(){
		if (!canGoForward()) return;
		
		Move move = game.moves.get(currentFrame++);
		
		if (move.capture != null){
			move.capture.loc.chessPiece = null;
			move.capture.loc = null;
		}
		move.source.chessPiece = null;
		move.chessPiece.loc = move.destination;
		move.destination.chessPiece = move.chessPiece;
		
		if (move.type == Move.Type.CASTLE){		
			ChessPiece rook = game.board[move.destination.x == 6 ? 7 : 0][move.source.y].chessPiece;
			rook.loc.chessPiece = null;
			rook.loc = game.board[move.destination.x == 6 ? 5 : 2][move.source.y];
			rook.loc.chessPiece = rook;
		}
		
		view.printBoard();
	}
	
	public void goBackward(){
		if (!canGoBackward()) return;
		
		Move move = game.moves.get(currentFrame--);
		
		if (move.type == Move.Type.NORMAL) {
			move.chessPiece.loc = move.source;
			move.source.chessPiece = move.chessPiece;
			move.destination.chessPiece = move.capture;
			if (move.capture != null){
				move.capture.loc = move.destination;
			}
		} else if (move.type == Move.Type.CASTLE){
			move.chessPiece.loc = move.source;
			move.source.chessPiece = move.chessPiece;
			
			ChessPiece rook = game.board[move.destination.x == 6 ? 5 : 2][move.source.y].chessPiece;
			rook.loc.chessPiece = null;
			rook.loc = game.board[move.destination.x == 6 ? 7 : 0][move.source.y];
			rook.loc.chessPiece = rook;
		} else if (move.type == Move.Type.ENPASSANT){
			move.chessPiece.loc = move.source;
			move.source.chessPiece = move.chessPiece;
			
			move.capture.loc = game.board[move.destination.x][move.source.y];
			move.capture.loc.chessPiece = move.capture;
		}
		
		view.printBoard();
	}
	
	public boolean canGoForward(){
		return (currentFrame < game.moves.size() - 1); 
	}
	
	public boolean canGoBackward(){
		return (currentFrame > 0);
	}
}
