import java.util.ArrayList;
import java.util.EnumSet;

public class Main {

    /*
    1. There are five houses.
    2. The Englishman lives in the red house.
    3. The Spaniard owns the dog.
    4. Coffee is drunk in the green house.
    5. The Ukrainian drinks tea.
    6. The green house is immediately to the right of the ivory house.
    7. The Old Gold smoker owns snails.
    8. Kools are smoked in the yellow house.
    9. Milk is drunk in the middle house.
    10. The Norwegian lives in the first house.
    11. The man who smokes Chesterfields lives in the house next to the man with the fox.
    12. Kools are smoked in the house next to the house where the horse is kept.
    13. The Lucky Strike smoker drinks orange juice.
    14. The Japanese smokes Parliaments.
    15. The Norwegian lives next to the blue house.
     */
    public static void main(String[] args) {
        /*ArrayList<House> houses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            House h = new House();
            houses.add(h);
        }

        // Unary constraints
        // Rules 9 and 10
        EnumSet<Drink> drink = EnumSet.of(Drink.MILK);
        houses.get(3).setDrinks(drink);
        EnumSet<Nationality> nationality = EnumSet.of(Nationality.NORWEGIAN);
        houses.get(1).setNationalities(nationality);

        // Resulting CSP is a Node-Consistent problem

        // Remove Values that don't have support
        EnumSet<Drink> drinks = EnumSet.of(Drink.BEER, Drink.TEA, Drink.COFFEE, Drink.WATER);
        for (House h : houses) h.setDrinks(drinks);
        */

        Model m = new Model();
        m.addConstraints();

        Algorithm a = new Algorithm();
        System.out.println(a.AC3(m));

         System.out.println(a.AC3(m));
        System.out.println();
        /*System.out.println("Water is drunk by the " + nationalityNames[h2o.getValue()]);
        System.out.println("The zebra is owned by the " + nationalityNames[zebra.getValue()]);
        System.out.println();*/
    }

}
