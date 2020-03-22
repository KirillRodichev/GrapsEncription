package model;

import java.util.ArrayList;
import java.util.BitSet;

abstract class Bytes {

    public static BitSet convert(Integer value) {
        BitSet bits = new BitSet();
        int index = 0;
        while (value != 0) {
            if (value % 2 != 0) {
                bits.set(index);
            }
            ++index;
            value = value >>> 1;
        }
        return bits;
    }

    public static int convert(BitSet bits) {
        int value = 0;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1 << i) : 0;
        }
        return value;
    }

    public static ArrayList<Integer> convertToInts(ArrayList<BitSet> bitSets) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (BitSet bitSet : bitSets) {
            numbers.add(convert(bitSet));
        }
        return numbers;
    }

    public static ArrayList<BitSet> convertToBits(ArrayList<Integer> numbers) {
        ArrayList<BitSet> bitSets = new ArrayList<>();
        for (Integer number : numbers) {
            bitSets.add(convert(number));
        }
        return bitSets;
    }
}
