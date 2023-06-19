/**
 * BinomialHeap
 *
 * An implementation of binomial heap over non-negative integers.
 * Based on exercise from previous semester.
 */
public class BinomialHeap
{
    public int size;
    public int numTrees;
    public HeapNode last;
    public HeapNode min;

    /* Constructor to make new BinomialHeap with specified values*/
    public BinomialHeap(int size, int numTrees, HeapNode last, HeapNode min) {
        this.size = size;
        this.numTrees = numTrees;
        this.last = last;
        this.min = min;
    }
    /* Empty Constructor*/
    public BinomialHeap(){}
    /* Constructor that receives a HeapNode, and makes a new Binomial Heap from him, his siblings and their descendents*/
    public BinomialHeap(HeapNode firstNode) {
        HeapNode Current_node = firstNode;
        this.last = firstNode;
        this.min = firstNode;
        do {
            /*update size and numTrees of new BinomialHeap*/
            this.size += (int) Math.pow(2, Current_node.rank);
            this.numTrees++;
            /*Separate from parent Node*/
            Current_node.parent = null;
            /*update last and min fields if necessary*/
            if (this.last.rank <= Current_node.rank)
            {
                this.last = Current_node;
            }
            if (this.min.item.key > Current_node.item.key)
            {
                this.min = Current_node;
            }
            /*continue to next iteration*/
            Current_node = Current_node.next;
        }while (Current_node != firstNode);
    }
    /**
     *
     * pre: key > 0
     *
     * Insert (key,info) into the heap and return the newly generated HeapItem.
     *
     */
    public HeapItem insert(int key, String info) {
        /* Create new item and node with the key and info*/
        HeapItem item = new HeapItem(key, info);
        HeapNode node = item.node;
        /* Create a BinomialHeap of size 1 with the item and node*/
        BinomialHeap toAdd = new BinomialHeap(node);
        /*use the meld function to add the new Heap of size one to General BinomialHeap*/
        this.meld(toAdd);
        /* Return the new HeapItem*/
        return item;
    }

    /**
     *
     * Delete the minimal item
     *
     */
    public void deleteMin() {
        // make new heap from min's descendents
        BinomialHeap heap2 = this.min.child == null? new BinomialHeap(): new BinomialHeap(this.min.child);
        // find new Min and Last
        HeapNode newMin = null;
        HeapNode newLast = null;
        HeapNode node = this.min;
        while (node.next != this.min){
            node = node.next;
            if (newMin == null || newMin.item.key > node.item.key)
            {
                newMin = node;
            }
            if (newLast == null || newLast.rank < node.rank )
            {
                newLast = node;
            }
        }
        // remove min and his descendents from the Heap
        node.next = node.next.next;
        // update size and numTrees
        this.numTrees--;
        this.size -= (int) Math.pow(2, this.min.rank);
        // update min and last fields
        this.min = newMin;
        this.last = newLast;
        // merge the heap of the min's descendents back to the original heap
        this.meld(heap2);
    }

    /**
     *
     * Return the minimal HeapItem
     *
     */
    public HeapItem findMin()
    {
        /* The BinomialHeap saves a min field, so we return it's item field*/
        return this.min.item;
    }

    /**
     *
     * pre: 0<diff<item.key
     *
     * Decrease the key of item by diff and fix the heap.
     *
     */
    public void decreaseKey(HeapItem item, int diff)
    {
        /* update key value*/
        item.key -= diff;
        /* bubble up the node in loop with shiftUp method to fix heap property*/
        while (item.node.parent != null && item.key <= item.node.parent.item.key)
            shiftUp(item.node);
        /* Update min field */
        this.min = this.min.item.key < item.key ? this.min : item.node;
    }

    /**
     *
     * Delete the item from the heap.
     *
     */
    public void delete(HeapItem item) {
        /* Decrease key to -1 to ensure it is the minimum and delete the minimum*/
        this.decreaseKey(item, item.key + 1);
        this.deleteMin();
    }

    public void shiftUp(HeapNode node)
    {
        /*create temp pointers*/
        HeapNode parent = node.parent;
        HeapItem temp = node.item;
        /* switch between the items*/
        node.item = parent.item;
        parent.item = temp;
        /* switch between the corresponding nodes*/
        node.item.node = node;
        parent.item.node = parent;
    }

    /**
     *
     * Meld the heap with heap2
     *
     */
    public void meld(BinomialHeap heap2) {
        /* if both trees aren't empty*/
        if( !this.empty() && !heap2.empty())
        {
            int t = 0;
            int s = this.size + heap2.size;
            HeapNode m = this.min.item.key < heap2.min.item.key? this.min: heap2.min;
            HeapNode carry = null;
            HeapNode dummy = new HeapNode();
            dummy.next = this.last.next;
            this.last.next = dummy;
            HeapNode curr = dummy;

            for(int i = 0; !(heap2.last == null && carry == null); i++) {
                HeapNode add1 = null;
                HeapNode add2 = null;
                if (curr.next.rank == i) {
                    add1 = curr.next;
                    curr.next = curr.next.next;
                    add1.next = add1;
                    t--;
                }
                if (heap2.last != null && heap2.last.next.rank == i) {
                    add2 = heap2.last.next;
                    if (heap2.last.next == heap2.last)
                        heap2.last = null;
                    else
                        heap2.last.next = heap2.last.next.next;
                    add2.next = add2;
                }

                HeapNode[] result = merge3Trees(add1, add2, carry);
                carry = result[1];
                HeapNode toAdd = result[0];
                if(toAdd != null){
                    toAdd.next = curr.next;
                    curr.next = toAdd;
                    curr = curr.next;
                    t++;
                }
            }
            this.last = curr.rank > this.last.rank? curr: this.last;
            this.last.next = this.last.next.next;
            this.size = s;
            this.numTrees += t;
            this.min = m;
        }
        /* if one of the heaps is empty, set "this" as the non-empty one */
        if (this.empty() && !heap2.empty()) {
            this.last = heap2.last;
            this.min = heap2.min;
            this.size = heap2.size;
            this.numTrees = heap2.numTrees;
        }
    }
    public HeapNode[] merge3Trees(HeapNode tree1, HeapNode tree2, HeapNode tree3){
        HeapNode[] result = new HeapNode[2];
        // reorder the trees
        if (tree3 == null){
            tree3 = tree1;
            tree1 = null;
        }
        if (tree2 == null){
            tree2 = tree1;
            tree1 = null;
        }
        if (tree3 == null){
            tree3 = tree2;
            tree2 = null;
        }
        // if 2 trees are null, the carry mast be empty and the result will be the third
        if (tree1 == null && tree2 == null){
            result[0] = tree3;
            result[1] = null;
        }
        else{
            // if 2 trees are not null, the carry mast be merging of them and the result will be the third
            result[0] = tree1;
            result[1] = merge2Trees(tree2, tree3);;
        }
        return result;
    }
    public HeapNode merge2Trees(HeapNode tree1, HeapNode tree2){
        // make sure tree1 contain the minimum value in the tree
        if (tree1.item.key > tree2.item.key){
            HeapNode temp = tree1;
            tree1 = tree2;
            tree2 = temp;
        }
        // set tree2 as child in tree1 (the child with the biggest rank)
        if (tree1.child == null){
            tree2.parent = tree1;
            tree2.next = tree2;
            tree1.child = tree2;
            tree1.rank++;
        }
        else {
            tree2.parent = tree1;
            tree2.next = tree1.child.next;
            tree1.child.next = tree2;
            tree1.child = tree2;
            tree1.rank++;
        }
        return tree1;
    }
    /**
     *
     * Return the number of elements in the heap
     *
     */
    public int size(){
        /* we save the number of elements in the size field, so we return the value in it*/
        return this.size;
    }

    /**
     *
     * The method returns true if and only if the heap
     * is empty.
     *
     */
    public boolean empty(){
        /* a heap is empty if and only if it's size is zero, so we return the boolean value "this.size == 0"*/
        return this.size == 0;
    }

    /**
     *
     * Return the number of trees in the heap.
     *
     */
    public int numTrees() {
        /* We save the number of heaps in the tree in the numTrees field */
        return this.numTrees;
    }

    /**
     * Class implementing a node in a Binomial Heap.
     *
     */
    public class HeapNode{
        public HeapItem item;
        public HeapNode child;
        public HeapNode next;
        public HeapNode parent;
        public int rank;

        /* empty constructor*/
        public HeapNode(){}
        public HeapNode(HeapItem item){
            /* constructor from item*/
            this.item = item;
            this.next = this;
            this.child = null;
            this.parent = null;
            this.rank = 0;
        }
    }

    /**
     * Class implementing an item in a Binomial Heap.
     *
     */
    public class HeapItem{
        public HeapNode node;
        public int key;
        public String info;

        /*empty constructor*/
        public HeapItem(){}
        public HeapItem(int key, String info) {
            /* Constructor with variables*/
            this.key = key;
            this.info = info;
            this.node = new HeapNode(this);
        }
    }
}
