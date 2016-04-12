#include "manager.h"
#include <iostream>
#include <iomanip>
#include <string>
#include <fstream>
#include <sstream>
using namespace std;

bool start(tManager &manager, string domain)
{
	bool initialized = false;

	manager.domain = domain;
	//Initialize the lists
	initialize(manager.users);
	initialize(manager.mails);
	if (load(manager.users, manager.domain))
	{
		if (load(manager.mails, manager.domain))
			initialized = true;
	}

	return initialized;
}

void stop(const tManager &manager)
{
	//Save all the information into the files
	save(manager.users, manager.domain);
	save(manager.mails, manager.domain);
}

bool createAccount(tManager &manager)
{
	bool created = false;
	tUser user;
	int pos;

	//Enter user's name and password
	cout << "User's name: ";
	cin >> user.id;
	user.id += "@" + manager.domain;
	cout << "Password: ";
	cin >> user.password;

	if (add(manager.users, user)) //If there is space and the user does not exist
	{
		created = true;
	}

	findUser(manager.users, user.id, pos);
	manager.activeUser = pos; //The active user is the new one.

	return created;
}

bool initSession(tManager &manager)
{
	bool logged = false;
	string id, password;
	int pos;

	//Enter user information to log in
	cout << "Enter user: ";
	cin >> id;
	id = id + "@" + manager.domain;
	cout << "Enter password: ";
	cin >> password;

	if ((manager.users.counter != 0) && findUser(manager.users, id, pos)) //If the user exists and the password is correct
	{
		if (manager.users.list[pos]->password == password)
		{
			logged = true;
			manager.activeUser = pos;
		}
	}

	return logged;
}

void manageSession(tManager &manager, tMailRefList mailbox)
{
	int pos;

	//Display the mailbox
	for (int i = 0; i < mailbox.counter; i++)
	{
		if (mailbox.references[i].read == false)
			cout << left << "*";
		else
			cout << " ";
		cout << setw(4) << right << i + 1;
		find(manager.mails, mailbox.references[i].id, pos);
		cout << headerToStr(manager.mails.list[pos]) << endl;
	}
	cout << "---------------------------------------------------------------------------" << endl;

}

void readMail(tManager &manager, tMailRefList &mailbox)
{
	int pos, opt;
	tMail mail;

	cout << "Enter the mail number to read: ";
	cin >> pos;
	pos = pos - 1; // The real position is pos - 1, because the user sees the position pos + 1 (in order to avoid the 0 in a list)

	
	readMail(mailbox, mailbox.references[pos].id);
	if (find(manager.mails, mailbox.references[pos].id, pos))
	{
		system("cls");
		cout << toString(manager.mails.list[pos]);
		//Answer menu
		cout << "----------------------------------------" << endl;
		cout << "Choose an option:" << endl;
		cout << " 1.- Answer mail" << endl;
		cout << " 0.- Back to the mailbox" << endl;
		cout << "----------------------------------------" << endl;
		cin >> opt;
		switch (opt)
		{
			case 1:
			{
				answerMail(manager.mails.list[pos], mail, manager.users.list[manager.activeUser]->id);
				sendMail(manager, mail);
			} break;
		}

	}
	else
	{
		cout << "Invalid mail number!" << endl;
		system("pause");
		system("cls");
	}
}

void sendMail(tManager &manager, const tMail &mail)
{
	tMailRef ref;
	int pos;

  //If the user exists...
	if (findUser(manager.users, mail.address, pos))
	{
		//Insert the mail in the list of mails and the references in their corresponding list
		insert(manager.mails, mail);
		ref.id = mail.id;
		ref.read = false;
		insertRef(manager.users.list[manager.activeUser]->mail_outbox, ref);
		insertRef(manager.users.list[pos]->mail_inbox, ref);
		bubble(manager.mails);
	}
	else
	{
		cout << "User does not exist! " << endl;
		system("pause");
	}
}

void deleteMail(tManager &manager, tMailRefList &mailbox)
{
	int pos;

	cout << "Enter the mail number to delete: ";
	cin >> pos;
	pos = pos - 1;

	if (!deleteRef(mailbox, mailbox.references[pos].id)) //To take into account: pos + 1;
		cout << "Mail couldn't be deleted!" << endl;
}

void quickRead(tManager &manager, tMailRefList& mailbox)
{
	int pos, aux;
	bool read = false;
	tMailList mails; //Auxiliary list of the unread mails

	//Initializing auxiliary list
	mails.capacity = manager.mails.capacity;
	mails.list = new tMail[mails.capacity];
	mails.counter = 0;
	

	//bubble(manager.mails); //We order the list of mails to find properly in it

	//Filling the list with the unread mails
	for (int i = 0; i < mailbox.counter; i++)
	{
		if (!mailbox.references[i].read)
		{
			find(manager.mails, mailbox.references[i].id, pos);
			mails.list[mails.counter] = manager.mails.list[pos];
			mails.counter++;
		}
	}

	//Deleting the "RE:"
	for (int i = 0; i < mails.counter; i++)
	{
		aux = mails.list[i].subject.rfind("RE:");
		while (aux != -1)
		{
			mails.list[i].subject.erase(0, aux + 3);
			aux = mails.list[i].subject.rfind("RE:");
		}
	}
	
	//Displaying the mails
	bubble(mails);
	for (int i = 0; i < mails.counter; i++)
	{
		if (find(mails, mails.list[i].id, pos)) //If the mail has been found, return its position in the list
		{	
			read = true;
			readMail(mailbox, mails.list[pos].id);
			cout << toString(mails.list[pos]);
		}
	}
	
	if (!read)
		cout << "There are no unread mails in this list!" << endl;
	destroy(mails);
	system("pause");
}

// Main program to test Manager module
//int main() {
//	tManager manager;
//
//	if (start(manager, "fdimail.com")) {
//		cout << "Manager started with lists loaded" << endl;
//	}
//	else {
//		cout << "Manager started with empty lists!" << endl;
//	}
//	if (initSession(manager)) {
//		cout << "Welcome " << manager.users.list[manager.activeUser].id << "!" << endl;
//		manageSession(manager);
//	}
//	else {
//		cout << "User or password not valid!" << endl;
//	}
//	if (createAccount(manager)) {
//		cout << "A new user enters..." << endl;
//		manageSession(manager);
//	}
//	else {
//		cout << "New user couldn't be created!" << endl;
//	}
//	stop(manager);
//
//	system("pause");
//	return 0;
//}