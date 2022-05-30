goog.provide('shadow.test.env');
/**
 * @define {boolean}
 */
shadow.test.env.UI_DRIVEN = goog.define("shadow.test.env.UI_DRIVEN",false);
if((typeof shadow !== 'undefined') && (typeof shadow.test !== 'undefined') && (typeof shadow.test.env !== 'undefined') && (typeof shadow.test.env.tests_ref !== 'undefined')){
} else {
shadow.test.env.tests_ref = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"namespaces","namespaces",-1444157469),cljs.core.PersistentArrayMap.EMPTY], null));
}
shadow.test.env.reset_test_data_BANG_ = (function shadow$test$env$reset_test_data_BANG_(test_data){
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(shadow.test.env.tests_ref,cljs.core.assoc,new cljs.core.Keyword(null,"namespaces","namespaces",-1444157469),test_data);
});
shadow.test.env.get_tests = (function shadow$test$env$get_tests(){
return cljs.core.get.cljs$core$IFn$_invoke$arity$2(cljs.core.deref(shadow.test.env.tests_ref),new cljs.core.Keyword(null,"namespaces","namespaces",-1444157469));
});
shadow.test.env.get_test_vars = (function shadow$test$env$get_test_vars(){
var iter__5520__auto__ = (function shadow$test$env$get_test_vars_$_iter__70418(s__70419){
return (new cljs.core.LazySeq(null,(function (){
var s__70419__$1 = s__70419;
while(true){
var temp__5753__auto__ = cljs.core.seq(s__70419__$1);
if(temp__5753__auto__){
var xs__6308__auto__ = temp__5753__auto__;
var vec__70426 = cljs.core.first(xs__6308__auto__);
var ns = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__70426,(0),null);
var ns_info = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__70426,(1),null);
var iterys__5516__auto__ = ((function (s__70419__$1,vec__70426,ns,ns_info,xs__6308__auto__,temp__5753__auto__){
return (function shadow$test$env$get_test_vars_$_iter__70418_$_iter__70420(s__70421){
return (new cljs.core.LazySeq(null,((function (s__70419__$1,vec__70426,ns,ns_info,xs__6308__auto__,temp__5753__auto__){
return (function (){
var s__70421__$1 = s__70421;
while(true){
var temp__5753__auto____$1 = cljs.core.seq(s__70421__$1);
if(temp__5753__auto____$1){
var s__70421__$2 = temp__5753__auto____$1;
if(cljs.core.chunked_seq_QMARK_(s__70421__$2)){
var c__5518__auto__ = cljs.core.chunk_first(s__70421__$2);
var size__5519__auto__ = cljs.core.count(c__5518__auto__);
var b__70423 = cljs.core.chunk_buffer(size__5519__auto__);
if((function (){var i__70422 = (0);
while(true){
if((i__70422 < size__5519__auto__)){
var var$ = cljs.core._nth(c__5518__auto__,i__70422);
cljs.core.chunk_append(b__70423,var$);

var G__70460 = (i__70422 + (1));
i__70422 = G__70460;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons(cljs.core.chunk(b__70423),shadow$test$env$get_test_vars_$_iter__70418_$_iter__70420(cljs.core.chunk_rest(s__70421__$2)));
} else {
return cljs.core.chunk_cons(cljs.core.chunk(b__70423),null);
}
} else {
var var$ = cljs.core.first(s__70421__$2);
return cljs.core.cons(var$,shadow$test$env$get_test_vars_$_iter__70418_$_iter__70420(cljs.core.rest(s__70421__$2)));
}
} else {
return null;
}
break;
}
});})(s__70419__$1,vec__70426,ns,ns_info,xs__6308__auto__,temp__5753__auto__))
,null,null));
});})(s__70419__$1,vec__70426,ns,ns_info,xs__6308__auto__,temp__5753__auto__))
;
var fs__5517__auto__ = cljs.core.seq(iterys__5516__auto__(new cljs.core.Keyword(null,"vars","vars",-2046957217).cljs$core$IFn$_invoke$arity$1(ns_info)));
if(fs__5517__auto__){
return cljs.core.concat.cljs$core$IFn$_invoke$arity$2(fs__5517__auto__,shadow$test$env$get_test_vars_$_iter__70418(cljs.core.rest(s__70419__$1)));
} else {
var G__70492 = cljs.core.rest(s__70419__$1);
s__70419__$1 = G__70492;
continue;
}
} else {
return null;
}
break;
}
}),null,null));
});
return iter__5520__auto__(shadow.test.env.get_tests());
});
shadow.test.env.get_test_ns_info = (function shadow$test$env$get_test_ns_info(ns){
if((ns instanceof cljs.core.Symbol)){
} else {
throw (new Error("Assert failed: (symbol? ns)"));
}

return cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(cljs.core.deref(shadow.test.env.tests_ref),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"namespaces","namespaces",-1444157469),ns], null));
});
/**
 * returns all the registered test namespaces and symbols
 * use (get-test-ns-info the-sym) to get the details
 */
shadow.test.env.get_test_namespaces = (function shadow$test$env$get_test_namespaces(){
return cljs.core.keys(new cljs.core.Keyword(null,"namespaces","namespaces",-1444157469).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(shadow.test.env.tests_ref)));
});
shadow.test.env.get_test_count = (function shadow$test$env$get_test_count(){
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3(cljs.core._PLUS_,(0),(function (){var iter__5520__auto__ = (function shadow$test$env$get_test_count_$_iter__70441(s__70442){
return (new cljs.core.LazySeq(null,(function (){
var s__70442__$1 = s__70442;
while(true){
var temp__5753__auto__ = cljs.core.seq(s__70442__$1);
if(temp__5753__auto__){
var s__70442__$2 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(s__70442__$2)){
var c__5518__auto__ = cljs.core.chunk_first(s__70442__$2);
var size__5519__auto__ = cljs.core.count(c__5518__auto__);
var b__70444 = cljs.core.chunk_buffer(size__5519__auto__);
if((function (){var i__70443 = (0);
while(true){
if((i__70443 < size__5519__auto__)){
var map__70447 = cljs.core._nth(c__5518__auto__,i__70443);
var map__70447__$1 = cljs.core.__destructure_map(map__70447);
var test_ns = map__70447__$1;
var vars = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70447__$1,new cljs.core.Keyword(null,"vars","vars",-2046957217));
cljs.core.chunk_append(b__70444,cljs.core.count(vars));

var G__70493 = (i__70443 + (1));
i__70443 = G__70493;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons(cljs.core.chunk(b__70444),shadow$test$env$get_test_count_$_iter__70441(cljs.core.chunk_rest(s__70442__$2)));
} else {
return cljs.core.chunk_cons(cljs.core.chunk(b__70444),null);
}
} else {
var map__70450 = cljs.core.first(s__70442__$2);
var map__70450__$1 = cljs.core.__destructure_map(map__70450);
var test_ns = map__70450__$1;
var vars = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70450__$1,new cljs.core.Keyword(null,"vars","vars",-2046957217));
return cljs.core.cons(cljs.core.count(vars),shadow$test$env$get_test_count_$_iter__70441(cljs.core.rest(s__70442__$2)));
}
} else {
return null;
}
break;
}
}),null,null));
});
return iter__5520__auto__(cljs.core.vals(new cljs.core.Keyword(null,"namespaces","namespaces",-1444157469).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(shadow.test.env.tests_ref))));
})());
});

//# sourceMappingURL=shadow.test.env.js.map
