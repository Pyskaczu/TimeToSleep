package com.pyskacz.shutter.model;

import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public class ProgressIndicator {
	private ProgressBar progressBar;
	private Text        overlayText;
	private int         initialSeconds;
	private int         currentSeconds;

	private Timer timer;

	public ProgressIndicator(ProgressBar progressBar, Text overlayText) {
		this.progressBar = progressBar;
		this.overlayText = overlayText;
	}

	public void start(int seconds) {
		progressBar.setProgress(1.0);
		initialSeconds = seconds;
		currentSeconds = initialSeconds;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override public void run() {

				progressBar.setProgress((double)currentSeconds / initialSeconds);
				int seconds = currentSeconds % 60;
				int minutes = (currentSeconds / 60 ) % 60;
				int hours = currentSeconds / 60 / 60 ;

				StringBuffer sb = new StringBuffer();
				sb.append(seconds).append("sec");
				if (hours > 0 || minutes > 0) {
					sb.insert(0, minutes + "min " );
				}

				if (hours > 0) {
					sb.insert(0, hours + "h ");
				}

				overlayText.setText(sb.toString());
				currentSeconds--;
			}
		}, 0, 1000);
	}

	public void stop() {
		if (timer != null) {
			timer.cancel();
		}
		progressBar.setProgress(0.0);
		overlayText.setText("");
	}
}
