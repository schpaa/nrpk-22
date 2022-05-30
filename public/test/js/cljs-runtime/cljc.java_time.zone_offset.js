goog.provide('cljc.java_time.zone_offset');
goog.scope(function(){
  cljc.java_time.zone_offset.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.zone_offset.max = cljc.java_time.zone_offset.goog$module$goog$object.get(java.time.ZoneOffset,"MAX");
cljc.java_time.zone_offset.min = cljc.java_time.zone_offset.goog$module$goog$object.get(java.time.ZoneOffset,"MIN");
cljc.java_time.zone_offset.utc = cljc.java_time.zone_offset.goog$module$goog$object.get(java.time.ZoneOffset,"UTC");
cljc.java_time.zone_offset.get_available_zone_ids = (function cljc$java_time$zone_offset$get_available_zone_ids(){
return cljs.core.js_invoke(java.time.ZoneOffset,"getAvailableZoneIds");
});
cljc.java_time.zone_offset.range = (function cljc$java_time$zone_offset$range(this15065,java_time_temporal_TemporalField15066){
return this15065.range(java_time_temporal_TemporalField15066);
});
cljc.java_time.zone_offset.of_total_seconds = (function cljc$java_time$zone_offset$of_total_seconds(int15067){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZoneOffset,"ofTotalSeconds",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int15067], 0));
});
cljc.java_time.zone_offset.of = (function cljc$java_time$zone_offset$of(var_args){
var G__74258 = arguments.length;
switch (G__74258) {
case 1:
return cljc.java_time.zone_offset.of.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljc.java_time.zone_offset.of.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.zone_offset.of.cljs$core$IFn$_invoke$arity$1 = (function (G__15069){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZoneOffset,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([G__15069], 0));
}));

(cljc.java_time.zone_offset.of.cljs$core$IFn$_invoke$arity$2 = (function (java_lang_String15070,java_util_Map15071){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZoneOffset,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_String15070,java_util_Map15071], 0));
}));

(cljc.java_time.zone_offset.of.cljs$lang$maxFixedArity = 2);

cljc.java_time.zone_offset.of_offset = (function cljc$java_time$zone_offset$of_offset(java_lang_String15072,java_time_ZoneOffset15073){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZoneOffset,"ofOffset",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_String15072,java_time_ZoneOffset15073], 0));
});
cljc.java_time.zone_offset.query = (function cljc$java_time$zone_offset$query(this15074,java_time_temporal_TemporalQuery15075){
return this15074.query(java_time_temporal_TemporalQuery15075);
});
cljc.java_time.zone_offset.to_string = (function cljc$java_time$zone_offset$to_string(this15076){
return this15076.toString();
});
cljc.java_time.zone_offset.get_display_name = (function cljc$java_time$zone_offset$get_display_name(this15077,java_time_format_TextStyle15078,java_util_Locale15079){
return this15077.displayName(java_time_format_TextStyle15078,java_util_Locale15079);
});
cljc.java_time.zone_offset.get_long = (function cljc$java_time$zone_offset$get_long(this15080,java_time_temporal_TemporalField15081){
return this15080.getLong(java_time_temporal_TemporalField15081);
});
cljc.java_time.zone_offset.get_rules = (function cljc$java_time$zone_offset$get_rules(this15082){
return this15082.rules();
});
cljc.java_time.zone_offset.of_hours = (function cljc$java_time$zone_offset$of_hours(int15083){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZoneOffset,"ofHours",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int15083], 0));
});
cljc.java_time.zone_offset.get_id = (function cljc$java_time$zone_offset$get_id(this15084){
return this15084.id();
});
cljc.java_time.zone_offset.normalized = (function cljc$java_time$zone_offset$normalized(this15085){
return this15085.normalized();
});
cljc.java_time.zone_offset.system_default = (function cljc$java_time$zone_offset$system_default(){
return cljs.core.js_invoke(java.time.ZoneOffset,"systemDefault");
});
cljc.java_time.zone_offset.from = (function cljc$java_time$zone_offset$from(G__15087){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZoneOffset,"from",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([G__15087], 0));
});
cljc.java_time.zone_offset.of_hours_minutes_seconds = (function cljc$java_time$zone_offset$of_hours_minutes_seconds(int15088,int15089,int15090){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZoneOffset,"ofHoursMinutesSeconds",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int15088,int15089,int15090], 0));
});
cljc.java_time.zone_offset.is_supported = (function cljc$java_time$zone_offset$is_supported(this15091,java_time_temporal_TemporalField15092){
return this15091.isSupported(java_time_temporal_TemporalField15092);
});
cljc.java_time.zone_offset.hash_code = (function cljc$java_time$zone_offset$hash_code(this15093){
return this15093.hashCode();
});
cljc.java_time.zone_offset.get_total_seconds = (function cljc$java_time$zone_offset$get_total_seconds(this15094){
return this15094.totalSeconds();
});
cljc.java_time.zone_offset.adjust_into = (function cljc$java_time$zone_offset$adjust_into(this15095,java_time_temporal_Temporal15096){
return this15095.adjustInto(java_time_temporal_Temporal15096);
});
cljc.java_time.zone_offset.of_hours_minutes = (function cljc$java_time$zone_offset$of_hours_minutes(int15097,int15098){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.ZoneOffset,"ofHoursMinutes",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([int15097,int15098], 0));
});
cljc.java_time.zone_offset.compare_to = (function cljc$java_time$zone_offset$compare_to(this15099,java_time_ZoneOffset15100){
return this15099.compareTo(java_time_ZoneOffset15100);
});
cljc.java_time.zone_offset.get = (function cljc$java_time$zone_offset$get(this15101,java_time_temporal_TemporalField15102){
return this15101.get(java_time_temporal_TemporalField15102);
});
cljc.java_time.zone_offset.equals = (function cljc$java_time$zone_offset$equals(this15103,java_lang_Object15104){
return this15103.equals(java_lang_Object15104);
});

//# sourceMappingURL=cljc.java_time.zone_offset.js.map
