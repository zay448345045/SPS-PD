/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2017 Evan Debenham
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

<<<<<<< HEAD:src/com/watabou/noosa/tweeners/CameraScrollTweener.java
package com.watabou.noosa.tweeners;

import com.watabou.noosa.Camera;
import com.watabou.utils.PointF;

public class CameraScrollTweener extends Tweener {

	public Camera camera;
	
	public PointF start;
	public PointF end;
	
	public CameraScrollTweener( Camera camera, PointF pos, float time ) {
		super( camera, time );
		
		this.camera = camera;
		start = camera.scroll;
		end = pos;
	}

	@Override
	protected void updateValues( float progress ) {
		camera.scroll = PointF.inter( start, end, progress );
=======
package com.watabou.glwrap;

import android.opengl.GLES20;

import javax.microedition.khronos.opengles.GL10;

public class Blending {
	
	public static void useDefault(){
		enable();
		setNormalMode();
	}
	
	public static void enable(){
		GLES20.glEnable( GL10.GL_BLEND );
	}
	
	public static void disable(){
		GLES20.glDisable( GL10.GL_BLEND );
	}
	
	//in this mode colors overwrite eachother, based on alpha value
	public static void setNormalMode(){
		GLES20.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
	}
	
	//in this mode colors add to eachother, eventually reaching pure white
	public static void setLightMode(){
		GLES20.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE );
>>>>>>> adb979673be899d5ff36c06d8074ae3692b3ebdd:libs/src/main/java/com/watabou/glwrap/Blending.java
	}
	
}
