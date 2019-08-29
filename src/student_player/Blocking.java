package student_player;

import java.util.AbstractMap;

//import java.util.AbstractMap;
//import java.util.ArrayList;
//import java.util.Arrays;

import boardgame.Board;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoBoardState.Piece;
import pentago_swap.PentagoBoardState.Quadrant;
import pentago_swap.PentagoCoord;
import pentago_swap.PentagoMove;

public class Blocking {
	public static final int BOARD_SIZE = 6;
	public static final int MIN = Integer.MIN_VALUE;
	public static final int MAX = Integer.MAX_VALUE;
	public static final int PENALTY = 10000;
	public static final Piece WHITE = PentagoBoardState.Piece.WHITE ;
	public static final Piece BLACK = PentagoBoardState.Piece.BLACK ;
	
	public static Quadrant getQuadrant(PentagoCoord coord) {

		Quadrant q = null;
		if (coord.getX() < 3 && coord.getY() < 3) {
			q = Quadrant.TL;
		} else if (coord.getX() >= 3 && coord.getY() >= 3) {
			q = Quadrant.BR;
		} else if (coord.getX() >= 3 && coord.getY() < 3) {
			q = Quadrant.BL;
		} else {
			q = Quadrant.TR;
		}
		return q;
	}
	
	public static AbstractMap.SimpleEntry<Quadrant, Quadrant> oppoQuadrant(Quadrant q1, Quadrant q2) {
//		Quadrant q = null;
		if ((q1 == Quadrant.TL && q2 == Quadrant.TR) ||(q2 == Quadrant.TL && q1 == Quadrant.TR) ) {
			return new AbstractMap.SimpleEntry<Quadrant, Quadrant>(Quadrant.BL, Quadrant.BR);
		} else if ((q1 == Quadrant.TL && q2 == Quadrant.BL) || (q2 == Quadrant.TL && q1 == Quadrant.BL)) {
			return new AbstractMap.SimpleEntry<Quadrant, Quadrant>(Quadrant.TR, Quadrant.BR);
		} else if ((q1 == Quadrant.TL && q2 == Quadrant.BR) || (q2 == Quadrant.TL && q1 == Quadrant.BR)) {
			return new AbstractMap.SimpleEntry<Quadrant, Quadrant>(Quadrant.TR, Quadrant.BL);
		} else if ((q1 == Quadrant.TR && q2 == Quadrant.BR) || (q2 == Quadrant.TR && q1 == Quadrant.BR)) {
			return new AbstractMap.SimpleEntry<Quadrant, Quadrant>(Quadrant.TL, Quadrant.BL);
		} else if ((q1 == Quadrant.TR && q2 == Quadrant.BL) || (q2 == Quadrant.TR && q1 == Quadrant.BL)){
			return new AbstractMap.SimpleEntry<Quadrant, Quadrant>(Quadrant.TL, Quadrant.BR);
		} else {
			return new AbstractMap.SimpleEntry<Quadrant, Quadrant>(Quadrant.TL, Quadrant.TR);
		}
	}
	
    public static AbstractMap.SimpleEntry<Integer, PentagoMove> shouldIBlcok (PentagoBoardState state, int player) {
    	
    	int opponent = (player == PentagoBoardState.WHITE) ? PentagoBoardState.BLACK: PentagoBoardState.WHITE;
    	int result1 = 0;
    	int result2 = 0;

    	if(opponent == 1) { //so I am WHITE
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 4; j++) {
                	////////////////////////////////////////////////////////////////////
                	//System.out.println("I hope I m at least here.....");
                	if ((j<3) && state.getPieceAt(i,j) == WHITE && 
                			state.getPieceAt(i,j+1) == WHITE && state.getPieceAt(i,j+2) == WHITE && state.getPieceAt(i,j+3) == WHITE) {
                		int xPos = i;
                		int yPos = j-1;
                		boolean invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
                		if(!invalid) {
                			PentagoCoord coo = new PentagoCoord(xPos, yPos);
                			Quadrant Qtemp1 = getQuadrant(coo);
                			PentagoCoord coo2 = new PentagoCoord(xPos, yPos+3);
                			Quadrant Qtemp2 = getQuadrant(coo2);
                			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
                			Quadrant q1 = holder.getKey();
                			Quadrant q2 = holder.getValue();
                			
                			result1 = MAX;
                			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
                			if(state.isLegal(m)) {
//                				System.out.println("84 - Here we execute the blocking function! "+m.toPrettyString());
                				return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result1, m);
                			}
                			else {
                				result1 = 0;
                			}
                		} 
                		
                		xPos = i;
                		yPos = j+4;
                		invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
                		if(!invalid) {
                			PentagoCoord coo = new PentagoCoord(xPos, yPos);
                			Quadrant Qtemp1 = getQuadrant(coo);
                			PentagoCoord coo2 = new PentagoCoord(xPos, yPos-3);
                			Quadrant Qtemp2 = getQuadrant(coo2);
                			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
                			Quadrant q1 = holder.getKey();
                			Quadrant q2 = holder.getValue();
                			
                			result1 = MAX;
                			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
                			if(state.isLegal(m)) {
//                				System.out.println("107 - Here we execute the blocking function! "+m.toPrettyString());
	                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result1, m);
                			}
                			else {
                				result1 = 0;
                			}
                		}
                	}//checking if I am gonna win
                	
                	if(state.getPieceAt(i,j) == BLACK && state.getPieceAt(i,j+1) == BLACK && state.getPieceAt(i,j+2) == BLACK) {
                		int xPos = i;
                		int yPos = j-1;
                		boolean invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
                		if(!invalid) {
                			PentagoCoord coo = new PentagoCoord(xPos, yPos);
                			Quadrant Qtemp1 = getQuadrant(coo);
                			PentagoCoord coo2 = new PentagoCoord(xPos, yPos+3);
                			Quadrant Qtemp2 = getQuadrant(coo2);
                			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
                			Quadrant q1 = holder.getKey();
                			Quadrant q2 = holder.getValue();
                			
                			result1 += Math.pow(PENALTY, 3);
                			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
                			if(state.isLegal(m)) {
//                				System.out.println("132 - Here we execute the blocking function! "+m.toPrettyString());
                				return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result1, m);
                			}
                			else {
                				result1 = 0;
                			}
                		}
                		
                		xPos = i;
                		yPos = j+3;
                		invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
                		if(!invalid) {
                			PentagoCoord coo = new PentagoCoord(xPos, yPos);
                			Quadrant Qtemp1 = getQuadrant(coo);
                			PentagoCoord coo2 = new PentagoCoord(xPos, yPos-3);
                			Quadrant Qtemp2 = getQuadrant(coo2);
                			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
                			Quadrant q1 = holder.getKey();
                			Quadrant q2 = holder.getValue();
                			
                			result1 += Math.pow(PENALTY, 3);
                			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
                			if(state.isLegal(m)) {
//                				System.out.println("155 - Here we execute the blocking function! "+m.toPrettyString());
	                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result1, m);
                			}
                			else {
                				result1 = 0;
                			}
                		}
                    }//checking opponent
                }
            }       

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 4; j++) {
                	////////////////////////////////////////////////////////////
                	if ((j<3) && state.getPieceAt(j,i) == WHITE && 
                			state.getPieceAt(j+1,i) == WHITE && state.getPieceAt(j+2,i) == WHITE && state.getPieceAt(j+3,i) == WHITE) {
                		int xPos = j-1;
                		int yPos = i;
                		boolean invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
                		if(!invalid) {
                			PentagoCoord coo = new PentagoCoord(xPos, yPos);
                			Quadrant Qtemp1 = getQuadrant(coo);
                			PentagoCoord coo2 = new PentagoCoord(xPos+3, yPos);
                			Quadrant Qtemp2 = getQuadrant(coo2);
                			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
                			Quadrant q1 = holder.getKey();
                			Quadrant q2 = holder.getValue();
                			
                			result1 = MAX;
                			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
                			if(state.isLegal(m)) {
//                				System.out.println("188 - Here we execute the blocking function! "+m.toPrettyString());
                				return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result1, m);
                			}
                			else {
                				result1 = 0;
                			}
                		}
                		
                		xPos = j+4;
                		yPos = i;
                		invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
                		if(!invalid) {
                			PentagoCoord coo = new PentagoCoord(xPos, yPos);
                			Quadrant Qtemp1 = getQuadrant(coo);
                			PentagoCoord coo2 = new PentagoCoord(xPos-3, yPos);
                			Quadrant Qtemp2 = getQuadrant(coo2);
                			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
                			Quadrant q1 = holder.getKey();
                			Quadrant q2 = holder.getValue();
                			
                			result1 = MAX;
                			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
                			if(state.isLegal(m)) {
//                				System.out.println("211 - Here we execute the blocking function! "+m.toPrettyString());
	                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result1, m);
                			}
                			else {
                				result1 = 0;
                			}
                		}
                	}//checking if I am gonna win
                	if(state.getPieceAt(j,i) == BLACK && state.getPieceAt(j+1,i) == BLACK && state.getPieceAt(j+2,i) == BLACK) {
                		int xPos = j-1;
                		int yPos = i;
                		boolean invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
                		if(!invalid) {
                			PentagoCoord coo = new PentagoCoord(xPos, yPos);
                			Quadrant Qtemp1 = getQuadrant(coo);
                			PentagoCoord coo2 = new PentagoCoord(xPos+3, yPos);
                			Quadrant Qtemp2 = getQuadrant(coo2);
                			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
                			Quadrant q1 = holder.getKey();
                			Quadrant q2 = holder.getValue();
                			
                			result1 += Math.pow(PENALTY, 3);
                			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
                			if(state.isLegal(m)) {
//                				System.out.println("235 - Here we execute the blocking function! "+m.toPrettyString());
	                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result1, m);
                			}
                			else {
                				result1 = 0;
                			}
                		}
                		xPos = j+3;
                		yPos = i;
                		invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
                		if(!invalid) {
                			PentagoCoord coo = new PentagoCoord(xPos, yPos);
                			Quadrant Qtemp1 = getQuadrant(coo);
                			PentagoCoord coo2 = new PentagoCoord(xPos-3, yPos);
                			Quadrant Qtemp2 = getQuadrant(coo2);
                			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
                			Quadrant q1 = holder.getKey();
                			Quadrant q2 = holder.getValue();
                			
                			result1 += Math.pow(PENALTY, 3);
                			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
                			if(state.isLegal(m)) {
//                				System.out.println("257 - Here we execute the blocking function! "+m.toPrettyString());
	                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result1, m);
                			}
                			else {
                				result1 = 0;
                			}
                		}
                    }//checking opponent	
                }
            }        

            for (int i = 0; i < 4; i++) {
            	////////////////////////////////////////////////////////////
            	if((i<3) && state.getPieceAt(i,i) == WHITE && 
                		state.getPieceAt(i+1,i+1) == WHITE && state.getPieceAt(i+2,i+2) == WHITE && state.getPieceAt(i+3,i+3) == WHITE) {
                	int xPos = i-1;
            		int yPos = i-1;
            		boolean invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
            		if(!invalid) {
            			PentagoCoord coo = new PentagoCoord(xPos, yPos);
            			Quadrant Qtemp1 = getQuadrant(coo);
            			PentagoCoord coo2 = new PentagoCoord(xPos+3, yPos+3);
            			Quadrant Qtemp2 = getQuadrant(coo2);
            			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
            			Quadrant q1 = holder.getKey();
            			Quadrant q2 = holder.getValue();
            			
            			result1 = MAX;
            			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
            			if(state.isLegal(m)) {
//            				System.out.println("289 - Here we execute the blocking function! "+m.toPrettyString());
                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result1, m);
            			}
            			else {
            				result1 = 0;
            			}
            		}
            		
            		xPos = i+4;
            		yPos = i+4;
            		invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
            		if(!invalid) {
            			PentagoCoord coo = new PentagoCoord(xPos, yPos);
            			Quadrant Qtemp1 = getQuadrant(coo);
            			PentagoCoord coo2 = new PentagoCoord(xPos-3, yPos-3);
            			Quadrant Qtemp2 = getQuadrant(coo2);
            			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
            			Quadrant q1 = holder.getKey();
            			Quadrant q2 = holder.getValue();
            			
            			result1 = MAX;
            			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
            			if(state.isLegal(m)) {
//            				System.out.println("312 - Here we execute the blocking function! "+m.toPrettyString());
                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result1, m);
            			}
            			else {
            				result1 = 0;
            			}
            		}
                }//checking myself
                if(state.getPieceAt(i,i) == BLACK && state.getPieceAt(i+1,i+1) == BLACK && state.getPieceAt(i+2,i+2) == BLACK) {
                	int xPos = i-1;
            		int yPos = i-1;
            		boolean invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
            		if(!invalid) {
            			PentagoCoord coo = new PentagoCoord(xPos, yPos);
            			Quadrant Qtemp1 = getQuadrant(coo);
            			PentagoCoord coo2 = new PentagoCoord(xPos+3, yPos+3);
            			Quadrant Qtemp2 = getQuadrant(coo2);
            			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
            			Quadrant q1 = holder.getKey();
            			Quadrant q2 = holder.getValue();
            			
            			result1 += Math.pow(PENALTY, 3);
            			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
            			if(state.isLegal(m)) {
//            				System.out.println("336 - Here we execute the blocking function! "+m.toPrettyString());
                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result1, m);
            			}
            			else {
            				result1 = 0;
            			}
            		}
            		
            		xPos = i+3;
            		yPos = i+3;
            		invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
            		if(!invalid) {
            			PentagoCoord coo = new PentagoCoord(xPos, yPos);
            			Quadrant Qtemp1 = getQuadrant(coo);
            			PentagoCoord coo2 = new PentagoCoord(xPos-3, yPos-3);
            			Quadrant Qtemp2 = getQuadrant(coo2);
            			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
            			Quadrant q1 = holder.getKey();
            			Quadrant q2 = holder.getValue();
            			
            			result1 += Math.pow(PENALTY, 3);
            			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
            			if(state.isLegal(m)) {
//            				System.out.println("359 - Here we execute the blocking function! "+m.toPrettyString());
                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result1, m);
            			}
            			else {
            				result1 = 0;
            			}
            		}
                }//checking opponent  
            }

            for (int i = 0; i < 4; i++) {
            	//////////////////////////////////////////////////
            	if((i<3) && state.getPieceAt(i,5-i) == WHITE && 
                		state.getPieceAt(i+1,4-i) == WHITE && state.getPieceAt(i+2,3-i) == WHITE && state.getPieceAt(i+3,2-i) == WHITE) {
                	int xPos = i-1;
            		int yPos = 6-i;
            		boolean invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
            		if(!invalid) {
            			PentagoCoord coo = new PentagoCoord(xPos, yPos);
            			Quadrant Qtemp1 = getQuadrant(coo);
            			PentagoCoord coo2 = new PentagoCoord(xPos+3, yPos-3);
            			Quadrant Qtemp2 = getQuadrant(coo2);
            			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
            			Quadrant q1 = holder.getKey();
            			Quadrant q2 = holder.getValue();
            			
            			result1 = MAX;
            			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
            			if(state.isLegal(m)) {
//            				System.out.println("390 - Here we execute the blocking function! "+m.toPrettyString());
                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result1, m);
            			}
            			else {
            				result1 = 0;
            			}
            		}
            		
            		xPos = i+4;
            		yPos = 1-i;
            		invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
            		if(!invalid) {
            			PentagoCoord coo = new PentagoCoord(xPos, yPos);
            			Quadrant Qtemp1 = getQuadrant(coo);
            			PentagoCoord coo2 = new PentagoCoord(xPos-3, yPos+3);
            			Quadrant Qtemp2 = getQuadrant(coo2);
            			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
            			Quadrant q1 = holder.getKey();
            			Quadrant q2 = holder.getValue();
            			
            			result1 = MAX;
            			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
            			if(state.isLegal(m)) {
//            				System.out.println("413 - Here we execute the blocking function! "+m.toPrettyString());
                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result1, m);
            			}
            			else {
            				result1 = 0;
            			}
            		}
                }//checking myself
                if(state.getPieceAt(i,5-i) == BLACK && state.getPieceAt(i+1,4-i) == BLACK && state.getPieceAt(i+2,3-i) == BLACK) {
                	int xPos = i-1;
            		int yPos = 6-i;
            		boolean invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
            		if(!invalid) {
            			PentagoCoord coo = new PentagoCoord(xPos, yPos);
            			Quadrant Qtemp1 = getQuadrant(coo);
            			PentagoCoord coo2 = new PentagoCoord(xPos+3, yPos-3);
            			Quadrant Qtemp2 = getQuadrant(coo2);
            			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
            			Quadrant q1 = holder.getKey();
            			Quadrant q2 = holder.getValue();
            			
            			result1 += Math.pow(PENALTY, 3);
            			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
            			if(state.isLegal(m)) {
//            				System.out.println("437 - Here we execute the blocking function! "+m.toPrettyString());
                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result1, m);
            			}
            			else {
            				result1 = 0;
            			}
            		}
            		
            		xPos = i+3;
            		yPos = 2-i;
            		invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
            		if(!invalid) {
            			PentagoCoord coo = new PentagoCoord(xPos, yPos);
            			Quadrant Qtemp1 = getQuadrant(coo);
            			PentagoCoord coo2 = new PentagoCoord(xPos-3, yPos+3);
            			Quadrant Qtemp2 = getQuadrant(coo2);
            			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
            			Quadrant q1 = holder.getKey();
            			Quadrant q2 = holder.getValue();
            			
            			result1 += Math.pow(PENALTY, 3);
            			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
            			if(state.isLegal(m)) {
//            				System.out.println("460 - Here we execute the blocking function! "+m.toPrettyString());
                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result1, m);
            			}
            			else {
            				result1 = 0;
            			}
            		}
                }//checking opponent
                
                
            }
            return null;
        }// if opponent = 1 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	else { // if opponent = 0
    		 for (int i = 0; i < 6; i++) {
                 for (int j = 0; j < 4; j++) {
                	 ///////////////////////////////////////////////////////////////
                	 if ((j<3) && state.getPieceAt(i,j) == BLACK && 
                  			state.getPieceAt(i,j+1) == BLACK && state.getPieceAt(i,j+2) == BLACK && state.getPieceAt(i,j+3) == BLACK) {
                  		int xPos = i;
                  		int yPos = j-1;
                  		boolean invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
                  		if(!invalid) {
                  			PentagoCoord coo = new PentagoCoord(xPos, yPos);
                  			Quadrant Qtemp1 = getQuadrant(coo);
                  			PentagoCoord coo2 = new PentagoCoord(xPos, yPos+3);
                  			Quadrant Qtemp2 = getQuadrant(coo2);
                  			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
                  			Quadrant q1 = holder.getKey();
                  			Quadrant q2 = holder.getValue();
                  			
                  			result2 = MAX;
                  			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
                  			if(state.isLegal(m)) {
//                  				System.out.println("494 - Here we execute the blocking function! "+m.toPrettyString());
                  				return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result2, m);
                  			}
                  			else {
                  				result2 = 0;
                  			}
                  		}
                  		
                  		xPos = i;
                  		yPos = j+4;
                  		invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
                  		if(!invalid) {
                  			PentagoCoord coo = new PentagoCoord(xPos, yPos);
                  			Quadrant Qtemp1 = getQuadrant(coo);
                  			PentagoCoord coo2 = new PentagoCoord(xPos, yPos-3);
                  			Quadrant Qtemp2 = getQuadrant(coo2);
                  			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
                  			Quadrant q1 = holder.getKey();
                  			Quadrant q2 = holder.getValue();
                  			
                  			result2 = MAX;
                  			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
                  			if(state.isLegal(m)) {
//                  				System.out.println("517 - Here we execute the blocking function! "+m.toPrettyString());
  	                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result2, m);
                  			}
                  			else {
                  				result2 = 0;
                  			}
                  		}
                  	}//checking if I am gonna win
                	 
                 	if(state.getPieceAt(i,j) == WHITE && state.getPieceAt(i,j+1) == WHITE && state.getPieceAt(i,j+2) == WHITE) {
                 		int xPos = i;
                 		int yPos = j-1;
                 		boolean invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
                 		if(!invalid) {
                 			PentagoCoord coo = new PentagoCoord(xPos, yPos);
                 			Quadrant Qtemp1 = getQuadrant(coo);
                 			PentagoCoord coo2 = new PentagoCoord(xPos, yPos+3);
                 			Quadrant Qtemp2 = getQuadrant(coo2);
                 			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
                 			Quadrant q1 = holder.getKey();
                 			Quadrant q2 = holder.getValue();
                 			
                 			result2 += Math.pow(PENALTY, 3);
                 			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
                 			if(state.isLegal(m)) {
//                 				System.out.println("542 - Here we execute the blocking function! "+m.toPrettyString());
                 				return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result2, m);
                 			}
                 			else {
                 				result2 = 0;
                 			}
                 		}
                 		
                 		xPos = i;
                 		yPos = j+3;
                 		invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
                 		if(!invalid) {
                 			PentagoCoord coo = new PentagoCoord(xPos, yPos);
                 			Quadrant Qtemp1 = getQuadrant(coo);
                 			PentagoCoord coo2 = new PentagoCoord(xPos, yPos-3);
                 			Quadrant Qtemp2 = getQuadrant(coo2);
                 			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
                 			Quadrant q1 = holder.getKey();
                 			Quadrant q2 = holder.getValue();
                 			
                 			result2 += Math.pow(PENALTY, 3);
                 			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
                 			if(state.isLegal(m)) {
//                 				System.out.println("565 - Here we execute the blocking function! "+m.toPrettyString());
 	                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result2, m);
                 			}
                 			else {
                 				result2 = 0;
                 			}
                 		}
                     }//checking opponent
                 	
                 	
                 }
             }       

             for (int i = 0; i < 6; i++) {
                 for (int j = 0; j < 4; j++) {
                	 /////////////////////////////////////////////////
                	 if ((j<3) && state.getPieceAt(j,i) == BLACK && 
                  			state.getPieceAt(j+1,i) == BLACK && state.getPieceAt(j+2,i) == BLACK && state.getPieceAt(j+3,i) == BLACK) {
                  		int xPos = j-1;
                  		int yPos = i;
                  		boolean invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
                  		if(!invalid) {
                  			PentagoCoord coo = new PentagoCoord(xPos, yPos);
                  			Quadrant Qtemp1 = getQuadrant(coo);
                  			PentagoCoord coo2 = new PentagoCoord(xPos+3, yPos);
                  			Quadrant Qtemp2 = getQuadrant(coo2);
                  			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
                  			Quadrant q1 = holder.getKey();
                  			Quadrant q2 = holder.getValue();
                  			
                  			result2 = MAX;
                  			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
                  			if(state.isLegal(m)) {
//                  				System.out.println("598 - Here we execute the blocking function! "+m.toPrettyString());
                  				return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result2, m);
                  			}
                  			else {
                  				result2 = 0;
                  			}
                  		}
                  		
                  		xPos = j+4;
                  		yPos = i;
                  		invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
                  		if(!invalid) {
                  			PentagoCoord coo = new PentagoCoord(xPos, yPos);
                  			Quadrant Qtemp1 = getQuadrant(coo);
                  			PentagoCoord coo2 = new PentagoCoord(xPos-3, yPos);
                  			Quadrant Qtemp2 = getQuadrant(coo2);
                  			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
                  			Quadrant q1 = holder.getKey();
                  			Quadrant q2 = holder.getValue();
                  			
                  			result2 = MAX;
                  			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
                  			if(state.isLegal(m)) {
//                  				System.out.println("621 - Here we execute the blocking function! "+m.toPrettyString());
  	                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result2, m);
                  			}
                  			else {
                  				result2 = 0;
                  			}
                  		}
                  	}//checking if I am gonna win
                	 
                 	if(state.getPieceAt(j,i) == WHITE && state.getPieceAt(j+1,i) == WHITE && state.getPieceAt(j+2,i) == WHITE) {
                 		int xPos = j-1;
                 		int yPos = i;
                 		boolean invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
                 		if(!invalid) {
                 			PentagoCoord coo = new PentagoCoord(xPos, yPos);
                 			Quadrant Qtemp1 = getQuadrant(coo);
                 			PentagoCoord coo2 = new PentagoCoord(xPos+3, yPos);
                 			Quadrant Qtemp2 = getQuadrant(coo2);
                 			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
                 			Quadrant q1 = holder.getKey();
                 			Quadrant q2 = holder.getValue();
                 			
                 			result2 += Math.pow(PENALTY, 3);
                 			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
                 			if(state.isLegal(m)) {
//                 				System.out.println("646 - Here we execute the blocking function! "+m.toPrettyString());
 	                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result2, m);
                 			}
                 			else {
                 				result2 = 0;
                 			}
                 		}
                 		xPos = j+3;
                 		yPos = i;
                 		invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
                 		if(!invalid) {
                 			PentagoCoord coo = new PentagoCoord(xPos, yPos);
                 			Quadrant Qtemp1 = getQuadrant(coo);
                 			PentagoCoord coo2 = new PentagoCoord(xPos-3, yPos);
                 			Quadrant Qtemp2 = getQuadrant(coo2);
                 			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
                 			Quadrant q1 = holder.getKey();
                 			Quadrant q2 = holder.getValue();
                 			
                 			result2 += Math.pow(PENALTY, 3);
                 			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
                 			if(state.isLegal(m)) {
//                 				System.out.println("668 - Here we execute the blocking function! "+m.toPrettyString());
 	                			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result2, m);
                 			}
                 			else {
                 				result2 = 0;
                 			}
                 		}
                     }//checking opponent
                 	
                 	
                 }
             }        

             for (int i = 0; i < 4; i++) {
            	 /////////////////////////////////////////////////////////
            	 if((i<3) && state.getPieceAt(i,i) == BLACK && 
                  		state.getPieceAt(i+1,i+1) == BLACK && state.getPieceAt(i+2,i+2) == BLACK && state.getPieceAt(i+3,i+3) == BLACK) {
                  	int xPos = i-1;
              		int yPos = i-1;
              		boolean invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
              		if(!invalid) {
              			PentagoCoord coo = new PentagoCoord(xPos, yPos);
              			Quadrant Qtemp1 = getQuadrant(coo);
              			PentagoCoord coo2 = new PentagoCoord(xPos+3, yPos+3);
              			Quadrant Qtemp2 = getQuadrant(coo2);
              			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
              			Quadrant q1 = holder.getKey();
              			Quadrant q2 = holder.getValue();
              			
              			result2 = MAX;
              			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
              			if(state.isLegal(m)) {
//              				System.out.println("700 - Here we execute the blocking function! "+m.toPrettyString());
                  			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result2, m);
              			}
              			else {
              				result2 = 0;
              			}
              		}
              		
              		xPos = i+4;
              		yPos = i+4;
              		invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
              		if(!invalid) {
              			PentagoCoord coo = new PentagoCoord(xPos, yPos);
              			Quadrant Qtemp1 = getQuadrant(coo);
              			PentagoCoord coo2 = new PentagoCoord(xPos-3, yPos-3);
              			Quadrant Qtemp2 = getQuadrant(coo2);
              			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
              			Quadrant q1 = holder.getKey();
              			Quadrant q2 = holder.getValue();
              			
              			result2 = MAX;
              			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
              			if(state.isLegal(m)) {
//              				System.out.println("723 - Here we execute the blocking function! "+m.toPrettyString());
                  			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result2, m);
              			}
              			else {
              				result2 = 0;
              			}
              		}
                  }//checking myself
            	 
                 if(state.getPieceAt(i,i) == WHITE && state.getPieceAt(i+1,i+1) == WHITE && state.getPieceAt(i+2,i+2) == WHITE) {
                 	int xPos = i-1;
             		int yPos = i-1;
             		boolean invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
             		if(!invalid) {
             			PentagoCoord coo = new PentagoCoord(xPos, yPos);
             			Quadrant Qtemp1 = getQuadrant(coo);
             			PentagoCoord coo2 = new PentagoCoord(xPos+3, yPos+3);
             			Quadrant Qtemp2 = getQuadrant(coo2);
             			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
             			Quadrant q1 = holder.getKey();
             			Quadrant q2 = holder.getValue();
             			
             			result2 += Math.pow(PENALTY, 3);
             			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
             			if(state.isLegal(m)) {
//             				System.out.println("748 - Here we execute the blocking function! "+m.toPrettyString());
                 			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result2, m);
             			}
             			else {
             				result2 = 0;
             			}
             		}
             		
             		xPos = i+3;
             		yPos = i+3;
             		invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
             		if(!invalid) {
             			PentagoCoord coo = new PentagoCoord(xPos, yPos);
             			Quadrant Qtemp1 = getQuadrant(coo);
             			PentagoCoord coo2 = new PentagoCoord(xPos-3, yPos-3);
             			Quadrant Qtemp2 = getQuadrant(coo2);
             			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
             			Quadrant q1 = holder.getKey();
             			Quadrant q2 = holder.getValue();
             			
             			result2 += Math.pow(PENALTY, 3);
             			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
             			if(state.isLegal(m)) {
//             				System.out.println("771 - Here we execute the blocking function! "+m.toPrettyString());
                 			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result2, m);
             			}
             			else {
             				result2 = 0;
             			}
             		}
                 }//checking opponent
                 
                
             }

             for (int i = 0; i < 4; i++) {
            /////////////////////////////////	 
            	 if((i<3) && state.getPieceAt(i,5-i) == BLACK && 
                  		state.getPieceAt(i+1,4-i) == BLACK && state.getPieceAt(i+2,3-i) == BLACK && state.getPieceAt(i+3,2-i) == BLACK) {
                  	int xPos = i-1;
              		int yPos = 6-i;
              		boolean invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
              		if(!invalid) {
              			PentagoCoord coo = new PentagoCoord(xPos, yPos);
              			Quadrant Qtemp1 = getQuadrant(coo);
              			PentagoCoord coo2 = new PentagoCoord(xPos+3, yPos-3);
              			Quadrant Qtemp2 = getQuadrant(coo2);
              			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
              			Quadrant q1 = holder.getKey();
              			Quadrant q2 = holder.getValue();
              			
              			result2 = MAX;
              			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
              			if(state.isLegal(m)) {
//              				System.out.println("802 - Here we execute the blocking function! "+m.toPrettyString());
                  			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result2, m);
              			}
              			else {
              				result2 = 0;
              			}
              		}
              		
              		xPos = i+4;
              		yPos = 1-i;
              		invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
              		if(!invalid) {
              			PentagoCoord coo = new PentagoCoord(xPos, yPos);
              			Quadrant Qtemp1 = getQuadrant(coo);
              			PentagoCoord coo2 = new PentagoCoord(xPos-3, yPos+3);
              			Quadrant Qtemp2 = getQuadrant(coo2);
              			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
              			Quadrant q1 = holder.getKey();
              			Quadrant q2 = holder.getValue();
              			
              			result2 = MAX;
              			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
              			if(state.isLegal(m)) {
//              				System.out.println("825 - Here we execute the blocking function! "+m.toPrettyString());
                  			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result2, m);
              			}
              			else {
              				result2 = 0;
              			}
              		}
                  }//checking myself
            	 
                 if(state.getPieceAt(i,5-i) == WHITE && state.getPieceAt(i+1,4-i) == WHITE && state.getPieceAt(i+2,3-i) == WHITE) {
                 	int xPos = i-1;
             		int yPos = 6-i;
             		boolean invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
             		if(!invalid) {
             			PentagoCoord coo = new PentagoCoord(xPos, yPos);
             			Quadrant Qtemp1 = getQuadrant(coo);
             			PentagoCoord coo2 = new PentagoCoord(xPos+3, yPos-3);
             			Quadrant Qtemp2 = getQuadrant(coo2);
             			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
             			Quadrant q1 = holder.getKey();
             			Quadrant q2 = holder.getValue();
             			
             			result2 += Math.pow(PENALTY, 3);
             			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
             			if(state.isLegal(m)) {
//             				System.out.println("850 - Here we execute the blocking function! "+m.toPrettyString());
                 			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result2, m);
             			}
             			else {
             				result2 = 0;
             			}
             		}
             		
             		xPos = i+3;
             		yPos = 2-i;
             		invalid = xPos < 0 || xPos >= BOARD_SIZE || yPos < 0 || yPos >= BOARD_SIZE;
             		if(!invalid) {
             			PentagoCoord coo = new PentagoCoord(xPos, yPos);
             			Quadrant Qtemp1 = getQuadrant(coo);
             			PentagoCoord coo2 = new PentagoCoord(xPos-3, yPos+3);
             			Quadrant Qtemp2 = getQuadrant(coo2);
             			AbstractMap.SimpleEntry<Quadrant, Quadrant> holder = oppoQuadrant(Qtemp1, Qtemp2);
             			Quadrant q1 = holder.getKey();
             			Quadrant q2 = holder.getValue();
             			
             			result2 += Math.pow(PENALTY, 3);
             			PentagoMove m = new PentagoMove(xPos, yPos, q1, q2, state.getTurnPlayer());
             			if(state.isLegal(m)) {
//             				System.out.println("873 - Here we execute the blocking function! "+m.toPrettyString());
                 			return new AbstractMap.SimpleEntry<Integer, PentagoMove> (result2, m);
             			}
             			else {
             				result2 = 0;
             			}
             		}
                 }//checking opponent  
             }
            return null;
    	}
    }//end of shouldIBlock method
}