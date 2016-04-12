#include "user.h"
#include <string>
#include <fstream>
using namespace std;

void initialize(tUser &user, string id, string password)
{
	user.id = id;
	user.password = password;
	user.mail_inbox.counter = 0;
	user.mail_outbox.counter = 0;
}

bool load(tUser &user, ifstream &file)
{
	string line;
	bool loaded = false;

	file >> line;
	if ((line != USER_SENTINEL) && (line != "")) // (line != "") to check if the file is empty
	{
		user.id = line;
		file >> user.password;
		//Initialize his lists
		load(user.mail_inbox, file);
		load(user.mail_outbox, file);
		loaded = true;
	}

	return loaded;
}

void save(const tUser &user, ofstream &file)
{
	file << user.id << endl;
	file << user.password << endl;
	file << user.mail_inbox.counter << endl;
	for (int i = 0; i < user.mail_inbox.counter; i++) //Save the references (inbox)
	{
		file << user.mail_inbox.references[i].id << " " << user.mail_inbox.references[i].read << endl;
	}
	file << user.mail_outbox.counter << endl;
	for (int i = 0; i < user.mail_outbox.counter; i++) //Save the references (outbox)
	{
		file << user.mail_outbox.references[i].id << " " << user.mail_outbox.references[i].read << endl;
	}
}

// Main program to test User module
//int main()
//{
//	tUser user;
//	ifstream inFile;
//	ofstream outFile;
//
//	initialize(user, "ana@fdimail.com", "ana");
//	inFile.open("user.txt");
//	if (inFile.is_open())
//	{
//		load(user, inFile);
//		inFile.close();
//		outFile.open("user.txt");
//		user.password = "abc";
//		save(user, outFile);
//		outFile.close();
//	}
//	else 
//	{
//		cout << "File not found!" << endl;
//	}
//
//	system("pause");
//	return 0;
//}
