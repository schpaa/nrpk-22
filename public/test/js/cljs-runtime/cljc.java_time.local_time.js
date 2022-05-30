goog.provide('cljc.java_time.local_time');
goog.scope(function(){
  cljc.java_time.local_time.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.local_time.max = cljc.java_time.local_time.goog$module$goog$object.get(java.time.LocalTime,"MAX");
cljc.java_time.local_time.noon = cljc.java_time.local_time.goog$module$goog$object.get(java.time.LocalTime,"NOON");
cljc.java_time.local_time.midnight = cljc.java_time.local_time.goog$module$goog$object.get(java.time.LocalTime,"MIDNIGHT");
cljc.java_time.local_time.min = cljc.java_time.local_time.goog$module$goog$object.get(java.time.LocalTime,"MIN");
cljc.java_time.local_time.minus_minutes = (function cljc$java_time$local_time$minus_minutes(this14288,long14289){
return this14288.minusMinutes(long14289);
});
cljc.java_time.local_time.truncated_to = (function cljc$java_time$local_time$truncated_to(this14290,java_time_temporal_TemporalUnit14291){
return this14290.truncatedTo(java_time_temporal_TemporalUnit14291);
});
cljc.java_time.local_time.range = (function cljc$java_time$local_time$range(this14292,java_time_temporal_TemporalField14293){
return this14292.range(java_time_temporal_TemporalField14293);
});
cljc.java_time.local_time.get_hour = (function cljc$java_time$local_time$get_hour(this14294){
return this14294.hour();
});
cljc.java_time.local_time.at_offset = (function cljc$java_time$local_time$at_offset(this14295,java_time_ZoneOffset14296){
return this14295.atOffset(java_time_ZoneOffset14296);
});
cljc.java_time.local_time.minus_hours = (function cljc$java_time$local_time$minus_hours(this14297,long14298){
return this14297.minusHours(long14298);
});
cljc.java_time.local_time.of = (function cljc$java_time$local_time$of(var_args){
var G__74201 = arguments.length;
switch (G__74201) {
case 3:
return cljc.java_time.local_time.of.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 4:
return cljc.java_time.local_time.of.cljs$core$IFn$_invoke$arity$4((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]));

break;
case 2:
return cljc.java_time.local_time.of.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_time.of.cljs$core$IFn$_invoke$arity$3 = (function (int14299,int14300,int14301){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalTime,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int14299,int14300,int14301], 0));
}));

(cljc.java_time.local_time.of.cljs$core$IFn$_invoke$arity$4 = (function (int14302,int14303,int14304,int14305){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalTime,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int14302,int14303,int14304,int14305], 0));
}));

(cljc.java_time.local_time.of.cljs$core$IFn$_invoke$arity$2 = (function (int14306,int14307){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalTime,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int14306,int14307], 0));
}));

(cljc.java_time.local_time.of.cljs$lang$maxFixedArity = 4);

cljc.java_time.local_time.get_nano = (function cljc$java_time$local_time$get_nano(this14308){
return this14308.nano();
});
cljc.java_time.local_time.minus_seconds = (function cljc$java_time$local_time$minus_seconds(this14309,long14310){
return this14309.minusSeconds(long14310);
});
cljc.java_time.local_time.get_second = (function cljc$java_time$local_time$get_second(this14311){
return this14311.second();
});
cljc.java_time.local_time.plus_nanos = (function cljc$java_time$local_time$plus_nanos(this14312,long14313){
return this14312.plusNanos(long14313);
});
cljc.java_time.local_time.plus = (function cljc$java_time$local_time$plus(var_args){
var G__74207 = arguments.length;
switch (G__74207) {
case 2:
return cljc.java_time.local_time.plus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.local_time.plus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_time.plus.cljs$core$IFn$_invoke$arity$2 = (function (this14314,java_time_temporal_TemporalAmount14315){
return this14314.plus(java_time_temporal_TemporalAmount14315);
}));

(cljc.java_time.local_time.plus.cljs$core$IFn$_invoke$arity$3 = (function (this14316,long14317,java_time_temporal_TemporalUnit14318){
return this14316.plus(long14317,java_time_temporal_TemporalUnit14318);
}));

(cljc.java_time.local_time.plus.cljs$lang$maxFixedArity = 3);

cljc.java_time.local_time.with_hour = (function cljc$java_time$local_time$with_hour(this14319,int14320){
return this14319.withHour(int14320);
});
cljc.java_time.local_time.with_minute = (function cljc$java_time$local_time$with_minute(this14321,int14322){
return this14321.withMinute(int14322);
});
cljc.java_time.local_time.plus_minutes = (function cljc$java_time$local_time$plus_minutes(this14323,long14324){
return this14323.plusMinutes(long14324);
});
cljc.java_time.local_time.query = (function cljc$java_time$local_time$query(this14325,java_time_temporal_TemporalQuery14326){
return this14325.query(java_time_temporal_TemporalQuery14326);
});
cljc.java_time.local_time.at_date = (function cljc$java_time$local_time$at_date(this14327,java_time_LocalDate14328){
return this14327.atDate(java_time_LocalDate14328);
});
cljc.java_time.local_time.to_string = (function cljc$java_time$local_time$to_string(this14329){
return this14329.toString();
});
cljc.java_time.local_time.is_before = (function cljc$java_time$local_time$is_before(this14330,java_time_LocalTime14331){
return this14330.isBefore(java_time_LocalTime14331);
});
cljc.java_time.local_time.minus = (function cljc$java_time$local_time$minus(var_args){
var G__74222 = arguments.length;
switch (G__74222) {
case 3:
return cljc.java_time.local_time.minus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 2:
return cljc.java_time.local_time.minus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_time.minus.cljs$core$IFn$_invoke$arity$3 = (function (this14332,long14333,java_time_temporal_TemporalUnit14334){
return this14332.minus(long14333,java_time_temporal_TemporalUnit14334);
}));

(cljc.java_time.local_time.minus.cljs$core$IFn$_invoke$arity$2 = (function (this14335,java_time_temporal_TemporalAmount14336){
return this14335.minus(java_time_temporal_TemporalAmount14336);
}));

(cljc.java_time.local_time.minus.cljs$lang$maxFixedArity = 3);

cljc.java_time.local_time.plus_hours = (function cljc$java_time$local_time$plus_hours(this14337,long14338){
return this14337.plusHours(long14338);
});
cljc.java_time.local_time.to_second_of_day = (function cljc$java_time$local_time$to_second_of_day(this14339){
return this14339.toSecondOfDay();
});
cljc.java_time.local_time.get_long = (function cljc$java_time$local_time$get_long(this14340,java_time_temporal_TemporalField14341){
return this14340.getLong(java_time_temporal_TemporalField14341);
});
cljc.java_time.local_time.with_nano = (function cljc$java_time$local_time$with_nano(this14342,int14343){
return this14342.withNano(int14343);
});
cljc.java_time.local_time.until = (function cljc$java_time$local_time$until(this14344,java_time_temporal_Temporal14345,java_time_temporal_TemporalUnit14346){
return this14344.until(java_time_temporal_Temporal14345,java_time_temporal_TemporalUnit14346);
});
cljc.java_time.local_time.of_nano_of_day = (function cljc$java_time$local_time$of_nano_of_day(long14347){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalTime,"ofNanoOfDay",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([long14347], 0));
});
cljc.java_time.local_time.from = (function cljc$java_time$local_time$from(java_time_temporal_TemporalAccessor14348){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalTime,"from",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_temporal_TemporalAccessor14348], 0));
});
cljc.java_time.local_time.is_after = (function cljc$java_time$local_time$is_after(this14349,java_time_LocalTime14350){
return this14349.isAfter(java_time_LocalTime14350);
});
cljc.java_time.local_time.minus_nanos = (function cljc$java_time$local_time$minus_nanos(this14351,long14352){
return this14351.minusNanos(long14352);
});
cljc.java_time.local_time.is_supported = (function cljc$java_time$local_time$is_supported(this14353,G__14354){
return this14353.isSupported(G__14354);
});
cljc.java_time.local_time.parse = (function cljc$java_time$local_time$parse(var_args){
var G__74233 = arguments.length;
switch (G__74233) {
case 1:
return cljc.java_time.local_time.parse.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljc.java_time.local_time.parse.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_time.parse.cljs$core$IFn$_invoke$arity$1 = (function (java_lang_CharSequence14355){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalTime,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence14355], 0));
}));

(cljc.java_time.local_time.parse.cljs$core$IFn$_invoke$arity$2 = (function (java_lang_CharSequence14356,java_time_format_DateTimeFormatter14357){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalTime,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence14356,java_time_format_DateTimeFormatter14357], 0));
}));

(cljc.java_time.local_time.parse.cljs$lang$maxFixedArity = 2);

cljc.java_time.local_time.with_second = (function cljc$java_time$local_time$with_second(this14358,int14359){
return this14358.withSecond(int14359);
});
cljc.java_time.local_time.get_minute = (function cljc$java_time$local_time$get_minute(this14360){
return this14360.minute();
});
cljc.java_time.local_time.hash_code = (function cljc$java_time$local_time$hash_code(this14361){
return this14361.hashCode();
});
cljc.java_time.local_time.adjust_into = (function cljc$java_time$local_time$adjust_into(this14362,java_time_temporal_Temporal14363){
return this14362.adjustInto(java_time_temporal_Temporal14363);
});
cljc.java_time.local_time.with$ = (function cljc$java_time$local_time$with(var_args){
var G__74241 = arguments.length;
switch (G__74241) {
case 2:
return cljc.java_time.local_time.with$.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.local_time.with$.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_time.with$.cljs$core$IFn$_invoke$arity$2 = (function (this14364,java_time_temporal_TemporalAdjuster14365){
return this14364.with(java_time_temporal_TemporalAdjuster14365);
}));

(cljc.java_time.local_time.with$.cljs$core$IFn$_invoke$arity$3 = (function (this14366,java_time_temporal_TemporalField14367,long14368){
return this14366.with(java_time_temporal_TemporalField14367,long14368);
}));

(cljc.java_time.local_time.with$.cljs$lang$maxFixedArity = 3);

cljc.java_time.local_time.now = (function cljc$java_time$local_time$now(var_args){
var G__74245 = arguments.length;
switch (G__74245) {
case 0:
return cljc.java_time.local_time.now.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return cljc.java_time.local_time.now.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.local_time.now.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljs.core.js_invoke(java.time.LocalTime,"now");
}));

(cljc.java_time.local_time.now.cljs$core$IFn$_invoke$arity$1 = (function (G__14370){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalTime,"now",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([G__14370], 0));
}));

(cljc.java_time.local_time.now.cljs$lang$maxFixedArity = 1);

cljc.java_time.local_time.compare_to = (function cljc$java_time$local_time$compare_to(this14371,java_time_LocalTime14372){
return this14371.compareTo(java_time_LocalTime14372);
});
cljc.java_time.local_time.to_nano_of_day = (function cljc$java_time$local_time$to_nano_of_day(this14373){
return this14373.toNanoOfDay();
});
cljc.java_time.local_time.plus_seconds = (function cljc$java_time$local_time$plus_seconds(this14374,long14375){
return this14374.plusSeconds(long14375);
});
cljc.java_time.local_time.get = (function cljc$java_time$local_time$get(this14376,java_time_temporal_TemporalField14377){
return this14376.get(java_time_temporal_TemporalField14377);
});
cljc.java_time.local_time.of_second_of_day = (function cljc$java_time$local_time$of_second_of_day(long14378){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.LocalTime,"ofSecondOfDay",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([long14378], 0));
});
cljc.java_time.local_time.equals = (function cljc$java_time$local_time$equals(this14379,java_lang_Object14380){
return this14379.equals(java_lang_Object14380);
});
cljc.java_time.local_time.format = (function cljc$java_time$local_time$format(this14381,java_time_format_DateTimeFormatter14382){
return this14381.format(java_time_format_DateTimeFormatter14382);
});

//# sourceMappingURL=cljc.java_time.local_time.js.map
