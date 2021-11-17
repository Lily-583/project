#ifndef BST_H
#define BST_H

#include <cstdlib>
#include <exception>
#include <iostream>
#include <utility>

/**
 * A templated class for a Node in a search tree.
 * The getters for parent/left/right are virtual so
 * that they can be overridden for future kinds of
 * search trees, such as Red Black trees, Splay trees,
 * and AVL trees.
 */
template<typename Key, typename Value>
class Node {
public:
    Node(const Key& key, const Value& value, Node<Key, Value>* parent);
    virtual ~Node();

    const std::pair<const Key, Value>& getItem() const;
    std::pair<const Key, Value>& getItem();
    const Key& getKey() const;
    const Value& getValue() const;
    Value& getValue();

    virtual Node<Key, Value>* getParent() const;
    virtual Node<Key, Value>* getLeft() const;
    virtual Node<Key, Value>* getRight() const;

    void setParent(Node<Key, Value>* parent);
    void setLeft(Node<Key, Value>* left);
    void setRight(Node<Key, Value>* right);
    void setValue(const Value& value);

protected:
    std::pair<const Key, Value> item_;
    Node<Key, Value>* parent_;
    Node<Key, Value>* left_;
    Node<Key, Value>* right_;
};

/*
  -----------------------------------------
  Begin implementations for the Node class.
  -----------------------------------------
*/

/**
 * Explicit constructor for a node.
 */
template<typename Key, typename Value>
Node<Key, Value>::Node(const Key& key, const Value& value, Node<Key, Value>* parent)
        : item_(key, value), parent_(parent), left_(NULL), right_(NULL) {}

/**
 * Destructor, which does not need to do anything since the pointers inside of a node
 * are only used as references to existing nodes. The nodes pointed to by parent/left/right
 * are freed within the deleteAll() helper method in the BinarySearchTree.
 */
template<typename Key, typename Value>
Node<Key, Value>::~Node() {}

/**
 * A const getter for the item.
 */
template<typename Key, typename Value>
const std::pair<const Key, Value>& Node<Key, Value>::getItem() const {
    return item_;
}

/**
 * A non-const getter for the item.
 */
template<typename Key, typename Value>
std::pair<const Key, Value>& Node<Key, Value>::getItem() {
    return item_;
}

/**
 * A const getter for the key.
 */
template<typename Key, typename Value>
const Key& Node<Key, Value>::getKey() const {
    return item_.first;
}

/**
 * A const getter for the value.
 */
template<typename Key, typename Value>
const Value& Node<Key, Value>::getValue() const {
    return item_.second;
}

/**
 * A non-const getter for the value.
 */
template<typename Key, typename Value>
Value& Node<Key, Value>::getValue() {
    return item_.second;
}

/**
 * An implementation of the virtual function for retreiving the parent.
 */
template<typename Key, typename Value>
Node<Key, Value>* Node<Key, Value>::getParent() const {
    return parent_;
}

/**
 * An implementation of the virtual function for retreiving the left child.
 */
template<typename Key, typename Value>
Node<Key, Value>* Node<Key, Value>::getLeft() const {
    return left_;
}

/**
 * An implementation of the virtual function for retreiving the right child.
 */
template<typename Key, typename Value>
Node<Key, Value>* Node<Key, Value>::getRight() const {
    return right_;
}

/**
 * A setter for setting the parent of a node.
 */
template<typename Key, typename Value>
void Node<Key, Value>::setParent(Node<Key, Value>* parent) {
    parent_ = parent;
}

/**
 * A setter for setting the left child of a node.
 */
template<typename Key, typename Value>
void Node<Key, Value>::setLeft(Node<Key, Value>* left) {
    left_ = left;
}

/**
 * A setter for setting the right child of a node.
 */
template<typename Key, typename Value>
void Node<Key, Value>::setRight(Node<Key, Value>* right) {
    right_ = right;
}

/**
 * A setter for the value of a node.
 */
template<typename Key, typename Value>
void Node<Key, Value>::setValue(const Value& value) {
    item_.second = value;
}

/*
  ---------------------------------------
  End implementations for the Node class.
  ---------------------------------------
*/

/**
 * A templated unbalanced binary search tree.
 */
template<typename Key, typename Value>
class BinarySearchTree {
public:
    BinarySearchTree();                                                    // TODO
    virtual ~BinarySearchTree();                                           // TODO
    virtual void insert(const std::pair<const Key, Value>& keyValuePair);  // TODO
    virtual void remove(const Key& key);                                   // TODO
    void clear();                                                          // TODO
    bool isBalanced() const;                                               // TODO
    void print() const;
    bool empty() const;

public:
    /**
     * An internal iterator class for traversing the contents of the BST.
     */
    class iterator  // TODO
    {
    public:
        iterator();

        std::pair<const Key, Value>& operator*() const;
        std::pair<const Key, Value>* operator->() const;

        bool operator==(const iterator& rhs) const;
        bool operator!=(const iterator& rhs) const;

        iterator& operator++();

    protected:
        friend class BinarySearchTree<Key, Value>;
        iterator(Node<Key, Value>* ptr);
        Node<Key, Value>* current_;
    };

public:
    iterator begin() const;
    iterator end() const;
    iterator find(const Key& key) const;

protected:
    // Mandatory helper functions
    Node<Key, Value>* internalFind(const Key& k) const;               // TODO
    Node<Key, Value>* getSmallestNode() const;                        // TODO
    static Node<Key, Value>* predecessor(Node<Key, Value>* current);  // TODO
    // Note:  static means these functions don't have a "this" pointer
    //        and instead just use the input argument.

    // Provided helper functions
    virtual void printRoot(Node<Key, Value>* r) const;
    virtual void nodeSwap(Node<Key, Value>* n1, Node<Key, Value>* n2);

    // Add helper functions here
    std::pair<int, bool> thebalanced(Node<Key, Value>* n) const;
    static Node<Key, Value>* successor(Node<Key, Value>* current);

protected:
    Node<Key, Value>* root_;
    // You should not need other data members
};

/*
--------------------------------------------------------------
Begin implementations for the BinarySearchTree::iterator class.
---------------------------------------------------------------
*/

/**
 * Explicit constructor that initializes an iterator with a given node pointer.
 */
template<class Key, class Value>
BinarySearchTree<Key, Value>::iterator::iterator(Node<Key, Value>* ptr) {
    // TODO
    this->current_ = ptr;
}

/**
 * A default constructor that initializes the iterator to NULL.
 */
template<class Key, class Value>
BinarySearchTree<Key, Value>::iterator::iterator()

{
    // TODO
    this->current_ = NULL;
}

/**
 * Provides access to the item.
 */
template<class Key, class Value>
std::pair<const Key, Value>& BinarySearchTree<Key, Value>::iterator::operator*() const {
    return current_->getItem();
}

/**
 * Provides access to the address of the item.
 */
template<class Key, class Value>
std::pair<const Key, Value>* BinarySearchTree<Key, Value>::iterator::operator->() const {
    return &(current_->getItem());
}

/**
 * Checks if 'this' iterator's internals have the same value
 * as 'rhs'
 */
template<class Key, class Value>
bool BinarySearchTree<Key, Value>::iterator::operator==(const BinarySearchTree<Key, Value>::iterator& rhs) const {
    // TODO
    return (this->current_ == rhs.current_);
}

/**
 * Checks if 'this' iterator's internals have a different value
 * as 'rhs'
 */
template<class Key, class Value>
bool BinarySearchTree<Key, Value>::iterator::operator!=(const BinarySearchTree<Key, Value>::iterator& rhs) const {
    return (this->current_ != rhs.current_);
}

/**
 * Advances the iterator's location using an in-order sequencing
 */
template<class Key, class Value>
typename BinarySearchTree<Key, Value>::iterator& BinarySearchTree<Key, Value>::iterator::operator++() {
    // TODO
    current_ = successor(current_);
    return *this;
}

/*
-------------------------------------------------------------
End implementations for the BinarySearchTree::iterator class.
-------------------------------------------------------------
*/

/*
-----------------------------------------------------
Begin implementations for the BinarySearchTree class.
-----------------------------------------------------
*/

/**
 * Default constructor for a BinarySearchTree, which sets the root to NULL.
 */
template<class Key, class Value>
BinarySearchTree<Key, Value>::BinarySearchTree() {
    // TODO
    root_ = NULL;
}

template<typename Key, typename Value>
BinarySearchTree<Key, Value>::~BinarySearchTree() {
    // TODO
    clear();
}

/**
 * Returns true if tree is empty
 */
template<class Key, class Value>
bool BinarySearchTree<Key, Value>::empty() const {
    return root_ == NULL;
}

template<typename Key, typename Value>
void BinarySearchTree<Key, Value>::print() const {
    printRoot(root_);
    std::cout << "\n";
}

/**
 * Returns an iterator to the "smallest" item in the tree
 */
template<class Key, class Value>
typename BinarySearchTree<Key, Value>::iterator BinarySearchTree<Key, Value>::begin() const {
    BinarySearchTree<Key, Value>::iterator begin(getSmallestNode());
    return begin;
}

/**
 * Returns an iterator whose value means INVALID
 */
template<class Key, class Value>
typename BinarySearchTree<Key, Value>::iterator BinarySearchTree<Key, Value>::end() const {
    BinarySearchTree<Key, Value>::iterator end(NULL);
    return end;
}

/**
 * Returns an iterator to the item with the given key, k
 * or the end iterator if k does not exist in the tree
 */
template<class Key, class Value>
typename BinarySearchTree<Key, Value>::iterator BinarySearchTree<Key, Value>::find(const Key& k) const {
    Node<Key, Value>* curr = internalFind(k);
    BinarySearchTree<Key, Value>::iterator it(curr);
    return it;
}

/**
 * An insert method to insert into a Binary Search Tree.
 * The tree will not remain balanced when inserting.
 */
template<class Key, class Value>
void BinarySearchTree<Key, Value>::insert(const std::pair<const Key, Value>& keyValuePair) {
    // TODO
    Node<Key, Value>* temp = root_;
    Node<Key, Value>* tempparent = temp;
    while (temp != NULL) {
        if (keyValuePair.first < temp->getItem().first) {
            tempparent = temp;
            temp = temp->getLeft();

        } else if (keyValuePair.first > temp->getItem().first) {
            tempparent = temp;
            temp = temp->getRight();
        } else if (keyValuePair.first == temp->getItem().first) {

            temp->getItem().second = keyValuePair.second;
            break;
        }
    }

    if (temp == NULL && root_ != NULL) {
        Node<Key, Value>* new_node = new Node<Key, Value>(keyValuePair.first, keyValuePair.second, tempparent);

        // set the parent's child to the node
        if (keyValuePair.first < tempparent->getItem().first) {
            tempparent->setLeft(new_node);
        } else if (keyValuePair.first > tempparent->getItem().first) {
            tempparent->setRight(new_node);
        }

    } else if (root_ == NULL) {
        Node<Key, Value>* new_node = new Node<Key, Value>(keyValuePair.first, keyValuePair.second, tempparent);
        root_ = new_node;
    }
}

/**
 * A remove method to remove a specific key from a Binary Search Tree.
 * The tree may not remain balanced after removal.
 */
template<typename Key, typename Value>
void BinarySearchTree<Key, Value>::remove(const Key& key) {
    // // TODO
    BinarySearchTree<Key, Value>::iterator it(find(key).current_);

    // if it is a root
    if (it.current_ != NULL) {
        // if there is just a root in the tree
        if (it.current_->getParent() == NULL && it.current_->getLeft() == NULL && it.current_->getRight() == NULL) {
            delete it.current_;
            root_ = NULL;
        }
        // if only has a right subtree, update the root
        else if (
                it.current_->getParent() == NULL && it.current_->getLeft() == NULL && it.current_->getRight() != NULL) {
            it.current_->getRight()->setParent(NULL);
            root_ = it.current_->getRight();
            delete it.current_;
        }

        // if only has a left subtree, update the root
        else if (
                it.current_->getParent() == NULL && it.current_->getLeft() != NULL && it.current_->getRight() == NULL) {
            it.current_->getLeft()->setParent(NULL);
            root_ = it.current_->getLeft();
            delete it.current_;
        }

        // if it is a leaf node and is not a root
        else if (
                it.current_->getParent() != NULL && it.current_->getLeft() == NULL && it.current_->getRight() == NULL) {
            // if the leaf node is the left child of its parent,update the parent's child pointer
            if (it.current_->getParent()->getLeft() == it.current_) {
                it.current_->getParent()->setLeft(NULL);
            }

            // if the leaf node is the right child of its parent,update the parent's child pointer
            else if (it.current_->getParent()->getRight() == it.current_) {
                it.current_->getParent()->setRight(NULL);
            }

            delete it.current_;
        }
        // if "it" is not a root and has one child(left child)
        else if (
                it.current_->getParent() != NULL && it.current_->getLeft() != NULL && it.current_->getRight() == NULL) {
            // if "it" is a left child of its parent
            if (it.current_->getParent()->getLeft() == it.current_) {
                // update the parent's left child pointer
                it.current_->getParent()->setLeft(it.current_->getLeft());
            }
            // if "it" is a right child of its parent
            else if (it.current_->getParent()->getRight() == it.current_) {
                // update the parent's right child pointer
                it.current_->getParent()->setRight(it.current_->getLeft());
            }
            // promote the child up
            it.current_->getLeft()->setParent(it.current_->getParent());
            delete it.current_;
        }

        // if "it" is not the root and has one child (right child)
        else if (
                it.current_->getParent() != NULL && it.current_->getLeft() == NULL && it.current_->getRight() != NULL) {
            // if "it" is a left child of its parent
            if (it.current_->getParent()->getLeft() == it.current_) {
                // update the parent's left child pointer
                it.current_->getParent()->setLeft(it.current_->getRight());
            }
            // if "it" is a right child of its parent
            else if (it.current_->getParent()->getRight() == it.current_) {
                // update the parent's right child pointer
                it.current_->getParent()->setRight(it.current_->getRight());
            }
            // promote the child up(also is the case when "it" is the root)
            it.current_->getRight()->setParent(it.current_->getParent());
            delete it.current_;
        }

        // if has 2 children (can be a root), swap with its predecessor
        else if (it.current_->getLeft() != NULL && it.current_->getRight() != NULL) {
            bool isroot;
            if (it.current_->getParent() == NULL) {
                isroot = true;
            } else {
                isroot = false;
            }
            Node<Key, Value>* pred = predecessor(it.current_);
            nodeSwap(it.current_, pred);
            if (isroot) {
                root_ = pred;
            }

            // if "it" is its parent's left child(that means the pred is right after it)
            // then "it" has no right child
            if (it.current_->getParent()->getLeft() == it.current_) {
                // if "it" at its new location has a left child
                if (it.current_->getLeft() != NULL) {
                    // set the parent to its new child
                    it.current_->getParent()->setLeft(it.current_->getLeft());
                    // update the parent pointer of its child
                    it.current_->getLeft()->setParent(it.current_->getParent());
                    delete it.current_;
                }
                // if "it" at its new location is a leaf node
                else if (it.current_->getLeft() == NULL) {
                    // set the parent's child pointer to be null;
                    it.current_->getParent()->setLeft(NULL);
                    delete it.current_;
                }
            }
            // if "it" is its parent's right child
            else if (it.current_->getParent()->getRight() == it.current_) {
                // if "it" at its new location has a left child
                if (it.current_->getLeft() != NULL) {
                    // update its parent's pointer to the left child
                    it.current_->getParent()->setRight(it.current_->getLeft());
                    // update the parent pointer of its child
                    it.current_->getLeft()->setParent(it.current_->getParent());
                    delete it.current_;

                }
                // if "it" at its new location is a leaf node
                else if (it.current_->getLeft() == NULL) {
                    // update its parent's pointer to the null
                    it.current_->getParent()->setRight(NULL);
                    delete it.current_;
                }
            }
        }
    }
}

template<class Key, class Value>
Node<Key, Value>* BinarySearchTree<Key, Value>::predecessor(Node<Key, Value>* current) {
    // TODO
    // if has the left subtree
    if (current->getLeft() != NULL) {
        current = current->getLeft();
        while (current->getRight() != NULL) {
            current = current->getRight();
        }
        return current;
    }
    // walk up the chain until you find the first node that is a left child of the parent
    else {
        while (current->getParent() != NULL && current != current->getParent()->getRight()) {
            current = current->getParent();
        }
        if (current->getParent() != NULL) {
            current = current->getParent();
            return current;
        }
        // if no pred
        return nullptr;
    }
}

/**
 * A method to remove all contents of the tree and
 * reset the values in the tree for use again.
 */
template<typename Key, typename Value>
void BinarySearchTree<Key, Value>::clear() {
    // TODO
    while (!empty()) {
        remove(root_->getKey());
    }
}

/**
 * A helper function to find the smallest node in the tree.
 */
template<typename Key, typename Value>
Node<Key, Value>* BinarySearchTree<Key, Value>::getSmallestNode() const {
    // TODO
    if (root_ == NULL) {
        return NULL;
    } else if (root_->getLeft() == NULL) {
        return root_;
    } else {
        Node<Key, Value>* temp = root_;
        while (temp->getLeft() != NULL) {
            temp = temp->getLeft();
        }
        return temp;
    }
}

/**
 * Helper function to find a node with given key, k and
 * return a pointer to it or NULL if no item with that key
 * exists
 */
template<typename Key, typename Value>
Node<Key, Value>* BinarySearchTree<Key, Value>::internalFind(const Key& key) const {
    // TODO
    BinarySearchTree<Key, Value>::iterator it(root_);
    BinarySearchTree<Key, Value>::iterator tempparent;
    while (it.current_ != NULL) {
        if (key < it->first) {

            it.current_ = it.current_->getLeft();

        } else if (key > it->first) {

            it.current_ = it.current_->getRight();
        } else if (key == it->first) {

            return it.current_;
        }
    }
    // if it  is Null, no given key in the tree
    if (it.current_ == NULL) {
        return NULL;
    } else {
        return it.current_;
    }
}

/**
 * Return true iff the BST is balanced.
 */
template<typename Key, typename Value>
bool BinarySearchTree<Key, Value>::isBalanced() const {
    // TODO
    std::pair<int, bool> p = thebalanced(root_);
    if (p.second == true) {
        return true;
    } else {
        return false;
    }
}

template<typename Key, typename Value>
std::pair<int, bool> BinarySearchTree<Key, Value>::thebalanced(Node<Key, Value>* n) const {

    if (n == NULL) {
        std::pair<int, bool> c1(0, true);
        return c1;
    }
    std::pair<int, bool> p1 = thebalanced(n->getLeft());
    std::pair<int, bool> p2 = thebalanced(n->getRight());
    std::pair<int, bool> p3;
    if (p1.first - p2.first > 1 || p2.first - p1.first > 1) {
        p3.second = false;
    } else if (p1.second == false || p2.second == false) {
        p3.second = false;
    } else {
        p3.second = true;
    }

    if (p1.first >= p2.first) {
        p3.first = p1.first + 1;
    } else {
        p3.first = p2.first + 1;
    }

    return p3;
}

template<class Key, class Value>
Node<Key, Value>* BinarySearchTree<Key, Value>::successor(Node<Key, Value>* current) {
    if (current->getRight() != NULL) {
        current = current->getRight();
        while (current->getLeft() != NULL) {
            current = current->getLeft();
        }
        return current;
    }
    // walk up the chain until you find the first node that is a left child of the parent
    else {
        while (current->getParent() != NULL && current != current->getParent()->getLeft()) {
            current = current->getParent();
        }

        if (current->getParent() != NULL && current == current->getParent()->getLeft()) {
            current = current->getParent();
            return current;
        }

        // else if(current->getParent()==NULL)
        else {
            return NULL;
        }
    }
}

template<typename Key, typename Value>
void BinarySearchTree<Key, Value>::nodeSwap(Node<Key, Value>* n1, Node<Key, Value>* n2) {
    if ((n1 == n2) || (n1 == NULL) || (n2 == NULL)) {
        return;
    }
    Node<Key, Value>* n1p = n1->getParent();
    Node<Key, Value>* n1r = n1->getRight();
    Node<Key, Value>* n1lt = n1->getLeft();
    bool n1isLeft = false;
    if (n1p != NULL && (n1 == n1p->getLeft()))
        n1isLeft = true;
    Node<Key, Value>* n2p = n2->getParent();
    Node<Key, Value>* n2r = n2->getRight();
    Node<Key, Value>* n2lt = n2->getLeft();
    bool n2isLeft = false;
    if (n2p != NULL && (n2 == n2p->getLeft()))
        n2isLeft = true;

    Node<Key, Value>* temp;
    temp = n1->getParent();
    n1->setParent(n2->getParent());
    n2->setParent(temp);

    temp = n1->getLeft();
    n1->setLeft(n2->getLeft());
    n2->setLeft(temp);

    temp = n1->getRight();
    n1->setRight(n2->getRight());
    n2->setRight(temp);

    if ((n1r != NULL && n1r == n2)) {
        n2->setRight(n1);
        n1->setParent(n2);
    } else if (n2r != NULL && n2r == n1) {
        n1->setRight(n2);
        n2->setParent(n1);

    } else if (n1lt != NULL && n1lt == n2) {
        n2->setLeft(n1);
        n1->setParent(n2);

    } else if (n2lt != NULL && n2lt == n1) {
        n1->setLeft(n2);
        n2->setParent(n1);
    }

    if (n1p != NULL && n1p != n2) {
        if (n1isLeft)
            n1p->setLeft(n2);
        else
            n1p->setRight(n2);
    }
    if (n1r != NULL && n1r != n2) {
        n1r->setParent(n2);
    }
    if (n1lt != NULL && n1lt != n2) {
        n1lt->setParent(n2);
    }

    if (n2p != NULL && n2p != n1) {
        if (n2isLeft)
            n2p->setLeft(n1);
        else
            n2p->setRight(n1);
    }
    if (n2r != NULL && n2r != n1) {
        n2r->setParent(n1);
    }
    if (n2lt != NULL && n2lt != n1) {
        n2lt->setParent(n1);
    }

    if (this->root_ == n1) {
        this->root_ = n2;
    } else if (this->root_ == n2) {
        this->root_ = n1;
    }
}

/**
 * Lastly, we are providing you with a print function,
   BinarySearchTree::printRoot().
   Just call it with a node to start printing at, e.g:
   this->printRoot(this->root_) // or any other node pointer
   It will print up to 5 levels of the tree rooted at the passed node,
   in ASCII graphics format.
   We hope it will make debugging easier!
  */

// include print function (in its own file because it's fairly long)
#include "print_bst.h"

/*
---------------------------------------------------
End implementations for the BinarySearchTree class.
---------------------------------------------------
*/

#endif