void main() throws IOException {
    var grid = Files.readAllLines(Path.of("day04")).stream()
            .map(String::toCharArray).toList().toArray(new char[0][]);

    // 1533
    IO.println(doPart1(grid));
    // 9206
    IO.println(doPart2(grid));
}

// Iterate through each position on the grid and look at the 8 adjacent grid points.
// Sum the number of positions that have 4 or less '@' symbols.
private static int doPart1(char[][] grid) {
    return searchAll(grid, false);
}

// Iterate through each position on the grid and look at the 8 adjacent grid points.
// Sum the number of positions that have 4 or less '@' symbols.
// Replace the position with a non-'@' symbol and continue iterating.
// Reiterate through each grid again and again until zero additional positions with 4 or less '@' symbols are found.
private static int doPart2(char[][] grid) {
    var total = 0;
    while (true) {
        var found = searchAll(grid, true);
        if (found == 0) {
            return total;
        }
        total += found;
    }
}

private static int searchAll(char[][] grid, boolean replace) {
    var offsets = new int[][] {{ -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }};
    var accessibleRolls = 0;
    for (var i = 0; i < grid.length; i++) {
        for (var j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == '@') {
                var adjacentRolls = 0;
                for (var offset : offsets) {
                    if (i + offset[0] >= 0 && i + offset[0] < grid.length               // valid row
                            && j + offset[1] >= 0 && j + offset[1] < grid[0].length     // valid col
                            && grid[i + offset[0]][j + offset[1]] == '@') {             // is a roll
                        adjacentRolls++;
                    }
                }
                if (adjacentRolls <= 3) {
                    accessibleRolls++;
                    if (replace) {
                        grid[i][j] = '.';
                    }
                }
            }
        }
    }
    return accessibleRolls;
}
