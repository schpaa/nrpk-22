goog.provide('db.fsdb');
var module$node_modules$firebase$firestore$dist$index_esm=shadow.js.require("module$node_modules$firebase$firestore$dist$index_esm", {});
/**
 * returns a reagent atom that will always have the latest value at 'path' in the Firebase database
 */
db.fsdb.on_snapshot_doc_reaction = (function db$fsdb$on_snapshot_doc_reaction(p__73706){
var map__73708 = p__73706;
var map__73708__$1 = cljs.core.__destructure_map(map__73708);
var path = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73708__$1,new cljs.core.Keyword(null,"path","path",-188191168));
var path_SINGLEQUOTE_ = ((typeof path === 'string')?path:((cljs.core.vector_QMARK_(path))?cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.str,cljs.core.interpose.cljs$core$IFn$_invoke$arity$2("/",path)):null));
var ref = module$node_modules$firebase$firestore$dist$index_esm.doc(module$node_modules$firebase$firestore$dist$index_esm.getFirestore(),path_SINGLEQUOTE_);
var reaction = reagent.core.atom.cljs$core$IFn$_invoke$arity$1(null);
var unsubscribe = module$node_modules$firebase$firestore$dist$index_esm.onSnapshot(ref,(function (d){
return cljs.core.reset_BANG_(reaction,cljs.core.js__GT_clj.cljs$core$IFn$_invoke$arity$1(d.data()));
}));
return reagent.ratom.make_reaction.cljs$core$IFn$_invoke$arity$variadic((function (){
return cljs.core.deref(reaction);
}),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"on-dispose","on-dispose",2105306360),(function (){
return (unsubscribe.cljs$core$IFn$_invoke$arity$0 ? unsubscribe.cljs$core$IFn$_invoke$arity$0() : unsubscribe.call(null));
})], 0));
});
/**
 * returns a reagent atom that will always have the latest snapshot at 'collection-path' in the Firebase firestore
 */
db.fsdb.on_snapshot_docs_reaction = (function db$fsdb$on_snapshot_docs_reaction(p__73716){
var map__73717 = p__73716;
var map__73717__$1 = cljs.core.__destructure_map(map__73717);
var path = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73717__$1,new cljs.core.Keyword(null,"path","path",-188191168));
var path__$1 = cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.str,cljs.core.interpose.cljs$core$IFn$_invoke$arity$2("/",path));
var ref = module$node_modules$firebase$firestore$dist$index_esm.collection(module$node_modules$firebase$firestore$dist$index_esm.getFirestore(),path__$1);
var q = module$node_modules$firebase$firestore$dist$index_esm.query(ref,module$node_modules$firebase$firestore$dist$index_esm.orderBy("timestamp","desc"));
var reaction = reagent.core.atom.cljs$core$IFn$_invoke$arity$1(null);
var unsubscribe = module$node_modules$firebase$firestore$dist$index_esm.onSnapshot(q,(function (querySnapshot){
var vs = cljs.core.map.cljs$core$IFn$_invoke$arity$2((function (e){
return new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"id","id",-1388402092),e.id,new cljs.core.Keyword(null,"data","data",-232669377),cljs.core.js__GT_clj.cljs$core$IFn$_invoke$arity$1(e.data())], null);
}),querySnapshot.docs);
return cljs.core.reset_BANG_(reaction,vs);
}));
return reagent.ratom.make_reaction.cljs$core$IFn$_invoke$arity$variadic((function (){
return cljs.core.deref(reaction);
}),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"on-dispose","on-dispose",2105306360),(function (){
return (unsubscribe.cljs$core$IFn$_invoke$arity$0 ? unsubscribe.cljs$core$IFn$_invoke$arity$0() : unsubscribe.call(null));
})], 0));
});
/**
 * store a document
 */
db.fsdb.firestore_set = (function db$fsdb$firestore_set(p__73722){
var map__73724 = p__73722;
var map__73724__$1 = cljs.core.__destructure_map(map__73724);
var path = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73724__$1,new cljs.core.Keyword(null,"path","path",-188191168));
var value = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73724__$1,new cljs.core.Keyword(null,"value","value",305978217));
var options = cljs.core.get.cljs$core$IFn$_invoke$arity$3(map__73724__$1,new cljs.core.Keyword(null,"options","options",99638489),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"timestamp","timestamp",579478971),true,new cljs.core.Keyword(null,"merge","merge",-1804319409),true], null));
var ref = cljs.core.apply.cljs$core$IFn$_invoke$arity$3(module$node_modules$firebase$firestore$dist$index_esm.doc,module$node_modules$firebase$firestore$dist$index_esm.getFirestore(),path);
var value__$1 = (cljs.core.truth_(new cljs.core.Keyword(null,"timestamp","timestamp",579478971).cljs$core$IFn$_invoke$arity$1(options))?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(value,new cljs.core.Keyword(null,"timestamp","timestamp",579478971),module$node_modules$firebase$firestore$dist$index_esm.serverTimestamp()):value);
return module$node_modules$firebase$firestore$dist$index_esm.setDoc(ref,cljs.core.clj__GT_js(value__$1),cljs.core.clj__GT_js(options));
});
/**
 * store a document with a new id
 */
db.fsdb.firestore_add = (function db$fsdb$firestore_add(p__73727){
var map__73728 = p__73727;
var map__73728__$1 = cljs.core.__destructure_map(map__73728);
var path = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73728__$1,new cljs.core.Keyword(null,"path","path",-188191168));
var value = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73728__$1,new cljs.core.Keyword(null,"value","value",305978217));
var options = cljs.core.get.cljs$core$IFn$_invoke$arity$3(map__73728__$1,new cljs.core.Keyword(null,"options","options",99638489),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"timestamp","timestamp",579478971),true,new cljs.core.Keyword(null,"merge","merge",-1804319409),true], null));
var ref = module$node_modules$firebase$firestore$dist$index_esm.doc(cljs.core.apply.cljs$core$IFn$_invoke$arity$3(module$node_modules$firebase$firestore$dist$index_esm.collection,module$node_modules$firebase$firestore$dist$index_esm.getFirestore(),path));
var value__$1 = (cljs.core.truth_(new cljs.core.Keyword(null,"timestamp","timestamp",579478971).cljs$core$IFn$_invoke$arity$1(options))?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(value,new cljs.core.Keyword(null,"timestamp","timestamp",579478971),module$node_modules$firebase$firestore$dist$index_esm.serverTimestamp()):value);
return module$node_modules$firebase$firestore$dist$index_esm.setDoc(ref,cljs.core.clj__GT_js(value__$1),cljs.core.clj__GT_js(options));
});

//# sourceMappingURL=db.fsdb.js.map
