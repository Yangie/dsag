

public class TreeMolecule implements Molecule {
    private final Atom root;

    public TreeMolecule(Atom root) {
        this.root = root;
    }

    @Override
    public boolean addBond(Atom a1, Atom a2, int strength) {
        if (!contains(a1) || contains(a2)) {
            return false;
        }
        Bond bond = new Bond(a2, strength);
        a1.addBond(bond);
        a1.getBonds().removeIf(b -> b.getChild().getElement().equals("H"));
        a2.getBonds().removeIf(b -> b.getChild().getElement().equals("H"));

        for (int i = 0; i < strength; i++) {
            a1.getBonds().removeIf(b -> b.getChild().getElement().equals("H"));
            a2.getBonds().removeIf(b -> b.getChild().getElement().equals("H"));
        }
        return true;
    }

    @Override
    public boolean contains(Atom a) {
        return dfs(a, root);
    }

    private boolean dfs(Atom target, Atom current) {
        if (current == null) {
            return false;
        }
        if (current.equals(target)) {
            return true;
        }
        for (Bond bond : current.getBonds()) {
            if (dfs(target, bond.getChild())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String smilesString() {
        StringBuilder sb = new StringBuilder();
        smilesHelper(root, sb, false, 0);
        return sb.toString();
    }

    private void smilesHelper(Atom current, StringBuilder sb, boolean includeHydrogen, int cnt) {
        if (current == null) {
            return;
        }
        sb.append(current.getElement());
        int nonHydrogenChildCount = 0;
        for (Bond bond : current.getBonds()) {
            if (!bond.getChild().getElement().equals("H")) {
                nonHydrogenChildCount++;
            }
        }
        if (includeHydrogen) {
            int boundCnt = current.getBoundWeight();
            int hydrogenCount = current.getValence() - boundCnt;
            if (hydrogenCount == 1) {
                sb.append("H");
            } else if (hydrogenCount > 1) {
                sb.append("H").append(hydrogenCount);
            }
        }

        for (Bond bond : current.getBonds()) {
            if (formatHelper(sb, includeHydrogen, nonHydrogenChildCount, bond)) continue;
            smilesHelper(bond.getChild(), sb, includeHydrogen, bond.getWeight());
            if (nonHydrogenChildCount > 1 && !bond.getChild().getElement().equals("H")) {
                sb.append(')');
            }
        }
    }

    private boolean formatHelper(StringBuilder sb, boolean includeHydrogen, int nonHydrogenChildCount, Bond bond) {
        if (bond.getChild().getElement().equals("H") && !includeHydrogen) {
            return true;
        }
        if (bond.getWeight() == 2) {
            sb.append('=');
        } else if (bond.getWeight() == 3) {
            sb.append('#');
        }
        if (nonHydrogenChildCount > 1 && !bond.getChild().getElement().equals("H")) {
            sb.append('(');
        }
        return false;
    }


    @Override
    public String structuralFormula() {
        StringBuilder sb = new StringBuilder();
        smilesHelper(root, sb, true, 0);
        return sb.toString();
    }
}

//331