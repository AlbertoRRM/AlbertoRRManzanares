/****************************************************************************/
/* Program: Project 5 - Mail Server fdimail.com                             */
/* Version: 1                                                               */
/* Autor: Alberto Rodríguez - Rabadán Manzanares                            */
/* Date: 16/05/2015                                                         */
/****************************************************************************/

#include <iostream>
#include <string>
#include <iomanip>
using namespace std;
#include <vld.h>

#include "manager.h"

void menu(tManager &manager); //Displays the mailbox menu
void logInMenu(tManager &manager); //Display the log in menu
void destroyAll(tManager &manager); //Destroy the dynamic data

int main()
{
	tManager manager;

	if (start(manager, "fdimail.com"))
		cout << "Manager started with lists loaded" << endl;
	else
		cout << "Manager started with empty lists!" << endl;

	logInMenu(manager);
	stop(manager);
	destroyAll(manager);
	
	system("pause");
	return 0;
}

void menu(tManager &manager)
{
	bool box = true;; // True -> inbox, false -> outbox
	int opt = -1;

	system("cls");
	cout << "Welcome " << manager.users.list[manager.activeUser]->id << "!" << endl;
	system("pause");

	while (opt != 0)
	{
		//Displaying the mailbox
		system("cls");
		cout << endl;
		cout << "Mail of " << manager.users.list[manager.activeUser]->id << endl;
		if (box)
		{
			cout << "----------------------------------" << " Inbox " << "----------------------------------" << endl;
			cout << "---------------------------------------------------------------------------" << endl;
			cout << "U" << setw(4) << "#" << setw(7) << "SENDER" << setw(21) << "SUBJECT" << setw(37) << "DATE" << endl;
			cout << "---------------------------------------------------------------------------" << endl;
			manageSession(manager, manager.users.list[manager.activeUser]->mail_inbox);
		}
		else
		{
			cout << "----------------------------------" << " Outbox " << "---------------------------------" << endl;
			cout << "---------------------------------------------------------------------------" << endl;
			cout << "U" << setw(4) << "#" << setw(7) << "SENDER" << setw(21) << "SUBJECT" << setw(37) << "DATE" << endl;
			cout << "---------------------------------------------------------------------------" << endl;
			manageSession(manager, manager.users.list[manager.activeUser]->mail_outbox);
		}
		//Displaying the menu of the mailbox
		cout << "Choose an option:" << endl;
		cout << " 1.- Read mail" << endl;
		cout << " 2.- Send mail" << endl;
		cout << " 3.- Delete mail" << endl;
		if (box)
			cout << " 4.- See outbox" << endl;
		else
			cout << " 4.- See inbox" << endl;
		cout << " 5.- Quick read of unread mails" << endl;
		cout << " 0.- Close session" << endl;
		cin >> opt;

		switch (opt)
		{
			case 1: //Read mail
			{
				if (box)
					readMail(manager, manager.users.list[manager.activeUser]->mail_inbox);
				else
					readMail(manager, manager.users.list[manager.activeUser]->mail_outbox);
		

			} break;

			case 2: //Send mail
			{
				tMail mail;

				newMail(mail, manager.users.list[manager.activeUser]->id);
				sendMail(manager, mail);
			} break;

			case 3: //Delete mail
			{
				if (box)
					deleteMail(manager, manager.users.list[manager.activeUser]->mail_inbox);
				else
					deleteMail(manager, manager.users.list[manager.activeUser]->mail_outbox);
			} break;

			case 4: //See outbox / inbox
			{
				if (box)
					box = false;
				else
					box = true;
			} break;

			case 5: //Quick read of unread mails
			{
				system("cls");
				if (box)
					quickRead(manager, manager.users.list[manager.activeUser]->mail_inbox);
				else
					quickRead(manager, manager.users.list[manager.activeUser]->mail_outbox);
			} break;

			case 0: //Close session
			{
				cout << "The session has been closed" << endl;
				system("pause");
				system("cls");
			} break;
			default:
				cout << "Invalid option" << endl;
				break;
		}
	}
}


void logInMenu(tManager &manager)
{
	short int opt = -1;

	while (opt != 0)
	{
		cout << "Choose an option: " << endl;
		cout << " 1.- Log into the system" << endl;
		cout << " 2.- Create a new account" << endl;
		cout << " 0.- Exit " << endl;
		cin >> opt;

		switch (opt)
		{
			case 1: //Log into the system
			{
				if (initSession(manager))
					menu(manager);
				else
				{
					cout << "User or password not valid!" << endl;
					system("pause");
					system("cls");
				}
			} break;

			case 2: //Create new account
			{
				if (createAccount(manager))
				{
					cout << "A new user enters..." << endl;
					system("pause");
					system("cls");

					menu(manager);
				}
			} break;

			case 0: //Exit the program
			{
			} break;
			default:
				system("cls");
				cout << "Invalid option" << endl;
				break;
		}
	}
}

void destroyAll(tManager &manager)
{
	destroy(manager.mails);
	destroy(manager.users);
}