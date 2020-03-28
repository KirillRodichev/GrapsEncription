package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.BitSet;
import java.util.List;

public class Array {
    public static void printNumericArray(List<List<Integer>> matrix) {
        for (int i = 0; i < matrix.size(); i++) {
            List<Integer> row = matrix.get(i);
            for (int j = 0; j < matrix.size(); j++) {
                System.out.printf("%3d", row.get(j));
            }
            System.out.println();
        }
    }

    public static void printBinaryArray(List<BitSet> matrix) {
        for (int i = 0; i < matrix.size(); i++) {
            BitSet row = matrix.get(i);
            for (int j = 0; j < matrix.size(); j++) {
                int val;
                if (j < row.size()) {
                    val = row.get(j) ? 1 : 0;
                } else {
                    val = 0;
                }
                System.out.printf("%d,", val);
            }
        }
    }

    public static void printBinaryArray(List<BitSet> matrix, PrintWriter writer) throws IOException {
        for (int i = 0; i < matrix.size(); i++) {
            BitSet row = matrix.get(i);
            for (int j = 0; j < matrix.size(); j++) {
                int val;
                if (j < row.size()) {
                    val = row.get(j) ? 1 : 0;
                } else {
                    val = 0;
                }
                writer.printf("%d,", val);
            }
        }
        writer.println();
    }
}
