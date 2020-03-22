import utils.Constants;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Test {
    public static void main(String[] args)
    {

        // create a list of Integer type
        List<Integer> list = new ArrayList<>();
        // add 5 element in ArrayList
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);

        // boundIndex for select in sub list
        int numberOfElements = 3;

        // take a random element from list and print them
        /*for (int i = 0; i < 20; i++) {
            System.out.println(getRandomElement(list, numberOfElements));
        }*/

        System.out.println(ThreadLocalRandom.current().nextInt(Constants.MIN_RANGE_ORIG, 1+1));


    }

    // Function select an element base on index and return
    // an element
    public static List<Integer> getRandomElement(List<Integer> list,
                                          int totalItems)
    {
        Random rand = new Random();

        // create a temporary list for storing
        // selected element
        List<Integer> newList = new ArrayList<>();
        for (int i = 0; i < totalItems; i++) {

            // take a raundom index between 0 to size
            // of given List
            int randomIndex = rand.nextInt(list.size());

            // add element in temporary list
            newList.add(list.get(randomIndex));

            // Remove selected element from orginal list
            list.remove(randomIndex);
        }
        return newList;
    }
}
