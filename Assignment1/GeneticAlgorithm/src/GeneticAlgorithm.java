import util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class GeneticAlgorithm {

    private final int populationSize;
    private final double crossoverRate;
    private final double mutationRate;
    private final int selectionMode;
    private final int maxNumberGenerations;


    public GeneticAlgorithm(int populationSize, double crossoverRate, double mutationRate, int selectionMode, int maxNumberGenerations) {
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.selectionMode = selectionMode;
        this.maxNumberGenerations = maxNumberGenerations;
    }

    public Population initPopulation(ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> map, Pair<Integer, Integer> dimensionsPCB) {

        Population population = new Population();

        int i = 0;
        while (population.getIndividuals().size() < populationSize && i < populationSize * 2) {
            ArrayList<Individual.Gene> chrom = generateRandomSolution(map, dimensionsPCB);
            if (chrom != null) {
                Individual.Chromosome chromosome = new Individual.Chromosome(chrom);
                Individual individual = new Individual(chromosome);
                int fitnessValue = calcFitness(individual, dimensionsPCB);
                individual.setFitnessValue(fitnessValue);
                population.addIndividual(individual);
            }
            ++i;
        }
        return population;
    }

    public ArrayList<Individual.Gene> generateRandomSolution(ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> map, Pair<Integer, Integer> dimensionsPCB) {

        ArrayList<Individual.Gene> chromosome = new ArrayList<>();

        for (int i = 0; i < map.size(); ++i) {
            int y0 = map.get(i).getFirst().getFirst();
            int x0 = map.get(i).getFirst().getSecond();
            int y1 = map.get(i).getSecond().getFirst();
            int x1 = map.get(i).getSecond().getSecond();
            ArrayList<Direction> pathDirections = null;
            int iterations = 0;

            while (pathDirections == null && iterations < 10) {
                boolean[][] pcb = new boolean[dimensionsPCB.getFirst()][dimensionsPCB.getSecond()];

                for (int j = 0; j < map.size(); ++j) {
                    int l0 = map.get(j).getFirst().getFirst();
                    int m0 = map.get(j).getFirst().getSecond();
                    int l1 = map.get(j).getSecond().getFirst();
                    int m1 = map.get(j).getSecond().getSecond();
                    pcb[l0][m0] = TRUE;
                    pcb[l1][m1] = TRUE;
                }

                pcb[y1][x1] = FALSE;

                pathDirections = GenerateRandomPath(y0, x0, y1, x1, dimensionsPCB, pcb);
                iterations++;
            }
            if (iterations == 10) {
                chromosome = null;
                break;
            }
            // The following path is saved in segments and the length of them
            ArrayList<Pair<Integer, Integer>> path;
            if (pathDirections != null) path = calcPath(pathDirections);
            else path = null;
            Pair<Integer, Integer> origin = new Pair<>(y0, x0);
            Pair<Integer, Integer> destination = new Pair<>(y1, x1);
            Individual.Gene gene = new Individual.Gene(origin, destination, path);
            chromosome.add(gene);
        }

        if (chromosome != null) {
            for (int i = 0; i < chromosome.size(); i++)
                if (chromosome.get(i).path == null) {
                    chromosome = null;
                    break;
                }
        }

        return chromosome;
    }

    /*
    The following function generates a random path between two points but it follows a certain tendency to arrive in the destination.
    I use a random integer between 2-10 which marks the iteration to follow a certain tendency.
    */
    public ArrayList<Direction> GenerateRandomPath(int starty, int startx, int endy, int endx, Pair<Integer, Integer> dimensionsPCB, boolean[][] pcb)
    {
        ArrayList<Direction> newpath = new ArrayList<>();

        int actualx = startx; int actualy = starty;
        Direction newd = null;

        int iteration = 0;
        int iterationToChange = getRandomNumberUsingNextInt(2, 10);

        int[] allowed;

        while (!(actualx == endx && actualy == endy) && iteration < dimensionsPCB.getFirst()*dimensionsPCB.getSecond()) {

            if (iteration % iterationToChange != 0) {
                allowed = new int[]{1, 2, 3, 4};
                allowed = newDirectionAllowed(dimensionsPCB, pcb, actualy, actualx, allowed);

                if (allowed.length > 0) newd = GetNewDirection(allowed);
                else break;
            }

            else {
                if (actualx < endx) {
                    if (actualy < endy) {
                        allowed = new int[]{3, 4};
                        allowed = newDirectionAllowed(dimensionsPCB, pcb, actualy, actualx, allowed);
                        if (allowed.length > 0) newd = GetNewDirection(allowed);
                    } else if (actualy > endy) {
                        allowed = new int[]{1, 3};
                        allowed = newDirectionAllowed(dimensionsPCB, pcb, actualy, actualx, allowed);
                        if (allowed.length > 0) newd = GetNewDirection(allowed);
                    } else {
                        allowed = new int[]{3};
                        allowed = newDirectionAllowed(dimensionsPCB, pcb, actualy, actualx, allowed);
                        if (allowed.length > 0) newd = GetNewDirection(allowed);
                    }
                } else if (actualx > endx) {
                    if (actualy < endy) {
                        allowed = new int[]{2, 4};
                        allowed = newDirectionAllowed(dimensionsPCB, pcb, actualy, actualx, allowed);
                        if (allowed.length > 0) newd = GetNewDirection(allowed);
                    } else if (actualy > endy) {
                        allowed = new int[]{1, 2};
                        allowed = newDirectionAllowed(dimensionsPCB, pcb, actualy, actualx, allowed);
                        if (allowed.length > 0) newd = GetNewDirection(allowed);
                    } else {
                        allowed = new int[]{2};
                        allowed = newDirectionAllowed(dimensionsPCB, pcb, actualy, actualx, allowed);
                        if (allowed.length > 0) newd = GetNewDirection(allowed);
                    }
                } else {
                    if (actualy < endy) {
                        allowed = new int[]{4};
                        allowed = newDirectionAllowed(dimensionsPCB, pcb, actualy, actualx, allowed);
                        if (allowed.length > 0) newd = GetNewDirection(allowed);
                    } else {
                        allowed = new int[]{1};
                        allowed = newDirectionAllowed(dimensionsPCB, pcb, actualy, actualx, allowed);
                        if (allowed.length > 0) newd = GetNewDirection(allowed);
                    }
                }
            }

            if (newd != null && allowed.length != 0) {
                newpath.add(newd);
                switch (newd) {
                    case TOP -> actualy--;
                    case LEFT -> actualx--;
                    case RIGHT -> actualx++;
                    case DOWN -> actualy++;
                }
                pcb[actualy][actualx] = TRUE;
            }

            ++iteration;
        }

        if (actualx == endx && actualy == endy) return newpath;
        else return null;
    }

    public static int[] addElement(int[] a, int e) {
        a  = Arrays.copyOf(a, a.length + 1);
        a[a.length - 1] = e;
        return a;
    }

    public int[] newDirectionAllowed(Pair<Integer, Integer> dimensionsPCB, boolean[][] pcb, int actualy, int actualx, int[] allowed) {
        int[] realAllowed = {};
        for (int j : allowed) {
            if (j == 1) {
                if (actualy - 1 >= 0 && pcb[actualy - 1][actualx] == FALSE) realAllowed = addElement(realAllowed, 1);
            } else if (j == 2) {
                if (actualx - 1 >= 0 && pcb[actualy][actualx - 1] == FALSE) realAllowed = addElement(realAllowed, 2);
            } else if (j == 3) {
                if (actualx + 1 < dimensionsPCB.getSecond() && pcb[actualy][actualx + 1] == FALSE)
                    realAllowed = addElement(realAllowed, 3);
            } else {
                if (actualy + 1 < dimensionsPCB.getFirst() && pcb[actualy + 1][actualx] == FALSE)
                    realAllowed = addElement(realAllowed, 4);
            }
        }

        return realAllowed;
    }

    public ArrayList<Pair<Integer, Integer>> calcPath(ArrayList<Direction> pathDirections) {
        ArrayList<Pair<Integer, Integer>> path = new ArrayList<>();

        if (pathDirections.size() > 1) {
            Direction last;
            Direction actual;

            int length = 1;

            for (int i = 0; i < pathDirections.size()-1; ++i) {
                last = pathDirections.get(i);
                actual = pathDirections.get(i+1);
                if (last == Direction.TOP) {
                    if (actual == Direction.TOP) length++;
                    else {
                        Pair<Integer, Integer> pair = new Pair<>(1, length);
                        path.add(pair);
                        length = 1;
                    }
                }
                else if (last == Direction.LEFT) {
                    if (actual == Direction.LEFT) length++;
                    else {
                        Pair<Integer, Integer> pair = new Pair<>(2, length);
                        path.add(pair);
                        length = 1;
                    }
                }
                else if (last == Direction.RIGHT) {
                    if (actual == Direction.RIGHT) length++;
                    else {
                        Pair<Integer, Integer> pair = new Pair<>(3, length);
                        path.add(pair);
                        length = 1;
                    }
                }
                else {
                    if (actual == Direction.DOWN) length++;
                    else {
                        Pair<Integer, Integer> pair = new Pair<>(4, length);
                        path.add(pair);
                        length = 1;
                    }
                }
                if (i == pathDirections.size()-2) {
                    if (actual == Direction.TOP) {
                        Pair<Integer, Integer> pair = new Pair<>(1, length);
                        path.add(pair);
                    }
                    else if (actual == Direction.LEFT) {
                        Pair<Integer, Integer> pair = new Pair<>(2, length);
                        path.add(pair);
                    }
                    else if (actual == Direction.RIGHT) {
                        Pair<Integer, Integer> pair = new Pair<>(3, length);
                        path.add(pair);
                    }
                    else {
                        Pair<Integer, Integer> pair = new Pair<>(4, length);
                        path.add(pair);
                    }
                }
            }

            return path;
        }
        else if (pathDirections.size() == 1) {
            if (pathDirections.get(0) == Direction.TOP) {
                Pair<Integer, Integer> pair = new Pair<>(1, 1);
                path.add(pair);
            }
            else if (pathDirections.get(0) == Direction.LEFT) {
                Pair<Integer, Integer> pair = new Pair<>(2, 1);
                path.add(pair);
            }
            else if (pathDirections.get(0) == Direction.RIGHT) {
                Pair<Integer, Integer> pair = new Pair<>(3, 1);
                path.add(pair);
            }
            else {
                Pair<Integer, Integer> pair = new Pair<>(4, 1);
                path.add(pair);
            }

            return path;
        }

        return null;
    }

    enum Direction {
        TOP,
        LEFT,
        RIGHT,
        DOWN
    }

    /*
    Directions:
    1 = Top
    2 = Left
    3 = Right
    4 = Down
    */
    public Direction GetNewDirection(int[] allowed)
    {
        Direction[] vals = Direction.values();
        int t = 0;
        if (allowed.length >= 1) {
            Random random = new Random();
            t = random.nextInt(allowed.length);
        }
        return vals[allowed[t]-1];
    }

    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public int calcFitness(Individual individual, Pair<Integer, Integer> dimensionsPCB) {
        int numberOfIntersections = 0;
        int totalPathLength = 0;
        int totalSegmentCount = 0;
        boolean[][] pcb = new boolean[dimensionsPCB.getFirst()][dimensionsPCB.getSecond()];
        int actualx, actualy;

        // Loop over individual's genes
        for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
            Individual.Gene gene = individual.getGene(geneIndex);
            actualy = gene.origin.getFirst();
            actualx = gene.origin.getSecond();

            pcb[gene.origin.getFirst()][gene.origin.getSecond()] = TRUE;
            pcb[gene.destination.getFirst()][gene.destination.getSecond()] = TRUE;

            totalSegmentCount += gene.path.size();

            for (int i = 0; i < gene.path.size(); ++i) {
                totalPathLength += gene.path.get(i).getSecond();

                if (gene.path.get(i).getFirst() == 1) {
                    for (int j = 0; j < gene.path.get(i).getSecond(); ++j) {
                        if (actualy != 0) {
                            if (pcb[actualy - 1][actualx] == TRUE) numberOfIntersections++;
                            else pcb[actualy - 1][actualx] = TRUE;
                            actualy = actualy - 1;
                        }
                    }
                }
                else if (gene.path.get(i).getFirst() == 2) {
                    for (int j = 0; j < gene.path.get(i).getSecond(); ++j) {
                        if (actualx != 0) {
                            if (pcb[actualy][actualx - 1] == TRUE) numberOfIntersections++;
                            else pcb[actualy][actualx - 1] = TRUE;
                            actualx = actualx - 1;
                        }
                    }
                }
                else if (gene.path.get(i).getFirst() == 3) {
                    for (int j = 0; j < gene.path.get(i).getSecond(); ++j) {
                        if (actualx != dimensionsPCB.getSecond()-1) {
                            if (pcb[actualy][actualx + 1] == TRUE) numberOfIntersections++;
                            else pcb[actualy][actualx + 1] = TRUE;
                            actualx = actualx + 1;
                        }
                    }
                }
                else {
                    for (int j = 0; j < gene.path.get(i).getSecond(); ++j) {
                        if (actualy != dimensionsPCB.getFirst()-1) {
                            if (pcb[actualy + 1][actualx] == TRUE) numberOfIntersections++;
                            else pcb[actualy + 1][actualx] = TRUE;
                            actualy = actualy + 1;
                        }
                    }
                }
            }
        }

        // Calculate fitness
        return 10 * numberOfIntersections + 3 * totalPathLength + 3 * totalSegmentCount;
    }


    public void evalPopulation(Population population, Pair<Integer, Integer> dimensionsPCB) {
        int populationFitness = 0;

        // Loop over population evaluating individuals and suming population
        // fitness
        for (Individual individual : population.getIndividuals()) {
            int indFitness = calcFitness(individual, dimensionsPCB);
            individual.setFitnessValue(indFitness);
            populationFitness += indFitness;
        }

        population.setPopulationFitness(populationFitness);
    }

    public int getMaxNumberGenerations() {
        return maxNumberGenerations;
    }

    public Population selectionPopulation(Population population) {

        population.shuffle();
        Population populationSelected = new Population();

        // Roulette selection
        if (selectionMode == 1) {
            int populationFitness = population.getPopulationFitness();
            ArrayList<Pair<Double, Double>> intervalsIndividual = new ArrayList<>();
            double totalProbability = 0;

            double probability = 0;
            for (Individual individual : population.getIndividuals()) {
                probability += ((double)populationFitness/(double)individual.getFitnessValue());
            }

            for (Individual individual : population.getIndividuals()) {
                Pair<Double, Double> interval = new Pair<>(totalProbability, totalProbability + ((double)populationFitness/(double)individual.getFitnessValue())/probability);
                intervalsIndividual.add(interval);
                totalProbability += ((double)populationFitness/(double)individual.getFitnessValue())/probability;
            }

            int numberPopulation = (int) (population.getIndividuals().size()/1.8);
            if (numberPopulation == 0) numberPopulation = 1;

            for (int i = 0; i < numberPopulation; ++i) {
                Random random = new Random();
                double r = random.nextDouble();

                int j = 0;
                while (j < intervalsIndividual.size()) {
                    if (r >= intervalsIndividual.get(j).getFirst() && r <= intervalsIndividual.get(j).getSecond()) {
                        populationSelected.addIndividual(population.getIndividual(j));
                    }
                    ++j;
                }
            }

        }

        return populationSelected;
    }

    public Population crossoverPopulation(Population population) {

        population.shuffle();

        Population populationCrossover = new Population();

        int notCrossoverNumber = ((Double)(population.size()*crossoverRate)).intValue();

        if (notCrossoverNumber == 0) notCrossoverNumber = 1;
        for (int i = 0; i < notCrossoverNumber; ++i) {
            Random random = new Random();
            int r = random.nextInt(notCrossoverNumber);
            populationCrossover.addIndividual(population.getIndividual(r));
        }

        while (populationCrossover.size() < populationSize) {
            Random random = new Random();
            int r1 = random.nextInt(population.size());
            int r2 = random.nextInt(population.size());
            Individual.Chromosome chromosome1 = population.getIndividual(r1).getChromosome();
            Individual.Chromosome chromosome2 = population.getIndividual(r2).getChromosome();

            int chromosomeLenght = population.getIndividual(r1).getChromosomeLength();
            int rand = random.nextInt(chromosomeLenght);

            ArrayList<Individual.Gene> newGenes = new ArrayList<>();
            for (int j = 0; j < rand; j++) newGenes.add(chromosome1.getGene(j));
            for (int j = rand; j < chromosomeLenght; j++) newGenes.add(chromosome2.getGene(j));

            Individual.Chromosome newChromosome = new Individual.Chromosome(newGenes);

            Individual newIndividual = new Individual(newChromosome);

            populationCrossover.addIndividual(newIndividual);
        }

        return populationCrossover;
    }

    public Population mutatePopulation(Population population, ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> map, Pair<Integer, Integer> dimensionsPCB) {

        int totalLength = 0;
        for (int i = 0; i < population.getIndividuals().size(); ++i) {
            Individual.Chromosome chromosome = population.getIndividual(i).getChromosome();
            for (int j = 0; j < chromosome.getChromosomeLength(); ++j) {
                Individual.Gene gene = chromosome.getGene(j);
                for (int k = 0; k < gene.path.size(); ++k) {
                    totalLength += gene.path.get(k).getSecond();
                }
            }
            int segmentsToModificate = ((Double)(totalLength*mutationRate)).intValue();

            boolean[][] pcb = new boolean[dimensionsPCB.getFirst()][dimensionsPCB.getSecond()];
            for (int j = 0; j < map.size(); ++j) {
                int y0 = map.get(j).getFirst().getFirst();
                int x0 = map.get(j).getFirst().getSecond();
                int y1 = map.get(j).getSecond().getFirst();
                int x1 = map.get(j).getSecond().getSecond();
                pcb[y0][x0] = TRUE;
                pcb[y1][x1] = TRUE;
            }

            ArrayList<Pair<Integer, Integer>> newPath = new ArrayList<>();
            for (int k = 0; k < segmentsToModificate; ++k) {
                Random random = new Random();
                int r1 = random.nextInt(chromosome.getChromosomeLength());
                Individual.Gene gene = chromosome.getGene(r1);
                int r2 = random.nextInt(chromosome.getGene(r1).path.size());

                int actualy = gene.origin.getFirst();
                int actualx = gene.origin.getSecond();

                ArrayList<Pair<Integer, Integer>> path1 = new ArrayList<>();
                for (int l = 0; l < r2; ++l) {
                    path1.add(gene.path.get(l));
                    if (gene.path.get(l).getFirst() == 1) {
                        for (int j = 0; j < gene.path.get(l).getSecond(); ++j) {
                            if (actualy != 0) {
                                pcb[actualy - 1][actualx] = TRUE;
                                actualy = actualy - 1;
                            }
                        }
                    }
                    else if (gene.path.get(l).getFirst() == 2) {
                        for (int j = 0; j < gene.path.get(l).getSecond(); ++j) {
                            if (actualx != 0) {
                                pcb[actualy][actualx - 1] = TRUE;
                                actualx = actualx - 1;
                            }
                        }
                    }
                    else if (gene.path.get(l).getFirst() == 3) {
                        for (int j = 0; j < gene.path.get(l).getSecond(); ++j) {
                            if (actualx != dimensionsPCB.getSecond()-1) {
                                pcb[actualy][actualx + 1] = TRUE;
                                actualx = actualx + 1;
                            }
                        }
                    }
                    else {
                        for (int j = 0; j < gene.path.get(l).getSecond(); ++j) {
                            if (actualy != dimensionsPCB.getFirst()-1) {
                                pcb[actualy + 1][actualx] = TRUE;
                                actualy = actualy + 1;
                            }
                        }
                    }
                }

                ArrayList<Direction> pathDirections = null;
                if (gene.path.get(r2).getFirst() == 1) {
                    if (actualy != 0) {
                        pcb[actualy - 1][actualx] = TRUE;
                        pathDirections = GenerateRandomPath(actualy, actualx, actualy - 1, actualx, dimensionsPCB, pcb);

                        actualy--;
                    }
                }
                else if (gene.path.get(r2).getFirst() == 2) {
                    if (actualx != 0) {
                        pcb[actualy][actualx - 1] = TRUE;
                        pathDirections = GenerateRandomPath(actualy, actualx, actualy, actualx - 1, dimensionsPCB, pcb);
                        actualx--;
                    }
                }
                else if (gene.path.get(r2).getFirst() == 3) {
                    if (actualx != dimensionsPCB.getSecond()-1) {
                        pcb[actualy][actualx + 1] = TRUE;
                        pathDirections = GenerateRandomPath(actualy, actualx, actualy, actualx + 1, dimensionsPCB, pcb);
                        actualx++;
                    }
                }
                else {
                    if (actualy != dimensionsPCB.getFirst()-1) {
                        pcb[actualy + 1][actualx] = TRUE;
                        pathDirections = GenerateRandomPath(actualy, actualx, actualy + 1, actualx, dimensionsPCB, pcb);
                        actualy++;
                    }
                }

                ArrayList<Pair<Integer, Integer>> path2;
                if (pathDirections != null) {
                    path2 = calcPath(pathDirections);

                    ArrayList<Direction> directions = new ArrayList<>();
                    for (int l = r2; l < gene.path.size(); ++l) {
                        if (gene.path.get(l).getFirst() == 1) {
                            for (int j = 0; j < gene.path.get(l).getSecond(); ++j) {
                                directions.add(Direction.TOP);
                                actualy = actualy-1;
                            }
                        }
                        else if (gene.path.get(l).getFirst() == 2) {
                            for (int j = 0; j < gene.path.get(l).getSecond(); ++j) {
                                directions.add(Direction.LEFT);
                                actualx = actualx-1;
                            }
                        }
                        else if (gene.path.get(l).getFirst() == 3) {
                            for (int j = 0; j < gene.path.get(l).getSecond(); ++j) {
                                directions.add(Direction.RIGHT);
                                actualx = actualx+1;
                            }
                        }
                        else {
                            for (int j = 0; j < gene.path.get(l).getSecond(); ++j) {
                                directions.add(Direction.DOWN);
                                actualy = actualy+1;
                            }
                        }
                    }

                    ArrayList<Pair<Integer, Integer>> path3 = calcPath(directions);
                    if (path3 != null) {
                        newPath.addAll(path1);
                        newPath.addAll(path2);
                        newPath.addAll(path3);
                    }
                    else newPath = null;

                }
                else newPath = null;

                if (newPath != null) {
                    gene.path = newPath;
                }
            }
        }

        return population;
    }

}
