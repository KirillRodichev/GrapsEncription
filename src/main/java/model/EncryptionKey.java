package model;

import lombok.Getter;
import lombok.Setter;
import utils.Constants;

import java.util.ArrayList;
import java.util.BitSet;

@Getter
@Setter
public class EncryptionKey extends Bytes {
    private ArrayList<Integer> numericKey;
    private ArrayList<BitSet> binaryKey;

    public static EncryptionKey create(Number number) {
        EncryptionKey encryptionKey = new EncryptionKey();

        ArrayList<Integer> decomposedNumber = number.decompose(Constants.MAT_SIZE_8);
        ArrayList<BitSet> decomposedBinaryNumber = convertToBits(decomposedNumber);

        encryptionKey.setNumericKey(decomposedNumber);
        encryptionKey.setBinaryKey(decomposedBinaryNumber);

        return encryptionKey;
    }
}
