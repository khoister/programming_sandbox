#include <cstdlib>
#include <iostream>
#include <vector>
#include <queue>
#include <fstream>
#include <sstream>
#include <math.h>
#include <iomanip>
#include <assert.h>
using namespace std;


class Location {
	public:
		Location(const string& _name, double _x, double _y) : name(_name), x(_x), y(_y) {}
		const string& get_name() const { return name; }
		double getx() const { return x; }
		double gety() const { return y; }
	
	protected:
		string name;
		double x;
		double y;
};

struct LocationDistancePair {
	LocationDistancePair(const Location& _location, double _distance) : location(_location), distance(_distance) {}

	Location location;
	double distance;
};

class LocationDistancePairComparator {
	public:
		bool operator() (const LocationDistancePair& lhs, const LocationDistancePair& rhs) const {
			return (lhs.distance > rhs.distance);
		}

};

class ClientLocation : public Location {
	public:
		ClientLocation(const string& _name, double _x, double _y, const vector<Location>& _stores)
			: Location(_name, _x, _y), stores(_stores)
		{
			calculate_distances();
		}

		void calculate_distances() {
			for (int i = 0; i < stores.size(); ++i) {
				double xdiff = (x - stores[i].getx());
				double ydiff = (y - stores[i].gety());
				double d = sqrt(xdiff * xdiff + ydiff * ydiff);
				
				LocationDistancePair loc(stores[i], d);
				distances.push(loc);
			}
		}

		LocationDistancePair closest() const {
			return distances.top();
		}

		void next() {
			return distances.pop();
		}

	private:
		vector<Location> stores;
		priority_queue<LocationDistancePair, vector<LocationDistancePair>, LocationDistancePairComparator> distances;
};


vector<Location> load_datafile(const string& datafile) {
	vector<Location> locations;
	fstream fs(datafile.c_str(), fstream::in);
	if (!fs.is_open()) {
		cout << "Error: Could not open file '" << datafile << "'" << endl;
		return locations;
	}

	string line;
	while (!fs.eof()) {
		std::getline(fs, line);
		if (line.empty())
			continue;

		char delimiter = ',';
		string store, store_x, store_y;

		stringstream ss(line);
		std::getline(ss, store, delimiter);
		std::getline(ss, store_x, delimiter);
		std::getline(ss, store_y, delimiter);

		Location loc(store, atof(store_x.c_str()), atof(store_y.c_str()));
		locations.push_back(loc);
	}
	return locations;
}


int main(int argc, char* argv[]) {
	if (argc < 3) {
		cout << "Error: Invalid number of arguments" << endl;
		return -1;
	}

	double x = atof(argv[1]);
	double y = atof(argv[2]);
	string datafile = argv[3];

	vector<Location> locations = load_datafile(datafile);
	if (locations.empty()) {
		return -1;
	}

	ClientLocation client("Khoi", x, y, locations);

	LocationDistancePair p = client.closest();
	client.next();
	cout << p.location.get_name() << "," << std::fixed << std::setprecision(4) << p.distance << endl;
	assert(p.location.get_name() == "Starbucks Seattle2");
	assert(p.distance > 0.06 && p.distance < 0.07);

	p = client.closest();
	client.next();
	cout << p.location.get_name() << "," << std::fixed << std::setprecision(4) << p.distance << endl;
	assert(p.location.get_name() == "Starbucks Seattle");
	assert(p.distance > 0.086 && p.distance < 0.087);

	p = client.closest();
	client.next();
	cout << p.location.get_name() << "," << std::fixed << std::setprecision(4) << p.distance << endl;
	assert(p.location.get_name() == "Starbucks SF");
	assert(p.distance > 10.0 && p.distance < 10.1);
}
