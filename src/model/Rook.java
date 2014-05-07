package model;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class Rook extends ChessPiece {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean canMoveToHook(Square to) {
		if (loc.x != to.x && loc.y != to.y) { 
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