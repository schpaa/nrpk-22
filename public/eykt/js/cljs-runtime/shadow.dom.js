goog.provide('shadow.dom');
shadow.dom.transition_supported_QMARK_ = (((typeof window !== 'undefined'))?goog.style.transition.isSupported():null);

/**
 * @interface
 */
shadow.dom.IElement = function(){};

var shadow$dom$IElement$_to_dom$dyn_49808 = (function (this$){
var x__4550__auto__ = (((this$ == null))?null:this$);
var m__4551__auto__ = (shadow.dom._to_dom[goog.typeOf(x__4550__auto__)]);
if((!((m__4551__auto__ == null)))){
return (m__4551__auto__.cljs$core$IFn$_invoke$arity$1 ? m__4551__auto__.cljs$core$IFn$_invoke$arity$1(this$) : m__4551__auto__.call(null,this$));
} else {
var m__4549__auto__ = (shadow.dom._to_dom["_"]);
if((!((m__4549__auto__ == null)))){
return (m__4549__auto__.cljs$core$IFn$_invoke$arity$1 ? m__4549__auto__.cljs$core$IFn$_invoke$arity$1(this$) : m__4549__auto__.call(null,this$));
} else {
throw cljs.core.missing_protocol("IElement.-to-dom",this$);
}
}
});
shadow.dom._to_dom = (function shadow$dom$_to_dom(this$){
if((((!((this$ == null)))) && ((!((this$.shadow$dom$IElement$_to_dom$arity$1 == null)))))){
return this$.shadow$dom$IElement$_to_dom$arity$1(this$);
} else {
return shadow$dom$IElement$_to_dom$dyn_49808(this$);
}
});


/**
 * @interface
 */
shadow.dom.SVGElement = function(){};

var shadow$dom$SVGElement$_to_svg$dyn_49812 = (function (this$){
var x__4550__auto__ = (((this$ == null))?null:this$);
var m__4551__auto__ = (shadow.dom._to_svg[goog.typeOf(x__4550__auto__)]);
if((!((m__4551__auto__ == null)))){
return (m__4551__auto__.cljs$core$IFn$_invoke$arity$1 ? m__4551__auto__.cljs$core$IFn$_invoke$arity$1(this$) : m__4551__auto__.call(null,this$));
} else {
var m__4549__auto__ = (shadow.dom._to_svg["_"]);
if((!((m__4549__auto__ == null)))){
return (m__4549__auto__.cljs$core$IFn$_invoke$arity$1 ? m__4549__auto__.cljs$core$IFn$_invoke$arity$1(this$) : m__4549__auto__.call(null,this$));
} else {
throw cljs.core.missing_protocol("SVGElement.-to-svg",this$);
}
}
});
shadow.dom._to_svg = (function shadow$dom$_to_svg(this$){
if((((!((this$ == null)))) && ((!((this$.shadow$dom$SVGElement$_to_svg$arity$1 == null)))))){
return this$.shadow$dom$SVGElement$_to_svg$arity$1(this$);
} else {
return shadow$dom$SVGElement$_to_svg$dyn_49812(this$);
}
});

shadow.dom.lazy_native_coll_seq = (function shadow$dom$lazy_native_coll_seq(coll,idx){
if((idx < coll.length)){
return (new cljs.core.LazySeq(null,(function (){
return cljs.core.cons((coll[idx]),(function (){var G__48847 = coll;
var G__48848 = (idx + (1));
return (shadow.dom.lazy_native_coll_seq.cljs$core$IFn$_invoke$arity$2 ? shadow.dom.lazy_native_coll_seq.cljs$core$IFn$_invoke$arity$2(G__48847,G__48848) : shadow.dom.lazy_native_coll_seq.call(null,G__48847,G__48848));
})());
}),null,null));
} else {
return null;
}
});

/**
* @constructor
 * @implements {cljs.core.IIndexed}
 * @implements {cljs.core.ICounted}
 * @implements {cljs.core.ISeqable}
 * @implements {cljs.core.IDeref}
 * @implements {shadow.dom.IElement}
*/
shadow.dom.NativeColl = (function (coll){
this.coll = coll;
this.cljs$lang$protocol_mask$partition0$ = 8421394;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(shadow.dom.NativeColl.prototype.cljs$core$IDeref$_deref$arity$1 = (function (this$){
var self__ = this;
var this$__$1 = this;
return self__.coll;
}));

(shadow.dom.NativeColl.prototype.cljs$core$IIndexed$_nth$arity$2 = (function (this$,n){
var self__ = this;
var this$__$1 = this;
return (self__.coll[n]);
}));

(shadow.dom.NativeColl.prototype.cljs$core$IIndexed$_nth$arity$3 = (function (this$,n,not_found){
var self__ = this;
var this$__$1 = this;
var or__4253__auto__ = (self__.coll[n]);
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
return not_found;
}
}));

(shadow.dom.NativeColl.prototype.cljs$core$ICounted$_count$arity$1 = (function (this$){
var self__ = this;
var this$__$1 = this;
return self__.coll.length;
}));

(shadow.dom.NativeColl.prototype.cljs$core$ISeqable$_seq$arity$1 = (function (this$){
var self__ = this;
var this$__$1 = this;
return shadow.dom.lazy_native_coll_seq(self__.coll,(0));
}));

(shadow.dom.NativeColl.prototype.shadow$dom$IElement$ = cljs.core.PROTOCOL_SENTINEL);

(shadow.dom.NativeColl.prototype.shadow$dom$IElement$_to_dom$arity$1 = (function (this$){
var self__ = this;
var this$__$1 = this;
return self__.coll;
}));

(shadow.dom.NativeColl.getBasis = (function (){
return new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"coll","coll",-1006698606,null)], null);
}));

(shadow.dom.NativeColl.cljs$lang$type = true);

(shadow.dom.NativeColl.cljs$lang$ctorStr = "shadow.dom/NativeColl");

(shadow.dom.NativeColl.cljs$lang$ctorPrWriter = (function (this__4491__auto__,writer__4492__auto__,opt__4493__auto__){
return cljs.core._write(writer__4492__auto__,"shadow.dom/NativeColl");
}));

/**
 * Positional factory function for shadow.dom/NativeColl.
 */
shadow.dom.__GT_NativeColl = (function shadow$dom$__GT_NativeColl(coll){
return (new shadow.dom.NativeColl(coll));
});

shadow.dom.native_coll = (function shadow$dom$native_coll(coll){
return (new shadow.dom.NativeColl(coll));
});
shadow.dom.dom_node = (function shadow$dom$dom_node(el){
if((el == null)){
return null;
} else {
if((((!((el == null))))?((((false) || ((cljs.core.PROTOCOL_SENTINEL === el.shadow$dom$IElement$))))?true:false):false)){
return el.shadow$dom$IElement$_to_dom$arity$1(null);
} else {
if(typeof el === 'string'){
return document.createTextNode(el);
} else {
if(typeof el === 'number'){
return document.createTextNode(cljs.core.str.cljs$core$IFn$_invoke$arity$1(el));
} else {
return el;

}
}
}
}
});
shadow.dom.query_one = (function shadow$dom$query_one(var_args){
var G__48868 = arguments.length;
switch (G__48868) {
case 1:
return shadow.dom.query_one.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return shadow.dom.query_one.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.dom.query_one.cljs$core$IFn$_invoke$arity$1 = (function (sel){
return document.querySelector(sel);
}));

(shadow.dom.query_one.cljs$core$IFn$_invoke$arity$2 = (function (sel,root){
return shadow.dom.dom_node(root).querySelector(sel);
}));

(shadow.dom.query_one.cljs$lang$maxFixedArity = 2);

shadow.dom.query = (function shadow$dom$query(var_args){
var G__48870 = arguments.length;
switch (G__48870) {
case 1:
return shadow.dom.query.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return shadow.dom.query.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.dom.query.cljs$core$IFn$_invoke$arity$1 = (function (sel){
return (new shadow.dom.NativeColl(document.querySelectorAll(sel)));
}));

(shadow.dom.query.cljs$core$IFn$_invoke$arity$2 = (function (sel,root){
return (new shadow.dom.NativeColl(shadow.dom.dom_node(root).querySelectorAll(sel)));
}));

(shadow.dom.query.cljs$lang$maxFixedArity = 2);

shadow.dom.by_id = (function shadow$dom$by_id(var_args){
var G__48876 = arguments.length;
switch (G__48876) {
case 2:
return shadow.dom.by_id.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 1:
return shadow.dom.by_id.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.dom.by_id.cljs$core$IFn$_invoke$arity$2 = (function (id,el){
return shadow.dom.dom_node(el).getElementById(id);
}));

(shadow.dom.by_id.cljs$core$IFn$_invoke$arity$1 = (function (id){
return document.getElementById(id);
}));

(shadow.dom.by_id.cljs$lang$maxFixedArity = 2);

shadow.dom.build = shadow.dom.dom_node;
shadow.dom.ev_stop = (function shadow$dom$ev_stop(var_args){
var G__48879 = arguments.length;
switch (G__48879) {
case 1:
return shadow.dom.ev_stop.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return shadow.dom.ev_stop.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 4:
return shadow.dom.ev_stop.cljs$core$IFn$_invoke$arity$4((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.dom.ev_stop.cljs$core$IFn$_invoke$arity$1 = (function (e){
if(cljs.core.truth_(e.stopPropagation)){
e.stopPropagation();

e.preventDefault();
} else {
(e.cancelBubble = true);

(e.returnValue = false);
}

return e;
}));

(shadow.dom.ev_stop.cljs$core$IFn$_invoke$arity$2 = (function (e,el){
shadow.dom.ev_stop.cljs$core$IFn$_invoke$arity$1(e);

return el;
}));

(shadow.dom.ev_stop.cljs$core$IFn$_invoke$arity$4 = (function (e,el,scope,owner){
shadow.dom.ev_stop.cljs$core$IFn$_invoke$arity$1(e);

return el;
}));

(shadow.dom.ev_stop.cljs$lang$maxFixedArity = 4);

/**
 * check wether a parent node (or the document) contains the child
 */
shadow.dom.contains_QMARK_ = (function shadow$dom$contains_QMARK_(var_args){
var G__48889 = arguments.length;
switch (G__48889) {
case 1:
return shadow.dom.contains_QMARK_.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return shadow.dom.contains_QMARK_.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.dom.contains_QMARK_.cljs$core$IFn$_invoke$arity$1 = (function (el){
return goog.dom.contains(document,shadow.dom.dom_node(el));
}));

(shadow.dom.contains_QMARK_.cljs$core$IFn$_invoke$arity$2 = (function (parent,el){
return goog.dom.contains(shadow.dom.dom_node(parent),shadow.dom.dom_node(el));
}));

(shadow.dom.contains_QMARK_.cljs$lang$maxFixedArity = 2);

shadow.dom.add_class = (function shadow$dom$add_class(el,cls){
return goog.dom.classlist.add(shadow.dom.dom_node(el),cls);
});
shadow.dom.remove_class = (function shadow$dom$remove_class(el,cls){
return goog.dom.classlist.remove(shadow.dom.dom_node(el),cls);
});
shadow.dom.toggle_class = (function shadow$dom$toggle_class(var_args){
var G__48907 = arguments.length;
switch (G__48907) {
case 2:
return shadow.dom.toggle_class.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return shadow.dom.toggle_class.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.dom.toggle_class.cljs$core$IFn$_invoke$arity$2 = (function (el,cls){
return goog.dom.classlist.toggle(shadow.dom.dom_node(el),cls);
}));

(shadow.dom.toggle_class.cljs$core$IFn$_invoke$arity$3 = (function (el,cls,v){
if(cljs.core.truth_(v)){
return shadow.dom.add_class(el,cls);
} else {
return shadow.dom.remove_class(el,cls);
}
}));

(shadow.dom.toggle_class.cljs$lang$maxFixedArity = 3);

shadow.dom.dom_listen = (cljs.core.truth_((function (){var or__4253__auto__ = (!((typeof document !== 'undefined')));
if(or__4253__auto__){
return or__4253__auto__;
} else {
return document.addEventListener;
}
})())?(function shadow$dom$dom_listen_good(el,ev,handler){
return el.addEventListener(ev,handler,false);
}):(function shadow$dom$dom_listen_ie(el,ev,handler){
try{return el.attachEvent(["on",cljs.core.str.cljs$core$IFn$_invoke$arity$1(ev)].join(''),(function (e){
return (handler.cljs$core$IFn$_invoke$arity$2 ? handler.cljs$core$IFn$_invoke$arity$2(e,el) : handler.call(null,e,el));
}));
}catch (e48914){if((e48914 instanceof Object)){
var e = e48914;
return console.log("didnt support attachEvent",el,e);
} else {
throw e48914;

}
}}));
shadow.dom.dom_listen_remove = (cljs.core.truth_((function (){var or__4253__auto__ = (!((typeof document !== 'undefined')));
if(or__4253__auto__){
return or__4253__auto__;
} else {
return document.removeEventListener;
}
})())?(function shadow$dom$dom_listen_remove_good(el,ev,handler){
return el.removeEventListener(ev,handler,false);
}):(function shadow$dom$dom_listen_remove_ie(el,ev,handler){
return el.detachEvent(["on",cljs.core.str.cljs$core$IFn$_invoke$arity$1(ev)].join(''),handler);
}));
shadow.dom.on_query = (function shadow$dom$on_query(root_el,ev,selector,handler){
var seq__48922 = cljs.core.seq(shadow.dom.query.cljs$core$IFn$_invoke$arity$2(selector,root_el));
var chunk__48923 = null;
var count__48924 = (0);
var i__48925 = (0);
while(true){
if((i__48925 < count__48924)){
var el = chunk__48923.cljs$core$IIndexed$_nth$arity$2(null,i__48925);
var handler_49937__$1 = ((function (seq__48922,chunk__48923,count__48924,i__48925,el){
return (function (e){
return (handler.cljs$core$IFn$_invoke$arity$2 ? handler.cljs$core$IFn$_invoke$arity$2(e,el) : handler.call(null,e,el));
});})(seq__48922,chunk__48923,count__48924,i__48925,el))
;
shadow.dom.dom_listen(el,cljs.core.name(ev),handler_49937__$1);


var G__49940 = seq__48922;
var G__49941 = chunk__48923;
var G__49942 = count__48924;
var G__49943 = (i__48925 + (1));
seq__48922 = G__49940;
chunk__48923 = G__49941;
count__48924 = G__49942;
i__48925 = G__49943;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__48922);
if(temp__5753__auto__){
var seq__48922__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__48922__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__48922__$1);
var G__49957 = cljs.core.chunk_rest(seq__48922__$1);
var G__49958 = c__4679__auto__;
var G__49959 = cljs.core.count(c__4679__auto__);
var G__49960 = (0);
seq__48922 = G__49957;
chunk__48923 = G__49958;
count__48924 = G__49959;
i__48925 = G__49960;
continue;
} else {
var el = cljs.core.first(seq__48922__$1);
var handler_49965__$1 = ((function (seq__48922,chunk__48923,count__48924,i__48925,el,seq__48922__$1,temp__5753__auto__){
return (function (e){
return (handler.cljs$core$IFn$_invoke$arity$2 ? handler.cljs$core$IFn$_invoke$arity$2(e,el) : handler.call(null,e,el));
});})(seq__48922,chunk__48923,count__48924,i__48925,el,seq__48922__$1,temp__5753__auto__))
;
shadow.dom.dom_listen(el,cljs.core.name(ev),handler_49965__$1);


var G__49967 = cljs.core.next(seq__48922__$1);
var G__49968 = null;
var G__49969 = (0);
var G__49970 = (0);
seq__48922 = G__49967;
chunk__48923 = G__49968;
count__48924 = G__49969;
i__48925 = G__49970;
continue;
}
} else {
return null;
}
}
break;
}
});
shadow.dom.on = (function shadow$dom$on(var_args){
var G__48942 = arguments.length;
switch (G__48942) {
case 3:
return shadow.dom.on.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 4:
return shadow.dom.on.cljs$core$IFn$_invoke$arity$4((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.dom.on.cljs$core$IFn$_invoke$arity$3 = (function (el,ev,handler){
return shadow.dom.on.cljs$core$IFn$_invoke$arity$4(el,ev,handler,false);
}));

(shadow.dom.on.cljs$core$IFn$_invoke$arity$4 = (function (el,ev,handler,capture){
if(cljs.core.vector_QMARK_(ev)){
return shadow.dom.on_query(el,cljs.core.first(ev),cljs.core.second(ev),handler);
} else {
var handler__$1 = (function (e){
return (handler.cljs$core$IFn$_invoke$arity$2 ? handler.cljs$core$IFn$_invoke$arity$2(e,el) : handler.call(null,e,el));
});
return shadow.dom.dom_listen(shadow.dom.dom_node(el),cljs.core.name(ev),handler__$1);
}
}));

(shadow.dom.on.cljs$lang$maxFixedArity = 4);

shadow.dom.remove_event_handler = (function shadow$dom$remove_event_handler(el,ev,handler){
return shadow.dom.dom_listen_remove(shadow.dom.dom_node(el),cljs.core.name(ev),handler);
});
shadow.dom.add_event_listeners = (function shadow$dom$add_event_listeners(el,events){
var seq__48969 = cljs.core.seq(events);
var chunk__48970 = null;
var count__48971 = (0);
var i__48972 = (0);
while(true){
if((i__48972 < count__48971)){
var vec__49003 = chunk__48970.cljs$core$IIndexed$_nth$arity$2(null,i__48972);
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49003,(0),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49003,(1),null);
shadow.dom.on.cljs$core$IFn$_invoke$arity$3(el,k,v);


var G__50002 = seq__48969;
var G__50003 = chunk__48970;
var G__50004 = count__48971;
var G__50005 = (i__48972 + (1));
seq__48969 = G__50002;
chunk__48970 = G__50003;
count__48971 = G__50004;
i__48972 = G__50005;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__48969);
if(temp__5753__auto__){
var seq__48969__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__48969__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__48969__$1);
var G__50010 = cljs.core.chunk_rest(seq__48969__$1);
var G__50011 = c__4679__auto__;
var G__50012 = cljs.core.count(c__4679__auto__);
var G__50013 = (0);
seq__48969 = G__50010;
chunk__48970 = G__50011;
count__48971 = G__50012;
i__48972 = G__50013;
continue;
} else {
var vec__49010 = cljs.core.first(seq__48969__$1);
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49010,(0),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49010,(1),null);
shadow.dom.on.cljs$core$IFn$_invoke$arity$3(el,k,v);


var G__50016 = cljs.core.next(seq__48969__$1);
var G__50017 = null;
var G__50018 = (0);
var G__50019 = (0);
seq__48969 = G__50016;
chunk__48970 = G__50017;
count__48971 = G__50018;
i__48972 = G__50019;
continue;
}
} else {
return null;
}
}
break;
}
});
shadow.dom.set_style = (function shadow$dom$set_style(el,styles){
var dom = shadow.dom.dom_node(el);
var seq__49015 = cljs.core.seq(styles);
var chunk__49016 = null;
var count__49017 = (0);
var i__49018 = (0);
while(true){
if((i__49018 < count__49017)){
var vec__49034 = chunk__49016.cljs$core$IIndexed$_nth$arity$2(null,i__49018);
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49034,(0),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49034,(1),null);
goog.style.setStyle(dom,cljs.core.name(k),(((v == null))?"":v));


var G__50026 = seq__49015;
var G__50027 = chunk__49016;
var G__50028 = count__49017;
var G__50029 = (i__49018 + (1));
seq__49015 = G__50026;
chunk__49016 = G__50027;
count__49017 = G__50028;
i__49018 = G__50029;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__49015);
if(temp__5753__auto__){
var seq__49015__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__49015__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__49015__$1);
var G__50031 = cljs.core.chunk_rest(seq__49015__$1);
var G__50032 = c__4679__auto__;
var G__50033 = cljs.core.count(c__4679__auto__);
var G__50034 = (0);
seq__49015 = G__50031;
chunk__49016 = G__50032;
count__49017 = G__50033;
i__49018 = G__50034;
continue;
} else {
var vec__49039 = cljs.core.first(seq__49015__$1);
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49039,(0),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49039,(1),null);
goog.style.setStyle(dom,cljs.core.name(k),(((v == null))?"":v));


var G__50037 = cljs.core.next(seq__49015__$1);
var G__50038 = null;
var G__50039 = (0);
var G__50040 = (0);
seq__49015 = G__50037;
chunk__49016 = G__50038;
count__49017 = G__50039;
i__49018 = G__50040;
continue;
}
} else {
return null;
}
}
break;
}
});
shadow.dom.set_attr_STAR_ = (function shadow$dom$set_attr_STAR_(el,key,value){
var G__49046_50041 = key;
var G__49046_50042__$1 = (((G__49046_50041 instanceof cljs.core.Keyword))?G__49046_50041.fqn:null);
switch (G__49046_50042__$1) {
case "id":
(el.id = cljs.core.str.cljs$core$IFn$_invoke$arity$1(value));

break;
case "class":
(el.className = cljs.core.str.cljs$core$IFn$_invoke$arity$1(value));

break;
case "for":
(el.htmlFor = value);

break;
case "cellpadding":
el.setAttribute("cellPadding",value);

break;
case "cellspacing":
el.setAttribute("cellSpacing",value);

break;
case "colspan":
el.setAttribute("colSpan",value);

break;
case "frameborder":
el.setAttribute("frameBorder",value);

break;
case "height":
el.setAttribute("height",value);

break;
case "maxlength":
el.setAttribute("maxLength",value);

break;
case "role":
el.setAttribute("role",value);

break;
case "rowspan":
el.setAttribute("rowSpan",value);

break;
case "type":
el.setAttribute("type",value);

break;
case "usemap":
el.setAttribute("useMap",value);

break;
case "valign":
el.setAttribute("vAlign",value);

break;
case "width":
el.setAttribute("width",value);

break;
case "on":
shadow.dom.add_event_listeners(el,value);

break;
case "style":
if((value == null)){
} else {
if(typeof value === 'string'){
el.setAttribute("style",value);
} else {
if(cljs.core.map_QMARK_(value)){
shadow.dom.set_style(el,value);
} else {
goog.style.setStyle(el,value);

}
}
}

break;
default:
var ks_50048 = cljs.core.name(key);
if(cljs.core.truth_((function (){var or__4253__auto__ = goog.string.startsWith(ks_50048,"data-");
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
return goog.string.startsWith(ks_50048,"aria-");
}
})())){
el.setAttribute(ks_50048,value);
} else {
(el[ks_50048] = value);
}

}

return el;
});
shadow.dom.set_attrs = (function shadow$dom$set_attrs(el,attrs){
return cljs.core.reduce_kv((function (el__$1,key,value){
shadow.dom.set_attr_STAR_(el__$1,key,value);

return el__$1;
}),shadow.dom.dom_node(el),attrs);
});
shadow.dom.set_attr = (function shadow$dom$set_attr(el,key,value){
return shadow.dom.set_attr_STAR_(shadow.dom.dom_node(el),key,value);
});
shadow.dom.has_class_QMARK_ = (function shadow$dom$has_class_QMARK_(el,cls){
return goog.dom.classlist.contains(shadow.dom.dom_node(el),cls);
});
shadow.dom.merge_class_string = (function shadow$dom$merge_class_string(current,extra_class){
if(cljs.core.seq(current)){
return [cljs.core.str.cljs$core$IFn$_invoke$arity$1(current)," ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(extra_class)].join('');
} else {
return extra_class;
}
});
shadow.dom.parse_tag = (function shadow$dom$parse_tag(spec){
var spec__$1 = cljs.core.name(spec);
var fdot = spec__$1.indexOf(".");
var fhash = spec__$1.indexOf("#");
if(((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2((-1),fdot)) && (cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2((-1),fhash)))){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [spec__$1,null,null], null);
} else {
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2((-1),fhash)){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [spec__$1.substring((0),fdot),null,clojure.string.replace(spec__$1.substring((fdot + (1))),/\./," ")], null);
} else {
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2((-1),fdot)){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [spec__$1.substring((0),fhash),spec__$1.substring((fhash + (1))),null], null);
} else {
if((fhash > fdot)){
throw ["cant have id after class?",spec__$1].join('');
} else {
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [spec__$1.substring((0),fhash),spec__$1.substring((fhash + (1)),fdot),clojure.string.replace(spec__$1.substring((fdot + (1))),/\./," ")], null);

}
}
}
}
});
shadow.dom.create_dom_node = (function shadow$dom$create_dom_node(tag_def,p__49066){
var map__49067 = p__49066;
var map__49067__$1 = cljs.core.__destructure_map(map__49067);
var props = map__49067__$1;
var class$ = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49067__$1,new cljs.core.Keyword(null,"class","class",-2030961996));
var tag_props = ({});
var vec__49069 = shadow.dom.parse_tag(tag_def);
var tag_name = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49069,(0),null);
var tag_id = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49069,(1),null);
var tag_classes = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49069,(2),null);
if(cljs.core.truth_(tag_id)){
(tag_props["id"] = tag_id);
} else {
}

if(cljs.core.truth_(tag_classes)){
(tag_props["class"] = shadow.dom.merge_class_string(class$,tag_classes));
} else {
}

var G__49085 = goog.dom.createDom(tag_name,tag_props);
shadow.dom.set_attrs(G__49085,cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(props,new cljs.core.Keyword(null,"class","class",-2030961996)));

return G__49085;
});
shadow.dom.append = (function shadow$dom$append(var_args){
var G__49091 = arguments.length;
switch (G__49091) {
case 1:
return shadow.dom.append.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return shadow.dom.append.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.dom.append.cljs$core$IFn$_invoke$arity$1 = (function (node){
if(cljs.core.truth_(node)){
var temp__5753__auto__ = shadow.dom.dom_node(node);
if(cljs.core.truth_(temp__5753__auto__)){
var n = temp__5753__auto__;
document.body.appendChild(n);

return n;
} else {
return null;
}
} else {
return null;
}
}));

(shadow.dom.append.cljs$core$IFn$_invoke$arity$2 = (function (el,node){
if(cljs.core.truth_(node)){
var temp__5753__auto__ = shadow.dom.dom_node(node);
if(cljs.core.truth_(temp__5753__auto__)){
var n = temp__5753__auto__;
shadow.dom.dom_node(el).appendChild(n);

return n;
} else {
return null;
}
} else {
return null;
}
}));

(shadow.dom.append.cljs$lang$maxFixedArity = 2);

shadow.dom.destructure_node = (function shadow$dom$destructure_node(create_fn,p__49099){
var vec__49100 = p__49099;
var seq__49101 = cljs.core.seq(vec__49100);
var first__49102 = cljs.core.first(seq__49101);
var seq__49101__$1 = cljs.core.next(seq__49101);
var nn = first__49102;
var first__49102__$1 = cljs.core.first(seq__49101__$1);
var seq__49101__$2 = cljs.core.next(seq__49101__$1);
var np = first__49102__$1;
var nc = seq__49101__$2;
var node = vec__49100;
if((nn instanceof cljs.core.Keyword)){
} else {
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("invalid dom node",new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"node","node",581201198),node], null));
}

if((((np == null)) && ((nc == null)))){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(function (){var G__49105 = nn;
var G__49106 = cljs.core.PersistentArrayMap.EMPTY;
return (create_fn.cljs$core$IFn$_invoke$arity$2 ? create_fn.cljs$core$IFn$_invoke$arity$2(G__49105,G__49106) : create_fn.call(null,G__49105,G__49106));
})(),cljs.core.List.EMPTY], null);
} else {
if(cljs.core.map_QMARK_(np)){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(create_fn.cljs$core$IFn$_invoke$arity$2 ? create_fn.cljs$core$IFn$_invoke$arity$2(nn,np) : create_fn.call(null,nn,np)),nc], null);
} else {
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(function (){var G__49108 = nn;
var G__49109 = cljs.core.PersistentArrayMap.EMPTY;
return (create_fn.cljs$core$IFn$_invoke$arity$2 ? create_fn.cljs$core$IFn$_invoke$arity$2(G__49108,G__49109) : create_fn.call(null,G__49108,G__49109));
})(),cljs.core.conj.cljs$core$IFn$_invoke$arity$2(nc,np)], null);

}
}
});
shadow.dom.make_dom_node = (function shadow$dom$make_dom_node(structure){
var vec__49110 = shadow.dom.destructure_node(shadow.dom.create_dom_node,structure);
var node = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49110,(0),null);
var node_children = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49110,(1),null);
var seq__49113_50131 = cljs.core.seq(node_children);
var chunk__49114_50132 = null;
var count__49115_50133 = (0);
var i__49116_50134 = (0);
while(true){
if((i__49116_50134 < count__49115_50133)){
var child_struct_50137 = chunk__49114_50132.cljs$core$IIndexed$_nth$arity$2(null,i__49116_50134);
var children_50138 = shadow.dom.dom_node(child_struct_50137);
if(cljs.core.seq_QMARK_(children_50138)){
var seq__49158_50141 = cljs.core.seq(cljs.core.map.cljs$core$IFn$_invoke$arity$2(shadow.dom.dom_node,children_50138));
var chunk__49160_50142 = null;
var count__49161_50143 = (0);
var i__49162_50144 = (0);
while(true){
if((i__49162_50144 < count__49161_50143)){
var child_50148 = chunk__49160_50142.cljs$core$IIndexed$_nth$arity$2(null,i__49162_50144);
if(cljs.core.truth_(child_50148)){
shadow.dom.append.cljs$core$IFn$_invoke$arity$2(node,child_50148);


var G__50153 = seq__49158_50141;
var G__50154 = chunk__49160_50142;
var G__50155 = count__49161_50143;
var G__50156 = (i__49162_50144 + (1));
seq__49158_50141 = G__50153;
chunk__49160_50142 = G__50154;
count__49161_50143 = G__50155;
i__49162_50144 = G__50156;
continue;
} else {
var G__50157 = seq__49158_50141;
var G__50158 = chunk__49160_50142;
var G__50159 = count__49161_50143;
var G__50160 = (i__49162_50144 + (1));
seq__49158_50141 = G__50157;
chunk__49160_50142 = G__50158;
count__49161_50143 = G__50159;
i__49162_50144 = G__50160;
continue;
}
} else {
var temp__5753__auto___50161 = cljs.core.seq(seq__49158_50141);
if(temp__5753__auto___50161){
var seq__49158_50162__$1 = temp__5753__auto___50161;
if(cljs.core.chunked_seq_QMARK_(seq__49158_50162__$1)){
var c__4679__auto___50167 = cljs.core.chunk_first(seq__49158_50162__$1);
var G__50168 = cljs.core.chunk_rest(seq__49158_50162__$1);
var G__50169 = c__4679__auto___50167;
var G__50170 = cljs.core.count(c__4679__auto___50167);
var G__50171 = (0);
seq__49158_50141 = G__50168;
chunk__49160_50142 = G__50169;
count__49161_50143 = G__50170;
i__49162_50144 = G__50171;
continue;
} else {
var child_50173 = cljs.core.first(seq__49158_50162__$1);
if(cljs.core.truth_(child_50173)){
shadow.dom.append.cljs$core$IFn$_invoke$arity$2(node,child_50173);


var G__50175 = cljs.core.next(seq__49158_50162__$1);
var G__50176 = null;
var G__50177 = (0);
var G__50178 = (0);
seq__49158_50141 = G__50175;
chunk__49160_50142 = G__50176;
count__49161_50143 = G__50177;
i__49162_50144 = G__50178;
continue;
} else {
var G__50179 = cljs.core.next(seq__49158_50162__$1);
var G__50180 = null;
var G__50181 = (0);
var G__50182 = (0);
seq__49158_50141 = G__50179;
chunk__49160_50142 = G__50180;
count__49161_50143 = G__50181;
i__49162_50144 = G__50182;
continue;
}
}
} else {
}
}
break;
}
} else {
shadow.dom.append.cljs$core$IFn$_invoke$arity$2(node,children_50138);
}


var G__50187 = seq__49113_50131;
var G__50188 = chunk__49114_50132;
var G__50189 = count__49115_50133;
var G__50190 = (i__49116_50134 + (1));
seq__49113_50131 = G__50187;
chunk__49114_50132 = G__50188;
count__49115_50133 = G__50189;
i__49116_50134 = G__50190;
continue;
} else {
var temp__5753__auto___50192 = cljs.core.seq(seq__49113_50131);
if(temp__5753__auto___50192){
var seq__49113_50193__$1 = temp__5753__auto___50192;
if(cljs.core.chunked_seq_QMARK_(seq__49113_50193__$1)){
var c__4679__auto___50194 = cljs.core.chunk_first(seq__49113_50193__$1);
var G__50195 = cljs.core.chunk_rest(seq__49113_50193__$1);
var G__50196 = c__4679__auto___50194;
var G__50197 = cljs.core.count(c__4679__auto___50194);
var G__50198 = (0);
seq__49113_50131 = G__50195;
chunk__49114_50132 = G__50196;
count__49115_50133 = G__50197;
i__49116_50134 = G__50198;
continue;
} else {
var child_struct_50199 = cljs.core.first(seq__49113_50193__$1);
var children_50200 = shadow.dom.dom_node(child_struct_50199);
if(cljs.core.seq_QMARK_(children_50200)){
var seq__49179_50201 = cljs.core.seq(cljs.core.map.cljs$core$IFn$_invoke$arity$2(shadow.dom.dom_node,children_50200));
var chunk__49181_50202 = null;
var count__49182_50203 = (0);
var i__49183_50204 = (0);
while(true){
if((i__49183_50204 < count__49182_50203)){
var child_50205 = chunk__49181_50202.cljs$core$IIndexed$_nth$arity$2(null,i__49183_50204);
if(cljs.core.truth_(child_50205)){
shadow.dom.append.cljs$core$IFn$_invoke$arity$2(node,child_50205);


var G__50208 = seq__49179_50201;
var G__50209 = chunk__49181_50202;
var G__50210 = count__49182_50203;
var G__50211 = (i__49183_50204 + (1));
seq__49179_50201 = G__50208;
chunk__49181_50202 = G__50209;
count__49182_50203 = G__50210;
i__49183_50204 = G__50211;
continue;
} else {
var G__50212 = seq__49179_50201;
var G__50213 = chunk__49181_50202;
var G__50214 = count__49182_50203;
var G__50215 = (i__49183_50204 + (1));
seq__49179_50201 = G__50212;
chunk__49181_50202 = G__50213;
count__49182_50203 = G__50214;
i__49183_50204 = G__50215;
continue;
}
} else {
var temp__5753__auto___50216__$1 = cljs.core.seq(seq__49179_50201);
if(temp__5753__auto___50216__$1){
var seq__49179_50218__$1 = temp__5753__auto___50216__$1;
if(cljs.core.chunked_seq_QMARK_(seq__49179_50218__$1)){
var c__4679__auto___50219 = cljs.core.chunk_first(seq__49179_50218__$1);
var G__50221 = cljs.core.chunk_rest(seq__49179_50218__$1);
var G__50222 = c__4679__auto___50219;
var G__50223 = cljs.core.count(c__4679__auto___50219);
var G__50224 = (0);
seq__49179_50201 = G__50221;
chunk__49181_50202 = G__50222;
count__49182_50203 = G__50223;
i__49183_50204 = G__50224;
continue;
} else {
var child_50225 = cljs.core.first(seq__49179_50218__$1);
if(cljs.core.truth_(child_50225)){
shadow.dom.append.cljs$core$IFn$_invoke$arity$2(node,child_50225);


var G__50226 = cljs.core.next(seq__49179_50218__$1);
var G__50227 = null;
var G__50228 = (0);
var G__50229 = (0);
seq__49179_50201 = G__50226;
chunk__49181_50202 = G__50227;
count__49182_50203 = G__50228;
i__49183_50204 = G__50229;
continue;
} else {
var G__50230 = cljs.core.next(seq__49179_50218__$1);
var G__50231 = null;
var G__50232 = (0);
var G__50233 = (0);
seq__49179_50201 = G__50230;
chunk__49181_50202 = G__50231;
count__49182_50203 = G__50232;
i__49183_50204 = G__50233;
continue;
}
}
} else {
}
}
break;
}
} else {
shadow.dom.append.cljs$core$IFn$_invoke$arity$2(node,children_50200);
}


var G__50234 = cljs.core.next(seq__49113_50193__$1);
var G__50235 = null;
var G__50236 = (0);
var G__50237 = (0);
seq__49113_50131 = G__50234;
chunk__49114_50132 = G__50235;
count__49115_50133 = G__50236;
i__49116_50134 = G__50237;
continue;
}
} else {
}
}
break;
}

return node;
});
(cljs.core.Keyword.prototype.shadow$dom$IElement$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.Keyword.prototype.shadow$dom$IElement$_to_dom$arity$1 = (function (this$){
var this$__$1 = this;
return shadow.dom.make_dom_node(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [this$__$1], null));
}));

(cljs.core.PersistentVector.prototype.shadow$dom$IElement$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.PersistentVector.prototype.shadow$dom$IElement$_to_dom$arity$1 = (function (this$){
var this$__$1 = this;
return shadow.dom.make_dom_node(this$__$1);
}));

(cljs.core.LazySeq.prototype.shadow$dom$IElement$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.LazySeq.prototype.shadow$dom$IElement$_to_dom$arity$1 = (function (this$){
var this$__$1 = this;
return cljs.core.map.cljs$core$IFn$_invoke$arity$2(shadow.dom._to_dom,this$__$1);
}));
if(cljs.core.truth_(((typeof HTMLElement) != 'undefined'))){
(HTMLElement.prototype.shadow$dom$IElement$ = cljs.core.PROTOCOL_SENTINEL);

(HTMLElement.prototype.shadow$dom$IElement$_to_dom$arity$1 = (function (this$){
var this$__$1 = this;
return this$__$1;
}));
} else {
}
if(cljs.core.truth_(((typeof DocumentFragment) != 'undefined'))){
(DocumentFragment.prototype.shadow$dom$IElement$ = cljs.core.PROTOCOL_SENTINEL);

(DocumentFragment.prototype.shadow$dom$IElement$_to_dom$arity$1 = (function (this$){
var this$__$1 = this;
return this$__$1;
}));
} else {
}
/**
 * clear node children
 */
shadow.dom.reset = (function shadow$dom$reset(node){
return goog.dom.removeChildren(shadow.dom.dom_node(node));
});
shadow.dom.remove = (function shadow$dom$remove(node){
if((((!((node == null))))?(((((node.cljs$lang$protocol_mask$partition0$ & (8388608))) || ((cljs.core.PROTOCOL_SENTINEL === node.cljs$core$ISeqable$))))?true:false):false)){
var seq__49218 = cljs.core.seq(node);
var chunk__49219 = null;
var count__49220 = (0);
var i__49221 = (0);
while(true){
if((i__49221 < count__49220)){
var n = chunk__49219.cljs$core$IIndexed$_nth$arity$2(null,i__49221);
(shadow.dom.remove.cljs$core$IFn$_invoke$arity$1 ? shadow.dom.remove.cljs$core$IFn$_invoke$arity$1(n) : shadow.dom.remove.call(null,n));


var G__50254 = seq__49218;
var G__50255 = chunk__49219;
var G__50256 = count__49220;
var G__50257 = (i__49221 + (1));
seq__49218 = G__50254;
chunk__49219 = G__50255;
count__49220 = G__50256;
i__49221 = G__50257;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__49218);
if(temp__5753__auto__){
var seq__49218__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__49218__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__49218__$1);
var G__50258 = cljs.core.chunk_rest(seq__49218__$1);
var G__50259 = c__4679__auto__;
var G__50260 = cljs.core.count(c__4679__auto__);
var G__50261 = (0);
seq__49218 = G__50258;
chunk__49219 = G__50259;
count__49220 = G__50260;
i__49221 = G__50261;
continue;
} else {
var n = cljs.core.first(seq__49218__$1);
(shadow.dom.remove.cljs$core$IFn$_invoke$arity$1 ? shadow.dom.remove.cljs$core$IFn$_invoke$arity$1(n) : shadow.dom.remove.call(null,n));


var G__50262 = cljs.core.next(seq__49218__$1);
var G__50263 = null;
var G__50264 = (0);
var G__50265 = (0);
seq__49218 = G__50262;
chunk__49219 = G__50263;
count__49220 = G__50264;
i__49221 = G__50265;
continue;
}
} else {
return null;
}
}
break;
}
} else {
return goog.dom.removeNode(node);
}
});
shadow.dom.replace_node = (function shadow$dom$replace_node(old,new$){
return goog.dom.replaceNode(shadow.dom.dom_node(new$),shadow.dom.dom_node(old));
});
shadow.dom.text = (function shadow$dom$text(var_args){
var G__49239 = arguments.length;
switch (G__49239) {
case 2:
return shadow.dom.text.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 1:
return shadow.dom.text.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.dom.text.cljs$core$IFn$_invoke$arity$2 = (function (el,new_text){
return (shadow.dom.dom_node(el).innerText = new_text);
}));

(shadow.dom.text.cljs$core$IFn$_invoke$arity$1 = (function (el){
return shadow.dom.dom_node(el).innerText;
}));

(shadow.dom.text.cljs$lang$maxFixedArity = 2);

shadow.dom.check = (function shadow$dom$check(var_args){
var G__49250 = arguments.length;
switch (G__49250) {
case 1:
return shadow.dom.check.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return shadow.dom.check.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.dom.check.cljs$core$IFn$_invoke$arity$1 = (function (el){
return shadow.dom.check.cljs$core$IFn$_invoke$arity$2(el,true);
}));

(shadow.dom.check.cljs$core$IFn$_invoke$arity$2 = (function (el,checked){
return (shadow.dom.dom_node(el).checked = checked);
}));

(shadow.dom.check.cljs$lang$maxFixedArity = 2);

shadow.dom.checked_QMARK_ = (function shadow$dom$checked_QMARK_(el){
return shadow.dom.dom_node(el).checked;
});
shadow.dom.form_elements = (function shadow$dom$form_elements(el){
return (new shadow.dom.NativeColl(shadow.dom.dom_node(el).elements));
});
shadow.dom.children = (function shadow$dom$children(el){
return (new shadow.dom.NativeColl(shadow.dom.dom_node(el).children));
});
shadow.dom.child_nodes = (function shadow$dom$child_nodes(el){
return (new shadow.dom.NativeColl(shadow.dom.dom_node(el).childNodes));
});
shadow.dom.attr = (function shadow$dom$attr(var_args){
var G__49270 = arguments.length;
switch (G__49270) {
case 2:
return shadow.dom.attr.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return shadow.dom.attr.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.dom.attr.cljs$core$IFn$_invoke$arity$2 = (function (el,key){
return shadow.dom.dom_node(el).getAttribute(cljs.core.name(key));
}));

(shadow.dom.attr.cljs$core$IFn$_invoke$arity$3 = (function (el,key,default$){
var or__4253__auto__ = shadow.dom.dom_node(el).getAttribute(cljs.core.name(key));
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
return default$;
}
}));

(shadow.dom.attr.cljs$lang$maxFixedArity = 3);

shadow.dom.del_attr = (function shadow$dom$del_attr(el,key){
return shadow.dom.dom_node(el).removeAttribute(cljs.core.name(key));
});
shadow.dom.data = (function shadow$dom$data(el,key){
return shadow.dom.dom_node(el).getAttribute(["data-",cljs.core.name(key)].join(''));
});
shadow.dom.set_data = (function shadow$dom$set_data(el,key,value){
return shadow.dom.dom_node(el).setAttribute(["data-",cljs.core.name(key)].join(''),cljs.core.str.cljs$core$IFn$_invoke$arity$1(value));
});
shadow.dom.set_html = (function shadow$dom$set_html(node,text){
return (shadow.dom.dom_node(node).innerHTML = text);
});
shadow.dom.get_html = (function shadow$dom$get_html(node){
return shadow.dom.dom_node(node).innerHTML;
});
shadow.dom.fragment = (function shadow$dom$fragment(var_args){
var args__4870__auto__ = [];
var len__4864__auto___50277 = arguments.length;
var i__4865__auto___50278 = (0);
while(true){
if((i__4865__auto___50278 < len__4864__auto___50277)){
args__4870__auto__.push((arguments[i__4865__auto___50278]));

var G__50279 = (i__4865__auto___50278 + (1));
i__4865__auto___50278 = G__50279;
continue;
} else {
}
break;
}

var argseq__4871__auto__ = ((((0) < args__4870__auto__.length))?(new cljs.core.IndexedSeq(args__4870__auto__.slice((0)),(0),null)):null);
return shadow.dom.fragment.cljs$core$IFn$_invoke$arity$variadic(argseq__4871__auto__);
});

(shadow.dom.fragment.cljs$core$IFn$_invoke$arity$variadic = (function (nodes){
var fragment = document.createDocumentFragment();
var seq__49294_50280 = cljs.core.seq(nodes);
var chunk__49295_50281 = null;
var count__49296_50282 = (0);
var i__49297_50283 = (0);
while(true){
if((i__49297_50283 < count__49296_50282)){
var node_50284 = chunk__49295_50281.cljs$core$IIndexed$_nth$arity$2(null,i__49297_50283);
fragment.appendChild(shadow.dom._to_dom(node_50284));


var G__50285 = seq__49294_50280;
var G__50286 = chunk__49295_50281;
var G__50287 = count__49296_50282;
var G__50288 = (i__49297_50283 + (1));
seq__49294_50280 = G__50285;
chunk__49295_50281 = G__50286;
count__49296_50282 = G__50287;
i__49297_50283 = G__50288;
continue;
} else {
var temp__5753__auto___50289 = cljs.core.seq(seq__49294_50280);
if(temp__5753__auto___50289){
var seq__49294_50290__$1 = temp__5753__auto___50289;
if(cljs.core.chunked_seq_QMARK_(seq__49294_50290__$1)){
var c__4679__auto___50291 = cljs.core.chunk_first(seq__49294_50290__$1);
var G__50292 = cljs.core.chunk_rest(seq__49294_50290__$1);
var G__50293 = c__4679__auto___50291;
var G__50294 = cljs.core.count(c__4679__auto___50291);
var G__50295 = (0);
seq__49294_50280 = G__50292;
chunk__49295_50281 = G__50293;
count__49296_50282 = G__50294;
i__49297_50283 = G__50295;
continue;
} else {
var node_50296 = cljs.core.first(seq__49294_50290__$1);
fragment.appendChild(shadow.dom._to_dom(node_50296));


var G__50297 = cljs.core.next(seq__49294_50290__$1);
var G__50298 = null;
var G__50299 = (0);
var G__50300 = (0);
seq__49294_50280 = G__50297;
chunk__49295_50281 = G__50298;
count__49296_50282 = G__50299;
i__49297_50283 = G__50300;
continue;
}
} else {
}
}
break;
}

return (new shadow.dom.NativeColl(fragment));
}));

(shadow.dom.fragment.cljs$lang$maxFixedArity = (0));

/** @this {Function} */
(shadow.dom.fragment.cljs$lang$applyTo = (function (seq49288){
var self__4852__auto__ = this;
return self__4852__auto__.cljs$core$IFn$_invoke$arity$variadic(cljs.core.seq(seq49288));
}));

/**
 * given a html string, eval all <script> tags and return the html without the scripts
 * don't do this for everything, only content you trust.
 */
shadow.dom.eval_scripts = (function shadow$dom$eval_scripts(s){
var scripts = cljs.core.re_seq(/<script[^>]*?>(.+?)<\/script>/,s);
var seq__49315_50301 = cljs.core.seq(scripts);
var chunk__49316_50302 = null;
var count__49317_50303 = (0);
var i__49318_50304 = (0);
while(true){
if((i__49318_50304 < count__49317_50303)){
var vec__49335_50305 = chunk__49316_50302.cljs$core$IIndexed$_nth$arity$2(null,i__49318_50304);
var script_tag_50306 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49335_50305,(0),null);
var script_body_50307 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49335_50305,(1),null);
eval(script_body_50307);


var G__50308 = seq__49315_50301;
var G__50309 = chunk__49316_50302;
var G__50310 = count__49317_50303;
var G__50311 = (i__49318_50304 + (1));
seq__49315_50301 = G__50308;
chunk__49316_50302 = G__50309;
count__49317_50303 = G__50310;
i__49318_50304 = G__50311;
continue;
} else {
var temp__5753__auto___50316 = cljs.core.seq(seq__49315_50301);
if(temp__5753__auto___50316){
var seq__49315_50317__$1 = temp__5753__auto___50316;
if(cljs.core.chunked_seq_QMARK_(seq__49315_50317__$1)){
var c__4679__auto___50318 = cljs.core.chunk_first(seq__49315_50317__$1);
var G__50319 = cljs.core.chunk_rest(seq__49315_50317__$1);
var G__50320 = c__4679__auto___50318;
var G__50321 = cljs.core.count(c__4679__auto___50318);
var G__50322 = (0);
seq__49315_50301 = G__50319;
chunk__49316_50302 = G__50320;
count__49317_50303 = G__50321;
i__49318_50304 = G__50322;
continue;
} else {
var vec__49351_50323 = cljs.core.first(seq__49315_50317__$1);
var script_tag_50324 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49351_50323,(0),null);
var script_body_50325 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49351_50323,(1),null);
eval(script_body_50325);


var G__50327 = cljs.core.next(seq__49315_50317__$1);
var G__50328 = null;
var G__50329 = (0);
var G__50330 = (0);
seq__49315_50301 = G__50327;
chunk__49316_50302 = G__50328;
count__49317_50303 = G__50329;
i__49318_50304 = G__50330;
continue;
}
} else {
}
}
break;
}

return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3((function (s__$1,p__49360){
var vec__49362 = p__49360;
var script_tag = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49362,(0),null);
var script_body = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49362,(1),null);
return clojure.string.replace(s__$1,script_tag,"");
}),s,scripts);
});
shadow.dom.str__GT_fragment = (function shadow$dom$str__GT_fragment(s){
var el = document.createElement("div");
(el.innerHTML = s);

return (new shadow.dom.NativeColl(goog.dom.childrenToNode_(document,el)));
});
shadow.dom.node_name = (function shadow$dom$node_name(el){
return shadow.dom.dom_node(el).nodeName;
});
shadow.dom.ancestor_by_class = (function shadow$dom$ancestor_by_class(el,cls){
return goog.dom.getAncestorByClass(shadow.dom.dom_node(el),cls);
});
shadow.dom.ancestor_by_tag = (function shadow$dom$ancestor_by_tag(var_args){
var G__49381 = arguments.length;
switch (G__49381) {
case 2:
return shadow.dom.ancestor_by_tag.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return shadow.dom.ancestor_by_tag.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.dom.ancestor_by_tag.cljs$core$IFn$_invoke$arity$2 = (function (el,tag){
return goog.dom.getAncestorByTagNameAndClass(shadow.dom.dom_node(el),cljs.core.name(tag));
}));

(shadow.dom.ancestor_by_tag.cljs$core$IFn$_invoke$arity$3 = (function (el,tag,cls){
return goog.dom.getAncestorByTagNameAndClass(shadow.dom.dom_node(el),cljs.core.name(tag),cljs.core.name(cls));
}));

(shadow.dom.ancestor_by_tag.cljs$lang$maxFixedArity = 3);

shadow.dom.get_value = (function shadow$dom$get_value(dom){
return goog.dom.forms.getValue(shadow.dom.dom_node(dom));
});
shadow.dom.set_value = (function shadow$dom$set_value(dom,value){
return goog.dom.forms.setValue(shadow.dom.dom_node(dom),value);
});
shadow.dom.px = (function shadow$dom$px(value){
return [cljs.core.str.cljs$core$IFn$_invoke$arity$1((value | (0))),"px"].join('');
});
shadow.dom.pct = (function shadow$dom$pct(value){
return [cljs.core.str.cljs$core$IFn$_invoke$arity$1(value),"%"].join('');
});
shadow.dom.remove_style_STAR_ = (function shadow$dom$remove_style_STAR_(el,style){
return el.style.removeProperty(cljs.core.name(style));
});
shadow.dom.remove_style = (function shadow$dom$remove_style(el,style){
var el__$1 = shadow.dom.dom_node(el);
return shadow.dom.remove_style_STAR_(el__$1,style);
});
shadow.dom.remove_styles = (function shadow$dom$remove_styles(el,style_keys){
var el__$1 = shadow.dom.dom_node(el);
var seq__49403 = cljs.core.seq(style_keys);
var chunk__49404 = null;
var count__49405 = (0);
var i__49406 = (0);
while(true){
if((i__49406 < count__49405)){
var it = chunk__49404.cljs$core$IIndexed$_nth$arity$2(null,i__49406);
shadow.dom.remove_style_STAR_(el__$1,it);


var G__50353 = seq__49403;
var G__50354 = chunk__49404;
var G__50355 = count__49405;
var G__50356 = (i__49406 + (1));
seq__49403 = G__50353;
chunk__49404 = G__50354;
count__49405 = G__50355;
i__49406 = G__50356;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__49403);
if(temp__5753__auto__){
var seq__49403__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__49403__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__49403__$1);
var G__50357 = cljs.core.chunk_rest(seq__49403__$1);
var G__50358 = c__4679__auto__;
var G__50359 = cljs.core.count(c__4679__auto__);
var G__50360 = (0);
seq__49403 = G__50357;
chunk__49404 = G__50358;
count__49405 = G__50359;
i__49406 = G__50360;
continue;
} else {
var it = cljs.core.first(seq__49403__$1);
shadow.dom.remove_style_STAR_(el__$1,it);


var G__50361 = cljs.core.next(seq__49403__$1);
var G__50362 = null;
var G__50363 = (0);
var G__50364 = (0);
seq__49403 = G__50361;
chunk__49404 = G__50362;
count__49405 = G__50363;
i__49406 = G__50364;
continue;
}
} else {
return null;
}
}
break;
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
 * @implements {cljs.core.IWithMeta}
 * @implements {cljs.core.IAssociative}
 * @implements {cljs.core.IMap}
 * @implements {cljs.core.ILookup}
*/
shadow.dom.Coordinate = (function (x,y,__meta,__extmap,__hash){
this.x = x;
this.y = y;
this.__meta = __meta;
this.__extmap = __extmap;
this.__hash = __hash;
this.cljs$lang$protocol_mask$partition0$ = 2230716170;
this.cljs$lang$protocol_mask$partition1$ = 139264;
});
(shadow.dom.Coordinate.prototype.cljs$core$ILookup$_lookup$arity$2 = (function (this__4502__auto__,k__4503__auto__){
var self__ = this;
var this__4502__auto____$1 = this;
return this__4502__auto____$1.cljs$core$ILookup$_lookup$arity$3(null,k__4503__auto__,null);
}));

(shadow.dom.Coordinate.prototype.cljs$core$ILookup$_lookup$arity$3 = (function (this__4504__auto__,k49415,else__4505__auto__){
var self__ = this;
var this__4504__auto____$1 = this;
var G__49419 = k49415;
var G__49419__$1 = (((G__49419 instanceof cljs.core.Keyword))?G__49419.fqn:null);
switch (G__49419__$1) {
case "x":
return self__.x;

break;
case "y":
return self__.y;

break;
default:
return cljs.core.get.cljs$core$IFn$_invoke$arity$3(self__.__extmap,k49415,else__4505__auto__);

}
}));

(shadow.dom.Coordinate.prototype.cljs$core$IKVReduce$_kv_reduce$arity$3 = (function (this__4522__auto__,f__4523__auto__,init__4524__auto__){
var self__ = this;
var this__4522__auto____$1 = this;
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3((function (ret__4525__auto__,p__49426){
var vec__49428 = p__49426;
var k__4526__auto__ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49428,(0),null);
var v__4527__auto__ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49428,(1),null);
return (f__4523__auto__.cljs$core$IFn$_invoke$arity$3 ? f__4523__auto__.cljs$core$IFn$_invoke$arity$3(ret__4525__auto__,k__4526__auto__,v__4527__auto__) : f__4523__auto__.call(null,ret__4525__auto__,k__4526__auto__,v__4527__auto__));
}),init__4524__auto__,this__4522__auto____$1);
}));

(shadow.dom.Coordinate.prototype.cljs$core$IPrintWithWriter$_pr_writer$arity$3 = (function (this__4517__auto__,writer__4518__auto__,opts__4519__auto__){
var self__ = this;
var this__4517__auto____$1 = this;
var pr_pair__4520__auto__ = (function (keyval__4521__auto__){
return cljs.core.pr_sequential_writer(writer__4518__auto__,cljs.core.pr_writer,""," ","",opts__4519__auto__,keyval__4521__auto__);
});
return cljs.core.pr_sequential_writer(writer__4518__auto__,pr_pair__4520__auto__,"#shadow.dom.Coordinate{",", ","}",opts__4519__auto__,cljs.core.concat.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"x","x",2099068185),self__.x],null)),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"y","y",-1757859776),self__.y],null))], null),self__.__extmap));
}));

(shadow.dom.Coordinate.prototype.cljs$core$IIterable$_iterator$arity$1 = (function (G__49414){
var self__ = this;
var G__49414__$1 = this;
return (new cljs.core.RecordIter((0),G__49414__$1,2,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"x","x",2099068185),new cljs.core.Keyword(null,"y","y",-1757859776)], null),(cljs.core.truth_(self__.__extmap)?cljs.core._iterator(self__.__extmap):cljs.core.nil_iter())));
}));

(shadow.dom.Coordinate.prototype.cljs$core$IMeta$_meta$arity$1 = (function (this__4500__auto__){
var self__ = this;
var this__4500__auto____$1 = this;
return self__.__meta;
}));

(shadow.dom.Coordinate.prototype.cljs$core$ICloneable$_clone$arity$1 = (function (this__4497__auto__){
var self__ = this;
var this__4497__auto____$1 = this;
return (new shadow.dom.Coordinate(self__.x,self__.y,self__.__meta,self__.__extmap,self__.__hash));
}));

(shadow.dom.Coordinate.prototype.cljs$core$ICounted$_count$arity$1 = (function (this__4506__auto__){
var self__ = this;
var this__4506__auto____$1 = this;
return (2 + cljs.core.count(self__.__extmap));
}));

(shadow.dom.Coordinate.prototype.cljs$core$IHash$_hash$arity$1 = (function (this__4498__auto__){
var self__ = this;
var this__4498__auto____$1 = this;
var h__4360__auto__ = self__.__hash;
if((!((h__4360__auto__ == null)))){
return h__4360__auto__;
} else {
var h__4360__auto____$1 = (function (coll__4499__auto__){
return (145542109 ^ cljs.core.hash_unordered_coll(coll__4499__auto__));
})(this__4498__auto____$1);
(self__.__hash = h__4360__auto____$1);

return h__4360__auto____$1;
}
}));

(shadow.dom.Coordinate.prototype.cljs$core$IEquiv$_equiv$arity$2 = (function (this49416,other49417){
var self__ = this;
var this49416__$1 = this;
return (((!((other49417 == null)))) && ((((this49416__$1.constructor === other49417.constructor)) && (((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this49416__$1.x,other49417.x)) && (((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this49416__$1.y,other49417.y)) && (cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this49416__$1.__extmap,other49417.__extmap)))))))));
}));

(shadow.dom.Coordinate.prototype.cljs$core$IMap$_dissoc$arity$2 = (function (this__4512__auto__,k__4513__auto__){
var self__ = this;
var this__4512__auto____$1 = this;
if(cljs.core.contains_QMARK_(new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"y","y",-1757859776),null,new cljs.core.Keyword(null,"x","x",2099068185),null], null), null),k__4513__auto__)){
return cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(cljs.core._with_meta(cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,this__4512__auto____$1),self__.__meta),k__4513__auto__);
} else {
return (new shadow.dom.Coordinate(self__.x,self__.y,self__.__meta,cljs.core.not_empty(cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(self__.__extmap,k__4513__auto__)),null));
}
}));

(shadow.dom.Coordinate.prototype.cljs$core$IAssociative$_contains_key_QMARK_$arity$2 = (function (this__4509__auto__,k49415){
var self__ = this;
var this__4509__auto____$1 = this;
var G__49457 = k49415;
var G__49457__$1 = (((G__49457 instanceof cljs.core.Keyword))?G__49457.fqn:null);
switch (G__49457__$1) {
case "x":
case "y":
return true;

break;
default:
return cljs.core.contains_QMARK_(self__.__extmap,k49415);

}
}));

(shadow.dom.Coordinate.prototype.cljs$core$IAssociative$_assoc$arity$3 = (function (this__4510__auto__,k__4511__auto__,G__49414){
var self__ = this;
var this__4510__auto____$1 = this;
var pred__49459 = cljs.core.keyword_identical_QMARK_;
var expr__49460 = k__4511__auto__;
if(cljs.core.truth_((pred__49459.cljs$core$IFn$_invoke$arity$2 ? pred__49459.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"x","x",2099068185),expr__49460) : pred__49459.call(null,new cljs.core.Keyword(null,"x","x",2099068185),expr__49460)))){
return (new shadow.dom.Coordinate(G__49414,self__.y,self__.__meta,self__.__extmap,null));
} else {
if(cljs.core.truth_((pred__49459.cljs$core$IFn$_invoke$arity$2 ? pred__49459.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"y","y",-1757859776),expr__49460) : pred__49459.call(null,new cljs.core.Keyword(null,"y","y",-1757859776),expr__49460)))){
return (new shadow.dom.Coordinate(self__.x,G__49414,self__.__meta,self__.__extmap,null));
} else {
return (new shadow.dom.Coordinate(self__.x,self__.y,self__.__meta,cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(self__.__extmap,k__4511__auto__,G__49414),null));
}
}
}));

(shadow.dom.Coordinate.prototype.cljs$core$ISeqable$_seq$arity$1 = (function (this__4515__auto__){
var self__ = this;
var this__4515__auto____$1 = this;
return cljs.core.seq(cljs.core.concat.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(new cljs.core.MapEntry(new cljs.core.Keyword(null,"x","x",2099068185),self__.x,null)),(new cljs.core.MapEntry(new cljs.core.Keyword(null,"y","y",-1757859776),self__.y,null))], null),self__.__extmap));
}));

(shadow.dom.Coordinate.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (this__4501__auto__,G__49414){
var self__ = this;
var this__4501__auto____$1 = this;
return (new shadow.dom.Coordinate(self__.x,self__.y,G__49414,self__.__extmap,self__.__hash));
}));

(shadow.dom.Coordinate.prototype.cljs$core$ICollection$_conj$arity$2 = (function (this__4507__auto__,entry__4508__auto__){
var self__ = this;
var this__4507__auto____$1 = this;
if(cljs.core.vector_QMARK_(entry__4508__auto__)){
return this__4507__auto____$1.cljs$core$IAssociative$_assoc$arity$3(null,cljs.core._nth(entry__4508__auto__,(0)),cljs.core._nth(entry__4508__auto__,(1)));
} else {
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3(cljs.core._conj,this__4507__auto____$1,entry__4508__auto__);
}
}));

(shadow.dom.Coordinate.getBasis = (function (){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"x","x",-555367584,null),new cljs.core.Symbol(null,"y","y",-117328249,null)], null);
}));

(shadow.dom.Coordinate.cljs$lang$type = true);

(shadow.dom.Coordinate.cljs$lang$ctorPrSeq = (function (this__4546__auto__){
return (new cljs.core.List(null,"shadow.dom/Coordinate",null,(1),null));
}));

(shadow.dom.Coordinate.cljs$lang$ctorPrWriter = (function (this__4546__auto__,writer__4547__auto__){
return cljs.core._write(writer__4547__auto__,"shadow.dom/Coordinate");
}));

/**
 * Positional factory function for shadow.dom/Coordinate.
 */
shadow.dom.__GT_Coordinate = (function shadow$dom$__GT_Coordinate(x,y){
return (new shadow.dom.Coordinate(x,y,null,null,null));
});

/**
 * Factory function for shadow.dom/Coordinate, taking a map of keywords to field values.
 */
shadow.dom.map__GT_Coordinate = (function shadow$dom$map__GT_Coordinate(G__49418){
var extmap__4542__auto__ = (function (){var G__49479 = cljs.core.dissoc.cljs$core$IFn$_invoke$arity$variadic(G__49418,new cljs.core.Keyword(null,"x","x",2099068185),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"y","y",-1757859776)], 0));
if(cljs.core.record_QMARK_(G__49418)){
return cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,G__49479);
} else {
return G__49479;
}
})();
return (new shadow.dom.Coordinate(new cljs.core.Keyword(null,"x","x",2099068185).cljs$core$IFn$_invoke$arity$1(G__49418),new cljs.core.Keyword(null,"y","y",-1757859776).cljs$core$IFn$_invoke$arity$1(G__49418),null,cljs.core.not_empty(extmap__4542__auto__),null));
});

shadow.dom.get_position = (function shadow$dom$get_position(el){
var pos = goog.style.getPosition(shadow.dom.dom_node(el));
return shadow.dom.__GT_Coordinate(pos.x,pos.y);
});
shadow.dom.get_client_position = (function shadow$dom$get_client_position(el){
var pos = goog.style.getClientPosition(shadow.dom.dom_node(el));
return shadow.dom.__GT_Coordinate(pos.x,pos.y);
});
shadow.dom.get_page_offset = (function shadow$dom$get_page_offset(el){
var pos = goog.style.getPageOffset(shadow.dom.dom_node(el));
return shadow.dom.__GT_Coordinate(pos.x,pos.y);
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
 * @implements {cljs.core.IWithMeta}
 * @implements {cljs.core.IAssociative}
 * @implements {cljs.core.IMap}
 * @implements {cljs.core.ILookup}
*/
shadow.dom.Size = (function (w,h,__meta,__extmap,__hash){
this.w = w;
this.h = h;
this.__meta = __meta;
this.__extmap = __extmap;
this.__hash = __hash;
this.cljs$lang$protocol_mask$partition0$ = 2230716170;
this.cljs$lang$protocol_mask$partition1$ = 139264;
});
(shadow.dom.Size.prototype.cljs$core$ILookup$_lookup$arity$2 = (function (this__4502__auto__,k__4503__auto__){
var self__ = this;
var this__4502__auto____$1 = this;
return this__4502__auto____$1.cljs$core$ILookup$_lookup$arity$3(null,k__4503__auto__,null);
}));

(shadow.dom.Size.prototype.cljs$core$ILookup$_lookup$arity$3 = (function (this__4504__auto__,k49490,else__4505__auto__){
var self__ = this;
var this__4504__auto____$1 = this;
var G__49498 = k49490;
var G__49498__$1 = (((G__49498 instanceof cljs.core.Keyword))?G__49498.fqn:null);
switch (G__49498__$1) {
case "w":
return self__.w;

break;
case "h":
return self__.h;

break;
default:
return cljs.core.get.cljs$core$IFn$_invoke$arity$3(self__.__extmap,k49490,else__4505__auto__);

}
}));

(shadow.dom.Size.prototype.cljs$core$IKVReduce$_kv_reduce$arity$3 = (function (this__4522__auto__,f__4523__auto__,init__4524__auto__){
var self__ = this;
var this__4522__auto____$1 = this;
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3((function (ret__4525__auto__,p__49499){
var vec__49500 = p__49499;
var k__4526__auto__ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49500,(0),null);
var v__4527__auto__ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49500,(1),null);
return (f__4523__auto__.cljs$core$IFn$_invoke$arity$3 ? f__4523__auto__.cljs$core$IFn$_invoke$arity$3(ret__4525__auto__,k__4526__auto__,v__4527__auto__) : f__4523__auto__.call(null,ret__4525__auto__,k__4526__auto__,v__4527__auto__));
}),init__4524__auto__,this__4522__auto____$1);
}));

(shadow.dom.Size.prototype.cljs$core$IPrintWithWriter$_pr_writer$arity$3 = (function (this__4517__auto__,writer__4518__auto__,opts__4519__auto__){
var self__ = this;
var this__4517__auto____$1 = this;
var pr_pair__4520__auto__ = (function (keyval__4521__auto__){
return cljs.core.pr_sequential_writer(writer__4518__auto__,cljs.core.pr_writer,""," ","",opts__4519__auto__,keyval__4521__auto__);
});
return cljs.core.pr_sequential_writer(writer__4518__auto__,pr_pair__4520__auto__,"#shadow.dom.Size{",", ","}",opts__4519__auto__,cljs.core.concat.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"w","w",354169001),self__.w],null)),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"h","h",1109658740),self__.h],null))], null),self__.__extmap));
}));

(shadow.dom.Size.prototype.cljs$core$IIterable$_iterator$arity$1 = (function (G__49489){
var self__ = this;
var G__49489__$1 = this;
return (new cljs.core.RecordIter((0),G__49489__$1,2,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"w","w",354169001),new cljs.core.Keyword(null,"h","h",1109658740)], null),(cljs.core.truth_(self__.__extmap)?cljs.core._iterator(self__.__extmap):cljs.core.nil_iter())));
}));

(shadow.dom.Size.prototype.cljs$core$IMeta$_meta$arity$1 = (function (this__4500__auto__){
var self__ = this;
var this__4500__auto____$1 = this;
return self__.__meta;
}));

(shadow.dom.Size.prototype.cljs$core$ICloneable$_clone$arity$1 = (function (this__4497__auto__){
var self__ = this;
var this__4497__auto____$1 = this;
return (new shadow.dom.Size(self__.w,self__.h,self__.__meta,self__.__extmap,self__.__hash));
}));

(shadow.dom.Size.prototype.cljs$core$ICounted$_count$arity$1 = (function (this__4506__auto__){
var self__ = this;
var this__4506__auto____$1 = this;
return (2 + cljs.core.count(self__.__extmap));
}));

(shadow.dom.Size.prototype.cljs$core$IHash$_hash$arity$1 = (function (this__4498__auto__){
var self__ = this;
var this__4498__auto____$1 = this;
var h__4360__auto__ = self__.__hash;
if((!((h__4360__auto__ == null)))){
return h__4360__auto__;
} else {
var h__4360__auto____$1 = (function (coll__4499__auto__){
return (-1228019642 ^ cljs.core.hash_unordered_coll(coll__4499__auto__));
})(this__4498__auto____$1);
(self__.__hash = h__4360__auto____$1);

return h__4360__auto____$1;
}
}));

(shadow.dom.Size.prototype.cljs$core$IEquiv$_equiv$arity$2 = (function (this49491,other49492){
var self__ = this;
var this49491__$1 = this;
return (((!((other49492 == null)))) && ((((this49491__$1.constructor === other49492.constructor)) && (((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this49491__$1.w,other49492.w)) && (((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this49491__$1.h,other49492.h)) && (cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this49491__$1.__extmap,other49492.__extmap)))))))));
}));

(shadow.dom.Size.prototype.cljs$core$IMap$_dissoc$arity$2 = (function (this__4512__auto__,k__4513__auto__){
var self__ = this;
var this__4512__auto____$1 = this;
if(cljs.core.contains_QMARK_(new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"w","w",354169001),null,new cljs.core.Keyword(null,"h","h",1109658740),null], null), null),k__4513__auto__)){
return cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(cljs.core._with_meta(cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,this__4512__auto____$1),self__.__meta),k__4513__auto__);
} else {
return (new shadow.dom.Size(self__.w,self__.h,self__.__meta,cljs.core.not_empty(cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(self__.__extmap,k__4513__auto__)),null));
}
}));

(shadow.dom.Size.prototype.cljs$core$IAssociative$_contains_key_QMARK_$arity$2 = (function (this__4509__auto__,k49490){
var self__ = this;
var this__4509__auto____$1 = this;
var G__49531 = k49490;
var G__49531__$1 = (((G__49531 instanceof cljs.core.Keyword))?G__49531.fqn:null);
switch (G__49531__$1) {
case "w":
case "h":
return true;

break;
default:
return cljs.core.contains_QMARK_(self__.__extmap,k49490);

}
}));

(shadow.dom.Size.prototype.cljs$core$IAssociative$_assoc$arity$3 = (function (this__4510__auto__,k__4511__auto__,G__49489){
var self__ = this;
var this__4510__auto____$1 = this;
var pred__49535 = cljs.core.keyword_identical_QMARK_;
var expr__49536 = k__4511__auto__;
if(cljs.core.truth_((pred__49535.cljs$core$IFn$_invoke$arity$2 ? pred__49535.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"w","w",354169001),expr__49536) : pred__49535.call(null,new cljs.core.Keyword(null,"w","w",354169001),expr__49536)))){
return (new shadow.dom.Size(G__49489,self__.h,self__.__meta,self__.__extmap,null));
} else {
if(cljs.core.truth_((pred__49535.cljs$core$IFn$_invoke$arity$2 ? pred__49535.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"h","h",1109658740),expr__49536) : pred__49535.call(null,new cljs.core.Keyword(null,"h","h",1109658740),expr__49536)))){
return (new shadow.dom.Size(self__.w,G__49489,self__.__meta,self__.__extmap,null));
} else {
return (new shadow.dom.Size(self__.w,self__.h,self__.__meta,cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(self__.__extmap,k__4511__auto__,G__49489),null));
}
}
}));

(shadow.dom.Size.prototype.cljs$core$ISeqable$_seq$arity$1 = (function (this__4515__auto__){
var self__ = this;
var this__4515__auto____$1 = this;
return cljs.core.seq(cljs.core.concat.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(new cljs.core.MapEntry(new cljs.core.Keyword(null,"w","w",354169001),self__.w,null)),(new cljs.core.MapEntry(new cljs.core.Keyword(null,"h","h",1109658740),self__.h,null))], null),self__.__extmap));
}));

(shadow.dom.Size.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (this__4501__auto__,G__49489){
var self__ = this;
var this__4501__auto____$1 = this;
return (new shadow.dom.Size(self__.w,self__.h,G__49489,self__.__extmap,self__.__hash));
}));

(shadow.dom.Size.prototype.cljs$core$ICollection$_conj$arity$2 = (function (this__4507__auto__,entry__4508__auto__){
var self__ = this;
var this__4507__auto____$1 = this;
if(cljs.core.vector_QMARK_(entry__4508__auto__)){
return this__4507__auto____$1.cljs$core$IAssociative$_assoc$arity$3(null,cljs.core._nth(entry__4508__auto__,(0)),cljs.core._nth(entry__4508__auto__,(1)));
} else {
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3(cljs.core._conj,this__4507__auto____$1,entry__4508__auto__);
}
}));

(shadow.dom.Size.getBasis = (function (){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"w","w",1994700528,null),new cljs.core.Symbol(null,"h","h",-1544777029,null)], null);
}));

(shadow.dom.Size.cljs$lang$type = true);

(shadow.dom.Size.cljs$lang$ctorPrSeq = (function (this__4546__auto__){
return (new cljs.core.List(null,"shadow.dom/Size",null,(1),null));
}));

(shadow.dom.Size.cljs$lang$ctorPrWriter = (function (this__4546__auto__,writer__4547__auto__){
return cljs.core._write(writer__4547__auto__,"shadow.dom/Size");
}));

/**
 * Positional factory function for shadow.dom/Size.
 */
shadow.dom.__GT_Size = (function shadow$dom$__GT_Size(w,h){
return (new shadow.dom.Size(w,h,null,null,null));
});

/**
 * Factory function for shadow.dom/Size, taking a map of keywords to field values.
 */
shadow.dom.map__GT_Size = (function shadow$dom$map__GT_Size(G__49497){
var extmap__4542__auto__ = (function (){var G__49548 = cljs.core.dissoc.cljs$core$IFn$_invoke$arity$variadic(G__49497,new cljs.core.Keyword(null,"w","w",354169001),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"h","h",1109658740)], 0));
if(cljs.core.record_QMARK_(G__49497)){
return cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,G__49548);
} else {
return G__49548;
}
})();
return (new shadow.dom.Size(new cljs.core.Keyword(null,"w","w",354169001).cljs$core$IFn$_invoke$arity$1(G__49497),new cljs.core.Keyword(null,"h","h",1109658740).cljs$core$IFn$_invoke$arity$1(G__49497),null,cljs.core.not_empty(extmap__4542__auto__),null));
});

shadow.dom.size__GT_clj = (function shadow$dom$size__GT_clj(size){
return (new shadow.dom.Size(size.width,size.height,null,null,null));
});
shadow.dom.get_size = (function shadow$dom$get_size(el){
return shadow.dom.size__GT_clj(goog.style.getSize(shadow.dom.dom_node(el)));
});
shadow.dom.get_height = (function shadow$dom$get_height(el){
return shadow.dom.get_size(el).h;
});
shadow.dom.get_viewport_size = (function shadow$dom$get_viewport_size(){
return shadow.dom.size__GT_clj(goog.dom.getViewportSize());
});
shadow.dom.first_child = (function shadow$dom$first_child(el){
return (shadow.dom.dom_node(el).children[(0)]);
});
shadow.dom.select_option_values = (function shadow$dom$select_option_values(el){
var native$ = shadow.dom.dom_node(el);
var opts = (native$["options"]);
var a__4738__auto__ = opts;
var l__4739__auto__ = a__4738__auto__.length;
var i = (0);
var ret = cljs.core.PersistentVector.EMPTY;
while(true){
if((i < l__4739__auto__)){
var G__50415 = (i + (1));
var G__50416 = cljs.core.conj.cljs$core$IFn$_invoke$arity$2(ret,(opts[i]["value"]));
i = G__50415;
ret = G__50416;
continue;
} else {
return ret;
}
break;
}
});
shadow.dom.build_url = (function shadow$dom$build_url(path,query_params){
if(cljs.core.empty_QMARK_(query_params)){
return path;
} else {
return [cljs.core.str.cljs$core$IFn$_invoke$arity$1(path),"?",clojure.string.join.cljs$core$IFn$_invoke$arity$2("&",cljs.core.map.cljs$core$IFn$_invoke$arity$2((function (p__49576){
var vec__49577 = p__49576;
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49577,(0),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49577,(1),null);
return [cljs.core.name(k),"=",cljs.core.str.cljs$core$IFn$_invoke$arity$1(encodeURIComponent(cljs.core.str.cljs$core$IFn$_invoke$arity$1(v)))].join('');
}),query_params))].join('');
}
});
shadow.dom.redirect = (function shadow$dom$redirect(var_args){
var G__49583 = arguments.length;
switch (G__49583) {
case 1:
return shadow.dom.redirect.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return shadow.dom.redirect.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.dom.redirect.cljs$core$IFn$_invoke$arity$1 = (function (path){
return shadow.dom.redirect.cljs$core$IFn$_invoke$arity$2(path,cljs.core.PersistentArrayMap.EMPTY);
}));

(shadow.dom.redirect.cljs$core$IFn$_invoke$arity$2 = (function (path,query_params){
return (document["location"]["href"] = shadow.dom.build_url(path,query_params));
}));

(shadow.dom.redirect.cljs$lang$maxFixedArity = 2);

shadow.dom.reload_BANG_ = (function shadow$dom$reload_BANG_(){
return (document.location.href = document.location.href);
});
shadow.dom.tag_name = (function shadow$dom$tag_name(el){
var dom = shadow.dom.dom_node(el);
return dom.tagName;
});
shadow.dom.insert_after = (function shadow$dom$insert_after(ref,new$){
var new_node = shadow.dom.dom_node(new$);
goog.dom.insertSiblingAfter(new_node,shadow.dom.dom_node(ref));

return new_node;
});
shadow.dom.insert_before = (function shadow$dom$insert_before(ref,new$){
var new_node = shadow.dom.dom_node(new$);
goog.dom.insertSiblingBefore(new_node,shadow.dom.dom_node(ref));

return new_node;
});
shadow.dom.insert_first = (function shadow$dom$insert_first(ref,new$){
var temp__5751__auto__ = shadow.dom.dom_node(ref).firstChild;
if(cljs.core.truth_(temp__5751__auto__)){
var child = temp__5751__auto__;
return shadow.dom.insert_before(child,new$);
} else {
return shadow.dom.append.cljs$core$IFn$_invoke$arity$2(ref,new$);
}
});
shadow.dom.index_of = (function shadow$dom$index_of(el){
var el__$1 = shadow.dom.dom_node(el);
var i = (0);
while(true){
var ps = el__$1.previousSibling;
if((ps == null)){
return i;
} else {
var G__50437 = ps;
var G__50438 = (i + (1));
el__$1 = G__50437;
i = G__50438;
continue;
}
break;
}
});
shadow.dom.get_parent = (function shadow$dom$get_parent(el){
return goog.dom.getParentElement(shadow.dom.dom_node(el));
});
shadow.dom.parents = (function shadow$dom$parents(el){
var parent = shadow.dom.get_parent(el);
if(cljs.core.truth_(parent)){
return cljs.core.cons(parent,(new cljs.core.LazySeq(null,(function (){
return (shadow.dom.parents.cljs$core$IFn$_invoke$arity$1 ? shadow.dom.parents.cljs$core$IFn$_invoke$arity$1(parent) : shadow.dom.parents.call(null,parent));
}),null,null)));
} else {
return null;
}
});
shadow.dom.matches = (function shadow$dom$matches(el,sel){
return shadow.dom.dom_node(el).matches(sel);
});
shadow.dom.get_next_sibling = (function shadow$dom$get_next_sibling(el){
return goog.dom.getNextElementSibling(shadow.dom.dom_node(el));
});
shadow.dom.get_previous_sibling = (function shadow$dom$get_previous_sibling(el){
return goog.dom.getPreviousElementSibling(shadow.dom.dom_node(el));
});
shadow.dom.xmlns = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(new cljs.core.PersistentArrayMap(null, 2, ["svg","http://www.w3.org/2000/svg","xlink","http://www.w3.org/1999/xlink"], null));
shadow.dom.create_svg_node = (function shadow$dom$create_svg_node(tag_def,props){
var vec__49609 = shadow.dom.parse_tag(tag_def);
var tag_name = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49609,(0),null);
var tag_id = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49609,(1),null);
var tag_classes = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49609,(2),null);
var el = document.createElementNS("http://www.w3.org/2000/svg",tag_name);
if(cljs.core.truth_(tag_id)){
el.setAttribute("id",tag_id);
} else {
}

if(cljs.core.truth_(tag_classes)){
el.setAttribute("class",shadow.dom.merge_class_string(new cljs.core.Keyword(null,"class","class",-2030961996).cljs$core$IFn$_invoke$arity$1(props),tag_classes));
} else {
}

var seq__49614_50444 = cljs.core.seq(props);
var chunk__49615_50445 = null;
var count__49616_50446 = (0);
var i__49617_50447 = (0);
while(true){
if((i__49617_50447 < count__49616_50446)){
var vec__49631_50448 = chunk__49615_50445.cljs$core$IIndexed$_nth$arity$2(null,i__49617_50447);
var k_50449 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49631_50448,(0),null);
var v_50450 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49631_50448,(1),null);
el.setAttributeNS((function (){var temp__5753__auto__ = cljs.core.namespace(k_50449);
if(cljs.core.truth_(temp__5753__auto__)){
var ns = temp__5753__auto__;
return cljs.core.get.cljs$core$IFn$_invoke$arity$2(cljs.core.deref(shadow.dom.xmlns),ns);
} else {
return null;
}
})(),cljs.core.name(k_50449),v_50450);


var G__50451 = seq__49614_50444;
var G__50452 = chunk__49615_50445;
var G__50453 = count__49616_50446;
var G__50454 = (i__49617_50447 + (1));
seq__49614_50444 = G__50451;
chunk__49615_50445 = G__50452;
count__49616_50446 = G__50453;
i__49617_50447 = G__50454;
continue;
} else {
var temp__5753__auto___50455 = cljs.core.seq(seq__49614_50444);
if(temp__5753__auto___50455){
var seq__49614_50457__$1 = temp__5753__auto___50455;
if(cljs.core.chunked_seq_QMARK_(seq__49614_50457__$1)){
var c__4679__auto___50458 = cljs.core.chunk_first(seq__49614_50457__$1);
var G__50460 = cljs.core.chunk_rest(seq__49614_50457__$1);
var G__50461 = c__4679__auto___50458;
var G__50462 = cljs.core.count(c__4679__auto___50458);
var G__50463 = (0);
seq__49614_50444 = G__50460;
chunk__49615_50445 = G__50461;
count__49616_50446 = G__50462;
i__49617_50447 = G__50463;
continue;
} else {
var vec__49639_50464 = cljs.core.first(seq__49614_50457__$1);
var k_50465 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49639_50464,(0),null);
var v_50466 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49639_50464,(1),null);
el.setAttributeNS((function (){var temp__5753__auto____$1 = cljs.core.namespace(k_50465);
if(cljs.core.truth_(temp__5753__auto____$1)){
var ns = temp__5753__auto____$1;
return cljs.core.get.cljs$core$IFn$_invoke$arity$2(cljs.core.deref(shadow.dom.xmlns),ns);
} else {
return null;
}
})(),cljs.core.name(k_50465),v_50466);


var G__50468 = cljs.core.next(seq__49614_50457__$1);
var G__50469 = null;
var G__50470 = (0);
var G__50471 = (0);
seq__49614_50444 = G__50468;
chunk__49615_50445 = G__50469;
count__49616_50446 = G__50470;
i__49617_50447 = G__50471;
continue;
}
} else {
}
}
break;
}

return el;
});
shadow.dom.svg_node = (function shadow$dom$svg_node(el){
if((el == null)){
return null;
} else {
if((((!((el == null))))?((((false) || ((cljs.core.PROTOCOL_SENTINEL === el.shadow$dom$SVGElement$))))?true:false):false)){
return el.shadow$dom$SVGElement$_to_svg$arity$1(null);
} else {
return el;

}
}
});
shadow.dom.make_svg_node = (function shadow$dom$make_svg_node(structure){
var vec__49649 = shadow.dom.destructure_node(shadow.dom.create_svg_node,structure);
var node = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49649,(0),null);
var node_children = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49649,(1),null);
var seq__49652_50472 = cljs.core.seq(node_children);
var chunk__49654_50473 = null;
var count__49655_50474 = (0);
var i__49656_50475 = (0);
while(true){
if((i__49656_50475 < count__49655_50474)){
var child_struct_50478 = chunk__49654_50473.cljs$core$IIndexed$_nth$arity$2(null,i__49656_50475);
if((!((child_struct_50478 == null)))){
if(typeof child_struct_50478 === 'string'){
var text_50479 = (node["textContent"]);
(node["textContent"] = [cljs.core.str.cljs$core$IFn$_invoke$arity$1(text_50479),child_struct_50478].join(''));
} else {
var children_50480 = shadow.dom.svg_node(child_struct_50478);
if(cljs.core.seq_QMARK_(children_50480)){
var seq__49679_50481 = cljs.core.seq(children_50480);
var chunk__49681_50482 = null;
var count__49682_50483 = (0);
var i__49683_50484 = (0);
while(true){
if((i__49683_50484 < count__49682_50483)){
var child_50485 = chunk__49681_50482.cljs$core$IIndexed$_nth$arity$2(null,i__49683_50484);
if(cljs.core.truth_(child_50485)){
node.appendChild(child_50485);


var G__50486 = seq__49679_50481;
var G__50487 = chunk__49681_50482;
var G__50488 = count__49682_50483;
var G__50489 = (i__49683_50484 + (1));
seq__49679_50481 = G__50486;
chunk__49681_50482 = G__50487;
count__49682_50483 = G__50488;
i__49683_50484 = G__50489;
continue;
} else {
var G__50490 = seq__49679_50481;
var G__50491 = chunk__49681_50482;
var G__50492 = count__49682_50483;
var G__50493 = (i__49683_50484 + (1));
seq__49679_50481 = G__50490;
chunk__49681_50482 = G__50491;
count__49682_50483 = G__50492;
i__49683_50484 = G__50493;
continue;
}
} else {
var temp__5753__auto___50494 = cljs.core.seq(seq__49679_50481);
if(temp__5753__auto___50494){
var seq__49679_50495__$1 = temp__5753__auto___50494;
if(cljs.core.chunked_seq_QMARK_(seq__49679_50495__$1)){
var c__4679__auto___50496 = cljs.core.chunk_first(seq__49679_50495__$1);
var G__50497 = cljs.core.chunk_rest(seq__49679_50495__$1);
var G__50498 = c__4679__auto___50496;
var G__50499 = cljs.core.count(c__4679__auto___50496);
var G__50500 = (0);
seq__49679_50481 = G__50497;
chunk__49681_50482 = G__50498;
count__49682_50483 = G__50499;
i__49683_50484 = G__50500;
continue;
} else {
var child_50501 = cljs.core.first(seq__49679_50495__$1);
if(cljs.core.truth_(child_50501)){
node.appendChild(child_50501);


var G__50502 = cljs.core.next(seq__49679_50495__$1);
var G__50503 = null;
var G__50504 = (0);
var G__50505 = (0);
seq__49679_50481 = G__50502;
chunk__49681_50482 = G__50503;
count__49682_50483 = G__50504;
i__49683_50484 = G__50505;
continue;
} else {
var G__50507 = cljs.core.next(seq__49679_50495__$1);
var G__50508 = null;
var G__50509 = (0);
var G__50510 = (0);
seq__49679_50481 = G__50507;
chunk__49681_50482 = G__50508;
count__49682_50483 = G__50509;
i__49683_50484 = G__50510;
continue;
}
}
} else {
}
}
break;
}
} else {
node.appendChild(children_50480);
}
}


var G__50511 = seq__49652_50472;
var G__50512 = chunk__49654_50473;
var G__50513 = count__49655_50474;
var G__50514 = (i__49656_50475 + (1));
seq__49652_50472 = G__50511;
chunk__49654_50473 = G__50512;
count__49655_50474 = G__50513;
i__49656_50475 = G__50514;
continue;
} else {
var G__50515 = seq__49652_50472;
var G__50516 = chunk__49654_50473;
var G__50517 = count__49655_50474;
var G__50518 = (i__49656_50475 + (1));
seq__49652_50472 = G__50515;
chunk__49654_50473 = G__50516;
count__49655_50474 = G__50517;
i__49656_50475 = G__50518;
continue;
}
} else {
var temp__5753__auto___50519 = cljs.core.seq(seq__49652_50472);
if(temp__5753__auto___50519){
var seq__49652_50520__$1 = temp__5753__auto___50519;
if(cljs.core.chunked_seq_QMARK_(seq__49652_50520__$1)){
var c__4679__auto___50521 = cljs.core.chunk_first(seq__49652_50520__$1);
var G__50522 = cljs.core.chunk_rest(seq__49652_50520__$1);
var G__50523 = c__4679__auto___50521;
var G__50524 = cljs.core.count(c__4679__auto___50521);
var G__50525 = (0);
seq__49652_50472 = G__50522;
chunk__49654_50473 = G__50523;
count__49655_50474 = G__50524;
i__49656_50475 = G__50525;
continue;
} else {
var child_struct_50526 = cljs.core.first(seq__49652_50520__$1);
if((!((child_struct_50526 == null)))){
if(typeof child_struct_50526 === 'string'){
var text_50532 = (node["textContent"]);
(node["textContent"] = [cljs.core.str.cljs$core$IFn$_invoke$arity$1(text_50532),child_struct_50526].join(''));
} else {
var children_50533 = shadow.dom.svg_node(child_struct_50526);
if(cljs.core.seq_QMARK_(children_50533)){
var seq__49705_50539 = cljs.core.seq(children_50533);
var chunk__49707_50540 = null;
var count__49708_50541 = (0);
var i__49709_50542 = (0);
while(true){
if((i__49709_50542 < count__49708_50541)){
var child_50543 = chunk__49707_50540.cljs$core$IIndexed$_nth$arity$2(null,i__49709_50542);
if(cljs.core.truth_(child_50543)){
node.appendChild(child_50543);


var G__50545 = seq__49705_50539;
var G__50546 = chunk__49707_50540;
var G__50547 = count__49708_50541;
var G__50548 = (i__49709_50542 + (1));
seq__49705_50539 = G__50545;
chunk__49707_50540 = G__50546;
count__49708_50541 = G__50547;
i__49709_50542 = G__50548;
continue;
} else {
var G__50552 = seq__49705_50539;
var G__50553 = chunk__49707_50540;
var G__50554 = count__49708_50541;
var G__50555 = (i__49709_50542 + (1));
seq__49705_50539 = G__50552;
chunk__49707_50540 = G__50553;
count__49708_50541 = G__50554;
i__49709_50542 = G__50555;
continue;
}
} else {
var temp__5753__auto___50557__$1 = cljs.core.seq(seq__49705_50539);
if(temp__5753__auto___50557__$1){
var seq__49705_50559__$1 = temp__5753__auto___50557__$1;
if(cljs.core.chunked_seq_QMARK_(seq__49705_50559__$1)){
var c__4679__auto___50560 = cljs.core.chunk_first(seq__49705_50559__$1);
var G__50562 = cljs.core.chunk_rest(seq__49705_50559__$1);
var G__50563 = c__4679__auto___50560;
var G__50564 = cljs.core.count(c__4679__auto___50560);
var G__50565 = (0);
seq__49705_50539 = G__50562;
chunk__49707_50540 = G__50563;
count__49708_50541 = G__50564;
i__49709_50542 = G__50565;
continue;
} else {
var child_50566 = cljs.core.first(seq__49705_50559__$1);
if(cljs.core.truth_(child_50566)){
node.appendChild(child_50566);


var G__50567 = cljs.core.next(seq__49705_50559__$1);
var G__50568 = null;
var G__50569 = (0);
var G__50570 = (0);
seq__49705_50539 = G__50567;
chunk__49707_50540 = G__50568;
count__49708_50541 = G__50569;
i__49709_50542 = G__50570;
continue;
} else {
var G__50571 = cljs.core.next(seq__49705_50559__$1);
var G__50572 = null;
var G__50573 = (0);
var G__50574 = (0);
seq__49705_50539 = G__50571;
chunk__49707_50540 = G__50572;
count__49708_50541 = G__50573;
i__49709_50542 = G__50574;
continue;
}
}
} else {
}
}
break;
}
} else {
node.appendChild(children_50533);
}
}


var G__50575 = cljs.core.next(seq__49652_50520__$1);
var G__50576 = null;
var G__50577 = (0);
var G__50578 = (0);
seq__49652_50472 = G__50575;
chunk__49654_50473 = G__50576;
count__49655_50474 = G__50577;
i__49656_50475 = G__50578;
continue;
} else {
var G__50579 = cljs.core.next(seq__49652_50520__$1);
var G__50580 = null;
var G__50581 = (0);
var G__50582 = (0);
seq__49652_50472 = G__50579;
chunk__49654_50473 = G__50580;
count__49655_50474 = G__50581;
i__49656_50475 = G__50582;
continue;
}
}
} else {
}
}
break;
}

return node;
});
(shadow.dom.SVGElement["string"] = true);

(shadow.dom._to_svg["string"] = (function (this$){
if((this$ instanceof cljs.core.Keyword)){
return shadow.dom.make_svg_node(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [this$], null));
} else {
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("strings cannot be in svgs",new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"this","this",-611633625),this$], null));
}
}));

(cljs.core.PersistentVector.prototype.shadow$dom$SVGElement$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.PersistentVector.prototype.shadow$dom$SVGElement$_to_svg$arity$1 = (function (this$){
var this$__$1 = this;
return shadow.dom.make_svg_node(this$__$1);
}));

(cljs.core.LazySeq.prototype.shadow$dom$SVGElement$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.LazySeq.prototype.shadow$dom$SVGElement$_to_svg$arity$1 = (function (this$){
var this$__$1 = this;
return cljs.core.map.cljs$core$IFn$_invoke$arity$2(shadow.dom._to_svg,this$__$1);
}));

(shadow.dom.SVGElement["null"] = true);

(shadow.dom._to_svg["null"] = (function (_){
return null;
}));
shadow.dom.svg = (function shadow$dom$svg(var_args){
var args__4870__auto__ = [];
var len__4864__auto___50586 = arguments.length;
var i__4865__auto___50587 = (0);
while(true){
if((i__4865__auto___50587 < len__4864__auto___50586)){
args__4870__auto__.push((arguments[i__4865__auto___50587]));

var G__50591 = (i__4865__auto___50587 + (1));
i__4865__auto___50587 = G__50591;
continue;
} else {
}
break;
}

var argseq__4871__auto__ = ((((1) < args__4870__auto__.length))?(new cljs.core.IndexedSeq(args__4870__auto__.slice((1)),(0),null)):null);
return shadow.dom.svg.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__4871__auto__);
});

(shadow.dom.svg.cljs$core$IFn$_invoke$arity$variadic = (function (attrs,children){
return shadow.dom._to_svg(cljs.core.vec(cljs.core.concat.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"svg","svg",856789142),attrs], null),children)));
}));

(shadow.dom.svg.cljs$lang$maxFixedArity = (1));

/** @this {Function} */
(shadow.dom.svg.cljs$lang$applyTo = (function (seq49736){
var G__49737 = cljs.core.first(seq49736);
var seq49736__$1 = cljs.core.next(seq49736);
var self__4851__auto__ = this;
return self__4851__auto__.cljs$core$IFn$_invoke$arity$variadic(G__49737,seq49736__$1);
}));

/**
 * returns a channel for events on el
 * transform-fn should be a (fn [e el] some-val) where some-val will be put on the chan
 * once-or-cleanup handles the removal of the event handler
 * - true: remove after one event
 * - false: never removed
 * - chan: remove on msg/close
 */
shadow.dom.event_chan = (function shadow$dom$event_chan(var_args){
var G__49759 = arguments.length;
switch (G__49759) {
case 2:
return shadow.dom.event_chan.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return shadow.dom.event_chan.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 4:
return shadow.dom.event_chan.cljs$core$IFn$_invoke$arity$4((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.dom.event_chan.cljs$core$IFn$_invoke$arity$2 = (function (el,event){
return shadow.dom.event_chan.cljs$core$IFn$_invoke$arity$4(el,event,null,false);
}));

(shadow.dom.event_chan.cljs$core$IFn$_invoke$arity$3 = (function (el,event,xf){
return shadow.dom.event_chan.cljs$core$IFn$_invoke$arity$4(el,event,xf,false);
}));

(shadow.dom.event_chan.cljs$core$IFn$_invoke$arity$4 = (function (el,event,xf,once_or_cleanup){
var buf = cljs.core.async.sliding_buffer((1));
var chan = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$2(buf,xf);
var event_fn = (function shadow$dom$event_fn(e){
cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$2(chan,e);

if(once_or_cleanup === true){
shadow.dom.remove_event_handler(el,event,shadow$dom$event_fn);

return cljs.core.async.close_BANG_(chan);
} else {
return null;
}
});
shadow.dom.dom_listen(shadow.dom.dom_node(el),cljs.core.name(event),event_fn);

if(cljs.core.truth_((function (){var and__4251__auto__ = once_or_cleanup;
if(cljs.core.truth_(and__4251__auto__)){
return (!(once_or_cleanup === true));
} else {
return and__4251__auto__;
}
})())){
var c__46528__auto___50608 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_49775){
var state_val_49776 = (state_49775[(1)]);
if((state_val_49776 === (1))){
var state_49775__$1 = state_49775;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_49775__$1,(2),once_or_cleanup);
} else {
if((state_val_49776 === (2))){
var inst_49772 = (state_49775[(2)]);
var inst_49773 = shadow.dom.remove_event_handler(el,event,event_fn);
var state_49775__$1 = (function (){var statearr_49777 = state_49775;
(statearr_49777[(7)] = inst_49772);

return statearr_49777;
})();
return cljs.core.async.impl.ioc_helpers.return_chan(state_49775__$1,inst_49773);
} else {
return null;
}
}
});
return (function() {
var shadow$dom$state_machine__46373__auto__ = null;
var shadow$dom$state_machine__46373__auto____0 = (function (){
var statearr_49781 = [null,null,null,null,null,null,null,null];
(statearr_49781[(0)] = shadow$dom$state_machine__46373__auto__);

(statearr_49781[(1)] = (1));

return statearr_49781;
});
var shadow$dom$state_machine__46373__auto____1 = (function (state_49775){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_49775);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e49782){var ex__46376__auto__ = e49782;
var statearr_49783_50611 = state_49775;
(statearr_49783_50611[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_49775[(4)]))){
var statearr_49784_50612 = state_49775;
(statearr_49784_50612[(1)] = cljs.core.first((state_49775[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__50613 = state_49775;
state_49775 = G__50613;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
shadow$dom$state_machine__46373__auto__ = function(state_49775){
switch(arguments.length){
case 0:
return shadow$dom$state_machine__46373__auto____0.call(this);
case 1:
return shadow$dom$state_machine__46373__auto____1.call(this,state_49775);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
shadow$dom$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = shadow$dom$state_machine__46373__auto____0;
shadow$dom$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = shadow$dom$state_machine__46373__auto____1;
return shadow$dom$state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_49787 = f__46529__auto__();
(statearr_49787[(6)] = c__46528__auto___50608);

return statearr_49787;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
}));

} else {
}

return chan;
}));

(shadow.dom.event_chan.cljs$lang$maxFixedArity = 4);


//# sourceMappingURL=shadow.dom.js.map
