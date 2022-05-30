goog.provide('cljc.java_time.clock');
goog.scope(function(){
  cljc.java_time.clock.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.clock.tick = (function cljc$java_time$clock$tick(java_time_Clock15008,java_time_Duration15009){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Clock,"tick",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_Clock15008,java_time_Duration15009], 0));
});
cljc.java_time.clock.offset = (function cljc$java_time$clock$offset(java_time_Clock15010,java_time_Duration15011){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Clock,"offset",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_Clock15010,java_time_Duration15011], 0));
});
cljc.java_time.clock.system_utc = (function cljc$java_time$clock$system_utc(){
return cljs.core.js_invoke(java.time.Clock,"systemUTC");
});
cljc.java_time.clock.system_default_zone = (function cljc$java_time$clock$system_default_zone(){
return cljs.core.js_invoke(java.time.Clock,"systemDefaultZone");
});
cljc.java_time.clock.fixed = (function cljc$java_time$clock$fixed(java_time_Instant15012,java_time_ZoneId15013){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Clock,"fixed",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_Instant15012,java_time_ZoneId15013], 0));
});
cljc.java_time.clock.tick_minutes = (function cljc$java_time$clock$tick_minutes(java_time_ZoneId15014){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Clock,"tickMinutes",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_ZoneId15014], 0));
});
cljc.java_time.clock.tick_seconds = (function cljc$java_time$clock$tick_seconds(java_time_ZoneId15015){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Clock,"tickSeconds",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_ZoneId15015], 0));
});
cljc.java_time.clock.millis = (function cljc$java_time$clock$millis(this15016){
return this15016.millis();
});
cljc.java_time.clock.with_zone = (function cljc$java_time$clock$with_zone(this15017,java_time_ZoneId15018){
return this15017.withZone(java_time_ZoneId15018);
});
cljc.java_time.clock.get_zone = (function cljc$java_time$clock$get_zone(this15019){
return this15019.zone();
});
cljc.java_time.clock.hash_code = (function cljc$java_time$clock$hash_code(this15020){
return this15020.hashCode();
});
cljc.java_time.clock.system = (function cljc$java_time$clock$system(java_time_ZoneId15021){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.Clock,"system",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_ZoneId15021], 0));
});
cljc.java_time.clock.instant = (function cljc$java_time$clock$instant(this15022){
return this15022.instant();
});
cljc.java_time.clock.equals = (function cljc$java_time$clock$equals(this15023,java_lang_Object15024){
return this15023.equals(java_lang_Object15024);
});

//# sourceMappingURL=cljc.java_time.clock.js.map
