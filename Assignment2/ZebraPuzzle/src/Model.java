import Characteristics.*;

import java.util.ArrayList;

public class Model {
    ArrayList<Color> Colors;
    /*
    C0 = Red
    C1 = Green
    C2 = Ivory
    C3 = Yellow
    C4 = Blue
    */
    ArrayList<Nationality> Nationalities;
    /*
    N0 = Englishman
    N1 = Spaniard
    N2 = Ukranian
    N3 = Norwegian
    N4 = Japanese
    */
    ArrayList<Animal> Animals;
    /*
    A0 = Dog
    A1 = Snails
    A2 = Fox
    A3 = Horse
    A4 = Zebra
    */
    ArrayList<Drink> Drinks;
    /*
    D0 = Coffe
    D1 = Tea
    D2 = Milk
    D3 = Orange juice
    D4 = Guiness
    */
    ArrayList<SmokeBrand> SmokeBrands;
    /*
    S0 = Old Gold
    S1 = Parliament
    S2 = Kools
    S3 = Lucky
    S4 = Chesterfield
    */
    ArrayList<Constraint> constraints;

    public Model() {
        Colors = new ArrayList<>();
        Nationalities = new ArrayList<>();
        Animals = new ArrayList<>();
        Drinks = new ArrayList<>();
        SmokeBrands = new ArrayList<>();
        constraints = new ArrayList<>();

        for (int i = 0; i < 5; ++i) {
            Color color = new Color();
            Colors.add(color);

            Nationality nationality = new Nationality();
            Nationalities.add(nationality);

            Animal animal = new Animal();
            Animals.add(animal);

            Drink drink = new Drink();
            Drinks.add(drink);

            SmokeBrand smokeBrand = new SmokeBrand();
            SmokeBrands.add(smokeBrand);
        }
    }

    /*
    Type Constraint
    1 = Related
    2 = Next to
    3 = Immediataly right
    4 = Immediataly left
    5 = Exact value

    Type 0 = Color
    Type 1 = Nationality
    Type 2 = Animal
    Type 3 = Drink
    Type 4 = SmokeBrand
    */
    public void addConstraints() {
        // Constraint 1: N0 = C0
        Constraint c = new Constraint(1,0, 1, 0, 0);
        constraints.add(c);

        // Constraint 2: N1 = A0
        c = new Constraint(1, 1, 1, 0, 2);
        constraints.add(c);

        // Constraint 3: D0 = C1
        c = new Constraint(1, 0, 3, 1, 0);
        constraints.add(c);

        // Constraint 4: N2 = D1
        c = new Constraint(1, 2, 1, 1, 3);
        constraints.add(c);

        // Constraint 5: C1 - C2 = 1
        c = new Constraint(3, 1, 0, 2, 0);
        constraints.add(c);

        // Constraint 6: S0 = A1
        c = new Constraint(1, 0, 4, 1, 2);
        constraints.add(c);

        // Constraint 7: S2 = C3
        c = new Constraint(1, 2, 4, 3, 0);
        constraints.add(c);

        // Constraint 8: D2 = 2
        c = new Constraint(5, 2, 3, 2, -1);
        constraints.add(c);

        // Constraint 9: N3 = 0
        c = new Constraint(5, 3, 1, 0, -1);
        constraints.add(c);

        // Constraint 10: |S4 - A2| = 1
        c = new Constraint(2, 4, 4, 2, 2);
        constraints.add(c);

        // Constraint 11: |S2 - A3| = 1
        c = new Constraint(2, 2, 4, 3, 2);
        constraints.add(c);

        // Constraint 12: S3 = D3
        c = new Constraint(1, 3, 4, 3, 3);
        constraints.add(c);

        // Constraint 13: N4 = S1
        c = new Constraint(1, 4, 1, 1, 4);
        constraints.add(c);

        // Constraint 14: |N3 - C4| = 1
        c = new Constraint(2, 3, 1, 4, 0);
        constraints.add(c);
    }


}
