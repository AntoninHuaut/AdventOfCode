import 'dart:collection';
import 'dart:io';

Map<String, int> count(ListQueue<String> elements) {
  return elements.fold<Map<String, int>>({}, (map, element) {
    map[element] = (map[element] ?? 0) + 1;
    return map;
  });
}

int compute(String input, int limit) {
  var currentChars = ListQueue<String>();
  for (var i = 0; i < input.length; i++) {
    var char = input[i];
    if (i > limit - 1) {
      currentChars.removeFirst();
    }

    currentChars.addLast(char);

    if (currentChars.length == limit) {
      var counts = count(currentChars);
      if (currentChars.every((element) => counts[element] == 1)) {
        return i + 1;
      }
    }
  }

  return -1;
}

void main(List<String> arguments) async {
  var input = await File("bin/input.txt").readAsString();

  print("Part1: ${compute(input, 4)}");
  print("Part2: ${compute(input, 14)}");
}
