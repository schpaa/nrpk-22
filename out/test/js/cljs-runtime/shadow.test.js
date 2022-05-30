goog.provide('shadow.test');
/**
 * like ct/test-vars-block but more generic
 * groups vars by namespace, executes fixtures
 */
shadow.test.test_vars_grouped_block = (function shadow$test$test_vars_grouped_block(vars){
return cljs.core.mapcat.cljs$core$IFn$_invoke$arity$variadic((function (p__70713){
var vec__70714 = p__70713;
var ns = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__70714,(0),null);
var vars__$1 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__70714,(1),null);
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(function (){
return cljs.test.report.call(null,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"type","type",1174270348),new cljs.core.Keyword(null,"begin-test-ns","begin-test-ns",-1701237033),new cljs.core.Keyword(null,"ns","ns",441598760),ns], null));
}),(function (){
return cljs.test.block((function (){var env = cljs.test.get_current_env();
var once_fixtures = cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(env,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"once-fixtures","once-fixtures",1253947167),ns], null));
var each_fixtures = cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(env,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"each-fixtures","each-fixtures",802243977),ns], null));
var G__70718 = cljs.test.execution_strategy(once_fixtures,each_fixtures);
var G__70718__$1 = (((G__70718 instanceof cljs.core.Keyword))?G__70718.fqn:null);
switch (G__70718__$1) {
case "async":
return cljs.test.wrap_map_fixtures(once_fixtures,cljs.core.mapcat.cljs$core$IFn$_invoke$arity$variadic(cljs.core.comp.cljs$core$IFn$_invoke$arity$2(cljs.core.partial.cljs$core$IFn$_invoke$arity$2(cljs.test.wrap_map_fixtures,each_fixtures),cljs.test.test_var_block),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([cljs.core.filter.cljs$core$IFn$_invoke$arity$2(cljs.core.comp.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"test","test",577538877),cljs.core.meta),vars__$1)], 0)));

break;
case "sync":
var each_fixture_fn = cljs.test.join_fixtures(each_fixtures);
return new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [(function (){
var G__70727 = (function (){
var seq__70728 = cljs.core.seq(vars__$1);
var chunk__70729 = null;
var count__70730 = (0);
var i__70731 = (0);
while(true){
if((i__70731 < count__70730)){
var v = chunk__70729.cljs$core$IIndexed$_nth$arity$2(null,i__70731);
var temp__5753__auto___70865 = new cljs.core.Keyword(null,"test","test",577538877).cljs$core$IFn$_invoke$arity$1(cljs.core.meta(v));
if(cljs.core.truth_(temp__5753__auto___70865)){
var t_70866 = temp__5753__auto___70865;
var G__70739_70867 = ((function (seq__70728,chunk__70729,count__70730,i__70731,t_70866,temp__5753__auto___70865,v,each_fixture_fn,G__70718,G__70718__$1,env,once_fixtures,each_fixtures,vec__70714,ns,vars__$1){
return (function (){
return cljs.test.run_block(cljs.test.test_var_block_STAR_(v,cljs.test.disable_async(t_70866)));
});})(seq__70728,chunk__70729,count__70730,i__70731,t_70866,temp__5753__auto___70865,v,each_fixture_fn,G__70718,G__70718__$1,env,once_fixtures,each_fixtures,vec__70714,ns,vars__$1))
;
(each_fixture_fn.cljs$core$IFn$_invoke$arity$1 ? each_fixture_fn.cljs$core$IFn$_invoke$arity$1(G__70739_70867) : each_fixture_fn.call(null,G__70739_70867));
} else {
}


var G__70868 = seq__70728;
var G__70869 = chunk__70729;
var G__70870 = count__70730;
var G__70871 = (i__70731 + (1));
seq__70728 = G__70868;
chunk__70729 = G__70869;
count__70730 = G__70870;
i__70731 = G__70871;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__70728);
if(temp__5753__auto__){
var seq__70728__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__70728__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__70728__$1);
var G__70872 = cljs.core.chunk_rest(seq__70728__$1);
var G__70873 = c__5565__auto__;
var G__70874 = cljs.core.count(c__5565__auto__);
var G__70875 = (0);
seq__70728 = G__70872;
chunk__70729 = G__70873;
count__70730 = G__70874;
i__70731 = G__70875;
continue;
} else {
var v = cljs.core.first(seq__70728__$1);
var temp__5753__auto___70876__$1 = new cljs.core.Keyword(null,"test","test",577538877).cljs$core$IFn$_invoke$arity$1(cljs.core.meta(v));
if(cljs.core.truth_(temp__5753__auto___70876__$1)){
var t_70877 = temp__5753__auto___70876__$1;
var G__70742_70878 = ((function (seq__70728,chunk__70729,count__70730,i__70731,t_70877,temp__5753__auto___70876__$1,v,seq__70728__$1,temp__5753__auto__,each_fixture_fn,G__70718,G__70718__$1,env,once_fixtures,each_fixtures,vec__70714,ns,vars__$1){
return (function (){
return cljs.test.run_block(cljs.test.test_var_block_STAR_(v,cljs.test.disable_async(t_70877)));
});})(seq__70728,chunk__70729,count__70730,i__70731,t_70877,temp__5753__auto___70876__$1,v,seq__70728__$1,temp__5753__auto__,each_fixture_fn,G__70718,G__70718__$1,env,once_fixtures,each_fixtures,vec__70714,ns,vars__$1))
;
(each_fixture_fn.cljs$core$IFn$_invoke$arity$1 ? each_fixture_fn.cljs$core$IFn$_invoke$arity$1(G__70742_70878) : each_fixture_fn.call(null,G__70742_70878));
} else {
}


var G__70880 = cljs.core.next(seq__70728__$1);
var G__70881 = null;
var G__70882 = (0);
var G__70883 = (0);
seq__70728 = G__70880;
chunk__70729 = G__70881;
count__70730 = G__70882;
i__70731 = G__70883;
continue;
}
} else {
return null;
}
}
break;
}
});
var fexpr__70726 = cljs.test.join_fixtures(once_fixtures);
return (fexpr__70726.cljs$core$IFn$_invoke$arity$1 ? fexpr__70726.cljs$core$IFn$_invoke$arity$1(G__70727) : fexpr__70726.call(null,G__70727));
})], null);

break;
default:
throw (new Error(["No matching clause: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(G__70718__$1)].join('')));

}
})());
}),(function (){
return cljs.test.report.call(null,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"type","type",1174270348),new cljs.core.Keyword(null,"end-test-ns","end-test-ns",1620675645),new cljs.core.Keyword(null,"ns","ns",441598760),ns], null));
})], null);
}),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([cljs.core.sort_by.cljs$core$IFn$_invoke$arity$2(cljs.core.first,cljs.core.group_by((function (p1__70707_SHARP_){
return new cljs.core.Keyword(null,"ns","ns",441598760).cljs$core$IFn$_invoke$arity$1(cljs.core.meta(p1__70707_SHARP_));
}),vars))], 0));
});
/**
 * Like test-ns, but returns a block for further composition and
 *   later execution.  Does not clear the current env.
 */
shadow.test.test_ns_block = (function shadow$test$test_ns_block(ns){
if((ns instanceof cljs.core.Symbol)){
} else {
throw (new Error("Assert failed: (symbol? ns)"));
}

var map__70751 = shadow.test.env.get_test_ns_info(ns);
var map__70751__$1 = cljs.core.__destructure_map(map__70751);
var test_ns = map__70751__$1;
var vars = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70751__$1,new cljs.core.Keyword(null,"vars","vars",-2046957217));
if(cljs.core.not(test_ns)){
return new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [(function (){
return cljs.core.println.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([["Namespace: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(ns)," not found, no tests to run."].join('')], 0));
})], null);
} else {
return shadow.test.test_vars_grouped_block(vars);
}
});
shadow.test.prepare_test_run = (function shadow$test$prepare_test_run(p__70767,vars){
var map__70770 = p__70767;
var map__70770__$1 = cljs.core.__destructure_map(map__70770);
var env = map__70770__$1;
var report_fn = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70770__$1,new cljs.core.Keyword(null,"report-fn","report-fn",-549046115));
var orig_report = cljs.test.report;
return new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [(function (){
cljs.test.set_env_BANG_(cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(env,new cljs.core.Keyword("shadow.test","report-fn","shadow.test/report-fn",1075704061),orig_report));

if(cljs.core.truth_(report_fn)){
(cljs.test.report = report_fn);
} else {
}

var seq__70776_70885 = cljs.core.seq(shadow.test.env.get_tests());
var chunk__70778_70886 = null;
var count__70779_70887 = (0);
var i__70780_70888 = (0);
while(true){
if((i__70780_70888 < count__70779_70887)){
var vec__70808_70892 = chunk__70778_70886.cljs$core$IIndexed$_nth$arity$2(null,i__70780_70888);
var test_ns_70893 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__70808_70892,(0),null);
var ns_info_70894 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__70808_70892,(1),null);
var map__70811_70895 = ns_info_70894;
var map__70811_70896__$1 = cljs.core.__destructure_map(map__70811_70895);
var fixtures_70897 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70811_70896__$1,new cljs.core.Keyword(null,"fixtures","fixtures",1009814994));
var temp__5753__auto___70898 = new cljs.core.Keyword(null,"once","once",-262568523).cljs$core$IFn$_invoke$arity$1(fixtures_70897);
if(cljs.core.truth_(temp__5753__auto___70898)){
var fix_70899 = temp__5753__auto___70898;
cljs.test.update_current_env_BANG_.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"once-fixtures","once-fixtures",1253947167)], null),cljs.core.assoc,cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([test_ns_70893,fix_70899], 0));
} else {
}

var temp__5753__auto___70901 = new cljs.core.Keyword(null,"each","each",940016129).cljs$core$IFn$_invoke$arity$1(fixtures_70897);
if(cljs.core.truth_(temp__5753__auto___70901)){
var fix_70903 = temp__5753__auto___70901;
cljs.test.update_current_env_BANG_.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"each-fixtures","each-fixtures",802243977)], null),cljs.core.assoc,cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([test_ns_70893,fix_70903], 0));
} else {
}


var G__70904 = seq__70776_70885;
var G__70905 = chunk__70778_70886;
var G__70906 = count__70779_70887;
var G__70907 = (i__70780_70888 + (1));
seq__70776_70885 = G__70904;
chunk__70778_70886 = G__70905;
count__70779_70887 = G__70906;
i__70780_70888 = G__70907;
continue;
} else {
var temp__5753__auto___70909 = cljs.core.seq(seq__70776_70885);
if(temp__5753__auto___70909){
var seq__70776_70910__$1 = temp__5753__auto___70909;
if(cljs.core.chunked_seq_QMARK_(seq__70776_70910__$1)){
var c__5565__auto___70911 = cljs.core.chunk_first(seq__70776_70910__$1);
var G__70913 = cljs.core.chunk_rest(seq__70776_70910__$1);
var G__70914 = c__5565__auto___70911;
var G__70915 = cljs.core.count(c__5565__auto___70911);
var G__70916 = (0);
seq__70776_70885 = G__70913;
chunk__70778_70886 = G__70914;
count__70779_70887 = G__70915;
i__70780_70888 = G__70916;
continue;
} else {
var vec__70814_70917 = cljs.core.first(seq__70776_70910__$1);
var test_ns_70918 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__70814_70917,(0),null);
var ns_info_70919 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__70814_70917,(1),null);
var map__70817_70920 = ns_info_70919;
var map__70817_70921__$1 = cljs.core.__destructure_map(map__70817_70920);
var fixtures_70922 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70817_70921__$1,new cljs.core.Keyword(null,"fixtures","fixtures",1009814994));
var temp__5753__auto___70923__$1 = new cljs.core.Keyword(null,"once","once",-262568523).cljs$core$IFn$_invoke$arity$1(fixtures_70922);
if(cljs.core.truth_(temp__5753__auto___70923__$1)){
var fix_70924 = temp__5753__auto___70923__$1;
cljs.test.update_current_env_BANG_.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"once-fixtures","once-fixtures",1253947167)], null),cljs.core.assoc,cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([test_ns_70918,fix_70924], 0));
} else {
}

var temp__5753__auto___70925__$1 = new cljs.core.Keyword(null,"each","each",940016129).cljs$core$IFn$_invoke$arity$1(fixtures_70922);
if(cljs.core.truth_(temp__5753__auto___70925__$1)){
var fix_70926 = temp__5753__auto___70925__$1;
cljs.test.update_current_env_BANG_.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"each-fixtures","each-fixtures",802243977)], null),cljs.core.assoc,cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([test_ns_70918,fix_70926], 0));
} else {
}


var G__70927 = cljs.core.next(seq__70776_70910__$1);
var G__70928 = null;
var G__70929 = (0);
var G__70930 = (0);
seq__70776_70885 = G__70927;
chunk__70778_70886 = G__70928;
count__70779_70887 = G__70929;
i__70780_70888 = G__70930;
continue;
}
} else {
}
}
break;
}

return cljs.test.report.call(null,new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"type","type",1174270348),new cljs.core.Keyword(null,"begin-run-tests","begin-run-tests",309363062),new cljs.core.Keyword(null,"var-count","var-count",-1513152110),cljs.core.count(vars),new cljs.core.Keyword(null,"ns-count","ns-count",-1269070724),cljs.core.count(cljs.core.set(cljs.core.map.cljs$core$IFn$_invoke$arity$2((function (p1__70757_SHARP_){
return new cljs.core.Keyword(null,"ns","ns",441598760).cljs$core$IFn$_invoke$arity$1(cljs.core.meta(p1__70757_SHARP_));
}),vars)))], null));
})], null);
});
shadow.test.finish_test_run = (function shadow$test$finish_test_run(block){
if(cljs.core.vector_QMARK_(block)){
} else {
throw (new Error("Assert failed: (vector? block)"));
}

return cljs.core.conj.cljs$core$IFn$_invoke$arity$2(block,(function (){
var map__70820 = cljs.test.get_current_env();
var map__70820__$1 = cljs.core.__destructure_map(map__70820);
var env = map__70820__$1;
var report_fn = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70820__$1,new cljs.core.Keyword("shadow.test","report-fn","shadow.test/report-fn",1075704061));
var report_counters = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70820__$1,new cljs.core.Keyword(null,"report-counters","report-counters",-1702609242));
cljs.test.report.call(null,cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(report_counters,new cljs.core.Keyword(null,"type","type",1174270348),new cljs.core.Keyword(null,"summary","summary",380847952)));

cljs.test.report.call(null,cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(report_counters,new cljs.core.Keyword(null,"type","type",1174270348),new cljs.core.Keyword(null,"end-run-tests","end-run-tests",267300563)));

return (cljs.test.report = report_fn);
}));
});
/**
 * tests all vars grouped by namespace, expects seq of test vars, can be obtained from env
 */
shadow.test.run_test_vars = (function shadow$test$run_test_vars(var_args){
var G__70829 = arguments.length;
switch (G__70829) {
case 1:
return shadow.test.run_test_vars.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return shadow.test.run_test_vars.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.test.run_test_vars.cljs$core$IFn$_invoke$arity$1 = (function (test_vars){
return shadow.test.run_test_vars.cljs$core$IFn$_invoke$arity$2(cljs.test.empty_env.cljs$core$IFn$_invoke$arity$0(),test_vars);
}));

(shadow.test.run_test_vars.cljs$core$IFn$_invoke$arity$2 = (function (env,vars){
return cljs.test.run_block(shadow.test.finish_test_run(cljs.core.into.cljs$core$IFn$_invoke$arity$2(shadow.test.prepare_test_run(env,vars),shadow.test.test_vars_grouped_block(vars))));
}));

(shadow.test.run_test_vars.cljs$lang$maxFixedArity = 2);

/**
 * test all vars for given namespace symbol
 */
shadow.test.test_ns = (function shadow$test$test_ns(var_args){
var G__70835 = arguments.length;
switch (G__70835) {
case 1:
return shadow.test.test_ns.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return shadow.test.test_ns.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.test.test_ns.cljs$core$IFn$_invoke$arity$1 = (function (ns){
return shadow.test.test_ns.cljs$core$IFn$_invoke$arity$2(cljs.test.empty_env.cljs$core$IFn$_invoke$arity$0(),ns);
}));

(shadow.test.test_ns.cljs$core$IFn$_invoke$arity$2 = (function (env,ns){
var map__70837 = shadow.test.env.get_test_ns_info(ns);
var map__70837__$1 = cljs.core.__destructure_map(map__70837);
var vars = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70837__$1,new cljs.core.Keyword(null,"vars","vars",-2046957217));
return cljs.test.run_block(shadow.test.finish_test_run(cljs.core.into.cljs$core$IFn$_invoke$arity$2(shadow.test.prepare_test_run(env,vars),shadow.test.test_vars_grouped_block(vars))));
}));

(shadow.test.test_ns.cljs$lang$maxFixedArity = 2);

/**
 * test all vars in specified namespace symbol set
 */
shadow.test.run_tests = (function shadow$test$run_tests(var_args){
var G__70842 = arguments.length;
switch (G__70842) {
case 0:
return shadow.test.run_tests.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return shadow.test.run_tests.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return shadow.test.run_tests.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.test.run_tests.cljs$core$IFn$_invoke$arity$0 = (function (){
return shadow.test.run_tests.cljs$core$IFn$_invoke$arity$1(cljs.test.empty_env.cljs$core$IFn$_invoke$arity$0());
}));

(shadow.test.run_tests.cljs$core$IFn$_invoke$arity$1 = (function (env){
return shadow.test.run_tests.cljs$core$IFn$_invoke$arity$2(env,shadow.test.env.get_test_namespaces());
}));

(shadow.test.run_tests.cljs$core$IFn$_invoke$arity$2 = (function (env,namespaces){
if(cljs.core.set_QMARK_(namespaces)){
} else {
throw (new Error("Assert failed: (set? namespaces)"));
}

var vars = cljs.core.filter.cljs$core$IFn$_invoke$arity$2((function (p1__70839_SHARP_){
return cljs.core.contains_QMARK_(namespaces,new cljs.core.Keyword(null,"ns","ns",441598760).cljs$core$IFn$_invoke$arity$1(cljs.core.meta(p1__70839_SHARP_)));
}),shadow.test.env.get_test_vars());
return cljs.test.run_block(shadow.test.finish_test_run(cljs.core.into.cljs$core$IFn$_invoke$arity$2(shadow.test.prepare_test_run(env,vars),shadow.test.test_vars_grouped_block(vars))));
}));

(shadow.test.run_tests.cljs$lang$maxFixedArity = 2);

/**
 * Runs all tests in all namespaces; prints results.
 *   Optional argument is a regular expression; only namespaces with
 *   names matching the regular expression (with re-matches) will be
 *   tested.
 */
shadow.test.run_all_tests = (function shadow$test$run_all_tests(var_args){
var G__70854 = arguments.length;
switch (G__70854) {
case 0:
return shadow.test.run_all_tests.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return shadow.test.run_all_tests.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return shadow.test.run_all_tests.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(shadow.test.run_all_tests.cljs$core$IFn$_invoke$arity$0 = (function (){
return shadow.test.run_all_tests.cljs$core$IFn$_invoke$arity$2(cljs.test.empty_env.cljs$core$IFn$_invoke$arity$0(),null);
}));

(shadow.test.run_all_tests.cljs$core$IFn$_invoke$arity$1 = (function (env){
return shadow.test.run_all_tests.cljs$core$IFn$_invoke$arity$2(env,null);
}));

(shadow.test.run_all_tests.cljs$core$IFn$_invoke$arity$2 = (function (env,re){
return shadow.test.run_tests.cljs$core$IFn$_invoke$arity$2(env,cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentHashSet.EMPTY,cljs.core.filter.cljs$core$IFn$_invoke$arity$2((function (p1__70851_SHARP_){
var or__5043__auto__ = (re == null);
if(or__5043__auto__){
return or__5043__auto__;
} else {
return cljs.core.re_matches(re,cljs.core.str.cljs$core$IFn$_invoke$arity$1(p1__70851_SHARP_));
}
}),shadow.test.env.get_test_namespaces())));
}));

(shadow.test.run_all_tests.cljs$lang$maxFixedArity = 2);


//# sourceMappingURL=shadow.test.js.map
