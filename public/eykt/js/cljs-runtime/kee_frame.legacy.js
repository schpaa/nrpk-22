goog.provide('kee_frame.legacy');
kee_frame.legacy.kee_frame_interceptors = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [kee_frame.event_logger.interceptor,re_frame.core.trim_v], null);
kee_frame.legacy.reg_warn = (function kee_frame$legacy$reg_warn(id){
return null;
});
/**
 * Exactly same signature as `re-frame.core/reg-event-fx`. Use this version if you want kee-frame logging and spec validation.
 * 
 *   `re-frame.core/trim-v` interceptor is also applied.
 */
kee_frame.legacy.reg_event_fx = (function kee_frame$legacy$reg_event_fx(var_args){
var G__48423 = arguments.length;
switch (G__48423) {
case 2:
return kee_frame.legacy.reg_event_fx.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return kee_frame.legacy.reg_event_fx.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(kee_frame.legacy.reg_event_fx.cljs$core$IFn$_invoke$arity$2 = (function (id,handler){
return kee_frame.legacy.reg_event_fx.cljs$core$IFn$_invoke$arity$3(id,null,handler);
}));

(kee_frame.legacy.reg_event_fx.cljs$core$IFn$_invoke$arity$3 = (function (id,interceptors,handler){
kee_frame.legacy.reg_warn(id);

return re_frame.core.reg_event_fx.cljs$core$IFn$_invoke$arity$3(id,cljs.core.concat.cljs$core$IFn$_invoke$arity$2(kee_frame.legacy.kee_frame_interceptors,interceptors),handler);
}));

(kee_frame.legacy.reg_event_fx.cljs$lang$maxFixedArity = 3);

/**
 * Exactly same signature as `re-frame.core/reg-event-db`. Use this version if you want kee-frame logging and spec validation.
 * 
 *   `re-frame.core/trim-v` interceptor is also applied.
 */
kee_frame.legacy.reg_event_db = (function kee_frame$legacy$reg_event_db(var_args){
var G__48446 = arguments.length;
switch (G__48446) {
case 2:
return kee_frame.legacy.reg_event_db.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return kee_frame.legacy.reg_event_db.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(kee_frame.legacy.reg_event_db.cljs$core$IFn$_invoke$arity$2 = (function (id,handler){
return kee_frame.legacy.reg_event_db.cljs$core$IFn$_invoke$arity$3(id,null,handler);
}));

(kee_frame.legacy.reg_event_db.cljs$core$IFn$_invoke$arity$3 = (function (id,interceptors,handler){
kee_frame.legacy.reg_warn(id);

return re_frame.core.reg_event_db.cljs$core$IFn$_invoke$arity$3(id,cljs.core.concat.cljs$core$IFn$_invoke$arity$2(kee_frame.legacy.kee_frame_interceptors,interceptors),handler);
}));

(kee_frame.legacy.reg_event_db.cljs$lang$maxFixedArity = 3);

/**
 * Register a list of re-frame fx handlers, chained together.
 * 
 *   The chaining is done through dispatch inference. https://github.com/Day8/re-frame-http-fx is supported by default,
 *   you can easily add your own like this: https://github.com/ingesolvoll/kee-frame#configuring-chains-since-020.
 * 
 *   Each handler's event vector is prepended with accumulated event vectors of previous handlers. So if the first handler
 *   receives [a b], and the second handler normally would receive [c], it will actually receive [a b c]. The purpose is
 *   to make all context available to the entire chain, without a complex framework or crazy scope tricks.
 * 
 *   Parameters:
 * 
 *   `id`: the id of the first re-frame event. The next events in the chain will get the same id followed by an index, so
 *   if your id is `add-todo`, the next one in chain will be called `add-todo-1`.
 * 
 *   `handlers`: re-frame event handler functions, registered with `kee-frame.core/reg-event-fx`.
 * 
 * 
 *   Usage:
 *   ```
 *   (k/reg-chain
 *  :load-customer-data
 * 
 *  (fn [ctx [customer-id]]
 *    {:http-xhrio {:uri    (str "/customer/" customer-id)
 *                  :method :get}})
 * 
 *  (fn [cxt [customer-id customer-data]
 *    (assoc-in ctx [:db :customers customer-id] customer-data)))
 *   ```
 */
kee_frame.legacy.reg_chain = (function kee_frame$legacy$reg_chain(var_args){
var args__4870__auto__ = [];
var len__4864__auto___48477 = arguments.length;
var i__4865__auto___48478 = (0);
while(true){
if((i__4865__auto___48478 < len__4864__auto___48477)){
args__4870__auto__.push((arguments[i__4865__auto___48478]));

var G__48479 = (i__4865__auto___48478 + (1));
i__4865__auto___48478 = G__48479;
continue;
} else {
}
break;
}

var argseq__4871__auto__ = ((((1) < args__4870__auto__.length))?(new cljs.core.IndexedSeq(args__4870__auto__.slice((1)),(0),null)):null);
return kee_frame.legacy.reg_chain.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__4871__auto__);
});

(kee_frame.legacy.reg_chain.cljs$core$IFn$_invoke$arity$variadic = (function (id,handlers){
kee_frame.legacy.reg_warn(id);

return cljs.core.apply.cljs$core$IFn$_invoke$arity$4(re_chain.core.reg_chain_STAR_,id,kee_frame.legacy.kee_frame_interceptors,handlers);
}));

(kee_frame.legacy.reg_chain.cljs$lang$maxFixedArity = (1));

/** @this {Function} */
(kee_frame.legacy.reg_chain.cljs$lang$applyTo = (function (seq48451){
var G__48452 = cljs.core.first(seq48451);
var seq48451__$1 = cljs.core.next(seq48451);
var self__4851__auto__ = this;
return self__4851__auto__.cljs$core$IFn$_invoke$arity$variadic(G__48452,seq48451__$1);
}));

/**
 * Same as `reg-chain`, but with manually named event handlers. Useful when you need more meaningful names in your
 *   event log.
 * 
 *   Parameters:
 * 
 *   `handlers`: pairs of id and event handler.
 * 
 *   Usage:
 *   ```
 *   (k/reg-chain-named
 * 
 *  :load-customer-data
 *  (fn [ctx [customer-id]]
 *    {:http-xhrio {:uri "..."}})
 * 
 *  :receive-customer-data
 *   (fn [ctx [customer-id customer-data]]
 *    (assoc-in ctx [:db :customers customer-id] customer-data)))
 *   ```
 */
kee_frame.legacy.reg_chain_named = (function kee_frame$legacy$reg_chain_named(var_args){
var args__4870__auto__ = [];
var len__4864__auto___48482 = arguments.length;
var i__4865__auto___48483 = (0);
while(true){
if((i__4865__auto___48483 < len__4864__auto___48482)){
args__4870__auto__.push((arguments[i__4865__auto___48483]));

var G__48485 = (i__4865__auto___48483 + (1));
i__4865__auto___48483 = G__48485;
continue;
} else {
}
break;
}

var argseq__4871__auto__ = ((((0) < args__4870__auto__.length))?(new cljs.core.IndexedSeq(args__4870__auto__.slice((0)),(0),null)):null);
return kee_frame.legacy.reg_chain_named.cljs$core$IFn$_invoke$arity$variadic(argseq__4871__auto__);
});

(kee_frame.legacy.reg_chain_named.cljs$core$IFn$_invoke$arity$variadic = (function (handlers){
kee_frame.legacy.reg_warn("");

return cljs.core.apply.cljs$core$IFn$_invoke$arity$3(re_chain.core.reg_chain_named_STAR_,kee_frame.legacy.kee_frame_interceptors,handlers);
}));

(kee_frame.legacy.reg_chain_named.cljs$lang$maxFixedArity = (0));

/** @this {Function} */
(kee_frame.legacy.reg_chain_named.cljs$lang$applyTo = (function (seq48462){
var self__4852__auto__ = this;
return self__4852__auto__.cljs$core$IFn$_invoke$arity$variadic(cljs.core.seq(seq48462));
}));


//# sourceMappingURL=kee_frame.legacy.js.map
