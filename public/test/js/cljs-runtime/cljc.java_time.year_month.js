goog.provide('cljc.java_time.year_month');
goog.scope(function(){
  cljc.java_time.year_month.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.year_month.length_of_year = (function cljc$java_time$year_month$length_of_year(this14917){
return this14917.lengthOfYear();
});
cljc.java_time.year_month.range = (function cljc$java_time$year_month$range(this14918,java_time_temporal_TemporalField14919){
return this14918.range(java_time_temporal_TemporalField14919);
});
cljc.java_time.year_month.is_valid_day = (function cljc$java_time$year_month$is_valid_day(this14920,int14921){
return this14920.isValidDay(int14921);
});
cljc.java_time.year_month.of = (function cljc$java_time$year_month$of(G__14923,G__14924){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.YearMonth,"of",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([G__14923,G__14924], 0));
});
cljc.java_time.year_month.with_month = (function cljc$java_time$year_month$with_month(this14925,int14926){
return this14925.withMonth(int14926);
});
cljc.java_time.year_month.at_day = (function cljc$java_time$year_month$at_day(this14927,int14928){
return this14927.atDay(int14928);
});
cljc.java_time.year_month.get_year = (function cljc$java_time$year_month$get_year(this14929){
return this14929.year();
});
cljc.java_time.year_month.plus = (function cljc$java_time$year_month$plus(var_args){
var G__74289 = arguments.length;
switch (G__74289) {
case 2:
return cljc.java_time.year_month.plus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.year_month.plus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.year_month.plus.cljs$core$IFn$_invoke$arity$2 = (function (this14930,java_time_temporal_TemporalAmount14931){
return this14930.plus(java_time_temporal_TemporalAmount14931);
}));

(cljc.java_time.year_month.plus.cljs$core$IFn$_invoke$arity$3 = (function (this14932,long14933,java_time_temporal_TemporalUnit14934){
return this14932.plus(long14933,java_time_temporal_TemporalUnit14934);
}));

(cljc.java_time.year_month.plus.cljs$lang$maxFixedArity = 3);

cljc.java_time.year_month.is_leap_year = (function cljc$java_time$year_month$is_leap_year(this14935){
return this14935.isLeapYear();
});
cljc.java_time.year_month.query = (function cljc$java_time$year_month$query(this14936,java_time_temporal_TemporalQuery14937){
return this14936.query(java_time_temporal_TemporalQuery14937);
});
cljc.java_time.year_month.to_string = (function cljc$java_time$year_month$to_string(this14938){
return this14938.toString();
});
cljc.java_time.year_month.plus_months = (function cljc$java_time$year_month$plus_months(this14939,long14940){
return this14939.plusMonths(long14940);
});
cljc.java_time.year_month.is_before = (function cljc$java_time$year_month$is_before(this14941,java_time_YearMonth14942){
return this14941.isBefore(java_time_YearMonth14942);
});
cljc.java_time.year_month.minus_months = (function cljc$java_time$year_month$minus_months(this14943,long14944){
return this14943.minusMonths(long14944);
});
cljc.java_time.year_month.minus = (function cljc$java_time$year_month$minus(var_args){
var G__74305 = arguments.length;
switch (G__74305) {
case 2:
return cljc.java_time.year_month.minus.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.year_month.minus.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.year_month.minus.cljs$core$IFn$_invoke$arity$2 = (function (this14945,java_time_temporal_TemporalAmount14946){
return this14945.minus(java_time_temporal_TemporalAmount14946);
}));

(cljc.java_time.year_month.minus.cljs$core$IFn$_invoke$arity$3 = (function (this14947,long14948,java_time_temporal_TemporalUnit14949){
return this14947.minus(long14948,java_time_temporal_TemporalUnit14949);
}));

(cljc.java_time.year_month.minus.cljs$lang$maxFixedArity = 3);

cljc.java_time.year_month.get_long = (function cljc$java_time$year_month$get_long(this14950,java_time_temporal_TemporalField14951){
return this14950.getLong(java_time_temporal_TemporalField14951);
});
cljc.java_time.year_month.with_year = (function cljc$java_time$year_month$with_year(this14952,int14953){
return this14952.withYear(int14953);
});
cljc.java_time.year_month.at_end_of_month = (function cljc$java_time$year_month$at_end_of_month(this14954){
return this14954.atEndOfMonth();
});
cljc.java_time.year_month.length_of_month = (function cljc$java_time$year_month$length_of_month(this14955){
return this14955.lengthOfMonth();
});
cljc.java_time.year_month.until = (function cljc$java_time$year_month$until(this14956,java_time_temporal_Temporal14957,java_time_temporal_TemporalUnit14958){
return this14956.until(java_time_temporal_Temporal14957,java_time_temporal_TemporalUnit14958);
});
cljc.java_time.year_month.from = (function cljc$java_time$year_month$from(java_time_temporal_TemporalAccessor14959){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.YearMonth,"from",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_temporal_TemporalAccessor14959], 0));
});
cljc.java_time.year_month.is_after = (function cljc$java_time$year_month$is_after(this14960,java_time_YearMonth14961){
return this14960.isAfter(java_time_YearMonth14961);
});
cljc.java_time.year_month.is_supported = (function cljc$java_time$year_month$is_supported(this14962,G__14963){
return this14962.isSupported(G__14963);
});
cljc.java_time.year_month.minus_years = (function cljc$java_time$year_month$minus_years(this14964,long14965){
return this14964.minusYears(long14965);
});
cljc.java_time.year_month.parse = (function cljc$java_time$year_month$parse(var_args){
var G__74320 = arguments.length;
switch (G__74320) {
case 1:
return cljc.java_time.year_month.parse.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljc.java_time.year_month.parse.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.year_month.parse.cljs$core$IFn$_invoke$arity$1 = (function (java_lang_CharSequence14966){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.YearMonth,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence14966], 0));
}));

(cljc.java_time.year_month.parse.cljs$core$IFn$_invoke$arity$2 = (function (java_lang_CharSequence14967,java_time_format_DateTimeFormatter14968){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.YearMonth,"parse",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_CharSequence14967,java_time_format_DateTimeFormatter14968], 0));
}));

(cljc.java_time.year_month.parse.cljs$lang$maxFixedArity = 2);

cljc.java_time.year_month.hash_code = (function cljc$java_time$year_month$hash_code(this14969){
return this14969.hashCode();
});
cljc.java_time.year_month.adjust_into = (function cljc$java_time$year_month$adjust_into(this14970,java_time_temporal_Temporal14971){
return this14970.adjustInto(java_time_temporal_Temporal14971);
});
cljc.java_time.year_month.with$ = (function cljc$java_time$year_month$with(var_args){
var G__74325 = arguments.length;
switch (G__74325) {
case 2:
return cljc.java_time.year_month.with$.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljc.java_time.year_month.with$.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.year_month.with$.cljs$core$IFn$_invoke$arity$2 = (function (this14972,java_time_temporal_TemporalAdjuster14973){
return this14972.with(java_time_temporal_TemporalAdjuster14973);
}));

(cljc.java_time.year_month.with$.cljs$core$IFn$_invoke$arity$3 = (function (this14974,java_time_temporal_TemporalField14975,long14976){
return this14974.with(java_time_temporal_TemporalField14975,long14976);
}));

(cljc.java_time.year_month.with$.cljs$lang$maxFixedArity = 3);

cljc.java_time.year_month.now = (function cljc$java_time$year_month$now(var_args){
var G__74330 = arguments.length;
switch (G__74330) {
case 0:
return cljc.java_time.year_month.now.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return cljc.java_time.year_month.now.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.year_month.now.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljs.core.js_invoke(java.time.YearMonth,"now");
}));

(cljc.java_time.year_month.now.cljs$core$IFn$_invoke$arity$1 = (function (G__14978){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.YearMonth,"now",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([G__14978], 0));
}));

(cljc.java_time.year_month.now.cljs$lang$maxFixedArity = 1);

cljc.java_time.year_month.get_month_value = (function cljc$java_time$year_month$get_month_value(this14979){
return this14979.monthValue();
});
cljc.java_time.year_month.compare_to = (function cljc$java_time$year_month$compare_to(this14980,java_time_YearMonth14981){
return this14980.compareTo(java_time_YearMonth14981);
});
cljc.java_time.year_month.get_month = (function cljc$java_time$year_month$get_month(this14982){
return this14982.month();
});
cljc.java_time.year_month.get = (function cljc$java_time$year_month$get(this14983,java_time_temporal_TemporalField14984){
return this14983.get(java_time_temporal_TemporalField14984);
});
cljc.java_time.year_month.equals = (function cljc$java_time$year_month$equals(this14985,java_lang_Object14986){
return this14985.equals(java_lang_Object14986);
});
cljc.java_time.year_month.format = (function cljc$java_time$year_month$format(this14987,java_time_format_DateTimeFormatter14988){
return this14987.format(java_time_format_DateTimeFormatter14988);
});
cljc.java_time.year_month.plus_years = (function cljc$java_time$year_month$plus_years(this14989,long14990){
return this14989.plusYears(long14990);
});

//# sourceMappingURL=cljc.java_time.year_month.js.map
