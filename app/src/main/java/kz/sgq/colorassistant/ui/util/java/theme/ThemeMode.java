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

package kz.sgq.colorassistant.ui.util.java.theme;

import android.support.v7.app.AppCompatDelegate;

public enum ThemeMode {
    DAY(0),
    NIGHT(1),
    AUTOMATIC(2);

    private int id;

    ThemeMode(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static int getId(int i) {

        switch (i) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                return 0;
            case AppCompatDelegate.MODE_NIGHT_YES:
                return 1;
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                return 2;
            default:
                return 0;
        }
    }
}
