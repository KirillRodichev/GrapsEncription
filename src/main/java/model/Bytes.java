package model;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

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

    public static List<Integer> convertToInts(List<BitSet> bitSets) {
        List<Integer> numbers = new ArrayList<>();
        for (BitSet bitSet : bitSets) {
            numbers.add(convert(bitSet));
        }
        return numbers;
    }

    public static List<BitSet> convertToBits(List<Integer> numbers) {
        List<BitSet> bitSets = new ArrayList<>();
        for (Integer number : numbers) {
            bitSets.add(convert(number));
        }
        return bitSets;
    }
}
