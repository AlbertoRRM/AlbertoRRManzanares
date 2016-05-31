package es.ucm.fdi.tp.extra.jcolor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class ColorsExample extends JFrame {

	private JTextField name;
	private MyTableModel tModel;
	private JTextField line;
	private Map<Integer, Color> colors; // Line -> Color

	public ColorsExample() {
		super("[=] Colors Example ! [=]");
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel(new BorderLayout());
		colors = new HashMap<>();

		// names table
		tModel = new MyTableModel();
		tModel.getRowCount();
		JTable table = new JTable(tModel) {
			private static final long serialVersionUID = 1L;

			// THIS IS HOW WE CHANGE THE COLOR OF EACH ROW
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);

				// the color of row 'row' is taken from the colors table, if
				// 'null' setBackground will use the parent component color.
				comp.setBackground(colors.get(row));
				return comp;
			}
		};
		mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		JPanel ctrlPabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainPanel.add(ctrlPabel, BorderLayout.PAGE_START);

		// name text box and corresponding button
		ctrlPabel.add(new JLabel("Name"));
		name = new JTextField(15);
		ctrlPabel.add(name);
		JButton addName = new JButton("Add");
		addName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = name.getText().trim();
				if (!s.equals("")) {
					tModel.addName(name.getText());
				}
				name.setText("");
			}
		});
		ctrlPabel.add(addName);

		// line and corresponding button for choosing the color.
		ctrlPabel.add(new JLabel("Line"));
		line = new JTextField(3);
		ctrlPabel.add(line);
		JButton setColor = new JButton("Set Color");
		ctrlPabel.add(setColor);
		setColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int n = Integer.parseInt(line.getText());
					ColorChooser c = new ColorChooser(new JFrame(), "Choose Line Color", colors.get(n));
					if (c.getColor() != null) {
						colors.put(n, c.getColor());
						repaint();
					}
				} catch (Exception _e) {
				}
			}
		});

		mainPanel.setOpaque(true);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 500);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ColorsExample();
			}
		});
	}
}
