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

    /**
     *
     * pre: key > 0
     *
     * Insert (key,info) into the heap and return the newly generated HeapItem.
     *
     */
    public HeapItem insert(int key, String info)
    {
        return null; // should be replaced by student code
    }

    /**
     *
     * Delete the minimal item
     *
     */
    public void deleteMin()
    {
        this.delete(this.findMin());
    }

    /**
     *
     * Return the minimal HeapItem
     *
     */
    public HeapItem findMin()
    {
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
        return; // should be replaced by student code
    }

    /**
     *
     * Delete the item from the heap.
     *
     */
    public void delete(HeapItem item)
    {
        return; // should be replaced by student code
    }

    /**
     *
     * Meld the heap with heap2
     *
     */
    public void meld(BinomialHeap heap2) {
        /* if one of the heaps is empty, set this heap to the second */
        if (heap2.last == null){return;}
        else if (this.last == null) {
            this.last = heap2.last;
            this.min = heap2.min;
            this.size = heap2.size;
            this.numTrees = heap2.numTrees;
        }
        else{
            int t = 0;
            int s = this.size + heap2.size;
            HeapNode m = this.min.item.key > heap2.min.item.key? this.min: heap2.min;
            HeapNode dummy = new HeapNode();
            HeapNode newNode = dummy;
            HeapNode carry = null;
            HeapNode add1;
            HeapNode add2;
            for(int i = 0; i <= Math.max(this.last.rank, heap2.last.rank)+1; i++){
                if (this.last.next.rank == i){
                    add1 = this.last.next;
                    this.last.next = this.last.next.next;
                    add1.next = add1;
                }
                else{
                    add1 = null;
                }
                if (heap2.last.next.rank == i){
                    add2 = heap2.last.next;
                    heap2.last.next = heap2.last.next.next;
                    add2.next = add2;
                }
                else{
                    add2 = null;
                }
                HeapNode [] updateTree = merge3Trees(add1, add2, carry);
                carry = updateTree[0];
                HeapNode toAdd = updateTree[1];
                toAdd.next = dummy;
                newNode.next = toAdd;
                t++;
            }
            newNode.next = dummy.next;
            this.last = newNode;
            this.size = s;
            this.numTrees = t;
            this.min = m;
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
        // if 2 trees are null, the carry mast be zero and the result will be the third
        if (tree1 == null && tree2 == null){
            result[0] = tree3;
            result[1] = null;
        }
        else{
            // if 2 trees are not null, the carry mast be merging of them and the result will be the third
            merge2Trees(tree2, tree3);
            result[0] = tree1;
            result[1] = tree2;
        }
        return result;
    }
    public void merge2Trees(HeapNode tree1, HeapNode tree2){
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
    }
    /**
     *
     * Return the number of elements in the heap
     *
     */
    public int size()
    {
        return this.size; // should be replaced by student code
    }

    /**
     *
     * The method returns true if and only if the heap
     * is empty.
     *
     */
    public boolean empty()
    {
        return this.size == 0; // should be replaced by student code
    }

    /**
     *
     * Return the number of trees in the heap.
     *
     */
    public int numTrees()
    {
        return this.numTrees; // should be replaced by student code
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
    }

    /**
     * Class implementing an item in a Binomial Heap.
     *
     */
    public class HeapItem{
        public HeapNode node;
        public int key;
        public String info;
    }

}
