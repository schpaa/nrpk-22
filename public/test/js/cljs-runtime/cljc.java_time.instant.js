goog.provide('cljc.java_time.instant');
goog.scope(function(){
  cljc.java_time.instant.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.instant.min = cljc.java_time.instant.goog$module$goog$object.get(java.time.Instant,"MIN");
cljc.java_time.instant.epoch = cljc.java_time.instant.goog$module$goog$object.get(java.time.Instant,"EPOCH");
cljc.java_time.instant.max = cljc.java_time.instant.goog$module$goog$object.get(java.time.Instant,"MAX");
cljc.java_time.instant.truncated_to = (function cljc$java_time$instant$truncated_to(this13743,java_time_temporal_TemporalUnit13744){
return this13743.truncatedTo(java_time_temporal_TemporalUnit13744);
});
cljc.java_time.instant.range = (function cljc$java_time$instant$range(this13745,java_time_temporal_TemporalField13746){
try{return this13745.range(java_time_temporal_TemporalField13746);
}catch (e74203){if((e74203 instanceof Error)){
var e__42539__auto__ = e74203;
throw (new Error(["Hi there! - It looks like you might be trying to do something with a java.time.Instant that would require it to be 'calendar-aware',\n   but in fact Instant has no facility with working with years, months, days etc. Think of it as just \n   a milli/nanosecond offset from the UNIX epoch.\n   \n   To get around this, consider converting the Instant to a \n   ZonedDateTime first or for formatting/parsing specifically, you might add a zone to your formatter.\n    see https://stackoverflow.com/a/27483371/1700930. \n    \n    You can disable these custom exceptions by setting -Dcljc.java-time.disable-helpful-exception-messages=true","\n original message ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"message")),"\n cause of exception: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"stack"))].join('')));
} else {
throw e74203;

}
}});
cljc.java_time.instant.of_epoch_second = (function cljc$java_time$instant$of_epoch_second(var_args){
var G__74205 = arguments.length;
switch (G__74205) {
case 1:
return cljc.java_time.instant.of_epoch_second.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljc.java_time.instant.of_epoch_second.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.instant.of_epoch_second.cljs$core$IFn$_invoke$arity$1 = (function (long13747){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Instant,"ofEpochSecond",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([long13747], 0));
}));

(cljc.java_time.instant.of_epoch_second.cljs$core$IFn$_invoke$arity$2 = (function (long13748,long13749){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Instant,"ofEpochSecond",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([long13748,long13749], 0));
}));

(cljc.java_time.instant.of_epoch_second.cljs$lang$maxFixedArity = 2);

cljc.java_time.instant.at_offset = (function cljc$java_time$instant$at_offset(this13750,java_time_ZoneOffset13751){
return this13750.atOffset(java_time_ZoneOffset13751);
});
cljc.java_time.instant.minus_millis = (function cljc$java_time$instant$minus_millis(this13752,long13753){
return this13752.minusMillis(long13753);
});
cljc.java_time.instant.get_nano = (function cljc$java_time$instant$get_nano(this13754){
return this13754.nano();
});
cljc.java_time.instant.plus_millis = (function cljc$java_time$instant$plus_millis(this13755,long13756){
return this13755.plusMillis(long13756);
});
cljc.java_time.instant.minus_seconds = (function cljc$java_time$instant$minus_seconds(this13757,long13758){
return this13757.minusSeconds(long13758);
});
cljc.java_time.instant.plus_nanos = (function cljc$java_time$instant$plus_nanos(this13759,long13760){
return this13759.plusNanos(long13760);
});
cljc.java_time.instant.plus = (function cljc$java_time$instant$plus(var_args){
var G__74214 = arguments.length;
switch (G__74214) {
case 2:
return cljc.java_time.instant.plus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.instant.plus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.instant.plus.cljs$core$IFn$_invoke$arity$2 = (function (this13761,java_time_temporal_TemporalAmount13762){
try{return this13761.plus(java_time_temporal_TemporalAmount13762);
}catch (e74215){if((e74215 instanceof Error)){
var e__42539__auto__ = e74215;
throw (new Error(["Hi there! - It looks like you might be trying to do something with a java.time.Instant that would require it to be 'calendar-aware',\n   but in fact Instant has no facility with working with years, months, days etc. Think of it as just \n   a milli/nanosecond offset from the UNIX epoch.\n   \n   To get around this, consider converting the Instant to a \n   ZonedDateTime first or for formatting/parsing specifically, you might add a zone to your formatter.\n    see https://stackoverflow.com/a/27483371/1700930. \n    \n    You can disable these custom exceptions by setting -Dcljc.java-time.disable-helpful-exception-messages=true","\n original message ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"message")),"\n cause of exception: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"stack"))].join('')));
} else {
throw e74215;

}
}}));

(cljc.java_time.instant.plus.cljs$core$IFn$_invoke$arity$3 = (function (this13763,long13764,java_time_temporal_TemporalUnit13765){
try{return this13763.plus(long13764,java_time_temporal_TemporalUnit13765);
}catch (e74216){if((e74216 instanceof Error)){
var e__42539__auto__ = e74216;
throw (new Error(["Hi there! - It looks like you might be trying to do something with a java.time.Instant that would require it to be 'calendar-aware',\n   but in fact Instant has no facility with working with years, months, days etc. Think of it as just \n   a milli/nanosecond offset from the UNIX epoch.\n   \n   To get around this, consider converting the Instant to a \n   ZonedDateTime first or for formatting/parsing specifically, you might add a zone to your formatter.\n    see https://stackoverflow.com/a/27483371/1700930. \n    \n    You can disable these custom exceptions by setting -Dcljc.java-time.disable-helpful-exception-messages=true","\n original message ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"message")),"\n cause of exception: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"stack"))].join('')));
} else {
throw e74216;

}
}}));

(cljc.java_time.instant.plus.cljs$lang$maxFixedArity = 3);

cljc.java_time.instant.query = (function cljc$java_time$instant$query(this13766,java_time_temporal_TemporalQuery13767){
return this13766.query(java_time_temporal_TemporalQuery13767);
});
cljc.java_time.instant.to_string = (function cljc$java_time$instant$to_string(this13768){
return this13768.toString();
});
cljc.java_time.instant.is_before = (function cljc$java_time$instant$is_before(this13769,java_time_Instant13770){
return this13769.isBefore(java_time_Instant13770);
});
cljc.java_time.instant.minus = (function cljc$java_time$instant$minus(var_args){
var G__74221 = arguments.length;
switch (G__74221) {
case 2:
return cljc.java_time.instant.minus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.instant.minus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.instant.minus.cljs$core$IFn$_invoke$arity$2 = (function (this13771,java_time_temporal_TemporalAmount13772){
try{return this13771.minus(java_time_temporal_TemporalAmount13772);
}catch (e74224){if((e74224 instanceof Error)){
var e__42539__auto__ = e74224;
throw (new Error(["Hi there! - It looks like you might be trying to do something with a java.time.Instant that would require it to be 'calendar-aware',\n   but in fact Instant has no facility with working with years, months, days etc. Think of it as just \n   a milli/nanosecond offset from the UNIX epoch.\n   \n   To get around this, consider converting the Instant to a \n   ZonedDateTime first or for formatting/parsing specifically, you might add a zone to your formatter.\n    see https://stackoverflow.com/a/27483371/1700930. \n    \n    You can disable these custom exceptions by setting -Dcljc.java-time.disable-helpful-exception-messages=true","\n original message ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"message")),"\n cause of exception: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"stack"))].join('')));
} else {
throw e74224;

}
}}));

(cljc.java_time.instant.minus.cljs$core$IFn$_invoke$arity$3 = (function (this13773,long13774,java_time_temporal_TemporalUnit13775){
try{return this13773.minus(long13774,java_time_temporal_TemporalUnit13775);
}catch (e74225){if((e74225 instanceof Error)){
var e__42539__auto__ = e74225;
throw (new Error(["Hi there! - It looks like you might be trying to do something with a java.time.Instant that would require it to be 'calendar-aware',\n   but in fact Instant has no facility with working with years, months, days etc. Think of it as just \n   a milli/nanosecond offset from the UNIX epoch.\n   \n   To get around this, consider converting the Instant to a \n   ZonedDateTime first or for formatting/parsing specifically, you might add a zone to your formatter.\n    see https://stackoverflow.com/a/27483371/1700930. \n    \n    You can disable these custom exceptions by setting -Dcljc.java-time.disable-helpful-exception-messages=true","\n original message ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"message")),"\n cause of exception: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"stack"))].join('')));
} else {
throw e74225;

}
}}));

(cljc.java_time.instant.minus.cljs$lang$maxFixedArity = 3);

cljc.java_time.instant.at_zone = (function cljc$java_time$instant$at_zone(this13776,java_time_ZoneId13777){
return this13776.atZone(java_time_ZoneId13777);
});
cljc.java_time.instant.of_epoch_milli = (function cljc$java_time$instant$of_epoch_milli(long13778){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Instant,"ofEpochMilli",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([long13778], 0));
});
cljc.java_time.instant.get_long = (function cljc$java_time$instant$get_long(this13779,java_time_temporal_TemporalField13780){
return this13779.getLong(java_time_temporal_TemporalField13780);
});
cljc.java_time.instant.until = (function cljc$java_time$instant$until(this13781,java_time_temporal_Temporal13782,java_time_temporal_TemporalUnit13783){
try{return this13781.until(java_time_temporal_Temporal13782,java_time_temporal_TemporalUnit13783);
}catch (e74228){if((e74228 instanceof Error)){
var e__42539__auto__ = e74228;
throw (new Error(["Hi there! - It looks like you might be trying to do something with a java.time.Instant that would require it to be 'calendar-aware',\n   but in fact Instant has no facility with working with years, months, days etc. Think of it as just \n   a milli/nanosecond offset from the UNIX epoch.\n   \n   To get around this, consider converting the Instant to a \n   ZonedDateTime first or for formatting/parsing specifically, you might add a zone to your formatter.\n    see https://stackoverflow.com/a/27483371/1700930. \n    \n    You can disable these custom exceptions by setting -Dcljc.java-time.disable-helpful-exception-messages=true","\n original message ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"message")),"\n cause of exception: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"stack"))].join('')));
} else {
throw e74228;

}
}});
cljc.java_time.instant.from = (function cljc$java_time$instant$from(java_time_temporal_TemporalAccessor13784){
try{return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Instant,"from",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_temporal_TemporalAccessor13784], 0));
}catch (e74229){if((e74229 instanceof Error)){
var e__42539__auto__ = e74229;
throw (new Error(["Hi there! - It looks like you might be trying to do something with a java.time.Instant that would require it to be 'calendar-aware',\n   but in fact Instant has no facility with working with years, months, days etc. Think of it as just \n   a milli/nanosecond offset from the UNIX epoch.\n   \n   To get around this, consider converting the Instant to a \n   ZonedDateTime first or for formatting/parsing specifically, you might add a zone to your formatter.\n    see https://stackoverflow.com/a/27483371/1700930. \n    \n    You can disable these custom exceptions by setting -Dcljc.java-time.disable-helpful-exception-messages=true","\n original message ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"message")),"\n cause of exception: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"stack"))].join('')));
} else {
throw e74229;

}
}});
cljc.java_time.instant.is_after = (function cljc$java_time$instant$is_after(this13785,java_time_Instant13786){
return this13785.isAfter(java_time_Instant13786);
});
cljc.java_time.instant.minus_nanos = (function cljc$java_time$instant$minus_nanos(this13787,long13788){
return this13787.minusNanos(long13788);
});
cljc.java_time.instant.is_supported = (function cljc$java_time$instant$is_supported(this13789,G__13790){
return this13789.isSupported(G__13790);
});
cljc.java_time.instant.parse = (function cljc$java_time$instant$parse(java_lang_CharSequence13791){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Instant,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence13791], 0));
});
cljc.java_time.instant.hash_code = (function cljc$java_time$instant$hash_code(this13792){
return this13792.hashCode();
});
cljc.java_time.instant.adjust_into = (function cljc$java_time$instant$adjust_into(this13793,java_time_temporal_Temporal13794){
try{return this13793.adjustInto(java_time_temporal_Temporal13794);
}catch (e74235){if((e74235 instanceof Error)){
var e__42539__auto__ = e74235;
throw (new Error(["Hi there! - It looks like you might be trying to do something with a java.time.Instant that would require it to be 'calendar-aware',\n   but in fact Instant has no facility with working with years, months, days etc. Think of it as just \n   a milli/nanosecond offset from the UNIX epoch.\n   \n   To get around this, consider converting the Instant to a \n   ZonedDateTime first or for formatting/parsing specifically, you might add a zone to your formatter.\n    see https://stackoverflow.com/a/27483371/1700930. \n    \n    You can disable these custom exceptions by setting -Dcljc.java-time.disable-helpful-exception-messages=true","\n original message ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"message")),"\n cause of exception: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"stack"))].join('')));
} else {
throw e74235;

}
}});
cljc.java_time.instant.with$ = (function cljc$java_time$instant$with(var_args){
var G__74247 = arguments.length;
switch (G__74247) {
case 2:
return cljc.java_time.instant.with$.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.instant.with$.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.instant.with$.cljs$core$IFn$_invoke$arity$2 = (function (this13795,java_time_temporal_TemporalAdjuster13796){
try{return this13795.with(java_time_temporal_TemporalAdjuster13796);
}catch (e74250){if((e74250 instanceof Error)){
var e__42539__auto__ = e74250;
throw (new Error(["Hi there! - It looks like you might be trying to do something with a java.time.Instant that would require it to be 'calendar-aware',\n   but in fact Instant has no facility with working with years, months, days etc. Think of it as just \n   a milli/nanosecond offset from the UNIX epoch.\n   \n   To get around this, consider converting the Instant to a \n   ZonedDateTime first or for formatting/parsing specifically, you might add a zone to your formatter.\n    see https://stackoverflow.com/a/27483371/1700930. \n    \n    You can disable these custom exceptions by setting -Dcljc.java-time.disable-helpful-exception-messages=true","\n original message ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"message")),"\n cause of exception: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"stack"))].join('')));
} else {
throw e74250;

}
}}));

(cljc.java_time.instant.with$.cljs$core$IFn$_invoke$arity$3 = (function (this13797,java_time_temporal_TemporalField13798,long13799){
try{return this13797.with(java_time_temporal_TemporalField13798,long13799);
}catch (e74251){if((e74251 instanceof Error)){
var e__42539__auto__ = e74251;
throw (new Error(["Hi there! - It looks like you might be trying to do something with a java.time.Instant that would require it to be 'calendar-aware',\n   but in fact Instant has no facility with working with years, months, days etc. Think of it as just \n   a milli/nanosecond offset from the UNIX epoch.\n   \n   To get around this, consider converting the Instant to a \n   ZonedDateTime first or for formatting/parsing specifically, you might add a zone to your formatter.\n    see https://stackoverflow.com/a/27483371/1700930. \n    \n    You can disable these custom exceptions by setting -Dcljc.java-time.disable-helpful-exception-messages=true","\n original message ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"message")),"\n cause of exception: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"stack"))].join('')));
} else {
throw e74251;

}
}}));

(cljc.java_time.instant.with$.cljs$lang$maxFixedArity = 3);

cljc.java_time.instant.now = (function cljc$java_time$instant$now(var_args){
var G__74254 = arguments.length;
switch (G__74254) {
case 0:
return cljc.java_time.instant.now.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return cljc.java_time.instant.now.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.instant.now.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljs.core.js_invoke(java.time.Instant,"now");
}));

(cljc.java_time.instant.now.cljs$core$IFn$_invoke$arity$1 = (function (java_time_Clock13800){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Instant,"now",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_Clock13800], 0));
}));

(cljc.java_time.instant.now.cljs$lang$maxFixedArity = 1);

cljc.java_time.instant.to_epoch_milli = (function cljc$java_time$instant$to_epoch_milli(this13801){
return this13801.toEpochMilli();
});
cljc.java_time.instant.get_epoch_second = (function cljc$java_time$instant$get_epoch_second(this13802){
return this13802.epochSecond();
});
cljc.java_time.instant.compare_to = (function cljc$java_time$instant$compare_to(this13803,java_time_Instant13804){
return this13803.compareTo(java_time_Instant13804);
});
cljc.java_time.instant.plus_seconds = (function cljc$java_time$instant$plus_seconds(this13805,long13806){
return this13805.plusSeconds(long13806);
});
cljc.java_time.instant.get = (function cljc$java_time$instant$get(this13807,java_time_temporal_TemporalField13808){
try{return this13807.get(java_time_temporal_TemporalField13808);
}catch (e74259){if((e74259 instanceof Error)){
var e__42539__auto__ = e74259;
throw (new Error(["Hi there! - It looks like you might be trying to do something with a java.time.Instant that would require it to be 'calendar-aware',\n   but in fact Instant has no facility with working with years, months, days etc. Think of it as just \n   a milli/nanosecond offset from the UNIX epoch.\n   \n   To get around this, consider converting the Instant to a \n   ZonedDateTime first or for formatting/parsing specifically, you might add a zone to your formatter.\n    see https://stackoverflow.com/a/27483371/1700930. \n    \n    You can disable these custom exceptions by setting -Dcljc.java-time.disable-helpful-exception-messages=true","\n original message ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"message")),"\n cause of exception: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.instant.goog$module$goog$object.get(e__42539__auto__,"stack"))].join('')));
} else {
throw e74259;

}
}});
cljc.java_time.instant.equals = (function cljc$java_time$instant$equals(this13809,java_lang_Object13810){
return this13809.equals(java_lang_Object13810);
});

//# sourceMappingURL=cljc.java_time.instant.js.map
