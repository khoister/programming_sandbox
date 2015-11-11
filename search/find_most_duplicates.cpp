#include <iostream>
#include <vector>
#include <map>
#include <climits>
#include <assert.h>
using namespace std;


int find_most_duplicates(const vector<int>& v) {
    int most_duplicated = INT_MIN;
    map<int, int> dup_map;
    if (!v.empty()) {
        for (int i = 0; i < v.size(); ++i) {
            ++dup_map[v[i]];
        }

        int num_duplicates = INT_MIN;
        map<int, int>::const_iterator iter = dup_map.begin();
        for ( ; iter != dup_map.end(); iter++) {
            if (iter->second > num_duplicates) {
                most_duplicated = iter->first;
                num_duplicates = iter->second;
            }
        }
    }
    return most_duplicated;
}

int main() {
    int a[] = { 4, 17, 3, 2, 8, 4, 2, 1, 2, 20, 44, 2, 4, 8, 16 };
    vector<int> v(a, a + sizeof(a) / sizeof(int));

    assert(find_most_duplicates(v) == 2);

    cout << "Success" << endl;
    return 0;
}
