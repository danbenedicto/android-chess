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
	
	public abstract boolean canMoveTo(Square to);
	
	protected abstract String getInitial();
	
	public boolean tryMoveTo(Square to){
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
		
		// valid

		moves++;
		
		return true;
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