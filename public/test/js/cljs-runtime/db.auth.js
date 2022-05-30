goog.provide('db.auth');
var module$node_modules$firebase$auth$dist$index_esm=shadow.js.require("module$node_modules$firebase$auth$dist$index_esm", {});
db.auth.user__GT_data = (function db$auth$user__GT_data(user__$1){
if(cljs.core.truth_(user__$1)){
return new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"email","email",1415816706),user__$1.email,new cljs.core.Keyword(null,"photo-url","photo-url",-1816449182),user__$1.photoURL,new cljs.core.Keyword(null,"uid","uid",-1447769400),user__$1.uid,new cljs.core.Keyword(null,"display-name","display-name",694513143),user__$1.displayName], null);
} else {
return null;
}
});
db.auth.user_info = (function db$auth$user_info(){
var auth_state = reagent.core.atom.cljs$core$IFn$_invoke$arity$1(null);
var callback = (function (user__$1){
var user_data = db.auth.user__GT_data(user__$1);
if(cljs.core.not_EQ_.cljs$core$IFn$_invoke$arity$2(user_data,cljs.core.deref(auth_state))){
if((user_data == null)){
db.presence.presence(new cljs.core.Keyword(null,"uid","uid",-1447769400).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(auth_state)));

return cljs.core.reset_BANG_(auth_state,user_data);
} else {
if((cljs.core.deref(auth_state) == null)){
cljs.core.reset_BANG_(auth_state,user_data);

return db.presence.presence(new cljs.core.Keyword(null,"uid","uid",-1447769400).cljs$core$IFn$_invoke$arity$1(user_data));
} else {
return null;
}
}
} else {
return null;
}
});
var error_callback = (function (x){
return cljs.core.reset_BANG_(auth_state,x);
});
module$node_modules$firebase$auth$dist$index_esm.onAuthStateChanged(module$node_modules$firebase$auth$dist$index_esm.getAuth(),callback,error_callback);

return auth_state;
});
db.auth.sign_out = (function db$auth$sign_out(){
return module$node_modules$firebase$auth$dist$index_esm.signOut(module$node_modules$firebase$auth$dist$index_esm.getAuth());
});

//# sourceMappingURL=db.auth.js.map
