package kz.sgq.colorassistant.ui.util

import kz.sgq.colorassistant.ui.util.interfaces.RandomItems
import java.util.*

class RandomItemsImpl(private var limit: Int) : RandomItems{
    private var random: Random = Random(System.currentTimeMillis())
    private var listCheck: MutableList<Boolean> = arrayListOf()

    init {
        initCheckList()
    }

    override fun resize(size: Int) {
        limit += size
        initCheckList()
    }

    override fun getNumbers(): IntArray {
        var freeNum = 0
        for (i in 0 until limit) {
            if (listCheck[i])
                freeNum++
        }
        return if (freeNum >= 20) {
            calc(20)
        } else {
            calc(freeNum)
        }
    }

    private fun initCheckList() {
        val size = listCheck.size
        if (size != 0)
            for (i in size - 1 until limit)
                listCheck.add(true)
        else
            for (i in 0 until limit)
                listCheck.add(true)
    }

    private fun calc(num: Int): IntArray {
        val number = IntArray(num)
        if (num != 0)
            for (i in 0 until num)
                number[i] = refresh(random.nextInt(limit))
        return number
    }

    private fun refresh(randNum: Int): Int {
        if (listCheck[randNum]) {
            listCheck[randNum] = false
            return randNum
        } else {
            return if (randNum + 1 <= limit - 1)
                refresh(randNum + 1)
            else
                refresh(0)
        }
    }

}