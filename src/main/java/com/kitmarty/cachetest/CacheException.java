package com.kitmarty.cachetest;

/**
 * Customized exception for cache.
 */
class CacheException extends Exception{

    public CacheException() {
    }

    public CacheException(String message) {
        super(message);
    }
}
