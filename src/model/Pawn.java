package model;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class Pawn extends ChessPiece {

	@Override
	public boolean canMoveTo(Square to) {
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
	public boolean tryMoveTo(Square to){
		// override for en passant

		if (loc.equals(to) || (to.chessPiece != null && player.equals(to.chessPiece.player))){
			return false;
		}
		
		if (!canMoveTo(to)){
			return false;
		}
		
		// temporarily move to see if king is safe
		Square oldLoc = this.loc;
		oldLoc.chessPiece = null;
		
		ChessPiece tempPiece = to.chessPiece;
		if (tempPiece != null) tempPiece.loc = null;	// consider any "to" piece killed
		
		to.chessPiece = this;
		this.loc = to;
		
		if (player.king.isInCheck()){
			// move would leave king in check, so invalid. restore old state
			to.chessPiece = tempPiece;
			if (tempPiece != null) to.chessPiece.loc = to;
			loc = oldLoc;
			loc.chessPiece = this;
			return false;
		}
		
		if (tempPiece == null){
			if (Math.abs(to.x - oldLoc.x) == 1){
				System.out.println("En passant!!");
				// must be en passant if no chess piece on target but canMoveTo() returned true
				board[to.x][oldLoc.y].chessPiece.loc = null;
				board[to.x][oldLoc.y].chessPiece = null; 
			}
		}

		moves++;
		
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
		
		return true;
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