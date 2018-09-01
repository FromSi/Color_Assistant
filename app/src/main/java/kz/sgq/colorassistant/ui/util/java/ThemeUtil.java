package kz.sgq.colorassistant.ui.util.java;

import kz.sgq.colorassistant.R;

public class ThemeUtil {

    public static int getThemeId(ThemeEnum theme) {
        int themeId = 0;

        switch (theme) {
            case RED:
                themeId = R.style.AppTheme_RED;
                break;
            case PINK:
                themeId = R.style.AppTheme_PINK;
                break;
            case PURPLE:
                themeId = R.style.AppTheme_PURPLE;
                break;
            case DEEP_PURPLE:
                themeId = R.style.AppTheme_DEEPPURPLE;
                break;
            case INDIGO:
                themeId = R.style.AppTheme_INDIGO;
                break;
            case BLUE:
                themeId = R.style.AppTheme_BLUE;
                break;
            case LIGHT_BLUE:
                themeId = R.style.AppTheme_LIGHTBLUE;
                break;
            case CYAN:
                themeId = R.style.AppTheme_CYAN;
                break;
            case TEAL:
                themeId = R.style.AppTheme_TEAL;
                break;
            case GREEN:
                themeId = R.style.AppTheme_GREEN;
                break;
            case LIGHT_GREEN:
                themeId = R.style.AppTheme_LIGHTGREEN;
                break;
            case LIME:
                themeId = R.style.AppTheme_LIME;
                break;
            case YELLOW:
                themeId = R.style.AppTheme_YELLOW;
                break;
            case AMBER:
                themeId = R.style.AppTheme_AMBER;
                break;
            case ORANGE:
                themeId = R.style.AppTheme_ORANGE;
                break;
            case DEEP_ORANGE:
                themeId = R.style.AppTheme_DEEPORANGE;
                break;
            case BROWN:
                themeId = R.style.AppTheme_BROWN;
                break;
            case GRAY:
                themeId = R.style.AppTheme_GRAY;
                break;
            case BLUE_GRAY:
                themeId = R.style.AppTheme_BLUEGRAY;
                break;
            default:
                break;
        }
        return themeId;
    }
}
