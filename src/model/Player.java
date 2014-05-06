package model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class Player {
	
	public PlayerColor color;
	public List<ChessPiece> pieces;
	public King king;	// keep a pointer to facilitate checking for a check
	public Player opponent;
	
	public Player(PlayerColor color){
		this.color = color;
		pieces = new ArrayList<ChessPiece>();
	}
	
	public boolean move(Square from, Square to, boolean commit){
		ChessPiece cp = from.chessPiece;
		
		if (cp == null || cp.player != this){
			return false;	// not moving one of his pieces
		}
		
		return cp.canMoveTo(to, commit);
	}
	
	public String toString(){
		if (color == PlayerColor.BLACK){
			return "b";
		} else {
			return "w";
		}
	}
}
