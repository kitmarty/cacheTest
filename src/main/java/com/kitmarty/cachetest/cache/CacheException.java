package com.kitmarty.cachetest.cache;

/**
        * Customized exception for cache.
        */
class CacheException extends Exception{

    public CacheException() {
        super();
    }

    public CacheException(String message) {
        super(message);
    }
}
