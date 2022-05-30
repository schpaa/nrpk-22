goog.provide('reagent.debug');
reagent.debug.has_console = (typeof console !== 'undefined');
reagent.debug.tracking = false;
if((typeof reagent !== 'undefined') && (typeof reagent.debug !== 'undefined') && (typeof reagent.debug.warnings !== 'undefined')){
} else {
reagent.debug.warnings = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(null);
}
if((typeof reagent !== 'undefined') && (typeof reagent.debug !== 'undefined') && (typeof reagent.debug.track_console !== 'undefined')){
} else {
reagent.debug.track_console = (function (){var o = ({});
(o.warn = (function() { 
var G__72143__delegate = function (args){
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$variadic(reagent.debug.warnings,cljs.core.update_in,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"warn","warn",-436710552)], null),cljs.core.conj,cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.str,args)], 0));
};
var G__72143 = function (var_args){
var args = null;
if (arguments.length > 0) {
var G__72144__i = 0, G__72144__a = new Array(arguments.length -  0);
while (G__72144__i < G__72144__a.length) {G__72144__a[G__72144__i] = arguments[G__72144__i + 0]; ++G__72144__i;}
  args = new cljs.core.IndexedSeq(G__72144__a,0,null);
} 
return G__72143__delegate.call(this,args);};
G__72143.cljs$lang$maxFixedArity = 0;
G__72143.cljs$lang$applyTo = (function (arglist__72145){
var args = cljs.core.seq(arglist__72145);
return G__72143__delegate(args);
});
G__72143.cljs$core$IFn$_invoke$arity$variadic = G__72143__delegate;
return G__72143;
})()
);

(o.error = (function() { 
var G__72146__delegate = function (args){
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$variadic(reagent.debug.warnings,cljs.core.update_in,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"error","error",-978969032)], null),cljs.core.conj,cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.str,args)], 0));
};
var G__72146 = function (var_args){
var args = null;
if (arguments.length > 0) {
var G__72147__i = 0, G__72147__a = new Array(arguments.length -  0);
while (G__72147__i < G__72147__a.length) {G__72147__a[G__72147__i] = arguments[G__72147__i + 0]; ++G__72147__i;}
  args = new cljs.core.IndexedSeq(G__72147__a,0,null);
} 
return G__72146__delegate.call(this,args);};
G__72146.cljs$lang$maxFixedArity = 0;
G__72146.cljs$lang$applyTo = (function (arglist__72148){
var args = cljs.core.seq(arglist__72148);
return G__72146__delegate(args);
});
G__72146.cljs$core$IFn$_invoke$arity$variadic = G__72146__delegate;
return G__72146;
})()
);

return o;
})();
}
reagent.debug.track_warnings = (function reagent$debug$track_warnings(f){
(reagent.debug.tracking = true);

cljs.core.reset_BANG_(reagent.debug.warnings,null);

(f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null));

var warns = cljs.core.deref(reagent.debug.warnings);
cljs.core.reset_BANG_(reagent.debug.warnings,null);

(reagent.debug.tracking = false);

return warns;
});

//# sourceMappingURL=reagent.debug.js.map
