package model;

import lombok.Data;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class Number {
    private int value;

    private static final int MIN = 1;

    public ArrayList<Integer> decompose(int matrixSize) {
        ArrayList<Integer> decomposedNumber= new ArrayList<>();
        int prevSum = 0;
        for (int i = 0; i < matrixSize; i++) {
            int bound = this.value - (matrixSize - (i + 1)) - prevSum;
            int range = ThreadLocalRandom.current().nextInt(MIN, bound + 1);
            int curVal = ThreadLocalRandom.current().nextInt(MIN, range + 1);
            prevSum += curVal;
            decomposedNumber.add(curVal);
        }
        return decomposedNumber;
    }
}
