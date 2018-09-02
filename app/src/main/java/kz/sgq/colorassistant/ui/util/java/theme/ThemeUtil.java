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
                    return R.style.AppTheme_RED;
            case PINK:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_PINK;
                else
                    return R.style.AppTheme_PINK;
            case PURPLE:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_PURPLE;
                else
                    return R.style.AppTheme_PURPLE;
            case DEEP_PURPLE:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_DEEPPURPLE;
                else
                    return R.style.AppTheme_DEEPPURPLE;
            case INDIGO:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_INDIGO;
                else
                    return R.style.AppTheme_INDIGO;
            case BLUE:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_BLUE;
                else
                    return R.style.AppTheme_BLUE;
            case LIGHT_BLUE:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_LIGHTBLUE;
                else
                    return R.style.AppTheme_LIGHTBLUE;
            case CYAN:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_CYAN;
                else
                    return R.style.AppTheme_CYAN;
            case TEAL:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_TEAL;
                else
                    return R.style.AppTheme_TEAL;
            case GREEN:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_GREEN;
                else
                    return R.style.AppTheme_GREEN;
            case LIGHT_GREEN:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_LIGHTGREEN;
                else
                    return R.style.AppTheme_LIGHTGREEN;
            case LIME:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_LIME;
                else
                    return R.style.AppTheme_LIME;
            case YELLOW:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_YELLOW;
                else
                    return R.style.AppTheme_YELLOW;
            case AMBER:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_AMBER;
                else
                    return R.style.AppTheme_AMBER;
            case ORANGE:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_ORANGE;
                else
                    return R.style.AppTheme_ORANGE;
            case DEEP_ORANGE:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_DEEPORANGE;
                else
                    return R.style.AppTheme_DEEPORANGE;
            case BROWN:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_BROWN;
                else
                    return R.style.AppTheme_BROWN;
            case GRAY:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_GRAY;
                else
                    return R.style.AppTheme_GRAY;
            case BLUE_GRAY:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_BLUEGRAY;
                else
                    return R.style.AppTheme_BLUEGRAY;
            default:
                if (AppCompatDelegate.MODE_NIGHT_NO == modeTheme)
                    return R.style.AppTheme_RED;
                else
                    return R.style.AppTheme_RED;
        }
    }
}
