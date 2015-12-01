#include <cstring>
#include <climits>
#include <iostream>
#include <vector>
#include <algorithm>
#include <fstream>
#include <sstream>
#include <utility>
using namespace std;


class SuffixArray {

    private:
    class Suffix {
        public:
            Suffix() : index(-1), text_len(0), text(NULL) {}
            Suffix(char* t, int i) : index(i), text_len(strlen(t)), text(t) {}

            inline const char* str() const { return text; }
            inline int length() const { return text_len; }
            inline char at(int i) const { return text[i]; }

            static bool compare_func(const Suffix& rhs, const Suffix& lhs) {
                int n = std::min(rhs.length(), lhs.length());
                for (int i = 0; i < n; ++i) {
                    if (rhs.at(i) < lhs.at(i))
                        return true;
                    if (rhs.at(i) > lhs.at(i))
                        return false;
                }
                return (rhs.length() < lhs.length());
            }

        private:
            int index;
            int text_len;
            char *text;
    };

    public:
        void load(const string& input_file);
        int longest_repeating_substring(std::pair<string,string>& lrs_pair) const;
        int longest_common_prefix(const Suffix& first, const Suffix& second) const;

    private:
        void init(const string& text);

    private:
        vector<Suffix> suffixes;
        string original_text;
};

// Reads a data file
void SuffixArray::load(const string& input_file) {
    ifstream fs;
    fs.open(input_file.c_str(), ifstream::in);

    stringstream text;
    string line;
    while (std::getline(fs, line)) {
        text << line << endl;
    }
    fs.close();

    original_text = text.str();
    init(original_text);
}

// Initializes the suffix array with provided text
void SuffixArray::init(const string& text) {
    for (int i = 0; i < text.length(); ++i) {
        suffixes.push_back(Suffix((char*)(text.c_str() + i), i));
    }
    sort(suffixes.begin(), suffixes.end(), Suffix::compare_func);
}

// Find the length of a common prefix, if any
int SuffixArray::longest_common_prefix(const Suffix& first, const Suffix& second) const {
    int n = std::min(first.length(), second.length());
    for (int i = 0; i < n; ++i) {
        if (first.at(i) != second.at(i))
            return i;
    }
    return n;
}

// Find the longest repeating substring in the text held by the suffix array
int SuffixArray::longest_repeating_substring(std::pair<string,string>& lrs_pair) const {
    int max_lcp = INT_MIN;
    for (int i = 0; i < suffixes.size()-1; ++i) {
        int lcp = longest_common_prefix(suffixes[i], suffixes[i+1]);
        if (lcp > max_lcp) {
            max_lcp = lcp;
            lrs_pair = std::make_pair(suffixes[i].str(), suffixes[i+1].str());
        }
    }
    return max_lcp;
}

int main(int argc, char* argv[]) {
    string file = "passage.txt";
    if (argc > 1) {
        file = argv[1];
    }

    SuffixArray sa;
    sa.load(file);

    std::pair<string,string> p;
    int len = sa.longest_repeating_substring(p);
    cout << "Longest repeating substring length = " << len << endl;
    cout << p.first.substr(0,len) << endl;
    return 0;
}
