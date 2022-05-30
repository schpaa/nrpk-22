goog.provide('schpaa.debug');
re_frame.core.reg_event_db.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword("debug","in-debug-cancel","debug/in-debug-cancel",1418836672),(function (db__$1,_){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(db__$1,new cljs.core.Keyword(null,"in-debug","in-debug",290605356),false);
}));
re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword("debug","in-debug?","debug/in-debug?",-770874151),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([(function (db__$1){
return new cljs.core.Keyword(null,"in-debug","in-debug",290605356).cljs$core$IFn$_invoke$arity$2(db__$1,false);
})], 0));
schpaa.debug.ppr = (function schpaa$debug$ppr(x){
var _STAR_print_pprint_dispatch_STAR__orig_val__74393 = cljs.pprint._STAR_print_pprint_dispatch_STAR_;
var _STAR_print_pprint_dispatch_STAR__temp_val__74394 = cljs.pprint.code_dispatch;
(cljs.pprint._STAR_print_pprint_dispatch_STAR_ = _STAR_print_pprint_dispatch_STAR__temp_val__74394);

try{var sb__5687__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__74395_74432 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__74396_74433 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__74397_74434 = true;
var _STAR_print_fn_STAR__temp_val__74398_74435 = (function (x__5688__auto__){
return sb__5687__auto__.append(x__5688__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__74397_74434);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__74398_74435);

try{cljs.pprint.pprint.cljs$core$IFn$_invoke$arity$1(x);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__74396_74433);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__74395_74432);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__5687__auto__);
}finally {(cljs.pprint._STAR_print_pprint_dispatch_STAR_ = _STAR_print_pprint_dispatch_STAR__orig_val__74393);
}});
schpaa.debug.pre = (function schpaa$debug$pre(var_args){
var args__5772__auto__ = [];
var len__5766__auto___74436 = arguments.length;
var i__5767__auto___74437 = (0);
while(true){
if((i__5767__auto___74437 < len__5766__auto___74436)){
args__5772__auto__.push((arguments[i__5767__auto___74437]));

var G__74438 = (i__5767__auto___74437 + (1));
i__5767__auto___74437 = G__74438;
continue;
} else {
}
break;
}

var argseq__5773__auto__ = ((((0) < args__5772__auto__.length))?(new cljs.core.IndexedSeq(args__5772__auto__.slice((0)),(0),null)):null);
return schpaa.debug.pre.cljs$core$IFn$_invoke$arity$variadic(argseq__5773__auto__);
});

(schpaa.debug.pre.cljs$core$IFn$_invoke$arity$variadic = (function (p){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div","div",1057191632),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"class","class",-2030961996),new cljs.core.PersistentVector(null, 4, 5, cljs.core.PersistentVector.EMPTY_NODE, ["bg-black/90",new cljs.core.Keyword(null,"text-amber-300","text-amber-300",-286424251),new cljs.core.Keyword(null,"text-xs","text-xs",1250326458),new cljs.core.Keyword(null,"p-1","p-1",190484676)], null)], null),((cljs.core.seq(p))?(function (){var iter__5520__auto__ = (function schpaa$debug$iter__74401(s__74402){
return (new cljs.core.LazySeq(null,(function (){
var s__74402__$1 = s__74402;
while(true){
var temp__5753__auto__ = cljs.core.seq(s__74402__$1);
if(temp__5753__auto__){
var s__74402__$2 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(s__74402__$2)){
var c__5518__auto__ = cljs.core.chunk_first(s__74402__$2);
var size__5519__auto__ = cljs.core.count(c__5518__auto__);
var b__74404 = cljs.core.chunk_buffer(size__5519__auto__);
if((function (){var i__74403 = (0);
while(true){
if((i__74403 < size__5519__auto__)){
var e = cljs.core._nth(c__5518__auto__,i__74403);
cljs.core.chunk_append(b__74404,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"pre","pre",2118456869),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"style","style",-496642736),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"white-space","white-space",-707351930),new cljs.core.Keyword(null,"pre-wrap","pre-wrap",979551718)], null)], null),(function (){var sb__5687__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__74405_74441 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__74406_74442 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__74407_74443 = true;
var _STAR_print_fn_STAR__temp_val__74408_74444 = ((function (i__74403,_STAR_print_newline_STAR__orig_val__74405_74441,_STAR_print_fn_STAR__orig_val__74406_74442,_STAR_print_newline_STAR__temp_val__74407_74443,sb__5687__auto__,e,c__5518__auto__,size__5519__auto__,b__74404,s__74402__$2,temp__5753__auto__){
return (function (x__5688__auto__){
return sb__5687__auto__.append(x__5688__auto__);
});})(i__74403,_STAR_print_newline_STAR__orig_val__74405_74441,_STAR_print_fn_STAR__orig_val__74406_74442,_STAR_print_newline_STAR__temp_val__74407_74443,sb__5687__auto__,e,c__5518__auto__,size__5519__auto__,b__74404,s__74402__$2,temp__5753__auto__))
;
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__74407_74443);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__74408_74444);

try{cljs.pprint.pprint.cljs$core$IFn$_invoke$arity$1(e);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__74406_74442);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__74405_74441);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__5687__auto__);
})()], null));

var G__74449 = (i__74403 + (1));
i__74403 = G__74449;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons(cljs.core.chunk(b__74404),schpaa$debug$iter__74401(cljs.core.chunk_rest(s__74402__$2)));
} else {
return cljs.core.chunk_cons(cljs.core.chunk(b__74404),null);
}
} else {
var e = cljs.core.first(s__74402__$2);
return cljs.core.cons(new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"pre","pre",2118456869),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"style","style",-496642736),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"white-space","white-space",-707351930),new cljs.core.Keyword(null,"pre-wrap","pre-wrap",979551718)], null)], null),(function (){var sb__5687__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__74409_74450 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__74410_74451 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__74411_74452 = true;
var _STAR_print_fn_STAR__temp_val__74412_74453 = ((function (_STAR_print_newline_STAR__orig_val__74409_74450,_STAR_print_fn_STAR__orig_val__74410_74451,_STAR_print_newline_STAR__temp_val__74411_74452,sb__5687__auto__,e,s__74402__$2,temp__5753__auto__){
return (function (x__5688__auto__){
return sb__5687__auto__.append(x__5688__auto__);
});})(_STAR_print_newline_STAR__orig_val__74409_74450,_STAR_print_fn_STAR__orig_val__74410_74451,_STAR_print_newline_STAR__temp_val__74411_74452,sb__5687__auto__,e,s__74402__$2,temp__5753__auto__))
;
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__74411_74452);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__74412_74453);

try{cljs.pprint.pprint.cljs$core$IFn$_invoke$arity$1(e);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__74410_74451);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__74409_74450);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__5687__auto__);
})()], null),schpaa$debug$iter__74401(cljs.core.rest(s__74402__$2)));
}
} else {
return null;
}
break;
}
}),null,null));
});
return iter__5520__auto__(p);
})():new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div","div",1057191632),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"style","style",-496642736),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"white-space","white-space",-707351930),new cljs.core.Keyword(null,"pre-wrap","pre-wrap",979551718)], null)], null),(function (){var sb__5687__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__74413_74456 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__74414_74457 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__74415_74458 = true;
var _STAR_print_fn_STAR__temp_val__74416_74459 = (function (x__5688__auto__){
return sb__5687__auto__.append(x__5688__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__74415_74458);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__74416_74459);

try{cljs.pprint.pprint.cljs$core$IFn$_invoke$arity$1(p);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__74414_74457);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__74413_74456);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__5687__auto__);
})()], null))], null);
}));

(schpaa.debug.pre.cljs$lang$maxFixedArity = (0));

/** @this {Function} */
(schpaa.debug.pre.cljs$lang$applyTo = (function (seq74399){
var self__5752__auto__ = this;
return self__5752__auto__.cljs$core$IFn$_invoke$arity$variadic(cljs.core.seq(seq74399));
}));

schpaa.debug.ppre = schpaa.debug.pre;
/**
 * Leverage the fact that printing will put a space between each entry.
 *   This is useful for things that accepts arguments as strings, like
 * 
 *   (defn pathData [{:keys [startX startY largeArcFlag endX endY]}]
 *  (strp "M" startX startY "A" 1 1 0 largeArcFlag 1 endX endY "L" 0 0)
 */
schpaa.debug.strp = (function schpaa$debug$strp(var_args){
var args__5772__auto__ = [];
var len__5766__auto___74460 = arguments.length;
var i__5767__auto___74461 = (0);
while(true){
if((i__5767__auto___74461 < len__5766__auto___74460)){
args__5772__auto__.push((arguments[i__5767__auto___74461]));

var G__74462 = (i__5767__auto___74461 + (1));
i__5767__auto___74461 = G__74462;
continue;
} else {
}
break;
}

var argseq__5773__auto__ = ((((0) < args__5772__auto__.length))?(new cljs.core.IndexedSeq(args__5772__auto__.slice((0)),(0),null)):null);
return schpaa.debug.strp.cljs$core$IFn$_invoke$arity$variadic(argseq__5773__auto__);
});

(schpaa.debug.strp.cljs$core$IFn$_invoke$arity$variadic = (function (s){
var sb__5687__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__74418_74463 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__74419_74464 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__74420_74465 = true;
var _STAR_print_fn_STAR__temp_val__74421_74466 = (function (x__5688__auto__){
return sb__5687__auto__.append(x__5688__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__74420_74465);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__74421_74466);

try{cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.print,s);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__74419_74464);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__74418_74463);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__5687__auto__);
}));

(schpaa.debug.strp.cljs$lang$maxFixedArity = (0));

/** @this {Function} */
(schpaa.debug.strp.cljs$lang$applyTo = (function (seq74417){
var self__5752__auto__ = this;
return self__5752__auto__.cljs$core$IFn$_invoke$arity$variadic(cljs.core.seq(seq74417));
}));


//# sourceMappingURL=schpaa.debug.js.map
