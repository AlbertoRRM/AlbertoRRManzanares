package es.ucm.fdi.tp.assignment5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.extra.jcolor.ColorChooser;

@SuppressWarnings("serial")
public abstract class SwingView extends JFrame implements GameObserver {

	/**
	 * Keeps a reference to the controller
	 */
	private Controller ctrl;
	private Piece localPiece;// If null, all can play. Important when deciding
								// to (de)activate the view,
								// or make an automatic play, etc.
	private Player randPlayer;
	private Player aiPlayer;
	private Piece turn; // GameObserver handlers keep this up-to-date
	private Board board;
	private List<Piece> pieces;
	
	/**
	 * Here we store the name of the pieces
	 */
	private Map<String, Piece> pieceIDs;
	
	/**
	 * Here we store the color of each piece
	 */
	private Map<Piece, Color> pieceColors;
	
	/**
	 * Here we store the types of the players
	 */
	private Map<Piece, PlayerMode> playerTypes;
	
	/**
	 * Here we store the number of pieces of each player
	 */
	private Map<Piece, Integer> playerPieces;
	
	/**
	 * Combo box of modes: Manual, Automatic and Intelligent.
	 */
	private JComboBox<String> playerModesList;
	private JPanel mainPanel;
	
	/**
	 * Table of players.
	 */
	private PlayerInfoTableModel playerInfoTable;
	private JComboBox<Piece> playerList1;
	private JComboBox<Piece> playerList2;
	private JTextArea statusMessages;
	JButton randomButton;
	JButton intelligentButton;

	/**
	 * This class creates a Swing View for the board games.
	 * 
	 * @param g
	 * @param ctrl
	 * @param localPiece
	 * @param randPlayer
	 * @param aiPlayer
	 */
	public SwingView(Observable<GameObserver> g, Controller ctrl, Piece localPiece, Player randPlayer,
			Player aiPlayer) {
		// Initialize the GUI and register as an observer
		super();
		this.ctrl = ctrl;
		this.localPiece = localPiece;
		this.randPlayer = randPlayer;
		this.aiPlayer = aiPlayer;
		initGUI();
		g.addObserver(this);
	}

	/**
	 * It builds the Control GUI and ask the subclass to install the board component.
	 */
	private void initGUI() {
		/* --------------------------------Main panel--------------------------------*/
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setOpaque(true);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 700);
		this.setVisible(true);

		/* -------------------------------Control panel------------------------------*/
		JPanel controlPanel = new JPanel();
		mainPanel.add(controlPanel, BorderLayout.LINE_END);
		controlPanel.setPreferredSize(new Dimension(350, 175));
		controlPanel.setOpaque(true);
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		controlPanel.setAlignmentY(Component.TOP_ALIGNMENT);

		/* ----------------------------Status messages panel--------------------------*/
		JPanel infoPanel = new JPanel();
		controlPanel.add(infoPanel);
		infoPanel.setBorder(BorderFactory.createTitledBorder("Status Message"));
		// Status messages area
		statusMessages = new JTextArea(300, 101);
		statusMessages.setEditable(false);
		statusMessages.setOpaque(true);
		statusMessages.setLineWrap(true);
		statusMessages.setWrapStyleWord(true);
		statusMessages.setAlignmentX(Component.LEFT_ALIGNMENT);
		infoPanel.add(statusMessages, BorderLayout.CENTER);
		// Scroll panel
		JScrollPane area = new JScrollPane(statusMessages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		area.setPreferredSize(new Dimension(340, 100));
		infoPanel.add(area);

		/* --------------------------Player information panel-------------------------*/
		JPanel playerInfoPanel = new JPanel(new BorderLayout());
		controlPanel.add(playerInfoPanel);
		playerInfoPanel.setBorder(BorderFactory.createTitledBorder("Player Information"));
		// Table
		playerInfoTable = new PlayerInfoTableModel();
		JTable table = new JTable(playerInfoTable) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);
				if (pieceColors != null && pieces != null)
					comp.setBackground(pieceColors.get(pieces.get(row)));
				return comp;
			}
		};
		table.setFillsViewportHeight(true);
		table.setCellSelectionEnabled(false);
		JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		playerInfoPanel.setPreferredSize(new Dimension(300, 150));
		playerInfoPanel.add(sp);

		/*------------------------------Piece colors panel-----------------------------*/
		JPanel pieceColorsPanel = new JPanel();
		controlPanel.add(pieceColorsPanel);
		pieceColorsPanel.setBorder(BorderFactory.createTitledBorder("Piece Colors"));
		playerList1 = new JComboBox<Piece>();
		pieceColorsPanel.add(playerList1);
		playerList1.setAlignmentX(Component.LEFT_ALIGNMENT);
		// Choose color button
		JButton colorButton = new JButton("Choose color");
		colorButton.setSize(new Dimension(120, 30));
		pieceColorsPanel.add(colorButton);
		colorButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		colorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Piece chosenPiece = (Piece) playerList1.getSelectedItem();

				ColorChooser color = new ColorChooser(new JFrame(), "Choose Line Color", pieceColors.get(chosenPiece));

				if (color.getColor() != null) {
					pieceColors.put(chosenPiece, color.getColor());
					repaint();
				}
			}
		});

		/* ------------------------------Player modes panel---------------------------*/
		JPanel playerModesPanel = new JPanel();
		controlPanel.add(playerModesPanel);
		playerModesPanel.setBorder(BorderFactory.createTitledBorder("Player Modes"));
		// Combo box
		playerList2 = new JComboBox<Piece>();
		if (localPiece == null)
			playerModesPanel.add(playerList2);
		String playerModesNames[] = { "Manual", "Random", "Intelligent" };
		JComboBox<String> playerModesList = new JComboBox<String>(playerModesNames);
		playerModesList.setSelectedIndex(0);
		playerModesPanel.add(playerModesList);
		// Set button
		JButton setButton = new JButton("Set");
		colorButton.setSize(new Dimension(60, 30));
		playerModesPanel.add(setButton);
		setButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Piece chosenPiece;
				String chosenMode;
				
				chosenMode = (String) playerModesList.getSelectedItem();
				if (localPiece != null)
					chosenPiece = turn;
				else 
					chosenPiece = (Piece) playerList2.getSelectedItem();

				if (chosenPiece.equals(localPiece) || localPiece == null) {
					if (chosenMode == "Manual") {
						playerTypes.put(chosenPiece, PlayerMode.MANUAL);
						randomButton.setEnabled(true);
						intelligentButton.setEnabled(true);
					}
					else if (chosenMode == "Random") {
						playerTypes.put(chosenPiece, PlayerMode.RANDOM);
						randomButton.setEnabled(false);
						intelligentButton.setEnabled(true);
						if (chosenPiece.equals(turn))
							decideMakeAutomaticMove(randPlayer);
					}
					else if (chosenMode == "Intelligent") {
						playerTypes.put(chosenPiece, PlayerMode.AI);
						randomButton.setEnabled(true);
						if (chosenPiece.equals(turn))
							decideMakeAutomaticMove(aiPlayer);
					}

					repaint();
					playerInfoTable.refresh();
				}
			}
		});

		/* -----------------------------Automatic moves panel------------------------------*/
		JPanel automaticMovesPanel = new JPanel();
		controlPanel.add(automaticMovesPanel);
		automaticMovesPanel.setBorder(BorderFactory.createTitledBorder("Automatic Moves"));
		// Random button
		randomButton = new JButton("Random");
		randomButton.setSize(new Dimension(120, 30));
		automaticMovesPanel.add(randomButton);
		randomButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (playerTypes.get(turn) == PlayerMode.MANUAL)
					decideMakeAutomaticMove(randPlayer);
			}

		});
		
		
		// Intelligent button
		intelligentButton = new JButton("Intelligent");
		intelligentButton.setSize(new Dimension(120, 30));
		automaticMovesPanel.add(intelligentButton);
		intelligentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (playerTypes.get(turn) == PlayerMode.MANUAL)
					decideMakeAutomaticMove(aiPlayer);
			}
		});
		
		/* --------------------------------Buttons panel--------------------------------*/
		JPanel buttonsPanel = new JPanel();
		controlPanel.add(buttonsPanel);
		// Quit button
		JButton quitButton = new JButton("Quit");
		quitButton.setSize(new Dimension(120, 30));
		buttonsPanel.add(quitButton);
		quitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.stop();
				
				System.exit(ABORT);
			}
		});

		// Restart button
		JButton restartButton = new JButton("Restart");
		restartButton.setSize(new Dimension(120, 30));
		buttonsPanel.add(restartButton);
		restartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.restart();
				playerList1.removeAllItems();
				playerList2.removeAllItems();
				statusMessages.removeAll();;
			}
		});

		initBoardGui();
	}

	class PlayerInfoTableModel extends DefaultTableModel {
		private String[] colNames;

		private PlayerInfoTableModel() {
			this.colNames = new String[] { "Player", "Mode", "#Pieces" };
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		@Override
		public String getColumnName(int col) {
			return colNames[col];
		}

		@Override
		public int getColumnCount() {
			return colNames.length;
		}

		@Override
		public int getRowCount() {
			return pieces == null ? 0 : pieces.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (pieces == null) {
				return null;
			}
			Piece p = pieces.get(rowIndex);

			if (columnIndex == 0) {
				return p;
			} else if (columnIndex == 1) {
				if (playerTypes.get(p) == PlayerMode.MANUAL)
					return "Manual";
				else if (playerTypes.get(p) == PlayerMode.RANDOM)
					return "Random";
				else if (playerTypes.get(p) == PlayerMode.AI)
					return "AI";
			} else if (columnIndex == 2)
				return board.getPieceCount(p);
			return null;
		}

		public void refresh() {
			fireTableDataChanged();
			repaint();
		}
	};

	enum PlayerMode {
		MANUAL("Manual"), RANDOM("Random"), AI("Intelegent");

		private String name;

		PlayerMode(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	// Subclasses use these methods to consult game info
	
	/**
	 * @return the piece of the current turn.
	 */
	final protected Piece getTurn() {
		return turn;
	}

	/**
	 * @return the current board.
	 */
	final protected Board getBoard() {
		return board;
	}

	/**
	 * @return the current list of pieces.
	 */
	final protected List<Piece> getPieces() {
		return pieces;
	}
	
	// Subclasses use these methods to consult or change piece colors and to set
	// the board component, add text to the info area, etc.
	
	/**
	 * @param p
	 *            is the piece we want to consult.
	 * @return the color of the piece.
	 */
	final protected Color getPieceColor(Piece p) {
		return pieceColors.get(p);
	}

	/**
	 * @param p
	 *            is the piece we want to modify.
	 * @param c
	 *            is the color we want to set.
	 * @return a color.
	 */
	final protected Color setPieceColor(Piece p, Color c) {
		return pieceColors.put(p, c);
	}

	/**
	 * @param c
	 */
	final protected void setBoardArea(JComponent c) {
		mainPanel.add(c, BorderLayout.CENTER);
	}

	/**
	 * @param msg
	 *            is the message we want to append to the status messages text
	 *            area.
	 */
	final protected void addMsg(String msg) {
		statusMessages.append(msg);
	}

	final protected void decideMakeManualMove(Player manualPlayer) {
		try {
			ctrl.makeMove(manualPlayer);
		} catch (GameError e) {
		}
	}

	// We call this to make an automatic (random or ai) move, if needed
	// When to call it: when turn changes, when player changes from Manual to
	// Automatic, when game starts, etc...
	private void decideMakeAutomaticMove(Player player) {
		// If the player ‘turn’ is automatic and belongs to this view, we make
		// an automatic move, i.e., call ctrl.makeMove(p) where p is the random
		// or the ai player (depending on the mode of ‘turn’)

		// Si localPiece == null o turn == localPiece, entonces el turn juega en
		// esta ventana
		// en este caso, si el modo de turn es automático, se llama a
		// ctrl.makeMove(aiPlayer)
		if (turn.equals(localPiece) || localPiece == null)
			ctrl.makeMove(player);
	}

	public int getPlayerPieces(Piece p) {
		return playerPieces.get(p);
	}
	
	public void setPlayerPieces(int num) {
		playerPieces.put(turn, num);
	}

	// Used to tell subclasses to initialise the board GUI (board component), to
	// redraw the board, to (de)activate it, etc.
	protected abstract void initBoardGui();

	protected abstract void activateBoard();

	protected abstract void deActivateBoard();

	protected abstract void redrawBoard();

	// GameObserver Methods. They will be called by the game to notify changes,
	// etc... They call another method to handle the event using invokeLater,
	// this way you do not block swing's events loop, and you will have your
	// assignment thread safe
	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				handleGameStart(board, gameDesc, pieces, turn);
			}
		});
	}

	private void handleGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		this.board = board;
		this.pieces = pieces;
		this.turn = turn;

		pieceIDs = new HashMap<String, Piece>();
		playerPieces = new HashMap<Piece, Integer>();
		playerTypes = new HashMap<Piece, PlayerMode>();
		pieceColors = new HashMap<Piece, Color>();
		Iterator<Color> colorGener = Utils.colorsGenerator();
		String players[] = new String[4];

		for (int i = 0; i < pieces.size(); i++) {
			players[i] = pieces.get(i).getId();
		}
		playerModesList = new JComboBox<String>(players);
		playerModesList.setSelectedIndex(0);

		// Initializing the hash maps
		for (Piece p : pieces) {
			playerList1.addItem(p);
			playerList2.addItem(p);
			pieceIDs.put(p.getId(), p);
			playerPieces.put(p, 0);
			playerTypes.put(p, PlayerMode.MANUAL);
			pieceColors.put(p, colorGener.next());
		}

		addMsg(gameDesc + "\n");
		addMsg("Turn for " + turn + "\n");

		playerInfoTable.refresh();
		redrawBoard();
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				handleOnGameOver(board, state, winner);
			}
		});
	}

	private void handleOnGameOver(Board board, State state, Piece winner) {
		this.board = board;
		redrawBoard();
		addMsg("Game Over! \n");
		if (winner != null)
			addMsg("Winner is: " + winner + "\n");
		else
			addMsg("Draw \n");
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				handleOnMoveStart(board, turn);
			}
		});
	}

	private void handleOnMoveStart(Board board, Piece turn) {
		redrawBoard();
		playerInfoTable.refresh();
		playerList1.repaint();
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				handleOnMoveEnd();
			}
		});
	}

	private void handleOnMoveEnd() {
		redrawBoard();
		playerInfoTable.refresh();
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				handleOnChangeTurn(board, turn);
			}
		});
	}

	private void handleOnChangeTurn(Board board, Piece piece) {
		turn = piece;
		
		// If we are in multiview mode
		if (localPiece != null) {
			if (turn.equals(localPiece))
				activateBoard();
			else
				deActivateBoard();
		}
		if (playerTypes.get(turn) == PlayerMode.AI)
			decideMakeAutomaticMove(aiPlayer);
		else if (playerTypes.get(turn) == PlayerMode.RANDOM)
			decideMakeAutomaticMove(randPlayer);
		addMsg("Turn for " + turn + "\n");
		this.board = board;
		redrawBoard();
		playerInfoTable.refresh();
	}

	@Override
	public void onError(String msg) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				handleOnError(msg);
			}
		});
	}

	private void handleOnError(String msg) {
		addMsg(msg + "\n");
	}
}
