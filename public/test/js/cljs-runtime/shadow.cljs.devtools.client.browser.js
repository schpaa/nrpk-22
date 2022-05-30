goog.provide('shadow.cljs.devtools.client.browser');
shadow.cljs.devtools.client.browser.devtools_msg = (function shadow$cljs$devtools$client$browser$devtools_msg(var_args){
var args__5772__auto__ = [];
var len__5766__auto___71115 = arguments.length;
var i__5767__auto___71116 = (0);
while(true){
if((i__5767__auto___71116 < len__5766__auto___71115)){
args__5772__auto__.push((arguments[i__5767__auto___71116]));

var G__71117 = (i__5767__auto___71116 + (1));
i__5767__auto___71116 = G__71117;
continue;
} else {
}
break;
}

var argseq__5773__auto__ = ((((1) < args__5772__auto__.length))?(new cljs.core.IndexedSeq(args__5772__auto__.slice((1)),(0),null)):null);
return shadow.cljs.devtools.client.browser.devtools_msg.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__5773__auto__);
});

(shadow.cljs.devtools.client.browser.devtools_msg.cljs$core$IFn$_invoke$arity$variadic = (function (msg,args){
if(shadow.cljs.devtools.client.env.log){
if(cljs.core.seq(shadow.cljs.devtools.client.env.log_style)){
return console.log.apply(console,cljs.core.into_array.cljs$core$IFn$_invoke$arity$1(cljs.core.into.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [["%cshadow-cljs: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(msg)].join(''),shadow.cljs.devtools.client.env.log_style], null),args)));
} else {
return console.log.apply(console,cljs.core.into_array.cljs$core$IFn$_invoke$arity$1(cljs.core.into.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [["shadow-cljs: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(msg)].join('')], null),args)));
}
} else {
return null;
}
}));

(shadow.cljs.devtools.client.browser.devtools_msg.cljs$lang$maxFixedArity = (1));

/** @this {Function} */
(shadow.cljs.devtools.client.browser.devtools_msg.cljs$lang$applyTo = (function (seq70583){
var G__70584 = cljs.core.first(seq70583);
var seq70583__$1 = cljs.core.next(seq70583);
var self__5751__auto__ = this;
return self__5751__auto__.cljs$core$IFn$_invoke$arity$variadic(G__70584,seq70583__$1);
}));

shadow.cljs.devtools.client.browser.script_eval = (function shadow$cljs$devtools$client$browser$script_eval(code){
return goog.globalEval(code);
});
shadow.cljs.devtools.client.browser.do_js_load = (function shadow$cljs$devtools$client$browser$do_js_load(sources){
var seq__70588 = cljs.core.seq(sources);
var chunk__70589 = null;
var count__70590 = (0);
var i__70591 = (0);
while(true){
if((i__70591 < count__70590)){
var map__70599 = chunk__70589.cljs$core$IIndexed$_nth$arity$2(null,i__70591);
var map__70599__$1 = cljs.core.__destructure_map(map__70599);
var src = map__70599__$1;
var resource_id = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70599__$1,new cljs.core.Keyword(null,"resource-id","resource-id",-1308422582));
var output_name = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70599__$1,new cljs.core.Keyword(null,"output-name","output-name",-1769107767));
var resource_name = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70599__$1,new cljs.core.Keyword(null,"resource-name","resource-name",2001617100));
var js = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70599__$1,new cljs.core.Keyword(null,"js","js",1768080579));
$CLJS.SHADOW_ENV.setLoaded(output_name);

shadow.cljs.devtools.client.browser.devtools_msg.cljs$core$IFn$_invoke$arity$variadic("load JS",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([resource_name], 0));

shadow.cljs.devtools.client.env.before_load_src(src);

try{shadow.cljs.devtools.client.browser.script_eval([cljs.core.str.cljs$core$IFn$_invoke$arity$1(js),"\n//# sourceURL=",cljs.core.str.cljs$core$IFn$_invoke$arity$1($CLJS.SHADOW_ENV.scriptBase),cljs.core.str.cljs$core$IFn$_invoke$arity$1(output_name)].join(''));
}catch (e70601){var e_71118 = e70601;
if(shadow.cljs.devtools.client.env.log){
console.error(["Failed to load ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(resource_name)].join(''),e_71118);
} else {
}

throw (new Error(["Failed to load ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(resource_name),": ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(e_71118.message)].join('')));
}

var G__71119 = seq__70588;
var G__71120 = chunk__70589;
var G__71121 = count__70590;
var G__71122 = (i__70591 + (1));
seq__70588 = G__71119;
chunk__70589 = G__71120;
count__70590 = G__71121;
i__70591 = G__71122;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__70588);
if(temp__5753__auto__){
var seq__70588__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__70588__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__70588__$1);
var G__71123 = cljs.core.chunk_rest(seq__70588__$1);
var G__71124 = c__5565__auto__;
var G__71125 = cljs.core.count(c__5565__auto__);
var G__71126 = (0);
seq__70588 = G__71123;
chunk__70589 = G__71124;
count__70590 = G__71125;
i__70591 = G__71126;
continue;
} else {
var map__70608 = cljs.core.first(seq__70588__$1);
var map__70608__$1 = cljs.core.__destructure_map(map__70608);
var src = map__70608__$1;
var resource_id = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70608__$1,new cljs.core.Keyword(null,"resource-id","resource-id",-1308422582));
var output_name = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70608__$1,new cljs.core.Keyword(null,"output-name","output-name",-1769107767));
var resource_name = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70608__$1,new cljs.core.Keyword(null,"resource-name","resource-name",2001617100));
var js = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70608__$1,new cljs.core.Keyword(null,"js","js",1768080579));
$CLJS.SHADOW_ENV.setLoaded(output_name);

shadow.cljs.devtools.client.browser.devtools_msg.cljs$core$IFn$_invoke$arity$variadic("load JS",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([resource_name], 0));

shadow.cljs.devtools.client.env.before_load_src(src);

try{shadow.cljs.devtools.client.browser.script_eval([cljs.core.str.cljs$core$IFn$_invoke$arity$1(js),"\n//# sourceURL=",cljs.core.str.cljs$core$IFn$_invoke$arity$1($CLJS.SHADOW_ENV.scriptBase),cljs.core.str.cljs$core$IFn$_invoke$arity$1(output_name)].join(''));
}catch (e70609){var e_71127 = e70609;
if(shadow.cljs.devtools.client.env.log){
console.error(["Failed to load ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(resource_name)].join(''),e_71127);
} else {
}

throw (new Error(["Failed to load ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(resource_name),": ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(e_71127.message)].join('')));
}

var G__71128 = cljs.core.next(seq__70588__$1);
var G__71129 = null;
var G__71130 = (0);
var G__71131 = (0);
seq__70588 = G__71128;
chunk__70589 = G__71129;
count__70590 = G__71130;
i__70591 = G__71131;
continue;
}
} else {
return null;
}
}
break;
}
});
shadow.cljs.devtools.client.browser.do_js_reload = (function shadow$cljs$devtools$client$browser$do_js_reload(msg,sources,complete_fn,failure_fn){
return shadow.cljs.devtools.client.env.do_js_reload.cljs$core$IFn$_invoke$arity$4(cljs.core.assoc.cljs$core$IFn$_invoke$arity$variadic(msg,new cljs.core.Keyword(null,"log-missing-fn","log-missing-fn",732676765),(function (fn_sym){
return null;
}),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"log-call-async","log-call-async",183826192),(function (fn_sym){
return shadow.cljs.devtools.client.browser.devtools_msg(["call async ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(fn_sym)].join(''));
}),new cljs.core.Keyword(null,"log-call","log-call",412404391),(function (fn_sym){
return shadow.cljs.devtools.client.browser.devtools_msg(["call ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(fn_sym)].join(''));
})], 0)),(function (){
return shadow.cljs.devtools.client.browser.do_js_load(sources);
}),complete_fn,failure_fn);
});
/**
 * when (require '["some-str" :as x]) is done at the REPL we need to manually call the shadow.js.require for it
 * since the file only adds the shadow$provide. only need to do this for shadow-js.
 */
shadow.cljs.devtools.client.browser.do_js_requires = (function shadow$cljs$devtools$client$browser$do_js_requires(js_requires){
var seq__70614 = cljs.core.seq(js_requires);
var chunk__70615 = null;
var count__70616 = (0);
var i__70617 = (0);
while(true){
if((i__70617 < count__70616)){
var js_ns = chunk__70615.cljs$core$IIndexed$_nth$arity$2(null,i__70617);
var require_str_71132 = ["var ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(js_ns)," = shadow.js.require(\"",cljs.core.str.cljs$core$IFn$_invoke$arity$1(js_ns),"\");"].join('');
shadow.cljs.devtools.client.browser.script_eval(require_str_71132);


var G__71133 = seq__70614;
var G__71134 = chunk__70615;
var G__71135 = count__70616;
var G__71136 = (i__70617 + (1));
seq__70614 = G__71133;
chunk__70615 = G__71134;
count__70616 = G__71135;
i__70617 = G__71136;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__70614);
if(temp__5753__auto__){
var seq__70614__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__70614__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__70614__$1);
var G__71137 = cljs.core.chunk_rest(seq__70614__$1);
var G__71138 = c__5565__auto__;
var G__71139 = cljs.core.count(c__5565__auto__);
var G__71140 = (0);
seq__70614 = G__71137;
chunk__70615 = G__71138;
count__70616 = G__71139;
i__70617 = G__71140;
continue;
} else {
var js_ns = cljs.core.first(seq__70614__$1);
var require_str_71141 = ["var ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(js_ns)," = shadow.js.require(\"",cljs.core.str.cljs$core$IFn$_invoke$arity$1(js_ns),"\");"].join('');
shadow.cljs.devtools.client.browser.script_eval(require_str_71141);


var G__71142 = cljs.core.next(seq__70614__$1);
var G__71143 = null;
var G__71144 = (0);
var G__71145 = (0);
seq__70614 = G__71142;
chunk__70615 = G__71143;
count__70616 = G__71144;
i__70617 = G__71145;
continue;
}
} else {
return null;
}
}
break;
}
});
shadow.cljs.devtools.client.browser.handle_build_complete = (function shadow$cljs$devtools$client$browser$handle_build_complete(runtime,p__70623){
var map__70624 = p__70623;
var map__70624__$1 = cljs.core.__destructure_map(map__70624);
var msg = map__70624__$1;
var info = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70624__$1,new cljs.core.Keyword(null,"info","info",-317069002));
var reload_info = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70624__$1,new cljs.core.Keyword(null,"reload-info","reload-info",1648088086));
var warnings = cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentVector.EMPTY,cljs.core.distinct.cljs$core$IFn$_invoke$arity$1((function (){var iter__5520__auto__ = (function shadow$cljs$devtools$client$browser$handle_build_complete_$_iter__70627(s__70628){
return (new cljs.core.LazySeq(null,(function (){
var s__70628__$1 = s__70628;
while(true){
var temp__5753__auto__ = cljs.core.seq(s__70628__$1);
if(temp__5753__auto__){
var xs__6308__auto__ = temp__5753__auto__;
var map__70633 = cljs.core.first(xs__6308__auto__);
var map__70633__$1 = cljs.core.__destructure_map(map__70633);
var src = map__70633__$1;
var resource_name = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70633__$1,new cljs.core.Keyword(null,"resource-name","resource-name",2001617100));
var warnings = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70633__$1,new cljs.core.Keyword(null,"warnings","warnings",-735437651));
if(cljs.core.not(new cljs.core.Keyword(null,"from-jar","from-jar",1050932827).cljs$core$IFn$_invoke$arity$1(src))){
var iterys__5516__auto__ = ((function (s__70628__$1,map__70633,map__70633__$1,src,resource_name,warnings,xs__6308__auto__,temp__5753__auto__,map__70624,map__70624__$1,msg,info,reload_info){
return (function shadow$cljs$devtools$client$browser$handle_build_complete_$_iter__70627_$_iter__70629(s__70630){
return (new cljs.core.LazySeq(null,((function (s__70628__$1,map__70633,map__70633__$1,src,resource_name,warnings,xs__6308__auto__,temp__5753__auto__,map__70624,map__70624__$1,msg,info,reload_info){
return (function (){
var s__70630__$1 = s__70630;
while(true){
var temp__5753__auto____$1 = cljs.core.seq(s__70630__$1);
if(temp__5753__auto____$1){
var s__70630__$2 = temp__5753__auto____$1;
if(cljs.core.chunked_seq_QMARK_(s__70630__$2)){
var c__5518__auto__ = cljs.core.chunk_first(s__70630__$2);
var size__5519__auto__ = cljs.core.count(c__5518__auto__);
var b__70632 = cljs.core.chunk_buffer(size__5519__auto__);
if((function (){var i__70631 = (0);
while(true){
if((i__70631 < size__5519__auto__)){
var warning = cljs.core._nth(c__5518__auto__,i__70631);
cljs.core.chunk_append(b__70632,cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(warning,new cljs.core.Keyword(null,"resource-name","resource-name",2001617100),resource_name));

var G__71146 = (i__70631 + (1));
i__70631 = G__71146;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons(cljs.core.chunk(b__70632),shadow$cljs$devtools$client$browser$handle_build_complete_$_iter__70627_$_iter__70629(cljs.core.chunk_rest(s__70630__$2)));
} else {
return cljs.core.chunk_cons(cljs.core.chunk(b__70632),null);
}
} else {
var warning = cljs.core.first(s__70630__$2);
return cljs.core.cons(cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(warning,new cljs.core.Keyword(null,"resource-name","resource-name",2001617100),resource_name),shadow$cljs$devtools$client$browser$handle_build_complete_$_iter__70627_$_iter__70629(cljs.core.rest(s__70630__$2)));
}
} else {
return null;
}
break;
}
});})(s__70628__$1,map__70633,map__70633__$1,src,resource_name,warnings,xs__6308__auto__,temp__5753__auto__,map__70624,map__70624__$1,msg,info,reload_info))
,null,null));
});})(s__70628__$1,map__70633,map__70633__$1,src,resource_name,warnings,xs__6308__auto__,temp__5753__auto__,map__70624,map__70624__$1,msg,info,reload_info))
;
var fs__5517__auto__ = cljs.core.seq(iterys__5516__auto__(warnings));
if(fs__5517__auto__){
return cljs.core.concat.cljs$core$IFn$_invoke$arity$2(fs__5517__auto__,shadow$cljs$devtools$client$browser$handle_build_complete_$_iter__70627(cljs.core.rest(s__70628__$1)));
} else {
var G__71147 = cljs.core.rest(s__70628__$1);
s__70628__$1 = G__71147;
continue;
}
} else {
var G__71148 = cljs.core.rest(s__70628__$1);
s__70628__$1 = G__71148;
continue;
}
} else {
return null;
}
break;
}
}),null,null));
});
return iter__5520__auto__(new cljs.core.Keyword(null,"sources","sources",-321166424).cljs$core$IFn$_invoke$arity$1(info));
})()));
if(shadow.cljs.devtools.client.env.log){
var seq__70635_71149 = cljs.core.seq(warnings);
var chunk__70636_71150 = null;
var count__70637_71151 = (0);
var i__70638_71152 = (0);
while(true){
if((i__70638_71152 < count__70637_71151)){
var map__70641_71153 = chunk__70636_71150.cljs$core$IIndexed$_nth$arity$2(null,i__70638_71152);
var map__70641_71154__$1 = cljs.core.__destructure_map(map__70641_71153);
var w_71155 = map__70641_71154__$1;
var msg_71156__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70641_71154__$1,new cljs.core.Keyword(null,"msg","msg",-1386103444));
var line_71157 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70641_71154__$1,new cljs.core.Keyword(null,"line","line",212345235));
var column_71158 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70641_71154__$1,new cljs.core.Keyword(null,"column","column",2078222095));
var resource_name_71159 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70641_71154__$1,new cljs.core.Keyword(null,"resource-name","resource-name",2001617100));
console.warn(["BUILD-WARNING in ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(resource_name_71159)," at [",cljs.core.str.cljs$core$IFn$_invoke$arity$1(line_71157),":",cljs.core.str.cljs$core$IFn$_invoke$arity$1(column_71158),"]\n\t",cljs.core.str.cljs$core$IFn$_invoke$arity$1(msg_71156__$1)].join(''));


var G__71160 = seq__70635_71149;
var G__71161 = chunk__70636_71150;
var G__71162 = count__70637_71151;
var G__71163 = (i__70638_71152 + (1));
seq__70635_71149 = G__71160;
chunk__70636_71150 = G__71161;
count__70637_71151 = G__71162;
i__70638_71152 = G__71163;
continue;
} else {
var temp__5753__auto___71164 = cljs.core.seq(seq__70635_71149);
if(temp__5753__auto___71164){
var seq__70635_71165__$1 = temp__5753__auto___71164;
if(cljs.core.chunked_seq_QMARK_(seq__70635_71165__$1)){
var c__5565__auto___71166 = cljs.core.chunk_first(seq__70635_71165__$1);
var G__71167 = cljs.core.chunk_rest(seq__70635_71165__$1);
var G__71168 = c__5565__auto___71166;
var G__71169 = cljs.core.count(c__5565__auto___71166);
var G__71170 = (0);
seq__70635_71149 = G__71167;
chunk__70636_71150 = G__71168;
count__70637_71151 = G__71169;
i__70638_71152 = G__71170;
continue;
} else {
var map__70642_71171 = cljs.core.first(seq__70635_71165__$1);
var map__70642_71172__$1 = cljs.core.__destructure_map(map__70642_71171);
var w_71173 = map__70642_71172__$1;
var msg_71174__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70642_71172__$1,new cljs.core.Keyword(null,"msg","msg",-1386103444));
var line_71175 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70642_71172__$1,new cljs.core.Keyword(null,"line","line",212345235));
var column_71176 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70642_71172__$1,new cljs.core.Keyword(null,"column","column",2078222095));
var resource_name_71177 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70642_71172__$1,new cljs.core.Keyword(null,"resource-name","resource-name",2001617100));
console.warn(["BUILD-WARNING in ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(resource_name_71177)," at [",cljs.core.str.cljs$core$IFn$_invoke$arity$1(line_71175),":",cljs.core.str.cljs$core$IFn$_invoke$arity$1(column_71176),"]\n\t",cljs.core.str.cljs$core$IFn$_invoke$arity$1(msg_71174__$1)].join(''));


var G__71178 = cljs.core.next(seq__70635_71165__$1);
var G__71179 = null;
var G__71180 = (0);
var G__71181 = (0);
seq__70635_71149 = G__71178;
chunk__70636_71150 = G__71179;
count__70637_71151 = G__71180;
i__70638_71152 = G__71181;
continue;
}
} else {
}
}
break;
}
} else {
}

if((!(shadow.cljs.devtools.client.env.autoload))){
return shadow.cljs.devtools.client.hud.load_end_success();
} else {
if(((cljs.core.empty_QMARK_(warnings)) || (shadow.cljs.devtools.client.env.ignore_warnings))){
var sources_to_get = shadow.cljs.devtools.client.env.filter_reload_sources(info,reload_info);
if(cljs.core.not(cljs.core.seq(sources_to_get))){
return shadow.cljs.devtools.client.hud.load_end_success();
} else {
if(cljs.core.seq(cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(msg,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"reload-info","reload-info",1648088086),new cljs.core.Keyword(null,"after-load","after-load",-1278503285)], null)))){
} else {
shadow.cljs.devtools.client.browser.devtools_msg.cljs$core$IFn$_invoke$arity$variadic("reloading code but no :after-load hooks are configured!",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["https://shadow-cljs.github.io/docs/UsersGuide.html#_lifecycle_hooks"], 0));
}

return shadow.cljs.devtools.client.shared.load_sources(runtime,sources_to_get,(function (p1__70622_SHARP_){
return shadow.cljs.devtools.client.browser.do_js_reload(msg,p1__70622_SHARP_,shadow.cljs.devtools.client.hud.load_end_success,shadow.cljs.devtools.client.hud.load_failure);
}));
}
} else {
return null;
}
}
});
shadow.cljs.devtools.client.browser.page_load_uri = (cljs.core.truth_(goog.global.document)?goog.Uri.parse(document.location.href):null);
shadow.cljs.devtools.client.browser.match_paths = (function shadow$cljs$devtools$client$browser$match_paths(old,new$){
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2("file",shadow.cljs.devtools.client.browser.page_load_uri.getScheme())){
var rel_new = cljs.core.subs.cljs$core$IFn$_invoke$arity$2(new$,(1));
if(((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(old,rel_new)) || (clojure.string.starts_with_QMARK_(old,[rel_new,"?"].join(''))))){
return rel_new;
} else {
return null;
}
} else {
var node_uri = goog.Uri.parse(old);
var node_uri_resolved = shadow.cljs.devtools.client.browser.page_load_uri.resolve(node_uri);
var node_abs = node_uri_resolved.getPath();
var and__5041__auto__ = ((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$1(shadow.cljs.devtools.client.browser.page_load_uri.hasSameDomainAs(node_uri))) || (cljs.core.not(node_uri.hasDomain())));
if(and__5041__auto__){
var and__5041__auto____$1 = cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(node_abs,new$);
if(and__5041__auto____$1){
return new$;
} else {
return and__5041__auto____$1;
}
} else {
return and__5041__auto__;
}
}
});
shadow.cljs.devtools.client.browser.handle_asset_update = (function shadow$cljs$devtools$client$browser$handle_asset_update(p__70658){
var map__70659 = p__70658;
var map__70659__$1 = cljs.core.__destructure_map(map__70659);
var msg = map__70659__$1;
var updates = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70659__$1,new cljs.core.Keyword(null,"updates","updates",2013983452));
var reload_info = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70659__$1,new cljs.core.Keyword(null,"reload-info","reload-info",1648088086));
var seq__70660 = cljs.core.seq(updates);
var chunk__70662 = null;
var count__70663 = (0);
var i__70664 = (0);
while(true){
if((i__70664 < count__70663)){
var path = chunk__70662.cljs$core$IIndexed$_nth$arity$2(null,i__70664);
if(clojure.string.ends_with_QMARK_(path,"css")){
var seq__70931_71182 = cljs.core.seq(cljs.core.array_seq.cljs$core$IFn$_invoke$arity$1(document.querySelectorAll("link[rel=\"stylesheet\"]")));
var chunk__70935_71183 = null;
var count__70936_71184 = (0);
var i__70937_71185 = (0);
while(true){
if((i__70937_71185 < count__70936_71184)){
var node_71186 = chunk__70935_71183.cljs$core$IIndexed$_nth$arity$2(null,i__70937_71185);
if(cljs.core.not(node_71186.shadow$old)){
var path_match_71187 = shadow.cljs.devtools.client.browser.match_paths(node_71186.getAttribute("href"),path);
if(cljs.core.truth_(path_match_71187)){
var new_link_71188 = (function (){var G__70979 = node_71186.cloneNode(true);
G__70979.setAttribute("href",[cljs.core.str.cljs$core$IFn$_invoke$arity$1(path_match_71187),"?r=",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.rand.cljs$core$IFn$_invoke$arity$0())].join(''));

return G__70979;
})();
(node_71186.shadow$old = true);

(new_link_71188.onload = ((function (seq__70931_71182,chunk__70935_71183,count__70936_71184,i__70937_71185,seq__70660,chunk__70662,count__70663,i__70664,new_link_71188,path_match_71187,node_71186,path,map__70659,map__70659__$1,msg,updates,reload_info){
return (function (e){
var seq__70983_71189 = cljs.core.seq(cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(msg,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"reload-info","reload-info",1648088086),new cljs.core.Keyword(null,"asset-load","asset-load",-1925902322)], null)));
var chunk__70985_71190 = null;
var count__70986_71191 = (0);
var i__70987_71192 = (0);
while(true){
if((i__70987_71192 < count__70986_71191)){
var map__70991_71193 = chunk__70985_71190.cljs$core$IIndexed$_nth$arity$2(null,i__70987_71192);
var map__70991_71194__$1 = cljs.core.__destructure_map(map__70991_71193);
var task_71195 = map__70991_71194__$1;
var fn_str_71196 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70991_71194__$1,new cljs.core.Keyword(null,"fn-str","fn-str",-1348506402));
var fn_sym_71197 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70991_71194__$1,new cljs.core.Keyword(null,"fn-sym","fn-sym",1423988510));
var fn_obj_71198 = goog.getObjectByName(fn_str_71196,$CLJS);
shadow.cljs.devtools.client.browser.devtools_msg(["call ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(fn_sym_71197)].join(''));

(fn_obj_71198.cljs$core$IFn$_invoke$arity$2 ? fn_obj_71198.cljs$core$IFn$_invoke$arity$2(path,new_link_71188) : fn_obj_71198.call(null,path,new_link_71188));


var G__71199 = seq__70983_71189;
var G__71200 = chunk__70985_71190;
var G__71201 = count__70986_71191;
var G__71202 = (i__70987_71192 + (1));
seq__70983_71189 = G__71199;
chunk__70985_71190 = G__71200;
count__70986_71191 = G__71201;
i__70987_71192 = G__71202;
continue;
} else {
var temp__5753__auto___71203 = cljs.core.seq(seq__70983_71189);
if(temp__5753__auto___71203){
var seq__70983_71204__$1 = temp__5753__auto___71203;
if(cljs.core.chunked_seq_QMARK_(seq__70983_71204__$1)){
var c__5565__auto___71205 = cljs.core.chunk_first(seq__70983_71204__$1);
var G__71206 = cljs.core.chunk_rest(seq__70983_71204__$1);
var G__71207 = c__5565__auto___71205;
var G__71208 = cljs.core.count(c__5565__auto___71205);
var G__71209 = (0);
seq__70983_71189 = G__71206;
chunk__70985_71190 = G__71207;
count__70986_71191 = G__71208;
i__70987_71192 = G__71209;
continue;
} else {
var map__70995_71210 = cljs.core.first(seq__70983_71204__$1);
var map__70995_71211__$1 = cljs.core.__destructure_map(map__70995_71210);
var task_71212 = map__70995_71211__$1;
var fn_str_71213 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70995_71211__$1,new cljs.core.Keyword(null,"fn-str","fn-str",-1348506402));
var fn_sym_71214 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__70995_71211__$1,new cljs.core.Keyword(null,"fn-sym","fn-sym",1423988510));
var fn_obj_71215 = goog.getObjectByName(fn_str_71213,$CLJS);
shadow.cljs.devtools.client.browser.devtools_msg(["call ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(fn_sym_71214)].join(''));

(fn_obj_71215.cljs$core$IFn$_invoke$arity$2 ? fn_obj_71215.cljs$core$IFn$_invoke$arity$2(path,new_link_71188) : fn_obj_71215.call(null,path,new_link_71188));


var G__71216 = cljs.core.next(seq__70983_71204__$1);
var G__71217 = null;
var G__71218 = (0);
var G__71219 = (0);
seq__70983_71189 = G__71216;
chunk__70985_71190 = G__71217;
count__70986_71191 = G__71218;
i__70987_71192 = G__71219;
continue;
}
} else {
}
}
break;
}

return goog.dom.removeNode(node_71186);
});})(seq__70931_71182,chunk__70935_71183,count__70936_71184,i__70937_71185,seq__70660,chunk__70662,count__70663,i__70664,new_link_71188,path_match_71187,node_71186,path,map__70659,map__70659__$1,msg,updates,reload_info))
);

shadow.cljs.devtools.client.browser.devtools_msg.cljs$core$IFn$_invoke$arity$variadic("load CSS",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([path_match_71187], 0));

goog.dom.insertSiblingAfter(new_link_71188,node_71186);


var G__71220 = seq__70931_71182;
var G__71221 = chunk__70935_71183;
var G__71222 = count__70936_71184;
var G__71223 = (i__70937_71185 + (1));
seq__70931_71182 = G__71220;
chunk__70935_71183 = G__71221;
count__70936_71184 = G__71222;
i__70937_71185 = G__71223;
continue;
} else {
var G__71224 = seq__70931_71182;
var G__71225 = chunk__70935_71183;
var G__71226 = count__70936_71184;
var G__71227 = (i__70937_71185 + (1));
seq__70931_71182 = G__71224;
chunk__70935_71183 = G__71225;
count__70936_71184 = G__71226;
i__70937_71185 = G__71227;
continue;
}
} else {
var G__71228 = seq__70931_71182;
var G__71229 = chunk__70935_71183;
var G__71230 = count__70936_71184;
var G__71231 = (i__70937_71185 + (1));
seq__70931_71182 = G__71228;
chunk__70935_71183 = G__71229;
count__70936_71184 = G__71230;
i__70937_71185 = G__71231;
continue;
}
} else {
var temp__5753__auto___71232 = cljs.core.seq(seq__70931_71182);
if(temp__5753__auto___71232){
var seq__70931_71233__$1 = temp__5753__auto___71232;
if(cljs.core.chunked_seq_QMARK_(seq__70931_71233__$1)){
var c__5565__auto___71234 = cljs.core.chunk_first(seq__70931_71233__$1);
var G__71235 = cljs.core.chunk_rest(seq__70931_71233__$1);
var G__71236 = c__5565__auto___71234;
var G__71237 = cljs.core.count(c__5565__auto___71234);
var G__71238 = (0);
seq__70931_71182 = G__71235;
chunk__70935_71183 = G__71236;
count__70936_71184 = G__71237;
i__70937_71185 = G__71238;
continue;
} else {
var node_71239 = cljs.core.first(seq__70931_71233__$1);
if(cljs.core.not(node_71239.shadow$old)){
var path_match_71240 = shadow.cljs.devtools.client.browser.match_paths(node_71239.getAttribute("href"),path);
if(cljs.core.truth_(path_match_71240)){
var new_link_71241 = (function (){var G__71000 = node_71239.cloneNode(true);
G__71000.setAttribute("href",[cljs.core.str.cljs$core$IFn$_invoke$arity$1(path_match_71240),"?r=",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.rand.cljs$core$IFn$_invoke$arity$0())].join(''));

return G__71000;
})();
(node_71239.shadow$old = true);

(new_link_71241.onload = ((function (seq__70931_71182,chunk__70935_71183,count__70936_71184,i__70937_71185,seq__70660,chunk__70662,count__70663,i__70664,new_link_71241,path_match_71240,node_71239,seq__70931_71233__$1,temp__5753__auto___71232,path,map__70659,map__70659__$1,msg,updates,reload_info){
return (function (e){
var seq__71001_71242 = cljs.core.seq(cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(msg,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"reload-info","reload-info",1648088086),new cljs.core.Keyword(null,"asset-load","asset-load",-1925902322)], null)));
var chunk__71003_71243 = null;
var count__71004_71244 = (0);
var i__71005_71245 = (0);
while(true){
if((i__71005_71245 < count__71004_71244)){
var map__71011_71246 = chunk__71003_71243.cljs$core$IIndexed$_nth$arity$2(null,i__71005_71245);
var map__71011_71247__$1 = cljs.core.__destructure_map(map__71011_71246);
var task_71248 = map__71011_71247__$1;
var fn_str_71249 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71011_71247__$1,new cljs.core.Keyword(null,"fn-str","fn-str",-1348506402));
var fn_sym_71250 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71011_71247__$1,new cljs.core.Keyword(null,"fn-sym","fn-sym",1423988510));
var fn_obj_71251 = goog.getObjectByName(fn_str_71249,$CLJS);
shadow.cljs.devtools.client.browser.devtools_msg(["call ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(fn_sym_71250)].join(''));

(fn_obj_71251.cljs$core$IFn$_invoke$arity$2 ? fn_obj_71251.cljs$core$IFn$_invoke$arity$2(path,new_link_71241) : fn_obj_71251.call(null,path,new_link_71241));


var G__71252 = seq__71001_71242;
var G__71253 = chunk__71003_71243;
var G__71254 = count__71004_71244;
var G__71255 = (i__71005_71245 + (1));
seq__71001_71242 = G__71252;
chunk__71003_71243 = G__71253;
count__71004_71244 = G__71254;
i__71005_71245 = G__71255;
continue;
} else {
var temp__5753__auto___71256__$1 = cljs.core.seq(seq__71001_71242);
if(temp__5753__auto___71256__$1){
var seq__71001_71257__$1 = temp__5753__auto___71256__$1;
if(cljs.core.chunked_seq_QMARK_(seq__71001_71257__$1)){
var c__5565__auto___71258 = cljs.core.chunk_first(seq__71001_71257__$1);
var G__71259 = cljs.core.chunk_rest(seq__71001_71257__$1);
var G__71260 = c__5565__auto___71258;
var G__71261 = cljs.core.count(c__5565__auto___71258);
var G__71262 = (0);
seq__71001_71242 = G__71259;
chunk__71003_71243 = G__71260;
count__71004_71244 = G__71261;
i__71005_71245 = G__71262;
continue;
} else {
var map__71012_71263 = cljs.core.first(seq__71001_71257__$1);
var map__71012_71264__$1 = cljs.core.__destructure_map(map__71012_71263);
var task_71265 = map__71012_71264__$1;
var fn_str_71266 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71012_71264__$1,new cljs.core.Keyword(null,"fn-str","fn-str",-1348506402));
var fn_sym_71267 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71012_71264__$1,new cljs.core.Keyword(null,"fn-sym","fn-sym",1423988510));
var fn_obj_71268 = goog.getObjectByName(fn_str_71266,$CLJS);
shadow.cljs.devtools.client.browser.devtools_msg(["call ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(fn_sym_71267)].join(''));

(fn_obj_71268.cljs$core$IFn$_invoke$arity$2 ? fn_obj_71268.cljs$core$IFn$_invoke$arity$2(path,new_link_71241) : fn_obj_71268.call(null,path,new_link_71241));


var G__71269 = cljs.core.next(seq__71001_71257__$1);
var G__71270 = null;
var G__71271 = (0);
var G__71272 = (0);
seq__71001_71242 = G__71269;
chunk__71003_71243 = G__71270;
count__71004_71244 = G__71271;
i__71005_71245 = G__71272;
continue;
}
} else {
}
}
break;
}

return goog.dom.removeNode(node_71239);
});})(seq__70931_71182,chunk__70935_71183,count__70936_71184,i__70937_71185,seq__70660,chunk__70662,count__70663,i__70664,new_link_71241,path_match_71240,node_71239,seq__70931_71233__$1,temp__5753__auto___71232,path,map__70659,map__70659__$1,msg,updates,reload_info))
);

shadow.cljs.devtools.client.browser.devtools_msg.cljs$core$IFn$_invoke$arity$variadic("load CSS",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([path_match_71240], 0));

goog.dom.insertSiblingAfter(new_link_71241,node_71239);


var G__71273 = cljs.core.next(seq__70931_71233__$1);
var G__71274 = null;
var G__71275 = (0);
var G__71276 = (0);
seq__70931_71182 = G__71273;
chunk__70935_71183 = G__71274;
count__70936_71184 = G__71275;
i__70937_71185 = G__71276;
continue;
} else {
var G__71277 = cljs.core.next(seq__70931_71233__$1);
var G__71278 = null;
var G__71279 = (0);
var G__71280 = (0);
seq__70931_71182 = G__71277;
chunk__70935_71183 = G__71278;
count__70936_71184 = G__71279;
i__70937_71185 = G__71280;
continue;
}
} else {
var G__71281 = cljs.core.next(seq__70931_71233__$1);
var G__71282 = null;
var G__71283 = (0);
var G__71284 = (0);
seq__70931_71182 = G__71281;
chunk__70935_71183 = G__71282;
count__70936_71184 = G__71283;
i__70937_71185 = G__71284;
continue;
}
}
} else {
}
}
break;
}


var G__71285 = seq__70660;
var G__71286 = chunk__70662;
var G__71287 = count__70663;
var G__71288 = (i__70664 + (1));
seq__70660 = G__71285;
chunk__70662 = G__71286;
count__70663 = G__71287;
i__70664 = G__71288;
continue;
} else {
var G__71289 = seq__70660;
var G__71290 = chunk__70662;
var G__71291 = count__70663;
var G__71292 = (i__70664 + (1));
seq__70660 = G__71289;
chunk__70662 = G__71290;
count__70663 = G__71291;
i__70664 = G__71292;
continue;
}
} else {
var temp__5753__auto__ = cljs.core.seq(seq__70660);
if(temp__5753__auto__){
var seq__70660__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__70660__$1)){
var c__5565__auto__ = cljs.core.chunk_first(seq__70660__$1);
var G__71293 = cljs.core.chunk_rest(seq__70660__$1);
var G__71294 = c__5565__auto__;
var G__71295 = cljs.core.count(c__5565__auto__);
var G__71296 = (0);
seq__70660 = G__71293;
chunk__70662 = G__71294;
count__70663 = G__71295;
i__70664 = G__71296;
continue;
} else {
var path = cljs.core.first(seq__70660__$1);
if(clojure.string.ends_with_QMARK_(path,"css")){
var seq__71015_71297 = cljs.core.seq(cljs.core.array_seq.cljs$core$IFn$_invoke$arity$1(document.querySelectorAll("link[rel=\"stylesheet\"]")));
var chunk__71019_71298 = null;
var count__71020_71299 = (0);
var i__71021_71300 = (0);
while(true){
if((i__71021_71300 < count__71020_71299)){
var node_71301 = chunk__71019_71298.cljs$core$IIndexed$_nth$arity$2(null,i__71021_71300);
if(cljs.core.not(node_71301.shadow$old)){
var path_match_71302 = shadow.cljs.devtools.client.browser.match_paths(node_71301.getAttribute("href"),path);
if(cljs.core.truth_(path_match_71302)){
var new_link_71303 = (function (){var G__71055 = node_71301.cloneNode(true);
G__71055.setAttribute("href",[cljs.core.str.cljs$core$IFn$_invoke$arity$1(path_match_71302),"?r=",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.rand.cljs$core$IFn$_invoke$arity$0())].join(''));

return G__71055;
})();
(node_71301.shadow$old = true);

(new_link_71303.onload = ((function (seq__71015_71297,chunk__71019_71298,count__71020_71299,i__71021_71300,seq__70660,chunk__70662,count__70663,i__70664,new_link_71303,path_match_71302,node_71301,path,seq__70660__$1,temp__5753__auto__,map__70659,map__70659__$1,msg,updates,reload_info){
return (function (e){
var seq__71057_71304 = cljs.core.seq(cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(msg,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"reload-info","reload-info",1648088086),new cljs.core.Keyword(null,"asset-load","asset-load",-1925902322)], null)));
var chunk__71059_71305 = null;
var count__71060_71306 = (0);
var i__71061_71307 = (0);
while(true){
if((i__71061_71307 < count__71060_71306)){
var map__71065_71308 = chunk__71059_71305.cljs$core$IIndexed$_nth$arity$2(null,i__71061_71307);
var map__71065_71309__$1 = cljs.core.__destructure_map(map__71065_71308);
var task_71310 = map__71065_71309__$1;
var fn_str_71311 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71065_71309__$1,new cljs.core.Keyword(null,"fn-str","fn-str",-1348506402));
var fn_sym_71312 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71065_71309__$1,new cljs.core.Keyword(null,"fn-sym","fn-sym",1423988510));
var fn_obj_71313 = goog.getObjectByName(fn_str_71311,$CLJS);
shadow.cljs.devtools.client.browser.devtools_msg(["call ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(fn_sym_71312)].join(''));

(fn_obj_71313.cljs$core$IFn$_invoke$arity$2 ? fn_obj_71313.cljs$core$IFn$_invoke$arity$2(path,new_link_71303) : fn_obj_71313.call(null,path,new_link_71303));


var G__71314 = seq__71057_71304;
var G__71315 = chunk__71059_71305;
var G__71316 = count__71060_71306;
var G__71317 = (i__71061_71307 + (1));
seq__71057_71304 = G__71314;
chunk__71059_71305 = G__71315;
count__71060_71306 = G__71316;
i__71061_71307 = G__71317;
continue;
} else {
var temp__5753__auto___71318__$1 = cljs.core.seq(seq__71057_71304);
if(temp__5753__auto___71318__$1){
var seq__71057_71319__$1 = temp__5753__auto___71318__$1;
if(cljs.core.chunked_seq_QMARK_(seq__71057_71319__$1)){
var c__5565__auto___71320 = cljs.core.chunk_first(seq__71057_71319__$1);
var G__71321 = cljs.core.chunk_rest(seq__71057_71319__$1);
var G__71322 = c__5565__auto___71320;
var G__71323 = cljs.core.count(c__5565__auto___71320);
var G__71324 = (0);
seq__71057_71304 = G__71321;
chunk__71059_71305 = G__71322;
count__71060_71306 = G__71323;
i__71061_71307 = G__71324;
continue;
} else {
var map__71068_71325 = cljs.core.first(seq__71057_71319__$1);
var map__71068_71326__$1 = cljs.core.__destructure_map(map__71068_71325);
var task_71327 = map__71068_71326__$1;
var fn_str_71328 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71068_71326__$1,new cljs.core.Keyword(null,"fn-str","fn-str",-1348506402));
var fn_sym_71329 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71068_71326__$1,new cljs.core.Keyword(null,"fn-sym","fn-sym",1423988510));
var fn_obj_71330 = goog.getObjectByName(fn_str_71328,$CLJS);
shadow.cljs.devtools.client.browser.devtools_msg(["call ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(fn_sym_71329)].join(''));

(fn_obj_71330.cljs$core$IFn$_invoke$arity$2 ? fn_obj_71330.cljs$core$IFn$_invoke$arity$2(path,new_link_71303) : fn_obj_71330.call(null,path,new_link_71303));


var G__71331 = cljs.core.next(seq__71057_71319__$1);
var G__71332 = null;
var G__71333 = (0);
var G__71334 = (0);
seq__71057_71304 = G__71331;
chunk__71059_71305 = G__71332;
count__71060_71306 = G__71333;
i__71061_71307 = G__71334;
continue;
}
} else {
}
}
break;
}

return goog.dom.removeNode(node_71301);
});})(seq__71015_71297,chunk__71019_71298,count__71020_71299,i__71021_71300,seq__70660,chunk__70662,count__70663,i__70664,new_link_71303,path_match_71302,node_71301,path,seq__70660__$1,temp__5753__auto__,map__70659,map__70659__$1,msg,updates,reload_info))
);

shadow.cljs.devtools.client.browser.devtools_msg.cljs$core$IFn$_invoke$arity$variadic("load CSS",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([path_match_71302], 0));

goog.dom.insertSiblingAfter(new_link_71303,node_71301);


var G__71335 = seq__71015_71297;
var G__71336 = chunk__71019_71298;
var G__71337 = count__71020_71299;
var G__71338 = (i__71021_71300 + (1));
seq__71015_71297 = G__71335;
chunk__71019_71298 = G__71336;
count__71020_71299 = G__71337;
i__71021_71300 = G__71338;
continue;
} else {
var G__71339 = seq__71015_71297;
var G__71340 = chunk__71019_71298;
var G__71341 = count__71020_71299;
var G__71342 = (i__71021_71300 + (1));
seq__71015_71297 = G__71339;
chunk__71019_71298 = G__71340;
count__71020_71299 = G__71341;
i__71021_71300 = G__71342;
continue;
}
} else {
var G__71343 = seq__71015_71297;
var G__71344 = chunk__71019_71298;
var G__71345 = count__71020_71299;
var G__71346 = (i__71021_71300 + (1));
seq__71015_71297 = G__71343;
chunk__71019_71298 = G__71344;
count__71020_71299 = G__71345;
i__71021_71300 = G__71346;
continue;
}
} else {
var temp__5753__auto___71347__$1 = cljs.core.seq(seq__71015_71297);
if(temp__5753__auto___71347__$1){
var seq__71015_71348__$1 = temp__5753__auto___71347__$1;
if(cljs.core.chunked_seq_QMARK_(seq__71015_71348__$1)){
var c__5565__auto___71349 = cljs.core.chunk_first(seq__71015_71348__$1);
var G__71350 = cljs.core.chunk_rest(seq__71015_71348__$1);
var G__71351 = c__5565__auto___71349;
var G__71352 = cljs.core.count(c__5565__auto___71349);
var G__71353 = (0);
seq__71015_71297 = G__71350;
chunk__71019_71298 = G__71351;
count__71020_71299 = G__71352;
i__71021_71300 = G__71353;
continue;
} else {
var node_71354 = cljs.core.first(seq__71015_71348__$1);
if(cljs.core.not(node_71354.shadow$old)){
var path_match_71355 = shadow.cljs.devtools.client.browser.match_paths(node_71354.getAttribute("href"),path);
if(cljs.core.truth_(path_match_71355)){
var new_link_71356 = (function (){var G__71069 = node_71354.cloneNode(true);
G__71069.setAttribute("href",[cljs.core.str.cljs$core$IFn$_invoke$arity$1(path_match_71355),"?r=",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.rand.cljs$core$IFn$_invoke$arity$0())].join(''));

return G__71069;
})();
(node_71354.shadow$old = true);

(new_link_71356.onload = ((function (seq__71015_71297,chunk__71019_71298,count__71020_71299,i__71021_71300,seq__70660,chunk__70662,count__70663,i__70664,new_link_71356,path_match_71355,node_71354,seq__71015_71348__$1,temp__5753__auto___71347__$1,path,seq__70660__$1,temp__5753__auto__,map__70659,map__70659__$1,msg,updates,reload_info){
return (function (e){
var seq__71070_71357 = cljs.core.seq(cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(msg,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"reload-info","reload-info",1648088086),new cljs.core.Keyword(null,"asset-load","asset-load",-1925902322)], null)));
var chunk__71072_71358 = null;
var count__71073_71359 = (0);
var i__71074_71360 = (0);
while(true){
if((i__71074_71360 < count__71073_71359)){
var map__71078_71361 = chunk__71072_71358.cljs$core$IIndexed$_nth$arity$2(null,i__71074_71360);
var map__71078_71362__$1 = cljs.core.__destructure_map(map__71078_71361);
var task_71363 = map__71078_71362__$1;
var fn_str_71364 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71078_71362__$1,new cljs.core.Keyword(null,"fn-str","fn-str",-1348506402));
var fn_sym_71365 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71078_71362__$1,new cljs.core.Keyword(null,"fn-sym","fn-sym",1423988510));
var fn_obj_71366 = goog.getObjectByName(fn_str_71364,$CLJS);
shadow.cljs.devtools.client.browser.devtools_msg(["call ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(fn_sym_71365)].join(''));

(fn_obj_71366.cljs$core$IFn$_invoke$arity$2 ? fn_obj_71366.cljs$core$IFn$_invoke$arity$2(path,new_link_71356) : fn_obj_71366.call(null,path,new_link_71356));


var G__71367 = seq__71070_71357;
var G__71368 = chunk__71072_71358;
var G__71369 = count__71073_71359;
var G__71370 = (i__71074_71360 + (1));
seq__71070_71357 = G__71367;
chunk__71072_71358 = G__71368;
count__71073_71359 = G__71369;
i__71074_71360 = G__71370;
continue;
} else {
var temp__5753__auto___71371__$2 = cljs.core.seq(seq__71070_71357);
if(temp__5753__auto___71371__$2){
var seq__71070_71372__$1 = temp__5753__auto___71371__$2;
if(cljs.core.chunked_seq_QMARK_(seq__71070_71372__$1)){
var c__5565__auto___71373 = cljs.core.chunk_first(seq__71070_71372__$1);
var G__71374 = cljs.core.chunk_rest(seq__71070_71372__$1);
var G__71375 = c__5565__auto___71373;
var G__71376 = cljs.core.count(c__5565__auto___71373);
var G__71377 = (0);
seq__71070_71357 = G__71374;
chunk__71072_71358 = G__71375;
count__71073_71359 = G__71376;
i__71074_71360 = G__71377;
continue;
} else {
var map__71079_71378 = cljs.core.first(seq__71070_71372__$1);
var map__71079_71379__$1 = cljs.core.__destructure_map(map__71079_71378);
var task_71380 = map__71079_71379__$1;
var fn_str_71381 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71079_71379__$1,new cljs.core.Keyword(null,"fn-str","fn-str",-1348506402));
var fn_sym_71382 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71079_71379__$1,new cljs.core.Keyword(null,"fn-sym","fn-sym",1423988510));
var fn_obj_71383 = goog.getObjectByName(fn_str_71381,$CLJS);
shadow.cljs.devtools.client.browser.devtools_msg(["call ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(fn_sym_71382)].join(''));

(fn_obj_71383.cljs$core$IFn$_invoke$arity$2 ? fn_obj_71383.cljs$core$IFn$_invoke$arity$2(path,new_link_71356) : fn_obj_71383.call(null,path,new_link_71356));


var G__71384 = cljs.core.next(seq__71070_71372__$1);
var G__71385 = null;
var G__71386 = (0);
var G__71387 = (0);
seq__71070_71357 = G__71384;
chunk__71072_71358 = G__71385;
count__71073_71359 = G__71386;
i__71074_71360 = G__71387;
continue;
}
} else {
}
}
break;
}

return goog.dom.removeNode(node_71354);
});})(seq__71015_71297,chunk__71019_71298,count__71020_71299,i__71021_71300,seq__70660,chunk__70662,count__70663,i__70664,new_link_71356,path_match_71355,node_71354,seq__71015_71348__$1,temp__5753__auto___71347__$1,path,seq__70660__$1,temp__5753__auto__,map__70659,map__70659__$1,msg,updates,reload_info))
);

shadow.cljs.devtools.client.browser.devtools_msg.cljs$core$IFn$_invoke$arity$variadic("load CSS",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([path_match_71355], 0));

goog.dom.insertSiblingAfter(new_link_71356,node_71354);


var G__71388 = cljs.core.next(seq__71015_71348__$1);
var G__71389 = null;
var G__71390 = (0);
var G__71391 = (0);
seq__71015_71297 = G__71388;
chunk__71019_71298 = G__71389;
count__71020_71299 = G__71390;
i__71021_71300 = G__71391;
continue;
} else {
var G__71392 = cljs.core.next(seq__71015_71348__$1);
var G__71393 = null;
var G__71394 = (0);
var G__71395 = (0);
seq__71015_71297 = G__71392;
chunk__71019_71298 = G__71393;
count__71020_71299 = G__71394;
i__71021_71300 = G__71395;
continue;
}
} else {
var G__71396 = cljs.core.next(seq__71015_71348__$1);
var G__71397 = null;
var G__71398 = (0);
var G__71399 = (0);
seq__71015_71297 = G__71396;
chunk__71019_71298 = G__71397;
count__71020_71299 = G__71398;
i__71021_71300 = G__71399;
continue;
}
}
} else {
}
}
break;
}


var G__71400 = cljs.core.next(seq__70660__$1);
var G__71401 = null;
var G__71402 = (0);
var G__71403 = (0);
seq__70660 = G__71400;
chunk__70662 = G__71401;
count__70663 = G__71402;
i__70664 = G__71403;
continue;
} else {
var G__71404 = cljs.core.next(seq__70660__$1);
var G__71405 = null;
var G__71406 = (0);
var G__71407 = (0);
seq__70660 = G__71404;
chunk__70662 = G__71405;
count__70663 = G__71406;
i__70664 = G__71407;
continue;
}
}
} else {
return null;
}
}
break;
}
});
shadow.cljs.devtools.client.browser.global_eval = (function shadow$cljs$devtools$client$browser$global_eval(js){
if(cljs.core.not_EQ_.cljs$core$IFn$_invoke$arity$2("undefined",typeof(module))){
return eval(js);
} else {
return (0,eval)(js);;
}
});
shadow.cljs.devtools.client.browser.repl_init = (function shadow$cljs$devtools$client$browser$repl_init(runtime,p__71082){
var map__71083 = p__71082;
var map__71083__$1 = cljs.core.__destructure_map(map__71083);
var repl_state = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71083__$1,new cljs.core.Keyword(null,"repl-state","repl-state",-1733780387));
return shadow.cljs.devtools.client.shared.load_sources(runtime,cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentVector.EMPTY,cljs.core.remove.cljs$core$IFn$_invoke$arity$2(shadow.cljs.devtools.client.env.src_is_loaded_QMARK_,new cljs.core.Keyword(null,"repl-sources","repl-sources",723867535).cljs$core$IFn$_invoke$arity$1(repl_state))),(function (sources){
shadow.cljs.devtools.client.browser.do_js_load(sources);

return shadow.cljs.devtools.client.browser.devtools_msg("ready!");
}));
});
shadow.cljs.devtools.client.browser.runtime_info = (((typeof SHADOW_CONFIG !== 'undefined'))?shadow.json.to_clj.cljs$core$IFn$_invoke$arity$1(SHADOW_CONFIG):null);
shadow.cljs.devtools.client.browser.client_info = cljs.core.merge.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([shadow.cljs.devtools.client.browser.runtime_info,new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"host","host",-1558485167),(cljs.core.truth_(goog.global.document)?new cljs.core.Keyword(null,"browser","browser",828191719):new cljs.core.Keyword(null,"browser-worker","browser-worker",1638998282)),new cljs.core.Keyword(null,"user-agent","user-agent",1220426212),[(cljs.core.truth_(goog.userAgent.OPERA)?"Opera":(cljs.core.truth_(goog.userAgent.product.CHROME)?"Chrome":(cljs.core.truth_(goog.userAgent.IE)?"MSIE":(cljs.core.truth_(goog.userAgent.EDGE)?"Edge":(cljs.core.truth_(goog.userAgent.GECKO)?"Firefox":(cljs.core.truth_(goog.userAgent.SAFARI)?"Safari":(cljs.core.truth_(goog.userAgent.WEBKIT)?"Webkit":null)))))))," ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(goog.userAgent.VERSION)," [",cljs.core.str.cljs$core$IFn$_invoke$arity$1(goog.userAgent.PLATFORM),"]"].join(''),new cljs.core.Keyword(null,"dom","dom",-1236537922),(!((goog.global.document == null)))], null)], 0));
if((typeof shadow !== 'undefined') && (typeof shadow.cljs !== 'undefined') && (typeof shadow.cljs.devtools !== 'undefined') && (typeof shadow.cljs.devtools.client !== 'undefined') && (typeof shadow.cljs.devtools.client.browser !== 'undefined') && (typeof shadow.cljs.devtools.client.browser.ws_was_welcome_ref !== 'undefined')){
} else {
shadow.cljs.devtools.client.browser.ws_was_welcome_ref = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(false);
}
if(((shadow.cljs.devtools.client.env.enabled) && ((shadow.cljs.devtools.client.env.worker_client_id > (0))))){
(shadow.cljs.devtools.client.shared.Runtime.prototype.shadow$remote$runtime$api$IEvalJS$ = cljs.core.PROTOCOL_SENTINEL);

(shadow.cljs.devtools.client.shared.Runtime.prototype.shadow$remote$runtime$api$IEvalJS$_js_eval$arity$2 = (function (this$,code){
var this$__$1 = this;
return shadow.cljs.devtools.client.browser.global_eval(code);
}));

(shadow.cljs.devtools.client.shared.Runtime.prototype.shadow$cljs$devtools$client$shared$IHostSpecific$ = cljs.core.PROTOCOL_SENTINEL);

(shadow.cljs.devtools.client.shared.Runtime.prototype.shadow$cljs$devtools$client$shared$IHostSpecific$do_invoke$arity$2 = (function (this$,p__71084){
var map__71085 = p__71084;
var map__71085__$1 = cljs.core.__destructure_map(map__71085);
var _ = map__71085__$1;
var js = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71085__$1,new cljs.core.Keyword(null,"js","js",1768080579));
var this$__$1 = this;
return shadow.cljs.devtools.client.browser.global_eval(js);
}));

(shadow.cljs.devtools.client.shared.Runtime.prototype.shadow$cljs$devtools$client$shared$IHostSpecific$do_repl_init$arity$4 = (function (runtime,p__71086,done,error){
var map__71088 = p__71086;
var map__71088__$1 = cljs.core.__destructure_map(map__71088);
var repl_sources = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71088__$1,new cljs.core.Keyword(null,"repl-sources","repl-sources",723867535));
var runtime__$1 = this;
return shadow.cljs.devtools.client.shared.load_sources(runtime__$1,cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentVector.EMPTY,cljs.core.remove.cljs$core$IFn$_invoke$arity$2(shadow.cljs.devtools.client.env.src_is_loaded_QMARK_,repl_sources)),(function (sources){
shadow.cljs.devtools.client.browser.do_js_load(sources);

return (done.cljs$core$IFn$_invoke$arity$0 ? done.cljs$core$IFn$_invoke$arity$0() : done.call(null));
}));
}));

(shadow.cljs.devtools.client.shared.Runtime.prototype.shadow$cljs$devtools$client$shared$IHostSpecific$do_repl_require$arity$4 = (function (runtime,p__71089,done,error){
var map__71091 = p__71089;
var map__71091__$1 = cljs.core.__destructure_map(map__71091);
var msg = map__71091__$1;
var sources = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71091__$1,new cljs.core.Keyword(null,"sources","sources",-321166424));
var reload_namespaces = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71091__$1,new cljs.core.Keyword(null,"reload-namespaces","reload-namespaces",250210134));
var js_requires = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71091__$1,new cljs.core.Keyword(null,"js-requires","js-requires",-1311472051));
var runtime__$1 = this;
var sources_to_load = cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentVector.EMPTY,cljs.core.remove.cljs$core$IFn$_invoke$arity$2((function (p__71094){
var map__71095 = p__71094;
var map__71095__$1 = cljs.core.__destructure_map(map__71095);
var src = map__71095__$1;
var provides = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71095__$1,new cljs.core.Keyword(null,"provides","provides",-1634397992));
var and__5041__auto__ = shadow.cljs.devtools.client.env.src_is_loaded_QMARK_(src);
if(cljs.core.truth_(and__5041__auto__)){
return cljs.core.not(cljs.core.some(reload_namespaces,provides));
} else {
return and__5041__auto__;
}
}),sources));
if(cljs.core.not(cljs.core.seq(sources_to_load))){
var G__71099 = cljs.core.PersistentVector.EMPTY;
return (done.cljs$core$IFn$_invoke$arity$1 ? done.cljs$core$IFn$_invoke$arity$1(G__71099) : done.call(null,G__71099));
} else {
return shadow.remote.runtime.shared.call.cljs$core$IFn$_invoke$arity$3(runtime__$1,new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"cljs-load-sources","cljs-load-sources",-1458295962),new cljs.core.Keyword(null,"to","to",192099007),shadow.cljs.devtools.client.env.worker_client_id,new cljs.core.Keyword(null,"sources","sources",-321166424),cljs.core.into.cljs$core$IFn$_invoke$arity$3(cljs.core.PersistentVector.EMPTY,cljs.core.map.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"resource-id","resource-id",-1308422582)),sources_to_load)], null),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"cljs-sources","cljs-sources",31121610),(function (p__71101){
var map__71102 = p__71101;
var map__71102__$1 = cljs.core.__destructure_map(map__71102);
var msg__$1 = map__71102__$1;
var sources__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71102__$1,new cljs.core.Keyword(null,"sources","sources",-321166424));
try{shadow.cljs.devtools.client.browser.do_js_load(sources__$1);

if(cljs.core.seq(js_requires)){
shadow.cljs.devtools.client.browser.do_js_requires(js_requires);
} else {
}

return (done.cljs$core$IFn$_invoke$arity$1 ? done.cljs$core$IFn$_invoke$arity$1(sources_to_load) : done.call(null,sources_to_load));
}catch (e71103){var ex = e71103;
return (error.cljs$core$IFn$_invoke$arity$1 ? error.cljs$core$IFn$_invoke$arity$1(ex) : error.call(null,ex));
}})], null));
}
}));

shadow.cljs.devtools.client.shared.add_plugin_BANG_(new cljs.core.Keyword("shadow.cljs.devtools.client.browser","client","shadow.cljs.devtools.client.browser/client",-1461019282),cljs.core.PersistentHashSet.EMPTY,(function (p__71104){
var map__71105 = p__71104;
var map__71105__$1 = cljs.core.__destructure_map(map__71105);
var env = map__71105__$1;
var runtime = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71105__$1,new cljs.core.Keyword(null,"runtime","runtime",-1331573996));
var svc = new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"runtime","runtime",-1331573996),runtime], null);
shadow.remote.runtime.api.add_extension(runtime,new cljs.core.Keyword("shadow.cljs.devtools.client.browser","client","shadow.cljs.devtools.client.browser/client",-1461019282),new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"on-welcome","on-welcome",1895317125),(function (){
cljs.core.reset_BANG_(shadow.cljs.devtools.client.browser.ws_was_welcome_ref,true);

shadow.cljs.devtools.client.hud.connection_error_clear_BANG_();

shadow.cljs.devtools.client.env.patch_goog_BANG_();

return shadow.cljs.devtools.client.browser.devtools_msg(["#",cljs.core.str.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"client-id","client-id",-464622140).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(new cljs.core.Keyword(null,"state-ref","state-ref",2127874952).cljs$core$IFn$_invoke$arity$1(runtime))))," ready!"].join(''));
}),new cljs.core.Keyword(null,"on-disconnect","on-disconnect",-809021814),(function (e){
if(cljs.core.truth_(cljs.core.deref(shadow.cljs.devtools.client.browser.ws_was_welcome_ref))){
shadow.cljs.devtools.client.hud.connection_error("The Websocket connection was closed!");

return cljs.core.reset_BANG_(shadow.cljs.devtools.client.browser.ws_was_welcome_ref,false);
} else {
return null;
}
}),new cljs.core.Keyword(null,"on-reconnect","on-reconnect",1239988702),(function (e){
return shadow.cljs.devtools.client.hud.connection_error("Reconnecting ...");
}),new cljs.core.Keyword(null,"ops","ops",1237330063),new cljs.core.PersistentArrayMap(null, 8, [new cljs.core.Keyword(null,"access-denied","access-denied",959449406),(function (msg){
cljs.core.reset_BANG_(shadow.cljs.devtools.client.browser.ws_was_welcome_ref,false);

return shadow.cljs.devtools.client.hud.connection_error(["Stale Output! Your loaded JS was not produced by the running shadow-cljs instance."," Is the watch for this build running?"].join(''));
}),new cljs.core.Keyword(null,"cljs-runtime-init","cljs-runtime-init",1305890232),(function (msg){
return shadow.cljs.devtools.client.browser.repl_init(runtime,msg);
}),new cljs.core.Keyword(null,"cljs-asset-update","cljs-asset-update",1224093028),(function (msg){
return shadow.cljs.devtools.client.browser.handle_asset_update(msg);
}),new cljs.core.Keyword(null,"cljs-build-configure","cljs-build-configure",-2089891268),(function (msg){
return null;
}),new cljs.core.Keyword(null,"cljs-build-start","cljs-build-start",-725781241),(function (msg){
shadow.cljs.devtools.client.hud.hud_hide();

shadow.cljs.devtools.client.hud.load_start();

return shadow.cljs.devtools.client.env.run_custom_notify_BANG_(cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(msg,new cljs.core.Keyword(null,"type","type",1174270348),new cljs.core.Keyword(null,"build-start","build-start",-959649480)));
}),new cljs.core.Keyword(null,"cljs-build-complete","cljs-build-complete",273626153),(function (msg){
var msg__$1 = shadow.cljs.devtools.client.env.add_warnings_to_info(msg);
shadow.cljs.devtools.client.hud.connection_error_clear_BANG_();

shadow.cljs.devtools.client.hud.hud_warnings(msg__$1);

shadow.cljs.devtools.client.browser.handle_build_complete(runtime,msg__$1);

return shadow.cljs.devtools.client.env.run_custom_notify_BANG_(cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(msg__$1,new cljs.core.Keyword(null,"type","type",1174270348),new cljs.core.Keyword(null,"build-complete","build-complete",-501868472)));
}),new cljs.core.Keyword(null,"cljs-build-failure","cljs-build-failure",1718154990),(function (msg){
shadow.cljs.devtools.client.hud.load_end();

shadow.cljs.devtools.client.hud.hud_error(msg);

return shadow.cljs.devtools.client.env.run_custom_notify_BANG_(cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(msg,new cljs.core.Keyword(null,"type","type",1174270348),new cljs.core.Keyword(null,"build-failure","build-failure",-2107487466)));
}),new cljs.core.Keyword("shadow.cljs.devtools.client.env","worker-notify","shadow.cljs.devtools.client.env/worker-notify",-1456820670),(function (p__71106){
var map__71107 = p__71106;
var map__71107__$1 = cljs.core.__destructure_map(map__71107);
var event_op = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71107__$1,new cljs.core.Keyword(null,"event-op","event-op",200358057));
var client_id = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71107__$1,new cljs.core.Keyword(null,"client-id","client-id",-464622140));
if(((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"client-disconnect","client-disconnect",640227957),event_op)) && (cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(client_id,shadow.cljs.devtools.client.env.worker_client_id)))){
shadow.cljs.devtools.client.hud.connection_error_clear_BANG_();

return shadow.cljs.devtools.client.hud.connection_error("The watch for this build was stopped!");
} else {
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"client-connect","client-connect",-1113973888),event_op)){
shadow.cljs.devtools.client.hud.connection_error_clear_BANG_();

return shadow.cljs.devtools.client.hud.connection_error("The watch for this build was restarted. Reload required!");
} else {
return null;
}
}
})], null)], null));

return svc;
}),(function (p__71108){
var map__71109 = p__71108;
var map__71109__$1 = cljs.core.__destructure_map(map__71109);
var svc = map__71109__$1;
var runtime = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__71109__$1,new cljs.core.Keyword(null,"runtime","runtime",-1331573996));
return shadow.remote.runtime.api.del_extension(runtime,new cljs.core.Keyword("shadow.cljs.devtools.client.browser","client","shadow.cljs.devtools.client.browser/client",-1461019282));
}));

shadow.cljs.devtools.client.shared.init_runtime_BANG_(shadow.cljs.devtools.client.browser.client_info,shadow.cljs.devtools.client.websocket.start,shadow.cljs.devtools.client.websocket.send,shadow.cljs.devtools.client.websocket.stop);
} else {
}

//# sourceMappingURL=shadow.cljs.devtools.client.browser.js.map
