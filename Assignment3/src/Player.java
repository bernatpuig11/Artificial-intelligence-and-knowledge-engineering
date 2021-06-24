import java.util.*;

public class Player {
    private Side side;
    private String name;

    public enum Side
    {
        BLACK, WHITE
    }
    public Player(String name, Side side)
    {
        this.name = name;
        this.side = side;
    }

    public Side getSide()
    {
        return side;
    }

    public String getName()
    {
        return name;
    }

    public void makeMove(Board board, int level) {
        int depth;
        if (level == 0) depth = 3;
        else if (level == 1) depth = 5;
        else depth = 7;

        ArrayList<Move> possibleMoves = new ArrayList<>();
        possibleMoves = board.getPossibleMoves(true, getSide());
        ArrayList<Pair<Integer, Integer>> evaluations = new ArrayList<>();

        ArrayList<Move> possibleMovesAux = new ArrayList<>();
        for (Move possibleMove : possibleMoves) {
            if (possibleMove.isKilling()) possibleMovesAux.add(possibleMove);
        }

        if (possibleMovesAux.size() == 0) {
            for (int i = 0; i < possibleMoves.size(); ++i) {
                Board tempBoard = board.copy();
                tempBoard.makeMove(possibleMoves.get(i), false);
                Pair<Integer, Integer> evaluation;
                if (getSide() == Side.WHITE) evaluation = new Pair<>(i, MiniMax.minimax(tempBoard, depth, Side.BLACK, false));
                else evaluation = new Pair<>(i, MiniMax.minimax(tempBoard, depth, Side.WHITE, false));
                evaluations.add(evaluation);
            }
        }
        else {
            for (int i = 0; i < possibleMovesAux.size(); ++i) {
                Board tempBoard = board.copy();
                tempBoard.makeMove(possibleMovesAux.get(i), false);
                Pair<Integer, Integer> evaluation;
                if (getSide() == Side.WHITE) evaluation = new Pair<>(i, MiniMax.minimax(tempBoard, depth, Side.BLACK, false));
                else evaluation = new Pair<>(i, MiniMax.minimax(tempBoard, depth, Side.WHITE, false));
                evaluations.add(evaluation);
            }
        }

        sortByValue(evaluations);

        if (possibleMovesAux.size() == 0) board.makeMove(possibleMoves.get(evaluations.get(0).getFirst()), true);
        else board.makeMove(possibleMovesAux.get(evaluations.get(0).getFirst()), true);
    }

    private void sortByValue(ArrayList<Pair<Integer, Integer>> evaluations) {
        evaluations.sort((o1, o2) -> {
            if (o1.getSecond() > o2.getSecond()) {
                return -1;
            } else if (o1.getSecond() < o2.getSecond()) {
                return 1;
            }
            return 0;
        });
    }
}
