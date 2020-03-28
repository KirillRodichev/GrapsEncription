package utils;

import java.io.PrintWriter;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

public class Printer {
    public static void printIntList(List<Integer> list, String header) {
        System.out.println("\n" + header);
        for (Integer num : list) {
            System.out.printf("%d ", num);
        }
    }

    public static void printIntList(List<Integer> list, PrintWriter out) {
        for (Integer num : list) {
            out.printf("%d,", num);
        }
        out.println();
    }

    public static void printIntList(List<Integer> list, List<Integer> indexes, PrintWriter out) {
        for (Integer index : indexes) {
            out.printf("%d,", list.get(index));
        }
        out.println();
    }

    public static void printBinList(List<BitSet> list, List<Integer> indexes, PrintWriter out) {
        for (Integer i : indexes) {
            BitSet row = list.get(i);
            for (int j = 0; j < list.size(); j++) {
                int val;
                if (j < row.size()) {
                    val = row.get(j) ? 1 : 0;
                } else {
                    val = 0;
                }
                out.printf("%d,", val);
            }
        }
        out.println();
    }

    public static void printBinList(List<BitSet> list, String header) {
        System.out.println("\n" + header);
        for (int i = 0; i < list.size(); i++) {
            BitSet row = list.get(i);
            for (int j = 0; j < list.size(); j++) {
                int val;
                if (j < row.size()) {
                    val = row.get(j) ? 1 : 0;
                } else {
                    val = 0;
                }
                System.out.printf("%d ", val);
            }
            System.out.print(" | ");
        }
    }

    public static void printMap(Map<Integer, List<Integer>> map, String header) {
        System.out.println("\n" + header);
        for (Integer key : map.keySet()) {
            System.out.printf("Key: %d", key);
            printIntList(map.get(key), "values");
        }
    }

    public static void printNumericMatrix(List<List<Integer>> lists, String header) {
        System.out.println("\n" + header);
        for (List<Integer> list : lists) {
            for (Integer num : list) {
                System.out.printf("%3d", num);
            }
            System.out.println();
        }
    }
}
