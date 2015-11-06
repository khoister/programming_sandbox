#include <iostream>
#include <vector>
#include <algorithm>
#include <assert.h>
using namespace std;


// Based on algorithm in "Introduction to Algorithms" book by CLR
// See http://www.cs.virginia.edu/~luebke/cs332.fall00/lecture8/sld011.htm

void print(const vector<int>& v) {
    if (v.empty()) {
        cout << "<empty>" << endl;
    }
    else {
        cout << "[ ";
        for (int i = 0; i < v.size(); ++i) {
            cout << v[i] << " ";
        }
        cout << "]" << endl;
    }
}

int swap(int& a, int& b) {
    int temp = a;
    a = b;
    b = temp;
}

int partition(vector<int>& A, int left, int right) {
    int x = A[left];

    while (left < right) {
        while (A[left] < x)
            ++left;

        while (A[right] > x)
            --right;

        if (left >= right)
            break;

        swap(A[left], A[right]);
    }
    return right;
}

int kth_element(vector<int>& A, int left, int right, int i) {
    if (left == right)
        return A[left];

    int pivot = partition(A, left, right);
    int k = pivot - left + 1;

    if (i < k) {
        return kth_element(A, left, pivot-1, i);
    }
    else if (i > k) {
        return kth_element(A, pivot+1, right, i-k);
    }
    else {
        return A[pivot];
    }
}

int main(int argc, char* argv[]) {
    int a[] = { 17, 13, 19, 12, 14, 111, 15, 18, 10, 110, 45 };
    vector<int> sorted(a, a + sizeof(a)/sizeof(int));
    std::sort(sorted.begin(), sorted.end());

    for (int i = 0; i < sorted.size(); ++i) {
        vector<int> v(a, a + sizeof(a)/sizeof(int));
        assert(kth_element(v, 0, v.size()-1, i+1) == sorted[i]);
    }

    cout << "Success" << endl;
    return 0;
}
