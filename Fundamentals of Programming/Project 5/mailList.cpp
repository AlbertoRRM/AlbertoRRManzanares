#include "mailList.h"
#include <string>
#include <fstream>
using namespace std;

void initialize(tMailList &mails)
{
	mails.list = new tMail[Size_Mails];
	mails.counter = 0;
	mails.capacity = Size_Mails;
}

bool load(tMailList &mails, string domain)
{
	bool loaded = false;
	ifstream file;

	file.open(domain + "_Mail.txt");
	if (file.is_open())
	{
		file >> mails.counter; //I have added the number of mails in the beginning of the .txt in order to know how much room I need to reserve
		mails.capacity = mails.counter;
		mails.list = new tMail[mails.capacity];
		for (int i = 0; i < mails.counter; i++)
		{
			load(mails.list[i], file);
		}
		loaded = true;
		file.close();
	}
	
	return loaded;
}

void save(const tMailList &mails, string domain)
{
	ofstream file;

	file.open(domain + "_Mail.txt");
	file << mails.counter; //Add the number of mails at the beginning of the .txt
	for (int i = 0; i < mails.counter; i++)
	{
		save(mails.list[i], file);
	}
	file.close();
}

void insert(tMailList &mails, const tMail &mail)
{

	if (mails.counter < mails.capacity)
	{
		mails.list[mails.counter] = mail;
		mails.counter++;
	}
	else //Increasing maximum size
	{
		tMailList aux = mails; //Create an auxiliary list to save the mails.

		mails.capacity = (mails.capacity * 3) / 2 + 1;
		mails.list = new tMail[mails.capacity];

		for (int i = 0; i < mails.counter; i++) //Copy the mail list from aux
		{
			mails.list[i] = aux.list[i];
		}

		destroy(aux); //Destroy the aux list

		insert(mails, mail); //Insert the new mail
	}
}

bool find(const tMailList &mails, string id, int &pos)
{

	int beg = 0, end = mails.counter - 1, middle;
	bool found = false;

	while ((beg <= end) && !found)
	{
		middle = (beg + end) / 2; 
		if (id == mails.list[middle].id)
			found = true;
		else if (id < mails.list[middle].id) 
			end = middle - 1;
		else 
			beg = middle + 1;
	} // If found, it is in [middle]
	pos = middle;

	return found;
}

void bubble(tMailList &mails)
{
	bool swap = true;
	int i = 0;
	// From 1st to second to last if there are swaps...
	while ((i < mails.counter - 1) && swap) 
	{
		swap = false;
		// From last to i + 1...
		for (int j = mails.counter - 1; j > i; j--) 
		{
			if (mails.list[j].id < mails.list[j - 1].id) 
			{
				tMail tmp;
				tmp = mails.list[j];
				mails.list[j] = mails.list[j - 1];
				mails.list[j - 1] = tmp;
				swap = true;
			}
		}
		if (swap) 
			i++;
	}
}

bool operator<(tMail leftMail, tMail rightMail)
{
	bool less = false;

	if (leftMail.subject < rightMail.subject)
		less = true;
	else if (leftMail.subject == rightMail.subject)
	{
		if (leftMail.date < rightMail.date)
			less = true;
	}

	return less;
}

void destroy(tMailList &mails)
{
	delete[] mails.list;
	mails.counter = 0;
}

// Main program to test MailList module
//int main()
// {
//	const string Domain = "fdimail.com";
//	tMailList mails;
//	tMail mail;
//	int pos;
//
//	initialize(mails);
//	if (load(mails, Domain)) 
//	{
//		cout << "Mail list loaded!" << endl;
//		if (find(mails, "javi@fdimail.com_1426614381", pos)) 
//		{
//			cout << "Mail with id javi@fdimail.com_1426614381 found in position " << pos << endl;
//		}
//		else 
//		{
//			cout << "Mail with id javi@fdimail.com_1426614381 NOT found; it would be in position " << pos << endl;
//		}
//		if (find(mails, "javi@fdimail.com_1426614382", pos)) 
//		{
//			cout << "Mail with id javi@fdimail.com_1426614382 found in position " << pos << endl;
//		}
//		else 
//		{
//			cout << "Mail with id javi@fdimail.com_1426614382 NOT found; it would be in position " << pos << endl;
//		}
//		newMail(mail, "javi@fdimail.com");
//		insert(mails, mail);
//		save(mails, Domain);
//		bubble(mails);
//	}
//	else 
//	{
//		cout << "Mail list NOT loaded!" << endl;
//	}
//
//	system("pause");
//	return 0;
//}
//