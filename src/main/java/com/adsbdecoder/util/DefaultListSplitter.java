package com.adsbdecoder.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of ListSplitter for splitting a list into sublists.
 */
public class DefaultListSplitter implements ListSplitter {

    /**
     * Splits a list into a specified number of sublists.
     *
     * @param <T>               The type of elements in the list.
     * @param list              The list to split.
     * @param numberOfSublists The number of sublists to create.
     * @return List of sublists.
     */
    @Override
    public <T> List<List<T>> splitList(List<T> list, int numberOfSublists) {
        List<List<T>> sublists = new ArrayList<>();
        int size = list.size() / numberOfSublists;
        int remainder = list.size() % numberOfSublists;
        int index = 0;
        for (int i = 0; i < numberOfSublists; i++) {
            int sublistSize = size + (i < remainder ? 1 : 0);
            sublists.add(list.subList(index, index + sublistSize));
            index += sublistSize;
        }
        return sublists;
    }
}
