import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private final Type[][] board;
    private static final int SIZE = 10;

    private int numWhiteNormalPieces;
    private int numBlackNormalPieces;
    private int numBlackKingPieces;
    private int numWhiteKingPieces;

    public enum Type {
        NULL, EMPTY, WHITE, BLACK, WHITE_KING, BLACK_KING
    }

    Board(boolean white_top) {
        numWhiteNormalPieces = 20;
        numBlackNormalPieces = 20;
        numBlackKingPieces = 0;
        numWhiteKingPieces = 0;
        board = new Type[SIZE][SIZE];

        if (white_top) {
            for (int i = 0; i < board.length; i++) {
                int start = 0;
                if (i % 2 == 0) start = 1;

                Type pieceType = Type.EMPTY;
                if (i <= 3)
                    pieceType = Type.WHITE;
                else if (i >= 6)
                    pieceType = Type.BLACK;

                for (int j = start; j < board[i].length; j += 2) {
                    board[i][j] = pieceType;
                }
            }
        }
        else {
            for (int i = 0; i < board.length; i++) {
                int start = 0;
                if (i % 2 == 0) start = 1;

                Type pieceType = Type.EMPTY;
                if (i <= 3)
                    pieceType = Type.BLACK;
                else if (i >= 6)
                    pieceType = Type.WHITE;

                for (int j = start; j < board[i].length; j += 2) {
                    board[i][j] = pieceType;
                }
            }
        }
    }

    Board(String x) {
        numWhiteNormalPieces = 1;
        numBlackNormalPieces = 0;
        numBlackKingPieces = 1;
        numWhiteKingPieces = 1;
        board = new Type[SIZE][SIZE];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = Type.EMPTY;
            }
        }

        board[2][3] = Type.BLACK_KING;
        //board[1][2] = Type.WHITE_KING;
        board[2][5] = Type.BLACK;
        board[3][2] = Type.WHITE_KING;
        //board[4][3] = Type.BLACK;
        //board[7][4] = Type.WHITE;
    }

    public Board(Type[][] board)
    {
        numWhiteNormalPieces = 0;
        numBlackNormalPieces = 0;
        numBlackKingPieces = 0;
        numWhiteKingPieces = 0;

        this.board = board;
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                Type piece = getPiece(i, j);
                if(piece == Type.BLACK)
                    numBlackNormalPieces++;
                else if(piece == Type.BLACK_KING)
                    numBlackKingPieces++;
                else if(piece == Type.WHITE)
                    numWhiteNormalPieces++;
                else if(piece == Type.WHITE_KING)
                    numWhiteKingPieces++;
            }
        }
    }

    public Type getPiece(int row, int col) {
        if (row < 0 || row > SIZE-1) return Type.NULL;
        else if (col < 0 || col > SIZE-1) return Type.NULL;
        return board[row][col];
    }

    public int getNumWhiteNormalPieces() {
        return numWhiteNormalPieces;
    }

    public void setNumWhiteNormalPieces(int numWhiteNormalPieces) {
        this.numWhiteNormalPieces = numWhiteNormalPieces;
    }

    public int getNumBlackNormalPieces() {
        return numBlackNormalPieces;
    }

    public void setNumBlackNormalPieces(int numBlackNormalPieces) {
        this.numBlackNormalPieces = numBlackNormalPieces;
    }

    public int getNumBlackKingPieces() {
        return numBlackKingPieces;
    }

    public void setNumBlackKingPieces(int numBlackKingPieces) {
        this.numBlackKingPieces = numBlackKingPieces;
    }

    public int getNumWhiteKingPieces() {
        return numWhiteKingPieces;
    }

    public void setNumWhiteKingPieces(int numWhiteKingPieces) {
        this.numWhiteKingPieces = numWhiteKingPieces;
    }

    public int board_evaluation(Player.Side side, boolean maximizing) {

        // The AI plays always in the top
        /* Value of pieces:
            Kings: 10
            Normal pieces:
                In 1st, 2nd, 3rd and 4th row: 2
                In 5th, 6th and 7th row: 4
                In 8th and 9th row: 6
        */
        int totalEval;

        int blackKings = getNumBlackKingPieces();
        int whiteKings = getNumWhiteKingPieces();

        if (maximizing) {
            if (side == Player.Side.BLACK) {

                totalEval = 10 * blackKings - 10 * whiteKings;

                for (int i = 0; i < SIZE; ++i) {
                    for (int j = 0; j < SIZE; ++j) {
                        if (i < 1) {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval += 2;
                            }
                        } else if (i < 3) {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval += 2;
                            } else if (getPiece(i, j) == Type.WHITE) {
                                totalEval -= 6;
                            }
                        } else if (i < 4) {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval += 2;
                            } else if (getPiece(i, j) == Type.WHITE) {
                                totalEval -= 4;
                            }
                        } else if (i < 6) {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval += 4;
                            } else if (getPiece(i, j) == Type.WHITE) {
                                totalEval -= 4;
                            }
                        } else if (i < 7) {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval += 4;
                            } else if (getPiece(i, j) == Type.WHITE) {
                                totalEval -= 2;
                            }
                        } else if (i < 9) {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval += 6;
                            } else if (getPiece(i, j) == Type.WHITE) {
                                totalEval -= 2;
                            }
                        } else {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval -= 2;
                            }
                        }
                    }
                }
            } else {
                totalEval = 10 * whiteKings - 10 * blackKings;

                for (int i = 0; i < SIZE; ++i) {
                    for (int j = 0; j < SIZE; ++j) {
                        if (i < 1) {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval += 2;
                            }
                        } else if (i < 3) {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval += 2;
                            } else if (getPiece(i, j) == Type.BLACK) {
                                totalEval -= 6;
                            }
                        } else if (i < 4) {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval += 2;
                            } else if (getPiece(i, j) == Type.BLACK) {
                                totalEval -= 4;
                            }
                        } else if (i < 6) {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval += 4;
                            } else if (getPiece(i, j) == Type.BLACK) {
                                totalEval -= 4;
                            }
                        } else if (i < 7) {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval += 4;
                            } else if (getPiece(i, j) == Type.BLACK) {
                                totalEval -= 2;
                            }
                        } else if (i < 9) {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval += 6;
                            } else if (getPiece(i, j) == Type.BLACK) {
                                totalEval -= 2;
                            }
                        } else {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval -= 2;
                            }
                        }
                    }
                }
            }
        }
        else {
            if (side == Player.Side.BLACK) {

                totalEval = 10 * blackKings - 10 * whiteKings;

                for (int i = 0; i < SIZE; ++i) {
                    for (int j = 0; j < SIZE; ++j) {
                        if (i < 1) {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval -= 2;
                            }
                        } else if (i < 3) {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval -= 2;
                            } else if (getPiece(i, j) == Type.BLACK) {
                                totalEval += 6;
                            }
                        } else if (i < 4) {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval -= 2;
                            } else if (getPiece(i, j) == Type.BLACK) {
                                totalEval += 4;
                            }
                        } else if (i < 6) {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval -= 4;
                            } else if (getPiece(i, j) == Type.BLACK) {
                                totalEval += 4;
                            }
                        } else if (i < 7) {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval -= 4;
                            } else if (getPiece(i, j) == Type.BLACK) {
                                totalEval += 2;
                            }
                        } else if (i < 9) {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval -= 6;
                            } else if (getPiece(i, j) == Type.BLACK) {
                                totalEval += 2;
                            }
                        } else {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval += 2;
                            }
                        }
                    }
                }
            } else {
                totalEval = 10 * whiteKings - 10 * blackKings;

                for (int i = 0; i < SIZE; ++i) {
                    for (int j = 0; j < SIZE; ++j) {
                        if (i < 1) {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval -= 2;
                            }
                        } else if (i < 3) {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval -= 2;
                            } else if (getPiece(i, j) == Type.WHITE) {
                                totalEval += 6;
                            }
                        } else if (i < 4) {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval -= 2;
                            } else if (getPiece(i, j) == Type.WHITE) {
                                totalEval += 4;
                            }
                        } else if (i < 6) {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval -= 4;
                            } else if (getPiece(i, j) == Type.WHITE) {
                                totalEval += 4;
                            }
                        } else if (i < 7) {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval -= 4;
                            } else if (getPiece(i, j) == Type.WHITE) {
                                totalEval += 2;
                            }
                        } else if (i < 9) {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval -= 6;
                            } else if (getPiece(i, j) == Type.WHITE) {
                                totalEval += 2;
                            }
                        } else {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval += 2;
                            }
                        }
                    }
                }
            }
        }

        return totalEval;
    }

    public int board_evaluation_2(Player.Side side, boolean maximizing) {
        int totalEval = 0;

        // The AI plays always in the top
        /* Value of pieces:
            Kings: 12
            Normal pieces:
                In 1st row: 6
                In 2nd, 3rd, 4th, 5th, 6th, 7th, 8th and 9th row: 4
                In 10th row: 6
        */

        int blackKings = getNumBlackKingPieces();
        int whiteKings = getNumWhiteKingPieces();

        if (maximizing) {
            if (side == Player.Side.BLACK) {

                totalEval = 12 * blackKings - 12 * whiteKings;

                for (int i = 0; i < SIZE; ++i) {
                    for (int j = 0; j < SIZE; ++j) {
                        if (i < 1) {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval += 6;
                            }
                        } else if (i < 9) {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval += 4;
                            } else if (getPiece(i, j) == Type.WHITE) {
                                totalEval -= 4;
                            }
                        }
                        else {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval -= 6;
                            }
                        }
                    }
                }
            } else {
                totalEval = 12 * whiteKings - 12 * blackKings;

                for (int i = 0; i < SIZE; ++i) {
                    for (int j = 0; j < SIZE; ++j) {
                        if (i < 1) {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval += 6;
                            }
                        } else if (i < 9) {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval += 4;
                            } else if (getPiece(i, j) == Type.BLACK) {
                                totalEval -= 4;
                            }
                        } else {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval -= 6;
                            }
                        }
                    }
                }
            }
        }
        else {
            if (side == Player.Side.BLACK) {

                totalEval = 10 * blackKings - 10 * whiteKings;

                for (int i = 0; i < SIZE; ++i) {
                    for (int j = 0; j < SIZE; ++j) {
                        if (i < 1) {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval -= 6;
                            }
                        } else if (i < 9) {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval -= 4;
                            } else if (getPiece(i, j) == Type.BLACK) {
                                totalEval += 4;
                            }
                        } else {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval += 6;
                            }
                        }
                    }
                }
            } else {
                totalEval = 10 * whiteKings - 10 * blackKings;

                for (int i = 0; i < SIZE; ++i) {
                    for (int j = 0; j < SIZE; ++j) {
                        if (i < 1) {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval -= 6;
                            }
                        } else if (i < 9) {
                            if (getPiece(i, j) == Type.BLACK) {
                                totalEval -= 4;
                            } else if (getPiece(i, j) == Type.WHITE) {
                                totalEval += 4;
                            }
                        }
                        else {
                            if (getPiece(i, j) == Type.WHITE) {
                                totalEval += 6;
                            }
                        }
                    }
                }
            }
        }

        return totalEval;
    }

    public ArrayList<Move> getPossibleMoves(boolean maximizing, Player.Side side) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        if (side == Player.Side.BLACK) {
            for (int i = 0; i < SIZE; ++i) {
                for (int j = 0; j < SIZE; ++j) {
                    if (getPiece(i, j) == Type.BLACK) possibleMoves.addAll(getPossibleMovesForPiece(i, j, maximizing, side, false));
                    if (getPiece(i, j) == Type.BLACK_KING) possibleMoves.addAll(getPossibleMovesForPiece(i, j, maximizing, side, true));
                }
            }
        }
        else {
            for (int i = 0; i < SIZE; ++i) {
                for (int j = 0; j < SIZE; ++j) {
                    if (getPiece(i, j) == Type.WHITE) possibleMoves.addAll(getPossibleMovesForPiece(i, j, maximizing, side, false));
                    if (getPiece(i, j) == Type.WHITE_KING) possibleMoves.addAll(getPossibleMovesForPiece(i, j, maximizing, side, true));
                }
            }
        }

        return possibleMoves;
    }

    public ArrayList<Move> getPossibleMovesForPiece(int i, int j, boolean maximizing, Player.Side side, boolean king) {

        ArrayList<Move> possibleMoves = new ArrayList<>();

        // If we aren't king we will only look in our 4x4 square
        if (!king) {
            // First of all, we will search for the possibility of killing
            if (side == Player.Side.BLACK) {
                if (getPiece(i - 2, j - 2) == Type.EMPTY && getPiece(i - 1, j - 1) == Type.WHITE || getPiece(i - 1, j - 1) == Type.WHITE_KING) {
                    Move move = new Move();
                    move = addPossibleMovementsForNormalPieceInKilling(possibleMoves, move, side, i - 2, j - 2, 0, i, j, 1);
                    move.setInitialRow(i);
                    move.setInitialCol(j);
                    move.setKilling(true);
                    possibleMoves.add(move);
                }
                if (getPiece(i - 2, j + 2) == Type.EMPTY && getPiece(i - 1, j + 1) == Type.WHITE || getPiece(i - 1, j + 1) == Type.WHITE_KING) {
                    Move move = new Move();
                    move = addPossibleMovementsForNormalPieceInKilling(possibleMoves, move, side, i - 2, j + 2, 0, i , j, 2);
                    move.setInitialRow(i);
                    move.setInitialCol(j);
                    move.setKilling(true);
                    possibleMoves.add(move);
                }
                if (getPiece(i + 2, j - 2) == Type.EMPTY && getPiece(i + 1, j - 1) == Type.WHITE || getPiece(i + 1, j - 1) == Type.WHITE_KING) {
                    Move move = new Move();
                    move = addPossibleMovementsForNormalPieceInKilling(possibleMoves, move, side, i + 2, j - 2, 0, i, j, 3);
                    move.setInitialRow(i);
                    move.setInitialCol(j);
                    move.setKilling(true);
                    possibleMoves.add(move);
                }
                if (getPiece(i + 2, j + 2) == Type.EMPTY && getPiece(i + 1, j + 1) == Type.WHITE || getPiece(i + 1, j + 1) == Type.WHITE_KING) {
                    Move move = new Move();
                    move = addPossibleMovementsForNormalPieceInKilling(possibleMoves, move, side, i + 2, j + 2, 0, i, j, 4);
                    move.setInitialRow(i);
                    move.setInitialCol(j);
                    move.setKilling(true);
                    possibleMoves.add(move);
                }
            }
            else {
                if (getPiece(i - 2, j - 2) == Type.EMPTY && getPiece(i - 1, j - 1) == Type.BLACK || getPiece(i - 1, j - 1) == Type.BLACK_KING) {
                    Move move = new Move();
                    move = addPossibleMovementsForNormalPieceInKilling(possibleMoves, move, side,i - 2, j - 2, 0, i, j, 1);
                    move.setInitialRow(i);
                    move.setInitialCol(j);
                    move.setKilling(true);
                    possibleMoves.add(move);
                }
                if (getPiece(i - 2, j + 2) == Type.EMPTY && getPiece(i - 1, j + 1) == Type.BLACK || getPiece(i - 1, j + 1) == Type.BLACK_KING) {
                    Move move = new Move();
                    move = addPossibleMovementsForNormalPieceInKilling(possibleMoves, move, side,i - 2, j + 2, 0, i, j, 2);
                    move.setInitialRow(i);
                    move.setInitialCol(j);
                    move.setKilling(true);
                    possibleMoves.add(move);
                }
                if (getPiece(i + 2, j - 2) == Type.EMPTY && getPiece(i + 1, j - 1) == Type.BLACK || getPiece(i + 1, j - 1) == Type.BLACK_KING) {
                    Move move = new Move();
                    move = addPossibleMovementsForNormalPieceInKilling(possibleMoves, move, side,i + 2, j - 2, 0, i, j, 3);
                    move.setInitialRow(i);
                    move.setInitialCol(j);
                    move.setKilling(true);
                    possibleMoves.add(move);
                }
                if (getPiece(i + 2, j + 2) == Type.EMPTY && getPiece(i + 1, j + 1) == Type.BLACK || getPiece(i + 1, j + 1) == Type.BLACK_KING) {
                    Move move = new Move();
                    move = addPossibleMovementsForNormalPieceInKilling(possibleMoves, move, side,i + 2, j + 2, 0, i, j, 4);
                    move.setInitialRow(i);
                    move.setInitialCol(j);
                    move.setKilling(true);
                    possibleMoves.add(move);
                }
            }
        }
        else {
            boolean[][] booleanBoard = new boolean[10][10];
            for (boolean[] booleans : booleanBoard) {
                Arrays.fill(booleans, true);
            }
            addPossibleMovesForKingPieceInKilling(possibleMoves, null, side, booleanBoard, i, j, i , j);
        }

        // If there's no possibility of killing, we will search another option.
        if (possibleMoves.size() == 0) {
            // If we want to maximize, we play for the AI player that is situated in the top
            if (maximizing) {
                if (getPiece(i + 1, j - 1) == Type.EMPTY) {
                    Move move = new Move();
                    move.setInitialRow(i);
                    move.setInitialCol(j);

                    ArrayList<Pair<Integer, Integer>> movements = new ArrayList<>();
                    Pair<Integer, Integer> movement = new Pair<>(i + 1, j - 1);
                    movements.add(movement);
                    move.setMovements(movements);

                    possibleMoves.add(move);
                }
                if (getPiece(i + 1, j + 1) == Type.EMPTY) {
                    Move move = new Move();
                    move.setInitialRow(i);
                    move.setInitialCol(j);

                    ArrayList<Pair<Integer, Integer>> movements = new ArrayList<>();
                    Pair<Integer, Integer> movement = new Pair<>(i + 1, j + 1);
                    movements.add(movement);
                    move.setMovements(movements);

                    possibleMoves.add(move);
                }
            }
            else {
                if (getPiece(i - 1, j - 1) == Type.EMPTY) {
                    Move move = new Move();
                    move.setInitialRow(i);
                    move.setInitialCol(j);

                    ArrayList<Pair<Integer, Integer>> movements = new ArrayList<>();
                    Pair<Integer, Integer> movement = new Pair<>(i - 1, j - 1);
                    movements.add(movement);
                    move.setMovements(movements);

                    possibleMoves.add(move);
                }
                if (getPiece(i - 1, j + 1) == Type.EMPTY) {
                    Move move = new Move();
                    move.setInitialRow(i);
                    move.setInitialCol(j);

                    ArrayList<Pair<Integer, Integer>> movements = new ArrayList<>();
                    Pair<Integer, Integer> movement = new Pair<>(i - 1, j + 1);
                    movements.add(movement);
                    move.setMovements(movements);

                    possibleMoves.add(move);
                }
            }
        }

        return possibleMoves;
    }

    // Recursive function to add kill movements for normal pieces
    private Move addPossibleMovementsForNormalPieceInKilling(ArrayList<Move> possibleMoves, Move move, Player.Side side, int i, int j, int turn, int p, int s, int previous) {
        Pair<Integer, Integer> movement = new Pair<>(i, j);
        if (turn == 0) move.getMovements().add(movement);

        if (side == Player.Side.BLACK) {
            if (getPiece(i - 2, j - 2) == Type.EMPTY && turn < 1 && getPiece(i - 1, j - 1) == Type.WHITE || getPiece(i - 1, j - 1) == Type.WHITE_KING && previous != 4) {
                Board tempboard = this.copy();
                if (tempboard.getPiece(i-1, j-1) == Type.WHITE) tempboard.numWhiteNormalPieces--;
                else if (tempboard.getPiece(i-1, j-1) == Type.WHITE_KING) tempboard.numWhiteKingPieces--;
                tempboard.board[i-1][j-1] = Type.EMPTY;

                Move mov = new Move();
                ArrayList<Pair<Integer, Integer>> movements = new ArrayList<>(move.getMovements());
                mov.setMovements(movements);
                int movementsSize = mov.getMovements().size();

                move = tempboard.addPossibleMovementsForNormalPieceInKilling(possibleMoves, move, side, i - 2, j - 2, 0, p, s, 1);

                mov = tempboard.addPossibleMovementsForNormalPieceInKilling(possibleMoves, mov, side, i, j, 1, p, s, previous);
                mov.setInitialRow(p);
                mov.setInitialCol(s);
                mov.setKilling(true);
                if (mov.getMovements().size() > movementsSize) possibleMoves.add(mov);
            } else if (getPiece(i - 2, j + 2) == Type.EMPTY && turn < 2 && getPiece(i - 1, j + 1) == Type.WHITE || getPiece(i - 1, j + 1) == Type.WHITE_KING && previous != 3) {
                Board tempboard = this.copy();
                if (tempboard.getPiece(i-1, j+1) == Type.WHITE) tempboard.numWhiteNormalPieces--;
                else if (tempboard.getPiece(i-1, j+1) == Type.WHITE_KING) tempboard.numWhiteKingPieces--;
                tempboard.board[i-1][j+1] = Type.EMPTY;

                Move mov = new Move();
                ArrayList<Pair<Integer, Integer>> movements = new ArrayList<>(move.getMovements());
                mov.setMovements(movements);
                int movementsSize = mov.getMovements().size();

                move = tempboard.addPossibleMovementsForNormalPieceInKilling(possibleMoves, move, side, i - 2, j + 2, 0, p, s, 2);

                mov = tempboard.addPossibleMovementsForNormalPieceInKilling(possibleMoves, mov, side,i, j, 2, p, s, previous);
                mov.setInitialRow(p);
                mov.setInitialCol(s);
                mov.setKilling(true);
                if (mov.getMovements().size() > movementsSize) possibleMoves.add(mov);
            } else if (getPiece(i + 2, j - 2) == Type.EMPTY && turn < 3 && getPiece(i + 1, j - 1) == Type.WHITE || getPiece(i + 1, j - 1) == Type.WHITE_KING && previous != 2) {
                Board tempboard = this.copy();
                if (tempboard.getPiece(i+1, j-1) == Type.WHITE) tempboard.numWhiteNormalPieces--;
                else if (tempboard.getPiece(i+1, j-1) == Type.WHITE_KING) tempboard.numWhiteKingPieces--;
                tempboard.board[i+1][j-1] = Type.EMPTY;

                Move mov = new Move();
                ArrayList<Pair<Integer, Integer>> movements = new ArrayList<>(move.getMovements());
                mov.setMovements(movements);
                int movementsSize = mov.getMovements().size();

                move = tempboard.addPossibleMovementsForNormalPieceInKilling(possibleMoves, move, side, i + 2, j - 2, 0, p, s, 3);

                mov = tempboard.addPossibleMovementsForNormalPieceInKilling(possibleMoves, mov, side,i, j, 3, p, s, previous);
                mov.setInitialRow(p);
                mov.setInitialCol(s);
                mov.setKilling(true);
                if (mov.getMovements().size() > movementsSize) possibleMoves.add(mov);
            } else if (getPiece(i + 2, j + 2) == Type.EMPTY && turn < 4 && getPiece(i + 1, j + 1) == Type.WHITE || getPiece(i + 1, j + 1) == Type.WHITE_KING && previous != 1) {
                Board tempboard = this.copy();
                if (tempboard.getPiece(i+1, j+1) == Type.WHITE) tempboard.numWhiteNormalPieces--;
                else if (tempboard.getPiece(i+1, j+1) == Type.WHITE_KING) tempboard.numWhiteKingPieces--;
                tempboard.board[i+1][j+1] = Type.EMPTY;

                Move mov = new Move();
                ArrayList<Pair<Integer, Integer>> movements = new ArrayList<>(move.getMovements());
                mov.setMovements(movements);
                int movementsSize = mov.getMovements().size();

                move = tempboard.addPossibleMovementsForNormalPieceInKilling(possibleMoves, move, side, i + 2, j + 2, 0, p, s, 4);

                mov = tempboard.addPossibleMovementsForNormalPieceInKilling(possibleMoves, mov, side,i, j, 4, p, s, previous);
                mov.setInitialRow(p);
                mov.setInitialCol(s);
                mov.setKilling(true);
                if (mov.getMovements().size() > movementsSize) possibleMoves.add(mov);
            }
        }

        else {
            if (getPiece(i - 2, j - 2) == Type.EMPTY && turn < 1 && getPiece(i - 1, j - 1) == Type.BLACK || getPiece(i - 1, j - 1) == Type.BLACK_KING && previous != 4) {
                Board tempboard = this.copy();
                if (tempboard.getPiece(i-1, j-1) == Type.BLACK) tempboard.numBlackNormalPieces--;
                else if (tempboard.getPiece(i-1, j-1) == Type.BLACK_KING) tempboard.numBlackKingPieces--;
                tempboard.board[i-1][j-1] = Type.EMPTY;

                Move mov = new Move();
                ArrayList<Pair<Integer, Integer>> movements = new ArrayList<>(move.getMovements());
                mov.setMovements(movements);
                int movementsSize = mov.getMovements().size();

                move = tempboard.addPossibleMovementsForNormalPieceInKilling(possibleMoves, move, side, i - 2, j - 2, 0, p, s, 1);

                mov = tempboard.addPossibleMovementsForNormalPieceInKilling(possibleMoves, mov, side, i, j, 1, p, s, previous);
                mov.setInitialRow(p);
                mov.setInitialCol(s);
                mov.setKilling(true);
                if (mov.getMovements().size() > movementsSize) possibleMoves.add(mov);
            } else if (getPiece(i - 2, j + 2) == Type.EMPTY && turn < 2 && getPiece(i - 1, j + 1) == Type.BLACK || getPiece(i - 1, j + 1) == Type.BLACK_KING && previous != 3) {
                Board tempboard = this.copy();
                if (tempboard.getPiece(i-1, j+1) == Type.BLACK) tempboard.numBlackNormalPieces--;
                else if (tempboard.getPiece(i-1, j+1) == Type.BLACK_KING) tempboard.numBlackKingPieces--;
                tempboard.board[i-1][j+1] = Type.EMPTY;

                Move mov = new Move();
                ArrayList<Pair<Integer, Integer>> movements = new ArrayList<>(move.getMovements());
                mov.setMovements(movements);
                int movementsSize = mov.getMovements().size();

                move = tempboard.addPossibleMovementsForNormalPieceInKilling(possibleMoves, move, side, i - 2, j + 2, 0, p, s, 2);

                mov = tempboard.addPossibleMovementsForNormalPieceInKilling(possibleMoves, mov, side,i, j, 2, p, s, previous);
                mov.setInitialRow(p);
                mov.setInitialCol(s);
                mov.setKilling(true);
                if (mov.getMovements().size() > movementsSize) possibleMoves.add(mov);
            } else if (getPiece(i + 2, j - 2) == Type.EMPTY && turn < 3 && getPiece(i + 1, j - 1) == Type.BLACK || getPiece(i + 1, j - 1) == Type.BLACK_KING && previous != 2) {
                Board tempboard = this.copy();
                if (tempboard.getPiece(i+1, j-1) == Type.BLACK) tempboard.numBlackNormalPieces--;
                else if (tempboard.getPiece(i+1, j-1) == Type.BLACK_KING) tempboard.numBlackKingPieces--;
                tempboard.board[i+1][j-1] = Type.EMPTY;

                Move mov = new Move();
                ArrayList<Pair<Integer, Integer>> movements = new ArrayList<>(move.getMovements());
                mov.setMovements(movements);
                int movementsSize = mov.getMovements().size();

                move = tempboard.addPossibleMovementsForNormalPieceInKilling(possibleMoves, move, side, i + 2, j - 2, 0, p, s, 3);

                mov = tempboard.addPossibleMovementsForNormalPieceInKilling(possibleMoves, mov, side,i, j, 3, p, s, previous);
                mov.setInitialRow(p);
                mov.setInitialCol(s);
                mov.setKilling(true);
                if (mov.getMovements().size() > movementsSize) possibleMoves.add(mov);
            } else if (getPiece(i + 2, j + 2) == Type.EMPTY && turn < 4 && getPiece(i + 1, j + 1) == Type.BLACK || getPiece(i + 1, j + 1) == Type.BLACK_KING && previous != 1) {
                Board tempboard = this.copy();
                if (tempboard.getPiece(i+1, j+1) == Type.BLACK) tempboard.numBlackNormalPieces--;
                else if (tempboard.getPiece(i+1, j+1) == Type.BLACK_KING) tempboard.numBlackKingPieces--;
                tempboard.board[i+1][j+1] = Type.EMPTY;

                Move mov = new Move();
                ArrayList<Pair<Integer, Integer>> movements = new ArrayList<>(move.getMovements());
                mov.setMovements(movements);
                int movementsSize = mov.getMovements().size();

                move = tempboard.addPossibleMovementsForNormalPieceInKilling(possibleMoves, move, side, i + 2, j + 2, 0, p, s, 4);

                mov = tempboard.addPossibleMovementsForNormalPieceInKilling(possibleMoves, mov, side,i, j, 4, p, s, previous);
                mov.setInitialRow(p);
                mov.setInitialCol(s);
                mov.setKilling(true);
                if (mov.getMovements().size() > movementsSize) possibleMoves.add(mov);
            }
        }

        return move;
    }

    // Function to add kill moves for king pieces
    public Move addPossibleMovesForKingPieceInKilling(ArrayList<Move> possibleMoves, Move move, Player.Side side, boolean[][] booleanBoard, int i, int j, int p, int s) {

        int k, l;
        if (side == Player.Side.BLACK) {
            int initialSize = 0;
            if (move != null) initialSize = move.getMovements().size();
            // Direction UP-LEFT
            k = i;
            l = j;
            while (getPiece(k-1,l-1) == Type.EMPTY && booleanBoard[k - 1][l - 1]) {
                --k;
                --l;
            }

            if (getPiece(k-1,l-1) == Type.WHITE || getPiece(k-1,l-1) == Type.WHITE_KING && booleanBoard[k][l]) {

                --k;
                --l;
                Board tempboard = this.copy();
                if (tempboard.getPiece(k, l) == Type.WHITE) tempboard.numWhiteNormalPieces--;
                else if (tempboard.getPiece(k, l) == Type.WHITE_KING) tempboard.numWhiteKingPieces--;
                tempboard.board[k][l] = Type.EMPTY;
                booleanBoard[k][l] = false;

                while (getPiece(k-1,l-1) == Type.EMPTY && booleanBoard[k - 1][l - 1]) {
                    Move mov = new Move();
                    mov.setInitialRow(p);
                    mov.setInitialCol(s);
                    mov.setKilling(true);
                    Pair<Integer, Integer> movement = new Pair<>(k - 1, l - 1);

                    if (move != null) {
                        if (move.getMovements().size() >= initialSize) {
                            move.getMovements().subList(initialSize, move.getMovements().size()).clear();
                        }
                        if (move.getMovements().get(move.getMovements().size()-1).getFirst() == k && move.getMovements().get(move.getMovements().size()-1).getSecond() == l) move.getMovements().remove(move.getMovements().size()-1);
                        move.getMovements().add(movement);
                        int movementsSize = move.getMovements().size();
                        Move mov2 = move.copy();
                        mov2 = tempboard.addPossibleMovesForKingPieceInKilling(possibleMoves, mov2, side, booleanBoard, k - 1, l - 1, p, s);
                        mov.setInitialRow(p);
                        mov.setInitialCol(s);
                        mov.setMovements(mov2.getMovements());
                        if (mov.getMovements().size() == movementsSize) possibleMoves.add(mov);
                    }
                    else {
                        mov.getMovements().add(movement);
                        possibleMoves.add(mov);
                        Move mov2 = mov.copy();
                        tempboard.addPossibleMovesForKingPieceInKilling(possibleMoves, mov2, side, booleanBoard,k - 1, l - 1, p, s);
                    }

                    --k;
                    --l;
                }
            }

            // Direction UP-RIGHT
            k = i;
            l = j;
            while (getPiece(k-1,l+1) == Type.EMPTY && booleanBoard[k - 1][l + 1]) {
                --k;
                ++l;
            }

            if (getPiece(k-1,l+1) == Type.WHITE || getPiece(k-1,l+1) == Type.WHITE_KING && booleanBoard[k][l]) {
                --k;
                ++l;
                Board tempboard = this.copy();
                if (tempboard.getPiece(k, l) == Type.WHITE) tempboard.numWhiteNormalPieces--;
                else if (tempboard.getPiece(k, l) == Type.WHITE_KING) tempboard.numWhiteKingPieces--;
                tempboard.board[k][l] = Type.EMPTY;
                booleanBoard[k][l] = false;

                while (getPiece(k-1,l+1) == Type.EMPTY && booleanBoard[k - 1][l + 1]) {
                    Move mov = new Move();
                    mov.setInitialRow(p);
                    mov.setInitialCol(s);
                    mov.setKilling(true);
                    Pair<Integer, Integer> movement = new Pair<>(k - 1, l + 1);

                    if (move != null) {
                        if (move.getMovements().size() >= initialSize) {
                            move.getMovements().subList(initialSize, move.getMovements().size()).clear();
                        }
                        if (move.getMovements().get(move.getMovements().size()-1).getFirst() == k && move.getMovements().get(move.getMovements().size()-1).getSecond() == l) move.getMovements().remove(move.getMovements().size()-1);
                        move.getMovements().add(movement);
                        int movementsSize = move.getMovements().size();
                        Move mov2 = move.copy();
                        mov2 = tempboard.addPossibleMovesForKingPieceInKilling(possibleMoves, mov2, side, booleanBoard, k - 1, l + 1, p, s);
                        mov.setInitialRow(p);
                        mov.setInitialCol(s);
                        mov.setMovements(mov2.getMovements());
                        if (mov.getMovements().size() == movementsSize) possibleMoves.add(mov);
                    }
                    else {
                        mov.getMovements().add(movement);
                        possibleMoves.add(mov);
                        Move mov2 = mov.copy();
                        tempboard.addPossibleMovesForKingPieceInKilling(possibleMoves, mov2, side, booleanBoard,k - 1, l + 1, p, s);
                    }

                    --k;
                    ++l;
                }
            }

            // Direction DOWN-LEFT
            k = i;
            l = j;
            while (getPiece(k+1,l-1) == Type.EMPTY && booleanBoard[k + 1][l - 1]) {
                ++k;
                --l;
            }

            if (getPiece(k+1,l-1) == Type.WHITE || getPiece(k+1,l-1) == Type.WHITE_KING && booleanBoard[k][l]) {
                ++k;
                --l;
                Board tempboard = this.copy();
                if (tempboard.getPiece(k, l) == Type.WHITE) tempboard.numWhiteNormalPieces--;
                else if (tempboard.getPiece(k, l) == Type.WHITE_KING) tempboard.numWhiteKingPieces--;
                tempboard.board[k][l] = Type.EMPTY;
                booleanBoard[k][l] = false;

                while (getPiece(k+1,l-1) == Type.EMPTY && booleanBoard[k + 1][l - 1]) {
                    Move mov = new Move();
                    mov.setInitialRow(p);
                    mov.setInitialCol(s);
                    mov.setKilling(true);
                    Pair<Integer, Integer> movement = new Pair<>(k + 1, l - 1);

                    if (move != null) {
                        if (move.getMovements().size() >= initialSize) {
                            move.getMovements().subList(initialSize, move.getMovements().size()).clear();
                        }
                        if (move.getMovements().get(move.getMovements().size()-1).getFirst() == k && move.getMovements().get(move.getMovements().size()-1).getSecond() == l) move.getMovements().remove(move.getMovements().size()-1);
                        move.getMovements().add(movement);
                        int movementsSize = move.getMovements().size();
                        Move mov2 = move.copy();
                        mov2 = tempboard.addPossibleMovesForKingPieceInKilling(possibleMoves, mov2, side, booleanBoard,k + 1, l - 1, p, s);
                        mov.setInitialRow(p);
                        mov.setInitialCol(s);
;                       mov.setMovements(mov2.getMovements());
                        if (mov.getMovements().size() == movementsSize) possibleMoves.add(mov);
                    }
                    else {
                        mov.getMovements().add(movement);
                        possibleMoves.add(mov);
                        Move mov2 = mov.copy();
                        tempboard.addPossibleMovesForKingPieceInKilling(possibleMoves, mov2, side, booleanBoard,k + 1, l - 1, p, s);
                    }

                    ++k;
                    --l;
                }
            }

            // Direction DOWN-RIGHT
            k = i;
            l = j;
            while (getPiece(k+1,l+1) == Type.EMPTY && booleanBoard[k + 1][l + 1]) {
                ++k;
                ++l;
            }

            if (getPiece(k+1,l+1) == Type.WHITE || getPiece(k+1,l+1) == Type.WHITE_KING && booleanBoard[k][l]) {
                ++k;
                ++l;
                Board tempboard = this.copy();
                if (tempboard.getPiece(k, l) == Type.WHITE) tempboard.numWhiteNormalPieces--;
                else if (tempboard.getPiece(k, l) == Type.WHITE_KING) tempboard.numWhiteKingPieces--;
                tempboard.board[k][l] = Type.EMPTY;
                booleanBoard[k][l] = false;

                while (getPiece(k+1,l+1) == Type.EMPTY && booleanBoard[k + 1][l + 1]) {
                    Move mov = new Move();
                    mov.setInitialRow(p);
                    mov.setInitialCol(s);
                    mov.setKilling(true);
                    Pair<Integer, Integer> movement = new Pair<>(k + 1, l + 1);

                    if (move != null) {
                        if (move.getMovements().size() >= initialSize) {
                            move.getMovements().subList(initialSize, move.getMovements().size()).clear();
                        }
                        if (move.getMovements().get(move.getMovements().size()-1).getFirst() == k && move.getMovements().get(move.getMovements().size()-1).getSecond() == l) move.getMovements().remove(move.getMovements().size()-1);
                        move.getMovements().add(movement);
                        int movementsSize = move.getMovements().size();
                        Move mov2 = move.copy();
                        mov2 = tempboard.addPossibleMovesForKingPieceInKilling(possibleMoves, mov2, side, booleanBoard,k + 1, l + 1, p, s);
                        mov.setInitialRow(p);
                        mov.setInitialCol(s);
                        mov.setMovements(mov2.getMovements());
                        if (mov.getMovements().size() == movementsSize) possibleMoves.add(mov);
                    }
                    else {
                        mov.getMovements().add(movement);
                        possibleMoves.add(mov);
                        Move mov2 = mov.copy();
                        tempboard.addPossibleMovesForKingPieceInKilling(possibleMoves, mov2, side, booleanBoard,k + 1, l + 1, p, s);
                    }

                    ++k;
                    ++l;
                }
            }
        }

        else {
            int initialSize = 0;
            if (move != null) initialSize = move.getMovements().size();
            // Direction UP-LEFT
            k = i;
            l = j;
            while (getPiece(k-1,l-1) == Type.EMPTY && booleanBoard[k - 1][l - 1]) {
                --k;
                --l;
            }

            if (getPiece(k-1,l-1) == Type.BLACK || getPiece(k-1,l-1) == Type.BLACK_KING && booleanBoard[k][l]) {

                --k;
                --l;
                Board tempboard = this.copy();
                if (tempboard.getPiece(k, l) == Type.BLACK) tempboard.numBlackNormalPieces--;
                else if (tempboard.getPiece(k, l) == Type.BLACK_KING) tempboard.numBlackKingPieces--;
                tempboard.board[k][l] = Type.EMPTY;
                booleanBoard[k][l] = false;

                while (getPiece(k-1,l-1) == Type.EMPTY && booleanBoard[k - 1][l - 1]) {
                    Move mov = new Move();
                    mov.setInitialRow(p);
                    mov.setInitialCol(s);
                    mov.setKilling(true);
                    Pair<Integer, Integer> movement = new Pair<>(k - 1, l - 1);

                    if (move != null) {
                        if (move.getMovements().size() >= initialSize) {
                            move.getMovements().subList(initialSize, move.getMovements().size()).clear();
                        }
                        if (move.getMovements().get(move.getMovements().size()-1).getFirst() == k && move.getMovements().get(move.getMovements().size()-1).getSecond() == l) move.getMovements().remove(move.getMovements().size()-1);
                        move.getMovements().add(movement);
                        int movementsSize = move.getMovements().size();
                        Move mov2 = move.copy();
                        mov2 = tempboard.addPossibleMovesForKingPieceInKilling(possibleMoves, mov2, side, booleanBoard, k - 1, l - 1, p, s);
                        mov.setInitialRow(p);
                        mov.setInitialCol(s);
                        mov.setMovements(mov2.getMovements());
                        if (mov.getMovements().size() == movementsSize) possibleMoves.add(mov);
                    }
                    else {
                        mov.getMovements().add(movement);
                        possibleMoves.add(mov);
                        Move mov2 = mov.copy();
                        tempboard.addPossibleMovesForKingPieceInKilling(possibleMoves, mov2, side, booleanBoard,k - 1, l - 1, p, s);
                    }

                    --k;
                    --l;
                }
            }

            // Direction UP-RIGHT
            k = i;
            l = j;
            while (getPiece(k-1,l+1) == Type.EMPTY && booleanBoard[k - 1][l + 1]) {
                --k;
                ++l;
            }

            if (getPiece(k-1,l+1) == Type.BLACK || getPiece(k-1,l+1) == Type.BLACK_KING && booleanBoard[k][l]) {
                --k;
                ++l;
                Board tempboard = this.copy();
                if (tempboard.getPiece(k, l) == Type.BLACK) tempboard.numBlackNormalPieces--;
                else if (tempboard.getPiece(k, l) == Type.BLACK_KING) tempboard.numBlackKingPieces--;
                tempboard.board[k][l] = Type.EMPTY;
                booleanBoard[k][l] = false;

                while (getPiece(k-1,l+1) == Type.EMPTY && booleanBoard[k - 1][l + 1]) {
                    Move mov = new Move();
                    mov.setInitialRow(p);
                    mov.setInitialCol(s);
                    mov.setKilling(true);
                    Pair<Integer, Integer> movement = new Pair<>(k - 1, l + 1);

                    if (move != null) {
                        if (move.getMovements().size() >= initialSize) {
                            move.getMovements().subList(initialSize, move.getMovements().size()).clear();
                        }
                        if (move.getMovements().get(move.getMovements().size()-1).getFirst() == k && move.getMovements().get(move.getMovements().size()-1).getSecond() == l) move.getMovements().remove(move.getMovements().size()-1);
                        move.getMovements().add(movement);
                        int movementsSize = move.getMovements().size();
                        Move mov2 = move.copy();
                        mov2 = tempboard.addPossibleMovesForKingPieceInKilling(possibleMoves, mov2, side, booleanBoard, k - 1, l + 1, p, s);
                        mov.setInitialRow(p);
                        mov.setInitialCol(s);
                        mov.setMovements(mov2.getMovements());
                        if (mov.getMovements().size() == movementsSize) possibleMoves.add(mov);
                    }
                    else {
                        mov.getMovements().add(movement);
                        possibleMoves.add(mov);
                        Move mov2 = mov.copy();
                        tempboard.addPossibleMovesForKingPieceInKilling(possibleMoves, mov2, side, booleanBoard,k - 1, l + 1, p, s);
                    }

                    --k;
                    ++l;
                }
            }

            // Direction DOWN-LEFT
            k = i;
            l = j;
            while (getPiece(k+1,l-1) == Type.EMPTY && booleanBoard[k + 1][l - 1]) {
                ++k;
                --l;
            }

            if (getPiece(k+1,l-1) == Type.BLACK || getPiece(k+1,l-1) == Type.BLACK_KING && booleanBoard[k][l]) {
                ++k;
                --l;
                Board tempboard = this.copy();
                if (tempboard.getPiece(k, l) == Type.BLACK) tempboard.numBlackNormalPieces--;
                else if (tempboard.getPiece(k, l) == Type.BLACK_KING) tempboard.numBlackKingPieces--;
                tempboard.board[k][l] = Type.EMPTY;
                booleanBoard[k][l] = false;

                while (getPiece(k+1,l-1) == Type.EMPTY && booleanBoard[k + 1][l - 1]) {
                    Move mov = new Move();
                    mov.setInitialRow(p);
                    mov.setInitialCol(s);
                    mov.setKilling(true);
                    Pair<Integer, Integer> movement = new Pair<>(k + 1, l - 1);

                    if (move != null) {
                        if (move.getMovements().size() >= initialSize) {
                            move.getMovements().subList(initialSize, move.getMovements().size()).clear();
                        }
                        if (move.getMovements().get(move.getMovements().size()-1).getFirst() == k && move.getMovements().get(move.getMovements().size()-1).getSecond() == l) move.getMovements().remove(move.getMovements().size()-1);
                        move.getMovements().add(movement);
                        int movementsSize = move.getMovements().size();
                        Move mov2 = move.copy();
                        mov2 = tempboard.addPossibleMovesForKingPieceInKilling(possibleMoves, mov2, side, booleanBoard,k + 1, l - 1, p, s);
                        mov.setInitialRow(p);
                        mov.setInitialCol(s);
                        ;                       mov.setMovements(mov2.getMovements());
                        if (mov.getMovements().size() == movementsSize) possibleMoves.add(mov);
                    }
                    else {
                        mov.getMovements().add(movement);
                        possibleMoves.add(mov);
                        Move mov2 = mov.copy();
                        tempboard.addPossibleMovesForKingPieceInKilling(possibleMoves, mov2, side, booleanBoard,k + 1, l - 1, p, s);
                    }

                    ++k;
                    --l;
                }
            }

            // Direction DOWN-RIGHT
            k = i;
            l = j;
            while (getPiece(k+1,l+1) == Type.EMPTY && booleanBoard[k + 1][l + 1]) {
                ++k;
                ++l;
            }

            if (getPiece(k+1,l+1) == Type.BLACK || getPiece(k+1,l+1) == Type.BLACK_KING && booleanBoard[k][l]) {
                ++k;
                ++l;
                Board tempboard = this.copy();
                if (tempboard.getPiece(k, l) == Type.BLACK) tempboard.numBlackNormalPieces--;
                else if (tempboard.getPiece(k, l) == Type.BLACK_KING) tempboard.numBlackKingPieces--;
                tempboard.board[k][l] = Type.EMPTY;
                booleanBoard[k][l] = false;

                while (getPiece(k+1,l+1) == Type.EMPTY && booleanBoard[k + 1][l + 1]) {
                    Move mov = new Move();
                    mov.setInitialRow(p);
                    mov.setInitialCol(s);
                    mov.setKilling(true);
                    Pair<Integer, Integer> movement = new Pair<>(k + 1, l + 1);

                    if (move != null) {
                        if (move.getMovements().size() >= initialSize) {
                            move.getMovements().subList(initialSize, move.getMovements().size()).clear();
                        }
                        if (move.getMovements().get(move.getMovements().size()-1).getFirst() == k && move.getMovements().get(move.getMovements().size()-1).getSecond() == l) move.getMovements().remove(move.getMovements().size()-1);
                        move.getMovements().add(movement);
                        int movementsSize = move.getMovements().size();
                        Move mov2 = move.copy();
                        mov2 = tempboard.addPossibleMovesForKingPieceInKilling(possibleMoves, mov2, side, booleanBoard,k + 1, l + 1, p, s);
                        mov.setInitialRow(p);
                        mov.setInitialCol(s);
                        mov.setMovements(mov2.getMovements());
                        if (mov.getMovements().size() == movementsSize) possibleMoves.add(mov);
                    }
                    else {
                        mov.getMovements().add(movement);
                        possibleMoves.add(mov);
                        Move mov2 = mov.copy();
                        tempboard.addPossibleMovesForKingPieceInKilling(possibleMoves, mov2, side, booleanBoard,k + 1, l + 1, p, s);
                    }

                    ++k;
                    ++l;
                }
            }
        }

        return move;

    }

    public Board makeMove(Move move, boolean maximizing) {

        int initialRow = move.getInitialRow();
        int initialCol = move.getInitialCol();

        int finalRow = -1;
        int finalCol = -1;

        for (Pair<Integer, Integer> movement : move.getMovements()) {
            finalRow = movement.getFirst();
            finalCol = movement.getSecond();

            if (initialRow > finalRow) {
                if (initialCol > finalCol) {
                    // UP-LEFT
                    int i = initialRow-1;
                    int j = initialCol-1;
                    while (i > finalRow && j > finalCol) {
                        if (getPiece(i, j) == Type.BLACK) {
                            board[i][j] = Type.EMPTY;
                            --numBlackNormalPieces;
                        }
                        else if (getPiece(i, j) == Type.BLACK_KING) {
                            board[i][j] = Type.EMPTY;
                            --numBlackKingPieces;
                        }
                        else if (getPiece(i, j) == Type.WHITE) {
                            board[i][j] = Type.EMPTY;
                            --numWhiteNormalPieces;
                        }
                        else if (getPiece(i, j) == Type.WHITE_KING) {
                            board[i][j] = Type.EMPTY;
                            --numWhiteKingPieces;
                        }
                        --i;
                        --j;
                    }
                }
                else {
                    // UP-RIGHT
                    int i = initialRow-1;
                    int j = initialCol+1;
                    while (i > finalRow && j < finalCol) {
                        if (getPiece(i, j) == Type.BLACK) {
                            board[i][j] = Type.EMPTY;
                            --numBlackNormalPieces;
                        }
                        else if (getPiece(i, j) == Type.BLACK_KING) {
                            board[i][j] = Type.EMPTY;
                            --numBlackKingPieces;
                        }
                        else if (getPiece(i, j) == Type.WHITE) {
                            board[i][j] = Type.EMPTY;
                            --numWhiteNormalPieces;
                        }
                        else if (getPiece(i, j) == Type.WHITE_KING) {
                            board[i][j] = Type.EMPTY;
                            --numWhiteKingPieces;
                        }
                        --i;
                        ++j;
                    }
                }
            }
            else {
                if (initialCol > finalCol) {
                    // DOWN-LEFT
                    int i = initialRow+1;
                    int j = initialCol-1;
                    while (i < finalRow && j > finalCol) {
                        if (getPiece(i, j) == Type.BLACK) {
                            board[i][j] = Type.EMPTY;
                            --numBlackNormalPieces;
                        }
                        else if (getPiece(i, j) == Type.BLACK_KING) {
                            board[i][j] = Type.EMPTY;
                            --numBlackKingPieces;
                        }
                        else if (getPiece(i, j) == Type.WHITE) {
                            board[i][j] = Type.EMPTY;
                            --numWhiteNormalPieces;
                        }
                        else if (getPiece(i, j) == Type.WHITE_KING) {
                            board[i][j] = Type.EMPTY;
                            --numWhiteKingPieces;
                        }
                        ++i;
                        --j;
                    }
                }
                else {
                    // DOWN-RIGHT
                    int i = initialRow+1;
                    int j = initialCol+1;
                    while (i < finalRow && j < finalCol) {
                        if (getPiece(i, j) == Type.BLACK) {
                            board[i][j] = Type.EMPTY;
                            --numBlackNormalPieces;
                        }
                        else if (getPiece(i, j) == Type.BLACK_KING) {
                            board[i][j] = Type.EMPTY;
                            --numBlackKingPieces;
                        }
                        else if (getPiece(i, j) == Type.WHITE) {
                            board[i][j] = Type.EMPTY;
                            --numWhiteNormalPieces;
                        }
                        else if (getPiece(i, j) == Type.WHITE_KING) {
                            board[i][j] = Type.EMPTY;
                            --numWhiteKingPieces;
                        }
                        ++i;
                        ++j;
                    }
                }
            }
            initialRow = movement.getFirst();
            initialCol = movement.getSecond();
        }

        initialRow = move.getInitialRow();
        initialCol = move.getInitialCol();

        if (finalRow != -1 && finalCol != -1) {
            board[finalRow][finalCol] = board[initialRow][initialCol];
            board[initialRow][initialCol] = Type.EMPTY;

            if (maximizing) {
                if (finalRow == 9) {
                    if (board[finalRow][finalCol] == Type.BLACK) {
                        board[finalRow][finalCol] = Type.BLACK_KING;
                        ++numBlackKingPieces;
                        --numBlackNormalPieces;
                    }
                    else if (board[finalRow][finalCol] == Type.WHITE){
                        board[finalRow][finalCol] = Type.WHITE_KING;
                        ++numWhiteKingPieces;
                        --numWhiteNormalPieces;
                    }
                }
            }
            else {
                if (finalRow == 0) {
                    if (board[finalRow][finalCol] == Type.BLACK) {
                        board[finalRow][finalCol] = Type.BLACK_KING;
                        ++numBlackKingPieces;
                        --numBlackNormalPieces;
                    }
                    else if (board[finalRow][finalCol] == Type.WHITE){
                        board[finalRow][finalCol] = Type.WHITE_KING;
                        ++numWhiteKingPieces;
                        --numWhiteNormalPieces;
                    }
                }
            }
        }

        return this;
    }

    public Board copy() {
        Type[][] newBoard = new Type[SIZE][SIZE];
        for(int i = 0; i < SIZE; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, SIZE);
        }
        return new Board(newBoard);
    }
}
