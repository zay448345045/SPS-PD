package com.hmdzl.spspd.actors.blobs.weather;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Dry;
import com.hmdzl.spspd.actors.buffs.Wet;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.BlobEmitter;
import com.hmdzl.spspd.effects.particles.RainParticle;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;

public class WeatherOfRain extends Blob {

    protected int pos;

	@Override
	protected void evolve() {
		
		int from = WIDTH + 1;
		int to = Level.LENGTH - WIDTH - 1;
		
		int[] map = Dungeon.level.map;
		
		for (int pos=from; pos < to; pos++) {
			if (cur[pos] > 0) {
				
				off[pos] = cur[pos];
				volume += off[pos];
				
			} else {
				off[pos] = 0;
			}
		}

		Hero hero = Dungeon.hero;
		if (hero.isAlive() && cur[hero.pos] > 0) {
			Buff.prolong( hero, Wet.class, Wet.DURATION );
			Buff.detach(hero,Dry.class);
		}
	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);
		emitter.start(RainParticle.FACTORY, 0.8f, 0);
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
	
}