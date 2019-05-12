package com.antibanscausebans.scripts.jot.gui;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.antibanscausebans.scripts.jot.JackOfTrades;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;


public class JotGUIController implements Initializable {

	private JackOfTrades script;

	@FXML
	private TableView<Map.Entry<String, String>> fishTable;

	public JotGUIController(JackOfTrades script) {
		this.script = script;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		update();
	}
	
	public void update() {

	}
}
