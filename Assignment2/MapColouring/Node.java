import java.util.ArrayList;

public class Node {
    int id;
    double x;
    double y;
    int color;
    ArrayList<Integer> possibleColors;
    ArrayList<Node> neighbours;

    public Node() {
        neighbours = new ArrayList<>();
        possibleColors = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(ArrayList<Node> neighbours) {
        this.neighbours = neighbours;
    }

    public void addNeighbour(Node neighbour) {
        this.neighbours.add(neighbour);
    }

    public ArrayList<Integer> getPossibleColors() {
        return possibleColors;
    }

    public void setPossibleColors(ArrayList<Integer> possibleColors) {
        this.possibleColors = possibleColors;
    }
}
