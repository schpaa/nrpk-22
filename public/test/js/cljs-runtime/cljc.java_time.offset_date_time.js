goog.provide('cljc.java_time.offset_date_time');
goog.scope(function(){
  cljc.java_time.offset_date_time.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.offset_date_time.min = cljc.java_time.offset_date_time.goog$module$goog$object.get(java.time.OffsetDateTime,"MIN");
cljc.java_time.offset_date_time.max = cljc.java_time.offset_date_time.goog$module$goog$object.get(java.time.OffsetDateTime,"MAX");
cljc.java_time.offset_date_time.minus_minutes = (function cljc$java_time$offset_date_time$minus_minutes(this13952,long13953){
return this13952.minusMinutes(long13953);
});
cljc.java_time.offset_date_time.truncated_to = (function cljc$java_time$offset_date_time$truncated_to(this13954,java_time_temporal_TemporalUnit13955){
return this13954.truncatedTo(java_time_temporal_TemporalUnit13955);
});
cljc.java_time.offset_date_time.minus_weeks = (function cljc$java_time$offset_date_time$minus_weeks(this13956,long13957){
return this13956.minusWeeks(long13957);
});
cljc.java_time.offset_date_time.to_instant = (function cljc$java_time$offset_date_time$to_instant(this13958){
return this13958.toInstant();
});
cljc.java_time.offset_date_time.plus_weeks = (function cljc$java_time$offset_date_time$plus_weeks(this13959,long13960){
return this13959.plusWeeks(long13960);
});
cljc.java_time.offset_date_time.range = (function cljc$java_time$offset_date_time$range(this13961,java_time_temporal_TemporalField13962){
return this13961.range(java_time_temporal_TemporalField13962);
});
cljc.java_time.offset_date_time.get_hour = (function cljc$java_time$offset_date_time$get_hour(this13963){
return this13963.hour();
});
cljc.java_time.offset_date_time.at_zone_same_instant = (function cljc$java_time$offset_date_time$at_zone_same_instant(this13964,java_time_ZoneId13965){
return this13964.atZoneSameInstant(java_time_ZoneId13965);
});
cljc.java_time.offset_date_time.minus_hours = (function cljc$java_time$offset_date_time$minus_hours(this13966,long13967){
return this13966.minusHours(long13967);
});
cljc.java_time.offset_date_time.of = (function cljc$java_time$offset_date_time$of(var_args){
var G__74279 = arguments.length;
switch (G__74279) {
case 8:
return cljc.java_time.offset_date_time.of.cljs$core$IFn$_invoke$arity$8((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]),(arguments[(4)]),(arguments[(5)]),(arguments[(6)]),(arguments[(7)]));

break;
case 2:
return cljc.java_time.offset_date_time.of.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.offset_date_time.of.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.offset_date_time.of.cljs$core$IFn$_invoke$arity$8 = (function (int13968,int13969,int13970,int13971,int13972,int13973,int13974,java_time_ZoneOffset13975){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.OffsetDateTime,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int13968,int13969,int13970,int13971,int13972,int13973,int13974,java_time_ZoneOffset13975], 0));
}));

(cljc.java_time.offset_date_time.of.cljs$core$IFn$_invoke$arity$2 = (function (java_time_LocalDateTime13976,java_time_ZoneOffset13977){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.OffsetDateTime,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_LocalDateTime13976,java_time_ZoneOffset13977], 0));
}));

(cljc.java_time.offset_date_time.of.cljs$core$IFn$_invoke$arity$3 = (function (java_time_LocalDate13978,java_time_LocalTime13979,java_time_ZoneOffset13980){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.OffsetDateTime,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_LocalDate13978,java_time_LocalTime13979,java_time_ZoneOffset13980], 0));
}));

(cljc.java_time.offset_date_time.of.cljs$lang$maxFixedArity = 8);

cljc.java_time.offset_date_time.with_month = (function cljc$java_time$offset_date_time$with_month(this13981,int13982){
return this13981.withMonth(int13982);
});
cljc.java_time.offset_date_time.is_equal = (function cljc$java_time$offset_date_time$is_equal(this13983,java_time_OffsetDateTime13984){
return this13983.isEqual(java_time_OffsetDateTime13984);
});
cljc.java_time.offset_date_time.get_nano = (function cljc$java_time$offset_date_time$get_nano(this13985){
return this13985.nano();
});
cljc.java_time.offset_date_time.to_offset_time = (function cljc$java_time$offset_date_time$to_offset_time(this13986){
return this13986.toOffsetTime();
});
cljc.java_time.offset_date_time.at_zone_similar_local = (function cljc$java_time$offset_date_time$at_zone_similar_local(this13987,java_time_ZoneId13988){
return this13987.atZoneSimilarLocal(java_time_ZoneId13988);
});
cljc.java_time.offset_date_time.get_year = (function cljc$java_time$offset_date_time$get_year(this13989){
return this13989.year();
});
cljc.java_time.offset_date_time.minus_seconds = (function cljc$java_time$offset_date_time$minus_seconds(this13990,long13991){
return this13990.minusSeconds(long13991);
});
cljc.java_time.offset_date_time.get_second = (function cljc$java_time$offset_date_time$get_second(this13992){
return this13992.second();
});
cljc.java_time.offset_date_time.plus_nanos = (function cljc$java_time$offset_date_time$plus_nanos(this13993,long13994){
return this13993.plusNanos(long13994);
});
cljc.java_time.offset_date_time.get_day_of_year = (function cljc$java_time$offset_date_time$get_day_of_year(this13995){
return this13995.dayOfYear();
});
cljc.java_time.offset_date_time.plus = (function cljc$java_time$offset_date_time$plus(var_args){
var G__74283 = arguments.length;
switch (G__74283) {
case 3:
return cljc.java_time.offset_date_time.plus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 2:
return cljc.java_time.offset_date_time.plus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.offset_date_time.plus.cljs$core$IFn$_invoke$arity$3 = (function (this13996,long13997,java_time_temporal_TemporalUnit13998){
return this13996.plus(long13997,java_time_temporal_TemporalUnit13998);
}));

(cljc.java_time.offset_date_time.plus.cljs$core$IFn$_invoke$arity$2 = (function (this13999,java_time_temporal_TemporalAmount14000){
return this13999.plus(java_time_temporal_TemporalAmount14000);
}));

(cljc.java_time.offset_date_time.plus.cljs$lang$maxFixedArity = 3);

cljc.java_time.offset_date_time.time_line_order = (function cljc$java_time$offset_date_time$time_line_order(){
return cljs.core.js_invoke(java.time.OffsetDateTime,"timeLineOrder");
});
cljc.java_time.offset_date_time.with_hour = (function cljc$java_time$offset_date_time$with_hour(this14001,int14002){
return this14001.withHour(int14002);
});
cljc.java_time.offset_date_time.with_minute = (function cljc$java_time$offset_date_time$with_minute(this14003,int14004){
return this14003.withMinute(int14004);
});
cljc.java_time.offset_date_time.plus_minutes = (function cljc$java_time$offset_date_time$plus_minutes(this14005,long14006){
return this14005.plusMinutes(long14006);
});
cljc.java_time.offset_date_time.query = (function cljc$java_time$offset_date_time$query(this14007,java_time_temporal_TemporalQuery14008){
return this14007.query(java_time_temporal_TemporalQuery14008);
});
cljc.java_time.offset_date_time.with_offset_same_instant = (function cljc$java_time$offset_date_time$with_offset_same_instant(this14009,java_time_ZoneOffset14010){
return this14009.withOffsetSameInstant(java_time_ZoneOffset14010);
});
cljc.java_time.offset_date_time.get_day_of_week = (function cljc$java_time$offset_date_time$get_day_of_week(this14011){
return this14011.dayOfWeek();
});
cljc.java_time.offset_date_time.to_string = (function cljc$java_time$offset_date_time$to_string(this14012){
return this14012.toString();
});
cljc.java_time.offset_date_time.plus_months = (function cljc$java_time$offset_date_time$plus_months(this14013,long14014){
return this14013.plusMonths(long14014);
});
cljc.java_time.offset_date_time.is_before = (function cljc$java_time$offset_date_time$is_before(this14015,java_time_OffsetDateTime14016){
return this14015.isBefore(java_time_OffsetDateTime14016);
});
cljc.java_time.offset_date_time.minus_months = (function cljc$java_time$offset_date_time$minus_months(this14017,long14018){
return this14017.minusMonths(long14018);
});
cljc.java_time.offset_date_time.minus = (function cljc$java_time$offset_date_time$minus(var_args){
var G__74291 = arguments.length;
switch (G__74291) {
case 2:
return cljc.java_time.offset_date_time.minus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.offset_date_time.minus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.offset_date_time.minus.cljs$core$IFn$_invoke$arity$2 = (function (this14019,java_time_temporal_TemporalAmount14020){
return this14019.minus(java_time_temporal_TemporalAmount14020);
}));

(cljc.java_time.offset_date_time.minus.cljs$core$IFn$_invoke$arity$3 = (function (this14021,long14022,java_time_temporal_TemporalUnit14023){
return this14021.minus(long14022,java_time_temporal_TemporalUnit14023);
}));

(cljc.java_time.offset_date_time.minus.cljs$lang$maxFixedArity = 3);

cljc.java_time.offset_date_time.plus_hours = (function cljc$java_time$offset_date_time$plus_hours(this14024,long14025){
return this14024.plusHours(long14025);
});
cljc.java_time.offset_date_time.plus_days = (function cljc$java_time$offset_date_time$plus_days(this14026,long14027){
return this14026.plusDays(long14027);
});
cljc.java_time.offset_date_time.to_local_time = (function cljc$java_time$offset_date_time$to_local_time(this14028){
return this14028.toLocalTime();
});
cljc.java_time.offset_date_time.get_long = (function cljc$java_time$offset_date_time$get_long(this14029,java_time_temporal_TemporalField14030){
return this14029.getLong(java_time_temporal_TemporalField14030);
});
cljc.java_time.offset_date_time.get_offset = (function cljc$java_time$offset_date_time$get_offset(this14031){
return this14031.offset();
});
cljc.java_time.offset_date_time.to_zoned_date_time = (function cljc$java_time$offset_date_time$to_zoned_date_time(this14032){
return this14032.toZonedDateTime();
});
cljc.java_time.offset_date_time.with_year = (function cljc$java_time$offset_date_time$with_year(this14033,int14034){
return this14033.withYear(int14034);
});
cljc.java_time.offset_date_time.with_nano = (function cljc$java_time$offset_date_time$with_nano(this14035,int14036){
return this14035.withNano(int14036);
});
cljc.java_time.offset_date_time.to_epoch_second = (function cljc$java_time$offset_date_time$to_epoch_second(this14037){
return this14037.toEpochSecond();
});
cljc.java_time.offset_date_time.until = (function cljc$java_time$offset_date_time$until(this14038,java_time_temporal_Temporal14039,java_time_temporal_TemporalUnit14040){
return this14038.until(java_time_temporal_Temporal14039,java_time_temporal_TemporalUnit14040);
});
cljc.java_time.offset_date_time.with_offset_same_local = (function cljc$java_time$offset_date_time$with_offset_same_local(this14041,java_time_ZoneOffset14042){
return this14041.withOffsetSameLocal(java_time_ZoneOffset14042);
});
cljc.java_time.offset_date_time.with_day_of_month = (function cljc$java_time$offset_date_time$with_day_of_month(this14043,int14044){
return this14043.withDayOfMonth(int14044);
});
cljc.java_time.offset_date_time.get_day_of_month = (function cljc$java_time$offset_date_time$get_day_of_month(this14045){
return this14045.dayOfMonth();
});
cljc.java_time.offset_date_time.from = (function cljc$java_time$offset_date_time$from(java_time_temporal_TemporalAccessor14046){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.OffsetDateTime,"from",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_temporal_TemporalAccessor14046], 0));
});
cljc.java_time.offset_date_time.is_after = (function cljc$java_time$offset_date_time$is_after(this14047,java_time_OffsetDateTime14048){
return this14047.isAfter(java_time_OffsetDateTime14048);
});
cljc.java_time.offset_date_time.minus_nanos = (function cljc$java_time$offset_date_time$minus_nanos(this14049,long14050){
return this14049.minusNanos(long14050);
});
cljc.java_time.offset_date_time.is_supported = (function cljc$java_time$offset_date_time$is_supported(this14051,G__14052){
return this14051.isSupported(G__14052);
});
cljc.java_time.offset_date_time.minus_years = (function cljc$java_time$offset_date_time$minus_years(this14053,long14054){
return this14053.minusYears(long14054);
});
cljc.java_time.offset_date_time.parse = (function cljc$java_time$offset_date_time$parse(var_args){
var G__74307 = arguments.length;
switch (G__74307) {
case 1:
return cljc.java_time.offset_date_time.parse.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljc.java_time.offset_date_time.parse.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.offset_date_time.parse.cljs$core$IFn$_invoke$arity$1 = (function (java_lang_CharSequence14055){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.OffsetDateTime,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence14055], 0));
}));

(cljc.java_time.offset_date_time.parse.cljs$core$IFn$_invoke$arity$2 = (function (java_lang_CharSequence14056,java_time_format_DateTimeFormatter14057){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.OffsetDateTime,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence14056,java_time_format_DateTimeFormatter14057], 0));
}));

(cljc.java_time.offset_date_time.parse.cljs$lang$maxFixedArity = 2);

cljc.java_time.offset_date_time.with_second = (function cljc$java_time$offset_date_time$with_second(this14058,int14059){
return this14058.withSecond(int14059);
});
cljc.java_time.offset_date_time.to_local_date = (function cljc$java_time$offset_date_time$to_local_date(this14060){
return this14060.toLocalDate();
});
cljc.java_time.offset_date_time.get_minute = (function cljc$java_time$offset_date_time$get_minute(this14061){
return this14061.minute();
});
cljc.java_time.offset_date_time.hash_code = (function cljc$java_time$offset_date_time$hash_code(this14062){
return this14062.hashCode();
});
cljc.java_time.offset_date_time.adjust_into = (function cljc$java_time$offset_date_time$adjust_into(this14063,java_time_temporal_Temporal14064){
return this14063.adjustInto(java_time_temporal_Temporal14064);
});
cljc.java_time.offset_date_time.with$ = (function cljc$java_time$offset_date_time$with(var_args){
var G__74317 = arguments.length;
switch (G__74317) {
case 2:
return cljc.java_time.offset_date_time.with$.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.offset_date_time.with$.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.offset_date_time.with$.cljs$core$IFn$_invoke$arity$2 = (function (this14065,java_time_temporal_TemporalAdjuster14066){
return this14065.with(java_time_temporal_TemporalAdjuster14066);
}));

(cljc.java_time.offset_date_time.with$.cljs$core$IFn$_invoke$arity$3 = (function (this14067,java_time_temporal_TemporalField14068,long14069){
return this14067.with(java_time_temporal_TemporalField14068,long14069);
}));

(cljc.java_time.offset_date_time.with$.cljs$lang$maxFixedArity = 3);

cljc.java_time.offset_date_time.now = (function cljc$java_time$offset_date_time$now(var_args){
var G__74321 = arguments.length;
switch (G__74321) {
case 1:
return cljc.java_time.offset_date_time.now.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 0:
return cljc.java_time.offset_date_time.now.cljs$core$IFn$_invoke$arity$0();

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.offset_date_time.now.cljs$core$IFn$_invoke$arity$1 = (function (G__14071){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.OffsetDateTime,"now",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([G__14071], 0));
}));

(cljc.java_time.offset_date_time.now.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljs.core.js_invoke(java.time.OffsetDateTime,"now");
}));

(cljc.java_time.offset_date_time.now.cljs$lang$maxFixedArity = 1);

cljc.java_time.offset_date_time.to_local_date_time = (function cljc$java_time$offset_date_time$to_local_date_time(this14072){
return this14072.toLocalDateTime();
});
cljc.java_time.offset_date_time.get_month_value = (function cljc$java_time$offset_date_time$get_month_value(this14073){
return this14073.monthValue();
});
cljc.java_time.offset_date_time.with_day_of_year = (function cljc$java_time$offset_date_time$with_day_of_year(this14074,int14075){
return this14074.withDayOfYear(int14075);
});
cljc.java_time.offset_date_time.compare_to = (function cljc$java_time$offset_date_time$compare_to(this14076,java_time_OffsetDateTime14077){
return this14076.compareTo(java_time_OffsetDateTime14077);
});
cljc.java_time.offset_date_time.get_month = (function cljc$java_time$offset_date_time$get_month(this14078){
return this14078.month();
});
cljc.java_time.offset_date_time.of_instant = (function cljc$java_time$offset_date_time$of_instant(java_time_Instant14079,java_time_ZoneId14080){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.OffsetDateTime,"ofInstant",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_Instant14079,java_time_ZoneId14080], 0));
});
cljc.java_time.offset_date_time.plus_seconds = (function cljc$java_time$offset_date_time$plus_seconds(this14081,long14082){
return this14081.plusSeconds(long14082);
});
cljc.java_time.offset_date_time.get = (function cljc$java_time$offset_date_time$get(this14083,java_time_temporal_TemporalField14084){
return this14083.get(java_time_temporal_TemporalField14084);
});
cljc.java_time.offset_date_time.equals = (function cljc$java_time$offset_date_time$equals(this14085,java_lang_Object14086){
return this14085.equals(java_lang_Object14086);
});
cljc.java_time.offset_date_time.format = (function cljc$java_time$offset_date_time$format(this14087,java_time_format_DateTimeFormatter14088){
return this14087.format(java_time_format_DateTimeFormatter14088);
});
cljc.java_time.offset_date_time.plus_years = (function cljc$java_time$offset_date_time$plus_years(this14089,long14090){
return this14089.plusYears(long14090);
});
cljc.java_time.offset_date_time.minus_days = (function cljc$java_time$offset_date_time$minus_days(this14091,long14092){
return this14091.minusDays(long14092);
});

//# sourceMappingURL=cljc.java_time.offset_date_time.js.map
