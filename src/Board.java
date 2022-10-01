import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private int board[][];

    public Board() {
        board = new int[dim][dim];
    }

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
                    a += String.valueOf(board[i][j]);
            }
            a += "\n";
        }
        return a;
    }

    public char[] toChar() {
        char[] a = new char[9];
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                a[i * 3 + j] = (char)(board[i][j] + '0');
        return a;
    }

    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(board);
        return result;
    }

    @Override
    public List<Ilayout> children() {
        List<Ilayout> children = new ArrayList<>();
        char[] a;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) {
                    if (i - 1 >= 0) {
                        a = this.toChar();
                        a[i * dim + j] = a[i * dim + j - dim];
                        a[i * dim + j - dim] = '0';
                        children.add(new Board(String.valueOf(a)));
                    }
                    if (i + 1 < dim) {
                        a = this.toChar();
                        a[i * dim + j] = a[i * dim + j + dim];
                        a[i * dim + j + dim] = '0';
                        children.add(new Board(String.valueOf(a)));
                    }
                    if (j - 1 >= 0) {
                        a = this.toChar();
                        a[i * dim + j] = a[i * dim + j - 1];
                        a[i * dim + j - 1] = '0';
                        children.add(new Board(String.valueOf(a)));
                    }
                    if (j + 1 < dim) {
                        a = this.toChar();
                        a[i * dim + j] = a[i * dim + j + 1];
                        a[i * dim + j + 1] = '0';
                        children.add(new Board(String.valueOf(a)));
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
        return 1;
    }
}