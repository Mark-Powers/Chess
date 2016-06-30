package util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import chessModel.Board;
import chessModel.ComputerPlayer;

public class Instantiator {
	public static Class classToObject(String location, String name) throws ClassNotFoundException {
		try {
			URLClassLoader loader = new URLClassLoader(new URL[] { new URL("file://" + location + "/") });
			System.out.println(loader.getURLs()[0]);

			return loader.loadClass(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NoClassDefFoundError e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ComputerPlayer makeComputerPlayer(String location, String name, Board b, int side) {

		Class AI = null;
		try {
			AI = Instantiator.classToObject(location, name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ComputerPlayer computerPlayer;
		Class[] cArg = new Class[2];
		cArg[0] = Board.class;
		cArg[1] = int.class;
		try {
			return (ComputerPlayer) AI.getDeclaredConstructor(cArg).newInstance(b, side);
		} catch (Exception e){
			e.printStackTrace();
		}

		JOptionPane.showMessageDialog(null, "Failed to load .class file");
		return null;
	}
	
	public static File[] getPackageContent(String packageName) throws IOException{
	    ArrayList<File> list = new ArrayList<File>();
	    Enumeration<URL> urls = Thread.currentThread().getContextClassLoader()
	                            .getResources(packageName);
	    while (urls.hasMoreElements()) {
	        URL url = urls.nextElement();
	        File dir = new File(url.getFile());
	        for (File f : dir.listFiles()) {
	            list.add(f);
	        }
	    }
	    return list.toArray(new File[]{});
	}
	
	
}
