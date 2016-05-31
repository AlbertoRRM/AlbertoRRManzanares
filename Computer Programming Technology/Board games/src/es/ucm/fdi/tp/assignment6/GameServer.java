package es.ucm.fdi.tp.assignment6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.control.commands.Command;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameServer extends Controller implements GameObserver {

	/**
	 * Port to be used by the server.
	 */
	private int port;

	/**
	 * Number of players allowed by the game (length of pieces).
	 */
	volatile private int numPlayers;

	/**
	 * Number of connected players. It is used to know when to start the game,
	 * etc.
	 */
	volatile private int numOfConnectedPlayers;

	/**
	 * The factory used to create the GameRules.
	 */
	private GameFactory gameFactory;

	/**
	 * List of connections to the clients.
	 */
	private List<Connection> clients;

	/**
	 * A reference to the server.
	 */
	volatile private ServerSocket server;

	/**
	 * Indicates if there is a request to stop the server.
	 */
	volatile private boolean stopped;

	/**
	 * Indicates if the game is over.
	 */
	volatile private boolean gameOver;

	private JTextArea infoStateArea;
	private JTextArea infoPlayersArea;
	/**
	 * Constructor of GameServer. Gets a GameFactory to create a game, a list of pieces for the game, and a
	 * port for the server.
	 * 
	 * @param gameFactory
	 * @param pieces
	 * @param port
	 */
	public GameServer(GameFactory gameFactory, List<Piece> pieces, int port) {
		// Create the game, and pass it together with the pieces to the super
		// class, they will be stored in corresponding fields. Don't start the
		// game yet.
		super(new Game(gameFactory.gameRules()), pieces);

		// Initialize the fields with corresponding values.
		this.gameFactory = gameFactory;
		this.pieces = pieces;
		this.port = port;
		this.clients = new ArrayList<Connection>();
		numPlayers = pieces.size();
		numOfConnectedPlayers = 0;

		// Register the server as an observer in the game, so it gets
		// notifications when the model changes, etc.
		game.addObserver(this);

	}
	
	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		// When GameServer receives an onGameStart notification from the model,
		// it saves all information in a Response object and forwards this
		// object to all clients.
		forwardNotification(new GameStartResponse(board, gameDesc, pieces, turn));
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {

		forwardNotification(new GameOverResponse(board, state, winner));
		stopGame();
		infoPlayersArea.setText(null);
		log("Game finished.");
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {

		forwardNotification(new MoveStartResponse(board, turn));

	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {

		forwardNotification(new MoveEndResponse(board, turn, success));

	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {

		forwardNotification(new ChangeTurnResponse(board, turn));

	}

	@Override
	public void onError(String msg) {

		forwardNotification(new ErrorResponse(msg));

	}

	@Override
	public synchronized void makeMove(Player player) {

		try {
			super.makeMove(player);
		} catch (GameError e) {
		}

	}

	@Override
	public synchronized void stop() {

		try {
			super.stop();
		} catch (GameError e) {
		}
		gameOver = true;

	}

	@Override
	public synchronized void restart() {

		try {
			super.restart();
		} catch (GameError e) {
		}

	}

	/**
	 * Construct the control GUI to show informative messages, stop the server,
	 * etc. and then start the server.
	 */
	@Override
	public void start() {

		controlGUI();
		try {
			startServer();
		} catch (IOException e) {
			System.err.println("Error when starting a connection " + e.getMessage());
			System.exit(1);
		}

	}


	private void forwardNotification(Response r) {

		try {
			for (Connection c : clients) {
				c.sendObject(r);
			}
		} catch (IOException e) {
		}

	}

	private void startServer() throws IOException {
		// Start the server.
		server = new ServerSocket(port);

		// Indicate that the game has not been stopped, since we are just about
		// to start it.
		stopped = false;
		log("Server started");
		// This loop waits for a connection and pass it to handleRequest to do
		// something with it.
		while (!stopped) {
			try {
				// accept a connection into a socket s
				Socket s = server.accept();
				// log a corresponding message.
				log("Client trying to connect from the port " + s.getPort());
				if (numOfConnectedPlayers < numPlayers)
					playersLog(pieces.get(numOfConnectedPlayers).toString());
				// call handleRequest(s) to handle the request
				handleRequest(s);
				
			} catch (IOException e) {
				if (!stopped)
					log("Error while waiting for a connection: " + e.getMessage());
			}
		}
	}

	private void stopServer() {

		stopped = true;
		stopGame();
		try {
			server.close();
		} catch (IOException e) {
		}

	}

	private void stopGame() {

		gameOver = true;
		if (game.getState() == Game.State.InPlay) {
			stop();
			
			try {
				for (Connection c : clients) {
					c.stop();
				}
			} catch (IOException e) {
			}
		}
		clients.clear();
		numOfConnectedPlayers = 0;

	}

	/**
	 * Initialization of the GUI of the server.
	 */
	private void controlGUI() {
		// Use invokeAndWait instead of invokeLater so once we exit controlGUI
		// we know that we can safely call method log to add messages, etc.
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					constructGUI();
				}

			});
		} catch (InvocationTargetException | InterruptedException e) {
			throw new GameError("Something went wrong when constructing the GUI");
		}
	}

	/**
	 * Creation of the GUI of the server.
	 */
	private void constructGUI() {

		JFrame window = new JFrame("Game Server");

		JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
		JPanel StateServerPanel = new JPanel(new BorderLayout(5, 5));
		JPanel StatePlayerPanel = new JPanel();

		// Area for printing messages
		StateServerPanel.setBorder(BorderFactory.createTitledBorder("Status Messages"));
		StatePlayerPanel.setBorder(BorderFactory.createTitledBorder("Players"));
		infoStateArea = new JTextArea(25, 50);
		infoPlayersArea = new JTextArea(11, 3);
		infoStateArea.setEditable(false);
		infoStateArea.setFont(new Font("Calibri", Font.BOLD, 18));
		infoPlayersArea.setEditable(false);
		infoPlayersArea.setFont(new Font("Calibri", Font.BOLD, 18));
		StateServerPanel.add(new JScrollPane(infoStateArea));
		StatePlayerPanel.add(new JScrollPane(infoPlayersArea));
		mainPanel.add(StateServerPanel, BorderLayout.CENTER);

		// Stop button
		JButton stopButton = new JButton("Stop Server");
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				stopServer();
				System.exit(0);
			}

		});
		stopButton.setPreferredSize(new Dimension(50, 50));
		stopButton.setBackground(Color.ORANGE);
		stopButton.setFont(new Font("Calibri", Font.BOLD, 24));
		mainPanel.add(StatePlayerPanel, BorderLayout.LINE_END);
		mainPanel.add(stopButton, BorderLayout.PAGE_END);
		window.add(mainPanel);
		window.setPreferredSize(new Dimension(600, 400));
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.pack();
		window.setVisible(true);

	}

	/**
	 * Add informative messages to the text area infoArea. It should be called
	 * from all parts of GameServer
	 * 
	 * @param msg
	 */
	private void log(String mssg) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				infoStateArea.append(mssg + System.getProperty("line.separator"));
			}

		});

	}

	private void playersLog(String mssg) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				infoPlayersArea.append(mssg + System.getProperty("line.separator"));
			}

		});
	}

	/**
	 * Handle a client's request.
	 * @param s is the socket used to create a connection.
	 */
	private void handleRequest(Socket s) {
		try {
			// Wrap the socket with a Connection, so it is easier to use it
			Connection c = new Connection(s);

			Object clientRequest = c.getObject();
			if (!(clientRequest instanceof String) && !((String) clientRequest).equalsIgnoreCase("Connect")) {
				c.sendObject(new GameError("Invalid Request"));
				c.stop();
				return;
			}
			if (numOfConnectedPlayers >= numPlayers) {
				c.sendObject(new GameError("Maximum amount of players reached"));
				return;
			}

			clients.add(c);
			numOfConnectedPlayers++;
			log("Player " + pieces.get(numOfConnectedPlayers - 1).toString() + " connected.");
			log(numOfConnectedPlayers + " player(s).");

			// 3. Send back an "OK" String to the client, followed by the
			// GaneFactory and a Piece to be used for initialising the client,
			// etc. The i-th client is assigned the i-th piece
			c.sendObject("OK");
			c.sendObject(gameFactory);
			c.sendObject(pieces.get(numOfConnectedPlayers - 1));
			// 4. If we have reached the expected number of players, start the
			// game. First time we call start(pieces), afterwards we use
			// restart()
			if (numOfConnectedPlayers == numPlayers) {
				log("Starting the game...");
				if(game.getState() == State.Starting)
					game.start(pieces);
				else
					game.restart();
			}
			// 5..
			// Call startClientListener to create a thread that listens to the
			// client messages, etc.
			startClientListener(c);
		} catch (IOException | ClassNotFoundException _e) {
		}

	}

	/**
	 * 
	 * @param c is the connection
	 */
	private void startClientListener(Connection c) {
		// Set gameOver to false to indicate that the game is not over.
		gameOver = false;

		// Start a thread that runs the loop below while the game is not over
		// and the server has not been stopped
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!stopped && !gameOver) {
					Command cmd;
					try {
						// 1. Read a Command (read an Object from client and
						// cast it to Command)
						cmd = (Command) c.getObject();
						// 2. Execute the command (execute cmd passing it the
						// controller GameServer.this, the command will then
						// call makeMove, stop, etc.
						cmd.execute(GameServer.this);
					} catch (ClassNotFoundException | IOException e) {
						if (!stopped && !gameOver) {
							// stop the game (not the server). When the game is
							// over or the server has been stopped, the
							// connection to all clients are closed, so if a
							// client is waiting to read a Command it will throw
							// an exception, in such case we should do nothing
							// and we will exit the loop in the next iteration.
							// If we reach the catch because of other errors we
							// should stop the game (not the server).
							stopGame();
						}
					}
				}
			}

		});
		t.start();
	}
	
}
