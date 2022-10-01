import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private int board[][];

    public Board() { board = new int[dim][dim]; }

    public Board(String str) throws IllegalStateException {
        if (str.length() != dim * dim)
            throw new IllegalStateException("Invalid arg in Board constructor");
        board = new int[dim][dim];
        int si = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                board[i][j] = Character.getNumericValue(str.charAt(si++));
    }

    public String toString() {
        String a = new String();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0)
                    a += " ";
                else
                    a+=String.valueOf(board[i][j]);
            }
            a += "\n";
        }
        return a;
    }

    public boolean equals(Object o) { return this.toString().equals(o.toString()); }

    public int hashCode() { 
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(board);
        return result; 
    }

    public char[] CharArray() {  
        // Cannot use Arrays.copyOf because of class initialization order issues  
    char[] ch=this.toString().toCharArray();  
    char[] temp = new char[9];
    int useful = 0;
    for(int i=0;i<ch.length;i++){
        if ( '\n' != ch[i] && '\r' != ch[i])  
            temp[useful++] = ch[i];
        }
    return temp;
    }  

    @Override
    public List<Ilayout> children() {
        List<Ilayout> children = new ArrayList<>();
        char[] a;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) {
                    if (i-1 >= 0) {
                        a = this.CharArray();
                        a[i*dim + j] = a[i*dim + j - dim];
                        a[i*dim + j - dim] = '0';
                        children.add(new Board(String.valueOf(a)));
                    }
                    if (i+1 < dim) {
                        a = this.CharArray();
                        a[i*dim + j] = a[i*dim + j + dim];
                        a[i*dim + j + dim] = '0';
                        children.add(new Board(String.valueOf(a)));
                    }
                    if (j-1 >= 0) {
                        a = this.CharArray();
                        a[i*dim + j] = a[i*dim + j - 1];
                        a[i*dim + j - 1] = '0';
                        children.add(new Board(String.valueOf(a)));
                    }
                    if (j+1 < dim) {
                        a = this.CharArray();
                        a[i*dim + j] = a[i*dim + j + 1];
                        a[i*dim + j + 1] = '0';
                        children.add(new Board(String.valueOf(a)));
                    }
                    return children;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isGoal(Ilayout l) { return this.equals(l); }

    @Override
    public double getG() { return 1; }
}