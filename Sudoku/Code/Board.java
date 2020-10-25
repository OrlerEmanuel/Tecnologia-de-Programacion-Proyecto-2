package Code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class Board {

	protected static final float odds = 0.05f;
	protected static final int max = 9;	
	protected MatrixCell board[][];
	protected Stack<Move> record;
	
	public Board() {
		board = new MatrixCell[max][max];
		record = new Stack<Move>();
		for(int i = 0; i<max; i++)
			for(int j = 0; j<max; j++){
				board[i][j] = new MatrixCell(0,i,j);
			}
	}
	
	public void loadGame(File file) {
		BufferedReader bf; 
		
		FileReader fr;
		String line;  
		int pos;
		try {
			fr = new FileReader(file);
			bf = new BufferedReader(fr);
			for(int i=0; i<max; i++) {
				line = bf.readLine();
				pos = 0;
				for(int j=0; j<max; j++) {
					while(pos<line.length() && line.charAt(pos) == ' ') 	
						pos++;
					if(pos<line.length() && line.charAt(pos) != ' ') 
						board[i][j].setValue(Integer.parseInt(""+line.charAt(pos++))); 
				}
			}
		bf.close();
		} 
		catch (IOException | NullPointerException | NumberFormatException e) {
		}
	}
	
	public void prepareGame() {
		for(int i=0; i<max; i++)
			for(int j=0; j<max; j++)
				if(Math.random()<odds) {
					board[i][j].setValue(0);
					board[i][j].setInitial(false);
				}
				else
					board[i][j].setInitial(true);
		record = new Stack<Move>();
	}
	
	public boolean checkCell(int r, int c) {
		boolean  satisfy = true;
		if(!board[r][c].isInitial()) {
			for(int i = 0; satisfy && i<max; i++) {
				satisfy = c==i || !board[r][c].equals(board[r][i]);
				
			}
			
			for(int i = 0; satisfy && i<max; i++) {
				satisfy = r==i || !board[r][c].equals(board[i][c]);
					
			}
		
			for(int rowAux = r - r%3;  rowAux < (r + 3 - r%3); rowAux++) {
				for(int colAux = c - (c%3); satisfy && colAux < (c + 3 -c%3); colAux++) 
					if(colAux != c && rowAux != r)
						satisfy = !board[rowAux][colAux].equals(board[r][c]); 
			}

		}
		return satisfy;
	}
	
	public boolean checkBoard() {
		boolean satisfy = true;
		for(int i=0; satisfy && i<max;i++)
			for(int j=0; satisfy && j<max; j++)
				satisfy = board[i][j].getValue()%(max+1) !=0 && checkCell(i,j) == true;
		return satisfy;
	}
	
	public void put(int r, int c, int n) {
		if(!board[r][c].isInitial()) {
			record.push(new Move(r,c,board[r][c].getValue(),n));
			board[r][c].setValue(n);
		}
		
	}

	public void remove(int r, int c) {
		if(!board[r][c].isInitial()) {
			record.push(new Move(r,c,board[r][c].getValue(),0));
			board[r][c].setValue(0);
		}
	}
	
	public MatrixCell get(int r, int c) {
		return board[r][c];
	}
	
	public int getMax() {
		return max;
	}
	
	public boolean back() {
		boolean success = false;
		if(!record.empty()) {
			success = true;
			Move m = record.pop();
			board[m.getRow()][m.getCol()].setValue(m.getPrev());
		}
		return success;
	}
	
	public Move getLastMove() {
		Move toReturn = null;
		if(!record.isEmpty())
			toReturn = record.peek();
		return toReturn;
	}
	
}
