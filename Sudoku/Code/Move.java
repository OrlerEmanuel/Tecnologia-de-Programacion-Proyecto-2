package Code;

public class Move {
	protected int row,col,prev,current;
	
	public Move(int i, int j, int p, int c) {
		row = i;
		col = j;
		prev = p;
		current = c;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getPrev() {
		return prev;
	}

	public int getCurrent() {
		return current;
	}
	
}
