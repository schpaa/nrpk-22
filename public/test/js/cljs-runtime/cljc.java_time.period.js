goog.provide('cljc.java_time.period');
goog.scope(function(){
  cljc.java_time.period.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.period.zero = cljc.java_time.period.goog$module$goog$object.get(java.time.Period,"ZERO");
cljc.java_time.period.get_months = (function cljc$java_time$period$get_months(this12608){
return this12608.months();
});
cljc.java_time.period.of_weeks = (function cljc$java_time$period$of_weeks(int12609){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Period,"ofWeeks",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int12609], 0));
});
cljc.java_time.period.of_days = (function cljc$java_time$period$of_days(int12610){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Period,"ofDays",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int12610], 0));
});
cljc.java_time.period.is_negative = (function cljc$java_time$period$is_negative(this12611){
return this12611.isNegative();
});
cljc.java_time.period.of = (function cljc$java_time$period$of(int12612,int12613,int12614){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Period,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int12612,int12613,int12614], 0));
});
cljc.java_time.period.is_zero = (function cljc$java_time$period$is_zero(this12615){
return this12615.isZero();
});
cljc.java_time.period.multiplied_by = (function cljc$java_time$period$multiplied_by(this12616,int12617){
return this12616.multipliedBy(int12617);
});
cljc.java_time.period.get_units = (function cljc$java_time$period$get_units(this12618){
return this12618.units();
});
cljc.java_time.period.with_days = (function cljc$java_time$period$with_days(this12619,int12620){
return this12619.withDays(int12620);
});
cljc.java_time.period.plus = (function cljc$java_time$period$plus(this12621,java_time_temporal_TemporalAmount12622){
return this12621.plus(java_time_temporal_TemporalAmount12622);
});
cljc.java_time.period.of_months = (function cljc$java_time$period$of_months(int12623){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Period,"ofMonths",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int12623], 0));
});
cljc.java_time.period.to_string = (function cljc$java_time$period$to_string(this12624){
return this12624.toString();
});
cljc.java_time.period.plus_months = (function cljc$java_time$period$plus_months(this12625,long12626){
return this12625.plusMonths(long12626);
});
cljc.java_time.period.minus_months = (function cljc$java_time$period$minus_months(this12627,long12628){
return this12627.minusMonths(long12628);
});
cljc.java_time.period.minus = (function cljc$java_time$period$minus(this12629,java_time_temporal_TemporalAmount12630){
return this12629.minus(java_time_temporal_TemporalAmount12630);
});
cljc.java_time.period.add_to = (function cljc$java_time$period$add_to(this12631,java_time_temporal_Temporal12632){
return this12631.addTo(java_time_temporal_Temporal12632);
});
cljc.java_time.period.to_total_months = (function cljc$java_time$period$to_total_months(this12633){
return this12633.toTotalMonths();
});
cljc.java_time.period.plus_days = (function cljc$java_time$period$plus_days(this12634,long12635){
return this12634.plusDays(long12635);
});
cljc.java_time.period.of_years = (function cljc$java_time$period$of_years(int12636){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Period,"ofYears",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int12636], 0));
});
cljc.java_time.period.get_days = (function cljc$java_time$period$get_days(this12637){
return this12637.days();
});
cljc.java_time.period.negated = (function cljc$java_time$period$negated(this12638){
return this12638.negated();
});
cljc.java_time.period.get_years = (function cljc$java_time$period$get_years(this12639){
return this12639.years();
});
cljc.java_time.period.with_years = (function cljc$java_time$period$with_years(this12640,int12641){
return this12640.withYears(int12641);
});
cljc.java_time.period.normalized = (function cljc$java_time$period$normalized(this12642){
return this12642.normalized();
});
cljc.java_time.period.with_months = (function cljc$java_time$period$with_months(this12643,int12644){
return this12643.withMonths(int12644);
});
cljc.java_time.period.between = (function cljc$java_time$period$between(java_time_LocalDate12645,java_time_LocalDate12646){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Period,"between",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_LocalDate12645,java_time_LocalDate12646], 0));
});
cljc.java_time.period.from = (function cljc$java_time$period$from(java_time_temporal_TemporalAmount12647){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Period,"from",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_temporal_TemporalAmount12647], 0));
});
cljc.java_time.period.minus_years = (function cljc$java_time$period$minus_years(this12648,long12649){
return this12648.minusYears(long12649);
});
cljc.java_time.period.get_chronology = (function cljc$java_time$period$get_chronology(this12650){
return this12650.chronology();
});
cljc.java_time.period.parse = (function cljc$java_time$period$parse(java_lang_CharSequence12651){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Period,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence12651], 0));
});
cljc.java_time.period.hash_code = (function cljc$java_time$period$hash_code(this12652){
return this12652.hashCode();
});
cljc.java_time.period.subtract_from = (function cljc$java_time$period$subtract_from(this12653,java_time_temporal_Temporal12654){
return this12653.subtractFrom(java_time_temporal_Temporal12654);
});
cljc.java_time.period.get = (function cljc$java_time$period$get(this12655,java_time_temporal_TemporalUnit12656){
return this12655.get(java_time_temporal_TemporalUnit12656);
});
cljc.java_time.period.equals = (function cljc$java_time$period$equals(this12657,java_lang_Object12658){
return this12657.equals(java_lang_Object12658);
});
cljc.java_time.period.plus_years = (function cljc$java_time$period$plus_years(this12659,long12660){
return this12659.plusYears(long12660);
});
cljc.java_time.period.minus_days = (function cljc$java_time$period$minus_days(this12661,long12662){
return this12661.minusDays(long12662);
});

//# sourceMappingURL=cljc.java_time.period.js.map
