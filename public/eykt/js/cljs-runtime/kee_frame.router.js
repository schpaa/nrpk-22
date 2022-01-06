goog.provide('kee_frame.router');
kee_frame.router.default_chain_links = new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"effect-present?","effect-present?",131752804),(function (effects){
return new cljs.core.Keyword(null,"http-xhrio","http-xhrio",1846166714).cljs$core$IFn$_invoke$arity$1(effects);
}),new cljs.core.Keyword(null,"get-dispatch","get-dispatch",-807865793),(function (effects){
return cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(effects,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"http-xhrio","http-xhrio",1846166714),new cljs.core.Keyword(null,"on-success","on-success",1786904109)], null));
}),new cljs.core.Keyword(null,"set-dispatch","set-dispatch",2115263401),(function (effects,dispatch){
return cljs.core.assoc_in(effects,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"http-xhrio","http-xhrio",1846166714),new cljs.core.Keyword(null,"on-success","on-success",1786904109)], null),dispatch);
})], null)], null);
kee_frame.router.url = (function kee_frame$router$url(data){
if(cljs.core.truth_(cljs.core.deref(kee_frame.state.router))){
} else {
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("No router defined for this app",new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"router","router",1091916230),cljs.core.deref(kee_frame.state.router)], null));
}

return kee_frame.api.data__GT_url(cljs.core.deref(kee_frame.state.router),data);
});
kee_frame.router.goto$ = (function kee_frame$router$goto(data){
return kee_frame.api.navigate_BANG_(cljs.core.deref(kee_frame.state.navigator),kee_frame.router.url(data));
});
kee_frame.router.nav_handler = (function kee_frame$router$nav_handler(router,route_change_event){
return (function (path){
var temp__5751__auto__ = kee_frame.api.url__GT_data(router,path);
if(cljs.core.truth_(temp__5751__auto__)){
var route = temp__5751__auto__;
return re_frame.core.dispatch(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(function (){var or__4253__auto__ = route_change_event;
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
return new cljs.core.Keyword("kee-frame.router","route-changed","kee-frame.router/route-changed",8744849);
}
})(),route], null));
} else {
re_frame.core.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"group","group",582596132),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["No route match found"], 0));

re_frame.core.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"error","error",-978969032),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["No match found for path ",path], 0));

return re_frame.core.console(new cljs.core.Keyword(null,"groupEnd","groupEnd",-337721382));
}
});
});
cljs.spec.alpha.def_impl(new cljs.core.Keyword("kee-frame.router","reitit-route-data","kee-frame.router/reitit-route-data",1934241365),cljs.core.list(new cljs.core.Symbol("cljs.spec.alpha","cat","cljs.spec.alpha/cat",-1471398329,null),new cljs.core.Keyword(null,"route-name","route-name",-932603717),new cljs.core.Symbol("cljs.core","keyword?","cljs.core/keyword?",713156450,null),new cljs.core.Keyword(null,"path-params","path-params",-48130597),cljs.core.list(new cljs.core.Symbol("cljs.spec.alpha","*","cljs.spec.alpha/*",-1238084288,null),cljs.core.list(new cljs.core.Symbol("cljs.spec.alpha","map-of","cljs.spec.alpha/map-of",153715093,null),new cljs.core.Symbol("cljs.core","keyword?","cljs.core/keyword?",713156450,null),new cljs.core.Symbol("cljs.core","any?","cljs.core/any?",-2068111842,null)))),cljs.spec.alpha.cat_impl(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"route-name","route-name",-932603717),new cljs.core.Keyword(null,"path-params","path-params",-48130597)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [cljs.core.keyword_QMARK_,cljs.spec.alpha.rep_impl(cljs.core.list(new cljs.core.Symbol("cljs.spec.alpha","map-of","cljs.spec.alpha/map-of",153715093,null),new cljs.core.Symbol("cljs.core","keyword?","cljs.core/keyword?",713156450,null),new cljs.core.Symbol("cljs.core","any?","cljs.core/any?",-2068111842,null)),cljs.spec.alpha.every_impl.cljs$core$IFn$_invoke$arity$4(cljs.core.list(new cljs.core.Symbol("cljs.spec.alpha","tuple","cljs.spec.alpha/tuple",-415901908,null),new cljs.core.Symbol(null,"keyword?","keyword?",1917797069,null),new cljs.core.Symbol(null,"any?","any?",-318999933,null)),cljs.spec.alpha.tuple_impl.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol("cljs.core","keyword?","cljs.core/keyword?",713156450,null),new cljs.core.Symbol("cljs.core","any?","cljs.core/any?",-2068111842,null)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [cljs.core.keyword_QMARK_,cljs.core.any_QMARK_], null)),new cljs.core.PersistentArrayMap(null, 7, [new cljs.core.Keyword(null,"into","into",-150836029),cljs.core.PersistentArrayMap.EMPTY,new cljs.core.Keyword("cljs.spec.alpha","kind-form","cljs.spec.alpha/kind-form",-1047104697),new cljs.core.Symbol("cljs.core","map?","cljs.core/map?",-1390345523,null),new cljs.core.Keyword("cljs.spec.alpha","cpred","cljs.spec.alpha/cpred",-693471218),(function (G__49829){
return cljs.core.map_QMARK_(G__49829);
}),new cljs.core.Keyword(null,"kind","kind",-717265803),cljs.core.map_QMARK_,new cljs.core.Keyword("cljs.spec.alpha","kfn","cljs.spec.alpha/kfn",672643897),(function (i__13468__auto__,v__13469__auto__){
return cljs.core.nth.cljs$core$IFn$_invoke$arity$2(v__13469__auto__,(0));
}),new cljs.core.Keyword("cljs.spec.alpha","conform-all","cljs.spec.alpha/conform-all",45201917),true,new cljs.core.Keyword("cljs.spec.alpha","describe","cljs.spec.alpha/describe",1883026911),cljs.core.list(new cljs.core.Symbol("cljs.spec.alpha","map-of","cljs.spec.alpha/map-of",153715093,null),new cljs.core.Symbol("cljs.core","keyword?","cljs.core/keyword?",713156450,null),new cljs.core.Symbol("cljs.core","any?","cljs.core/any?",-2068111842,null))], null),null))], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol("cljs.core","keyword?","cljs.core/keyword?",713156450,null),cljs.core.list(new cljs.core.Symbol("cljs.spec.alpha","*","cljs.spec.alpha/*",-1238084288,null),cljs.core.list(new cljs.core.Symbol("cljs.spec.alpha","map-of","cljs.spec.alpha/map-of",153715093,null),new cljs.core.Symbol("cljs.core","keyword?","cljs.core/keyword?",713156450,null),new cljs.core.Symbol("cljs.core","any?","cljs.core/any?",-2068111842,null)))], null)));
kee_frame.router.assert_route_data = (function kee_frame$router$assert_route_data(data){
if(cljs.spec.alpha.valid_QMARK_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword("kee-frame.router","reitit-route-data","kee-frame.router/reitit-route-data",1934241365),data)){
return null;
} else {
expound.alpha.expound.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword("kee-frame.router","reitit-route-data","kee-frame.router/reitit-route-data",1934241365),data);

throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("Bad route data input",cljs.spec.alpha.explain_data(new cljs.core.Keyword("kee-frame.router","reitit-route-data","kee-frame.router/reitit-route-data",1934241365),data));
}
});
kee_frame.router.url_not_found = (function kee_frame$router$url_not_found(routes,data){
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("Could not find url for the provided data",new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"routes","routes",457900162),routes,new cljs.core.Keyword(null,"data","data",-232669377),data], null));
});
kee_frame.router.route_match_not_found = (function kee_frame$router$route_match_not_found(routes,url){
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("No match for URL in routes",new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"url","url",276297046),url,new cljs.core.Keyword(null,"routes","routes",457900162),routes], null));
});
kee_frame.router.valid_QMARK_ = (function kee_frame$router$valid_QMARK_(p__49831){
var map__49832 = p__49831;
var map__49832__$1 = cljs.core.__destructure_map(map__49832);
var path_params = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49832__$1,new cljs.core.Keyword(null,"path-params","path-params",-48130597));
var required = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49832__$1,new cljs.core.Keyword(null,"required","required",1807647006));
return clojure.set.subset_QMARK_(required,cljs.core.set(cljs.core.keys(path_params)));
});
kee_frame.router.match_data = (function kee_frame$router$match_data(routes,route,hash_QMARK_){
var vec__49833 = route;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49833,(0),null);
var path_params = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49833,(1),null);
var map__49836 = cljs.core.apply.cljs$core$IFn$_invoke$arity$3(reitit.core.match_by_name,routes,route);
var map__49836__$1 = cljs.core.__destructure_map(map__49836);
var match = map__49836__$1;
var path = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49836__$1,new cljs.core.Keyword(null,"path","path",-188191168));
if(kee_frame.router.valid_QMARK_(match)){
return [(cljs.core.truth_(hash_QMARK_)?"/#":null),cljs.core.str.cljs$core$IFn$_invoke$arity$1(path),(function (){var temp__5757__auto__ = new cljs.core.Keyword(null,"query-string","query-string",-1018845061).cljs$core$IFn$_invoke$arity$1(path_params);
if((temp__5757__auto__ == null)){
return null;
} else {
var q = temp__5757__auto__;
return ["?",cljs.core.str.cljs$core$IFn$_invoke$arity$1(q)].join('');
}
})(),(function (){var temp__5757__auto__ = new cljs.core.Keyword(null,"hash","hash",-13781596).cljs$core$IFn$_invoke$arity$1(path_params);
if((temp__5757__auto__ == null)){
return null;
} else {
var h = temp__5757__auto__;
return ["#",cljs.core.str.cljs$core$IFn$_invoke$arity$1(h)].join('');
}
})()].join('');
} else {
return null;
}
});
kee_frame.router.match_url = (function kee_frame$router$match_url(routes,url){
var vec__49838 = clojure.string.split.cljs$core$IFn$_invoke$arity$3(clojure.string.replace(url,/^\/#\//,"/"),/#/,(2));
var path_PLUS_query = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49838,(0),null);
var fragment = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49838,(1),null);
var vec__49841 = clojure.string.split.cljs$core$IFn$_invoke$arity$3(path_PLUS_query,/\?/,(2));
var path = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49841,(0),null);
var query = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49841,(1),null);
var G__49844 = reitit.core.match_by_path(routes,path);
if((G__49844 == null)){
return null;
} else {
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$variadic(G__49844,new cljs.core.Keyword(null,"query-string","query-string",-1018845061),query,cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"hash","hash",-13781596),fragment], 0));
}
});

/**
* @constructor
 * @implements {cljs.core.IRecord}
 * @implements {cljs.core.IKVReduce}
 * @implements {cljs.core.IEquiv}
 * @implements {cljs.core.IHash}
 * @implements {cljs.core.ICollection}
 * @implements {cljs.core.ICounted}
 * @implements {cljs.core.ISeqable}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.ICloneable}
 * @implements {cljs.core.IPrintWithWriter}
 * @implements {cljs.core.IIterable}
 * @implements {kee_frame.api.Router}
 * @implements {cljs.core.IWithMeta}
 * @implements {cljs.core.IAssociative}
 * @implements {cljs.core.IMap}
 * @implements {cljs.core.ILookup}
*/
kee_frame.router.ReititRouter = (function (routes,hash_QMARK_,not_found,__meta,__extmap,__hash){
this.routes = routes;
this.hash_QMARK_ = hash_QMARK_;
this.not_found = not_found;
this.__meta = __meta;
this.__extmap = __extmap;
this.__hash = __hash;
this.cljs$lang$protocol_mask$partition0$ = 2230716170;
this.cljs$lang$protocol_mask$partition1$ = 139264;
});
(kee_frame.router.ReititRouter.prototype.cljs$core$ILookup$_lookup$arity$2 = (function (this__4502__auto__,k__4503__auto__){
var self__ = this;
var this__4502__auto____$1 = this;
return this__4502__auto____$1.cljs$core$ILookup$_lookup$arity$3(null,k__4503__auto__,null);
}));

(kee_frame.router.ReititRouter.prototype.cljs$core$ILookup$_lookup$arity$3 = (function (this__4504__auto__,k49847,else__4505__auto__){
var self__ = this;
var this__4504__auto____$1 = this;
var G__49851 = k49847;
var G__49851__$1 = (((G__49851 instanceof cljs.core.Keyword))?G__49851.fqn:null);
switch (G__49851__$1) {
case "routes":
return self__.routes;

break;
case "hash?":
return self__.hash_QMARK_;

break;
case "not-found":
return self__.not_found;

break;
default:
return cljs.core.get.cljs$core$IFn$_invoke$arity$3(self__.__extmap,k49847,else__4505__auto__);

}
}));

(kee_frame.router.ReititRouter.prototype.cljs$core$IKVReduce$_kv_reduce$arity$3 = (function (this__4522__auto__,f__4523__auto__,init__4524__auto__){
var self__ = this;
var this__4522__auto____$1 = this;
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3((function (ret__4525__auto__,p__49853){
var vec__49854 = p__49853;
var k__4526__auto__ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49854,(0),null);
var v__4527__auto__ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49854,(1),null);
return (f__4523__auto__.cljs$core$IFn$_invoke$arity$3 ? f__4523__auto__.cljs$core$IFn$_invoke$arity$3(ret__4525__auto__,k__4526__auto__,v__4527__auto__) : f__4523__auto__.call(null,ret__4525__auto__,k__4526__auto__,v__4527__auto__));
}),init__4524__auto__,this__4522__auto____$1);
}));

(kee_frame.router.ReititRouter.prototype.cljs$core$IPrintWithWriter$_pr_writer$arity$3 = (function (this__4517__auto__,writer__4518__auto__,opts__4519__auto__){
var self__ = this;
var this__4517__auto____$1 = this;
var pr_pair__4520__auto__ = (function (keyval__4521__auto__){
return cljs.core.pr_sequential_writer(writer__4518__auto__,cljs.core.pr_writer,""," ","",opts__4519__auto__,keyval__4521__auto__);
});
return cljs.core.pr_sequential_writer(writer__4518__auto__,pr_pair__4520__auto__,"#kee-frame.router.ReititRouter{",", ","}",opts__4519__auto__,cljs.core.concat.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"routes","routes",457900162),self__.routes],null)),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"hash?","hash?",-1899275922),self__.hash_QMARK_],null)),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"not-found","not-found",-629079980),self__.not_found],null))], null),self__.__extmap));
}));

(kee_frame.router.ReititRouter.prototype.cljs$core$IIterable$_iterator$arity$1 = (function (G__49846){
var self__ = this;
var G__49846__$1 = this;
return (new cljs.core.RecordIter((0),G__49846__$1,3,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"routes","routes",457900162),new cljs.core.Keyword(null,"hash?","hash?",-1899275922),new cljs.core.Keyword(null,"not-found","not-found",-629079980)], null),(cljs.core.truth_(self__.__extmap)?cljs.core._iterator(self__.__extmap):cljs.core.nil_iter())));
}));

(kee_frame.router.ReititRouter.prototype.cljs$core$IMeta$_meta$arity$1 = (function (this__4500__auto__){
var self__ = this;
var this__4500__auto____$1 = this;
return self__.__meta;
}));

(kee_frame.router.ReititRouter.prototype.cljs$core$ICloneable$_clone$arity$1 = (function (this__4497__auto__){
var self__ = this;
var this__4497__auto____$1 = this;
return (new kee_frame.router.ReititRouter(self__.routes,self__.hash_QMARK_,self__.not_found,self__.__meta,self__.__extmap,self__.__hash));
}));

(kee_frame.router.ReititRouter.prototype.cljs$core$ICounted$_count$arity$1 = (function (this__4506__auto__){
var self__ = this;
var this__4506__auto____$1 = this;
return (3 + cljs.core.count(self__.__extmap));
}));

(kee_frame.router.ReititRouter.prototype.cljs$core$IHash$_hash$arity$1 = (function (this__4498__auto__){
var self__ = this;
var this__4498__auto____$1 = this;
var h__4360__auto__ = self__.__hash;
if((!((h__4360__auto__ == null)))){
return h__4360__auto__;
} else {
var h__4360__auto____$1 = (function (coll__4499__auto__){
return (-1127529290 ^ cljs.core.hash_unordered_coll(coll__4499__auto__));
})(this__4498__auto____$1);
(self__.__hash = h__4360__auto____$1);

return h__4360__auto____$1;
}
}));

(kee_frame.router.ReititRouter.prototype.cljs$core$IEquiv$_equiv$arity$2 = (function (this49848,other49849){
var self__ = this;
var this49848__$1 = this;
return (((!((other49849 == null)))) && ((((this49848__$1.constructor === other49849.constructor)) && (((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this49848__$1.routes,other49849.routes)) && (((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this49848__$1.hash_QMARK_,other49849.hash_QMARK_)) && (((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this49848__$1.not_found,other49849.not_found)) && (cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this49848__$1.__extmap,other49849.__extmap)))))))))));
}));

(kee_frame.router.ReititRouter.prototype.cljs$core$IMap$_dissoc$arity$2 = (function (this__4512__auto__,k__4513__auto__){
var self__ = this;
var this__4512__auto____$1 = this;
if(cljs.core.contains_QMARK_(new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"routes","routes",457900162),null,new cljs.core.Keyword(null,"hash?","hash?",-1899275922),null,new cljs.core.Keyword(null,"not-found","not-found",-629079980),null], null), null),k__4513__auto__)){
return cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(cljs.core._with_meta(cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,this__4512__auto____$1),self__.__meta),k__4513__auto__);
} else {
return (new kee_frame.router.ReititRouter(self__.routes,self__.hash_QMARK_,self__.not_found,self__.__meta,cljs.core.not_empty(cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(self__.__extmap,k__4513__auto__)),null));
}
}));

(kee_frame.router.ReititRouter.prototype.kee_frame$api$Router$ = cljs.core.PROTOCOL_SENTINEL);

(kee_frame.router.ReititRouter.prototype.kee_frame$api$Router$data__GT_url$arity$2 = (function (_,data){
var self__ = this;
var ___$1 = this;
kee_frame.router.assert_route_data(data);

var or__4253__auto__ = kee_frame.router.match_data(self__.routes,data,self__.hash_QMARK_);
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
return kee_frame.router.url_not_found(self__.routes,data);
}
}));

(kee_frame.router.ReititRouter.prototype.kee_frame$api$Router$url__GT_data$arity$2 = (function (_,url){
var self__ = this;
var ___$1 = this;
var or__4253__auto__ = kee_frame.router.match_url(self__.routes,url);
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
var or__4253__auto____$1 = (function (){var G__49862 = self__.not_found;
if((G__49862 == null)){
return null;
} else {
return kee_frame.router.match_url(self__.routes,G__49862);
}
})();
if(cljs.core.truth_(or__4253__auto____$1)){
return or__4253__auto____$1;
} else {
return kee_frame.router.route_match_not_found(self__.routes,url);
}
}
}));

(kee_frame.router.ReititRouter.prototype.cljs$core$IAssociative$_contains_key_QMARK_$arity$2 = (function (this__4509__auto__,k49847){
var self__ = this;
var this__4509__auto____$1 = this;
var G__49863 = k49847;
var G__49863__$1 = (((G__49863 instanceof cljs.core.Keyword))?G__49863.fqn:null);
switch (G__49863__$1) {
case "routes":
case "hash?":
case "not-found":
return true;

break;
default:
return cljs.core.contains_QMARK_(self__.__extmap,k49847);

}
}));

(kee_frame.router.ReititRouter.prototype.cljs$core$IAssociative$_assoc$arity$3 = (function (this__4510__auto__,k__4511__auto__,G__49846){
var self__ = this;
var this__4510__auto____$1 = this;
var pred__49864 = cljs.core.keyword_identical_QMARK_;
var expr__49865 = k__4511__auto__;
if(cljs.core.truth_((pred__49864.cljs$core$IFn$_invoke$arity$2 ? pred__49864.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"routes","routes",457900162),expr__49865) : pred__49864.call(null,new cljs.core.Keyword(null,"routes","routes",457900162),expr__49865)))){
return (new kee_frame.router.ReititRouter(G__49846,self__.hash_QMARK_,self__.not_found,self__.__meta,self__.__extmap,null));
} else {
if(cljs.core.truth_((pred__49864.cljs$core$IFn$_invoke$arity$2 ? pred__49864.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"hash?","hash?",-1899275922),expr__49865) : pred__49864.call(null,new cljs.core.Keyword(null,"hash?","hash?",-1899275922),expr__49865)))){
return (new kee_frame.router.ReititRouter(self__.routes,G__49846,self__.not_found,self__.__meta,self__.__extmap,null));
} else {
if(cljs.core.truth_((pred__49864.cljs$core$IFn$_invoke$arity$2 ? pred__49864.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"not-found","not-found",-629079980),expr__49865) : pred__49864.call(null,new cljs.core.Keyword(null,"not-found","not-found",-629079980),expr__49865)))){
return (new kee_frame.router.ReititRouter(self__.routes,self__.hash_QMARK_,G__49846,self__.__meta,self__.__extmap,null));
} else {
return (new kee_frame.router.ReititRouter(self__.routes,self__.hash_QMARK_,self__.not_found,self__.__meta,cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(self__.__extmap,k__4511__auto__,G__49846),null));
}
}
}
}));

(kee_frame.router.ReititRouter.prototype.cljs$core$ISeqable$_seq$arity$1 = (function (this__4515__auto__){
var self__ = this;
var this__4515__auto____$1 = this;
return cljs.core.seq(cljs.core.concat.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(new cljs.core.MapEntry(new cljs.core.Keyword(null,"routes","routes",457900162),self__.routes,null)),(new cljs.core.MapEntry(new cljs.core.Keyword(null,"hash?","hash?",-1899275922),self__.hash_QMARK_,null)),(new cljs.core.MapEntry(new cljs.core.Keyword(null,"not-found","not-found",-629079980),self__.not_found,null))], null),self__.__extmap));
}));

(kee_frame.router.ReititRouter.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (this__4501__auto__,G__49846){
var self__ = this;
var this__4501__auto____$1 = this;
return (new kee_frame.router.ReititRouter(self__.routes,self__.hash_QMARK_,self__.not_found,G__49846,self__.__extmap,self__.__hash));
}));

(kee_frame.router.ReititRouter.prototype.cljs$core$ICollection$_conj$arity$2 = (function (this__4507__auto__,entry__4508__auto__){
var self__ = this;
var this__4507__auto____$1 = this;
if(cljs.core.vector_QMARK_(entry__4508__auto__)){
return this__4507__auto____$1.cljs$core$IAssociative$_assoc$arity$3(null,cljs.core._nth(entry__4508__auto__,(0)),cljs.core._nth(entry__4508__auto__,(1)));
} else {
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3(cljs.core._conj,this__4507__auto____$1,entry__4508__auto__);
}
}));

(kee_frame.router.ReititRouter.getBasis = (function (){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"routes","routes",2098431689,null),new cljs.core.Symbol(null,"hash?","hash?",-258744395,null),new cljs.core.Symbol(null,"not-found","not-found",1011451547,null)], null);
}));

(kee_frame.router.ReititRouter.cljs$lang$type = true);

(kee_frame.router.ReititRouter.cljs$lang$ctorPrSeq = (function (this__4546__auto__){
return (new cljs.core.List(null,"kee-frame.router/ReititRouter",null,(1),null));
}));

(kee_frame.router.ReititRouter.cljs$lang$ctorPrWriter = (function (this__4546__auto__,writer__4547__auto__){
return cljs.core._write(writer__4547__auto__,"kee-frame.router/ReititRouter");
}));

/**
 * Positional factory function for kee-frame.router/ReititRouter.
 */
kee_frame.router.__GT_ReititRouter = (function kee_frame$router$__GT_ReititRouter(routes,hash_QMARK_,not_found){
return (new kee_frame.router.ReititRouter(routes,hash_QMARK_,not_found,null,null,null));
});

/**
 * Factory function for kee-frame.router/ReititRouter, taking a map of keywords to field values.
 */
kee_frame.router.map__GT_ReititRouter = (function kee_frame$router$map__GT_ReititRouter(G__49850){
var extmap__4542__auto__ = (function (){var G__49868 = cljs.core.dissoc.cljs$core$IFn$_invoke$arity$variadic(G__49850,new cljs.core.Keyword(null,"routes","routes",457900162),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"hash?","hash?",-1899275922),new cljs.core.Keyword(null,"not-found","not-found",-629079980)], 0));
if(cljs.core.record_QMARK_(G__49850)){
return cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,G__49868);
} else {
return G__49868;
}
})();
return (new kee_frame.router.ReititRouter(new cljs.core.Keyword(null,"routes","routes",457900162).cljs$core$IFn$_invoke$arity$1(G__49850),new cljs.core.Keyword(null,"hash?","hash?",-1899275922).cljs$core$IFn$_invoke$arity$1(G__49850),new cljs.core.Keyword(null,"not-found","not-found",-629079980).cljs$core$IFn$_invoke$arity$1(G__49850),null,cljs.core.not_empty(extmap__4542__auto__),null));
});

kee_frame.router.bootstrap_routes = (function kee_frame$router$bootstrap_routes(p__49870){
var map__49871 = p__49870;
var map__49871__$1 = cljs.core.__destructure_map(map__49871);
var routes = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49871__$1,new cljs.core.Keyword(null,"routes","routes",457900162));
var router = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49871__$1,new cljs.core.Keyword(null,"router","router",1091916230));
var hash_routing_QMARK_ = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49871__$1,new cljs.core.Keyword(null,"hash-routing?","hash-routing?",471914732));
var scroll = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49871__$1,new cljs.core.Keyword(null,"scroll","scroll",971553779));
var route_change_event = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49871__$1,new cljs.core.Keyword(null,"route-change-event","route-change-event",1834214120));
var not_found = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49871__$1,new cljs.core.Keyword(null,"not-found","not-found",-629079980));
var initialized_QMARK_ = cljs.core.boolean$(cljs.core.deref(kee_frame.state.navigator));
var router__$1 = (function (){var or__4253__auto__ = router;
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
return kee_frame.router.__GT_ReititRouter(reitit.core.router.cljs$core$IFn$_invoke$arity$1(routes),hash_routing_QMARK_,not_found);
}
})();
cljs.core.reset_BANG_(kee_frame.state.router,router__$1);

re_frame.core.reg_fx(new cljs.core.Keyword(null,"navigate-to","navigate-to",-1161651312),kee_frame.router.goto$);

if(initialized_QMARK_){
} else {
if(cljs.core.truth_(scroll)){
kee_frame.scroll.start_BANG_();
} else {
}

cljs.core.reset_BANG_(kee_frame.state.navigator,kee_frame.interop.make_navigator(new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"nav-handler","nav-handler",2039495484),kee_frame.router.nav_handler(router__$1,route_change_event),new cljs.core.Keyword(null,"path-exists?","path-exists?",1473384514),(function (p1__49869_SHARP_){
return cljs.core.boolean$(kee_frame.api.url__GT_data(router__$1,p1__49869_SHARP_));
})], null)));
}

return kee_frame.api.dispatch_current_BANG_(cljs.core.deref(kee_frame.state.navigator));
});
re_frame.core.reg_event_db.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"init","init",-1875481434),(function (db,p__49873){
var vec__49874 = p__49873;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49874,(0),null);
var initial = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49874,(1),null);
return cljs.core.merge.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([initial,db], 0));
}));
kee_frame.router.reg_route_event = (function kee_frame$router$reg_route_event(scroll){
return re_frame.core.reg_event_fx.cljs$core$IFn$_invoke$arity$3(new cljs.core.Keyword("kee-frame.router","route-changed","kee-frame.router/route-changed",8744849),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [kee_frame.event_logger.interceptor], null),(function (p__49877,p__49878){
var map__49879 = p__49877;
var map__49879__$1 = cljs.core.__destructure_map(map__49879);
var ctx = map__49879__$1;
var db = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49879__$1,new cljs.core.Keyword(null,"db","db",993250759));
var vec__49880 = p__49878;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49880,(0),null);
var route = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49880,(1),null);
if(cljs.core.truth_(scroll)){
kee_frame.scroll.monitor_requests_BANG_(route);
} else {
}

var map__49883 = kee_frame.controller.controller_effects(cljs.core.deref(kee_frame.state.controllers),ctx,route);
var map__49883__$1 = cljs.core.__destructure_map(map__49883);
var update_controllers = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49883__$1,new cljs.core.Keyword(null,"update-controllers","update-controllers",2036761559));
var dispatch_n = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49883__$1,new cljs.core.Keyword(null,"dispatch-n","dispatch-n",-504469236));
var G__49884 = new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"db","db",993250759),cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(db,new cljs.core.Keyword("kee-frame","route","kee-frame/route",-106555640),route),new cljs.core.Keyword(null,"dispatch-later","dispatch-later",291951390),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [(cljs.core.truth_(scroll)?new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"ms","ms",-1152709733),(50),new cljs.core.Keyword(null,"dispatch","dispatch",1319337009),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("kee-frame.scroll","poll","kee-frame.scroll/poll",-1227808225),route,(0)], null)], null):null)], null)], null);
var G__49884__$1 = (cljs.core.truth_(dispatch_n)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49884,new cljs.core.Keyword(null,"dispatch-n","dispatch-n",-504469236),dispatch_n):G__49884);
if(cljs.core.truth_(update_controllers)){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49884__$1,new cljs.core.Keyword(null,"update-controllers","update-controllers",2036761559),update_controllers);
} else {
return G__49884__$1;
}
}));
});
kee_frame.router.deprecations = (function kee_frame$router$deprecations(p__49885){
var map__49886 = p__49885;
var map__49886__$1 = cljs.core.__destructure_map(map__49886);
var debug_QMARK_ = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49886__$1,new cljs.core.Keyword(null,"debug?","debug?",-1831756173));
var debug_config = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49886__$1,new cljs.core.Keyword(null,"debug-config","debug-config",329260673));
if((!((debug_QMARK_ == null)))){
re_frame.core.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["Kee-frame option :debug? has been removed. Configure timbre logger through :log option instead. Example: {:level :debug :ns-blacklist [\"kee-frame.event-logger\"]}"], 0));
} else {
}

if((!((debug_config == null)))){
return re_frame.core.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["Kee-frame option :debug-config has been removed. Configure timbre logger through :log option instead. Example: {:level :debug :ns-blacklist [\"kee-frame.event-logger\"]}"], 0));
} else {
return null;
}
});
kee_frame.router.start_BANG_ = (function kee_frame$router$start_BANG_(p__49888){
var map__49889 = p__49888;
var map__49889__$1 = cljs.core.__destructure_map(map__49889);
var config = map__49889__$1;
var initial_db = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49889__$1,new cljs.core.Keyword(null,"initial-db","initial-db",1939835102));
var routes = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49889__$1,new cljs.core.Keyword(null,"routes","routes",457900162));
var screen = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49889__$1,new cljs.core.Keyword(null,"screen","screen",1990059748));
var router = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49889__$1,new cljs.core.Keyword(null,"router","router",1091916230));
var app_db_spec = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49889__$1,new cljs.core.Keyword(null,"app-db-spec","app-db-spec",-1030582586));
var chain_links = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49889__$1,new cljs.core.Keyword(null,"chain-links","chain-links",951542256));
var global_interceptors = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49889__$1,new cljs.core.Keyword(null,"global-interceptors","global-interceptors",-1995759472));
var scroll = cljs.core.get.cljs$core$IFn$_invoke$arity$3(map__49889__$1,new cljs.core.Keyword(null,"scroll","scroll",971553779),true);
var root_component = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49889__$1,new cljs.core.Keyword(null,"root-component","root-component",-1807026475));
var log_spec_error = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49889__$1,new cljs.core.Keyword(null,"log-spec-error","log-spec-error",718867605));
kee_frame.router.deprecations(config);

if(cljs.core.truth_(app_db_spec)){
re_frame.core.reg_global_interceptor(kee_frame.spec.spec_interceptor(app_db_spec,log_spec_error));
} else {
}

var seq__49890_49947 = cljs.core.seq(global_interceptors);
var chunk__49891_49948 = null;
var count__49892_49949 = (0);
var i__49893_49950 = (0);
while(true){
if((i__49893_49950 < count__49892_49949)){
var i_49951 = chunk__49891_49948.cljs$core$IIndexed$_nth$arity$2(null,i__49893_49950);
re_frame.core.reg_global_interceptor(i_49951);


var G__49952 = seq__49890_49947;
var G__49953 = chunk__49891_49948;
var G__49954 = count__49892_49949;
var G__49955 = (i__49893_49950 + (1));
seq__49890_49947 = G__49952;
chunk__49891_49948 = G__49953;
count__49892_49949 = G__49954;
i__49893_49950 = G__49955;
continue;
} else {
var temp__5753__auto___49956 = cljs.core.seq(seq__49890_49947);
if(temp__5753__auto___49956){
var seq__49890_49957__$1 = temp__5753__auto___49956;
if(cljs.core.chunked_seq_QMARK_(seq__49890_49957__$1)){
var c__4679__auto___49958 = cljs.core.chunk_first(seq__49890_49957__$1);
var G__49959 = cljs.core.chunk_rest(seq__49890_49957__$1);
var G__49960 = c__4679__auto___49958;
var G__49961 = cljs.core.count(c__4679__auto___49958);
var G__49962 = (0);
seq__49890_49947 = G__49959;
chunk__49891_49948 = G__49960;
count__49892_49949 = G__49961;
i__49893_49950 = G__49962;
continue;
} else {
var i_49963 = cljs.core.first(seq__49890_49957__$1);
re_frame.core.reg_global_interceptor(i_49963);


var G__49964 = cljs.core.next(seq__49890_49957__$1);
var G__49965 = null;
var G__49966 = (0);
var G__49967 = (0);
seq__49890_49947 = G__49964;
chunk__49891_49948 = G__49965;
count__49892_49949 = G__49966;
i__49893_49950 = G__49967;
continue;
}
} else {
}
}
break;
}

re_chain.core.configure_BANG_(cljs.core.concat.cljs$core$IFn$_invoke$arity$2(kee_frame.router.default_chain_links,chain_links));

kee_frame.router.reg_route_event(scroll);

if(cljs.core.truth_((function (){var and__4251__auto__ = routes;
if(cljs.core.truth_(and__4251__auto__)){
return router;
} else {
return and__4251__auto__;
}
})())){
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("Both routes and router specified. If you want to use these routes, pass them to your router constructor.",new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"routes","routes",457900162),routes,new cljs.core.Keyword(null,"router","router",1091916230),router], null));
} else {
}

if(cljs.core.truth_((function (){var or__4253__auto__ = routes;
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
return router;
}
})())){
kee_frame.router.bootstrap_routes(config);
} else {
}

if(cljs.core.truth_(initial_db)){
re_frame.core.dispatch_sync(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"init","init",-1875481434),initial_db], null));
} else {
}

if(cljs.core.truth_(screen)){
var config_49971__$1 = ((cljs.core.boolean_QMARK_(screen))?null:screen);
if(cljs.core.truth_(cljs.core.deref(kee_frame.state.breakpoints_initialized_QMARK_))){
kee_frame.interop.set_breakpoint_subs(config_49971__$1);
} else {
kee_frame.interop.set_breakpoints(config_49971__$1);

cljs.core.reset_BANG_(kee_frame.state.breakpoints_initialized_QMARK_,true);
}
} else {
}

re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword("kee-frame","route","kee-frame/route",-106555640),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([(function (db,_){
return new cljs.core.Keyword("kee-frame","route","kee-frame/route",-106555640).cljs$core$IFn$_invoke$arity$2(db,null);
})], 0));

return kee_frame.interop.render_root(root_component);
});
kee_frame.router.make_route_component = (function kee_frame$router$make_route_component(component,route){
if(cljs.core.fn_QMARK_(component)){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [component,route], null);
} else {
return component;
}
});
kee_frame.router.case_route = (function kee_frame$router$case_route(var_args){
var args__4870__auto__ = [];
var len__4864__auto___49975 = arguments.length;
var i__4865__auto___49976 = (0);
while(true){
if((i__4865__auto___49976 < len__4864__auto___49975)){
args__4870__auto__.push((arguments[i__4865__auto___49976]));

var G__49979 = (i__4865__auto___49976 + (1));
i__4865__auto___49976 = G__49979;
continue;
} else {
}
break;
}

var argseq__4871__auto__ = ((((1) < args__4870__auto__.length))?(new cljs.core.IndexedSeq(args__4870__auto__.slice((1)),(0),null)):null);
return kee_frame.router.case_route.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__4871__auto__);
});

(kee_frame.router.case_route.cljs$core$IFn$_invoke$arity$variadic = (function (f,pairs){
var route = re_frame.core.subscribe.cljs$core$IFn$_invoke$arity$1(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("kee-frame","route","kee-frame/route",-106555640)], null));
var dispatch_value = (function (){var G__49898 = cljs.core.deref(route);
return (f.cljs$core$IFn$_invoke$arity$1 ? f.cljs$core$IFn$_invoke$arity$1(G__49898) : f.call(null,G__49898));
})();
var G__49902 = cljs.core.partition_all.cljs$core$IFn$_invoke$arity$2((2),pairs);
var vec__49903 = G__49902;
var seq__49904 = cljs.core.seq(vec__49903);
var first__49905 = cljs.core.first(seq__49904);
var seq__49904__$1 = cljs.core.next(seq__49904);
var first_pair = first__49905;
var rest_pairs = seq__49904__$1;
var G__49902__$1 = G__49902;
while(true){
var vec__49907 = G__49902__$1;
var seq__49908 = cljs.core.seq(vec__49907);
var first__49909 = cljs.core.first(seq__49908);
var seq__49908__$1 = cljs.core.next(seq__49908);
var first_pair__$1 = first__49909;
var rest_pairs__$1 = seq__49908__$1;
if(cljs.core.truth_((function (){var G__49912 = first_pair__$1;
var G__49912__$1 = (((G__49912 == null))?null:cljs.core.seq(G__49912));
var G__49912__$2 = (((G__49912__$1 == null))?null:cljs.core.count(G__49912__$1));
if((G__49912__$2 == null)){
return null;
} else {
return cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(G__49912__$2,(2));
}
})())){
var vec__49914 = first_pair__$1;
var value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49914,(0),null);
var component = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49914,(1),null);
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(value,dispatch_value)){
return kee_frame.router.make_route_component(component,cljs.core.deref(route));
} else {
var G__49981 = rest_pairs__$1;
G__49902__$1 = G__49981;
continue;
}
} else {
if(cljs.core.truth_((function (){var G__49917 = first_pair__$1;
var G__49917__$1 = (((G__49917 == null))?null:cljs.core.seq(G__49917));
var G__49917__$2 = (((G__49917__$1 == null))?null:cljs.core.count(G__49917__$1));
if((G__49917__$2 == null)){
return null;
} else {
return cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(G__49917__$2,(1));
}
})())){
return kee_frame.router.make_route_component(cljs.core.first(first_pair__$1),cljs.core.deref(route));
} else {
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("Could not find a component to match route. Did you remember to include a single last default case?",new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"route","route",329891309),cljs.core.deref(route),new cljs.core.Keyword(null,"dispatch-value","dispatch-value",163470182),dispatch_value,new cljs.core.Keyword(null,"pairs","pairs",614609779),pairs], null));

}
}
break;
}
}));

(kee_frame.router.case_route.cljs$lang$maxFixedArity = (1));

/** @this {Function} */
(kee_frame.router.case_route.cljs$lang$applyTo = (function (seq49895){
var G__49896 = cljs.core.first(seq49895);
var seq49895__$1 = cljs.core.next(seq49895);
var self__4851__auto__ = this;
return self__4851__auto__.cljs$core$IFn$_invoke$arity$variadic(G__49896,seq49895__$1);
}));

kee_frame.router.switch_route = (function kee_frame$router$switch_route(var_args){
var args__4870__auto__ = [];
var len__4864__auto___49982 = arguments.length;
var i__4865__auto___49983 = (0);
while(true){
if((i__4865__auto___49983 < len__4864__auto___49982)){
args__4870__auto__.push((arguments[i__4865__auto___49983]));

var G__49984 = (i__4865__auto___49983 + (1));
i__4865__auto___49983 = G__49984;
continue;
} else {
}
break;
}

var argseq__4871__auto__ = ((((1) < args__4870__auto__.length))?(new cljs.core.IndexedSeq(args__4870__auto__.slice((1)),(0),null)):null);
return kee_frame.router.switch_route.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__4871__auto__);
});

(kee_frame.router.switch_route.cljs$core$IFn$_invoke$arity$variadic = (function (f,pairs){
if(cljs.core.even_QMARK_(cljs.core.count(pairs))){
} else {
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("switch-route accepts an even number of args",new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"pairs","pairs",614609779),pairs,new cljs.core.Keyword(null,"pairs-count","pairs-count",168301941),cljs.core.count(pairs)], null));
}

var route = re_frame.core.subscribe.cljs$core$IFn$_invoke$arity$1(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("kee-frame","route","kee-frame/route",-106555640)], null));
var dispatch_value = (function (){var G__49920 = cljs.core.deref(route);
return (f.cljs$core$IFn$_invoke$arity$1 ? f.cljs$core$IFn$_invoke$arity$1(G__49920) : f.call(null,G__49920));
})();
var G__49924 = cljs.core.partition.cljs$core$IFn$_invoke$arity$2((2),pairs);
var vec__49925 = G__49924;
var seq__49926 = cljs.core.seq(vec__49925);
var first__49927 = cljs.core.first(seq__49926);
var seq__49926__$1 = cljs.core.next(seq__49926);
var first_pair = first__49927;
var rest_pairs = seq__49926__$1;
var G__49924__$1 = G__49924;
while(true){
var vec__49928 = G__49924__$1;
var seq__49929 = cljs.core.seq(vec__49928);
var first__49930 = cljs.core.first(seq__49929);
var seq__49929__$1 = cljs.core.next(seq__49929);
var first_pair__$1 = first__49930;
var rest_pairs__$1 = seq__49929__$1;
if(cljs.core.truth_(first_pair__$1)){
var vec__49931 = first_pair__$1;
var value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49931,(0),null);
var component = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49931,(1),null);
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(value,dispatch_value)){
return kee_frame.router.make_route_component(component,cljs.core.deref(route));
} else {
var G__49985 = rest_pairs__$1;
G__49924__$1 = G__49985;
continue;
}
} else {
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("Could not find a component to match route. Did you remember to include a case for nil?",new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"route","route",329891309),cljs.core.deref(route),new cljs.core.Keyword(null,"dispatch-value","dispatch-value",163470182),dispatch_value,new cljs.core.Keyword(null,"pairs","pairs",614609779),pairs], null));
}
break;
}
}));

(kee_frame.router.switch_route.cljs$lang$maxFixedArity = (1));

/** @this {Function} */
(kee_frame.router.switch_route.cljs$lang$applyTo = (function (seq49918){
var G__49919 = cljs.core.first(seq49918);
var seq49918__$1 = cljs.core.next(seq49918);
var self__4851__auto__ = this;
return self__4851__auto__.cljs$core$IFn$_invoke$arity$variadic(G__49919,seq49918__$1);
}));


//# sourceMappingURL=kee_frame.router.js.map
