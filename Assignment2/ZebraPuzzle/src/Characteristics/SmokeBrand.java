package Characteristics;

import java.util.ArrayList;

public class SmokeBrand {
    ArrayList<Integer> house;

    public SmokeBrand() {
        house = new ArrayList<>();
        for (int i = 0; i < 5; ++i) house.add(i);
    }

    public ArrayList<Integer> getHouse() {
        return house;
    }

    public void setHouse(ArrayList<Integer> house) {
        this.house = house;
    }
}
