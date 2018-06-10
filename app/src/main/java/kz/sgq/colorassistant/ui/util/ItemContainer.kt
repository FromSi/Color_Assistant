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

    fun addItem(item: ItemColor){
        itemList.add(item)
        likeList.add(item.like)
        visiblyList.add(true)
    }

    fun deleteItem(id: Int){
        for (i in 0 until itemList.size)
            if (itemList[i].id == id){
                itemList.removeAt(i)
                likeList.removeAt(i)
                visiblyList.removeAt(i)
                return
            }
    }

    fun clearItems() {
        itemList.clear()
        likeList.clear()
        visiblyList.clear()
    }

    fun dislike(id: Int) {
        for (i in 0 until itemList.size)
            if (itemList[i].id == id) {
                likeList[i] = false
                return
            }
    }

    fun updateItems(index: Int) {
        itemList[index].like = false
    }
}