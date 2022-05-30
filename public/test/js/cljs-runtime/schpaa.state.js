goog.provide('schpaa.state');
/**
 * @define {string}
 */
schpaa.state.ls_key = goog.define("schpaa.state.ls_key","schpaa.state");
/**
 * Puts settings into localStorage
 */
schpaa.state.settings__GT_local_store = (function schpaa$state$settings__GT_local_store(app_db){
return localStorage.setItem(schpaa.state.ls_key,cljs.core.str.cljs$core$IFn$_invoke$arity$1(new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"settings","settings",1556144875),new cljs.core.Keyword(null,"settings","settings",1556144875).cljs$core$IFn$_invoke$arity$1(app_db)], null)));
});
re_frame.core.reg_cofx(new cljs.core.Keyword(null,"local-store-settings","local-store-settings",220958749),(function (cofx,_){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(cofx,new cljs.core.Keyword(null,"local-store-settings","local-store-settings",220958749),cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.sorted_map(),(function (){var G__74651 = localStorage.getItem(schpaa.state.ls_key);
if((G__74651 == null)){
return null;
} else {
return cljs.reader.read_string.cljs$core$IFn$_invoke$arity$1(G__74651);
}
})()));
}));
schpaa.state.__GT_local_store = re_frame.core.after(schpaa.state.settings__GT_local_store);
schpaa.state.localstorage_interceptors = new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [schpaa.state.__GT_local_store], null);
re_frame.core.reg_event_db.cljs$core$IFn$_invoke$arity$3(new cljs.core.Keyword("schpaa.state","state","schpaa.state/state",1759326058),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [schpaa.state.localstorage_interceptors], null),(function (db__$1,p__74654){
var vec__74655 = p__74654;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74655,(0),null);
var tag = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74655,(1),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74655,(2),null);
var path = new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"settings","settings",1556144875),new cljs.core.Keyword(null,"state","state",-1988618099),tag], null);
if((!((v == null)))){
return cljs.core.assoc_in(db__$1,path,v);
} else {
return cljs.core.update_in.cljs$core$IFn$_invoke$arity$3(db__$1,path,cljs.core.fnil.cljs$core$IFn$_invoke$arity$2(cljs.core.not,false));
}
}));
re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword("schpaa.state","state-toggle","schpaa.state/state-toggle",1349542382),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([(function (db__$1,p__74662){
var vec__74663 = p__74662;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74663,(0),null);
var tag = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74663,(1),null);
return cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(db__$1,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"settings","settings",1556144875),new cljs.core.Keyword(null,"state","state",-1988618099),tag], null));
})], 0));
re_frame.core.reg_event_db.cljs$core$IFn$_invoke$arity$3(new cljs.core.Keyword("schpaa.state","change-state","schpaa.state/change-state",2022677553),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [schpaa.state.localstorage_interceptors], null),(function (db__$1,p__74666){
var vec__74667 = p__74666;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74667,(0),null);
var tag = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74667,(1),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74667,(2),null);
var path = new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"settings","settings",1556144875),new cljs.core.Keyword(null,"state","state",-1988618099),tag], null);
return cljs.core.assoc_in(db__$1,path,v);
}));
schpaa.state.listen = (function schpaa$state$listen(tag){
return re_frame.core.subscribe.cljs$core$IFn$_invoke$arity$1(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("schpaa.state","state-toggle","schpaa.state/state-toggle",1349542382),tag], null));
});
re_frame.core.reg_event_db.cljs$core$IFn$_invoke$arity$3(new cljs.core.Keyword("schpaa.state","restore-settings","schpaa.state/restore-settings",739680481),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [schpaa.state.localstorage_interceptors], null),(function (db__$1,p__74674){
var vec__74675 = p__74674;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74675,(0),null);
var tag = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74675,(1),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74675,(2),null);
var path = new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"settings","settings",1556144875)], null);
return cljs.core.assoc_in(db__$1,path,null);
}));
re_frame.core.reg_event_db.cljs$core$IFn$_invoke$arity$3(new cljs.core.Keyword("schpaa.state","mark-help-as-read","schpaa.state/mark-help-as-read",531643403),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [schpaa.state.localstorage_interceptors], null),(function (db__$1,p__74679){
var vec__74680 = p__74679;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74680,(0),null);
var id = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74680,(1),null);
var path = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"settings","settings",1556144875),new cljs.core.Keyword(null,"help","help",-439233446)], null);
return cljs.core.update_in.cljs$core$IFn$_invoke$arity$3(db__$1,path,(function (e){
if((e == null)){
return cljs.core.PersistentHashSet.createAsIfByAssoc([id]);
} else {
return clojure.set.union.cljs$core$IFn$_invoke$arity$2(e,cljs.core.PersistentHashSet.createAsIfByAssoc([id]));
}
}));
}));
re_frame.core.reg_event_db.cljs$core$IFn$_invoke$arity$3(new cljs.core.Keyword("schpaa.state","mark-help-to-restore","schpaa.state/mark-help-to-restore",-1351279109),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [schpaa.state.localstorage_interceptors], null),(function (db__$1,p__74683){
var vec__74684 = p__74683;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74684,(0),null);
var id = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74684,(1),null);
var path = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"settings","settings",1556144875),new cljs.core.Keyword(null,"help","help",-439233446)], null);
return cljs.core.update_in.cljs$core$IFn$_invoke$arity$3(db__$1,path,(function (e){
if((!((e == null)))){
return clojure.set.difference.cljs$core$IFn$_invoke$arity$2(e,cljs.core.PersistentHashSet.createAsIfByAssoc([id]));
} else {
return cljs.core.PersistentHashSet.createAsIfByAssoc([id]);
}
}));
}));
re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword("schpaa.state","state-help","schpaa.state/state-help",1716937593),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([(function (db__$1,p__74687){
var vec__74688 = p__74687;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74688,(0),null);
var id = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74688,(1),null);
return (!((cljs.core.some(cljs.core.PersistentHashSet.createAsIfByAssoc([id]),cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(db__$1,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"settings","settings",1556144875),new cljs.core.Keyword(null,"help","help",-439233446)], null))) == null)));
})], 0));
schpaa.state.help_COLON_listen = (function schpaa$state$help_COLON_listen(id){
return re_frame.core.subscribe.cljs$core$IFn$_invoke$arity$1(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("schpaa.state","state-help","schpaa.state/state-help",1716937593),id], null));
});
schpaa.state.help_COLON_mark_as_read = (function schpaa$state$help_COLON_mark_as_read(id){
return re_frame.core.dispatch(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("schpaa.state","mark-help-as-read","schpaa.state/mark-help-as-read",531643403),id], null));
});
schpaa.state.help_COLON_restore_single = (function schpaa$state$help_COLON_restore_single(id){
return re_frame.core.dispatch(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("schpaa.state","mark-help-to-restore","schpaa.state/mark-help-to-restore",-1351279109),id], null));
});
re_frame.core.reg_event_db.cljs$core$IFn$_invoke$arity$3(new cljs.core.Keyword("schpaa.state","restore-help","schpaa.state/restore-help",1449346961),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [schpaa.state.localstorage_interceptors], null),(function (db__$1,p__74692){
var vec__74693 = p__74692;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74693,(0),null);
var tag = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74693,(1),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__74693,(2),null);
var path = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"settings","settings",1556144875),new cljs.core.Keyword(null,"help","help",-439233446)], null);
return cljs.core.assoc_in(db__$1,path,null);
}));
schpaa.state.help_COLON_restore_all = (function schpaa$state$help_COLON_restore_all(){
return re_frame.core.dispatch(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("schpaa.state","restore-help","schpaa.state/restore-help",1449346961)], null));
});
schpaa.state.toggle = (function schpaa$state$toggle(tag){
return re_frame.core.dispatch(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("schpaa.state","state","schpaa.state/state",1759326058),tag], null));
});
schpaa.state.change = (function schpaa$state$change(tag,v){
return re_frame.core.dispatch(new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("schpaa.state","change-state","schpaa.state/change-state",2022677553),tag,v], null));
});
schpaa.state.clear = (function schpaa$state$clear(tag){
return re_frame.core.dispatch(new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("schpaa.state","change-state","schpaa.state/change-state",2022677553),tag,null], null));
});

//# sourceMappingURL=schpaa.state.js.map
