package com.antibanscausebans.jot;

import java.io.IOException;

import com.antibanscausebans.jot.gui.JotGUIController;
import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.runemate.game.api.hybrid.util.Resources;
import com.runemate.game.api.script.framework.LoopingBot;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class JackOfTrades extends LoopingBot implements EmbeddableUI {
	
	private ObjectProperty<Node> interfaceProperty;

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
		
	}
	
	@Override
	public void onStart(String... args) {

	}

}
