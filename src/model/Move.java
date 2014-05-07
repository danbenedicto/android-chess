package model;

public class Move {
	
	public static enum Type {
		NORMAL, CASTLE, ENPASSANT
	}
	
	ChessPiece chessPiece;
	ChessPiece capture;
	Square source;
	Square destination;
	Type type;
	
	public Move(ChessPiece chessPiece, ChessPiece capture, Square source, Square destination) {
		this.chessPiece = chessPiece;
		this.capture = capture;
		this.source = source;
		this.destination = destination;
		this.type = Type.NORMAL; 
	}

	public Move(ChessPiece chessPiece, ChessPiece capture, Square source, Square destination, Type type) {
		this.chessPiece = chessPiece;
		this.capture = capture;
		this.source = source;
		this.destination = destination;
		this.type = type;
	}

}
