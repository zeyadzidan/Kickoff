import 'package:flutter/material.dart';

import 'fpbuilder.dart';

class FPListBuilder extends StatefulWidget {
  final List _info;
  const FPListBuilder(this._info, {super.key});
  @override
  State<FPListBuilder> createState() => FPLBuilderState(
    _info[0],
    _info[1],
    _info[2],
    _info[3],
  );
}

class FPLBuilderState extends State<FPListBuilder> {
  final FixturePanelBuilder _panelBuilder = FixturePanelBuilder();
  final int _panelsNumber;
  final List<String> _listHeaders;
  final List<String> _listBodies;
  final List<bool> _listExpanded;

  FPLBuilderState(
      this._panelsNumber,
      this._listHeaders,
      this._listBodies,
      this._listExpanded
      );

  @override
  Widget build(BuildContext context) => ExpansionPanelList(
    animationDuration: const Duration(milliseconds: 300),
    expandedHeaderPadding: EdgeInsets.zero,
    dividerColor: Theme.of(context).dividerColor,
    elevation: 4,
    children: List<ExpansionPanel>.generate(
        _panelsNumber,
            (index) => _panelBuilder.build(
            _listHeaders[index],
            _listBodies[index],
            _listExpanded[index]
        )
    ),
    expansionCallback: (i, isExpanded) =>
        setState(
                () =>
            _listExpanded[i] = !isExpanded
        ),
  );
}