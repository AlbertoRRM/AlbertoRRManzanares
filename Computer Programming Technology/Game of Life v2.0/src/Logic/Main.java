/**
* Program: Assignment 2 - Game of Life  V2                                   
* @author: Alberto Rodríguez - Rabadán Manzanares y Javier Rodríguez Miranda                                          
* Date:    14/12/2015                                                      
*/
package Logic;
import java.util.Scanner;
import Control.Controller;

public class Main {
	public static void main(String[] args) {
		Controller control = new Controller(new World(), new Scanner(System.in));
		control.executeSimulation();
	}
}
