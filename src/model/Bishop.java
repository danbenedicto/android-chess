package model;


/**
 * 
 * @author Dan Benedicto
 *
 */
public class Bishop extends ChessPiece {
	@Override
	public boolean canMoveToHook(Square to) {
		if (Math.abs(to.x - loc.x) != Math.abs(to.y - loc.y)) {
			return false;	// not diagonal with bishop
		}
		return this.hasClearPathTo(to); // valid if not blocked by another piece
	}

	@Override
	public String getInitial() {
		return "B";
	}

	@Override
	public String getUnicode() {
		return (player.color == PlayerColor.BLACK) ? "♝" : "♗" ;
	}
}