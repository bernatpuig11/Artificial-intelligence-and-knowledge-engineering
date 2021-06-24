import java.util.*;

public class Population {

    private final ArrayList<Individual> population;
    private int populationFitness;

    public Population() {
        population = new ArrayList<>();
    }

    public ArrayList<Individual> getIndividuals() {
        return population;
    }

    /**
     * Find an individual in the population by its fitness
     *
     * This method lets you select an individual in order of its fitness. This
     * can be used to find the single strongest individual (eg, if you're
     * testing for a solution), but it can also be used to find weak individuals
     * (if you're looking to cull the population) or some of the strongest
     * individuals (if you're using "elitism").
     *
     * @param offset
     *            The offset of the individual you want, sorted by fitness. 0 is
     *            the strongest, population.length - 1 is the weakest.
     * @return individual Individual at offset
     */
    public Individual getFittest(int offset) {

        if (this.population.isEmpty()) return null;
        // Order population by fitness
        this.population.sort((o1, o2) -> {
            if (o1.getFitnessValue() < o2.getFitnessValue()) {
                return -1;
            } else if (o1.getFitnessValue() > o2.getFitnessValue()) {
                return 1;
            }
            return 0;
        });

        // Return the fittest individual
        return this.population.get(offset);

    }

    //The population's total fitness
    public void setPopulationFitness(int fitness) {
        this.populationFitness = fitness;
    }

    //Get population's group fitness
    public int getPopulationFitness() {
        return this.populationFitness;
    }

    /**
     * Get population's size
     *
     * @return size The population's size
     */
    public int size() {
        return this.population.size();
    }

    public void addIndividual(Individual individual) {
        this.population.add(individual);
    }

    public void removeNullIndividuals() {
        for (Individual i : getIndividuals()) {
            if (i.getFitnessValue() == -1) this.population.remove(i);
        }
    }

    /**
     * Get individual at offset
     */
    public Individual getIndividual(int offset) {
        return population.get(offset);
    }

    /**
     * Shuffles the population in-place
     */
    public void shuffle() {
        Random rnd = new Random();
        for (int i = population.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Individual a = population.get(index);
            population.set(index, population.get(i));
            population.set(i, a);
        }
    }
}
