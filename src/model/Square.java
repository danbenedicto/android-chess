package model;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class Square {
	
	public int x;	// [0,8) ; rank
	public int y;	// [0,8) ; file
	
	public ChessPiece chessPiece;	// the piece currently on this square

	public Square(int x, int y){
		this.x = x;
		this.y = y;
	}
}
