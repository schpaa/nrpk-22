goog.provide('shadow.dom');
shadow.dom.transition_supported_QMARK_ = (((typeof window !== 'undefined'))?goog.style.transition.isSupported():null);

/**
 * @interface
 */
shadow.dom.IElement = function(){};

var shadow$dom$IElement$_to_dom$dyn_68894 = (function (this$){
var x__5390__auto__ = (((this$ == null))?null:this$);
var m__5391__auto__ = (shadow.dom._to_dom[goog.typeOf(x__5390__auto__)]);
if((!((m__5391__auto__ == null)))){
return (m__5391__auto__.cljs$core$IFn$_invoke$arity$1 ? m__5391__auto__.cljs$core$IFn$_invoke$arity$1(this$) : m__5391__auto__.call(null,this$));
} else {
var m__5389__auto__ = (shadow.dom._to_dom["_"]);
if((!((m__5389__auto__ == null)))){
return (m__5389__auto__.cljs$core$IFn$_invoke$arity$1 ? m__5389__auto__.cljs$core$IFn$_invoke$arity$1(this$) : m__5389__auto__.call(null,this$));
} else {
throw cljs.core.missing_protocol("IElement.-to-dom",this$);
}
}
});
shadow.dom._to_dom = (function shadow$dom$_to_dom(this$){
if((((!((this$ == null)))) && ((!((this$.shadow$dom$IElement$_to_dom$arity$1 == null)))))){
return this$.shadow$dom$IElement$_to_dom$arity$1(this$);
} else {
return shadow$dom$IElement$_to_dom$dyn_68894(this$);
}
});


/**
 * @interface
 */
shadow.dom.SVGElement = function(){};

var shadow$dom$SVGElement$_to_svg$dyn_68896 = (function (this$){
var x__5390__auto__ = (((this$ == null))?null:this$);
var m__5391__auto__ = (shadow.dom._to_svg[goog.typeOf(x__5390__auto__)]);
if((!((m__5391__auto__ == null)))){
return (m__5391__auto__.cljs$core$IFn$_invoke$arity$1 ? m__5391__auto__.cljs$core$IFn$_invoke$arity$1(this$) : m__5391__auto__.call(null,this$));
} else {
var m__5389__auto__ = (shadow.dom._to_svg["_"]);
if((!((m__5389__auto__ == null)))){
return (m__5389__auto__.cljs$core$IFn$_invoke$arity$1 ? m__5389__auto__.cljs$core$IFn$_invoke$arity$1(this$) : m__5389__auto__.call(null,this$));
} else {
throw cljs.core.missing_protocol("SVGElement.-to-svg",this$);
}
}
});
shadow.dom._to_svg = (function shadow$dom$_to_svg(this$){
if((((!((this$ == null)))) && ((!((this$.shadow$dom$SVGElement$_to_svg$arity$1 == null)))))){
return this$.shadow$dom$SVGElement$_to_svg$arity$1(this$);
} else {
return shadow$dom$SVGElement$_to_svg$dyn_68896(this$);
}
});

shadow.dom.lazy_native_coll_seq = (function shadow$dom$lazy_native_coll_seq(coll,idx){
if((idx < coll.length)){
return (new cljs.core.LazySeq(null,(function (){
return cljs.core.cons((coll[idx]),(function (){var G__67886 = coll;
var G__67887 = (idx + (1));
return (shadow.dom.lazy_native_coll_seq.cljs$core$IFn$_invoke$arity$2 ? shadow.dom.lazy_native_coll_seq.cljs$core$IFn$_invoke$arity$2(G__67886,G__67887) : shadow.dom.lazy_native_coll_seq.call(null,G__67886,G__67887));
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
var or__5043__auto__ = (self__.coll[n]);
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
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

(shadow.dom.NativeColl.cljs$lang$ctorPrWriter = (function (this__5327__auto__,writer__5328__auto__,opt__5329__auto__){
return cljs.core._write(writer__5328__auto__,"shadow.dom/NativeColl");
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
var G__67904 = arguments.length;
switch (G__67904) {
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
var G__67910 = arguments.length;
switch (G__67910) {
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
var G__67919 = arguments.length;
switch (G__67919) {
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
var G__67935 = arguments.length;
switch (G__67935) {
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
var G__67948 = arguments.length;
switch (G__67948) {
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
var G__67966 = arguments.length;
switch (G__67966) {
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

shadow.dom.dom_listen = (cljs.core.truth_((function (){var or__5043__auto__ = (!((typeof document !== 'undefined')));
if(or__5043__auto__){
return or__5043__auto__;
} else {
return document.addEventListener;
}
})())?(function shadow$dom$dom_listen_good(el,ev,handler){
return el.addEventListener(ev,handler,false);
}):(function shadow$dom$dom_listen_ie(el,ev,handler){
try{return el.attachEvent(["on",cljs.core.str.cljs$core$IFn$_invoke$arity$1(ev)].join(''),(function (e){
return (handler.cljs$core$IFn$_invoke$arity$2 ? handler.cljs$core$IFn$_invoke$arity$2(e,el) : handler.call(null,e,el));
}));
}catch (e67977){if((e67977 instanceof Object)){
var e = e67977;
return console.log("didnt support attachEvent",el,e);
} else {
throw e67977;

}
}}));
shadow.dom.dom_listen_remove = (cljs.core.truth_((function (){var or__5043__auto__ = (!((typeof document !== 'undefined')));
if(or__5043__auto__){
return or__5043__auto__;
} else {
return document.removeEventListener;
}
})())?(function shadow$dom$dom_listen_remove_good(el,ev,handler){
return el.removeEventListener(ev,handler,false);
}):(function shadow$dom$dom_listen_remove_ie(el,ev,handler){
return el.detachEvent(["on",cljs.core.str.cljs$core$IFn$_invoke$arity$1(ev)].join(''),handler);
}));
shadow.dom.on_query = (function shadow$dom$on_query(root_el,ev,selector,handler){
var seq__67991 = cljs.core.seq(shadow.dom.query.cljs$core$IFn$_invoke$arity$2(selector,root_el));
var chunk__67992 = null;
var count__67993 = (0);
var i__67994 = (0);
while(true){
if((i__67994 < count__67993)){
var el = chunk__67992.cljs$core$IIndexed$_nth$arity$2(null,i__67994);
var handler_68909__$1 = ((function (seq__67991,chunk__67992,count__67993,i__67994,el){
return (function (e){
return (handler.cljs$core$IFn$_invoke$arity$2 ? handler.cljs$core$IFn$_invoke$arity$2(e,el) : handler.call(null,e,el));
});})(seq__67991,chunk__67992,count__67993,i__67994,el))
;
shadow.dom.dom_listen(el,cljs.core.name(ev),handler_68909__$1);


var G__68910 = seq__67991;
var G__68911 = chunk__67992;
var G__68912 = count__67993;
var G__68913 = (i__67994 + (1));
seq__67991 = G__68910;
chunk__67992 = G__68911;
count__67993 = G__68912;
i__67994 = G__68913;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__67991);
if(temp__5753__auto__){
var seq__67991__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__67991__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__67991__$1);
var G__68914 = cljs.core.chunk_rest(seq__67991__$1);
var G__68915 = c__5565__auto__;
var G__68916 = cljs.core.count(c__5565__auto__);
var G__68917 = (0);
seq__67991 = G__68914;
chunk__67992 = G__68915;
count__67993 = G__68916;
i__67994 = G__68917;
continue;
} else {
var el = cljs.core.first(seq__67991__$1);
var handler_68918__$1 = ((function (seq__67991,chunk__67992,count__67993,i__67994,el,seq__67991__$1,temp__5753__auto__){
return (function (e){
return (handler.cljs$core$IFn$_invoke$arity$2 ? handler.cljs$core$IFn$_invoke$arity$2(e,el) : handler.call(null,e,el));
});})(seq__67991,chunk__67992,count__67993,i__67994,el,seq__67991__$1,temp__5753__auto__))
;
shadow.dom.dom_listen(el,cljs.core.name(ev),handler_68918__$1);


var G__68919 = cljs.core.next(seq__67991__$1);
var G__68920 = null;
var G__68921 = (0);
var G__68922 = (0);
seq__67991 = G__68919;
chunk__67992 = G__68920;
count__67993 = G__68921;
i__67994 = G__68922;
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
var G__68013 = arguments.length;
switch (G__68013) {
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
var seq__68035 = cljs.core.seq(events);
var chunk__68036 = null;
var count__68037 = (0);
var i__68038 = (0);
while(true){
if((i__68038 < count__68037)){
var vec__68059 = chunk__68036.cljs$core$IIndexed$_nth$arity$2(null,i__68038);
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68059,(0),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68059,(1),null);
shadow.dom.on.cljs$core$IFn$_invoke$arity$3(el,k,v);


var G__68925 = seq__68035;
var G__68926 = chunk__68036;
var G__68927 = count__68037;
var G__68928 = (i__68038 + (1));
seq__68035 = G__68925;
chunk__68036 = G__68926;
count__68037 = G__68927;
i__68038 = G__68928;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__68035);
if(temp__5753__auto__){
var seq__68035__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__68035__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__68035__$1);
var G__68929 = cljs.core.chunk_rest(seq__68035__$1);
var G__68930 = c__5565__auto__;
var G__68931 = cljs.core.count(c__5565__auto__);
var G__68932 = (0);
seq__68035 = G__68929;
chunk__68036 = G__68930;
count__68037 = G__68931;
i__68038 = G__68932;
continue;
} else {
var vec__68067 = cljs.core.first(seq__68035__$1);
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68067,(0),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68067,(1),null);
shadow.dom.on.cljs$core$IFn$_invoke$arity$3(el,k,v);


var G__68933 = cljs.core.next(seq__68035__$1);
var G__68934 = null;
var G__68935 = (0);
var G__68936 = (0);
seq__68035 = G__68933;
chunk__68036 = G__68934;
count__68037 = G__68935;
i__68038 = G__68936;
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
var seq__68072 = cljs.core.seq(styles);
var chunk__68073 = null;
var count__68074 = (0);
var i__68075 = (0);
while(true){
if((i__68075 < count__68074)){
var vec__68089 = chunk__68073.cljs$core$IIndexed$_nth$arity$2(null,i__68075);
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68089,(0),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68089,(1),null);
goog.style.setStyle(dom,cljs.core.name(k),(((v == null))?"":v));


var G__68937 = seq__68072;
var G__68938 = chunk__68073;
var G__68939 = count__68074;
var G__68940 = (i__68075 + (1));
seq__68072 = G__68937;
chunk__68073 = G__68938;
count__68074 = G__68939;
i__68075 = G__68940;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__68072);
if(temp__5753__auto__){
var seq__68072__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__68072__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__68072__$1);
var G__68941 = cljs.core.chunk_rest(seq__68072__$1);
var G__68942 = c__5565__auto__;
var G__68943 = cljs.core.count(c__5565__auto__);
var G__68944 = (0);
seq__68072 = G__68941;
chunk__68073 = G__68942;
count__68074 = G__68943;
i__68075 = G__68944;
continue;
} else {
var vec__68096 = cljs.core.first(seq__68072__$1);
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68096,(0),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68096,(1),null);
goog.style.setStyle(dom,cljs.core.name(k),(((v == null))?"":v));


var G__68945 = cljs.core.next(seq__68072__$1);
var G__68946 = null;
var G__68947 = (0);
var G__68948 = (0);
seq__68072 = G__68945;
chunk__68073 = G__68946;
count__68074 = G__68947;
i__68075 = G__68948;
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
var G__68108_68949 = key;
var G__68108_68950__$1 = (((G__68108_68949 instanceof cljs.core.Keyword))?G__68108_68949.fqn:null);
switch (G__68108_68950__$1) {
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
var ks_68960 = cljs.core.name(key);
if(cljs.core.truth_((function (){var or__5043__auto__ = goog.string.startsWith(ks_68960,"data-");
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
return goog.string.startsWith(ks_68960,"aria-");
}
})())){
el.setAttribute(ks_68960,value);
} else {
(el[ks_68960] = value);
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
shadow.dom.create_dom_node = (function shadow$dom$create_dom_node(tag_def,p__68134){
var map__68135 = p__68134;
var map__68135__$1 = cljs.core.__destructure_map(map__68135);
var props = map__68135__$1;
var class$ = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__68135__$1,new cljs.core.Keyword(null,"class","class",-2030961996));
var tag_props = ({});
var vec__68136 = shadow.dom.parse_tag(tag_def);
var tag_name = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68136,(0),null);
var tag_id = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68136,(1),null);
var tag_classes = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68136,(2),null);
if(cljs.core.truth_(tag_id)){
(tag_props["id"] = tag_id);
} else {
}

if(cljs.core.truth_(tag_classes)){
(tag_props["class"] = shadow.dom.merge_class_string(class$,tag_classes));
} else {
}

var G__68139 = goog.dom.createDom(tag_name,tag_props);
shadow.dom.set_attrs(G__68139,cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(props,new cljs.core.Keyword(null,"class","class",-2030961996)));

return G__68139;
});
shadow.dom.append = (function shadow$dom$append(var_args){
var G__68144 = arguments.length;
switch (G__68144) {
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

shadow.dom.destructure_node = (function shadow$dom$destructure_node(create_fn,p__68155){
var vec__68157 = p__68155;
var seq__68158 = cljs.core.seq(vec__68157);
var first__68159 = cljs.core.first(seq__68158);
var seq__68158__$1 = cljs.core.next(seq__68158);
var nn = first__68159;
var first__68159__$1 = cljs.core.first(seq__68158__$1);
var seq__68158__$2 = cljs.core.next(seq__68158__$1);
var np = first__68159__$1;
var nc = seq__68158__$2;
var node = vec__68157;
if((nn instanceof cljs.core.Keyword)){
} else {
throw cljs.core.ex_info.cljs$core$IFn$_invoke$arity$2("invalid dom node",new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"node","node",581201198),node], null));
}

if((((np == null)) && ((nc == null)))){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(function (){var G__68160 = nn;
var G__68161 = cljs.core.PersistentArrayMap.EMPTY;
return (create_fn.cljs$core$IFn$_invoke$arity$2 ? create_fn.cljs$core$IFn$_invoke$arity$2(G__68160,G__68161) : create_fn.call(null,G__68160,G__68161));
})(),cljs.core.List.EMPTY], null);
} else {
if(cljs.core.map_QMARK_(np)){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(create_fn.cljs$core$IFn$_invoke$arity$2 ? create_fn.cljs$core$IFn$_invoke$arity$2(nn,np) : create_fn.call(null,nn,np)),nc], null);
} else {
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(function (){var G__68166 = nn;
var G__68167 = cljs.core.PersistentArrayMap.EMPTY;
return (create_fn.cljs$core$IFn$_invoke$arity$2 ? create_fn.cljs$core$IFn$_invoke$arity$2(G__68166,G__68167) : create_fn.call(null,G__68166,G__68167));
})(),cljs.core.conj.cljs$core$IFn$_invoke$arity$2(nc,np)], null);

}
}
});
shadow.dom.make_dom_node = (function shadow$dom$make_dom_node(structure){
var vec__68184 = shadow.dom.destructure_node(shadow.dom.create_dom_node,structure);
var node = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68184,(0),null);
var node_children = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68184,(1),null);
var seq__68193_68966 = cljs.core.seq(node_children);
var chunk__68194_68967 = null;
var count__68195_68968 = (0);
var i__68196_68969 = (0);
while(true){
if((i__68196_68969 < count__68195_68968)){
var child_struct_68970 = chunk__68194_68967.cljs$core$IIndexed$_nth$arity$2(null,i__68196_68969);
var children_68971 = shadow.dom.dom_node(child_struct_68970);
if(cljs.core.seq_QMARK_(children_68971)){
var seq__68270_68972 = cljs.core.seq(cljs.core.map.cljs$core$IFn$_invoke$arity$2(shadow.dom.dom_node,children_68971));
var chunk__68272_68973 = null;
var count__68273_68974 = (0);
var i__68274_68975 = (0);
while(true){
if((i__68274_68975 < count__68273_68974)){
var child_68976 = chunk__68272_68973.cljs$core$IIndexed$_nth$arity$2(null,i__68274_68975);
if(cljs.core.truth_(child_68976)){
shadow.dom.append.cljs$core$IFn$_invoke$arity$2(node,child_68976);


var G__68977 = seq__68270_68972;
var G__68978 = chunk__68272_68973;
var G__68979 = count__68273_68974;
var G__68980 = (i__68274_68975 + (1));
seq__68270_68972 = G__68977;
chunk__68272_68973 = G__68978;
count__68273_68974 = G__68979;
i__68274_68975 = G__68980;
continue;
} else {
var G__68981 = seq__68270_68972;
var G__68982 = chunk__68272_68973;
var G__68983 = count__68273_68974;
var G__68984 = (i__68274_68975 + (1));
seq__68270_68972 = G__68981;
chunk__68272_68973 = G__68982;
count__68273_68974 = G__68983;
i__68274_68975 = G__68984;
continue;
}
} else {
var temp__5753__auto___68985 = cljs.core.seq(seq__68270_68972);
if(temp__5753__auto___68985){
var seq__68270_68986__$1 = temp__5753__auto___68985;
if(cljs.core.chunked_seq_QMARK_(seq__68270_68986__$1)){
var c__5565__auto___68987 = cljs.core.chunk_first(seq__68270_68986__$1);
var G__68988 = cljs.core.chunk_rest(seq__68270_68986__$1);
var G__68989 = c__5565__auto___68987;
var G__68990 = cljs.core.count(c__5565__auto___68987);
var G__68991 = (0);
seq__68270_68972 = G__68988;
chunk__68272_68973 = G__68989;
count__68273_68974 = G__68990;
i__68274_68975 = G__68991;
continue;
} else {
var child_68992 = cljs.core.first(seq__68270_68986__$1);
if(cljs.core.truth_(child_68992)){
shadow.dom.append.cljs$core$IFn$_invoke$arity$2(node,child_68992);


var G__68993 = cljs.core.next(seq__68270_68986__$1);
var G__68994 = null;
var G__68995 = (0);
var G__68996 = (0);
seq__68270_68972 = G__68993;
chunk__68272_68973 = G__68994;
count__68273_68974 = G__68995;
i__68274_68975 = G__68996;
continue;
} else {
var G__68997 = cljs.core.next(seq__68270_68986__$1);
var G__68998 = null;
var G__68999 = (0);
var G__69000 = (0);
seq__68270_68972 = G__68997;
chunk__68272_68973 = G__68998;
count__68273_68974 = G__68999;
i__68274_68975 = G__69000;
continue;
}
}
} else {
}
}
break;
}
} else {
shadow.dom.append.cljs$core$IFn$_invoke$arity$2(node,children_68971);
}


var G__69001 = seq__68193_68966;
var G__69002 = chunk__68194_68967;
var G__69003 = count__68195_68968;
var G__69004 = (i__68196_68969 + (1));
seq__68193_68966 = G__69001;
chunk__68194_68967 = G__69002;
count__68195_68968 = G__69003;
i__68196_68969 = G__69004;
continue;
} else {
var temp__5753__auto___69005 = cljs.core.seq(seq__68193_68966);
if(temp__5753__auto___69005){
var seq__68193_69006__$1 = temp__5753__auto___69005;
if(cljs.core.chunked_seq_QMARK_(seq__68193_69006__$1)){
var c__5565__auto___69007 = cljs.core.chunk_first(seq__68193_69006__$1);
var G__69008 = cljs.core.chunk_rest(seq__68193_69006__$1);
var G__69009 = c__5565__auto___69007;
var G__69010 = cljs.core.count(c__5565__auto___69007);
var G__69011 = (0);
seq__68193_68966 = G__69008;
chunk__68194_68967 = G__69009;
count__68195_68968 = G__69010;
i__68196_68969 = G__69011;
continue;
} else {
var child_struct_69012 = cljs.core.first(seq__68193_69006__$1);
var children_69013 = shadow.dom.dom_node(child_struct_69012);
if(cljs.core.seq_QMARK_(children_69013)){
var seq__68283_69014 = cljs.core.seq(cljs.core.map.cljs$core$IFn$_invoke$arity$2(shadow.dom.dom_node,children_69013));
var chunk__68285_69015 = null;
var count__68286_69016 = (0);
var i__68287_69017 = (0);
while(true){
if((i__68287_69017 < count__68286_69016)){
var child_69018 = chunk__68285_69015.cljs$core$IIndexed$_nth$arity$2(null,i__68287_69017);
if(cljs.core.truth_(child_69018)){
shadow.dom.append.cljs$core$IFn$_invoke$arity$2(node,child_69018);


var G__69019 = seq__68283_69014;
var G__69020 = chunk__68285_69015;
var G__69021 = count__68286_69016;
var G__69022 = (i__68287_69017 + (1));
seq__68283_69014 = G__69019;
chunk__68285_69015 = G__69020;
count__68286_69016 = G__69021;
i__68287_69017 = G__69022;
continue;
} else {
var G__69023 = seq__68283_69014;
var G__69024 = chunk__68285_69015;
var G__69025 = count__68286_69016;
var G__69026 = (i__68287_69017 + (1));
seq__68283_69014 = G__69023;
chunk__68285_69015 = G__69024;
count__68286_69016 = G__69025;
i__68287_69017 = G__69026;
continue;
}
} else {
var temp__5753__auto___69027__$1 = cljs.core.seq(seq__68283_69014);
if(temp__5753__auto___69027__$1){
var seq__68283_69028__$1 = temp__5753__auto___69027__$1;
if(cljs.core.chunked_seq_QMARK_(seq__68283_69028__$1)){
var c__5565__auto___69029 = cljs.core.chunk_first(seq__68283_69028__$1);
var G__69030 = cljs.core.chunk_rest(seq__68283_69028__$1);
var G__69031 = c__5565__auto___69029;
var G__69032 = cljs.core.count(c__5565__auto___69029);
var G__69033 = (0);
seq__68283_69014 = G__69030;
chunk__68285_69015 = G__69031;
count__68286_69016 = G__69032;
i__68287_69017 = G__69033;
continue;
} else {
var child_69034 = cljs.core.first(seq__68283_69028__$1);
if(cljs.core.truth_(child_69034)){
shadow.dom.append.cljs$core$IFn$_invoke$arity$2(node,child_69034);


var G__69035 = cljs.core.next(seq__68283_69028__$1);
var G__69036 = null;
var G__69037 = (0);
var G__69038 = (0);
seq__68283_69014 = G__69035;
chunk__68285_69015 = G__69036;
count__68286_69016 = G__69037;
i__68287_69017 = G__69038;
continue;
} else {
var G__69039 = cljs.core.next(seq__68283_69028__$1);
var G__69040 = null;
var G__69041 = (0);
var G__69042 = (0);
seq__68283_69014 = G__69039;
chunk__68285_69015 = G__69040;
count__68286_69016 = G__69041;
i__68287_69017 = G__69042;
continue;
}
}
} else {
}
}
break;
}
} else {
shadow.dom.append.cljs$core$IFn$_invoke$arity$2(node,children_69013);
}


var G__69043 = cljs.core.next(seq__68193_69006__$1);
var G__69044 = null;
var G__69045 = (0);
var G__69046 = (0);
seq__68193_68966 = G__69043;
chunk__68194_68967 = G__69044;
count__68195_68968 = G__69045;
i__68196_68969 = G__69046;
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
var seq__68301 = cljs.core.seq(node);
var chunk__68302 = null;
var count__68303 = (0);
var i__68304 = (0);
while(true){
if((i__68304 < count__68303)){
var n = chunk__68302.cljs$core$IIndexed$_nth$arity$2(null,i__68304);
(shadow.dom.remove.cljs$core$IFn$_invoke$arity$1 ? shadow.dom.remove.cljs$core$IFn$_invoke$arity$1(n) : shadow.dom.remove.call(null,n));


var G__69048 = seq__68301;
var G__69049 = chunk__68302;
var G__69050 = count__68303;
var G__69051 = (i__68304 + (1));
seq__68301 = G__69048;
chunk__68302 = G__69049;
count__68303 = G__69050;
i__68304 = G__69051;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__68301);
if(temp__5753__auto__){
var seq__68301__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__68301__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__68301__$1);
var G__69052 = cljs.core.chunk_rest(seq__68301__$1);
var G__69053 = c__5565__auto__;
var G__69054 = cljs.core.count(c__5565__auto__);
var G__69055 = (0);
seq__68301 = G__69052;
chunk__68302 = G__69053;
count__68303 = G__69054;
i__68304 = G__69055;
continue;
} else {
var n = cljs.core.first(seq__68301__$1);
(shadow.dom.remove.cljs$core$IFn$_invoke$arity$1 ? shadow.dom.remove.cljs$core$IFn$_invoke$arity$1(n) : shadow.dom.remove.call(null,n));


var G__69056 = cljs.core.next(seq__68301__$1);
var G__69057 = null;
var G__69058 = (0);
var G__69059 = (0);
seq__68301 = G__69056;
chunk__68302 = G__69057;
count__68303 = G__69058;
i__68304 = G__69059;
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
var G__68331 = arguments.length;
switch (G__68331) {
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
var G__68343 = arguments.length;
switch (G__68343) {
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
var G__68358 = arguments.length;
switch (G__68358) {
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
var or__5043__auto__ = shadow.dom.dom_node(el).getAttribute(cljs.core.name(key));
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
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
var args__5772__auto__ = [];
var len__5766__auto___69069 = arguments.length;
var i__5767__auto___69070 = (0);
while(true){
if((i__5767__auto___69070 < len__5766__auto___69069)){
args__5772__auto__.push((arguments[i__5767__auto___69070]));

var G__69071 = (i__5767__auto___69070 + (1));
i__5767__auto___69070 = G__69071;
continue;
} else {
}
break;
}

var argseq__5773__auto__ = ((((0) < args__5772__auto__.length))?(new cljs.core.IndexedSeq(args__5772__auto__.slice((0)),(0),null)):null);
return shadow.dom.fragment.cljs$core$IFn$_invoke$arity$variadic(argseq__5773__auto__);
});

(shadow.dom.fragment.cljs$core$IFn$_invoke$arity$variadic = (function (nodes){
var fragment = document.createDocumentFragment();
var seq__68380_69072 = cljs.core.seq(nodes);
var chunk__68381_69073 = null;
var count__68382_69074 = (0);
var i__68383_69075 = (0);
while(true){
if((i__68383_69075 < count__68382_69074)){
var node_69079 = chunk__68381_69073.cljs$core$IIndexed$_nth$arity$2(null,i__68383_69075);
fragment.appendChild(shadow.dom._to_dom(node_69079));


var G__69080 = seq__68380_69072;
var G__69081 = chunk__68381_69073;
var G__69082 = count__68382_69074;
var G__69083 = (i__68383_69075 + (1));
seq__68380_69072 = G__69080;
chunk__68381_69073 = G__69081;
count__68382_69074 = G__69082;
i__68383_69075 = G__69083;
continue;
} else {
var temp__5753__auto___69084 = cljs.core.seq(seq__68380_69072);
if(temp__5753__auto___69084){
var seq__68380_69085__$1 = temp__5753__auto___69084;
if(cljs.core.chunked_seq_QMARK_(seq__68380_69085__$1)){
var c__5565__auto___69086 = cljs.core.chunk_first(seq__68380_69085__$1);
var G__69087 = cljs.core.chunk_rest(seq__68380_69085__$1);
var G__69088 = c__5565__auto___69086;
var G__69089 = cljs.core.count(c__5565__auto___69086);
var G__69090 = (0);
seq__68380_69072 = G__69087;
chunk__68381_69073 = G__69088;
count__68382_69074 = G__69089;
i__68383_69075 = G__69090;
continue;
} else {
var node_69091 = cljs.core.first(seq__68380_69085__$1);
fragment.appendChild(shadow.dom._to_dom(node_69091));


var G__69092 = cljs.core.next(seq__68380_69085__$1);
var G__69093 = null;
var G__69094 = (0);
var G__69095 = (0);
seq__68380_69072 = G__69092;
chunk__68381_69073 = G__69093;
count__68382_69074 = G__69094;
i__68383_69075 = G__69095;
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
(shadow.dom.fragment.cljs$lang$applyTo = (function (seq68371){
var self__5752__auto__ = this;
return self__5752__auto__.cljs$core$IFn$_invoke$arity$variadic(cljs.core.seq(seq68371));
}));

/**
 * given a html string, eval all <script> tags and return the html without the scripts
 * don't do this for everything, only content you trust.
 */
shadow.dom.eval_scripts = (function shadow$dom$eval_scripts(s){
var scripts = cljs.core.re_seq(/<script[^>]*?>(.+?)<\/script>/,s);
var seq__68400_69096 = cljs.core.seq(scripts);
var chunk__68401_69097 = null;
var count__68402_69098 = (0);
var i__68403_69099 = (0);
while(true){
if((i__68403_69099 < count__68402_69098)){
var vec__68417_69100 = chunk__68401_69097.cljs$core$IIndexed$_nth$arity$2(null,i__68403_69099);
var script_tag_69101 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68417_69100,(0),null);
var script_body_69102 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68417_69100,(1),null);
eval(script_body_69102);


var G__69104 = seq__68400_69096;
var G__69105 = chunk__68401_69097;
var G__69106 = count__68402_69098;
var G__69107 = (i__68403_69099 + (1));
seq__68400_69096 = G__69104;
chunk__68401_69097 = G__69105;
count__68402_69098 = G__69106;
i__68403_69099 = G__69107;
continue;
} else {
var temp__5753__auto___69108 = cljs.core.seq(seq__68400_69096);
if(temp__5753__auto___69108){
var seq__68400_69109__$1 = temp__5753__auto___69108;
if(cljs.core.chunked_seq_QMARK_(seq__68400_69109__$1)){
var c__5565__auto___69110 = cljs.core.chunk_first(seq__68400_69109__$1);
var G__69111 = cljs.core.chunk_rest(seq__68400_69109__$1);
var G__69112 = c__5565__auto___69110;
var G__69113 = cljs.core.count(c__5565__auto___69110);
var G__69114 = (0);
seq__68400_69096 = G__69111;
chunk__68401_69097 = G__69112;
count__68402_69098 = G__69113;
i__68403_69099 = G__69114;
continue;
} else {
var vec__68428_69115 = cljs.core.first(seq__68400_69109__$1);
var script_tag_69116 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68428_69115,(0),null);
var script_body_69117 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68428_69115,(1),null);
eval(script_body_69117);


var G__69118 = cljs.core.next(seq__68400_69109__$1);
var G__69119 = null;
var G__69120 = (0);
var G__69121 = (0);
seq__68400_69096 = G__69118;
chunk__68401_69097 = G__69119;
count__68402_69098 = G__69120;
i__68403_69099 = G__69121;
continue;
}
} else {
}
}
break;
}

return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3((function (s__$1,p__68438){
var vec__68439 = p__68438;
var script_tag = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68439,(0),null);
var script_body = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68439,(1),null);
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
var G__68465 = arguments.length;
switch (G__68465) {
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
var seq__68503 = cljs.core.seq(style_keys);
var chunk__68504 = null;
var count__68505 = (0);
var i__68506 = (0);
while(true){
if((i__68506 < count__68505)){
var it = chunk__68504.cljs$core$IIndexed$_nth$arity$2(null,i__68506);
shadow.dom.remove_style_STAR_(el__$1,it);


var G__69125 = seq__68503;
var G__69126 = chunk__68504;
var G__69127 = count__68505;
var G__69128 = (i__68506 + (1));
seq__68503 = G__69125;
chunk__68504 = G__69126;
count__68505 = G__69127;
i__68506 = G__69128;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__68503);
if(temp__5753__auto__){
var seq__68503__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__68503__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__68503__$1);
var G__69129 = cljs.core.chunk_rest(seq__68503__$1);
var G__69130 = c__5565__auto__;
var G__69131 = cljs.core.count(c__5565__auto__);
var G__69132 = (0);
seq__68503 = G__69129;
chunk__68504 = G__69130;
count__68505 = G__69131;
i__68506 = G__69132;
continue;
} else {
var it = cljs.core.first(seq__68503__$1);
shadow.dom.remove_style_STAR_(el__$1,it);


var G__69134 = cljs.core.next(seq__68503__$1);
var G__69135 = null;
var G__69136 = (0);
var G__69137 = (0);
seq__68503 = G__69134;
chunk__68504 = G__69135;
count__68505 = G__69136;
i__68506 = G__69137;
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
(shadow.dom.Coordinate.prototype.cljs$core$ILookup$_lookup$arity$2 = (function (this__5340__auto__,k__5341__auto__){
var self__ = this;
var this__5340__auto____$1 = this;
return this__5340__auto____$1.cljs$core$ILookup$_lookup$arity$3(null,k__5341__auto__,null);
}));

(shadow.dom.Coordinate.prototype.cljs$core$ILookup$_lookup$arity$3 = (function (this__5342__auto__,k68516,else__5343__auto__){
var self__ = this;
var this__5342__auto____$1 = this;
var G__68527 = k68516;
var G__68527__$1 = (((G__68527 instanceof cljs.core.Keyword))?G__68527.fqn:null);
switch (G__68527__$1) {
case "x":
return self__.x;

break;
case "y":
return self__.y;

break;
default:
return cljs.core.get.cljs$core$IFn$_invoke$arity$3(self__.__extmap,k68516,else__5343__auto__);

}
}));

(shadow.dom.Coordinate.prototype.cljs$core$IKVReduce$_kv_reduce$arity$3 = (function (this__5360__auto__,f__5361__auto__,init__5362__auto__){
var self__ = this;
var this__5360__auto____$1 = this;
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3((function (ret__5363__auto__,p__68532){
var vec__68533 = p__68532;
var k__5364__auto__ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68533,(0),null);
var v__5365__auto__ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68533,(1),null);
return (f__5361__auto__.cljs$core$IFn$_invoke$arity$3 ? f__5361__auto__.cljs$core$IFn$_invoke$arity$3(ret__5363__auto__,k__5364__auto__,v__5365__auto__) : f__5361__auto__.call(null,ret__5363__auto__,k__5364__auto__,v__5365__auto__));
}),init__5362__auto__,this__5360__auto____$1);
}));

(shadow.dom.Coordinate.prototype.cljs$core$IPrintWithWriter$_pr_writer$arity$3 = (function (this__5355__auto__,writer__5356__auto__,opts__5357__auto__){
var self__ = this;
var this__5355__auto____$1 = this;
var pr_pair__5358__auto__ = (function (keyval__5359__auto__){
return cljs.core.pr_sequential_writer(writer__5356__auto__,cljs.core.pr_writer,""," ","",opts__5357__auto__,keyval__5359__auto__);
});
return cljs.core.pr_sequential_writer(writer__5356__auto__,pr_pair__5358__auto__,"#shadow.dom.Coordinate{",", ","}",opts__5357__auto__,cljs.core.concat.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"x","x",2099068185),self__.x],null)),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"y","y",-1757859776),self__.y],null))], null),self__.__extmap));
}));

(shadow.dom.Coordinate.prototype.cljs$core$IIterable$_iterator$arity$1 = (function (G__68515){
var self__ = this;
var G__68515__$1 = this;
return (new cljs.core.RecordIter((0),G__68515__$1,2,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"x","x",2099068185),new cljs.core.Keyword(null,"y","y",-1757859776)], null),(cljs.core.truth_(self__.__extmap)?cljs.core._iterator(self__.__extmap):cljs.core.nil_iter())));
}));

(shadow.dom.Coordinate.prototype.cljs$core$IMeta$_meta$arity$1 = (function (this__5338__auto__){
var self__ = this;
var this__5338__auto____$1 = this;
return self__.__meta;
}));

(shadow.dom.Coordinate.prototype.cljs$core$ICloneable$_clone$arity$1 = (function (this__5335__auto__){
var self__ = this;
var this__5335__auto____$1 = this;
return (new shadow.dom.Coordinate(self__.x,self__.y,self__.__meta,self__.__extmap,self__.__hash));
}));

(shadow.dom.Coordinate.prototype.cljs$core$ICounted$_count$arity$1 = (function (this__5344__auto__){
var self__ = this;
var this__5344__auto____$1 = this;
return (2 + cljs.core.count(self__.__extmap));
}));

(shadow.dom.Coordinate.prototype.cljs$core$IHash$_hash$arity$1 = (function (this__5336__auto__){
var self__ = this;
var this__5336__auto____$1 = this;
var h__5152__auto__ = self__.__hash;
if((!((h__5152__auto__ == null)))){
return h__5152__auto__;
} else {
var h__5152__auto____$1 = (function (coll__5337__auto__){
return (145542109 ^ cljs.core.hash_unordered_coll(coll__5337__auto__));
})(this__5336__auto____$1);
(self__.__hash = h__5152__auto____$1);

return h__5152__auto____$1;
}
}));

(shadow.dom.Coordinate.prototype.cljs$core$IEquiv$_equiv$arity$2 = (function (this68517,other68518){
var self__ = this;
var this68517__$1 = this;
return (((!((other68518 == null)))) && ((((this68517__$1.constructor === other68518.constructor)) && (((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this68517__$1.x,other68518.x)) && (((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this68517__$1.y,other68518.y)) && (cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this68517__$1.__extmap,other68518.__extmap)))))))));
}));

(shadow.dom.Coordinate.prototype.cljs$core$IMap$_dissoc$arity$2 = (function (this__5350__auto__,k__5351__auto__){
var self__ = this;
var this__5350__auto____$1 = this;
if(cljs.core.contains_QMARK_(new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"y","y",-1757859776),null,new cljs.core.Keyword(null,"x","x",2099068185),null], null), null),k__5351__auto__)){
return cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(cljs.core._with_meta(cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,this__5350__auto____$1),self__.__meta),k__5351__auto__);
} else {
return (new shadow.dom.Coordinate(self__.x,self__.y,self__.__meta,cljs.core.not_empty(cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(self__.__extmap,k__5351__auto__)),null));
}
}));

(shadow.dom.Coordinate.prototype.cljs$core$IAssociative$_contains_key_QMARK_$arity$2 = (function (this__5347__auto__,k68516){
var self__ = this;
var this__5347__auto____$1 = this;
var G__68554 = k68516;
var G__68554__$1 = (((G__68554 instanceof cljs.core.Keyword))?G__68554.fqn:null);
switch (G__68554__$1) {
case "x":
case "y":
return true;

break;
default:
return cljs.core.contains_QMARK_(self__.__extmap,k68516);

}
}));

(shadow.dom.Coordinate.prototype.cljs$core$IAssociative$_assoc$arity$3 = (function (this__5348__auto__,k__5349__auto__,G__68515){
var self__ = this;
var this__5348__auto____$1 = this;
var pred__68555 = cljs.core.keyword_identical_QMARK_;
var expr__68556 = k__5349__auto__;
if(cljs.core.truth_((pred__68555.cljs$core$IFn$_invoke$arity$2 ? pred__68555.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"x","x",2099068185),expr__68556) : pred__68555.call(null,new cljs.core.Keyword(null,"x","x",2099068185),expr__68556)))){
return (new shadow.dom.Coordinate(G__68515,self__.y,self__.__meta,self__.__extmap,null));
} else {
if(cljs.core.truth_((pred__68555.cljs$core$IFn$_invoke$arity$2 ? pred__68555.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"y","y",-1757859776),expr__68556) : pred__68555.call(null,new cljs.core.Keyword(null,"y","y",-1757859776),expr__68556)))){
return (new shadow.dom.Coordinate(self__.x,G__68515,self__.__meta,self__.__extmap,null));
} else {
return (new shadow.dom.Coordinate(self__.x,self__.y,self__.__meta,cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(self__.__extmap,k__5349__auto__,G__68515),null));
}
}
}));

(shadow.dom.Coordinate.prototype.cljs$core$ISeqable$_seq$arity$1 = (function (this__5353__auto__){
var self__ = this;
var this__5353__auto____$1 = this;
return cljs.core.seq(cljs.core.concat.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(new cljs.core.MapEntry(new cljs.core.Keyword(null,"x","x",2099068185),self__.x,null)),(new cljs.core.MapEntry(new cljs.core.Keyword(null,"y","y",-1757859776),self__.y,null))], null),self__.__extmap));
}));

(shadow.dom.Coordinate.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (this__5339__auto__,G__68515){
var self__ = this;
var this__5339__auto____$1 = this;
return (new shadow.dom.Coordinate(self__.x,self__.y,G__68515,self__.__extmap,self__.__hash));
}));

(shadow.dom.Coordinate.prototype.cljs$core$ICollection$_conj$arity$2 = (function (this__5345__auto__,entry__5346__auto__){
var self__ = this;
var this__5345__auto____$1 = this;
if(cljs.core.vector_QMARK_(entry__5346__auto__)){
return this__5345__auto____$1.cljs$core$IAssociative$_assoc$arity$3(null,cljs.core._nth(entry__5346__auto__,(0)),cljs.core._nth(entry__5346__auto__,(1)));
} else {
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3(cljs.core._conj,this__5345__auto____$1,entry__5346__auto__);
}
}));

(shadow.dom.Coordinate.getBasis = (function (){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"x","x",-555367584,null),new cljs.core.Symbol(null,"y","y",-117328249,null)], null);
}));

(shadow.dom.Coordinate.cljs$lang$type = true);

(shadow.dom.Coordinate.cljs$lang$ctorPrSeq = (function (this__5386__auto__){
return (new cljs.core.List(null,"shadow.dom/Coordinate",null,(1),null));
}));

(shadow.dom.Coordinate.cljs$lang$ctorPrWriter = (function (this__5386__auto__,writer__5387__auto__){
return cljs.core._write(writer__5387__auto__,"shadow.dom/Coordinate");
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
shadow.dom.map__GT_Coordinate = (function shadow$dom$map__GT_Coordinate(G__68520){
var extmap__5382__auto__ = (function (){var G__68572 = cljs.core.dissoc.cljs$core$IFn$_invoke$arity$variadic(G__68520,new cljs.core.Keyword(null,"x","x",2099068185),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"y","y",-1757859776)], 0));
if(cljs.core.record_QMARK_(G__68520)){
return cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,G__68572);
} else {
return G__68572;
}
})();
return (new shadow.dom.Coordinate(new cljs.core.Keyword(null,"x","x",2099068185).cljs$core$IFn$_invoke$arity$1(G__68520),new cljs.core.Keyword(null,"y","y",-1757859776).cljs$core$IFn$_invoke$arity$1(G__68520),null,cljs.core.not_empty(extmap__5382__auto__),null));
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
(shadow.dom.Size.prototype.cljs$core$ILookup$_lookup$arity$2 = (function (this__5340__auto__,k__5341__auto__){
var self__ = this;
var this__5340__auto____$1 = this;
return this__5340__auto____$1.cljs$core$ILookup$_lookup$arity$3(null,k__5341__auto__,null);
}));

(shadow.dom.Size.prototype.cljs$core$ILookup$_lookup$arity$3 = (function (this__5342__auto__,k68585,else__5343__auto__){
var self__ = this;
var this__5342__auto____$1 = this;
var G__68596 = k68585;
var G__68596__$1 = (((G__68596 instanceof cljs.core.Keyword))?G__68596.fqn:null);
switch (G__68596__$1) {
case "w":
return self__.w;

break;
case "h":
return self__.h;

break;
default:
return cljs.core.get.cljs$core$IFn$_invoke$arity$3(self__.__extmap,k68585,else__5343__auto__);

}
}));

(shadow.dom.Size.prototype.cljs$core$IKVReduce$_kv_reduce$arity$3 = (function (this__5360__auto__,f__5361__auto__,init__5362__auto__){
var self__ = this;
var this__5360__auto____$1 = this;
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3((function (ret__5363__auto__,p__68612){
var vec__68614 = p__68612;
var k__5364__auto__ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68614,(0),null);
var v__5365__auto__ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68614,(1),null);
return (f__5361__auto__.cljs$core$IFn$_invoke$arity$3 ? f__5361__auto__.cljs$core$IFn$_invoke$arity$3(ret__5363__auto__,k__5364__auto__,v__5365__auto__) : f__5361__auto__.call(null,ret__5363__auto__,k__5364__auto__,v__5365__auto__));
}),init__5362__auto__,this__5360__auto____$1);
}));

(shadow.dom.Size.prototype.cljs$core$IPrintWithWriter$_pr_writer$arity$3 = (function (this__5355__auto__,writer__5356__auto__,opts__5357__auto__){
var self__ = this;
var this__5355__auto____$1 = this;
var pr_pair__5358__auto__ = (function (keyval__5359__auto__){
return cljs.core.pr_sequential_writer(writer__5356__auto__,cljs.core.pr_writer,""," ","",opts__5357__auto__,keyval__5359__auto__);
});
return cljs.core.pr_sequential_writer(writer__5356__auto__,pr_pair__5358__auto__,"#shadow.dom.Size{",", ","}",opts__5357__auto__,cljs.core.concat.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"w","w",354169001),self__.w],null)),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"h","h",1109658740),self__.h],null))], null),self__.__extmap));
}));

(shadow.dom.Size.prototype.cljs$core$IIterable$_iterator$arity$1 = (function (G__68584){
var self__ = this;
var G__68584__$1 = this;
return (new cljs.core.RecordIter((0),G__68584__$1,2,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"w","w",354169001),new cljs.core.Keyword(null,"h","h",1109658740)], null),(cljs.core.truth_(self__.__extmap)?cljs.core._iterator(self__.__extmap):cljs.core.nil_iter())));
}));

(shadow.dom.Size.prototype.cljs$core$IMeta$_meta$arity$1 = (function (this__5338__auto__){
var self__ = this;
var this__5338__auto____$1 = this;
return self__.__meta;
}));

(shadow.dom.Size.prototype.cljs$core$ICloneable$_clone$arity$1 = (function (this__5335__auto__){
var self__ = this;
var this__5335__auto____$1 = this;
return (new shadow.dom.Size(self__.w,self__.h,self__.__meta,self__.__extmap,self__.__hash));
}));

(shadow.dom.Size.prototype.cljs$core$ICounted$_count$arity$1 = (function (this__5344__auto__){
var self__ = this;
var this__5344__auto____$1 = this;
return (2 + cljs.core.count(self__.__extmap));
}));

(shadow.dom.Size.prototype.cljs$core$IHash$_hash$arity$1 = (function (this__5336__auto__){
var self__ = this;
var this__5336__auto____$1 = this;
var h__5152__auto__ = self__.__hash;
if((!((h__5152__auto__ == null)))){
return h__5152__auto__;
} else {
var h__5152__auto____$1 = (function (coll__5337__auto__){
return (-1228019642 ^ cljs.core.hash_unordered_coll(coll__5337__auto__));
})(this__5336__auto____$1);
(self__.__hash = h__5152__auto____$1);

return h__5152__auto____$1;
}
}));

(shadow.dom.Size.prototype.cljs$core$IEquiv$_equiv$arity$2 = (function (this68586,other68587){
var self__ = this;
var this68586__$1 = this;
return (((!((other68587 == null)))) && ((((this68586__$1.constructor === other68587.constructor)) && (((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this68586__$1.w,other68587.w)) && (((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this68586__$1.h,other68587.h)) && (cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(this68586__$1.__extmap,other68587.__extmap)))))))));
}));

(shadow.dom.Size.prototype.cljs$core$IMap$_dissoc$arity$2 = (function (this__5350__auto__,k__5351__auto__){
var self__ = this;
var this__5350__auto____$1 = this;
if(cljs.core.contains_QMARK_(new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"w","w",354169001),null,new cljs.core.Keyword(null,"h","h",1109658740),null], null), null),k__5351__auto__)){
return cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(cljs.core._with_meta(cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,this__5350__auto____$1),self__.__meta),k__5351__auto__);
} else {
return (new shadow.dom.Size(self__.w,self__.h,self__.__meta,cljs.core.not_empty(cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(self__.__extmap,k__5351__auto__)),null));
}
}));

(shadow.dom.Size.prototype.cljs$core$IAssociative$_contains_key_QMARK_$arity$2 = (function (this__5347__auto__,k68585){
var self__ = this;
var this__5347__auto____$1 = this;
var G__68636 = k68585;
var G__68636__$1 = (((G__68636 instanceof cljs.core.Keyword))?G__68636.fqn:null);
switch (G__68636__$1) {
case "w":
case "h":
return true;

break;
default:
return cljs.core.contains_QMARK_(self__.__extmap,k68585);

}
}));

(shadow.dom.Size.prototype.cljs$core$IAssociative$_assoc$arity$3 = (function (this__5348__auto__,k__5349__auto__,G__68584){
var self__ = this;
var this__5348__auto____$1 = this;
var pred__68642 = cljs.core.keyword_identical_QMARK_;
var expr__68643 = k__5349__auto__;
if(cljs.core.truth_((pred__68642.cljs$core$IFn$_invoke$arity$2 ? pred__68642.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"w","w",354169001),expr__68643) : pred__68642.call(null,new cljs.core.Keyword(null,"w","w",354169001),expr__68643)))){
return (new shadow.dom.Size(G__68584,self__.h,self__.__meta,self__.__extmap,null));
} else {
if(cljs.core.truth_((pred__68642.cljs$core$IFn$_invoke$arity$2 ? pred__68642.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"h","h",1109658740),expr__68643) : pred__68642.call(null,new cljs.core.Keyword(null,"h","h",1109658740),expr__68643)))){
return (new shadow.dom.Size(self__.w,G__68584,self__.__meta,self__.__extmap,null));
} else {
return (new shadow.dom.Size(self__.w,self__.h,self__.__meta,cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(self__.__extmap,k__5349__auto__,G__68584),null));
}
}
}));

(shadow.dom.Size.prototype.cljs$core$ISeqable$_seq$arity$1 = (function (this__5353__auto__){
var self__ = this;
var this__5353__auto____$1 = this;
return cljs.core.seq(cljs.core.concat.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(new cljs.core.MapEntry(new cljs.core.Keyword(null,"w","w",354169001),self__.w,null)),(new cljs.core.MapEntry(new cljs.core.Keyword(null,"h","h",1109658740),self__.h,null))], null),self__.__extmap));
}));

(shadow.dom.Size.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (this__5339__auto__,G__68584){
var self__ = this;
var this__5339__auto____$1 = this;
return (new shadow.dom.Size(self__.w,self__.h,G__68584,self__.__extmap,self__.__hash));
}));

(shadow.dom.Size.prototype.cljs$core$ICollection$_conj$arity$2 = (function (this__5345__auto__,entry__5346__auto__){
var self__ = this;
var this__5345__auto____$1 = this;
if(cljs.core.vector_QMARK_(entry__5346__auto__)){
return this__5345__auto____$1.cljs$core$IAssociative$_assoc$arity$3(null,cljs.core._nth(entry__5346__auto__,(0)),cljs.core._nth(entry__5346__auto__,(1)));
} else {
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3(cljs.core._conj,this__5345__auto____$1,entry__5346__auto__);
}
}));

(shadow.dom.Size.getBasis = (function (){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"w","w",1994700528,null),new cljs.core.Symbol(null,"h","h",-1544777029,null)], null);
}));

(shadow.dom.Size.cljs$lang$type = true);

(shadow.dom.Size.cljs$lang$ctorPrSeq = (function (this__5386__auto__){
return (new cljs.core.List(null,"shadow.dom/Size",null,(1),null));
}));

(shadow.dom.Size.cljs$lang$ctorPrWriter = (function (this__5386__auto__,writer__5387__auto__){
return cljs.core._write(writer__5387__auto__,"shadow.dom/Size");
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
shadow.dom.map__GT_Size = (function shadow$dom$map__GT_Size(G__68590){
var extmap__5382__auto__ = (function (){var G__68661 = cljs.core.dissoc.cljs$core$IFn$_invoke$arity$variadic(G__68590,new cljs.core.Keyword(null,"w","w",354169001),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"h","h",1109658740)], 0));
if(cljs.core.record_QMARK_(G__68590)){
return cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,G__68661);
} else {
return G__68661;
}
})();
return (new shadow.dom.Size(new cljs.core.Keyword(null,"w","w",354169001).cljs$core$IFn$_invoke$arity$1(G__68590),new cljs.core.Keyword(null,"h","h",1109658740).cljs$core$IFn$_invoke$arity$1(G__68590),null,cljs.core.not_empty(extmap__5382__auto__),null));
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
var a__5630__auto__ = opts;
var l__5631__auto__ = a__5630__auto__.length;
var i = (0);
var ret = cljs.core.PersistentVector.EMPTY;
while(true){
if((i < l__5631__auto__)){
var G__69158 = (i + (1));
var G__69159 = cljs.core.conj.cljs$core$IFn$_invoke$arity$2(ret,(opts[i]["value"]));
i = G__69158;
ret = G__69159;
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
return [cljs.core.str.cljs$core$IFn$_invoke$arity$1(path),"?",clojure.string.join.cljs$core$IFn$_invoke$arity$2("&",cljs.core.map.cljs$core$IFn$_invoke$arity$2((function (p__68717){
var vec__68718 = p__68717;
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68718,(0),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68718,(1),null);
return [cljs.core.name(k),"=",cljs.core.str.cljs$core$IFn$_invoke$arity$1(encodeURIComponent(cljs.core.str.cljs$core$IFn$_invoke$arity$1(v)))].join('');
}),query_params))].join('');
}
});
shadow.dom.redirect = (function shadow$dom$redirect(var_args){
var G__68735 = arguments.length;
switch (G__68735) {
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
var G__69165 = ps;
var G__69166 = (i + (1));
el__$1 = G__69165;
i = G__69166;
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
var vec__68757 = shadow.dom.parse_tag(tag_def);
var tag_name = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68757,(0),null);
var tag_id = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68757,(1),null);
var tag_classes = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68757,(2),null);
var el = document.createElementNS("http://www.w3.org/2000/svg",tag_name);
if(cljs.core.truth_(tag_id)){
el.setAttribute("id",tag_id);
} else {
}

if(cljs.core.truth_(tag_classes)){
el.setAttribute("class",shadow.dom.merge_class_string(new cljs.core.Keyword(null,"class","class",-2030961996).cljs$core$IFn$_invoke$arity$1(props),tag_classes));
} else {
}

var seq__68761_69168 = cljs.core.seq(props);
var chunk__68762_69169 = null;
var count__68763_69170 = (0);
var i__68764_69171 = (0);
while(true){
if((i__68764_69171 < count__68763_69170)){
var vec__68772_69172 = chunk__68762_69169.cljs$core$IIndexed$_nth$arity$2(null,i__68764_69171);
var k_69173 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68772_69172,(0),null);
var v_69174 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68772_69172,(1),null);
el.setAttributeNS((function (){var temp__5753__auto__ = cljs.core.namespace(k_69173);
if(cljs.core.truth_(temp__5753__auto__)){
var ns = temp__5753__auto__;
return cljs.core.get.cljs$core$IFn$_invoke$arity$2(cljs.core.deref(shadow.dom.xmlns),ns);
} else {
return null;
}
})(),cljs.core.name(k_69173),v_69174);


var G__69176 = seq__68761_69168;
var G__69177 = chunk__68762_69169;
var G__69178 = count__68763_69170;
var G__69179 = (i__68764_69171 + (1));
seq__68761_69168 = G__69176;
chunk__68762_69169 = G__69177;
count__68763_69170 = G__69178;
i__68764_69171 = G__69179;
continue;
} else {
var temp__5753__auto___69181 = cljs.core.seq(seq__68761_69168);
if(temp__5753__auto___69181){
var seq__68761_69182__$1 = temp__5753__auto___69181;
if(cljs.core.chunked_seq_QMARK_(seq__68761_69182__$1)){
var c__5565__auto___69183 = cljs.core.chunk_first(seq__68761_69182__$1);
var G__69184 = cljs.core.chunk_rest(seq__68761_69182__$1);
var G__69185 = c__5565__auto___69183;
var G__69186 = cljs.core.count(c__5565__auto___69183);
var G__69187 = (0);
seq__68761_69168 = G__69184;
chunk__68762_69169 = G__69185;
count__68763_69170 = G__69186;
i__68764_69171 = G__69187;
continue;
} else {
var vec__68779_69188 = cljs.core.first(seq__68761_69182__$1);
var k_69189 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68779_69188,(0),null);
var v_69190 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68779_69188,(1),null);
el.setAttributeNS((function (){var temp__5753__auto____$1 = cljs.core.namespace(k_69189);
if(cljs.core.truth_(temp__5753__auto____$1)){
var ns = temp__5753__auto____$1;
return cljs.core.get.cljs$core$IFn$_invoke$arity$2(cljs.core.deref(shadow.dom.xmlns),ns);
} else {
return null;
}
})(),cljs.core.name(k_69189),v_69190);


var G__69191 = cljs.core.next(seq__68761_69182__$1);
var G__69192 = null;
var G__69193 = (0);
var G__69194 = (0);
seq__68761_69168 = G__69191;
chunk__68762_69169 = G__69192;
count__68763_69170 = G__69193;
i__68764_69171 = G__69194;
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
var vec__68790 = shadow.dom.destructure_node(shadow.dom.create_svg_node,structure);
var node = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68790,(0),null);
var node_children = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__68790,(1),null);
var seq__68793_69198 = cljs.core.seq(node_children);
var chunk__68795_69199 = null;
var count__68796_69200 = (0);
var i__68797_69201 = (0);
while(true){
if((i__68797_69201 < count__68796_69200)){
var child_struct_69202 = chunk__68795_69199.cljs$core$IIndexed$_nth$arity$2(null,i__68797_69201);
if((!((child_struct_69202 == null)))){
if(typeof child_struct_69202 === 'string'){
var text_69203 = (node["textContent"]);
(node["textContent"] = [cljs.core.str.cljs$core$IFn$_invoke$arity$1(text_69203),child_struct_69202].join(''));
} else {
var children_69204 = shadow.dom.svg_node(child_struct_69202);
if(cljs.core.seq_QMARK_(children_69204)){
var seq__68837_69205 = cljs.core.seq(children_69204);
var chunk__68839_69206 = null;
var count__68840_69207 = (0);
var i__68841_69208 = (0);
while(true){
if((i__68841_69208 < count__68840_69207)){
var child_69209 = chunk__68839_69206.cljs$core$IIndexed$_nth$arity$2(null,i__68841_69208);
if(cljs.core.truth_(child_69209)){
node.appendChild(child_69209);


var G__69210 = seq__68837_69205;
var G__69211 = chunk__68839_69206;
var G__69212 = count__68840_69207;
var G__69213 = (i__68841_69208 + (1));
seq__68837_69205 = G__69210;
chunk__68839_69206 = G__69211;
count__68840_69207 = G__69212;
i__68841_69208 = G__69213;
continue;
} else {
var G__69214 = seq__68837_69205;
var G__69215 = chunk__68839_69206;
var G__69216 = count__68840_69207;
var G__69217 = (i__68841_69208 + (1));
seq__68837_69205 = G__69214;
chunk__68839_69206 = G__69215;
count__68840_69207 = G__69216;
i__68841_69208 = G__69217;
continue;
}
} else {
var temp__5753__auto___69218 = cljs.core.seq(seq__68837_69205);
if(temp__5753__auto___69218){
var seq__68837_69219__$1 = temp__5753__auto___69218;
if(cljs.core.chunked_seq_QMARK_(seq__68837_69219__$1)){
var c__5565__auto___69220 = cljs.core.chunk_first(seq__68837_69219__$1);
var G__69221 = cljs.core.chunk_rest(seq__68837_69219__$1);
var G__69222 = c__5565__auto___69220;
var G__69223 = cljs.core.count(c__5565__auto___69220);
var G__69224 = (0);
seq__68837_69205 = G__69221;
chunk__68839_69206 = G__69222;
count__68840_69207 = G__69223;
i__68841_69208 = G__69224;
continue;
} else {
var child_69225 = cljs.core.first(seq__68837_69219__$1);
if(cljs.core.truth_(child_69225)){
node.appendChild(child_69225);


var G__69226 = cljs.core.next(seq__68837_69219__$1);
var G__69227 = null;
var G__69228 = (0);
var G__69229 = (0);
seq__68837_69205 = G__69226;
chunk__68839_69206 = G__69227;
count__68840_69207 = G__69228;
i__68841_69208 = G__69229;
continue;
} else {
var G__69230 = cljs.core.next(seq__68837_69219__$1);
var G__69231 = null;
var G__69232 = (0);
var G__69233 = (0);
seq__68837_69205 = G__69230;
chunk__68839_69206 = G__69231;
count__68840_69207 = G__69232;
i__68841_69208 = G__69233;
continue;
}
}
} else {
}
}
break;
}
} else {
node.appendChild(children_69204);
}
}


var G__69234 = seq__68793_69198;
var G__69235 = chunk__68795_69199;
var G__69236 = count__68796_69200;
var G__69237 = (i__68797_69201 + (1));
seq__68793_69198 = G__69234;
chunk__68795_69199 = G__69235;
count__68796_69200 = G__69236;
i__68797_69201 = G__69237;
continue;
} else {
var G__69238 = seq__68793_69198;
var G__69239 = chunk__68795_69199;
var G__69240 = count__68796_69200;
var G__69241 = (i__68797_69201 + (1));
seq__68793_69198 = G__69238;
chunk__68795_69199 = G__69239;
count__68796_69200 = G__69240;
i__68797_69201 = G__69241;
continue;
}
} else {
var temp__5753__auto___69242 = cljs.core.seq(seq__68793_69198);
if(temp__5753__auto___69242){
var seq__68793_69243__$1 = temp__5753__auto___69242;
if(cljs.core.chunked_seq_QMARK_(seq__68793_69243__$1)){
var c__5565__auto___69244 = cljs.core.chunk_first(seq__68793_69243__$1);
var G__69245 = cljs.core.chunk_rest(seq__68793_69243__$1);
var G__69246 = c__5565__auto___69244;
var G__69247 = cljs.core.count(c__5565__auto___69244);
var G__69248 = (0);
seq__68793_69198 = G__69245;
chunk__68795_69199 = G__69246;
count__68796_69200 = G__69247;
i__68797_69201 = G__69248;
continue;
} else {
var child_struct_69249 = cljs.core.first(seq__68793_69243__$1);
if((!((child_struct_69249 == null)))){
if(typeof child_struct_69249 === 'string'){
var text_69250 = (node["textContent"]);
(node["textContent"] = [cljs.core.str.cljs$core$IFn$_invoke$arity$1(text_69250),child_struct_69249].join(''));
} else {
var children_69251 = shadow.dom.svg_node(child_struct_69249);
if(cljs.core.seq_QMARK_(children_69251)){
var seq__68850_69252 = cljs.core.seq(children_69251);
var chunk__68852_69253 = null;
var count__68853_69254 = (0);
var i__68854_69255 = (0);
while(true){
if((i__68854_69255 < count__68853_69254)){
var child_69256 = chunk__68852_69253.cljs$core$IIndexed$_nth$arity$2(null,i__68854_69255);
if(cljs.core.truth_(child_69256)){
node.appendChild(child_69256);


var G__69257 = seq__68850_69252;
var G__69258 = chunk__68852_69253;
var G__69259 = count__68853_69254;
var G__69260 = (i__68854_69255 + (1));
seq__68850_69252 = G__69257;
chunk__68852_69253 = G__69258;
count__68853_69254 = G__69259;
i__68854_69255 = G__69260;
continue;
} else {
var G__69261 = seq__68850_69252;
var G__69262 = chunk__68852_69253;
var G__69263 = count__68853_69254;
var G__69264 = (i__68854_69255 + (1));
seq__68850_69252 = G__69261;
chunk__68852_69253 = G__69262;
count__68853_69254 = G__69263;
i__68854_69255 = G__69264;
continue;
}
} else {
var temp__5753__auto___69265__$1 = cljs.core.seq(seq__68850_69252);
if(temp__5753__auto___69265__$1){
var seq__68850_69266__$1 = temp__5753__auto___69265__$1;
if(cljs.core.chunked_seq_QMARK_(seq__68850_69266__$1)){
var c__5565__auto___69267 = cljs.core.chunk_first(seq__68850_69266__$1);
var G__69268 = cljs.core.chunk_rest(seq__68850_69266__$1);
var G__69269 = c__5565__auto___69267;
var G__69270 = cljs.core.count(c__5565__auto___69267);
var G__69271 = (0);
seq__68850_69252 = G__69268;
chunk__68852_69253 = G__69269;
count__68853_69254 = G__69270;
i__68854_69255 = G__69271;
continue;
} else {
var child_69272 = cljs.core.first(seq__68850_69266__$1);
if(cljs.core.truth_(child_69272)){
node.appendChild(child_69272);


var G__69273 = cljs.core.next(seq__68850_69266__$1);
var G__69274 = null;
var G__69275 = (0);
var G__69276 = (0);
seq__68850_69252 = G__69273;
chunk__68852_69253 = G__69274;
count__68853_69254 = G__69275;
i__68854_69255 = G__69276;
continue;
} else {
var G__69277 = cljs.core.next(seq__68850_69266__$1);
var G__69278 = null;
var G__69279 = (0);
var G__69280 = (0);
seq__68850_69252 = G__69277;
chunk__68852_69253 = G__69278;
count__68853_69254 = G__69279;
i__68854_69255 = G__69280;
continue;
}
}
} else {
}
}
break;
}
} else {
node.appendChild(children_69251);
}
}


var G__69281 = cljs.core.next(seq__68793_69243__$1);
var G__69282 = null;
var G__69283 = (0);
var G__69284 = (0);
seq__68793_69198 = G__69281;
chunk__68795_69199 = G__69282;
count__68796_69200 = G__69283;
i__68797_69201 = G__69284;
continue;
} else {
var G__69285 = cljs.core.next(seq__68793_69243__$1);
var G__69286 = null;
var G__69287 = (0);
var G__69288 = (0);
seq__68793_69198 = G__69285;
chunk__68795_69199 = G__69286;
count__68796_69200 = G__69287;
i__68797_69201 = G__69288;
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
var args__5772__auto__ = [];
var len__5766__auto___69292 = arguments.length;
var i__5767__auto___69293 = (0);
while(true){
if((i__5767__auto___69293 < len__5766__auto___69292)){
args__5772__auto__.push((arguments[i__5767__auto___69293]));

var G__69294 = (i__5767__auto___69293 + (1));
i__5767__auto___69293 = G__69294;
continue;
} else {
}
break;
}

var argseq__5773__auto__ = ((((1) < args__5772__auto__.length))?(new cljs.core.IndexedSeq(args__5772__auto__.slice((1)),(0),null)):null);
return shadow.dom.svg.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__5773__auto__);
});

(shadow.dom.svg.cljs$core$IFn$_invoke$arity$variadic = (function (attrs,children){
return shadow.dom._to_svg(cljs.core.vec(cljs.core.concat.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"svg","svg",856789142),attrs], null),children)));
}));

(shadow.dom.svg.cljs$lang$maxFixedArity = (1));

/** @this {Function} */
(shadow.dom.svg.cljs$lang$applyTo = (function (seq68865){
var G__68866 = cljs.core.first(seq68865);
var seq68865__$1 = cljs.core.next(seq68865);
var self__5751__auto__ = this;
return self__5751__auto__.cljs$core$IFn$_invoke$arity$variadic(G__68866,seq68865__$1);
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
var G__68878 = arguments.length;
switch (G__68878) {
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

if(cljs.core.truth_((function (){var and__5041__auto__ = once_or_cleanup;
if(cljs.core.truth_(and__5041__auto__)){
return (!(once_or_cleanup === true));
} else {
return and__5041__auto__;
}
})())){
var c__35508__auto___69296 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_68886){
var state_val_68887 = (state_68886[(1)]);
if((state_val_68887 === (1))){
var state_68886__$1 = state_68886;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_68886__$1,(2),once_or_cleanup);
} else {
if((state_val_68887 === (2))){
var inst_68883 = (state_68886[(2)]);
var inst_68884 = shadow.dom.remove_event_handler(el,event,event_fn);
var state_68886__$1 = (function (){var statearr_68888 = state_68886;
(statearr_68888[(7)] = inst_68883);

return statearr_68888;
})();
return cljs.core.async.impl.ioc_helpers.return_chan(state_68886__$1,inst_68884);
} else {
return null;
}
}
});
return (function() {
var shadow$dom$state_machine__35424__auto__ = null;
var shadow$dom$state_machine__35424__auto____0 = (function (){
var statearr_68889 = [null,null,null,null,null,null,null,null];
(statearr_68889[(0)] = shadow$dom$state_machine__35424__auto__);

(statearr_68889[(1)] = (1));

return statearr_68889;
});
var shadow$dom$state_machine__35424__auto____1 = (function (state_68886){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_68886);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e68890){var ex__35427__auto__ = e68890;
var statearr_68891_69297 = state_68886;
(statearr_68891_69297[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_68886[(4)]))){
var statearr_68892_69300 = state_68886;
(statearr_68892_69300[(1)] = cljs.core.first((state_68886[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__69302 = state_68886;
state_68886 = G__69302;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
shadow$dom$state_machine__35424__auto__ = function(state_68886){
switch(arguments.length){
case 0:
return shadow$dom$state_machine__35424__auto____0.call(this);
case 1:
return shadow$dom$state_machine__35424__auto____1.call(this,state_68886);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
shadow$dom$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = shadow$dom$state_machine__35424__auto____0;
shadow$dom$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = shadow$dom$state_machine__35424__auto____1;
return shadow$dom$state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_68893 = f__35509__auto__();
(statearr_68893[(6)] = c__35508__auto___69296);

return statearr_68893;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));

} else {
}

return chan;
}));

(shadow.dom.event_chan.cljs$lang$maxFixedArity = 4);


//# sourceMappingURL=shadow.dom.js.map
