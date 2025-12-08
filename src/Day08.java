void main() throws IOException {
    // 244188
    IO.println(run1());
    // 8361881885
    IO.println(run2());
}

long run1() throws IOException {
    var circuits = new LinkedList<Set<Coord>>();
    var coordToCircuit = new HashMap<Coord, Set<Coord>>();
    var distances = new ArrayList<Distance>();
    setup(circuits, coordToCircuit, distances);

    // combine circuits
    for (var i = 0; i < 1000; i++) {
        combineCircuits(circuits, coordToCircuit, distances.get(i));
    }
    circuits.sort((o1, o2) -> Integer.compare(o2.size(), o1.size()));
    return (long) circuits.get(0).size() * circuits.get(1).size() * circuits.get(2).size();
}

long run2() throws IOException {
    var circuits = new LinkedList<Set<Coord>>();
    var coordToCircuit = new HashMap<Coord, Set<Coord>>();
    var distances = new ArrayList<Distance>();
    setup(circuits, coordToCircuit, distances);

    Distance lastDistance = null;
    for (var distance : distances) {
        if (combineCircuits(circuits, coordToCircuit, distance)) {
            lastDistance = distance;
        }
    }
    return (long) lastDistance.coord1.x * lastDistance.coord2.x;
}

private static void setup(List<Set<Coord>> circuits, Map<Coord, Set<Coord>> coordToCircuit, List<Distance> distances)
        throws IOException {
    var coords = Files.readAllLines(Path.of("day08")).stream()
            .map(x -> Arrays.stream(x.split(",")).mapToInt(Integer::parseInt).toArray())
            .map(x -> new Coord(x[0], x[1], x[2]))
            .toList();

    // add individual circuits
    for (var coord : coords) {
        var circuit = new HashSet<Coord>();
        circuit.add(coord);
        circuits.add(circuit);
        coordToCircuit.put(coord, circuit);
    }

    // find the 1000 pairs of coordinates with the smallest distance
    for (var i = 0; i < coords.size(); i++) {
        for (var j = i + 1; j < coords.size(); j++) {
            var c1 = coords.get(i);
            var c2 = coords.get(j);
            var distance = Math.sqrt(1.0 * (c2.x - c1.x) * (c2.x - c1.x)
                    + 1.0 * (c2.y - c1.y) * (c2.y - c1.y)
                    + 1.0 * (c2.z - c1.z) * (c2.z - c1.z));
            distances.add(new Distance(c1, c2, distance));
        }
    }
    distances.sort(Comparator.comparingDouble((Distance o) -> o.distance));
}

private static boolean combineCircuits(List<Set<Coord>> circuits, Map<Coord, Set<Coord>> coordToCircuit, Distance distance) {
    var circuit1 = coordToCircuit.get(distance.coord1);
    var circuit2 = coordToCircuit.get(distance.coord2);

    // combine
    if (circuit1 != circuit2) {
        circuits.remove(circuit1); // Java caches the hash code value so we have to remove, add new elements, then add back
        circuits.remove(circuit2);
        circuit1.addAll(circuit2);
        circuits.add(circuit1);
        circuit2.forEach(x -> coordToCircuit.put(x, circuit1)); // update all
        return true;
    }
    return false;
}

record Coord(int x, int y, int z) {}

record Distance(Coord coord1, Coord coord2, double distance) {}
