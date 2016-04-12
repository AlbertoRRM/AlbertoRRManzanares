#include "mail.h"
#include <iostream>
#include <sstream>
#include <iomanip>
using namespace std;

void newMail(tMail &mail, string sender)
{
	string line;
	stringstream stream;

	//Introduce the information for the new mail
	mail.sender = sender;
	mail.date = time(0); //current date and time
	stream << mail.sender << "_" << mail.date;
	mail.id = stream.str();
	cout << "Enter the address: ";
	cin >> line;
	mail.address = line;
	cout << "Subject: ";
	cin.sync();
	getline(cin, mail.subject);
	cout << "Body (end with a line XXX): " << endl;
	cin.sync();
	do
	{
		getline(cin, line);
		if (line != SENTINEL)
			mail.body += line + '\n';
	} while (line != SENTINEL);
}

void answerMail(const tMail &originalMail, tMail &mail, string sender)
{
	string line, date;
	stringstream stream;

	//New mail
	mail.sender = sender;
	mail.address = originalMail.sender;
	mail.subject = "RE:" + originalMail.subject;
	cout << "Body (end with a line XXX): " << endl;
	cin.sync();
	do
	{
		getline(cin, line);
		if (line != SENTINEL)
			mail.body += line + '\n';
	} while (line != SENTINEL);

	//Original mail
	mail.body += "----------------------------------------\n";
	mail.body += "From: " + originalMail.sender + "'\t\t\t" + toString(originalMail.date) + '\n';
	mail.body += "To: " + originalMail.address + '\n';
	mail.body += "Subject: " + originalMail.subject + '\n';
	mail.body += originalMail.body;
	mail.body += "----------------------------------------\n";
	mail.date = time(0); //current date and time
	stream << mail.sender << "_" << mail.date;
	mail.id = stream.str();
}

string toString(const tMail &mail)
{
	string mail_message;
	stringstream stream;

	stream << "From: " << mail.sender << setw(30) << right << toString(mail.date) << endl << "To: " << mail.address << endl << "Subject: " << mail.subject <<
		endl << mail.body << endl;// Output to the stream
	mail_message = stream.str(); // Convert to string

	return mail_message;
}

string toString(tDate date) 
{
	stringstream result;
	tm ltm;

	localtime_s(&ltm, &date);
	result << 1900 + ltm.tm_year << "/" << 1 + ltm.tm_mon << "/"
		<< ltm.tm_mday;
	result << " (" << ltm.tm_hour << ":" << ltm.tm_min << ":"
		<< ltm.tm_sec << ")";

	return result.str();
}

string headerToStr(const tMail &mail)
{
	string mail_header;
	stringstream stream;
	tm ltm;

	localtime_s(&ltm, &mail.date);

	stream << " " << left << setw(20) << mail.sender << setw(38) << mail.subject << "  " << right << 1900 + ltm.tm_year << "/" << 1 + ltm.tm_mon << "/"
	<< ltm.tm_mday;
	mail_header = stream.str();

	return mail_header;
}

bool load(tMail &mail, ifstream &file)
{
	bool loaded = false;
	string line;

	file >> line;
	if (line != "X")
	{
		mail.id = line;
		file >> mail.date;
		file >> mail.sender;
		file >> mail.address;
		file.get();
		getline(file, mail.subject);
		do
		{
			getline(file, line);
			if (line != "X")
				mail.body += line + '\n';
		} while (line != "X");
		loaded = true;
	}

	return loaded;
}

void save(const tMail &mail, ofstream &file)
{
	file << mail.id << endl;
	file << mail.date << endl;
	file << mail.sender << endl;
	file << mail.address << endl;
	file << mail.subject << endl;
	file << mail.body;
	file << "X" << endl;
}

//int main() {
//	tMail mail1, mail2;
//	newMail(mail1, "luis@fdimail.com"); // TestingnewMail()
//	cout << "Mail1..." << endl;
//	cout << toString(mail1) << endl; // TestingtoString()
//	cout << "Mail1 header..." << endl;
//	cout << headerToStr(mail1) << endl; // TestingheaderToStr()
//	ofstream outFile;
//	outFile.open("test.txt");
//	save(mail1, outFile); // Testingsave()
//	outFile.close();
//	ifstream inFile;
//	inFile.open("test.txt");
//	if (load(mail1, inFile)) { // Testingload()
//		cout << "Mail1 loaded..." << endl;
//		cout << toString(mail1) << endl;
//	}
//	inFile.close();
//
//	newMail(mail2, "luis@fdimail.com");
//	cout << "Mail2..." << endl;
//	cout << toString(mail2) << endl;
//	if (mail1.id < mail2.id) { // Testing<
//		cout << "Mail1 < Mail2" << endl;
//	}
//	else{
//		cout << "Mail1 >= Mail2" << endl;
//	}
//	cout << "AnswerMail1..." << endl;
//	answerMail(mail1, mail2, "javi@fdimail.com");
//	// TestingasnwerMail()
//	cout << "Mail2 answersMail1..." << endl;
//	cout << toString(mail2) << endl;
//
//	system("pause");
//	return 0;
//}