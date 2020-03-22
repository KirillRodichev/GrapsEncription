package model;

import lombok.Getter;
import lombok.Setter;
import utils.Constants;
import utils.NonLinearArray;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import static model.MatrixBuilder.toBinaryMatrix;
import static model.MatrixBuilder.toNumericMatrix;

@Getter
@Setter
public class MatrixCipher {
    private MatrixBuilder initialMatrix;
    private List<BitSet> cryptoMatrix;
    private EncryptionKey initialKey;
    private EncryptionKey cryptoKey;

    public static MatrixCipher encrypt(MatrixBuilder initialMatrix, EncryptionKey initialKey) {
        MatrixCipher matrixCipher = new MatrixCipher();
        matrixCipher.initialMatrix = initialMatrix;
        matrixCipher.initialKey = initialKey;
        matrixCipher.cryptoKey = encryptKey(initialMatrix, initialKey);
        matrixCipher.cryptoMatrix = toBinaryMatrix(encryptMatrix(initialMatrix, matrixCipher.cryptoKey));
        return matrixCipher;
    }

    private static EncryptionKey encryptKey(MatrixBuilder initialMatrix, EncryptionKey initialKey) {
        List<Integer> cryptoNumericKey = new ArrayList<>();
        for (int i = 0; i < initialMatrix.getSize(); i++) {
            BitSet row = initialMatrix.getMatrix().get(i);
            int summand = 0;
            for (int j = 0; j < initialMatrix.getSize(); j++) {
                if (j > i && j < row.length()) {
                    if (row.get(j)) {
                        summand += initialKey.getNumericKey().get(j);
                    }
                }
            }
            cryptoNumericKey.add(summand);
        }
        EncryptionKey result = new EncryptionKey();
        result.setNumericKey(cryptoNumericKey);
        result.setBinaryKeyConvert(cryptoNumericKey);
        return result;
    }

    private static List<EncryptionKey> getRoundKeys(EncryptionKey initialKey) {
        List<EncryptionKey> roundKeys = new ArrayList<>();
        int keySize = initialKey.getNumericKey().size();
        roundKeys.add(initialKey);
        for (int i = 1; i < Constants.ROUNDS_NUMBER + 1; i++) {
            EncryptionKey roundKey = new EncryptionKey();
            List<Integer> numericKey = new ArrayList<>();
            for (int j = 0; j < keySize; j++) {
                numericKey.add(NonLinearArray.array.get(roundKeys.get(i - 1).getNumericKey().get(j)));
            }
            roundKey.setNumericKey(numericKey);
            roundKey.setBinaryKeyConvert(numericKey);
            roundKeys.add(roundKey);
        }
        roundKeys.remove(0);
        return roundKeys;
    }

    private static void goRounds(List<List<Integer>> numericMatrix, List<EncryptionKey> roundKeys) {
        for (int i = 0; i < Constants.ROUNDS_NUMBER; i++) {
            EncryptionKey roundKey = roundKeys.get(i);
            matrixXOR(numericMatrix, roundKey);
            circularShift(numericMatrix, roundKey);
            rowsShift(numericMatrix);
        }
    }

    private static void nullInit(List<Integer> list, int size) {
        for (int j = 0; j < size; j++) {
            list.add(0);
        }
    }

    private static void rowShift(List<List<Integer>> numericMatrix, int shift, int rowIndex) {
        int matrixSize = numericMatrix.size();
        List<Integer> row = new ArrayList<>();
        nullInit(row, matrixSize);
        for (int j = 0; j < matrixSize; j++) {
            int index = j + shift >= matrixSize ? (j + shift) - matrixSize : j + shift;
            row.set(index, numericMatrix.get(rowIndex).get(j));
        }
        numericMatrix.set(rowIndex, row);
    }

    private static void rowsShift(List<List<Integer>> numericMatrix) {
        int matrixSize = numericMatrix.size();
        for (int i = 0; i < matrixSize; i++) {
            rowShift(numericMatrix, i, i);
        }
    }

    private static void colShift(List<List<Integer>> numericMatrix, int shift, int curColInd) {
        int matrixSize = numericMatrix.size();
        for (int colIndex = 0; colIndex < matrixSize; colIndex++) {
            int shiftedIndex = colIndex + shift >= matrixSize ? (colIndex + shift) - matrixSize : colIndex + shift;
            List<Integer> row = numericMatrix.get(colIndex);
            List<Integer> shiftedRow = numericMatrix.get(shiftedIndex);
            row.set(curColInd, shiftedRow.get(curColInd));
            numericMatrix.set(colIndex, row);
        }
    }

    private static void circularShift(List<List<Integer>> numericMatrix, EncryptionKey roundKey) {
        int matrixSize = numericMatrix.size();
        List<Integer> numericKey = roundKey.getNumericKey();
        for (int i = 0; i < matrixSize; i++) {
            int shift = numericKey.get(i) % matrixSize;
            rowShift(numericMatrix, shift, i);
            colShift(numericMatrix, shift, i);
        }
    }

    private static List<List<Integer>> matrixXOR(List<List<Integer>> numericMatrix, EncryptionKey roundKey) {
        List<BitSet> binaryMatrix = toBinaryMatrix(numericMatrix);
        List<BitSet> binaryKey = roundKey.getBinaryKey();
        for (int i = 0; i < binaryMatrix.size(); i++) {
            binaryMatrix.get(i).xor(binaryKey.get(i));
        }
        return toNumericMatrix(binaryMatrix);
    }

    private static List<List<Integer>> encryptMatrix(MatrixBuilder initialMatrix, EncryptionKey initialKey) {
        List<List<Integer>> numericMatrix = toNumericMatrix(initialMatrix.getMatrix());
        List<EncryptionKey> roundKeys = getRoundKeys(initialKey);
        goRounds(numericMatrix, roundKeys);
        return numericMatrix;
    }
}