goog.provide('cljc.java_time.year');
goog.scope(function(){
  cljc.java_time.year.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.year.min_value = cljc.java_time.year.goog$module$goog$object.get(java.time.Year,"MIN_VALUE");
cljc.java_time.year.max_value = cljc.java_time.year.goog$module$goog$object.get(java.time.Year,"MAX_VALUE");
cljc.java_time.year.range = (function cljc$java_time$year$range(this14780,java_time_temporal_TemporalField14781){
return this14780.range(java_time_temporal_TemporalField14781);
});
cljc.java_time.year.of = (function cljc$java_time$year$of(int14782){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Year,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int14782], 0));
});
cljc.java_time.year.at_day = (function cljc$java_time$year$at_day(this14783,int14784){
return this14783.atDay(int14784);
});
cljc.java_time.year.plus = (function cljc$java_time$year$plus(var_args){
var G__74294 = arguments.length;
switch (G__74294) {
case 3:
return cljc.java_time.year.plus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 2:
return cljc.java_time.year.plus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.year.plus.cljs$core$IFn$_invoke$arity$3 = (function (this14785,long14786,java_time_temporal_TemporalUnit14787){
return this14785.plus(long14786,java_time_temporal_TemporalUnit14787);
}));

(cljc.java_time.year.plus.cljs$core$IFn$_invoke$arity$2 = (function (this14788,java_time_temporal_TemporalAmount14789){
return this14788.plus(java_time_temporal_TemporalAmount14789);
}));

(cljc.java_time.year.plus.cljs$lang$maxFixedArity = 3);

cljc.java_time.year.is_valid_month_day = (function cljc$java_time$year$is_valid_month_day(this14790,java_time_MonthDay14791){
return this14790.isValidMonthDay(java_time_MonthDay14791);
});
cljc.java_time.year.query = (function cljc$java_time$year$query(this14792,java_time_temporal_TemporalQuery14793){
return this14792.query(java_time_temporal_TemporalQuery14793);
});
cljc.java_time.year.is_leap = (function cljc$java_time$year$is_leap(long57050){
return java.time.Year.isLeap(long57050);
});
cljc.java_time.year.to_string = (function cljc$java_time$year$to_string(this14794){
return this14794.toString();
});
cljc.java_time.year.is_before = (function cljc$java_time$year$is_before(this14795,java_time_Year14796){
return this14795.isBefore(java_time_Year14796);
});
cljc.java_time.year.minus = (function cljc$java_time$year$minus(var_args){
var G__74311 = arguments.length;
switch (G__74311) {
case 3:
return cljc.java_time.year.minus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 2:
return cljc.java_time.year.minus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.year.minus.cljs$core$IFn$_invoke$arity$3 = (function (this14797,long14798,java_time_temporal_TemporalUnit14799){
return this14797.minus(long14798,java_time_temporal_TemporalUnit14799);
}));

(cljc.java_time.year.minus.cljs$core$IFn$_invoke$arity$2 = (function (this14800,java_time_temporal_TemporalAmount14801){
return this14800.minus(java_time_temporal_TemporalAmount14801);
}));

(cljc.java_time.year.minus.cljs$lang$maxFixedArity = 3);

cljc.java_time.year.at_month_day = (function cljc$java_time$year$at_month_day(this14802,java_time_MonthDay14803){
return this14802.atMonthDay(java_time_MonthDay14803);
});
cljc.java_time.year.get_value = (function cljc$java_time$year$get_value(this14804){
return this14804.value();
});
cljc.java_time.year.get_long = (function cljc$java_time$year$get_long(this14805,java_time_temporal_TemporalField14806){
return this14805.getLong(java_time_temporal_TemporalField14806);
});
cljc.java_time.year.at_month = (function cljc$java_time$year$at_month(this14807,G__14808){
return this14807.atMonth(G__14808);
});
cljc.java_time.year.until = (function cljc$java_time$year$until(this14809,java_time_temporal_Temporal14810,java_time_temporal_TemporalUnit14811){
return this14809.until(java_time_temporal_Temporal14810,java_time_temporal_TemporalUnit14811);
});
cljc.java_time.year.length = (function cljc$java_time$year$length(this14812){
return this14812.length();
});
cljc.java_time.year.from = (function cljc$java_time$year$from(java_time_temporal_TemporalAccessor14813){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Year,"from",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_temporal_TemporalAccessor14813], 0));
});
cljc.java_time.year.is_after = (function cljc$java_time$year$is_after(this14814,java_time_Year14815){
return this14814.isAfter(java_time_Year14815);
});
cljc.java_time.year.is_supported = (function cljc$java_time$year$is_supported(this14816,G__14817){
return this14816.isSupported(G__14817);
});
cljc.java_time.year.minus_years = (function cljc$java_time$year$minus_years(this14818,long14819){
return this14818.minusYears(long14819);
});
cljc.java_time.year.parse = (function cljc$java_time$year$parse(var_args){
var G__74334 = arguments.length;
switch (G__74334) {
case 1:
return cljc.java_time.year.parse.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljc.java_time.year.parse.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.year.parse.cljs$core$IFn$_invoke$arity$1 = (function (java_lang_CharSequence14820){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Year,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence14820], 0));
}));

(cljc.java_time.year.parse.cljs$core$IFn$_invoke$arity$2 = (function (java_lang_CharSequence14821,java_time_format_DateTimeFormatter14822){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Year,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence14821,java_time_format_DateTimeFormatter14822], 0));
}));

(cljc.java_time.year.parse.cljs$lang$maxFixedArity = 2);

cljc.java_time.year.hash_code = (function cljc$java_time$year$hash_code(this14823){
return this14823.hashCode();
});
cljc.java_time.year.adjust_into = (function cljc$java_time$year$adjust_into(this14824,java_time_temporal_Temporal14825){
return this14824.adjustInto(java_time_temporal_Temporal14825);
});
cljc.java_time.year.with$ = (function cljc$java_time$year$with(var_args){
var G__74336 = arguments.length;
switch (G__74336) {
case 2:
return cljc.java_time.year.with$.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.year.with$.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.year.with$.cljs$core$IFn$_invoke$arity$2 = (function (this14826,java_time_temporal_TemporalAdjuster14827){
return this14826.with(java_time_temporal_TemporalAdjuster14827);
}));

(cljc.java_time.year.with$.cljs$core$IFn$_invoke$arity$3 = (function (this14828,java_time_temporal_TemporalField14829,long14830){
return this14828.with(java_time_temporal_TemporalField14829,long14830);
}));

(cljc.java_time.year.with$.cljs$lang$maxFixedArity = 3);

cljc.java_time.year.now = (function cljc$java_time$year$now(var_args){
var G__74341 = arguments.length;
switch (G__74341) {
case 0:
return cljc.java_time.year.now.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return cljc.java_time.year.now.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.year.now.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljs.core.js_invoke(java.time.Year,"now");
}));

(cljc.java_time.year.now.cljs$core$IFn$_invoke$arity$1 = (function (G__14832){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Year,"now",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([G__14832], 0));
}));

(cljc.java_time.year.now.cljs$lang$maxFixedArity = 1);

cljc.java_time.year.compare_to = (function cljc$java_time$year$compare_to(this14833,java_time_Year14834){
return this14833.compareTo(java_time_Year14834);
});
cljc.java_time.year.get = (function cljc$java_time$year$get(this14835,java_time_temporal_TemporalField14836){
return this14835.get(java_time_temporal_TemporalField14836);
});
cljc.java_time.year.equals = (function cljc$java_time$year$equals(this14837,java_lang_Object14838){
return this14837.equals(java_lang_Object14838);
});
cljc.java_time.year.format = (function cljc$java_time$year$format(this14839,java_time_format_DateTimeFormatter14840){
return this14839.format(java_time_format_DateTimeFormatter14840);
});
cljc.java_time.year.plus_years = (function cljc$java_time$year$plus_years(this14841,long14842){
return this14841.plusYears(long14842);
});

//# sourceMappingURL=cljc.java_time.year.js.map
