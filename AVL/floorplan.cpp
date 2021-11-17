#include "avlbst.h"
#include <cstring>
#include <fstream>
#include <iostream>
#include <map>
#include <sstream>
#include <vector>

using namespace std;

struct Rectangle {
    int ID;
    int length;
    int height;
};

// Typedefs for you input and output map. Start with
// std::map but once the backtracking search algorithm is working
// try to use your AVL-tree map implementation by changing these
// typedef's
typedef AVLTree<int, Rectangle> InputMapType;
typedef AVLTree<int, std::pair<int, int>> OutputMapType;

// Allowed global variables: the dimensions of the grid
int n;  // X-dim size
int m;  // Y-dim size

// No other global variables are allowed

// A dummy operator << for Rectangles so the BST and AVL BST will
// compile with their printRoot() implementations
std::ostream& operator<<(std::ostream& os, const Rectangle& r) {
    os << r.ID;
    return os;
}

// A dummy operator << for pairs so the BST and AVL BST will
// compile with their printRoot() implementations
template<typename T, typename U>
std::ostream& operator<<(std::ostream& os, const std::pair<T, U>& p) {
    os << p.first << "," << p.second;
    return os;
}

void printSolution(std::ostream& os, InputMapType& input, OutputMapType& output) {
    for (OutputMapType::iterator it = output.begin(); it != output.end(); ++it) {
        InputMapType::iterator rbit = input.find(it->first);
        os << it->first << " ";
        os << it->second.first << " ";
        os << it->second.second << " ";
        os << rbit->second.length << " ";
        os << rbit->second.height << endl;
    }
}

// Changes the grid entries to their opposite values for the
// rectangle r starting at x1,y1
void flip(int x1, int y1, const Rectangle& r, vector<vector<bool>>& grid) {
    for (int x = x1; x < x1 + r.length; x++) {
        for (int y = y1; y < y1 + r.height; y++)
            grid[x][y] = !grid[x][y];
    }
}

// TODO: Write your backtracking search function here
// bool solution(InputMapType& input, OutputMapType& output, vector<vector<bool> >& grid);
bool helperSolution(InputMapType& input, OutputMapType& output, vector<vector<bool>>& grid, InputMapType::iterator it);
bool isLocationValid(vector<vector<bool>>& grid, Rectangle& r, int i, int j);
void SwapHeightLength(Rectangle& r);
bool Solution(InputMapType& input, OutputMapType& output, vector<vector<bool>>& grid, InputMapType::iterator it);
bool CalculateArea(InputMapType& input);

// See if the total area of rectangles is greater than the area of the surface
bool CalculateArea(InputMapType& input) {
    int SurfaceArea = n * m;
    InputMapType::iterator it;
    int TotalRecArea = 0;
    for (it = input.begin(); it != input.end(); ++it) {
        int area = (it->second.height) * (it->second.length);
        TotalRecArea = TotalRecArea + area;
    }

    if (TotalRecArea > SurfaceArea) {
        return false;
    } else {
        return true;
    }
}

bool Solution(InputMapType& input, OutputMapType& output, vector<vector<bool>>& grid, InputMapType::iterator it) {
    // IF the total area of the rectangle is larger than that of the surface, just return true immediately
    if (CalculateArea(input) == false) {
        return false;
    } else {
        bool result = helperSolution(input, output, grid, it);
        return result;
    }
}

bool helperSolution(InputMapType& input, OutputMapType& output, vector<vector<bool>>& grid, InputMapType::iterator it) {
    // base case
    if (it == input.end()) {
        return true;
    }
    // i is the column, j is row
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            // if column less than m boundary and row less than n boundary
            if (i + (it->second.length - 1) < n && j + (it->second.height - 1) < m) {
                if (isLocationValid(grid, it->second, i, j)) {
                    flip(i, j, it->second, grid);  // update the grid to show it has been tiled
                    // int ID=it->first;
                    InputMapType::iterator at = it;
                    bool result = helperSolution(input, output, grid, ++at);

                    if (result == true) {
                        output.insert(std::make_pair(it->first, std::make_pair(i, j)));

                        return true;
                    }
                    // if the next one returned false, set the grid back and go to the nxt iteration of for loop
                    flip(i, j, it->second, grid);
                }
            }

            // if the one above does not work, try swaping the length and height
            if (i + (it->second.height - 1) < n && j + (it->second.length - 1) < m) {

                // update the height stored for the rectangle
                SwapHeightLength(it->second);
                if (isLocationValid(grid, it->second, i, j)) {
                    flip(i, j, it->second, grid);  // update the grid to show ithas been tiled
                    // //update the height stored for the rectangle
                    // SwapHeightLength(it->second);
                    // it++;
                    // int ID=it->first;
                    InputMapType::iterator at = it;
                    bool result = helperSolution(input, output, grid, ++at);

                    if (result == true) {

                        output.insert(std::make_pair(it->first, std::make_pair(i, j)));
                        return true;
                    }
                    // if the next one returned false, set the grid back and go to the nxt iteration of for loop
                    flip(i, j, it->second, grid);
                    // //change back the height and length of rectangle
                    // SwapHeightLength(it->second);
                }
                // change back the height and length of rectangle
                SwapHeightLength(it->second);
            }
        }
    }
    // cout<<"no solution!"<<endl;
    return false;
}

bool isLocationValid(vector<vector<bool>>& grid, Rectangle& r, int i, int j) {
    for (int x = i; x < i + r.length; x++) {
        for (int y = j; y < j + r.height; y++) {
            // if any of the grid cell has been tiled already
            if (grid[x][y] == true) {
                return false;
            }
        }
    }
    return true;
}
void SwapHeightLength(Rectangle& r) {
    int temp = r.height;
    r.height = r.length;
    r.length = temp;
}
int main(int argc, char* argv[]) {
    if (argc < 3) {
        cout << "please specify an input and output file";
        return 0;
    }
    ifstream ifile(argv[1]);
    if (ifile.fail()) {
        cout << "Invalid filename" << endl;
        return 1;
    }
    stringstream ss;
    string line;
    ofstream ofile(argv[2]);
    int x;
    getline(ifile, line);
    ss << line;
    ss >> n;
    ss >> m;
    ss >> x;

    InputMapType input;
    OutputMapType output;
    for (int i = 0; i < x; i++) {
        getline(ifile, line);
        stringstream ss2(line);
        Rectangle r;
        ss2 >> r.ID;
        ss2 >> r.length;
        ss2 >> r.height;
        input.insert(std::make_pair(r.ID, r));
    }
    ifile.close();
    vector<vector<bool>> grid;

    for (int i = 0; i < n; i++) {
        grid.push_back(vector<bool>(m, false));
    }
    InputMapType::iterator it = input.begin();
    bool solution_exists = false;

    // TODO:  Call your backtracking search function here
    solution_exists = Solution(input, output, grid, it);

    if (!solution_exists) {
        ofile << "No solution found.";
    } else {
        printSolution(ofile, input, output);
    }
    ofile.close();
    return 0;
}