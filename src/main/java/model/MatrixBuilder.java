package model;

import lombok.Data;
import lombok.Getter;
import utils.Constants;
import utils.Printer;
import utils.Randomizer;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class MatrixBuilder {
    private List<BitSet> matrix;
    private int size;

    public MatrixBuilder() {
        this.size = Constants.MAT_SIZE_8;
        int specVertQuantity = calculateSpecialVertQuantity();
        System.out.println("\nSpecial vertexes quantity: " + specVertQuantity);
        List<Integer> specVertPositions = calculateSpecVertPositions(specVertQuantity);
        Printer.printIntList(specVertPositions, "Special vertexes positions");
        Map<Integer, List<Integer>> specVertRelatedVertexes = getSpecVertRelatedVertexes(specVertPositions);
        Printer.printMap(specVertRelatedVertexes, "Special vertexes and relatedList");
        fillMatrix(specVertRelatedVertexes);
    }

    private int calculateSpecialVertQuantity() {
        return ThreadLocalRandom.current().nextInt(Constants.SPEC_VERT_RANGE_ORIG, Constants.SPEC_VERT_RANGE_BOUND);
    }

    private List<Integer> calculateSpecVertPositions(int quantity) {
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            positions.add(i);
        }
        return Randomizer.getNonRepeatedList(positions, quantity);
    }

    private Map<Integer, List<Integer>> getSpecVertRelatedVertexes(List<Integer> specVertPositions) {
        Map<Integer, List<Integer>> relativeMap = new HashMap<>();
        List<Integer> remainingPositions = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            if (!specVertPositions.contains(i)) {
                remainingPositions.add(i);
            }
        }
        for (int i = 0; i < specVertPositions.size(); i++) {
            int quantityOfRelatedVertexes = 0;
            if (remainingPositions.size() > 1) {
                quantityOfRelatedVertexes = ThreadLocalRandom.current().nextInt(
                        Constants.MIN_RANGE_ORIG,
                        remainingPositions.size() + 1 - (specVertPositions.size() - (i + 1))
                );
            } else if (remainingPositions.size() == 1) {
                quantityOfRelatedVertexes = 1;
            }
            List<Integer> relatedVertexesPositions
                    = Randomizer.getNonRepeatedList(remainingPositions, quantityOfRelatedVertexes);
            relativeMap.put(specVertPositions.get(i), relatedVertexesPositions);
        }
        return relativeMap;
    }

    private void fillSymmetric(Map<Integer, List<Integer>> relativeMap) {
        for (int i = 0; i < this.size; i++) {
            BitSet matrixRow = this.matrix.get(i);
            List<Integer> relatedList = relativeMap.get(i);
            if (relatedList != null) {
                for (Integer pos : relatedList) {
                    matrixRow.set(pos, true);
                    BitSet relatedRow = new BitSet(this.size);
                    relatedRow.set(i, true);
                    this.matrix.set(pos, relatedRow);
                }
            }
        }
    }

    private List<List<Integer>> generateCombinations(List<List<Integer>> lists) {
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

    private List<List<Integer>> selectRelations(List<List<Integer>> generatedRelations) {
        List<List<Integer>> selectedRelations = new ArrayList<>();
        int quantityOfRelations
                = ThreadLocalRandom.current().nextInt(Constants.MIN_RANGE_ORIG, generatedRelations.size() + 1);
        List<Integer> listOfRelationsSeqNumbers = new ArrayList<>();
        for (int i = 0; i < generatedRelations.size(); i++) {
            listOfRelationsSeqNumbers.add(i);
        }
        List<Integer> selectedRelationsSeqNumbers
                = Randomizer.getNonRepeatedList(listOfRelationsSeqNumbers, quantityOfRelations);
        for (Integer num : selectedRelationsSeqNumbers) {
            selectedRelations.add(generatedRelations.get(num));
        }
        return selectedRelations;
    }

    private void fillRelations(List<List<Integer>> selectedRelations) {
        final int FIRST_RELATION_NUMBER = 0;
        final int SECOND_RELATION_NUMBER = 1;
        for (int i = 0; i < this.size; i++) {
            BitSet matrixRow = matrix.get(i);
            for (List<Integer> relation : selectedRelations) {
                if (i == relation.get(FIRST_RELATION_NUMBER)) {
                    matrixRow.set(relation.get(SECOND_RELATION_NUMBER), true);
                } else if (i == relation.get(SECOND_RELATION_NUMBER)) {
                    matrixRow.set(relation.get(FIRST_RELATION_NUMBER));
                }
            }
        }
    }

    private void addRelations(Map<Integer, List<Integer>> relativeMap) {
        List<List<Integer>> notRelatedVertLists = new ArrayList<>();
        for (Integer key : relativeMap.keySet()) {
            notRelatedVertLists.add(relativeMap.get(key));
        }
        List<List<Integer>> relationsVariations = generateCombinations(notRelatedVertLists);
        List<List<Integer>> selectedRelations = selectRelations(relationsVariations);
        fillRelations(selectedRelations);
    }

    private void fillMatrix(Map<Integer, List<Integer>> specVertRelatedVertexes) {
        fillDefault();
        fillSymmetric(specVertRelatedVertexes);
        print("Symmetric matrix");
        addRelations(specVertRelatedVertexes);
        print("Prepared matrix");
    }

    private void fillDefault() {
        this.matrix = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            this.matrix.add(new BitSet(this.size));
        }
    }

    public void print(String header) {
        Printer.printBinList(this.matrix, header);
    }

    public static List<List<Integer>> toNumericMatrix(List<BitSet> matrix) {
        List<List<Integer>> result = new ArrayList<>();
        int matrixSize = matrix.size();
        for (BitSet binaryRow : matrix) {
            List<Integer> numericRow = new ArrayList<>();
            for (int j = 0; j < matrixSize; j++) {
                int val;
                if (j < binaryRow.size()) {
                    val = binaryRow.get(j) ? 1 : 0;
                } else {
                    val = 0;
                }
                numericRow.add(val);
            }
            result.add(numericRow);
        }
        return result;
    }

    public static List<BitSet> toBinaryMatrix(List<List<Integer>> matrix) {
        List<BitSet> result = new ArrayList<>();
        int matrixSize = matrix.size();
        for (List<Integer> list : matrix) {
            BitSet row = new BitSet(matrixSize);
            for (int j = 0; j < matrixSize; j++) {
                if (list.get(j) == 1) {
                    row.set(j, true);
                }
            }
            result.add(row);
        }
        return result;
    }
}
