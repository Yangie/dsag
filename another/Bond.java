public class Bond {
    private Atom child;
    private int weight;

    public Bond(Atom child, int weight) {
        this.child = child;
        this.weight = weight;
    }

    public Atom getChild() {
        return child;
    }

    public int getWeight() {
        return weight;
    }

    public void setChild(Atom child) {
        this.child = child;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
