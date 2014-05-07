package model;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class Knight extends ChessPiece {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean canMoveToHook(Square to) {
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