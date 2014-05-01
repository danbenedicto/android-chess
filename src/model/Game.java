package model;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class Game {

	public Square[][] board;
	public Player white;
	public Player black;
	
	public Game(){
		
		board = new Square[8][8];
		white = new Player(PlayerColor.WHITE);
		black = new Player(PlayerColor.BLACK);
		white.opponent = black;
		black.opponent = white;
		
		for (int r = 0; r < 8; r++){
			for (int c = 0; c < 8; c++){
				board[r][c] = new Square(r, c);
			}
		}
		
		int x = 0;
		for (ChessPiece cp : new ChessPiece[] { 
				new Rook(), new Knight(), new Bishop(), new Queen(),
				new King(), new Bishop(), new Knight(), new Rook() }) {
			cp.board = board;
			cp.loc = board[x++][0];
			cp.loc.chessPiece = cp;
			cp.player = black;
			black.pieces.add(cp);
		}
		black.king = (King) black.pieces.get(4);
		
		x = 0;
		for (ChessPiece cp : new ChessPiece[] { 
				new Rook(), new Knight(), new Bishop(), new Queen(),
				new King(), new Bishop(), new Knight(), new Rook() }) {
			cp.board = board;
			cp.loc = board[x++][7];
			cp.loc.chessPiece = cp;
			cp.player = white;
			white.pieces.add(cp);
		}
		white.king = (King) white.pieces.get(4);
		
		Player tmp = black;
		for (int y = 1; y == 1 || y == 6; y += 5){
			for (x = 0; x < 8; x++){
				Pawn p = new Pawn();
				p.board = board;
				p.loc = board[x][y];
				p.loc.chessPiece = p;
				p.player = tmp;
				tmp.pieces.add(p);
			}
			tmp = white;
		}
	}
}
