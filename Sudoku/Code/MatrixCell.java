package Code;

import javax.swing.ImageIcon;

public class MatrixCell {

	protected int value,row,col;
	protected boolean initial;
	protected GRepresentation img ;
	
	public MatrixCell(int value, int row, int col) {
		this.value = value;
		this.row = row;
		this.col = col;
		initial = false;
		img = new GRepresentation();
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	protected ImageIcon img(){
		return img.get();
	}

	public void setValue(int value) {
		this.value = value;
		img.set(value);
	}

	public int getValue() {
		return value;
	}

	public boolean isInitial() {
		return initial;
	}

	public void setInitial(boolean initial) {
		this.initial = initial;
	}
	
	public boolean equals(MatrixCell c) {
		return c.getValue() != 0 && c.getValue() == value;
	}

	
	
}
