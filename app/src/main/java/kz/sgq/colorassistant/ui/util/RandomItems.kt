/*
 * Copyright 2018 Vlad Weber-Pflaumer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kz.sgq.colorassistant.ui.util

import java.util.*

class RandomItems(private var limit: Int) {
    private var random: Random = Random(System.currentTimeMillis())
    private var listCheck: MutableList<Boolean> = arrayListOf()

    fun onNumbers(): IntArray {
        var freeNum = 0

        for (i in 0 until limit)
            listCheck.add(true)

        for (i in 0 until limit)
            if (listCheck[i])
                freeNum++

        return if (freeNum >= 20)
            calc(20)
        else
            calc(freeNum)
    }

    private fun calc(num: Int): IntArray {
        val number = IntArray(num)

        if (num != 0)
            for (i in 0 until num)
                number[i] = refresh(random.nextInt(limit))

        return number
    }

    private fun refresh(randNum: Int): Int {
        return when {
            listCheck[randNum] -> {
                listCheck[randNum] = false

                randNum
            }
            randNum + 1 <= limit - 1 -> refresh(randNum + 1)
            else -> refresh(0)
        }
    }
}