package es.ucm.fdi.tp.basecode.bgame.control.commands;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;

/**
 * A 'RESTART' command. It executes {@link Controller#restart()} of the
 * corresponding controller.
 * 
 * <p>
 * Comando 'RESTART'. Ejecuta el metodo {@link Controller#restart()} del
 * controlador correspondiente.
 *
 */
public class RestartCommand implements Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Controller c) {
		c.restart();
	}

	@Override
	public String helpText() {
		return "RESTART: restart the game.";
	}

	@Override
	public Command parse(String cmd) {
		if (cmd.trim().equalsIgnoreCase("restart")) {
			return this;
		} else {
			return null;
		}
	}

}
