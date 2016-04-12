/****************************************************************************/
/* Program: Project 3 - 2048                                                */
/* Version: 4                                                               */
/* Autor: Alberto Rodríguez - Rabadán Manzanares                            */
/* Date: 18/02/2015                                                         */
/****************************************************************************/

#include <iostream>
#include <iomanip>
#include <Windows.h>
#include <time.h>
#include <cstdlib>
#include <fstream>
#include <string>
#include <conio.h>
using namespace std;

typedef enum { Up, Down, Right, Left, Exit, None } tAction;
const int DIM = 4;
typedef int tGrid[DIM][DIM];

typedef struct
{
	string name;
	int points;
}tPlayer;

const int MAX = 10;
typedef tPlayer tArrayPlayers[MAX];

typedef struct
{
	int counter;
	tArrayPlayers players;
}tList;

//Prototypes
void init(tGrid grid); //Initialize the grid.
void printLine(int c1, int c2, int c3); //Display the upper, middle and lower border.
void display(const tGrid grid, int points, int totalPoints); //Display the entire grid.
int  log2(int num); //Counts the number of times one number can be divided by two.
void load(tGrid grid, int &totalPoints, bool &ok); //Load a grid from a file.
void setBackgroundColor(int color); //Set the tile's color.
void emptyGrid(tGrid grid); //Empties the grid.
tAction readAction(); //Read the direction buttons from the keyboard.
void getDirParameters(tAction direction, int counter, int &xini, int &yini, int &incrx, int &incry); //Get the parameters depending on the direction.
void moveTiles(tGrid grid, tAction direction, bool &newone); //Move the tiles. I have added the bool "newone" in order to check if a new tile should be added or no.
bool valid(int xini, int yini); //Check if the grid position is valid.
void mergeTiles(tGrid grid, tAction direction, int &points, bool &newone2); // Merge the same tiles. The bool "newone2" has the same function as newone.
bool sameValue(tGrid grid, int x, int y, int xini, int yini); //Check if two positions have the same value.
void newTile(tGrid grid); //Add a new tile.
bool full(const tGrid grid); //Check if the grid is full.
bool validNewTile(bool newone, bool newone2); //It check if any movement o merge has been realized. If so, then a new tile could be added.
int highest(const tGrid grid); //Check the highest value in the tile and return its number.
void updateHallOfFame(int totalPoints); //Updates the Hall of Fame.
void displayHallOfFame(); //Displays the Hall of Fame.
void save(const tGrid grid, int totalPoints); //Save the current match.
void insertionSortSwaps(tList &list); //Sorting the Hall of Fame list.

const int Empty = 1;
const int GOAL = 2048;
const string fileName = "HallOfFame.txt";

int main()
{
	tGrid grid;
	tAction direction;
	int points = 0, totalPoints = 0;
	char opt;
	bool ok = false; //File opened or not.
	bool newone, newone2; //Booleans to check if a new tile should appear or not.
	bool filled = false; //Grid full or not.

	srand(int(time(NULL)));

	cout << "Do you want to load a game? (y/n)";
	cin >> opt;
	while ((opt != 'y') && (opt != 'n')) // Checks if the user has introduced a valid value.
	{
		cout << "Invalid option! Try again...";
		cin >> opt;
	}

	if (opt == 'y')
	{
		load(grid, totalPoints, ok);

		if (ok) // If the file has been opened then it's displayed.
		{
			display(grid, points, totalPoints);
		}
		else // If not we initialize a new game.
		{
			cout << "Couldn't open the file, we will start a new game!" << endl;
			init(grid);
		}
	}
	else
	{
		init(grid);
	}

	direction = None;

	while ((direction != Exit) && !filled && (highest(grid) != GOAL)) // The game will repeat this until the user presses Exit, the grid is full or the user reach 2048
	{
		newone = false;
		newone2 = false;

		display(grid, points, totalPoints);
		direction = readAction();

		if (direction == Exit) //If player presses exit, ask to confirm
		{
			cout << "Are you sure you want to exit? [y/n]: ";
			cin >> opt;
			if (opt == 'n')
			{
				direction = None;
			}
			else //If player abandons the game, ask for save it
			{
				cout << "Do you want to save the game? [y/n]: ";
				cin >> opt;
				if (opt == 'y')
				{
					save(grid, totalPoints);
				}
				cout << "You abandoned the game... good bye!" << endl;
			}
		}
		else
		{
			//Slide and merge tiles
			moveTiles(grid, direction, newone);
			mergeTiles(grid, direction, points, newone2);
			moveTiles(grid, direction, newone);

			totalPoints += points; //Acumulate the move points to total points

			if (!full(grid) && validNewTile(newone, newone2)) //Check if the grid is not full and if a new tile should be added
			{
				newTile(grid);
			}
			else if (full(grid)) //If the grid is full but you stil can merge tiles you will
			{
				mergeTiles(grid, direction, points, newone2);
				if (!newone2) //If no tiles can be merged, then the grid is entirely full and the game should finish.
				{
					filled = true;
				}
				else //If some tiles has been merged then we should move the tiles to the correct position.
				{
					moveTiles(grid, direction, newone);
				}
			}
		}

		if (highest(grid) == GOAL) //If the player wins then show the Hall of Fame and update it
		{
			display(grid, points, totalPoints);
			cout << "Congratulations! You win!" << endl;
			updateHallOfFame(totalPoints);
			displayHallOfFame();
		}
	}
	system("pause");
	return 0;
}

void emptyGrid(tGrid grid)
{
	for (int row = 0; row < DIM; row++)
	{
		for (int col = 0; col < DIM; col++)
		{
			grid[row][col] = Empty;
		}
	}
}

void init(tGrid grid)
{
	int row, col;
	bool ok = false;

	emptyGrid(grid); 

	//Choose randomly a spot
	row = rand() % DIM;
	col = rand() % DIM;

	//95% of probability of being a 2
	if ((rand() % (100 + 1)) >= 95)
	{
		grid[row][col] = 4;
		ok = true;
	}
	else
	{
		grid[row][col] = 2;
	}

	row = rand() % DIM;
	col = rand() % DIM;

	while (grid[row][col] != Empty)
	{
		row = rand() % DIM;
		col = rand() % DIM;
	}

	if ((rand() % (100 + 1)) >= 95 && ok == false)
	{
		
		grid[row][col] = 4;
	}
	else
	{
		grid[row][col] = 2;
	}
}

void display(const tGrid grid, int points, int totalPoints)
{
	system("cls");
	setBackgroundColor(0);
	cout << endl;
	cout << setw(6) << points << setw(16) << "Total: " << setw(6) << totalPoints << endl;
	printLine(218, 194, 191);

	for (int row = 0; row < DIM; row++)
	{
		//1st
		for (int col = 0; col < DIM; col++)
		{
			cout << char(179);
			setBackgroundColor(log2(grid[row][col]));
			cout << "      ";
			setBackgroundColor(0);
		}
		cout << char(179) << endl;

		//2nd
		for (int col = 0; col < DIM; col++)
		{
			cout << char(179);
			setBackgroundColor(log2(grid[row][col]));

			if (grid[row][col] == 1)
			{
				cout << setw(7);
			}
			else
			{
				cout << setw(6) << grid[row][col];
			}
			setBackgroundColor(0);
		}
		cout << char(179) << endl;

		//3rd
		for (int col = 0; col < DIM; col++)
		{
			cout << char(179);
			setBackgroundColor(log2(grid[row][col]));
			cout << "      ";
			setBackgroundColor(0);
		}
		cout << char(179) << endl;

		if (row == DIM - 1)
		{
			printLine(192, 193, 217);
		}
		else
		{
			printLine(195, 197, 180);
		}
	}
	cout << endl;
	cout << "Use arrow keys (esc to exit)..." << endl;
}

void load(tGrid grid, int &totalPoints, bool &ok)
{
	ifstream file;
	int dim;
	string fileName;

	//Ask for a filename
	cout << "Please introduce a file name (ended in .txt): ";
	cin.sync(),
	getline(cin, fileName);

	file.open(fileName.c_str());

	if (file.is_open()) //If opened
	{
		//Read the dimension
		file >> dim;
		if (dim == DIM)
		{
			//Read the grid
			for (int row = 0; row < DIM; row++)
			{
				for (int col = 0; col < DIM; col++)
				{
					file >> grid[row][col];
				}
			}
			file >> totalPoints; //Read the total points
			file.close();
			ok = true;
		}
		else
		{
			ok = false;
		}
	}
	else
	{
		ok = false;
	}
}

void setBackgroundColor(int color) 
{
	HANDLE handle = GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(handle, 15 | (color << 4));
}

void printLine(int c1, int c2, int c3)
{
	cout << char(c1);
	for (int i = 0; i < DIM - 1; i++)
	{
		cout << char(196) << char(196) << char(196) << char(196) << char(196) << char(196) << char(c2);
	}
	cout << char(196) << char(196) << char(196) << char(196) << char(196) << char(196) << char(c3) << endl;
}

int log2(int num)
{
	int count = 0;

	while (num > 1)
	{
		num = num / 2;
		count++;
	}

	return count;
}

tAction readAction()
{
	tAction action = None;
	int aux;

	do
	{
		cin.sync();
		aux = _getch();
		if (aux == 0xe0)
		{
			aux = _getch();
			if (aux == 72)
				action = Up;
			else if (aux == 80)
				action = Down;
			else if (aux == 77)
				action = Right;
			else if (aux == 75)
				action = Left;
		}
		else if (aux == 27)
			action = Exit;
		else
			action = None;
	}
	while (action == None);

	return action;
}

void getDirParameters(tAction direction, int counter, int &xini, int &yini, int &incrx, int &incry)
{
	if (direction == Up)
	{
		xini = 0;
		yini = counter;
		incrx = 1;
		incry = 0;
	}
	else if (direction == Down)
	{
		xini = DIM - 1;
		yini = counter;
		incrx = -1;
		incry = 0;
	}
	else if (direction == Left)
	{
		xini = counter;
		yini = 0;
		incrx = 0;
		incry = 1;
	}
	else if (direction == Right)
	{
		xini = counter;
		yini = DIM - 1;
		incrx = 0;
		incry = -1;
	}
}

void moveTiles(tGrid grid, tAction direction, bool &newone)
{
	int xini, yini, incrx, incry, x, y, aux;
	bool found = false;

	for (int i = 0; i <= DIM - 1; i++)
	{
		getDirParameters(direction, i, xini, yini, incrx, incry);
		// We will use auxiliary variables to traverse the grid
		x = xini;
		y = yini;
		while (valid(x, y) && !found)
		{
			if (grid[x][y] != Empty) //If not empty
			{
				found = true;

				if (found && ((x != xini) || (y != yini))) //If an occupied tile has been found and not in the same spot
				{
					//Exchange the contents of the spots
					aux = grid[xini][yini];
					grid[xini][yini] = grid[x][y];
					grid[x][y] = aux;

					//Go to the next spot
					xini += incrx;
					yini += incry;
					x += incrx;
					y += incry;
					found = false;
					newone = true;
				}
				else //If not go to the next spot
				{
					xini += incrx;
					yini += incry;
					x += incrx;
					y += incry;
					found = false;
				}
			}
			else //If empty, go to next spot
			{
				x += incrx;
				y += incry;
			}
		}
	}
}

bool valid(int xini, int yini)
{
	bool ok = false;

	if ((xini >= 0) && (xini <= DIM - 1))
	{
		if ((yini >= 0) && (yini <= DIM - 1))
		{
			ok = true;
		}
	}

	return ok;
}

void mergeTiles(tGrid grid, tAction direction, int &points, bool &newone2)
{
	int xini, yini, incrx, incry, x, y;

	points = 0;
	for (int i = 0; i <= DIM - 1; i++)
	{
		getDirParameters(direction, i, xini, yini, incrx, incry);

		while (valid(xini, yini) && (grid[xini][yini] != Empty)) //Valid xini, yini and spot not free
		{
			//x and y wil be next spot coordinates
			x = xini;
			y = yini;
			x += incrx;
			y += incry;

			if (valid(x, y) && sameValue(grid, x, y, xini, yini)) //If there are equal tiles together, then merge it
			{
				grid[xini][yini] *= 2; //Double the value
				points += grid[xini][yini]; //Accumulate value for points
				grid[x][y] = Empty; //Empties the current grid
				//Go to next spot
				x += incrx;
				y += incry;
				newone2 = true;
				xini += incrx;
				yini += incry;

				while (valid(x, y) && grid[x][y] != Empty)
				{
					grid[x-incrx][y-incry] = grid[x][y]; //Move the tile to the previous spot
					grid[x][y] = Empty; //Free the current spot
					//Go to next spot
					x += incrx;
					y += incry;
				}
			}
			else //If not go to next spot
			{
				xini += incrx;
				yini += incry;
			}
		}
			xini += incrx;
			yini += incry;
	}
}

bool sameValue(tGrid grid, int x, int y, int xini, int yini)
{
	bool same = false;

	if (grid[xini][yini] == grid[x][y])
	{
		same = true;
	}

	return same;
}

bool full(const tGrid grid)
{
	bool full = false;
	bool free = false;

	int x, y;

	x = y = 0;

	while ((x <= DIM - 1) && !free)
	{
		while ((y <= DIM - 1) && !free)
		{
			if (grid[x][y] == Empty)
			{
				free = true;
			}
			else
			{
				y++;
			}
		}
		x++;
		y = 0;
	}
	if (!free)
	{
		full = true;

	}

	return full;
}

void newTile(tGrid grid)
{
	int row, col;

		row = rand() % DIM;
		col = rand() % DIM;

		while ((grid[row][col] != Empty) && valid(row, col))
		{
			row = rand() % DIM;
			col = rand() % DIM;
		}
		if (valid(row, col))
		{
			if ((rand() % (100 + 1)) >= 95)
			{
				grid[row][col] = 4;
			}
			else
			{
				grid[row][col] = 2;
			}
		}
}

bool validNewTile(bool newone, bool newone2)
{
	bool valid = false;

	// We check if some movement has been realized or if some tiles have been merged. If not, no new tile will appear
	if (newone || newone2)
	{
		valid = true;
	}

	return valid;
}

int highest(const tGrid grid)
{
	int highest = 0, next;

	for (int row = 0; row <= DIM - 1; row++)
	{
		for (int col = 0; col <= DIM - 1; col++)
		{
			next = grid[row][col];

			if (highest < next)
			{
				highest = next;
			}
		}
	}

	return highest;
}

void updateHallOfFame(int totalPoints)
{
	tList list;
	ifstream inputFile;
	ofstream outputFile;
	string name;

	inputFile.open(fileName.c_str());
	
	list.counter = 0;
	if (inputFile.is_open()) //If file is opened, read the list
	{
		inputFile >> name;
		while ((name != "???") && (list.counter < MAX))
		{
			list.players[list.counter].name = name;
			inputFile >> list.players[list.counter].points;
			list.counter++;
			inputFile >> name;
		}
		inputFile.close();

	}
	
	if ((list.counter < MAX) || (totalPoints > list.players[list.counter - 1].points)) //If the player has a record, save it
	{
		cout << "Great! You have one of the 10 best scores!" << endl;
		cout << "Please enter your name: ";
		cin >> name;

		if (list.counter < MAX) //If the list is not full, add a new player
		{
			list.players[list.counter].name = name;
			list.players[list.counter].points = totalPoints;
			list.counter++;
		}
		else //If the list is full, eliminate the last player adding the new one
		{
			list.players[list.counter - 1].name = name;
			list.players[list.counter - 1].points = totalPoints;
		}

		insertionSortSwaps(list); //Sort the list

		outputFile.open(fileName.c_str());
		if (outputFile.is_open()) //Write the updated list into the .txt file
		{
			for (int i = 0; i < list.counter; i++)
			{
				outputFile << list.players[i].name << " ";
				outputFile << list.players[i].points << endl;
			}
			outputFile << "???" << endl;
			outputFile.close();
		}
	}
	
}

void displayHallOfFame()
{
	int points;
	string name;
	ifstream file;

	cout << "*****************************" << endl;
	cout << "*** H A L L  O F  F A M E ***" << endl;
	cout << "*****************************" << endl;

	file.open(fileName.c_str());
	if (file.is_open())
	{
		file >> name;
		while (name != "???")
		{
			file >> points;
			cout << setw(20) << left << name;
			cout << points << endl;
			file >> name;
		}
	}
}

void save(const tGrid grid, int totalPoints)
{
	ofstream file;
	string fileName2;

	//Ask for a file name
	cout << "Please enter a file name (ended in .txt): ";
	cin >> fileName2;

	file.open(fileName2.c_str());

	if (file.is_open()) //If is opened, save the list in a nex .txt file
	{
		file << DIM << endl;
		for (int row = 0; row <= DIM - 1; row++)
		{
			for (int col = 0; col <= DIM - 1; col++)
			{
				file << grid[row][col] << endl;
			}
		}
		file << totalPoints;
	}
}

void insertionSortSwaps(tList &list)
{
	int  pos;
	tPlayer tmp;
	for (int i = 1; i < list.counter; i++)
	{
		pos = i;
		while ((pos > 0) && (list.players[pos - 1].points < list.players[pos].points))
		{
			tmp = list.players[pos];
			list.players[pos] = list.players[pos - 1];
			list.players[pos - 1] = tmp;
			pos--;
		}
	}
}