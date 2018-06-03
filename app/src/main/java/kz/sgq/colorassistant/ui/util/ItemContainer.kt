package kz.sgq.colorassistant.ui.util

data class ItemContainer(
        var itemList: MutableList<ItemColor> = arrayListOf(),
        var likeList: MutableList<Boolean> = arrayListOf(),
        var visiblyList: MutableList<Boolean> = arrayListOf()
) {
    fun addItems(itemList: MutableList<ItemColor>) {
        this.itemList.addAll(itemList)

        for (i in 0 until itemList.size) {
            likeList.add(itemList[i].like)
            visiblyList.add(true)
        }
    }

    fun clearItems() {
        itemList.clear()
        likeList.clear()
        visiblyList.clear()
    }

    fun setLike(index: Int, like: Boolean) {
        for (i in 0 until itemList.size)
            if (itemList[i].id == index) {
                likeList[i] = like
                return
            }
    }

    fun updateItems(index: Int) {
        itemList[index].like = false
    }
}