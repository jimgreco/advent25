void main() throws IOException {
    IO.println(run1()); // 44854383294
    IO.println(run2()); // 55647141923
}

// Iterate through every number in each range.
// Split the number into the first N/2 digits (divide) and the second N/2 digits (mod) and compare,
// where N is the number of digits in the number.
// No string compare!
long run1() throws IOException {
    return parse().mapToLong(x -> {
        var current = x.low;
        var sum = 0L;
        while (current <= x.high) {
            var digits = digits(current);
            if (digits % 2 == 0) {
                var divisor = divisor(digits / 2);
                if (current / divisor == current % divisor) {
                    sum += current;
                }
            }
            current++;
        }
        return sum;
    }).sum();
}


// Iterate through every number in each range.
// For each number, look for repeating sequences by iterating from 1 to N/2,
// where N is the number of digits in the number.
// No string compare!
long run2() throws IOException {
    return parse().mapToLong(x -> {
        var current = Math.max(10, x.low); // single digits are never a pattern
        var sum = 0L;
        while (current <= x.high) {
            var digits = digits(current);
            for (var patternLen = 1; patternLen <= digits / 2; patternLen++) {
                var found = true;
                if (digits % patternLen == 0) {
                    var divisor = divisor(patternLen);
                    var pattern = current % divisor;
                    var remaining = current / divisor;
                    while (remaining > 0) {
                        if (pattern != remaining % divisor) {
                            found = false;
                            break;
                        }
                        remaining /= divisor;
                    }
                    if (found) {
                        sum += current;
                        break;
                    }
                }
            }
            current++;
        }
        return sum;
    }).sum();
}

record Range(long low, long high) {}

Stream<Range> parse() throws IOException {
    return Arrays.stream(Files.readString(Path.of("day02")).split(","))
            .map(x -> new Range(Long.parseLong(x.split("-")[0]), Long.parseLong(x.split("-")[1])));
}

int digits(long num) {
    var digits = 0;
    while (num > 0) {
        digits++;
        num /= 10;
    }
    return digits;
}

long divisor(int digits) {
    var n = 1L;
    while (digits-- > 0) {
        n *= 10;
    }
    return n;
}