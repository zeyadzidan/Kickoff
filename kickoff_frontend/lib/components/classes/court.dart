class Court {
  String cname = ''; // Court Name.
  String cid = ''; // Court ID.
  String description = '';
  String morningCost = '';
  String nightCost = '';
  String minBookingHours = '';
  String startingWorkingHours = '';
  String finishWorkingHours = '';

  asList() => [
    'Court Name: $cname',
    'Description: $description',
    'Morning Cost: $morningCost EGP',
    'Night Cost: $nightCost EGP',
    'Minimum Booking Hours: $minBookingHours',
    'Starting Working Hours: $startingWorkingHours',
    'Finishing Working Hours: $finishWorkingHours'
  ];
}