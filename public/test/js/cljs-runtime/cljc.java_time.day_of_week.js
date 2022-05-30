goog.provide('cljc.java_time.day_of_week');
goog.scope(function(){
  cljc.java_time.day_of_week.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.day_of_week.saturday = cljc.java_time.day_of_week.goog$module$goog$object.get(java.time.DayOfWeek,"SATURDAY");
cljc.java_time.day_of_week.thursday = cljc.java_time.day_of_week.goog$module$goog$object.get(java.time.DayOfWeek,"THURSDAY");
cljc.java_time.day_of_week.friday = cljc.java_time.day_of_week.goog$module$goog$object.get(java.time.DayOfWeek,"FRIDAY");
cljc.java_time.day_of_week.wednesday = cljc.java_time.day_of_week.goog$module$goog$object.get(java.time.DayOfWeek,"WEDNESDAY");
cljc.java_time.day_of_week.sunday = cljc.java_time.day_of_week.goog$module$goog$object.get(java.time.DayOfWeek,"SUNDAY");
cljc.java_time.day_of_week.monday = cljc.java_time.day_of_week.goog$module$goog$object.get(java.time.DayOfWeek,"MONDAY");
cljc.java_time.day_of_week.tuesday = cljc.java_time.day_of_week.goog$module$goog$object.get(java.time.DayOfWeek,"TUESDAY");
cljc.java_time.day_of_week.range = (function cljc$java_time$day_of_week$range(this14159,java_time_temporal_TemporalField14160){
return this14159.range(java_time_temporal_TemporalField14160);
});
cljc.java_time.day_of_week.values = (function cljc$java_time$day_of_week$values(){
return cljs.core.js_invoke(java.time.DayOfWeek,"values");
});
cljc.java_time.day_of_week.value_of = (function cljc$java_time$day_of_week$value_of(var_args){
var G__74299 = arguments.length;
switch (G__74299) {
case 1:
return cljc.java_time.day_of_week.value_of.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljc.java_time.day_of_week.value_of.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.day_of_week.value_of.cljs$core$IFn$_invoke$arity$1 = (function (java_lang_String14161){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.DayOfWeek,"valueOf",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_String14161], 0));
}));

(cljc.java_time.day_of_week.value_of.cljs$core$IFn$_invoke$arity$2 = (function (java_lang_Class14162,java_lang_String14163){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.DayOfWeek,"valueOf",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_Class14162,java_lang_String14163], 0));
}));

(cljc.java_time.day_of_week.value_of.cljs$lang$maxFixedArity = 2);

cljc.java_time.day_of_week.of = (function cljc$java_time$day_of_week$of(int14164){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.DayOfWeek,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int14164], 0));
});
cljc.java_time.day_of_week.ordinal = (function cljc$java_time$day_of_week$ordinal(this14165){
return this14165.ordinal();
});
cljc.java_time.day_of_week.plus = (function cljc$java_time$day_of_week$plus(this14166,long14167){
return this14166.plus(long14167);
});
cljc.java_time.day_of_week.query = (function cljc$java_time$day_of_week$query(this14168,java_time_temporal_TemporalQuery14169){
return this14168.query(java_time_temporal_TemporalQuery14169);
});
cljc.java_time.day_of_week.to_string = (function cljc$java_time$day_of_week$to_string(this14170){
return this14170.toString();
});
cljc.java_time.day_of_week.minus = (function cljc$java_time$day_of_week$minus(this14171,long14172){
return this14171.minus(long14172);
});
cljc.java_time.day_of_week.get_display_name = (function cljc$java_time$day_of_week$get_display_name(this14173,java_time_format_TextStyle14174,java_util_Locale14175){
return this14173.displayName(java_time_format_TextStyle14174,java_util_Locale14175);
});
cljc.java_time.day_of_week.get_value = (function cljc$java_time$day_of_week$get_value(this14176){
return this14176.value();
});
cljc.java_time.day_of_week.name = (function cljc$java_time$day_of_week$name(this14177){
return this14177.name();
});
cljc.java_time.day_of_week.get_long = (function cljc$java_time$day_of_week$get_long(this14178,java_time_temporal_TemporalField14179){
return this14178.getLong(java_time_temporal_TemporalField14179);
});
cljc.java_time.day_of_week.get_declaring_class = (function cljc$java_time$day_of_week$get_declaring_class(this14180){
return this14180.declaringClass();
});
cljc.java_time.day_of_week.from = (function cljc$java_time$day_of_week$from(java_time_temporal_TemporalAccessor14181){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.DayOfWeek,"from",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_temporal_TemporalAccessor14181], 0));
});
cljc.java_time.day_of_week.is_supported = (function cljc$java_time$day_of_week$is_supported(this14182,java_time_temporal_TemporalField14183){
return this14182.isSupported(java_time_temporal_TemporalField14183);
});
cljc.java_time.day_of_week.hash_code = (function cljc$java_time$day_of_week$hash_code(this14184){
return this14184.hashCode();
});
cljc.java_time.day_of_week.adjust_into = (function cljc$java_time$day_of_week$adjust_into(this14185,java_time_temporal_Temporal14186){
return this14185.adjustInto(java_time_temporal_Temporal14186);
});
cljc.java_time.day_of_week.compare_to = (function cljc$java_time$day_of_week$compare_to(this14187,java_lang_Enum14188){
return this14187.compareTo(java_lang_Enum14188);
});
cljc.java_time.day_of_week.get = (function cljc$java_time$day_of_week$get(this14189,java_time_temporal_TemporalField14190){
return this14189.get(java_time_temporal_TemporalField14190);
});
cljc.java_time.day_of_week.equals = (function cljc$java_time$day_of_week$equals(this14191,java_lang_Object14192){
return this14191.equals(java_lang_Object14192);
});

//# sourceMappingURL=cljc.java_time.day_of_week.js.map
