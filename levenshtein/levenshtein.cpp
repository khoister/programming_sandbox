#include <iostream>
#include <fstream>
#include <vector>
#include <assert.h>
#include <stdlib.h>
using namespace std;


void load_file(const string& file, vector<string>& word_list) {
    ifstream fs;
    fs.open(file.c_str(), ios_base::in);

    string line;
    while(!std::getline(fs, line).eof()) {
        word_list.push_back(line);
    }
}

int levenshtein(const string& s, const string& t) {
    if (s == t)
        return 0;
    if (s.empty())
        return t.length();
    if (t.empty())
        return s.length();


    // Initialize the first row
    vector<int> prev_row(t.length()+1, 0);
    for (int i = 0; i < prev_row.size(); ++i) {
        prev_row[i] = i;
    }

    vector<int> curr_row(t.length()+1, 0);
    for (int i = 0; i < s.length(); ++i) {
        curr_row[0] = i + 1;

        for (int j = 0; j < t.length(); ++j) {
            int edit = (s[i] == t[j]) ? 0 : 1;
            curr_row[j+1] = min(min(curr_row[j] + 1, prev_row[j+1] + 1), prev_row[j] + edit);
        }
        prev_row = curr_row;
    }
    return curr_row[t.length()];
}

void find_similar(const string& s, const vector<string>& word_list, int max_diff, vector<string>& results) {
    for (int i = 0; i < word_list.size(); ++i) {
        if (abs(long(s.length() - word_list[i].length())) < 3) {
            if (levenshtein(s, word_list[i]) <= max_diff) {
                results.push_back(word_list[i]);
            }
        }
    }
}

int main(int argc, char* argv[]) {
    if (argc < 3) {
        // Error
        cout << "Failure: incorrect number of arguments" << endl;
        return -1;
    }
    string s = argv[1];
    int max_diff = atoi(argv[2]);

    vector<string> word_list;
    load_file("enable1.txt", word_list);

    vector<string> matches;
    find_similar(s, word_list, max_diff, matches);
    cout << "Similar words: " << endl;
    for (int i = 0; i < matches.size(); ++i) {
        cout << matches[i] << endl;
    }
    return 0;
}
