package com.pyskacz.shutter.controller;

import com.pyskacz.shutter.CMDCommand;
import com.pyskacz.shutter.model.NumericField;
import com.pyskacz.shutter.model.ProgressIndicator;
import com.pyskacz.shutter.utils.StringUtils;
import com.pyskacz.shutter.utils.TextContorlNotifier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MainSceneController {
	@FXML private Text        feedbackText;
	@FXML private RadioButton radio30;
	@FXML private RadioButton radio60;
	@FXML private RadioButton radioCustom;
	@FXML private TextField   fieldHours;
	@FXML private TextField   fieldMinutes;
	@FXML private Button      setButton;
	@FXML private ProgressBar progressBar;
	@FXML private Text        progressBarText;

	private NumericField numFieldHours   = new NumericField();
	private NumericField numFieldMinutes = new NumericField();

	private TextContorlNotifier notifier;

	private Map<RadioButton, Supplier<Integer>> minutesFromButtonsSupplierMap;

	private ProgressIndicator progressIndicator;

	@FXML
	public void initialize() {
		minutesFromButtonsSupplierMap = new HashMap<>();
		minutesFromButtonsSupplierMap.put(radio30, () -> 30);
		minutesFromButtonsSupplierMap.put(radio60, () -> 60);
		minutesFromButtonsSupplierMap.put(radioCustom, () -> {
			int minutes = 0;
			String hoursFromField = fieldHours.getText();
			String minutesFromField = fieldMinutes.getText();

			if (hoursFromField.length() > 0) {
				minutes += Integer.valueOf(hoursFromField) * 60;
			}

			if (minutesFromField.length() > 0) {
				minutes += Integer.valueOf(minutesFromField);
			}

			if (minutes <= 0) {
				throw new IllegalArgumentException("Musisz podać jakąś wartość, Perło!");
			} else {
				return minutes;
			}
		});

		notifier = new TextContorlNotifier(feedbackText);
		progressIndicator = new ProgressIndicator(progressBar, progressBarText);
	}

	@FXML protected void handleApplyButtonAction(ActionEvent event) {
		int minutes = 0;
		try {
			for (RadioButton button : minutesFromButtonsSupplierMap.keySet()) {
				if (button.isSelected()) {
					minutes = minutesFromButtonsSupplierMap.get(button).get();
				}
			}

			CMDCommand.execute("shutdown -s -t " + minutes * 60);
			feedbackText.setText("Ustawiłaś tajmer na " + formatMinutes(minutes));
			progressIndicator.start(minutes * 60);
			setButton.setDisable(true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			notifier.logError(e.getMessage());
		}
	}

	private String formatMinutes(int minutes) {
		int hours = minutes / 60;
		int minutesLeft = minutes % 60;
		return hours > 0 ? "" + hours + "h i " + minutesLeft + "min" : +minutesLeft + "min";

	}

	public void handleCancelButtonAction(ActionEvent actionEvent) {
		try {
			CMDCommand.execute("shutdown -a");
			notifier.logInfo("Wyłączyłaś tajmer");
			progressIndicator.stop();
			setButton.setDisable(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleKeyReleasedMinutes(KeyEvent keyEvent) {
		handleKeyTyped(keyEvent, numFieldMinutes, fieldMinutes);
	}

	public void handleKeyReleasedHours(KeyEvent keyEvent) {
		handleKeyTyped(keyEvent, numFieldHours, fieldHours);
	}

	private void handleKeyTyped(KeyEvent keyEvent, NumericField numField, TextField textField) {
		String fieldText = textField.getText();
		if (StringUtils.onlyDigitsRegex(fieldText) || fieldText.length() == 0) {
			radioCustom.setSelected(true);
			numField.setValue(textField.getText());
			notifier.logInfo("");
		} else {
			textField.setText(numField.getValue());
			textField.positionCaret(numField.getValue().length());
			notifier.logError("Tylko cyfry, Kocie!");
		}
	}
}
