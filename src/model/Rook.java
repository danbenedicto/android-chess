package model;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class Rook extends ChessPiece {
	@Override
	public boolean canMoveTo(Square to) {
		if (loc.x != to.x && loc.y != loc.y) { 
			return false;	// not horizontal or vertical with rook
		}
		return this.hasClearPathTo(to);	// valid if not blocked by another piece
	}
	
	@Override
	public String getUnicode() {
		return (player.color == PlayerColor.BLACK) ? "♜" : "♖" ;
	}

	@Override
	public String getInitial() {
		return "R";
	}	
}