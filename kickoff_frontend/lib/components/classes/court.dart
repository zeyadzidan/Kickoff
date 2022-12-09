class Court {
  String cname = ''; // Court Name.
  String cid = ''; // Court ID.
  String description = '';
  String morningCost = '';
  String nightCost = '';
  String minBookingHours = '';
  String startingWorkingHours = '';
  String finishWorkingHours = '';
  String state = '';

  asView() => [
        'وصف الملعب: $description',
        'سعر الساعة صباحاً: $morningCost جنيهاً مصرياً',
        'سعر الساعة مساءً: $nightCost جنيهاً مصرياً',
        'أقل عدد ساعات للحجز: $minBookingHours',
        'بداية العمل: $startingWorkingHours',
        'نهاية العمل: $finishWorkingHours'
      ];
}
