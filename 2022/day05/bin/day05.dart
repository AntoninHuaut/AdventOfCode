import 'dart:collection';
import 'dart:io';

class Move {
  int nb;
  int from;
  int to;
  Move(this.nb, this.from, this.to);

  @override
  String toString() {
    return 'Move{nb: $nb, from: $from, to: $to}';
  }
}

class State {
  List<ListQueue<String>> positions = [];
  List<Move> moves = [];
  State(this.positions, this.moves);

  @override
  String toString() {
    return 'State{\n  positions: $positions, \n  moves: $moves\n}';
  }

  State.clone(State state) {
    positions = [];
    for (var i = 0; i < state.positions.length; i++) {
      positions.add(ListQueue<String>.from(state.positions[i]));
    }
    moves = List<Move>.from(state.moves);
  }
}

Future<State> parseInput(String fileName) async {
  var lines = await File("bin/$fileName.txt").readAsLines();
  List<ListQueue<String>> positions = [];
  List<Move> moves = [];

  var isSetup = true;

  for (var line in lines) {
    if (line.isEmpty) {
      isSetup = false;
      continue;
    }

    if (isSetup) {
      var chars = line.split('');

      for (var i = 0; i < chars.length; i += 4) {
        if (chars[i] == ' ' || chars[i + 2] == ' ') continue;

        var index = ((i / 4) + 1).toInt();
        while (positions.length < index) {
          positions.add(ListQueue());
        }

        positions[index - 1].addLast(chars[i + 1]);
      }
    } else {
      var chars = line.split(' ');
      var nb = int.parse(chars[1]);
      var from = int.parse(chars[3]);
      var to = int.parse(chars[5]);

      moves.add(Move(nb, from, to));
    }
  }

  return State(positions, moves);
}

void partOne(State state) {
  for (var move in state.moves) {
    var nb = move.nb;
    var from = state.positions[move.from - 1];
    var to = state.positions[move.to - 1];

    while (nb-- > 0) {
      to.addFirst(from.removeFirst());
    }
  }

  var result = '';
  for (var position in state.positions) {
    result += position.first;
  }

  print("Part1: $result");
}

void partTwo(State state) {
  for (var move in state.moves) {
    var from = state.positions[move.from - 1];
    var to = state.positions[move.to - 1];

    var nb = move.nb;
    var toMove = ListQueue<String>();
    while (nb-- > 0) {
      toMove.addFirst(from.removeFirst());
    }

    for (var element in toMove) {
      to.addFirst(element);
    }
  }

  var result = '';
  for (var position in state.positions) {
    result += position.first;
  }

  print("Part2: $result");
}

void main(List<String> arguments) async {
  var initialState = await parseInput("input");
  partOne(State.clone(initialState));
  partTwo(State.clone(initialState));
}
