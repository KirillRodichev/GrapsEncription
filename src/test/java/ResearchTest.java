import model.EncryptionKey;
import model.MatrixBuilder;
import model.Cipher;
import model.Number;
import utils.Array;
import utils.Printer;

import java.util.BitSet;
import java.util.List;

public class ResearchTest {
    public static void main(String[] args) {
        Number number = new Number();
        number.setValue(60);
        EncryptionKey initialKey = EncryptionKey.create(number);

        Printer.printIntList(initialKey.getNumericKey(), "Initial key: numeric");
        Printer.printBinList(initialKey.getBinaryKey(), "Initial key: binary");

        MatrixBuilder matrixBuilder = new MatrixBuilder();

        Cipher cipher = Cipher.encrypt(matrixBuilder, initialKey);
        List<BitSet> cryptoMatrix = cipher.getCryptoMatrix();
        System.out.println("\n" + "FINAL");
        Array.printBinaryArray(cryptoMatrix);
    }
}
