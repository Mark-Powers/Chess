package chessViewController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import chessModel.ComputerPlayer;
import util.Instantiator;

public class SelectMode {
	public static int selectMode() {
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("Human vs. AI");
		modes.add("Human vs. Human");
		modes.add("AI vs. AI");
		JComboBox<?> selectMode = new JComboBox<Object>(modes.toArray());
		JOptionPane.showMessageDialog(null, selectMode, "Select Game Mode", JOptionPane.PLAIN_MESSAGE);
		return selectMode.getSelectedIndex();
	}

	public static ComputerPlayer pickComputerPlayer(int side) throws IOException {
		File[] files = Instantiator.getPackageContent("artificialIntelligence");
		ArrayList<String> names = new ArrayList<String>();
		for (File f : files) {
			names.add(f.getName().substring(0, f.getName().indexOf(".")));
		}
		JComboBox<Object> options = new JComboBox<Object>(names.toArray());
		JOptionPane.showMessageDialog(null, options, "Select Player #" + (side+1), JOptionPane.PLAIN_MESSAGE);
		File f = files[options.getSelectedIndex()];
		String className = "artificialIntelligence" + "." + f.getName().substring(0, f.getName().indexOf("."));
		return Instantiator.makeComputerPlayer(f.getPath(), className, 1);
	}
}
