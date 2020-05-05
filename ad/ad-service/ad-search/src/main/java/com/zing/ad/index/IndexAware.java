package com.zing.ad.index;

/**
 * @author Zing
 * @date 2019-11-25
 */
public interface IndexAware<K, V> {

    V get(K key);

    void add(K key, V value);

    void update(K key, V value);

    void delete(K key, V value);

}
