goog.provide('day8.re_frame.http_fx');
/**
 * ajax-request only provides a single handler for success and errors
 */
day8.re_frame.http_fx.ajax_xhrio_handler = (function day8$re_frame$http_fx$ajax_xhrio_handler(on_success,on_failure,xhrio,p__44614){
var vec__44615 = p__44614;
var success_QMARK_ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__44615,(0),null);
var response = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__44615,(1),null);
if(cljs.core.truth_(success_QMARK_)){
return (on_success.cljs$core$IFn$_invoke$arity$1 ? on_success.cljs$core$IFn$_invoke$arity$1(response) : on_success.call(null,response));
} else {
var details = cljs.core.merge.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.PersistentArrayMap(null, 5, [new cljs.core.Keyword(null,"uri","uri",-774711847),xhrio.getLastUri(),new cljs.core.Keyword(null,"last-method","last-method",-563909920),xhrio.lastMethod_,new cljs.core.Keyword(null,"last-error","last-error",1848699973),xhrio.getLastError(),new cljs.core.Keyword(null,"last-error-code","last-error-code",276598110),xhrio.getLastErrorCode(),new cljs.core.Keyword(null,"debug-message","debug-message",-502855302),goog.net.ErrorCode.getDebugMessage(xhrio.getLastErrorCode())], null),response], 0));
return (on_failure.cljs$core$IFn$_invoke$arity$1 ? on_failure.cljs$core$IFn$_invoke$arity$1(details) : on_failure.call(null,details));
}
});
day8.re_frame.http_fx.request__GT_xhrio_options = (function day8$re_frame$http_fx$request__GT_xhrio_options(p__44626){
var map__44627 = p__44626;
var map__44627__$1 = cljs.core.__destructure_map(map__44627);
var request = map__44627__$1;
var on_success = cljs.core.get.cljs$core$IFn$_invoke$arity$3(map__44627__$1,new cljs.core.Keyword(null,"on-success","on-success",1786904109),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"http-no-on-success","http-no-on-success",-1593227158)], null));
var on_failure = cljs.core.get.cljs$core$IFn$_invoke$arity$3(map__44627__$1,new cljs.core.Keyword(null,"on-failure","on-failure",842888245),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"http-no-on-failure","http-no-on-failure",962976084)], null));
var api = (new goog.net.XhrIo());
return cljs.core.dissoc.cljs$core$IFn$_invoke$arity$variadic(cljs.core.assoc.cljs$core$IFn$_invoke$arity$variadic(request,new cljs.core.Keyword(null,"api","api",-899839580),api,cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"handler","handler",-195596612),cljs.core.partial.cljs$core$IFn$_invoke$arity$4(day8.re_frame.http_fx.ajax_xhrio_handler,(function (p1__44622_SHARP_){
return re_frame.core.dispatch(cljs.core.conj.cljs$core$IFn$_invoke$arity$2(on_success,p1__44622_SHARP_));
}),(function (p1__44623_SHARP_){
return re_frame.core.dispatch(cljs.core.conj.cljs$core$IFn$_invoke$arity$2(on_failure,p1__44623_SHARP_));
}),api)], 0)),new cljs.core.Keyword(null,"on-success","on-success",1786904109),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"on-failure","on-failure",842888245)], 0));
});
day8.re_frame.http_fx.http_effect = (function day8$re_frame$http_fx$http_effect(request){
var seq_request_maps = ((cljs.core.sequential_QMARK_(request))?request:new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [request], null));
var seq__44652 = cljs.core.seq(seq_request_maps);
var chunk__44653 = null;
var count__44654 = (0);
var i__44655 = (0);
while(true){
if((i__44655 < count__44654)){
var request__$1 = chunk__44653.cljs$core$IIndexed$_nth$arity$2(null,i__44655);
var G__44672_44683 = day8.re_frame.http_fx.request__GT_xhrio_options(request__$1);
(ajax.core.ajax_request.cljs$core$IFn$_invoke$arity$1 ? ajax.core.ajax_request.cljs$core$IFn$_invoke$arity$1(G__44672_44683) : ajax.core.ajax_request.call(null,G__44672_44683));


var G__44684 = seq__44652;
var G__44685 = chunk__44653;
var G__44686 = count__44654;
var G__44687 = (i__44655 + (1));
seq__44652 = G__44684;
chunk__44653 = G__44685;
count__44654 = G__44686;
i__44655 = G__44687;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__44652);
if(temp__5753__auto__){
var seq__44652__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__44652__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__44652__$1);
var G__44690 = cljs.core.chunk_rest(seq__44652__$1);
var G__44691 = c__4679__auto__;
var G__44692 = cljs.core.count(c__4679__auto__);
var G__44693 = (0);
seq__44652 = G__44690;
chunk__44653 = G__44691;
count__44654 = G__44692;
i__44655 = G__44693;
continue;
} else {
var request__$1 = cljs.core.first(seq__44652__$1);
var G__44682_44694 = day8.re_frame.http_fx.request__GT_xhrio_options(request__$1);
(ajax.core.ajax_request.cljs$core$IFn$_invoke$arity$1 ? ajax.core.ajax_request.cljs$core$IFn$_invoke$arity$1(G__44682_44694) : ajax.core.ajax_request.call(null,G__44682_44694));


var G__44695 = cljs.core.next(seq__44652__$1);
var G__44696 = null;
var G__44697 = (0);
var G__44698 = (0);
seq__44652 = G__44695;
chunk__44653 = G__44696;
count__44654 = G__44697;
i__44655 = G__44698;
continue;
}
} else {
return null;
}
}
break;
}
});
re_frame.core.reg_fx(new cljs.core.Keyword(null,"http-xhrio","http-xhrio",1846166714),day8.re_frame.http_fx.http_effect);

//# sourceMappingURL=day8.re_frame.http_fx.js.map
