goog.provide('cljc.java_time.zone_id');
goog.scope(function(){
  cljc.java_time.zone_id.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.zone_id.short_ids = cljc.java_time.zone_id.goog$module$goog$object.get(java.time.ZoneId,"SHORT_IDS");
cljc.java_time.zone_id.get_available_zone_ids = (function cljc$java_time$zone_id$get_available_zone_ids(){
return cljs.core.js_invoke(java.time.ZoneId,"getAvailableZoneIds");
});
cljc.java_time.zone_id.of = (function cljc$java_time$zone_id$of(var_args){
var G__74210 = arguments.length;
switch (G__74210) {
case 2:
return cljc.java_time.zone_id.of.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 1:
return cljc.java_time.zone_id.of.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.zone_id.of.cljs$core$IFn$_invoke$arity$2 = (function (java_lang_String14109,java_util_Map14110){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZoneId,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_String14109,java_util_Map14110], 0));
}));

(cljc.java_time.zone_id.of.cljs$core$IFn$_invoke$arity$1 = (function (java_lang_String14111){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZoneId,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_String14111], 0));
}));

(cljc.java_time.zone_id.of.cljs$lang$maxFixedArity = 2);

cljc.java_time.zone_id.of_offset = (function cljc$java_time$zone_id$of_offset(java_lang_String14112,java_time_ZoneOffset14113){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZoneId,"ofOffset",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_String14112,java_time_ZoneOffset14113], 0));
});
cljc.java_time.zone_id.to_string = (function cljc$java_time$zone_id$to_string(this14114){
return this14114.toString();
});
cljc.java_time.zone_id.get_display_name = (function cljc$java_time$zone_id$get_display_name(this14115,java_time_format_TextStyle14116,java_util_Locale14117){
return this14115.displayName(java_time_format_TextStyle14116,java_util_Locale14117);
});
cljc.java_time.zone_id.get_rules = (function cljc$java_time$zone_id$get_rules(this14118){
return this14118.rules();
});
cljc.java_time.zone_id.get_id = (function cljc$java_time$zone_id$get_id(this14119){
return this14119.id();
});
cljc.java_time.zone_id.normalized = (function cljc$java_time$zone_id$normalized(this14120){
return this14120.normalized();
});
cljc.java_time.zone_id.system_default = (function cljc$java_time$zone_id$system_default(){
return cljs.core.js_invoke(java.time.ZoneId,"systemDefault");
});
cljc.java_time.zone_id.from = (function cljc$java_time$zone_id$from(java_time_temporal_TemporalAccessor14121){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZoneId,"from",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_temporal_TemporalAccessor14121], 0));
});
cljc.java_time.zone_id.hash_code = (function cljc$java_time$zone_id$hash_code(this14122){
return this14122.hashCode();
});
cljc.java_time.zone_id.equals = (function cljc$java_time$zone_id$equals(this14123,java_lang_Object14124){
return this14123.equals(java_lang_Object14124);
});

//# sourceMappingURL=cljc.java_time.zone_id.js.map
