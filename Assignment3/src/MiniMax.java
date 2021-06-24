import java.util.ArrayList;

public class MiniMax {
    MiniMax() {}

    public static int minimax(Board board, int depth, Player.Side side, boolean maximizing) {
        if (depth == 0) return board.board_evaluation(side, maximizing);

        ArrayList<Move> possibleMoves = board.getPossibleMoves(maximizing, side);

        Board tempBoard = board.copy();
        if (maximizing) {
            int maxEva = Integer.MIN_VALUE;

            for (Move m: possibleMoves) {
                tempBoard = board.makeMove(m, maximizing);
                int eva;
                if (side == Player.Side.BLACK) eva = minimax(tempBoard, depth-1, Player.Side.WHITE,false);
                else eva = minimax(tempBoard, depth-1, Player.Side.BLACK,false);
                maxEva = Math.max(maxEva, eva);
            }
            return maxEva;
        }
        else {
            int minEva = Integer.MAX_VALUE;

            for (Move m: possibleMoves) {
                tempBoard = board.makeMove(m, maximizing);
                int eva;
                if (side == Player.Side.BLACK) eva = minimax(tempBoard, depth-1, Player.Side.WHITE,true);
                else eva = minimax(tempBoard, depth-1, Player.Side.BLACK,true);
                minEva = Math.min(minEva, eva);
            }
            return minEva;
        }
    }

    public static int alpha_beta(Board board, int depth, Player.Side side, boolean maximizing, double alpha, double beta)
    {
        if(depth == 0) {
            return board.board_evaluation(side, maximizing);
        }
        ArrayList<Move> possibleMoves = board.getPossibleMoves(maximizing, side);

        int initial = 0;
        Board tempBoard = null;
        if(maximizing)
        {
            initial = Integer.MIN_VALUE;
            for (Move possibleMove : possibleMoves) {
                tempBoard = board.copy();
                tempBoard.makeMove(possibleMove, maximizing);

                int result;
                if (side == Player.Side.BLACK)
                    result = alpha_beta(tempBoard, depth - 1, Player.Side.WHITE, !maximizing, alpha, beta);
                else result = alpha_beta(tempBoard, depth - 1, Player.Side.BLACK, !maximizing, alpha, beta);

                initial = Math.max(result, initial);
                alpha = Math.max(alpha, initial);

                if (alpha >= beta)
                    break;
            }
        }
        //minimizing
        else
        {
            initial = Integer.MAX_VALUE;
            for (Move possibleMove : possibleMoves) {
                tempBoard = board.copy();
                tempBoard.makeMove(possibleMove, maximizing);

                int result;
                if (side == Player.Side.BLACK)
                    result = alpha_beta(tempBoard, depth - 1, Player.Side.WHITE, !maximizing, alpha, beta);
                else result = alpha_beta(tempBoard, depth - 1, Player.Side.BLACK, !maximizing, alpha, beta);

                initial = Math.min(result, initial);
                alpha = Math.min(alpha, initial);

                if (alpha >= beta)
                    break;
            }
        }

        return initial;
    }
}
