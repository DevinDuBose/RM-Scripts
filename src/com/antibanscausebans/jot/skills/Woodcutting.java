package com.antibanscausebans.jot.skills;

import com.antibanscausebans.jot.handlers.SkillHandler;
import com.makar.util.Interact;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;

public class Woodcutting implements SkillHandler {
	
	public Area taskLocation = new Area.Rectangular(new Coordinate(0,1,2), new Coordinate(3,4,5));
	private int logs = 1511;
	
	@Override
	public Object checkTask() {
		GameObject tree = GameObjects.newQuery().names("Tree").results().nearest();
		if (tree != null) {
			if (tree.getPosition().isReachable()) {	
				return tree;
			}
		}
		return null;
	}

	@Override
	public boolean performTask() {
		GameObject tree = (GameObject) checkTask();

		if (tree != null) {
			if (Interact.walkOrTurnTo(tree, "Chop", 70)) {
				Execution.delayWhile(() -> Players.getLocal().getAnimationId() != -1 && !Inventory.contains(logs));
				return true;
			}
		}
		
		Environment.getBot().getLogger().info("Could not turn to and cut nearest tree, pathing to default location.");
		return false;
	}

	@Override
	public boolean checkItems() {
		Environment.getBot().getLogger().info("Woodcutting does not have any item requirements, a hatchet is on the toolbelt.");
		return true;
	}

	@Override
	public Area getTaskLocation() {
		return taskLocation;
	}
	
}
