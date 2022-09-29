import java.util.ArrayList;
import java.util.List;

interface Ilayout {
    /**
     * @return the children of the receiver.
     */
    List<Ilayout> children();

    /**
     * @return true if the receiver equals the argument l;
     *         return false otherwise.
     */
    boolean isGoal(Ilayout l);

    /**
     * @return the cost for moving from the input config to the receiver.
     */
    double getG();
}

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
                    a += Integer.toString(board[i][j]);
            }
            a += "\r\n";
        }
        return a;
    }

    public boolean equals(Object o) { return this.toString().equals(o.toString()); }

    public int hashCode() {
        // TO BE COMPLETED
        return 0;
    }

    @Override
    public List<Ilayout> children() {
        List<Ilayout> children = new ArrayList<Ilayout>();
        Board temp;
        char[] a;
        char b;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) {
                    if (i-1 > 0) {
                        a = this.toString().toCharArray();
                        b = a[i*dim + j];
                        a[i*dim + j] = a[i*dim + j - dim];
                        a[i*dim + j - dim] = b;
                        temp = new Board(String.valueOf(a));
                        children.add(temp);
                    }
                    if (i+1 < dim) {
                        a = this.toString().toCharArray();
                        b = a[i*dim + j];
                        a[i*dim + j] = a[i*dim + j + dim];
                        a[i*dim + j + dim] = b;
                        temp = new Board(String.valueOf(a));
                        children.add(temp);
                    }
                    if (j-1 > 0) {
                        a = this.toString().toCharArray();
                        b = a[i*dim + j];
                        a[i*dim + j] = a[i*dim + j - 1];
                        a[i*dim + j - 1] = b;
                        temp = new Board(String.valueOf(a));
                        children.add(temp);
                    }
                    if (j+1 < dim) {
                        a = this.toString().toCharArray();
                        b = a[i*dim + j];
                        a[i*dim + j] = a[i*dim + j + 1];
                        a[i*dim + j + 1] = b;
                        temp = new Board(String.valueOf(a));
                        children.add(temp);
                    }
                    return children;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }

    @Override
    public double getG() {
        // TODO Auto-generated method stub
        return 0;
    }
    // … TO BE COMPLETED
}