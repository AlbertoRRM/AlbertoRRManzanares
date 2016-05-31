package pr5test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class BoardExample extends JFrame implements GameObserver {

	private BoardComponent boardComp;
	private JTextField rows;
	private JTextField cols;
	Controller ctrl;
	
	public BoardExample(Observable<GameObserver> g, Controller ctrl) {
		super("[=] JComponent Example! [=]");this.ctrl = ctrl;
		
		initGUI();
		g.addObserver(this);
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());

		boardComp = new BoardComponent();
		mainPanel.add(boardComp, BorderLayout.CENTER);
		JPanel sizePabel = new JPanel();
		mainPanel.add(sizePabel, BorderLayout.PAGE_START);

		rows = new JTextField(5);
		cols = new JTextField(5);
		sizePabel.add(new JLabel("Rows"));
		sizePabel.add(rows);
		sizePabel.add(new JLabel("Cols"));
		sizePabel.add(cols);
		JButton setSize = new JButton("Set Size");
		sizePabel.add(setSize);
		setSize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int x = new Integer(rows.getText());
					int y = new Integer(cols.getText());
					boardComp.setBoardSize(x, y);
				} catch (NumberFormatException _e) {
				}
			}
		});

		mainPanel.setOpaque(true);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setVisible(true);
	}

	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		//this.boardComp.initBoard(board);
		boardComp.redraw(board);
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		// TODO Auto-generated method stub
		boardComp.redraw(board);
		
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		//this.boardComp.initBoard(board);
		boardComp.redraw(board);
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}
}
