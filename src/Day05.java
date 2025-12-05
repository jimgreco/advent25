void main() throws IOException {
    var ranges = new ArrayList<long[]>();
    var ingredients = new ArrayList<Long>();
    for (var line : Files.readAllLines(Path.of("day05"))) {
        if (!line.isBlank() && line.contains("-")) {
            ranges.add(new long[] { Long.parseLong(line.split("-")[0]), Long.parseLong(line.split("-")[1]) });
        } else if (!line.isBlank()) {
            ingredients.add(Long.parseLong(line));
        }
    }
    // sort in order
    ranges.sort(Comparator.comparingLong(x -> x[0]));

    // 733
    IO.println(run1(ingredients, ranges));

    // 345821388687084
    IO.println(run2(ranges));
}

// Check if each ingrediant is in each range.
// You could do a binary search, but this is a small dataset.
int run1(ArrayList<Long> ingredients, ArrayList<long[]> ranges) {
    var fresh = 0;
    for (var ingredient : ingredients) {
        for (var range : ranges) {
            if (ingredient >= range[0] && ingredient <= range[1]) {
                fresh++;
                break;
            }
        }
    }
    return fresh;
}

// Search through each range.
private long run2(ArrayList<long[]> ranges) {
    var fresh = 0L;
    var highest = 0L;
    for (var range : ranges) {
        if (range[0] >= highest) {
            fresh += range[1] - range[0] + 1;
            highest = range[1] + 1;
        } else if (range[1] >= highest) {
            fresh += range[1] - highest + 1;
            highest = range[1] + 1;
        }
        // else, highest > range[1], already covered
    }
    return fresh;
}