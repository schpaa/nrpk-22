goog.provide('shadow.remote.runtime.shared');
shadow.remote.runtime.shared.init_state = (function shadow$remote$runtime$shared$init_state(client_info){
return new cljs.core.PersistentArrayMap(null, 5, [new cljs.core.Keyword(null,"extensions","extensions",-1103629196),cljs.core.PersistentArrayMap.EMPTY,new cljs.core.Keyword(null,"ops","ops",1237330063),cljs.core.PersistentArrayMap.EMPTY,new cljs.core.Keyword(null,"client-info","client-info",1958982504),client_info,new cljs.core.Keyword(null,"call-id-seq","call-id-seq",-1679248218),(0),new cljs.core.Keyword(null,"call-handlers","call-handlers",386605551),cljs.core.PersistentArrayMap.EMPTY], null);
});
shadow.remote.runtime.shared.now = (function shadow$remote$runtime$shared$now(){
return Date.now();
});
shadow.remote.runtime.shared.relay_msg = (function shadow$remote$runtime$shared$relay_msg(runtime,msg){
return shadow.remote.runtime.api.relay_msg(runtime,msg);
});
shadow.remote.runtime.shared.reply = (function shadow$remote$runtime$shared$reply(runtime,p__66350,res){
var map__66351 = p__66350;
var map__66351__$1 = cljs.core.__destructure_map(map__66351);
var call_id = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66351__$1,new cljs.core.Keyword(null,"call-id","call-id",1043012968));
var from = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66351__$1,new cljs.core.Keyword(null,"from","from",1815293044));
var res__$1 = (function (){var G__66352 = res;
var G__66352__$1 = (cljs.core.truth_(call_id)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__66352,new cljs.core.Keyword(null,"call-id","call-id",1043012968),call_id):G__66352);
if(cljs.core.truth_(from)){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__66352__$1,new cljs.core.Keyword(null,"to","to",192099007),from);
} else {
return G__66352__$1;
}
})();
return shadow.remote.runtime.api.relay_msg(runtime,res__$1);
});
shadow.remote.runtime.shared.call = (function shadow$remote$runtime$shared$call(var_args){
var G__66355 = arguments.length;
switch (G__66355) {
case 3:
return shadow.remote.runtime.shared.call.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 4:
return shadow.remote.runtime.shared.call.cljs$core$IFn$_invoke$arity$4((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.remote.runtime.shared.call.cljs$core$IFn$_invoke$arity$3 = (function (runtime,msg,handlers){
return shadow.remote.runtime.shared.call.cljs$core$IFn$_invoke$arity$4(runtime,msg,handlers,(0));
}));

(shadow.remote.runtime.shared.call.cljs$core$IFn$_invoke$arity$4 = (function (p__66365,msg,handlers,timeout_after_ms){
var map__66366 = p__66365;
var map__66366__$1 = cljs.core.__destructure_map(map__66366);
var runtime = map__66366__$1;
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66366__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
var call_id = new cljs.core.Keyword(null,"call-id-seq","call-id-seq",-1679248218).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(state_ref));
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(state_ref,cljs.core.update,new cljs.core.Keyword(null,"call-id-seq","call-id-seq",-1679248218),cljs.core.inc);

cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(state_ref,cljs.core.assoc_in,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"call-handlers","call-handlers",386605551),call_id], null),new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"handlers","handlers",79528781),handlers,new cljs.core.Keyword(null,"called-at","called-at",607081160),shadow.remote.runtime.shared.now(),new cljs.core.Keyword(null,"msg","msg",-1386103444),msg,new cljs.core.Keyword(null,"timeout","timeout",-318625318),timeout_after_ms], null));

return shadow.remote.runtime.api.relay_msg(runtime,cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(msg,new cljs.core.Keyword(null,"call-id","call-id",1043012968),call_id));
}));

(shadow.remote.runtime.shared.call.cljs$lang$maxFixedArity = 4);

shadow.remote.runtime.shared.trigger_BANG_ = (function shadow$remote$runtime$shared$trigger_BANG_(var_args){
var args__5772__auto__ = [];
var len__5766__auto___66589 = arguments.length;
var i__5767__auto___66590 = (0);
while(true){
if((i__5767__auto___66590 < len__5766__auto___66589)){
args__5772__auto__.push((arguments[i__5767__auto___66590]));

var G__66591 = (i__5767__auto___66590 + (1));
i__5767__auto___66590 = G__66591;
continue;
} else {
}
break;
}

var argseq__5773__auto__ = ((((2) < args__5772__auto__.length))?(new cljs.core.IndexedSeq(args__5772__auto__.slice((2)),(0),null)):null);
return shadow.remote.runtime.shared.trigger_BANG_.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),(arguments[(1)]),argseq__5773__auto__);
});

(shadow.remote.runtime.shared.trigger_BANG_.cljs$core$IFn$_invoke$arity$variadic = (function (p__66375,ev,args){
var map__66376 = p__66375;
var map__66376__$1 = cljs.core.__destructure_map(map__66376);
var runtime = map__66376__$1;
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66376__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
var seq__66377 = cljs.core.seq(cljs.core.vals(new cljs.core.Keyword(null,"extensions","extensions",-1103629196).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(state_ref))));
var chunk__66380 = null;
var count__66381 = (0);
var i__66382 = (0);
while(true){
if((i__66382 < count__66381)){
var ext = chunk__66380.cljs$core$IIndexed$_nth$arity$2(null,i__66382);
var ev_fn = cljs.core.get.cljs$core$IFn$_invoke$arity$2(ext,ev);
if(cljs.core.truth_(ev_fn)){
cljs.core.apply.cljs$core$IFn$_invoke$arity$2(ev_fn,args);


var G__66593 = seq__66377;
var G__66594 = chunk__66380;
var G__66595 = count__66381;
var G__66596 = (i__66382 + (1));
seq__66377 = G__66593;
chunk__66380 = G__66594;
count__66381 = G__66595;
i__66382 = G__66596;
continue;
} else {
var G__66597 = seq__66377;
var G__66598 = chunk__66380;
var G__66599 = count__66381;
var G__66600 = (i__66382 + (1));
seq__66377 = G__66597;
chunk__66380 = G__66598;
count__66381 = G__66599;
i__66382 = G__66600;
continue;
}
} else {
var temp__5753__auto__ = cljs.core.seq(seq__66377);
if(temp__5753__auto__){
var seq__66377__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__66377__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__66377__$1);
var G__66602 = cljs.core.chunk_rest(seq__66377__$1);
var G__66603 = c__5565__auto__;
var G__66604 = cljs.core.count(c__5565__auto__);
var G__66605 = (0);
seq__66377 = G__66602;
chunk__66380 = G__66603;
count__66381 = G__66604;
i__66382 = G__66605;
continue;
} else {
var ext = cljs.core.first(seq__66377__$1);
var ev_fn = cljs.core.get.cljs$core$IFn$_invoke$arity$2(ext,ev);
if(cljs.core.truth_(ev_fn)){
cljs.core.apply.cljs$core$IFn$_invoke$arity$2(ev_fn,args);


var G__66606 = cljs.core.next(seq__66377__$1);
var G__66607 = null;
var G__66608 = (0);
var G__66609 = (0);
seq__66377 = G__66606;
chunk__66380 = G__66607;
count__66381 = G__66608;
i__66382 = G__66609;
continue;
} else {
var G__66610 = cljs.core.next(seq__66377__$1);
var G__66611 = null;
var G__66612 = (0);
var G__66613 = (0);
seq__66377 = G__66610;
chunk__66380 = G__66611;
count__66381 = G__66612;
i__66382 = G__66613;
continue;
}
}
} else {
return null;
}
}
break;
}
}));

(shadow.remote.runtime.shared.trigger_BANG_.cljs$lang$maxFixedArity = (2));

/** @this {Function} */
(shadow.remote.runtime.shared.trigger_BANG_.cljs$lang$applyTo = (function (seq66372){
var G__66373 = cljs.core.first(seq66372);
var seq66372__$1 = cljs.core.next(seq66372);
var G__66374 = cljs.core.first(seq66372__$1);
var seq66372__$2 = cljs.core.next(seq66372__$1);
var self__5751__auto__ = this;
return self__5751__auto__.cljs$core$IFn$_invoke$arity$variadic(G__66373,G__66374,seq66372__$2);
}));

shadow.remote.runtime.shared.welcome = (function shadow$remote$runtime$shared$welcome(p__66385,p__66386){
var map__66387 = p__66385;
var map__66387__$1 = cljs.core.__destructure_map(map__66387);
var runtime = map__66387__$1;
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66387__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
var map__66388 = p__66386;
var map__66388__$1 = cljs.core.__destructure_map(map__66388);
var msg = map__66388__$1;
var client_id = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66388__$1,new cljs.core.Keyword(null,"client-id","client-id",-464622140));
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(state_ref,cljs.core.assoc,new cljs.core.Keyword(null,"client-id","client-id",-464622140),client_id);

var map__66389 = cljs.core.deref(state_ref);
var map__66389__$1 = cljs.core.__destructure_map(map__66389);
var client_info = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66389__$1,new cljs.core.Keyword(null,"client-info","client-info",1958982504));
var extensions = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66389__$1,new cljs.core.Keyword(null,"extensions","extensions",-1103629196));
shadow.remote.runtime.shared.relay_msg(runtime,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"hello","hello",-245025397),new cljs.core.Keyword(null,"client-info","client-info",1958982504),client_info], null));

return shadow.remote.runtime.shared.trigger_BANG_(runtime,new cljs.core.Keyword(null,"on-welcome","on-welcome",1895317125));
});
shadow.remote.runtime.shared.ping = (function shadow$remote$runtime$shared$ping(runtime,msg){
return shadow.remote.runtime.shared.reply(runtime,msg,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"pong","pong",-172484958)], null));
});
shadow.remote.runtime.shared.get_client_id = (function shadow$remote$runtime$shared$get_client_id(p__66392){
var map__66394 = p__66392;
var map__66394__$1 = cljs.core.__destructure_map(map__66394);
var runtime = map__66394__$1;
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66394__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
var or__5043__auto__ = new cljs.core.Keyword(null,"client-id","client-id",-464622140).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(state_ref));
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("runtime has no assigned runtime-id",new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"runtime","runtime",-1331573996),runtime], null));
}
});
shadow.remote.runtime.shared.request_supported_ops = (function shadow$remote$runtime$shared$request_supported_ops(p__66395,msg){
var map__66396 = p__66395;
var map__66396__$1 = cljs.core.__destructure_map(map__66396);
var runtime = map__66396__$1;
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66396__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
return shadow.remote.runtime.shared.reply(runtime,msg,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"supported-ops","supported-ops",337914702),new cljs.core.Keyword(null,"ops","ops",1237330063),cljs.core.disj.cljs$core$IFn$_invoke$arity$variadic(cljs.core.set(cljs.core.keys(new cljs.core.Keyword(null,"ops","ops",1237330063).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(state_ref)))),new cljs.core.Keyword(null,"welcome","welcome",-578152123),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"unknown-relay-op","unknown-relay-op",170832753),new cljs.core.Keyword(null,"unknown-op","unknown-op",1900385996),new cljs.core.Keyword(null,"request-supported-ops","request-supported-ops",-1034994502),new cljs.core.Keyword(null,"tool-disconnect","tool-disconnect",189103996)], 0))], null));
});
shadow.remote.runtime.shared.unknown_relay_op = (function shadow$remote$runtime$shared$unknown_relay_op(msg){
return console.warn("unknown-relay-op",msg);
});
shadow.remote.runtime.shared.unknown_op = (function shadow$remote$runtime$shared$unknown_op(msg){
return console.warn("unknown-op",msg);
});
shadow.remote.runtime.shared.add_extension_STAR_ = (function shadow$remote$runtime$shared$add_extension_STAR_(p__66398,key,p__66399){
var map__66400 = p__66398;
var map__66400__$1 = cljs.core.__destructure_map(map__66400);
var state = map__66400__$1;
var extensions = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66400__$1,new cljs.core.Keyword(null,"extensions","extensions",-1103629196));
var map__66401 = p__66399;
var map__66401__$1 = cljs.core.__destructure_map(map__66401);
var spec = map__66401__$1;
var ops = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66401__$1,new cljs.core.Keyword(null,"ops","ops",1237330063));
if(cljs.core.contains_QMARK_(extensions,key)){
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("extension already registered",new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"key","key",-1516042587),key,new cljs.core.Keyword(null,"spec","spec",347520401),spec], null));
} else {
}

return cljs.core.reduce_kv((function (state__$1,op_kw,op_handler){
if(cljs.core.truth_(cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(state__$1,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"ops","ops",1237330063),op_kw], null)))){
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("op already registered",new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"key","key",-1516042587),key,new cljs.core.Keyword(null,"op","op",-1882987955),op_kw], null));
} else {
}

return cljs.core.assoc_in(state__$1,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"ops","ops",1237330063),op_kw], null),op_handler);
}),cljs.core.assoc_in(state,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"extensions","extensions",-1103629196),key], null),spec),ops);
});
shadow.remote.runtime.shared.add_extension = (function shadow$remote$runtime$shared$add_extension(p__66417,key,spec){
var map__66419 = p__66417;
var map__66419__$1 = cljs.core.__destructure_map(map__66419);
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66419__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(state_ref,shadow.remote.runtime.shared.add_extension_STAR_,key,spec);
});
shadow.remote.runtime.shared.add_defaults = (function shadow$remote$runtime$shared$add_defaults(runtime){
return shadow.remote.runtime.shared.add_extension(runtime,new cljs.core.Keyword("shadow.remote.runtime.shared","defaults","shadow.remote.runtime.shared/defaults",-1821257543),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"ops","ops",1237330063),new cljs.core.PersistentArrayMap(null, 5, [new cljs.core.Keyword(null,"welcome","welcome",-578152123),(function (p1__66426_SHARP_){
return shadow.remote.runtime.shared.welcome(runtime,p1__66426_SHARP_);
}),new cljs.core.Keyword(null,"unknown-relay-op","unknown-relay-op",170832753),(function (p1__66427_SHARP_){
return shadow.remote.runtime.shared.unknown_relay_op(p1__66427_SHARP_);
}),new cljs.core.Keyword(null,"unknown-op","unknown-op",1900385996),(function (p1__66428_SHARP_){
return shadow.remote.runtime.shared.unknown_op(p1__66428_SHARP_);
}),new cljs.core.Keyword(null,"ping","ping",-1670114784),(function (p1__66429_SHARP_){
return shadow.remote.runtime.shared.ping(runtime,p1__66429_SHARP_);
}),new cljs.core.Keyword(null,"request-supported-ops","request-supported-ops",-1034994502),(function (p1__66430_SHARP_){
return shadow.remote.runtime.shared.request_supported_ops(runtime,p1__66430_SHARP_);
})], null)], null));
});
shadow.remote.runtime.shared.del_extension_STAR_ = (function shadow$remote$runtime$shared$del_extension_STAR_(state,key){
var ext = cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(state,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"extensions","extensions",-1103629196),key], null));
if(cljs.core.not(ext)){
return state;
} else {
return cljs.core.reduce_kv((function (state__$1,op_kw,op_handler){
return cljs.core.update_in.cljs$core$IFn$_invoke$arity$4(state__$1,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"ops","ops",1237330063)], null),cljs.core.dissoc,op_kw);
}),cljs.core.update.cljs$core$IFn$_invoke$arity$4(state,new cljs.core.Keyword(null,"extensions","extensions",-1103629196),cljs.core.dissoc,key),new cljs.core.Keyword(null,"ops","ops",1237330063).cljs$core$IFn$_invoke$arity$1(ext));
}
});
shadow.remote.runtime.shared.del_extension = (function shadow$remote$runtime$shared$del_extension(p__66492,key){
var map__66496 = p__66492;
var map__66496__$1 = cljs.core.__destructure_map(map__66496);
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66496__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(state_ref,shadow.remote.runtime.shared.del_extension_STAR_,key);
});
shadow.remote.runtime.shared.unhandled_call_result = (function shadow$remote$runtime$shared$unhandled_call_result(call_config,msg){
return console.warn("unhandled call result",msg,call_config);
});
shadow.remote.runtime.shared.unhandled_client_not_found = (function shadow$remote$runtime$shared$unhandled_client_not_found(p__66526,msg){
var map__66532 = p__66526;
var map__66532__$1 = cljs.core.__destructure_map(map__66532);
var runtime = map__66532__$1;
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66532__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
return shadow.remote.runtime.shared.trigger_BANG_.cljs$core$IFn$_invoke$arity$variadic(runtime,new cljs.core.Keyword(null,"on-client-not-found","on-client-not-found",-642452849),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([msg], 0));
});
shadow.remote.runtime.shared.reply_unknown_op = (function shadow$remote$runtime$shared$reply_unknown_op(runtime,msg){
return shadow.remote.runtime.shared.reply(runtime,msg,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"unknown-op","unknown-op",1900385996),new cljs.core.Keyword(null,"msg","msg",-1386103444),msg], null));
});
shadow.remote.runtime.shared.process = (function shadow$remote$runtime$shared$process(p__66547,p__66548){
var map__66549 = p__66547;
var map__66549__$1 = cljs.core.__destructure_map(map__66549);
var runtime = map__66549__$1;
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66549__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
var map__66550 = p__66548;
var map__66550__$1 = cljs.core.__destructure_map(map__66550);
var msg = map__66550__$1;
var op = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66550__$1,new cljs.core.Keyword(null,"op","op",-1882987955));
var call_id = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66550__$1,new cljs.core.Keyword(null,"call-id","call-id",1043012968));
var state = cljs.core.deref(state_ref);
var op_handler = cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(state,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"ops","ops",1237330063),op], null));
if(cljs.core.truth_(call_id)){
var cfg = cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(state,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"call-handlers","call-handlers",386605551),call_id], null));
var call_handler = cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(cfg,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"handlers","handlers",79528781),op], null));
if(cljs.core.truth_(call_handler)){
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$variadic(state_ref,cljs.core.update,new cljs.core.Keyword(null,"call-handlers","call-handlers",386605551),cljs.core.dissoc,cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([call_id], 0));

return (call_handler.cljs$core$IFn$_invoke$arity$1 ? call_handler.cljs$core$IFn$_invoke$arity$1(msg) : call_handler.call(null,msg));
} else {
if(cljs.core.truth_(op_handler)){
return (op_handler.cljs$core$IFn$_invoke$arity$1 ? op_handler.cljs$core$IFn$_invoke$arity$1(msg) : op_handler.call(null,msg));
} else {
return shadow.remote.runtime.shared.unhandled_call_result(cfg,msg);

}
}
} else {
if(cljs.core.truth_(op_handler)){
return (op_handler.cljs$core$IFn$_invoke$arity$1 ? op_handler.cljs$core$IFn$_invoke$arity$1(msg) : op_handler.call(null,msg));
} else {
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"client-not-found","client-not-found",-1754042614),op)){
return shadow.remote.runtime.shared.unhandled_client_not_found(runtime,msg);
} else {
return shadow.remote.runtime.shared.reply_unknown_op(runtime,msg);

}
}
}
});
shadow.remote.runtime.shared.run_on_idle = (function shadow$remote$runtime$shared$run_on_idle(state_ref){
var seq__66556 = cljs.core.seq(cljs.core.vals(new cljs.core.Keyword(null,"extensions","extensions",-1103629196).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(state_ref))));
var chunk__66558 = null;
var count__66559 = (0);
var i__66560 = (0);
while(true){
if((i__66560 < count__66559)){
var map__66572 = chunk__66558.cljs$core$IIndexed$_nth$arity$2(null,i__66560);
var map__66572__$1 = cljs.core.__destructure_map(map__66572);
var on_idle = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66572__$1,new cljs.core.Keyword(null,"on-idle","on-idle",2044706602));
if(cljs.core.truth_(on_idle)){
(on_idle.cljs$core$IFn$_invoke$arity$0 ? on_idle.cljs$core$IFn$_invoke$arity$0() : on_idle.call(null));


var G__66631 = seq__66556;
var G__66632 = chunk__66558;
var G__66633 = count__66559;
var G__66634 = (i__66560 + (1));
seq__66556 = G__66631;
chunk__66558 = G__66632;
count__66559 = G__66633;
i__66560 = G__66634;
continue;
} else {
var G__66636 = seq__66556;
var G__66637 = chunk__66558;
var G__66638 = count__66559;
var G__66639 = (i__66560 + (1));
seq__66556 = G__66636;
chunk__66558 = G__66637;
count__66559 = G__66638;
i__66560 = G__66639;
continue;
}
} else {
var temp__5753__auto__ = cljs.core.seq(seq__66556);
if(temp__5753__auto__){
var seq__66556__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__66556__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__66556__$1);
var G__66641 = cljs.core.chunk_rest(seq__66556__$1);
var G__66642 = c__5565__auto__;
var G__66643 = cljs.core.count(c__5565__auto__);
var G__66644 = (0);
seq__66556 = G__66641;
chunk__66558 = G__66642;
count__66559 = G__66643;
i__66560 = G__66644;
continue;
} else {
var map__66576 = cljs.core.first(seq__66556__$1);
var map__66576__$1 = cljs.core.__destructure_map(map__66576);
var on_idle = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__66576__$1,new cljs.core.Keyword(null,"on-idle","on-idle",2044706602));
if(cljs.core.truth_(on_idle)){
(on_idle.cljs$core$IFn$_invoke$arity$0 ? on_idle.cljs$core$IFn$_invoke$arity$0() : on_idle.call(null));


var G__66646 = cljs.core.next(seq__66556__$1);
var G__66647 = null;
var G__66648 = (0);
var G__66649 = (0);
seq__66556 = G__66646;
chunk__66558 = G__66647;
count__66559 = G__66648;
i__66560 = G__66649;
continue;
} else {
var G__66650 = cljs.core.next(seq__66556__$1);
var G__66651 = null;
var G__66652 = (0);
var G__66653 = (0);
seq__66556 = G__66650;
chunk__66558 = G__66651;
count__66559 = G__66652;
i__66560 = G__66653;
continue;
}
}
} else {
return null;
}
}
break;
}
});

//# sourceMappingURL=shadow.remote.runtime.shared.js.map
