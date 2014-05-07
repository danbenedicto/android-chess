package model;

/**
 * 
 * @author Dan Benedicto
 *
 */
public abstract class ChessPiece {
	
	public Square[][] board;
	
	public Square loc;
	
	public Player player;
	
	protected int moves; // useful for validating pawn moves and for castling
	
	public ChessPiece(){
		moves = 0;
	}
	
	public abstract boolean canMoveToHook(Square to);
	
	protected abstract String getInitial();
	
	protected Move canMoveTo(Square to, boolean commit){
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
		
		if (player.king.isInCheck() || !commit){
			// move would leave king in check, so invalid. restore old state
			to.chessPiece = capture;
			if (capture != null) to.chessPiece.loc = to;
			loc = from;
			loc.chessPiece = this;
			if (player.king.isInCheck()){
				return null;
			}
		}
		
		// valid
		
		if (commit){
			moves++;
		}
		
		return new Move(this, capture, from, to);
	}
		
	public boolean canMoveTo(Square to){
		return (canMoveTo(to, false) != null);
	}
	
	public Move tryMoveTo(Square to){
		return canMoveTo(to, true);
	}
	
	protected boolean hasClearPathTo(Square to){
		
		if (loc.x == to.x && loc.y != to.y){
			int curr = Math.min(loc.y, to.y) + 1;
			int end = Math.max(loc.y, to.y) - 1;
			while (curr <= end){
				if (board[loc.x][curr++].chessPiece != null){
					return false;
				}
			}
			return true;
		}
		
		else if (loc.x != to.x && loc.y == to.y){
			int curr = Math.min(loc.x, to.x) + 1;
			int end = Math.max(loc.x, to.x) - 1;
			while (curr <= end){
				if (board[curr++][loc.y].chessPiece != null){
					return false;
				}
			}
			return true;
		}
		
		else if (Math.abs(loc.x - to.x) == Math.abs(loc.y - to.y)){
			int dx = (loc.x < to.x) ? 1 : -1;
			int dy = (loc.y < to.y) ? 1 : -1;
			int currX = loc.x + dx;
			int currY = loc.y + dy;
			
			while ((dx == 1 && currX < to.x) || (dx == -1 && currX > to.x)){
				if (board[currX][currY].chessPiece != null){
					return false;
				}
				currX += dx;
				currY += dy;
			}
			return true;
		}
		
		return false; // no straight line between squares
	}
	
	public abstract String getUnicode();
	
	public String toString(){
		return player.toString() + getInitial();
	}
}