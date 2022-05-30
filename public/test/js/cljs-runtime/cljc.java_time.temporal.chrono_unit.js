goog.provide('cljc.java_time.temporal.chrono_unit');
goog.scope(function(){
  cljc.java_time.temporal.chrono_unit.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.temporal.chrono_unit.millis = cljc.java_time.temporal.chrono_unit.goog$module$goog$object.get(java.time.temporal.ChronoUnit,"MILLIS");
cljc.java_time.temporal.chrono_unit.minutes = cljc.java_time.temporal.chrono_unit.goog$module$goog$object.get(java.time.temporal.ChronoUnit,"MINUTES");
cljc.java_time.temporal.chrono_unit.micros = cljc.java_time.temporal.chrono_unit.goog$module$goog$object.get(java.time.temporal.ChronoUnit,"MICROS");
cljc.java_time.temporal.chrono_unit.half_days = cljc.java_time.temporal.chrono_unit.goog$module$goog$object.get(java.time.temporal.ChronoUnit,"HALF_DAYS");
cljc.java_time.temporal.chrono_unit.millennia = cljc.java_time.temporal.chrono_unit.goog$module$goog$object.get(java.time.temporal.ChronoUnit,"MILLENNIA");
cljc.java_time.temporal.chrono_unit.years = cljc.java_time.temporal.chrono_unit.goog$module$goog$object.get(java.time.temporal.ChronoUnit,"YEARS");
cljc.java_time.temporal.chrono_unit.decades = cljc.java_time.temporal.chrono_unit.goog$module$goog$object.get(java.time.temporal.ChronoUnit,"DECADES");
cljc.java_time.temporal.chrono_unit.days = cljc.java_time.temporal.chrono_unit.goog$module$goog$object.get(java.time.temporal.ChronoUnit,"DAYS");
cljc.java_time.temporal.chrono_unit.centuries = cljc.java_time.temporal.chrono_unit.goog$module$goog$object.get(java.time.temporal.ChronoUnit,"CENTURIES");
cljc.java_time.temporal.chrono_unit.weeks = cljc.java_time.temporal.chrono_unit.goog$module$goog$object.get(java.time.temporal.ChronoUnit,"WEEKS");
cljc.java_time.temporal.chrono_unit.hours = cljc.java_time.temporal.chrono_unit.goog$module$goog$object.get(java.time.temporal.ChronoUnit,"HOURS");
cljc.java_time.temporal.chrono_unit.eras = cljc.java_time.temporal.chrono_unit.goog$module$goog$object.get(java.time.temporal.ChronoUnit,"ERAS");
cljc.java_time.temporal.chrono_unit.seconds = cljc.java_time.temporal.chrono_unit.goog$module$goog$object.get(java.time.temporal.ChronoUnit,"SECONDS");
cljc.java_time.temporal.chrono_unit.months = cljc.java_time.temporal.chrono_unit.goog$module$goog$object.get(java.time.temporal.ChronoUnit,"MONTHS");
cljc.java_time.temporal.chrono_unit.nanos = cljc.java_time.temporal.chrono_unit.goog$module$goog$object.get(java.time.temporal.ChronoUnit,"NANOS");
cljc.java_time.temporal.chrono_unit.forever = cljc.java_time.temporal.chrono_unit.goog$module$goog$object.get(java.time.temporal.ChronoUnit,"FOREVER");
cljc.java_time.temporal.chrono_unit.values = (function cljc$java_time$temporal$chrono_unit$values(){
return cljs.core.js_invoke(java.time.temporal.ChronoUnit,"values");
});
cljc.java_time.temporal.chrono_unit.value_of = (function cljc$java_time$temporal$chrono_unit$value_of(var_args){
var G__74388 = arguments.length;
switch (G__74388) {
case 1:
return cljc.java_time.temporal.chrono_unit.value_of.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljc.java_time.temporal.chrono_unit.value_of.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.temporal.chrono_unit.value_of.cljs$core$IFn$_invoke$arity$1 = (function (java_lang_String15217){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.temporal.ChronoUnit,"valueOf",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_String15217], 0));
}));

(cljc.java_time.temporal.chrono_unit.value_of.cljs$core$IFn$_invoke$arity$2 = (function (java_lang_Class15218,java_lang_String15219){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.temporal.ChronoUnit,"valueOf",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_Class15218,java_lang_String15219], 0));
}));

(cljc.java_time.temporal.chrono_unit.value_of.cljs$lang$maxFixedArity = 2);

cljc.java_time.temporal.chrono_unit.ordinal = (function cljc$java_time$temporal$chrono_unit$ordinal(this15220){
return this15220.ordinal();
});
cljc.java_time.temporal.chrono_unit.is_duration_estimated = (function cljc$java_time$temporal$chrono_unit$is_duration_estimated(this15221){
return this15221.isDurationEstimated();
});
cljc.java_time.temporal.chrono_unit.to_string = (function cljc$java_time$temporal$chrono_unit$to_string(this15222){
return this15222.toString();
});
cljc.java_time.temporal.chrono_unit.is_date_based = (function cljc$java_time$temporal$chrono_unit$is_date_based(this15223){
return this15223.isDateBased();
});
cljc.java_time.temporal.chrono_unit.add_to = (function cljc$java_time$temporal$chrono_unit$add_to(this15224,java_time_temporal_Temporal15225,long15226){
return this15224.addTo(java_time_temporal_Temporal15225,long15226);
});
cljc.java_time.temporal.chrono_unit.name = (function cljc$java_time$temporal$chrono_unit$name(this15227){
return this15227.name();
});
cljc.java_time.temporal.chrono_unit.is_supported_by = (function cljc$java_time$temporal$chrono_unit$is_supported_by(this15228,java_time_temporal_Temporal15229){
return this15228.isSupportedBy(java_time_temporal_Temporal15229);
});
cljc.java_time.temporal.chrono_unit.get_declaring_class = (function cljc$java_time$temporal$chrono_unit$get_declaring_class(this15230){
return this15230.declaringClass();
});
cljc.java_time.temporal.chrono_unit.between = (function cljc$java_time$temporal$chrono_unit$between(this15231,java_time_temporal_Temporal15232,java_time_temporal_Temporal15233){
return this15231.between(java_time_temporal_Temporal15232,java_time_temporal_Temporal15233);
});
cljc.java_time.temporal.chrono_unit.hash_code = (function cljc$java_time$temporal$chrono_unit$hash_code(this15234){
return this15234.hashCode();
});
cljc.java_time.temporal.chrono_unit.compare_to = (function cljc$java_time$temporal$chrono_unit$compare_to(this15235,java_lang_Enum15236){
return this15235.compareTo(java_lang_Enum15236);
});
cljc.java_time.temporal.chrono_unit.get_duration = (function cljc$java_time$temporal$chrono_unit$get_duration(this15237){
return this15237.duration();
});
cljc.java_time.temporal.chrono_unit.equals = (function cljc$java_time$temporal$chrono_unit$equals(this15238,java_lang_Object15239){
return this15238.equals(java_lang_Object15239);
});
cljc.java_time.temporal.chrono_unit.is_time_based = (function cljc$java_time$temporal$chrono_unit$is_time_based(this15240){
return this15240.isTimeBased();
});

//# sourceMappingURL=cljc.java_time.temporal.chrono_unit.js.map
