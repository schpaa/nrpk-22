goog.provide('db.core');
var module$node_modules$firebase$app$dist$index_esm=shadow.js.require("module$node_modules$firebase$app$dist$index_esm", {});
var module$node_modules$firebase$firestore$dist$index_esm=shadow.js.require("module$node_modules$firebase$firestore$dist$index_esm", {});
var module$node_modules$firebase$storage$dist$index_esm=shadow.js.require("module$node_modules$firebase$storage$dist$index_esm", {});
var module$node_modules$firebase$database$dist$index_esm=shadow.js.require("module$node_modules$firebase$database$dist$index_esm", {});
db.core.debug_QMARK_ = goog.DEBUG;
db.core.database_set = db.rtdb.ref_set;
db.core.database_update = db.rtdb.ref_update;
db.core.database_update_increment = db.rtdb.database_update_increment;
db.core.database_push = db.rtdb.ref_push;
db.core.database_get = db.rtdb.ref_get;
db.core.on_value_reaction = db.rtdb.on_value_reaction;
db.core.on_snapshot_docs_reaction = db.fsdb.on_snapshot_docs_reaction;
db.core.on_snapshot_doc_reaction = db.fsdb.on_snapshot_doc_reaction;
db.core.sign_out = db.auth.sign_out;
db.core.firestore_set = db.fsdb.firestore_set;
db.core.firestore_add = db.fsdb.firestore_add;
db.core.timestamp = (function db$core$timestamp(m){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(m,new cljs.core.Keyword(null,"timestamp","timestamp",579478971),cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs_time.core.now()));
});
db.core.init_BANG_ = (function db$core$init_BANG_(p__73948){
var map__73949 = p__73948;
var map__73949__$1 = cljs.core.__destructure_map(map__73949);
var config = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73949__$1,new cljs.core.Keyword(null,"config","config",994861415));
console.log("db/init!");

cljs.core.reset_BANG_(db.state.app,module$node_modules$firebase$app$dist$index_esm.initializeApp(cljs.core.clj__GT_js(config)));

if(cljs.core.truth_(db.core.debug_QMARK_)){
module$node_modules$firebase$database$dist$index_esm.connectDatabaseEmulator(module$node_modules$firebase$database$dist$index_esm.getDatabase(),"localhost",(9009));

module$node_modules$firebase$storage$dist$index_esm.connectStorageEmulator(module$node_modules$firebase$storage$dist$index_esm.getStorage(),"localhost",(9199));

return module$node_modules$firebase$firestore$dist$index_esm.connectFirestoreEmulator(module$node_modules$firebase$firestore$dist$index_esm.getFirestore(),"localhost",(8080));
} else {
return null;
}
});
re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword("db.core","presence-status","db.core/presence-status",1406778448),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([(function (_){
var G__73952 = new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"path","path",-188191168),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, ["presence"], null)], null);
return (db.core.on_value_reaction.cljs$core$IFn$_invoke$arity$1 ? db.core.on_value_reaction.cljs$core$IFn$_invoke$arity$1(G__73952) : db.core.on_value_reaction.call(null,G__73952));
}),(function (input,_){
var data = cljs.core.reduce.cljs$core$IFn$_invoke$arity$3((function (a,p__73956){
var vec__73957 = p__73956;
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__73957,(0),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__73957,(1),null);
var temp__5751__auto__ = new cljs.core.Keyword(null,"connections","connections",-2064090887).cljs$core$IFn$_invoke$arity$1(v);
if(cljs.core.truth_(temp__5751__auto__)){
var c = temp__5751__auto__;
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(a,k,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"connections","connections",-2064090887),cljs.core.count(c),new cljs.core.Keyword(null,"ugh","ugh",241810220),cljs.core.val(cljs.core.first(c))], null));
} else {
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(a,k,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"lastOnline","lastOnline",-658077326),cljs_time.coerce.from_long(new cljs.core.Keyword(null,"lastOnline","lastOnline",-658077326).cljs$core$IFn$_invoke$arity$1(v))], null));
}
}),cljs.core.PersistentArrayMap.EMPTY,cljs.core.seq(input));
return new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"offline","offline",-107631935),cljs.core.filterv(cljs.core.comp.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"lastOnline","lastOnline",-658077326),cljs.core.val),data),new cljs.core.Keyword(null,"online","online",868574801),cljs.core.filterv(cljs.core.comp.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"connections","connections",-2064090887),cljs.core.val),data)], null);
})], 0));
re_frame.core.reg_event_db.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword("db.core","set-fake-user","db.core/set-fake-user",-726862924),(function (db__$1,p__73962){
var vec__73963 = p__73962;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__73963,(0),null);
var arg = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__73963,(1),null);
if((((arg == null)) || (cljs.core.map_QMARK_(arg)))){
} else {
throw (new Error("Assert failed: (or (nil? arg) (map? arg))"));
}

return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(db__$1,new cljs.core.Keyword("db.core","fake-user","db.core/fake-user",-1832107314),arg);
}));
re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword("db.core","fake-user","db.core/fake-user",-1832107314),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([(function (db__$1){
return cljs.core.get.cljs$core$IFn$_invoke$arity$2(db__$1,new cljs.core.Keyword("db.core","fake-user","db.core/fake-user",-1832107314));
})], 0));
re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword("db.core","user-auth","db.core/user-auth",-1883386069),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([db.auth.user_info,(function (user__$1,_){
if(((cljs.core.not(user__$1)) || ((user__$1 instanceof Error)))){
return null;
} else {
return user__$1;
}
})], 0));
re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword("db.core","root-auth","db.core/root-auth",-1227105671),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"<-","<-",760412998),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("db.core","user-auth","db.core/user-auth",-1883386069)], null),new cljs.core.Keyword(null,"<-","<-",760412998),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("db.core","fake-user","db.core/fake-user",-1832107314)], null),(function (p__73966,p__73967){
var vec__73968 = p__73966;
var ua = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__73968,(0),null);
var fu = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__73968,(1),null);
var vec__73971 = p__73967;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__73971,(0),null);
var field = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__73971,(1),null);
var u = (function (){var or__5043__auto__ = fu;
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
return ua;
}
})();
var G__73974 = field;
var G__73974__$1 = (((G__73974 instanceof cljs.core.Keyword))?G__73974.fqn:null);
switch (G__73974__$1) {
case "uid":
return new cljs.core.Keyword(null,"uid","uid",-1447769400).cljs$core$IFn$_invoke$arity$1(u);

break;
default:
return u;

}
})], 0));

//# sourceMappingURL=db.core.js.map
