import util.Pair;
import java.util.*;

import static java.lang.Math.*;

public class Graph {

    ArrayList<Node> nodes;
    ArrayList<Segment> segments;

    public Graph() {
        nodes = new ArrayList<>();
        segments = new ArrayList<>();
    }

    public void generateGraph(int n) {
        Random random = new Random();
        double d;
        boolean b;

        // Generate n nodes in unit square
        for (int i = 0; i < n; ++i) {
            Node node = new Node();

            // Set X to node
            d = random.nextDouble();
            d = round(d, 3);
            b = random.nextBoolean();
            if (!b) d = -d;
            node.setX(d);

            // Set Y to node
            d = random.nextDouble();
            d = round(d, 3);
            b = random.nextBoolean();
            if (!b) d = -d;
            node.setY(d);

            node.setId(i);

            //System.out.println("Node " + i + ": " + node.x + " , " + node.y);
            nodes.add(node);
        }

        // Generate the net
        generateNet();

        for(int i = 0; i < segments.size(); ++i) {
            System.out.println("Node1 : " + segments.get(i).node1.id + "  and   Node2 : " + segments.get(i).node2.id);
        }
    }

    private void generateNet() {
        ArrayList<Node> auxiliar = new ArrayList<>(nodes);

        Pair<Integer,Double>[][] distances = new Pair[nodes.size()][nodes.size()];
        calculateDistances(distances);
        /*for(int i = 0; i < distances.length; ++i) {
            for(int j = 0; j < distances.length; ++j) {
                System.out.print(distances[i][j].getSecond() + " ");
            }
            System.out.println();
        }*/

        while (auxiliar.size()>1) {
            int randomNode = getRandomNumberUsingNextInt(0, auxiliar.size());
            boolean found = false;
            int i = 1;

            while (i < distances[randomNode].length && !found) {
                if (!areNeighbours(auxiliar.get(randomNode).id, distances[auxiliar.get(randomNode).id][i].getFirst())) {
                    if (!checkSegmentsCross(auxiliar.get(randomNode).id, distances[auxiliar.get(randomNode).id][i].getFirst())) {
                        Segment segment = new Segment();
                        segment.setNode1(nodes.get(auxiliar.get(randomNode).id));
                        segment.setNode2(nodes.get(distances[auxiliar.get(randomNode).id][i].getFirst()));
                        segments.add(segment);
                        nodes.get(auxiliar.get(randomNode).id).addNeighbour(nodes.get(distances[auxiliar.get(randomNode).id][i].getFirst()));
                        nodes.get(distances[auxiliar.get(randomNode).id][i].getFirst()).addNeighbour(nodes.get(auxiliar.get(randomNode).id));
                        found = true;
                    }
                }
                ++i;
            }

            if (!found) {
                auxiliar.remove(randomNode);}
        }
    }

    private boolean areNeighbours (int node1, int node2) {
        Node n = nodes.get(node1);
        for(int i = 0; i < n.getNeighbours().size(); ++i) {
            if(n.getNeighbours().get(i).id == node2) return true;
        }
        return false;
    }

    private void calculateDistances(Pair<Integer,Double>[][] distances) {
        // Calculate distances of the upper triangle matrix part
        for (int i = 0; i < nodes.size(); ++i) {
            for (int j = i; j < nodes.size(); ++j) {
                Node node1 = nodes.get(i);
                Node node2 = nodes.get(j);
                double dist = sqrt(pow(node2.x - node1.x, 2) + pow(node2.y - node1.y, 2));
                dist = round(dist, 3);
                Pair<Integer, Double> pair = new Pair<>(j, dist);

                distances[i][j] = pair;
            }
        }

        // Copy distances to the lower triangle part of the matrix
        // This is only for efficiency
        for (int i = 1; i < nodes.size(); ++i) {
            for (int j = 0; j < i; ++j) {
                double dist = distances[j][i].getSecond();
                Pair<Integer, Double> pair = new Pair<>(j, dist);
                distances[i][j] = pair;
            }
        }

        // Order each row per distance to search more efficiently the minimum distance
        for (Pair<Integer, Double>[] distance : distances) {
            Arrays.sort(distance, (Comparator<Pair>) (p1, p2) -> {
                if ((double) p1.getSecond() > (double) p2.getSecond()) return 1;
                else if ((double) p1.getSecond() < (double) p2.getSecond()) return -1;
                else return 0;
            });
        }

    }

    private boolean checkSegmentsCross (int node1, int node2) {
        for (Segment segment : segments) {
            if (doIntersect(nodes.get(node1), nodes.get(node2), segment.node1, segment.node2)) return true;
        }

        return false;
    }

    // Given three colinear points p, q, r, the function checks if point q lies on line segment 'pr'
    static boolean onSegment(Node p, Node q, Node r)
    {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) && q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
            return true;

        return false;
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are colinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    static int orientation(Node p, Node q, Node r)
    {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0; // colinear
        else if (val > 0) return 1; //clock
        else return 2; //counterclock wise
    }

    // The main function that returns true if line segment 'p1q1' and 'p2q2' intersect.
    static boolean doIntersect(Node p1, Node q1, Node p2, Node q2) {

        // Find the four orientations needed for general and special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 + o2 == 0) return false;
        else if (o1 + o3 == 0) return false;
        else if (o1 + o4 == 0) return false;
        else if (o2 + o3 == 0) return false;
        else if (o2 + o4 == 0) return false;
        else if (o3 + o4 == 0) return false;

        // General case
        if (o1 != o2 && o3 != o4) {
            if (o1 + o2 == 0) return false;
            else if (o1 + o3 == 0) return false;
            else if (o1 + o4 == 0) return false;
            else if (o2 + o3 == 0) return false;
            else if (o2 + o4 == 0) return false;
            else if (o3 + o4 == 0) return false;
            return true;
        }


        // Special Cases
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and q2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        return o4 == 0 && onSegment(p2, q1, q2);// Doesn't fall in any of the above cases
    }

    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void printGraph() {
        System.out.println();

        System.out.println("Printing graph information: Coordinates, color and neighbours per each node");
        System.out.println();

        for (int i = 0; i < nodes.size(); ++i) {
            System.out.println("Node " + i + ": " + nodes.get(i).getX() + " , " + nodes.get(i).getY());
            System.out.println("Color = " + nodes.get(i).color);
            for(int j = 0; j < nodes.get(i).getNeighbours().size(); ++j) {
                System.out.println("Neighbour: " + nodes.get(i).getNeighbours().get(j).getId());
            }
            System.out.println();
        }

        System.out.println("Printing graph information: Segments");
        System.out.println();

        for (int i = 0; i < segments.size(); ++i) {
            System.out.println("Segment " + segments.get(i).getNode1().getId() + " , " + segments.get(i).getNode2().getId());
        }
        System.out.println();
        System.out.println();
    }

    public void printSolution() {
        System.out.println();

        for (int i = 0; i < nodes.size(); ++i) System.out.println("Node " + i + "--> " + "Color = " + nodes.get(i).color);

        System.out.println();
    }
}
