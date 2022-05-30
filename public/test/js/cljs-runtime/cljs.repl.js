goog.provide('cljs.repl');
cljs.repl.print_doc = (function cljs$repl$print_doc(p__69325){
var map__69326 = p__69325;
var map__69326__$1 = cljs.core.__destructure_map(map__69326);
var m = map__69326__$1;
var n = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69326__$1,new cljs.core.Keyword(null,"ns","ns",441598760));
var nm = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69326__$1,new cljs.core.Keyword(null,"name","name",1843675177));
cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["-------------------------"], 0));

cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([(function (){var or__5043__auto__ = new cljs.core.Keyword(null,"spec","spec",347520401).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
return [(function (){var temp__5753__auto__ = new cljs.core.Keyword(null,"ns","ns",441598760).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(temp__5753__auto__)){
var ns = temp__5753__auto__;
return [cljs.core.str.cljs$core$IFn$_invoke$arity$1(ns),"/"].join('');
} else {
return null;
}
})(),cljs.core.str.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"name","name",1843675177).cljs$core$IFn$_invoke$arity$1(m))].join('');
}
})()], 0));

if(cljs.core.truth_(new cljs.core.Keyword(null,"protocol","protocol",652470118).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["Protocol"], 0));
} else {
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"forms","forms",2045992350).cljs$core$IFn$_invoke$arity$1(m))){
var seq__69330_69570 = cljs.core.seq(new cljs.core.Keyword(null,"forms","forms",2045992350).cljs$core$IFn$_invoke$arity$1(m));
var chunk__69331_69571 = null;
var count__69332_69572 = (0);
var i__69333_69573 = (0);
while(true){
if((i__69333_69573 < count__69332_69572)){
var f_69574 = chunk__69331_69571.cljs$core$IIndexed$_nth$arity$2(null,i__69333_69573);
cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["  ",f_69574], 0));


var G__69575 = seq__69330_69570;
var G__69576 = chunk__69331_69571;
var G__69577 = count__69332_69572;
var G__69578 = (i__69333_69573 + (1));
seq__69330_69570 = G__69575;
chunk__69331_69571 = G__69576;
count__69332_69572 = G__69577;
i__69333_69573 = G__69578;
continue;
} else {
var temp__5753__auto___69579 = cljs.core.seq(seq__69330_69570);
if(temp__5753__auto___69579){
var seq__69330_69580__$1 = temp__5753__auto___69579;
if(cljs.core.chunked_seq_QMARK_(seq__69330_69580__$1)){
var c__5565__auto___69581 = cljs.core.chunk_first(seq__69330_69580__$1);
var G__69582 = cljs.core.chunk_rest(seq__69330_69580__$1);
var G__69583 = c__5565__auto___69581;
var G__69584 = cljs.core.count(c__5565__auto___69581);
var G__69585 = (0);
seq__69330_69570 = G__69582;
chunk__69331_69571 = G__69583;
count__69332_69572 = G__69584;
i__69333_69573 = G__69585;
continue;
} else {
var f_69586 = cljs.core.first(seq__69330_69580__$1);
cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["  ",f_69586], 0));


var G__69587 = cljs.core.next(seq__69330_69580__$1);
var G__69588 = null;
var G__69589 = (0);
var G__69590 = (0);
seq__69330_69570 = G__69587;
chunk__69331_69571 = G__69588;
count__69332_69572 = G__69589;
i__69333_69573 = G__69590;
continue;
}
} else {
}
}
break;
}
} else {
if(cljs.core.truth_(new cljs.core.Keyword(null,"arglists","arglists",1661989754).cljs$core$IFn$_invoke$arity$1(m))){
var arglists_69591 = new cljs.core.Keyword(null,"arglists","arglists",1661989754).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_((function (){var or__5043__auto__ = new cljs.core.Keyword(null,"macro","macro",-867863404).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
return new cljs.core.Keyword(null,"repl-special-function","repl-special-function",1262603725).cljs$core$IFn$_invoke$arity$1(m);
}
})())){
cljs.core.prn.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([arglists_69591], 0));
} else {
cljs.core.prn.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Symbol(null,"quote","quote",1377916282,null),cljs.core.first(arglists_69591)))?cljs.core.second(arglists_69591):arglists_69591)], 0));
}
} else {
}
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"special-form","special-form",-1326536374).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["Special Form"], 0));

cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([" ",new cljs.core.Keyword(null,"doc","doc",1913296891).cljs$core$IFn$_invoke$arity$1(m)], 0));

if(cljs.core.contains_QMARK_(m,new cljs.core.Keyword(null,"url","url",276297046))){
if(cljs.core.truth_(new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(m))){
return cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([["\n  Please see http://clojure.org/",cljs.core.str.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(m))].join('')], 0));
} else {
return null;
}
} else {
return cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([["\n  Please see http://clojure.org/special_forms#",cljs.core.str.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"name","name",1843675177).cljs$core$IFn$_invoke$arity$1(m))].join('')], 0));
}
} else {
if(cljs.core.truth_(new cljs.core.Keyword(null,"macro","macro",-867863404).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["Macro"], 0));
} else {
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"spec","spec",347520401).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["Spec"], 0));
} else {
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"repl-special-function","repl-special-function",1262603725).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["REPL Special Function"], 0));
} else {
}

cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([" ",new cljs.core.Keyword(null,"doc","doc",1913296891).cljs$core$IFn$_invoke$arity$1(m)], 0));

if(cljs.core.truth_(new cljs.core.Keyword(null,"protocol","protocol",652470118).cljs$core$IFn$_invoke$arity$1(m))){
var seq__69339_69593 = cljs.core.seq(new cljs.core.Keyword(null,"methods","methods",453930866).cljs$core$IFn$_invoke$arity$1(m));
var chunk__69340_69594 = null;
var count__69341_69595 = (0);
var i__69342_69596 = (0);
while(true){
if((i__69342_69596 < count__69341_69595)){
var vec__69359_69597 = chunk__69340_69594.cljs$core$IIndexed$_nth$arity$2(null,i__69342_69596);
var name_69598 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__69359_69597,(0),null);
var map__69362_69599 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__69359_69597,(1),null);
var map__69362_69600__$1 = cljs.core.__destructure_map(map__69362_69599);
var doc_69601 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69362_69600__$1,new cljs.core.Keyword(null,"doc","doc",1913296891));
var arglists_69602 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69362_69600__$1,new cljs.core.Keyword(null,"arglists","arglists",1661989754));
cljs.core.println();

cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([" ",name_69598], 0));

cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([" ",arglists_69602], 0));

if(cljs.core.truth_(doc_69601)){
cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([" ",doc_69601], 0));
} else {
}


var G__69603 = seq__69339_69593;
var G__69604 = chunk__69340_69594;
var G__69605 = count__69341_69595;
var G__69606 = (i__69342_69596 + (1));
seq__69339_69593 = G__69603;
chunk__69340_69594 = G__69604;
count__69341_69595 = G__69605;
i__69342_69596 = G__69606;
continue;
} else {
var temp__5753__auto___69607 = cljs.core.seq(seq__69339_69593);
if(temp__5753__auto___69607){
var seq__69339_69608__$1 = temp__5753__auto___69607;
if(cljs.core.chunked_seq_QMARK_(seq__69339_69608__$1)){
var c__5565__auto___69609 = cljs.core.chunk_first(seq__69339_69608__$1);
var G__69610 = cljs.core.chunk_rest(seq__69339_69608__$1);
var G__69611 = c__5565__auto___69609;
var G__69612 = cljs.core.count(c__5565__auto___69609);
var G__69613 = (0);
seq__69339_69593 = G__69610;
chunk__69340_69594 = G__69611;
count__69341_69595 = G__69612;
i__69342_69596 = G__69613;
continue;
} else {
var vec__69364_69614 = cljs.core.first(seq__69339_69608__$1);
var name_69615 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__69364_69614,(0),null);
var map__69367_69616 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__69364_69614,(1),null);
var map__69367_69617__$1 = cljs.core.__destructure_map(map__69367_69616);
var doc_69618 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69367_69617__$1,new cljs.core.Keyword(null,"doc","doc",1913296891));
var arglists_69619 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69367_69617__$1,new cljs.core.Keyword(null,"arglists","arglists",1661989754));
cljs.core.println();

cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([" ",name_69615], 0));

cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([" ",arglists_69619], 0));

if(cljs.core.truth_(doc_69618)){
cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([" ",doc_69618], 0));
} else {
}


var G__69620 = cljs.core.next(seq__69339_69608__$1);
var G__69621 = null;
var G__69622 = (0);
var G__69623 = (0);
seq__69339_69593 = G__69620;
chunk__69340_69594 = G__69621;
count__69341_69595 = G__69622;
i__69342_69596 = G__69623;
continue;
}
} else {
}
}
break;
}
} else {
}

if(cljs.core.truth_(n)){
var temp__5753__auto__ = cljs.spec.alpha.get_spec(cljs.core.symbol.cljs$core$IFn$_invoke$arity$2(cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.ns_name(n)),cljs.core.name(nm)));
if(cljs.core.truth_(temp__5753__auto__)){
var fnspec = temp__5753__auto__;
cljs.core.print.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["Spec"], 0));

var seq__69368 = cljs.core.seq(new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"args","args",1315556576),new cljs.core.Keyword(null,"ret","ret",-468222814),new cljs.core.Keyword(null,"fn","fn",-1175266204)], null));
var chunk__69369 = null;
var count__69370 = (0);
var i__69371 = (0);
while(true){
if((i__69371 < count__69370)){
var role = chunk__69369.cljs$core$IIndexed$_nth$arity$2(null,i__69371);
var temp__5753__auto___69628__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(fnspec,role);
if(cljs.core.truth_(temp__5753__auto___69628__$1)){
var spec_69629 = temp__5753__auto___69628__$1;
cljs.core.print.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([["\n ",cljs.core.name(role),":"].join(''),cljs.spec.alpha.describe(spec_69629)], 0));
} else {
}


var G__69630 = seq__69368;
var G__69631 = chunk__69369;
var G__69632 = count__69370;
var G__69633 = (i__69371 + (1));
seq__69368 = G__69630;
chunk__69369 = G__69631;
count__69370 = G__69632;
i__69371 = G__69633;
continue;
} else {
var temp__5753__auto____$1 = cljs.core.seq(seq__69368);
if(temp__5753__auto____$1){
var seq__69368__$1 = temp__5753__auto____$1;
if(cljs.core.chunked_seq_QMARK_(seq__69368__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__69368__$1);
var G__69634 = cljs.core.chunk_rest(seq__69368__$1);
var G__69635 = c__5565__auto__;
var G__69636 = cljs.core.count(c__5565__auto__);
var G__69637 = (0);
seq__69368 = G__69634;
chunk__69369 = G__69635;
count__69370 = G__69636;
i__69371 = G__69637;
continue;
} else {
var role = cljs.core.first(seq__69368__$1);
var temp__5753__auto___69638__$2 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(fnspec,role);
if(cljs.core.truth_(temp__5753__auto___69638__$2)){
var spec_69639 = temp__5753__auto___69638__$2;
cljs.core.print.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([["\n ",cljs.core.name(role),":"].join(''),cljs.spec.alpha.describe(spec_69639)], 0));
} else {
}


var G__69640 = cljs.core.next(seq__69368__$1);
var G__69641 = null;
var G__69642 = (0);
var G__69643 = (0);
seq__69368 = G__69640;
chunk__69369 = G__69641;
count__69370 = G__69642;
i__69371 = G__69643;
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
} else {
return null;
}
}
});
/**
 * Constructs a data representation for a Error with keys:
 *  :cause - root cause message
 *  :phase - error phase
 *  :via - cause chain, with cause keys:
 *           :type - exception class symbol
 *           :message - exception message
 *           :data - ex-data
 *           :at - top stack element
 *  :trace - root cause stack elements
 */
cljs.repl.Error__GT_map = (function cljs$repl$Error__GT_map(o){
var base = (function (t){
return cljs.core.merge.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"type","type",1174270348),(((t instanceof cljs.core.ExceptionInfo))?new cljs.core.Symbol("cljs.core","ExceptionInfo","cljs.core/ExceptionInfo",701839050,null):(((t instanceof Error))?cljs.core.symbol.cljs$core$IFn$_invoke$arity$2("js",t.name):null
))], null),(function (){var temp__5753__auto__ = cljs.core.ex_message(t);
if(cljs.core.truth_(temp__5753__auto__)){
var msg = temp__5753__auto__;
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"message","message",-406056002),msg], null);
} else {
return null;
}
})(),(function (){var temp__5753__auto__ = cljs.core.ex_data(t);
if(cljs.core.truth_(temp__5753__auto__)){
var ed = temp__5753__auto__;
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"data","data",-232669377),ed], null);
} else {
return null;
}
})()], 0));
});
var via = (function (){var via = cljs.core.PersistentVector.EMPTY;
var t = o;
while(true){
if(cljs.core.truth_(t)){
var G__69650 = cljs.core.conj.cljs$core$IFn$_invoke$arity$2(via,t);
var G__69651 = cljs.core.ex_cause(t);
via = G__69650;
t = G__69651;
continue;
} else {
return via;
}
break;
}
})();
var root = cljs.core.peek(via);
return cljs.core.merge.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"via","via",-1904457336),cljs.core.vec(cljs.core.map.cljs$core$IFn$_invoke$arity$2(base,via)),new cljs.core.Keyword(null,"trace","trace",-1082747415),null], null),(function (){var temp__5753__auto__ = cljs.core.ex_message(root);
if(cljs.core.truth_(temp__5753__auto__)){
var root_msg = temp__5753__auto__;
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"cause","cause",231901252),root_msg], null);
} else {
return null;
}
})(),(function (){var temp__5753__auto__ = cljs.core.ex_data(root);
if(cljs.core.truth_(temp__5753__auto__)){
var data = temp__5753__auto__;
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"data","data",-232669377),data], null);
} else {
return null;
}
})(),(function (){var temp__5753__auto__ = new cljs.core.Keyword("clojure.error","phase","clojure.error/phase",275140358).cljs$core$IFn$_invoke$arity$1(cljs.core.ex_data(o));
if(cljs.core.truth_(temp__5753__auto__)){
var phase = temp__5753__auto__;
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"phase","phase",575722892),phase], null);
} else {
return null;
}
})()], 0));
});
/**
 * Returns an analysis of the phase, error, cause, and location of an error that occurred
 *   based on Throwable data, as returned by Throwable->map. All attributes other than phase
 *   are optional:
 *  :clojure.error/phase - keyword phase indicator, one of:
 *    :read-source :compile-syntax-check :compilation :macro-syntax-check :macroexpansion
 *    :execution :read-eval-result :print-eval-result
 *  :clojure.error/source - file name (no path)
 *  :clojure.error/line - integer line number
 *  :clojure.error/column - integer column number
 *  :clojure.error/symbol - symbol being expanded/compiled/invoked
 *  :clojure.error/class - cause exception class symbol
 *  :clojure.error/cause - cause exception message
 *  :clojure.error/spec - explain-data for spec error
 */
cljs.repl.ex_triage = (function cljs$repl$ex_triage(datafied_throwable){
var map__69485 = datafied_throwable;
var map__69485__$1 = cljs.core.__destructure_map(map__69485);
var via = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69485__$1,new cljs.core.Keyword(null,"via","via",-1904457336));
var trace = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69485__$1,new cljs.core.Keyword(null,"trace","trace",-1082747415));
var phase = cljs.core.get.cljs$core$IFn$_invoke$arity$3(map__69485__$1,new cljs.core.Keyword(null,"phase","phase",575722892),new cljs.core.Keyword(null,"execution","execution",253283524));
var map__69486 = cljs.core.last(via);
var map__69486__$1 = cljs.core.__destructure_map(map__69486);
var type = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69486__$1,new cljs.core.Keyword(null,"type","type",1174270348));
var message = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69486__$1,new cljs.core.Keyword(null,"message","message",-406056002));
var data = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69486__$1,new cljs.core.Keyword(null,"data","data",-232669377));
var map__69487 = data;
var map__69487__$1 = cljs.core.__destructure_map(map__69487);
var problems = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69487__$1,new cljs.core.Keyword("cljs.spec.alpha","problems","cljs.spec.alpha/problems",447400814));
var fn = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69487__$1,new cljs.core.Keyword("cljs.spec.alpha","fn","cljs.spec.alpha/fn",408600443));
var caller = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69487__$1,new cljs.core.Keyword("cljs.spec.test.alpha","caller","cljs.spec.test.alpha/caller",-398302390));
var map__69488 = new cljs.core.Keyword(null,"data","data",-232669377).cljs$core$IFn$_invoke$arity$1(cljs.core.first(via));
var map__69488__$1 = cljs.core.__destructure_map(map__69488);
var top_data = map__69488__$1;
var source = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69488__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397));
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3((function (){var G__69490 = phase;
var G__69490__$1 = (((G__69490 instanceof cljs.core.Keyword))?G__69490.fqn:null);
switch (G__69490__$1) {
case "read-source":
var map__69492 = data;
var map__69492__$1 = cljs.core.__destructure_map(map__69492);
var line = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69492__$1,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471));
var column = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69492__$1,new cljs.core.Keyword("clojure.error","column","clojure.error/column",304721553));
var G__69494 = cljs.core.merge.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"data","data",-232669377).cljs$core$IFn$_invoke$arity$1(cljs.core.second(via)),top_data], 0));
var G__69494__$1 = (cljs.core.truth_(source)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__69494,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),source):G__69494);
var G__69494__$2 = (cljs.core.truth_((function (){var fexpr__69495 = new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["NO_SOURCE_PATH",null,"NO_SOURCE_FILE",null], null), null);
return (fexpr__69495.cljs$core$IFn$_invoke$arity$1 ? fexpr__69495.cljs$core$IFn$_invoke$arity$1(source) : fexpr__69495.call(null,source));
})())?cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(G__69494__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397)):G__69494__$1);
if(cljs.core.truth_(message)){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__69494__$2,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message);
} else {
return G__69494__$2;
}

break;
case "compile-syntax-check":
case "compilation":
case "macro-syntax-check":
case "macroexpansion":
var G__69500 = top_data;
var G__69500__$1 = (cljs.core.truth_(source)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__69500,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),source):G__69500);
var G__69500__$2 = (cljs.core.truth_((function (){var fexpr__69501 = new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["NO_SOURCE_PATH",null,"NO_SOURCE_FILE",null], null), null);
return (fexpr__69501.cljs$core$IFn$_invoke$arity$1 ? fexpr__69501.cljs$core$IFn$_invoke$arity$1(source) : fexpr__69501.call(null,source));
})())?cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(G__69500__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397)):G__69500__$1);
var G__69500__$3 = (cljs.core.truth_(type)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__69500__$2,new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890),type):G__69500__$2);
var G__69500__$4 = (cljs.core.truth_(message)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__69500__$3,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message):G__69500__$3);
if(cljs.core.truth_(problems)){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__69500__$4,new cljs.core.Keyword("clojure.error","spec","clojure.error/spec",2055032595),data);
} else {
return G__69500__$4;
}

break;
case "read-eval-result":
case "print-eval-result":
var vec__69502 = cljs.core.first(trace);
var source__$1 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__69502,(0),null);
var method = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__69502,(1),null);
var file = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__69502,(2),null);
var line = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__69502,(3),null);
var G__69505 = top_data;
var G__69505__$1 = (cljs.core.truth_(line)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__69505,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471),line):G__69505);
var G__69505__$2 = (cljs.core.truth_(file)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__69505__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),file):G__69505__$1);
var G__69505__$3 = (cljs.core.truth_((function (){var and__5041__auto__ = source__$1;
if(cljs.core.truth_(and__5041__auto__)){
return method;
} else {
return and__5041__auto__;
}
})())?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__69505__$2,new cljs.core.Keyword("clojure.error","symbol","clojure.error/symbol",1544821994),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[source__$1,method],null))):G__69505__$2);
var G__69505__$4 = (cljs.core.truth_(type)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__69505__$3,new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890),type):G__69505__$3);
if(cljs.core.truth_(message)){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__69505__$4,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message);
} else {
return G__69505__$4;
}

break;
case "execution":
var vec__69507 = cljs.core.first(trace);
var source__$1 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__69507,(0),null);
var method = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__69507,(1),null);
var file = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__69507,(2),null);
var line = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__69507,(3),null);
var file__$1 = cljs.core.first(cljs.core.remove.cljs$core$IFn$_invoke$arity$2((function (p1__69480_SHARP_){
var or__5043__auto__ = (p1__69480_SHARP_ == null);
if(or__5043__auto__){
return or__5043__auto__;
} else {
var fexpr__69511 = new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["NO_SOURCE_PATH",null,"NO_SOURCE_FILE",null], null), null);
return (fexpr__69511.cljs$core$IFn$_invoke$arity$1 ? fexpr__69511.cljs$core$IFn$_invoke$arity$1(p1__69480_SHARP_) : fexpr__69511.call(null,p1__69480_SHARP_));
}
}),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"file","file",-1269645878).cljs$core$IFn$_invoke$arity$1(caller),file], null)));
var err_line = (function (){var or__5043__auto__ = new cljs.core.Keyword(null,"line","line",212345235).cljs$core$IFn$_invoke$arity$1(caller);
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
return line;
}
})();
var G__69512 = new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890),type], null);
var G__69512__$1 = (cljs.core.truth_(err_line)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__69512,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471),err_line):G__69512);
var G__69512__$2 = (cljs.core.truth_(message)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__69512__$1,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message):G__69512__$1);
var G__69512__$3 = (cljs.core.truth_((function (){var or__5043__auto__ = fn;
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
var and__5041__auto__ = source__$1;
if(cljs.core.truth_(and__5041__auto__)){
return method;
} else {
return and__5041__auto__;
}
}
})())?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__69512__$2,new cljs.core.Keyword("clojure.error","symbol","clojure.error/symbol",1544821994),(function (){var or__5043__auto__ = fn;
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
return (new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[source__$1,method],null));
}
})()):G__69512__$2);
var G__69512__$4 = (cljs.core.truth_(file__$1)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__69512__$3,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),file__$1):G__69512__$3);
if(cljs.core.truth_(problems)){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__69512__$4,new cljs.core.Keyword("clojure.error","spec","clojure.error/spec",2055032595),data);
} else {
return G__69512__$4;
}

break;
default:
throw (new Error(["No matching clause: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(G__69490__$1)].join('')));

}
})(),new cljs.core.Keyword("clojure.error","phase","clojure.error/phase",275140358),phase);
});
/**
 * Returns a string from exception data, as produced by ex-triage.
 *   The first line summarizes the exception phase and location.
 *   The subsequent lines describe the cause.
 */
cljs.repl.ex_str = (function cljs$repl$ex_str(p__69520){
var map__69521 = p__69520;
var map__69521__$1 = cljs.core.__destructure_map(map__69521);
var triage_data = map__69521__$1;
var phase = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69521__$1,new cljs.core.Keyword("clojure.error","phase","clojure.error/phase",275140358));
var source = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69521__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397));
var line = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69521__$1,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471));
var column = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69521__$1,new cljs.core.Keyword("clojure.error","column","clojure.error/column",304721553));
var symbol = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69521__$1,new cljs.core.Keyword("clojure.error","symbol","clojure.error/symbol",1544821994));
var class$ = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69521__$1,new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890));
var cause = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69521__$1,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742));
var spec = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__69521__$1,new cljs.core.Keyword("clojure.error","spec","clojure.error/spec",2055032595));
var loc = [cljs.core.str.cljs$core$IFn$_invoke$arity$1((function (){var or__5043__auto__ = source;
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
return "<cljs repl>";
}
})()),":",cljs.core.str.cljs$core$IFn$_invoke$arity$1((function (){var or__5043__auto__ = line;
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
return (1);
}
})()),(cljs.core.truth_(column)?[":",cljs.core.str.cljs$core$IFn$_invoke$arity$1(column)].join(''):"")].join('');
var class_name = cljs.core.name((function (){var or__5043__auto__ = class$;
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
return "";
}
})());
var simple_class = class_name;
var cause_type = ((cljs.core.contains_QMARK_(new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["RuntimeException",null,"Exception",null], null), null),simple_class))?"":[" (",simple_class,")"].join(''));
var format = goog.string.format;
var G__69523 = phase;
var G__69523__$1 = (((G__69523 instanceof cljs.core.Keyword))?G__69523.fqn:null);
switch (G__69523__$1) {
case "read-source":
return (format.cljs$core$IFn$_invoke$arity$3 ? format.cljs$core$IFn$_invoke$arity$3("Syntax error reading source at (%s).\n%s\n",loc,cause) : format.call(null,"Syntax error reading source at (%s).\n%s\n",loc,cause));

break;
case "macro-syntax-check":
var G__69524 = "Syntax error macroexpanding %sat (%s).\n%s";
var G__69525 = (cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):"");
var G__69526 = loc;
var G__69527 = (cljs.core.truth_(spec)?(function (){var sb__5687__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__69528_69668 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__69529_69669 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__69530_69670 = true;
var _STAR_print_fn_STAR__temp_val__69531_69671 = (function (x__5688__auto__){
return sb__5687__auto__.append(x__5688__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__69530_69670);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__69531_69671);

try{cljs.spec.alpha.explain_out(cljs.core.update.cljs$core$IFn$_invoke$arity$3(spec,new cljs.core.Keyword("cljs.spec.alpha","problems","cljs.spec.alpha/problems",447400814),(function (probs){
return cljs.core.map.cljs$core$IFn$_invoke$arity$2((function (p1__69515_SHARP_){
return cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(p1__69515_SHARP_,new cljs.core.Keyword(null,"in","in",-1531184865));
}),probs);
}))
);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__69529_69669);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__69528_69668);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__5687__auto__);
})():(format.cljs$core$IFn$_invoke$arity$2 ? format.cljs$core$IFn$_invoke$arity$2("%s\n",cause) : format.call(null,"%s\n",cause)));
return (format.cljs$core$IFn$_invoke$arity$4 ? format.cljs$core$IFn$_invoke$arity$4(G__69524,G__69525,G__69526,G__69527) : format.call(null,G__69524,G__69525,G__69526,G__69527));

break;
case "macroexpansion":
var G__69533 = "Unexpected error%s macroexpanding %sat (%s).\n%s\n";
var G__69534 = cause_type;
var G__69535 = (cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):"");
var G__69536 = loc;
var G__69537 = cause;
return (format.cljs$core$IFn$_invoke$arity$5 ? format.cljs$core$IFn$_invoke$arity$5(G__69533,G__69534,G__69535,G__69536,G__69537) : format.call(null,G__69533,G__69534,G__69535,G__69536,G__69537));

break;
case "compile-syntax-check":
var G__69543 = "Syntax error%s compiling %sat (%s).\n%s\n";
var G__69544 = cause_type;
var G__69545 = (cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):"");
var G__69546 = loc;
var G__69547 = cause;
return (format.cljs$core$IFn$_invoke$arity$5 ? format.cljs$core$IFn$_invoke$arity$5(G__69543,G__69544,G__69545,G__69546,G__69547) : format.call(null,G__69543,G__69544,G__69545,G__69546,G__69547));

break;
case "compilation":
var G__69548 = "Unexpected error%s compiling %sat (%s).\n%s\n";
var G__69549 = cause_type;
var G__69550 = (cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):"");
var G__69551 = loc;
var G__69552 = cause;
return (format.cljs$core$IFn$_invoke$arity$5 ? format.cljs$core$IFn$_invoke$arity$5(G__69548,G__69549,G__69550,G__69551,G__69552) : format.call(null,G__69548,G__69549,G__69550,G__69551,G__69552));

break;
case "read-eval-result":
return (format.cljs$core$IFn$_invoke$arity$5 ? format.cljs$core$IFn$_invoke$arity$5("Error reading eval result%s at %s (%s).\n%s\n",cause_type,symbol,loc,cause) : format.call(null,"Error reading eval result%s at %s (%s).\n%s\n",cause_type,symbol,loc,cause));

break;
case "print-eval-result":
return (format.cljs$core$IFn$_invoke$arity$5 ? format.cljs$core$IFn$_invoke$arity$5("Error printing return value%s at %s (%s).\n%s\n",cause_type,symbol,loc,cause) : format.call(null,"Error printing return value%s at %s (%s).\n%s\n",cause_type,symbol,loc,cause));

break;
case "execution":
if(cljs.core.truth_(spec)){
var G__69553 = "Execution error - invalid arguments to %s at (%s).\n%s";
var G__69554 = symbol;
var G__69555 = loc;
var G__69556 = (function (){var sb__5687__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__69561_69680 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__69562_69681 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__69563_69682 = true;
var _STAR_print_fn_STAR__temp_val__69564_69683 = (function (x__5688__auto__){
return sb__5687__auto__.append(x__5688__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__69563_69682);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__69564_69683);

try{cljs.spec.alpha.explain_out(cljs.core.update.cljs$core$IFn$_invoke$arity$3(spec,new cljs.core.Keyword("cljs.spec.alpha","problems","cljs.spec.alpha/problems",447400814),(function (probs){
return cljs.core.map.cljs$core$IFn$_invoke$arity$2((function (p1__69519_SHARP_){
return cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(p1__69519_SHARP_,new cljs.core.Keyword(null,"in","in",-1531184865));
}),probs);
}))
);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__69562_69681);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__69561_69680);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__5687__auto__);
})();
return (format.cljs$core$IFn$_invoke$arity$4 ? format.cljs$core$IFn$_invoke$arity$4(G__69553,G__69554,G__69555,G__69556) : format.call(null,G__69553,G__69554,G__69555,G__69556));
} else {
var G__69565 = "Execution error%s at %s(%s).\n%s\n";
var G__69566 = cause_type;
var G__69567 = (cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):"");
var G__69568 = loc;
var G__69569 = cause;
return (format.cljs$core$IFn$_invoke$arity$5 ? format.cljs$core$IFn$_invoke$arity$5(G__69565,G__69566,G__69567,G__69568,G__69569) : format.call(null,G__69565,G__69566,G__69567,G__69568,G__69569));
}

break;
default:
throw (new Error(["No matching clause: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(G__69523__$1)].join('')));

}
});
cljs.repl.error__GT_str = (function cljs$repl$error__GT_str(error){
return cljs.repl.ex_str(cljs.repl.ex_triage(cljs.repl.Error__GT_map(error)));
});

//# sourceMappingURL=cljs.repl.js.map
