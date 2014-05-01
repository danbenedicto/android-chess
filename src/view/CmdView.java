package view;

import model.*;

import java.util.Scanner;
import java.util.StringTokenizer;

import controller.Controller;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class CmdView {
	
	private Controller controller;
	
	public CmdView(Controller controller){
		this.controller = controller;
	}
	
	public void getMove(Player player){
		if (player.color == PlayerColor.BLACK){
			System.out.print("Black's move: ");
		} else {
			System.out.print("White's move: ");
		}
		
		getMove();
	}
	
	public void getMove(){
		Scanner sc = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(sc.nextLine());
		String[] tokens = new String[st.countTokens()];
		
		int i = 0;
		while (st.hasMoreTokens()){
			tokens[i++] = st.nextToken();
		}
		
		System.out.println();
		
		controller.processMove(tokens);
	}

	public void printBoard(Square[][] board){
		for (int y = 0; y < 8; y++){
			for (int x = 0; x < 8; x++){
				if (board[x][y].chessPiece != null){
					System.out.print(board[x][y].chessPiece + " ");
				}
				else if ((x + y) % 2 == 0){
					System.out.print("   ");
				}
				else {
					System.out.print("## ");
				}
			}
			System.out.println(8 - y);
		}
		for (char file = 'a'; file <= 'h'; file++){
			System.out.print(" " + file + " ");
		}
		System.out.println("\n");
	}
	
	public void printIllegal(){
		System.out.println("Illegal move, try again\n");
	}
	
	public void printCheck(){
		System.out.println("Check\n");
	}
}