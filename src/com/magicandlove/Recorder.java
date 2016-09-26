package com.magicandlove;

import processing.core.*;
import org.jcodec.api.awt.AWTSequenceEncoder8Bit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.io.IOException;

/**
 *
 */

public class Recorder {

	// myParent is a reference to the parent sketch
	PApplet myParent;
	int myVariable = 0;
	File file;
	AWTSequenceEncoder8Bit enc;
	int fps;
	int numFrames;

	public final static String VERSION = "1.0.0";

	/**
	 * a Constructor, usually called in the setup() method in your sketch to
	 * initialize and start the Library.
	 * 
	 * @example Hello
	 * @param theParent
	 */
	public Recorder(PApplet p, String f, int s) {
		myParent = p;
		fps = s;
		numFrames = 0;
		try {
			File dir = new File(myParent.dataPath(""));
			if (!dir.exists()) {
				Files.createDirectory(dir.toPath());
			}
			file = new File(myParent.dataPath(f));
			enc = AWTSequenceEncoder8Bit.createSequenceEncoder8Bit(file, fps);
		} catch (IOException e) {
			e.printStackTrace();
		}
		welcome();
	}

	public void addFrame(PImage i) {
		BufferedImage bi = (BufferedImage) i.getNative();
		try {
			enc.encodeImage(bi);
			numFrames++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addFrame() {
		BufferedImage bi = (BufferedImage) myParent.getGraphics().getImage();
		try {
			enc.encodeImage(bi);
			numFrames++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void finish() {
		try {
			if (numFrames==0) 
				Files.delete(file.toPath());
			else 
				enc.finish();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getFrameCount() {
		return numFrames;
	}
	
	private void welcome() {
		System.out.println("Recorder 1.0.0 by Bryan Chung");
	}

	/**
	 * return the version of the Library.
	 * 
	 * @return String
	 */
	public static String version() {
		return VERSION;
	}
}
