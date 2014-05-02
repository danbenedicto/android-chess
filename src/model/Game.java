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
	public Player currentPlayer;
	
	/**
	 * Creates a Game object with a board containing chess pieces, a white and a black player, and current player equal to white.
	 */
	public Game(){
		this.board = new Square[8][8];
		this.white = new Player(PlayerColor.WHITE);
		this.black = new Player(PlayerColor.BLACK);
		this.white.opponent = black;
		this.black.opponent = white;
		this.currentPlayer = white;	// white goes first
		
		// initialize the board with piece-less squares
		for (int r = 0; r < 8; r++){
			for (int c = 0; c < 8; c++){
				board[r][c] = new Square(r, c);
			}
		}
		
		setupBackPieces(black, 0);
		setupBackPieces(white, 7);
		
		setupPawns(black, 1);
		setupPawns(white, 6);
	}
	
	/**
	 * Used when a new game is created to create a player's non-pawn pieces (rook, knight, etc) in the given row. 
	 * @param p	The player whose pieces are to be created
	 * @param row The row where the pieces should be placed
	 */
	private void setupBackPieces(Player p, int row){
		
		ChessPiece[] piecesToPlace = new ChessPiece[] { 
			new Rook(), new Knight(), new Bishop(), new Queen(), new King(), new Bishop(), new Knight(), new Rook() 
		};
		
		for (int i = 0; i < 8; i++){
			ChessPiece cp = piecesToPlace[i];
			cp.board = board;
			cp.loc = board[i][row];
			cp.loc.chessPiece = cp;
			cp.player = p;
			p.pieces.add(cp);
		}
		
		p.king = (King) p.pieces.get(4);
	}
	
	
	/**
	 * Used when a new game is created to create a player's pawns in the given row. 
	 * @param p	The player whose pawns are to be created
	 * @param row The row where the pawns should be placed
	 */
	private void setupPawns(Player p, int row){
		for (int x = 0; x < 8; x++){
			Pawn pawn = new Pawn();
			pawn.board = board;
			pawn.loc = board[x][row];
			pawn.loc.chessPiece = pawn;
			pawn.player = p;
			p.pieces.add(pawn);
		}
	}
}
