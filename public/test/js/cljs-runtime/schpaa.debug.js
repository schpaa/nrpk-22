goog.provide('schpaa.debug');
re_frame.core.reg_event_db.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword("debug","in-debug-cancel","debug/in-debug-cancel",1418836672),(function (db__$1,_){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(db__$1,new cljs.core.Keyword(null,"in-debug","in-debug",290605356),false);
}));
re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword("debug","in-debug?","debug/in-debug?",-770874151),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([(function (db__$1){
return new cljs.core.Keyword(null,"in-debug","in-debug",290605356).cljs$core$IFn$_invoke$arity$2(db__$1,false);
})], 0));
schpaa.debug.ppr = (function schpaa$debug$ppr(x){
var _STAR_print_pprint_dispatch_STAR__orig_val__354610 = cljs.pprint._STAR_print_pprint_dispatch_STAR_;
var _STAR_print_pprint_dispatch_STAR__temp_val__354613 = cljs.pprint.code_dispatch;
(cljs.pprint._STAR_print_pprint_dispatch_STAR_ = _STAR_print_pprint_dispatch_STAR__temp_val__354613);

try{var sb__5687__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__354614_354673 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__354615_354674 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__354616_354675 = true;
var _STAR_print_fn_STAR__temp_val__354617_354676 = (function (x__5688__auto__){
return sb__5687__auto__.append(x__5688__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__354616_354675);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__354617_354676);

try{cljs.pprint.pprint.cljs$core$IFn$_invoke$arity$1(x);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__354615_354674);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__354614_354673);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__5687__auto__);
}finally {(cljs.pprint._STAR_print_pprint_dispatch_STAR_ = _STAR_print_pprint_dispatch_STAR__orig_val__354610);
}});
schpaa.debug.pre = (function schpaa$debug$pre(var_args){
var args__5772__auto__ = [];
var len__5766__auto___354678 = arguments.length;
var i__5767__auto___354679 = (0);
while(true){
if((i__5767__auto___354679 < len__5766__auto___354678)){
args__5772__auto__.push((arguments[i__5767__auto___354679]));

var G__354680 = (i__5767__auto___354679 + (1));
i__5767__auto___354679 = G__354680;
continue;
} else {
}
break;
}

var argseq__5773__auto__ = ((((0) < args__5772__auto__.length))?(new cljs.core.IndexedSeq(args__5772__auto__.slice((0)),(0),null)):null);
return schpaa.debug.pre.cljs$core$IFn$_invoke$arity$variadic(argseq__5773__auto__);
});

(schpaa.debug.pre.cljs$core$IFn$_invoke$arity$variadic = (function (p){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div","div",1057191632),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"class","class",-2030961996),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, ["bg-black/90",new cljs.core.Keyword(null,"text-amber-300","text-amber-300",-286424251),new cljs.core.Keyword(null,"p-1","p-1",190484676)], null)], null),((cljs.core.seq(p))?(function (){var iter__5520__auto__ = (function schpaa$debug$iter__354619(s__354620){
return (new cljs.core.LazySeq(null,(function (){
var s__354620__$1 = s__354620;
while(true){
var temp__5753__auto__ = cljs.core.seq(s__354620__$1);
if(temp__5753__auto__){
var s__354620__$2 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(s__354620__$2)){
var c__5518__auto__ = cljs.core.chunk_first(s__354620__$2);
var size__5519__auto__ = cljs.core.count(c__5518__auto__);
var b__354622 = cljs.core.chunk_buffer(size__5519__auto__);
if((function (){var i__354621 = (0);
while(true){
if((i__354621 < size__5519__auto__)){
var e = cljs.core._nth(c__5518__auto__,i__354621);
cljs.core.chunk_append(b__354622,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"pre","pre",2118456869),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"style","style",-496642736),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"white-space","white-space",-707351930),new cljs.core.Keyword(null,"pre-wrap","pre-wrap",979551718)], null)], null),(function (){var sb__5687__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__354623_354685 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__354624_354686 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__354625_354687 = true;
var _STAR_print_fn_STAR__temp_val__354626_354688 = ((function (i__354621,_STAR_print_newline_STAR__orig_val__354623_354685,_STAR_print_fn_STAR__orig_val__354624_354686,_STAR_print_newline_STAR__temp_val__354625_354687,sb__5687__auto__,e,c__5518__auto__,size__5519__auto__,b__354622,s__354620__$2,temp__5753__auto__){
return (function (x__5688__auto__){
return sb__5687__auto__.append(x__5688__auto__);
});})(i__354621,_STAR_print_newline_STAR__orig_val__354623_354685,_STAR_print_fn_STAR__orig_val__354624_354686,_STAR_print_newline_STAR__temp_val__354625_354687,sb__5687__auto__,e,c__5518__auto__,size__5519__auto__,b__354622,s__354620__$2,temp__5753__auto__))
;
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__354625_354687);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__354626_354688);

try{cljs.pprint.pprint.cljs$core$IFn$_invoke$arity$1(e);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__354624_354686);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__354623_354685);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__5687__auto__);
})()], null));

var G__354690 = (i__354621 + (1));
i__354621 = G__354690;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons(cljs.core.chunk(b__354622),schpaa$debug$iter__354619(cljs.core.chunk_rest(s__354620__$2)));
} else {
return cljs.core.chunk_cons(cljs.core.chunk(b__354622),null);
}
} else {
var e = cljs.core.first(s__354620__$2);
return cljs.core.cons(new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"pre","pre",2118456869),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"style","style",-496642736),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"white-space","white-space",-707351930),new cljs.core.Keyword(null,"pre-wrap","pre-wrap",979551718)], null)], null),(function (){var sb__5687__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__354641_354693 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__354642_354694 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__354643_354695 = true;
var _STAR_print_fn_STAR__temp_val__354644_354696 = ((function (_STAR_print_newline_STAR__orig_val__354641_354693,_STAR_print_fn_STAR__orig_val__354642_354694,_STAR_print_newline_STAR__temp_val__354643_354695,sb__5687__auto__,e,s__354620__$2,temp__5753__auto__){
return (function (x__5688__auto__){
return sb__5687__auto__.append(x__5688__auto__);
});})(_STAR_print_newline_STAR__orig_val__354641_354693,_STAR_print_fn_STAR__orig_val__354642_354694,_STAR_print_newline_STAR__temp_val__354643_354695,sb__5687__auto__,e,s__354620__$2,temp__5753__auto__))
;
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__354643_354695);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__354644_354696);

try{cljs.pprint.pprint.cljs$core$IFn$_invoke$arity$1(e);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__354642_354694);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__354641_354693);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__5687__auto__);
})()], null),schpaa$debug$iter__354619(cljs.core.rest(s__354620__$2)));
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
var _STAR_print_newline_STAR__orig_val__354646_354697 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__354647_354698 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__354648_354699 = true;
var _STAR_print_fn_STAR__temp_val__354649_354700 = (function (x__5688__auto__){
return sb__5687__auto__.append(x__5688__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__354648_354699);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__354649_354700);

try{cljs.pprint.pprint.cljs$core$IFn$_invoke$arity$1(p);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__354647_354698);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__354646_354697);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__5687__auto__);
})()], null))], null);
}));

(schpaa.debug.pre.cljs$lang$maxFixedArity = (0));

/** @this {Function} */
(schpaa.debug.pre.cljs$lang$applyTo = (function (seq354618){
var self__5752__auto__ = this;
return self__5752__auto__.cljs$core$IFn$_invoke$arity$variadic(cljs.core.seq(seq354618));
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
var len__5766__auto___354701 = arguments.length;
var i__5767__auto___354702 = (0);
while(true){
if((i__5767__auto___354702 < len__5766__auto___354701)){
args__5772__auto__.push((arguments[i__5767__auto___354702]));

var G__354703 = (i__5767__auto___354702 + (1));
i__5767__auto___354702 = G__354703;
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
var _STAR_print_newline_STAR__orig_val__354652_354704 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__354653_354705 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__354654_354706 = true;
var _STAR_print_fn_STAR__temp_val__354655_354707 = (function (x__5688__auto__){
return sb__5687__auto__.append(x__5688__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__354654_354706);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__354655_354707);

try{cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.print,s);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__354653_354705);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__354652_354704);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__5687__auto__);
}));

(schpaa.debug.strp.cljs$lang$maxFixedArity = (0));

/** @this {Function} */
(schpaa.debug.strp.cljs$lang$applyTo = (function (seq354650){
var self__5752__auto__ = this;
return self__5752__auto__.cljs$core$IFn$_invoke$arity$variadic(cljs.core.seq(seq354650));
}));


//# sourceMappingURL=schpaa.debug.js.map
