#ifndef user_h
#define user_h

#include "mailRefList.h"
#include <string>
#include <fstream>

typedef struct
{
	std::string id;
	std::string password;
	tMailRefList mail_inbox;
	tMailRefList mail_outbox;
}tUser;

const std::string USER_SENTINEL = "XXX";

void initialize(tUser &user, std::string id, std::string password); //Initialize the user information
bool load(tUser &user, std::ifstream &file); //Load a user from a file
void save(const tUser &user, std::ofstream &file); //Save a user into a file

#endif