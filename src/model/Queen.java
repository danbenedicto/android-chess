package model;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class Queen extends ChessPiece {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean canMoveToHook(Square to) {
		if (loc.x == to.x || loc.y == to.y || (Math.abs(to.x - loc.x) == Math.abs(to.y - loc.y))){
			return this.hasClearPathTo(to);
		}

		return false;
	}
	
	@Override
	public String getUnicode() {
		return (player.color == PlayerColor.BLACK) ? "♛" : "♕" ;
	}

	@Override
	public String getInitial() {
		return "Q";
	}
}