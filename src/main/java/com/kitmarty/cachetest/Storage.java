package com.kitmarty.cachetest;

import com.kitmarty.cachetest.Cache;

/**
 * Abstract class for storage. Methods are common and some of them can be implemented right here, but particular implementation must be placed in child classes.
 */
//TODO understand internal structure of all base collections
public abstract class Storage implements Cache {
    /**
     * Checks whether the Storage is empty
     * @return true if the storage is empty, false if the storage contains 1 element at least
     */
    public boolean isEmpty(){
        return getSize()==0;
    }

    /**
     * Sets
     * @param size
     */
    public void setSize(int size){

    }

    public int getSize(){
        return 0;
    }
}