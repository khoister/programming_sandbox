// To build: g++ -std=c++11 wordladder.cpp

#include <memory>
#include <iostream>
#include <fstream>
#include <set>
#include <queue>
#include <vector>
#include <assert.h>
#include <memory>

using namespace std;

class Dictionary
{
public:
    void load(const std::string& f) {
        ifstream fs;
        fs.open(f.c_str(), ifstream::in);

        string word;
        while (getline(fs, word)) {
            dict.insert(word);
        }
    }

    bool exists(const string& s) const {
        return (dict.find(s) != dict.end());
    }

    void add(const string& s) {
        dict.insert(s);
    }

    void remove(const string& s) {
        dict.erase(s);
    }

    int size() const {
        return dict.size();
    }

private:
    set<string> dict;
};

class WordNode;
typedef std::shared_ptr<WordNode> WordNodePtr;

struct WordNode
{
    WordNode(const string& s, WordNodePtr prev) : word(s), prev(prev) {}

    string word;
    WordNodePtr prev;
};

void print(WordNodePtr node, bool last_node = true) {
    if (node) {
        print(node->prev, false);
        cout << node->word;
        if (!last_node)
            cout << " -> ";
    }
}

void wordladder(const string& start, const string& end, const Dictionary& dict) {
    // For cleaning all dynamically allocated nodes when we're done
    queue<WordNodePtr> q;

    WordNodePtr node(new WordNode(start, WordNodePtr()));
    q.push(node);

    Dictionary visited; // empty bucket of words we've seen
    while (!q.empty()) {
        WordNodePtr node = q.front();
        q.pop();

        // Found the ending word. Print it out results and exit.
        if (node->word == end) {
            print(node);
            cout << endl << "Visited nodes : " << visited.size() << endl;
            return;
        }

        string w = node->word;
        for (int i = 0; i < w.length(); ++i) {
            char t = w[i];
            for (char c = 'a'; c <= 'z'; ++c) {
                w[i] = c;
                if (!visited.exists(w) && dict.exists(w)) {
                    WordNodePtr n(new WordNode(w, node));
                    q.push(n);
                    visited.add(w);
                }
            }
            w[i] = t;
        }
    }
}

int main(int argc, char* argv[])
{
    string start("fool");
    string end("sage");
    if (argc > 2) {
        start = argv[1];
        end = argv[2];

        // Different length strings aren't allowed
        if (start.length() != end.length()) {
            cerr << "Start and end strings must be the same length" << endl;
        }
    }

    Dictionary d;
    d.load("enable1.txt");
    assert(d.exists("lion") == true);
    assert(d.exists("witch") == true);
    assert(d.exists("cog") == true);
    assert(d.exists("khoi") == false);
    assert(d.exists("cooy") == false);

    wordladder(start, end, d);

    return 0;
}
