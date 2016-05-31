package es.ucm.fdi.tp.extra.jboard;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class BoardExample extends JFrame {

	private BoardComponent board;
	private JTextField rows;
	private JTextField cols;

	public BoardExample() {
		super("[=] JComponent Example! [=]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());

		board = new BoardComponent(10, 10);
		mainPanel.add(board, BorderLayout.CENTER);
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
					board.setBoardSize(x, y);
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

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new BoardExample();
			}
		});
	}
}
