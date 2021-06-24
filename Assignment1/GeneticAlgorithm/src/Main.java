import util.Pair;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class Main {

    private static PCB pcb;


   public static void main(String[] args) throws FileNotFoundException {
        pcb = new PCB();
        readFile(args);

        PrintWriter out = new PrintWriter("OutputFitnessValue.txt");
        PrintWriter out2 = new PrintWriter("OutputGenerations.txt");
        // Create GA object
        /* Parameters:
            - Population size
            - Mutation rate (0-1)
            - Crossover rate (0-1)
            - Selection mode (1 or 2)
            - Max generations
        */
       GeneticAlgorithm ga = new GeneticAlgorithm(300, 0.95, 0.05, 1, 100);

        // Initialize population
        Population population = ga.initPopulation(pcb.getMapPCB(), pcb.getDimensionsPCB());
        population.removeNullIndividuals();

        if (population.getIndividuals().size() == 0) {
            System.out.println("It could not be created any individual");
        }

        else {
            // Evaluate population
            ga.evalPopulation(population, pcb.getDimensionsPCB());

            // Keep track of current generation
            int generation = 1;

            int lastpopulationFitness;
            int actualpopulationFitness = population.getPopulationFitness();
            int repetitions = 0;

            if (population.getFittest(0) == null) System.out.println("Best solution: null");
            else {
                System.out.println("Best solution id: " + population.getFittest(0).toString());
                System.out.println("Best solution: " + population.getFittest(0).getFitnessValue());
            }

            /**
             * Start the evolution loop
             */
            while (repetitions < 20 && generation < ga.getMaxNumberGenerations()) {
                // Print fittest individual from population
                System.out.println("Best solution: " + population.getFittest(0).getFitnessValue());
                out.print(population.getFittest(0).getFitnessValue() + " ");
                out2.print("Generation" + generation + " ");

                // Apply selection
                population = ga.selectionPopulation(population);

                // Apply crossover
                population = ga.crossoverPopulation(population);

                // Apply mutation
                population = ga.mutatePopulation(population, pcb.getMapPCB(), pcb.getDimensionsPCB());

                // Evaluate population
                ga.evalPopulation(population, pcb.getDimensionsPCB());

                // Increment the current generation
                generation++;

                // Calculate the number of repetitions of the best solution
                lastpopulationFitness = actualpopulationFitness;
                actualpopulationFitness = population.getPopulationFitness();
                if (lastpopulationFitness == actualpopulationFitness) repetitions++;
                else repetitions = 0;

            }

            if (population.getFittest(0) == null) System.out.println("Best solution: null");
            else System.out.println("Best solution: " + population.getFittest(0).toString());

            /**
             * We're out of the loop now, which means we have a perfect solution on
             * our hands. Let's print it out to confirm that it is actually all
             * ones, as promised.
             */

            System.out.println("Found solution in " + generation + " generations");
            if (population.getFittest(0) == null) System.out.println("Best solution: null");
            else {
                System.out.println("Best solution id: " + population.getFittest(0).toString());
                System.out.println("Best solution: " + population.getFittest(0).getFitnessValue());
            }
            out.close();
            out2.close();
        }

    }


    private static void readFile(String[] args) {
        try {
            File myObj = new File(args[0]);
            Scanner myReader = new Scanner(myObj);
            if (myReader.hasNextLine()) {
                String dimensions = myReader.nextLine();
                System.out.println(dimensions);
                String[] parts = dimensions.split(";");
                String widthS = parts[0];
                String heightS = parts[1];
                Pair<Integer, Integer> dimension = new Pair<>(Integer.parseInt(heightS), Integer.parseInt(widthS));
                pcb.setDimensionsPCB(dimension);
            }
            ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> map = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
                String[] parts = data.split(";");
                String x0 = parts[0];
                String y0 = parts[1];
                String x1 = parts[2];
                String y1 = parts[3];
                Pair<Integer, Integer> origin = new Pair<>(Integer.parseInt(y0), Integer.parseInt(x0));
                Pair<Integer, Integer> destination = new Pair<>(Integer.parseInt(y1), Integer.parseInt(x1));
                Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> pair = new Pair<>(origin, destination);
                map.add(pair);
            }
            pcb.setMapPCB(map);

            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
