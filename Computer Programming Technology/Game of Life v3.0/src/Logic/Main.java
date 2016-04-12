/**
* Program: Assignment 3 - Game of Life  V3                                   
* @author: Alberto Rodriguez - Rabadan Manzanares y Javier Rodriguez Miranda                                          
* Date:    17/01/2016                                                      
*/
package Logic;
import java.util.Scanner;
import Control.Controller;

public class Main {
	public static void main(String[] args) {
		Controller control = new Controller(new SimpleWorld(6, 5, 7, 0), new Scanner(System.in));
		control.executeSimulation();
	}
}
