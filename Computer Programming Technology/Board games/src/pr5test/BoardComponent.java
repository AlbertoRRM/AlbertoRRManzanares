package pr5test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.JComponent;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class BoardComponent extends JComponent {

	private int _CELL_HEIGHT = 50;
	private int _CELL_WIDTH = 50;

	private int rows;
	private int cols;
	private Board board;
	Map<Piece, Color> colors;

	public BoardComponent() {
		initGUI();
	}

	public void initBoard(Board board) {
		this.board = board;
		this.rows = board.getRows();
		this.cols = board.getCols();
		repaint();
	}

	private void initGUI() {

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Mouse Released: " + "(" + e.getX() + ","
						+ e.getY() + ")");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Mouse Pressed: " + "(" + e.getX() + ","
						+ e.getY() + ")");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				System.out.println("Mouse Exited Component: " + "(" + e.getX()
						+ "," + e.getY() + ")");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				System.out.println("Mouse Entered Component: " + "(" + e.getX()
						+ "," + e.getY() + ")");
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Mouse Button "+e.getButton()+" Clicked at " + "(" + e.getX() + ","
						+ e.getY() + ")");
			}
		});
		this.setSize(new Dimension(rows * _CELL_HEIGHT, cols * _CELL_WIDTH));
		repaint();
	}
	
	public void redraw(Board b){
		this.board=b;
		this.rows=b.getRows();
		this.cols=b.getCols();
		repaint();	
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (board == null) {
			return;
		}
		
		_CELL_WIDTH = this.getWidth() / cols;
		_CELL_HEIGHT = this.getHeight() / rows;

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				drawCell(i, j, g);
	}

	private void drawCell(int row, int col, Graphics g) {
		int x = col * _CELL_WIDTH;
		int y = row * _CELL_HEIGHT;

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + 2, y + 2, _CELL_WIDTH - 4, _CELL_HEIGHT - 4);

		Piece p = board.getPosition(row, col);
		if ( p  != null) {
			g.setColor(getPieceColor(p));
			g.fillOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
	
			g.setColor(Color.black);
			g.drawOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
		}
	}

	private Color getPieceColor(Piece p) {
		return Utils.randomColor();
	}

	public void setBoardSize(int rows, int cols) {
		initBoard(board);
		repaint();
	}

}
