goog.provide('cljc.java_time.format.date_time_formatter');
goog.scope(function(){
  cljc.java_time.format.date_time_formatter.goog$module$goog$object = goog.module.get('goog.object');
});
cljc.java_time.format.date_time_formatter.iso_local_time = cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(java.time.format.DateTimeFormatter,"ISO_LOCAL_TIME");
cljc.java_time.format.date_time_formatter.iso_ordinal_date = cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(java.time.format.DateTimeFormatter,"ISO_ORDINAL_DATE");
cljc.java_time.format.date_time_formatter.iso_offset_date = cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(java.time.format.DateTimeFormatter,"ISO_OFFSET_DATE");
cljc.java_time.format.date_time_formatter.iso_time = cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(java.time.format.DateTimeFormatter,"ISO_TIME");
cljc.java_time.format.date_time_formatter.iso_local_date_time = cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(java.time.format.DateTimeFormatter,"ISO_LOCAL_DATE_TIME");
cljc.java_time.format.date_time_formatter.iso_instant = cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(java.time.format.DateTimeFormatter,"ISO_INSTANT");
cljc.java_time.format.date_time_formatter.rfc_1123_date_time = cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(java.time.format.DateTimeFormatter,"RFC_1123_DATE_TIME");
cljc.java_time.format.date_time_formatter.iso_date = cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(java.time.format.DateTimeFormatter,"ISO_DATE");
cljc.java_time.format.date_time_formatter.iso_week_date = cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(java.time.format.DateTimeFormatter,"ISO_WEEK_DATE");
cljc.java_time.format.date_time_formatter.iso_offset_time = cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(java.time.format.DateTimeFormatter,"ISO_OFFSET_TIME");
cljc.java_time.format.date_time_formatter.iso_local_date = cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(java.time.format.DateTimeFormatter,"ISO_LOCAL_DATE");
cljc.java_time.format.date_time_formatter.iso_zoned_date_time = cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(java.time.format.DateTimeFormatter,"ISO_ZONED_DATE_TIME");
cljc.java_time.format.date_time_formatter.iso_offset_date_time = cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(java.time.format.DateTimeFormatter,"ISO_OFFSET_DATE_TIME");
cljc.java_time.format.date_time_formatter.iso_date_time = cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(java.time.format.DateTimeFormatter,"ISO_DATE_TIME");
cljc.java_time.format.date_time_formatter.basic_iso_date = cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(java.time.format.DateTimeFormatter,"BASIC_ISO_DATE");
cljc.java_time.format.date_time_formatter.of_pattern = (function cljc$java_time$format$date_time_formatter$of_pattern(var_args){
var G__74391 = arguments.length;
switch (G__74391) {
case 1:
return cljc.java_time.format.date_time_formatter.of_pattern.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljc.java_time.format.date_time_formatter.of_pattern.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.format.date_time_formatter.of_pattern.cljs$core$IFn$_invoke$arity$1 = (function (java_lang_String15541){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.format.DateTimeFormatter,"ofPattern",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_String15541], 0));
}));

(cljc.java_time.format.date_time_formatter.of_pattern.cljs$core$IFn$_invoke$arity$2 = (function (java_lang_String15542,java_util_Locale15543){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.format.DateTimeFormatter,"ofPattern",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_lang_String15542,java_util_Locale15543], 0));
}));

(cljc.java_time.format.date_time_formatter.of_pattern.cljs$lang$maxFixedArity = 2);

cljc.java_time.format.date_time_formatter.parse_best = (function cljc$java_time$format$date_time_formatter$parse_best(this15544,java_lang_CharSequence15545,java_time_temporal_TemporalQuery_array15546){
return this15544.parseBest(java_lang_CharSequence15545,java_time_temporal_TemporalQuery_array15546);
});
cljc.java_time.format.date_time_formatter.format_to = (function cljc$java_time$format$date_time_formatter$format_to(this15547,java_time_temporal_TemporalAccessor15548,java_lang_Appendable15549){
return this15547.formatTo(java_time_temporal_TemporalAccessor15548,java_lang_Appendable15549);
});
cljc.java_time.format.date_time_formatter.get_decimal_style = (function cljc$java_time$format$date_time_formatter$get_decimal_style(this15550){
return this15550.decimalStyle();
});
cljc.java_time.format.date_time_formatter.with_chronology = (function cljc$java_time$format$date_time_formatter$with_chronology(this15551,java_time_chrono_Chronology15552){
return this15551.withChronology(java_time_chrono_Chronology15552);
});
cljc.java_time.format.date_time_formatter.get_resolver_style = (function cljc$java_time$format$date_time_formatter$get_resolver_style(this15553){
return this15553.resolverStyle();
});
cljc.java_time.format.date_time_formatter.with_decimal_style = (function cljc$java_time$format$date_time_formatter$with_decimal_style(this15554,java_time_format_DecimalStyle15555){
return this15554.withDecimalStyle(java_time_format_DecimalStyle15555);
});
cljc.java_time.format.date_time_formatter.get_locale = (function cljc$java_time$format$date_time_formatter$get_locale(this15556){
return this15556.locale();
});
cljc.java_time.format.date_time_formatter.to_string = (function cljc$java_time$format$date_time_formatter$to_string(this15557){
return this15557.toString();
});
cljc.java_time.format.date_time_formatter.parsed_leap_second = (function cljc$java_time$format$date_time_formatter$parsed_leap_second(){
return cljs.core.js_invoke(java.time.format.DateTimeFormatter,"parsedLeapSecond");
});
cljc.java_time.format.date_time_formatter.with_zone = (function cljc$java_time$format$date_time_formatter$with_zone(this15558,java_time_ZoneId15559){
return this15558.withZone(java_time_ZoneId15559);
});
cljc.java_time.format.date_time_formatter.parsed_excess_days = (function cljc$java_time$format$date_time_formatter$parsed_excess_days(){
return cljs.core.js_invoke(java.time.format.DateTimeFormatter,"parsedExcessDays");
});
cljc.java_time.format.date_time_formatter.get_zone = (function cljc$java_time$format$date_time_formatter$get_zone(this15560){
return this15560.zone();
});
cljc.java_time.format.date_time_formatter.of_localized_date_time = (function cljc$java_time$format$date_time_formatter$of_localized_date_time(var_args){
var G__74423 = arguments.length;
switch (G__74423) {
case 2:
return cljc.java_time.format.date_time_formatter.of_localized_date_time.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 1:
return cljc.java_time.format.date_time_formatter.of_localized_date_time.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.format.date_time_formatter.of_localized_date_time.cljs$core$IFn$_invoke$arity$2 = (function (java_time_format_FormatStyle15561,java_time_format_FormatStyle15562){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.format.DateTimeFormatter,"ofLocalizedDateTime",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_format_FormatStyle15561,java_time_format_FormatStyle15562], 0));
}));

(cljc.java_time.format.date_time_formatter.of_localized_date_time.cljs$core$IFn$_invoke$arity$1 = (function (java_time_format_FormatStyle15563){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.format.DateTimeFormatter,"ofLocalizedDateTime",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_format_FormatStyle15563], 0));
}));

(cljc.java_time.format.date_time_formatter.of_localized_date_time.cljs$lang$maxFixedArity = 2);

cljc.java_time.format.date_time_formatter.get_resolver_fields = (function cljc$java_time$format$date_time_formatter$get_resolver_fields(this15564){
return this15564.resolverFields();
});
cljc.java_time.format.date_time_formatter.get_chronology = (function cljc$java_time$format$date_time_formatter$get_chronology(this15565){
return this15565.chronology();
});
cljc.java_time.format.date_time_formatter.parse = (function cljc$java_time$format$date_time_formatter$parse(var_args){
var G__74425 = arguments.length;
switch (G__74425) {
case 3:
return cljc.java_time.format.date_time_formatter.parse.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 2:
return cljc.java_time.format.date_time_formatter.parse.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.format.date_time_formatter.parse.cljs$core$IFn$_invoke$arity$3 = (function (this15566,G__15567,G__15568){
return this15566.parse(G__15567,G__15568);
}));

(cljc.java_time.format.date_time_formatter.parse.cljs$core$IFn$_invoke$arity$2 = (function (this15569,java_lang_CharSequence15570){
return this15569.parse(java_lang_CharSequence15570);
}));

(cljc.java_time.format.date_time_formatter.parse.cljs$lang$maxFixedArity = 3);

cljc.java_time.format.date_time_formatter.with_locale = (function cljc$java_time$format$date_time_formatter$with_locale(this15571,java_util_Locale15572){
return this15571.withLocale(java_util_Locale15572);
});
cljc.java_time.format.date_time_formatter.with_resolver_fields = (function cljc$java_time$format$date_time_formatter$with_resolver_fields(this15573,G__15574){
return this15573.withResolverFields(G__15574);
});
cljc.java_time.format.date_time_formatter.parse_unresolved = (function cljc$java_time$format$date_time_formatter$parse_unresolved(this15575,java_lang_CharSequence15576,java_text_ParsePosition15577){
return this15575.parseUnresolved(java_lang_CharSequence15576,java_text_ParsePosition15577);
});
cljc.java_time.format.date_time_formatter.of_localized_time = (function cljc$java_time$format$date_time_formatter$of_localized_time(java_time_format_FormatStyle15578){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.format.DateTimeFormatter,"ofLocalizedTime",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_format_FormatStyle15578], 0));
});
cljc.java_time.format.date_time_formatter.of_localized_date = (function cljc$java_time$format$date_time_formatter$of_localized_date(java_time_format_FormatStyle15579){
return cljs.core.js_invoke.cljs$core$IFn$_invoke$arity$variadic(java.time.format.DateTimeFormatter,"ofLocalizedDate",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([java_time_format_FormatStyle15579], 0));
});
cljc.java_time.format.date_time_formatter.format = (function cljc$java_time$format$date_time_formatter$format(this15580,java_time_temporal_TemporalAccessor15581){
try{return this15580.format(java_time_temporal_TemporalAccessor15581);
}catch (e74440){if((e74440 instanceof Error)){
var e__42539__auto__ = e74440;
throw (new Error(["Hi there! - It looks like you might be trying to do something with a java.time.Instant that would require it to be 'calendar-aware',\n   but in fact Instant has no facility with working with years, months, days etc. Think of it as just \n   a milli/nanosecond offset from the UNIX epoch.\n   \n   To get around this, consider converting the Instant to a \n   ZonedDateTime first or for formatting/parsing specifically, you might add a zone to your formatter.\n    see https://stackoverflow.com/a/27483371/1700930. \n    \n    You can disable these custom exceptions by setting -Dcljc.java-time.disable-helpful-exception-messages=true","\n original message ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(e__42539__auto__,"message")),"\n cause of exception: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljc.java_time.format.date_time_formatter.goog$module$goog$object.get(e__42539__auto__,"stack"))].join('')));
} else {
throw e74440;

}
}});
cljc.java_time.format.date_time_formatter.to_format = (function cljc$java_time$format$date_time_formatter$to_format(var_args){
var G__74455 = arguments.length;
switch (G__74455) {
case 1:
return cljc.java_time.format.date_time_formatter.to_format.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljc.java_time.format.date_time_formatter.to_format.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljc.java_time.format.date_time_formatter.to_format.cljs$core$IFn$_invoke$arity$1 = (function (this15582){
return this15582.toFormat();
}));

(cljc.java_time.format.date_time_formatter.to_format.cljs$core$IFn$_invoke$arity$2 = (function (this15583,java_time_temporal_TemporalQuery15584){
return this15583.toFormat(java_time_temporal_TemporalQuery15584);
}));

(cljc.java_time.format.date_time_formatter.to_format.cljs$lang$maxFixedArity = 2);

cljc.java_time.format.date_time_formatter.with_resolver_style = (function cljc$java_time$format$date_time_formatter$with_resolver_style(this15585,java_time_format_ResolverStyle15586){
return this15585.withResolverStyle(java_time_format_ResolverStyle15586);
});

//# sourceMappingURL=cljc.java_time.format.date_time_formatter.js.map
