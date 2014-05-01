package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

import model.Player;
import model.PlayerColor;
import model.Square;
import android.graphics.Color;
import android.widget.TextView;

import com.example.hellochess.MainActivity;
import com.example.hellochess.R;

import controller.Controller;

/**
 * 
 * @author Dan Benedicto
 *
 */
public class AndroidView {
	
	private Controller controller;
	public MainActivity activity;
	private TextView[][] textViews;
	private Square[][] board;
	private HashMap<TextView, Square> viewSquares;
	private HashMap<Square, TextView> squareViews;
	
	public AndroidView(Controller controller, MainActivity activity, TextView[][] textViews){
		this.controller = controller;
		this.activity = activity;
		this.textViews = textViews;
		this.board = controller.game.board;
		this.viewSquares = new HashMap<TextView, Square>();
		this.squareViews = new HashMap<Square, TextView>();
		for (int y = 0; y < 8; y++){
			for (int x = 0; x < 8; x++){
				viewSquares.put(textViews[x][y], board[x][y]);
				squareViews.put(board[x][y], textViews[x][y]);
			}
		}
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
	
	public void processTouch(TextView view){
		controller.processTouch(viewSquares.get(view));
	}

	public void printBoard(){
		for (int y = 0; y < 8; y++){
			for (int x = 0; x < 8; x++){
				if (board[x][y].chessPiece != null){
					String pieceId = board[x][y].chessPiece.toString();
					int resourceId = activity.getResources().getIdentifier(pieceId, "string", activity.getPackageName());
					textViews[x][y].setText((String) activity.getResources().getText(resourceId));
				} else {
					textViews[x][y].setText("");
				}
				
				if ((x + y) % 2 == 0){
					textViews[x][y].setBackgroundColor(Color.parseColor("#FFFF00"));
				}
				else {
					textViews[x][y].setBackgroundColor(Color.parseColor("#FF0000"));
				}
			}
		}
		for (char file = 'a'; file <= 'h'; file++){
			System.out.print(" " + file + " ");
		}
		System.out.println("\n");
	}
	
	public void hintAt(ArrayList<Square> squares){
		for (Square s : squares){
			squareViews.get(s).setBackgroundColor(Color.GREEN);
		}
	}
	
	public void highlight(Square selected){
		squareViews.get(selected).setBackgroundColor(Color.GREEN);
	}
	
	public void printIllegal(){
		System.out.println("Illegal move, try again\n");
	}
	
	public void printCheck(){
		System.out.println("Check\n");
	}
}