goog.provide('cljc.java_time.offset_time');
goog.scope(function(){
  cljc.java_time.offset_time.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.offset_time.min = cljc.java_time.offset_time.goog$module$goog$object.get(java.time.OffsetTime,"MIN");
cljc.java_time.offset_time.max = cljc.java_time.offset_time.goog$module$goog$object.get(java.time.OffsetTime,"MAX");
cljc.java_time.offset_time.minus_minutes = (function cljc$java_time$offset_time$minus_minutes(this13578,long13579){
return this13578.minusMinutes(long13579);
});
cljc.java_time.offset_time.truncated_to = (function cljc$java_time$offset_time$truncated_to(this13580,java_time_temporal_TemporalUnit13581){
return this13580.truncatedTo(java_time_temporal_TemporalUnit13581);
});
cljc.java_time.offset_time.range = (function cljc$java_time$offset_time$range(this13582,java_time_temporal_TemporalField13583){
return this13582.range(java_time_temporal_TemporalField13583);
});
cljc.java_time.offset_time.get_hour = (function cljc$java_time$offset_time$get_hour(this13584){
return this13584.hour();
});
cljc.java_time.offset_time.minus_hours = (function cljc$java_time$offset_time$minus_hours(this13585,long13586){
return this13585.minusHours(long13586);
});
cljc.java_time.offset_time.of = (function cljc$java_time$offset_time$of(var_args){
var G__74285 = arguments.length;
switch (G__74285) {
case 2:
return cljc.java_time.offset_time.of.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 5:
return cljc.java_time.offset_time.of.cljs$core$IFn$_invoke$arity$5((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]),(arguments[(4)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.offset_time.of.cljs$core$IFn$_invoke$arity$2 = (function (java_time_LocalTime13587,java_time_ZoneOffset13588){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.OffsetTime,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_LocalTime13587,java_time_ZoneOffset13588], 0));
}));

(cljc.java_time.offset_time.of.cljs$core$IFn$_invoke$arity$5 = (function (int13589,int13590,int13591,int13592,java_time_ZoneOffset13593){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.OffsetTime,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int13589,int13590,int13591,int13592,java_time_ZoneOffset13593], 0));
}));

(cljc.java_time.offset_time.of.cljs$lang$maxFixedArity = 5);

cljc.java_time.offset_time.is_equal = (function cljc$java_time$offset_time$is_equal(this13594,java_time_OffsetTime13595){
return this13594.isEqual(java_time_OffsetTime13595);
});
cljc.java_time.offset_time.get_nano = (function cljc$java_time$offset_time$get_nano(this13596){
return this13596.nano();
});
cljc.java_time.offset_time.minus_seconds = (function cljc$java_time$offset_time$minus_seconds(this13597,long13598){
return this13597.minusSeconds(long13598);
});
cljc.java_time.offset_time.get_second = (function cljc$java_time$offset_time$get_second(this13599){
return this13599.second();
});
cljc.java_time.offset_time.plus_nanos = (function cljc$java_time$offset_time$plus_nanos(this13600,long13601){
return this13600.plusNanos(long13601);
});
cljc.java_time.offset_time.plus = (function cljc$java_time$offset_time$plus(var_args){
var G__74296 = arguments.length;
switch (G__74296) {
case 2:
return cljc.java_time.offset_time.plus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.offset_time.plus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.offset_time.plus.cljs$core$IFn$_invoke$arity$2 = (function (this13602,java_time_temporal_TemporalAmount13603){
return this13602.plus(java_time_temporal_TemporalAmount13603);
}));

(cljc.java_time.offset_time.plus.cljs$core$IFn$_invoke$arity$3 = (function (this13604,long13605,java_time_temporal_TemporalUnit13606){
return this13604.plus(long13605,java_time_temporal_TemporalUnit13606);
}));

(cljc.java_time.offset_time.plus.cljs$lang$maxFixedArity = 3);

cljc.java_time.offset_time.with_hour = (function cljc$java_time$offset_time$with_hour(this13607,int13608){
return this13607.withHour(int13608);
});
cljc.java_time.offset_time.with_minute = (function cljc$java_time$offset_time$with_minute(this13609,int13610){
return this13609.withMinute(int13610);
});
cljc.java_time.offset_time.plus_minutes = (function cljc$java_time$offset_time$plus_minutes(this13611,long13612){
return this13611.plusMinutes(long13612);
});
cljc.java_time.offset_time.query = (function cljc$java_time$offset_time$query(this13613,java_time_temporal_TemporalQuery13614){
return this13613.query(java_time_temporal_TemporalQuery13614);
});
cljc.java_time.offset_time.at_date = (function cljc$java_time$offset_time$at_date(this13615,java_time_LocalDate13616){
return this13615.atDate(java_time_LocalDate13616);
});
cljc.java_time.offset_time.with_offset_same_instant = (function cljc$java_time$offset_time$with_offset_same_instant(this13617,java_time_ZoneOffset13618){
return this13617.withOffsetSameInstant(java_time_ZoneOffset13618);
});
cljc.java_time.offset_time.to_string = (function cljc$java_time$offset_time$to_string(this13619){
return this13619.toString();
});
cljc.java_time.offset_time.is_before = (function cljc$java_time$offset_time$is_before(this13620,java_time_OffsetTime13621){
return this13620.isBefore(java_time_OffsetTime13621);
});
cljc.java_time.offset_time.minus = (function cljc$java_time$offset_time$minus(var_args){
var G__74303 = arguments.length;
switch (G__74303) {
case 3:
return cljc.java_time.offset_time.minus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 2:
return cljc.java_time.offset_time.minus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.offset_time.minus.cljs$core$IFn$_invoke$arity$3 = (function (this13622,long13623,java_time_temporal_TemporalUnit13624){
return this13622.minus(long13623,java_time_temporal_TemporalUnit13624);
}));

(cljc.java_time.offset_time.minus.cljs$core$IFn$_invoke$arity$2 = (function (this13625,java_time_temporal_TemporalAmount13626){
return this13625.minus(java_time_temporal_TemporalAmount13626);
}));

(cljc.java_time.offset_time.minus.cljs$lang$maxFixedArity = 3);

cljc.java_time.offset_time.plus_hours = (function cljc$java_time$offset_time$plus_hours(this13627,long13628){
return this13627.plusHours(long13628);
});
cljc.java_time.offset_time.to_local_time = (function cljc$java_time$offset_time$to_local_time(this13629){
return this13629.toLocalTime();
});
cljc.java_time.offset_time.get_long = (function cljc$java_time$offset_time$get_long(this13630,java_time_temporal_TemporalField13631){
return this13630.getLong(java_time_temporal_TemporalField13631);
});
cljc.java_time.offset_time.get_offset = (function cljc$java_time$offset_time$get_offset(this13632){
return this13632.offset();
});
cljc.java_time.offset_time.with_nano = (function cljc$java_time$offset_time$with_nano(this13633,int13634){
return this13633.withNano(int13634);
});
cljc.java_time.offset_time.until = (function cljc$java_time$offset_time$until(this13635,java_time_temporal_Temporal13636,java_time_temporal_TemporalUnit13637){
return this13635.until(java_time_temporal_Temporal13636,java_time_temporal_TemporalUnit13637);
});
cljc.java_time.offset_time.with_offset_same_local = (function cljc$java_time$offset_time$with_offset_same_local(this13638,java_time_ZoneOffset13639){
return this13638.withOffsetSameLocal(java_time_ZoneOffset13639);
});
cljc.java_time.offset_time.from = (function cljc$java_time$offset_time$from(java_time_temporal_TemporalAccessor13640){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.OffsetTime,"from",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_temporal_TemporalAccessor13640], 0));
});
cljc.java_time.offset_time.is_after = (function cljc$java_time$offset_time$is_after(this13641,java_time_OffsetTime13642){
return this13641.isAfter(java_time_OffsetTime13642);
});
cljc.java_time.offset_time.minus_nanos = (function cljc$java_time$offset_time$minus_nanos(this13643,long13644){
return this13643.minusNanos(long13644);
});
cljc.java_time.offset_time.is_supported = (function cljc$java_time$offset_time$is_supported(this13645,G__13646){
return this13645.isSupported(G__13646);
});
cljc.java_time.offset_time.parse = (function cljc$java_time$offset_time$parse(var_args){
var G__74315 = arguments.length;
switch (G__74315) {
case 1:
return cljc.java_time.offset_time.parse.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljc.java_time.offset_time.parse.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.offset_time.parse.cljs$core$IFn$_invoke$arity$1 = (function (java_lang_CharSequence13647){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.OffsetTime,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence13647], 0));
}));

(cljc.java_time.offset_time.parse.cljs$core$IFn$_invoke$arity$2 = (function (java_lang_CharSequence13648,java_time_format_DateTimeFormatter13649){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.OffsetTime,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence13648,java_time_format_DateTimeFormatter13649], 0));
}));

(cljc.java_time.offset_time.parse.cljs$lang$maxFixedArity = 2);

cljc.java_time.offset_time.with_second = (function cljc$java_time$offset_time$with_second(this13650,int13651){
return this13650.withSecond(int13651);
});
cljc.java_time.offset_time.get_minute = (function cljc$java_time$offset_time$get_minute(this13652){
return this13652.minute();
});
cljc.java_time.offset_time.hash_code = (function cljc$java_time$offset_time$hash_code(this13653){
return this13653.hashCode();
});
cljc.java_time.offset_time.adjust_into = (function cljc$java_time$offset_time$adjust_into(this13654,java_time_temporal_Temporal13655){
return this13654.adjustInto(java_time_temporal_Temporal13655);
});
cljc.java_time.offset_time.with$ = (function cljc$java_time$offset_time$with(var_args){
var G__74327 = arguments.length;
switch (G__74327) {
case 3:
return cljc.java_time.offset_time.with$.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 2:
return cljc.java_time.offset_time.with$.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.offset_time.with$.cljs$core$IFn$_invoke$arity$3 = (function (this13656,java_time_temporal_TemporalField13657,long13658){
return this13656.with(java_time_temporal_TemporalField13657,long13658);
}));

(cljc.java_time.offset_time.with$.cljs$core$IFn$_invoke$arity$2 = (function (this13659,java_time_temporal_TemporalAdjuster13660){
return this13659.with(java_time_temporal_TemporalAdjuster13660);
}));

(cljc.java_time.offset_time.with$.cljs$lang$maxFixedArity = 3);

cljc.java_time.offset_time.now = (function cljc$java_time$offset_time$now(var_args){
var G__74332 = arguments.length;
switch (G__74332) {
case 0:
return cljc.java_time.offset_time.now.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return cljc.java_time.offset_time.now.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.offset_time.now.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljs.core.js_invoke(java.time.OffsetTime,"now");
}));

(cljc.java_time.offset_time.now.cljs$core$IFn$_invoke$arity$1 = (function (G__13662){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.OffsetTime,"now",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([G__13662], 0));
}));

(cljc.java_time.offset_time.now.cljs$lang$maxFixedArity = 1);

cljc.java_time.offset_time.compare_to = (function cljc$java_time$offset_time$compare_to(this13663,java_time_OffsetTime13664){
return this13663.compareTo(java_time_OffsetTime13664);
});
cljc.java_time.offset_time.of_instant = (function cljc$java_time$offset_time$of_instant(java_time_Instant13665,java_time_ZoneId13666){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.OffsetTime,"ofInstant",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_Instant13665,java_time_ZoneId13666], 0));
});
cljc.java_time.offset_time.plus_seconds = (function cljc$java_time$offset_time$plus_seconds(this13667,long13668){
return this13667.plusSeconds(long13668);
});
cljc.java_time.offset_time.get = (function cljc$java_time$offset_time$get(this13669,java_time_temporal_TemporalField13670){
return this13669.get(java_time_temporal_TemporalField13670);
});
cljc.java_time.offset_time.equals = (function cljc$java_time$offset_time$equals(this13671,java_lang_Object13672){
return this13671.equals(java_lang_Object13672);
});
cljc.java_time.offset_time.format = (function cljc$java_time$offset_time$format(this13673,java_time_format_DateTimeFormatter13674){
return this13673.format(java_time_format_DateTimeFormatter13674);
});

//# sourceMappingURL=cljc.java_time.offset_time.js.map
