goog.provide('cljs.repl');
cljs.repl.print_doc = (function cljs$repl$print_doc(p__49695){
var map__49696 = p__49695;
var map__49696__$1 = cljs.core.__destructure_map(map__49696);
var m = map__49696__$1;
var n = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49696__$1,new cljs.core.Keyword(null,"ns","ns",441598760));
var nm = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49696__$1,new cljs.core.Keyword(null,"name","name",1843675177));
cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["-------------------------"], 0));

cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([(function (){var or__4253__auto__ = new cljs.core.Keyword(null,"spec","spec",347520401).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
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
var seq__49699_50054 = cljs.core.seq(new cljs.core.Keyword(null,"forms","forms",2045992350).cljs$core$IFn$_invoke$arity$1(m));
var chunk__49701_50055 = null;
var count__49702_50056 = (0);
var i__49703_50057 = (0);
while(true){
if((i__49703_50057 < count__49702_50056)){
var f_50060 = chunk__49701_50055.cljs$core$IIndexed$_nth$arity$2(null,i__49703_50057);
cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["  ",f_50060], 0));


var G__50062 = seq__49699_50054;
var G__50063 = chunk__49701_50055;
var G__50064 = count__49702_50056;
var G__50065 = (i__49703_50057 + (1));
seq__49699_50054 = G__50062;
chunk__49701_50055 = G__50063;
count__49702_50056 = G__50064;
i__49703_50057 = G__50065;
continue;
} else {
var temp__5753__auto___50066 = cljs.core.seq(seq__49699_50054);
if(temp__5753__auto___50066){
var seq__49699_50067__$1 = temp__5753__auto___50066;
if(cljs.core.chunked_seq_QMARK_(seq__49699_50067__$1)){
var c__4679__auto___50068 = cljs.core.chunk_first(seq__49699_50067__$1);
var G__50069 = cljs.core.chunk_rest(seq__49699_50067__$1);
var G__50070 = c__4679__auto___50068;
var G__50071 = cljs.core.count(c__4679__auto___50068);
var G__50072 = (0);
seq__49699_50054 = G__50069;
chunk__49701_50055 = G__50070;
count__49702_50056 = G__50071;
i__49703_50057 = G__50072;
continue;
} else {
var f_50075 = cljs.core.first(seq__49699_50067__$1);
cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["  ",f_50075], 0));


var G__50076 = cljs.core.next(seq__49699_50067__$1);
var G__50077 = null;
var G__50078 = (0);
var G__50079 = (0);
seq__49699_50054 = G__50076;
chunk__49701_50055 = G__50077;
count__49702_50056 = G__50078;
i__49703_50057 = G__50079;
continue;
}
} else {
}
}
break;
}
} else {
if(cljs.core.truth_(new cljs.core.Keyword(null,"arglists","arglists",1661989754).cljs$core$IFn$_invoke$arity$1(m))){
var arglists_50080 = new cljs.core.Keyword(null,"arglists","arglists",1661989754).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_((function (){var or__4253__auto__ = new cljs.core.Keyword(null,"macro","macro",-867863404).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
return new cljs.core.Keyword(null,"repl-special-function","repl-special-function",1262603725).cljs$core$IFn$_invoke$arity$1(m);
}
})())){
cljs.core.prn.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([arglists_50080], 0));
} else {
cljs.core.prn.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Symbol(null,"quote","quote",1377916282,null),cljs.core.first(arglists_50080)))?cljs.core.second(arglists_50080):arglists_50080)], 0));
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
var seq__49717_50090 = cljs.core.seq(new cljs.core.Keyword(null,"methods","methods",453930866).cljs$core$IFn$_invoke$arity$1(m));
var chunk__49718_50091 = null;
var count__49719_50092 = (0);
var i__49720_50093 = (0);
while(true){
if((i__49720_50093 < count__49719_50092)){
var vec__49740_50095 = chunk__49718_50091.cljs$core$IIndexed$_nth$arity$2(null,i__49720_50093);
var name_50096 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49740_50095,(0),null);
var map__49743_50097 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49740_50095,(1),null);
var map__49743_50098__$1 = cljs.core.__destructure_map(map__49743_50097);
var doc_50099 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49743_50098__$1,new cljs.core.Keyword(null,"doc","doc",1913296891));
var arglists_50100 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49743_50098__$1,new cljs.core.Keyword(null,"arglists","arglists",1661989754));
cljs.core.println();

cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([" ",name_50096], 0));

cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([" ",arglists_50100], 0));

if(cljs.core.truth_(doc_50099)){
cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([" ",doc_50099], 0));
} else {
}


var G__50103 = seq__49717_50090;
var G__50104 = chunk__49718_50091;
var G__50105 = count__49719_50092;
var G__50106 = (i__49720_50093 + (1));
seq__49717_50090 = G__50103;
chunk__49718_50091 = G__50104;
count__49719_50092 = G__50105;
i__49720_50093 = G__50106;
continue;
} else {
var temp__5753__auto___50107 = cljs.core.seq(seq__49717_50090);
if(temp__5753__auto___50107){
var seq__49717_50109__$1 = temp__5753__auto___50107;
if(cljs.core.chunked_seq_QMARK_(seq__49717_50109__$1)){
var c__4679__auto___50110 = cljs.core.chunk_first(seq__49717_50109__$1);
var G__50111 = cljs.core.chunk_rest(seq__49717_50109__$1);
var G__50112 = c__4679__auto___50110;
var G__50113 = cljs.core.count(c__4679__auto___50110);
var G__50114 = (0);
seq__49717_50090 = G__50111;
chunk__49718_50091 = G__50112;
count__49719_50092 = G__50113;
i__49720_50093 = G__50114;
continue;
} else {
var vec__49747_50117 = cljs.core.first(seq__49717_50109__$1);
var name_50118 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49747_50117,(0),null);
var map__49750_50119 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49747_50117,(1),null);
var map__49750_50120__$1 = cljs.core.__destructure_map(map__49750_50119);
var doc_50121 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49750_50120__$1,new cljs.core.Keyword(null,"doc","doc",1913296891));
var arglists_50122 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49750_50120__$1,new cljs.core.Keyword(null,"arglists","arglists",1661989754));
cljs.core.println();

cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([" ",name_50118], 0));

cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([" ",arglists_50122], 0));

if(cljs.core.truth_(doc_50121)){
cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([" ",doc_50121], 0));
} else {
}


var G__50127 = cljs.core.next(seq__49717_50109__$1);
var G__50128 = null;
var G__50129 = (0);
var G__50130 = (0);
seq__49717_50090 = G__50127;
chunk__49718_50091 = G__50128;
count__49719_50092 = G__50129;
i__49720_50093 = G__50130;
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

var seq__49754 = cljs.core.seq(new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"args","args",1315556576),new cljs.core.Keyword(null,"ret","ret",-468222814),new cljs.core.Keyword(null,"fn","fn",-1175266204)], null));
var chunk__49755 = null;
var count__49757 = (0);
var i__49758 = (0);
while(true){
if((i__49758 < count__49757)){
var role = chunk__49755.cljs$core$IIndexed$_nth$arity$2(null,i__49758);
var temp__5753__auto___50140__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(fnspec,role);
if(cljs.core.truth_(temp__5753__auto___50140__$1)){
var spec_50146 = temp__5753__auto___50140__$1;
cljs.core.print.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([["\n ",cljs.core.name(role),":"].join(''),cljs.spec.alpha.describe(spec_50146)], 0));
} else {
}


var G__50149 = seq__49754;
var G__50150 = chunk__49755;
var G__50151 = count__49757;
var G__50152 = (i__49758 + (1));
seq__49754 = G__50149;
chunk__49755 = G__50150;
count__49757 = G__50151;
i__49758 = G__50152;
continue;
} else {
var temp__5753__auto____$1 = cljs.core.seq(seq__49754);
if(temp__5753__auto____$1){
var seq__49754__$1 = temp__5753__auto____$1;
if(cljs.core.chunked_seq_QMARK_(seq__49754__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__49754__$1);
var G__50163 = cljs.core.chunk_rest(seq__49754__$1);
var G__50164 = c__4679__auto__;
var G__50165 = cljs.core.count(c__4679__auto__);
var G__50166 = (0);
seq__49754 = G__50163;
chunk__49755 = G__50164;
count__49757 = G__50165;
i__49758 = G__50166;
continue;
} else {
var role = cljs.core.first(seq__49754__$1);
var temp__5753__auto___50172__$2 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(fnspec,role);
if(cljs.core.truth_(temp__5753__auto___50172__$2)){
var spec_50174 = temp__5753__auto___50172__$2;
cljs.core.print.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([["\n ",cljs.core.name(role),":"].join(''),cljs.spec.alpha.describe(spec_50174)], 0));
} else {
}


var G__50183 = cljs.core.next(seq__49754__$1);
var G__50184 = null;
var G__50185 = (0);
var G__50186 = (0);
seq__49754 = G__50183;
chunk__49755 = G__50184;
count__49757 = G__50185;
i__49758 = G__50186;
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
var G__50206 = cljs.core.conj.cljs$core$IFn$_invoke$arity$2(via,t);
var G__50207 = cljs.core.ex_cause(t);
via = G__50206;
t = G__50207;
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
var map__49798 = datafied_throwable;
var map__49798__$1 = cljs.core.__destructure_map(map__49798);
var via = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49798__$1,new cljs.core.Keyword(null,"via","via",-1904457336));
var trace = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49798__$1,new cljs.core.Keyword(null,"trace","trace",-1082747415));
var phase = cljs.core.get.cljs$core$IFn$_invoke$arity$3(map__49798__$1,new cljs.core.Keyword(null,"phase","phase",575722892),new cljs.core.Keyword(null,"execution","execution",253283524));
var map__49799 = cljs.core.last(via);
var map__49799__$1 = cljs.core.__destructure_map(map__49799);
var type = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49799__$1,new cljs.core.Keyword(null,"type","type",1174270348));
var message = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49799__$1,new cljs.core.Keyword(null,"message","message",-406056002));
var data = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49799__$1,new cljs.core.Keyword(null,"data","data",-232669377));
var map__49800 = data;
var map__49800__$1 = cljs.core.__destructure_map(map__49800);
var problems = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49800__$1,new cljs.core.Keyword("cljs.spec.alpha","problems","cljs.spec.alpha/problems",447400814));
var fn = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49800__$1,new cljs.core.Keyword("cljs.spec.alpha","fn","cljs.spec.alpha/fn",408600443));
var caller = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49800__$1,new cljs.core.Keyword("cljs.spec.test.alpha","caller","cljs.spec.test.alpha/caller",-398302390));
var map__49801 = new cljs.core.Keyword(null,"data","data",-232669377).cljs$core$IFn$_invoke$arity$1(cljs.core.first(via));
var map__49801__$1 = cljs.core.__destructure_map(map__49801);
var top_data = map__49801__$1;
var source = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49801__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397));
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3((function (){var G__49806 = phase;
var G__49806__$1 = (((G__49806 instanceof cljs.core.Keyword))?G__49806.fqn:null);
switch (G__49806__$1) {
case "read-source":
var map__49807 = data;
var map__49807__$1 = cljs.core.__destructure_map(map__49807);
var line = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49807__$1,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471));
var column = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49807__$1,new cljs.core.Keyword("clojure.error","column","clojure.error/column",304721553));
var G__49810 = cljs.core.merge.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"data","data",-232669377).cljs$core$IFn$_invoke$arity$1(cljs.core.second(via)),top_data], 0));
var G__49810__$1 = (cljs.core.truth_(source)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49810,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),source):G__49810);
var G__49810__$2 = (cljs.core.truth_((function (){var fexpr__49813 = new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["NO_SOURCE_PATH",null,"NO_SOURCE_FILE",null], null), null);
return (fexpr__49813.cljs$core$IFn$_invoke$arity$1 ? fexpr__49813.cljs$core$IFn$_invoke$arity$1(source) : fexpr__49813.call(null,source));
})())?cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(G__49810__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397)):G__49810__$1);
if(cljs.core.truth_(message)){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49810__$2,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message);
} else {
return G__49810__$2;
}

break;
case "compile-syntax-check":
case "compilation":
case "macro-syntax-check":
case "macroexpansion":
var G__49816 = top_data;
var G__49816__$1 = (cljs.core.truth_(source)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49816,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),source):G__49816);
var G__49816__$2 = (cljs.core.truth_((function (){var fexpr__49821 = new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["NO_SOURCE_PATH",null,"NO_SOURCE_FILE",null], null), null);
return (fexpr__49821.cljs$core$IFn$_invoke$arity$1 ? fexpr__49821.cljs$core$IFn$_invoke$arity$1(source) : fexpr__49821.call(null,source));
})())?cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(G__49816__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397)):G__49816__$1);
var G__49816__$3 = (cljs.core.truth_(type)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49816__$2,new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890),type):G__49816__$2);
var G__49816__$4 = (cljs.core.truth_(message)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49816__$3,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message):G__49816__$3);
if(cljs.core.truth_(problems)){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49816__$4,new cljs.core.Keyword("clojure.error","spec","clojure.error/spec",2055032595),data);
} else {
return G__49816__$4;
}

break;
case "read-eval-result":
case "print-eval-result":
var vec__49836 = cljs.core.first(trace);
var source__$1 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49836,(0),null);
var method = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49836,(1),null);
var file = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49836,(2),null);
var line = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49836,(3),null);
var G__49841 = top_data;
var G__49841__$1 = (cljs.core.truth_(line)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49841,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471),line):G__49841);
var G__49841__$2 = (cljs.core.truth_(file)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49841__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),file):G__49841__$1);
var G__49841__$3 = (cljs.core.truth_((function (){var and__4251__auto__ = source__$1;
if(cljs.core.truth_(and__4251__auto__)){
return method;
} else {
return and__4251__auto__;
}
})())?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49841__$2,new cljs.core.Keyword("clojure.error","symbol","clojure.error/symbol",1544821994),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[source__$1,method],null))):G__49841__$2);
var G__49841__$4 = (cljs.core.truth_(type)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49841__$3,new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890),type):G__49841__$3);
if(cljs.core.truth_(message)){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49841__$4,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message);
} else {
return G__49841__$4;
}

break;
case "execution":
var vec__49852 = cljs.core.first(trace);
var source__$1 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49852,(0),null);
var method = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49852,(1),null);
var file = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49852,(2),null);
var line = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__49852,(3),null);
var file__$1 = cljs.core.first(cljs.core.remove.cljs$core$IFn$_invoke$arity$2((function (p1__49795_SHARP_){
var or__4253__auto__ = (p1__49795_SHARP_ == null);
if(or__4253__auto__){
return or__4253__auto__;
} else {
var fexpr__49871 = new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["NO_SOURCE_PATH",null,"NO_SOURCE_FILE",null], null), null);
return (fexpr__49871.cljs$core$IFn$_invoke$arity$1 ? fexpr__49871.cljs$core$IFn$_invoke$arity$1(p1__49795_SHARP_) : fexpr__49871.call(null,p1__49795_SHARP_));
}
}),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"file","file",-1269645878).cljs$core$IFn$_invoke$arity$1(caller),file], null)));
var err_line = (function (){var or__4253__auto__ = new cljs.core.Keyword(null,"line","line",212345235).cljs$core$IFn$_invoke$arity$1(caller);
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
return line;
}
})();
var G__49874 = new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890),type], null);
var G__49874__$1 = (cljs.core.truth_(err_line)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49874,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471),err_line):G__49874);
var G__49874__$2 = (cljs.core.truth_(message)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49874__$1,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message):G__49874__$1);
var G__49874__$3 = (cljs.core.truth_((function (){var or__4253__auto__ = fn;
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
var and__4251__auto__ = source__$1;
if(cljs.core.truth_(and__4251__auto__)){
return method;
} else {
return and__4251__auto__;
}
}
})())?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49874__$2,new cljs.core.Keyword("clojure.error","symbol","clojure.error/symbol",1544821994),(function (){var or__4253__auto__ = fn;
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
return (new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[source__$1,method],null));
}
})()):G__49874__$2);
var G__49874__$4 = (cljs.core.truth_(file__$1)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49874__$3,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),file__$1):G__49874__$3);
if(cljs.core.truth_(problems)){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__49874__$4,new cljs.core.Keyword("clojure.error","spec","clojure.error/spec",2055032595),data);
} else {
return G__49874__$4;
}

break;
default:
throw (new Error(["No matching clause: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(G__49806__$1)].join('')));

}
})(),new cljs.core.Keyword("clojure.error","phase","clojure.error/phase",275140358),phase);
});
/**
 * Returns a string from exception data, as produced by ex-triage.
 *   The first line summarizes the exception phase and location.
 *   The subsequent lines describe the cause.
 */
cljs.repl.ex_str = (function cljs$repl$ex_str(p__49910){
var map__49912 = p__49910;
var map__49912__$1 = cljs.core.__destructure_map(map__49912);
var triage_data = map__49912__$1;
var phase = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49912__$1,new cljs.core.Keyword("clojure.error","phase","clojure.error/phase",275140358));
var source = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49912__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397));
var line = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49912__$1,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471));
var column = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49912__$1,new cljs.core.Keyword("clojure.error","column","clojure.error/column",304721553));
var symbol = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49912__$1,new cljs.core.Keyword("clojure.error","symbol","clojure.error/symbol",1544821994));
var class$ = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49912__$1,new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890));
var cause = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49912__$1,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742));
var spec = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__49912__$1,new cljs.core.Keyword("clojure.error","spec","clojure.error/spec",2055032595));
var loc = [cljs.core.str.cljs$core$IFn$_invoke$arity$1((function (){var or__4253__auto__ = source;
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
return "<cljs repl>";
}
})()),":",cljs.core.str.cljs$core$IFn$_invoke$arity$1((function (){var or__4253__auto__ = line;
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
return (1);
}
})()),(cljs.core.truth_(column)?[":",cljs.core.str.cljs$core$IFn$_invoke$arity$1(column)].join(''):"")].join('');
var class_name = cljs.core.name((function (){var or__4253__auto__ = class$;
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
return "";
}
})());
var simple_class = class_name;
var cause_type = ((cljs.core.contains_QMARK_(new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["RuntimeException",null,"Exception",null], null), null),simple_class))?"":[" (",simple_class,")"].join(''));
var format = goog.string.format;
var G__49938 = phase;
var G__49938__$1 = (((G__49938 instanceof cljs.core.Keyword))?G__49938.fqn:null);
switch (G__49938__$1) {
case "read-source":
return (format.cljs$core$IFn$_invoke$arity$3 ? format.cljs$core$IFn$_invoke$arity$3("Syntax error reading source at (%s).\n%s\n",loc,cause) : format.call(null,"Syntax error reading source at (%s).\n%s\n",loc,cause));

break;
case "macro-syntax-check":
var G__49948 = "Syntax error macroexpanding %sat (%s).\n%s";
var G__49949 = (cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):"");
var G__49950 = loc;
var G__49951 = (cljs.core.truth_(spec)?(function (){var sb__4795__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__49961_50334 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__49962_50335 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__49963_50336 = true;
var _STAR_print_fn_STAR__temp_val__49964_50337 = (function (x__4796__auto__){
return sb__4795__auto__.append(x__4796__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__49963_50336);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__49964_50337);

try{cljs.spec.alpha.explain_out(cljs.core.update.cljs$core$IFn$_invoke$arity$3(spec,new cljs.core.Keyword("cljs.spec.alpha","problems","cljs.spec.alpha/problems",447400814),(function (probs){
return cljs.core.map.cljs$core$IFn$_invoke$arity$2((function (p1__49904_SHARP_){
return cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(p1__49904_SHARP_,new cljs.core.Keyword(null,"in","in",-1531184865));
}),probs);
}))
);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__49962_50335);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__49961_50334);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__4795__auto__);
})():(format.cljs$core$IFn$_invoke$arity$2 ? format.cljs$core$IFn$_invoke$arity$2("%s\n",cause) : format.call(null,"%s\n",cause)));
return (format.cljs$core$IFn$_invoke$arity$4 ? format.cljs$core$IFn$_invoke$arity$4(G__49948,G__49949,G__49950,G__49951) : format.call(null,G__49948,G__49949,G__49950,G__49951));

break;
case "macroexpansion":
var G__49976 = "Unexpected error%s macroexpanding %sat (%s).\n%s\n";
var G__49977 = cause_type;
var G__49978 = (cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):"");
var G__49979 = loc;
var G__49980 = cause;
return (format.cljs$core$IFn$_invoke$arity$5 ? format.cljs$core$IFn$_invoke$arity$5(G__49976,G__49977,G__49978,G__49979,G__49980) : format.call(null,G__49976,G__49977,G__49978,G__49979,G__49980));

break;
case "compile-syntax-check":
var G__49986 = "Syntax error%s compiling %sat (%s).\n%s\n";
var G__49987 = cause_type;
var G__49988 = (cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):"");
var G__49989 = loc;
var G__49990 = cause;
return (format.cljs$core$IFn$_invoke$arity$5 ? format.cljs$core$IFn$_invoke$arity$5(G__49986,G__49987,G__49988,G__49989,G__49990) : format.call(null,G__49986,G__49987,G__49988,G__49989,G__49990));

break;
case "compilation":
var G__49991 = "Unexpected error%s compiling %sat (%s).\n%s\n";
var G__49993 = cause_type;
var G__49994 = (cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):"");
var G__49995 = loc;
var G__49996 = cause;
return (format.cljs$core$IFn$_invoke$arity$5 ? format.cljs$core$IFn$_invoke$arity$5(G__49991,G__49993,G__49994,G__49995,G__49996) : format.call(null,G__49991,G__49993,G__49994,G__49995,G__49996));

break;
case "read-eval-result":
return (format.cljs$core$IFn$_invoke$arity$5 ? format.cljs$core$IFn$_invoke$arity$5("Error reading eval result%s at %s (%s).\n%s\n",cause_type,symbol,loc,cause) : format.call(null,"Error reading eval result%s at %s (%s).\n%s\n",cause_type,symbol,loc,cause));

break;
case "print-eval-result":
return (format.cljs$core$IFn$_invoke$arity$5 ? format.cljs$core$IFn$_invoke$arity$5("Error printing return value%s at %s (%s).\n%s\n",cause_type,symbol,loc,cause) : format.call(null,"Error printing return value%s at %s (%s).\n%s\n",cause_type,symbol,loc,cause));

break;
case "execution":
if(cljs.core.truth_(spec)){
var G__49997 = "Execution error - invalid arguments to %s at (%s).\n%s";
var G__49998 = symbol;
var G__49999 = loc;
var G__50000 = (function (){var sb__4795__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__50006_50347 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__50007_50348 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__50008_50349 = true;
var _STAR_print_fn_STAR__temp_val__50009_50350 = (function (x__4796__auto__){
return sb__4795__auto__.append(x__4796__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__50008_50349);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__50009_50350);

try{cljs.spec.alpha.explain_out(cljs.core.update.cljs$core$IFn$_invoke$arity$3(spec,new cljs.core.Keyword("cljs.spec.alpha","problems","cljs.spec.alpha/problems",447400814),(function (probs){
return cljs.core.map.cljs$core$IFn$_invoke$arity$2((function (p1__49907_SHARP_){
return cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(p1__49907_SHARP_,new cljs.core.Keyword(null,"in","in",-1531184865));
}),probs);
}))
);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__50007_50348);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__50006_50347);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__4795__auto__);
})();
return (format.cljs$core$IFn$_invoke$arity$4 ? format.cljs$core$IFn$_invoke$arity$4(G__49997,G__49998,G__49999,G__50000) : format.call(null,G__49997,G__49998,G__49999,G__50000));
} else {
var G__50021 = "Execution error%s at %s(%s).\n%s\n";
var G__50022 = cause_type;
var G__50023 = (cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):"");
var G__50024 = loc;
var G__50025 = cause;
return (format.cljs$core$IFn$_invoke$arity$5 ? format.cljs$core$IFn$_invoke$arity$5(G__50021,G__50022,G__50023,G__50024,G__50025) : format.call(null,G__50021,G__50022,G__50023,G__50024,G__50025));
}

break;
default:
throw (new Error(["No matching clause: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(G__49938__$1)].join('')));

}
});
cljs.repl.error__GT_str = (function cljs$repl$error__GT_str(error){
return cljs.repl.ex_str(cljs.repl.ex_triage(cljs.repl.Error__GT_map(error)));
});

//# sourceMappingURL=cljs.repl.js.map
