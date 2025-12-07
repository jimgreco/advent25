void main() throws IOException {
    var grid = Files.readAllLines(Path.of("day07")).stream()
            .map(String::toCharArray).toList().toArray(new char[0][]);

    // 1587
    IO.println(run1(grid));
    // 2012790981 too low
    IO.println(run2(grid));
}

// Run a BFS and count the splitters.
private static int run1(char[][] grid) {
    var rowBeams = new HashSet<Integer>();
    rowBeams.add(new String(grid[0]).indexOf("S")); // start position

    var splitCount = 0;
    for (var row = 1; row < grid.length; row++) {
        var nextRowBeams = new HashSet<Integer>();
        var size = rowBeams.size();
        var it = rowBeams.iterator();
        for (var i = 0; i < size; i++) {
            var col = it.next();
            it.remove();
            if (grid[row][col] == '^') {
                nextRowBeams.add(col - 1);
                nextRowBeams.add(col + 1);
                splitCount++;
            } else {
                nextRowBeams.add(col);
            }
        }
        rowBeams = nextRowBeams;
    }
    return splitCount;
}

// Run a dfs and count each unique path.
// Use memoization to have it run this century.
long run2(char[][] grid) {
    return dfs(new HashMap<Coord, Long>(), grid, new Coord(1, new String(grid[0]).indexOf("S")));
}

long dfs(Map<Coord, Long> memo, char[][] grid, Coord coords) {
    if (coords.row == grid.length) {
        return 1;
    } else if (memo.containsKey(coords)) {
        return memo.get(coords);
    } else if (grid[coords.row][coords.col] == '^') {
        memo.put(coords, dfs(memo, grid, new Coord(coords.row + 1, coords.col - 1))
                + dfs(memo, grid, new Coord(coords.row + 1, coords.col + 1)));
        return memo.get(coords);
    } else {
        memo.put(coords, dfs(memo, grid, new Coord(coords.row + 1, coords.col)));
        return memo.get(coords);
    }
}

record Coord(int row, int col) {}
