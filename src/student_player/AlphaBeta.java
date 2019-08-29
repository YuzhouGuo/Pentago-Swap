package student_player;

import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoBoardState.Quadrant;
import pentago_swap.PentagoCoord;
import pentago_swap.PentagoMove;
import java.util.AbstractMap;
import java.util.ArrayList;

import boardgame.Board;

public class AlphaBeta {

	public static AbstractMap.SimpleEntry<Integer, PentagoMove> pruning (PentagoBoardState state, int depth, int player, int alpha, int beta) {
		int bestScore = 0;
		int bestScoreB = 0;

		ArrayList<PentagoMove> possibleMoves = state.getAllLegalMoves();
		// System.out.println(possibleMoves);
		PentagoMove bestMove = possibleMoves.get(0);

		if (depth == 0 || possibleMoves.isEmpty()) {
			if (possibleMoves.isEmpty()) {
				bestScore = Heuristics.eva(state, player); // this is the base case, we save bestMove at the end	
			}
		} else {
			for (PentagoMove currentMove : possibleMoves) {
				if (player == PentagoBoardState.WHITE) {

					PentagoBoardState tryMove = (PentagoBoardState) state.clone(); 
					AbstractMap.SimpleEntry<Integer, PentagoMove> holder = Blocking.shouldIBlcok(tryMove, player);
					if (holder != null) {
						bestScoreB = holder.getKey();
					}
					else {
						bestScoreB = Integer.MIN_VALUE;
					}

					tryMove.processMove(currentMove);
					bestScore = pruning(tryMove, (--depth), PentagoBoardState.BLACK, alpha, beta).getKey();
					int r = Math.max(bestScoreB, bestScore);
					if (r > alpha) {
						if (r == bestScoreB) {
							alpha = bestScoreB;
							bestMove = holder.getValue();
//							System.out.println("This is the blcoking move: "+holder.getValue().toPrettyString());
							return new AbstractMap.SimpleEntry<>(alpha, bestMove);
						} else {
							alpha = bestScore;
							bestMove = currentMove;
						}
					}
				} 
				else { // say that we are the BLACK side
					PentagoBoardState tryMove = (PentagoBoardState) state.clone(); // thinking I should use the clone
																					// method
					AbstractMap.SimpleEntry<Integer, PentagoMove> holder = Blocking.shouldIBlcok(tryMove, player);
					// System.out.println(holder.getKey());
					if (holder != null) {
						bestScoreB = holder.getKey();
					} else {
						bestScoreB = Integer.MAX_VALUE;
					}

					tryMove.processMove(currentMove);
					bestScore = pruning(tryMove, (--depth), PentagoBoardState.WHITE, alpha, beta).getKey();
					int r = Math.min(bestScoreB, bestScore);
					if (r < beta) {
						if (r == bestScoreB) {
							beta = bestScoreB;
							bestMove = holder.getValue();
							return new AbstractMap.SimpleEntry<>(beta, bestMove);
						} else {
							beta = bestScore;
							bestMove = currentMove;
						}
					}
				}
				if (alpha >= beta) {
					break; // the pruning step in alpha Beta
				}
			}
		}
		return new AbstractMap.SimpleEntry<>((player == PentagoBoardState.WHITE) ? alpha : beta, bestMove);
	}
}
