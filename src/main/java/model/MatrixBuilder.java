package model;

import utils.Constants;
import utils.Randomizer;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MatrixBuilder {
    private List<BitSet> matrix;
    private int size;

    public MatrixBuilder(int number) {
        this.size = Constants.MAT_SIZE_8;
        this.matrix = new ArrayList<>();
        int specVertQuantity = calculateSpecialVertQuantity();
        List<Integer> specVertPositions = calculateSpecVertPositions(specVertQuantity);
        Map<Integer, List<Integer>> specVertRelatedVertexes = getSpecVertRelatedVertexes(specVertPositions);
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
        for (Integer pos : specVertPositions) {
            int quantityOfRelatedVertexes = remainingPositions.size() > 1
                    ? ThreadLocalRandom.current().nextInt(Constants.MIN_RANGE_ORIG, remainingPositions.size())
                    : 1;
            List<Integer> relatedVertexesPositions
                    = Randomizer.getNonRepeatedList(remainingPositions, quantityOfRelatedVertexes);
            relativeMap.put(pos, relatedVertexesPositions);
        }
        return relativeMap;
    }

    private void fillSymmetric(Map<Integer, List<Integer>> relativeMap) {
        for (int i = 0; i < this.size; i++) {
            BitSet matrixRow = new BitSet(this.size);
            this.matrix.add(matrixRow);
            List<Integer> relatedList = relativeMap.get(i);
            if (relatedList != null) {
                for (Integer pos : relatedList) {
                    matrixRow.set(pos, true);
                    BitSet relatedRow = new BitSet(this.size);
                    relatedRow.set(i, true);
                    this.matrix.add(pos, relatedRow);
                }
            }
        }
    }

    private List<List<Integer>> generateCombinations(List<List<Integer>> lists) {
        List<List<Integer>> combinations = new ArrayList<>();
        int totalQuantity = 0;
        for (int i = 0; i < lists.size() - 1; i++) {
            int quantity = lists.get(i).size();
            for (int j = i; j < lists.size(); j++) {
                quantity *= lists.get(j).size();
            }
            totalQuantity += quantity;
        }
        return combinations;
    }

    private void addRelations(Map<Integer, List<Integer>> relativeMap) {
        List<List<Integer>> notRelatedSets = new ArrayList<>();
        List<List<Integer>> relationsVariations = new ArrayList<>();
        for (Integer key : relativeMap.keySet()) {
            notRelatedSets.add(relativeMap.get(key));
        }
        for (List<Integer> set : notRelatedSets) {
            for (Integer vertex : set) {

            }
        }
    }

    private void fillMatrix(Map<Integer, List<Integer>> specVertRelatedVertexes, List<Integer> specVertPositions) {
        fillSymmetric(specVertRelatedVertexes);
    }
}
