package com.pyskacz.shutter.utils;

import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class TextContorlNotifier {

	private Text text;

	public TextContorlNotifier(Text text) {
		this.text = text;
	}

	public void logInfo(String message){
		log(message, Paint.valueOf("BLACK"));
	}

	public void logError(String message){
		log(message, Paint.valueOf("RED"));
	}

	private void log(String message, Paint paint) {
		text.setFill(paint);
		text.setText(message);
	}
}
