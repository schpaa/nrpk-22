goog.provide('shadow.cljs.devtools.client.browser');
shadow.cljs.devtools.client.browser.devtools_msg = (function shadow$cljs$devtools$client$browser$devtools_msg(var_args){
var args__4870__auto__ = [];
var len__4864__auto___51529 = arguments.length;
var i__4865__auto___51530 = (0);
while(true){
if((i__4865__auto___51530 < len__4864__auto___51529)){
args__4870__auto__.push((arguments[i__4865__auto___51530]));

var G__51531 = (i__4865__auto___51530 + (1));
i__4865__auto___51530 = G__51531;
continue;
} else {
}
break;
}

var argseq__4871__auto__ = ((((1) < args__4870__auto__.length))?(new cljs.core.IndexedSeq(args__4870__auto__.slice((1)),(0),null)):null);
return shadow.cljs.devtools.client.browser.devtools_msg.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__4871__auto__);
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
(shadow.cljs.devtools.client.browser.devtools_msg.cljs$lang$applyTo = (function (seq51400){
var G__51401 = cljs.core.first(seq51400);
var seq51400__$1 = cljs.core.next(seq51400);
var self__4851__auto__ = this;
return self__4851__auto__.cljs$core$IFn$_invoke$arity$variadic(G__51401,seq51400__$1);
}));

shadow.cljs.devtools.client.browser.script_eval = (function shadow$cljs$devtools$client$browser$script_eval(code){
return goog.globalEval(code);
});
shadow.cljs.devtools.client.browser.do_js_load = (function shadow$cljs$devtools$client$browser$do_js_load(sources){
var seq__51404 = cljs.core.seq(sources);
var chunk__51405 = null;
var count__51406 = (0);
var i__51407 = (0);
while(true){
if((i__51407 < count__51406)){
var map__51415 = chunk__51405.cljs$core$IIndexed$_nth$arity$2(null,i__51407);
var map__51415__$1 = cljs.core.__destructure_map(map__51415);
var src = map__51415__$1;
var resource_id = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51415__$1,new cljs.core.Keyword(null,"resource-id","resource-id",-1308422582));
var output_name = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51415__$1,new cljs.core.Keyword(null,"output-name","output-name",-1769107767));
var resource_name = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51415__$1,new cljs.core.Keyword(null,"resource-name","resource-name",2001617100));
var js = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51415__$1,new cljs.core.Keyword(null,"js","js",1768080579));
$CLJS.SHADOW_ENV.setLoaded(output_name);

shadow.cljs.devtools.client.browser.devtools_msg.cljs$core$IFn$_invoke$arity$variadic("load JS",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([resource_name], 0));

shadow.cljs.devtools.client.env.before_load_src(src);

try{shadow.cljs.devtools.client.browser.script_eval([cljs.core.str.cljs$core$IFn$_invoke$arity$1(js),"\n//# sourceURL=",cljs.core.str.cljs$core$IFn$_invoke$arity$1($CLJS.SHADOW_ENV.scriptBase),cljs.core.str.cljs$core$IFn$_invoke$arity$1(output_name)].join(''));
}catch (e51416){var e_51532 = e51416;
if(shadow.cljs.devtools.client.env.log){
console.error(["Failed to load ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(resource_name)].join(''),e_51532);
} else {
}

throw (new Error(["Failed to load ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(resource_name),": ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(e_51532.message)].join('')));
}

var G__51533 = seq__51404;
var G__51534 = chunk__51405;
var G__51535 = count__51406;
var G__51536 = (i__51407 + (1));
seq__51404 = G__51533;
chunk__51405 = G__51534;
count__51406 = G__51535;
i__51407 = G__51536;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__51404);
if(temp__5753__auto__){
var seq__51404__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__51404__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__51404__$1);
var G__51537 = cljs.core.chunk_rest(seq__51404__$1);
var G__51538 = c__4679__auto__;
var G__51539 = cljs.core.count(c__4679__auto__);
var G__51540 = (0);
seq__51404 = G__51537;
chunk__51405 = G__51538;
count__51406 = G__51539;
i__51407 = G__51540;
continue;
} else {
var map__51417 = cljs.core.first(seq__51404__$1);
var map__51417__$1 = cljs.core.__destructure_map(map__51417);
var src = map__51417__$1;
var resource_id = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51417__$1,new cljs.core.Keyword(null,"resource-id","resource-id",-1308422582));
var output_name = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51417__$1,new cljs.core.Keyword(null,"output-name","output-name",-1769107767));
var resource_name = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51417__$1,new cljs.core.Keyword(null,"resource-name","resource-name",2001617100));
var js = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51417__$1,new cljs.core.Keyword(null,"js","js",1768080579));
$CLJS.SHADOW_ENV.setLoaded(output_name);

shadow.cljs.devtools.client.browser.devtools_msg.cljs$core$IFn$_invoke$arity$variadic("load JS",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([resource_name], 0));

shadow.cljs.devtools.client.env.before_load_src(src);

try{shadow.cljs.devtools.client.browser.script_eval([cljs.core.str.cljs$core$IFn$_invoke$arity$1(js),"\n//# sourceURL=",cljs.core.str.cljs$core$IFn$_invoke$arity$1($CLJS.SHADOW_ENV.scriptBase),cljs.core.str.cljs$core$IFn$_invoke$arity$1(output_name)].join(''));
}catch (e51418){var e_51541 = e51418;
if(shadow.cljs.devtools.client.env.log){
console.error(["Failed to load ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(resource_name)].join(''),e_51541);
} else {
}

throw (new Error(["Failed to load ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(resource_name),": ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(e_51541.message)].join('')));
}

var G__51542 = cljs.core.next(seq__51404__$1);
var G__51543 = null;
var G__51544 = (0);
var G__51545 = (0);
seq__51404 = G__51542;
chunk__51405 = G__51543;
count__51406 = G__51544;
i__51407 = G__51545;
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
var seq__51419 = cljs.core.seq(js_requires);
var chunk__51420 = null;
var count__51421 = (0);
var i__51422 = (0);
while(true){
if((i__51422 < count__51421)){
var js_ns = chunk__51420.cljs$core$IIndexed$_nth$arity$2(null,i__51422);
var require_str_51546 = ["var ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(js_ns)," = shadow.js.require(\"",cljs.core.str.cljs$core$IFn$_invoke$arity$1(js_ns),"\");"].join('');
shadow.cljs.devtools.client.browser.script_eval(require_str_51546);


var G__51547 = seq__51419;
var G__51548 = chunk__51420;
var G__51549 = count__51421;
var G__51550 = (i__51422 + (1));
seq__51419 = G__51547;
chunk__51420 = G__51548;
count__51421 = G__51549;
i__51422 = G__51550;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__51419);
if(temp__5753__auto__){
var seq__51419__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__51419__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__51419__$1);
var G__51551 = cljs.core.chunk_rest(seq__51419__$1);
var G__51552 = c__4679__auto__;
var G__51553 = cljs.core.count(c__4679__auto__);
var G__51554 = (0);
seq__51419 = G__51551;
chunk__51420 = G__51552;
count__51421 = G__51553;
i__51422 = G__51554;
continue;
} else {
var js_ns = cljs.core.first(seq__51419__$1);
var require_str_51555 = ["var ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(js_ns)," = shadow.js.require(\"",cljs.core.str.cljs$core$IFn$_invoke$arity$1(js_ns),"\");"].join('');
shadow.cljs.devtools.client.browser.script_eval(require_str_51555);


var G__51556 = cljs.core.next(seq__51419__$1);
var G__51557 = null;
var G__51558 = (0);
var G__51559 = (0);
seq__51419 = G__51556;
chunk__51420 = G__51557;
count__51421 = G__51558;
i__51422 = G__51559;
continue;
}
} else {
return null;
}
}
break;
}
});
shadow.cljs.devtools.client.browser.handle_build_complete = (function shadow$cljs$devtools$client$browser$handle_build_complete(runtime,p__51426){
var map__51427 = p__51426;
var map__51427__$1 = cljs.core.__destructure_map(map__51427);
var msg = map__51427__$1;
var info = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51427__$1,new cljs.core.Keyword(null,"info","info",-317069002));
var reload_info = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51427__$1,new cljs.core.Keyword(null,"reload-info","reload-info",1648088086));
var warnings = cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentVector.EMPTY,cljs.core.distinct.cljs$core$IFn$_invoke$arity$1((function (){var iter__4652__auto__ = (function shadow$cljs$devtools$client$browser$handle_build_complete_$_iter__51428(s__51429){
return (new cljs.core.LazySeq(null,(function (){
var s__51429__$1 = s__51429;
while(true){
var temp__5753__auto__ = cljs.core.seq(s__51429__$1);
if(temp__5753__auto__){
var xs__6308__auto__ = temp__5753__auto__;
var map__51434 = cljs.core.first(xs__6308__auto__);
var map__51434__$1 = cljs.core.__destructure_map(map__51434);
var src = map__51434__$1;
var resource_name = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51434__$1,new cljs.core.Keyword(null,"resource-name","resource-name",2001617100));
var warnings = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51434__$1,new cljs.core.Keyword(null,"warnings","warnings",-735437651));
if(cljs.core.not(new cljs.core.Keyword(null,"from-jar","from-jar",1050932827).cljs$core$IFn$_invoke$arity$1(src))){
var iterys__4648__auto__ = ((function (s__51429__$1,map__51434,map__51434__$1,src,resource_name,warnings,xs__6308__auto__,temp__5753__auto__,map__51427,map__51427__$1,msg,info,reload_info){
return (function shadow$cljs$devtools$client$browser$handle_build_complete_$_iter__51428_$_iter__51430(s__51431){
return (new cljs.core.LazySeq(null,((function (s__51429__$1,map__51434,map__51434__$1,src,resource_name,warnings,xs__6308__auto__,temp__5753__auto__,map__51427,map__51427__$1,msg,info,reload_info){
return (function (){
var s__51431__$1 = s__51431;
while(true){
var temp__5753__auto____$1 = cljs.core.seq(s__51431__$1);
if(temp__5753__auto____$1){
var s__51431__$2 = temp__5753__auto____$1;
if(cljs.core.chunked_seq_QMARK_(s__51431__$2)){
var c__4650__auto__ = cljs.core.chunk_first(s__51431__$2);
var size__4651__auto__ = cljs.core.count(c__4650__auto__);
var b__51433 = cljs.core.chunk_buffer(size__4651__auto__);
if((function (){var i__51432 = (0);
while(true){
if((i__51432 < size__4651__auto__)){
var warning = cljs.core._nth(c__4650__auto__,i__51432);
cljs.core.chunk_append(b__51433,cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(warning,new cljs.core.Keyword(null,"resource-name","resource-name",2001617100),resource_name));

var G__51560 = (i__51432 + (1));
i__51432 = G__51560;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons(cljs.core.chunk(b__51433),shadow$cljs$devtools$client$browser$handle_build_complete_$_iter__51428_$_iter__51430(cljs.core.chunk_rest(s__51431__$2)));
} else {
return cljs.core.chunk_cons(cljs.core.chunk(b__51433),null);
}
} else {
var warning = cljs.core.first(s__51431__$2);
return cljs.core.cons(cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(warning,new cljs.core.Keyword(null,"resource-name","resource-name",2001617100),resource_name),shadow$cljs$devtools$client$browser$handle_build_complete_$_iter__51428_$_iter__51430(cljs.core.rest(s__51431__$2)));
}
} else {
return null;
}
break;
}
});})(s__51429__$1,map__51434,map__51434__$1,src,resource_name,warnings,xs__6308__auto__,temp__5753__auto__,map__51427,map__51427__$1,msg,info,reload_info))
,null,null));
});})(s__51429__$1,map__51434,map__51434__$1,src,resource_name,warnings,xs__6308__auto__,temp__5753__auto__,map__51427,map__51427__$1,msg,info,reload_info))
;
var fs__4649__auto__ = cljs.core.seq(iterys__4648__auto__(warnings));
if(fs__4649__auto__){
return cljs.core.concat.cljs$core$IFn$_invoke$arity$2(fs__4649__auto__,shadow$cljs$devtools$client$browser$handle_build_complete_$_iter__51428(cljs.core.rest(s__51429__$1)));
} else {
var G__51561 = cljs.core.rest(s__51429__$1);
s__51429__$1 = G__51561;
continue;
}
} else {
var G__51562 = cljs.core.rest(s__51429__$1);
s__51429__$1 = G__51562;
continue;
}
} else {
return null;
}
break;
}
}),null,null));
});
return iter__4652__auto__(new cljs.core.Keyword(null,"sources","sources",-321166424).cljs$core$IFn$_invoke$arity$1(info));
})()));
if(shadow.cljs.devtools.client.env.log){
var seq__51435_51563 = cljs.core.seq(warnings);
var chunk__51436_51564 = null;
var count__51437_51565 = (0);
var i__51438_51566 = (0);
while(true){
if((i__51438_51566 < count__51437_51565)){
var map__51441_51567 = chunk__51436_51564.cljs$core$IIndexed$_nth$arity$2(null,i__51438_51566);
var map__51441_51568__$1 = cljs.core.__destructure_map(map__51441_51567);
var w_51569 = map__51441_51568__$1;
var msg_51570__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51441_51568__$1,new cljs.core.Keyword(null,"msg","msg",-1386103444));
var line_51571 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51441_51568__$1,new cljs.core.Keyword(null,"line","line",212345235));
var column_51572 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51441_51568__$1,new cljs.core.Keyword(null,"column","column",2078222095));
var resource_name_51573 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51441_51568__$1,new cljs.core.Keyword(null,"resource-name","resource-name",2001617100));
console.warn(["BUILD-WARNING in ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(resource_name_51573)," at [",cljs.core.str.cljs$core$IFn$_invoke$arity$1(line_51571),":",cljs.core.str.cljs$core$IFn$_invoke$arity$1(column_51572),"]\n\t",cljs.core.str.cljs$core$IFn$_invoke$arity$1(msg_51570__$1)].join(''));


var G__51574 = seq__51435_51563;
var G__51575 = chunk__51436_51564;
var G__51576 = count__51437_51565;
var G__51577 = (i__51438_51566 + (1));
seq__51435_51563 = G__51574;
chunk__51436_51564 = G__51575;
count__51437_51565 = G__51576;
i__51438_51566 = G__51577;
continue;
} else {
var temp__5753__auto___51578 = cljs.core.seq(seq__51435_51563);
if(temp__5753__auto___51578){
var seq__51435_51579__$1 = temp__5753__auto___51578;
if(cljs.core.chunked_seq_QMARK_(seq__51435_51579__$1)){
var c__4679__auto___51580 = cljs.core.chunk_first(seq__51435_51579__$1);
var G__51581 = cljs.core.chunk_rest(seq__51435_51579__$1);
var G__51582 = c__4679__auto___51580;
var G__51583 = cljs.core.count(c__4679__auto___51580);
var G__51584 = (0);
seq__51435_51563 = G__51581;
chunk__51436_51564 = G__51582;
count__51437_51565 = G__51583;
i__51438_51566 = G__51584;
continue;
} else {
var map__51442_51585 = cljs.core.first(seq__51435_51579__$1);
var map__51442_51586__$1 = cljs.core.__destructure_map(map__51442_51585);
var w_51587 = map__51442_51586__$1;
var msg_51588__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51442_51586__$1,new cljs.core.Keyword(null,"msg","msg",-1386103444));
var line_51589 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51442_51586__$1,new cljs.core.Keyword(null,"line","line",212345235));
var column_51590 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51442_51586__$1,new cljs.core.Keyword(null,"column","column",2078222095));
var resource_name_51591 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51442_51586__$1,new cljs.core.Keyword(null,"resource-name","resource-name",2001617100));
console.warn(["BUILD-WARNING in ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(resource_name_51591)," at [",cljs.core.str.cljs$core$IFn$_invoke$arity$1(line_51589),":",cljs.core.str.cljs$core$IFn$_invoke$arity$1(column_51590),"]\n\t",cljs.core.str.cljs$core$IFn$_invoke$arity$1(msg_51588__$1)].join(''));


var G__51592 = cljs.core.next(seq__51435_51579__$1);
var G__51593 = null;
var G__51594 = (0);
var G__51595 = (0);
seq__51435_51563 = G__51592;
chunk__51436_51564 = G__51593;
count__51437_51565 = G__51594;
i__51438_51566 = G__51595;
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

return shadow.cljs.devtools.client.shared.load_sources(runtime,sources_to_get,(function (p1__51425_SHARP_){
return shadow.cljs.devtools.client.browser.do_js_reload(msg,p1__51425_SHARP_,shadow.cljs.devtools.client.hud.load_end_success,shadow.cljs.devtools.client.hud.load_failure);
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
var and__4251__auto__ = ((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$1(shadow.cljs.devtools.client.browser.page_load_uri.hasSameDomainAs(node_uri))) || (cljs.core.not(node_uri.hasDomain())));
if(and__4251__auto__){
var and__4251__auto____$1 = cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(node_abs,new$);
if(and__4251__auto____$1){
return new$;
} else {
return and__4251__auto____$1;
}
} else {
return and__4251__auto__;
}
}
});
shadow.cljs.devtools.client.browser.handle_asset_update = (function shadow$cljs$devtools$client$browser$handle_asset_update(p__51443){
var map__51444 = p__51443;
var map__51444__$1 = cljs.core.__destructure_map(map__51444);
var msg = map__51444__$1;
var updates = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51444__$1,new cljs.core.Keyword(null,"updates","updates",2013983452));
var seq__51445 = cljs.core.seq(updates);
var chunk__51447 = null;
var count__51448 = (0);
var i__51449 = (0);
while(true){
if((i__51449 < count__51448)){
var path = chunk__51447.cljs$core$IIndexed$_nth$arity$2(null,i__51449);
if(clojure.string.ends_with_QMARK_(path,"css")){
var seq__51479_51596 = cljs.core.seq(cljs.core.array_seq.cljs$core$IFn$_invoke$arity$1(document.querySelectorAll("link[rel=\"stylesheet\"]")));
var chunk__51483_51597 = null;
var count__51484_51598 = (0);
var i__51485_51599 = (0);
while(true){
if((i__51485_51599 < count__51484_51598)){
var node_51600 = chunk__51483_51597.cljs$core$IIndexed$_nth$arity$2(null,i__51485_51599);
if(cljs.core.not(node_51600.shadow$old)){
var path_match_51601 = shadow.cljs.devtools.client.browser.match_paths(node_51600.getAttribute("href"),path);
if(cljs.core.truth_(path_match_51601)){
var new_link_51602 = (function (){var G__51491 = node_51600.cloneNode(true);
G__51491.setAttribute("href",[cljs.core.str.cljs$core$IFn$_invoke$arity$1(path_match_51601),"?r=",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.rand.cljs$core$IFn$_invoke$arity$0())].join(''));

return G__51491;
})();
(node_51600.shadow$old = true);

(new_link_51602.onload = ((function (seq__51479_51596,chunk__51483_51597,count__51484_51598,i__51485_51599,seq__51445,chunk__51447,count__51448,i__51449,new_link_51602,path_match_51601,node_51600,path,map__51444,map__51444__$1,msg,updates){
return (function (e){
return goog.dom.removeNode(node_51600);
});})(seq__51479_51596,chunk__51483_51597,count__51484_51598,i__51485_51599,seq__51445,chunk__51447,count__51448,i__51449,new_link_51602,path_match_51601,node_51600,path,map__51444,map__51444__$1,msg,updates))
);

shadow.cljs.devtools.client.browser.devtools_msg.cljs$core$IFn$_invoke$arity$variadic("load CSS",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([path_match_51601], 0));

goog.dom.insertSiblingAfter(new_link_51602,node_51600);


var G__51603 = seq__51479_51596;
var G__51604 = chunk__51483_51597;
var G__51605 = count__51484_51598;
var G__51606 = (i__51485_51599 + (1));
seq__51479_51596 = G__51603;
chunk__51483_51597 = G__51604;
count__51484_51598 = G__51605;
i__51485_51599 = G__51606;
continue;
} else {
var G__51607 = seq__51479_51596;
var G__51608 = chunk__51483_51597;
var G__51609 = count__51484_51598;
var G__51610 = (i__51485_51599 + (1));
seq__51479_51596 = G__51607;
chunk__51483_51597 = G__51608;
count__51484_51598 = G__51609;
i__51485_51599 = G__51610;
continue;
}
} else {
var G__51611 = seq__51479_51596;
var G__51612 = chunk__51483_51597;
var G__51613 = count__51484_51598;
var G__51614 = (i__51485_51599 + (1));
seq__51479_51596 = G__51611;
chunk__51483_51597 = G__51612;
count__51484_51598 = G__51613;
i__51485_51599 = G__51614;
continue;
}
} else {
var temp__5753__auto___51615 = cljs.core.seq(seq__51479_51596);
if(temp__5753__auto___51615){
var seq__51479_51616__$1 = temp__5753__auto___51615;
if(cljs.core.chunked_seq_QMARK_(seq__51479_51616__$1)){
var c__4679__auto___51617 = cljs.core.chunk_first(seq__51479_51616__$1);
var G__51618 = cljs.core.chunk_rest(seq__51479_51616__$1);
var G__51619 = c__4679__auto___51617;
var G__51620 = cljs.core.count(c__4679__auto___51617);
var G__51621 = (0);
seq__51479_51596 = G__51618;
chunk__51483_51597 = G__51619;
count__51484_51598 = G__51620;
i__51485_51599 = G__51621;
continue;
} else {
var node_51622 = cljs.core.first(seq__51479_51616__$1);
if(cljs.core.not(node_51622.shadow$old)){
var path_match_51623 = shadow.cljs.devtools.client.browser.match_paths(node_51622.getAttribute("href"),path);
if(cljs.core.truth_(path_match_51623)){
var new_link_51624 = (function (){var G__51492 = node_51622.cloneNode(true);
G__51492.setAttribute("href",[cljs.core.str.cljs$core$IFn$_invoke$arity$1(path_match_51623),"?r=",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.rand.cljs$core$IFn$_invoke$arity$0())].join(''));

return G__51492;
})();
(node_51622.shadow$old = true);

(new_link_51624.onload = ((function (seq__51479_51596,chunk__51483_51597,count__51484_51598,i__51485_51599,seq__51445,chunk__51447,count__51448,i__51449,new_link_51624,path_match_51623,node_51622,seq__51479_51616__$1,temp__5753__auto___51615,path,map__51444,map__51444__$1,msg,updates){
return (function (e){
return goog.dom.removeNode(node_51622);
});})(seq__51479_51596,chunk__51483_51597,count__51484_51598,i__51485_51599,seq__51445,chunk__51447,count__51448,i__51449,new_link_51624,path_match_51623,node_51622,seq__51479_51616__$1,temp__5753__auto___51615,path,map__51444,map__51444__$1,msg,updates))
);

shadow.cljs.devtools.client.browser.devtools_msg.cljs$core$IFn$_invoke$arity$variadic("load CSS",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([path_match_51623], 0));

goog.dom.insertSiblingAfter(new_link_51624,node_51622);


var G__51625 = cljs.core.next(seq__51479_51616__$1);
var G__51626 = null;
var G__51627 = (0);
var G__51628 = (0);
seq__51479_51596 = G__51625;
chunk__51483_51597 = G__51626;
count__51484_51598 = G__51627;
i__51485_51599 = G__51628;
continue;
} else {
var G__51629 = cljs.core.next(seq__51479_51616__$1);
var G__51630 = null;
var G__51631 = (0);
var G__51632 = (0);
seq__51479_51596 = G__51629;
chunk__51483_51597 = G__51630;
count__51484_51598 = G__51631;
i__51485_51599 = G__51632;
continue;
}
} else {
var G__51633 = cljs.core.next(seq__51479_51616__$1);
var G__51634 = null;
var G__51635 = (0);
var G__51636 = (0);
seq__51479_51596 = G__51633;
chunk__51483_51597 = G__51634;
count__51484_51598 = G__51635;
i__51485_51599 = G__51636;
continue;
}
}
} else {
}
}
break;
}


var G__51637 = seq__51445;
var G__51638 = chunk__51447;
var G__51639 = count__51448;
var G__51640 = (i__51449 + (1));
seq__51445 = G__51637;
chunk__51447 = G__51638;
count__51448 = G__51639;
i__51449 = G__51640;
continue;
} else {
var G__51641 = seq__51445;
var G__51642 = chunk__51447;
var G__51643 = count__51448;
var G__51644 = (i__51449 + (1));
seq__51445 = G__51641;
chunk__51447 = G__51642;
count__51448 = G__51643;
i__51449 = G__51644;
continue;
}
} else {
var temp__5753__auto__ = cljs.core.seq(seq__51445);
if(temp__5753__auto__){
var seq__51445__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__51445__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__51445__$1);
var G__51645 = cljs.core.chunk_rest(seq__51445__$1);
var G__51646 = c__4679__auto__;
var G__51647 = cljs.core.count(c__4679__auto__);
var G__51648 = (0);
seq__51445 = G__51645;
chunk__51447 = G__51646;
count__51448 = G__51647;
i__51449 = G__51648;
continue;
} else {
var path = cljs.core.first(seq__51445__$1);
if(clojure.string.ends_with_QMARK_(path,"css")){
var seq__51493_51649 = cljs.core.seq(cljs.core.array_seq.cljs$core$IFn$_invoke$arity$1(document.querySelectorAll("link[rel=\"stylesheet\"]")));
var chunk__51497_51650 = null;
var count__51498_51651 = (0);
var i__51499_51652 = (0);
while(true){
if((i__51499_51652 < count__51498_51651)){
var node_51653 = chunk__51497_51650.cljs$core$IIndexed$_nth$arity$2(null,i__51499_51652);
if(cljs.core.not(node_51653.shadow$old)){
var path_match_51654 = shadow.cljs.devtools.client.browser.match_paths(node_51653.getAttribute("href"),path);
if(cljs.core.truth_(path_match_51654)){
var new_link_51655 = (function (){var G__51505 = node_51653.cloneNode(true);
G__51505.setAttribute("href",[cljs.core.str.cljs$core$IFn$_invoke$arity$1(path_match_51654),"?r=",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.rand.cljs$core$IFn$_invoke$arity$0())].join(''));

return G__51505;
})();
(node_51653.shadow$old = true);

(new_link_51655.onload = ((function (seq__51493_51649,chunk__51497_51650,count__51498_51651,i__51499_51652,seq__51445,chunk__51447,count__51448,i__51449,new_link_51655,path_match_51654,node_51653,path,seq__51445__$1,temp__5753__auto__,map__51444,map__51444__$1,msg,updates){
return (function (e){
return goog.dom.removeNode(node_51653);
});})(seq__51493_51649,chunk__51497_51650,count__51498_51651,i__51499_51652,seq__51445,chunk__51447,count__51448,i__51449,new_link_51655,path_match_51654,node_51653,path,seq__51445__$1,temp__5753__auto__,map__51444,map__51444__$1,msg,updates))
);

shadow.cljs.devtools.client.browser.devtools_msg.cljs$core$IFn$_invoke$arity$variadic("load CSS",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([path_match_51654], 0));

goog.dom.insertSiblingAfter(new_link_51655,node_51653);


var G__51656 = seq__51493_51649;
var G__51657 = chunk__51497_51650;
var G__51658 = count__51498_51651;
var G__51659 = (i__51499_51652 + (1));
seq__51493_51649 = G__51656;
chunk__51497_51650 = G__51657;
count__51498_51651 = G__51658;
i__51499_51652 = G__51659;
continue;
} else {
var G__51660 = seq__51493_51649;
var G__51661 = chunk__51497_51650;
var G__51662 = count__51498_51651;
var G__51663 = (i__51499_51652 + (1));
seq__51493_51649 = G__51660;
chunk__51497_51650 = G__51661;
count__51498_51651 = G__51662;
i__51499_51652 = G__51663;
continue;
}
} else {
var G__51664 = seq__51493_51649;
var G__51665 = chunk__51497_51650;
var G__51666 = count__51498_51651;
var G__51667 = (i__51499_51652 + (1));
seq__51493_51649 = G__51664;
chunk__51497_51650 = G__51665;
count__51498_51651 = G__51666;
i__51499_51652 = G__51667;
continue;
}
} else {
var temp__5753__auto___51668__$1 = cljs.core.seq(seq__51493_51649);
if(temp__5753__auto___51668__$1){
var seq__51493_51669__$1 = temp__5753__auto___51668__$1;
if(cljs.core.chunked_seq_QMARK_(seq__51493_51669__$1)){
var c__4679__auto___51670 = cljs.core.chunk_first(seq__51493_51669__$1);
var G__51671 = cljs.core.chunk_rest(seq__51493_51669__$1);
var G__51672 = c__4679__auto___51670;
var G__51673 = cljs.core.count(c__4679__auto___51670);
var G__51674 = (0);
seq__51493_51649 = G__51671;
chunk__51497_51650 = G__51672;
count__51498_51651 = G__51673;
i__51499_51652 = G__51674;
continue;
} else {
var node_51675 = cljs.core.first(seq__51493_51669__$1);
if(cljs.core.not(node_51675.shadow$old)){
var path_match_51676 = shadow.cljs.devtools.client.browser.match_paths(node_51675.getAttribute("href"),path);
if(cljs.core.truth_(path_match_51676)){
var new_link_51677 = (function (){var G__51506 = node_51675.cloneNode(true);
G__51506.setAttribute("href",[cljs.core.str.cljs$core$IFn$_invoke$arity$1(path_match_51676),"?r=",cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.rand.cljs$core$IFn$_invoke$arity$0())].join(''));

return G__51506;
})();
(node_51675.shadow$old = true);

(new_link_51677.onload = ((function (seq__51493_51649,chunk__51497_51650,count__51498_51651,i__51499_51652,seq__51445,chunk__51447,count__51448,i__51449,new_link_51677,path_match_51676,node_51675,seq__51493_51669__$1,temp__5753__auto___51668__$1,path,seq__51445__$1,temp__5753__auto__,map__51444,map__51444__$1,msg,updates){
return (function (e){
return goog.dom.removeNode(node_51675);
});})(seq__51493_51649,chunk__51497_51650,count__51498_51651,i__51499_51652,seq__51445,chunk__51447,count__51448,i__51449,new_link_51677,path_match_51676,node_51675,seq__51493_51669__$1,temp__5753__auto___51668__$1,path,seq__51445__$1,temp__5753__auto__,map__51444,map__51444__$1,msg,updates))
);

shadow.cljs.devtools.client.browser.devtools_msg.cljs$core$IFn$_invoke$arity$variadic("load CSS",cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([path_match_51676], 0));

goog.dom.insertSiblingAfter(new_link_51677,node_51675);


var G__51678 = cljs.core.next(seq__51493_51669__$1);
var G__51679 = null;
var G__51680 = (0);
var G__51681 = (0);
seq__51493_51649 = G__51678;
chunk__51497_51650 = G__51679;
count__51498_51651 = G__51680;
i__51499_51652 = G__51681;
continue;
} else {
var G__51682 = cljs.core.next(seq__51493_51669__$1);
var G__51683 = null;
var G__51684 = (0);
var G__51685 = (0);
seq__51493_51649 = G__51682;
chunk__51497_51650 = G__51683;
count__51498_51651 = G__51684;
i__51499_51652 = G__51685;
continue;
}
} else {
var G__51686 = cljs.core.next(seq__51493_51669__$1);
var G__51687 = null;
var G__51688 = (0);
var G__51689 = (0);
seq__51493_51649 = G__51686;
chunk__51497_51650 = G__51687;
count__51498_51651 = G__51688;
i__51499_51652 = G__51689;
continue;
}
}
} else {
}
}
break;
}


var G__51690 = cljs.core.next(seq__51445__$1);
var G__51691 = null;
var G__51692 = (0);
var G__51693 = (0);
seq__51445 = G__51690;
chunk__51447 = G__51691;
count__51448 = G__51692;
i__51449 = G__51693;
continue;
} else {
var G__51694 = cljs.core.next(seq__51445__$1);
var G__51695 = null;
var G__51696 = (0);
var G__51697 = (0);
seq__51445 = G__51694;
chunk__51447 = G__51695;
count__51448 = G__51696;
i__51449 = G__51697;
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
shadow.cljs.devtools.client.browser.repl_init = (function shadow$cljs$devtools$client$browser$repl_init(runtime,p__51507){
var map__51508 = p__51507;
var map__51508__$1 = cljs.core.__destructure_map(map__51508);
var repl_state = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51508__$1,new cljs.core.Keyword(null,"repl-state","repl-state",-1733780387));
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

(shadow.cljs.devtools.client.shared.Runtime.prototype.shadow$cljs$devtools$client$shared$IHostSpecific$do_invoke$arity$2 = (function (this$,p__51509){
var map__51510 = p__51509;
var map__51510__$1 = cljs.core.__destructure_map(map__51510);
var _ = map__51510__$1;
var js = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51510__$1,new cljs.core.Keyword(null,"js","js",1768080579));
var this$__$1 = this;
return shadow.cljs.devtools.client.browser.global_eval(js);
}));

(shadow.cljs.devtools.client.shared.Runtime.prototype.shadow$cljs$devtools$client$shared$IHostSpecific$do_repl_init$arity$4 = (function (runtime,p__51511,done,error){
var map__51512 = p__51511;
var map__51512__$1 = cljs.core.__destructure_map(map__51512);
var repl_sources = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51512__$1,new cljs.core.Keyword(null,"repl-sources","repl-sources",723867535));
var runtime__$1 = this;
return shadow.cljs.devtools.client.shared.load_sources(runtime__$1,cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentVector.EMPTY,cljs.core.remove.cljs$core$IFn$_invoke$arity$2(shadow.cljs.devtools.client.env.src_is_loaded_QMARK_,repl_sources)),(function (sources){
shadow.cljs.devtools.client.browser.do_js_load(sources);

return (done.cljs$core$IFn$_invoke$arity$0 ? done.cljs$core$IFn$_invoke$arity$0() : done.call(null));
}));
}));

(shadow.cljs.devtools.client.shared.Runtime.prototype.shadow$cljs$devtools$client$shared$IHostSpecific$do_repl_require$arity$4 = (function (runtime,p__51513,done,error){
var map__51514 = p__51513;
var map__51514__$1 = cljs.core.__destructure_map(map__51514);
var msg = map__51514__$1;
var sources = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51514__$1,new cljs.core.Keyword(null,"sources","sources",-321166424));
var reload_namespaces = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51514__$1,new cljs.core.Keyword(null,"reload-namespaces","reload-namespaces",250210134));
var js_requires = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51514__$1,new cljs.core.Keyword(null,"js-requires","js-requires",-1311472051));
var runtime__$1 = this;
var sources_to_load = cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentVector.EMPTY,cljs.core.remove.cljs$core$IFn$_invoke$arity$2((function (p__51515){
var map__51516 = p__51515;
var map__51516__$1 = cljs.core.__destructure_map(map__51516);
var src = map__51516__$1;
var provides = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51516__$1,new cljs.core.Keyword(null,"provides","provides",-1634397992));
var and__4251__auto__ = shadow.cljs.devtools.client.env.src_is_loaded_QMARK_(src);
if(cljs.core.truth_(and__4251__auto__)){
return cljs.core.not(cljs.core.some(reload_namespaces,provides));
} else {
return and__4251__auto__;
}
}),sources));
if(cljs.core.not(cljs.core.seq(sources_to_load))){
var G__51517 = cljs.core.PersistentVector.EMPTY;
return (done.cljs$core$IFn$_invoke$arity$1 ? done.cljs$core$IFn$_invoke$arity$1(G__51517) : done.call(null,G__51517));
} else {
return shadow.remote.runtime.shared.call.cljs$core$IFn$_invoke$arity$3(runtime__$1,new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"op","op",-1882987955),new cljs.core.Keyword(null,"cljs-load-sources","cljs-load-sources",-1458295962),new cljs.core.Keyword(null,"to","to",192099007),shadow.cljs.devtools.client.env.worker_client_id,new cljs.core.Keyword(null,"sources","sources",-321166424),cljs.core.into.cljs$core$IFn$_invoke$arity$3(cljs.core.PersistentVector.EMPTY,cljs.core.map.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"resource-id","resource-id",-1308422582)),sources_to_load)], null),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"cljs-sources","cljs-sources",31121610),(function (p__51518){
var map__51519 = p__51518;
var map__51519__$1 = cljs.core.__destructure_map(map__51519);
var msg__$1 = map__51519__$1;
var sources__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51519__$1,new cljs.core.Keyword(null,"sources","sources",-321166424));
try{shadow.cljs.devtools.client.browser.do_js_load(sources__$1);

if(cljs.core.seq(js_requires)){
shadow.cljs.devtools.client.browser.do_js_requires(js_requires);
} else {
}

return (done.cljs$core$IFn$_invoke$arity$1 ? done.cljs$core$IFn$_invoke$arity$1(sources_to_load) : done.call(null,sources_to_load));
}catch (e51520){var ex = e51520;
return (error.cljs$core$IFn$_invoke$arity$1 ? error.cljs$core$IFn$_invoke$arity$1(ex) : error.call(null,ex));
}})], null));
}
}));

shadow.cljs.devtools.client.shared.add_plugin_BANG_(new cljs.core.Keyword("shadow.cljs.devtools.client.browser","client","shadow.cljs.devtools.client.browser/client",-1461019282),cljs.core.PersistentHashSet.EMPTY,(function (p__51521){
var map__51522 = p__51521;
var map__51522__$1 = cljs.core.__destructure_map(map__51522);
var env = map__51522__$1;
var runtime = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51522__$1,new cljs.core.Keyword(null,"runtime","runtime",-1331573996));
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
}),new cljs.core.Keyword(null,"cljs-asset-update","cljs-asset-update",1224093028),(function (p__51523){
var map__51524 = p__51523;
var map__51524__$1 = cljs.core.__destructure_map(map__51524);
var msg = map__51524__$1;
var updates = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51524__$1,new cljs.core.Keyword(null,"updates","updates",2013983452));
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
}),new cljs.core.Keyword("shadow.cljs.devtools.client.env","worker-notify","shadow.cljs.devtools.client.env/worker-notify",-1456820670),(function (p__51525){
var map__51526 = p__51525;
var map__51526__$1 = cljs.core.__destructure_map(map__51526);
var event_op = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51526__$1,new cljs.core.Keyword(null,"event-op","event-op",200358057));
var client_id = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51526__$1,new cljs.core.Keyword(null,"client-id","client-id",-464622140));
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
}),(function (p__51527){
var map__51528 = p__51527;
var map__51528__$1 = cljs.core.__destructure_map(map__51528);
var svc = map__51528__$1;
var runtime = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__51528__$1,new cljs.core.Keyword(null,"runtime","runtime",-1331573996));
return shadow.remote.runtime.api.del_extension(runtime,new cljs.core.Keyword("shadow.cljs.devtools.client.browser","client","shadow.cljs.devtools.client.browser/client",-1461019282));
}));

shadow.cljs.devtools.client.shared.init_runtime_BANG_(shadow.cljs.devtools.client.browser.client_info,shadow.cljs.devtools.client.websocket.start,shadow.cljs.devtools.client.websocket.send,shadow.cljs.devtools.client.websocket.stop);
} else {
}

//# sourceMappingURL=shadow.cljs.devtools.client.browser.js.map
