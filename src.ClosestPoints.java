package algorithms;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPoints {
    
    public static class Point {
        double x, y;
        
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        public double distanceTo(Point other) {
            return Math.sqrt((this.x - other.x) * (this.x - other.x) + 
                           (this.y - other.y) * (this.y - other.y));
        }
    }
    
    public static double findClosestDistance(Point[] points) {
        if (points.length < 2) return Double.POSITIVE_INFINITY;
        
        // Sort by x coordinate
        Point[] pointsByX = points.clone();
        Arrays.sort(pointsByX, Comparator.comparingDouble(p -> p.x));
        
        // Create array sorted by y for strip checking
        ClosestPoints.Point[] pointsByY = points.clone();
        Arrays.sort(pointsByY, Comparator.comparingDouble(p -> p.y));
        
        return closestPairRec(pointsByX, pointsByY, 0, points.length - 1);
    }
    
    private static double closestPairRec(ClosestPoints.Point[] pointsByX, ClosestPoints.Point[] pointsByY, int left, int right) {
        // Base case - brute force for small arrays
        if (right - left <= 3) {
            return bruteForce(pointsByX, left, right);
        }
        
        int mid = left + (right - left) / 2;
        Point midPoint = pointsByX[mid];
        
        // Split pointsByY array
        Point[] leftY = new Point[mid - left + 1];
        Point[] rightY = new Point[right - mid];
        
        int li = 0, ri = 0;
        for (Point p : pointsByY) {
            if ((p.x < midPoint.x || (p.x == midPoint.x && p.y < midPoint.y)) && li < leftY.length) {
                leftY[li++] = p;
            } else if (ri < rightY.length) {
                rightY[ri++] = p;
            }
        }
        
        // Recursive calls
        double leftMin = closestPairRec(pointsByX, leftY, left, mid);
        double rightMin = closestPairRec(pointsByX, rightY, mid + 1, right);
        
        double minDist = Math.min(leftMin, rightMin);
        
        // Check strip around the dividing line
        return Math.min(minDist, stripClosest(pointsByY, midPoint.x, minDist));
    }
    
    private static double stripClosest(Point[] pointsByY, double midX, double minDist) {
        // Find points in strip
        int stripSize = 0;
        for (Point p : pointsByY) {
            if (Math.abs(p.x - midX) < minDist) {
                stripSize++;
            }
        }
        
        Point[] strip = new Point[stripSize];
        int idx = 0;
        for (Point p : pointsByY) {
            if (Math.abs(p.x - midX) < minDist) {
                strip[idx++] = p;
            }
        }
        
        // Check distances in strip (classic 7-8 neighbor scan)
        double min = minDist;
        for (int i = 0; i < strip.length; i++) {
            for (int j = i + 1; j < strip.length && (strip[j].y - strip[i].y) < min; j++) {
                min = Math.min(min, strip[i].distanceTo(strip[j]));
            }
        }
        
        return min;
    }
    
    private static double bruteForce(Point[] points, int left, int right) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                min = Math.min(min, points[i].distanceTo(points[j]));
            }
        }
        return min;
    }
}
