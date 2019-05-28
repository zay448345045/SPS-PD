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
package com.hmdzl.spspd.change.actors.mobs;

import java.util.ArrayList;
import java.util.HashSet;

import com.hmdzl.spspd.change.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.change.actors.buffs.AttackDown;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Locked;
import com.hmdzl.spspd.change.actors.buffs.Silent;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.items.TenguKey;
import com.hmdzl.spspd.change.items.armor.Armor;
import com.hmdzl.spspd.change.items.artifacts.DriedRose;
import com.hmdzl.spspd.change.items.artifacts.MasterThievesArmband;
import com.hmdzl.spspd.change.items.potions.PotionOfToxicGas;
import com.hmdzl.spspd.change.items.wands.WandOfFlow;
import com.hmdzl.spspd.change.items.wands.WandOfLight;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentLight;
import com.hmdzl.spspd.change.levels.PrisonBossLevel;
import com.hmdzl.spspd.change.levels.traps.PoisonTrap;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.TomeOfMastery;
import com.hmdzl.spspd.change.items.journalpages.Sokoban2;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfMagicMapping;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ErrorSprite;
import com.hmdzl.spspd.change.sprites.TankSprite;
import com.hmdzl.spspd.change.sprites.TenguSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Tank extends Mob {

	private static final int JUMP_DELAY = 20;

	{
		spriteClass = TankSprite.class;

		HP = HT = 1000;
		EXP = 40;
		evadeSkill = 5;
		viewDistance = 4;

		properties.add(Property.UNDEAD);
		properties.add(Property.BOSS);

		loot =  new DriedRose().identify();
		lootChance = 0.2f;

		lootOther = new PotionOfToxicGas();
		lootChanceOther = 1f; // by default, see die()

	}

	private int timeToJump = JUMP_DELAY;
	private boolean rock = true;
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(40, 65);
	}

	@Override
	public int hitSkill(Char target) {
		return 60;
	}

	@Override
	public int drRoll() {
		return 0;
	}

	@Override
	protected float attackDelay() {
		return 3f;
	}	

	@Override
	public void die(Object cause) {
		yell(Messages.get(this,"die"));
		
		GameScene.bossSlain();
		
		Badges.validateBossSlain();
	    
	    ((PrisonBossLevel) Dungeon.level).unseal();

	   
		Dungeon.level.drop(new TomeOfMastery(), pos).sprite.drop();	

		Dungeon.level.drop(new Sokoban2(), pos).sprite.drop();
		Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();		
		Dungeon.level.drop(new TenguKey(), pos).sprite.drop();
		
	    super.die(cause);
	}
	
	@Override
	public boolean act() {
		Dungeon.level.updateFieldOfView( this );
		timeToJump--;
        if (timeToJump == 0 ) {
			jump();
			rock=false;
		}
        if (!rock&& timeToJump > 5 && timeToJump < 15 && state == HUNTING &&
				paralysed <= 0 &&
				enemy != null &&
				enemy.invisible == 0 &&
				Level.fieldOfView[enemy.pos] &&
				Level.distance( pos, enemy.pos ) < 5 && !Level.adjacent( pos, enemy.pos ) &&
				Random.Int(3) == 0 &&  HP > 0 ) {
			rockattack();
		}

		return super.act();
	}	

	@Override
	public void move(int step) {
		super.move(step);
		
		int[] cells = { step - 1, step + 1, step - Level.getWidth(),
				step + Level.getWidth(), step - 1 - Level.getWidth(),
				step - 1 + Level.getWidth(), step + 1 - Level.getWidth(),
				step + 1 + Level.getWidth() };
		int cell = cells[Random.Int(cells.length)];

		if (Dungeon.visible[cell]) {
			CellEmitter.get(cell).start(Speck.factory(Speck.ROCK), 0.07f, 10);
			Camera.main.shake(3, 0.7f);
			Sample.INSTANCE.play(Assets.SND_ROCKS);
		}

		Char ch = Actor.findChar(cell);
		if (ch != null && ch != this) {
			Buff.prolong(ch, Vertigo.class, 2f);
		}
	}	
	
	@Override
	public int attackProc(Char enemy, int damage) {
		int oppositeDefender = enemy.pos + (enemy.pos - pos);
		Ballistica trajectory = new Ballistica(enemy.pos, oppositeDefender, Ballistica.MAGIC_BOLT);
		WandOfFlow.throwChar(enemy, trajectory, 1);
        Buff.affect(enemy, Vertigo.class,3f);
		return damage;
	}

	private void jump() {
		timeToJump = JUMP_DELAY;
		int heropos = Dungeon.hero.pos;
		ArrayList<Integer> candidates = new ArrayList<Integer>();
		boolean[] passable = Level.passable;
		int[] heroneighbours = {heropos - 1, heropos + 1, heropos - Level.getWidth(),
				heropos + Level.getWidth(), heropos - 1 - Level.getWidth(),
				heropos - 1 + Level.getWidth(), heropos + 1 - Level.getWidth(),
				heropos + 1 + Level.getWidth()};
		for (int n : heroneighbours) {
			if (passable[n] && Actor.findChar(n) == null) {
				candidates.add(n);
			}

			if (candidates.size() > 0) {
				int newPos;
				newPos = Random.element(candidates);
				sprite.move(pos, newPos);
				move(newPos);


				for (int n2 : Level.NEIGHBOURS8) {
					int blockpos = newPos + n2;
					if (blockpos > 0 && Dungeon.level.map[blockpos] == Terrain.WALL) {
						Level.set(blockpos, Terrain.EMBERS);
						GameScene.updateMap(blockpos);
					}
				}

			}
		}
		spend(2f);
	}

	private void rockattack() {
		rock = true;
		int heropos = Dungeon.hero.pos;
		ArrayList<Integer> candidates = new ArrayList<Integer>();
		boolean[] passable = Level.passable;

			int[] heroneighbours2 = { heropos + 1, heropos - 1, heropos + Level.getWidth(),
                    heropos - Level.getWidth() };
			for (int n : heroneighbours2) {
				if (passable[n] && Actor.findChar(n) == null) {
					candidates.add(n);
				}
			}
			if (candidates.size() > 0) {
			    int rockpos;
			rockpos = Random.element(candidates);
			
			Level.set(rockpos, Terrain.WALL);
		     GameScene.updateMap(rockpos);

                for (int n : Level.NEIGHBOURS8) {
                    Char ch = Actor.findChar(n+rockpos);
                    if (ch != null && ch.isAlive()) {
                        ch.damage(15,this);
                    }
                }
			}

			GLog.n(Messages.get(Tank.class,"rock"));
	}

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this, "notice"));
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	private static final HashSet<Class<?>> WEAKNESS = new HashSet<Class<?>>();
	static {
		WEAKNESS.add(WandOfLight.class);
		WEAKNESS.add(EnchantmentLight.class);

		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Poison.class);
		RESISTANCES.add(EnchantmentDark.class);
		
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	@Override
	public HashSet<Class<?>> weakness() {
		return WEAKNESS;
	}
}
