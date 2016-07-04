package util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JOptionPane;

import chessModel.ComputerPlayer;

public class Instantiator {
	private static URLClassLoader loader;

	public static Class<?> classToObject(String location, String name) throws ClassNotFoundException {
		try {
			loader = new URLClassLoader(new URL[] { new URL("file://" + location + "/") });
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

	public static ComputerPlayer makeComputerPlayer(String location, String name, int side) {

		Class<?> AI = null;
		try {
			AI = Instantiator.classToObject(location, name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			return (ComputerPlayer) AI.getDeclaredConstructor(int.class).newInstance(side);
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
