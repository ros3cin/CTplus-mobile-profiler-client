package cin.ufpe.br.energyprofiler.modes.selectors.implementations.sorting;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cin.ufpe.br.energyprofiler.Options;
import cin.ufpe.br.energyprofiler.enums.EnumSorts;
import cin.ufpe.br.energyprofiler.enums.exceptions.ActionNotImplementedException;
import cin.ufpe.br.energyprofiler.modes.SortingMode;

/**
 * Created by welli on 04-Dec-17.
 */

public class SortingList {

    List<Integer> collectionList;

    public SortingList(List collectionList){
        this.collectionList = collectionList;
    }

    public List<Integer> sort(EnumSorts action) throws ActionNotImplementedException {

        Integer[] array = Arrays.copyOf(collectionList.toArray(), collectionList.toArray().length, Integer[].class);

        switch (action){
            case DEFAULT_SORT:
                Collections.sort(collectionList);
                break;
            case INSERT_SORT:
                SortingImpl.insertionSort(collectionList);
                break;
            case MERGE_SORT:
                collectionList = SortingImpl.mergeSort(collectionList);
                break;
            case QUICK_SORT:
                collectionList = SortingImpl.quickSort(collectionList);
                break;
            case SELECTION_SORT:
                SortingImpl.selectionsort(array);
                break;
            case HEAP_SORT:
                SortingImpl.heapSort(array);
                break;
            case BUBBLE_SORT:
                SortingImpl.bubbleSort(array);
                break;
            case SHELL_SORT:
                SortingImpl.shellsort(array);
                break;
            case SHAKE_SORT:
                //not implemented
                break;
            case NOT_FOUND:
        }

        return collectionList;
    }

    public void fillRandom(int max, int min){

        int index = Options.getIndex();
        int limit = Options.getLimit();
        Random generator = new Random(Options.seed);
        for (; index <limit ; index++){
            collectionList.add(
                    collectionList.size(),
                    (generator.nextInt() * (max - min) + min));
        }
    }

    public void removeAll() {
        collectionList.clear();
    }
}
