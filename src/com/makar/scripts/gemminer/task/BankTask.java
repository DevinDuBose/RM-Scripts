package com.makar.scripts.gemminer.task;

import com.makar.util.Util;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Equipment;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.sun.glass.events.KeyEvent;

public class BankTask extends Task {
	
	private static final Area BANK_AREA = Area.rectangular(new Coordinate(3438, 3691, 0), new Coordinate(3459, 3727, 0));
	private static final Coordinate BANK_TILE = new Coordinate(3449, 3720, 0);
	
	@Override
	public boolean validate() {
		return Inventory.isFull();
	}
	
	@Override
	public void execute() {
		if (!BANK_AREA.contains(Players.getLocal())) {
			SpriteItem ring = Equipment.getItems("Ring of kinship").first();
			if (ring != null && ring.interact("Teleport to Damonheim")) {
				Execution.delayWhile(() -> Players.getLocal().getAnimationId() != -1, 15000, 20000);
			}
		} else {
			Npc banker = Npcs.newQuery().names("Fremennik banker").results().first();
			if (banker != null && banker.interact("Bank")) {
				Execution.delayUntil(() -> Bank.isOpen(), 3000, 7000);
				Bank.depositInventory();
				Execution.delayUntil(() -> Inventory.isEmpty(), 3200, 5600);
				if (Util.gaussian(0, 100, 75) < 30)
					Keyboard.pressKey(KeyEvent.VK_ESCAPE);
				else
					Bank.close();
			} else {
				RegionPath path = RegionPath.buildTo(BANK_TILE);
				if (path != null) {
					path.step();
				}
			}
		}
	}
}
