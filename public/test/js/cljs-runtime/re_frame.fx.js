goog.provide('re_frame.fx');
re_frame.fx.kind = new cljs.core.Keyword(null,"fx","fx",-1237829572);
if(cljs.core.truth_((re_frame.registrar.kinds.cljs$core$IFn$_invoke$arity$1 ? re_frame.registrar.kinds.cljs$core$IFn$_invoke$arity$1(re_frame.fx.kind) : re_frame.registrar.kinds.call(null,re_frame.fx.kind)))){
} else {
throw (new Error("Assert failed: (re-frame.registrar/kinds kind)"));
}
re_frame.fx.reg_fx = (function re_frame$fx$reg_fx(id,handler){
return re_frame.registrar.register_handler(re_frame.fx.kind,id,handler);
});
/**
 * An interceptor whose `:after` actions the contents of `:effects`. As a result,
 *   this interceptor is Domino 3.
 * 
 *   This interceptor is silently added (by reg-event-db etc) to the front of
 *   interceptor chains for all events.
 * 
 *   For each key in `:effects` (a map), it calls the registered `effects handler`
 *   (see `reg-fx` for registration of effect handlers).
 * 
 *   So, if `:effects` was:
 *    {:dispatch  [:hello 42]
 *     :db        {...}
 *     :undo      "set flag"}
 * 
 *   it will call the registered effect handlers for each of the map's keys:
 *   `:dispatch`, `:undo` and `:db`. When calling each handler, provides the map
 *   value for that key - so in the example above the effect handler for :dispatch
 *   will be given one arg `[:hello 42]`.
 * 
 *   You cannot rely on the ordering in which effects are executed, other than that
 *   `:db` is guaranteed to be executed first.
 */
re_frame.fx.do_fx = re_frame.interceptor.__GT_interceptor.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"id","id",-1388402092),new cljs.core.Keyword(null,"do-fx","do-fx",1194163050),new cljs.core.Keyword(null,"after","after",594996914),(function re_frame$fx$do_fx_after(context){
if(re_frame.trace.is_trace_enabled_QMARK_()){
var _STAR_current_trace_STAR__orig_val__72868 = re_frame.trace._STAR_current_trace_STAR_;
var _STAR_current_trace_STAR__temp_val__72869 = re_frame.trace.start_trace(new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"op-type","op-type",-1636141668),new cljs.core.Keyword("event","do-fx","event/do-fx",1357330452)], null));
(re_frame.trace._STAR_current_trace_STAR_ = _STAR_current_trace_STAR__temp_val__72869);

try{try{var effects = new cljs.core.Keyword(null,"effects","effects",-282369292).cljs$core$IFn$_invoke$arity$1(context);
var effects_without_db = cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(effects,new cljs.core.Keyword(null,"db","db",993250759));
var temp__5753__auto___72994 = new cljs.core.Keyword(null,"db","db",993250759).cljs$core$IFn$_invoke$arity$1(effects);
if(cljs.core.truth_(temp__5753__auto___72994)){
var new_db_72995 = temp__5753__auto___72994;
var fexpr__72875_72996 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,new cljs.core.Keyword(null,"db","db",993250759),false);
(fexpr__72875_72996.cljs$core$IFn$_invoke$arity$1 ? fexpr__72875_72996.cljs$core$IFn$_invoke$arity$1(new_db_72995) : fexpr__72875_72996.call(null,new_db_72995));
} else {
}

var seq__72878 = cljs.core.seq(effects_without_db);
var chunk__72879 = null;
var count__72880 = (0);
var i__72881 = (0);
while(true){
if((i__72881 < count__72880)){
var vec__72902 = chunk__72879.cljs$core$IIndexed$_nth$arity$2(null,i__72881);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__72902,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__72902,(1),null);
var temp__5751__auto___72997 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5751__auto___72997)){
var effect_fn_72998 = temp__5751__auto___72997;
(effect_fn_72998.cljs$core$IFn$_invoke$arity$1 ? effect_fn_72998.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_72998.call(null,effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: no handler registered for effect:",effect_key,". Ignoring."], 0));
}


var G__72999 = seq__72878;
var G__73000 = chunk__72879;
var G__73001 = count__72880;
var G__73002 = (i__72881 + (1));
seq__72878 = G__72999;
chunk__72879 = G__73000;
count__72880 = G__73001;
i__72881 = G__73002;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__72878);
if(temp__5753__auto__){
var seq__72878__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__72878__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__72878__$1);
var G__73003 = cljs.core.chunk_rest(seq__72878__$1);
var G__73004 = c__5565__auto__;
var G__73005 = cljs.core.count(c__5565__auto__);
var G__73006 = (0);
seq__72878 = G__73003;
chunk__72879 = G__73004;
count__72880 = G__73005;
i__72881 = G__73006;
continue;
} else {
var vec__72913 = cljs.core.first(seq__72878__$1);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__72913,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__72913,(1),null);
var temp__5751__auto___73007 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5751__auto___73007)){
var effect_fn_73008 = temp__5751__auto___73007;
(effect_fn_73008.cljs$core$IFn$_invoke$arity$1 ? effect_fn_73008.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_73008.call(null,effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: no handler registered for effect:",effect_key,". Ignoring."], 0));
}


var G__73009 = cljs.core.next(seq__72878__$1);
var G__73010 = null;
var G__73011 = (0);
var G__73012 = (0);
seq__72878 = G__73009;
chunk__72879 = G__73010;
count__72880 = G__73011;
i__72881 = G__73012;
continue;
}
} else {
return null;
}
}
break;
}
}finally {if(re_frame.trace.is_trace_enabled_QMARK_()){
var end__36217__auto___73013 = re_frame.interop.now();
var duration__36218__auto___73014 = (end__36217__auto___73013 - new cljs.core.Keyword(null,"start","start",-355208981).cljs$core$IFn$_invoke$arity$1(re_frame.trace._STAR_current_trace_STAR_));
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(re_frame.trace.traces,cljs.core.conj,cljs.core.assoc.cljs$core$IFn$_invoke$arity$variadic(re_frame.trace._STAR_current_trace_STAR_,new cljs.core.Keyword(null,"duration","duration",1444101068),duration__36218__auto___73014,cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"end","end",-268185958),re_frame.interop.now()], 0)));

re_frame.trace.run_tracing_callbacks_BANG_(end__36217__auto___73013);
} else {
}
}}finally {(re_frame.trace._STAR_current_trace_STAR_ = _STAR_current_trace_STAR__orig_val__72868);
}} else {
var effects = new cljs.core.Keyword(null,"effects","effects",-282369292).cljs$core$IFn$_invoke$arity$1(context);
var effects_without_db = cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(effects,new cljs.core.Keyword(null,"db","db",993250759));
var temp__5753__auto___73015 = new cljs.core.Keyword(null,"db","db",993250759).cljs$core$IFn$_invoke$arity$1(effects);
if(cljs.core.truth_(temp__5753__auto___73015)){
var new_db_73016 = temp__5753__auto___73015;
var fexpr__72918_73017 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,new cljs.core.Keyword(null,"db","db",993250759),false);
(fexpr__72918_73017.cljs$core$IFn$_invoke$arity$1 ? fexpr__72918_73017.cljs$core$IFn$_invoke$arity$1(new_db_73016) : fexpr__72918_73017.call(null,new_db_73016));
} else {
}

var seq__72920 = cljs.core.seq(effects_without_db);
var chunk__72921 = null;
var count__72922 = (0);
var i__72923 = (0);
while(true){
if((i__72923 < count__72922)){
var vec__72931 = chunk__72921.cljs$core$IIndexed$_nth$arity$2(null,i__72923);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__72931,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__72931,(1),null);
var temp__5751__auto___73018 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5751__auto___73018)){
var effect_fn_73019 = temp__5751__auto___73018;
(effect_fn_73019.cljs$core$IFn$_invoke$arity$1 ? effect_fn_73019.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_73019.call(null,effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: no handler registered for effect:",effect_key,". Ignoring."], 0));
}


var G__73020 = seq__72920;
var G__73021 = chunk__72921;
var G__73022 = count__72922;
var G__73023 = (i__72923 + (1));
seq__72920 = G__73020;
chunk__72921 = G__73021;
count__72922 = G__73022;
i__72923 = G__73023;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__72920);
if(temp__5753__auto__){
var seq__72920__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__72920__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__72920__$1);
var G__73024 = cljs.core.chunk_rest(seq__72920__$1);
var G__73025 = c__5565__auto__;
var G__73026 = cljs.core.count(c__5565__auto__);
var G__73027 = (0);
seq__72920 = G__73024;
chunk__72921 = G__73025;
count__72922 = G__73026;
i__72923 = G__73027;
continue;
} else {
var vec__72934 = cljs.core.first(seq__72920__$1);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__72934,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__72934,(1),null);
var temp__5751__auto___73029 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5751__auto___73029)){
var effect_fn_73030 = temp__5751__auto___73029;
(effect_fn_73030.cljs$core$IFn$_invoke$arity$1 ? effect_fn_73030.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_73030.call(null,effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: no handler registered for effect:",effect_key,". Ignoring."], 0));
}


var G__73031 = cljs.core.next(seq__72920__$1);
var G__73032 = null;
var G__73033 = (0);
var G__73034 = (0);
seq__72920 = G__73031;
chunk__72921 = G__73032;
count__72922 = G__73033;
i__72923 = G__73034;
continue;
}
} else {
return null;
}
}
break;
}
}
})], 0));
re_frame.fx.dispatch_later = (function re_frame$fx$dispatch_later(p__72939){
var map__72940 = p__72939;
var map__72940__$1 = cljs.core.__destructure_map(map__72940);
var effect = map__72940__$1;
var ms = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__72940__$1,new cljs.core.Keyword(null,"ms","ms",-1152709733));
var dispatch = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__72940__$1,new cljs.core.Keyword(null,"dispatch","dispatch",1319337009));
if(((cljs.core.empty_QMARK_(dispatch)) || ((!(typeof ms === 'number'))))){
return re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"error","error",-978969032),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: ignoring bad :dispatch-later value:",effect], 0));
} else {
return re_frame.interop.set_timeout_BANG_((function (){
return re_frame.router.dispatch(dispatch);
}),ms);
}
});
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"dispatch-later","dispatch-later",291951390),(function (value){
if(cljs.core.map_QMARK_(value)){
return re_frame.fx.dispatch_later(value);
} else {
var seq__72941 = cljs.core.seq(cljs.core.remove.cljs$core$IFn$_invoke$arity$2(cljs.core.nil_QMARK_,value));
var chunk__72942 = null;
var count__72943 = (0);
var i__72944 = (0);
while(true){
if((i__72944 < count__72943)){
var effect = chunk__72942.cljs$core$IIndexed$_nth$arity$2(null,i__72944);
re_frame.fx.dispatch_later(effect);


var G__73036 = seq__72941;
var G__73037 = chunk__72942;
var G__73038 = count__72943;
var G__73039 = (i__72944 + (1));
seq__72941 = G__73036;
chunk__72942 = G__73037;
count__72943 = G__73038;
i__72944 = G__73039;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__72941);
if(temp__5753__auto__){
var seq__72941__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__72941__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__72941__$1);
var G__73041 = cljs.core.chunk_rest(seq__72941__$1);
var G__73042 = c__5565__auto__;
var G__73043 = cljs.core.count(c__5565__auto__);
var G__73044 = (0);
seq__72941 = G__73041;
chunk__72942 = G__73042;
count__72943 = G__73043;
i__72944 = G__73044;
continue;
} else {
var effect = cljs.core.first(seq__72941__$1);
re_frame.fx.dispatch_later(effect);


var G__73045 = cljs.core.next(seq__72941__$1);
var G__73046 = null;
var G__73047 = (0);
var G__73048 = (0);
seq__72941 = G__73045;
chunk__72942 = G__73046;
count__72943 = G__73047;
i__72944 = G__73048;
continue;
}
} else {
return null;
}
}
break;
}
}
}));
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"fx","fx",-1237829572),(function (seq_of_effects){
if((!(cljs.core.sequential_QMARK_(seq_of_effects)))){
return re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: \":fx\" effect expects a seq, but was given ",cljs.core.type(seq_of_effects)], 0));
} else {
var seq__72959 = cljs.core.seq(cljs.core.remove.cljs$core$IFn$_invoke$arity$2(cljs.core.nil_QMARK_,seq_of_effects));
var chunk__72960 = null;
var count__72961 = (0);
var i__72962 = (0);
while(true){
if((i__72962 < count__72961)){
var vec__72970 = chunk__72960.cljs$core$IIndexed$_nth$arity$2(null,i__72962);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__72970,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__72970,(1),null);
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"db","db",993250759),effect_key)){
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: \":fx\" effect should not contain a :db effect"], 0));
} else {
}

var temp__5751__auto___73050 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5751__auto___73050)){
var effect_fn_73051 = temp__5751__auto___73050;
(effect_fn_73051.cljs$core$IFn$_invoke$arity$1 ? effect_fn_73051.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_73051.call(null,effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: in \":fx\" effect found ",effect_key," which has no associated handler. Ignoring."], 0));
}


var G__73052 = seq__72959;
var G__73053 = chunk__72960;
var G__73054 = count__72961;
var G__73055 = (i__72962 + (1));
seq__72959 = G__73052;
chunk__72960 = G__73053;
count__72961 = G__73054;
i__72962 = G__73055;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__72959);
if(temp__5753__auto__){
var seq__72959__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__72959__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__72959__$1);
var G__73058 = cljs.core.chunk_rest(seq__72959__$1);
var G__73059 = c__5565__auto__;
var G__73060 = cljs.core.count(c__5565__auto__);
var G__73061 = (0);
seq__72959 = G__73058;
chunk__72960 = G__73059;
count__72961 = G__73060;
i__72962 = G__73061;
continue;
} else {
var vec__72973 = cljs.core.first(seq__72959__$1);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__72973,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__72973,(1),null);
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"db","db",993250759),effect_key)){
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: \":fx\" effect should not contain a :db effect"], 0));
} else {
}

var temp__5751__auto___73062 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5751__auto___73062)){
var effect_fn_73063 = temp__5751__auto___73062;
(effect_fn_73063.cljs$core$IFn$_invoke$arity$1 ? effect_fn_73063.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_73063.call(null,effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: in \":fx\" effect found ",effect_key," which has no associated handler. Ignoring."], 0));
}


var G__73064 = cljs.core.next(seq__72959__$1);
var G__73065 = null;
var G__73066 = (0);
var G__73067 = (0);
seq__72959 = G__73064;
chunk__72960 = G__73065;
count__72961 = G__73066;
i__72962 = G__73067;
continue;
}
} else {
return null;
}
}
break;
}
}
}));
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"dispatch","dispatch",1319337009),(function (value){
if((!(cljs.core.vector_QMARK_(value)))){
return re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"error","error",-978969032),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: ignoring bad :dispatch value. Expected a vector, but got:",value], 0));
} else {
return re_frame.router.dispatch(value);
}
}));
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"dispatch-n","dispatch-n",-504469236),(function (value){
if((!(cljs.core.sequential_QMARK_(value)))){
return re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"error","error",-978969032),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: ignoring bad :dispatch-n value. Expected a collection, but got:",value], 0));
} else {
var seq__72978 = cljs.core.seq(cljs.core.remove.cljs$core$IFn$_invoke$arity$2(cljs.core.nil_QMARK_,value));
var chunk__72979 = null;
var count__72980 = (0);
var i__72981 = (0);
while(true){
if((i__72981 < count__72980)){
var event = chunk__72979.cljs$core$IIndexed$_nth$arity$2(null,i__72981);
re_frame.router.dispatch(event);


var G__73070 = seq__72978;
var G__73071 = chunk__72979;
var G__73072 = count__72980;
var G__73073 = (i__72981 + (1));
seq__72978 = G__73070;
chunk__72979 = G__73071;
count__72980 = G__73072;
i__72981 = G__73073;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__72978);
if(temp__5753__auto__){
var seq__72978__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__72978__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__72978__$1);
var G__73074 = cljs.core.chunk_rest(seq__72978__$1);
var G__73075 = c__5565__auto__;
var G__73076 = cljs.core.count(c__5565__auto__);
var G__73077 = (0);
seq__72978 = G__73074;
chunk__72979 = G__73075;
count__72980 = G__73076;
i__72981 = G__73077;
continue;
} else {
var event = cljs.core.first(seq__72978__$1);
re_frame.router.dispatch(event);


var G__73078 = cljs.core.next(seq__72978__$1);
var G__73079 = null;
var G__73080 = (0);
var G__73081 = (0);
seq__72978 = G__73078;
chunk__72979 = G__73079;
count__72980 = G__73080;
i__72981 = G__73081;
continue;
}
} else {
return null;
}
}
break;
}
}
}));
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"deregister-event-handler","deregister-event-handler",-1096518994),(function (value){
var clear_event = cljs.core.partial.cljs$core$IFn$_invoke$arity$2(re_frame.registrar.clear_handlers,re_frame.events.kind);
if(cljs.core.sequential_QMARK_(value)){
var seq__72984 = cljs.core.seq(value);
var chunk__72985 = null;
var count__72986 = (0);
var i__72987 = (0);
while(true){
if((i__72987 < count__72986)){
var event = chunk__72985.cljs$core$IIndexed$_nth$arity$2(null,i__72987);
clear_event(event);


var G__73082 = seq__72984;
var G__73083 = chunk__72985;
var G__73084 = count__72986;
var G__73085 = (i__72987 + (1));
seq__72984 = G__73082;
chunk__72985 = G__73083;
count__72986 = G__73084;
i__72987 = G__73085;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__72984);
if(temp__5753__auto__){
var seq__72984__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__72984__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__72984__$1);
var G__73086 = cljs.core.chunk_rest(seq__72984__$1);
var G__73087 = c__5565__auto__;
var G__73088 = cljs.core.count(c__5565__auto__);
var G__73089 = (0);
seq__72984 = G__73086;
chunk__72985 = G__73087;
count__72986 = G__73088;
i__72987 = G__73089;
continue;
} else {
var event = cljs.core.first(seq__72984__$1);
clear_event(event);


var G__73090 = cljs.core.next(seq__72984__$1);
var G__73091 = null;
var G__73092 = (0);
var G__73093 = (0);
seq__72984 = G__73090;
chunk__72985 = G__73091;
count__72986 = G__73092;
i__72987 = G__73093;
continue;
}
} else {
return null;
}
}
break;
}
} else {
return clear_event(value);
}
}));
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"db","db",993250759),(function (value){
if((!((cljs.core.deref(re_frame.db.app_db) === value)))){
return cljs.core.reset_BANG_(re_frame.db.app_db,value);
} else {
return null;
}
}));

//# sourceMappingURL=re_frame.fx.js.map
