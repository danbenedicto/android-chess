package model;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class King extends ChessPiece {
	@Override
	public boolean canMoveToHook(Square to) {
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
	public Move canMoveTo(Square to, boolean commit) {
		if (loc.equals(to) || (to.chessPiece != null && player.equals(to.chessPiece.player))){
			return null;
		}
		
		if (!canMoveToHook(to)){
			return null;
		}
		
		boolean castleAttempt = Math.abs(to.x - loc.x) == 2;
		
		Square rookFrom = null;
		Square rookTo = null;
		ChessPiece castlingRook = null;
		
		if (castleAttempt){
			if (to.x == 6){
				rookFrom = board[7][loc.y];
				rookTo = board[5][loc.y];
			} else {
				rookFrom = board[0][loc.y];
				rookTo = board[2][loc.y];
			}
			
			castlingRook = rookFrom.chessPiece;
		}
		
		ChessPiece capture = to.chessPiece;
		if (capture != null) capture.loc = null;	// consider any piece on the destination Square killed
		
		// move king to the new location
		Square from = this.loc;		// remember old location
		from.chessPiece = null;		// take it off the old Square
		to.chessPiece = this;		// put it on the new Square
		this.loc = to;
		
		if (castleAttempt){
			rookFrom.chessPiece = null;
			rookTo.chessPiece = castlingRook;
			castlingRook.loc = rookTo;
		}
		
		boolean inCheck = isInCheck();
		
		if (inCheck || !commit){
			// move would leave king in check, so invalid. restore old state
			to.chessPiece = capture;
			if (capture != null) to.chessPiece.loc = to;
			
			loc = from;
			loc.chessPiece = this;
			
			if (castleAttempt){
				rookFrom.chessPiece = castlingRook;
				castlingRook.loc = rookFrom;
				rookTo.chessPiece = null;
			}
			
			if (inCheck){
				return null;
			}
		}
		
		// valid
		
		if (commit){
			moves++;
		}
		
		if (castleAttempt){
			return new Move(this, capture, from, to, Move.Type.CASTLE);
		} else {
			return new Move(this, capture, from, to);
		}
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
			if (cp.loc != null && cp.canMoveToHook(this.loc)){
				return true;
			}
		}
		return false;
	}
}