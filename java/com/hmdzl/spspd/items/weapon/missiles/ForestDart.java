/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
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
package com.hmdzl.spspd.items.weapon.missiles;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class ForestDart extends MissileWeapon {

	{
		//name = "lucky throwing knive";
		image = ItemSpriteSheet.KNIVE;

		MIN = 6;
		MAX = 14;

		  // Finding them in bones would be semi-frequent and
						// disappointing.
	}

	public ForestDart() {
		this(1);
	}

	public ForestDart(int number) {
		super();
		quantity = number;
	}
	
	@Override
	public void proc(Char attacker, Char defender, int damage) {
		
		
       if (    defender.properties().contains(Char.Property.BOSS)
    		|| defender.properties().contains(Char.Property.HUMAN)
    		|| defender.properties().contains(Char.Property.ORC)
    		|| defender.properties().contains(Char.Property.BEAST)
    		|| defender.properties().contains(Char.Property.PLANT)
			|| defender.properties().contains(Char.Property.FISHER)
    		){
    	   defender.damage(Random.Int(damage*5,damage*8), this);
       } else {
    	   defender.damage(Random.Int(damage,damage*2), this); 
       }


	}


	@Override
	public Item random() {
		quantity = Random.Int(5, 15);
		return this;
	}

	@Override
	public int price() {
		return quantity * 2;
	}
	
	private static final Glowing GREEN = new Glowing(0x00FF00);
	
	@Override
	public Glowing glowing() {
		return GREEN;
	}
}
