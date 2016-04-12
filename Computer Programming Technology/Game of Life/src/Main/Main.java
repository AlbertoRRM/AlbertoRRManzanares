package Main;
/**
* Program: Assignment 1 - Game of Life                                     
* @author: Alberto Rodríguez - Rabadán Manzanares y Javier Rodríguez Miranda                                          
* Date:    14/11/2015                                                      
*/
import java.util.Scanner;

import Control.Controller;
import Logic.World;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Controller control = new Controller(new World(), new Scanner(System.in));
		control.executeSimulation();
	}
}
