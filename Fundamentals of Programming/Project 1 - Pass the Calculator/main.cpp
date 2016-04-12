/*************************************************************/
/* Program: Pass the calculator                              */
/* Version: 23                                               */
/* Autor: Alberto Rodríguez - Rabadán Manzanares             */
/* Date: 03/12/2014                                          */
/*************************************************************/
#include <iostream>
#include <string>
#include <fstream>
#include <cstdlib> //Library needed to generate random numbers
#include <time.h> //Library needed to generate random numbers with the time
#include <iomanip> 
using namespace std;

typedef enum { None, Computer, User } tPlayer;

//Prototypes
tPlayer passCalculator(); // Conducts one match and return the winner
tPlayer whoStarts(); // Decide who starts the match.
bool sameRow(int last, int next); // Check if the digit is in the same row.
bool sameColumn(int last, int next); // Check if the digit is in the same column.
bool validDigit(int last, int next); //Check if the digit satisfies game rules.
int randomNumber(); //Return a randomly number between (1 to 9)
int computerDigit(int last); //Returns a valid digit in relation to last.
int userInput(); // Ask the user for a valid digit.
int userDigit(int last); // Ask the user for a digit while displaying numerical pad.
int menu(); // Displays the menu
bool display(string nameFile); // Displays the information about the program.
bool reportUpdate(int matches, int won, int abandoned); //Print information about the game in a .txt


int main()
{
	string name, nameFile = "versionPC.txt";
	tPlayer winner = None;
	int last = -1, next = -1, opt;
	int matches = 0, won = 0, abandoned = 0;
	bool exit = false;

	srand(int(time(NULL)));

	cout << "Welcome to the pass calculator!" << endl;
	cout << "What's your name?" << endl;
	getline(cin, name);
	cout << "Hi, " << name << endl;

	while (exit != true)
	{
		opt = menu();
		switch (opt)
		{
		case 0:
		{
			exit = true;
		}
		break;

		case 1:
		{
			winner = passCalculator();
			if (winner == User)
				cout << "Congratulations! You win!" << endl;
			if (winner == Computer)
			{
				cout << "Computer wins!" << endl;
				won++;
			}
			if (winner == None)
			{
				cout << "You abandoned :(" << endl;
				abandoned++;
			}
			cout << "" << endl;
			cout << "See you " << name << "!" << endl;
			cout << "" << endl;

			matches++;
		}
		break;

		case 2:
		{
			if (!display(nameFile))
			{
				cout << "Couldn't open the file!" << endl;
			}

			cout << "" << endl;
		}
		break;
		}
	}
	reportUpdate(matches, won, abandoned);

	system("pause");
	return 0;
}

int menu()
{
	int opt;
	bool valid = false;

	cout << "Please select an option" << endl;
	cout << "1.- Play" << endl;
	cout << "2.- About" << endl;
	cout << "0.- Exit" << endl;
	cout << "Option: ";
	cin >> opt;
	
	while (valid != true)
	{
		if (opt >= 0 && opt <= 2)
		{
			valid = true;	
		}
		else
		{
			cout << "Invalid! Please choose a correct option" << endl;
			cout << "Option: ";
			cin >> opt;
		}
	}


	return opt;
}

tPlayer passCalculator()
{
	tPlayer winner = None;
	int sum = 0;
	int next, last;
	const int GOAL = 31;

	winner = whoStarts();

	if (winner == User)
	{
		next = userInput();
		last = next;
	}
	else if (winner == Computer)
	{
		next = randomNumber();
		last = next;
		cout << "I choose: " << next << endl;
	}

	sum = sum + next;
	cout << "SUM: " << sum << endl;

	while (sum < GOAL)
	{
		if (winner == User)
		{
			if (next == 0) //Check if the user abandoned the game.
			{
				sum = 31;
				winner = None;
			}
			else
			{
				next = computerDigit(last);
				last = next;
				sum = sum + next;
				cout << "I choose: " << next << endl;
				cout << "SUM: " << sum << endl;
				winner = Computer;
			}
		}

		else if (winner == Computer)
		{
			next = userDigit(last);
			if (next == 0) //Check if the user abandoned the game.
			{
				sum = 31;
				winner = None;
			}
			else
			{
				last = next;
				sum = sum + next;
				cout << "SUM: " << sum << endl;
				winner = User;
			}
		}
	}

	if (winner == Computer)
		winner = User;
	else if (winner == User)
		winner = Computer;

	return winner;
}

tPlayer whoStarts()
{
	int random, M = 2;
	tPlayer who;

	random = (rand() % M) + 1;

	if (random % 2 == 0)
	{
		cout << "I start!" << endl;
		who = Computer;
	}
	else
	{
		cout << "You start!" << endl;
		who = User;
	}

	return who;
}

bool sameRow(int last, int next)
{
	bool row = false;

		if ((last - 1) / 3 == (next - 1) / 3)
		{
			row = true;
		}

	return row;
}

bool sameColumn(int last, int next)
{
	bool column = false;

	if ((last - 1) % 3 == (next - 1) % 3)
	{
		column = true;
	}

	return column;
}

bool validDigit(int last, int next)
{
	bool validDigit = false;

	if (next != last)
	{
		if (sameColumn(last, next) == true || sameRow(last, next) == true || next == 0)
		{
			validDigit = true;
		}
	}
	return validDigit;
}

int randomNumber()
{
	int random, M = 9;

	random = (rand() % M) + 1;

	return random;
}

int computerDigit(int last)
{
	int next;

	next = randomNumber();
	while (validDigit(last, next) != true)
	{
		next = randomNumber();
	}
	return next;
}

int userInput()
{
	int next;
	bool ok = false;

	cout << setw(4) << "7" << setw(4) << "8" << setw(4) << "9" << endl;
	cout << setw(4) << "4" << setw(4) << "5" << setw(4) << "6" << endl;
	cout << setw(4) << "1" << setw(4) << "2" << setw(4) << "3" << endl;
	cout << "Please enter a digit (0 to end): ";
	cin >> next;
	while (ok = false)
	{
		if (next <= 9 && next >= 0)
		{
			ok = true;
		}
		else
		{
			cout << "Invalid digit! Try again." << endl;
		}
	}
	return next;
}

int userDigit(int last)
{
	int next;

	next = userInput();
	while (validDigit(last, next) != true)
	{
		cout << "Error: It must be different than " << last << " and in the same row or column" << endl;
		next = userInput();
	}
	return next;
}

bool display(string nameFile)
{
	bool display = false;
	string word;
	ifstream file;

	file.open(nameFile.c_str());

	if (file.is_open())
	{
		display = true;

		getline(file, word);
		while (word != "X")
		{
			cout << word;
			cout << "" << endl;
			getline(file, word);
		}
		file.close();
	}
	else
	{
		display = false;
	}


	return display;
}

bool reportUpdate(int matches, int won, int abandoned)
{
	int exe = 0, prevMatches = 0, prevWon = 0, prevAbandon = 0; //exe = number of executions
	bool report = true;
	ofstream outFile;
	ifstream inFile;

	inFile.open("reportPC.txt");
	if (inFile.is_open())
	{
		inFile >> exe;
		exe = exe + 1;;
		inFile >> prevMatches;
		matches = prevMatches + matches;
		inFile >> prevWon;
		won = prevWon + won;
		inFile >> prevAbandon;
		abandoned = prevAbandon + abandoned;

		outFile.open("reportPC.txt");
		if (!outFile.is_open())
		{
			report = false;
		}
		else
		{
			outFile << exe << endl;
			outFile << matches << endl;
			outFile << won << endl;
			outFile << abandoned << endl;

			outFile.close();
		}
		inFile.close();
	}
	else
	{
		cout << "Couldn't open the file to report" << endl;
	}
	

	return report;
}