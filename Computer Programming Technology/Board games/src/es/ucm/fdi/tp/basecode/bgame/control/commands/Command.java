package es.ucm.fdi.tp.basecode.bgame.control.commands;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;

/**
 * Represents a command that can be executed by the user (e.g. , at the console
 * controller).
 * 
 * <p>
 * Representa un comando que puede ser ejecutado por el usuario (por ejemplo,
 * desde el controlador del modo consola).
 */
public interface Command extends java.io.Serializable {

	/**
	 * Executes the command.
	 * 
	 * <p>
	 * Ejecuta el comando.
	 * 
	 * @param c
	 *            A controller (typically the one executing this command).
	 *            <p>
	 *            Controlador (normalmente el controlador que ejecuta este
	 *            comando).
	 */
	public void execute(Controller c);

	/**
	 * Generates a help text that describes the command.
	 * 
	 * <p>
	 * Genera un texto de ayuda que describe el comando.
	 * 
	 * @return Help text.
	 *         <p>
	 *         Texto de ayuda.
	 */
	public String helpText();

	/**
	 * Parses an input string, and returns an instance of a corresponding
	 * command.
	 * 
	 * <p>
	 * Analiza un string de entrada y devuelve una instancia del comando
	 * correspondiente.
	 * 
	 * @param line
	 * @return
	 */
	public Command parse(String line);

}
