import 'dart:collection';
import 'dart:io';

class FileSys {
  final FileSys? parent;
  final String name;
  final int weight;
  List<FileSys> children = [];

  FileSys(this.parent, this.name, this.weight);

  @override
  String toString() {
    return 'FileSys{name: $name, weight: $weight, children: $children}';
  }

  int get totalWeight => weight + children.fold(0, (a, b) => a + b.totalWeight);

  int get childWeight => children.fold(0, (a, b) => a + b.childWeight + (b.children.isEmpty ? b.weight : 0));
}

FileSys parseFileSys(List<String> input) {
  FileSys? current;

  for (int lineIndex = 0; lineIndex < input.length; lineIndex++) {
    var line = input[lineIndex];
    var parts = line.split(" ");
    var first = parts[0];

    if (first == '\$') {
      var cmd = parts[1];

      if (cmd == 'cd') {
        var name = parts[2];
        if (name == '..') {
          current = current?.parent;
        } else {
          var child = FileSys(current, name, 0);
          current?.children.add(child);
          current = child;
        }
      }
      //
      else if (cmd == 'ls') {
        lineIndex++;
        while (lineIndex < input.length && !input[lineIndex].startsWith("\$")) {
          var subLine = input[lineIndex];
          var subParts = subLine.split(" ");

          var type = subParts[0];
          if (type == 'dir') {
            lineIndex++;
            continue;
          }

          var weight = int.parse(subParts[0]);
          var name = subParts[1];
          var child = FileSys(current, name, weight);

          current?.children.add(child);
          lineIndex++;
        }
        lineIndex--;
      }
    } else {
      print("PANIC");
      exit(1);
    }
  }

  while (current?.parent != null) {
    current = current?.parent;
  }

  if (current == null) {
    throw Exception("PANIC FileSys null");
  }

  return current;
}

void printFileSys(FileSys file, int indent) {
  var optType = file.children.isNotEmpty ? 'dir' : 'file, size=${file.weight}';
  print('${' ' * indent} - ${file.name} ($optType)');
  for (var child in file.children) {
    printFileSys(child, indent + 2);
  }
}

int partOne(FileSys root) {
  var total = 0;

  for (var child in root.children) {
    if (child.totalWeight <= 100000) {
      total += child.childWeight;
    }
    total += partOne(child);
  }

  return total;
}

int partTwo(FileSys root) {
  var availableSpace = 70000000;
  var needSpace = 30000000;
  var totalWeight = root.totalWeight;
  var useSpace = availableSpace - totalWeight;
  var missingSpace = needSpace - useSpace;

  int bestSize = -1;
  ListQueue<FileSys> toCheck = ListQueue();
  toCheck.addAll(root.children);

  while (toCheck.isNotEmpty) {
    var child = toCheck.removeFirst();
    if (child.children.isEmpty) {
      // Not a directory
      continue;
    }

    var childTotalWeight = child.totalWeight;
    if (childTotalWeight >= missingSpace && (childTotalWeight < bestSize || bestSize == -1)) {
      bestSize = childTotalWeight;
    }

    toCheck.addAll(child.children);
  }

  return bestSize;
}

void main(List<String> arguments) async {
  var input = await File("bin/input.txt").readAsLines();
  var rootFile = parseFileSys(input);

  printFileSys(rootFile, 0);
  print("Part1: ${partOne(rootFile)}");
  print("Part2: ${partTwo(rootFile)}");
}
