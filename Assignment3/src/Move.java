import java.util.ArrayList;

public class Move {
    private int initialRow;
    private int initialCol;
    private boolean killing;

    // Movements is defined by a set of: stopRow, stopColumn.
    private ArrayList<Pair<Integer,Integer>> movements;

    public Move() {
        killing = false;
        movements = new ArrayList<>();
    }

    public Move(ArrayList<Pair<Integer,Integer>> movements) {
        killing = false;
        this.movements = movements;
    }

    public int getInitialRow() {
        return initialRow;
    }

    public void setInitialRow(int initialRow) {
        this.initialRow = initialRow;
    }

    public int getInitialCol() {
        return initialCol;
    }

    public void setInitialCol(int initialCol) {
        this.initialCol = initialCol;
    }

    public ArrayList<Pair<Integer, Integer>> getMovements() {
        return movements;
    }

    public void setMovements(ArrayList<Pair<Integer, Integer>> movements) {
        this.movements = movements;
    }

    public boolean isKilling() {
        return killing;
    }

    public void setKilling(boolean killing) {
        this.killing = killing;
    }

    public Move copy() {
        ArrayList<Pair<Integer, Integer>> newMovements = new ArrayList<>(movements);
        return new Move(newMovements);
    }
}
