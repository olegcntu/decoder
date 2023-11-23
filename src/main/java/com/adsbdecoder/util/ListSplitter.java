package com.adsbdecoder.util;

import java.util.List;

/**
 * Interface for splitting a list into sublists.
 */
public interface ListSplitter {

    /**
     * Splits a list into a specified number of sublists.
     *
     * @param <T>               The type of elements in the list.
     * @param list              The list to split.
     * @param numberOfSublists The number of sublists to create.
     * @return List of sublists.
     */
    <T> List<List<T>> splitList(List<T> list, int numberOfSublists);
}
