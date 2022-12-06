import 'dart:io';

Future<List<int>> getWeightList() async {
  var lines = await File("bin/input.txt").readAsLines();
  var weightIndex = 0;
  List<int> weight = [];

  for (var line in lines) {
    if (line.isNotEmpty) {
      if (weight.length <= weightIndex) {
        weight.add(0);
      }

      weight[weightIndex] += int.parse(line);
    } else {
      weightIndex++;
    }
  }

  return weight;
}

void main(List<String> arguments) async {
  var weightList = await getWeightList();
  weightList.sort((a, b) => b.compareTo(a));

  if (weightList.length < 3) {
    print("Not enough data");
    exit(1);
  }

  print("Part1 ${weightList[0]}");
  print("Part2 ${weightList[0] + weightList[1] + weightList[2]}");
}
