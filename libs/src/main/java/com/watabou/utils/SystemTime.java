/*
<<<<<<< HEAD:src/com/watabou/noosa/Resizable.java
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2017 Evan Debenham
=======
 * Copyright (C) 2012-2015 Oleg Dolya
>>>>>>> adb979673be899d5ff36c06d8074ae3692b3ebdd:libs/src/main/java/com/watabou/utils/SystemTime.java
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

<<<<<<< HEAD:src/com/watabou/noosa/Resizable.java
package com.watabou.noosa;

public interface Resizable {

	public void size( float width, float height );
	public float width();
	public float height();
	
=======
package com.watabou.utils;

public class SystemTime {

	public static long now;
	
	public static void tick() {
		now = System.currentTimeMillis();
	}
>>>>>>> adb979673be899d5ff36c06d8074ae3692b3ebdd:libs/src/main/java/com/watabou/utils/SystemTime.java
}

