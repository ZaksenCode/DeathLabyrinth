package me.zaksen.deathLabyrinth.util

class WeightedRandomList<T> {
    inner class Entry {
        var weight: Double = 0.0
        var accumulatedWeight: Double = 0.0
        var `object`: T? = null
    }

    private val entries: MutableList<Entry> = ArrayList()
    private var accumulatedWeight: Double = 0.0

    fun addEntry(`object`: T, weight: Double) {
        accumulatedWeight += weight
        val e = Entry()
        e.`object` = `object`
        e.accumulatedWeight = accumulatedWeight
        e.weight = weight
        entries.add(e)
    }

    fun random(): T? {
        val r = Math.random() * accumulatedWeight

        for (entry in entries) {
            if (entry.accumulatedWeight >= r) {
                return entry.`object`
            }
        }

        return null
    }

    fun getEntries(): List<Entry> {
        return entries
    }
}