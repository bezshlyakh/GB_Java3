import Fruits.Fruit;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    ArrayList<T> box;
    private float weight;
    private final int CAPACITY = 10;

    public int getCAPACITY() {
        return CAPACITY;
    }

    public Box() {
        this.box = new ArrayList<>(CAPACITY);
    }

    public ArrayList<T> getBox() {
        return box;
    }

    public void fillBox(T fruit){
        this.box.add(fruit);
    }

    public float calculateWeight(){
        weight = 0.0f;
        for(int i = 0; i < box.size(); i++){
            weight += box.get(i).getWeight();
        }
        return weight;
    }

    public int calculateAmount(){
        int amount = 0;
        for(int i = 0; i < box.size(); i++){
            amount ++;
        }
        return amount;
    }

    public boolean compareBoxes(Box<? extends Fruit> boxToCompare){
        return weight == boxToCompare.calculateWeight();
    }

}
