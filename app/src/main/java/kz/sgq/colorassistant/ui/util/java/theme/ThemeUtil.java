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

import kz.sgq.colorassistant.R;

public class ThemeUtil {

    public static int getThemeId(ThemeStyle theme, int modeTheme) {

        switch (theme) {
            case RED:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_RED;
                else
                    return R.style.AppTheme_RED_Night;
            case PINK:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_PINK;
                else
                    return R.style.AppTheme_PINK_Night;
            case PURPLE:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_PURPLE;
                else
                    return R.style.AppTheme_PURPLE_Night;
            case DEEP_PURPLE:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_DEEPPURPLE;
                else
                    return R.style.AppTheme_DEEPPURPLE_Night;
            case INDIGO:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_INDIGO;
                else
                    return R.style.AppTheme_INDIGO_Night;
            case BLUE:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_BLUE;
                else
                    return R.style.AppTheme_BLUE_Night;
            case LIGHT_BLUE:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_LIGHTBLUE;
                else
                    return R.style.AppTheme_LIGHTBLUE_Night;
            case CYAN:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_CYAN;
                else
                    return R.style.AppTheme_CYAN_Night;
            case TEAL:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_TEAL;
                else
                    return R.style.AppTheme_TEAL_Night;
            case GREEN:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_GREEN;
                else
                    return R.style.AppTheme_GREEN_Night;
            case LIGHT_GREEN:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_LIGHTGREEN;
                else
                    return R.style.AppTheme_LIGHTGREEN_Night;
            case LIME:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_LIME;
                else
                    return R.style.AppTheme_LIME_Night;
            case YELLOW:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_YELLOW;
                else
                    return R.style.AppTheme_YELLOW_Night;
            case AMBER:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_AMBER;
                else
                    return R.style.AppTheme_AMBER_Night;
            case ORANGE:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_ORANGE;
                else
                    return R.style.AppTheme_ORANGE_Night;
            case DEEP_ORANGE:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_DEEPORANGE;
                else
                    return R.style.AppTheme_DEEPORANGE_Night;
            case BROWN:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_BROWN;
                else
                    return R.style.AppTheme_BROWN_Night;
            case GRAY:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_GRAY;
                else
                    return R.style.AppTheme_GRAY_Night;
            case BLUE_GRAY:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_BLUEGRAY;
                else
                    return R.style.AppTheme_BLUEGRAY_Night;
            default:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_RED;
                else
                    return R.style.AppTheme_RED_Night;
        }
    }
}
