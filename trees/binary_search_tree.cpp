#include <iostream>
#include <string>
#include <vector>
#include <queue>
using namespace std;


class BinaryNode
{
    BinaryNode* l;
    BinaryNode* r;
    int data;

    public:
        BinaryNode(int _data, BinaryNode* _l, BinaryNode* _r) : data(_data), l(_l), r(_r) {}
        BinaryNode* left() { return l; }
        BinaryNode* right() { return r; }
        void set_left(BinaryNode* n) { l = n; }
        void set_right(BinaryNode* n) { r = n; }
        int value() const { return data; }
};

void delete_tree(BinaryNode* root)
{
    if (root)
    {
        delete_tree(root->left());
        delete_tree(root->right());
        delete root;
    }
}

void inorder_traversal(BinaryNode* root) {
    if (root) {
        inorder_traversal(root->left());
        cout << root->value() << " ";
        inorder_traversal(root->right());
    }
}

void preorder_traversal(BinaryNode* root) {
    if (root) {
        cout << root->value() << " ";
        inorder_traversal(root->left());
        inorder_traversal(root->right());
    }
}

void postorder_traversal(BinaryNode* root) {
    if (root) {
        inorder_traversal(root->left());
        inorder_traversal(root->right());
        cout << root->value() << " ";
    }
}

void levelorder_traversal(BinaryNode* root) {
    if (root) {
        queue<BinaryNode*> q;
        q.push(root);
        q.push(NULL);

        while (!q.empty()) {
            BinaryNode* node = q.front();
            q.pop();

            if (node) {
                cout << node->value() << " ";

                if (node->left())
                    q.push(node->left());
                if (node->right())
                    q.push(node->right());
            }
            else {
                if (!q.empty()) {
                    q.push(NULL);
                }
                cout << endl;
            }
        }
    }
}

BinaryNode* build_bst(const vector<int>& data) {
    if (data.empty())
        return NULL;

    BinaryNode* root = NULL;
    for (int i = 0; i < data.size(); ++i) {
        BinaryNode* p = root;
        BinaryNode *prev = NULL;

        while (p && (data[i] != p->value())) {
            prev = p;
            if (data[i] < p->value()) {
                p = p->left();
            }
            else {
                p = p->right();
            }
        }

        // Did not find match. Insert new node
        if (!p) {
            if (prev) {
                if (data[i] < prev->value())
                    prev->set_left(new BinaryNode(data[i], NULL, NULL));
                else
                    prev->set_right(new BinaryNode(data[i], NULL, NULL));
            }
            else {
                // Empty tree and this is the first node to be added
                root = new BinaryNode(data[i], NULL, NULL);
            }
        }
    }
    return root;
}

void print_array(const vector<int>& v) {
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

int main() {
    int a[] = { 34, 45, 7, 9, 15, 100, 54, 4, 17, 11, 35, 25 };
    vector<int> v(a, a + sizeof(a) / sizeof(int));
    print_array(v);

    BinaryNode* root = build_bst(v);

    inorder_traversal(root);
    cout << endl;

    preorder_traversal(root);
    cout << endl;

    postorder_traversal(root);
    cout << endl;

    levelorder_traversal(root);
    cout << endl;

    delete_tree(root);
    return 0;
}

