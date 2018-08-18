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

package kz.sgq.colorassistant.mvp.model.interfaces

import android.content.Intent
import android.support.v4.app.Fragment
import kz.sgq.colorassistant.mvp.model.MainModelImpl
import kz.sgq.colorassistant.room.common.DataBaseRequest
import kz.sgq.colorassistant.room.table.Cloud

interface MainModel {

    fun getCurrentFragment(): MainModelImpl.MainFragment

    fun getFragment(): Fragment

    fun setFragment(fragment: Fragment)

    fun setCurrentFragment(fragmentCurrent: MainModelImpl.MainFragment)

    fun save(cloud: Cloud, eventListener: DataBaseRequest.OnEventListener)

    fun calcQRAnswer(data: Intent?): Cloud

    fun calcQRCode(resultCode: Int, data: Intent?): Boolean
}