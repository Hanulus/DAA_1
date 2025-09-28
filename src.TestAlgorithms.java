package algorithms; 

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;

public class TestAlgorithms {
    
    @Test
    public void testMergeSort() {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        int[] expected = {11, 12, 22, 25, 34, 64, 90};
        
        Algorithms.mergeSort(arr);
        assertArrayEquals(expected, arr);
        
        // Test recursion depth is reasonable
        assertTrue(Algorithms.getMergeDepth() <= Math.ceil(Math.log(arr.length) / Math.log(2)) + 1);
    }
    
    @Test
    public void testQuickSort() {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        int[] expected = {11, 12, 22, 25, 34, 64, 90};
        
        Algorithms.quickSort(arr);
        assertArrayEquals(expected, arr);
        
        // Test depth is bounded (approximately 2*log2(n) for random pivot)
        assertTrue(Algorithms.getQuickDepth() <= 2 * Math.floor(Math.log(arr.length) / Math.log(2)) + 5);
    }
    
    @Test
    public void testSelect() {
        Random rand = new Random(42);
        
        // Test 100 random trials as required
        for (int trial = 0; trial < 100; trial++) {
            int[] arr = new int[50];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = rand.nextInt(1000);
            }
            
            int k = rand.nextInt(arr.length);
            int[] sortedCopy = arr.clone();
            Arrays.sort(sortedCopy);
            
            int result = Algorithms.select(arr.clone(), k);
            assertEquals(sortedCopy[k], result);
        }
    }
    
    @Test
    public void testClosestPair() {
        ClosestPoints.Point[] points = {
            new ClosestPoints.Point(0, 0),
            new ClosestPoints.Point(1, 1),
            new ClosestPoints.Point(2, 2),
            new ClosestPoints.Point(10, 10)
        };
        
        double result = ClosestPoints.findClosestDistance(points);
        double expected = Math.sqrt(2); // distance between (0,0) and (1,1)
        
        assertEquals(expected, result, 0.001);
    }
    
    @Test
    public void testClosestPairBruteForceComparison() {
        // Test against brute force on small arrays
        Random rand = new Random(42);
        ClosestPoints.Point[] points = new ClosestPoints.Point[20];
        
        for (int i = 0; i < points.length; i++) {
            points[i] = new ClosestPoints.Point(rand.nextDouble() * 100, rand.nextDouble() * 100);
        }
        
        double fastResult = ClosestPoints.findClosestDistance(points);
        double bruteResult = bruteForceClosest(points);
        
        assertEquals(bruteResult, fastResult, 0.001);
    }
    
    private double bruteForceClosest(ClosestPoints.Point[] points) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                min = Math.min(min, points[i].distanceTo(points[j]));
            }
        }
        return min;
    }
    
    @Test
    public void testAdversarialArrays() {
        // Test reverse sorted array
        int[] reverse = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        
        Algorithms.mergeSort(reverse.clone());
        int[] mergeResult = reverse.clone();
        Algorithms.mergeSort(mergeResult);
        assertArrayEquals(expected, mergeResult);
        
        int[] quickResult = reverse.clone();
        Algorithms.quickSort(quickResult);
        assertArrayEquals(expected, quickResult);
    }
}
