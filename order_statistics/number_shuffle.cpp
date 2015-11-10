#include <cstdlib>
#include <algorithm>
#include <iostream>
#include <vector>
#include <set>
#include <assert.h>
using namespace std;


void print(const vector<int>& v) {
    if (v.empty())
        cout << "<empty>" << endl;
    else {
        cout << "[ ";
        for (int i = 0; i < v.size(); ++i) {
            cout << v[i] << " ";
        }
        cout << "]" << endl;
    }
}

void swap(int& a, int& b) {
    int temp = a;
    a = b;
    b = temp;
}

int random_range(int start, int end) {
    // Range is [0..end-1], inclusively
    int range = end - start;
    return start + (rand() % range);
}

void shuffle(vector<int>& v) {
    if (v.empty())
        return;

    srand(time(NULL));

    // Initialize array with sequential numbers
    for (int i = 0; i < v.size(); ++i) {
        v[i] = i;
    }

    int end = v.size();
    for (int start = 0; start < end-1; ++start) {
        int pos = random_range(start, end);
        swap(v[start], v[pos]);
    }
}

int main(int argc, char* argv[]) {
    int n = 10;
    if (argc > 1)
        n = atoi(argv[1]);

    vector<int> v(n);
    shuffle(v);
    print(v);

    // Verify
    set<int> s;
    for (int i = 0; i < v.size(); ++i) {
        if (s.find(i) != s.end()) {
            assert(false && "Random shuffle failed");
            return -1;
        }
        s.insert(i);
    }

    cout << "Success" << endl;
    return 0;
}
