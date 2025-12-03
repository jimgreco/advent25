void main() throws IOException {
    IO.println(run1()); // 997
    IO.println(run2()); // 5978
}

// Parse into positive (right turns) and negative numbers (left turns).
// Add each number to a running sum.
// Increment a counter if the sum is a multiple of 100 (0 position on the dial).
int run1() throws IOException {
    var position = new AtomicInteger(50);
    return parse().map(x -> position.addAndGet(x) % 100 == 0 ? 1 : 0).sum();
}

// Parse into positive (right turns) and negative numbers (left turns).
// Add each number to a running sum.
// Increment a counter for each time zero is passed.
int run2() throws IOException {
    var position = new AtomicInteger(50);
    return parse().map(x -> {
        var oldValue = position.get();
        var newValue = position.addAndGet(x % 100);
        position.set((100 + newValue) % 100);
        return Math.abs(x / 100) // full spins
            + (oldValue != 0 && newValue < 0 || newValue == 0 || newValue >= 100 ? 1 : 0); // partial spins
    }).sum();
}

IntStream parse() throws IOException {
    return Files.readAllLines(Path.of("day01")).stream()
            .mapToInt(x -> (x.charAt(0) == 'L' ? -1 : 1) * Integer.parseInt(x.substring(1)));
}
