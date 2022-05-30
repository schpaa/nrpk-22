goog.provide('shadow.remote.runtime.tap_support');
shadow.remote.runtime.tap_support.tap_subscribe = (function shadow$remote$runtime$tap_support$tap_subscribe(p__69900,p__69901){
var map__69902 = p__69900;
var map__69902__$1 = cljs.core.__destructure_map(map__69902);
var svc = map__69902__$1;
var subs_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69902__$1,new cljs.core.Keyword(null,"subs-ref","subs-ref",-1355989911));
var obj_support = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69902__$1,new cljs.core.Keyword(null,"obj-support","obj-support",1522559229));
var runtime = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69902__$1,new cljs.core.Keyword(null,"runtime","runtime",-1331573996));
var map__69903 = p__69901;
var map__69903__$1 = cljs.core.__destructure_map(map__69903);
var msg = map__69903__$1;
var from = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69903__$1,new cljs.core.Keyword(null,"from","from",1815293044));
var summary = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69903__$1,new cljs.core.Keyword(null,"summary","summary",380847952));
var history__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69903__$1,new cljs.core.Keyword(null,"history","history",-247395220));
var num = cljs.core.get.cljs$core$IFn$_invoke$arity$3(map__69903__$1,new cljs.core.Keyword(null,"num","num",1985240673),(10));
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(subs_ref,cljs.core.assoc,from,msg);

if(cljs.core.truth_(history__$1)){
return shadow.remote.runtime.shared.reply(runtime,msg,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"tap-subscribed","tap-subscribed",-1882247432),new cljs.core.Keyword(null,"history","history",-247395220),cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentVector.EMPTY,cljs.core.map.cljs$core$IFn$_invoke$arity$2((function (oid){
return new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"oid","oid",-768692334),oid,new cljs.core.Keyword(null,"summary","summary",380847952),shadow.remote.runtime.obj_support.obj_describe_STAR_(obj_support,oid)], null);
}),shadow.remote.runtime.obj_support.get_tap_history(obj_support,num)))], null));
} else {
return null;
}
});
shadow.remote.runtime.tap_support.tap_unsubscribe = (function shadow$remote$runtime$tap_support$tap_unsubscribe(p__69920,p__69921){
var map__69923 = p__69920;
var map__69923__$1 = cljs.core.__destructure_map(map__69923);
var subs_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69923__$1,new cljs.core.Keyword(null,"subs-ref","subs-ref",-1355989911));
var map__69924 = p__69921;
var map__69924__$1 = cljs.core.__destructure_map(map__69924);
var from = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69924__$1,new cljs.core.Keyword(null,"from","from",1815293044));
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(subs_ref,cljs.core.dissoc,from);
});
shadow.remote.runtime.tap_support.request_tap_history = (function shadow$remote$runtime$tap_support$request_tap_history(p__69927,p__69928){
var map__69930 = p__69927;
var map__69930__$1 = cljs.core.__destructure_map(map__69930);
var obj_support = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69930__$1,new cljs.core.Keyword(null,"obj-support","obj-support",1522559229));
var runtime = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69930__$1,new cljs.core.Keyword(null,"runtime","runtime",-1331573996));
var map__69931 = p__69928;
var map__69931__$1 = cljs.core.__destructure_map(map__69931);
var msg = map__69931__$1;
var num = cljs.core.get.cljs$core$IFn$_invoke$arity$3(map__69931__$1,new cljs.core.Keyword(null,"num","num",1985240673),(10));
var tap_ids = shadow.remote.runtime.obj_support.get_tap_history(obj_support,num);
return shadow.remote.runtime.shared.reply(runtime,msg,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"tap-history","tap-history",-282803347),new cljs.core.Keyword(null,"oids","oids",-1580877688),tap_ids], null));
});
shadow.remote.runtime.tap_support.tool_disconnect = (function shadow$remote$runtime$tap_support$tool_disconnect(p__69935,tid){
var map__69936 = p__69935;
var map__69936__$1 = cljs.core.__destructure_map(map__69936);
var svc = map__69936__$1;
var subs_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69936__$1,new cljs.core.Keyword(null,"subs-ref","subs-ref",-1355989911));
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(subs_ref,cljs.core.dissoc,tid);
});
shadow.remote.runtime.tap_support.start = (function shadow$remote$runtime$tap_support$start(runtime,obj_support){
var subs_ref = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(cljs.core.PersistentArrayMap.EMPTY);
var tap_fn = (function shadow$remote$runtime$tap_support$start_$_runtime_tap(obj){
if((!((obj == null)))){
var oid = shadow.remote.runtime.obj_support.register(obj_support,obj,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"from","from",1815293044),new cljs.core.Keyword(null,"tap","tap",-1086702463)], null));
var seq__69962 = cljs.core.seq(cljs.core.deref(subs_ref));
var chunk__69963 = null;
var count__69964 = (0);
var i__69965 = (0);
while(true){
if((i__69965 < count__69964)){
var vec__70000 = chunk__69963.cljs$core$IIndexed$_nth$arity$2(null,i__69965);
var tid = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__70000,(0),null);
var tap_config = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__70000,(1),null);
shadow.remote.runtime.api.relay_msg(runtime,new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"tap","tap",-1086702463),new cljs.core.Keyword(null,"to","to",192099007),tid,new cljs.core.Keyword(null,"oid","oid",-768692334),oid], null));


var G__70019 = seq__69962;
var G__70020 = chunk__69963;
var G__70021 = count__69964;
var G__70022 = (i__69965 + (1));
seq__69962 = G__70019;
chunk__69963 = G__70020;
count__69964 = G__70021;
i__69965 = G__70022;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__69962);
if(temp__5753__auto__){
var seq__69962__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__69962__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__69962__$1);
var G__70023 = cljs.core.chunk_rest(seq__69962__$1);
var G__70024 = c__5565__auto__;
var G__70025 = cljs.core.count(c__5565__auto__);
var G__70026 = (0);
seq__69962 = G__70023;
chunk__69963 = G__70024;
count__69964 = G__70025;
i__69965 = G__70026;
continue;
} else {
var vec__70008 = cljs.core.first(seq__69962__$1);
var tid = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__70008,(0),null);
var tap_config = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__70008,(1),null);
shadow.remote.runtime.api.relay_msg(runtime,new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"tap","tap",-1086702463),new cljs.core.Keyword(null,"to","to",192099007),tid,new cljs.core.Keyword(null,"oid","oid",-768692334),oid], null));


var G__70031 = cljs.core.next(seq__69962__$1);
var G__70032 = null;
var G__70033 = (0);
var G__70034 = (0);
seq__69962 = G__70031;
chunk__69963 = G__70032;
count__69964 = G__70033;
i__69965 = G__70034;
continue;
}
} else {
return null;
}
}
break;
}
} else {
return null;
}
});
var svc = new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"runtime","runtime",-1331573996),runtime,new cljs.core.Keyword(null,"obj-support","obj-support",1522559229),obj_support,new cljs.core.Keyword(null,"tap-fn","tap-fn",1573556461),tap_fn,new cljs.core.Keyword(null,"subs-ref","subs-ref",-1355989911),subs_ref], null);
shadow.remote.runtime.api.add_extension(runtime,new cljs.core.Keyword("shadow.remote.runtime.tap-support","ext","shadow.remote.runtime.tap-support/ext",1019069674),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"ops","ops",1237330063),new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"tap-subscribe","tap-subscribe",411179050),(function (p1__69941_SHARP_){
return shadow.remote.runtime.tap_support.tap_subscribe(svc,p1__69941_SHARP_);
}),new cljs.core.Keyword(null,"tap-unsubscribe","tap-unsubscribe",1183890755),(function (p1__69942_SHARP_){
return shadow.remote.runtime.tap_support.tap_unsubscribe(svc,p1__69942_SHARP_);
}),new cljs.core.Keyword(null,"request-tap-history","request-tap-history",-670837812),(function (p1__69943_SHARP_){
return shadow.remote.runtime.tap_support.request_tap_history(svc,p1__69943_SHARP_);
})], null),new cljs.core.Keyword(null,"on-tool-disconnect","on-tool-disconnect",693464366),(function (p1__69944_SHARP_){
return shadow.remote.runtime.tap_support.tool_disconnect(svc,p1__69944_SHARP_);
})], null));

cljs.core.add_tap(tap_fn);

return svc;
});
shadow.remote.runtime.tap_support.stop = (function shadow$remote$runtime$tap_support$stop(p__70014){
var map__70015 = p__70014;
var map__70015__$1 = cljs.core.__destructure_map(map__70015);
var svc = map__70015__$1;
var tap_fn = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70015__$1,new cljs.core.Keyword(null,"tap-fn","tap-fn",1573556461));
var runtime = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70015__$1,new cljs.core.Keyword(null,"runtime","runtime",-1331573996));
cljs.core.remove_tap(tap_fn);

return shadow.remote.runtime.api.del_extension(runtime,new cljs.core.Keyword("shadow.remote.runtime.tap-support","ext","shadow.remote.runtime.tap-support/ext",1019069674));
});

//# sourceMappingURL=shadow.remote.runtime.tap_support.js.map
