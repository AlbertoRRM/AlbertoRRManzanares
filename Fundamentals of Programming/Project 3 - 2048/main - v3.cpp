/****************************************************************************/
/* Program: Project 3 - 2048                                                */
/* Version: 2                                                               */
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

//Prototypes
void init(tGrid grid);
void printLine(int c1, int c2, int c3); //Display the upper, middle and lower border
void display(const tGrid grid, int points, int totalPoints);
int  log2(int num);
void load(tGrid grid, int &totalPoints, bool &ok);
void setBackgroundColor(int color);
void emptyGrid(tGrid grid);
tAction readAction();
void getDirParameters(tAction direction, int counter, int &xini, int &yini, int &incrx, int &incry);
void moveTiles(tGrid grid, tAction direction);
bool valid(int xini, int yini);
void mergeTiles(tGrid grid, tAction direction, int &points);
bool sameValue(tGrid grid, int x, int y, int xini, int yini);
void newTile(tGrid grid);
bool full(const tGrid grid);

const int Empty = 1;

int main()
{
	tGrid grid;
	tAction direction;
	int points = 0, totalPoints = 0;
	char opt;
	bool ok = false; //File opened or not

	srand(int(time(NULL)));

	cout << "Do you want to load a game? (y/n)";
	cin >> opt;
	while ((opt != 'y') && (opt != 'n'))
	{
		cout << "Invalid option! Try again...";
		cin >> opt;
	}

	if (opt == 'y')
	{
		load(grid, totalPoints, ok);
		if (ok)
		{
			display(grid, points, totalPoints);
		}
		else
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
	display(grid, points, totalPoints);

	while ((direction != Exit) && !full(grid))
	{
		direction = readAction();
		moveTiles(grid, direction);
		mergeTiles(grid, direction, points);
		moveTiles(grid, direction);
		newTile(grid);
		display(grid, points, totalPoints);
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

	row = rand() % DIM;
	col = rand() % DIM;

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

	cout << "Please introduce a file name (ended in .txt): ";
	cin.sync(),
		getline(cin, fileName);

	file.open(fileName.c_str());

	if (file.is_open())
	{
		file >> dim;
		if (dim == DIM)
		{
			for (int row = 0; row < DIM; row++)
			{
				for (int col = 0; col < DIM; col++)
				{
					file >> grid[row][col];
				}
			}
			file >> totalPoints;
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
	} while (action == None);

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

void moveTiles(tGrid grid, tAction direction)
{
	int xini, yini, incrx, incry, x, y, aux;
	bool found = false;

	for (int i = 0; i <= DIM - 1; i++)
	{
		getDirParameters(direction, i, xini, yini, incrx, incry);
		x = xini;
		y = yini;
		while (valid(x, y) && !found)
		{
			if (grid[x][y] != Empty)
			{
				found = true;

				if (found && ((x != xini) || (y != yini)))
				{
					aux = grid[xini][yini];
					grid[xini][yini] = grid[x][y];
					grid[x][y] = aux;

					xini += incrx;
					yini += incry;
					x += incrx;
					y += incry;
					found = false;
				}
				else
				{
					xini += incrx;
					yini += incry;
					x += incrx;
					y += incry;
					found = false;
				}
			}
			else
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

void mergeTiles(tGrid grid, tAction direction, int &points)
{
	int xini, yini, incrx, incry, x, y, aux;

	for (int i = 0; i <= DIM - 1; i++)
	{
		getDirParameters(direction, i, xini, yini, incrx, incry);
		x = xini;
		y = yini;

		while (valid(xini, yini) && (grid[x][y] != Empty))
		{
			x += incrx;
			y += incry;

			if (valid(x, y) && sameValue(grid, x, y, xini, yini))
			{
				grid[xini][yini] *= 2; //Double the value
				aux = grid[xini][yini]; //Accumulate value for points
				grid[x][y] = Empty;
				x += incrx;
				y += incry;

				while (valid(x, y) && grid[x][y] != Empty)
				{
					xini += incrx;
					yini += incry;
					grid[xini][yini] = Empty;
					x += incrx;
					y += incry;
				}
			}
			xini += incrx;
			yini += incry;
		}
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

	while (grid[row][col] != Empty)
	{
		row = rand() % DIM;
		col = rand() % DIM;
	}

	if ((rand() % (100 + 1)) >= 95)
	{
		grid[row][col] = 4;
	}
	else
	{
		grid[row][col] = 2;
	}
}