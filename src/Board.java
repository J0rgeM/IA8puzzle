import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private int board[][];
    private int zeroI, zeroJ;

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
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (this.board[i][j] != ((Board) o).board[i][j]) return false;
            }
        }
        return true;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(board);
        return result;
    }

    public Board clone() {
        Board clone = new Board();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                clone.board[i][j] = this.board[i][j];
                if (this.board[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                }
            }
        }
        return clone;
    }

    @Override
    public List<Ilayout> children() {
        List<Ilayout> children = new ArrayList<>();
        Board clone = this.clone();
        if (this.zeroI - 1 >= 0) {
            clone.board[this.zeroI][this.zeroJ] = clone.board[this.zeroI - 1][this.zeroJ];
            clone.board[this.zeroI - 1][this.zeroJ] = 0;
            children.add(clone);
        }
        if (this.zeroI + 1 < dim) {
            clone = this.clone();
            clone.board[this.zeroI][this.zeroJ] = clone.board[this.zeroI + 1][this.zeroJ];
            clone.board[this.zeroI + 1][this.zeroJ] = 0;
            children.add(clone);
        }
        if (this.zeroJ - 1 >= 0) {
            clone = this.clone();
            clone.board[this.zeroI][this.zeroJ] = clone.board[this.zeroI][this.zeroJ - 1];
            clone.board[this.zeroI][this.zeroJ - 1] = 0;
            children.add(clone);
        }
        if (this.zeroJ + 1 < dim) {
            clone = this.clone();
            clone.board[this.zeroI][this.zeroJ] = clone.board[this.zeroI][this.zeroJ + 1];
            clone.board[this.zeroI][this.zeroJ + 1] = 0;
            children.add(clone);
        }
        return children;
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