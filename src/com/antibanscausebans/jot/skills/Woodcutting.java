package com.antibanscausebans.jot.skills;

import com.antibanscausebans.jot.handlers.SkillHandler;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.GameObjects;

public class Woodcutting implements SkillHandler {
	
	public Area taskLocation = new Area.Rectangular(new Coordinate(0,1,2), new Coordinate(3,4,5));
	
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
		
		return false;
	}

	@Override
	public boolean checkEquipment() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Area getTaskLocation() {
		return taskLocation;
	}
	
	

}
