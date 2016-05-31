package es.ucm.fdi.tp.basecode.bgame.control.commands;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;

/**
 * A 'PLAY' command. It executes {@link Controller#makeMove(Player)} of the
 * corresponding controller.
 * 
 * <p>
 * Comando 'PLAY'. Ejecuta {@link Controller#makeMove(Player)} del controlador
 * correspondiente.
 *
 */
public class PlayCommand implements Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A player to be passed to {@link Controller#makeMove(Player)} when the
	 * command is executed.
	 * 
	 * <p>
	 * Jugador que se debe pasar a {@link Controller#makeMove(Player)} cuando se
	 * ejecuta este comando.
	 */
	private Player player;

	/**
	 * Creates a PlayCommand (with a {@code null} player).
	 * 
	 * <p>
	 * Crea un objeto PlayCommand (con un jugador {@code null}).
	 */
	public PlayCommand() {
		this.player = null;
	}

	/**
	 * Creates a PlayCommand for a specific player.
	 * 
	 * <p>
	 * Crea un objeto PlayCommand con un jugador específico.
	 * 
	 * @param player
	 *            A player.
	 *            <p>
	 *            Jugador.
	 */
	public PlayCommand(Player player) {
		this.player = player;
	}

	@Override
	public void execute(Controller c) {
		c.makeMove(player); // we pass null since the controller has the players
	}

	@Override
	public String helpText() {
		return "PLAY: make a move.";
	}

	@Override
	public Command parse(String cmdStr) {
		if (cmdStr.trim().equalsIgnoreCase("play")) {
			return this;
		} else {
			return null;
		}
	}

}
