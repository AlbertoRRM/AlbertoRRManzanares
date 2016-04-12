#ifndef manager_h
#define manager_h

#include <string>
#include "userList.h"
#include "mailList.h"

typedef struct
{
	std::string domain;
	tUserList users;
	tMailList mails;
	int activeUser;
}tManager;

bool start(tManager &manager, std::string domain); //Load the lists and initialize the manager 
void stop(const tManager &manager); //Save the list of users and the list of mails
bool createAccount(tManager &manager); //Create a new account
bool initSession(tManager &manager); // Log in into the system
void manageSession(tManager &manager, tMailRefList mailbox); //Displays the mailboxes and the menu when a user has logged in
void readMail(tManager &manager, tMailRefList &mailbox); //Read a mail from the mailbox
void sendMail(tManager &manager, const tMail &mail); //Send a mail to a user
void deleteMail(tManager &manager, tMailRefList &mailbox); //Delete a mail from the mailbox
void quickRead(tManager &manager, tMailRefList& mailbox); //Read all the unread mails from the mailbox
void hidden_password(std::string &password);

#endif