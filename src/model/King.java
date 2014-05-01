package model;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class King extends ChessPiece {
	@Override
	public boolean canMoveTo(Square to) {
		if (Math.abs(to.x - loc.x) <= 1 && Math.abs(to.y - loc.y) <= 1){
			return true;
		}
		
		// maybe castle
		
		if (moves != 0 || to.y != loc.y || Math.abs(to.x - loc.x) != 2){
			return false;
		}
		
		// these squares are valid for castling
		
		int rookX = (to.x == 6) ? 7 : 0;	// where a rook must be
		ChessPiece rook = board[rookX][loc.y].chessPiece;
		
		if (rook == null || rook.moves != 0 || !(rook instanceof Rook)){
			return false;
		} 
		
		if (rookX == 7){
			return board[5][loc.y].chessPiece == null && board[6][loc.y].chessPiece == null; 
		} else {
			return board[1][loc.y].chessPiece == null && board[2][loc.y].chessPiece == null && board[3][loc.y].chessPiece == null;
		}
		
	}

	@Override
	public boolean tryMoveTo(Square to) {
		if (loc.equals(to) || (to.chessPiece != null && player.equals(to.chessPiece.player))){
			return false;
		}
		
		if (!canMoveTo(to)){
			return false;
		}
		
		int oldRookX = -1;
		int newRookX = -1;
		if (Math.abs(to.x - loc.x) == 2){
			// let (rook == null) mean this is not a castle attempt. otherwise it is a castle attempt and rook is the involved rook
			if (to.x == 6){
				oldRookX = 7;
				newRookX = 5;
			} else {
				oldRookX = 0;
				newRookX = 2;
			}
		}
		
		// temporarily move to see if king is safe
		Square oldLoc = this.loc;
		oldLoc.chessPiece = null;
		
		ChessPiece tempPiece = to.chessPiece;
		if (tempPiece != null) tempPiece.loc = null;	// consider any "to" piece killed
		
		to.chessPiece = this;
		this.loc = to;
		
		if (oldRookX != -1){
			board[newRookX][loc.y].chessPiece = board[oldRookX][loc.y].chessPiece;
			board[newRookX][loc.y].chessPiece.loc = board[newRookX][loc.y];
			board[oldRookX][loc.y].chessPiece = null;
		}
		
		if (isInCheck()){
			// move would leave king in check, so invalid. restore old state
			to.chessPiece = tempPiece;
			if (tempPiece != null) to.chessPiece.loc = to;
			loc = oldLoc;
			loc.chessPiece = this;
			if (oldRookX != -1){
				board[oldRookX][loc.y].chessPiece = board[newRookX][loc.y].chessPiece;
				board[oldRookX][loc.y].chessPiece.loc = board[oldRookX][loc.y];
				board[newRookX][loc.y].chessPiece = null;
			}
			return false;
		}
		
		// valid

		moves++;
		
		return true;
	}
	
	@Override
	public String getUnicode() {
		return (player.color == PlayerColor.BLACK) ? "♚" : "♔" ;
	}


	@Override
	public String getInitial() {
		return "K";
	}
	
	public boolean isInCheck(){
		for (ChessPiece cp : player.opponent.pieces){
			if (cp.loc != null && cp.canMoveTo(this.loc)){
				return true;
			}
		}
		return false;
	}
}