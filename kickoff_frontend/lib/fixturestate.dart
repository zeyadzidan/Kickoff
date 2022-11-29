import 'package:flutter/material.dart';

import 'fixtures.dart';

class FixturesState extends State<Fixtures> {
  final List fixturesList;
  final int rowsNumber;
  late List<bool> selected;

  FixturesState(this.fixturesList, this.rowsNumber) {
    selected = List<bool>.generate(rowsNumber, (index) => false);
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: MediaQuery.of(context).size.width,
      child: DataTable(
          headingRowColor: MaterialStateProperty.resolveWith<Color?>(
              (Set<MaterialState> states) {
                return Colors.transparent;
              }
          ),
          showCheckboxColumn: false,
          dividerThickness: 3,
          columns: const <DataColumn>[
            DataColumn(
                label: Expanded(
                  child: Text(""),
                )
            ),
            DataColumn(
                label: Expanded(
                  child: Text("FIXTURES"),
                )
            ),
            DataColumn(
                label: Expanded(
                  child: Text(""),
                )
            ),
          ],
          rows: List<DataRow>.generate(
            rowsNumber,
            (i) => DataRow(
              color: MaterialStateProperty.resolveWith<Color?>(
                      (Set<MaterialState> states) {
                        if(states.contains(MaterialState.selected)) {
                          return Colors.green.withOpacity(0.8);
                        } else if (states.contains(MaterialState.hovered)) {
                          return Colors.green.withOpacity(0.3);
                        }
                        return null;
                      }
              ),
              cells: List<DataCell>.generate(
                  3,
                  (j) => DataCell(
                          Text(
                              fixturesList[i][j].toString()
                          )
                  )
              ),
              selected: selected[i],
              onSelectChanged: (bool? value) {
                setState(() {
                  selected[i] = value!;
                });
              },
            )
          )
      ),
    );
  }
}