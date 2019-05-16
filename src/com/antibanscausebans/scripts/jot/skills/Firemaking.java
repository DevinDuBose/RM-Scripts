package com.antibanscausebans.scripts.jot.skills;

import com.antibanscausebans.scripts.jot.handlers.SkillHandler;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.location.Area;

public class Firemaking implements SkillHandler {

	private int logsId = 1511;
	
	@Override
	public boolean checkItems() {
		if (Inventory.contains(logsId)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean performTask() {
		if (checkItems()) {
			SpriteItem logs = Inventory.newQuery().names("logs").results().first();
			if (logs != null) {
				logs.interact("light");
				return true;
			}
		}
		return false;
	}

	@Override
	public Object checkTask() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Area getTaskLocation() {
		// TODO Auto-generated method stub
		return null;
	}

}
