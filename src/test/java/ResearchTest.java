import model.EncryptionKey;
import model.MatrixBuilder;
import model.Cipher;
import model.Number;
import utils.Array;
import utils.Printer;
import utils.Randomizer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class ResearchTest {
    public static void main(String[] args) {
        Number number = new Number();
        //number.setValue(60);
        try (PrintWriter printWriter = new PrintWriter(new FileWriter("out.txt"))) {
            /*for (int i = 0; i < 1000; i++) {
                EncryptionKey initialKey = EncryptionKey.create(number);
                MatrixBuilder matrixBuilder = new MatrixBuilder();
                Cipher cipher = Cipher.encrypt(matrixBuilder, initialKey);
                List<BitSet> cryptoMatrix = cipher.getCryptoMatrix();
                Array.printBinaryArray(cryptoMatrix, printWriter);
            }*/


            for (int i = 20000; i < 30000; i++) {
                List<Integer> initialList = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7));
                number.setValue(i);
                EncryptionKey initialKey = EncryptionKey.create(number);
                MatrixBuilder matrixBuilder = new MatrixBuilder();
                Cipher cipher = Cipher.encrypt(matrixBuilder, initialKey);
                EncryptionKey cryptoKey = cipher.getCryptoKey();
                List<Integer> numericKey = cryptoKey.getNumericKey();
                List<Integer> indexes = Randomizer.getNonRepeatedList(initialList, initialList.size());
                //Printer.printIntList(numericKey, list, printWriter);
                //Printer.printIntList(numericKey, printWriter);
                //Printer.printIntList(list, printWriter);
                Printer.printBinList(cryptoKey.getBinaryKey(), indexes, printWriter);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
