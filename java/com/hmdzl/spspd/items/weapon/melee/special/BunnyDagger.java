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
package com.hmdzl.spspd.items.weapon.melee.special;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HTimprove;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.items.GreatRune;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.Torch;
import com.hmdzl.spspd.items.medicine.Greaterpill;
import com.hmdzl.spspd.items.misc.GunOfSoldier;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BunnyDagger extends MeleeWeapon {
	{
		
		image = ItemSpriteSheet.DAGGER;
		usesTargeting = true;
	}

	public BunnyDagger() {
		super(1, 1.2f, 1f, 2);
		MIN = 5;
		MAX = 10;
		unique = true;
		reinforced = true;
		cursed = true;
	}

	@Override
	public Item uncurse(){
		//cursed=false;
		return this;
	}		
	
	@Override
	public Item upgrade(boolean enchant) {
		MIN += 1;
		MAX += 1;
		return super.upgrade(enchant);
	}

	
	@Override
	public void proc(Char attacker, Char defender, int damage) {
        int exdmg = Dungeon.hero.damageRoll();
		defender.damage(Random.Int(exdmg/2,exdmg), this);

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);
		}
	
	}

}
