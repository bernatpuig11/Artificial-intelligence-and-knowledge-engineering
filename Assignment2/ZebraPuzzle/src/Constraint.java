public class Constraint {
    /* Type Constraint
    1 = Related
    2 = Next to
    3 = Immediataly right
    4 = Immediataly left
    5 = Exact value */

    int typeConstraint, x1, type1, x2, type2;

    public Constraint(int typeConstraint, int x1, int type1, int x2, int type2) {
        this.typeConstraint = typeConstraint;
        this.x1 = x1;
        this.type1 = type1;
        this.x2 = x2;
        this.type2 = type2;
    }
}
