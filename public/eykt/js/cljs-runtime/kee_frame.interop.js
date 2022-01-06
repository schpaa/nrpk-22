goog.provide('kee_frame.interop');

/**
* @constructor
 * @implements {cljs.core.IRecord}
 * @implements {cljs.core.IKVReduce}
 * @implements {cljs.core.IEquiv}
 * @implements {cljs.core.IHash}
 * @implements {cljs.core.ICollection}
 * @implements {kee_frame.api.Navigator}
 * @implements {cljs.core.ICounted}
 * @implements {cljs.core.ISeqable}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.ICloneable}
 * @implements {cljs.core.IPrintWithWriter}
 * @implements {cljs.core.IIterable}
 * @implements {cljs.core.IWithMeta}
 * @implements {cljs.core.IAssociative}
 * @implements {cljs.core.IMap}
 * @implements {cljs.core.ILookup}
*/
kee_frame.interop.AccountantNavigator = (function (__meta,__extmap,__hash){
this.__meta = __meta;
this.__extmap = __extmap;
this.__hash = __hash;
this.cljs$lang$protocol_mask$partition0$ = 2230716170;
this.cljs$lang$protocol_mask$partition1$ = 139264;
});
(kee_frame.interop.AccountantNavigator.prototype.cljs$core$ILookup$_lookup$arity$2 = (function (this__4502__auto__,k__4503__auto__){
var self__ = this;
var this__4502__auto____$1 = this;
return this__4502__auto____$1.cljs$core$ILookup$_lookup$arity$3(null,k__4503__auto__,null);
}));

(kee_frame.interop.AccountantNavigator.prototype.cljs$core$ILookup$_lookup$arity$3 = (function (this__4504__auto__,k44761,else__4505__auto__){
var self__ = this;
var this__4504__auto____$1 = this;
var G__44772 = k44761;
switch (G__44772) {
default:
return cljs.core.get.cljs$core$IFn$_invoke$arity$3(self__.__extmap,k44761,else__4505__auto__);

}
}));

(kee_frame.interop.AccountantNavigator.prototype.cljs$core$IKVReduce$_kv_reduce$arity$3 = (function (this__4522__auto__,f__4523__auto__,init__4524__auto__){
var self__ = this;
var this__4522__auto____$1 = this;
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3((function (ret__4525__auto__,p__44773){
var vec__44774 = p__44773;
var k__4526__auto__ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__44774,(0),null);
var v__4527__auto__ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__44774,(1),null);
return (f__4523__auto__.cljs$core$IFn$_invoke$arity$3 ? f__4523__auto__.cljs$core$IFn$_invoke$arity$3(ret__4525__auto__,k__4526__auto__,v__4527__auto__) : f__4523__auto__.call(null,ret__4525__auto__,k__4526__auto__,v__4527__auto__));
}),init__4524__auto__,this__4522__auto____$1);
}));

(kee_frame.interop.AccountantNavigator.prototype.cljs$core$IPrintWithWriter$_pr_writer$arity$3 = (function (this__4517__auto__,writer__4518__auto__,opts__4519__auto__){
var self__ = this;
var this__4517__auto____$1 = this;
var pr_pair__4520__auto__ = (function (keyval__4521__auto__){
return cljs.core.pr_sequential_writer(writer__4518__auto__,cljs.core.pr_writer,""," ","",opts__4519__auto__,keyval__4521__auto__);
});
return cljs.core.pr_sequential_writer(writer__4518__auto__,pr_pair__4520__auto__,"#kee-frame.interop.AccountantNavigator{",", ","}",opts__4519__auto__,cljs.core.concat.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentVector.EMPTY,self__.__extmap));
}));

(kee_frame.interop.AccountantNavigator.prototype.cljs$core$IIterable$_iterator$arity$1 = (function (G__44760){
var self__ = this;
var G__44760__$1 = this;
return (new cljs.core.RecordIter((0),G__44760__$1,0,cljs.core.PersistentVector.EMPTY,(cljs.core.truth_(self__.__extmap)?cljs.core._iterator(self__.__extmap):cljs.core.nil_iter())));
}));

(kee_frame.interop.AccountantNavigator.prototype.cljs$core$IMeta$_meta$arity$1 = (function (this__4500__auto__){
var self__ = this;
var this__4500__auto____$1 = this;
return self__.__meta;
}));

(kee_frame.interop.AccountantNavigator.prototype.cljs$core$ICloneable$_clone$arity$1 = (function (this__4497__auto__){
var self__ = this;
var this__4497__auto____$1 = this;
return (new kee_frame.interop.AccountantNavigator(self__.__meta,self__.__extmap,self__.__hash));
}));

(kee_frame.interop.AccountantNavigator.prototype.cljs$core$ICounted$_count$arity$1 = (function (this__4506__auto__){
var self__ = this;
var this__4506__auto____$1 = this;
return (0 + cljs.core.count(self__.__extmap));
}));

(kee_frame.interop.AccountantNavigator.prototype.kee_frame$api$Navigator$ = cljs.core.PROTOCOL_SENTINEL);

(kee_frame.interop.AccountantNavigator.prototype.kee_frame$api$Navigator$dispatch_current_BANG_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return accountant.core.dispatch_current_BANG_();
}));

(kee_frame.interop.AccountantNavigator.prototype.kee_frame$api$Navigator$navigate_BANG_$arity$2 = (function (_,url){
var self__ = this;
var ___$1 = this;
return accountant.core.navigate_BANG_.cljs$core$IFn$_invoke$arity$1(url);
}));

(kee_frame.interop.AccountantNavigator.prototype.cljs$core$IHash$_hash$arity$1 = (function (this__4498__auto__){
var self__ = this;
var this__4498__auto____$1 = this;
var h__4360__auto__ = self__.__hash;
if((!((h__4360__auto__ == null)))){
return h__4360__auto__;
} else {
var h__4360__auto____$1 = (function (coll__4499__auto__){
return (1922069210 ^ cljs.core.hash_unordered_coll(coll__4499__auto__));
})(this__4498__auto____$1);
(self__.__hash = h__4360__auto____$1);

return h__4360__auto____$1;
}
}));

(kee_frame.interop.AccountantNavigator.prototype.cljs$core$IEquiv$_equiv$arity$2 = (function (this44762,other44763){
var self__ = this;
var this44762__$1 = this;
return (((!((other44763 == null)))) && ((((this44762__$1.constructor === other44763.constructor)) && (cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this44762__$1.__extmap,other44763.__extmap)))));
}));

(kee_frame.interop.AccountantNavigator.prototype.cljs$core$IMap$_dissoc$arity$2 = (function (this__4512__auto__,k__4513__auto__){
var self__ = this;
var this__4512__auto____$1 = this;
if(cljs.core.contains_QMARK_(cljs.core.PersistentHashSet.EMPTY,k__4513__auto__)){
return cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(cljs.core._with_meta(cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,this__4512__auto____$1),self__.__meta),k__4513__auto__);
} else {
return (new kee_frame.interop.AccountantNavigator(self__.__meta,cljs.core.not_empty(cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(self__.__extmap,k__4513__auto__)),null));
}
}));

(kee_frame.interop.AccountantNavigator.prototype.cljs$core$IAssociative$_contains_key_QMARK_$arity$2 = (function (this__4509__auto__,k44761){
var self__ = this;
var this__4509__auto____$1 = this;
return cljs.core.contains_QMARK_(self__.__extmap,k44761);
}));

(kee_frame.interop.AccountantNavigator.prototype.cljs$core$IAssociative$_assoc$arity$3 = (function (this__4510__auto__,k__4511__auto__,G__44760){
var self__ = this;
var this__4510__auto____$1 = this;
var pred__44799 = cljs.core.keyword_identical_QMARK_;
var expr__44800 = k__4511__auto__;
return (new kee_frame.interop.AccountantNavigator(self__.__meta,cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(self__.__extmap,k__4511__auto__,G__44760),null));
}));

(kee_frame.interop.AccountantNavigator.prototype.cljs$core$ISeqable$_seq$arity$1 = (function (this__4515__auto__){
var self__ = this;
var this__4515__auto____$1 = this;
return cljs.core.seq(cljs.core.concat.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentVector.EMPTY,self__.__extmap));
}));

(kee_frame.interop.AccountantNavigator.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (this__4501__auto__,G__44760){
var self__ = this;
var this__4501__auto____$1 = this;
return (new kee_frame.interop.AccountantNavigator(G__44760,self__.__extmap,self__.__hash));
}));

(kee_frame.interop.AccountantNavigator.prototype.cljs$core$ICollection$_conj$arity$2 = (function (this__4507__auto__,entry__4508__auto__){
var self__ = this;
var this__4507__auto____$1 = this;
if(cljs.core.vector_QMARK_(entry__4508__auto__)){
return this__4507__auto____$1.cljs$core$IAssociative$_assoc$arity$3(null,cljs.core._nth(entry__4508__auto__,(0)),cljs.core._nth(entry__4508__auto__,(1)));
} else {
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3(cljs.core._conj,this__4507__auto____$1,entry__4508__auto__);
}
}));

(kee_frame.interop.AccountantNavigator.getBasis = (function (){
return cljs.core.PersistentVector.EMPTY;
}));

(kee_frame.interop.AccountantNavigator.cljs$lang$type = true);

(kee_frame.interop.AccountantNavigator.cljs$lang$ctorPrSeq = (function (this__4546__auto__){
return (new cljs.core.List(null,"kee-frame.interop/AccountantNavigator",null,(1),null));
}));

(kee_frame.interop.AccountantNavigator.cljs$lang$ctorPrWriter = (function (this__4546__auto__,writer__4547__auto__){
return cljs.core._write(writer__4547__auto__,"kee-frame.interop/AccountantNavigator");
}));

/**
 * Positional factory function for kee-frame.interop/AccountantNavigator.
 */
kee_frame.interop.__GT_AccountantNavigator = (function kee_frame$interop$__GT_AccountantNavigator(){
return (new kee_frame.interop.AccountantNavigator(null,null,null));
});

/**
 * Factory function for kee-frame.interop/AccountantNavigator, taking a map of keywords to field values.
 */
kee_frame.interop.map__GT_AccountantNavigator = (function kee_frame$interop$map__GT_AccountantNavigator(G__44765){
var extmap__4542__auto__ = (function (){var G__44808 = cljs.core.dissoc.cljs$core$IFn$_invoke$arity$1(G__44765);
if(cljs.core.record_QMARK_(G__44765)){
return cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,G__44808);
} else {
return G__44808;
}
})();
return (new kee_frame.interop.AccountantNavigator(null,cljs.core.not_empty(extmap__4542__auto__),null));
});

kee_frame.interop.make_navigator = (function kee_frame$interop$make_navigator(opts){
accountant.core.configure_navigation_BANG_(opts);

return kee_frame.interop.__GT_AccountantNavigator();
});
kee_frame.interop.render_root = (function kee_frame$interop$render_root(root_component){
if(cljs.core.truth_(root_component)){
var temp__5751__auto__ = document.getElementById("app");
if(cljs.core.truth_(temp__5751__auto__)){
var app_element = temp__5751__auto__;
return reagent.dom.render.cljs$core$IFn$_invoke$arity$2(root_component,app_element);
} else {
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("Could not find element with id 'app' to mount app into",new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"component","component",1555936782),root_component], null));
}
} else {
return null;
}
});
kee_frame.interop.breakpoints_or_defaults = (function kee_frame$interop$breakpoints_or_defaults(breakpoints){
var or__4253__auto__ = breakpoints;
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
return new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"debounce-ms","debounce-ms",-1127263167),(166),new cljs.core.Keyword(null,"breakpoints","breakpoints",1018731739),new cljs.core.PersistentVector(null, 7, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"mobile","mobile",1403078170),(768),new cljs.core.Keyword(null,"tablet","tablet",-318585919),(992),new cljs.core.Keyword(null,"small-monitor","small-monitor",-1851699481),(1200),new cljs.core.Keyword(null,"large-monitor","large-monitor",-1142074365)], null)], null);
}
});
kee_frame.interop.set_breakpoint_subs = (function kee_frame$interop$set_breakpoint_subs(breakpoints){
return breaking_point.core.register_subs(new cljs.core.Keyword(null,"breakpoints","breakpoints",1018731739).cljs$core$IFn$_invoke$arity$1(kee_frame.interop.breakpoints_or_defaults(breakpoints)));
});
kee_frame.interop.set_breakpoints = (function kee_frame$interop$set_breakpoints(breakpoints){
return re_frame.core.dispatch_sync(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("breaking-point.core","set-breakpoints","breaking-point.core/set-breakpoints",607629013),kee_frame.interop.breakpoints_or_defaults(breakpoints)], null));
});
kee_frame.interop.set_timeout = (function kee_frame$interop$set_timeout(f,ms){
return setTimeout(f,ms);
});
kee_frame.interop.clear_timeout = (function kee_frame$interop$clear_timeout(t){
return clearTimeout(t);
});

//# sourceMappingURL=kee_frame.interop.js.map
