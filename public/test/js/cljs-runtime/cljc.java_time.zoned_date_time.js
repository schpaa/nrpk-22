goog.provide('cljc.java_time.zoned_date_time');
goog.scope(function(){
  cljc.java_time.zoned_date_time.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.zoned_date_time.minus_minutes = (function cljc$java_time$zoned_date_time$minus_minutes(this13333,long13334){
return this13333.minusMinutes(long13334);
});
cljc.java_time.zoned_date_time.truncated_to = (function cljc$java_time$zoned_date_time$truncated_to(this13335,java_time_temporal_TemporalUnit13336){
return this13335.truncatedTo(java_time_temporal_TemporalUnit13336);
});
cljc.java_time.zoned_date_time.minus_weeks = (function cljc$java_time$zoned_date_time$minus_weeks(this13337,long13338){
return this13337.minusWeeks(long13338);
});
cljc.java_time.zoned_date_time.to_instant = (function cljc$java_time$zoned_date_time$to_instant(this13339){
return this13339.toInstant();
});
cljc.java_time.zoned_date_time.plus_weeks = (function cljc$java_time$zoned_date_time$plus_weeks(this13340,long13341){
return this13340.plusWeeks(long13341);
});
cljc.java_time.zoned_date_time.range = (function cljc$java_time$zoned_date_time$range(this13342,java_time_temporal_TemporalField13343){
return this13342.range(java_time_temporal_TemporalField13343);
});
cljc.java_time.zoned_date_time.with_earlier_offset_at_overlap = (function cljc$java_time$zoned_date_time$with_earlier_offset_at_overlap(this13344){
return this13344.withEarlierOffsetAtOverlap();
});
cljc.java_time.zoned_date_time.get_hour = (function cljc$java_time$zoned_date_time$get_hour(this13345){
return this13345.hour();
});
cljc.java_time.zoned_date_time.minus_hours = (function cljc$java_time$zoned_date_time$minus_hours(this13346,long13347){
return this13346.minusHours(long13347);
});
cljc.java_time.zoned_date_time.of = (function cljc$java_time$zoned_date_time$of(var_args){
var G__74277 = arguments.length;
switch (G__74277) {
case 8:
return cljc.java_time.zoned_date_time.of.cljs$core$IFn$_invoke$arity$8((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]),(arguments[(4)]),(arguments[(5)]),(arguments[(6)]),(arguments[(7)]));

break;
case 3:
return cljc.java_time.zoned_date_time.of.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 2:
return cljc.java_time.zoned_date_time.of.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.zoned_date_time.of.cljs$core$IFn$_invoke$arity$8 = (function (int13348,int13349,int13350,int13351,int13352,int13353,int13354,java_time_ZoneId13355){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZonedDateTime,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int13348,int13349,int13350,int13351,int13352,int13353,int13354,java_time_ZoneId13355], 0));
}));

(cljc.java_time.zoned_date_time.of.cljs$core$IFn$_invoke$arity$3 = (function (java_time_LocalDate13356,java_time_LocalTime13357,java_time_ZoneId13358){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZonedDateTime,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_LocalDate13356,java_time_LocalTime13357,java_time_ZoneId13358], 0));
}));

(cljc.java_time.zoned_date_time.of.cljs$core$IFn$_invoke$arity$2 = (function (java_time_LocalDateTime13359,java_time_ZoneId13360){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZonedDateTime,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_LocalDateTime13359,java_time_ZoneId13360], 0));
}));

(cljc.java_time.zoned_date_time.of.cljs$lang$maxFixedArity = 8);

cljc.java_time.zoned_date_time.with_month = (function cljc$java_time$zoned_date_time$with_month(this13361,int13362){
return this13361.withMonth(int13362);
});
cljc.java_time.zoned_date_time.is_equal = (function cljc$java_time$zoned_date_time$is_equal(this13363,java_time_chrono_ChronoZonedDateTime13364){
return this13363.isEqual(java_time_chrono_ChronoZonedDateTime13364);
});
cljc.java_time.zoned_date_time.get_nano = (function cljc$java_time$zoned_date_time$get_nano(this13365){
return this13365.nano();
});
cljc.java_time.zoned_date_time.of_local = (function cljc$java_time$zoned_date_time$of_local(java_time_LocalDateTime13366,java_time_ZoneId13367,java_time_ZoneOffset13368){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZonedDateTime,"ofLocal",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_LocalDateTime13366,java_time_ZoneId13367,java_time_ZoneOffset13368], 0));
});
cljc.java_time.zoned_date_time.get_year = (function cljc$java_time$zoned_date_time$get_year(this13369){
return this13369.year();
});
cljc.java_time.zoned_date_time.minus_seconds = (function cljc$java_time$zoned_date_time$minus_seconds(this13370,long13371){
return this13370.minusSeconds(long13371);
});
cljc.java_time.zoned_date_time.get_second = (function cljc$java_time$zoned_date_time$get_second(this13372){
return this13372.second();
});
cljc.java_time.zoned_date_time.plus_nanos = (function cljc$java_time$zoned_date_time$plus_nanos(this13373,long13374){
return this13373.plusNanos(long13374);
});
cljc.java_time.zoned_date_time.get_day_of_year = (function cljc$java_time$zoned_date_time$get_day_of_year(this13375){
return this13375.dayOfYear();
});
cljc.java_time.zoned_date_time.plus = (function cljc$java_time$zoned_date_time$plus(var_args){
var G__74281 = arguments.length;
switch (G__74281) {
case 3:
return cljc.java_time.zoned_date_time.plus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 2:
return cljc.java_time.zoned_date_time.plus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.zoned_date_time.plus.cljs$core$IFn$_invoke$arity$3 = (function (this13376,long13377,java_time_temporal_TemporalUnit13378){
return this13376.plus(long13377,java_time_temporal_TemporalUnit13378);
}));

(cljc.java_time.zoned_date_time.plus.cljs$core$IFn$_invoke$arity$2 = (function (this13379,java_time_temporal_TemporalAmount13380){
return this13379.plus(java_time_temporal_TemporalAmount13380);
}));

(cljc.java_time.zoned_date_time.plus.cljs$lang$maxFixedArity = 3);

cljc.java_time.zoned_date_time.with_hour = (function cljc$java_time$zoned_date_time$with_hour(this13381,int13382){
return this13381.withHour(int13382);
});
cljc.java_time.zoned_date_time.with_minute = (function cljc$java_time$zoned_date_time$with_minute(this13383,int13384){
return this13383.withMinute(int13384);
});
cljc.java_time.zoned_date_time.plus_minutes = (function cljc$java_time$zoned_date_time$plus_minutes(this13385,long13386){
return this13385.plusMinutes(long13386);
});
cljc.java_time.zoned_date_time.query = (function cljc$java_time$zoned_date_time$query(this13387,java_time_temporal_TemporalQuery13388){
return this13387.query(java_time_temporal_TemporalQuery13388);
});
cljc.java_time.zoned_date_time.get_day_of_week = (function cljc$java_time$zoned_date_time$get_day_of_week(this13389){
return this13389.dayOfWeek();
});
cljc.java_time.zoned_date_time.to_string = (function cljc$java_time$zoned_date_time$to_string(this13390){
return this13390.toString();
});
cljc.java_time.zoned_date_time.plus_months = (function cljc$java_time$zoned_date_time$plus_months(this13391,long13392){
return this13391.plusMonths(long13392);
});
cljc.java_time.zoned_date_time.is_before = (function cljc$java_time$zoned_date_time$is_before(this13393,java_time_chrono_ChronoZonedDateTime13394){
return this13393.isBefore(java_time_chrono_ChronoZonedDateTime13394);
});
cljc.java_time.zoned_date_time.minus_months = (function cljc$java_time$zoned_date_time$minus_months(this13395,long13396){
return this13395.minusMonths(long13396);
});
cljc.java_time.zoned_date_time.minus = (function cljc$java_time$zoned_date_time$minus(var_args){
var G__74287 = arguments.length;
switch (G__74287) {
case 2:
return cljc.java_time.zoned_date_time.minus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.zoned_date_time.minus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.zoned_date_time.minus.cljs$core$IFn$_invoke$arity$2 = (function (this13397,java_time_temporal_TemporalAmount13398){
return this13397.minus(java_time_temporal_TemporalAmount13398);
}));

(cljc.java_time.zoned_date_time.minus.cljs$core$IFn$_invoke$arity$3 = (function (this13399,long13400,java_time_temporal_TemporalUnit13401){
return this13399.minus(long13400,java_time_temporal_TemporalUnit13401);
}));

(cljc.java_time.zoned_date_time.minus.cljs$lang$maxFixedArity = 3);

cljc.java_time.zoned_date_time.with_fixed_offset_zone = (function cljc$java_time$zoned_date_time$with_fixed_offset_zone(this13402){
return this13402.withFixedOffsetZone();
});
cljc.java_time.zoned_date_time.plus_hours = (function cljc$java_time$zoned_date_time$plus_hours(this13403,long13404){
return this13403.plusHours(long13404);
});
cljc.java_time.zoned_date_time.with_zone_same_local = (function cljc$java_time$zoned_date_time$with_zone_same_local(this13405,java_time_ZoneId13406){
return this13405.withZoneSameLocal(java_time_ZoneId13406);
});
cljc.java_time.zoned_date_time.with_zone_same_instant = (function cljc$java_time$zoned_date_time$with_zone_same_instant(this13407,java_time_ZoneId13408){
return this13407.withZoneSameInstant(java_time_ZoneId13408);
});
cljc.java_time.zoned_date_time.plus_days = (function cljc$java_time$zoned_date_time$plus_days(this13409,long13410){
return this13409.plusDays(long13410);
});
cljc.java_time.zoned_date_time.to_local_time = (function cljc$java_time$zoned_date_time$to_local_time(this13411){
return this13411.toLocalTime();
});
cljc.java_time.zoned_date_time.get_long = (function cljc$java_time$zoned_date_time$get_long(this13412,java_time_temporal_TemporalField13413){
return this13412.getLong(java_time_temporal_TemporalField13413);
});
cljc.java_time.zoned_date_time.get_offset = (function cljc$java_time$zoned_date_time$get_offset(this13414){
return this13414.offset();
});
cljc.java_time.zoned_date_time.with_year = (function cljc$java_time$zoned_date_time$with_year(this13415,int13416){
return this13415.withYear(int13416);
});
cljc.java_time.zoned_date_time.with_nano = (function cljc$java_time$zoned_date_time$with_nano(this13417,int13418){
return this13417.withNano(int13418);
});
cljc.java_time.zoned_date_time.to_epoch_second = (function cljc$java_time$zoned_date_time$to_epoch_second(this13419){
return this13419.toEpochSecond();
});
cljc.java_time.zoned_date_time.to_offset_date_time = (function cljc$java_time$zoned_date_time$to_offset_date_time(this13420){
return this13420.toOffsetDateTime();
});
cljc.java_time.zoned_date_time.with_later_offset_at_overlap = (function cljc$java_time$zoned_date_time$with_later_offset_at_overlap(this13421){
return this13421.withLaterOffsetAtOverlap();
});
cljc.java_time.zoned_date_time.until = (function cljc$java_time$zoned_date_time$until(this13422,java_time_temporal_Temporal13423,java_time_temporal_TemporalUnit13424){
return this13422.until(java_time_temporal_Temporal13423,java_time_temporal_TemporalUnit13424);
});
cljc.java_time.zoned_date_time.get_zone = (function cljc$java_time$zoned_date_time$get_zone(this13425){
return this13425.zone();
});
cljc.java_time.zoned_date_time.with_day_of_month = (function cljc$java_time$zoned_date_time$with_day_of_month(this13426,int13427){
return this13426.withDayOfMonth(int13427);
});
cljc.java_time.zoned_date_time.get_day_of_month = (function cljc$java_time$zoned_date_time$get_day_of_month(this13428){
return this13428.dayOfMonth();
});
cljc.java_time.zoned_date_time.from = (function cljc$java_time$zoned_date_time$from(java_time_temporal_TemporalAccessor13429){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZonedDateTime,"from",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_temporal_TemporalAccessor13429], 0));
});
cljc.java_time.zoned_date_time.is_after = (function cljc$java_time$zoned_date_time$is_after(this13430,java_time_chrono_ChronoZonedDateTime13431){
return this13430.isAfter(java_time_chrono_ChronoZonedDateTime13431);
});
cljc.java_time.zoned_date_time.minus_nanos = (function cljc$java_time$zoned_date_time$minus_nanos(this13432,long13433){
return this13432.minusNanos(long13433);
});
cljc.java_time.zoned_date_time.is_supported = (function cljc$java_time$zoned_date_time$is_supported(this13434,G__13435){
return this13434.isSupported(G__13435);
});
cljc.java_time.zoned_date_time.minus_years = (function cljc$java_time$zoned_date_time$minus_years(this13436,long13437){
return this13436.minusYears(long13437);
});
cljc.java_time.zoned_date_time.get_chronology = (function cljc$java_time$zoned_date_time$get_chronology(this13438){
return this13438.chronology();
});
cljc.java_time.zoned_date_time.parse = (function cljc$java_time$zoned_date_time$parse(var_args){
var G__74298 = arguments.length;
switch (G__74298) {
case 1:
return cljc.java_time.zoned_date_time.parse.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljc.java_time.zoned_date_time.parse.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.zoned_date_time.parse.cljs$core$IFn$_invoke$arity$1 = (function (java_lang_CharSequence13439){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZonedDateTime,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence13439], 0));
}));

(cljc.java_time.zoned_date_time.parse.cljs$core$IFn$_invoke$arity$2 = (function (java_lang_CharSequence13440,java_time_format_DateTimeFormatter13441){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZonedDateTime,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence13440,java_time_format_DateTimeFormatter13441], 0));
}));

(cljc.java_time.zoned_date_time.parse.cljs$lang$maxFixedArity = 2);

cljc.java_time.zoned_date_time.with_second = (function cljc$java_time$zoned_date_time$with_second(this13442,int13443){
return this13442.withSecond(int13443);
});
cljc.java_time.zoned_date_time.to_local_date = (function cljc$java_time$zoned_date_time$to_local_date(this13444){
return this13444.toLocalDate();
});
cljc.java_time.zoned_date_time.get_minute = (function cljc$java_time$zoned_date_time$get_minute(this13445){
return this13445.minute();
});
cljc.java_time.zoned_date_time.hash_code = (function cljc$java_time$zoned_date_time$hash_code(this13446){
return this13446.hashCode();
});
cljc.java_time.zoned_date_time.with$ = (function cljc$java_time$zoned_date_time$with(var_args){
var G__74309 = arguments.length;
switch (G__74309) {
case 2:
return cljc.java_time.zoned_date_time.with$.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.zoned_date_time.with$.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.zoned_date_time.with$.cljs$core$IFn$_invoke$arity$2 = (function (this13447,java_time_temporal_TemporalAdjuster13448){
return this13447.with(java_time_temporal_TemporalAdjuster13448);
}));

(cljc.java_time.zoned_date_time.with$.cljs$core$IFn$_invoke$arity$3 = (function (this13449,java_time_temporal_TemporalField13450,long13451){
return this13449.with(java_time_temporal_TemporalField13450,long13451);
}));

(cljc.java_time.zoned_date_time.with$.cljs$lang$maxFixedArity = 3);

cljc.java_time.zoned_date_time.now = (function cljc$java_time$zoned_date_time$now(var_args){
var G__74313 = arguments.length;
switch (G__74313) {
case 0:
return cljc.java_time.zoned_date_time.now.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return cljc.java_time.zoned_date_time.now.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.zoned_date_time.now.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljs.core.js_invoke(java.time.ZonedDateTime,"now");
}));

(cljc.java_time.zoned_date_time.now.cljs$core$IFn$_invoke$arity$1 = (function (G__13453){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZonedDateTime,"now",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([G__13453], 0));
}));

(cljc.java_time.zoned_date_time.now.cljs$lang$maxFixedArity = 1);

cljc.java_time.zoned_date_time.to_local_date_time = (function cljc$java_time$zoned_date_time$to_local_date_time(this13454){
return this13454.toLocalDateTime();
});
cljc.java_time.zoned_date_time.get_month_value = (function cljc$java_time$zoned_date_time$get_month_value(this13455){
return this13455.monthValue();
});
cljc.java_time.zoned_date_time.with_day_of_year = (function cljc$java_time$zoned_date_time$with_day_of_year(this13456,int13457){
return this13456.withDayOfYear(int13457);
});
cljc.java_time.zoned_date_time.compare_to = (function cljc$java_time$zoned_date_time$compare_to(this13458,java_time_chrono_ChronoZonedDateTime13459){
return this13458.compareTo(java_time_chrono_ChronoZonedDateTime13459);
});
cljc.java_time.zoned_date_time.of_strict = (function cljc$java_time$zoned_date_time$of_strict(java_time_LocalDateTime13460,java_time_ZoneOffset13461,java_time_ZoneId13462){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZonedDateTime,"ofStrict",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_LocalDateTime13460,java_time_ZoneOffset13461,java_time_ZoneId13462], 0));
});
cljc.java_time.zoned_date_time.get_month = (function cljc$java_time$zoned_date_time$get_month(this13463){
return this13463.month();
});
cljc.java_time.zoned_date_time.of_instant = (function cljc$java_time$zoned_date_time$of_instant(var_args){
var G__74323 = arguments.length;
switch (G__74323) {
case 2:
return cljc.java_time.zoned_date_time.of_instant.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.zoned_date_time.of_instant.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.zoned_date_time.of_instant.cljs$core$IFn$_invoke$arity$2 = (function (java_time_Instant13464,java_time_ZoneId13465){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZonedDateTime,"ofInstant",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_Instant13464,java_time_ZoneId13465], 0));
}));

(cljc.java_time.zoned_date_time.of_instant.cljs$core$IFn$_invoke$arity$3 = (function (java_time_LocalDateTime13466,java_time_ZoneOffset13467,java_time_ZoneId13468){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZonedDateTime,"ofInstant",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_LocalDateTime13466,java_time_ZoneOffset13467,java_time_ZoneId13468], 0));
}));

(cljc.java_time.zoned_date_time.of_instant.cljs$lang$maxFixedArity = 3);

cljc.java_time.zoned_date_time.plus_seconds = (function cljc$java_time$zoned_date_time$plus_seconds(this13469,long13470){
return this13469.plusSeconds(long13470);
});
cljc.java_time.zoned_date_time.get = (function cljc$java_time$zoned_date_time$get(this13471,java_time_temporal_TemporalField13472){
return this13471.get(java_time_temporal_TemporalField13472);
});
cljc.java_time.zoned_date_time.equals = (function cljc$java_time$zoned_date_time$equals(this13473,java_lang_Object13474){
return this13473.equals(java_lang_Object13474);
});
cljc.java_time.zoned_date_time.format = (function cljc$java_time$zoned_date_time$format(this13475,java_time_format_DateTimeFormatter13476){
return this13475.format(java_time_format_DateTimeFormatter13476);
});
cljc.java_time.zoned_date_time.plus_years = (function cljc$java_time$zoned_date_time$plus_years(this13477,long13478){
return this13477.plusYears(long13478);
});
cljc.java_time.zoned_date_time.minus_days = (function cljc$java_time$zoned_date_time$minus_days(this13479,long13480){
return this13479.minusDays(long13480);
});

//# sourceMappingURL=cljc.java_time.zoned_date_time.js.map
