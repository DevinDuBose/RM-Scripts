package com.antibanscausebans.scripts.jot.handlers;

import java.util.HashSet;

import com.antibanscausebans.scripts.jot.handlers.SkillHandler;
import com.antibanscausebans.scripts.jot.skills.Woodcutting;
import com.runemate.game.api.hybrid.local.hud.interfaces.Equipment;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.definitions.ItemDefinition;
import com.runemate.game.api.hybrid.local.Skill;

public class TaskHandler {
	
	public enum State {
		UNKNOWN, COMBAT, PRAYER, COOKING, WOODCUTTING, FLETCHING, FISHING, FIREMAKING, CRAFTING, SMITHING, MINING, HERBLORE, AGILITY, THIEVING, FARMING, RUNECRAFTING, HUNTER, DIVINATION, INVENTION
	}
	
	private int tasksRequired;
	private HashSet<State> tasksCompleted;
	public State currentStage;
	public SkillHandler currentSkill;
	public RegionPath pathToCurrentTask;
	
	public TaskHandler() {
		tasksCompleted = null; 
		tasksRequired = getRequiredTaskQuantity();
		
		setCurrentStage(State.UNKNOWN);
		
		if (tasksRequired < 20) { //Only supporting regular (10) and master (15) skills at this time.
			if (currentStage == State.UNKNOWN) {
				if (getNextTask()) {
					//Go ahead and walk to the first task we will be performing.
					if (!currentSkill.checkCoordinates(currentSkill.getTaskLocation())) {
						currentSkill.performPathingToLocation(pathToCurrentTask);
					}
				} else { Environment.getBot().getLogger().info("Failed to get initial task."); }
			}
		} else { Environment.getBot().getLogger().info("This script only supports the 10/15 skill requirement auras."); }
	}
	
	public boolean setTaskComplete(State skill) {
		return tasksCompleted.add(skill);
	}
	
	public State getCurrentStage() {
		return currentStage;
	}
	
	public void setCurrentStage(State skill) {
		currentStage = skill;
	}
	
	public SkillHandler getCurrentSkill() {
		return currentSkill;
	}
	
	public void setCurrentSkill(SkillHandler skill) {
		currentSkill = skill;
	}
	
	public void setCurrentPath() {
		pathToCurrentTask = RegionPath.buildTo(currentSkill.getTaskLocation().getRandomCoordinate());
	}
	
	public RegionPath getCurrentPath() {
		return pathToCurrentTask;
	}
	
	public boolean getNextTask() {
		State state = null;
		
		while (state == null) {
			int randomSkill = (int)Random.nextGaussian(0, State.values().length);
			SkillHandler skillObject = canPerformTask(State.values()[randomSkill]);
			if (skillObject != null) {
				setCurrentStage(State.values()[randomSkill]);
				setCurrentSkill(skillObject);
				return true;
			}
		}
		return false;
	}
	
	private SkillHandler canPerformTask(State skill) {
		if (tasksCompleted.contains(skill)) {
			Environment.getBot().getLogger().info(skill.name().toString() + " has already been completed.");
			return null;
		}
		
		switch (skill) {
			case WOODCUTTING:
				Woodcutting task = new Woodcutting();
				if (task.checkRequirements(Skill.WOODCUTTING, 1)) {
					return task;
				} else { return null; }
//			case FIREMAKING:
//				Firemaking task = new Firemaking();
//				return tasksCompleted.contains(State.WOODCUTTING) && task.checkRequirements(Skill.FIREMAKING, 1);
//			case FLETCHING:
//				return tasksCompleted.contains(State.WOODCUTTING);
//			case PRAYER:
//				return tasksCompleted.contains(State.COMBAT);
//			case COOKING:
//				return tasksCompleted.contains(State.FISHING);
//			case SMITHING:
//				return tasksCompleted.contains(State.MINING);
			default:
				Environment.getBot().getLogger().info(skill.name().toString() + " validation has not yet been built.");
				return null;
		}
	}
	
	private int getRequiredTaskQuantity() {
		ItemDefinition aura = Players.getLocal().getEquipment().get(Equipment.Slot.AURA.getIndex());
		
		switch (aura.getName()) {
			case "Jack of Trades":
				return 10;
			case "Master jack of trades":
				return 15;
			case "Supreme Jack of Trades":
				return 20;
			case "Legendary Master jack of trades":
				return 25;
			default:
				Environment.getBot().getLogger().info("Player is not wearing an active Jack of Trades aura.");
				return 0;
		}
	}
	
}
