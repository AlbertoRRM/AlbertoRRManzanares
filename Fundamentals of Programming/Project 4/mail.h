#ifndef mail_h
#define mail_h

#include <string>
#include <fstream>
#include <ctime>

const std::string SENTINEL = "XXX";
typedef time_t tDate;

typedef struct
{
	std::string id;
	std::string sender;
	std::string address;
	std::string subject;
	std::string body;
	tDate date;
}tMail;

void newMail(tMail &mail, std::string sender); //Create a new mail given the sender
void answerMail(const tMail &originalMail, tMail &mail, std::string sender); //Answer a mail given the original mail and the sender
std::string toString(const tMail &mail); //Convert the full mail intro a string
std::string toString(tDate date); //Convert the tDate date into a readable string
std::string headerToStr(const tMail &mail); //Displays the main information of a mail
bool load(tMail &mail, std::ifstream &file); //Load a mail from a file
void save(const tMail &mail, std::ofstream &file); //Save a mail into a file;

#endif