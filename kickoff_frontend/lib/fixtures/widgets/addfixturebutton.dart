import 'package:flutter/material.dart';
import 'package:kickoff_frontend/fixtures/classes/datepicker.dart';

class AddFixtureButton extends StatefulWidget {
  const AddFixtureButton({super.key});

  @override
  State<AddFixtureButton> createState() => AddFixtureButtonState();

}

class AddFixtureButtonState extends State<AddFixtureButton> {
  @override
  Widget build(BuildContext context) => FloatingActionButton(
    onPressed: () => showModalBottomSheet(
        context: context,
        builder: (BuildContext context) => SingleChildScrollView(
          child: SizedBox(
            height: 500,
            child: Form(
              child: Padding(
                padding: const EdgeInsets.symmetric(vertical: 15, horizontal: 30),
                child: Column(
                  children: [
                    TextFormField(
                      keyboardType: TextInputType.name,
                      validator: (input) =>
                      (input == null) ? 'Name cannot be blank' : null,
                      decoration: const InputDecoration(
                        hintText: "Player Name",
                        prefixIcon: Icon(Icons.person),
                      ),
                    ),
                    const Divider(
                      height: 3,
                      thickness: 2,
                    ),
                    DropdownButtonFormField<String>(
                      decoration: const InputDecoration(
                        prefixIcon: Icon(Icons.stadium),
                        labelText: "Court to be reserved",
                      ),
                      items: const [
                        DropdownMenuItem(
                            child: Text(
                            "COURT # 500"
                            )
                        )
                      ],
                      onTap: () => 0,
                      onChanged: (Object? value) => 0,
                    ),
                    const Divider(
                      height: 3,
                      thickness: 2,
                    ),
                    InputDatePickerFormField(
                      firstDate: DateTime.now(),
                      lastDate: DateTime.now(),
                    ),
                  ],
                )
              ),
            ),
          ),
        ),
        elevation: 4,
    ),
    foregroundColor: Theme.of(context).floatingActionButtonTheme.foregroundColor,
    backgroundColor: Theme.of(context).floatingActionButtonTheme.backgroundColor,
    hoverColor: Colors.green.shade800,
    hoverElevation: 4,
    child: const Icon(Icons.add, size: 35,)
  );
}