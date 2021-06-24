import util.Pair;

import java.util.*;

public class Algorithms {

    public boolean MinConflicts(Graph g, int maxSteps, int numberColors) {
        for (int i = 0; i < maxSteps; ++i) {
            int min = 99999;
            int id = -1;
            for(int j = 0; j < g.nodes.size(); ++j) {
                int num = countNeighboursSameColor(g.nodes.get(j), g.nodes.get(j).getNeighbours());
                if (num < min && num != 0) {
                    id = g.nodes.get(j).id;
                    min = num;
                }
            }
            if (min != 99999) {
                if (g.nodes.get(id).color == numberColors-1) g.nodes.get(id).color = 0;
                else ++g.nodes.get(id).color;
            }
            else return true;
        }
        return false;
    }

    private int countNeighboursSameColor(Node node, ArrayList<Node> neighbours){
        int count = 0;
        for (Node n: neighbours){
            if (node.color == n.color) count++;
        }
        return count;
    }

    public boolean BackTracking(Graph g, int index, int numberColors) {
        // if all vertices are covered
        if (index == g.nodes.size()) return true;

        for (int i = 0; i < numberColors; ++i) {
            // Color node
            g.nodes.get(index).color = i;

            // Check if it's safe
            if (isSafe(g.nodes.get(index), g.nodes.get(index).getNeighbours())) {
                //color next vertex
                if (BackTracking(g, index+1, numberColors)) return true;
                //if next node was not colorable, reset the color and try next one
                g.nodes.get(index).color = -1;
            }
        }
        return false;
    }

    private boolean isSafe(Node node, ArrayList<Node> neighbours){
        for (Node n: neighbours){
            if (node.color == n.color)
                return false;
        }
        return true;
    }

    public boolean BackTracking_ForwardChecking(Graph g, int index, int numberColors, int eliminatedColor) {
        // if all vertices are covered
        if (index == g.nodes.size()) return true;

        for (int i = 0; i < numberColors; ++i) {
            if (eliminatedColor != i) {
                // Color node
                g.nodes.get(index).color = i;

                // Check if it's safe
                if (isSafe(g.nodes.get(index), g.nodes.get(index).getNeighbours())) {
                    eliminatedColor = i;

                    //color next vertex
                    if (BackTracking_ForwardChecking(g, index + 1, numberColors, eliminatedColor)) return true;
                    //if next node was not colorable, reset the color and try next one
                    g.nodes.get(index).color = -1;
                }
            }
        }
        return false;
    }

    public boolean BackTracking_AC3(Graph g, int index) {

        if (!AC3(g)) return false;

        // if all vertices are covered
        if (index == g.nodes.size()) return true;

        for (int i = 0; i < g.nodes.get(index).possibleColors.size(); ++i) {
            // Color node
            g.nodes.get(index).color = g.nodes.get(index).possibleColors.get(i);

            // Check if it's safe
            if (isSafe(g.nodes.get(index), g.nodes.get(index).getNeighbours())) {
                // Delete value all neighbours
                //deleteValueNeigbours(g.nodes.get(index).getNeighbours(), g.nodes.get(index).color);

                //color next vertex
                if (BackTracking_AC3(g, index+1)) return true;
                //if next node was not colorable, reset the color and try next one
                g.nodes.get(index).color = -1;
            }
        }
        return false;

    }

    private void deleteValueNeigbours(ArrayList<Node> neighbours, int color){
        for (Node n: neighbours){
            for (int i = 0; i < n.possibleColors.size(); ++i) {
                if (n.possibleColors.get(i) == color) {
                    n.possibleColors.remove(i);
                    break;
                }
            }
        }
    }

    public boolean AC3(Graph g) {
        ArrayList<Pair<Integer, Integer>> queue = new ArrayList<>();

        for(int i = 0; i < g.segments.size(); ++i) {
                Pair<Integer, Integer> pair = new Pair<>(g.segments.get(i).getNode1().id, g.segments.get(i).getNode2().id);
                queue.add(pair);
                Pair<Integer, Integer> pair2 = new Pair<>(g.segments.get(i).getNode2().id, g.segments.get(i).getNode1().id);
                queue.add(pair2);
        }

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> pair = queue.get(0);
            queue.remove(0);
            if (removeValues(pair, g)) {
                Node n = g.nodes.get(pair.getFirst());
                if (n.possibleColors.isEmpty()) return false;
                for (int i = 0; i < n.getNeighbours().size(); ++i) {
                    pair = new Pair<>(n.getNeighbours().get(i).id, n.id);
                    queue.add(pair);
                }
            }
        }

        return true;
    }

    private boolean removeValues(Pair<Integer, Integer> pair, Graph g) {
        boolean valueRemoved = false;
        boolean found = false;
        Node n1 = g.nodes.get(pair.getFirst());
        Node n2 = g.nodes.get(pair.getSecond());
        for (int i = 0; i < n1.possibleColors.size(); ++i) {
            for (int j = 0; j < n2.possibleColors.size(); ++j) {
                if (!n1.possibleColors.get(i).equals(n2.possibleColors.get(j))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                n1.possibleColors.remove(i);
                valueRemoved = true;
            }
        }

        return valueRemoved;
    }

}
