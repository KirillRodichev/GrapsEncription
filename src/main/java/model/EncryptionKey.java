package model;

import lombok.Getter;
import lombok.Setter;
import utils.Constants;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@Getter
@Setter
public class EncryptionKey extends Bytes {
    private List<Integer> numericKey;
    private List<BitSet> binaryKey;

    public static EncryptionKey create(Number number) {
        EncryptionKey encryptionKey = new EncryptionKey();

        List<Integer> decomposedNumber = number.decompose(Constants.MAT_SIZE_8);
        List<BitSet> decomposedBinaryNumber = convertToBits(decomposedNumber);

        encryptionKey.setNumericKey(decomposedNumber);
        encryptionKey.setBinaryKey(decomposedBinaryNumber);

        return encryptionKey;
    }

    public void setBinaryKeyConvert(List<Integer> numericKey) {
        this.binaryKey = convertToBits(numericKey);
    }
}
