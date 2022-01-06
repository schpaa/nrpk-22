goog.provide('re_frame_fx.dispatch');
re_frame_fx.dispatch.deferred_actions = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(cljs.core.PersistentArrayMap.EMPTY);
re_frame_fx.dispatch.dispatch_debounce = (function re_frame_fx$dispatch$dispatch_debounce(dispatch_map_or_seq){
var cancel_timeout = (function (id){
var temp__5753__auto__ = cljs.core.get.cljs$core$IFn$_invoke$arity$2(cljs.core.deref(re_frame_fx.dispatch.deferred_actions),id);
if(cljs.core.truth_(temp__5753__auto__)){
var deferred = temp__5753__auto__;
clearTimeout(new cljs.core.Keyword(null,"timer","timer",-1266967739).cljs$core$IFn$_invoke$arity$1(deferred));

return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(re_frame_fx.dispatch.deferred_actions,cljs.core.dissoc,id);
} else {
return null;
}
});
var run_action = (function (action,event){
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"dispatch","dispatch",1319337009),action)){
return re_frame.core.dispatch(event);
} else {
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"dispatch-n","dispatch-n",-504469236),action)){
var seq__44567 = cljs.core.seq(event);
var chunk__44568 = null;
var count__44569 = (0);
var i__44570 = (0);
while(true){
if((i__44570 < count__44569)){
var e = chunk__44568.cljs$core$IIndexed$_nth$arity$2(null,i__44570);
re_frame.core.dispatch(e);


var G__44629 = seq__44567;
var G__44630 = chunk__44568;
var G__44631 = count__44569;
var G__44632 = (i__44570 + (1));
seq__44567 = G__44629;
chunk__44568 = G__44630;
count__44569 = G__44631;
i__44570 = G__44632;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__44567);
if(temp__5753__auto__){
var seq__44567__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__44567__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__44567__$1);
var G__44633 = cljs.core.chunk_rest(seq__44567__$1);
var G__44634 = c__4679__auto__;
var G__44635 = cljs.core.count(c__4679__auto__);
var G__44636 = (0);
seq__44567 = G__44633;
chunk__44568 = G__44634;
count__44569 = G__44635;
i__44570 = G__44636;
continue;
} else {
var e = cljs.core.first(seq__44567__$1);
re_frame.core.dispatch(e);


var G__44637 = cljs.core.next(seq__44567__$1);
var G__44638 = null;
var G__44639 = (0);
var G__44640 = (0);
seq__44567 = G__44637;
chunk__44568 = G__44638;
count__44569 = G__44639;
i__44570 = G__44640;
continue;
}
} else {
return null;
}
}
break;
}
} else {
return null;
}
}
});
var seq__44578 = cljs.core.seq((function (){var G__44597 = dispatch_map_or_seq;
if((!(cljs.core.sequential_QMARK_(dispatch_map_or_seq)))){
return (new cljs.core.PersistentVector(null,1,(5),cljs.core.PersistentVector.EMPTY_NODE,[G__44597],null));
} else {
return G__44597;
}
})());
var chunk__44579 = null;
var count__44580 = (0);
var i__44581 = (0);
while(true){
if((i__44581 < count__44580)){
var map__44598 = chunk__44579.cljs$core$IIndexed$_nth$arity$2(null,i__44581);
var map__44598__$1 = cljs.core.__destructure_map(map__44598);
var id = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44598__$1,new cljs.core.Keyword(null,"id","id",-1388402092));
var timeout = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44598__$1,new cljs.core.Keyword(null,"timeout","timeout",-318625318));
var action = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44598__$1,new cljs.core.Keyword(null,"action","action",-811238024));
var event = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44598__$1,new cljs.core.Keyword(null,"event","event",301435442));
if(cljs.core.truth_((function (){var fexpr__44600 = new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"dispatch-n","dispatch-n",-504469236),null,new cljs.core.Keyword(null,"dispatch","dispatch",1319337009),null], null), null);
return (fexpr__44600.cljs$core$IFn$_invoke$arity$1 ? fexpr__44600.cljs$core$IFn$_invoke$arity$1(action) : fexpr__44600.call(null,action));
})())){
cancel_timeout(id);

cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(re_frame_fx.dispatch.deferred_actions,cljs.core.assoc,id,new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"action","action",-811238024),action,new cljs.core.Keyword(null,"event","event",301435442),event,new cljs.core.Keyword(null,"timer","timer",-1266967739),setTimeout(((function (seq__44578,chunk__44579,count__44580,i__44581,map__44598,map__44598__$1,id,timeout,action,event,cancel_timeout,run_action){
return (function (){
cancel_timeout(id);

return run_action(action,event);
});})(seq__44578,chunk__44579,count__44580,i__44581,map__44598,map__44598__$1,id,timeout,action,event,cancel_timeout,run_action))
,timeout)], null));
} else {
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"cancel","cancel",-1964088360),action)){
cancel_timeout(id);
} else {
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"flush","flush",-1138711199),action)){
var temp__5753__auto___44647 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(cljs.core.deref(re_frame_fx.dispatch.deferred_actions),id);
if(cljs.core.truth_(temp__5753__auto___44647)){
var map__44602_44648 = temp__5753__auto___44647;
var map__44602_44649__$1 = cljs.core.__destructure_map(map__44602_44648);
var action_44650__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44602_44649__$1,new cljs.core.Keyword(null,"action","action",-811238024));
var event_44651__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44602_44649__$1,new cljs.core.Keyword(null,"event","event",301435442));
cancel_timeout(id);

run_action(action_44650__$1,event_44651__$1);
} else {
}
} else {
throw Error([":dispatch-debounce invalid action ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(action)].join(''));

}
}
}


var G__44658 = seq__44578;
var G__44659 = chunk__44579;
var G__44660 = count__44580;
var G__44661 = (i__44581 + (1));
seq__44578 = G__44658;
chunk__44579 = G__44659;
count__44580 = G__44660;
i__44581 = G__44661;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__44578);
if(temp__5753__auto__){
var seq__44578__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__44578__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__44578__$1);
var G__44664 = cljs.core.chunk_rest(seq__44578__$1);
var G__44665 = c__4679__auto__;
var G__44666 = cljs.core.count(c__4679__auto__);
var G__44667 = (0);
seq__44578 = G__44664;
chunk__44579 = G__44665;
count__44580 = G__44666;
i__44581 = G__44667;
continue;
} else {
var map__44609 = cljs.core.first(seq__44578__$1);
var map__44609__$1 = cljs.core.__destructure_map(map__44609);
var id = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44609__$1,new cljs.core.Keyword(null,"id","id",-1388402092));
var timeout = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44609__$1,new cljs.core.Keyword(null,"timeout","timeout",-318625318));
var action = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44609__$1,new cljs.core.Keyword(null,"action","action",-811238024));
var event = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44609__$1,new cljs.core.Keyword(null,"event","event",301435442));
if(cljs.core.truth_((function (){var fexpr__44610 = new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"dispatch-n","dispatch-n",-504469236),null,new cljs.core.Keyword(null,"dispatch","dispatch",1319337009),null], null), null);
return (fexpr__44610.cljs$core$IFn$_invoke$arity$1 ? fexpr__44610.cljs$core$IFn$_invoke$arity$1(action) : fexpr__44610.call(null,action));
})())){
cancel_timeout(id);

cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(re_frame_fx.dispatch.deferred_actions,cljs.core.assoc,id,new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"action","action",-811238024),action,new cljs.core.Keyword(null,"event","event",301435442),event,new cljs.core.Keyword(null,"timer","timer",-1266967739),setTimeout(((function (seq__44578,chunk__44579,count__44580,i__44581,map__44609,map__44609__$1,id,timeout,action,event,seq__44578__$1,temp__5753__auto__,cancel_timeout,run_action){
return (function (){
cancel_timeout(id);

return run_action(action,event);
});})(seq__44578,chunk__44579,count__44580,i__44581,map__44609,map__44609__$1,id,timeout,action,event,seq__44578__$1,temp__5753__auto__,cancel_timeout,run_action))
,timeout)], null));
} else {
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"cancel","cancel",-1964088360),action)){
cancel_timeout(id);
} else {
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"flush","flush",-1138711199),action)){
var temp__5753__auto___44673__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(cljs.core.deref(re_frame_fx.dispatch.deferred_actions),id);
if(cljs.core.truth_(temp__5753__auto___44673__$1)){
var map__44618_44674 = temp__5753__auto___44673__$1;
var map__44618_44675__$1 = cljs.core.__destructure_map(map__44618_44674);
var action_44676__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44618_44675__$1,new cljs.core.Keyword(null,"action","action",-811238024));
var event_44677__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44618_44675__$1,new cljs.core.Keyword(null,"event","event",301435442));
cancel_timeout(id);

run_action(action_44676__$1,event_44677__$1);
} else {
}
} else {
throw Error([":dispatch-debounce invalid action ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(action)].join(''));

}
}
}


var G__44678 = cljs.core.next(seq__44578__$1);
var G__44679 = null;
var G__44680 = (0);
var G__44681 = (0);
seq__44578 = G__44678;
chunk__44579 = G__44679;
count__44580 = G__44680;
i__44581 = G__44681;
continue;
}
} else {
return null;
}
}
break;
}
});
re_frame.core.reg_fx(new cljs.core.Keyword(null,"dispatch-debounce","dispatch-debounce",-2065179020),re_frame_fx.dispatch.dispatch_debounce);

//# sourceMappingURL=re_frame_fx.dispatch.js.map
