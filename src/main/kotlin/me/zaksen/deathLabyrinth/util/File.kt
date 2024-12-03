package me.zaksen.deathLabyrinth.util

import java.io.File

fun File.loadDirectoryFilesNames(): MutableSet<String> {
    if(!this.exists()) {
        this.mkdirs()
    }

    val result = mutableSetOf<String>()
    val files = this.listFiles() ?: return mutableSetOf()

    for(file in files) {
        if(file.isDirectory) {
            return file.loadDirectoryFilesNames()
        }

        result.add(file.name)
    }

    return result
}

fun File.loadDirectoryFiles(): MutableSet<File> {
    if(!this.exists()) {
        this.mkdirs()
    }

    val result = mutableSetOf<File>()
    val files = this.listFiles() ?: return mutableSetOf()

    for(file in files) {
        if(file.isDirectory) {
            return file.loadDirectoryFiles()
        }

        result.add(file)
    }

    return result
}