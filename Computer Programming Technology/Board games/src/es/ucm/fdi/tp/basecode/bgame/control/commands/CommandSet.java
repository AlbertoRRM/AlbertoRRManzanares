package es.ucm.fdi.tp.basecode.bgame.control.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for managing a set of commands. It includes static methods to
 * generate help text for all the commands and to create a Command object based
 * on the text entered by the user.
 * 
 * <p>
 * Clase para manipular un conjunto de comandos. Incluye metodos estaticos para
 * generar textos de ayuda para todos los comandos y para crear un objeto
 * comando basado en el texto introducido por el usuario.
 */
public class CommandSet {

	/**
	 * A list of supported commands.
	 * 
	 * <p>
	 * Lista de comandos permitidos.
	 */
	private static List<Command> cmds = new ArrayList<Command>();

	/**
	 * Adds a command to the supported commands.
	 * 
	 * <p>
	 * Añade un comando al conjunto de comandos permitidos.
	 * 
	 * @param cmd
	 *            A command.
	 */
	public static void addCommand(Command cmd) {
		cmds.add(cmd);
	}

	/**
	 * Adds several commands to the supported commands.
	 * 
	 * <p>
	 * Añade varios comandos al conjunto de comandos permitidos.
	 * 
	 * @param cmds
	 *            A list of commands.
	 *            <p>
	 *            Lista de comandos.
	 */
	public static void addCommands(List<Command> cmds) {
		CommandSet.cmds.addAll(cmds);
	}

	/**
	 * Returns a command that corresponds to the string {@code cmdStr}.
	 * 
	 * <p>
	 * Devuelve un comando que corresponde al string {@code cmdStr}.
	 * 
	 * @param cmdStr
	 *            Text that represents a command.
	 *            <p>
	 *            Texto que representa un comando.
	 * @return A command that corresponds to {@code cmdStr}.
	 *         <p>
	 *         Comando que corresponde a {@code cmdStr}.
	 */
	public static Command parse(String cmdStr) {
		for (Command cmd : cmds) {
			Command tryCommand = cmd.parse(cmdStr);
			if (tryCommand != null)
				return tryCommand;
		}
		return null;
	}

	/**
	 * Returns the help text for all supported commands.
	 * 
	 * <P>
	 * Devuelve el texto de ayuda de todos los comandos permitidos.
	 * 
	 * @return Help text string.
	 *         <p>
	 *         Texto de ayuda.
	 */
	public static String helpText() {
		StringBuilder sb = new StringBuilder();

		for (Command com : cmds) {
			sb.append(com.helpText());
			sb.append("\n");
		}
		return sb.toString();
	}

}
