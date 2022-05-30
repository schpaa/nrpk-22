goog.provide('cljc.java_time.local_date_time');
goog.scope(function(){
  cljc.java_time.local_date_time.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.local_date_time.max = cljc.java_time.local_date_time.goog$module$goog$object.get(java.time.LocalDateTime,"MAX");
cljc.java_time.local_date_time.min = cljc.java_time.local_date_time.goog$module$goog$object.get(java.time.LocalDateTime,"MIN");
cljc.java_time.local_date_time.minus_minutes = (function cljc$java_time$local_date_time$minus_minutes(this13036,long13037){
return this13036.minusMinutes(long13037);
});
cljc.java_time.local_date_time.truncated_to = (function cljc$java_time$local_date_time$truncated_to(this13038,java_time_temporal_TemporalUnit13039){
return this13038.truncatedTo(java_time_temporal_TemporalUnit13039);
});
cljc.java_time.local_date_time.minus_weeks = (function cljc$java_time$local_date_time$minus_weeks(this13040,long13041){
return this13040.minusWeeks(long13041);
});
cljc.java_time.local_date_time.to_instant = (function cljc$java_time$local_date_time$to_instant(this13042,java_time_ZoneOffset13043){
return this13042.toInstant(java_time_ZoneOffset13043);
});
cljc.java_time.local_date_time.plus_weeks = (function cljc$java_time$local_date_time$plus_weeks(this13044,long13045){
return this13044.plusWeeks(long13045);
});
cljc.java_time.local_date_time.range = (function cljc$java_time$local_date_time$range(this13046,java_time_temporal_TemporalField13047){
return this13046.range(java_time_temporal_TemporalField13047);
});
cljc.java_time.local_date_time.of_epoch_second = (function cljc$java_time$local_date_time$of_epoch_second(long13048,int13049,java_time_ZoneOffset13050){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDateTime,"ofEpochSecond",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([long13048,int13049,java_time_ZoneOffset13050], 0));
});
cljc.java_time.local_date_time.get_hour = (function cljc$java_time$local_date_time$get_hour(this13051){
return this13051.hour();
});
cljc.java_time.local_date_time.at_offset = (function cljc$java_time$local_date_time$at_offset(this13052,java_time_ZoneOffset13053){
return this13052.atOffset(java_time_ZoneOffset13053);
});
cljc.java_time.local_date_time.minus_hours = (function cljc$java_time$local_date_time$minus_hours(this13054,long13055){
return this13054.minusHours(long13055);
});
cljc.java_time.local_date_time.of = (function cljc$java_time$local_date_time$of(var_args){
var G__74158 = arguments.length;
switch (G__74158) {
case 6:
return cljc.java_time.local_date_time.of.cljs$core$IFn$_invoke$arity$6((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]),(arguments[(4)]),(arguments[(5)]));

break;
case 5:
return cljc.java_time.local_date_time.of.cljs$core$IFn$_invoke$arity$5((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]),(arguments[(4)]));

break;
case 7:
return cljc.java_time.local_date_time.of.cljs$core$IFn$_invoke$arity$7((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]),(arguments[(4)]),(arguments[(5)]),(arguments[(6)]));

break;
case 2:
return cljc.java_time.local_date_time.of.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_date_time.of.cljs$core$IFn$_invoke$arity$6 = (function (G__13057,G__13058,G__13059,G__13060,G__13061,G__13062){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDateTime,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([G__13057,G__13058,G__13059,G__13060,G__13061,G__13062], 0));
}));

(cljc.java_time.local_date_time.of.cljs$core$IFn$_invoke$arity$5 = (function (G__13064,G__13065,G__13066,G__13067,G__13068){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDateTime,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([G__13064,G__13065,G__13066,G__13067,G__13068], 0));
}));

(cljc.java_time.local_date_time.of.cljs$core$IFn$_invoke$arity$7 = (function (G__13070,G__13071,G__13072,G__13073,G__13074,G__13075,G__13076){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDateTime,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([G__13070,G__13071,G__13072,G__13073,G__13074,G__13075,G__13076], 0));
}));

(cljc.java_time.local_date_time.of.cljs$core$IFn$_invoke$arity$2 = (function (java_time_LocalDate13077,java_time_LocalTime13078){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDateTime,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_LocalDate13077,java_time_LocalTime13078], 0));
}));

(cljc.java_time.local_date_time.of.cljs$lang$maxFixedArity = 7);

cljc.java_time.local_date_time.with_month = (function cljc$java_time$local_date_time$with_month(this13079,int13080){
return this13079.withMonth(int13080);
});
cljc.java_time.local_date_time.is_equal = (function cljc$java_time$local_date_time$is_equal(this13081,java_time_chrono_ChronoLocalDateTime13082){
return this13081.isEqual(java_time_chrono_ChronoLocalDateTime13082);
});
cljc.java_time.local_date_time.get_nano = (function cljc$java_time$local_date_time$get_nano(this13083){
return this13083.nano();
});
cljc.java_time.local_date_time.get_year = (function cljc$java_time$local_date_time$get_year(this13084){
return this13084.year();
});
cljc.java_time.local_date_time.minus_seconds = (function cljc$java_time$local_date_time$minus_seconds(this13085,long13086){
return this13085.minusSeconds(long13086);
});
cljc.java_time.local_date_time.get_second = (function cljc$java_time$local_date_time$get_second(this13087){
return this13087.second();
});
cljc.java_time.local_date_time.plus_nanos = (function cljc$java_time$local_date_time$plus_nanos(this13088,long13089){
return this13088.plusNanos(long13089);
});
cljc.java_time.local_date_time.get_day_of_year = (function cljc$java_time$local_date_time$get_day_of_year(this13090){
return this13090.dayOfYear();
});
cljc.java_time.local_date_time.plus = (function cljc$java_time$local_date_time$plus(var_args){
var G__74193 = arguments.length;
switch (G__74193) {
case 3:
return cljc.java_time.local_date_time.plus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 2:
return cljc.java_time.local_date_time.plus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_date_time.plus.cljs$core$IFn$_invoke$arity$3 = (function (this13091,long13092,java_time_temporal_TemporalUnit13093){
return this13091.plus(long13092,java_time_temporal_TemporalUnit13093);
}));

(cljc.java_time.local_date_time.plus.cljs$core$IFn$_invoke$arity$2 = (function (this13094,java_time_temporal_TemporalAmount13095){
return this13094.plus(java_time_temporal_TemporalAmount13095);
}));

(cljc.java_time.local_date_time.plus.cljs$lang$maxFixedArity = 3);

cljc.java_time.local_date_time.with_hour = (function cljc$java_time$local_date_time$with_hour(this13096,int13097){
return this13096.withHour(int13097);
});
cljc.java_time.local_date_time.with_minute = (function cljc$java_time$local_date_time$with_minute(this13098,int13099){
return this13098.withMinute(int13099);
});
cljc.java_time.local_date_time.plus_minutes = (function cljc$java_time$local_date_time$plus_minutes(this13100,long13101){
return this13100.plusMinutes(long13101);
});
cljc.java_time.local_date_time.query = (function cljc$java_time$local_date_time$query(this13102,java_time_temporal_TemporalQuery13103){
return this13102.query(java_time_temporal_TemporalQuery13103);
});
cljc.java_time.local_date_time.get_day_of_week = (function cljc$java_time$local_date_time$get_day_of_week(this13104){
return this13104.dayOfWeek();
});
cljc.java_time.local_date_time.to_string = (function cljc$java_time$local_date_time$to_string(this13105){
return this13105.toString();
});
cljc.java_time.local_date_time.plus_months = (function cljc$java_time$local_date_time$plus_months(this13106,long13107){
return this13106.plusMonths(long13107);
});
cljc.java_time.local_date_time.is_before = (function cljc$java_time$local_date_time$is_before(this13108,java_time_chrono_ChronoLocalDateTime13109){
return this13108.isBefore(java_time_chrono_ChronoLocalDateTime13109);
});
cljc.java_time.local_date_time.minus_months = (function cljc$java_time$local_date_time$minus_months(this13110,long13111){
return this13110.minusMonths(long13111);
});
cljc.java_time.local_date_time.minus = (function cljc$java_time$local_date_time$minus(var_args){
var G__74198 = arguments.length;
switch (G__74198) {
case 2:
return cljc.java_time.local_date_time.minus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.local_date_time.minus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_date_time.minus.cljs$core$IFn$_invoke$arity$2 = (function (this13112,java_time_temporal_TemporalAmount13113){
return this13112.minus(java_time_temporal_TemporalAmount13113);
}));

(cljc.java_time.local_date_time.minus.cljs$core$IFn$_invoke$arity$3 = (function (this13114,long13115,java_time_temporal_TemporalUnit13116){
return this13114.minus(long13115,java_time_temporal_TemporalUnit13116);
}));

(cljc.java_time.local_date_time.minus.cljs$lang$maxFixedArity = 3);

cljc.java_time.local_date_time.at_zone = (function cljc$java_time$local_date_time$at_zone(this13117,java_time_ZoneId13118){
return this13117.atZone(java_time_ZoneId13118);
});
cljc.java_time.local_date_time.plus_hours = (function cljc$java_time$local_date_time$plus_hours(this13119,long13120){
return this13119.plusHours(long13120);
});
cljc.java_time.local_date_time.plus_days = (function cljc$java_time$local_date_time$plus_days(this13121,long13122){
return this13121.plusDays(long13122);
});
cljc.java_time.local_date_time.to_local_time = (function cljc$java_time$local_date_time$to_local_time(this13123){
return this13123.toLocalTime();
});
cljc.java_time.local_date_time.get_long = (function cljc$java_time$local_date_time$get_long(this13124,java_time_temporal_TemporalField13125){
return this13124.getLong(java_time_temporal_TemporalField13125);
});
cljc.java_time.local_date_time.with_year = (function cljc$java_time$local_date_time$with_year(this13126,int13127){
return this13126.withYear(int13127);
});
cljc.java_time.local_date_time.with_nano = (function cljc$java_time$local_date_time$with_nano(this13128,int13129){
return this13128.withNano(int13129);
});
cljc.java_time.local_date_time.to_epoch_second = (function cljc$java_time$local_date_time$to_epoch_second(this13130,java_time_ZoneOffset13131){
return this13130.toEpochSecond(java_time_ZoneOffset13131);
});
cljc.java_time.local_date_time.until = (function cljc$java_time$local_date_time$until(this13132,java_time_temporal_Temporal13133,java_time_temporal_TemporalUnit13134){
return this13132.until(java_time_temporal_Temporal13133,java_time_temporal_TemporalUnit13134);
});
cljc.java_time.local_date_time.with_day_of_month = (function cljc$java_time$local_date_time$with_day_of_month(this13135,int13136){
return this13135.withDayOfMonth(int13136);
});
cljc.java_time.local_date_time.get_day_of_month = (function cljc$java_time$local_date_time$get_day_of_month(this13137){
return this13137.dayOfMonth();
});
cljc.java_time.local_date_time.from = (function cljc$java_time$local_date_time$from(java_time_temporal_TemporalAccessor13138){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDateTime,"from",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_temporal_TemporalAccessor13138], 0));
});
cljc.java_time.local_date_time.is_after = (function cljc$java_time$local_date_time$is_after(this13139,java_time_chrono_ChronoLocalDateTime13140){
return this13139.isAfter(java_time_chrono_ChronoLocalDateTime13140);
});
cljc.java_time.local_date_time.minus_nanos = (function cljc$java_time$local_date_time$minus_nanos(this13141,long13142){
return this13141.minusNanos(long13142);
});
cljc.java_time.local_date_time.is_supported = (function cljc$java_time$local_date_time$is_supported(this13143,G__13144){
return this13143.isSupported(G__13144);
});
cljc.java_time.local_date_time.minus_years = (function cljc$java_time$local_date_time$minus_years(this13145,long13146){
return this13145.minusYears(long13146);
});
cljc.java_time.local_date_time.get_chronology = (function cljc$java_time$local_date_time$get_chronology(this13147){
return this13147.chronology();
});
cljc.java_time.local_date_time.parse = (function cljc$java_time$local_date_time$parse(var_args){
var G__74227 = arguments.length;
switch (G__74227) {
case 2:
return cljc.java_time.local_date_time.parse.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 1:
return cljc.java_time.local_date_time.parse.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_date_time.parse.cljs$core$IFn$_invoke$arity$2 = (function (java_lang_CharSequence13148,java_time_format_DateTimeFormatter13149){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDateTime,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence13148,java_time_format_DateTimeFormatter13149], 0));
}));

(cljc.java_time.local_date_time.parse.cljs$core$IFn$_invoke$arity$1 = (function (java_lang_CharSequence13150){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDateTime,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence13150], 0));
}));

(cljc.java_time.local_date_time.parse.cljs$lang$maxFixedArity = 2);

cljc.java_time.local_date_time.with_second = (function cljc$java_time$local_date_time$with_second(this13151,int13152){
return this13151.withSecond(int13152);
});
cljc.java_time.local_date_time.to_local_date = (function cljc$java_time$local_date_time$to_local_date(this13153){
return this13153.toLocalDate();
});
cljc.java_time.local_date_time.get_minute = (function cljc$java_time$local_date_time$get_minute(this13154){
return this13154.minute();
});
cljc.java_time.local_date_time.hash_code = (function cljc$java_time$local_date_time$hash_code(this13155){
return this13155.hashCode();
});
cljc.java_time.local_date_time.adjust_into = (function cljc$java_time$local_date_time$adjust_into(this13156,java_time_temporal_Temporal13157){
return this13156.adjustInto(java_time_temporal_Temporal13157);
});
cljc.java_time.local_date_time.with$ = (function cljc$java_time$local_date_time$with(var_args){
var G__74239 = arguments.length;
switch (G__74239) {
case 3:
return cljc.java_time.local_date_time.with$.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 2:
return cljc.java_time.local_date_time.with$.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_date_time.with$.cljs$core$IFn$_invoke$arity$3 = (function (this13158,java_time_temporal_TemporalField13159,long13160){
return this13158.with(java_time_temporal_TemporalField13159,long13160);
}));

(cljc.java_time.local_date_time.with$.cljs$core$IFn$_invoke$arity$2 = (function (this13161,java_time_temporal_TemporalAdjuster13162){
return this13161.with(java_time_temporal_TemporalAdjuster13162);
}));

(cljc.java_time.local_date_time.with$.cljs$lang$maxFixedArity = 3);

cljc.java_time.local_date_time.now = (function cljc$java_time$local_date_time$now(var_args){
var G__74243 = arguments.length;
switch (G__74243) {
case 0:
return cljc.java_time.local_date_time.now.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return cljc.java_time.local_date_time.now.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_date_time.now.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljs.core.js_invoke(java.time.LocalDateTime,"now");
}));

(cljc.java_time.local_date_time.now.cljs$core$IFn$_invoke$arity$1 = (function (G__13164){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDateTime,"now",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([G__13164], 0));
}));

(cljc.java_time.local_date_time.now.cljs$lang$maxFixedArity = 1);

cljc.java_time.local_date_time.get_month_value = (function cljc$java_time$local_date_time$get_month_value(this13165){
return this13165.monthValue();
});
cljc.java_time.local_date_time.with_day_of_year = (function cljc$java_time$local_date_time$with_day_of_year(this13166,int13167){
return this13166.withDayOfYear(int13167);
});
cljc.java_time.local_date_time.compare_to = (function cljc$java_time$local_date_time$compare_to(this13168,java_time_chrono_ChronoLocalDateTime13169){
return this13168.compareTo(java_time_chrono_ChronoLocalDateTime13169);
});
cljc.java_time.local_date_time.get_month = (function cljc$java_time$local_date_time$get_month(this13170){
return this13170.month();
});
cljc.java_time.local_date_time.of_instant = (function cljc$java_time$local_date_time$of_instant(java_time_Instant13171,java_time_ZoneId13172){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDateTime,"ofInstant",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_Instant13171,java_time_ZoneId13172], 0));
});
cljc.java_time.local_date_time.plus_seconds = (function cljc$java_time$local_date_time$plus_seconds(this13173,long13174){
return this13173.plusSeconds(long13174);
});
cljc.java_time.local_date_time.get = (function cljc$java_time$local_date_time$get(this13175,java_time_temporal_TemporalField13176){
return this13175.get(java_time_temporal_TemporalField13176);
});
cljc.java_time.local_date_time.equals = (function cljc$java_time$local_date_time$equals(this13177,java_lang_Object13178){
return this13177.equals(java_lang_Object13178);
});
cljc.java_time.local_date_time.format = (function cljc$java_time$local_date_time$format(this13179,java_time_format_DateTimeFormatter13180){
return this13179.format(java_time_format_DateTimeFormatter13180);
});
cljc.java_time.local_date_time.plus_years = (function cljc$java_time$local_date_time$plus_years(this13181,long13182){
return this13181.plusYears(long13182);
});
cljc.java_time.local_date_time.minus_days = (function cljc$java_time$local_date_time$minus_days(this13183,long13184){
return this13183.minusDays(long13184);
});

//# sourceMappingURL=cljc.java_time.local_date_time.js.map
