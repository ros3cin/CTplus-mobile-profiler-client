package cin.ufpe.br.energyprofiler.modes.selectors.implementations.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by welli on 05-Dec-17.
 */

public class SortingImpl {

    public static void bubbleSort(Integer[] comparable) {
            boolean changed = false;
            do {
                changed = false;
                for (int a = 0; a < comparable.length - 1; a++) {
                    if (comparable[a].compareTo(comparable[a + 1]) > 0) {
                        Integer tmp = comparable[a];
                        comparable[a] = comparable[a + 1];
                        comparable[a + 1] = tmp;
                        changed = true;
                    }
                }
            } while (changed);
        }

    public static void heapSort(Integer[] a){
        int count = a.length;

        //first place a in max-heap order
        heapify(a, count);

        int end = count - 1;
        while(end > 0){
            //swap the root(maximum value) of the heap with the
            //last element of the heap
            int tmp = a[end];
            a[end] = a[0];
            a[0] = tmp;
            //put the heap back in max-heap order
            siftDown(a, 0, end - 1);
            //decrement the size of the heap so that the previous
            //max value will stay in its proper place
            end--;
        }
    }

    private static void heapify(Integer[] a, int count){
        //start is assigned the index in a of the last parent node
        int start = (count - 2) / 2; //binary heap

        while(start >= 0){
            //sift down the node at index start to the proper place
            //such that all nodes below the start index are in heap
            //order
            siftDown(a, start, count - 1);
            start--;
        }
        //after sifting down the root all nodes/elements are in heap order
    }

    private static void siftDown(Integer[] a, int start, int end){
        //end represents the limit of how far down the heap to sift
        int root = start;

        while((root * 2 + 1) <= end){      //While the root has at least one child
            int child = root * 2 + 1;           //root*2+1 points to the left child
            //if the child has a sibling and the child's value is less than its sibling's...
            if(child + 1 <= end && a[child] < a[child + 1])
                child = child + 1;           //... then point to the right child instead
            if(a[root] < a[child]){     //out of max-heap order
                int tmp = a[root];
                a[root] = a[child];
                a[child] = tmp;
                root = child;                //repeat to continue sifting down the child now
            }else
                return;
        }
    }


    public static <E extends Comparable<? super E>> void insertionSort(List<E> a) {
        for (int i = 1; i < a.size(); i++) {
            int j = Math.abs(Collections.binarySearch(a.subList(0, i), a.get(i)) + 1);
            Collections.rotate(a.subList(j, i+1), j - i);
        }
    }



    public static <E extends Comparable<? super E>> List<E> mergeSort(List<E> m){
        if(m.size() <= 1) return m;

        int middle = m.size() / 2;
        List<E> left = m.subList(0, middle);
        List<E> right = m.subList(middle, m.size());

        right = mergeSort(right);
        left = mergeSort(left);
        List<E> result = merge(left, right);

        return result;
    }

    public static Integer[] selectionsort(Integer[] nums){
        for(int currentPlace = 0;currentPlace<nums.length-1;currentPlace++){
            int smallest = Integer.MAX_VALUE;
            int smallestAt = currentPlace+1;
            for(int check = currentPlace; check<nums.length;check++){
                if(nums[check]<smallest){
                    smallestAt = check;
                    smallest = nums[check];
                }
            }
            int temp = nums[currentPlace];
            nums[currentPlace] = nums[smallestAt];
            nums[smallestAt] = temp;
        }
        return nums;
    }

    private static <E extends Comparable<? super E>> List<E> merge(List<E> left, List<E> right){
        List<E> result = new ArrayList<>();
        Iterator<E> it1 = left.iterator();
        Iterator<E> it2 = right.iterator();

        E x = it1.next();
        E y = it2.next();
        while (true){
            //change the direction of this comparison to change the direction of the sort
            if(x.compareTo(y) <= 0){
                result.add(x);
                if(it1.hasNext()){
                    x = it1.next();
                }else{
                    result.add(y);
                    while(it2.hasNext()){
                        result.add(it2.next());
                    }
                    break;
                }
            }else{
                result.add(y);
                if(it2.hasNext()){
                    y = it2.next();
                }else{
                    result.add(x);
                    while (it1.hasNext()){
                        result.add(it1.next());
                    }
                    break;
                }
            }
        }
        return result;
    }


    public static void shellsort(Integer[] a) {
        int increment = a.length / 2;
        while (increment > 0) {
            for (int i = increment; i < a.length; i++) {
                int j = i;
                int temp = a[i];
                while (j >= increment && a[j - increment] > temp) {
                    a[j] = a[j - increment];
                    j = j - increment;
                }
                a[j] = temp;
            }
            if (increment == 2) {
                increment = 1;
            } else {
                increment *= (5.0 / 11);
            }
        }
    }


    public static <E extends Comparable<? super E>> List<E> quickSort(List<E> arr) {
        if (!arr.isEmpty()) {
            E pivot = arr.get(0); //This pivot can change to get faster results


            List<E> less = new LinkedList<>();
            List<E> pivotList = new LinkedList<E>();
            List<E> more = new LinkedList<E>();

            // Partition
            for (E i: arr) {
                if (i.compareTo(pivot) < 0)
                    less.add(i);
                else if (i.compareTo(pivot) > 0)
                    more.add(i);
                else
                    pivotList.add(i);
            }

            // Recursively sort sublists
            less = quickSort(less);
            more = quickSort(more);

            // Concatenate results
            less.addAll(pivotList);
            less.addAll(more);
            return less;
        }
        return arr;

    }

}
