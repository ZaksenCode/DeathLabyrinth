package me.zaksen.deathLabyrinth.menu.api

enum class MenuType(private val totalSize: Int) {
    BASE_9(9),
    BASE_18(18),
    BASE_27(27),
    BASE_36(36),
    BASE_45(45),
    BASE_54(54);

    fun size(): Int {
        return this.totalSize
    }

    companion object {

        fun ofSize(size: Int): MenuType {
            var result = BASE_9

            for(type in entries) {
                if(type.totalSize > size) {
                    continue
                }
                result = type
                break
            }

            return result
        }

    }
}