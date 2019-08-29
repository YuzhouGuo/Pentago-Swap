package student_player;

import boardgame.Board;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoBoardState.Piece;
import pentago_swap.PentagoCoord;
import pentago_swap.PentagoMove;

// THIS IS THE CLASS DOING ALL IMPORTANT EVALUATION WORKS !!!

public class Heuristics {
	public static final int MIN = Integer.MIN_VALUE;
	public static final int MAX = Integer.MAX_VALUE;
	public static final Piece WHITE = PentagoBoardState.Piece.WHITE ;
	public static final Piece BLACK = PentagoBoardState.Piece.BLACK ;
	
    public static int eva(/*PentagoMove evaMove, */PentagoBoardState state, int player) {
    	
//    	PentagoCoord coord = evaMove.getMoveCoord();
//    	int x = coord.getX();
//    	int y = coord.getY(); // this is out cuurent Move (so that we can identify during the loops)
    	
    	int consecutiveW = 0;
    	int consecutiveB = 0;
    	int haveWhite = 0; int haveBlack = 0;
    	
    	int rowWhite = 0; int rowBlack = 0;
    	int colWhite = 0; int colBlack = 0;
    	int diaWhite = 0; int diaBlack = 0;
    	int sumWhite = 0; int sumBlack = 0;  // ready to evaluate both sides
    	
    	int result;

        int winner = state.getWinner();

        if (winner == PentagoBoardState.WHITE) {
        	sumWhite = MAX;
        	return sumWhite;
        }
        else if (winner == PentagoBoardState.BLACK) { 
        	sumBlack = MIN;
        	return sumBlack;
        }
        else { // the other case is the NOBODY case
            // count horizontally
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    if (state.getPieceAt(i,j) == WHITE && (j<5) && state.getPieceAt(i,j+1) == WHITE) {
                        ++consecutiveW; // if consecutive, weight it even higher
                        rowWhite += Math.pow(10, consecutiveW);
                        if(consecutiveW ==3) {
                        	rowWhite += Math.pow(200, consecutiveW);
                        }
                        if(consecutiveW ==4) {
                        	rowWhite += Math.pow(1000, consecutiveW);
                        }
                    }
                    else if(state.getPieceAt(i,j) == BLACK && (j<5) && state.getPieceAt(i,j+1) == BLACK) {
                        ++consecutiveB; // if consecutive, weight it even higher
                        rowBlack += Math.pow(10, consecutiveB);
                        if(consecutiveB ==3) {
                        	rowBlack += Math.pow(200, consecutiveB);
                        }
                        if(consecutiveB ==4) {
                        	rowBlack += Math.pow(1000, consecutiveB);
                        }
                    }
                    else {
                    	consecutiveW = 0; consecutiveB = 0; // the line breaks, value reset to zero
                    	if(state.getPieceAt(i,j) == WHITE && player == PentagoBoardState.WHITE) {
                    		++haveWhite;
                    		rowWhite += Math.pow(3, haveWhite);
                    	}
                    	else if (state.getPieceAt(i,j) == BLACK && player == PentagoBoardState.BLACK) {
                    		++haveBlack;
                    		rowBlack += Math.pow(3, haveBlack);
                    	}
                    }
                }
                consecutiveW = 0; consecutiveB = 0; haveWhite = 0; haveBlack = 0;
            }
            
            consecutiveW = 0; consecutiveB = 0; haveWhite = 0; haveBlack = 0;
            
            // count vertically
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    if (state.getPieceAt(j,i) == WHITE && (j<5) && state.getPieceAt(j+1,i) == WHITE) {
                    	++consecutiveW;
                    	colWhite += Math.pow(10, consecutiveW);  
                    	if(consecutiveW ==3) {
                    		colWhite += Math.pow(200, consecutiveW);
                        }
                    	if(consecutiveW ==4) {
                    		colWhite += Math.pow(1000, consecutiveW);
                        }
                    }
                    else if(state.getPieceAt(j,i) == BLACK && (j<5) && state.getPieceAt(j+1,i) == BLACK) {
                    	++consecutiveB;
                    	colBlack += Math.pow(10, consecutiveB);
                    	if(consecutiveB ==3) {
                    		colBlack += Math.pow(200, consecutiveB);
                        }
                    	if(consecutiveB ==4) {
                    		colBlack += Math.pow(1000, consecutiveB);
                        }
                    }
                    else {
                    	consecutiveW = 0; consecutiveB = 0;
                    	if(state.getPieceAt(i,j) == WHITE && player == PentagoBoardState.WHITE) {
                    		++haveWhite;
                    		colWhite += Math.pow(3, haveWhite);
                    	}
                    	else if (state.getPieceAt(i,j) == BLACK && player == PentagoBoardState.BLACK) {
                    		++haveBlack;
                    		colBlack += Math.pow(3, haveBlack);
                    	}
                    }
                }
                consecutiveW = 0; consecutiveB = 0; haveWhite = 0; haveBlack = 0;
            }
            
            consecutiveW = 0; consecutiveB = 0; haveWhite = 0; haveBlack = 0;
            
            // count main diagonal;
            for (int i = 0; i < 6; i++) {
                if (state.getPieceAt(i,i) == WHITE && (i<5) && state.getPieceAt(i+1,i+1) == WHITE) {
                	++consecutiveW;
                	diaWhite += Math.pow(10, consecutiveW);  
                	if(consecutiveW ==3) {
                		diaWhite += Math.pow(200, consecutiveW);
                    }
                	if(consecutiveW ==4) {
                		diaWhite += Math.pow(1000, consecutiveW);
                    }
                	
                }
                else if(state.getPieceAt(i,i) == BLACK && (i<5) && state.getPieceAt(i+1,i+1) == BLACK) {
                	++consecutiveB;
                	diaBlack += Math.pow(10, consecutiveB);
                	if(consecutiveB ==3) {
                		diaBlack += Math.pow(200, consecutiveB);
                    }
                	if(consecutiveB ==4) {
                		diaBlack += Math.pow(1000, consecutiveB);
                    }
                }
                else {
                	consecutiveW = 0; consecutiveB = 0;
                	if(state.getPieceAt(i,i) == WHITE && player == PentagoBoardState.WHITE) {
                		++haveWhite;
                		diaWhite += Math.pow(3, haveWhite);
                	}
                	else if (state.getPieceAt(i,i) == BLACK && player == PentagoBoardState.BLACK) {
                		++haveBlack;
                		diaBlack += Math.pow(3, haveBlack);
                	}
                }
            }
            
            consecutiveW = 0; consecutiveB = 0; haveWhite = 0; haveBlack = 0;

            for (int i = 0; i < 6; i++) {
                if (state.getPieceAt(i,5-i) == WHITE && (i<5) && state.getPieceAt(i+1,4-i) == WHITE) {
                	diaWhite += Math.pow(10, consecutiveW);
                    ++consecutiveW;
                    if(consecutiveW ==3) {
                		diaWhite += Math.pow(200, consecutiveW);
                    }
                    if(consecutiveW ==4) {
                		diaWhite += Math.pow(1000, consecutiveW);
                    }
                }
                else if(state.getPieceAt(i,5-i) == BLACK && (i<5) && state.getPieceAt(i+1,4-i) == BLACK) {
                	diaBlack += Math.pow(10, consecutiveB);
                    ++consecutiveB;
                    if(consecutiveB ==3) {
                		diaBlack += Math.pow(200, consecutiveB);
                    }
                    if(consecutiveB ==4) {
                		diaBlack += Math.pow(1000, consecutiveB);
                    }
                }
                else {
                	consecutiveW = 0; consecutiveB = 0;
                	if(state.getPieceAt(i,5-i) == WHITE && player == PentagoBoardState.WHITE) {
                		++haveWhite;
                		diaWhite += Math.pow(3, haveWhite);
                	}
                	else if (state.getPieceAt(i,5-i) == BLACK && player == PentagoBoardState.BLACK) {
                		++haveBlack;
                		diaBlack += Math.pow(3, haveBlack);
                	}
                }
            }
            // count the offset diagonals
        }
        
        sumWhite = rowWhite + colWhite + diaWhite;
        sumBlack = rowBlack + colBlack + diaBlack; // sum up all the evaluation value

        // swap points if opponent is actually playing first (second-player)
        if(player == PentagoBoardState.WHITE) {
        	result = sumWhite - sumBlack;
        }
        else {
        	result = sumBlack - sumWhite;
        }
        //System.out.println(player + " and the result is " + result);
        return result;
    }
}