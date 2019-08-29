package student_player;

import java.util.AbstractMap;

import boardgame.Move;

import pentago_swap.PentagoPlayer;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoBoardState.Quadrant;
import pentago_swap.PentagoMove;

/** A player file submitted by a student. */
public class StudentPlayer extends PentagoPlayer {
	public static final int MIN = Integer.MIN_VALUE;
	public static final int MAX = Integer.MAX_VALUE;
    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260715042");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(PentagoBoardState boardState) {
        Move myMove;
        PentagoBoardState tryMove = (PentagoBoardState)boardState.clone();
        
        AbstractMap.SimpleEntry<Integer, PentagoMove> holder = Blocking.shouldIBlcok (tryMove, boardState.getTurnPlayer());
        
        if(holder != null) {
        	return holder.getValue();
        }
        
        // the four starting strategy  
        PentagoMove c1= new PentagoMove(1,1,Quadrant.TL,Quadrant.TR, boardState.getTurnPlayer());

        PentagoMove c2= new PentagoMove(1,4,Quadrant.TL,Quadrant.TR, boardState.getTurnPlayer());

        PentagoMove c3= new PentagoMove(4,1,Quadrant.TL,Quadrant.TR, boardState.getTurnPlayer());

        PentagoMove c4= new PentagoMove(4,4,Quadrant.TL,Quadrant.TR, boardState.getTurnPlayer());

        if(boardState.isLegal(c1)) {return c1;}

        else if (boardState.isLegal(c2)) {return c2;}

        else if (boardState.isLegal(c3)) {return c3;}

        else if (boardState.isLegal(c4)) {return c4;} 
        
        else {
	        myMove = AlphaBeta.pruning
	        		(tryMove, 3, boardState.getTurnPlayer(), MIN, MAX).getValue();
	        return myMove;
        }
    }
}