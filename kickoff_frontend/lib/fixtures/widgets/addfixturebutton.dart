import 'package:flutter/material.dart';
import 'package:kickoff_frontend/fixtures/widgets/application.dart';

class AddFixtureButton extends StatefulWidget {
  const AddFixtureButton({super.key});

  @override
  State<AddFixtureButton> createState() => AddFixtureButtonState();

}

class AddFixtureButtonState extends State<AddFixtureButton> {
  late String _selectedTime = TimeOfDay.now().format(context);

  _pickTime() async {
    final String? timeOfDay = (await showTimePicker(
      context: context,
      initialTime: TimeOfDay.now(),
    ))?.format(context);

    if (timeOfDay != _selectedTime) {
      setState(() => _selectedTime = timeOfDay!);
    }
  }
  
  @override
  Widget build(BuildContext context) => FloatingActionButton(
      onPressed: () {
        GlobalKey<FormState> formKey = GlobalKey();
        List ticketInformation = <String>[];
        showModalBottomSheet(
          context: context,
          builder: (BuildContext context) => SingleChildScrollView(
            child: SizedBox(
              height: 300,
              child: Form(
                key: formKey,
                child: Padding(
                    padding: const EdgeInsets.symmetric(vertical: 15, horizontal: 30),
                    child: Column(
                      children: [
                        TextFormField(
                          keyboardType: TextInputType.name,
                          validator: (input) {
                            if (input == null) {
                              return 'Name cannot be blank';
                            } else {
                              formKey.currentState?.save();
                              return null;
                            }
                          },
                          decoration: const InputDecoration(
                            hintText: "Player Name",
                            prefixIcon: Icon(Icons.person),
                          ),
                          onSaved: (value) => ticketInformation.add(value),
                        ),
                        MaterialButton(
                          padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 30),
                          onPressed: _pickTime,
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.start,
                            children: [
                              const Icon(Icons.timer),
                              Text('  Picked time: $_selectedTime'),
                            ],
                          ),
                        ),
                        Container(
                          alignment: Alignment.bottomCenter,
                          child: ElevatedButton(
                              onPressed: () {
                                String court = KickoffApplication.pages[KickoffApplicationState.selectedPage].selectedCourt;
                                String date = KickoffApplication.pages[KickoffApplicationState.selectedPage].selectedDate;
                                ticketInformation.add(court);
                                ticketInformation.add(date);

                                formKey.currentState?.save();

                                print(ticketInformation);

                                // TODO: SEND THE TICKET INFO TO BACK END
                                print(ticketInformation);
                              },
                              child: const Text("SUBMIT")
                          ),
                        )
                      ],
                    )
                ),
              ),
            ),
          ),
          elevation: 4,
        );
      },
    foregroundColor: Theme.of(context).floatingActionButtonTheme.foregroundColor,
    backgroundColor: Theme.of(context).floatingActionButtonTheme.backgroundColor,
    hoverColor: Colors.green.shade800,
    hoverElevation: 4,
    child: const Icon(Icons.add, size: 35)
  );
}