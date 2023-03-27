import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Atom {
    private final String element;
    private final List<Bond> bonds = new ArrayList<>();
    private final int valency;

    private static final Map<String, Integer> VALENCY_MAP = createMap();

    public Atom(String element) {
        this.element = element;
        this.valency = VALENCY_MAP.getOrDefault(element, 0);
    }

    private static Map<String, Integer> createMap() {
        return Map.of("H", 1, "C", 4, "B", 3, "N", 3, "O", 2, "F", 1, "P", 3, "S", 2, "Cl", 1, "Br", 1);
    }

    public String getElement() {
        return element;
    }

    public List<Bond> getBonds() {
        return bonds;
    }

    public int getValency() {
        return valency;
    }

    public void addBond(Bond bond) {
        bonds.add(bond);
    }

    public void removeBond(Bond bond) {
        bonds.remove(bond);
    }

    public int getValence() {
        return switch (element) {
            case "H" -> 1;
            case "C" -> 4;
            case "N", "O" -> 2;
            default -> 0;
        };
    }

    public int getBondCount() {
        return this.bonds.size();
    }

    public int getBoundWeight() {
        int boundCnt = 0;
        for (Bond bond : getBonds()) {
            boundCnt += bond.getWeight();
        }
        return boundCnt;
    }
}

