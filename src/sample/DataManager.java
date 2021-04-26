package sample;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileWriter;
import java.util.Comparator;
import java.util.Scanner;

public class DataManager {

	public static void saveToFile(Vector[] data) {
		try {
			FileChooser fc = new FileChooser();
			fc.setTitle("Save vector graph");
			fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Data file", "*.dat"));
			File file = fc.showSaveDialog(Main.getStage());
			FileWriter writer = new FileWriter(file);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < data.length; i++) {
				sb.append(data[i].toString() + "\t" + data[i].getColor().toString() + "\n");
			}
			writer.write(sb.toString());
			writer.close();
			Alert a = new Alert(Alert.AlertType.INFORMATION);
			a.setTitle("Success!");
			a.setHeaderText("File saved successfully");
			a.setContentText("Saved under: " + file.getAbsolutePath());
			a.show();
		}
		catch(Exception err) {
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setTitle("File error!");
			a.setHeaderText("There's been an error");
			a.setContentText(err.getMessage());
			a.show();
		}

	}

	public static Stack<Vector> loadFile() {
		try {
			FileChooser fc = new FileChooser();
			fc.setTitle("Save vector graph");
			Stack<Vector> result = new Stack<>();
			fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Data file", "*.dat"));
			File file = fc.showOpenDialog(Main.getStage());
			Scanner reader = new Scanner(file);
			while(reader.hasNextLine()) {
				Vector2 v = Vector2.parse(reader.nextLine());
				result.PushBack(v);
			}
			return result;
		}
		catch(Exception err) {
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setTitle("Error!");
			a.setHeaderText("There's been an error");
			a.setContentText(err.getMessage());
			a.show();
			return null;
		}
	}

	public static<T> int interpolationSearch(T[] arr, int lo, int hi, T x, Comparator comparator) {
		int pos;
		int loDiff = comparator.compare(x, arr[lo]);
		int hiDiff = comparator.compare(x, arr[hi]);
		if (lo <= hi && loDiff >= 0 && hiDiff <= 0) {
			int den = comparator.compare(arr[hi], arr[lo]);
			if (den == 0)
				return lo;
			pos = lo + (((hi - lo) / den) * (loDiff));

			int diff = comparator.compare(arr[pos], x);
			if (diff < 0)
				return interpolationSearch(arr, pos + 1, hi, x, comparator);

			if (diff > 0)
				return interpolationSearch(arr, lo, pos - 1, x, comparator);
			return pos;
		}
		return -1;
	}

	//Class to manage files, reading, writing, and sorting algorithms
	public static<T> void insertionSort(T[] arr, Comparator comparator) {
		for (int j = 1; j < arr.length; j++) {
			for (int i = j; i >= 0; i--) {
				if (i == 0)
					continue;
				int diff = comparator.compare(arr[i], arr[i - 1]);
				if (diff >= 0)
					break;
				T temp = arr[i];
				arr[i] = arr[i - 1];
				arr[i - 1] = temp;
			}
		}
	}

}
