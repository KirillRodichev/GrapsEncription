import model.EncryptionKey;
import model.Number;

import java.util.ArrayList;
import java.util.BitSet;

public class Main {
    public static void main(String[] args) {
        Number number = new Number();
        number.setValue(50);
        EncryptionKey key = EncryptionKey.create(number);

        ArrayList<Integer> intsList = key.getNumericKey();
        ArrayList<BitSet> bitSetsList = key.getBinaryKey();

        for (Integer integer : intsList) {
            System.out.printf("%d ", integer);
        }

        System.out.println("\n");
        for (BitSet bitSet : bitSetsList) {
            System.out.println(bitSet);
        }
    }
}
