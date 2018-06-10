package kz.sgq.colorassistant.ui.util.interfaces

interface RandomItems {
    fun resize(size: Int)
    fun delete(index: Int)
    fun clear()
    fun getNumbers(): IntArray
}