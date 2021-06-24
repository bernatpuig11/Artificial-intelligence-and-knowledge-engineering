import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Main {

        // Args[0] --> n (nodes in the graph)
        // Args[1] --> nColors (number of k-coloring)
        public static void main(String[] args) throws FileNotFoundException {
                PrintWriter out = new PrintWriter("OutputProblemSize.txt");
                PrintWriter out2 = new PrintWriter("OutputMinConflicts.txt");
                PrintWriter out3 = new PrintWriter("OutputBacktracking.txt");
                PrintWriter out4 = new PrintWriter("OutputBackTracking_ForwardChecking.txt");
                PrintWriter out5 = new PrintWriter("OutputBackTracking_AC3.txt");

                int n = Integer.parseInt(args[0]);
                int nColors = Integer.parseInt(args[1]);

                Graph g = new Graph();
                g.generateGraph(n);
                //g.printGraph();

                for(int i = 0; i < g.nodes.size(); ++i) {
                        Random random = new Random();
                        g.nodes.get(i).color = random.nextInt(nColors);
                }

                Algorithms a = new Algorithms();

                System.out.println("Solution with Min-Conflicts algorithm");
                System.out.println();
                long start1 = System.nanoTime();
                long end1 = System.nanoTime();
                if (a.MinConflicts(g, 100, nColors)) {
                        end1 = System.nanoTime();
                        System.out.println("Elapsed Time in nano seconds: "+ (end1-start1));
                                             g.printSolution();
                }
                else System.out.println("Not possible to colour the map");
                System.out.println();

                for(int i = 0; i < g.nodes.size(); ++i) g.nodes.get(i).color = -1;

                System.out.println("Solution with BackTracking algorithm");
                long start2 = System.nanoTime();
                long end2 = System.nanoTime();
                if (a.BackTracking(g, 0, nColors)) {
                        end2 = System.nanoTime();
                        System.out.println("Elapsed Time in nano seconds: "+ (end2-start2));
                        g.printSolution();
                }
                else System.out.println("Not possible to colour the map");
                System.out.println();

                for(int i = 0; i < g.nodes.size(); ++i) g.nodes.get(i).color = -1;

                System.out.println("Solution with BackTracking_ForwardChecking algorithm");
                long start3 = System.nanoTime();
                long end3 = System.nanoTime();
                if (a.BackTracking_ForwardChecking(g, 0, nColors, -1)) {
                        end3 = System.nanoTime();
                        System.out.println("Elapsed Time in nano seconds: "+ (end3-start3));
                        g.printSolution();
                }
                else System.out.println("Not possible to colour the map");
                System.out.println();

                ArrayList<Integer> possibleColors = new ArrayList<>();
                for(int i = 0; i < nColors; ++i) possibleColors.add(i);
                for(int i = 0; i < g.nodes.size(); ++i) {
                        g.nodes.get(i).color = -1;
                        g.nodes.get(i).possibleColors = possibleColors;
                }

                System.out.println("Solution with BackTracking_AC3 algorithm");
                long start4 = System.nanoTime();
                long end4 = System.nanoTime();
                if (a.BackTracking_AC3(g,0)) {
                        end4 = System.nanoTime();
                        System.out.println("Elapsed Time in nano seconds: "+ (end4-start4));
                                         g.printSolution();
                }
                else System.out.println("Not possible to colour the map");
                System.out.println();

                out.print(n + " ");
                out2.print((end1-start1)+ " ");
                out3.print((end2-start2)+ " ");
                out4.print((end3-start3)+ " ");
                out5.print((end4-start4)+ " ");

                out.close();
                out2.close();
                out3.close();
                out4.close();
                out5.close();
        }
}
