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
shadow.remote.runtime.shared.reply = (function shadow$remote$runtime$shared$reply(runtime,p__46951,res){
var map__46954 = p__46951;
var map__46954__$1 = cljs.core.__destructure_map(map__46954);
var call_id = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__46954__$1,new cljs.core.Keyword(null,"call-id","call-id",1043012968));
var from = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__46954__$1,new cljs.core.Keyword(null,"from","from",1815293044));
var res__$1 = (function (){var G__46964 = res;
var G__46964__$1 = (cljs.core.truth_(call_id)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__46964,new cljs.core.Keyword(null,"call-id","call-id",1043012968),call_id):G__46964);
if(cljs.core.truth_(from)){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__46964__$1,new cljs.core.Keyword(null,"to","to",192099007),from);
} else {
return G__46964__$1;
}
})();
return shadow.remote.runtime.api.relay_msg(runtime,res__$1);
});
shadow.remote.runtime.shared.call = (function shadow$remote$runtime$shared$call(var_args){
var G__46997 = arguments.length;
switch (G__46997) {
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

(shadow.remote.runtime.shared.call.cljs$core$IFn$_invoke$arity$4 = (function (p__47005,msg,handlers,timeout_after_ms){
var map__47007 = p__47005;
var map__47007__$1 = cljs.core.__destructure_map(map__47007);
var runtime = map__47007__$1;
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47007__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
var call_id = new cljs.core.Keyword(null,"call-id-seq","call-id-seq",-1679248218).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(state_ref));
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(state_ref,cljs.core.update,new cljs.core.Keyword(null,"call-id-seq","call-id-seq",-1679248218),cljs.core.inc);

cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(state_ref,cljs.core.assoc_in,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"call-handlers","call-handlers",386605551),call_id], null),new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"handlers","handlers",79528781),handlers,new cljs.core.Keyword(null,"called-at","called-at",607081160),shadow.remote.runtime.shared.now(),new cljs.core.Keyword(null,"msg","msg",-1386103444),msg,new cljs.core.Keyword(null,"timeout","timeout",-318625318),timeout_after_ms], null));

return shadow.remote.runtime.api.relay_msg(runtime,cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(msg,new cljs.core.Keyword(null,"call-id","call-id",1043012968),call_id));
}));

(shadow.remote.runtime.shared.call.cljs$lang$maxFixedArity = 4);

shadow.remote.runtime.shared.trigger_BANG_ = (function shadow$remote$runtime$shared$trigger_BANG_(var_args){
var args__4870__auto__ = [];
var len__4864__auto___47209 = arguments.length;
var i__4865__auto___47211 = (0);
while(true){
if((i__4865__auto___47211 < len__4864__auto___47209)){
args__4870__auto__.push((arguments[i__4865__auto___47211]));

var G__47213 = (i__4865__auto___47211 + (1));
i__4865__auto___47211 = G__47213;
continue;
} else {
}
break;
}

var argseq__4871__auto__ = ((((2) < args__4870__auto__.length))?(new cljs.core.IndexedSeq(args__4870__auto__.slice((2)),(0),null)):null);
return shadow.remote.runtime.shared.trigger_BANG_.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),(arguments[(1)]),argseq__4871__auto__);
});

(shadow.remote.runtime.shared.trigger_BANG_.cljs$core$IFn$_invoke$arity$variadic = (function (p__47039,ev,args){
var map__47040 = p__47039;
var map__47040__$1 = cljs.core.__destructure_map(map__47040);
var runtime = map__47040__$1;
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47040__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
var seq__47042 = cljs.core.seq(cljs.core.vals(new cljs.core.Keyword(null,"extensions","extensions",-1103629196).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(state_ref))));
var chunk__47045 = null;
var count__47046 = (0);
var i__47047 = (0);
while(true){
if((i__47047 < count__47046)){
var ext = chunk__47045.cljs$core$IIndexed$_nth$arity$2(null,i__47047);
var ev_fn = cljs.core.get.cljs$core$IFn$_invoke$arity$2(ext,ev);
if(cljs.core.truth_(ev_fn)){
cljs.core.apply.cljs$core$IFn$_invoke$arity$2(ev_fn,args);


var G__47215 = seq__47042;
var G__47216 = chunk__47045;
var G__47217 = count__47046;
var G__47218 = (i__47047 + (1));
seq__47042 = G__47215;
chunk__47045 = G__47216;
count__47046 = G__47217;
i__47047 = G__47218;
continue;
} else {
var G__47220 = seq__47042;
var G__47221 = chunk__47045;
var G__47222 = count__47046;
var G__47223 = (i__47047 + (1));
seq__47042 = G__47220;
chunk__47045 = G__47221;
count__47046 = G__47222;
i__47047 = G__47223;
continue;
}
} else {
var temp__5753__auto__ = cljs.core.seq(seq__47042);
if(temp__5753__auto__){
var seq__47042__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__47042__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__47042__$1);
var G__47225 = cljs.core.chunk_rest(seq__47042__$1);
var G__47226 = c__4679__auto__;
var G__47227 = cljs.core.count(c__4679__auto__);
var G__47228 = (0);
seq__47042 = G__47225;
chunk__47045 = G__47226;
count__47046 = G__47227;
i__47047 = G__47228;
continue;
} else {
var ext = cljs.core.first(seq__47042__$1);
var ev_fn = cljs.core.get.cljs$core$IFn$_invoke$arity$2(ext,ev);
if(cljs.core.truth_(ev_fn)){
cljs.core.apply.cljs$core$IFn$_invoke$arity$2(ev_fn,args);


var G__47229 = cljs.core.next(seq__47042__$1);
var G__47230 = null;
var G__47231 = (0);
var G__47232 = (0);
seq__47042 = G__47229;
chunk__47045 = G__47230;
count__47046 = G__47231;
i__47047 = G__47232;
continue;
} else {
var G__47234 = cljs.core.next(seq__47042__$1);
var G__47235 = null;
var G__47236 = (0);
var G__47237 = (0);
seq__47042 = G__47234;
chunk__47045 = G__47235;
count__47046 = G__47236;
i__47047 = G__47237;
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
(shadow.remote.runtime.shared.trigger_BANG_.cljs$lang$applyTo = (function (seq47029){
var G__47030 = cljs.core.first(seq47029);
var seq47029__$1 = cljs.core.next(seq47029);
var G__47031 = cljs.core.first(seq47029__$1);
var seq47029__$2 = cljs.core.next(seq47029__$1);
var self__4851__auto__ = this;
return self__4851__auto__.cljs$core$IFn$_invoke$arity$variadic(G__47030,G__47031,seq47029__$2);
}));

shadow.remote.runtime.shared.welcome = (function shadow$remote$runtime$shared$welcome(p__47077,p__47078){
var map__47080 = p__47077;
var map__47080__$1 = cljs.core.__destructure_map(map__47080);
var runtime = map__47080__$1;
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47080__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
var map__47081 = p__47078;
var map__47081__$1 = cljs.core.__destructure_map(map__47081);
var msg = map__47081__$1;
var client_id = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47081__$1,new cljs.core.Keyword(null,"client-id","client-id",-464622140));
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(state_ref,cljs.core.assoc,new cljs.core.Keyword(null,"client-id","client-id",-464622140),client_id);

var map__47083 = cljs.core.deref(state_ref);
var map__47083__$1 = cljs.core.__destructure_map(map__47083);
var client_info = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47083__$1,new cljs.core.Keyword(null,"client-info","client-info",1958982504));
var extensions = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47083__$1,new cljs.core.Keyword(null,"extensions","extensions",-1103629196));
shadow.remote.runtime.shared.relay_msg(runtime,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"hello","hello",-245025397),new cljs.core.Keyword(null,"client-info","client-info",1958982504),client_info], null));

return shadow.remote.runtime.shared.trigger_BANG_(runtime,new cljs.core.Keyword(null,"on-welcome","on-welcome",1895317125));
});
shadow.remote.runtime.shared.ping = (function shadow$remote$runtime$shared$ping(runtime,msg){
return shadow.remote.runtime.shared.reply(runtime,msg,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"pong","pong",-172484958)], null));
});
shadow.remote.runtime.shared.get_client_id = (function shadow$remote$runtime$shared$get_client_id(p__47086){
var map__47087 = p__47086;
var map__47087__$1 = cljs.core.__destructure_map(map__47087);
var runtime = map__47087__$1;
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47087__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
var or__4253__auto__ = new cljs.core.Keyword(null,"client-id","client-id",-464622140).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(state_ref));
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("runtime has no assigned runtime-id",new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"runtime","runtime",-1331573996),runtime], null));
}
});
shadow.remote.runtime.shared.request_supported_ops = (function shadow$remote$runtime$shared$request_supported_ops(p__47091,msg){
var map__47092 = p__47091;
var map__47092__$1 = cljs.core.__destructure_map(map__47092);
var runtime = map__47092__$1;
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47092__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
return shadow.remote.runtime.shared.reply(runtime,msg,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"supported-ops","supported-ops",337914702),new cljs.core.Keyword(null,"ops","ops",1237330063),cljs.core.disj.cljs$core$IFn$_invoke$arity$variadic(cljs.core.set(cljs.core.keys(new cljs.core.Keyword(null,"ops","ops",1237330063).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(state_ref)))),new cljs.core.Keyword(null,"welcome","welcome",-578152123),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"unknown-relay-op","unknown-relay-op",170832753),new cljs.core.Keyword(null,"unknown-op","unknown-op",1900385996),new cljs.core.Keyword(null,"request-supported-ops","request-supported-ops",-1034994502),new cljs.core.Keyword(null,"tool-disconnect","tool-disconnect",189103996)], 0))], null));
});
shadow.remote.runtime.shared.unknown_relay_op = (function shadow$remote$runtime$shared$unknown_relay_op(msg){
return console.warn("unknown-relay-op",msg);
});
shadow.remote.runtime.shared.unknown_op = (function shadow$remote$runtime$shared$unknown_op(msg){
return console.warn("unknown-op",msg);
});
shadow.remote.runtime.shared.add_extension_STAR_ = (function shadow$remote$runtime$shared$add_extension_STAR_(p__47095,key,p__47096){
var map__47097 = p__47095;
var map__47097__$1 = cljs.core.__destructure_map(map__47097);
var state = map__47097__$1;
var extensions = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47097__$1,new cljs.core.Keyword(null,"extensions","extensions",-1103629196));
var map__47098 = p__47096;
var map__47098__$1 = cljs.core.__destructure_map(map__47098);
var spec = map__47098__$1;
var ops = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47098__$1,new cljs.core.Keyword(null,"ops","ops",1237330063));
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
shadow.remote.runtime.shared.add_extension = (function shadow$remote$runtime$shared$add_extension(p__47099,key,spec){
var map__47100 = p__47099;
var map__47100__$1 = cljs.core.__destructure_map(map__47100);
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47100__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(state_ref,shadow.remote.runtime.shared.add_extension_STAR_,key,spec);
});
shadow.remote.runtime.shared.add_defaults = (function shadow$remote$runtime$shared$add_defaults(runtime){
return shadow.remote.runtime.shared.add_extension(runtime,new cljs.core.Keyword("shadow.remote.runtime.shared","defaults","shadow.remote.runtime.shared/defaults",-1821257543),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"ops","ops",1237330063),new cljs.core.PersistentArrayMap(null, 5, [new cljs.core.Keyword(null,"welcome","welcome",-578152123),(function (p1__47101_SHARP_){
return shadow.remote.runtime.shared.welcome(runtime,p1__47101_SHARP_);
}),new cljs.core.Keyword(null,"unknown-relay-op","unknown-relay-op",170832753),(function (p1__47102_SHARP_){
return shadow.remote.runtime.shared.unknown_relay_op(p1__47102_SHARP_);
}),new cljs.core.Keyword(null,"unknown-op","unknown-op",1900385996),(function (p1__47103_SHARP_){
return shadow.remote.runtime.shared.unknown_op(p1__47103_SHARP_);
}),new cljs.core.Keyword(null,"ping","ping",-1670114784),(function (p1__47104_SHARP_){
return shadow.remote.runtime.shared.ping(runtime,p1__47104_SHARP_);
}),new cljs.core.Keyword(null,"request-supported-ops","request-supported-ops",-1034994502),(function (p1__47105_SHARP_){
return shadow.remote.runtime.shared.request_supported_ops(runtime,p1__47105_SHARP_);
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
shadow.remote.runtime.shared.del_extension = (function shadow$remote$runtime$shared$del_extension(p__47119,key){
var map__47125 = p__47119;
var map__47125__$1 = cljs.core.__destructure_map(map__47125);
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47125__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(state_ref,shadow.remote.runtime.shared.del_extension_STAR_,key);
});
shadow.remote.runtime.shared.unhandled_call_result = (function shadow$remote$runtime$shared$unhandled_call_result(call_config,msg){
return console.warn("unhandled call result",msg,call_config);
});
shadow.remote.runtime.shared.unhandled_client_not_found = (function shadow$remote$runtime$shared$unhandled_client_not_found(p__47139,msg){
var map__47141 = p__47139;
var map__47141__$1 = cljs.core.__destructure_map(map__47141);
var runtime = map__47141__$1;
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47141__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
return shadow.remote.runtime.shared.trigger_BANG_.cljs$core$IFn$_invoke$arity$variadic(runtime,new cljs.core.Keyword(null,"on-client-not-found","on-client-not-found",-642452849),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([msg], 0));
});
shadow.remote.runtime.shared.reply_unknown_op = (function shadow$remote$runtime$shared$reply_unknown_op(runtime,msg){
return shadow.remote.runtime.shared.reply(runtime,msg,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"unknown-op","unknown-op",1900385996),new cljs.core.Keyword(null,"msg","msg",-1386103444),msg], null));
});
shadow.remote.runtime.shared.process = (function shadow$remote$runtime$shared$process(p__47145,p__47146){
var map__47148 = p__47145;
var map__47148__$1 = cljs.core.__destructure_map(map__47148);
var runtime = map__47148__$1;
var state_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47148__$1,new cljs.core.Keyword(null,"state-ref","state-ref",2127874952));
var map__47149 = p__47146;
var map__47149__$1 = cljs.core.__destructure_map(map__47149);
var msg = map__47149__$1;
var op = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47149__$1,new cljs.core.Keyword(null,"op","op",-1882987955));
var call_id = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47149__$1,new cljs.core.Keyword(null,"call-id","call-id",1043012968));
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
var seq__47155 = cljs.core.seq(cljs.core.vals(new cljs.core.Keyword(null,"extensions","extensions",-1103629196).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(state_ref))));
var chunk__47157 = null;
var count__47158 = (0);
var i__47159 = (0);
while(true){
if((i__47159 < count__47158)){
var map__47172 = chunk__47157.cljs$core$IIndexed$_nth$arity$2(null,i__47159);
var map__47172__$1 = cljs.core.__destructure_map(map__47172);
var on_idle = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47172__$1,new cljs.core.Keyword(null,"on-idle","on-idle",2044706602));
if(cljs.core.truth_(on_idle)){
(on_idle.cljs$core$IFn$_invoke$arity$0 ? on_idle.cljs$core$IFn$_invoke$arity$0() : on_idle.call(null));


var G__47293 = seq__47155;
var G__47294 = chunk__47157;
var G__47295 = count__47158;
var G__47296 = (i__47159 + (1));
seq__47155 = G__47293;
chunk__47157 = G__47294;
count__47158 = G__47295;
i__47159 = G__47296;
continue;
} else {
var G__47298 = seq__47155;
var G__47299 = chunk__47157;
var G__47300 = count__47158;
var G__47301 = (i__47159 + (1));
seq__47155 = G__47298;
chunk__47157 = G__47299;
count__47158 = G__47300;
i__47159 = G__47301;
continue;
}
} else {
var temp__5753__auto__ = cljs.core.seq(seq__47155);
if(temp__5753__auto__){
var seq__47155__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__47155__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__47155__$1);
var G__47304 = cljs.core.chunk_rest(seq__47155__$1);
var G__47305 = c__4679__auto__;
var G__47306 = cljs.core.count(c__4679__auto__);
var G__47307 = (0);
seq__47155 = G__47304;
chunk__47157 = G__47305;
count__47158 = G__47306;
i__47159 = G__47307;
continue;
} else {
var map__47178 = cljs.core.first(seq__47155__$1);
var map__47178__$1 = cljs.core.__destructure_map(map__47178);
var on_idle = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__47178__$1,new cljs.core.Keyword(null,"on-idle","on-idle",2044706602));
if(cljs.core.truth_(on_idle)){
(on_idle.cljs$core$IFn$_invoke$arity$0 ? on_idle.cljs$core$IFn$_invoke$arity$0() : on_idle.call(null));


var G__47309 = cljs.core.next(seq__47155__$1);
var G__47310 = null;
var G__47311 = (0);
var G__47312 = (0);
seq__47155 = G__47309;
chunk__47157 = G__47310;
count__47158 = G__47311;
i__47159 = G__47312;
continue;
} else {
var G__47314 = cljs.core.next(seq__47155__$1);
var G__47315 = null;
var G__47316 = (0);
var G__47317 = (0);
seq__47155 = G__47314;
chunk__47157 = G__47315;
count__47158 = G__47316;
i__47159 = G__47317;
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
