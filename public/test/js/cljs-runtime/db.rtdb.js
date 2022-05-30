goog.provide('db.rtdb');
var module$node_modules$firebase$database$dist$index_esm=shadow.js.require("module$node_modules$firebase$database$dist$index_esm", {});
/**
 * returns a reagent atom that will always have the latest value at 'path' in the Firebase database
 */
db.rtdb.on_value_reaction = (function db$rtdb$on_value_reaction(p__73399){
var map__73400 = p__73399;
var map__73400__$1 = cljs.core.__destructure_map(map__73400);
var args = map__73400__$1;
var path = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73400__$1,new cljs.core.Keyword(null,"path","path",-188191168));
var ref = module$node_modules$firebase$database$dist$index_esm.ref(module$node_modules$firebase$database$dist$index_esm.getDatabase(),cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.str,cljs.core.interpose.cljs$core$IFn$_invoke$arity$2("/",path)));
var reaction = reagent.core.atom.cljs$core$IFn$_invoke$arity$1(null);
var callback = (function (snap){
return cljs.core.reset_BANG_(reaction,(function (){var G__73408 = snap;
var G__73408__$1 = (((G__73408 == null))?null:G__73408.val());
if((G__73408__$1 == null)){
return null;
} else {
return cljs_bean.core.__GT_clj(G__73408__$1);
}
})());
});
module$node_modules$firebase$database$dist$index_esm.onValue(ref,callback);

return reagent.ratom.make_reaction.cljs$core$IFn$_invoke$arity$variadic((function (){
return cljs.core.deref(reaction);
}),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"on-dispose","on-dispose",2105306360),(function (){
return module$node_modules$firebase$database$dist$index_esm.off(ref,"value",callback);
})], 0));
});
db.rtdb.ref_get = (function db$rtdb$ref_get(p__73409){
var map__73413 = p__73409;
var map__73413__$1 = cljs.core.__destructure_map(map__73413);
var args = map__73413__$1;
var path = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73413__$1,new cljs.core.Keyword(null,"path","path",-188191168));
var path__$1 = cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.str,cljs.core.interpose.cljs$core$IFn$_invoke$arity$2("/",path));
var value = reagent.core.atom.cljs$core$IFn$_invoke$arity$1(null);
module$node_modules$firebase$database$dist$index_esm.get(module$node_modules$firebase$database$dist$index_esm.ref(module$node_modules$firebase$database$dist$index_esm.getDatabase(),path__$1),cljs_bean.core.__GT_js(value)).then((function (snapshot){
if(cljs.core.truth_(snapshot.exists())){
var result = cljs_bean.core.__GT_clj(snapshot.val());
return cljs.core.reset_BANG_(value,result);
} else {
return cljs.core.reset_BANG_(value,null);
}
}));

return reagent.ratom.make_reaction.cljs$core$IFn$_invoke$arity$variadic((function (){
return cljs.core.deref(value);
}),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"on-dispose","on-dispose",2105306360),(function (){
return cljs.core.tap_GT_("disposed a ref-get");
})], 0));
});
db.rtdb.ref_set = (function db$rtdb$ref_set(p__73418){
var map__73419 = p__73418;
var map__73419__$1 = cljs.core.__destructure_map(map__73419);
var args = map__73419__$1;
var path = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73419__$1,new cljs.core.Keyword(null,"path","path",-188191168));
var value = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73419__$1,new cljs.core.Keyword(null,"value","value",305978217));
var path__$1 = cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.str,cljs.core.interpose.cljs$core$IFn$_invoke$arity$2("/",path));
return module$node_modules$firebase$database$dist$index_esm.set(module$node_modules$firebase$database$dist$index_esm.ref(module$node_modules$firebase$database$dist$index_esm.getDatabase(),path__$1),cljs_bean.core.__GT_js(value));
});
db.rtdb.ref_update = (function db$rtdb$ref_update(p__73420){
var map__73421 = p__73420;
var map__73421__$1 = cljs.core.__destructure_map(map__73421);
var args = map__73421__$1;
var path = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73421__$1,new cljs.core.Keyword(null,"path","path",-188191168));
var value = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73421__$1,new cljs.core.Keyword(null,"value","value",305978217));
var path__$1 = cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.str,cljs.core.interpose.cljs$core$IFn$_invoke$arity$2("/",path));
return module$node_modules$firebase$database$dist$index_esm.update(module$node_modules$firebase$database$dist$index_esm.ref(module$node_modules$firebase$database$dist$index_esm.getDatabase(),path__$1),cljs_bean.core.__GT_js(value));
});
/**
 * useful with stars
 */
db.rtdb.database_update_increment = (function db$rtdb$database_update_increment(p__73426){
var map__73427 = p__73426;
var map__73427__$1 = cljs.core.__destructure_map(map__73427);
var args = map__73427__$1;
var path = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73427__$1,new cljs.core.Keyword(null,"path","path",-188191168));
var field = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73427__$1,new cljs.core.Keyword(null,"field","field",-1302436500));
var delta = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73427__$1,new cljs.core.Keyword(null,"delta","delta",108939957));
return db.rtdb.ref_update(new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"path","path",-188191168),path,new cljs.core.Keyword(null,"value","value",305978217),cljs.core.PersistentArrayMap.createAsIfByAssoc([field,module$node_modules$firebase$database$dist$index_esm.increment(delta)])], null));
});
db.rtdb.ref_push = (function db$rtdb$ref_push(p__73433){
var map__73434 = p__73433;
var map__73434__$1 = cljs.core.__destructure_map(map__73434);
var args = map__73434__$1;
var path = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73434__$1,new cljs.core.Keyword(null,"path","path",-188191168));
var value = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__73434__$1,new cljs.core.Keyword(null,"value","value",305978217));
var path__$1 = cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.str,cljs.core.interpose.cljs$core$IFn$_invoke$arity$2("/",path));
return module$node_modules$firebase$database$dist$index_esm.push(module$node_modules$firebase$database$dist$index_esm.ref(module$node_modules$firebase$database$dist$index_esm.getDatabase(),path__$1),cljs_bean.core.__GT_js(value));
});

//# sourceMappingURL=db.rtdb.js.map
