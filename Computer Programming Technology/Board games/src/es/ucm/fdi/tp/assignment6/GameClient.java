package es.ucm.fdi.tp.assignment6;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.control.commands.Command;
import es.ucm.fdi.tp.basecode.bgame.control.commands.PlayCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.QuitCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.RestartCommand;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameClient extends Controller implements Observable<GameObserver> {

	/**
	 * Host name the client wants to access.
	 */
	private String host;
	/**
	 * Port number the client wants to access.
	 */
	private int port;
	private List<GameObserver> observers;
	/**
	 * Piece to be used for this client (will come from the server).
	 */
	private Piece localPiece;
	/**
	 * GameFactory to be used for this client (will come from the server).
	 */
	private GameFactory gameFactory;
	private Connection connectionToServer;
	/**
	 * Indicates if the game is over (to stop the client).
	 */
	private boolean gameOver;

	/**
	 * Constructor of a GameClient
	 * 
	 * @param host
	 *            is the host name to connect
	 * @param port
	 *            is the port we want to connect
	 * @throws Exception
	 */
	public GameClient(String host, int port) throws Exception {

		super(null, null);
		this.host = host;
		this.port = port;
		observers = new ArrayList<>();
		// Initialize the fields and call connect to establish the connection to
		// the server
		connect();

	}

	/**
	 * @return the gameFactory.
	 */
	public GameFactory getGameFactory() {
		return gameFactory;
	}

	/**
	 * @return the localPiece.
	 */
	public Piece getPlayerPiece() {
		return localPiece;
	}

	@Override
	public void addObserver(GameObserver o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(GameObserver o) {
		observers.remove(o);
	}

	@Override
	public void makeMove(Player p) {
		forwardCommand(new PlayCommand(p));
	}

	public void start() {

		this.observers.add(new GameObserver() {

			@Override
			public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {

			}

			@Override
			public void onGameOver(Board board, State state, Piece winner) {
				gameOver = true;
				try {
					connectionToServer.stop();
				} catch (IOException e) {
				}
			}

			@Override
			public void onMoveStart(Board board, Piece turn) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMoveEnd(Board board, Piece turn, boolean success) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChangeTurn(Board board, Piece turn) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(String msg) {
				// TODO Auto-generated method stub
			}
		});

		gameOver = false;
		while (!gameOver) {
			try {
				Response res = (Response) connectionToServer.getObject();
				for (GameObserver o : observers) {
					res.run(o);
				}
			} catch (ClassNotFoundException | IOException e) {

			}
		}

	}

	@Override
	public void stop() {
		forwardCommand(new QuitCommand());
	}

	@Override
	public void restart() {
		forwardCommand(new RestartCommand());
	}

	private void forwardCommand(Command cmd) {

		if (!gameOver)
			try {
				connectionToServer.sendObject(cmd);
			} catch (IOException e) {
			}

	}

	private void connect() throws Exception {

		// We tell to GameServer we want to connect
		connectionToServer = new Connection(new Socket(host, port));
		connectionToServer.sendObject("Connect");

		Object response = connectionToServer.getObject();
		if (response instanceof Exception) {
			throw (Exception) response;
		}

		try {
			// Otherwise, the server should send back a GameFactory followed by
			// a Piece to be used by this client
			gameFactory = (GameFactory) connectionToServer.getObject();
			localPiece = (Piece) connectionToServer.getObject();
		} catch (Exception e) {
			throw new GameError("Unknown server response: " + e.getMessage());
		}

	}

}
