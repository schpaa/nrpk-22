goog.provide('cljc.java_time.local_date');
goog.scope(function(){
  cljc.java_time.local_date.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.local_date.max = cljc.java_time.local_date.goog$module$goog$object.get(java.time.LocalDate,"MAX");
cljc.java_time.local_date.min = cljc.java_time.local_date.goog$module$goog$object.get(java.time.LocalDate,"MIN");
cljc.java_time.local_date.minus_weeks = (function cljc$java_time$local_date$minus_weeks(this12775,long12776){
return this12775.minusWeeks(long12776);
});
cljc.java_time.local_date.plus_weeks = (function cljc$java_time$local_date$plus_weeks(this12777,long12778){
return this12777.plusWeeks(long12778);
});
cljc.java_time.local_date.length_of_year = (function cljc$java_time$local_date$length_of_year(this12779){
return this12779.lengthOfYear();
});
cljc.java_time.local_date.range = (function cljc$java_time$local_date$range(this12780,java_time_temporal_TemporalField12781){
return this12780.range(java_time_temporal_TemporalField12781);
});
cljc.java_time.local_date.get_era = (function cljc$java_time$local_date$get_era(this12782){
return this12782.era();
});
cljc.java_time.local_date.of = (function cljc$java_time$local_date$of(G__12784,G__12785,G__12786){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDate,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([G__12784,G__12785,G__12786], 0));
});
cljc.java_time.local_date.with_month = (function cljc$java_time$local_date$with_month(this12787,int12788){
return this12787.withMonth(int12788);
});
cljc.java_time.local_date.is_equal = (function cljc$java_time$local_date$is_equal(this12789,java_time_chrono_ChronoLocalDate12790){
return this12789.isEqual(java_time_chrono_ChronoLocalDate12790);
});
cljc.java_time.local_date.get_year = (function cljc$java_time$local_date$get_year(this12791){
return this12791.year();
});
cljc.java_time.local_date.to_epoch_day = (function cljc$java_time$local_date$to_epoch_day(this12792){
return this12792.toEpochDay();
});
cljc.java_time.local_date.get_day_of_year = (function cljc$java_time$local_date$get_day_of_year(this12793){
return this12793.dayOfYear();
});
cljc.java_time.local_date.plus = (function cljc$java_time$local_date$plus(var_args){
var G__74130 = arguments.length;
switch (G__74130) {
case 3:
return cljc.java_time.local_date.plus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 2:
return cljc.java_time.local_date.plus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_date.plus.cljs$core$IFn$_invoke$arity$3 = (function (this12794,long12795,java_time_temporal_TemporalUnit12796){
return this12794.plus(long12795,java_time_temporal_TemporalUnit12796);
}));

(cljc.java_time.local_date.plus.cljs$core$IFn$_invoke$arity$2 = (function (this12797,java_time_temporal_TemporalAmount12798){
return this12797.plus(java_time_temporal_TemporalAmount12798);
}));

(cljc.java_time.local_date.plus.cljs$lang$maxFixedArity = 3);

cljc.java_time.local_date.is_leap_year = (function cljc$java_time$local_date$is_leap_year(this12799){
return this12799.isLeapYear();
});
cljc.java_time.local_date.query = (function cljc$java_time$local_date$query(this12800,java_time_temporal_TemporalQuery12801){
return this12800.query(java_time_temporal_TemporalQuery12801);
});
cljc.java_time.local_date.get_day_of_week = (function cljc$java_time$local_date$get_day_of_week(this12802){
return this12802.dayOfWeek();
});
cljc.java_time.local_date.to_string = (function cljc$java_time$local_date$to_string(this12803){
return this12803.toString();
});
cljc.java_time.local_date.plus_months = (function cljc$java_time$local_date$plus_months(this12804,long12805){
return this12804.plusMonths(long12805);
});
cljc.java_time.local_date.is_before = (function cljc$java_time$local_date$is_before(this12806,java_time_chrono_ChronoLocalDate12807){
return this12806.isBefore(java_time_chrono_ChronoLocalDate12807);
});
cljc.java_time.local_date.minus_months = (function cljc$java_time$local_date$minus_months(this12808,long12809){
return this12808.minusMonths(long12809);
});
cljc.java_time.local_date.minus = (function cljc$java_time$local_date$minus(var_args){
var G__74164 = arguments.length;
switch (G__74164) {
case 2:
return cljc.java_time.local_date.minus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.local_date.minus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_date.minus.cljs$core$IFn$_invoke$arity$2 = (function (this12810,java_time_temporal_TemporalAmount12811){
return this12810.minus(java_time_temporal_TemporalAmount12811);
}));

(cljc.java_time.local_date.minus.cljs$core$IFn$_invoke$arity$3 = (function (this12812,long12813,java_time_temporal_TemporalUnit12814){
return this12812.minus(long12813,java_time_temporal_TemporalUnit12814);
}));

(cljc.java_time.local_date.minus.cljs$lang$maxFixedArity = 3);

cljc.java_time.local_date.plus_days = (function cljc$java_time$local_date$plus_days(this12815,long12816){
return this12815.plusDays(long12816);
});
cljc.java_time.local_date.get_long = (function cljc$java_time$local_date$get_long(this12817,java_time_temporal_TemporalField12818){
return this12817.getLong(java_time_temporal_TemporalField12818);
});
cljc.java_time.local_date.with_year = (function cljc$java_time$local_date$with_year(this12819,int12820){
return this12819.withYear(int12820);
});
cljc.java_time.local_date.length_of_month = (function cljc$java_time$local_date$length_of_month(this12821){
return this12821.lengthOfMonth();
});
cljc.java_time.local_date.until = (function cljc$java_time$local_date$until(var_args){
var G__74191 = arguments.length;
switch (G__74191) {
case 3:
return cljc.java_time.local_date.until.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 2:
return cljc.java_time.local_date.until.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_date.until.cljs$core$IFn$_invoke$arity$3 = (function (this12822,java_time_temporal_Temporal12823,java_time_temporal_TemporalUnit12824){
return this12822.until(java_time_temporal_Temporal12823,java_time_temporal_TemporalUnit12824);
}));

(cljc.java_time.local_date.until.cljs$core$IFn$_invoke$arity$2 = (function (this12825,java_time_chrono_ChronoLocalDate12826){
return this12825.until(java_time_chrono_ChronoLocalDate12826);
}));

(cljc.java_time.local_date.until.cljs$lang$maxFixedArity = 3);

cljc.java_time.local_date.of_epoch_day = (function cljc$java_time$local_date$of_epoch_day(long12827){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDate,"ofEpochDay",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([long12827], 0));
});
cljc.java_time.local_date.with_day_of_month = (function cljc$java_time$local_date$with_day_of_month(this12828,int12829){
return this12828.withDayOfMonth(int12829);
});
cljc.java_time.local_date.get_day_of_month = (function cljc$java_time$local_date$get_day_of_month(this12830){
return this12830.dayOfMonth();
});
cljc.java_time.local_date.from = (function cljc$java_time$local_date$from(java_time_temporal_TemporalAccessor12831){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDate,"from",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_temporal_TemporalAccessor12831], 0));
});
cljc.java_time.local_date.is_after = (function cljc$java_time$local_date$is_after(this12832,java_time_chrono_ChronoLocalDate12833){
return this12832.isAfter(java_time_chrono_ChronoLocalDate12833);
});
cljc.java_time.local_date.is_supported = (function cljc$java_time$local_date$is_supported(this12834,G__12835){
return this12834.isSupported(G__12835);
});
cljc.java_time.local_date.minus_years = (function cljc$java_time$local_date$minus_years(this12836,long12837){
return this12836.minusYears(long12837);
});
cljc.java_time.local_date.get_chronology = (function cljc$java_time$local_date$get_chronology(this12838){
return this12838.chronology();
});
cljc.java_time.local_date.parse = (function cljc$java_time$local_date$parse(var_args){
var G__74196 = arguments.length;
switch (G__74196) {
case 1:
return cljc.java_time.local_date.parse.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljc.java_time.local_date.parse.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_date.parse.cljs$core$IFn$_invoke$arity$1 = (function (java_lang_CharSequence12839){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDate,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence12839], 0));
}));

(cljc.java_time.local_date.parse.cljs$core$IFn$_invoke$arity$2 = (function (java_lang_CharSequence12840,java_time_format_DateTimeFormatter12841){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDate,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence12840,java_time_format_DateTimeFormatter12841], 0));
}));

(cljc.java_time.local_date.parse.cljs$lang$maxFixedArity = 2);

cljc.java_time.local_date.hash_code = (function cljc$java_time$local_date$hash_code(this12842){
return this12842.hashCode();
});
cljc.java_time.local_date.adjust_into = (function cljc$java_time$local_date$adjust_into(this12843,java_time_temporal_Temporal12844){
return this12843.adjustInto(java_time_temporal_Temporal12844);
});
cljc.java_time.local_date.with$ = (function cljc$java_time$local_date$with(var_args){
var G__74200 = arguments.length;
switch (G__74200) {
case 2:
return cljc.java_time.local_date.with$.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.local_date.with$.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_date.with$.cljs$core$IFn$_invoke$arity$2 = (function (this12845,java_time_temporal_TemporalAdjuster12846){
return this12845.with(java_time_temporal_TemporalAdjuster12846);
}));

(cljc.java_time.local_date.with$.cljs$core$IFn$_invoke$arity$3 = (function (this12847,java_time_temporal_TemporalField12848,long12849){
return this12847.with(java_time_temporal_TemporalField12848,long12849);
}));

(cljc.java_time.local_date.with$.cljs$lang$maxFixedArity = 3);

cljc.java_time.local_date.now = (function cljc$java_time$local_date$now(var_args){
var G__74208 = arguments.length;
switch (G__74208) {
case 0:
return cljc.java_time.local_date.now.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return cljc.java_time.local_date.now.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_date.now.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljs.core.js_invoke(java.time.LocalDate,"now");
}));

(cljc.java_time.local_date.now.cljs$core$IFn$_invoke$arity$1 = (function (G__12851){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDate,"now",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([G__12851], 0));
}));

(cljc.java_time.local_date.now.cljs$lang$maxFixedArity = 1);

cljc.java_time.local_date.at_start_of_day = (function cljc$java_time$local_date$at_start_of_day(var_args){
var G__74212 = arguments.length;
switch (G__74212) {
case 1:
return cljc.java_time.local_date.at_start_of_day.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljc.java_time.local_date.at_start_of_day.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_date.at_start_of_day.cljs$core$IFn$_invoke$arity$1 = (function (this12852){
return this12852.atStartOfDay();
}));

(cljc.java_time.local_date.at_start_of_day.cljs$core$IFn$_invoke$arity$2 = (function (this12853,java_time_ZoneId12854){
return this12853.atStartOfDay(java_time_ZoneId12854);
}));

(cljc.java_time.local_date.at_start_of_day.cljs$lang$maxFixedArity = 2);

cljc.java_time.local_date.get_month_value = (function cljc$java_time$local_date$get_month_value(this12855){
return this12855.monthValue();
});
cljc.java_time.local_date.with_day_of_year = (function cljc$java_time$local_date$with_day_of_year(this12856,int12857){
return this12856.withDayOfYear(int12857);
});
cljc.java_time.local_date.compare_to = (function cljc$java_time$local_date$compare_to(this12858,java_time_chrono_ChronoLocalDate12859){
return this12858.compareTo(java_time_chrono_ChronoLocalDate12859);
});
cljc.java_time.local_date.get_month = (function cljc$java_time$local_date$get_month(this12860){
return this12860.month();
});
cljc.java_time.local_date.of_year_day = (function cljc$java_time$local_date$of_year_day(int12861,int12862){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalDate,"ofYearDay",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int12861,int12862], 0));
});
cljc.java_time.local_date.get = (function cljc$java_time$local_date$get(this12863,java_time_temporal_TemporalField12864){
return this12863.get(java_time_temporal_TemporalField12864);
});
cljc.java_time.local_date.equals = (function cljc$java_time$local_date$equals(this12865,java_lang_Object12866){
return this12865.equals(java_lang_Object12866);
});
cljc.java_time.local_date.at_time = (function cljc$java_time$local_date$at_time(var_args){
var G__74218 = arguments.length;
switch (G__74218) {
case 2:
return cljc.java_time.local_date.at_time.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 5:
return cljc.java_time.local_date.at_time.cljs$core$IFn$_invoke$arity$5((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]),(arguments[(4)]));

break;
case 4:
return cljc.java_time.local_date.at_time.cljs$core$IFn$_invoke$arity$4((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]));

break;
case 3:
return cljc.java_time.local_date.at_time.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_date.at_time.cljs$core$IFn$_invoke$arity$2 = (function (this12867,G__12868){
return this12867.atTime(G__12868);
}));

(cljc.java_time.local_date.at_time.cljs$core$IFn$_invoke$arity$5 = (function (this12869,int12870,int12871,int12872,int12873){
return this12869.atTime(int12870,int12871,int12872,int12873);
}));

(cljc.java_time.local_date.at_time.cljs$core$IFn$_invoke$arity$4 = (function (this12874,int12875,int12876,int12877){
return this12874.atTime(int12875,int12876,int12877);
}));

(cljc.java_time.local_date.at_time.cljs$core$IFn$_invoke$arity$3 = (function (this12878,int12879,int12880){
return this12878.atTime(int12879,int12880);
}));

(cljc.java_time.local_date.at_time.cljs$lang$maxFixedArity = 5);

cljc.java_time.local_date.format = (function cljc$java_time$local_date$format(this12881,java_time_format_DateTimeFormatter12882){
return this12881.format(java_time_format_DateTimeFormatter12882);
});
cljc.java_time.local_date.plus_years = (function cljc$java_time$local_date$plus_years(this12883,long12884){
return this12883.plusYears(long12884);
});
cljc.java_time.local_date.minus_days = (function cljc$java_time$local_date$minus_days(this12885,long12886){
return this12885.minusDays(long12886);
});

//# sourceMappingURL=cljc.java_time.local_date.js.map
