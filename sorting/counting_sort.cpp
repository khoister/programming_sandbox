#include <iostream>
#include <vector>
#include <assert.h>
using namespace std;


void print(const vector<int>& v) {
	if (v.empty())
		cout << "<empty>" << endl;
	else {
		cout << "[ ";
		for (int i = 0; i < v.size(); ++i)
			cout << v[i] << " ";
		cout << "]" << endl;;
	}
}

vector<int> counting_sort(const vector<int>& data, int max_value) {
	if (data.empty()) {
		// Empty input
		return vector<int>();
	}

	vector<int> results(data.size(), 0);
	vector<int> aux(max_value + 1, 0); // For 0..max_value

	for (int i = 0; i < data.size(); ++i)
		++aux[data[i]];

	for (int i = 1; i < aux.size(); ++i)
		aux[i] += aux[i-1];

	for (int i = 0; i < data.size(); ++i) {
		results[aux[data[i]]-1] = data[i];
		--aux[data[i]];
	}

	return results;
}

int main() {
	int a[] = { 4, 2, 5, 5, 3, 1, 0, 6, 2 };
	vector<int> v(a, a + sizeof(a) / sizeof(int));
	vector<int> results = counting_sort(v, 6);

	print(v);
	print(results);

	return 0;
}
