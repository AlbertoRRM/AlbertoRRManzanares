#ifndef userList_h
#define userList_h

#include "user.h"

const int Max_Users = 10;
typedef tUser tArrayUsers[Max_Users];

typedef struct
{
	tArrayUsers list;
	int counter;
}tUserList;

void initialize(tUserList &users); //Initialize a list of users
bool load(tUserList &users, std::string domain); //Load a list of users from a file
void save(const tUserList &users, std::string domain); //Save a list of users into a file
bool add(tUserList &users, const tUser &user); //Add a new user in the list of users
bool findUser(const tUserList &users, std::string id, int &position); //Find a user from the list and return its position

#endif