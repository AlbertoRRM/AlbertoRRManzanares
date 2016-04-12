#ifndef mailRefList_h
#define mailRefList_h

#include <string>
#include <fstream>

const int Size_Ref = 10;

typedef struct
{
	std::string id;
	bool read;
}tMailRef;


typedef tMailRef *tMailRefPtr;

typedef struct
{
	tMailRefPtr references;
	int capacity;
	int counter;
}tMailRefList;

void initialize(tMailRefList &refList); //Initialize a list of references
void load(tMailRefList &refList, std::ifstream &file); //Load a list of references from a file
void save(const tMailRefList &refList, std::ofstream &file); //Save a list of references into a file
void insertRef(tMailRefList &refList, tMailRef ref); //Insert a new reference in the list of references
bool deleteRef(tMailRefList &refList, std::string id); //Delete a reference from the list of references
bool readMail(tMailRefList &refList, std::string id); //Mark a mail as read in the list of references
int find(const tMailRefList &refList, std::string id); //Find the position of a reference in the list of references and return that position
void destroy(tMailRefList &refList); //Destroy the list of references

#endif