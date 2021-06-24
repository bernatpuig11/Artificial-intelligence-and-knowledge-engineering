import util.Pair;

import java.util.ArrayList;

public class PCB {
    private Pair<Integer, Integer> dimensionsPCB;

    ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> mapPCB;

    public PCB() {
        dimensionsPCB = null;
        mapPCB = null;
    }

    public Pair<Integer, Integer> getDimensionsPCB() {
        return dimensionsPCB;
    }

    public void setDimensionsPCB(Pair<Integer, Integer> dimensionsPCB) {
        this.dimensionsPCB = dimensionsPCB;
    }

    public ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> getMapPCB() {
        return mapPCB;
    }

    public void setMapPCB(ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> mapPCB) {
        this.mapPCB = mapPCB;
    }
}
