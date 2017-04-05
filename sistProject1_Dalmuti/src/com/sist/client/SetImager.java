package com.sist.client;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class SetImager {
	 public static Image setImage(URL filename, int w, int h) {
			ImageIcon ii = new ImageIcon(filename);
			Image i = ii.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
			return i;
		}
}
