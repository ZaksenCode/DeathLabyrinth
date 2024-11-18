package me.zaksen.deathLabyrinth.util

fun String.stringList(splitter: String = "|split|"): List<String> {
    return this.split(splitter)
}

fun List<String>.string(splitter: String = "|split|"): String {
    val builder = StringBuilder()
    val iterator = this.iterator()

    while(iterator.hasNext()) {
        val element = iterator.next()
        builder.append(element)

        if(iterator.hasNext()) {
            builder.append(splitter)
        }
    }

    return builder.toString()
}

fun Set<String>.string(splitter: String = "|split|"): String {
    val builder = StringBuilder()
    val iterator = this.iterator()

    while(iterator.hasNext()) {
        val element = iterator.next()
        builder.append(element)

        if(iterator.hasNext()) {
            builder.append(splitter)
        }
    }

    return builder.toString()
}