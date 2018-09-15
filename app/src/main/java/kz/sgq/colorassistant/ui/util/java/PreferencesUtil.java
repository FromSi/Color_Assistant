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

package kz.sgq.colorassistant.ui.util.java;

import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import kz.sgq.colorassistant.ui.util.java.theme.ThemeStyle;
import kz.sgq.colorassistant.ui.util.java.theme.ThemeUtil;

public class PreferencesUtil {

    public static int getThemeId(Context context) {
        return ThemeUtil.getThemeId(
                ThemeStyle.valueOf(
                        context.getSharedPreferences("settings", Context.MODE_PRIVATE)
                                .getString("theme_color", "RED")
                ),
                getNightMode(context)
        );
    }

    public static int getThemeDialogId(Context context) {
        return ThemeUtil.getThemeDialogId(
                ThemeStyle.valueOf(
                        context.getSharedPreferences("settings", Context.MODE_PRIVATE)
                                .getString("theme_color", "RED")
                ),
                getNightMode(context)
        );
    }

    public static int getNightMode(Context context) {
        return context.getSharedPreferences("settings", Context.MODE_PRIVATE)
                .getInt("night_mode", AppCompatDelegate.MODE_NIGHT_NO);
    }
}
