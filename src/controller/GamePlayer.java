package controller;

import model.Game;
import model.Move;
import view.AndroidView;

public class GamePlayer {

	private Game game;
	private AndroidView view;
	
	private int currentFrame;
	
	public GamePlayer(Game game, AndroidView view){
		this.game = game;
		this.view = view;
	}
	
	public void goForward(){
		if (!canGoForward()) return;
		
		Move move = game.moves.get(currentFrame++);
		
		if (move.type == Move.Type.NORMAL) {
			
		}
	}
	
	public boolean canGoForward(){
		return (currentFrame < game.moves.size() - 1); 
	}
	
	public boolean canGoBackward(){
		return (currentFrame > 0);
	}
}
