goog.provide('shadow.remote.runtime.tap_support');
shadow.remote.runtime.tap_support.tap_subscribe = (function shadow$remote$runtime$tap_support$tap_subscribe(p__50660,p__50661){
var map__50664 = p__50660;
var map__50664__$1 = cljs.core.__destructure_map(map__50664);
var svc = map__50664__$1;
var subs_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__50664__$1,new cljs.core.Keyword(null,"subs-ref","subs-ref",-1355989911));
var obj_support = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__50664__$1,new cljs.core.Keyword(null,"obj-support","obj-support",1522559229));
var runtime = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__50664__$1,new cljs.core.Keyword(null,"runtime","runtime",-1331573996));
var map__50665 = p__50661;
var map__50665__$1 = cljs.core.__destructure_map(map__50665);
var msg = map__50665__$1;
var from = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__50665__$1,new cljs.core.Keyword(null,"from","from",1815293044));
var summary = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__50665__$1,new cljs.core.Keyword(null,"summary","summary",380847952));
var history = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__50665__$1,new cljs.core.Keyword(null,"history","history",-247395220));
var num = cljs.core.get.cljs$core$IFn$_invoke$arity$3(map__50665__$1,new cljs.core.Keyword(null,"num","num",1985240673),(10));
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(subs_ref,cljs.core.assoc,from,msg);

if(cljs.core.truth_(history)){
return shadow.remote.runtime.shared.reply(runtime,msg,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"tap-subscribed","tap-subscribed",-1882247432),new cljs.core.Keyword(null,"history","history",-247395220),cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentVector.EMPTY,cljs.core.map.cljs$core$IFn$_invoke$arity$2((function (oid){
return new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"oid","oid",-768692334),oid,new cljs.core.Keyword(null,"summary","summary",380847952),shadow.remote.runtime.obj_support.obj_describe_STAR_(obj_support,oid)], null);
}),shadow.remote.runtime.obj_support.get_tap_history(obj_support,num)))], null));
} else {
return null;
}
});
shadow.remote.runtime.tap_support.tap_unsubscribe = (function shadow$remote$runtime$tap_support$tap_unsubscribe(p__50677,p__50678){
var map__50679 = p__50677;
var map__50679__$1 = cljs.core.__destructure_map(map__50679);
var subs_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__50679__$1,new cljs.core.Keyword(null,"subs-ref","subs-ref",-1355989911));
var map__50680 = p__50678;
var map__50680__$1 = cljs.core.__destructure_map(map__50680);
var from = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__50680__$1,new cljs.core.Keyword(null,"from","from",1815293044));
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(subs_ref,cljs.core.dissoc,from);
});
shadow.remote.runtime.tap_support.request_tap_history = (function shadow$remote$runtime$tap_support$request_tap_history(p__50684,p__50685){
var map__50686 = p__50684;
var map__50686__$1 = cljs.core.__destructure_map(map__50686);
var obj_support = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__50686__$1,new cljs.core.Keyword(null,"obj-support","obj-support",1522559229));
var runtime = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__50686__$1,new cljs.core.Keyword(null,"runtime","runtime",-1331573996));
var map__50687 = p__50685;
var map__50687__$1 = cljs.core.__destructure_map(map__50687);
var msg = map__50687__$1;
var num = cljs.core.get.cljs$core$IFn$_invoke$arity$3(map__50687__$1,new cljs.core.Keyword(null,"num","num",1985240673),(10));
var tap_ids = shadow.remote.runtime.obj_support.get_tap_history(obj_support,num);
return shadow.remote.runtime.shared.reply(runtime,msg,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"tap-history","tap-history",-282803347),new cljs.core.Keyword(null,"oids","oids",-1580877688),tap_ids], null));
});
shadow.remote.runtime.tap_support.tool_disconnect = (function shadow$remote$runtime$tap_support$tool_disconnect(p__50690,tid){
var map__50691 = p__50690;
var map__50691__$1 = cljs.core.__destructure_map(map__50691);
var svc = map__50691__$1;
var subs_ref = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__50691__$1,new cljs.core.Keyword(null,"subs-ref","subs-ref",-1355989911));
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(subs_ref,cljs.core.dissoc,tid);
});
shadow.remote.runtime.tap_support.start = (function shadow$remote$runtime$tap_support$start(runtime,obj_support){
var subs_ref = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(cljs.core.PersistentArrayMap.EMPTY);
var tap_fn = (function shadow$remote$runtime$tap_support$start_$_runtime_tap(obj){
if((!((obj == null)))){
var oid = shadow.remote.runtime.obj_support.register(obj_support,obj,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"from","from",1815293044),new cljs.core.Keyword(null,"tap","tap",-1086702463)], null));
var seq__50700 = cljs.core.seq(cljs.core.deref(subs_ref));
var chunk__50701 = null;
var count__50702 = (0);
var i__50703 = (0);
while(true){
if((i__50703 < count__50702)){
var vec__50719 = chunk__50701.cljs$core$IIndexed$_nth$arity$2(null,i__50703);
var tid = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__50719,(0),null);
var tap_config = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__50719,(1),null);
shadow.remote.runtime.api.relay_msg(runtime,new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"tap","tap",-1086702463),new cljs.core.Keyword(null,"to","to",192099007),tid,new cljs.core.Keyword(null,"oid","oid",-768692334),oid], null));


var G__50739 = seq__50700;
var G__50740 = chunk__50701;
var G__50741 = count__50702;
var G__50742 = (i__50703 + (1));
seq__50700 = G__50739;
chunk__50701 = G__50740;
count__50702 = G__50741;
i__50703 = G__50742;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__50700);
if(temp__5753__auto__){
var seq__50700__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__50700__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__50700__$1);
var G__50744 = cljs.core.chunk_rest(seq__50700__$1);
var G__50745 = c__4679__auto__;
var G__50746 = cljs.core.count(c__4679__auto__);
var G__50747 = (0);
seq__50700 = G__50744;
chunk__50701 = G__50745;
count__50702 = G__50746;
i__50703 = G__50747;
continue;
} else {
var vec__50724 = cljs.core.first(seq__50700__$1);
var tid = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__50724,(0),null);
var tap_config = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__50724,(1),null);
shadow.remote.runtime.api.relay_msg(runtime,new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"tap","tap",-1086702463),new cljs.core.Keyword(null,"to","to",192099007),tid,new cljs.core.Keyword(null,"oid","oid",-768692334),oid], null));


var G__50748 = cljs.core.next(seq__50700__$1);
var G__50749 = null;
var G__50750 = (0);
var G__50751 = (0);
seq__50700 = G__50748;
chunk__50701 = G__50749;
count__50702 = G__50750;
i__50703 = G__50751;
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
shadow.remote.runtime.api.add_extension(runtime,new cljs.core.Keyword("shadow.remote.runtime.tap-support","ext","shadow.remote.runtime.tap-support/ext",1019069674),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"ops","ops",1237330063),new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"tap-subscribe","tap-subscribe",411179050),(function (p1__50693_SHARP_){
return shadow.remote.runtime.tap_support.tap_subscribe(svc,p1__50693_SHARP_);
}),new cljs.core.Keyword(null,"tap-unsubscribe","tap-unsubscribe",1183890755),(function (p1__50694_SHARP_){
return shadow.remote.runtime.tap_support.tap_unsubscribe(svc,p1__50694_SHARP_);
}),new cljs.core.Keyword(null,"request-tap-history","request-tap-history",-670837812),(function (p1__50695_SHARP_){
return shadow.remote.runtime.tap_support.request_tap_history(svc,p1__50695_SHARP_);
})], null),new cljs.core.Keyword(null,"on-tool-disconnect","on-tool-disconnect",693464366),(function (p1__50696_SHARP_){
return shadow.remote.runtime.tap_support.tool_disconnect(svc,p1__50696_SHARP_);
})], null));

cljs.core.add_tap(tap_fn);

return svc;
});
shadow.remote.runtime.tap_support.stop = (function shadow$remote$runtime$tap_support$stop(p__50731){
var map__50732 = p__50731;
var map__50732__$1 = cljs.core.__destructure_map(map__50732);
var svc = map__50732__$1;
var tap_fn = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__50732__$1,new cljs.core.Keyword(null,"tap-fn","tap-fn",1573556461));
var runtime = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__50732__$1,new cljs.core.Keyword(null,"runtime","runtime",-1331573996));
cljs.core.remove_tap(tap_fn);

return shadow.remote.runtime.api.del_extension(runtime,new cljs.core.Keyword("shadow.remote.runtime.tap-support","ext","shadow.remote.runtime.tap-support/ext",1019069674));
});

//# sourceMappingURL=shadow.remote.runtime.tap_support.js.map
