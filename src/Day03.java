void main() throws IOException {
    // 16973
    IO.println(run(2));
    // 168027167146027
    IO.println(run(12));
}

// For each digit in a number with N digits, search from left to right for the largest number.
// Record the index when a larger number is found.
private static long run(int digits) throws IOException {
    var sum = 0L;
    for (var bank : Files.readAllLines(Path.of("day03"))) {
        var batteries = bank.toCharArray();

        // pre-fill with the first N indexes
        var largest = new int[digits];
        fillSequential(largest, 0, 0);

        // search from left to right for the highest number
        for (var largestIdx = 0; largestIdx < digits; largestIdx++) {
            for (var i = largest[largestIdx] + 1; i < batteries.length - (digits - largestIdx - 1); i++) {
                if (batteries[i] > batteries[largest[largestIdx]]) {
                    fillSequential(largest, largestIdx, i);
                }
            }
        }

        // build number from character string
        var num = 0L;
        for (var i = 0; i < digits; i++) {
            num *= 10;
            num += batteries[largest[i]] - '0';
        }
        sum += num;
    }
    return sum;
}

private static void fillSequential(int[] array, int startIdx, int value) {
    array[startIdx] = value;
    for (var i = 1; i < array.length - startIdx; i++) {
        array[startIdx + i] = value + i;
    }
}