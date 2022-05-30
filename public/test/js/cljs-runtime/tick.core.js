goog.provide('tick.core');
goog.scope(function(){
  tick.core.goog$module$goog$object = goog.module.get('goog.object');
});
time_literals.read_write.print_time_literals_clj_BANG_();

time_literals.read_write.print_time_literals_cljs_BANG_();
tick.core.parse_int = (function tick$core$parse_int(x){
return Number(x);
});
(tick.protocols.IParseable["string"] = true);

(tick.protocols.parse["string"] = (function (s){
var pred__74484 = cljs.core.re_matches;
var expr__74485 = s;
var temp__5751__auto__ = (function (){var G__74491 = /(\d{1,2})\s*(am|pm)/;
var G__74492 = expr__74485;
return (pred__74484.cljs$core$IFn$_invoke$arity$2 ? pred__74484.cljs$core$IFn$_invoke$arity$2(G__74491,G__74492) : pred__74484.call(null,G__74491,G__74492));
})();
if(cljs.core.truth_(temp__5751__auto__)){
var p__5474__auto__ = temp__5751__auto__;
return (function (p__74494){
var vec__74495 = p__74494;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74495,(0),null);
var h = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74495,(1),null);
var ap = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74495,(2),null);
return cljc.java_time.local_time.of.cljs$core$IFn$_invoke$arity$2((function (){var G__74498 = tick.core.parse_int(h);
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2("pm",ap)){
return (G__74498 + (12));
} else {
return G__74498;
}
})(),(0));
})(p__5474__auto__);
} else {
var temp__5751__auto____$1 = (function (){var G__74499 = /(\d{1,2})/;
var G__74500 = expr__74485;
return (pred__74484.cljs$core$IFn$_invoke$arity$2 ? pred__74484.cljs$core$IFn$_invoke$arity$2(G__74499,G__74500) : pred__74484.call(null,G__74499,G__74500));
})();
if(cljs.core.truth_(temp__5751__auto____$1)){
var p__5474__auto__ = temp__5751__auto____$1;
return (function (p__74501){
var vec__74503 = p__74501;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74503,(0),null);
var h = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74503,(1),null);
return cljc.java_time.local_time.of.cljs$core$IFn$_invoke$arity$2(tick.core.parse_int(h),(0));
})(p__5474__auto__);
} else {
var temp__5751__auto____$2 = (function (){var G__74506 = /\d{2}:\d{2}\S*/;
var G__74507 = expr__74485;
return (pred__74484.cljs$core$IFn$_invoke$arity$2 ? pred__74484.cljs$core$IFn$_invoke$arity$2(G__74506,G__74507) : pred__74484.call(null,G__74506,G__74507));
})();
if(cljs.core.truth_(temp__5751__auto____$2)){
var p__5474__auto__ = temp__5751__auto____$2;
return (function (s__$1){
return cljc.java_time.local_time.parse.cljs$core$IFn$_invoke$arity$1(s__$1);
})(p__5474__auto__);
} else {
var temp__5751__auto____$3 = (function (){var G__74510 = /(\d{1,2}):(\d{2})/;
var G__74513 = expr__74485;
return (pred__74484.cljs$core$IFn$_invoke$arity$2 ? pred__74484.cljs$core$IFn$_invoke$arity$2(G__74510,G__74513) : pred__74484.call(null,G__74510,G__74513));
})();
if(cljs.core.truth_(temp__5751__auto____$3)){
var p__5474__auto__ = temp__5751__auto____$3;
return (function (p__74515){
var vec__74516 = p__74515;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74516,(0),null);
var h = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74516,(1),null);
var m = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74516,(2),null);
return cljc.java_time.local_time.of.cljs$core$IFn$_invoke$arity$2(tick.core.parse_int(h),tick.core.parse_int(m));
})(p__5474__auto__);
} else {
var temp__5751__auto____$4 = (function (){var G__74519 = /\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(?:\.\d{1,9})?Z/;
var G__74520 = expr__74485;
return (pred__74484.cljs$core$IFn$_invoke$arity$2 ? pred__74484.cljs$core$IFn$_invoke$arity$2(G__74519,G__74520) : pred__74484.call(null,G__74519,G__74520));
})();
if(cljs.core.truth_(temp__5751__auto____$4)){
var p__5474__auto__ = temp__5751__auto____$4;
return (function (s__$1){
return cljc.java_time.instant.parse(s__$1);
})(p__5474__auto__);
} else {
var temp__5751__auto____$5 = (function (){var G__74521 = /\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(?:\.\d{1,9})?[+-]\d{2}:\d{2}/;
var G__74522 = expr__74485;
return (pred__74484.cljs$core$IFn$_invoke$arity$2 ? pred__74484.cljs$core$IFn$_invoke$arity$2(G__74521,G__74522) : pred__74484.call(null,G__74521,G__74522));
})();
if(cljs.core.truth_(temp__5751__auto____$5)){
var p__5474__auto__ = temp__5751__auto____$5;
return (function (s__$1){
return cljc.java_time.offset_date_time.parse.cljs$core$IFn$_invoke$arity$1(s__$1);
})(p__5474__auto__);
} else {
var temp__5751__auto____$6 = (function (){var G__74523 = /\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(?:\.\d{1,9})?(?:[+-]\d{2}:\d{2}|Z)\[\w+\/\w+\]/;
var G__74524 = expr__74485;
return (pred__74484.cljs$core$IFn$_invoke$arity$2 ? pred__74484.cljs$core$IFn$_invoke$arity$2(G__74523,G__74524) : pred__74484.call(null,G__74523,G__74524));
})();
if(cljs.core.truth_(temp__5751__auto____$6)){
var p__5474__auto__ = temp__5751__auto____$6;
return (function (s__$1){
return cljc.java_time.zoned_date_time.parse.cljs$core$IFn$_invoke$arity$1(s__$1);
})(p__5474__auto__);
} else {
var temp__5751__auto____$7 = (function (){var G__74525 = /\d{4}-\d{2}-\d{2}T\S*/;
var G__74526 = expr__74485;
return (pred__74484.cljs$core$IFn$_invoke$arity$2 ? pred__74484.cljs$core$IFn$_invoke$arity$2(G__74525,G__74526) : pred__74484.call(null,G__74525,G__74526));
})();
if(cljs.core.truth_(temp__5751__auto____$7)){
var p__5474__auto__ = temp__5751__auto____$7;
return (function (s__$1){
return cljc.java_time.local_date_time.parse.cljs$core$IFn$_invoke$arity$1(s__$1);
})(p__5474__auto__);
} else {
var temp__5751__auto____$8 = (function (){var G__74528 = /\d{4}-\d{2}-\d{2}/;
var G__74529 = expr__74485;
return (pred__74484.cljs$core$IFn$_invoke$arity$2 ? pred__74484.cljs$core$IFn$_invoke$arity$2(G__74528,G__74529) : pred__74484.call(null,G__74528,G__74529));
})();
if(cljs.core.truth_(temp__5751__auto____$8)){
var p__5474__auto__ = temp__5751__auto____$8;
return (function (s__$1){
return cljc.java_time.local_date.parse.cljs$core$IFn$_invoke$arity$1(s__$1);
})(p__5474__auto__);
} else {
var temp__5751__auto____$9 = (function (){var G__74530 = /\d{4}-\d{2}/;
var G__74531 = expr__74485;
return (pred__74484.cljs$core$IFn$_invoke$arity$2 ? pred__74484.cljs$core$IFn$_invoke$arity$2(G__74530,G__74531) : pred__74484.call(null,G__74530,G__74531));
})();
if(cljs.core.truth_(temp__5751__auto____$9)){
var p__5474__auto__ = temp__5751__auto____$9;
return (function (s__$1){
return cljc.java_time.year_month.parse.cljs$core$IFn$_invoke$arity$1(s__$1);
})(p__5474__auto__);
} else {
var temp__5751__auto____$10 = (function (){var G__74532 = /\d{4}/;
var G__74533 = expr__74485;
return (pred__74484.cljs$core$IFn$_invoke$arity$2 ? pred__74484.cljs$core$IFn$_invoke$arity$2(G__74532,G__74533) : pred__74484.call(null,G__74532,G__74533));
})();
if(cljs.core.truth_(temp__5751__auto____$10)){
var p__5474__auto__ = temp__5751__auto____$10;
return (function (s__$1){
return cljc.java_time.year.parse.cljs$core$IFn$_invoke$arity$1(s__$1);
})(p__5474__auto__);
} else {
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("Unparseable time string",new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"input","input",556931961),s], null));
}
}
}
}
}
}
}
}
}
}
}
}));
tick.core._STAR_clock_STAR_ = cljc.java_time.clock.system_default_zone();
tick.core.now = (function tick$core$now(){
return cljc.java_time.instant.now.cljs$core$IFn$_invoke$arity$1(tick.core._STAR_clock_STAR_);
});
tick.core.today = (function tick$core$today(){
return cljc.java_time.local_date.now.cljs$core$IFn$_invoke$arity$1(tick.core._STAR_clock_STAR_);
});
tick.core.epoch = (function tick$core$epoch(){
return cljc.java_time.instant.epoch;
});
tick.core.midnight = (function tick$core$midnight(var_args){
var G__74539 = arguments.length;
switch (G__74539) {
case 0:
return tick.core.midnight.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.midnight.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.midnight.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljc.java_time.local_time.midnight;
}));

(tick.core.midnight.cljs$core$IFn$_invoke$arity$1 = (function (date){
return tick.protocols.at(date,cljc.java_time.local_time.midnight);
}));

(tick.core.midnight.cljs$lang$maxFixedArity = 1);

tick.core.noon = (function tick$core$noon(var_args){
var G__74545 = arguments.length;
switch (G__74545) {
case 0:
return tick.core.noon.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.noon.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.noon.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljc.java_time.local_time.noon;
}));

(tick.core.noon.cljs$core$IFn$_invoke$arity$1 = (function (date){
return tick.protocols.at(date,cljc.java_time.local_time.noon);
}));

(tick.core.noon.cljs$lang$maxFixedArity = 1);

tick.core.parse_day = (function tick$core$parse_day(input){
var pred__74548 = cljs.core.re_matches;
var expr__74549 = clojure.string.lower_case(input);
if(cljs.core.truth_((function (){var G__74552 = /^(mon)(day)?$/;
var G__74553 = expr__74549;
return (pred__74548.cljs$core$IFn$_invoke$arity$2 ? pred__74548.cljs$core$IFn$_invoke$arity$2(G__74552,G__74553) : pred__74548.call(null,G__74552,G__74553));
})())){
return cljc.java_time.day_of_week.monday;
} else {
if(cljs.core.truth_((function (){var G__74554 = /^(tue)(s|sday)?$/;
var G__74555 = expr__74549;
return (pred__74548.cljs$core$IFn$_invoke$arity$2 ? pred__74548.cljs$core$IFn$_invoke$arity$2(G__74554,G__74555) : pred__74548.call(null,G__74554,G__74555));
})())){
return cljc.java_time.day_of_week.tuesday;
} else {
if(cljs.core.truth_((function (){var G__74560 = /^(wed)(s|nesday)?$/;
var G__74561 = expr__74549;
return (pred__74548.cljs$core$IFn$_invoke$arity$2 ? pred__74548.cljs$core$IFn$_invoke$arity$2(G__74560,G__74561) : pred__74548.call(null,G__74560,G__74561));
})())){
return cljc.java_time.day_of_week.wednesday;
} else {
if(cljs.core.truth_((function (){var G__74562 = /^(thur)(s|sday)?$/;
var G__74563 = expr__74549;
return (pred__74548.cljs$core$IFn$_invoke$arity$2 ? pred__74548.cljs$core$IFn$_invoke$arity$2(G__74562,G__74563) : pred__74548.call(null,G__74562,G__74563));
})())){
return cljc.java_time.day_of_week.thursday;
} else {
if(cljs.core.truth_((function (){var G__74564 = /^(fri)(day)?$/;
var G__74565 = expr__74549;
return (pred__74548.cljs$core$IFn$_invoke$arity$2 ? pred__74548.cljs$core$IFn$_invoke$arity$2(G__74564,G__74565) : pred__74548.call(null,G__74564,G__74565));
})())){
return cljc.java_time.day_of_week.friday;
} else {
if(cljs.core.truth_((function (){var G__74567 = /^(sat)(urday)?$/;
var G__74568 = expr__74549;
return (pred__74548.cljs$core$IFn$_invoke$arity$2 ? pred__74548.cljs$core$IFn$_invoke$arity$2(G__74567,G__74568) : pred__74548.call(null,G__74567,G__74568));
})())){
return cljc.java_time.day_of_week.saturday;
} else {
if(cljs.core.truth_((function (){var G__74570 = /^(sun)(day)?$/;
var G__74571 = expr__74549;
return (pred__74548.cljs$core$IFn$_invoke$arity$2 ? pred__74548.cljs$core$IFn$_invoke$arity$2(G__74570,G__74571) : pred__74548.call(null,G__74570,G__74571));
})())){
return cljc.java_time.day_of_week.sunday;
} else {
return null;
}
}
}
}
}
}
}
});
tick.core.parse_month = (function tick$core$parse_month(input){
var pred__74573 = cljs.core.re_matches;
var expr__74574 = clojure.string.lower_case(input);
if(cljs.core.truth_((function (){var G__74579 = /^(jan)(uary)?$/;
var G__74580 = expr__74574;
return (pred__74573.cljs$core$IFn$_invoke$arity$2 ? pred__74573.cljs$core$IFn$_invoke$arity$2(G__74579,G__74580) : pred__74573.call(null,G__74579,G__74580));
})())){
return cljc.java_time.month.january;
} else {
if(cljs.core.truth_((function (){var G__74582 = /^(feb)(ruary)?$/;
var G__74583 = expr__74574;
return (pred__74573.cljs$core$IFn$_invoke$arity$2 ? pred__74573.cljs$core$IFn$_invoke$arity$2(G__74582,G__74583) : pred__74573.call(null,G__74582,G__74583));
})())){
return cljc.java_time.month.february;
} else {
if(cljs.core.truth_((function (){var G__74585 = /^(mar)(ch)?$/;
var G__74586 = expr__74574;
return (pred__74573.cljs$core$IFn$_invoke$arity$2 ? pred__74573.cljs$core$IFn$_invoke$arity$2(G__74585,G__74586) : pred__74573.call(null,G__74585,G__74586));
})())){
return cljc.java_time.month.march;
} else {
if(cljs.core.truth_((function (){var G__74587 = /^(apr)(il)?$/;
var G__74588 = expr__74574;
return (pred__74573.cljs$core$IFn$_invoke$arity$2 ? pred__74573.cljs$core$IFn$_invoke$arity$2(G__74587,G__74588) : pred__74573.call(null,G__74587,G__74588));
})())){
return cljc.java_time.month.april;
} else {
if(cljs.core.truth_((function (){var G__74589 = /^may$/;
var G__74590 = expr__74574;
return (pred__74573.cljs$core$IFn$_invoke$arity$2 ? pred__74573.cljs$core$IFn$_invoke$arity$2(G__74589,G__74590) : pred__74573.call(null,G__74589,G__74590));
})())){
return cljc.java_time.month.may;
} else {
if(cljs.core.truth_((function (){var G__74591 = /^(jun)(e)?$/;
var G__74592 = expr__74574;
return (pred__74573.cljs$core$IFn$_invoke$arity$2 ? pred__74573.cljs$core$IFn$_invoke$arity$2(G__74591,G__74592) : pred__74573.call(null,G__74591,G__74592));
})())){
return cljc.java_time.month.june;
} else {
if(cljs.core.truth_((function (){var G__74594 = /^(jul)(y)?$/;
var G__74595 = expr__74574;
return (pred__74573.cljs$core$IFn$_invoke$arity$2 ? pred__74573.cljs$core$IFn$_invoke$arity$2(G__74594,G__74595) : pred__74573.call(null,G__74594,G__74595));
})())){
return cljc.java_time.month.july;
} else {
if(cljs.core.truth_((function (){var G__74596 = /^(aug)(ust)?$/;
var G__74597 = expr__74574;
return (pred__74573.cljs$core$IFn$_invoke$arity$2 ? pred__74573.cljs$core$IFn$_invoke$arity$2(G__74596,G__74597) : pred__74573.call(null,G__74596,G__74597));
})())){
return cljc.java_time.month.august;
} else {
if(cljs.core.truth_((function (){var G__74599 = /^(sep)(tember)?$/;
var G__74600 = expr__74574;
return (pred__74573.cljs$core$IFn$_invoke$arity$2 ? pred__74573.cljs$core$IFn$_invoke$arity$2(G__74599,G__74600) : pred__74573.call(null,G__74599,G__74600));
})())){
return cljc.java_time.month.september;
} else {
if(cljs.core.truth_((function (){var G__74605 = /^(oct)(ober)?$/;
var G__74606 = expr__74574;
return (pred__74573.cljs$core$IFn$_invoke$arity$2 ? pred__74573.cljs$core$IFn$_invoke$arity$2(G__74605,G__74606) : pred__74573.call(null,G__74605,G__74606));
})())){
return cljc.java_time.month.october;
} else {
if(cljs.core.truth_((function (){var G__74607 = /^(nov)(ember)?$/;
var G__74608 = expr__74574;
return (pred__74573.cljs$core$IFn$_invoke$arity$2 ? pred__74573.cljs$core$IFn$_invoke$arity$2(G__74607,G__74608) : pred__74573.call(null,G__74607,G__74608));
})())){
return cljc.java_time.month.november;
} else {
if(cljs.core.truth_((function (){var G__74609 = /^(dec)(ember)?$/;
var G__74610 = expr__74574;
return (pred__74573.cljs$core$IFn$_invoke$arity$2 ? pred__74573.cljs$core$IFn$_invoke$arity$2(G__74609,G__74610) : pred__74573.call(null,G__74609,G__74610));
})())){
return cljc.java_time.month.december;
} else {
return null;
}
}
}
}
}
}
}
}
}
}
}
}
});
tick.core.new_time = (function tick$core$new_time(var_args){
var G__74614 = arguments.length;
switch (G__74614) {
case 0:
return tick.core.new_time.cljs$core$IFn$_invoke$arity$0();

break;
case 2:
return tick.core.new_time.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return tick.core.new_time.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 4:
return tick.core.new_time.cljs$core$IFn$_invoke$arity$4((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.new_time.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.protocols.time(tick.core.now());
}));

(tick.core.new_time.cljs$core$IFn$_invoke$arity$2 = (function (hour,minute){
return cljc.java_time.local_time.of.cljs$core$IFn$_invoke$arity$2(hour,minute);
}));

(tick.core.new_time.cljs$core$IFn$_invoke$arity$3 = (function (hour,minute,second){
return cljc.java_time.local_time.of.cljs$core$IFn$_invoke$arity$3(hour,minute,second);
}));

(tick.core.new_time.cljs$core$IFn$_invoke$arity$4 = (function (hour,minute,second,nano){
return cljc.java_time.local_time.of.cljs$core$IFn$_invoke$arity$4(hour,minute,second,nano);
}));

(tick.core.new_time.cljs$lang$maxFixedArity = 4);

tick.core.new_date = (function tick$core$new_date(var_args){
var G__74622 = arguments.length;
switch (G__74622) {
case 0:
return tick.core.new_date.cljs$core$IFn$_invoke$arity$0();

break;
case 3:
return tick.core.new_date.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 2:
return tick.core.new_date.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 1:
return tick.core.new_date.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.new_date.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.core.today();
}));

(tick.core.new_date.cljs$core$IFn$_invoke$arity$3 = (function (year,month,day_of_month){
return cljc.java_time.local_date.of(year,month,day_of_month);
}));

(tick.core.new_date.cljs$core$IFn$_invoke$arity$2 = (function (year,day_of_year){
return cljc.java_time.local_date.of_year_day(year,day_of_year);
}));

(tick.core.new_date.cljs$core$IFn$_invoke$arity$1 = (function (epoch_day){
return cljc.java_time.local_date.of_epoch_day(epoch_day);
}));

(tick.core.new_date.cljs$lang$maxFixedArity = 3);

tick.core.new_year_month = (function tick$core$new_year_month(var_args){
var G__74628 = arguments.length;
switch (G__74628) {
case 0:
return tick.core.new_year_month.cljs$core$IFn$_invoke$arity$0();

break;
case 2:
return tick.core.new_year_month.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.new_year_month.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljc.java_time.year_month.now.cljs$core$IFn$_invoke$arity$0();
}));

(tick.core.new_year_month.cljs$core$IFn$_invoke$arity$2 = (function (year,month){
return cljc.java_time.year_month.of(year,month);
}));

(tick.core.new_year_month.cljs$lang$maxFixedArity = 2);

/**
 * Return the current zone, which can be overridden by the *clock* dynamic var
 */
tick.core.current_zone = (function tick$core$current_zone(){
var temp__5751__auto__ = tick.core._STAR_clock_STAR_;
if(cljs.core.truth_(temp__5751__auto__)){
var clk = temp__5751__auto__;
return cljc.java_time.clock.get_zone(clk);
} else {
return cljc.java_time.zone_id.system_default();
}
});
tick.core.zone = (function tick$core$zone(var_args){
var G__74634 = arguments.length;
switch (G__74634) {
case 0:
return tick.core.zone.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.zone.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.zone.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.core.current_zone();
}));

(tick.core.zone.cljs$core$IFn$_invoke$arity$1 = (function (z){
return tick.protocols.zone(z);
}));

(tick.core.zone.cljs$lang$maxFixedArity = 1);

tick.core.zone_offset = (function tick$core$zone_offset(var_args){
var G__74637 = arguments.length;
switch (G__74637) {
case 1:
return tick.core.zone_offset.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return tick.core.zone_offset.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return tick.core.zone_offset.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.zone_offset.cljs$core$IFn$_invoke$arity$1 = (function (offset){
return tick.protocols.zone_offset(offset);
}));

(tick.core.zone_offset.cljs$core$IFn$_invoke$arity$2 = (function (hours,minutes){
return cljc.java_time.zone_offset.of_hours_minutes(hours,minutes);
}));

(tick.core.zone_offset.cljs$core$IFn$_invoke$arity$3 = (function (hours,minutes,seconds){
return cljc.java_time.zone_offset.of_hours_minutes_seconds(hours,minutes,seconds);
}));

(tick.core.zone_offset.cljs$lang$maxFixedArity = 3);

(tick.protocols.IConversion["function"] = true);

(tick.protocols.inst["function"] = (function (f){
return tick.protocols.inst((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.instant["function"] = (function (f){
return tick.protocols.instant((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.offset_date_time["function"] = (function (f){
return tick.protocols.offset_date_time((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.zoned_date_time["function"] = (function (f){
return tick.protocols.zoned_date_time((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(java.time.Instant.prototype.tick$protocols$IConversion$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Instant.prototype.tick$protocols$IConversion$inst$arity$1 = (function (i){
var i__$1 = this;
return (new Date(cljc.java_time.instant.to_epoch_milli(i__$1)));
}));

(java.time.Instant.prototype.tick$protocols$IConversion$instant$arity$1 = (function (i){
var i__$1 = this;
return i__$1;
}));

(java.time.Instant.prototype.tick$protocols$IConversion$offset_date_time$arity$1 = (function (i){
var i__$1 = this;
return cljc.java_time.offset_date_time.of_instant(i__$1,tick.core.current_zone());
}));

(java.time.Instant.prototype.tick$protocols$IConversion$zoned_date_time$arity$1 = (function (i){
var i__$1 = this;
return cljc.java_time.zoned_date_time.of_instant.cljs$core$IFn$_invoke$arity$2(i__$1,tick.core.current_zone());
}));

(tick.protocols.IConversion["string"] = true);

(tick.protocols.inst["string"] = (function (s){
return tick.protocols.inst(tick.protocols.instant(s));
}));

(tick.protocols.instant["string"] = (function (s){
return cljc.java_time.instant.parse(s);
}));

(tick.protocols.offset_date_time["string"] = (function (s){
return cljc.java_time.offset_date_time.parse.cljs$core$IFn$_invoke$arity$1(s);
}));

(tick.protocols.zoned_date_time["string"] = (function (s){
return cljc.java_time.zoned_date_time.parse.cljs$core$IFn$_invoke$arity$1(s);
}));

(tick.protocols.IConversion["number"] = true);

(tick.protocols.instant["number"] = (function (n){
return cljc.java_time.instant.of_epoch_milli(n);
}));

(java.time.LocalDateTime.prototype.tick$protocols$IConversion$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDateTime.prototype.tick$protocols$IConversion$inst$arity$1 = (function (ldt){
var ldt__$1 = this;
return tick.protocols.inst(tick.protocols.zoned_date_time(ldt__$1));
}));

(java.time.LocalDateTime.prototype.tick$protocols$IConversion$instant$arity$1 = (function (ldt){
var ldt__$1 = this;
return tick.protocols.instant(tick.protocols.zoned_date_time(ldt__$1));
}));

(java.time.LocalDateTime.prototype.tick$protocols$IConversion$offset_date_time$arity$1 = (function (ldt){
var ldt__$1 = this;
return cljc.java_time.local_date_time.at_offset(ldt__$1,cljc.java_time.zone_id.get_rules(tick.core.current_zone()).offset(ldt__$1));
}));

(java.time.LocalDateTime.prototype.tick$protocols$IConversion$zoned_date_time$arity$1 = (function (ldt){
var ldt__$1 = this;
return cljc.java_time.local_date_time.at_zone(ldt__$1,tick.core.current_zone());
}));

(Date.prototype.tick$protocols$IConversion$ = cljs.core.PROTOCOL_SENTINEL);

(Date.prototype.tick$protocols$IConversion$inst$arity$1 = (function (d){
var d__$1 = this;
return d__$1;
}));

(Date.prototype.tick$protocols$IConversion$instant$arity$1 = (function (d){
var d__$1 = this;
return cljc.java_time.instant.of_epoch_milli(d__$1.getTime());
}));

(Date.prototype.tick$protocols$IConversion$zoned_date_time$arity$1 = (function (d){
var d__$1 = this;
return tick.protocols.zoned_date_time(tick.protocols.instant(d__$1));
}));

(Date.prototype.tick$protocols$IConversion$offset_date_time$arity$1 = (function (d){
var d__$1 = this;
return tick.protocols.offset_date_time(tick.protocols.instant(d__$1));
}));

(java.time.OffsetDateTime.prototype.tick$protocols$IConversion$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.OffsetDateTime.prototype.tick$protocols$IConversion$inst$arity$1 = (function (odt){
var odt__$1 = this;
return tick.protocols.inst(tick.protocols.instant(odt__$1));
}));

(java.time.OffsetDateTime.prototype.tick$protocols$IConversion$instant$arity$1 = (function (odt){
var odt__$1 = this;
return cljc.java_time.offset_date_time.to_instant(odt__$1);
}));

(java.time.OffsetDateTime.prototype.tick$protocols$IConversion$offset_date_time$arity$1 = (function (odt){
var odt__$1 = this;
return odt__$1;
}));

(java.time.OffsetDateTime.prototype.tick$protocols$IConversion$zoned_date_time$arity$1 = (function (odt){
var odt__$1 = this;
return cljc.java_time.offset_date_time.to_zoned_date_time(odt__$1);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IConversion$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.ZonedDateTime.prototype.tick$protocols$IConversion$inst$arity$1 = (function (zdt){
var zdt__$1 = this;
return tick.protocols.inst(tick.protocols.instant(zdt__$1));
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IConversion$instant$arity$1 = (function (zdt){
var zdt__$1 = this;
return cljc.java_time.zoned_date_time.to_instant(zdt__$1);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IConversion$offset_date_time$arity$1 = (function (zdt){
var zdt__$1 = this;
return cljc.java_time.zoned_date_time.to_offset_date_time(zdt__$1);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IConversion$zoned_date_time$arity$1 = (function (zdt){
var zdt__$1 = this;
return zdt__$1;
}));
(java.time.YearMonth.prototype.tick$protocols$IExtraction$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.YearMonth.prototype.tick$protocols$IExtraction$year_month$arity$1 = (function (ym){
var ym__$1 = this;
return ym__$1;
}));

(java.time.YearMonth.prototype.tick$protocols$IExtraction$month$arity$1 = (function (ym){
var ym__$1 = this;
return cljc.java_time.year_month.get_month(ym__$1);
}));

(java.time.YearMonth.prototype.tick$protocols$IExtraction$year$arity$1 = (function (ym){
var ym__$1 = this;
return tick.protocols.year(cljc.java_time.year_month.get_year(ym__$1));
}));

(java.time.Year.prototype.tick$protocols$IExtraction$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Year.prototype.tick$protocols$IExtraction$year$arity$1 = (function (y){
var y__$1 = this;
return y__$1;
}));

(java.time.Year.prototype.tick$protocols$IExtraction$int$arity$1 = (function (y){
var y__$1 = this;
return cljc.java_time.year.get_value(y__$1);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IExtraction$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.ZonedDateTime.prototype.tick$protocols$IExtraction$time$arity$1 = (function (zdt){
var zdt__$1 = this;
return cljc.java_time.zoned_date_time.to_local_time(zdt__$1);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IExtraction$date$arity$1 = (function (zdt){
var zdt__$1 = this;
return cljc.java_time.zoned_date_time.to_local_date(zdt__$1);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IExtraction$date_time$arity$1 = (function (zdt){
var zdt__$1 = this;
return cljc.java_time.zoned_date_time.to_local_date_time(zdt__$1);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IExtraction$nanosecond$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.zoned_date_time.get(t__$1,cljc.java_time.temporal.chrono_field.nano_of_second);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IExtraction$microsecond$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.local_time.get(t__$1,cljc.java_time.temporal.chrono_field.micro_of_second);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IExtraction$millisecond$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.local_time.get(t__$1,cljc.java_time.temporal.chrono_field.milli_of_second);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IExtraction$second$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.zoned_date_time.get_second(t__$1);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IExtraction$minute$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.zoned_date_time.get_minute(t__$1);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IExtraction$hour$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.zoned_date_time.get_hour(t__$1);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IExtraction$day_of_week$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.zoned_date_time.get_day_of_week(t__$1);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IExtraction$day_of_month$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.zoned_date_time.get_day_of_month(t__$1);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IExtraction$month$arity$1 = (function (zdt){
var zdt__$1 = this;
return cljc.java_time.zoned_date_time.get_month(zdt__$1);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IExtraction$year$arity$1 = (function (zdt){
var zdt__$1 = this;
return tick.protocols.year(cljc.java_time.zoned_date_time.get_year(zdt__$1));
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IExtraction$zone$arity$1 = (function (zdt){
var zdt__$1 = this;
return cljc.java_time.zoned_date_time.get_zone(zdt__$1);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IExtraction$zone_offset$arity$1 = (function (zdt){
var zdt__$1 = this;
return cljc.java_time.zoned_date_time.get_offset(zdt__$1);
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Instant.prototype.tick$protocols$IExtraction$time$arity$1 = (function (i){
var i__$1 = this;
return tick.protocols.time(tick.protocols.zoned_date_time(i__$1));
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$date$arity$1 = (function (i){
var i__$1 = this;
return tick.protocols.date(tick.protocols.zoned_date_time(i__$1));
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$date_time$arity$1 = (function (i){
var i__$1 = this;
return tick.protocols.date_time(tick.protocols.zoned_date_time(i__$1));
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$nanosecond$arity$1 = (function (t){
var t__$1 = this;
return tick.protocols.nanosecond(tick.protocols.zoned_date_time(t__$1));
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$microsecond$arity$1 = (function (t){
var t__$1 = this;
return tick.protocols.microsecond(tick.protocols.zoned_date_time(t__$1));
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$millisecond$arity$1 = (function (t){
var t__$1 = this;
return tick.protocols.millisecond(tick.protocols.zoned_date_time(t__$1));
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$second$arity$1 = (function (t){
var t__$1 = this;
return tick.protocols.second(tick.protocols.zoned_date_time(t__$1));
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$minute$arity$1 = (function (t){
var t__$1 = this;
return tick.protocols.minute(tick.protocols.zoned_date_time(t__$1));
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$hour$arity$1 = (function (t){
var t__$1 = this;
return tick.protocols.hour(tick.protocols.zoned_date_time(t__$1));
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$day_of_week$arity$1 = (function (i){
var i__$1 = this;
return tick.protocols.day_of_week(tick.protocols.date(i__$1));
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$day_of_month$arity$1 = (function (i){
var i__$1 = this;
return tick.protocols.day_of_month(tick.protocols.date(i__$1));
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$int$arity$1 = (function (i){
var i__$1 = this;
return cljc.java_time.instant.get_nano(i__$1);
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$long$arity$1 = (function (i){
var i__$1 = this;
return cljc.java_time.instant.get_epoch_second(i__$1);
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$month$arity$1 = (function (i){
var i__$1 = this;
return tick.protocols.month(tick.protocols.date(i__$1));
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$year$arity$1 = (function (i){
var i__$1 = this;
return tick.protocols.year(tick.protocols.date(i__$1));
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$year_month$arity$1 = (function (i){
var i__$1 = this;
return tick.protocols.year_month(tick.protocols.date(i__$1));
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$zone$arity$1 = (function (i){
var i__$1 = this;
return cljc.java_time.zone_id.of.cljs$core$IFn$_invoke$arity$1("UTC");
}));

(java.time.Instant.prototype.tick$protocols$IExtraction$zone_offset$arity$1 = (function (i){
var i__$1 = this;
return cljc.java_time.zone_offset.utc;
}));

(tick.protocols.IExtraction["object"] = true);

(tick.protocols.int$["object"] = (function (v){
return tick.core.parse_int(v);
}));

(tick.protocols.long$["object"] = (function (v){
return tick.core.parse_int(v);
}));

(Date.prototype.tick$protocols$IExtraction$ = cljs.core.PROTOCOL_SENTINEL);

(Date.prototype.tick$protocols$IExtraction$date$arity$1 = (function (d){
var d__$1 = this;
return tick.protocols.date(tick.protocols.zoned_date_time(tick.protocols.instant(d__$1)));
}));

(Date.prototype.tick$protocols$IExtraction$date_time$arity$1 = (function (d){
var d__$1 = this;
return tick.protocols.date_time(tick.protocols.instant(d__$1));
}));

(Date.prototype.tick$protocols$IExtraction$year_month$arity$1 = (function (d){
var d__$1 = this;
return tick.protocols.year_month(tick.protocols.date(d__$1));
}));

(Date.prototype.tick$protocols$IExtraction$year$arity$1 = (function (d){
var d__$1 = this;
return tick.protocols.year(tick.protocols.date(d__$1));
}));

(java.time.ZoneId.prototype.tick$protocols$IExtraction$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.ZoneId.prototype.tick$protocols$IExtraction$zone$arity$1 = (function (z){
var z__$1 = this;
return z__$1;
}));

(java.time.LocalDate.prototype.tick$protocols$IExtraction$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDate.prototype.tick$protocols$IExtraction$date$arity$1 = (function (d){
var d__$1 = this;
return d__$1;
}));

(java.time.LocalDate.prototype.tick$protocols$IExtraction$day_of_week$arity$1 = (function (d){
var d__$1 = this;
return cljc.java_time.local_date.get_day_of_week(d__$1);
}));

(java.time.LocalDate.prototype.tick$protocols$IExtraction$day_of_month$arity$1 = (function (d){
var d__$1 = this;
return cljc.java_time.local_date.get_day_of_month(d__$1);
}));

(java.time.LocalDate.prototype.tick$protocols$IExtraction$month$arity$1 = (function (d){
var d__$1 = this;
return cljc.java_time.month.from(d__$1);
}));

(java.time.LocalDate.prototype.tick$protocols$IExtraction$year_month$arity$1 = (function (d){
var d__$1 = this;
return cljc.java_time.year_month.of(cljc.java_time.local_date.get_year(d__$1),cljc.java_time.local_date.get_month_value(d__$1));
}));

(java.time.LocalDate.prototype.tick$protocols$IExtraction$year$arity$1 = (function (d){
var d__$1 = this;
return cljc.java_time.year.of(cljc.java_time.local_date.get_year(d__$1));
}));

(tick.protocols.IExtraction["number"] = true);

(tick.protocols.day_of_week["number"] = (function (n){
return cljc.java_time.day_of_week.of(n);
}));

(tick.protocols.month["number"] = (function (n){
return cljc.java_time.month.of(n);
}));

(tick.protocols.year["number"] = (function (n){
return cljc.java_time.year.of(n);
}));

(tick.protocols.zone_offset["number"] = (function (s){
return cljc.java_time.zone_offset.of_hours(s);
}));

(java.time.ZoneOffset.prototype.tick$protocols$IExtraction$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.ZoneOffset.prototype.tick$protocols$IExtraction$zone_offset$arity$1 = (function (z){
var z__$1 = this;
return z__$1;
}));

(java.time.ZoneOffset.prototype.tick$protocols$IExtraction$zone$arity$1 = (function (z){
var z__$1 = this;
return z__$1;
}));

(tick.protocols.IExtraction["string"] = true);

(tick.protocols.time["string"] = (function (s){
return cljc.java_time.local_time.parse.cljs$core$IFn$_invoke$arity$1(s);
}));

(tick.protocols.date["string"] = (function (s){
return cljc.java_time.local_date.parse.cljs$core$IFn$_invoke$arity$1(s);
}));

(tick.protocols.date_time["string"] = (function (s){
return cljc.java_time.local_date_time.parse.cljs$core$IFn$_invoke$arity$1(s);
}));

(tick.protocols.day_of_week["string"] = (function (s){
var or__5043__auto__ = tick.core.parse_day(s);
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
return tick.protocols.day_of_week(tick.protocols.date(s));
}
}));

(tick.protocols.day_of_month["string"] = (function (s){
return tick.protocols.day_of_month(tick.protocols.date(s));
}));

(tick.protocols.month["string"] = (function (s){
var or__5043__auto__ = tick.core.parse_month(s);
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
return tick.protocols.month(tick.protocols.date(s));
}
}));

(tick.protocols.year["string"] = (function (s){
return cljc.java_time.year.parse.cljs$core$IFn$_invoke$arity$1(s);
}));

(tick.protocols.year_month["string"] = (function (s){
return cljc.java_time.year_month.parse.cljs$core$IFn$_invoke$arity$1(s);
}));

(tick.protocols.zone["string"] = (function (s){
return cljc.java_time.zone_id.of.cljs$core$IFn$_invoke$arity$1(s);
}));

(tick.protocols.zone_offset["string"] = (function (s){
return cljc.java_time.zone_offset.of.cljs$core$IFn$_invoke$arity$1(s);
}));

(tick.protocols.int$["string"] = (function (s){
return cljc.java_time.instant.get_nano(tick.protocols.instant(s));
}));

(tick.protocols.long$["string"] = (function (s){
return cljc.java_time.instant.get_epoch_second(tick.protocols.instant(s));
}));

(tick.protocols.IExtraction["function"] = true);

(tick.protocols.time["function"] = (function (f){
return tick.protocols.time((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.date["function"] = (function (f){
return tick.protocols.date((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.date_time["function"] = (function (f){
return tick.protocols.date_time((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.nanosecond["function"] = (function (f){
return tick.protocols.nanosecond((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.microsecond["function"] = (function (f){
return tick.protocols.microsecond((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.millisecond["function"] = (function (f){
return tick.protocols.millisecond((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.second["function"] = (function (f){
return tick.protocols.second((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.minute["function"] = (function (f){
return tick.protocols.minute((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.hour["function"] = (function (f){
return tick.protocols.hour((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.day_of_week["function"] = (function (f){
return tick.protocols.day_of_week((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.day_of_month["function"] = (function (f){
return tick.protocols.day_of_month((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.int$["function"] = (function (f){
return tick.protocols.int$((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.long$["function"] = (function (f){
return tick.protocols.long$((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.month["function"] = (function (f){
return tick.protocols.month((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.year["function"] = (function (f){
return tick.protocols.year((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.year_month["function"] = (function (f){
return tick.protocols.year_month((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.zone["function"] = (function (f){
return tick.protocols.zone((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(tick.protocols.zone_offset["function"] = (function (f){
return tick.protocols.zone_offset((f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)));
}));

(java.time.LocalTime.prototype.tick$protocols$IExtraction$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalTime.prototype.tick$protocols$IExtraction$time$arity$1 = (function (t){
var t__$1 = this;
return t__$1;
}));

(java.time.LocalTime.prototype.tick$protocols$IExtraction$nanosecond$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.local_time.get(t__$1,cljc.java_time.temporal.chrono_field.nano_of_second);
}));

(java.time.LocalTime.prototype.tick$protocols$IExtraction$microsecond$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.local_time.get(t__$1,cljc.java_time.temporal.chrono_field.micro_of_second);
}));

(java.time.LocalTime.prototype.tick$protocols$IExtraction$millisecond$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.local_time.get(t__$1,cljc.java_time.temporal.chrono_field.milli_of_second);
}));

(java.time.LocalTime.prototype.tick$protocols$IExtraction$second$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.local_time.get_second(t__$1);
}));

(java.time.LocalTime.prototype.tick$protocols$IExtraction$minute$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.local_time.get_minute(t__$1);
}));

(java.time.LocalTime.prototype.tick$protocols$IExtraction$hour$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.local_time.get_hour(t__$1);
}));

(java.time.OffsetDateTime.prototype.tick$protocols$IExtraction$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.OffsetDateTime.prototype.tick$protocols$IExtraction$time$arity$1 = (function (odt){
var odt__$1 = this;
return cljc.java_time.offset_date_time.to_local_time(odt__$1);
}));

(java.time.OffsetDateTime.prototype.tick$protocols$IExtraction$date$arity$1 = (function (odt){
var odt__$1 = this;
return cljc.java_time.offset_date_time.to_local_date(odt__$1);
}));

(java.time.OffsetDateTime.prototype.tick$protocols$IExtraction$date_time$arity$1 = (function (odt){
var odt__$1 = this;
return cljc.java_time.offset_date_time.to_local_date_time(odt__$1);
}));

(java.time.OffsetDateTime.prototype.tick$protocols$IExtraction$year$arity$1 = (function (odt){
var odt__$1 = this;
return tick.protocols.year(cljc.java_time.offset_date_time.get_year(odt__$1));
}));

(java.time.OffsetDateTime.prototype.tick$protocols$IExtraction$zone_offset$arity$1 = (function (odt){
var odt__$1 = this;
return cljc.java_time.offset_date_time.get_offset(odt__$1);
}));

(java.time.LocalDateTime.prototype.tick$protocols$IExtraction$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDateTime.prototype.tick$protocols$IExtraction$time$arity$1 = (function (dt){
var dt__$1 = this;
return cljc.java_time.local_date_time.to_local_time(dt__$1);
}));

(java.time.LocalDateTime.prototype.tick$protocols$IExtraction$date$arity$1 = (function (dt){
var dt__$1 = this;
return cljc.java_time.local_date_time.to_local_date(dt__$1);
}));

(java.time.LocalDateTime.prototype.tick$protocols$IExtraction$date_time$arity$1 = (function (ldt){
var ldt__$1 = this;
return ldt__$1;
}));

(java.time.LocalDateTime.prototype.tick$protocols$IExtraction$second$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.local_date_time.get_second(t__$1);
}));

(java.time.LocalDateTime.prototype.tick$protocols$IExtraction$minute$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.local_date_time.get_minute(t__$1);
}));

(java.time.LocalDateTime.prototype.tick$protocols$IExtraction$hour$arity$1 = (function (t){
var t__$1 = this;
return cljc.java_time.local_date_time.get_hour(t__$1);
}));

(java.time.LocalDateTime.prototype.tick$protocols$IExtraction$day_of_week$arity$1 = (function (dt){
var dt__$1 = this;
return tick.protocols.day_of_week(tick.protocols.date(dt__$1));
}));

(java.time.LocalDateTime.prototype.tick$protocols$IExtraction$day_of_month$arity$1 = (function (dt){
var dt__$1 = this;
return tick.protocols.day_of_month(tick.protocols.date(dt__$1));
}));

(java.time.LocalDateTime.prototype.tick$protocols$IExtraction$year_month$arity$1 = (function (dt){
var dt__$1 = this;
return tick.protocols.year_month(tick.protocols.date(dt__$1));
}));

(java.time.LocalDateTime.prototype.tick$protocols$IExtraction$month$arity$1 = (function (dt){
var dt__$1 = this;
return cljc.java_time.local_date_time.get_month(dt__$1);
}));

(java.time.LocalDateTime.prototype.tick$protocols$IExtraction$year$arity$1 = (function (dt){
var dt__$1 = this;
return tick.protocols.year(tick.protocols.date(dt__$1));
}));

(java.time.Month.prototype.tick$protocols$IExtraction$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Month.prototype.tick$protocols$IExtraction$int$arity$1 = (function (m){
var m__$1 = this;
return cljc.java_time.month.get_value(m__$1);
}));

(java.time.DayOfWeek.prototype.tick$protocols$IExtraction$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.DayOfWeek.prototype.tick$protocols$IExtraction$int$arity$1 = (function (d){
var d__$1 = this;
return cljc.java_time.day_of_week.get_value(d__$1);
}));
tick.core.field_map = cljs.core.PersistentHashMap.fromArrays([new cljs.core.Keyword(null,"proleptic-month","proleptic-month",-746961920),new cljs.core.Keyword(null,"aligned-week-of-month","aligned-week-of-month",-1988230912),new cljs.core.Keyword(null,"clock-hour-of-ampm","clock-hour-of-ampm",-268394751),new cljs.core.Keyword(null,"epoch-day","epoch-day",-1151217791),new cljs.core.Keyword(null,"nano-of-second","nano-of-second",-1642935645),new cljs.core.Keyword(null,"aligned-week-of-year","aligned-week-of-year",70378276),new cljs.core.Keyword(null,"era","era",1165135812),new cljs.core.Keyword(null,"offset-seconds","offset-seconds",-695890139),new cljs.core.Keyword(null,"micro-of-day","micro-of-day",-1255932121),new cljs.core.Keyword(null,"minute-of-day","minute-of-day",558111464),new cljs.core.Keyword(null,"day-of-week","day-of-week",1639326729),new cljs.core.Keyword(null,"ampm-of-day","ampm-of-day",-284051414),new cljs.core.Keyword(null,"clock-hour-of-day","clock-hour-of-day",370314698),new cljs.core.Keyword(null,"hour-of-day","hour-of-day",2086777099),new cljs.core.Keyword(null,"month-of-year","month-of-year",760756492),new cljs.core.Keyword(null,"milli-of-second","milli-of-second",-1422144788),new cljs.core.Keyword(null,"instant-seconds","instant-seconds",1032794797),new cljs.core.Keyword(null,"micro-of-second","micro-of-second",1805992110),new cljs.core.Keyword(null,"aligned-day-of-week-in-month","aligned-day-of-week-in-month",-452127505),new cljs.core.Keyword(null,"day-of-month","day-of-month",-1096650288),new cljs.core.Keyword(null,"year","year",335913393),new cljs.core.Keyword(null,"day-of-year","day-of-year",478600113),new cljs.core.Keyword(null,"year-of-era","year-of-era",682445876),new cljs.core.Keyword(null,"nano-of-day","nano-of-day",525361845),new cljs.core.Keyword(null,"hour-of-ampm","hour-of-ampm",1171096469),new cljs.core.Keyword(null,"second-of-minute","second-of-minute",222734326),new cljs.core.Keyword(null,"aligned-day-of-week-in-year","aligned-day-of-week-in-year",-931066377),new cljs.core.Keyword(null,"second-of-day","second-of-day",806277913),new cljs.core.Keyword(null,"milli-of-day","milli-of-day",-2024730021),new cljs.core.Keyword(null,"minute-of-hour","minute-of-hour",1903220478)],[cljc.java_time.temporal.chrono_field.proleptic_month,cljc.java_time.temporal.chrono_field.aligned_week_of_month,cljc.java_time.temporal.chrono_field.clock_hour_of_ampm,cljc.java_time.temporal.chrono_field.epoch_day,cljc.java_time.temporal.chrono_field.nano_of_second,cljc.java_time.temporal.chrono_field.aligned_week_of_year,cljc.java_time.temporal.chrono_field.era,cljc.java_time.temporal.chrono_field.offset_seconds,cljc.java_time.temporal.chrono_field.micro_of_day,cljc.java_time.temporal.chrono_field.minute_of_day,cljc.java_time.temporal.chrono_field.day_of_week,cljc.java_time.temporal.chrono_field.ampm_of_day,cljc.java_time.temporal.chrono_field.clock_hour_of_day,cljc.java_time.temporal.chrono_field.hour_of_day,cljc.java_time.temporal.chrono_field.month_of_year,cljc.java_time.temporal.chrono_field.milli_of_second,cljc.java_time.temporal.chrono_field.instant_seconds,cljc.java_time.temporal.chrono_field.micro_of_second,cljc.java_time.temporal.chrono_field.aligned_day_of_week_in_month,cljc.java_time.temporal.chrono_field.day_of_month,cljc.java_time.temporal.chrono_field.year,cljc.java_time.temporal.chrono_field.day_of_year,cljc.java_time.temporal.chrono_field.year_of_era,cljc.java_time.temporal.chrono_field.nano_of_day,cljc.java_time.temporal.chrono_field.hour_of_ampm,cljc.java_time.temporal.chrono_field.second_of_minute,cljc.java_time.temporal.chrono_field.aligned_day_of_week_in_year,cljc.java_time.temporal.chrono_field.second_of_day,cljc.java_time.temporal.chrono_field.milli_of_day,cljc.java_time.temporal.chrono_field.minute_of_hour]);

/**
* @constructor
 * @implements {cljs.core.ISeqable}
 * @implements {cljs.core.ILookup}
*/
tick.core.FieldsLookup = (function (t){
this.t = t;
this.cljs$lang$protocol_mask$partition0$ = 8388864;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(tick.core.FieldsLookup.prototype.cljs$core$ISeqable$_seq$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.seq(cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,cljs.core.keep.cljs$core$IFn$_invoke$arity$2((function (p__74728){
var vec__74729 = p__74728;
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74729,(0),null);
var _v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74729,(1),null);
var cf = cljs.core.get.cljs$core$IFn$_invoke$arity$2(tick.core.field_map,k);
if(cljs.core.truth_(cljc.java_time.temporal.temporal.is_supported(self__.t,cf))){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [k,cljc.java_time.temporal.temporal.get_long(self__.t,cf)], null);
} else {
return null;
}
}),tick.core.field_map)));
}));

(tick.core.FieldsLookup.prototype.cljs$core$ILookup$_lookup$arity$2 = (function (_,fld){
var self__ = this;
var ___$1 = this;
var temp__5753__auto__ = cljs.core.get.cljs$core$IFn$_invoke$arity$2(tick.core.field_map,fld);
if(cljs.core.truth_(temp__5753__auto__)){
var f = temp__5753__auto__;
return cljc.java_time.temporal.temporal.get_long(self__.t,f);
} else {
return null;
}
}));

(tick.core.FieldsLookup.prototype.cljs$core$ILookup$_lookup$arity$3 = (function (_,fld,notfound){
var self__ = this;
var ___$1 = this;
var temp__5751__auto__ = cljs.core.get.cljs$core$IFn$_invoke$arity$2(tick.core.field_map,fld);
if(cljs.core.truth_(temp__5751__auto__)){
var f = temp__5751__auto__;
try{return cljc.java_time.temporal.temporal.get_long(self__.t,f);
}catch (e74732){if((e74732 instanceof Error)){
var _e = e74732;
return notfound;
} else {
throw e74732;

}
}} else {
return notfound;
}
}));

(tick.core.FieldsLookup.getBasis = (function (){
return new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"t","t",242699008,null)], null);
}));

(tick.core.FieldsLookup.cljs$lang$type = true);

(tick.core.FieldsLookup.cljs$lang$ctorStr = "tick.core/FieldsLookup");

(tick.core.FieldsLookup.cljs$lang$ctorPrWriter = (function (this__5327__auto__,writer__5328__auto__,opt__5329__auto__){
return cljs.core._write(writer__5328__auto__,"tick.core/FieldsLookup");
}));

/**
 * Positional factory function for tick.core/FieldsLookup.
 */
tick.core.__GT_FieldsLookup = (function tick$core$__GT_FieldsLookup(t){
return (new tick.core.FieldsLookup(t));
});

tick.core.fields = (function tick$core$fields(t){
return tick.core.__GT_FieldsLookup(t);
});
/**
 * Adjust a temporal with an adjuster or field
 */
tick.core.with$ = (function tick$core$with(var_args){
var G__74734 = arguments.length;
switch (G__74734) {
case 2:
return tick.core.with$.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return tick.core.with$.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.with$.cljs$core$IFn$_invoke$arity$2 = (function (t,adj){
return cljc.java_time.temporal.temporal.with$.cljs$core$IFn$_invoke$arity$2(t,adj);
}));

(tick.core.with$.cljs$core$IFn$_invoke$arity$3 = (function (t,fld,new_value){
var temp__5753__auto__ = cljs.core.get.cljs$core$IFn$_invoke$arity$2(tick.core.field_map,fld);
if(cljs.core.truth_(temp__5753__auto__)){
var f = temp__5753__auto__;
return cljc.java_time.temporal.temporal.with$.cljs$core$IFn$_invoke$arity$3(t,f,new_value);
} else {
return null;
}
}));

(tick.core.with$.cljs$lang$maxFixedArity = 3);

tick.core.day_of_week_in_month = (function tick$core$day_of_week_in_month(var_args){
var G__74736 = arguments.length;
switch (G__74736) {
case 2:
return tick.core.day_of_week_in_month.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return tick.core.day_of_week_in_month.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.day_of_week_in_month.cljs$core$IFn$_invoke$arity$2 = (function (ordinal,dow){
return cljc.java_time.temporal.temporal_adjusters.day_of_week_in_month(ordinal,tick.protocols.day_of_week(dow));
}));

(tick.core.day_of_week_in_month.cljs$core$IFn$_invoke$arity$3 = (function (t,ordinal,dow){
return tick.core.with$.cljs$core$IFn$_invoke$arity$2(t,tick.core.day_of_week_in_month.cljs$core$IFn$_invoke$arity$2(ordinal,dow));
}));

(tick.core.day_of_week_in_month.cljs$lang$maxFixedArity = 3);

tick.core.first_day_of_month = (function tick$core$first_day_of_month(var_args){
var G__74738 = arguments.length;
switch (G__74738) {
case 0:
return tick.core.first_day_of_month.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.first_day_of_month.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.first_day_of_month.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljc.java_time.temporal.temporal_adjusters.first_day_of_month();
}));

(tick.core.first_day_of_month.cljs$core$IFn$_invoke$arity$1 = (function (t){
return tick.core.with$.cljs$core$IFn$_invoke$arity$2(t,tick.core.first_day_of_month.cljs$core$IFn$_invoke$arity$0());
}));

(tick.core.first_day_of_month.cljs$lang$maxFixedArity = 1);

tick.core.first_day_of_next_month = (function tick$core$first_day_of_next_month(var_args){
var G__74740 = arguments.length;
switch (G__74740) {
case 0:
return tick.core.first_day_of_next_month.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.first_day_of_next_month.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.first_day_of_next_month.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljc.java_time.temporal.temporal_adjusters.first_day_of_next_month();
}));

(tick.core.first_day_of_next_month.cljs$core$IFn$_invoke$arity$1 = (function (t){
return tick.core.with$.cljs$core$IFn$_invoke$arity$2(t,tick.core.first_day_of_next_month.cljs$core$IFn$_invoke$arity$0());
}));

(tick.core.first_day_of_next_month.cljs$lang$maxFixedArity = 1);

tick.core.first_day_of_next_year = (function tick$core$first_day_of_next_year(var_args){
var G__74742 = arguments.length;
switch (G__74742) {
case 0:
return tick.core.first_day_of_next_year.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.first_day_of_next_year.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.first_day_of_next_year.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljc.java_time.temporal.temporal_adjusters.first_day_of_next_year();
}));

(tick.core.first_day_of_next_year.cljs$core$IFn$_invoke$arity$1 = (function (t){
return tick.core.with$.cljs$core$IFn$_invoke$arity$2(t,tick.core.first_day_of_next_year.cljs$core$IFn$_invoke$arity$0());
}));

(tick.core.first_day_of_next_year.cljs$lang$maxFixedArity = 1);

tick.core.first_day_of_year = (function tick$core$first_day_of_year(var_args){
var G__74744 = arguments.length;
switch (G__74744) {
case 0:
return tick.core.first_day_of_year.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.first_day_of_year.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.first_day_of_year.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljc.java_time.temporal.temporal_adjusters.first_day_of_year();
}));

(tick.core.first_day_of_year.cljs$core$IFn$_invoke$arity$1 = (function (t){
return tick.core.with$.cljs$core$IFn$_invoke$arity$2(t,tick.core.first_day_of_year.cljs$core$IFn$_invoke$arity$0());
}));

(tick.core.first_day_of_year.cljs$lang$maxFixedArity = 1);

tick.core.first_in_month = (function tick$core$first_in_month(var_args){
var G__74746 = arguments.length;
switch (G__74746) {
case 1:
return tick.core.first_in_month.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return tick.core.first_in_month.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.first_in_month.cljs$core$IFn$_invoke$arity$1 = (function (dow){
return cljc.java_time.temporal.temporal_adjusters.first_in_month(tick.protocols.day_of_week(dow));
}));

(tick.core.first_in_month.cljs$core$IFn$_invoke$arity$2 = (function (t,dow){
return tick.core.with$.cljs$core$IFn$_invoke$arity$2(t,tick.core.first_in_month.cljs$core$IFn$_invoke$arity$1(dow));
}));

(tick.core.first_in_month.cljs$lang$maxFixedArity = 2);

tick.core.last_day_of_month = (function tick$core$last_day_of_month(var_args){
var G__74748 = arguments.length;
switch (G__74748) {
case 0:
return tick.core.last_day_of_month.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.last_day_of_month.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.last_day_of_month.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljc.java_time.temporal.temporal_adjusters.last_day_of_month();
}));

(tick.core.last_day_of_month.cljs$core$IFn$_invoke$arity$1 = (function (t){
return tick.core.with$.cljs$core$IFn$_invoke$arity$2(t,tick.core.last_day_of_month.cljs$core$IFn$_invoke$arity$0());
}));

(tick.core.last_day_of_month.cljs$lang$maxFixedArity = 1);

tick.core.last_day_of_year = (function tick$core$last_day_of_year(var_args){
var G__74750 = arguments.length;
switch (G__74750) {
case 0:
return tick.core.last_day_of_year.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.last_day_of_year.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.last_day_of_year.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljc.java_time.temporal.temporal_adjusters.last_day_of_year();
}));

(tick.core.last_day_of_year.cljs$core$IFn$_invoke$arity$1 = (function (t){
return tick.core.with$.cljs$core$IFn$_invoke$arity$2(t,tick.core.last_day_of_year.cljs$core$IFn$_invoke$arity$0());
}));

(tick.core.last_day_of_year.cljs$lang$maxFixedArity = 1);

tick.core.last_in_month = (function tick$core$last_in_month(var_args){
var G__74756 = arguments.length;
switch (G__74756) {
case 1:
return tick.core.last_in_month.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return tick.core.last_in_month.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.last_in_month.cljs$core$IFn$_invoke$arity$1 = (function (dow){
return cljc.java_time.temporal.temporal_adjusters.last_in_month(tick.protocols.day_of_week(dow));
}));

(tick.core.last_in_month.cljs$core$IFn$_invoke$arity$2 = (function (t,dow){
return tick.core.with$.cljs$core$IFn$_invoke$arity$2(t,tick.core.last_in_month.cljs$core$IFn$_invoke$arity$1(dow));
}));

(tick.core.last_in_month.cljs$lang$maxFixedArity = 2);

tick.core.next = (function tick$core$next(var_args){
var G__74758 = arguments.length;
switch (G__74758) {
case 1:
return tick.core.next.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return tick.core.next.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.next.cljs$core$IFn$_invoke$arity$1 = (function (dow){
return cljc.java_time.temporal.temporal_adjusters.next(tick.protocols.day_of_week(dow));
}));

(tick.core.next.cljs$core$IFn$_invoke$arity$2 = (function (t,dow){
return tick.core.with$.cljs$core$IFn$_invoke$arity$2(t,tick.core.next.cljs$core$IFn$_invoke$arity$1(dow));
}));

(tick.core.next.cljs$lang$maxFixedArity = 2);

tick.core.next_or_same = (function tick$core$next_or_same(var_args){
var G__74767 = arguments.length;
switch (G__74767) {
case 1:
return tick.core.next_or_same.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return tick.core.next_or_same.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.next_or_same.cljs$core$IFn$_invoke$arity$1 = (function (dow){
return cljc.java_time.temporal.temporal_adjusters.next_or_same(tick.protocols.day_of_week(dow));
}));

(tick.core.next_or_same.cljs$core$IFn$_invoke$arity$2 = (function (t,dow){
return tick.core.with$.cljs$core$IFn$_invoke$arity$2(t,tick.core.next_or_same.cljs$core$IFn$_invoke$arity$1(dow));
}));

(tick.core.next_or_same.cljs$lang$maxFixedArity = 2);

tick.core.previous = (function tick$core$previous(var_args){
var G__74771 = arguments.length;
switch (G__74771) {
case 1:
return tick.core.previous.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return tick.core.previous.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.previous.cljs$core$IFn$_invoke$arity$1 = (function (dow){
return cljc.java_time.temporal.temporal_adjusters.previous(tick.protocols.day_of_week(dow));
}));

(tick.core.previous.cljs$core$IFn$_invoke$arity$2 = (function (t,dow){
return tick.core.with$.cljs$core$IFn$_invoke$arity$2(t,tick.core.previous.cljs$core$IFn$_invoke$arity$1(dow));
}));

(tick.core.previous.cljs$lang$maxFixedArity = 2);

tick.core.previous_or_same = (function tick$core$previous_or_same(var_args){
var G__74773 = arguments.length;
switch (G__74773) {
case 1:
return tick.core.previous_or_same.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return tick.core.previous_or_same.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.previous_or_same.cljs$core$IFn$_invoke$arity$1 = (function (dow){
return cljc.java_time.temporal.temporal_adjusters.previous_or_same(tick.protocols.day_of_week(dow));
}));

(tick.core.previous_or_same.cljs$core$IFn$_invoke$arity$2 = (function (t,dow){
return tick.core.with$.cljs$core$IFn$_invoke$arity$2(t,tick.core.previous_or_same.cljs$core$IFn$_invoke$arity$1(dow));
}));

(tick.core.previous_or_same.cljs$lang$maxFixedArity = 2);

tick.core.unit_map = cljs.core.PersistentHashMap.fromArrays([new cljs.core.Keyword(null,"nanos","nanos",-1777059258),new cljs.core.Keyword(null,"forever","forever",2103455015),new cljs.core.Keyword(null,"months","months",-45571637),new cljs.core.Keyword(null,"days","days",-1394072564),new cljs.core.Keyword(null,"half-days","half-days",-534088147),new cljs.core.Keyword(null,"micros","micros",420024622),new cljs.core.Keyword(null,"seconds","seconds",-445266194),new cljs.core.Keyword(null,"centuries","centuries",-306410384),new cljs.core.Keyword(null,"decades","decades",-2105076367),new cljs.core.Keyword(null,"hours","hours",58380855),new cljs.core.Keyword(null,"years","years",-1298579689),new cljs.core.Keyword(null,"minutes","minutes",1319166394),new cljs.core.Keyword(null,"eras","eras",1406613306),new cljs.core.Keyword(null,"millennia","millennia",2120675355),new cljs.core.Keyword(null,"weeks","weeks",1844596125),new cljs.core.Keyword(null,"millis","millis",-1338288387)],[cljc.java_time.temporal.chrono_unit.nanos,cljc.java_time.temporal.chrono_unit.forever,cljc.java_time.temporal.chrono_unit.months,cljc.java_time.temporal.chrono_unit.days,cljc.java_time.temporal.chrono_unit.half_days,cljc.java_time.temporal.chrono_unit.micros,cljc.java_time.temporal.chrono_unit.seconds,cljc.java_time.temporal.chrono_unit.centuries,cljc.java_time.temporal.chrono_unit.decades,cljc.java_time.temporal.chrono_unit.hours,cljc.java_time.temporal.chrono_unit.years,cljc.java_time.temporal.chrono_unit.minutes,cljc.java_time.temporal.chrono_unit.eras,cljc.java_time.temporal.chrono_unit.millennia,cljc.java_time.temporal.chrono_unit.weeks,cljc.java_time.temporal.chrono_unit.millis]);
tick.core.reverse_unit_map = cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,cljs.core.map.cljs$core$IFn$_invoke$arity$2(cljs.core.vec,cljs.core.map.cljs$core$IFn$_invoke$arity$2(cljs.core.reverse,tick.core.unit_map)));
tick.core.units = (function tick$core$units(x){
return cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,(function (){var iter__5520__auto__ = (function tick$core$units_$_iter__74779(s__74780){
return (new cljs.core.LazySeq(null,(function (){
var s__74780__$1 = s__74780;
while(true){
var temp__5753__auto__ = cljs.core.seq(s__74780__$1);
if(temp__5753__auto__){
var s__74780__$2 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(s__74780__$2)){
var c__5518__auto__ = cljs.core.chunk_first(s__74780__$2);
var size__5519__auto__ = cljs.core.count(c__5518__auto__);
var b__74782 = cljs.core.chunk_buffer(size__5519__auto__);
if((function (){var i__74781 = (0);
while(true){
if((i__74781 < size__5519__auto__)){
var tu = cljs.core._nth(c__5518__auto__,i__74781);
var k = (tick.core.reverse_unit_map.cljs$core$IFn$_invoke$arity$1 ? tick.core.reverse_unit_map.cljs$core$IFn$_invoke$arity$1(tu) : tick.core.reverse_unit_map.call(null,tu));
if(cljs.core.truth_(k)){
cljs.core.chunk_append(b__74782,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [k,cljc.java_time.temporal.temporal_amount.get(x,tu)], null));

var G__75800 = (i__74781 + (1));
i__74781 = G__75800;
continue;
} else {
var G__75801 = (i__74781 + (1));
i__74781 = G__75801;
continue;
}
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons(cljs.core.chunk(b__74782),tick$core$units_$_iter__74779(cljs.core.chunk_rest(s__74780__$2)));
} else {
return cljs.core.chunk_cons(cljs.core.chunk(b__74782),null);
}
} else {
var tu = cljs.core.first(s__74780__$2);
var k = (tick.core.reverse_unit_map.cljs$core$IFn$_invoke$arity$1 ? tick.core.reverse_unit_map.cljs$core$IFn$_invoke$arity$1(tu) : tick.core.reverse_unit_map.call(null,tu));
if(cljs.core.truth_(k)){
return cljs.core.cons(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [k,cljc.java_time.temporal.temporal_amount.get(x,tu)], null),tick$core$units_$_iter__74779(cljs.core.rest(s__74780__$2)));
} else {
var G__75804 = cljs.core.rest(s__74780__$2);
s__74780__$1 = G__75804;
continue;
}
}
} else {
return null;
}
break;
}
}),null,null));
});
return iter__5520__auto__(cljc.java_time.temporal.temporal_amount.get_units(x));
})());
});
(java.time.Instant.prototype.tick$protocols$ITruncate$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Instant.prototype.tick$protocols$ITruncate$truncate$arity$2 = (function (x,u){
var x__$1 = this;
return cljc.java_time.instant.truncated_to(x__$1,cljs.core.get.cljs$core$IFn$_invoke$arity$2(tick.core.unit_map,u));
}));

(java.time.LocalDateTime.prototype.tick$protocols$ITruncate$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDateTime.prototype.tick$protocols$ITruncate$truncate$arity$2 = (function (x,u){
var x__$1 = this;
return cljc.java_time.local_date_time.truncated_to(x__$1,cljs.core.get.cljs$core$IFn$_invoke$arity$2(tick.core.unit_map,u));
}));

(java.time.ZonedDateTime.prototype.tick$protocols$ITruncate$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.ZonedDateTime.prototype.tick$protocols$ITruncate$truncate$arity$2 = (function (x,u){
var x__$1 = this;
return cljc.java_time.zoned_date_time.truncated_to(x__$1,cljs.core.get.cljs$core$IFn$_invoke$arity$2(tick.core.unit_map,u));
}));

(java.time.OffsetDateTime.prototype.tick$protocols$ITruncate$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.OffsetDateTime.prototype.tick$protocols$ITruncate$truncate$arity$2 = (function (x,u){
var x__$1 = this;
return cljc.java_time.offset_date_time.truncated_to(x__$1,cljs.core.get.cljs$core$IFn$_invoke$arity$2(tick.core.unit_map,u));
}));

(java.time.LocalTime.prototype.tick$protocols$ITruncate$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalTime.prototype.tick$protocols$ITruncate$truncate$arity$2 = (function (x,u){
var x__$1 = this;
return cljc.java_time.local_time.truncated_to(x__$1,cljs.core.get.cljs$core$IFn$_invoke$arity$2(tick.core.unit_map,u));
}));
tick.core.truncate = (function tick$core$truncate(x,u){
if(cljs.core.contains_QMARK_(tick.core.unit_map,u)){
} else {
throw (new Error("Assert failed: (contains? unit-map u)"));
}

return tick.protocols.truncate(x,u);
});
(java.time.Duration.prototype.tick$protocols$IConversion$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Duration.prototype.tick$protocols$IConversion$instant$arity$1 = (function (d){
var d__$1 = this;
return cljc.java_time.instant.of_epoch_milli(tick.protocols.millis(d__$1));
}));

(java.time.Duration.prototype.tick$protocols$IConversion$inst$arity$1 = (function (d){
var d__$1 = this;
return tick.protocols.inst(tick.protocols.instant(d__$1));
}));
(java.time.Duration.prototype.tick$protocols$ITimeLength$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Duration.prototype.tick$protocols$ITimeLength$nanos$arity$1 = (function (d){
var d__$1 = this;
return cljc.java_time.duration.to_nanos(d__$1);
}));

(java.time.Duration.prototype.tick$protocols$ITimeLength$micros$arity$1 = (function (d){
var d__$1 = this;
return (tick.protocols.nanos(d__$1) / (1000));
}));

(java.time.Duration.prototype.tick$protocols$ITimeLength$millis$arity$1 = (function (d){
var d__$1 = this;
return cljc.java_time.duration.to_millis(d__$1);
}));

(java.time.Duration.prototype.tick$protocols$ITimeLength$seconds$arity$1 = (function (d){
var d__$1 = this;
return cljc.java_time.duration.get_seconds(d__$1);
}));

(java.time.Duration.prototype.tick$protocols$ITimeLength$minutes$arity$1 = (function (d){
var d__$1 = this;
return cljc.java_time.duration.to_minutes(d__$1);
}));

(java.time.Duration.prototype.tick$protocols$ITimeLength$hours$arity$1 = (function (d){
var d__$1 = this;
return cljc.java_time.duration.to_hours(d__$1);
}));

(java.time.Duration.prototype.tick$protocols$ITimeLength$days$arity$1 = (function (d){
var d__$1 = this;
return cljc.java_time.duration.to_days(d__$1);
}));

(java.time.Period.prototype.tick$protocols$ITimeLength$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Period.prototype.tick$protocols$ITimeLength$days$arity$1 = (function (p){
var p__$1 = this;
return cljc.java_time.period.get_days(p__$1);
}));

(java.time.Period.prototype.tick$protocols$ITimeLength$months$arity$1 = (function (p){
var p__$1 = this;
return cljc.java_time.period.get_months(p__$1);
}));

(java.time.Period.prototype.tick$protocols$ITimeLength$years$arity$1 = (function (p){
var p__$1 = this;
return cljc.java_time.period.get_years(p__$1);
}));
tick.core.new_duration = (function tick$core$new_duration(n,u){
var unit = (tick.core.unit_map.cljs$core$IFn$_invoke$arity$1 ? tick.core.unit_map.cljs$core$IFn$_invoke$arity$1(u) : tick.core.unit_map.call(null,u));
if(cljs.core.truth_(unit)){
} else {
throw (new Error(["Assert failed: ",["Not a unit: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(u)].join(''),"\n","unit"].join('')));
}

return cljc.java_time.duration.of(n,unit);
});
tick.core.new_period = (function tick$core$new_period(n,u){
var G__74809 = u;
var G__74809__$1 = (((G__74809 instanceof cljs.core.Keyword))?G__74809.fqn:null);
switch (G__74809__$1) {
case "days":
return cljc.java_time.period.of_days(n);

break;
case "weeks":
return cljc.java_time.period.of_weeks(n);

break;
case "months":
return cljc.java_time.period.of_months(n);

break;
case "years":
return cljc.java_time.period.of_years(n);

break;
default:
throw (new Error(["No matching clause: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(G__74809__$1)].join('')));

}
});
/**
 * Takes a java.lang.Long n and returns a duration of n nanoseconds.
 */
tick.core.of_nanos = (function tick$core$of_nanos(n){
return tick.core.new_duration(n,new cljs.core.Keyword(null,"nanos","nanos",-1777059258));
});
/**
 * Takes a java.lang.Long n and returns a duration of n micros.
 */
tick.core.of_micros = (function tick$core$of_micros(n){
return tick.core.new_duration(n,new cljs.core.Keyword(null,"micros","micros",420024622));
});
/**
 * Takes a java.lang.Long n and returns a duration of n micros.
 */
tick.core.of_millis = (function tick$core$of_millis(n){
return tick.core.new_duration(n,new cljs.core.Keyword(null,"millis","millis",-1338288387));
});
/**
 * Takes a java.lang.Long n and returns a duration of n seconds.
 */
tick.core.of_seconds = (function tick$core$of_seconds(n){
return tick.core.new_duration(n,new cljs.core.Keyword(null,"seconds","seconds",-445266194));
});
/**
 * Takes a java.lang.Long n and returns a duration of n minutes.
 */
tick.core.of_minutes = (function tick$core$of_minutes(n){
return tick.core.new_duration(n,new cljs.core.Keyword(null,"minutes","minutes",1319166394));
});
/**
 * Takes a java.lang.Long n and returns a duration of n hours.
 */
tick.core.of_hours = (function tick$core$of_hours(n){
return tick.core.new_duration(n,new cljs.core.Keyword(null,"hours","hours",58380855));
});
/**
 * Takes a java.lang.Long n and returns a period of n days.
 */
tick.core.of_days = (function tick$core$of_days(n){
return tick.core.new_period(n,new cljs.core.Keyword(null,"days","days",-1394072564));
});
/**
 * Takes a java.lang.Long n and returns a period of n months.
 */
tick.core.of_months = (function tick$core$of_months(n){
return tick.core.new_period(n,new cljs.core.Keyword(null,"months","months",-45571637));
});
/**
 * Takes a java.lang.Long n and returns a period of n years.
 */
tick.core.of_years = (function tick$core$of_years(n){
return tick.core.new_period(n,new cljs.core.Keyword(null,"years","years",-1298579689));
});
(java.time.Duration.prototype.tick$protocols$IExtraction$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Duration.prototype.tick$protocols$IExtraction$zone_offset$arity$1 = (function (d){
var d__$1 = this;
return cljc.java_time.zone_offset.of_total_seconds(tick.core.new_duration((1),new cljs.core.Keyword(null,"seconds","seconds",-445266194)));
}));
tick.core.current_clock = (function tick$core$current_clock(){
return tick.core._STAR_clock_STAR_;
});
(java.time.Instant.prototype.tick$protocols$IClock$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Instant.prototype.tick$protocols$IClock$clock$arity$1 = (function (i){
var i__$1 = this;
return cljc.java_time.clock.fixed(i__$1,tick.core.current_zone());
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IClock$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.ZonedDateTime.prototype.tick$protocols$IClock$clock$arity$1 = (function (zdt){
var zdt__$1 = this;
return cljc.java_time.clock.fixed(cljc.java_time.zoned_date_time.to_instant(zdt__$1),cljc.java_time.zoned_date_time.get_zone(zdt__$1));
}));

(tick.protocols.IClock["object"] = true);

(tick.protocols.clock["object"] = (function (o){
return tick.protocols.clock(tick.protocols.zoned_date_time(o));
}));

(java.time.Clock.prototype.tick$protocols$IClock$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Clock.prototype.tick$protocols$IClock$clock$arity$1 = (function (clk){
var clk__$1 = this;
return clk__$1;
}));

(java.time.ZoneId.prototype.tick$protocols$IClock$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.ZoneId.prototype.tick$protocols$IClock$clock$arity$1 = (function (z){
var z__$1 = this;
return cljc.java_time.clock.system(z__$1);
}));

(tick.protocols.IClock["string"] = true);

(tick.protocols.clock["string"] = (function (s){
return tick.protocols.clock(tick.protocols.parse(s));
}));
tick.core.tick_resolution = (function tick$core$tick_resolution(var_args){
var G__74847 = arguments.length;
switch (G__74847) {
case 1:
return tick.core.tick_resolution.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return tick.core.tick_resolution.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.tick_resolution.cljs$core$IFn$_invoke$arity$1 = (function (clk){
return tick.core.tick_resolution.cljs$core$IFn$_invoke$arity$2(clk,tick.core.new_duration((1),new cljs.core.Keyword(null,"seconds","seconds",-445266194)));
}));

(tick.core.tick_resolution.cljs$core$IFn$_invoke$arity$2 = (function (clk,dur){
return cljc.java_time.clock.tick(clk,dur);
}));

(tick.core.tick_resolution.cljs$lang$maxFixedArity = 2);

(java.time.Clock.prototype.tick$protocols$IConversion$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Clock.prototype.tick$protocols$IConversion$instant$arity$1 = (function (clk){
var clk__$1 = this;
return cljc.java_time.clock.instant(clk__$1);
}));
(java.time.Clock.prototype.tick$protocols$IExtraction$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Clock.prototype.tick$protocols$IExtraction$zone$arity$1 = (function (clk){
var clk__$1 = this;
return cljc.java_time.clock.get_zone(clk__$1);
}));
(java.time.Clock.prototype.tick$protocols$ITimeReify$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Clock.prototype.tick$protocols$ITimeReify$in$arity$2 = (function (clk,zone){
var clk__$1 = this;
return cljc.java_time.clock.with_zone(clk__$1,tick.protocols.zone(zone));
}));

/**
* @constructor
 * @implements {cljs.core.IRecord}
 * @implements {tick.protocols.IClock}
 * @implements {cljs.core.IKVReduce}
 * @implements {cljs.core.IEquiv}
 * @implements {cljs.core.IHash}
 * @implements {cljs.core.ICollection}
 * @implements {cljs.core.ICounted}
 * @implements {cljs.core.ISeqable}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.ICloneable}
 * @implements {cljs.core.IDeref}
 * @implements {cljs.core.IPrintWithWriter}
 * @implements {cljs.core.IIterable}
 * @implements {cljs.core.IWithMeta}
 * @implements {cljs.core.IAssociative}
 * @implements {cljs.core.IMap}
 * @implements {cljs.core.ILookup}
*/
tick.core.AtomicClock = (function (_STAR_clock,__meta,__extmap,__hash){
this._STAR_clock = _STAR_clock;
this.__meta = __meta;
this.__extmap = __extmap;
this.__hash = __hash;
this.cljs$lang$protocol_mask$partition0$ = 2230748938;
this.cljs$lang$protocol_mask$partition1$ = 139264;
});
(tick.core.AtomicClock.prototype.cljs$core$ILookup$_lookup$arity$2 = (function (this__5340__auto__,k__5341__auto__){
var self__ = this;
var this__5340__auto____$1 = this;
return this__5340__auto____$1.cljs$core$ILookup$_lookup$arity$3(null,k__5341__auto__,null);
}));

(tick.core.AtomicClock.prototype.cljs$core$ILookup$_lookup$arity$3 = (function (this__5342__auto__,k74857,else__5343__auto__){
var self__ = this;
var this__5342__auto____$1 = this;
var G__74869 = k74857;
var G__74869__$1 = (((G__74869 instanceof cljs.core.Keyword))?G__74869.fqn:null);
switch (G__74869__$1) {
case "*clock":
return self__._STAR_clock;

break;
default:
return cljs.core.get.cljs$core$IFn$_invoke$arity$3(self__.__extmap,k74857,else__5343__auto__);

}
}));

(tick.core.AtomicClock.prototype.cljs$core$IKVReduce$_kv_reduce$arity$3 = (function (this__5360__auto__,f__5361__auto__,init__5362__auto__){
var self__ = this;
var this__5360__auto____$1 = this;
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3((function (ret__5363__auto__,p__74872){
var vec__74873 = p__74872;
var k__5364__auto__ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74873,(0),null);
var v__5365__auto__ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74873,(1),null);
return (f__5361__auto__.cljs$core$IFn$_invoke$arity$3 ? f__5361__auto__.cljs$core$IFn$_invoke$arity$3(ret__5363__auto__,k__5364__auto__,v__5365__auto__) : f__5361__auto__.call(null,ret__5363__auto__,k__5364__auto__,v__5365__auto__));
}),init__5362__auto__,this__5360__auto____$1);
}));

(tick.core.AtomicClock.prototype.cljs$core$IPrintWithWriter$_pr_writer$arity$3 = (function (this__5355__auto__,writer__5356__auto__,opts__5357__auto__){
var self__ = this;
var this__5355__auto____$1 = this;
var pr_pair__5358__auto__ = (function (keyval__5359__auto__){
return cljs.core.pr_sequential_writer(writer__5356__auto__,cljs.core.pr_writer,""," ","",opts__5357__auto__,keyval__5359__auto__);
});
return cljs.core.pr_sequential_writer(writer__5356__auto__,pr_pair__5358__auto__,"#tick.core.AtomicClock{",", ","}",opts__5357__auto__,cljs.core.concat.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"*clock","*clock",-1520020371),self__._STAR_clock],null))], null),self__.__extmap));
}));

(tick.core.AtomicClock.prototype.cljs$core$IIterable$_iterator$arity$1 = (function (G__74856){
var self__ = this;
var G__74856__$1 = this;
return (new cljs.core.RecordIter((0),G__74856__$1,1,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"*clock","*clock",-1520020371)], null),(cljs.core.truth_(self__.__extmap)?cljs.core._iterator(self__.__extmap):cljs.core.nil_iter())));
}));

(tick.core.AtomicClock.prototype.cljs$core$IMeta$_meta$arity$1 = (function (this__5338__auto__){
var self__ = this;
var this__5338__auto____$1 = this;
return self__.__meta;
}));

(tick.core.AtomicClock.prototype.cljs$core$ICloneable$_clone$arity$1 = (function (this__5335__auto__){
var self__ = this;
var this__5335__auto____$1 = this;
return (new tick.core.AtomicClock(self__._STAR_clock,self__.__meta,self__.__extmap,self__.__hash));
}));

(tick.core.AtomicClock.prototype.cljs$core$ICounted$_count$arity$1 = (function (this__5344__auto__){
var self__ = this;
var this__5344__auto____$1 = this;
return (1 + cljs.core.count(self__.__extmap));
}));

(tick.core.AtomicClock.prototype.cljs$core$IHash$_hash$arity$1 = (function (this__5336__auto__){
var self__ = this;
var this__5336__auto____$1 = this;
var h__5152__auto__ = self__.__hash;
if((!((h__5152__auto__ == null)))){
return h__5152__auto__;
} else {
var h__5152__auto____$1 = (function (coll__5337__auto__){
return (1122898333 ^ cljs.core.hash_unordered_coll(coll__5337__auto__));
})(this__5336__auto____$1);
(self__.__hash = h__5152__auto____$1);

return h__5152__auto____$1;
}
}));

(tick.core.AtomicClock.prototype.tick$protocols$IClock$ = cljs.core.PROTOCOL_SENTINEL);

(tick.core.AtomicClock.prototype.tick$protocols$IClock$clock$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.deref(self__._STAR_clock);
}));

(tick.core.AtomicClock.prototype.cljs$core$IEquiv$_equiv$arity$2 = (function (this74858,other74859){
var self__ = this;
var this74858__$1 = this;
return (((!((other74859 == null)))) && ((((this74858__$1.constructor === other74859.constructor)) && (((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this74858__$1._STAR_clock,other74859._STAR_clock)) && (cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this74858__$1.__extmap,other74859.__extmap)))))));
}));

(tick.core.AtomicClock.prototype.cljs$core$IMap$_dissoc$arity$2 = (function (this__5350__auto__,k__5351__auto__){
var self__ = this;
var this__5350__auto____$1 = this;
if(cljs.core.contains_QMARK_(new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"*clock","*clock",-1520020371),null], null), null),k__5351__auto__)){
return cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(cljs.core._with_meta(cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,this__5350__auto____$1),self__.__meta),k__5351__auto__);
} else {
return (new tick.core.AtomicClock(self__._STAR_clock,self__.__meta,cljs.core.not_empty(cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(self__.__extmap,k__5351__auto__)),null));
}
}));

(tick.core.AtomicClock.prototype.cljs$core$IAssociative$_contains_key_QMARK_$arity$2 = (function (this__5347__auto__,k74857){
var self__ = this;
var this__5347__auto____$1 = this;
var G__74889 = k74857;
var G__74889__$1 = (((G__74889 instanceof cljs.core.Keyword))?G__74889.fqn:null);
switch (G__74889__$1) {
case "*clock":
return true;

break;
default:
return cljs.core.contains_QMARK_(self__.__extmap,k74857);

}
}));

(tick.core.AtomicClock.prototype.cljs$core$IAssociative$_assoc$arity$3 = (function (this__5348__auto__,k__5349__auto__,G__74856){
var self__ = this;
var this__5348__auto____$1 = this;
var pred__74894 = cljs.core.keyword_identical_QMARK_;
var expr__74895 = k__5349__auto__;
if(cljs.core.truth_((pred__74894.cljs$core$IFn$_invoke$arity$2 ? pred__74894.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"*clock","*clock",-1520020371),expr__74895) : pred__74894.call(null,new cljs.core.Keyword(null,"*clock","*clock",-1520020371),expr__74895)))){
return (new tick.core.AtomicClock(G__74856,self__.__meta,self__.__extmap,null));
} else {
return (new tick.core.AtomicClock(self__._STAR_clock,self__.__meta,cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(self__.__extmap,k__5349__auto__,G__74856),null));
}
}));

(tick.core.AtomicClock.prototype.cljs$core$ISeqable$_seq$arity$1 = (function (this__5353__auto__){
var self__ = this;
var this__5353__auto____$1 = this;
return cljs.core.seq(cljs.core.concat.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [(new cljs.core.MapEntry(new cljs.core.Keyword(null,"*clock","*clock",-1520020371),self__._STAR_clock,null))], null),self__.__extmap));
}));

(tick.core.AtomicClock.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (this__5339__auto__,G__74856){
var self__ = this;
var this__5339__auto____$1 = this;
return (new tick.core.AtomicClock(self__._STAR_clock,G__74856,self__.__extmap,self__.__hash));
}));

(tick.core.AtomicClock.prototype.cljs$core$ICollection$_conj$arity$2 = (function (this__5345__auto__,entry__5346__auto__){
var self__ = this;
var this__5345__auto____$1 = this;
if(cljs.core.vector_QMARK_(entry__5346__auto__)){
return this__5345__auto____$1.cljs$core$IAssociative$_assoc$arity$3(null,cljs.core._nth(entry__5346__auto__,(0)),cljs.core._nth(entry__5346__auto__,(1)));
} else {
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3(cljs.core._conj,this__5345__auto____$1,entry__5346__auto__);
}
}));

(tick.core.AtomicClock.prototype.cljs$core$IDeref$_deref$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return tick.protocols.instant(cljs.core.deref(self__._STAR_clock));
}));

(tick.core.AtomicClock.getBasis = (function (){
return new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"*clock","*clock",120511156,null)], null);
}));

(tick.core.AtomicClock.cljs$lang$type = true);

(tick.core.AtomicClock.cljs$lang$ctorPrSeq = (function (this__5386__auto__){
return (new cljs.core.List(null,"tick.core/AtomicClock",null,(1),null));
}));

(tick.core.AtomicClock.cljs$lang$ctorPrWriter = (function (this__5386__auto__,writer__5387__auto__){
return cljs.core._write(writer__5387__auto__,"tick.core/AtomicClock");
}));

/**
 * Positional factory function for tick.core/AtomicClock.
 */
tick.core.__GT_AtomicClock = (function tick$core$__GT_AtomicClock(_STAR_clock){
return (new tick.core.AtomicClock(_STAR_clock,null,null,null));
});

/**
 * Factory function for tick.core/AtomicClock, taking a map of keywords to field values.
 */
tick.core.map__GT_AtomicClock = (function tick$core$map__GT_AtomicClock(G__74862){
var extmap__5382__auto__ = (function (){var G__74911 = cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(G__74862,new cljs.core.Keyword(null,"*clock","*clock",-1520020371));
if(cljs.core.record_QMARK_(G__74862)){
return cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,G__74911);
} else {
return G__74911;
}
})();
return (new tick.core.AtomicClock(new cljs.core.Keyword(null,"*clock","*clock",-1520020371).cljs$core$IFn$_invoke$arity$1(G__74862),null,cljs.core.not_empty(extmap__5382__auto__),null));
});

tick.core.atom = (function tick$core$atom(var_args){
var G__74919 = arguments.length;
switch (G__74919) {
case 1:
return tick.core.atom.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 0:
return tick.core.atom.cljs$core$IFn$_invoke$arity$0();

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.atom.cljs$core$IFn$_invoke$arity$1 = (function (clk){
return tick.core.__GT_AtomicClock(cljs.core.atom.cljs$core$IFn$_invoke$arity$1(clk));
}));

(tick.core.atom.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.core.atom.cljs$core$IFn$_invoke$arity$1(tick.core.current_clock());
}));

(tick.core.atom.cljs$lang$maxFixedArity = 1);

tick.core.swap_BANG_ = (function tick$core$swap_BANG_(var_args){
var args__5772__auto__ = [];
var len__5766__auto___75818 = arguments.length;
var i__5767__auto___75819 = (0);
while(true){
if((i__5767__auto___75819 < len__5766__auto___75818)){
args__5772__auto__.push((arguments[i__5767__auto___75819]));

var G__75820 = (i__5767__auto___75819 + (1));
i__5767__auto___75819 = G__75820;
continue;
} else {
}
break;
}

var argseq__5773__auto__ = ((((2) < args__5772__auto__.length))?(new cljs.core.IndexedSeq(args__5772__auto__.slice((2)),(0),null)):null);
return tick.core.swap_BANG_.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),(arguments[(1)]),argseq__5773__auto__);
});

(tick.core.swap_BANG_.cljs$core$IFn$_invoke$arity$variadic = (function (at,f,args){
return cljs.core.apply.cljs$core$IFn$_invoke$arity$4(cljs.core.swap_BANG_,new cljs.core.Keyword(null,"*clock","*clock",-1520020371).cljs$core$IFn$_invoke$arity$1(at),f,args);
}));

(tick.core.swap_BANG_.cljs$lang$maxFixedArity = (2));

/** @this {Function} */
(tick.core.swap_BANG_.cljs$lang$applyTo = (function (seq74926){
var G__74927 = cljs.core.first(seq74926);
var seq74926__$1 = cljs.core.next(seq74926);
var G__74928 = cljs.core.first(seq74926__$1);
var seq74926__$2 = cljs.core.next(seq74926__$1);
var self__5751__auto__ = this;
return self__5751__auto__.cljs$core$IFn$_invoke$arity$variadic(G__74927,G__74928,seq74926__$2);
}));

tick.core.swap_vals_BANG_ = (function tick$core$swap_vals_BANG_(var_args){
var args__5772__auto__ = [];
var len__5766__auto___75821 = arguments.length;
var i__5767__auto___75822 = (0);
while(true){
if((i__5767__auto___75822 < len__5766__auto___75821)){
args__5772__auto__.push((arguments[i__5767__auto___75822]));

var G__75823 = (i__5767__auto___75822 + (1));
i__5767__auto___75822 = G__75823;
continue;
} else {
}
break;
}

var argseq__5773__auto__ = ((((2) < args__5772__auto__.length))?(new cljs.core.IndexedSeq(args__5772__auto__.slice((2)),(0),null)):null);
return tick.core.swap_vals_BANG_.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),(arguments[(1)]),argseq__5773__auto__);
});

(tick.core.swap_vals_BANG_.cljs$core$IFn$_invoke$arity$variadic = (function (at,f,args){
return cljs.core.apply.cljs$core$IFn$_invoke$arity$4(cljs.core.swap_vals_BANG_,new cljs.core.Keyword(null,"*clock","*clock",-1520020371).cljs$core$IFn$_invoke$arity$1(at),f,args);
}));

(tick.core.swap_vals_BANG_.cljs$lang$maxFixedArity = (2));

/** @this {Function} */
(tick.core.swap_vals_BANG_.cljs$lang$applyTo = (function (seq74940){
var G__74941 = cljs.core.first(seq74940);
var seq74940__$1 = cljs.core.next(seq74940);
var G__74942 = cljs.core.first(seq74940__$1);
var seq74940__$2 = cljs.core.next(seq74940__$1);
var self__5751__auto__ = this;
return self__5751__auto__.cljs$core$IFn$_invoke$arity$variadic(G__74941,G__74942,seq74940__$2);
}));

tick.core.compare_and_set_BANG_ = (function tick$core$compare_and_set_BANG_(at,oldval,newval){
return cljs.core.compare_and_set_BANG_(new cljs.core.Keyword(null,"*clock","*clock",-1520020371).cljs$core$IFn$_invoke$arity$1(at),oldval,newval);
});
tick.core.reset_BANG_ = (function tick$core$reset_BANG_(at,newval){
return cljs.core.reset_BANG_(new cljs.core.Keyword(null,"*clock","*clock",-1520020371).cljs$core$IFn$_invoke$arity$1(at),newval);
});
tick.core.reset_vals_BANG_ = (function tick$core$reset_vals_BANG_(at,newval){
return cljs.core.reset_vals_BANG_(new cljs.core.Keyword(null,"*clock","*clock",-1520020371).cljs$core$IFn$_invoke$arity$1(at),newval);
});
(java.time.Duration.prototype.tick$protocols$ITimeArithmetic$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Duration.prototype.tick$protocols$ITimeArithmetic$_PLUS_$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.duration.plus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.Duration.prototype.tick$protocols$ITimeArithmetic$_$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.duration.minus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.Period.prototype.tick$protocols$ITimeArithmetic$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Period.prototype.tick$protocols$ITimeArithmetic$_PLUS_$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.period.plus(t__$1,d);
}));

(java.time.Period.prototype.tick$protocols$ITimeArithmetic$_$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.period.minus(t__$1,d);
}));
/**
 * Return the duration as a negative duration
 */
tick.core.negated = (function tick$core$negated(d){
return cljc.java_time.duration.negated(d);
});
/**
 * Sum amounts of time
 */
tick.core._PLUS_ = (function tick$core$_PLUS_(var_args){
var G__74976 = arguments.length;
switch (G__74976) {
case 0:
return tick.core._PLUS_.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core._PLUS_.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
var args_arr__5791__auto__ = [];
var len__5766__auto___75827 = arguments.length;
var i__5767__auto___75828 = (0);
while(true){
if((i__5767__auto___75828 < len__5766__auto___75827)){
args_arr__5791__auto__.push((arguments[i__5767__auto___75828]));

var G__75829 = (i__5767__auto___75828 + (1));
i__5767__auto___75828 = G__75829;
continue;
} else {
}
break;
}

var argseq__5792__auto__ = (new cljs.core.IndexedSeq(args_arr__5791__auto__.slice((1)),(0),null));
return tick.core._PLUS_.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__5792__auto__);

}
});

(tick.core._PLUS_.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljc.java_time.duration.zero;
}));

(tick.core._PLUS_.cljs$core$IFn$_invoke$arity$1 = (function (arg){
return arg;
}));

(tick.core._PLUS_.cljs$core$IFn$_invoke$arity$variadic = (function (arg,args){
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3(tick.protocols._PLUS_,arg,args);
}));

/** @this {Function} */
(tick.core._PLUS_.cljs$lang$applyTo = (function (seq74974){
var G__74975 = cljs.core.first(seq74974);
var seq74974__$1 = cljs.core.next(seq74974);
var self__5751__auto__ = this;
return self__5751__auto__.cljs$core$IFn$_invoke$arity$variadic(G__74975,seq74974__$1);
}));

(tick.core._PLUS_.cljs$lang$maxFixedArity = (1));

/**
 * Subtract amounts of time.
 */
tick.core._ = (function tick$core$_(var_args){
var G__74992 = arguments.length;
switch (G__74992) {
case 0:
return tick.core._.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core._.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
var args_arr__5791__auto__ = [];
var len__5766__auto___75832 = arguments.length;
var i__5767__auto___75833 = (0);
while(true){
if((i__5767__auto___75833 < len__5766__auto___75832)){
args_arr__5791__auto__.push((arguments[i__5767__auto___75833]));

var G__75834 = (i__5767__auto___75833 + (1));
i__5767__auto___75833 = G__75834;
continue;
} else {
}
break;
}

var argseq__5792__auto__ = (new cljs.core.IndexedSeq(args_arr__5791__auto__.slice((1)),(0),null));
return tick.core._.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__5792__auto__);

}
});

(tick.core._.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljc.java_time.duration.zero;
}));

(tick.core._.cljs$core$IFn$_invoke$arity$1 = (function (arg){
return tick.core.negated(arg);
}));

(tick.core._.cljs$core$IFn$_invoke$arity$variadic = (function (arg,args){
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3(tick.protocols._,arg,args);
}));

/** @this {Function} */
(tick.core._.cljs$lang$applyTo = (function (seq74990){
var G__74991 = cljs.core.first(seq74990);
var seq74990__$1 = cljs.core.next(seq74990);
var self__5751__auto__ = this;
return self__5751__auto__.cljs$core$IFn$_invoke$arity$variadic(G__74991,seq74990__$1);
}));

(tick.core._.cljs$lang$maxFixedArity = (1));

(java.time.YearMonth.prototype.tick$protocols$ITimeShift$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.YearMonth.prototype.tick$protocols$ITimeShift$forward_number$arity$2 = (function (t,n){
var t__$1 = this;
return cljc.java_time.year_month.plus_months(t__$1,n);
}));

(java.time.YearMonth.prototype.tick$protocols$ITimeShift$backward_number$arity$2 = (function (t,n){
var t__$1 = this;
return cljc.java_time.year_month.minus_months(t__$1,n);
}));

(java.time.YearMonth.prototype.tick$protocols$ITimeShift$forward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.year_month.plus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.YearMonth.prototype.tick$protocols$ITimeShift$backward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.year_month.minus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.Year.prototype.tick$protocols$ITimeShift$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Year.prototype.tick$protocols$ITimeShift$forward_number$arity$2 = (function (t,n){
var t__$1 = this;
return cljc.java_time.year.plus_years(t__$1,n);
}));

(java.time.Year.prototype.tick$protocols$ITimeShift$backward_number$arity$2 = (function (t,n){
var t__$1 = this;
return cljc.java_time.year.minus_years(t__$1,n);
}));

(java.time.Year.prototype.tick$protocols$ITimeShift$forward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.year.plus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.Year.prototype.tick$protocols$ITimeShift$backward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.year.minus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeShift$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeShift$forward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.zoned_date_time.plus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeShift$backward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.zoned_date_time.minus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.Instant.prototype.tick$protocols$ITimeShift$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Instant.prototype.tick$protocols$ITimeShift$forward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.instant.plus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.Instant.prototype.tick$protocols$ITimeShift$backward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.instant.minus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(Date.prototype.tick$protocols$ITimeShift$ = cljs.core.PROTOCOL_SENTINEL);

(Date.prototype.tick$protocols$ITimeShift$forward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return tick.protocols.forward_duration(tick.protocols.instant(t__$1),d);
}));

(Date.prototype.tick$protocols$ITimeShift$backward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return tick.protocols.backward_duration(tick.protocols.instant(t__$1),d);
}));

(java.time.LocalDate.prototype.tick$protocols$ITimeShift$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDate.prototype.tick$protocols$ITimeShift$forward_number$arity$2 = (function (t,n){
var t__$1 = this;
return cljc.java_time.local_date.plus_days(t__$1,n);
}));

(java.time.LocalDate.prototype.tick$protocols$ITimeShift$backward_number$arity$2 = (function (t,n){
var t__$1 = this;
return cljc.java_time.local_date.minus_days(t__$1,n);
}));

(java.time.LocalDate.prototype.tick$protocols$ITimeShift$forward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.local_date.plus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.LocalDate.prototype.tick$protocols$ITimeShift$backward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.local_date.minus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.LocalTime.prototype.tick$protocols$ITimeShift$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalTime.prototype.tick$protocols$ITimeShift$forward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.local_time.plus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.LocalTime.prototype.tick$protocols$ITimeShift$backward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.local_time.minus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.OffsetDateTime.prototype.tick$protocols$ITimeShift$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.OffsetDateTime.prototype.tick$protocols$ITimeShift$forward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.offset_date_time.plus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.OffsetDateTime.prototype.tick$protocols$ITimeShift$backward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.offset_date_time.minus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.LocalDateTime.prototype.tick$protocols$ITimeShift$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDateTime.prototype.tick$protocols$ITimeShift$forward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.local_date_time.plus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.LocalDateTime.prototype.tick$protocols$ITimeShift$backward_duration$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.local_date_time.minus.cljs$core$IFn$_invoke$arity$2(t__$1,d);
}));

(java.time.Clock.prototype.tick$protocols$ITimeShift$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Clock.prototype.tick$protocols$ITimeShift$forward_duration$arity$2 = (function (clk,d){
var clk__$1 = this;
return cljc.java_time.clock.offset(clk__$1,d);
}));

(java.time.Clock.prototype.tick$protocols$ITimeShift$backward_duration$arity$2 = (function (clk,d){
var clk__$1 = this;
return cljc.java_time.clock.offset(clk__$1,tick.core.negated(d));
}));
tick.core._GT__GT_ = (function tick$core$_GT__GT_(t,n_or_d){
if(typeof n_or_d === 'number'){
return tick.protocols.forward_number(t,n_or_d);
} else {
return tick.protocols.forward_duration(t,n_or_d);
}
});
tick.core._LT__LT_ = (function tick$core$_LT__LT_(t,n_or_d){
if(typeof n_or_d === 'number'){
return tick.protocols.backward_number(t,n_or_d);
} else {
return tick.protocols.backward_duration(t,n_or_d);
}
});
(java.time.Instant.prototype.tick$protocols$ITimeRangeable$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Instant.prototype.tick$protocols$ITimeRangeable$range$arity$1 = (function (from){
var from__$1 = this;
return cljs.core.iterate((function (p1__75043_SHARP_){
return cljc.java_time.instant.plus_seconds(p1__75043_SHARP_,(1));
}),from__$1);
}));

(java.time.Instant.prototype.tick$protocols$ITimeRangeable$range$arity$2 = (function (from,to){
var from__$1 = this;
var G__75054 = cljs.core.iterate((function (p1__75044_SHARP_){
return cljc.java_time.instant.plus_seconds(p1__75044_SHARP_,(1));
}),from__$1);
if(cljs.core.truth_(to)){
return cljs.core.take_while.cljs$core$IFn$_invoke$arity$2((function (p1__75046_SHARP_){
return tick.protocols._LT_(p1__75046_SHARP_,to);
}),G__75054);
} else {
return G__75054;
}
}));

(java.time.Instant.prototype.tick$protocols$ITimeRangeable$range$arity$3 = (function (from,to,step){
var from__$1 = this;
var G__75057 = cljs.core.iterate((function (p1__75048_SHARP_){
return cljc.java_time.instant.plus.cljs$core$IFn$_invoke$arity$2(p1__75048_SHARP_,step);
}),from__$1);
if(cljs.core.truth_(to)){
return cljs.core.take_while.cljs$core$IFn$_invoke$arity$2((function (p1__75049_SHARP_){
return tick.protocols._LT_(p1__75049_SHARP_,to);
}),G__75057);
} else {
return G__75057;
}
}));
(java.time.ZonedDateTime.prototype.tick$protocols$ITimeRangeable$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeRangeable$range$arity$1 = (function (from){
var from__$1 = this;
return cljs.core.iterate((function (p1__75060_SHARP_){
return cljc.java_time.zoned_date_time.plus_seconds(p1__75060_SHARP_,(1));
}),from__$1);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeRangeable$range$arity$2 = (function (from,to){
var from__$1 = this;
var G__75069 = cljs.core.iterate((function (p1__75061_SHARP_){
return cljc.java_time.zoned_date_time.plus_seconds(p1__75061_SHARP_,(1));
}),from__$1);
if(cljs.core.truth_(to)){
return cljs.core.take_while.cljs$core$IFn$_invoke$arity$2((function (p1__75062_SHARP_){
return tick.protocols._LT_(p1__75062_SHARP_,to);
}),G__75069);
} else {
return G__75069;
}
}));

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeRangeable$range$arity$3 = (function (from,to,step){
var from__$1 = this;
var G__75072 = cljs.core.iterate((function (p1__75063_SHARP_){
return cljc.java_time.zoned_date_time.plus.cljs$core$IFn$_invoke$arity$2(p1__75063_SHARP_,step);
}),from__$1);
if(cljs.core.truth_(to)){
return cljs.core.take_while.cljs$core$IFn$_invoke$arity$2((function (p1__75064_SHARP_){
return tick.protocols._LT_(p1__75064_SHARP_,to);
}),G__75072);
} else {
return G__75072;
}
}));
(java.time.LocalDate.prototype.tick$protocols$ITimeRangeable$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDate.prototype.tick$protocols$ITimeRangeable$range$arity$1 = (function (from){
var from__$1 = this;
return cljs.core.iterate((function (p1__75075_SHARP_){
return cljc.java_time.local_date.plus_days(p1__75075_SHARP_,(1));
}),from__$1);
}));

(java.time.LocalDate.prototype.tick$protocols$ITimeRangeable$range$arity$2 = (function (from,to){
var from__$1 = this;
var G__75086 = cljs.core.iterate((function (p1__75077_SHARP_){
return cljc.java_time.local_date.plus_days(p1__75077_SHARP_,(1));
}),from__$1);
if(cljs.core.truth_(to)){
return cljs.core.take_while.cljs$core$IFn$_invoke$arity$2((function (p1__75079_SHARP_){
return tick.protocols._LT_(p1__75079_SHARP_,to);
}),G__75086);
} else {
return G__75086;
}
}));

(java.time.LocalDate.prototype.tick$protocols$ITimeRangeable$range$arity$3 = (function (from,to,step){
var from__$1 = this;
var G__75091 = cljs.core.iterate((function (p1__75080_SHARP_){
return cljc.java_time.local_date.plus.cljs$core$IFn$_invoke$arity$2(p1__75080_SHARP_,step);
}),from__$1);
if(cljs.core.truth_(to)){
return cljs.core.take_while.cljs$core$IFn$_invoke$arity$2((function (p1__75081_SHARP_){
return tick.protocols._LT_(p1__75081_SHARP_,to);
}),G__75091);
} else {
return G__75091;
}
}));
tick.core.inc = (function tick$core$inc(t){
return tick.protocols.forward_number(t,(1));
});
tick.core.dec = (function tick$core$dec(t){
return tick.protocols.backward_number(t,(1));
});
tick.core.tomorrow = (function tick$core$tomorrow(){
return tick.protocols.forward_number(tick.core.today(),(1));
});
tick.core.yesterday = (function tick$core$yesterday(){
return tick.protocols.backward_number(tick.core.today(),(1));
});
(java.time.LocalDateTime.prototype.tick$protocols$ITimeRangeable$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDateTime.prototype.tick$protocols$ITimeRangeable$range$arity$1 = (function (from){
var from__$1 = this;
return cljs.core.iterate((function (p1__75102_SHARP_){
return cljc.java_time.local_date_time.plus_seconds(p1__75102_SHARP_,(1));
}),from__$1);
}));

(java.time.LocalDateTime.prototype.tick$protocols$ITimeRangeable$range$arity$2 = (function (from,to){
var from__$1 = this;
var G__75113 = cljs.core.iterate((function (p1__75103_SHARP_){
return cljc.java_time.local_date_time.plus_seconds(p1__75103_SHARP_,(1));
}),from__$1);
if(cljs.core.truth_(to)){
return cljs.core.take_while.cljs$core$IFn$_invoke$arity$2((function (p1__75105_SHARP_){
return tick.protocols._LT_(p1__75105_SHARP_,to);
}),G__75113);
} else {
return G__75113;
}
}));

(java.time.LocalDateTime.prototype.tick$protocols$ITimeRangeable$range$arity$3 = (function (from,to,step){
var from__$1 = this;
var G__75118 = cljs.core.iterate((function (p1__75107_SHARP_){
return cljc.java_time.local_date_time.plus.cljs$core$IFn$_invoke$arity$2(p1__75107_SHARP_,step);
}),from__$1);
if(cljs.core.truth_(to)){
return cljs.core.take_while.cljs$core$IFn$_invoke$arity$2((function (p1__75108_SHARP_){
return tick.protocols._LT_(p1__75108_SHARP_,to);
}),G__75118);
} else {
return G__75118;
}
}));
(java.time.YearMonth.prototype.tick$protocols$ITimeRangeable$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.YearMonth.prototype.tick$protocols$ITimeRangeable$range$arity$1 = (function (from){
var from__$1 = this;
return cljs.core.iterate((function (p1__75122_SHARP_){
return cljc.java_time.year_month.plus_months(p1__75122_SHARP_,(1));
}),from__$1);
}));

(java.time.YearMonth.prototype.tick$protocols$ITimeRangeable$range$arity$2 = (function (from,to){
var from__$1 = this;
var G__75134 = cljs.core.iterate((function (p1__75124_SHARP_){
return cljc.java_time.year_month.plus_months(p1__75124_SHARP_,(1));
}),from__$1);
if(cljs.core.truth_(to)){
return cljs.core.take_while.cljs$core$IFn$_invoke$arity$2((function (p1__75125_SHARP_){
return tick.protocols._LT_(p1__75125_SHARP_,to);
}),G__75134);
} else {
return G__75134;
}
}));

(java.time.YearMonth.prototype.tick$protocols$ITimeRangeable$range$arity$3 = (function (from,to,step){
var from__$1 = this;
var G__75138 = cljs.core.iterate((function (p1__75126_SHARP_){
return cljc.java_time.year_month.plus.cljs$core$IFn$_invoke$arity$2(p1__75126_SHARP_,step);
}),from__$1);
if(cljs.core.truth_(to)){
return cljs.core.take_while.cljs$core$IFn$_invoke$arity$2((function (p1__75127_SHARP_){
return tick.protocols._LT_(p1__75127_SHARP_,to);
}),G__75138);
} else {
return G__75138;
}
}));
(java.time.Year.prototype.tick$protocols$ITimeRangeable$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Year.prototype.tick$protocols$ITimeRangeable$range$arity$1 = (function (from){
var from__$1 = this;
return cljs.core.iterate((function (p1__75142_SHARP_){
return cljc.java_time.year.plus_years(p1__75142_SHARP_,(1));
}),from__$1);
}));

(java.time.Year.prototype.tick$protocols$ITimeRangeable$range$arity$2 = (function (from,to){
var from__$1 = this;
var G__75153 = cljs.core.iterate((function (p1__75143_SHARP_){
return cljc.java_time.year.plus_years(p1__75143_SHARP_,(1));
}),from__$1);
if(cljs.core.truth_(to)){
return cljs.core.take_while.cljs$core$IFn$_invoke$arity$2((function (p1__75144_SHARP_){
return tick.protocols._LT_(p1__75144_SHARP_,to);
}),G__75153);
} else {
return G__75153;
}
}));

(java.time.Year.prototype.tick$protocols$ITimeRangeable$range$arity$3 = (function (from,to,step){
var from__$1 = this;
var G__75158 = cljs.core.iterate((function (p1__75145_SHARP_){
return cljc.java_time.year.plus.cljs$core$IFn$_invoke$arity$2(p1__75145_SHARP_,step);
}),from__$1);
if(cljs.core.truth_(to)){
return cljs.core.take_while.cljs$core$IFn$_invoke$arity$2((function (p1__75146_SHARP_){
return tick.protocols._LT_(p1__75146_SHARP_,to);
}),G__75158);
} else {
return G__75158;
}
}));
(tick.protocols.IDivisible["string"] = true);

(tick.protocols.divide["string"] = (function (s,d){
return tick.protocols.divide(tick.protocols.parse(s),d);
}));
(tick.protocols.IDivisibleDuration["number"] = true);

(tick.protocols.divide_duration["number"] = (function (n,duration){
return cljc.java_time.duration.divided_by(duration,n);
}));

(java.time.Duration.prototype.tick$protocols$IDivisibleDuration$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Duration.prototype.tick$protocols$IDivisibleDuration$divide_duration$arity$2 = (function (divisor,duration){
var divisor__$1 = this;
return (cljc.java_time.duration.get_seconds(duration) / cljc.java_time.duration.get_seconds(divisor__$1));
}));
(java.time.Duration.prototype.tick$protocols$IDivisible$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Duration.prototype.tick$protocols$IDivisible$divide$arity$2 = (function (d,x){
var d__$1 = this;
return tick.protocols.divide_duration(x,d__$1);
}));
tick.core.duration = (function tick$core$duration(x){
return cljc.java_time.duration.between(tick.protocols.beginning(x),tick.protocols.end(x));
});
(java.time.LocalDate.prototype.tick$protocols$IBetween$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDate.prototype.tick$protocols$IBetween$between$arity$2 = (function (v1,v2){
var v1__$1 = this;
return cljc.java_time.period.between(v1__$1,tick.protocols.date(v2));
}));

(java.time.LocalTime.prototype.tick$protocols$IBetween$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalTime.prototype.tick$protocols$IBetween$between$arity$2 = (function (v1,v2){
var v1__$1 = this;
return cljc.java_time.duration.between(v1__$1,tick.protocols.time(v2));
}));

(java.time.ZonedDateTime.prototype.tick$protocols$IBetween$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.ZonedDateTime.prototype.tick$protocols$IBetween$between$arity$2 = (function (v1,v2){
var v1__$1 = this;
return cljc.java_time.duration.between(v1__$1,tick.protocols.zoned_date_time(v2));
}));

(java.time.LocalDateTime.prototype.tick$protocols$IBetween$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDateTime.prototype.tick$protocols$IBetween$between$arity$2 = (function (v1,v2){
var v1__$1 = this;
return cljc.java_time.duration.between(v1__$1,tick.protocols.date_time(v2));
}));

(java.time.Instant.prototype.tick$protocols$IBetween$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Instant.prototype.tick$protocols$IBetween$between$arity$2 = (function (v1,v2){
var v1__$1 = this;
return cljc.java_time.duration.between(v1__$1,tick.protocols.instant(v2));
}));

(java.time.OffsetDateTime.prototype.tick$protocols$IBetween$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.OffsetDateTime.prototype.tick$protocols$IBetween$between$arity$2 = (function (v1,v2){
var v1__$1 = this;
return cljc.java_time.duration.between(v1__$1,tick.protocols.offset_date_time(v2));
}));

(tick.protocols.IBetween["string"] = true);

(tick.protocols.between["string"] = (function (v1,v2){
return tick.protocols.between(tick.protocols.parse(v1),tick.protocols.parse(v2));
}));

(Date.prototype.tick$protocols$IBetween$ = cljs.core.PROTOCOL_SENTINEL);

(Date.prototype.tick$protocols$IBetween$between$arity$2 = (function (x,y){
var x__$1 = this;
return tick.protocols.between(tick.protocols.instant(x__$1),tick.protocols.instant(y));
}));
/**
 * Does the span of time contain the given event? If the given event
 *   is itself a span, then t must wholly contain the beginning and end
 *   of the event.
 */
tick.core.coincident_QMARK_ = (function tick$core$coincident_QMARK_(t,event){
return ((cljs.core.not_EQ_.cljs$core$IFn$_invoke$arity$2((1),cljs.core.compare(tick.protocols.beginning(t),tick.protocols.beginning(event)))) && (cljs.core.not_EQ_.cljs$core$IFn$_invoke$arity$2((1),cljs.core.compare(tick.protocols.end(event),tick.protocols.end(t)))));
});
(tick.protocols.ITimeSpan["null"] = true);

(tick.protocols.beginning["null"] = (function (_){
return null;
}));

(tick.protocols.end["null"] = (function (_){
return null;
}));

(java.time.YearMonth.prototype.tick$protocols$ITimeSpan$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.YearMonth.prototype.tick$protocols$ITimeSpan$beginning$arity$1 = (function (ym){
var ym__$1 = this;
return tick.protocols.beginning(cljc.java_time.year_month.at_day(ym__$1,(1)));
}));

(java.time.YearMonth.prototype.tick$protocols$ITimeSpan$end$arity$1 = (function (ym){
var ym__$1 = this;
return tick.protocols.end(cljc.java_time.year_month.at_end_of_month(ym__$1));
}));

(java.time.Year.prototype.tick$protocols$ITimeSpan$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Year.prototype.tick$protocols$ITimeSpan$beginning$arity$1 = (function (year){
var year__$1 = this;
return tick.protocols.beginning(cljc.java_time.year.at_month(year__$1,(1)));
}));

(java.time.Year.prototype.tick$protocols$ITimeSpan$end$arity$1 = (function (year){
var year__$1 = this;
return tick.protocols.end(cljc.java_time.year.at_month(year__$1,(12)));
}));

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeSpan$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeSpan$beginning$arity$1 = (function (i){
var i__$1 = this;
return i__$1;
}));

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeSpan$end$arity$1 = (function (i){
var i__$1 = this;
return i__$1;
}));

(java.time.Instant.prototype.tick$protocols$ITimeSpan$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Instant.prototype.tick$protocols$ITimeSpan$beginning$arity$1 = (function (i){
var i__$1 = this;
return i__$1;
}));

(java.time.Instant.prototype.tick$protocols$ITimeSpan$end$arity$1 = (function (i){
var i__$1 = this;
return i__$1;
}));

(Date.prototype.tick$protocols$ITimeSpan$ = cljs.core.PROTOCOL_SENTINEL);

(Date.prototype.tick$protocols$ITimeSpan$beginning$arity$1 = (function (i){
var i__$1 = this;
return tick.protocols.instant(i__$1);
}));

(Date.prototype.tick$protocols$ITimeSpan$end$arity$1 = (function (i){
var i__$1 = this;
return tick.protocols.instant(i__$1);
}));

(java.time.LocalDate.prototype.tick$protocols$ITimeSpan$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDate.prototype.tick$protocols$ITimeSpan$beginning$arity$1 = (function (date){
var date__$1 = this;
return cljc.java_time.local_date.at_start_of_day.cljs$core$IFn$_invoke$arity$1(date__$1);
}));

(java.time.LocalDate.prototype.tick$protocols$ITimeSpan$end$arity$1 = (function (date){
var date__$1 = this;
return cljc.java_time.local_date.at_start_of_day.cljs$core$IFn$_invoke$arity$1(tick.core.inc(date__$1));
}));

(tick.protocols.ITimeSpan["number"] = true);

(tick.protocols.beginning["number"] = (function (n){
return tick.protocols.beginning(tick.protocols.time(n));
}));

(tick.protocols.end["number"] = (function (n){
return tick.protocols.end(tick.protocols.time(n));
}));

(tick.protocols.ITimeSpan["string"] = true);

(tick.protocols.beginning["string"] = (function (s){
return tick.protocols.beginning(tick.protocols.parse(s));
}));

(tick.protocols.end["string"] = (function (s){
return tick.protocols.end(tick.protocols.parse(s));
}));

(java.time.LocalTime.prototype.tick$protocols$ITimeSpan$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalTime.prototype.tick$protocols$ITimeSpan$beginning$arity$1 = (function (x){
var x__$1 = this;
return x__$1;
}));

(java.time.LocalTime.prototype.tick$protocols$ITimeSpan$end$arity$1 = (function (x){
var x__$1 = this;
return x__$1;
}));

(java.time.OffsetDateTime.prototype.tick$protocols$ITimeSpan$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.OffsetDateTime.prototype.tick$protocols$ITimeSpan$beginning$arity$1 = (function (i){
var i__$1 = this;
return i__$1;
}));

(java.time.OffsetDateTime.prototype.tick$protocols$ITimeSpan$end$arity$1 = (function (i){
var i__$1 = this;
return i__$1;
}));

(java.time.LocalDateTime.prototype.tick$protocols$ITimeSpan$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDateTime.prototype.tick$protocols$ITimeSpan$beginning$arity$1 = (function (x){
var x__$1 = this;
return x__$1;
}));

(java.time.LocalDateTime.prototype.tick$protocols$ITimeSpan$end$arity$1 = (function (x){
var x__$1 = this;
return x__$1;
}));
(java.time.LocalTime.prototype.tick$protocols$ITimeReify$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalTime.prototype.tick$protocols$ITimeReify$on$arity$2 = (function (t,d){
var t__$1 = this;
return cljc.java_time.local_time.at_date(t__$1,tick.protocols.date(d));
}));

(java.time.OffsetTime.prototype.tick$protocols$ITimeReify$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.OffsetTime.prototype.tick$protocols$ITimeReify$on$arity$2 = (function (t,date){
var t__$1 = this;
return cljc.java_time.offset_time.at_date(t__$1,tick.protocols.date(date));
}));

(java.time.LocalDate.prototype.tick$protocols$ITimeReify$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDate.prototype.tick$protocols$ITimeReify$at$arity$2 = (function (date,t){
var date__$1 = this;
return cljc.java_time.local_date.at_time.cljs$core$IFn$_invoke$arity$2(date__$1,tick.protocols.time(t));
}));

(java.time.LocalDateTime.prototype.tick$protocols$ITimeReify$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDateTime.prototype.tick$protocols$ITimeReify$in$arity$2 = (function (ldt,z){
var ldt__$1 = this;
return cljc.java_time.local_date_time.at_zone(ldt__$1,tick.protocols.zone(z));
}));

(java.time.LocalDateTime.prototype.tick$protocols$ITimeReify$offset_by$arity$2 = (function (ldt,offset){
var ldt__$1 = this;
return cljc.java_time.local_date_time.at_offset(ldt__$1,tick.protocols.zone_offset(offset));
}));

(java.time.Instant.prototype.tick$protocols$ITimeReify$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Instant.prototype.tick$protocols$ITimeReify$in$arity$2 = (function (t,z){
var t__$1 = this;
return cljc.java_time.instant.at_zone(t__$1,tick.protocols.zone(z));
}));

(java.time.Instant.prototype.tick$protocols$ITimeReify$offset_by$arity$2 = (function (t,offset){
var t__$1 = this;
return cljc.java_time.instant.at_offset(t__$1,tick.protocols.zone_offset(offset));
}));

(java.time.OffsetDateTime.prototype.tick$protocols$ITimeReify$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.OffsetDateTime.prototype.tick$protocols$ITimeReify$in$arity$2 = (function (t,z){
var t__$1 = this;
return cljc.java_time.offset_date_time.at_zone_same_instant(t__$1,tick.protocols.zone(z));
}));

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeReify$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeReify$in$arity$2 = (function (t,z){
var t__$1 = this;
return cljc.java_time.zoned_date_time.with_zone_same_instant(t__$1,tick.protocols.zone(z));
}));

(Date.prototype.tick$protocols$ITimeReify$ = cljs.core.PROTOCOL_SENTINEL);

(Date.prototype.tick$protocols$ITimeReify$in$arity$2 = (function (t,z){
var t__$1 = this;
return tick.protocols.in$(tick.protocols.instant(t__$1),tick.protocols.zone(z));
}));
(Date.prototype.tick$protocols$ILocalTime$ = cljs.core.PROTOCOL_SENTINEL);

(Date.prototype.tick$protocols$ILocalTime$local_QMARK_$arity$1 = (function (d){
var d__$1 = this;
return false;
}));

(java.time.Instant.prototype.tick$protocols$ILocalTime$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Instant.prototype.tick$protocols$ILocalTime$local_QMARK_$arity$1 = (function (i){
var i__$1 = this;
return false;
}));

(java.time.LocalDateTime.prototype.tick$protocols$ILocalTime$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDateTime.prototype.tick$protocols$ILocalTime$local_QMARK_$arity$1 = (function (i){
var i__$1 = this;
return true;
}));

(java.time.LocalTime.prototype.tick$protocols$ILocalTime$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalTime.prototype.tick$protocols$ILocalTime$local_QMARK_$arity$1 = (function (i){
var i__$1 = this;
return true;
}));

(tick.protocols.ILocalTime["null"] = true);

(tick.protocols.local_QMARK_["null"] = (function (_){
return null;
}));
(java.time.LocalTime.prototype.tick$protocols$MinMax$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalTime.prototype.tick$protocols$MinMax$min_of_type$arity$1 = (function (_){
var ___$1 = this;
return cljc.java_time.local_time.min;
}));

(java.time.LocalTime.prototype.tick$protocols$MinMax$max_of_type$arity$1 = (function (_){
var ___$1 = this;
return cljc.java_time.local_time.max;
}));

(java.time.LocalDate.prototype.tick$protocols$MinMax$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDate.prototype.tick$protocols$MinMax$min_of_type$arity$1 = (function (_){
var ___$1 = this;
return cljc.java_time.local_date.min;
}));

(java.time.LocalDate.prototype.tick$protocols$MinMax$max_of_type$arity$1 = (function (_){
var ___$1 = this;
return cljc.java_time.local_date.max;
}));

(java.time.LocalDateTime.prototype.tick$protocols$MinMax$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDateTime.prototype.tick$protocols$MinMax$min_of_type$arity$1 = (function (_){
var ___$1 = this;
return cljc.java_time.local_date_time.min;
}));

(java.time.LocalDateTime.prototype.tick$protocols$MinMax$max_of_type$arity$1 = (function (_){
var ___$1 = this;
return cljc.java_time.local_date_time.max;
}));

(java.time.Instant.prototype.tick$protocols$MinMax$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Instant.prototype.tick$protocols$MinMax$min_of_type$arity$1 = (function (_){
var ___$1 = this;
return cljc.java_time.instant.min;
}));

(java.time.Instant.prototype.tick$protocols$MinMax$max_of_type$arity$1 = (function (_){
var ___$1 = this;
return cljc.java_time.instant.max;
}));

(tick.protocols.MinMax["null"] = true);

(tick.protocols.min_of_type["null"] = (function (_){
return cljc.java_time.instant.min;
}));

(tick.protocols.max_of_type["null"] = (function (_){
return cljc.java_time.instant.max;
}));
tick.core.ago = (function tick$core$ago(dur){
return tick.protocols.backward_duration(tick.core.now(),dur);
});
tick.core.hence = (function tick$core$hence(dur){
return tick.protocols.forward_duration(tick.core.now(),dur);
});
tick.core.midnight_QMARK_ = (function tick$core$midnight_QMARK_(t){
return cljc.java_time.duration.is_zero(cljc.java_time.duration.between(t,tick.protocols.beginning(tick.protocols.date(t))));
});
tick.core.clock_QMARK_ = (function tick$core$clock_QMARK_(v){
return cljc.java_time.extn.predicates.clock_QMARK_(v);
});
tick.core.day_of_week_QMARK_ = (function tick$core$day_of_week_QMARK_(v){
return cljc.java_time.extn.predicates.day_of_week_QMARK_(v);
});
tick.core.duration_QMARK_ = (function tick$core$duration_QMARK_(v){
return cljc.java_time.extn.predicates.duration_QMARK_(v);
});
tick.core.instant_QMARK_ = (function tick$core$instant_QMARK_(v){
return cljc.java_time.extn.predicates.instant_QMARK_(v);
});
tick.core.date_QMARK_ = (function tick$core$date_QMARK_(v){
return cljc.java_time.extn.predicates.local_date_QMARK_(v);
});
tick.core.date_time_QMARK_ = (function tick$core$date_time_QMARK_(v){
return cljc.java_time.extn.predicates.local_date_time_QMARK_(v);
});
tick.core.time_QMARK_ = (function tick$core$time_QMARK_(v){
return cljc.java_time.extn.predicates.local_time_QMARK_(v);
});
tick.core.month_QMARK_ = (function tick$core$month_QMARK_(v){
return cljc.java_time.extn.predicates.month_QMARK_(v);
});
tick.core.offset_date_time_QMARK_ = (function tick$core$offset_date_time_QMARK_(v){
return cljc.java_time.extn.predicates.offset_date_time_QMARK_(v);
});
tick.core.period_QMARK_ = (function tick$core$period_QMARK_(v){
return cljc.java_time.extn.predicates.period_QMARK_(v);
});
tick.core.year_QMARK_ = (function tick$core$year_QMARK_(v){
return cljc.java_time.extn.predicates.year_QMARK_(v);
});
tick.core.year_month_QMARK_ = (function tick$core$year_month_QMARK_(v){
return cljc.java_time.extn.predicates.year_month_QMARK_(v);
});
tick.core.zone_QMARK_ = (function tick$core$zone_QMARK_(v){
return cljc.java_time.extn.predicates.zone_id_QMARK_(v);
});
tick.core.zone_offset_QMARK_ = (function tick$core$zone_offset_QMARK_(v){
return cljc.java_time.extn.predicates.zone_offset_QMARK_(v);
});
tick.core.zoned_date_time_QMARK_ = (function tick$core$zoned_date_time_QMARK_(v){
return cljc.java_time.extn.predicates.zoned_date_time_QMARK_(v);
});
tick.core.interval_QMARK_ = (function tick$core$interval_QMARK_(v){
if((!((v == null)))){
if(((false) || ((cljs.core.PROTOCOL_SENTINEL === v.tick$protocols$ITimeSpan$)))){
return true;
} else {
if((!v.cljs$lang$protocol_mask$partition$)){
return cljs.core.native_satisfies_QMARK_(tick.protocols.ITimeSpan,v);
} else {
return false;
}
}
} else {
return cljs.core.native_satisfies_QMARK_(tick.protocols.ITimeSpan,v);
}
});
tick.core.MONDAY = cljc.java_time.day_of_week.monday;
tick.core.TUESDAY = cljc.java_time.day_of_week.tuesday;
tick.core.WEDNESDAY = cljc.java_time.day_of_week.wednesday;
tick.core.THURSDAY = cljc.java_time.day_of_week.thursday;
tick.core.FRIDAY = cljc.java_time.day_of_week.friday;
tick.core.SATURDAY = cljc.java_time.day_of_week.saturday;
tick.core.SUNDAY = cljc.java_time.day_of_week.sunday;
tick.core.JANUARY = cljc.java_time.month.january;
tick.core.FEBRUARY = cljc.java_time.month.february;
tick.core.MARCH = cljc.java_time.month.march;
tick.core.APRIL = cljc.java_time.month.april;
tick.core.MAY = cljc.java_time.month.may;
tick.core.JUNE = cljc.java_time.month.june;
tick.core.JULY = cljc.java_time.month.july;
tick.core.AUGUST = cljc.java_time.month.august;
tick.core.SEPTEMBER = cljc.java_time.month.september;
tick.core.OCTOBER = cljc.java_time.month.october;
tick.core.NOVEMBER = cljc.java_time.month.november;
tick.core.DECEMBER = cljc.java_time.month.december;
tick.core.UTC = tick.core.zone.cljs$core$IFn$_invoke$arity$1("UTC");
tick.core.min_of_type = tick.protocols.min_of_type;
tick.core.max_of_type = tick.protocols.max_of_type;
tick.core.range = tick.protocols.range;
tick.core.int$ = (function tick$core$int(arg){
return tick.protocols.int$(arg);
});
tick.core.long$ = (function tick$core$long(arg){
return tick.protocols.long$(arg);
});
tick.core.on = (function tick$core$on(t,d){
return tick.protocols.on(t,d);
});
tick.core.at = (function tick$core$at(d,t){
return tick.protocols.at(d,t);
});
tick.core.in$ = (function tick$core$in(ldt,z){
return tick.protocols.in$(ldt,z);
});
tick.core.offset_by = (function tick$core$offset_by(ldt,offset){
return tick.protocols.offset_by(ldt,offset);
});
tick.core.date = (function tick$core$date(var_args){
var G__75329 = arguments.length;
switch (G__75329) {
case 0:
return tick.core.date.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.date.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.date.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.core.today();
}));

(tick.core.date.cljs$core$IFn$_invoke$arity$1 = (function (v){
return tick.protocols.date(v);
}));

(tick.core.date.cljs$lang$maxFixedArity = 1);

tick.core.inst = (function tick$core$inst(var_args){
var G__75341 = arguments.length;
switch (G__75341) {
case 0:
return tick.core.inst.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.inst.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.inst.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.protocols.inst(tick.core.now());
}));

(tick.core.inst.cljs$core$IFn$_invoke$arity$1 = (function (v){
return tick.protocols.inst(v);
}));

(tick.core.inst.cljs$lang$maxFixedArity = 1);

tick.core.instant = (function tick$core$instant(var_args){
var G__75349 = arguments.length;
switch (G__75349) {
case 0:
return tick.core.instant.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.instant.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.instant.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.protocols.instant(tick.core.now());
}));

(tick.core.instant.cljs$core$IFn$_invoke$arity$1 = (function (v){
return tick.protocols.instant(v);
}));

(tick.core.instant.cljs$lang$maxFixedArity = 1);

tick.core.date_time = (function tick$core$date_time(var_args){
var G__75355 = arguments.length;
switch (G__75355) {
case 0:
return tick.core.date_time.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.date_time.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.date_time.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.protocols.date_time(tick.core.now());
}));

(tick.core.date_time.cljs$core$IFn$_invoke$arity$1 = (function (v){
return tick.protocols.date_time(v);
}));

(tick.core.date_time.cljs$lang$maxFixedArity = 1);

tick.core.offset_date_time = (function tick$core$offset_date_time(var_args){
var G__75361 = arguments.length;
switch (G__75361) {
case 0:
return tick.core.offset_date_time.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.offset_date_time.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.offset_date_time.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.protocols.offset_date_time(tick.core.now());
}));

(tick.core.offset_date_time.cljs$core$IFn$_invoke$arity$1 = (function (v){
return tick.protocols.offset_date_time(v);
}));

(tick.core.offset_date_time.cljs$lang$maxFixedArity = 1);

tick.core.zoned_date_time = (function tick$core$zoned_date_time(var_args){
var G__75367 = arguments.length;
switch (G__75367) {
case 0:
return tick.core.zoned_date_time.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.zoned_date_time.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.zoned_date_time.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.protocols.zoned_date_time(tick.core.now());
}));

(tick.core.zoned_date_time.cljs$core$IFn$_invoke$arity$1 = (function (v){
return tick.protocols.zoned_date_time(v);
}));

(tick.core.zoned_date_time.cljs$lang$maxFixedArity = 1);

(java.time.YearMonth.prototype.tick$protocols$ITimeComparison$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.YearMonth.prototype.tick$protocols$ITimeComparison$_LT_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.year_month.is_before(x__$1,y);
}));

(java.time.YearMonth.prototype.tick$protocols$ITimeComparison$_LT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core.not(cljc.java_time.year_month.is_after(x__$1,y));
}));

(java.time.YearMonth.prototype.tick$protocols$ITimeComparison$_GT_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.year_month.is_after(x__$1,y);
}));

(java.time.YearMonth.prototype.tick$protocols$ITimeComparison$_GT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core.not(cljc.java_time.year_month.is_before(x__$1,y));
}));

(java.time.YearMonth.prototype.tick$protocols$ITimeComparison$_EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(x__$1,y);
}));

(java.time.Year.prototype.tick$protocols$ITimeComparison$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Year.prototype.tick$protocols$ITimeComparison$_LT_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.year.is_before(x__$1,y);
}));

(java.time.Year.prototype.tick$protocols$ITimeComparison$_LT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core.not(cljc.java_time.year.is_after(x__$1,y));
}));

(java.time.Year.prototype.tick$protocols$ITimeComparison$_GT_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.year.is_after(x__$1,y);
}));

(java.time.Year.prototype.tick$protocols$ITimeComparison$_GT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core.not(cljc.java_time.year.is_before(x__$1,y));
}));

(java.time.Year.prototype.tick$protocols$ITimeComparison$_EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(x__$1,y);
}));

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeComparison$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeComparison$_LT_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.zoned_date_time.is_before(x__$1,tick.core.zoned_date_time.cljs$core$IFn$_invoke$arity$1(y));
}));

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeComparison$_LT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core.not(cljc.java_time.zoned_date_time.is_after(x__$1,tick.core.zoned_date_time.cljs$core$IFn$_invoke$arity$1(y)));
}));

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeComparison$_GT_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.zoned_date_time.is_after(x__$1,tick.core.zoned_date_time.cljs$core$IFn$_invoke$arity$1(y));
}));

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeComparison$_GT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core.not(cljc.java_time.zoned_date_time.is_before(x__$1,tick.core.zoned_date_time.cljs$core$IFn$_invoke$arity$1(y)));
}));

(java.time.ZonedDateTime.prototype.tick$protocols$ITimeComparison$_EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.zoned_date_time.is_equal(x__$1,tick.core.zoned_date_time.cljs$core$IFn$_invoke$arity$1(y));
}));

(java.time.Instant.prototype.tick$protocols$ITimeComparison$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Instant.prototype.tick$protocols$ITimeComparison$_LT_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.instant.is_before(x__$1,tick.core.instant.cljs$core$IFn$_invoke$arity$1(y));
}));

(java.time.Instant.prototype.tick$protocols$ITimeComparison$_LT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core.not(cljc.java_time.instant.is_after(x__$1,tick.core.instant.cljs$core$IFn$_invoke$arity$1(y)));
}));

(java.time.Instant.prototype.tick$protocols$ITimeComparison$_GT_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.instant.is_after(x__$1,tick.core.instant.cljs$core$IFn$_invoke$arity$1(y));
}));

(java.time.Instant.prototype.tick$protocols$ITimeComparison$_GT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core.not(cljc.java_time.instant.is_before(x__$1,tick.core.instant.cljs$core$IFn$_invoke$arity$1(y)));
}));

(java.time.Instant.prototype.tick$protocols$ITimeComparison$_EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(x__$1,tick.protocols.instant(y));
}));

(Date.prototype.tick$protocols$ITimeComparison$ = cljs.core.PROTOCOL_SENTINEL);

(Date.prototype.tick$protocols$ITimeComparison$_LT_$arity$2 = (function (x,y){
var x__$1 = this;
return (cljs.core.compare(x__$1,tick.core.inst.cljs$core$IFn$_invoke$arity$1(y)) < (0));
}));

(Date.prototype.tick$protocols$ITimeComparison$_LT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return (!((cljs.core.compare(x__$1,tick.core.inst.cljs$core$IFn$_invoke$arity$1(y)) > (0))));
}));

(Date.prototype.tick$protocols$ITimeComparison$_GT_$arity$2 = (function (x,y){
var x__$1 = this;
return (cljs.core.compare(x__$1,tick.core.inst.cljs$core$IFn$_invoke$arity$1(y)) > (0));
}));

(Date.prototype.tick$protocols$ITimeComparison$_GT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return (!((cljs.core.compare(x__$1,tick.core.inst.cljs$core$IFn$_invoke$arity$1(y)) < (0))));
}));

(Date.prototype.tick$protocols$ITimeComparison$_EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(x__$1,tick.protocols.inst(y));
}));

(java.time.LocalDate.prototype.tick$protocols$ITimeComparison$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDate.prototype.tick$protocols$ITimeComparison$_LT_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.local_date.is_before(x__$1,y);
}));

(java.time.LocalDate.prototype.tick$protocols$ITimeComparison$_LT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core.not(cljc.java_time.local_date.is_after(x__$1,y));
}));

(java.time.LocalDate.prototype.tick$protocols$ITimeComparison$_GT_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.local_date.is_after(x__$1,y);
}));

(java.time.LocalDate.prototype.tick$protocols$ITimeComparison$_GT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core.not(cljc.java_time.local_date.is_before(x__$1,y));
}));

(java.time.LocalDate.prototype.tick$protocols$ITimeComparison$_EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(x__$1,y);
}));

(java.time.LocalTime.prototype.tick$protocols$ITimeComparison$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalTime.prototype.tick$protocols$ITimeComparison$_LT_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.local_time.is_before(x__$1,y);
}));

(java.time.LocalTime.prototype.tick$protocols$ITimeComparison$_LT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core.not(cljc.java_time.local_time.is_after(x__$1,y));
}));

(java.time.LocalTime.prototype.tick$protocols$ITimeComparison$_GT_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.local_time.is_after(x__$1,y);
}));

(java.time.LocalTime.prototype.tick$protocols$ITimeComparison$_GT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core.not(cljc.java_time.local_time.is_before(x__$1,y));
}));

(java.time.LocalTime.prototype.tick$protocols$ITimeComparison$_EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(x__$1,y);
}));

(java.time.OffsetDateTime.prototype.tick$protocols$ITimeComparison$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.OffsetDateTime.prototype.tick$protocols$ITimeComparison$_LT_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.offset_date_time.is_before(x__$1,tick.core.offset_date_time.cljs$core$IFn$_invoke$arity$1(y));
}));

(java.time.OffsetDateTime.prototype.tick$protocols$ITimeComparison$_LT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core.not(cljc.java_time.offset_date_time.is_after(x__$1,tick.core.offset_date_time.cljs$core$IFn$_invoke$arity$1(y)));
}));

(java.time.OffsetDateTime.prototype.tick$protocols$ITimeComparison$_GT_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.offset_date_time.is_after(x__$1,tick.core.offset_date_time.cljs$core$IFn$_invoke$arity$1(y));
}));

(java.time.OffsetDateTime.prototype.tick$protocols$ITimeComparison$_GT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core.not(cljc.java_time.offset_date_time.is_before(x__$1,tick.core.offset_date_time.cljs$core$IFn$_invoke$arity$1(y)));
}));

(java.time.OffsetDateTime.prototype.tick$protocols$ITimeComparison$_EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.offset_date_time.is_equal(x__$1,tick.core.offset_date_time.cljs$core$IFn$_invoke$arity$1(y));
}));

(java.time.LocalDateTime.prototype.tick$protocols$ITimeComparison$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.LocalDateTime.prototype.tick$protocols$ITimeComparison$_LT_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.local_date_time.is_before(x__$1,y);
}));

(java.time.LocalDateTime.prototype.tick$protocols$ITimeComparison$_LT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core.not(cljc.java_time.local_date_time.is_after(x__$1,y));
}));

(java.time.LocalDateTime.prototype.tick$protocols$ITimeComparison$_GT_$arity$2 = (function (x,y){
var x__$1 = this;
return cljc.java_time.local_date_time.is_after(x__$1,y);
}));

(java.time.LocalDateTime.prototype.tick$protocols$ITimeComparison$_GT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core.not(cljc.java_time.local_date_time.is_before(x__$1,y));
}));

(java.time.LocalDateTime.prototype.tick$protocols$ITimeComparison$_EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(x__$1,y);
}));

(java.time.Duration.prototype.tick$protocols$ITimeComparison$ = cljs.core.PROTOCOL_SENTINEL);

(java.time.Duration.prototype.tick$protocols$ITimeComparison$_LT_$arity$2 = (function (x,y){
var x__$1 = this;
return (cljc.java_time.duration.compare_to(x__$1,y) < (0));
}));

(java.time.Duration.prototype.tick$protocols$ITimeComparison$_LT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
var or__5043__auto__ = cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(x__$1,y);
if(or__5043__auto__){
return or__5043__auto__;
} else {
return cljc.java_time.duration.compare_to(x__$1,y);
}
}));

(java.time.Duration.prototype.tick$protocols$ITimeComparison$_GT_$arity$2 = (function (x,y){
var x__$1 = this;
return (cljc.java_time.duration.compare_to(x__$1,y) > (0));
}));

(java.time.Duration.prototype.tick$protocols$ITimeComparison$_GT__EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return ((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(x__$1,y)) || ((cljc.java_time.duration.compare_to(x__$1,y) > (0))));
}));

(java.time.Duration.prototype.tick$protocols$ITimeComparison$_EQ_$arity$2 = (function (x,y){
var x__$1 = this;
return cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(x__$1,y);
}));
tick.core.nanosecond = (function tick$core$nanosecond(t){
return tick.protocols.nanosecond(t);
});
tick.core.microsecond = (function tick$core$microsecond(t){
return tick.protocols.microsecond(t);
});
tick.core.millisecond = (function tick$core$millisecond(t){
return tick.protocols.millisecond(t);
});
tick.core.second = (function tick$core$second(t){
return tick.protocols.second(t);
});
tick.core.minute = (function tick$core$minute(t){
return tick.protocols.minute(t);
});
tick.core.hour = (function tick$core$hour(t){
return tick.protocols.hour(t);
});
tick.core.time = (function tick$core$time(var_args){
var G__75485 = arguments.length;
switch (G__75485) {
case 0:
return tick.core.time.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.time.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.time.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.protocols.time(tick.core.now());
}));

(tick.core.time.cljs$core$IFn$_invoke$arity$1 = (function (v){
return tick.protocols.time(v);
}));

(tick.core.time.cljs$lang$maxFixedArity = 1);

tick.core.day_of_week = (function tick$core$day_of_week(var_args){
var G__75493 = arguments.length;
switch (G__75493) {
case 0:
return tick.core.day_of_week.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.day_of_week.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.day_of_week.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.protocols.day_of_week(tick.core.today());
}));

(tick.core.day_of_week.cljs$core$IFn$_invoke$arity$1 = (function (v){
return tick.protocols.day_of_week(v);
}));

(tick.core.day_of_week.cljs$lang$maxFixedArity = 1);

tick.core.day_of_month = (function tick$core$day_of_month(var_args){
var G__75499 = arguments.length;
switch (G__75499) {
case 0:
return tick.core.day_of_month.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.day_of_month.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.day_of_month.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.protocols.day_of_month(tick.core.today());
}));

(tick.core.day_of_month.cljs$core$IFn$_invoke$arity$1 = (function (v){
return tick.protocols.day_of_month(v);
}));

(tick.core.day_of_month.cljs$lang$maxFixedArity = 1);

tick.core.month = (function tick$core$month(var_args){
var G__75506 = arguments.length;
switch (G__75506) {
case 0:
return tick.core.month.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.month.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.month.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.protocols.month(tick.core.today());
}));

(tick.core.month.cljs$core$IFn$_invoke$arity$1 = (function (v){
return tick.protocols.month(v);
}));

(tick.core.month.cljs$lang$maxFixedArity = 1);

tick.core.year = (function tick$core$year(var_args){
var G__75513 = arguments.length;
switch (G__75513) {
case 0:
return tick.core.year.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.year.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.year.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.protocols.year(tick.core.today());
}));

(tick.core.year.cljs$core$IFn$_invoke$arity$1 = (function (v){
return tick.protocols.year(v);
}));

(tick.core.year.cljs$lang$maxFixedArity = 1);

tick.core.year_month = (function tick$core$year_month(var_args){
var G__75521 = arguments.length;
switch (G__75521) {
case 0:
return tick.core.year_month.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.year_month.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.year_month.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.protocols.year_month(tick.core.today());
}));

(tick.core.year_month.cljs$core$IFn$_invoke$arity$1 = (function (v){
return tick.protocols.year_month(v);
}));

(tick.core.year_month.cljs$lang$maxFixedArity = 1);

tick.core.clock = (function tick$core$clock(var_args){
var G__75529 = arguments.length;
switch (G__75529) {
case 0:
return tick.core.clock.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return tick.core.clock.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.clock.cljs$core$IFn$_invoke$arity$0 = (function (){
return tick.core.current_clock();
}));

(tick.core.clock.cljs$core$IFn$_invoke$arity$1 = (function (i){
return tick.protocols.clock(i);
}));

(tick.core.clock.cljs$lang$maxFixedArity = 1);

tick.core.predefined_formatters = new cljs.core.PersistentArrayMap(null, 6, [new cljs.core.Keyword(null,"iso-zoned-date-time","iso-zoned-date-time",-1626338878),cljc.java_time.format.date_time_formatter.iso_zoned_date_time,new cljs.core.Keyword(null,"iso-offset-date-time","iso-offset-date-time",-47990863),cljc.java_time.format.date_time_formatter.iso_offset_date_time,new cljs.core.Keyword(null,"iso-local-time","iso-local-time",-1676599821),cljc.java_time.format.date_time_formatter.iso_local_time,new cljs.core.Keyword(null,"iso-local-date-time","iso-local-date-time",-1669236935),cljc.java_time.format.date_time_formatter.iso_local_date_time,new cljs.core.Keyword(null,"iso-local-date","iso-local-date",571187900),cljc.java_time.format.date_time_formatter.iso_local_date,new cljs.core.Keyword(null,"iso-instant","iso-instant",1024383901),cljc.java_time.format.date_time_formatter.iso_instant], null);
/**
 * Constructs a DateTimeFormatter out of either a
 * 
 *   * format string - "YYYY/mm/DD" "YYY HH:MM" etc.
 *   or
 *   * formatter name - :iso-instant :iso-local-date etc
 *   
 *   and a Locale, which is optional.
 */
tick.core.formatter = (function tick$core$formatter(var_args){
var G__75539 = arguments.length;
switch (G__75539) {
case 1:
return tick.core.formatter.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return tick.core.formatter.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.formatter.cljs$core$IFn$_invoke$arity$1 = (function (fmt){
return tick.core.formatter.cljs$core$IFn$_invoke$arity$2(fmt,(function (){try{var G__75545 = tick.core.goog$module$goog$object.get(JSJodaLocale,"Locale");
if((G__75545 == null)){
return null;
} else {
return tick.core.goog$module$goog$object.get(G__75545,"US");
}
}catch (e75544){if((e75544 instanceof Error)){
var _e = e75544;
return null;
} else {
throw e75544;

}
}})());
}));

(tick.core.formatter.cljs$core$IFn$_invoke$arity$2 = (function (fmt,locale){
var fmt__$1 = (((fmt instanceof java.time.format.DateTimeFormatter))?fmt:((typeof fmt === 'string')?(((locale == null))?(function(){throw (new Error("Locale is nil, try adding a require '[tick.locale-en-us]"))})():cljc.java_time.format.date_time_formatter.with_locale(cljc.java_time.format.date_time_formatter.of_pattern.cljs$core$IFn$_invoke$arity$1(fmt),locale)):cljs.core.get.cljs$core$IFn$_invoke$arity$2(tick.core.predefined_formatters,fmt)
));
return fmt__$1;
}));

(tick.core.formatter.cljs$lang$maxFixedArity = 2);

/**
 * Formats the given time entity as a string.
 *   Accepts something that can be converted to a `DateTimeFormatter` as a first
 *   argument. Given one argument uses the default format.
 */
tick.core.format = (function tick$core$format(var_args){
var G__75553 = arguments.length;
switch (G__75553) {
case 1:
return tick.core.format.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return tick.core.format.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(tick.core.format.cljs$core$IFn$_invoke$arity$1 = (function (o){
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(o);
}));

(tick.core.format.cljs$core$IFn$_invoke$arity$2 = (function (fmt,o){
return cljc.java_time.format.date_time_formatter.format(tick.core.formatter.cljs$core$IFn$_invoke$arity$1(fmt),o);
}));

(tick.core.format.cljs$lang$maxFixedArity = 2);

tick.core.between = (function tick$core$between(v1,v2){
return tick.protocols.between(v1,v2);
});
tick.core.beginning = (function tick$core$beginning(v){
return tick.protocols.beginning(v);
});
tick.core.end = (function tick$core$end(v){
return tick.protocols.end(v);
});
tick.core._EQ_ = (function tick$core$_EQ_(var_args){
var G__75572 = arguments.length;
switch (G__75572) {
case 1:
return tick.core._EQ_.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return tick.core._EQ_.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
var args_arr__5791__auto__ = [];
var len__5766__auto___75917 = arguments.length;
var i__5767__auto___75918 = (0);
while(true){
if((i__5767__auto___75918 < len__5766__auto___75917)){
args_arr__5791__auto__.push((arguments[i__5767__auto___75918]));

var G__75919 = (i__5767__auto___75918 + (1));
i__5767__auto___75918 = G__75919;
continue;
} else {
}
break;
}

var argseq__5792__auto__ = (new cljs.core.IndexedSeq(args_arr__5791__auto__.slice((2)),(0),null));
return tick.core._EQ_.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),(arguments[(1)]),argseq__5792__auto__);

}
});

(tick.core._EQ_.cljs$core$IFn$_invoke$arity$1 = (function (_x){
return true;
}));

(tick.core._EQ_.cljs$core$IFn$_invoke$arity$2 = (function (x,y){
return tick.protocols._EQ_(x,y);
}));

(tick.core._EQ_.cljs$core$IFn$_invoke$arity$variadic = (function (x,y,more){
while(true){
if(cljs.core.truth_(tick.protocols._EQ_(x,y))){
if(cljs.core.next(more)){
var G__75920 = y;
var G__75921 = cljs.core.first(more);
var G__75922 = cljs.core.next(more);
x = G__75920;
y = G__75921;
more = G__75922;
continue;
} else {
return tick.protocols._EQ_(y,cljs.core.first(more));
}
} else {
return false;
}
break;
}
}));

/** @this {Function} */
(tick.core._EQ_.cljs$lang$applyTo = (function (seq75567){
var G__75568 = cljs.core.first(seq75567);
var seq75567__$1 = cljs.core.next(seq75567);
var G__75569 = cljs.core.first(seq75567__$1);
var seq75567__$2 = cljs.core.next(seq75567__$1);
var self__5751__auto__ = this;
return self__5751__auto__.cljs$core$IFn$_invoke$arity$variadic(G__75568,G__75569,seq75567__$2);
}));

(tick.core._EQ_.cljs$lang$maxFixedArity = (2));

tick.core._LT_ = (function tick$core$_LT_(var_args){
var G__75589 = arguments.length;
switch (G__75589) {
case 1:
return tick.core._LT_.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return tick.core._LT_.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
var args_arr__5791__auto__ = [];
var len__5766__auto___75924 = arguments.length;
var i__5767__auto___75925 = (0);
while(true){
if((i__5767__auto___75925 < len__5766__auto___75924)){
args_arr__5791__auto__.push((arguments[i__5767__auto___75925]));

var G__75926 = (i__5767__auto___75925 + (1));
i__5767__auto___75925 = G__75926;
continue;
} else {
}
break;
}

var argseq__5792__auto__ = (new cljs.core.IndexedSeq(args_arr__5791__auto__.slice((2)),(0),null));
return tick.core._LT_.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),(arguments[(1)]),argseq__5792__auto__);

}
});

(tick.core._LT_.cljs$core$IFn$_invoke$arity$1 = (function (_x){
return true;
}));

(tick.core._LT_.cljs$core$IFn$_invoke$arity$2 = (function (x,y){
return tick.protocols._LT_(x,y);
}));

(tick.core._LT_.cljs$core$IFn$_invoke$arity$variadic = (function (x,y,more){
while(true){
if(cljs.core.truth_(tick.protocols._LT_(x,y))){
if(cljs.core.next(more)){
var G__75927 = y;
var G__75928 = cljs.core.first(more);
var G__75929 = cljs.core.next(more);
x = G__75927;
y = G__75928;
more = G__75929;
continue;
} else {
return tick.protocols._LT_(y,cljs.core.first(more));
}
} else {
return false;
}
break;
}
}));

/** @this {Function} */
(tick.core._LT_.cljs$lang$applyTo = (function (seq75586){
var G__75587 = cljs.core.first(seq75586);
var seq75586__$1 = cljs.core.next(seq75586);
var G__75588 = cljs.core.first(seq75586__$1);
var seq75586__$2 = cljs.core.next(seq75586__$1);
var self__5751__auto__ = this;
return self__5751__auto__.cljs$core$IFn$_invoke$arity$variadic(G__75587,G__75588,seq75586__$2);
}));

(tick.core._LT_.cljs$lang$maxFixedArity = (2));

tick.core._LT__EQ_ = (function tick$core$_LT__EQ_(var_args){
var G__75606 = arguments.length;
switch (G__75606) {
case 1:
return tick.core._LT__EQ_.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return tick.core._LT__EQ_.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
var args_arr__5791__auto__ = [];
var len__5766__auto___75931 = arguments.length;
var i__5767__auto___75932 = (0);
while(true){
if((i__5767__auto___75932 < len__5766__auto___75931)){
args_arr__5791__auto__.push((arguments[i__5767__auto___75932]));

var G__75933 = (i__5767__auto___75932 + (1));
i__5767__auto___75932 = G__75933;
continue;
} else {
}
break;
}

var argseq__5792__auto__ = (new cljs.core.IndexedSeq(args_arr__5791__auto__.slice((2)),(0),null));
return tick.core._LT__EQ_.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),(arguments[(1)]),argseq__5792__auto__);

}
});

(tick.core._LT__EQ_.cljs$core$IFn$_invoke$arity$1 = (function (_x){
return true;
}));

(tick.core._LT__EQ_.cljs$core$IFn$_invoke$arity$2 = (function (x,y){
return tick.protocols._LT__EQ_(x,y);
}));

(tick.core._LT__EQ_.cljs$core$IFn$_invoke$arity$variadic = (function (x,y,more){
while(true){
if(cljs.core.truth_(tick.protocols._LT__EQ_(x,y))){
if(cljs.core.next(more)){
var G__75934 = y;
var G__75935 = cljs.core.first(more);
var G__75936 = cljs.core.next(more);
x = G__75934;
y = G__75935;
more = G__75936;
continue;
} else {
return tick.protocols._LT__EQ_(y,cljs.core.first(more));
}
} else {
return false;
}
break;
}
}));

/** @this {Function} */
(tick.core._LT__EQ_.cljs$lang$applyTo = (function (seq75603){
var G__75604 = cljs.core.first(seq75603);
var seq75603__$1 = cljs.core.next(seq75603);
var G__75605 = cljs.core.first(seq75603__$1);
var seq75603__$2 = cljs.core.next(seq75603__$1);
var self__5751__auto__ = this;
return self__5751__auto__.cljs$core$IFn$_invoke$arity$variadic(G__75604,G__75605,seq75603__$2);
}));

(tick.core._LT__EQ_.cljs$lang$maxFixedArity = (2));

tick.core._GT_ = (function tick$core$_GT_(var_args){
var G__75623 = arguments.length;
switch (G__75623) {
case 1:
return tick.core._GT_.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return tick.core._GT_.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
var args_arr__5791__auto__ = [];
var len__5766__auto___75938 = arguments.length;
var i__5767__auto___75939 = (0);
while(true){
if((i__5767__auto___75939 < len__5766__auto___75938)){
args_arr__5791__auto__.push((arguments[i__5767__auto___75939]));

var G__75940 = (i__5767__auto___75939 + (1));
i__5767__auto___75939 = G__75940;
continue;
} else {
}
break;
}

var argseq__5792__auto__ = (new cljs.core.IndexedSeq(args_arr__5791__auto__.slice((2)),(0),null));
return tick.core._GT_.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),(arguments[(1)]),argseq__5792__auto__);

}
});

(tick.core._GT_.cljs$core$IFn$_invoke$arity$1 = (function (_x){
return true;
}));

(tick.core._GT_.cljs$core$IFn$_invoke$arity$2 = (function (x,y){
return tick.protocols._GT_(x,y);
}));

(tick.core._GT_.cljs$core$IFn$_invoke$arity$variadic = (function (x,y,more){
while(true){
if(cljs.core.truth_(tick.protocols._GT_(x,y))){
if(cljs.core.next(more)){
var G__75941 = y;
var G__75942 = cljs.core.first(more);
var G__75943 = cljs.core.next(more);
x = G__75941;
y = G__75942;
more = G__75943;
continue;
} else {
return tick.protocols._GT_(y,cljs.core.first(more));
}
} else {
return false;
}
break;
}
}));

/** @this {Function} */
(tick.core._GT_.cljs$lang$applyTo = (function (seq75620){
var G__75621 = cljs.core.first(seq75620);
var seq75620__$1 = cljs.core.next(seq75620);
var G__75622 = cljs.core.first(seq75620__$1);
var seq75620__$2 = cljs.core.next(seq75620__$1);
var self__5751__auto__ = this;
return self__5751__auto__.cljs$core$IFn$_invoke$arity$variadic(G__75621,G__75622,seq75620__$2);
}));

(tick.core._GT_.cljs$lang$maxFixedArity = (2));

tick.core._GT__EQ_ = (function tick$core$_GT__EQ_(var_args){
var G__75636 = arguments.length;
switch (G__75636) {
case 1:
return tick.core._GT__EQ_.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return tick.core._GT__EQ_.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
var args_arr__5791__auto__ = [];
var len__5766__auto___75945 = arguments.length;
var i__5767__auto___75946 = (0);
while(true){
if((i__5767__auto___75946 < len__5766__auto___75945)){
args_arr__5791__auto__.push((arguments[i__5767__auto___75946]));

var G__75947 = (i__5767__auto___75946 + (1));
i__5767__auto___75946 = G__75947;
continue;
} else {
}
break;
}

var argseq__5792__auto__ = (new cljs.core.IndexedSeq(args_arr__5791__auto__.slice((2)),(0),null));
return tick.core._GT__EQ_.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),(arguments[(1)]),argseq__5792__auto__);

}
});

(tick.core._GT__EQ_.cljs$core$IFn$_invoke$arity$1 = (function (_x){
return true;
}));

(tick.core._GT__EQ_.cljs$core$IFn$_invoke$arity$2 = (function (x,y){
return tick.protocols._GT__EQ_(x,y);
}));

(tick.core._GT__EQ_.cljs$core$IFn$_invoke$arity$variadic = (function (x,y,more){
while(true){
if(cljs.core.truth_(tick.protocols._GT__EQ_(x,y))){
if(cljs.core.next(more)){
var G__75948 = y;
var G__75949 = cljs.core.first(more);
var G__75950 = cljs.core.next(more);
x = G__75948;
y = G__75949;
more = G__75950;
continue;
} else {
return tick.protocols._GT__EQ_(y,cljs.core.first(more));
}
} else {
return false;
}
break;
}
}));

/** @this {Function} */
(tick.core._GT__EQ_.cljs$lang$applyTo = (function (seq75633){
var G__75634 = cljs.core.first(seq75633);
var seq75633__$1 = cljs.core.next(seq75633);
var G__75635 = cljs.core.first(seq75633__$1);
var seq75633__$2 = cljs.core.next(seq75633__$1);
var self__5751__auto__ = this;
return self__5751__auto__.cljs$core$IFn$_invoke$arity$variadic(G__75634,G__75635,seq75633__$2);
}));

(tick.core._GT__EQ_.cljs$lang$maxFixedArity = (2));

tick.core.greater = (function tick$core$greater(x,y){
if(cljs.core.truth_(tick.core._GT_.cljs$core$IFn$_invoke$arity$2(x,y))){
return x;
} else {
return y;
}
});
/**
 * Find the latest of the given arguments. Callers should ensure that no
 *   argument is nil.
 */
tick.core.max = (function tick$core$max(var_args){
var args__5772__auto__ = [];
var len__5766__auto___75951 = arguments.length;
var i__5767__auto___75952 = (0);
while(true){
if((i__5767__auto___75952 < len__5766__auto___75951)){
args__5772__auto__.push((arguments[i__5767__auto___75952]));

var G__75953 = (i__5767__auto___75952 + (1));
i__5767__auto___75952 = G__75953;
continue;
} else {
}
break;
}

var argseq__5773__auto__ = ((((1) < args__5772__auto__.length))?(new cljs.core.IndexedSeq(args__5772__auto__.slice((1)),(0),null)):null);
return tick.core.max.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__5773__auto__);
});

(tick.core.max.cljs$core$IFn$_invoke$arity$variadic = (function (arg,args){
if(cljs.core.every_QMARK_(cljs.core.some_QMARK_,cljs.core.cons(arg,args))){
} else {
throw (new Error("Assert failed: (every? some? (cons arg args))"));
}

return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3(tick.core.greater,arg,args);
}));

(tick.core.max.cljs$lang$maxFixedArity = (1));

/** @this {Function} */
(tick.core.max.cljs$lang$applyTo = (function (seq75645){
var G__75646 = cljs.core.first(seq75645);
var seq75645__$1 = cljs.core.next(seq75645);
var self__5751__auto__ = this;
return self__5751__auto__.cljs$core$IFn$_invoke$arity$variadic(G__75646,seq75645__$1);
}));

tick.core.lesser = (function tick$core$lesser(x,y){
if(cljs.core.truth_(tick.core._LT_.cljs$core$IFn$_invoke$arity$2(x,y))){
return x;
} else {
return y;
}
});
/**
 * Find the earliest of the given arguments. Callers should ensure that no
 *   argument is nil.
 */
tick.core.min = (function tick$core$min(var_args){
var args__5772__auto__ = [];
var len__5766__auto___75954 = arguments.length;
var i__5767__auto___75955 = (0);
while(true){
if((i__5767__auto___75955 < len__5766__auto___75954)){
args__5772__auto__.push((arguments[i__5767__auto___75955]));

var G__75956 = (i__5767__auto___75955 + (1));
i__5767__auto___75955 = G__75956;
continue;
} else {
}
break;
}

var argseq__5773__auto__ = ((((1) < args__5772__auto__.length))?(new cljs.core.IndexedSeq(args__5772__auto__.slice((1)),(0),null)):null);
return tick.core.min.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__5773__auto__);
});

(tick.core.min.cljs$core$IFn$_invoke$arity$variadic = (function (arg,args){
if(cljs.core.every_QMARK_(cljs.core.some_QMARK_,cljs.core.cons(arg,args))){
} else {
throw (new Error("Assert failed: (every? some? (cons arg args))"));
}

return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3(tick.core.lesser,arg,args);
}));

(tick.core.min.cljs$lang$maxFixedArity = (1));

/** @this {Function} */
(tick.core.min.cljs$lang$applyTo = (function (seq75656){
var G__75657 = cljs.core.first(seq75656);
var seq75656__$1 = cljs.core.next(seq75656);
var self__5751__auto__ = this;
return self__5751__auto__.cljs$core$IFn$_invoke$arity$variadic(G__75657,seq75656__$1);
}));

tick.core.beginning_composite = (function tick$core$beginning_composite(m){
var map__75667 = m;
var map__75667__$1 = cljs.core.__destructure_map(map__75667);
var beginning = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__75667__$1,new cljs.core.Keyword("tick","beginning","tick/beginning",82659968));
var intervals = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__75667__$1,new cljs.core.Keyword("tick","intervals","tick/intervals",2091945314));
if(cljs.core.truth_(intervals)){
return cljs.core.apply.cljs$core$IFn$_invoke$arity$2(tick.core.min,cljs.core.map.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword("tick","beginning","tick/beginning",82659968),intervals));
} else {
return beginning;
}
});
tick.core.end_composite = (function tick$core$end_composite(m){
var map__75670 = m;
var map__75670__$1 = cljs.core.__destructure_map(map__75670);
var end = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__75670__$1,new cljs.core.Keyword("tick","end","tick/end",-269896517));
var intervals = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__75670__$1,new cljs.core.Keyword("tick","intervals","tick/intervals",2091945314));
if(cljs.core.truth_(intervals)){
return cljs.core.apply.cljs$core$IFn$_invoke$arity$2(tick.core.max,cljs.core.map.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword("tick","end","tick/end",-269896517),intervals));
} else {
return end;
}
});
(cljs.core.PersistentArrayMap.prototype.tick$protocols$ITimeSpan$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.PersistentArrayMap.prototype.tick$protocols$ITimeSpan$beginning$arity$1 = (function (m){
var m__$1 = this;
return tick.core.beginning_composite(m__$1);
}));

(cljs.core.PersistentArrayMap.prototype.tick$protocols$ITimeSpan$end$arity$1 = (function (m){
var m__$1 = this;
return tick.core.end_composite(m__$1);
}));
(cljs.core.PersistentHashMap.prototype.tick$protocols$ITimeSpan$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.PersistentHashMap.prototype.tick$protocols$ITimeSpan$beginning$arity$1 = (function (m){
var m__$1 = this;
return tick.core.beginning_composite(m__$1);
}));

(cljs.core.PersistentHashMap.prototype.tick$protocols$ITimeSpan$end$arity$1 = (function (m){
var m__$1 = this;
return tick.core.end_composite(m__$1);
}));
tick.core.nanos = (function tick$core$nanos(v){
return tick.protocols.nanos(v);
});
tick.core.micros = (function tick$core$micros(v){
return tick.protocols.micros(v);
});
tick.core.millis = (function tick$core$millis(v){
return tick.protocols.millis(v);
});
tick.core.seconds = (function tick$core$seconds(v){
return tick.protocols.seconds(v);
});
tick.core.minutes = (function tick$core$minutes(v){
return tick.protocols.minutes(v);
});
tick.core.hours = (function tick$core$hours(v){
return tick.protocols.hours(v);
});
tick.core.days = (function tick$core$days(v){
return tick.protocols.days(v);
});
tick.core.months = (function tick$core$months(v){
return tick.protocols.months(v);
});
tick.core.years = (function tick$core$years(v){
return tick.protocols.years(v);
});
tick.core.divide = (function tick$core$divide(t,divisor){
return tick.protocols.divide(t,divisor);
});
/**
 * to parse an iso-formatted date, use (t/date "2020..") instead
 */
tick.core.parse_date = (function tick$core$parse_date(date_str,formatter){
return cljc.java_time.local_date.parse.cljs$core$IFn$_invoke$arity$2(date_str,formatter);
});
/**
 * to parse an iso-formatted date-time, use (t/date-time "2020..") instead
 */
tick.core.parse_date_time = (function tick$core$parse_date_time(date_str,formatter){
return cljc.java_time.local_date_time.parse.cljs$core$IFn$_invoke$arity$2(date_str,formatter);
});
/**
 * to parse an iso-formatted time, use (t/time "20:20..") instead
 */
tick.core.parse_time = (function tick$core$parse_time(date_str,formatter){
return cljc.java_time.local_time.parse.cljs$core$IFn$_invoke$arity$2(date_str,formatter);
});
/**
 * to parse an iso-formatted offset-date-time, use (t/offset-date-time "2020..") instead
 */
tick.core.parse_offset_date_time = (function tick$core$parse_offset_date_time(date_str,formatter){
return cljc.java_time.offset_date_time.parse.cljs$core$IFn$_invoke$arity$2(date_str,formatter);
});
/**
 * to parse an iso-formatted year, use (t/year "2020") instead
 */
tick.core.parse_year = (function tick$core$parse_year(date_str,formatter){
return cljc.java_time.year.parse.cljs$core$IFn$_invoke$arity$2(date_str,formatter);
});
/**
 * to parse an iso-formatted year-month, use (t/year-month "2020..") instead
 */
tick.core.parse_year_month = (function tick$core$parse_year_month(date_str,formatter){
return cljc.java_time.year_month.parse.cljs$core$IFn$_invoke$arity$2(date_str,formatter);
});
/**
 * to parse an iso-formatted zoned-date-time, use (t/zoned-date-time "2020..") instead
 */
tick.core.parse_zoned_date_time = (function tick$core$parse_zoned_date_time(date_str,formatter){
return cljc.java_time.zoned_date_time.parse.cljs$core$IFn$_invoke$arity$2(date_str,formatter);
});

//# sourceMappingURL=tick.core.js.map
