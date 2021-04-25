package sample;

import javafx.scene.control.TextInputDialog;

public class InputPrompt {

	private double defaultResult;
	private TextInputDialog td;

	//Input dialog wrapper with some validation
	public InputPrompt(double defaultResult, String defaultValue, String header) {
		this.defaultResult = defaultResult;
		this.td = new TextInputDialog(defaultValue);
		this.td.setHeaderText(header);
	}

	public double showAndReturn() {
		String rawRes = td.showAndWait().map(r -> {
			try {
				Double n = Double.valueOf(r);
				return n.toString();
			}
			catch(Exception err) {
				return Double.toString(defaultResult);
			}
		}).orElse(Double.toString(defaultResult));

		return Double.parseDouble(rawRes);
	}

}
