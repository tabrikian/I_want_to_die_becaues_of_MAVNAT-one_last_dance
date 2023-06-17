public class test {
    public static void printHeap(BinomialHeap heap){
        System.out.println("size: " + heap.size);
        System.out.println("num of trees: " + heap.numTrees());
        if (heap.empty())
            return;
        System.out.println("Min: " + itemToString(heap.min.item));
        System.out.println("Last.rank: " +heap.last.rank);
        System.out.println("Last: " +itemToString(heap.last.item));
        System.out.println();
        System.out.println();

        String[][] toPrint = new String[heap.last.rank+1][(int) Math.pow(2, heap.last.rank)];
        for (int i = 0; i < toPrint.length; i++){
            for (int j = 0; j < toPrint[0].length; j++){
                toPrint[i][j] = "";
            }
        }
        int offset = 0;
        BinomialHeap.HeapNode node = heap.last;
        do {
            node = node.next;
            offset += printTree(node, toPrint, offset, 0);
        }while (node != heap.last);

        int[] lens = new int[toPrint[0].length];
        for(int i = 0; i < lens.length; i++){
            for (String[] strings : toPrint) {
                lens[i] = Math.max(lens[i], strings[i].length());
            }
        }
        for (String[] strings : toPrint) {
            for (int j = 0; j < toPrint[0].length; j++) {
                System.out.print(strings[j]);
                for (int k = 0; k < lens[j] - strings[j].length() + 3; k++)
                    System.out.print(' ');
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }
    public static int printTree(BinomialHeap.HeapNode tree, String[][] toPrint, int offsetX, int offsetY){
        toPrint[offsetY][offsetX] = itemToString(tree.item);
        if(tree.child != null){
            int offset = 0;
            BinomialHeap.HeapNode node = tree.child;
            do {
                node = node.next;
                offset += printTree(node, toPrint, offsetX+offset, offsetY+1);
            }while (node != tree.child);
            return offset;
        }
        return 1;
    }
    public static String itemToString(BinomialHeap.HeapItem item){
        return "<" + item.key + ", " + item.info + ">";
    }

    public static void main(String[] args){
        BinomialHeap heap = new BinomialHeap();
        for(int i = 1; i < 16; i++)
            heap.insert(2*i, "");
        printHeap(heap);
        heap.deleteMin();
        printHeap(heap);
        BinomialHeap.HeapNode del = heap.last.child;
        heap.delete(del.item);
        BinomialHeap.HeapNode inc = heap.last.child.child.child;
        printHeap(heap);
        heap.decreaseKey(inc.item, 25);
        printHeap(heap);
        while (!heap.empty()){
            heap.deleteMin();
            printHeap(heap);
        }
    }
}
