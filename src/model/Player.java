package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class Player implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PlayerColor color;
	public List<ChessPiece> pieces;
	public King king;	// keep a pointer to facilitate checking for a check
	public Player opponent;
	
	public Player(PlayerColor color){
		this.color = color;
		pieces = new ArrayList<ChessPiece>();
	}
	
	public String toString(){
		if (color == PlayerColor.BLACK){
			return "b";
		} else {
			return "w";
		}
	}
}
