void main() throws IOException {
    // 6957525317641
    IO.println(run1());
    // 13215665360076
    IO.println(run2());
}

private static long run1() throws IOException {
    var lines = Files.readAllLines(Path.of("day06"));
    var numbers = lines.subList(0, lines.size() - 1).stream()
            .map(x -> Arrays.stream(x.split("\\s+")).mapToLong(Integer::parseInt).toArray()).toList();
    var symbols = Arrays.stream(lines.getLast().split("\\s+")).map(x -> x.charAt(0)).toList();

    var total = 0L;
    for (var col = 0; col < symbols.size(); col++) {
        var subtotal = numbers.get(0)[col];
        for (var row = 1; row < numbers.size(); row++) {
            if (symbols.get(col) == '*') {
                subtotal *= numbers.get(row)[col];
            } else {
                subtotal += numbers.get(row)[col];
            }
        }
        total += subtotal;
    }
    return total;
}

private static long run2() throws IOException {
    var lines = Files.readAllLines(Path.of("day06"));
    var symbolsLine = lines.getLast();
    var nextNumberIndex = 0;
    var total = 0L;
    while (nextNumberIndex < symbolsLine.length()) {
        var numberIndex = nextNumberIndex++; // start of the number
        // find the start of the next number
        while (nextNumberIndex < symbolsLine.length() && symbolsLine.charAt(nextNumberIndex) == ' ') {
            nextNumberIndex++;
        }
        if (nextNumberIndex == symbolsLine.length()) {
            nextNumberIndex = lines.getFirst().length() + 1; // last problem edge case
        }

        var subtotal = symbolsLine.charAt(numberIndex) == '*' ? 1L : 0L;
        // iterate over each column to get each number
        for (var col = numberIndex; col < nextNumberIndex - 1; col++) {
            // build the number from each row
            var num = 0L;
            for (var row = 0; row < lines.size() - 1; row++) {
                var ch = lines.get(row).charAt(col);
                if (ch != ' ') {     // spaces are at the end or beginning of the number
                    num *= 10;
                    num += ch - '0';
                }
            }
            if (symbolsLine.charAt(numberIndex) == '*') {
                subtotal *= num;
            } else {
                subtotal += num;
            }
        }
        total += subtotal;
    }
    return total;
}