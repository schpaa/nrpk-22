goog.provide('reagent.dom');
var module$node_modules$react_dom$index=shadow.js.require("module$node_modules$react_dom$index", {});
if((typeof reagent !== 'undefined') && (typeof reagent.dom !== 'undefined') && (typeof reagent.dom.roots !== 'undefined')){
} else {
reagent.dom.roots = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(cljs.core.PersistentArrayMap.EMPTY);
}
reagent.dom.unmount_comp = (function reagent$dom$unmount_comp(container){
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(reagent.dom.roots,cljs.core.dissoc,container);

return module$node_modules$react_dom$index.unmountComponentAtNode(container);
});
reagent.dom.render_comp = (function reagent$dom$render_comp(comp,container,callback){
var _STAR_always_update_STAR__orig_val__53017 = reagent.impl.util._STAR_always_update_STAR_;
var _STAR_always_update_STAR__temp_val__53018 = true;
(reagent.impl.util._STAR_always_update_STAR_ = _STAR_always_update_STAR__temp_val__53018);

try{return module$node_modules$react_dom$index.render((comp.cljs$core$IFn$_invoke$arity$0 ? comp.cljs$core$IFn$_invoke$arity$0() : comp.call(null)),container,(function (){
var _STAR_always_update_STAR__orig_val__53019 = reagent.impl.util._STAR_always_update_STAR_;
var _STAR_always_update_STAR__temp_val__53020 = false;
(reagent.impl.util._STAR_always_update_STAR_ = _STAR_always_update_STAR__temp_val__53020);

try{cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(reagent.dom.roots,cljs.core.assoc,container,comp);

reagent.impl.batching.flush_after_render();

if((!((callback == null)))){
return (callback.cljs$core$IFn$_invoke$arity$0 ? callback.cljs$core$IFn$_invoke$arity$0() : callback.call(null));
} else {
return null;
}
}finally {(reagent.impl.util._STAR_always_update_STAR_ = _STAR_always_update_STAR__orig_val__53019);
}}));
}finally {(reagent.impl.util._STAR_always_update_STAR_ = _STAR_always_update_STAR__orig_val__53017);
}});
reagent.dom.re_render_component = (function reagent$dom$re_render_component(comp,container){
return reagent.dom.render_comp(comp,container,null);
});
/**
 * Render a Reagent component into the DOM. The first argument may be
 *   either a vector (using Reagent's Hiccup syntax), or a React element.
 *   The second argument should be a DOM node.
 * 
 *   Optionally takes a callback that is called when the component is in place.
 * 
 *   Returns the mounted component instance.
 */
reagent.dom.render = (function reagent$dom$render(var_args){
var G__53031 = arguments.length;
switch (G__53031) {
case 2:
return reagent.dom.render.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return reagent.dom.render.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(reagent.dom.render.cljs$core$IFn$_invoke$arity$2 = (function (comp,container){
return reagent.dom.render.cljs$core$IFn$_invoke$arity$3(comp,container,reagent.impl.template.default_compiler);
}));

(reagent.dom.render.cljs$core$IFn$_invoke$arity$3 = (function (comp,container,callback_or_compiler){
reagent.ratom.flush_BANG_();

var vec__53037 = ((cljs.core.fn_QMARK_(callback_or_compiler))?new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [reagent.impl.template.default_compiler,callback_or_compiler], null):new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [callback_or_compiler,new cljs.core.Keyword(null,"callback","callback",-705136228).cljs$core$IFn$_invoke$arity$1(callback_or_compiler)], null));
var compiler = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53037,(0),null);
var callback = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53037,(1),null);
var f = (function (){
return reagent.impl.protocols.as_element(compiler,((cljs.core.fn_QMARK_(comp))?(comp.cljs$core$IFn$_invoke$arity$0 ? comp.cljs$core$IFn$_invoke$arity$0() : comp.call(null)):comp));
});
return reagent.dom.render_comp(f,container,callback);
}));

(reagent.dom.render.cljs$lang$maxFixedArity = 3);

/**
 * Remove a component from the given DOM node.
 */
reagent.dom.unmount_component_at_node = (function reagent$dom$unmount_component_at_node(container){
return reagent.dom.unmount_comp(container);
});
/**
 * Returns the root DOM node of a mounted component.
 */
reagent.dom.dom_node = (function reagent$dom$dom_node(this$){
return module$node_modules$react_dom$index.findDOMNode(this$);
});
/**
 * Force re-rendering of all mounted Reagent components. This is
 *   probably only useful in a development environment, when you want to
 *   update components in response to some dynamic changes to code.
 * 
 *   Note that force-update-all may not update root components. This
 *   happens if a component 'foo' is mounted with `(render [foo])` (since
 *   functions are passed by value, and not by reference, in
 *   ClojureScript). To get around this you'll have to introduce a layer
 *   of indirection, for example by using `(render [#'foo])` instead.
 */
reagent.dom.force_update_all = (function reagent$dom$force_update_all(){
reagent.ratom.flush_BANG_();

var seq__53043_53084 = cljs.core.seq(cljs.core.deref(reagent.dom.roots));
var chunk__53044_53085 = null;
var count__53045_53086 = (0);
var i__53046_53087 = (0);
while(true){
if((i__53046_53087 < count__53045_53086)){
var vec__53064_53088 = chunk__53044_53085.cljs$core$IIndexed$_nth$arity$2(null,i__53046_53087);
var container_53089 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53064_53088,(0),null);
var comp_53090 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53064_53088,(1),null);
reagent.dom.re_render_component(comp_53090,container_53089);


var G__53095 = seq__53043_53084;
var G__53096 = chunk__53044_53085;
var G__53097 = count__53045_53086;
var G__53098 = (i__53046_53087 + (1));
seq__53043_53084 = G__53095;
chunk__53044_53085 = G__53096;
count__53045_53086 = G__53097;
i__53046_53087 = G__53098;
continue;
} else {
var temp__5753__auto___53099 = cljs.core.seq(seq__53043_53084);
if(temp__5753__auto___53099){
var seq__53043_53100__$1 = temp__5753__auto___53099;
if(cljs.core.chunked_seq_QMARK_(seq__53043_53100__$1)){
var c__4679__auto___53101 = cljs.core.chunk_first(seq__53043_53100__$1);
var G__53102 = cljs.core.chunk_rest(seq__53043_53100__$1);
var G__53103 = c__4679__auto___53101;
var G__53104 = cljs.core.count(c__4679__auto___53101);
var G__53105 = (0);
seq__53043_53084 = G__53102;
chunk__53044_53085 = G__53103;
count__53045_53086 = G__53104;
i__53046_53087 = G__53105;
continue;
} else {
var vec__53072_53106 = cljs.core.first(seq__53043_53100__$1);
var container_53107 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53072_53106,(0),null);
var comp_53108 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53072_53106,(1),null);
reagent.dom.re_render_component(comp_53108,container_53107);


var G__53109 = cljs.core.next(seq__53043_53100__$1);
var G__53110 = null;
var G__53111 = (0);
var G__53112 = (0);
seq__53043_53084 = G__53109;
chunk__53044_53085 = G__53110;
count__53045_53086 = G__53111;
i__53046_53087 = G__53112;
continue;
}
} else {
}
}
break;
}

return reagent.impl.batching.flush_after_render();
});

//# sourceMappingURL=reagent.dom.js.map
