Divide and Conquer Algorithms
What I implemented
I made 4 algorithms using divide and conquer approach:

MergeSort
QuickSort
Deterministic Select (finds kth element)
Closest Pair of Points

How it works
Architecture
I tried to control recursion depth to avoid stack overflow:

MergeSort: uses depth tracking, switches to insertion sort for small arrays (< 10 elements)
QuickSort: does "smaller first" recursion - recurse on smaller part, iterate on bigger part
Select: only recurses into one side (where target element is)
ClosestPair: depth is naturally O(log n)

For memory, MergeSort reuses one buffer array instead of creating new ones each time.
Algorithm Analysis
MergeSort: T(n) = 2T(n/2) + O(n) = O(n log n)
Uses Master Theorem case 2. Always splits in half and merges in linear time.
QuickSort: Average case O(n log n) with random pivot
Worst case could be O(n²) but randomized pivot makes it unlikely. Smaller-first keeps stack depth low.
Select: T(n) = T(n/5) + T(3n/4) + O(n) = O(n)
Uses median-of-medians to guarantee good pivot. Always eliminates at least 1/4 of elements.
ClosestPair: T(n) = 2T(n/2) + O(n) = O(n log n)
Same as MergeSort - divide points in half, solve recursively, check middle strip.
Testing

Tested sorting on random and reverse-sorted arrays
Verified QuickSort depth stays reasonable (≤ 2*log₂(n) + some constant)
Tested Select against Arrays.sort() on 100 random cases
Validated ClosestPair against brute force O(n²) method

What I learned
The algorithms work as expected theoretically. MergeSort and ClosestPair both get O(n log n) consistently. QuickSort averages O(n log n) but can vary due to randomness. Select actually runs in linear time which is pretty cool.
Small optimizations like insertion sort cutoff and reusable buffers help performance without changing the big-O complexity.
