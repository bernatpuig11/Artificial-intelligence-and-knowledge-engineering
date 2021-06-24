import util.Pair;

import java.util.ArrayList;


public class Individual {
    private final Chromosome chromosome;
    private int fitnessValue = -1;

    public Individual(Chromosome chromosome) {
        this.chromosome = chromosome;
    }

    public int getChromosomeLength() {
        return this.chromosome.getChromosomeLength();
    }

    public Chromosome getChromosome() {
        return chromosome;
    }

    public Gene getGene(int offset) {
        return this.chromosome.getGene(offset);
    }

    public int getFitnessValue() {
        return fitnessValue;
    }

    public void setFitnessValue(int fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    public static class Chromosome {
        ArrayList<Gene> chromosome = new ArrayList<>();

        public Chromosome(ArrayList<Gene> chromosome) {
            this.chromosome = chromosome;
        }

        public int getChromosomeLength() {
            return this.chromosome.size();
        }

        public Gene getGene(int offset) {
            return this.chromosome.get(offset);
        }
    }

    public static class Gene {
        Pair<Integer, Integer> origin;
        Pair<Integer, Integer> destination;
        ArrayList<Pair<Integer, Integer>> path;

        public Gene(Pair<Integer, Integer> origin, Pair<Integer, Integer> destination, ArrayList<Pair<Integer, Integer>> path) {
            this.origin = origin;
            this.destination = destination;
            this.path = path;
        }
    }
}
