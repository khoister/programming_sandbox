#include <boost/shared_ptr.hpp>
#include <iostream>
#include <fstream>
#include <string>
#include <assert.h>
#include <vector>

using namespace std;

class TrieNode;
typedef boost::shared_ptr<TrieNode> TrieNodePtr;

class TrieNode
{
    public:
        TrieNode(char _c = 0): c(_c), is_word(false) {}
        inline TrieNodePtr child(char _c) { return children[_c]; }
        inline void new_child(char _c) { children[_c] = TrieNodePtr(new TrieNode(_c)); }

        char c;
        bool is_word;
        TrieNodePtr children[128];

};

//-----------------------------------------------------------------------------

class Trie
{
    public:
        Trie(): root(new TrieNode()) {}
        void load(const string& file);
        void add(const string& word);
        bool search(const string& word) const;
        void suffix(TrieNodePtr n, string& s, vector<string>& v) const;
        vector<string> similar(const string& prefix) const;

    private:
        TrieNodePtr root;
};

void Trie::load(const string& file) {
    ifstream fs;
    fs.open(file.c_str(), ifstream::in);

    string word;
    while (getline(fs, word)) {
        add(word);
    }
    fs.close();
}

void Trie::add(const string& word) {
    TrieNodePtr n = root;

    for (int i = 0; i < word.length(); ++i) {
        char c = word[i];
        if (!n->child(c)) {
            n->new_child(c);
        }
        n = n->child(c);
    }
    n->is_word = true;
}

bool Trie::search(const string& word) const {
    TrieNodePtr n = root;
    int l = word.length();

    for (int i = 0; i < l; ++i) {
        char c = word[i];
        if (!n->child(c))
            return false;

        n = n->child(c);
    }
    return n->is_word;
}

void Trie::suffix(TrieNodePtr n, string& s, vector<string>& v) const {
    if (n) {
        s.push_back(n->c);

        // Found a word
        if (n->is_word) {
            v.push_back(s);
        }

        for (char c = 0; c < (char)127; ++c) {
            if (n->child(c)) {
                suffix(n->child(c), s, v);
                // Backtrack
                s.erase(s.length()-1, 1);
            }
        }
    }
}

vector<string> Trie::similar(const string& prefix) const {
    vector<string> v;
    TrieNodePtr n = root;
    for (int i = 0; i < prefix.length(); ++i) {
        char c = prefix[i];
        if (!n->child(c))
            return v;

        n = n->child(c);

        // The prefix itself is a valid word
        if (i == prefix.length()-1 && n->is_word)
            v.push_back(prefix);
    }
    for (char c = 0; c < (char)127; ++c) {
        if (n->child(c)) {
            string s(prefix);
            suffix(n->child(c), s, v);
        }
    }
    return v;
}

//-----------------------------------------------------------------------------

int main(int argc, char* argv[])
{
    string prefix("prep");
    if (argc > 1) {
        prefix = argv[1];
    }

    Trie t;
    t.load("enable1.txt");
    assert(t.search("dog") == true);
    assert(t.search("cog") == true);
    assert(t.search("fool") == true);
    assert(t.search("sage") == true);
    assert(t.search("khoi") == false);
    assert(t.search("zymogen") == true);
    assert(t.search("nguyen") == false);
    cout << "Success!" << endl;

    vector<string> v = t.similar(prefix);
    for (int i = 0; i < v.size(); ++i) {
        cout << v[i] << endl;
    }

    return 0;
}
