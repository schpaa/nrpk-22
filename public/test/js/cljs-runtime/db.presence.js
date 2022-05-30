goog.provide('db.presence');
var module$node_modules$firebase$database$dist$index_esm=shadow.js.require("module$node_modules$firebase$database$dist$index_esm", {});
db.presence.presence = (function db$presence$presence(uid){
var myConnectionsRef = module$node_modules$firebase$database$dist$index_esm.ref(module$node_modules$firebase$database$dist$index_esm.getDatabase(),["presence/",cljs.core.str.cljs$core$IFn$_invoke$arity$1(uid),"/connections"].join(''));
var lastOnlineRef = module$node_modules$firebase$database$dist$index_esm.ref(module$node_modules$firebase$database$dist$index_esm.getDatabase(),["presence/",cljs.core.str.cljs$core$IFn$_invoke$arity$1(uid),"/lastOnline"].join(''));
var connectedRef = module$node_modules$firebase$database$dist$index_esm.ref(module$node_modules$firebase$database$dist$index_esm.getDatabase(),".info/connected");
return module$node_modules$firebase$database$dist$index_esm.onValue(connectedRef,(function (snap){
if(cljs.core.truth_(snap.val())){
var con = module$node_modules$firebase$database$dist$index_esm.push(myConnectionsRef);
module$node_modules$firebase$database$dist$index_esm.onDisconnect(con).remove();

module$node_modules$firebase$database$dist$index_esm.set(con,module$node_modules$firebase$database$dist$index_esm.serverTimestamp());

return module$node_modules$firebase$database$dist$index_esm.onDisconnect(lastOnlineRef).set(module$node_modules$firebase$database$dist$index_esm.serverTimestamp());
} else {
return null;
}
}));
});

//# sourceMappingURL=db.presence.js.map
