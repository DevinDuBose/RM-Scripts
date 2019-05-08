package com.antibanscausebans.jot.handlers;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.local.Skills;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.region.Players;

public interface SkillHandler {
	
	boolean checkEquipment();
	boolean performTask();
	Object checkTask();
	Area getTaskLocation();
	
	default boolean checkCoordinates(Area taskLocation) {
		Player player = Players.getLocal();
		
		if (player != null) {
			if (taskLocation.contains(player.getPosition())) {
				return true;
			}
		}
		
		Environment.getBot().getLogger().info("Player is not at the task location.");
		return false;
	}
	
	default boolean checkRequirements(Skill skill, int requiredLevel) {
		if ((skill.getCurrentLevel() >= requiredLevel) && checkEquipment()) {
			return true;
		}
		
		Environment.getBot().getLogger().info("Player does not meet the requirements to perform the current task.");
		return false;
	}
	
	
	default boolean performPathingToLocation(Area taskLocation) {
		RegionPath pathToTask = RegionPath.buildTo(taskLocation.getRandomCoordinate());
		
		if (pathToTask != null) {
			return pathToTask.step();
		}
		
		Environment.getBot().getLogger().info("Failed to build path to current task.");
		return false;
	}
	
}
