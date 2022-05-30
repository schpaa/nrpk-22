goog.provide('cljc.java_time.duration');
goog.scope(function(){
  cljc.java_time.duration.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.duration.zero = cljc.java_time.duration.goog$module$goog$object.get(java.time.Duration,"ZERO");
cljc.java_time.duration.minus_minutes = (function cljc$java_time$duration$minus_minutes(this14637,long14638){
return this14637.minusMinutes(long14638);
});
cljc.java_time.duration.to_nanos = (function cljc$java_time$duration$to_nanos(this14639){
return this14639.toNanos();
});
cljc.java_time.duration.minus_millis = (function cljc$java_time$duration$minus_millis(this14640,long14641){
return this14640.minusMillis(long14641);
});
cljc.java_time.duration.minus_hours = (function cljc$java_time$duration$minus_hours(this14642,long14643){
return this14642.minusHours(long14643);
});
cljc.java_time.duration.of_days = (function cljc$java_time$duration$of_days(long14644){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Duration,"ofDays",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([long14644], 0));
});
cljc.java_time.duration.is_negative = (function cljc$java_time$duration$is_negative(this14645){
return this14645.isNegative();
});
cljc.java_time.duration.of = (function cljc$java_time$duration$of(long14646,java_time_temporal_TemporalUnit14647){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Duration,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([long14646,java_time_temporal_TemporalUnit14647], 0));
});
cljc.java_time.duration.is_zero = (function cljc$java_time$duration$is_zero(this14648){
return this14648.isZero();
});
cljc.java_time.duration.multiplied_by = (function cljc$java_time$duration$multiplied_by(this14649,long14650){
return this14649.multipliedBy(long14650);
});
cljc.java_time.duration.with_nanos = (function cljc$java_time$duration$with_nanos(this14651,int14652){
return this14651.withNanos(int14652);
});
cljc.java_time.duration.get_units = (function cljc$java_time$duration$get_units(this14653){
return this14653.units();
});
cljc.java_time.duration.get_nano = (function cljc$java_time$duration$get_nano(this14654){
return this14654.nano();
});
cljc.java_time.duration.plus_millis = (function cljc$java_time$duration$plus_millis(this14655,long14656){
return this14655.plusMillis(long14656);
});
cljc.java_time.duration.to_minutes = (function cljc$java_time$duration$to_minutes(this14657){
return this14657.toMinutes();
});
cljc.java_time.duration.minus_seconds = (function cljc$java_time$duration$minus_seconds(this14658,long14659){
return this14658.minusSeconds(long14659);
});
cljc.java_time.duration.plus_nanos = (function cljc$java_time$duration$plus_nanos(this14660,long14661){
return this14660.plusNanos(long14661);
});
cljc.java_time.duration.plus = (function cljc$java_time$duration$plus(var_args){
var G__74370 = arguments.length;
switch (G__74370) {
case 3:
return cljc.java_time.duration.plus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 2:
return cljc.java_time.duration.plus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.duration.plus.cljs$core$IFn$_invoke$arity$3 = (function (this14662,long14663,java_time_temporal_TemporalUnit14664){
return this14662.plus(long14663,java_time_temporal_TemporalUnit14664);
}));

(cljc.java_time.duration.plus.cljs$core$IFn$_invoke$arity$2 = (function (this14665,java_time_Duration14666){
return this14665.plus(java_time_Duration14666);
}));

(cljc.java_time.duration.plus.cljs$lang$maxFixedArity = 3);

cljc.java_time.duration.divided_by = (function cljc$java_time$duration$divided_by(this14667,long14668){
return this14667.dividedBy(long14668);
});
cljc.java_time.duration.plus_minutes = (function cljc$java_time$duration$plus_minutes(this14669,long14670){
return this14669.plusMinutes(long14670);
});
cljc.java_time.duration.to_string = (function cljc$java_time$duration$to_string(this14671){
return this14671.toString();
});
cljc.java_time.duration.minus = (function cljc$java_time$duration$minus(var_args){
var G__74372 = arguments.length;
switch (G__74372) {
case 3:
return cljc.java_time.duration.minus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 2:
return cljc.java_time.duration.minus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.duration.minus.cljs$core$IFn$_invoke$arity$3 = (function (this14672,long14673,java_time_temporal_TemporalUnit14674){
return this14672.minus(long14673,java_time_temporal_TemporalUnit14674);
}));

(cljc.java_time.duration.minus.cljs$core$IFn$_invoke$arity$2 = (function (this14675,java_time_Duration14676){
return this14675.minus(java_time_Duration14676);
}));

(cljc.java_time.duration.minus.cljs$lang$maxFixedArity = 3);

cljc.java_time.duration.add_to = (function cljc$java_time$duration$add_to(this14677,java_time_temporal_Temporal14678){
return this14677.addTo(java_time_temporal_Temporal14678);
});
cljc.java_time.duration.plus_hours = (function cljc$java_time$duration$plus_hours(this14679,long14680){
return this14679.plusHours(long14680);
});
cljc.java_time.duration.plus_days = (function cljc$java_time$duration$plus_days(this14681,long14682){
return this14681.plusDays(long14682);
});
cljc.java_time.duration.of_hours = (function cljc$java_time$duration$of_hours(long14683){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Duration,"ofHours",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([long14683], 0));
});
cljc.java_time.duration.to_millis = (function cljc$java_time$duration$to_millis(this14684){
return this14684.toMillis();
});
cljc.java_time.duration.to_hours = (function cljc$java_time$duration$to_hours(this14685){
return this14685.toHours();
});
cljc.java_time.duration.of_nanos = (function cljc$java_time$duration$of_nanos(long14686){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Duration,"ofNanos",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([long14686], 0));
});
cljc.java_time.duration.of_millis = (function cljc$java_time$duration$of_millis(long14687){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Duration,"ofMillis",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([long14687], 0));
});
cljc.java_time.duration.negated = (function cljc$java_time$duration$negated(this14688){
return this14688.negated();
});
cljc.java_time.duration.abs = (function cljc$java_time$duration$abs(this14689){
return this14689.abs();
});
cljc.java_time.duration.between = (function cljc$java_time$duration$between(java_time_temporal_Temporal14690,java_time_temporal_Temporal14691){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Duration,"between",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_temporal_Temporal14690,java_time_temporal_Temporal14691], 0));
});
cljc.java_time.duration.get_seconds = (function cljc$java_time$duration$get_seconds(this14692){
return this14692.seconds();
});
cljc.java_time.duration.from = (function cljc$java_time$duration$from(java_time_temporal_TemporalAmount14693){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Duration,"from",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_temporal_TemporalAmount14693], 0));
});
cljc.java_time.duration.minus_nanos = (function cljc$java_time$duration$minus_nanos(this14694,long14695){
return this14694.minusNanos(long14695);
});
cljc.java_time.duration.parse = (function cljc$java_time$duration$parse(java_lang_CharSequence14696){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Duration,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence14696], 0));
});
cljc.java_time.duration.hash_code = (function cljc$java_time$duration$hash_code(this14697){
return this14697.hashCode();
});
cljc.java_time.duration.with_seconds = (function cljc$java_time$duration$with_seconds(this14698,long14699){
return this14698.withSeconds(long14699);
});
cljc.java_time.duration.of_minutes = (function cljc$java_time$duration$of_minutes(long14700){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Duration,"ofMinutes",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([long14700], 0));
});
cljc.java_time.duration.subtract_from = (function cljc$java_time$duration$subtract_from(this14701,java_time_temporal_Temporal14702){
return this14701.subtractFrom(java_time_temporal_Temporal14702);
});
cljc.java_time.duration.compare_to = (function cljc$java_time$duration$compare_to(this14703,java_time_Duration14704){
return this14703.compareTo(java_time_Duration14704);
});
cljc.java_time.duration.plus_seconds = (function cljc$java_time$duration$plus_seconds(this14705,long14706){
return this14705.plusSeconds(long14706);
});
cljc.java_time.duration.get = (function cljc$java_time$duration$get(this14707,java_time_temporal_TemporalUnit14708){
return this14707.get(java_time_temporal_TemporalUnit14708);
});
cljc.java_time.duration.equals = (function cljc$java_time$duration$equals(this14709,java_lang_Object14710){
return this14709.equals(java_lang_Object14710);
});
cljc.java_time.duration.of_seconds = (function cljc$java_time$duration$of_seconds(var_args){
var G__74383 = arguments.length;
switch (G__74383) {
case 2:
return cljc.java_time.duration.of_seconds.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 1:
return cljc.java_time.duration.of_seconds.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.duration.of_seconds.cljs$core$IFn$_invoke$arity$2 = (function (long14711,long14712){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Duration,"ofSeconds",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([long14711,long14712], 0));
}));

(cljc.java_time.duration.of_seconds.cljs$core$IFn$_invoke$arity$1 = (function (long14713){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Duration,"ofSeconds",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([long14713], 0));
}));

(cljc.java_time.duration.of_seconds.cljs$lang$maxFixedArity = 2);

cljc.java_time.duration.minus_days = (function cljc$java_time$duration$minus_days(this14714,long14715){
return this14714.minusDays(long14715);
});
cljc.java_time.duration.to_days = (function cljc$java_time$duration$to_days(this14716){
return this14716.toDays();
});

//# sourceMappingURL=cljc.java_time.duration.js.map
