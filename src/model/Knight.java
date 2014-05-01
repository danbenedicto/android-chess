package model;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class Knight extends ChessPiece {

	@Override
	public boolean canMoveTo(Square to) {
		int dx = Math.abs(to.x - loc.x);
		int dy = Math.abs(to.y - loc.y);
		return ((dx == 2 && dy == 1) || (dx == 1 && dy == 2));
	}
	
	@Override
	public String getUnicode() {
		return (player.color == PlayerColor.BLACK) ? "♞" : "♘" ;
	}

	@Override
	public String getInitial() {
		return "N";
	}
}