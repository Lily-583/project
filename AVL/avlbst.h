#ifndef RBBST_H
#define RBBST_H

#include "bst.h"
#include <algorithm>
#include <cstdlib>
#include <exception>
#include <iostream>

struct KeyError {};

/**
 * A special kind of node for an AVL tree, which adds the height as a data member, plus
 * other additional helper functions. You do NOT need to implement any functionality or
 * add additional data members or helper functions.
 */
template<typename Key, typename Value>
class AVLNode : public Node<Key, Value> {
public:
    // Constructor/destructor.
    AVLNode(const Key& key, const Value& value, AVLNode<Key, Value>* parent);
    virtual ~AVLNode();

    // Getter/setter for the node's height.
    int getHeight() const;
    void setHeight(int height);

    // Getters for parent, left, and right. These need to be redefined since they
    // return pointers to AVLNodes - not plain Nodes. See the Node class in bst.h
    // for more information.
    virtual AVLNode<Key, Value>* getParent() const override;
    virtual AVLNode<Key, Value>* getLeft() const override;
    virtual AVLNode<Key, Value>* getRight() const override;

protected:
    int height_;
};

/*
  -------------------------------------------------
  Begin implementations for the AVLNode class.
  -------------------------------------------------
*/

/**
 * An explicit constructor to initialize the elements by calling the base class constructor and setting
 * the color to red since every new node will be red when it is first inserted.
 */
template<class Key, class Value>
AVLNode<Key, Value>::AVLNode(const Key& key, const Value& value, AVLNode<Key, Value>* parent)
        : Node<Key, Value>(key, value, parent), height_(1) {}

/**
 * A destructor which does nothing.
 */
template<class Key, class Value>
AVLNode<Key, Value>::~AVLNode() {}

/**
 * A getter for the height of a AVLNode.
 */
template<class Key, class Value>
int AVLNode<Key, Value>::getHeight() const {
    return height_;
}

/**
 * A setter for the height of a AVLNode.
 */
template<class Key, class Value>
void AVLNode<Key, Value>::setHeight(int height) {
    height_ = height;
}

/**
 * An overridden function for getting the parent since a static_cast is necessary to make sure
 * that our node is a AVLNode.
 */
template<class Key, class Value>
AVLNode<Key, Value>* AVLNode<Key, Value>::getParent() const {
    return static_cast<AVLNode<Key, Value>*>(this->parent_);
}

/**
 * Overridden for the same reasons as above.
 */
template<class Key, class Value>
AVLNode<Key, Value>* AVLNode<Key, Value>::getLeft() const {
    return static_cast<AVLNode<Key, Value>*>(this->left_);
}

/**
 * Overridden for the same reasons as above.
 */
template<class Key, class Value>
AVLNode<Key, Value>* AVLNode<Key, Value>::getRight() const {
    return static_cast<AVLNode<Key, Value>*>(this->right_);
}

/*
  -----------------------------------------------
  End implementations for the AVLNode class.
  -----------------------------------------------
*/

template<class Key, class Value>
class AVLTree : public BinarySearchTree<Key, Value> {
public:
    virtual void insert(const std::pair<const Key, Value>& new_item);  // TODO
    virtual void remove(const Key& key);                               // TODO
protected:
    virtual void nodeSwap(AVLNode<Key, Value>* n1, AVLNode<Key, Value>* n2);

    // Add helper functions here
    void rotateLeft_zigzig(AVLNode<Key, Value>* g, AVLNode<Key, Value>* p, AVLNode<Key, Value>* n);
    void rotateRight_zigzig(AVLNode<Key, Value>* g, AVLNode<Key, Value>* p, AVLNode<Key, Value>* n);
    void rotateLeft_zigzag(AVLNode<Key, Value>* g, AVLNode<Key, Value>* p, AVLNode<Key, Value>* n);
    void rotateRight_zigzag(AVLNode<Key, Value>* g, AVLNode<Key, Value>* p, AVLNode<Key, Value>* n);
    bool isLeftChild(AVLNode<Key, Value>* n1, AVLNode<Key, Value>* n2);
    bool isRightChild(AVLNode<Key, Value>* n1, AVLNode<Key, Value>* n2);
    void updateHeight(AVLNode<Key, Value>* n);
    bool isHeightChanged(AVLNode<Key, Value>* n, int originalHeight);
    void insertFix(AVLNode<Key, Value>* g);
    bool balanced(AVLNode<Key, Value>* n);
    AVLNode<Key, Value>* determineNext(AVLNode<Key, Value>* n);
    void removeFix(AVLNode<Key, Value>* g);
};

template<class Key, class Value>
void AVLTree<Key, Value>::insert(const std::pair<const Key, Value>& new_item) {
    // TODO
    AVLNode<Key, Value>* temp = static_cast<AVLNode<Key, Value>*>(this->root_);
    AVLNode<Key, Value>* tempparent = temp;
    while (temp != NULL) {
        if (new_item.first < temp->getItem().first) {
            tempparent = temp;
            temp = temp->getLeft();

        } else if (new_item.first > temp->getItem().first) {
            tempparent = temp;
            temp = temp->getRight();
        } else if (new_item.first == temp->getItem().first) {

            temp->getItem().second = new_item.second;
            break;
        }
    }

    // doing insertion
    if (temp == NULL && this->root_ != NULL) {
        AVLNode<Key, Value>* new_node = new AVLNode<Key, Value>(new_item.first, new_item.second, tempparent);

        // set the parent's child to the node
        if (new_item.first < tempparent->getItem().first) {
            tempparent->setLeft(new_node);
        } else if (new_item.first > tempparent->getItem().first) {
            tempparent->setRight(new_node);
        }

        // set it to point to the new node
        temp = new_node;

        // insert-fix, recursing all the way up
        int originalHeight = temp->getParent()->getHeight();
        if (isHeightChanged(temp->getParent(), originalHeight)) {
            temp = temp->getParent();
            if (temp->getParent() != NULL) {

                insertFix(temp->getParent());
            }
        }

    }

    // insert on empty tree
    else if (this->root_ == NULL) {
        AVLNode<Key, Value>* new_node = new AVLNode<Key, Value>(new_item.first, new_item.second, tempparent);
        this->root_ = new_node;
    }
}
template<class Key, class Value>
void AVLTree<Key, Value>::insertFix(AVLNode<Key, Value>* g) {
    // base case
    if (g == NULL) {
        return;
    }
    // if the height has been changed(including updating the height)
    int originalHeight = g->getHeight();
    if (isHeightChanged(g, originalHeight)) {
        if (balanced(g)) {
            insertFix(g->getParent());
        }
        // if not balanced, just do the rotation
        else {
            AVLNode<Key, Value>* p = determineNext(g);
            AVLNode<Key, Value>* c = determineNext(p);

            // which rotation
            if ((p == g->getLeft() && c == p->getLeft())) {
                rotateRight_zigzig(g, p, c);
            } else if (p == g->getRight() && c == p->getRight()) {
                rotateLeft_zigzig(g, p, c);

            } else if (p == g->getLeft() && c == p->getRight()) {
                rotateRight_zigzag(g, p, c);
            } else if (p == g->getRight() && c == p->getLeft()) {
                rotateLeft_zigzag(g, p, c);
            }
        }
    }
}

template<class Key, class Value>
bool AVLTree<Key, Value>::balanced(AVLNode<Key, Value>* n) {
    int LH;
    int RH;
    if (n->getLeft() != NULL) {
        LH = n->getLeft()->getHeight();
    } else {
        LH = 0;
    }
    if (n->getRight() != NULL) {
        RH = n->getRight()->getHeight();
    } else {
        RH = 0;
    }

    if (LH - RH > 1 || RH - LH > 1) {
        return false;
    } else {
        return true;
    }
}

template<class Key, class Value>
AVLNode<Key, Value>* AVLTree<Key, Value>::determineNext(AVLNode<Key, Value>* n) {
    if ((n->getLeft() != NULL && n->getRight() != NULL && n->getLeft()->getHeight() >= n->getRight()->getHeight())
        || (n->getLeft() != NULL && n->getRight() == NULL)) {
        return n->getLeft();
    } else {
        return n->getRight();
    }
}

template<class Key, class Value>
void AVLTree<Key, Value>::remove(const Key& key) {
    // TODO
    // // TODO
    Node<Key, Value>* t = this->internalFind(key);

    AVLNode<Key, Value>* temp = static_cast<AVLNode<Key, Value>*>(t);
    AVLNode<Key, Value>* theparent = NULL;
    // if it can be found
    if (temp != NULL) {
        // if there is just a root in the tree
        if (temp->getParent() == NULL && temp->getLeft() == NULL && temp->getRight() == NULL) {

            this->root_ = NULL;
            delete temp;
        }
        // if only has a right subtree, update the root
        else if (temp->getParent() == NULL && temp->getLeft() == NULL && temp->getRight() != NULL) {
            temp->getRight()->setParent(NULL);
            this->root_ = temp->getRight();
            delete temp;
        }

        // if only has a left subtree, update the root
        else if (temp->getParent() == NULL && temp->getLeft() != NULL && temp->getRight() == NULL) {
            temp->getLeft()->setParent(NULL);
            this->root_ = temp->getLeft();
            delete temp;
        }
        // if it is a leaf node and is not a root
        else if (temp->getParent() != NULL && temp->getLeft() == NULL && temp->getRight() == NULL) {
            // if the leaf node is the left child of its parent,update the parent's child pointer
            if (temp->getParent()->getLeft() == temp) {
                temp->getParent()->setLeft(NULL);
            }

            // if the leaf node is the right child of its parent,update the parent's child pointer
            else if (temp->getParent()->getRight() == temp) {
                temp->getParent()->setRight(NULL);
            }

            theparent = temp->getParent();
            delete temp;
        }

        // if "it" is not a root and has one child(left child)
        else if (temp->getParent() != NULL && temp->getLeft() != NULL && temp->getRight() == NULL) {
            // if "it" is a left child of its parent
            if (temp->getParent()->getLeft() == temp) {
                // update the parent's left child pointer
                temp->getParent()->setLeft(temp->getLeft());
            }
            // if "it" is a right child of its parent
            else if (temp->getParent()->getRight() == temp) {
                // update the parent's right child pointer
                temp->getParent()->setRight(temp->getLeft());
            }
            // promote the child up
            temp->getLeft()->setParent(temp->getParent());
            theparent = temp->getParent();
            delete temp;
        }

        // if "it" is not the root and has one child (right child)
        else if (temp->getParent() != NULL && temp->getLeft() == NULL && temp->getRight() != NULL) {
            // if "it" is a left child of its parent
            if (temp->getParent()->getLeft() == temp) {
                // update the parent's left child pointer
                temp->getParent()->setLeft(temp->getRight());
            }
            // if "it" is a right child of its parent
            else if (temp->getParent()->getRight() == temp) {
                // update the parent's right child pointer
                temp->getParent()->setRight(temp->getRight());
            }
            // promote the child up(also is the case when "it" is the root)
            temp->getRight()->setParent(temp->getParent());
            theparent = temp->getParent();
            delete temp;
        }

        // if has 2 children (can be a root), swap with its predecessor
        else if (temp->getLeft() != NULL && temp->getRight() != NULL) {
            bool isroot;
            if (temp->getParent() == NULL) {
                isroot = true;
            } else {
                isroot = false;
            }
            Node<Key, Value>* p = this->predecessor(temp);
            AVLNode<Key, Value>* pred = static_cast<AVLNode<Key, Value>*>(p);
            nodeSwap(temp, pred);
            if (isroot) {
                (this->root_) = pred;
            }
            // if "it" is its parent's left child(that means the pred is right after it)
            // then "it" has no right child
            if (temp->getParent()->getLeft() == temp) {
                // if "it" at its new location has a left child
                if (temp->getLeft() != NULL) {
                    // set the parent to its new child
                    temp->getParent()->setLeft(temp->getLeft());
                    // update the parent pointer of its child
                    temp->getLeft()->setParent(temp->getParent());
                    theparent = temp->getParent();
                    delete temp;
                }
                // if "it" at its new location is a leaf node
                else if (temp->getLeft() == NULL) {
                    // set the parent's child pointer to be null;
                    temp->getParent()->setLeft(NULL);
                    theparent = temp->getParent();
                    delete temp;
                }
            }
            // if "it" is its parent's right child
            else if (temp->getParent()->getRight() == temp) {
                // if "it" at its new location has a left child
                if (temp->getLeft() != NULL) {
                    // update its parent's pointer to the left child
                    temp->getParent()->setRight(temp->getLeft());
                    // update the parent pointer of its child
                    temp->getLeft()->setParent(temp->getParent());
                    theparent = temp->getParent();
                    delete temp;

                }
                // if "it" at its new location is a leaf node
                else if (temp->getLeft() == NULL) {
                    // update its parent's pointer to the null
                    temp->getParent()->setRight(NULL);
                    theparent = temp->getParent();
                    delete temp;
                }
            }
        }

        if (theparent != NULL) {
            removeFix(theparent);
        }
    }
}

template<class Key, class Value>
void AVLTree<Key, Value>::removeFix(AVLNode<Key, Value>* g) {
    if (g == NULL) {
        return;
    }

    if (!balanced(g)) {
        AVLNode<Key, Value>* p = determineNext(g);
        AVLNode<Key, Value>* c;

        // prefer zigzig than zigzag
        if (g->getLeft() == p) {

            if ((p->getLeft() != NULL) && (p->getRight() != NULL)
                && ((p->getLeft())->getHeight() >= (p->getRight())->getHeight())) {
                c = p->getLeft();
            } else if (p->getLeft() != NULL && p->getRight() == NULL) {
                c = p->getLeft();
            } else {
                c = p->getRight();
            }

        } else if (g->getRight() == p) {
            if ((p->getLeft() != NULL) && (p->getRight() != NULL)
                && ((p->getLeft())->getHeight() > (p->getRight())->getHeight())) {
                c = p->getLeft();
            } else if (p->getLeft() != NULL && p->getRight() == NULL) {
                c = p->getLeft();
            } else {
                c = p->getRight();
            }
        }

        // which rotation
        if ((p == g->getLeft() && c == p->getLeft())) {
            rotateRight_zigzig(g, p, c);
            removeFix(p->getParent());
        } else if (p == g->getRight() && c == p->getRight()) {
            rotateLeft_zigzig(g, p, c);
            removeFix(p->getParent());

        } else if (p == g->getLeft() && c == p->getRight()) {
            rotateRight_zigzag(g, p, c);
            removeFix(c->getParent());
        } else if (p == g->getRight() && c == p->getLeft()) {
            rotateLeft_zigzag(g, p, c);
            removeFix(c->getParent());
        }

    } else if (balanced(g)) {

        int originalHeight = g->getHeight();
        if (isHeightChanged(g, originalHeight)) {

            removeFix(g->getParent());
        }
    }
}

template<class Key, class Value>
void AVLTree<Key, Value>::nodeSwap(AVLNode<Key, Value>* n1, AVLNode<Key, Value>* n2) {
    BinarySearchTree<Key, Value>::nodeSwap(n1, n2);
    int tempH = n1->getHeight();
    n1->setHeight(n2->getHeight());
    n2->setHeight(tempH);
}

template<class Key, class Value>
void AVLTree<Key, Value>::rotateLeft_zigzig(AVLNode<Key, Value>* g, AVLNode<Key, Value>* p, AVLNode<Key, Value>* n) {
    // check if g is the root
    bool rootisg;
    if (static_cast<AVLNode<Key, Value>*>(this->root_) == g) {
        rootisg = true;
    } else {
        rootisg = false;
    }

    if (p->getLeft() != NULL) {
        AVLNode<Key, Value>* c = p->getLeft();
        c->setParent(g);
        g->setRight(c);
    } else {
        g->setRight(NULL);
    }

    if (g->getParent() != NULL) {
        AVLNode<Key, Value>* e = g->getParent();
        p->setParent(e);

        if (isLeftChild(g, e)) {
            e->setLeft(p);
        } else if (isRightChild(g, e)) {
            e->setRight(p);
        }
    } else {
        p->setParent(NULL);
    }

    p->setLeft(g);

    g->setParent(p);

    // update height
    updateHeight(n);
    updateHeight(g);
    updateHeight(p);

    // update the root
    if (rootisg == true) {
        (this->root_) = p;
    }
}

template<class Key, class Value>
void AVLTree<Key, Value>::rotateRight_zigzig(AVLNode<Key, Value>* g, AVLNode<Key, Value>* p, AVLNode<Key, Value>* n) {
    // check if g is the root
    bool rootisg;
    if (static_cast<AVLNode<Key, Value>*>(this->root_) == g) {
        rootisg = true;
    } else {
        rootisg = false;
    }

    if (p->getRight() != NULL) {
        AVLNode<Key, Value>* c = p->getRight();
        c->setParent(g);
        g->setLeft(c);
    } else {
        g->setLeft(NULL);
    }

    if (g->getParent() != NULL) {
        AVLNode<Key, Value>* e = g->getParent();
        p->setParent(e);
        if (isLeftChild(g, e)) {
            e->setLeft(p);
        } else if (isRightChild(g, e)) {
            e->setRight(p);
        }
    } else {
        p->setParent(NULL);
    }

    p->setRight(g);

    g->setParent(p);

    updateHeight(n);
    updateHeight(g);
    updateHeight(p);

    // update the root
    if (rootisg == true) {
        (this->root_) = p;
    }
}

template<class Key, class Value>
void AVLTree<Key, Value>::rotateLeft_zigzag(AVLNode<Key, Value>* g, AVLNode<Key, Value>* p, AVLNode<Key, Value>* n) {
    bool rootisg;
    if (static_cast<AVLNode<Key, Value>*>(this->root_) == g) {
        rootisg = true;
    } else {
        rootisg = false;
    }

    if (n->getRight() != NULL) {
        AVLNode<Key, Value>* b = n->getRight();
        b->setParent(p);
        p->setLeft(b);
    } else {
        p->setLeft(NULL);
    }
    if (n->getLeft() != NULL) {
        AVLNode<Key, Value>* c = n->getLeft();
        c->setParent(g);
        g->setRight(c);
    } else {
        g->setRight(NULL);
    }

    if (g->getParent() != NULL) {
        AVLNode<Key, Value>* e = g->getParent();
        n->setParent(e);
        if (isLeftChild(g, e)) {
            e->setLeft(n);
        } else if (isRightChild(g, e)) {
            e->setRight(n);
        }
    } else {
        n->setParent(NULL);
    }

    p->setParent(n);
    g->setParent(n);
    n->setRight(p);
    n->setLeft(g);

    updateHeight(p);
    updateHeight(g);
    updateHeight(n);

    // update the root
    if (rootisg == true) {
        (this->root_) = n;
    }
}

template<class Key, class Value>
void AVLTree<Key, Value>::rotateRight_zigzag(AVLNode<Key, Value>* g, AVLNode<Key, Value>* p, AVLNode<Key, Value>* n) {
    bool rootisg;
    if (static_cast<AVLNode<Key, Value>*>(this->root_) == g) {
        rootisg = true;
    } else {
        rootisg = false;
    }

    if (n->getLeft() != NULL) {
        AVLNode<Key, Value>* b = n->getLeft();
        b->setParent(p);
        p->setRight(b);

    } else {
        p->setRight(NULL);
    }

    if (n->getRight() != NULL) {
        AVLNode<Key, Value>* c = n->getRight();
        c->setParent(g);
        g->setLeft(c);

    } else {
        g->setLeft(NULL);
    }

    if (g->getParent() != NULL) {
        AVLNode<Key, Value>* e = g->getParent();
        n->setParent(e);
        if (isLeftChild(g, e)) {
            e->setLeft(n);
        } else if (isRightChild(g, e)) {
            e->setRight(n);
        }
    } else {
        n->setParent(NULL);
    }

    p->setParent(n);
    g->setParent(n);
    n->setLeft(p);

    n->setRight(g);

    updateHeight(p);
    updateHeight(g);
    updateHeight(n);

    // update the root
    if (rootisg == true) {
        (this->root_) = n;
    }
}

template<class Key, class Value>
bool AVLTree<Key, Value>::isLeftChild(AVLNode<Key, Value>* p, AVLNode<Key, Value>* c) {
    if (c->getLeft() == p) {
        return true;
    } else {
        return false;
    }
}

template<class Key, class Value>
bool AVLTree<Key, Value>::isRightChild(AVLNode<Key, Value>* p, AVLNode<Key, Value>* c) {
    if (c->getRight() == p) {
        return true;
    } else {
        return false;
    }
}

template<class Key, class Value>
void AVLTree<Key, Value>::updateHeight(AVLNode<Key, Value>* n) {
    int LH = 0;
    int RH = 0;

    int max = 0;

    if (n->getLeft() != NULL) {
        LH = n->getLeft()->getHeight();
    }
    if (n->getRight() != NULL) {
        RH = n->getRight()->getHeight();
    }

    if (LH >= RH) {
        max = LH;
    } else {
        max = RH;
    }

    n->setHeight(max + 1);
}

template<class Key, class Value>
bool AVLTree<Key, Value>::isHeightChanged(AVLNode<Key, Value>* n, int originalHeight) {

    updateHeight(n);
    if (n->getHeight() != originalHeight) {
        return true;
    } else {
        return false;
    }
}

#endif