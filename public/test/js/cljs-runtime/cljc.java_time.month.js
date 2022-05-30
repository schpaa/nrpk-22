goog.provide('cljc.java_time.month');
goog.scope(function(){
  cljc.java_time.month.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.month.may = cljc.java_time.month.goog$module$goog$object.get(java.time.Month,"MAY");
cljc.java_time.month.december = cljc.java_time.month.goog$module$goog$object.get(java.time.Month,"DECEMBER");
cljc.java_time.month.june = cljc.java_time.month.goog$module$goog$object.get(java.time.Month,"JUNE");
cljc.java_time.month.september = cljc.java_time.month.goog$module$goog$object.get(java.time.Month,"SEPTEMBER");
cljc.java_time.month.february = cljc.java_time.month.goog$module$goog$object.get(java.time.Month,"FEBRUARY");
cljc.java_time.month.january = cljc.java_time.month.goog$module$goog$object.get(java.time.Month,"JANUARY");
cljc.java_time.month.november = cljc.java_time.month.goog$module$goog$object.get(java.time.Month,"NOVEMBER");
cljc.java_time.month.august = cljc.java_time.month.goog$module$goog$object.get(java.time.Month,"AUGUST");
cljc.java_time.month.july = cljc.java_time.month.goog$module$goog$object.get(java.time.Month,"JULY");
cljc.java_time.month.march = cljc.java_time.month.goog$module$goog$object.get(java.time.Month,"MARCH");
cljc.java_time.month.october = cljc.java_time.month.goog$module$goog$object.get(java.time.Month,"OCTOBER");
cljc.java_time.month.april = cljc.java_time.month.goog$module$goog$object.get(java.time.Month,"APRIL");
cljc.java_time.month.range = (function cljc$java_time$month$range(this14424,java_time_temporal_TemporalField14425){
return this14424.range(java_time_temporal_TemporalField14425);
});
cljc.java_time.month.values = (function cljc$java_time$month$values(){
return cljs.core.js_invoke(java.time.Month,"values");
});
cljc.java_time.month.value_of = (function cljc$java_time$month$value_of(var_args){
var G__74301 = arguments.length;
switch (G__74301) {
case 1:
return cljc.java_time.month.value_of.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljc.java_time.month.value_of.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.month.value_of.cljs$core$IFn$_invoke$arity$1 = (function (java_lang_String14426){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Month,"valueOf",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_String14426], 0));
}));

(cljc.java_time.month.value_of.cljs$core$IFn$_invoke$arity$2 = (function (java_lang_Class14427,java_lang_String14428){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Month,"valueOf",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_Class14427,java_lang_String14428], 0));
}));

(cljc.java_time.month.value_of.cljs$lang$maxFixedArity = 2);

cljc.java_time.month.of = (function cljc$java_time$month$of(int14429){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Month,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int14429], 0));
});
cljc.java_time.month.ordinal = (function cljc$java_time$month$ordinal(this14430){
return this14430.ordinal();
});
cljc.java_time.month.first_month_of_quarter = (function cljc$java_time$month$first_month_of_quarter(this14431){
return this14431.firstMonthOfQuarter();
});
cljc.java_time.month.min_length = (function cljc$java_time$month$min_length(this14432){
return this14432.minLength();
});
cljc.java_time.month.plus = (function cljc$java_time$month$plus(this14433,long14434){
return this14433.plus(long14434);
});
cljc.java_time.month.query = (function cljc$java_time$month$query(this14435,java_time_temporal_TemporalQuery14436){
return this14435.query(java_time_temporal_TemporalQuery14436);
});
cljc.java_time.month.to_string = (function cljc$java_time$month$to_string(this14437){
return this14437.toString();
});
cljc.java_time.month.first_day_of_year = (function cljc$java_time$month$first_day_of_year(this14438,boolean14439){
return this14438.firstDayOfYear(boolean14439);
});
cljc.java_time.month.minus = (function cljc$java_time$month$minus(this14440,long14441){
return this14440.minus(long14441);
});
cljc.java_time.month.get_display_name = (function cljc$java_time$month$get_display_name(this14442,java_time_format_TextStyle14443,java_util_Locale14444){
return this14442.displayName(java_time_format_TextStyle14443,java_util_Locale14444);
});
cljc.java_time.month.get_value = (function cljc$java_time$month$get_value(this14445){
return this14445.value();
});
cljc.java_time.month.max_length = (function cljc$java_time$month$max_length(this14446){
return this14446.maxLength();
});
cljc.java_time.month.name = (function cljc$java_time$month$name(this14447){
return this14447.name();
});
cljc.java_time.month.get_long = (function cljc$java_time$month$get_long(this14448,java_time_temporal_TemporalField14449){
return this14448.getLong(java_time_temporal_TemporalField14449);
});
cljc.java_time.month.length = (function cljc$java_time$month$length(this14450,boolean14451){
return this14450.length(boolean14451);
});
cljc.java_time.month.get_declaring_class = (function cljc$java_time$month$get_declaring_class(this14452){
return this14452.declaringClass();
});
cljc.java_time.month.from = (function cljc$java_time$month$from(java_time_temporal_TemporalAccessor14453){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Month,"from",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_temporal_TemporalAccessor14453], 0));
});
cljc.java_time.month.is_supported = (function cljc$java_time$month$is_supported(this14454,java_time_temporal_TemporalField14455){
return this14454.isSupported(java_time_temporal_TemporalField14455);
});
cljc.java_time.month.hash_code = (function cljc$java_time$month$hash_code(this14456){
return this14456.hashCode();
});
cljc.java_time.month.adjust_into = (function cljc$java_time$month$adjust_into(this14457,java_time_temporal_Temporal14458){
return this14457.adjustInto(java_time_temporal_Temporal14458);
});
cljc.java_time.month.compare_to = (function cljc$java_time$month$compare_to(this14459,java_lang_Enum14460){
return this14459.compareTo(java_lang_Enum14460);
});
cljc.java_time.month.get = (function cljc$java_time$month$get(this14461,java_time_temporal_TemporalField14462){
return this14461.get(java_time_temporal_TemporalField14462);
});
cljc.java_time.month.equals = (function cljc$java_time$month$equals(this14463,java_lang_Object14464){
return this14463.equals(java_lang_Object14464);
});

//# sourceMappingURL=cljc.java_time.month.js.map
