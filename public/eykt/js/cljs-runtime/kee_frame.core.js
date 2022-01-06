goog.provide('kee_frame.core');
kee_frame.core.valid_option_key_QMARK_ = new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 17, [new cljs.core.Keyword(null,"debug-config","debug-config",329260673),null,new cljs.core.Keyword(null,"routes","routes",457900162),null,new cljs.core.Keyword(null,"screen","screen",1990059748),null,new cljs.core.Keyword(null,"router","router",1091916230),null,new cljs.core.Keyword(null,"app-db-spec","app-db-spec",-1030582586),null,new cljs.core.Keyword(null,"route-change-event","route-change-event",1834214120),null,new cljs.core.Keyword(null,"hash-routing?","hash-routing?",471914732),null,new cljs.core.Keyword(null,"global-interceptors","global-interceptors",-1995759472),null,new cljs.core.Keyword(null,"chain-links","chain-links",951542256),null,new cljs.core.Keyword(null,"process-route","process-route",-38423566),null,new cljs.core.Keyword(null,"debug?","debug?",-1831756173),null,new cljs.core.Keyword(null,"scroll","scroll",971553779),null,new cljs.core.Keyword(null,"not-found","not-found",-629079980),null,new cljs.core.Keyword(null,"log-spec-error","log-spec-error",718867605),null,new cljs.core.Keyword(null,"root-component","root-component",-1807026475),null,new cljs.core.Keyword(null,"log","log",-1595516004),null,new cljs.core.Keyword(null,"initial-db","initial-db",1939835102),null], null), null);
/**
 * Complete listing of invalid options sent to the `start!` function.
 */
kee_frame.core.extra_options = (function kee_frame$core$extra_options(options){
return cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,cljs.core.filter.cljs$core$IFn$_invoke$arity$2((function (p__49934){
var vec__49935 = p__49934;
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49935,(0),null);
return cljs.core.not((kee_frame.core.valid_option_key_QMARK_.cljs$core$IFn$_invoke$arity$1 ? kee_frame.core.valid_option_key_QMARK_.cljs$core$IFn$_invoke$arity$1(k) : kee_frame.core.valid_option_key_QMARK_.call(null,k)));
}),options));
});
/**
 * Starts your client application with the specified `options`.
 * 
 *   This function is intentionally forgiving in certain ways:
 *   - You can call it as often as you want. Figwheel should call it on each code change
 *   - You can omit the `options` altogether. kee-frame chooses sensible defaults for you and leads the way.
 * 
 *   Usage:
 *   ```
 *   (k/start! {:debug?         true
 *           :routes         my-reitit-routes
 *           :hash-routing?  true
 *           :initial-db     {:some-property "default value"}
 *           :root-component [my-reagent-root-component]
 *           :app-db-spec    :spec/my-db-spec})
 *   ```
 */
kee_frame.core.start_BANG_ = (function kee_frame$core$start_BANG_(options){
kee_frame.log.init_BANG_(new cljs.core.Keyword(null,"log","log",-1595516004).cljs$core$IFn$_invoke$arity$1(options));

if(cljs.spec.alpha.valid_QMARK_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword("kee-frame.spec","start-options","kee-frame.spec/start-options",-1456248968),options)){
} else {
expound.alpha.expound.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword("kee-frame.spec","start-options","kee-frame.spec/start-options",-1456248968),options);

throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("Invalid options",cljs.spec.alpha.explain_data(new cljs.core.Keyword("kee-frame.spec","start-options","kee-frame.spec/start-options",-1456248968),options));
}

var extras_49946 = kee_frame.core.extra_options(options);
if(cljs.core.seq(extras_49946)){
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2(["Uknown startup options. Valid keys are ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(kee_frame.core.valid_option_key_QMARK_)].join(''),extras_49946);
} else {
}

return kee_frame.router.start_BANG_(options);
});
kee_frame.core.reg_chain = kee_frame.legacy.reg_chain;
kee_frame.core.reg_chain_named = kee_frame.legacy.reg_chain_named;
kee_frame.core.reg_event_fx = kee_frame.legacy.reg_event_fx;
kee_frame.core.reg_event_db = kee_frame.legacy.reg_event_db;
kee_frame.core._replace_controller = (function kee_frame$core$_replace_controller(controllers,controller){
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3((function (ret,existing_controller){
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"id","id",-1388402092).cljs$core$IFn$_invoke$arity$1(controller),new cljs.core.Keyword(null,"id","id",-1388402092).cljs$core$IFn$_invoke$arity$1(existing_controller))){
return cljs.core.conj.cljs$core$IFn$_invoke$arity$2(ret,controller);
} else {
return cljs.core.conj.cljs$core$IFn$_invoke$arity$2(ret,existing_controller);
}
}),re_frame.interop.empty_queue,controllers);
});
/**
 * Put a controller config map into the global controller registry.
 * 
 *   Parameters:
 * 
 *   `id`: Must be unique in controllere registry. Will appear in logs.
 * 
 *   `controller`: A map with the following keys:
 *   - `:params`: A function that receives the route data and returns the part that should be sent to the `start` function. A nil
 *   return means that the controller should not run for this route.
 * 
 *   - `:start`: A function or an event vector. Called when `params` returns a non-nil value different from the previous
 *   invocation. The function receives whatever non-nil value that was returned from `params`,
 *   and returns a re-frame event vector. If the function does nothing but returning the vector, the surrounding function
 *   can be omitted.
 * 
 *   - `:stop`: Optional. A function or an event vector. Called when previous invocation of `params` returned non-nil and the
 *   current invocation returned nil. If the function does nothing but returning the vector, the surrounding function
 *   can be omitted.
 */
kee_frame.core.reg_controller = (function kee_frame$core$reg_controller(id,controller){
var controller__$1 = cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(controller,new cljs.core.Keyword(null,"id","id",-1388402092),id);
if(cljs.spec.alpha.valid_QMARK_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword("kee-frame.spec","controller","kee-frame.spec/controller",70465085),controller__$1)){
} else {
expound.alpha.expound.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword("kee-frame.spec","controller","kee-frame.spec/controller",70465085),controller__$1);

throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("Invalid controller",cljs.spec.alpha.explain_data(new cljs.core.Keyword("kee-frame.spec","controller","kee-frame.spec/controller",70465085),controller__$1));
}

return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$2(kee_frame.state.controllers,(function (controllers){
var ids = cljs.core.map.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"id","id",-1388402092),controllers);
if(cljs.core.truth_(cljs.core.some(cljs.core.PersistentHashSet.createAsIfByAssoc([id]),ids))){
return kee_frame.core._replace_controller(controllers,controller__$1);
} else {
return cljs.core.conj.cljs$core$IFn$_invoke$arity$2(controllers,controller__$1);
}
}));
});
/**
 * Make a uri from route data. Useful for avoiding hard coded links in your app.
 * 
 *   Parameters:
 * 
 *   `handler`: The reitit handler from route data
 * 
 *   `params`: Reitit route params for the requested route
 * 
 *   Usage: `[:a {:href (k/path-for [:orders :sort-by :date]} "Orders sorted by date"]`
 */
kee_frame.core.path_for = (function kee_frame$core$path_for(var_args){
var args__4870__auto__ = [];
var len__4864__auto___49968 = arguments.length;
var i__4865__auto___49969 = (0);
while(true){
if((i__4865__auto___49969 < len__4864__auto___49968)){
args__4870__auto__.push((arguments[i__4865__auto___49969]));

var G__49970 = (i__4865__auto___49969 + (1));
i__4865__auto___49969 = G__49970;
continue;
} else {
}
break;
}

var argseq__4871__auto__ = ((((1) < args__4870__auto__.length))?(new cljs.core.IndexedSeq(args__4870__auto__.slice((1)),(0),null)):null);
return kee_frame.core.path_for.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__4871__auto__);
});

(kee_frame.core.path_for.cljs$core$IFn$_invoke$arity$variadic = (function (handler,params){
return cljs.core.apply.cljs$core$IFn$_invoke$arity$3(kee_frame.router.url,handler,params);
}));

(kee_frame.core.path_for.cljs$lang$maxFixedArity = (1));

/** @this {Function} */
(kee_frame.core.path_for.cljs$lang$applyTo = (function (seq49939){
var G__49940 = cljs.core.first(seq49939);
var seq49939__$1 = cljs.core.next(seq49939);
var self__4851__auto__ = this;
return self__4851__auto__.cljs$core$IFn$_invoke$arity$variadic(G__49940,seq49939__$1);
}));

/**
 * Reagent component that renders different components for different routes.
 * 
 *   Semantics similar to clojure.core/case
 * 
 *   You can include a single default component at the end that serves as the default view
 * 
 *   Parameters:
 * 
 *   `f`: A function that receives the route data on every route change, and returns the value to dispatch on.
 * 
 *   `pairs`: A pair consists of the dispatch value and the reagent component to dispatch to. An optional single default
 *   component can be added at the end.
 * 
 *   Returns the first component with a matching dispatch value.
 * 
 *   Usage:
 *   ```
 *   [k/switch-route (fn [route] (:handler route))
 *  :index [:div "This is index page"]
 *  :about [:div "This is the about page"]
 *  [:div "Probably also the index page"]]
 *   ```
 */
kee_frame.core.case_route = (function kee_frame$core$case_route(var_args){
var args__4870__auto__ = [];
var len__4864__auto___49972 = arguments.length;
var i__4865__auto___49973 = (0);
while(true){
if((i__4865__auto___49973 < len__4864__auto___49972)){
args__4870__auto__.push((arguments[i__4865__auto___49973]));

var G__49974 = (i__4865__auto___49973 + (1));
i__4865__auto___49973 = G__49974;
continue;
} else {
}
break;
}

var argseq__4871__auto__ = ((((1) < args__4870__auto__.length))?(new cljs.core.IndexedSeq(args__4870__auto__.slice((1)),(0),null)):null);
return kee_frame.core.case_route.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__4871__auto__);
});

(kee_frame.core.case_route.cljs$core$IFn$_invoke$arity$variadic = (function (f,pairs){
return cljs.core.apply.cljs$core$IFn$_invoke$arity$3(kee_frame.router.case_route,f,pairs);
}));

(kee_frame.core.case_route.cljs$lang$maxFixedArity = (1));

/** @this {Function} */
(kee_frame.core.case_route.cljs$lang$applyTo = (function (seq49942){
var G__49943 = cljs.core.first(seq49942);
var seq49942__$1 = cljs.core.next(seq49942);
var self__4851__auto__ = this;
return self__4851__auto__.cljs$core$IFn$_invoke$arity$variadic(G__49943,seq49942__$1);
}));

/**
 * DEPRECATED in favor of case-route
 * 
 *   Reagent component that renders different components for different routes.
 * 
 *   You might need to include a case for `nil`, since there are no route data before the first navigation.
 * 
 *   Parameters:
 * 
 *   `f`: A function that receives the route data on every route change, and returns the value to dispatch on.
 * 
 *   `pairs`: A pair consists of the dispatch value and the reagent component to dispatch to.
 * 
 *   Returns the first component with a matching dispatch value.
 * 
 *   Usage:
 *   ```
 *   [k/switch-route (fn [route] (:handler route))
 *  :index [:div "This is index page"]
 *  :about [:div "This is the about page"]
 *  nil    [:div "Probably also the index page"]]
 *   ```
 */
kee_frame.core.switch_route = (function kee_frame$core$switch_route(var_args){
var args__4870__auto__ = [];
var len__4864__auto___49977 = arguments.length;
var i__4865__auto___49978 = (0);
while(true){
if((i__4865__auto___49978 < len__4864__auto___49977)){
args__4870__auto__.push((arguments[i__4865__auto___49978]));

var G__49980 = (i__4865__auto___49978 + (1));
i__4865__auto___49978 = G__49980;
continue;
} else {
}
break;
}

var argseq__4871__auto__ = ((((1) < args__4870__auto__.length))?(new cljs.core.IndexedSeq(args__4870__auto__.slice((1)),(0),null)):null);
return kee_frame.core.switch_route.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__4871__auto__);
});

(kee_frame.core.switch_route.cljs$core$IFn$_invoke$arity$variadic = (function (f,pairs){
return cljs.core.apply.cljs$core$IFn$_invoke$arity$3(kee_frame.router.switch_route,f,pairs);
}));

(kee_frame.core.switch_route.cljs$lang$maxFixedArity = (1));

/** @this {Function} */
(kee_frame.core.switch_route.cljs$lang$applyTo = (function (seq49944){
var G__49945 = cljs.core.first(seq49944);
var seq49944__$1 = cljs.core.next(seq49944);
var self__4851__auto__ = this;
return self__4851__auto__.cljs$core$IFn$_invoke$arity$variadic(G__49945,seq49944__$1);
}));


//# sourceMappingURL=kee_frame.core.js.map
