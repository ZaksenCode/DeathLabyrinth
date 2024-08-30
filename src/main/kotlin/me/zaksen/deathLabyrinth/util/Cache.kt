package me.zaksen.deathLabyrinth.util

abstract class Cache<O, K, V> {
    abstract val cache: MutableMap<O, MutableMap<K, V>>

    open fun get(owner: O, key: K): V? {
        val currentCache = cache[owner] ?: initialize(owner)
        val currentValue = currentCache[key]
        return currentValue
    }

    open fun put(owner: O, key: K, value: V) {
        val currentCache = cache[owner] ?: initialize(owner)
        currentCache[key] = value
        cache[owner] = currentCache
    }

    open fun delete(owner: O) {
        cache.remove(owner)
    }

    protected open fun initialize(owner: O): MutableMap<K, V> {
        val newCache = mutableMapOf<K, V>()
        cache[owner] = newCache
        return newCache
    }
}