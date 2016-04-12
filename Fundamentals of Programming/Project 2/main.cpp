/****************************************************************************/
/* Program: Project 2 - Magic Tricks with Cards                             */
/* Version: 4                                                               */
/* Autor: Alberto Rodríguez - Rabadán Manzanares                            */
/* Date: 22/01/2015                                                         */
/****************************************************************************/
#include <iostream>
#include <string>
#include <fstream>
#include <cstdlib>
#include <ctime>
using namespace std;

typedef int tCard;
typedef enum { spades, clubs, diamonds, hearts } tSuit;
typedef enum { ace, two, three, four, five, six, seven, eight, nine, ten, jack, queen, king } tNumber;

const int CardsInSuit = 13;
const int MaxCards = 53;
const tCard Sentinel = 52;

typedef tCard tDeck[MaxCards]; 

//Prototypes
int menu(); //Displays the menu
tSuit theSuit(tCard card); //Returns the suit of the card
tNumber theNumber(tCard card); //Returns the number of the card
void emptyDeck(tDeck deck); //Clears the deck
int cardsInDeck(const tDeck deck); //Return the number of the cards in the deck	
void writeCard(tCard card); //Displays the card
void writeDeck(const tDeck deck); //Displays the deck
bool openFile(string &fileName, ifstream &inputFile); //Opens a file
bool loadDeck(tDeck deck); //Loads a deck
void saveDeck(const tDeck deck); //Saves a deck
void shuffleDeck(tDeck deck); //Shuffles a deck
void splitBlackRed(const tDeck deck, tDeck black, tDeck red); //Splits a deck into two: red cards and black cards
void splitLowHigh(const tDeck deck, tDeck low, tDeck high); //Splits a deck into two: low cards and high cards 
void splitAlternatively(const tDeck deck, int numHeaps, int chosen, tDeck chosenHeap); //Split a deck into a given number of heaps, then displays one
bool join(tDeck deck, const tDeck other); //Joins two decks
void threeHeapTrick();//Three Heap Trick
bool extractCards(tDeck deck, int howMany, tDeck newDeck); //Extracts cards from a deck
void cutDeck(tDeck deck, int howMany); //Cuts a deck
void innTrick(); //Inn Trick
void splitEvenOdd(const tDeck deck, tDeck even, tDeck odd); //Splits a deck into two: even cards and odd cards
void splitFaceNumber(const tDeck deck, tDeck face, tDeck num); //Splits a deck into two: face cards and num cards
void suspiciousPlayerTrick();

int main()
{
	tDeck deck, black, red, low, high, chosenHeap, other, newDeck;
	int opt = -1, numHeaps, chosen, howMany;
	char save;
	bool loaded = false;

	srand(int(time(NULL)));

	while (opt != 0)
	{
		opt = menu();
		switch (opt)
		{
		case 1:
		{
			if (loadDeck(deck))
			{
				cout << "\nLoaded Deck: ";
				writeDeck(deck);
				loaded = true;
			}
			else
			{
				cout << "Couldn't open the file!" << endl;
				emptyDeck(deck);
			}
		}
		break;

		case 2:
		{
			if (loaded)
			{
				cout << "Current deck: ";
				writeDeck(deck);
			}

			else
			{
				cout << "Error! The deck hasn't been loaded!" << endl;
			}
		}
		break;

		case 3:
		{
			if (loaded)
			{
				shuffleDeck(deck);
				cout << "Shuffled deck: ";
				writeDeck(deck);
				cout << "Do you want to save this deck? (y/n)";
				cin >> save;
				if (save == 'y')
					saveDeck(deck);
			}

			else
			{
				cout << "Error! The deck hasn't been loaded!" << endl;
			}
		}
		break;

		case 4:
		{
			if (loaded)
			{
				splitBlackRed(deck, black, red);

				cout << "Red cards deck: ";
				writeDeck(red);
				cout << "Black cards deck: ";
				writeDeck(black);
			}

			else
			{
				cout << "Error! The deck hasn't been loaded!" << endl;
			}
		}
		break;

		case 5:
		{
			if (loaded)
			{
				splitLowHigh(deck, low, high);

				cout << "Low cards deck: ";
				writeDeck(low);
				cout << "High cards deck: ";
				writeDeck(high);
			}

			else
			{
				cout << "Error! The deck hasn't been loaded!" << endl;
			}
		}
		break;

		case 6:
		{
			if (loaded)
			{
				cout << "Shuffling..." << endl;
				shuffleDeck(deck);
				cout << "Current deck: ";
				writeDeck(deck);
				cout << "Please, enter number of players: ";
				cin >> numHeaps;
				cout << "Choose whichone do you want to see(1 - " << numHeaps << "): ";
				cin >> chosen;
				cout << endl;
				splitAlternatively(deck, numHeaps, chosen, chosenHeap);
				writeDeck(chosenHeap);
			}

			else
			{
				cout << "Error! The deck hasn't been loaded!" << endl;
			}
		}
		break;

		case 7:
		{
			if (loaded)
			{
				cout << "Loading second deck..." << endl;
				if (loadDeck(other))
				{
					if (join(deck, other))
					{
						cout << "Join: ";
						writeDeck(deck);
					}

					else
					{
						cout << "\nError! The join of the decks exceeds the limit!" << endl;
					}
				}
				else
				{
					cout << "Couldn't open the file!" << endl;
					emptyDeck(other);
				}
			}

			else
			{
				cout << "Error! A first deck hasn't been loaded!" << endl;
			}
		}
		break;

		case 8:
		{
			threeHeapTrick();
		}
		break;

		case 9:
		{
			if (loaded)
			{
				cout << "How many cards do will extract?: ";
				cin >> howMany;
				if (extractCards(deck, howMany, newDeck))
				{
					cout << "Original deck after extracting: ";
					writeDeck(deck);
					cout << "New deck with extracted cards:	";
					writeDeck(newDeck);
				}
				else
				{
					cout << "Error! This number is higher than cards in deck or lower than 1" << endl;
				}
			}
			
			else
			{
				cout << "The deck hasn't been loaded!" << endl;
			}
		}
		break;

		case 10:
		{
			if (loaded)
			{
				cout << "In which card is going to be cutted?: ";
				cin >> howMany;
				cutDeck(deck, howMany);
				cout << "Cutted deck: ";
				writeDeck(deck);
			}

			else
			{
				cout << "The deck hasn't been loaded!" << endl;
			}
		}
		break;

		case 11:
		{
			innTrick();
		}
		break;

		case 12:
		{
			suspiciousPlayerTrick();
		}
		break;
		}
		cout << endl;
	}
	system("pause");
	return 0;
}

int menu()
{
	int opt = -1;
	const int NumOp = 12;

	do
	{
		cout << "0. Exit the program" << endl;
		cout << "1. Load a card deck" << endl;
		cout << "2. Display the card deck" << endl;
		cout << "3. Shuffle and save deck" << endl;
		cout << "4. Split the deck: black and red cards" << endl;
		cout << "5. Split the deck: low (A-7) and high (8-K) cards" << endl;
		cout << "6. Split into several decks and return one of them" << endl;
		cout << "7. Join with other deck" << endl;
		cout << "8. Three heap trick" << endl;
		cout << "9. Extract cards from the deck" << endl;
		cout << "10. Cut the deck" << endl;
		cout << "11. Inn Trick" << endl;
		cout << "12. Suspicious Player Trick" << endl;
		cout << "Your option: ";
		cin >> opt;
		cout << endl;

		if ((opt < 0) || (opt > NumOp))
		{
			cout << "Wrong option. Try again " << endl;
			cout << endl;
		}
	} while ((opt < 0) || (opt > NumOp));

	return opt;
}

tSuit theSuit(tCard card)
{
	return tSuit(card / CardsInSuit);
}

tNumber theNumber(tCard card)
{
	return tNumber(card % CardsInSuit);
}


void emptyDeck(tDeck deck)
{
	int i = 0;
	deck[i] = Sentinel;
}

int cardsInDeck(const tDeck deck)
{
	int count = 0;

	while (deck[count] != Sentinel)
	{
		count++;
	}

	return count;
}


void writeCard(tCard card)
{
	int num = card % CardsInSuit + 1;

	switch (num)
	{
	case 1:
	{
		cout << 'A';
	}
	break;

	case 11:
	{
		cout << 'J';
	}
	break;

	case 12:
	{
		cout << 'Q';
	}
	break;

	case 13:
	{
		cout << 'K';
	}
	break;

	default:
	{
		cout << num;
	}

	}

	num = card / CardsInSuit;

	cout << char(6 - num); //6 - Spades; 5 - Clubs; 4 - Diamonds; 3 - Hearts
	
}


void writeDeck(const tDeck deck)
{
	int i = 0;

	while (deck[i] != Sentinel)
	{
		writeCard(deck[i]);
		i++;
		cout << " ";
	}
	cout << endl;
}

bool openFile(string &fileName, ifstream &inputFile)
{
	bool ok = false;
	int count = 0;

	while ((count < 3) && !ok)
	{
		cout << "Please enter a file name (ended in .txt): ";
		cin.sync();
		getline(cin, fileName);

		inputFile.open(fileName.c_str()); 
		if (inputFile.is_open())
		{
			ok = true;
		}

		else
		{
			cout << endl;
			cout << "Error, try again! " << 2 - count << " left" << endl;
			count++;
		}
	}

	return ok;
}

bool loadDeck(tDeck deck)
{
	bool ok = false;
	string fileName;
	ifstream inputFile;
	char suit;
	int num, i = 0;
	tCard card;

	if (openFile(fileName, inputFile))
	{
		inputFile >> suit;
		while (suit != 'x')
		{
			if (i < MaxCards - 1)
			{
				inputFile >> num;
				if (suit == 's')
				{
					card = 0;
				}
				else if (suit == 'c')
				{
					card = 1;
				}
				else if (suit == 'd')
				{
					card = 2;
				}
				else if (suit == 'h')
				{
					card = 3;
				}
				card = (CardsInSuit * card) + num - 1;
				deck[i] = card;
				i++;
			}
			inputFile >> suit;
		}
		deck[i] = Sentinel;
		inputFile.close();
		ok = true;
	}

	return ok;
}

void saveDeck(const tDeck deck)
{
	ofstream outputFile;
	string nameFile;

	cout << "Please enter a name for the file (ended in .txt): ";
	cin >> nameFile;

	outputFile.open(nameFile.c_str());
	for (int i = 0; i < cardsInDeck(deck); i++)
	{
		int num = deck[i] / CardsInSuit;

		switch (num)
		{
		case 0:
		{
			outputFile << 's' << " ";
		}
		break;

		case 1:
		{
			outputFile << 'c' << " ";
		}
		break;

		case 2:
		{
			outputFile << 'd' << " ";
		}
		break;

		default:
		{
			outputFile << 'h' << " ";
		}
		break;
		}
		num = deck[i] % CardsInSuit + 1;
		outputFile << num << endl;
	}
	outputFile << 'X';
	outputFile.close();
}

void shuffleDeck(tDeck deck)
{
	const int exchanges = 500;
	int random, random2, num, aux;

	num = cardsInDeck(deck);

	for (int i = 0; i < exchanges; i++)
	{
		random = (rand() % (num));
		random2 = (rand() % (num));

		aux = deck[random];
		deck[random] = deck[random2];
		deck[random2] = aux;
	}
	deck[num] = Sentinel;
}

void splitBlackRed(const tDeck deck, tDeck black, tDeck red)
{
	int index = 0, blackIndex = 0, redIndex = 0;
	int num;

	while (deck[index] != Sentinel)
	{
		num = deck[index];

		if (num <= 25 && num >= 0)
		{
			black[blackIndex] = deck[index];
			blackIndex++;
		}

		else if (num <= 52 && num >= 26)
		{
			red[redIndex] = deck[index];
			redIndex++;
		}
		index++;
	}
	red[redIndex] = Sentinel;
	black[blackIndex] = Sentinel;
}

void splitLowHigh(const tDeck deck, tDeck low, tDeck high)
{
	int index = 0, lowIndex = 0, highIndex = 0;
	int num;

	while (deck[index] != Sentinel)
	{
		num = deck[index] % CardsInSuit + 1;
		if ((num >= 0) && (num <= 7))
		{
			low[lowIndex] = deck[index];
			lowIndex++;
		}
		else if ((num >= 8) && (num <= 13))
		{
			high[highIndex] = deck[index];
			highIndex++;
		}
		index++;
	}
	low[lowIndex] = Sentinel;
	high[highIndex] = Sentinel;
}

void splitAlternatively(const tDeck deck, int numHeaps, int chosen, tDeck chosenHeap)
{
	int i = 0;

	while ((numHeaps * i + chosen - 1) < cardsInDeck(deck))
	{
		chosenHeap[i] = deck[numHeaps * i + chosen - 1];
		i++;
	}
	chosenHeap[i] = Sentinel;
}

bool join(tDeck deck, const tDeck other)
{
	bool join = false;
	int aux;

	aux = cardsInDeck(deck);

	for (int i = 0; i <= cardsInDeck(other); i++)
		{
			deck[aux] = other[i];
			aux++;
		}
	deck[aux] = Sentinel;

	if ((cardsInDeck(deck) + cardsInDeck(other)) < MaxCards - 1)
	{
		join = true;
	}

	return join;
}

void threeHeapTrick()
{
	tDeck mainDeck, split1, split2, split3;
	int numHeaps = 3, chosenHeap;

	if (loadDeck(mainDeck))
	{
		shuffleDeck(mainDeck);

		for (int i = 0; i < 3; i++)
		{
			//Dealing the deck into 3 different ones
			splitAlternatively(mainDeck, numHeaps, 1, split1);
			splitAlternatively(mainDeck, numHeaps, 2, split2);
			splitAlternatively(mainDeck, numHeaps, 3, split3);

			//Showing the deck
			cout << "Deck 1: ";
			writeDeck(split1);
			cout << "Deck 2: ";
			writeDeck(split2);
			cout << "Deck 3: ";
			writeDeck(split3);

			cout << "\nIn which deck is your card?: ";
			cin >> chosenHeap;

			emptyDeck(mainDeck);

			//Joining the deck
			if (chosenHeap == 1)
			{
				join(mainDeck, split2);
				join(mainDeck, split1);
				join(mainDeck, split3);
			}

			else if (chosenHeap == 2)
			{
				join(mainDeck, split1);
				join(mainDeck, split2);
				join(mainDeck, split3);
			}

			else if (chosenHeap == 3)
			{
				join(mainDeck, split1);
				join(mainDeck, split3);
				join(mainDeck, split2);
			}
		}
		cout << "You card is: ";
		writeCard(mainDeck[10]);
		cout << endl;
	}
	
	else
	{
		cout << "Couldn't open the file!" << endl;
	}
}

bool extractCards(tDeck deck, int howMany, tDeck newDeck)
{
	bool extract;
	int len, i, l;

	len = cardsInDeck(deck);
	if (howMany > len || howMany < 1)
	{
		extract = false;
	}

	else
	{
		//Extracting cards
		for (i = 0; i < howMany; i++)
		{
			newDeck[i] = deck[i];
		}
		newDeck[i] = Sentinel;
		
		//Deleting cards
		for (l = 0; l <= (len - howMany); l++)
		{
			deck[l] = deck[howMany + l];
		}
		deck[l] = Sentinel;

		extract = true;
	}
	return extract;
}

void cutDeck(tDeck deck, int howMany)
{
	int len, i, l;
	tDeck aux;

	len = cardsInDeck(deck);
	if (howMany < len)
	{
		for (i = 0; i < howMany; i++)
		{
			aux[i] = deck[i];
		}
		aux[i] = Sentinel;

		for (l = 0; l <= (len - howMany); l++)
		{
			deck[l] = deck[howMany + l];
		}
		deck[l] = Sentinel;

		join(deck, aux);
	}
}

void innTrick()
{
	tDeck mainDeck, room1, room2, room3, room4;
	int numHeaps = 4, number;

	if (loadDeck(mainDeck))
	{
		//Dealing the deck into the 4 different rooms
		splitAlternatively(mainDeck, numHeaps, 1, room1);
		splitAlternatively(mainDeck, numHeaps, 2, room2);
		splitAlternatively(mainDeck, numHeaps, 3, room3);
		splitAlternatively(mainDeck, numHeaps, 4, room4);

		//Showing the rooms
		cout << "Room 1: ";
		writeDeck(room1);
		cout << "Room 2: ";
		writeDeck(room2);
		cout << "Room 3: ";
		writeDeck(room3);
		cout << "Room 4: ";
		writeDeck(room4);

		emptyDeck(mainDeck);

		//Joining the rooms into the main deck
		join(mainDeck, room1);
		join(mainDeck, room2);
		join(mainDeck, room3);
		join(mainDeck, room4);

		cout << "In which card is going to be the deck cutted?:	";
		cin >> number;

		cutDeck(mainDeck, number);

		//Dealing again the main deck into the four rooms
		splitAlternatively(mainDeck, numHeaps, 1, room1);
		splitAlternatively(mainDeck, numHeaps, 2, room2);
		splitAlternatively(mainDeck, numHeaps, 3, room3);
		splitAlternatively(mainDeck, numHeaps, 4, room4);

		//Showing the rooms
		cout << "Room 1: ";
		writeDeck(room1);
		cout << "Room 2: ";
		writeDeck(room2);
		cout << "Room 3: ";
		writeDeck(room3);
		cout << "Room 4: ";
		writeDeck(room4);
	}

	else
	{
		cout << "Couldn't open the file!" << endl;
	}
}

void splitEvenOdd(const tDeck deck, tDeck even, tDeck odd)
{
	int index = 0, evenIndex = 0, oddIndex = 0;
	int num;

	while (deck[index] != Sentinel)
	{
		num = deck[index] % CardsInSuit + 1;
		if ((num % 2) == 0)
		{
			even[evenIndex] = deck[index];
			evenIndex++;
		}
		else if ((num % 2) != 0)
		{
			odd[oddIndex] = deck[index];
			oddIndex++;
		}
		index++;
	}
	even[evenIndex] = Sentinel;
	odd[oddIndex] = Sentinel;
}

void splitFaceNumber(const tDeck deck, tDeck face, tDeck num)
{
	int index = 0, faceIndex = 0, numIndex = 0;
	int numb;

	while (deck[index] != Sentinel)
	{
		numb = deck[index] % CardsInSuit + 1;
		if (((numb >= 11) && (numb <= 13)) || numb == 1)
		{
			face[faceIndex] = deck[index];
			faceIndex++;
		}
		else
		{
			num[numIndex] = deck[index];
			numIndex++;
		}
		index++;
	}
	face[faceIndex] = Sentinel;
	num[numIndex] = Sentinel;
}

void suspiciousPlayerTrick()
{
	tDeck mainDeck, player1, player2, player3, player4, left1, left2, left3, left4, right1, right2, right3, right4;
	int num, numHeaps = 4, i;

	if (loadDeck(mainDeck))
	{
		num = cardsInDeck(mainDeck);
		cout << "Current Deck: ";
		writeDeck(mainDeck);
		cout << "\nShuffling...    Dealing..." << endl;
		shuffleDeck(mainDeck);

		//Dealing the initial deck to each player
		splitAlternatively(mainDeck, numHeaps, 1, player1);
		splitAlternatively(mainDeck, numHeaps, 2, player2);
		splitAlternatively(mainDeck, numHeaps, 3, player3);
		splitAlternatively(mainDeck, numHeaps, 4, player4);

		//Displaying the decks
		cout << "\nInitial cards for each player: " << endl;
		cout << "\nPlayer 1: ";
		writeDeck(player1);
		cout << "Player 2: ";
		writeDeck(player2);
		cout << "Player 3: ";
		writeDeck(player3);
		cout << "Player 4: ";
		writeDeck(player4);

		for (i = 0; i < 2; i++)
		{
			//Mixing the decks
			splitBlackRed(player1, left1, right1);
			splitLowHigh(player2, left2, right2);
			splitEvenOdd(player3, left3, right3);
			splitFaceNumber(player4, left4, right4);

			emptyDeck(player1);
			emptyDeck(player2);
			emptyDeck(player3);
			emptyDeck(player4);

			join(player1, left2);
			join(player2, left3);
			join(player3, left4);
			join(player4, left1);

			join(player1, right4);
			join(player2, right1);
			join(player3, right2);
			join(player4, right3);

			//Displaying after mix the decks
			cout << "\nPlayer's cards after round: " << i + 1 << endl;
			cout << "\nPlayer 1: "; 
			writeDeck(player1);
			cout << "Player 2: ";
			writeDeck(player2);
			cout << "Player 3: ";
			writeDeck(player3);
			cout << "Player 4: ";
			writeDeck(player4);
		}
		
		//Mixing the decks for last time
		splitBlackRed(player1, left1, right1);
		splitLowHigh(player2, left2, right2);
		splitEvenOdd(player3, left3, right3);
		splitFaceNumber(player4, left4, right4);

		emptyDeck(player1);
		emptyDeck(player2);
		emptyDeck(player3);
		emptyDeck(player4);

		join(player1, left2);
		join(player2, left3);
		join(player3, left4);
		join(player4, left1);

		join(player1, right1);
		join(player2, right2);
		join(player3, right3);
		join(player4, right4);

		//Displaying the final decks
		cout << "\nPlayer's cards after round: " << i + 1 << endl;
		cout << "\nPlayer 1: ";
		writeDeck(player1);
		cout << "Player 2: ";
		writeDeck(player2);
		cout << "Player 3: ";
		writeDeck(player3);
		cout << "Player 4: ";
		writeDeck(player4);
	}

	else
	{
		cout << "Couldn't open the file!" << endl;
	}
}