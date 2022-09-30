import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

class BestFirst {
    protected Queue<State> abertos;
    private Map<Ilayout, State> fechados;
    private State actual;
    private Ilayout objective;

    static class State {
        private Ilayout layout;
        private State father;
        private double g;

        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if (father != null)
                g = father.g + l.getG();
            else
                g = 0.0;
        }

        public String toString() { return layout.toString(); }

        public double getG() { return g; }

        public int hashCode() { return toString().hashCode(); }

        public boolean equals(Object o) {
            if (o == null)
                return false;
            if (this.getClass() != o.getClass())
                return false;
            State n = (State) o;
            return this.layout.equals(n.layout);
        }
    }

    final private List<State> sucessores(State n) {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    @SuppressWarnings("unchecked")
    final public Iterator<State> solve(Ilayout s, Ilayout goal) {
        objective = goal;
        abertos = new PriorityQueue<>(10, (s1, s2) -> (int) Math.signum(s1.getG() - s2.getG()));
        fechados = new HashMap<>();
        abertos.add(new State(s, null));
        List<State> sucs;
        State temp;
        Stack<Ilayout> stack = new Stack<>();

        while (true) {
            if (abertos.isEmpty()) {
                System.out.println("No solution has been found");
                return null;
            }
            actual = abertos.remove();
            if (actual.layout.equals(objective)) {
                temp = actual;
                while (temp.father != null) {
                    stack.push(temp.layout);
                    temp = temp.father;
                }
                stack.push(temp.layout);
                return stack.iterator();
            } else {
                sucs = sucessores(actual);
                fechados.put(actual.layout, actual.father);
                for (State child : sucs)
                    if (!fechados.containsKey(child.layout)) abertos.add(new State(child.layout, actual));
            }
        }

       /*  abertos.add(new State(s, null));
        List<State> sucs; // inicializei em list
        while (s.isGoal(goal)) { // objetivo e um loop ate que se encontre a solução, isto foi so para ter ai qq coisa
            if (abertos.isEmpty() || abertos == null) // nao percebi bem oq e com fracasso
                State actual = State(abertos.peek(), abertos.children()); //
                abertos.remove();
            if (s.equals(goal)) {

            }
            else{
                sucs = sucessores(actual);
                fechados.add(actual);
                for (state sucessor : sucs) 
                    { 
                        if (fechados.Contains(sucessor));
                            abertos.add(sucessor);
                    }
            }
            s.getG()++;
        } */
    }
}