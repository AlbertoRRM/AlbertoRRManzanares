#include "mailRefList.h"
#include <fstream>
#include <string>
#include <iomanip>
using namespace std;


void initialize(tMailRefList &refList)
{
	refList.references = new tMailRef[Size_Ref];
	refList.counter = 0;
	refList.capacity = Size_Ref;
}

void load(tMailRefList &refList, ifstream &file)
{
	//Initialize the list
	file >> refList.counter;
	refList.capacity = refList.counter;
	refList.references = new tMailRef[refList.capacity];
	//Read elements from the file
	for (int i = 0; i < refList.counter; i++)
	{
		file >> refList.references[i].id;
		file >> refList.references[i].read;
	}
}

void save(const tMailRefList &refList, ofstream &file)
{
	file << refList.counter << endl;
	for (int i = 0; i < refList.counter; i++)
	{
		file << refList.references[i].id << setw(8) << right << noboolalpha << refList.references[i].read << endl;
		
	}
}

void insertRef(tMailRefList &refList, tMailRef ref)
{
	if (refList.counter < refList.capacity) //If there is enough space in the list
	{
		refList.references[refList.counter] = ref;
		refList.counter++;
	}
	else // Increasing maximum size
	{
		tMailRefList aux = refList; //Create auxiliary list to save the mails.

		refList.capacity = (refList.capacity * 3) / 2 + 1;
		refList.references = new tMailRef[refList.capacity];

		for (int i = 0; i < refList.counter; i++) //Copy mails list into aux list
		{
			refList.references[i] = aux.references[i];
		}

		destroy(aux); //Destroy aux list

		insertRef(refList, ref); //Insert the new mail
	}
}

bool deleteRef(tMailRefList &refList, string id)
{
	bool deleted = false;
	int pos;
	
	pos = find(refList, id);
	if (pos != -1) //If found, delete it and move all references one position to the left.
	{
		refList.references[pos].id = "";
		refList.references[pos].read = false;
		for (int i = pos; i < refList.counter - 1; i++) 
		{
			refList.references[i] = refList.references[i + 1];
		}
		refList.counter--;

		if (refList.capacity > (refList.counter + 20)) //Decreasing the maximum size
		{
			tMailRefList aux = refList;

			refList.capacity = refList.capacity - Size_Ref;
			refList.references = new tMailRef[refList.capacity];
			for (int i = 0; i < refList.counter; i++)
			{
				refList.references[i] = aux.references[i];
			}

			destroy(aux);
		}

		deleted = true;
	}

	return deleted;
}

bool readMail(tMailRefList &refList, string id)
{
	bool read = false;
	int pos;

	pos = find(refList, id);
	if (pos != -1) //If the mail has been found
	{
		refList.references[pos].read = true; //Mark reference as read
		read = true;
	}

	return read;
}

int find(const tMailRefList &refList, string id)
{
	int pos = 0;
	bool found = false;

	while ((pos < refList.counter) && !found) // While not at the end and not found
	{
		if (refList.references[pos].id == id) 
			found = true;
		else
			pos++;
	}
	
	if (!found)
		pos = -1;

	return pos;
}

void destroy(tMailRefList &refList)
{
	delete[] refList.references;
	refList.counter = 0;
}

// Main program to test MailListRef module
//int main() {
//	tMailRef mailref;
//	tMailRefList mailbox;
//	ifstream inFile;
//	ofstream outFile;
//	int pos;
//
//	initialize(mailbox);
//	inFile.open("mailbox.txt");
//	if (inFile.is_open()) 
//	{
//		load(mailbox, inFile);
//		inFile.close();
//		pos = find(mailbox, "javi@fdimail.com_1426614381");
//		if (pos == -1) 
//		{
//			cout << "Mail with id javi@fdimail.com_1426614381 NOT found!" << endl;
//		}
//		else 
//		{
//			cout << "Mail with id javi@fdimail.com_1426614381 found in position " << pos << endl;
//		}
//		deleteRef(mailbox, "javi@fdimail.com_1426614381");
//		pos = find(mailbox, "javi@fdimail.com_1426614381");
//		if (pos == -1)
//		{
//			cout << "Mail with id javi@fdimail.com_1426614381 NOT found!" << endl;
//		}
//		else 
//		{
//			cout << "Mail with id javi@fdimail.com_1426614381 found in position " << pos << endl;
//		}
//		readMail(mailbox, "luis@fdimail.com_1426607318");
//		mailref.id = "javi@fdimail.com_1426614381";
//		mailref.read = false;
//		insertRef(mailbox, mailref);
//		outFile.open("mailbox.txt");
//		save(mailbox, outFile);
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