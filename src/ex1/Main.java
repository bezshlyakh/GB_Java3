import Fruits.Apple;
import Fruits.Fruit;
import Fruits.Orange;

import java.util.ArrayList;

import static java.lang.Math.random;

public class Main {

    public static void main(String[] args) {
        Box<Apple> appleBox = new Box<>();
        Box<Orange> orangeBox = new Box<>();
        Box<Fruit> uniBox = new Box<>();
        Fruit randomFruit;

        for(int i = 0; i < uniBox.getCAPACITY(); i++){
            int j = (int) (random() * 2);
            if (j == 0){
                randomFruit = new Apple(1.0f);
            } else {
                randomFruit = new Orange(1.5f);
            }
            uniBox.fillBox(randomFruit);
            System.out.println(randomFruit.getClass().toString() + " " + randomFruit.getWeight());
        }

        System.out.println("Universal Box weight " + uniBox.calculateWeight());
        System.out.println("Total amount of fruits in Universal Box " + uniBox.calculateAmount());

        for(int i = 0; i < uniBox.getBox().size(); i++){
            if(uniBox.getBox().get(i) instanceof Apple apple){
               appleBox.getBox().add(apple);
            } else if(uniBox.getBox().get(i) instanceof Orange orange){
                orangeBox.getBox().add(orange);
            } else System.out.println("ERROR: unknown fruit");
        }
        System.out.println("Apple Box weight " + appleBox.calculateWeight() + ", " + "Amount of apples in Apple Box " + appleBox.calculateAmount());
        System.out.println("Orange Box weight " + orangeBox.calculateWeight() + ", " + "Amount of oranges in Orange Box " + orangeBox.calculateAmount());

        if(appleBox.compareBoxes(orangeBox)){
            System.out.println("Apple Box has the same weight as Orange Box");
        } else {
            System.out.println("Boxes have different weight");
        }

        //additional tasks
        //task 1. two list elems change places

        Object[] list = new Object[5];
        System.out.println("\nAdditional task 1 >> input list:");
        for(int i = 0; i < 5; i++){
            String e = "elem" + i;
            list[i] = e;
            System.out.print(e + " ");
        }
        int j = 1;
        int k = 2;

        Object obj4 = list[j];
        Object obj2 = list[k];

        list[j] = obj2;
        list[k] = obj4;

        System.out.println("\nnew list:");
        for(int i = 0; i < 5; i++){
            System.out.print(list[i] + " ");
        }

        //task 2. List to ArrayList

        Object[] list2 = new Object[5];
        ArrayList<Object> arrayList = new ArrayList<>();
        System.out.println("\n\nAdditional task 2 >> input list:");
        for(int i = 0; i < 5; i++){
            String e = "e" + i;
            list2[i] = e;
            System.out.print(e + " ");
        }

        for (int i = 0; i < list2.length; i++){
            arrayList.add(list2[i]);
        }
        System.out.print(arrayList);

    }

}
