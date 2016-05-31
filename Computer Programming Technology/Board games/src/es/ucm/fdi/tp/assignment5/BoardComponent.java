package es.ucm.fdi.tp.assignment5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JComponent;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public abstract class BoardComponent extends JComponent {

	/**
	 * The height of a cell.
	 */
	private int _CELL_HEIGHT = 50;

	/**
	 * The width of a cell.
	 */
	private int _CELL_WIDTH = 50;

	/**
	 * Number of rows in the board.
	 */
	private int rows;

	/**
	 * Number of columns in the board.
	 */
	private int cols;

	/**
	 * A board component.
	 */
	private Board board;

	/**
	 * A map for pieces and colors.
	 */
	Map<Piece, Color> colors;

	/**
	 * Iterator used to generate random colors.
	 */
	Iterator<Color> colorIter;

	/**
	 * Constructor of BoardComponent. It creates a HashMap of pieces and colors
	 * and initializes the iterator of colors and the GUI.
	 */
	public BoardComponent() {
		colors = new HashMap<Piece, Color>();
		colorIter = Utils.colorsGenerator();
		initGUI();
	}

	/**
	 * This function redraws the board.
	 * 
	 * @param b
	 *            is the board which is going to be redraw.
	 */
	public void redraw(Board b) {
		this.board = b;
		this.rows = b.getRows();
		this.cols = b.getCols();
		repaint();
	}

	/**
	 * Initializes the GUI setting the size and repainting it.
	 */
	private void initGUI() {

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				BoardComponent.this.mouseClicked(e.getY() / _CELL_HEIGHT, e.getX() / _CELL_WIDTH, e.getButton());
			}
		});
		this.setSize(new Dimension(rows * _CELL_HEIGHT, cols * _CELL_WIDTH));
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

	/**
	 * It draws a cell.
	 * 
	 * @param row
	 *            is the row where the cell is located.
	 * @param col
	 *            is the column where the cell is located.
	 * @param g
	 */
	private void drawCell(int row, int col, Graphics g) {
		int x = col * _CELL_WIDTH;
		int y = row * _CELL_HEIGHT;

		g.setColor(Color.LIGHT_GRAY); // Background
		g.fillRect(x + 2, y + 2, _CELL_WIDTH - 4, _CELL_HEIGHT - 4);

		Piece p = board.getPosition(row, col);
		if (p != null) {
			g.setColor(getPieceColor(p)); // Inside the piece
			g.fillOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
			g.setColor(Color.black); // Border
			g.drawOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
		}
	}

	/**
	 * Consult the color or type (player or obstacle) of a piece.
	 * 
	 * @param p
	 *            is the piece to consult.
	 * @return a color.
	 */
	protected abstract Color getPieceColor(Piece p);

	/**
	 * Consult if a piece is of a given player.
	 * 
	 * @param p
	 *            is the piece to consult.
	 * @return true or false depending on if it is a player's piece.
	 */
	protected abstract boolean isPlayerPiece(Piece p);

	/**
	 * When the user clicks on a cell, pass it to this method.
	 * 
	 * @param row
	 *            is the clicked row.
	 * @param col
	 *            is the clicked column.
	 * @param mouseButton
	 */
	protected abstract void mouseClicked(int row, int col, int mouseButton);
}
