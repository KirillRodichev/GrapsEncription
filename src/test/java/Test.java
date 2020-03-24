import model.EncryptionKey;
import model.MatrixBuilder;
import model.Number;
import utils.Array;
import utils.Constants;
import utils.NonLinearArray;
import utils.Randomizer;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static model.MatrixBuilder.toBinaryMatrix;
import static model.MatrixBuilder.toNumericMatrix;

public class Test {
    public static void main(String[] args)
    {


        /*ist<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        List<Integer> list1 = new ArrayList<>();
        list1.add(3);
        list1.add(4);
        List<Integer> list2 = new ArrayList<>();
        list2.add(5);
        list2.add(6);
        List<List<Integer>> lol = new ArrayList<>();
        lol.add(list);
        lol.add(list1);
        lol.add(list2);
        List<List<Integer>> lists = generateCombinations(lol);*/

       /* List<BitSet> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(new BitSet(4));
        }
        BitSet bitSet = new BitSet();
        bitSet.set(0, true);
        bitSet.set(2, true);
        list.set(2, bitSet);*/

        /*List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(3);
        list.add(4);

        Map<Integer, List<Integer>> map = getSpecVertRelatedVertexes(list);
        Map<Integer, List<Integer>> map1 = getSpecVertRelatedVertexes(list);
        Map<Integer, List<Integer>> map2 = getSpecVertRelatedVertexes(list);*/

        /*Number number = new Number();
        number.setValue(50);
        EncryptionKey key = EncryptionKey.create(number);

        List<EncryptionKey> roundKeys = getRoundKeys(key, 5);*/

        /*List<Integer> list = new ArrayList<>(Arrays.asList(1,0,1,1,0,0));
        List<Integer> list1 = new ArrayList<>(Arrays.asList(0,0,0,1,1,0));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(1,1,1,0,1,0));
        List<Integer> list3 = new ArrayList<>(Arrays.asList(0,0,1,1,0,0));
        List<Integer> list4 = new ArrayList<>(Arrays.asList(1,0,1,1,0,0));
        List<Integer> list5 = new ArrayList<>(Arrays.asList(0,0,1,1,1,0));
        List<List<Integer>> lists = new ArrayList<>();
        lists.add(list);
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        lists.add(list4);
        lists.add(list5);
        List<BitSet> mat = toBinaryMatrix(lists);*/

        MatrixBuilder matrixBuilder = new MatrixBuilder();
        System.out.println("initial");

        Number number = new Number();
        number.setValue(60);
        EncryptionKey key = EncryptionKey.create(number);

        List<List<Integer>> numM = MatrixBuilder.toNumericMatrix(matrixBuilder.getMatrix());
        circularShift(numM, key);

        /*System.out.println();
        Array.printBinaryArray(matrixBuilder.getMatrix());
        System.out.println();
        Array.printNumericArray(numM);*/

        /*MatrixBuilder matrixBuilder = new MatrixBuilder();
        matrixBuilder.print();
        Number number = new Number();
        number.setValue(60);
        EncryptionKey key = EncryptionKey.create(number);
        circularShift(toNumericMatrix(matrixBuilder.getMatrix()), key);*/
    }

    private static void rowsShift(List<List<Integer>> numericMatrix) {
        int matrixSize = numericMatrix.size();
        for (int i = 0; i < matrixSize; i++) {
            rowShift(numericMatrix, i, i);
            System.out.printf("\nshift = %d, i = %d\n", i, i);
            Array.printNumericArray(numericMatrix);
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
        for (int colInd = 0; colInd < matrixSize; colInd++) {
            int shift = numericKey.get(colInd) % matrixSize;
            rowShift(numericMatrix, shift, colInd);
            System.out.printf("\nshift = %d, i = %d\n", shift, colInd);
            Array.printNumericArray(numericMatrix);
            colShift(numericMatrix, shift, colInd);
            System.out.println();
            Array.printNumericArray(numericMatrix);
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

    private static void nullInit(List<Integer> list, int size) {
        for (int j = 0; j < size; j++) {
            list.add(0);
        }
    }

    private static void rowShift(List<List<Integer>> numericMatrix, int shift, int rowIndex) {////////////////////
        int matrixSize = numericMatrix.size();
        List<Integer> row = new ArrayList<>();
        nullInit(row, matrixSize);
        for (int j = 0; j < matrixSize; j++) {
            int index = j + shift >= matrixSize ? (j + shift) - matrixSize : j + shift;////////////
            row.set(index, numericMatrix.get(rowIndex).get(j));////////
        }
        numericMatrix.set(rowIndex, row);/////////
    }

    private static List<BitSet> toBinaryMatrix(List<List<Integer>> matrix) {
        List<BitSet> result = new ArrayList<>();
        int matrixSize = matrix.size();
        for (int i = 0; i < matrixSize; i++) {
            BitSet row = new BitSet(matrixSize);
            for (int j = 0; j < matrixSize; j++) {
                if (matrix.get(i).get(j) == 1) {
                    row.set(j, true);
                }
            }
            result.add(row);
        }
        return result;
    }

    private static List<EncryptionKey> getRoundKeys(EncryptionKey initialKey, int size) {
        List<EncryptionKey> roundKeys = new ArrayList<>();
        int keySize = initialKey.getNumericKey().size();
        roundKeys.add(initialKey);
        for (int i = 1; i < size + 1; i++) {
            EncryptionKey roundKey = new EncryptionKey();
            List<Integer> numericKey = new ArrayList<>();
            for (int j = 0; j < keySize; j++) {
                numericKey.add(NonLinearArray.array.get(roundKeys.get(i - 1).getNumericKey().get(j)));
            }
            roundKey.setNumericKey(numericKey);
            roundKey.setBinaryKeyConvert(numericKey);
            roundKeys.add(roundKey);
        }
        return roundKeys;
    }

    private static Map<Integer, List<Integer>> getSpecVertRelatedVertexes(List<Integer> specVertPositions) {
        Map<Integer, List<Integer>> relativeMap = new HashMap<>();
        List<Integer> remainingPositions = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (!specVertPositions.contains(i)) {
                remainingPositions.add(i);
            }
        }
        for (int i = 0; i < specVertPositions.size(); i++) {
            int quantityOfRelatedVertexes = 0;
            if (remainingPositions.size() > 1) {
                quantityOfRelatedVertexes
                        = ThreadLocalRandom.current().nextInt(Constants.MIN_RANGE_ORIG, remainingPositions.size() + 1 - (specVertPositions.size() - (i + 1)));
            } else if (remainingPositions.size() == 1) {
                quantityOfRelatedVertexes = 1;
            }
            List<Integer> relatedVertexesPositions
                    = Randomizer.getNonRepeatedList(remainingPositions, quantityOfRelatedVertexes);
            relativeMap.put(specVertPositions.get(i), relatedVertexesPositions);
        }
        return relativeMap;
    }

    private static List<List<Integer>> generateCombinations(List<List<Integer>> lists) {
        List<List<Integer>> combinations = new ArrayList<>();
        for (int i = 0; i < lists.size() - 1; i++) {
            for (Integer elem : lists.get(i)) {
                for (int j = i + 1; j < lists.size(); j++) {
                    for (Integer neighborElem : lists.get(j)) {
                        List<Integer> combination = new ArrayList<>();
                        combination.add(elem);
                        combination.add(neighborElem);
                        combinations.add(combination);
                    }
                }
            }
        }
        return combinations;
    }

    // Function select an element base on index and return
    // an element
    public static List<Integer> getRandomElement(List<Integer> list,
                                          int totalItems)
    {
        Random rand = new Random();

        // create a temporary list for storing
        // selected element
        List<Integer> newList = new ArrayList<>();
        for (int i = 0; i < totalItems; i++) {

            // take a raundom index between 0 to size
            // of given List
            int randomIndex = rand.nextInt(list.size());

            // add element in temporary list
            newList.add(list.get(randomIndex));

            // Remove selected element from orginal list
            list.remove(randomIndex);
        }
        return newList;
    }
}
