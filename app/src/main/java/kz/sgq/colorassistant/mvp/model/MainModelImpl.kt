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

package kz.sgq.colorassistant.mvp.model

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import kz.sgq.colorassistant.mvp.model.interfaces.MainModel
import kz.sgq.colorassistant.room.common.DataBaseRequest
import kz.sgq.colorassistant.room.table.Cloud

class MainModelImpl : MainModel {
    private var fragmentCurrent = MainFragment.GLOBAL
    private lateinit var fragment: Fragment

    enum class MainFragment { GLOBAL, LIKE, CLOUD }

    override fun getCurrentFragment(): MainFragment = fragmentCurrent

    override fun setCurrentFragment(fragmentCurrent: MainFragment) {
        this.fragmentCurrent = fragmentCurrent
    }

    override fun getFragment(): Fragment = fragment

    override fun setFragment(fragment: Fragment) {
        this.fragment = fragment
    }

    override fun calcLinkAnswer(data: String): Cloud {
        val text = if (data.substring(0, 5).equals("https"))
            data.substring(1)
        else
            data.substring(0)

        return Cloud(
                text.substring(44, 51),
                text.substring(56, 63),
                text.substring(70, 77)
        ).apply {

            if (text.length >= 90)
                colFour = text.substring(83, 90)

            if (text.length >= 103)
                colFive = text.substring(96, 103)
        }
    }

    override fun save(cloud: Cloud, eventListener: DataBaseRequest.OnEventListener) {

        DataBaseRequest.insertCloud(cloud, eventListener)
    }

    override fun calcQRCode(resultCode: Int, data: Intent?): Boolean {

        if (resultCode == Activity.RESULT_OK) {
            val size = data!!.getStringExtra("scan_result").length

            if ((size == 21) || (size == 28) || (size == 35))
                return true
        }

        return false
    }

    override fun calcQRAnswer(data: Intent?): Cloud {
        val scanResult = data!!.getStringExtra("scan_result")

        return Cloud(
                scanResult.substring(0, 7),
                scanResult.substring(7, 14),
                scanResult.substring(14, 21)
        ).apply {

            if (scanResult.length >= 28) {
                colFour = scanResult.substring(21, 28)

                if (scanResult.length >= 35)
                    colFive = scanResult.substring(28, 35)
            }
        }
    }
}