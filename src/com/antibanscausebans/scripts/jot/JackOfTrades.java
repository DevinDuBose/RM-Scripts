package com.antibanscausebans.scripts.jot;

import java.io.IOException;

import com.antibanscausebans.scripts.jot.gui.JotGUIController;
import com.antibanscausebans.scripts.jot.handlers.SkillHandler;
import com.antibanscausebans.scripts.jot.handlers.TaskHandler;
import com.antibanscausebans.scripts.jot.skills.Woodcutting;
import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.Resources;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.LoopingBot;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class JackOfTrades extends LoopingBot implements EmbeddableUI {
	
	private ObjectProperty<Node> interfaceProperty;
	private TaskHandler task;
	private SkillHandler currentSkill;
	private boolean taskComplete;

	public JackOfTrades() {
		setEmbeddableUI(this);
	}

	@Override
	public ObjectProperty<? extends Node> botInterfaceProperty() {
		if (interfaceProperty == null) {
			FXMLLoader loader = new FXMLLoader();
			loader.setController(new JotGUIController(this));
			Node node;
			try {
				node = loader.load(Resources.getAsStream("com/antibanscausebans/jot/gui/GUI.fxml"));
				interfaceProperty = new SimpleObjectProperty<>(node);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return interfaceProperty;
	}

	@Override
	public void onLoop() {
		currentSkill  = task.getCurrentSkill();
		taskComplete = false;
		
		switch (task.getCurrentStage()) {
			case AGILITY:
				break;
			case COMBAT:
				break;
			case COOKING:
				break;
			case CRAFTING:
				break;
			case DIVINATION:
				break;
			case FARMING:
				break;
			case FIREMAKING:
				break;
			case FISHING:
				break;
			case FLETCHING:
				break;
			case HERBLORE:
				break;
			case HUNTER:
				break;
			case INVENTION:
				break;
			case MINING:
				break;
			case PRAYER:
				break;
			case RUNECRAFTING:
				break;
			case SMITHING:
				break;
			case THIEVING:
				break;
			case UNKNOWN:
				task.getNextTask();
				break;
			case WOODCUTTING:
				currentSkill = (Woodcutting) task.getCurrentSkill();
				if (currentSkill.checkCoordinates(currentSkill.getTaskLocation())) {
					taskComplete = currentSkill.performTask();
				} else {
					currentSkill.performPathingToLocation(task.getCurrentPath());
					Execution.delayWhile(() -> Players.getLocal().isMoving());
				}
				break;
			default:
				break;
		}
		
		if (taskComplete) {
			task.setTaskComplete(task.currentStage);
			task.getNextTask();
		}
	}
	
	@Override
	public void onStart(String... args) {
		task = new TaskHandler();
	}

}
