#ifndef mailList_h
#define mailList_h

#include "mail.h"

typedef tMail *tMailPtr;
const int Size_Mails = 10;

typedef struct
{
	tMailPtr list;
	int capacity;
	int counter;
}tMailList;

void initialize(tMailList &mails); //Initialize a list of mails
bool load(tMailList &mails, std::string domain); //Load a list of mails from a file
void save(const tMailList &mails, std::string domain); //Save a list of mails into a file
void insert(tMailList &mails, const tMail &mail); //Insert a new mail in the list
bool find(const tMailList &mails, std::string id, int &pos); //Find a mail in the list given a id
void bubble(tMailList &mails); //Sort the list using the bubble algorithm
bool operator<(tMail leftMail, tMail rightMail); //Operator redefined to search in the bubble algorithm
void destroy(tMailList &mails); //Destroy the list of mails

#endif