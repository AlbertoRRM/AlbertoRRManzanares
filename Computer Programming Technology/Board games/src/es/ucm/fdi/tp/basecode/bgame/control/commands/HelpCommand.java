package es.ucm.fdi.tp.basecode.bgame.control.commands;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;

/**
 * A 'HELP' command, simply prints the text returned by
 * {@link CommandSet#helpText()}.
 * 
 * <p>
 * Comando 'HELP', que solo imprime el texto devuelto por
 * {@link CommandSet#helpText()}.
 * 
 */
public class HelpCommand implements Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Controller c) {
		System.out.println();
		System.out.println(CommandSet.helpText());
	}

	@Override
	public String helpText() {
		return "HELP: show usage information.";
	}

	@Override
	public Command parse(String cmdStr) {
		if (cmdStr.trim().equalsIgnoreCase("help")) {
			return this;
		} else {
			return null;
		}
	}

}
