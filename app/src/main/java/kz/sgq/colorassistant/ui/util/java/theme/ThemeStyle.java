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

public enum ThemeStyle {
    RED(0),
    PINK(1),
    PURPLE(2),
    DEEP_PURPLE(3),
    INDIGO(4),
    BLUE(5),
    LIGHT_BLUE(6),
    CYAN(7),
    TEAL(8),
    GREEN(9),
    LIGHT_GREEN(10),
    LIME(11),
    YELLOW(12),
    AMBER(13),
    ORANGE(14),
    DEEP_ORANGE(15),
    BROWN(16),
    GRAY(17),
    BLUE_GRAY(18);

    private int id;

    ThemeStyle(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
