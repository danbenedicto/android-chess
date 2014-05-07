package model;


/**
 * 
 * @author Dan Benedicto
 *
 */
public class Pawn extends ChessPiece {

	@Override
	public boolean canMoveToHook(Square to) {
		int dy = (player.color == PlayerColor.BLACK) ? 1 : -1;
		if (loc.x == to.x){
			// trying to move vertically
			return (to.chessPiece == null && (to.y == loc.y + dy || ((moves == 0) && (to.y == loc.y + 2*dy))));
		}

		if (Math.abs(to.x - loc.x) == 1 && (to.y == loc.y + dy)){
			if (to.chessPiece != null){
				return true;
			}

			ChessPiece cp = board[to.x][loc.y].chessPiece;
			if (cp != null && cp instanceof Pawn && cp.moves == 1){
				return (player.color == PlayerColor.BLACK && to.y == 5) || (player.color == PlayerColor.WHITE && to.y == 2);
				// 5 and 2 are the y values that imply valid en passant captures
			}
		}
		
		return false;
	}

	@Override
	public Move canMoveTo(Square to, boolean commit){
		// override for en passant

		if (loc.equals(to) || (to.chessPiece != null && player.equals(to.chessPiece.player))){
			return null;
		}
		
		if (!canMoveToHook(to)){
			return null;
		}
		
		// temporarily move to see if king is safe
		Square from = this.loc;
		from.chessPiece = null;
		
		ChessPiece capture = to.chessPiece;
		if (capture != null) capture.loc = null;	// consider any "to" piece killed
		
		to.chessPiece = this;
		this.loc = to;
		
		boolean inCheck = player.king.isInCheck();
		
		if (inCheck || !commit){
			// move would leave king in check, so invalid. restore old state
			to.chessPiece = capture;
			if (capture != null) to.chessPiece.loc = to;
			loc = from;
			loc.chessPiece = this;
			if (inCheck){
				return null;
			}
		}
		
		// must be true if canMoveTo() returned true
		boolean enPassant = (capture == null && to.x != from.x); 
		
		if (commit && enPassant){
			// must be en passant if no chess piece on target but canMoveTo() returned true
			capture = board[to.x][from.y].chessPiece;
			capture.loc = null;
			board[to.x][from.y].chessPiece = null;
		}

		if (commit){
			moves++;
		}
		
		if ((player.color == PlayerColor.WHITE && loc.y == 0) || (player.color == PlayerColor.BLACK && loc.y == 7)){
			// promote to queen
			player.pieces.remove(this);
			Queen q = new Queen();
			q.board = board;
			q.loc = loc;
			q.player = player;
			loc.chessPiece = q;
			player.pieces.add(q);
		}
		
		if (enPassant){
			return new Move(this, capture, from, to, Move.Type.ENPASSANT);
		} else {
			return new Move(this, capture, from, to);
		}
	}
	
	@Override
	public String getUnicode() {
		return (player.color == PlayerColor.BLACK) ? "♟" : "♙" ;
	}

	@Override
	public String getInitial() {
		return "p";
	}
}