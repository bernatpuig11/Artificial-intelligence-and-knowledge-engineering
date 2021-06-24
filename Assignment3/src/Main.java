import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream
        System.out.println("Define a name player: ");
        String name= sc.nextLine();

        System.out.println("Select a side: \n0 --> BLACK\n1 --> WHITE");
        String side= sc.nextLine();
        int sid = Integer.parseInt(side);

        System.out.println("Define the level of the opponent: \n0 --> BASIC\n1 --> MEDIUM\n2 --> PRO");
        String level= sc.nextLine();
        int lev = Integer.parseInt(level);

        boolean turn;
        Player p1, p2;
        Board board;
        if (sid == 0) {
            p1 = new Player(name, Player.Side.BLACK);
            p2 = new Player("AI", Player.Side.WHITE);
            turn = false;
            board = new Board(true);
        }
        else {
            p1 = new Player(name, Player.Side.WHITE);
            p2 = new Player("AI", Player.Side.BLACK);
            turn = true;
            board = new Board(false);
        }

        boolean winner = true;
        // if winner == true --> black WINS
        // else if winner == false --> white WINS

        while (true) {
            if (turn) {
                // Player1 moves via terminal
                Move m = new Move();

                System.out.println("Insert the piece you want to move with the row and col: ");
                System.out.println("Example: 5 9");
                boolean valid = false;
                while (!valid) {
                    String piece = sc.nextLine();
                    String[] parts = piece.split(" ");
                    String part1 = parts[0];
                    String part2 = parts[1];
                    int row = Integer.parseInt(part1);
                    int col = Integer.parseInt(part2);
                    m.setInitialRow(row);
                    m.setInitialCol(col);
                    valid = true;
                    if (!valid) {
                        System.out.println("Insert the piece you want to move with the row and col: ");
                        System.out.println("Example: 5 9");
                    }
                }

                String play = "";
                System.out.println("Insert the location you want to move with the row and col: ");
                System.out.println("If it's more than 1, insert one by one and finish with END ");
                System.out.println("Example:");
                System.out.println("3 2");
                System.out.println("5 4");
                System.out.println("END");

                ArrayList<Pair<Integer,Integer>> movements = new ArrayList<>();

                while (true) {
                    play = sc.nextLine();
                    if (play.equals("END")) break;
                    String[] parts = play.split(" ");
                    String part1 = parts[0];
                    String part2 = parts[1];
                    int row = Integer.parseInt(part1);
                    int col = Integer.parseInt(part2);
                    Pair<Integer,Integer> movement = new Pair<>(row, col);
                    movements.add(movement);
                }
                m.setMovements(movements);

                board.makeMove(m, false);
            }
            else {
                p2.makeMove(board, lev);
            }
            if (board.getNumBlackKingPieces() + board.getNumBlackNormalPieces() == 0) {
                winner = false;
                break;
            }
            else if (board.getNumWhiteKingPieces() + board.getNumWhiteNormalPieces() == 0) {
                winner = true;
                break;
            }

            System.out.println("   ________________________");
            for (int i = 0; i < 10; ++i) {
                System.out.print(i +  " |");
                for (int j = 0; j < 10; ++j) {
                    if (board.getPiece(i,j) == Board.Type.EMPTY) System.out.print("-");
                    else if (board.getPiece(i,j) == Board.Type.BLACK) System.out.print("x");
                    else if (board.getPiece(i,j) == Board.Type.WHITE) System.out.print("o");
                    else if (board.getPiece(i,j) == Board.Type.BLACK_KING) System.out.print("X");
                    else if (board.getPiece(i,j) == Board.Type.WHITE_KING) System.out.print("O");
                    if (j != 9) System.out.print("  ");
                }
                System.out.print("|\n");
            }
            System.out.println("  -------------------------");
            System.out.println();

            turn = !turn;
        }

        if (winner) {
            if (p1.getSide() == Player.Side.BLACK) {
                System.out.println("YOU WIN!!!!");
                System.out.println("Player name: " + p1.getName());
            }
            else {
                System.out.println("YOU LOSE...");
                System.out.println("AI wins!");
            }
        }
        else {
            if (p1.getSide() == Player.Side.WHITE) {
                System.out.println("YOU WIN!!!!");
                System.out.println("Player name: " + p1.getName());
            }
            else {
                System.out.println("YOU LOSE...");
                System.out.println("AI wins!");
            }
        }

        /*
        Board board = new Board("a");
        ArrayList<Move> possibleMoves = new ArrayList<>();
        Player.Side p = Player.Side.BLACK;
        boolean[][] booleanBoard = new boolean[10][10];
        for (boolean[] booleans : booleanBoard) {
            Arrays.fill(booleans, true);
        }
        //board.addPossibleMovesForKingPieceInKilling(possibleMoves, null, p, booleanBoard, 0, 1, 0, 1);
        possibleMoves = board.getPossibleMovesForPiece(3, 2, false, Player.Side.WHITE, true);
        ArrayList<Pair<Integer, Integer>> evaluations = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                System.out.print(board.getPiece(i,j) + " ");
            }
            System.out.println();
        }
        System.out.println();

        for (Move m : possibleMoves) {
            System.out.println("Initial row: " + m.getInitialRow() + " , Initial col: " + m.getInitialCol());
            if (m.getMovements().isEmpty()) System.out.println("Movements empty");
            for (Pair<Integer, Integer> dest : m.getMovements()) {
                System.out.println("Destination: " + dest.getFirst() + " , " + dest.getSecond());
            }
        }*/

        /*
        int k = 0;
        for (Move m : possibleMoves) {
            System.out.println("Initial row: " + m.getInitialRow() + " , Initial col: " + m.getInitialCol());
            if (m.getMovements().isEmpty()) System.out.println("Movements empty");
            for (Pair<Integer,Integer> dest : m.getMovements()) {
                System.out.println("Destination: " + dest.getFirst() + " , " + dest.getSecond());
            }

            Board tempBoard = board.copy();
            tempBoard.makeMove(m);
            System.out.println("Board evaluation: " + tempBoard.board_evaluation(p));
            Pair<Integer, Integer> evaluation = new Pair<>(k, MiniMax.minimax(tempBoard, 1, Player.Side.WHITE, false));
            evaluations.add(evaluation);
            System.out.println("Board evaluation minimax: " + evaluation.getSecond());
            ++k;

            for (int i = 0; i < 10; ++i) {
                for (int j = 0; j < 10; ++j) {
                    System.out.print(tempBoard.getPiece(i,j) + " ");
                }
                System.out.println();
            }
            System.out.println();
            break;
        }*/

        /*

        Player pl = new Player("Pau", p);
        //pl.makeMove(board, 0);

        System.out.println("Board evaluation: " + board.board_evaluation(pl.getSide(), false));

        if (possibleMoves.isEmpty()) System.out.println("Possible moves is empty ");

        */
    }
}
