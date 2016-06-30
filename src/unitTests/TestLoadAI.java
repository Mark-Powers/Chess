package unitTests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.junit.Test;

import chessModel.Board;
import chessModel.ComputerPlayer;
import util.Instantiator;

public class TestLoadAI {

	@Test
	public void testLoadAIFromJFileChooser(){
		try {
			File[] files = Instantiator.getPackageContent("artificialIntelligence");
			ArrayList<String> names = new ArrayList<String>();
			for (File f : files){
				names.add(f.getName());
			}
			JComboBox options = new JComboBox(names.toArray());
			JOptionPane.showMessageDialog(null, options);
			File f = files[options.getSelectedIndex()];
			String className = "artificialIntelligence" + "." + f.getName().substring(0,f.getName().indexOf("."));
			ComputerPlayer cp = Instantiator.makeComputerPlayer(f.getPath(), className, new Board(), 0);
			assertTrue(cp instanceof ComputerPlayer);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception occured");
		}
		
	}

}
