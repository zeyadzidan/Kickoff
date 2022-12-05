class Court {
  late String cname; // Court Name.
  late String cid; // Court ID.
  late String description;
  late String morningCost;
  late String nightCost;
  late String minBookingHours;
  late String startingWorkingHours;
  late String finishWorkingHours;

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