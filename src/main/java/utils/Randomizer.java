package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Randomizer {
    public static List<Integer> getNonRepeatedList(List<Integer> initialList, int quantity) {
        Random rand = new Random();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            int randomIndex = rand.nextInt(initialList.size());

            result.add(initialList.get(randomIndex));

            initialList.remove(randomIndex);
        }
        return result;
    }
}
