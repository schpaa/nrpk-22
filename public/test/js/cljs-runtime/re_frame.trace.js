goog.provide('re_frame.trace');
re_frame.trace.id = cljs.core.atom.cljs$core$IFn$_invoke$arity$1((0));
re_frame.trace._STAR_current_trace_STAR_ = null;
re_frame.trace.reset_tracing_BANG_ = (function re_frame$trace$reset_tracing_BANG_(){
return cljs.core.reset_BANG_(re_frame.trace.id,(0));
});
/**
 * @define {boolean}
 */
re_frame.trace.trace_enabled_QMARK_ = goog.define("re_frame.trace.trace_enabled_QMARK_",false);
/**
 * See https://groups.google.com/d/msg/clojurescript/jk43kmYiMhA/IHglVr_TPdgJ for more details
 */
re_frame.trace.is_trace_enabled_QMARK_ = (function re_frame$trace$is_trace_enabled_QMARK_(){
return re_frame.trace.trace_enabled_QMARK_;
});
re_frame.trace.trace_cbs = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(cljs.core.PersistentArrayMap.EMPTY);
if((typeof re_frame !== 'undefined') && (typeof re_frame.trace !== 'undefined') && (typeof re_frame.trace.traces !== 'undefined')){
} else {
re_frame.trace.traces = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(cljs.core.PersistentVector.EMPTY);
}
if((typeof re_frame !== 'undefined') && (typeof re_frame.trace !== 'undefined') && (typeof re_frame.trace.next_delivery !== 'undefined')){
} else {
re_frame.trace.next_delivery = cljs.core.atom.cljs$core$IFn$_invoke$arity$1((0));
}
/**
 * Registers a tracing callback function which will receive a collection of one or more traces.
 *   Will replace an existing callback function if it shares the same key.
 */
re_frame.trace.register_trace_cb = (function re_frame$trace$register_trace_cb(key,f){
if(re_frame.trace.trace_enabled_QMARK_){
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(re_frame.trace.trace_cbs,cljs.core.assoc,key,f);
} else {
return re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["Tracing is not enabled. Please set {\"re_frame.trace.trace_enabled_QMARK_\" true} in :closure-defines. See: https://github.com/day8/re-frame-10x#installation."], 0));
}
});
re_frame.trace.remove_trace_cb = (function re_frame$trace$remove_trace_cb(key){
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(re_frame.trace.trace_cbs,cljs.core.dissoc,key);

return null;
});
re_frame.trace.next_id = (function re_frame$trace$next_id(){
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$2(re_frame.trace.id,cljs.core.inc);
});
re_frame.trace.start_trace = (function re_frame$trace$start_trace(p__72543){
var map__72545 = p__72543;
var map__72545__$1 = cljs.core.__destructure_map(map__72545);
var operation = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__72545__$1,new cljs.core.Keyword(null,"operation","operation",-1267664310));
var op_type = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__72545__$1,new cljs.core.Keyword(null,"op-type","op-type",-1636141668));
var tags = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__72545__$1,new cljs.core.Keyword(null,"tags","tags",1771418977));
var child_of = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__72545__$1,new cljs.core.Keyword(null,"child-of","child-of",-903376662));
return new cljs.core.PersistentArrayMap(null, 6, [new cljs.core.Keyword(null,"id","id",-1388402092),re_frame.trace.next_id(),new cljs.core.Keyword(null,"operation","operation",-1267664310),operation,new cljs.core.Keyword(null,"op-type","op-type",-1636141668),op_type,new cljs.core.Keyword(null,"tags","tags",1771418977),tags,new cljs.core.Keyword(null,"child-of","child-of",-903376662),(function (){var or__5043__auto__ = child_of;
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
return new cljs.core.Keyword(null,"id","id",-1388402092).cljs$core$IFn$_invoke$arity$1(re_frame.trace._STAR_current_trace_STAR_);
}
})(),new cljs.core.Keyword(null,"start","start",-355208981),re_frame.interop.now()], null);
});
re_frame.trace.debounce_time = (50);
re_frame.trace.debounce = (function re_frame$trace$debounce(f,interval){
return goog.functions.debounce(f,interval);
});
re_frame.trace.schedule_debounce = re_frame.trace.debounce((function re_frame$trace$tracing_cb_debounced(){
var seq__72546_72578 = cljs.core.seq(cljs.core.deref(re_frame.trace.trace_cbs));
var chunk__72547_72579 = null;
var count__72548_72580 = (0);
var i__72549_72581 = (0);
while(true){
if((i__72549_72581 < count__72548_72580)){
var vec__72561_72582 = chunk__72547_72579.cljs$core$IIndexed$_nth$arity$2(null,i__72549_72581);
var k_72583 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__72561_72582,(0),null);
var cb_72584 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__72561_72582,(1),null);
try{var G__72567_72585 = cljs.core.deref(re_frame.trace.traces);
(cb_72584.cljs$core$IFn$_invoke$arity$1 ? cb_72584.cljs$core$IFn$_invoke$arity$1(G__72567_72585) : cb_72584.call(null,G__72567_72585));
}catch (e72565){var e_72586 = e72565;
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"error","error",-978969032),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["Error thrown from trace cb",k_72583,"while storing",cljs.core.deref(re_frame.trace.traces),e_72586], 0));
}

var G__72587 = seq__72546_72578;
var G__72588 = chunk__72547_72579;
var G__72589 = count__72548_72580;
var G__72590 = (i__72549_72581 + (1));
seq__72546_72578 = G__72587;
chunk__72547_72579 = G__72588;
count__72548_72580 = G__72589;
i__72549_72581 = G__72590;
continue;
} else {
var temp__5753__auto___72591 = cljs.core.seq(seq__72546_72578);
if(temp__5753__auto___72591){
var seq__72546_72592__$1 = temp__5753__auto___72591;
if(cljs.core.chunked_seq_QMARK_(seq__72546_72592__$1)){
var c__5565__auto___72593 = cljs.core.chunk_first(seq__72546_72592__$1);
var G__72595 = cljs.core.chunk_rest(seq__72546_72592__$1);
var G__72596 = c__5565__auto___72593;
var G__72597 = cljs.core.count(c__5565__auto___72593);
var G__72598 = (0);
seq__72546_72578 = G__72595;
chunk__72547_72579 = G__72596;
count__72548_72580 = G__72597;
i__72549_72581 = G__72598;
continue;
} else {
var vec__72568_72599 = cljs.core.first(seq__72546_72592__$1);
var k_72600 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__72568_72599,(0),null);
var cb_72601 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__72568_72599,(1),null);
try{var G__72572_72602 = cljs.core.deref(re_frame.trace.traces);
(cb_72601.cljs$core$IFn$_invoke$arity$1 ? cb_72601.cljs$core$IFn$_invoke$arity$1(G__72572_72602) : cb_72601.call(null,G__72572_72602));
}catch (e72571){var e_72603 = e72571;
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"error","error",-978969032),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["Error thrown from trace cb",k_72600,"while storing",cljs.core.deref(re_frame.trace.traces),e_72603], 0));
}

var G__72604 = cljs.core.next(seq__72546_72592__$1);
var G__72605 = null;
var G__72606 = (0);
var G__72607 = (0);
seq__72546_72578 = G__72604;
chunk__72547_72579 = G__72605;
count__72548_72580 = G__72606;
i__72549_72581 = G__72607;
continue;
}
} else {
}
}
break;
}

return cljs.core.reset_BANG_(re_frame.trace.traces,cljs.core.PersistentVector.EMPTY);
}),re_frame.trace.debounce_time);
re_frame.trace.run_tracing_callbacks_BANG_ = (function re_frame$trace$run_tracing_callbacks_BANG_(now){
if(((cljs.core.deref(re_frame.trace.next_delivery) - (25)) < now)){
(re_frame.trace.schedule_debounce.cljs$core$IFn$_invoke$arity$0 ? re_frame.trace.schedule_debounce.cljs$core$IFn$_invoke$arity$0() : re_frame.trace.schedule_debounce.call(null));

return cljs.core.reset_BANG_(re_frame.trace.next_delivery,(now + re_frame.trace.debounce_time));
} else {
return null;
}
});

//# sourceMappingURL=re_frame.trace.js.map
