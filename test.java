public class test {
    public static void printHeap(BinomialHeap heap){
        System.out.println(heap.size);
        System.out.println(heap.numTrees);
        if (heap.last == null)
            return;
        System.out.println(heap.min.item.key);
        String[][] toPrint = new String[(int) Math.pow(2, heap.last.rank)][heap.last.rank+1];
        int offset = 0;
        BinomialHeap.HeapNode node = heap.last;
        do {
            node = node.next;
            offset += printTree(node, toPrint, offset, 0);
        }while (node != heap.last);
        int[] lens = new int[(int) Math.pow(2, heap.last.rank)];
        for(int i = 0; i < lens.length; i++){
            for(int j = 0; j < toPrint[0].length; j++){
                lens[i] = Math.max(lens[i], toPrint[i][j].length());
            }
        }
        for(int i = 0; i < toPrint[0].length; i++){
            for(int j = 0; j < lens.length; j++){
                System.out.print(toPrint[j][i]);
                for(int k = 0; k < lens[i] - toPrint[j][i].length(); k++)
                    System.out.print(' ');
            }
            System.out.println(' ');
        }
    }
    public static int printTree(BinomialHeap.HeapNode tree, String[][] toPrint, int offsetX, int offsetY){
        toPrint[offsetX][offsetY] = toString(tree.item);
        if(tree.child != null){
            int offset = 0;
            BinomialHeap.HeapNode node = tree.child;
            do {
                node = node.next;
                offset += printTree(node, toPrint, offsetX+offset, offsetY+1);
            }while (node != tree.child);
        }
        return 1;
    }
    public static String toString(BinomialHeap.HeapItem item){
        return "<" + Integer.toString(item.key) + ", " + item.info + ">";
    }
    public static void main(String[] args){
        BinomialHeap heap = new BinomialHeap();
        heap.insert(1, "abc");
        heap.insert(2, "abcd");
        printHeap(heap);
    }
}
