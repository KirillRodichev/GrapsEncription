package controller;

import model.EncryptionKey;
import model.MatrixBuilder;
import model.MatrixCipher;
import model.Number;
import utils.Array;

import java.util.BitSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Number number = new Number();
        number.setValue(50);
        EncryptionKey initialKey = EncryptionKey.create(number);

        List<Integer> intsList = initialKey.getNumericKey();
        List<BitSet> bitSetsList = initialKey.getBinaryKey();

        MatrixBuilder matrixBuilder = new MatrixBuilder();

        System.out.println("\n");
        matrixBuilder.print();

        MatrixCipher matrixCipher = MatrixCipher.encrypt(matrixBuilder, initialKey);
        List<BitSet> cryptoMatrix = matrixCipher.getCryptoMatrix();
        System.out.println();
        Array.printBinaryArray(cryptoMatrix);
    }
}
