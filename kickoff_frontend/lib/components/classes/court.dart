class Court {
  String cname = '';
  String cid = '';
  String description = '';
  String morningCost = '';
  String nightCost = '';
  String minBookingHours = '';
  String startingWorkingHours = '';
  String finishWorkingHours = '';
  String morningFinish = '';
  String state = '';

  asView() => [
        'وصف الملعب: $description',
        'أقل عدد ساعات للحجز: $minBookingHours',
        'بداية العمل: $startingWorkingHours',
        'نهاية العمل: $finishWorkingHours',
        'انتهاء الساعات الصباحية: $morningFinish',
        'سعر الساعة صباحاً: $morningCost جنيهاً',
        'سعر الساعة مساءً: $nightCost جنيهاًً',
      ];
}
