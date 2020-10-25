package Code;

import javax.swing.ImageIcon;

public class GRepresentation {
	protected ImageIcon icon;
	protected ImageIcon[] iconlist;
	
	public GRepresentation() {
		iconlist = new ImageIcon[11];
		for(int i=0; i<iconlist.length; i++) 
			iconlist[i] = new ImageIcon(GRepresentation.class.getResource("/Images/"+i+".png"));
		icon = iconlist[0];
	}
	
	public void set(int value) {
		icon = iconlist[value];
	}
	
	public ImageIcon get() {
		return icon;
	}
}
