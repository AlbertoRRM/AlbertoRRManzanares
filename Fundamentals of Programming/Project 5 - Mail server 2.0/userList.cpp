#include "userList.h"
#include <string>
#include <fstream>
#include <iostream>
using namespace std;

void initialize(tUserList &users)
{
	users.counter = 0;
}

bool load(tUserList &users, string domain)
{
	ifstream file;
	tUser user;
	bool loaded = false;

	file.open(domain + "_Users.txt");
	if (file.is_open())
	{
		while (load(user, file))
		{
			users.list[users.counter] = new tUser(user);
			users.counter++;
		}
		if (users.counter != 0)
			loaded = true;

		file.close();
	}

	return loaded;
}

void save(const tUserList &users, string domain)
{
	ofstream file;

	file.open(domain + "_Users.txt");
	for (int i = 0; i < users.counter; i++)
	{
		save(*users.list[i], file);
	}
	file << USER_SENTINEL << endl;
	file.close();
}

bool add(tUserList &users, const tUser &user)
{
	bool added = false;
	tUser next;
	int pos;
	
	if ((users.counter < Max_Users) && !findUser(users, user.id, pos)) //If there is enough space in the users list and the users does not already exist
	{
		//Add the user
		users.list[users.counter] = new tUser (user);
		//Initialize his lists
		initialize(users.list[users.counter]->mail_inbox);
		initialize(users.list[users.counter]->mail_outbox);
		users.counter++;

		added = true;

		// From second element to last one...
		for (int i = 1; i < users.counter; i++) 
		{
			next = *users.list[i];
			pos = 0;
			while ((pos < i) && !(users.list[pos]->id > next.id))
			{
				pos++;
			}

			for (int j = i; j > pos; j--) 
			{
				*users.list[j] = *users.list[j - 1];
			}
			*users.list[pos] = next;
		}
	}
	else if (!(users.counter < Max_Users))
	{
		cout << "There is no more space for a new user" << endl;
		system("pause");
		system("cls");
	}
	else
	{
		cout << "The user already exists" << endl;
		system("pause");
		system("cls");
	}

	return added;
}

bool findUser(const tUserList &users, string id, int &pos)
{
	int beg = 0, end = users.counter - 1, middle = 0;
	bool found = false;

	while ((beg <= end) && !found)
	{
		middle = (beg + end) / 2;
		if (id == users.list[middle]->id)
			found = true;
		else if (id < users.list[middle]->id)
			end = middle - 1;
		else
			beg = middle + 1;
	} // If found, it is in [middle]
	pos = middle;

	return found;

}

void destroy(tUserList &users)
{
	for (int i = 0; i < users.counter; i++)
	{
		destroy(users.list[i]->mail_inbox);
		destroy(users.list[i]->mail_outbox);
		delete users.list[i];
	}

	users.counter = 0;
}

// Main program to test UserList module
//int main() {
//	tUser user;
//	tUserList users;
//	int pos;
//
//	initialize(users);
//	load(users, "fdimail.com");
//	if (findUser(users, "javi@fdimail.com", pos)) {
//		cout << "User javi@fdimail.com found in position " << pos << endl;
//	}
//	else {
//		cout << "User javi@fdimail.com NOT found; it would be in position " << pos << endl;
//	}
//	if (findUser(users, "rosa@fdimail.com", pos)) {
//		cout << "User rosa@fdimail.com found in position " << pos << endl;
//	}
//	else {
//		cout << "User rosa@fdimail.com NOT found; it would be in position " << pos << endl;
//	}
//	initialize(user, "ana@fdimail.com", "ana");
//	add(users, user);
//	save(users, "fdimail.com");
//
//	return 0;
//}