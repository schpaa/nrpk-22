goog.provide('breaking_point.core');
re_frame.core.reg_event_fx.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword("breaking-point.core","set-breakpoints","breaking-point.core/set-breakpoints",607629013),(function (_,p__44706){
var vec__44707 = p__44706;
var ___$1 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__44707,(0),null);
var map__44710 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__44707,(1),null);
var map__44710__$1 = cljs.core.__destructure_map(map__44710);
var opts = map__44710__$1;
var breakpoints = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44710__$1,new cljs.core.Keyword(null,"breakpoints","breakpoints",1018731739));
var debounce_ms = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44710__$1,new cljs.core.Keyword(null,"debounce-ms","debounce-ms",-1127263167));
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword("breaking-point.core","set-breakpoints","breaking-point.core/set-breakpoints",607629013),opts], null);
}));
re_frame.core.reg_cofx(new cljs.core.Keyword("breaking-point.core","screen-dimensions","breaking-point.core/screen-dimensions",407195814),(function (coeffect){
var screen_width = (function (){var or__4253__auto__ = (function (){var G__44711 = window;
if((G__44711 == null)){
return null;
} else {
return G__44711.innerWidth;
}
})();
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
var or__4253__auto____$1 = (function (){var G__44712 = document;
var G__44712__$1 = (((G__44712 == null))?null:G__44712.documentElement);
if((G__44712__$1 == null)){
return null;
} else {
return G__44712__$1.clientWidth;
}
})();
if(cljs.core.truth_(or__4253__auto____$1)){
return or__4253__auto____$1;
} else {
var G__44713 = document;
var G__44713__$1 = (((G__44713 == null))?null:G__44713.body);
if((G__44713__$1 == null)){
return null;
} else {
return G__44713__$1.clientWidth;
}
}
}
})();
var screen_height = (function (){var or__4253__auto__ = (function (){var G__44714 = window;
if((G__44714 == null)){
return null;
} else {
return G__44714.innerHeight;
}
})();
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
var or__4253__auto____$1 = (function (){var G__44715 = document;
var G__44715__$1 = (((G__44715 == null))?null:G__44715.documentElement);
if((G__44715__$1 == null)){
return null;
} else {
return G__44715__$1.clientHeight;
}
})();
if(cljs.core.truth_(or__4253__auto____$1)){
return or__4253__auto____$1;
} else {
var G__44716 = document;
var G__44716__$1 = (((G__44716 == null))?null:G__44716.body);
if((G__44716__$1 == null)){
return null;
} else {
return G__44716__$1.clientHeight;
}
}
}
})();
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$variadic(coeffect,new cljs.core.Keyword(null,"screen-width","screen-width",1557963263),screen_width,cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"screen-height","screen-height",141363033),screen_height], 0));
}));
breaking_point.core.set_screen_dimensions = (function breaking_point$core$set_screen_dimensions(p__44717,_){
var map__44718 = p__44717;
var map__44718__$1 = cljs.core.__destructure_map(map__44718);
var db = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44718__$1,new cljs.core.Keyword(null,"db","db",993250759));
var screen_width = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44718__$1,new cljs.core.Keyword(null,"screen-width","screen-width",1557963263));
var screen_height = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44718__$1,new cljs.core.Keyword(null,"screen-height","screen-height",141363033));
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"db","db",993250759),cljs.core.assoc_in(cljs.core.assoc_in(db,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("breaking-point.core","breakpoints","breaking-point.core/breakpoints",1759036980),new cljs.core.Keyword(null,"screen-width","screen-width",1557963263)], null),screen_width),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("breaking-point.core","breakpoints","breaking-point.core/breakpoints",1759036980),new cljs.core.Keyword(null,"screen-height","screen-height",141363033)], null),screen_height)], null);
});
re_frame.core.reg_event_fx.cljs$core$IFn$_invoke$arity$3(new cljs.core.Keyword("breaking-point.core","set-screen-dimensions","breaking-point.core/set-screen-dimensions",1622393691),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [re_frame.core.inject_cofx.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword("breaking-point.core","screen-dimensions","breaking-point.core/screen-dimensions",407195814))], null),breaking_point.core.set_screen_dimensions);
re_frame.core.reg_event_fx.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword("breaking-point.core","set-screen-dimensions-debounced","breaking-point.core/set-screen-dimensions-debounced",807683602),(function (_,p__44720){
var vec__44721 = p__44720;
var ___$1 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__44721,(0),null);
var debounce_ms = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__44721,(1),null);
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"dispatch-debounce","dispatch-debounce",-2065179020),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"id","id",-1388402092),new cljs.core.Keyword("breaking-point.core","calcaulate-width-after-resize","breaking-point.core/calcaulate-width-after-resize",1288445004),new cljs.core.Keyword(null,"timeout","timeout",-318625318),debounce_ms,new cljs.core.Keyword(null,"action","action",-811238024),new cljs.core.Keyword(null,"dispatch","dispatch",1319337009),new cljs.core.Keyword(null,"event","event",301435442),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("breaking-point.core","set-screen-dimensions","breaking-point.core/set-screen-dimensions",1622393691)], null)], null)], null)], null);
}));
breaking_point.core.get_screen_width = (function breaking_point$core$get_screen_width(db,_){
return cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(db,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("breaking-point.core","breakpoints","breaking-point.core/breakpoints",1759036980),new cljs.core.Keyword(null,"screen-width","screen-width",1557963263)], null));
});
breaking_point.core.get_screen_height = (function breaking_point$core$get_screen_height(db,_){
return cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(db,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("breaking-point.core","breakpoints","breaking-point.core/breakpoints",1759036980),new cljs.core.Keyword(null,"screen-height","screen-height",141363033)], null));
});
breaking_point.core.__GT_get_screen = (function breaking_point$core$__GT_get_screen(breakpoints){
return (function breaking_point$core$__GT_get_screen_$_get_screen(screen_width,_){
if(cljs.core.truth_(screen_width)){
return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3((function (prev_breakpoint,p__44733){
var vec__44734 = p__44733;
var screen_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__44734,(0),null);
var breakpoint = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__44734,(1),null);
if((((breakpoint == null)) || ((((screen_width < breakpoint)) && ((screen_width >= prev_breakpoint)))))){
return cljs.core.reduced(screen_key);
} else {
return breakpoint;
}
}),(0),cljs.core.partition_all.cljs$core$IFn$_invoke$arity$2((2),breakpoints));
} else {
return null;
}
});
});
breaking_point.core.get_orientation = (function breaking_point$core$get_orientation(p__44737,_){
var vec__44738 = p__44737;
var screen_width = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__44738,(0),null);
var screen_height = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__44738,(1),null);
if((screen_height > screen_width)){
return new cljs.core.Keyword(null,"portrait","portrait",-9810007);
} else {
return new cljs.core.Keyword(null,"landscape","landscape",1519839253);
}
});
breaking_point.core.register_subs = (function breaking_point$core$register_subs(breakpoints){
re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword("breaking-point.core","screen-width","breaking-point.core/screen-width",-2005106360),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([breaking_point.core.get_screen_width], 0));

re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword("breaking-point.core","screen-height","breaking-point.core/screen-height",1145919666),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([breaking_point.core.get_screen_height], 0));

re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword("breaking-point.core","screen","breaking-point.core/screen",834517405),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"<-","<-",760412998),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("breaking-point.core","screen-width","breaking-point.core/screen-width",-2005106360)], null),breaking_point.core.__GT_get_screen(breakpoints)], 0));

re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword("breaking-point.core","orientation","breaking-point.core/orientation",1892289812),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"<-","<-",760412998),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("breaking-point.core","screen-width","breaking-point.core/screen-width",-2005106360)], null),new cljs.core.Keyword(null,"<-","<-",760412998),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("breaking-point.core","screen-height","breaking-point.core/screen-height",1145919666)], null),breaking_point.core.get_orientation], 0));

re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword("breaking-point.core","portrait?","breaking-point.core/portrait?",-1731575960),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"<-","<-",760412998),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("breaking-point.core","orientation","breaking-point.core/orientation",1892289812)], null),(function (orientation,_){
return cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(orientation,new cljs.core.Keyword(null,"portrait","portrait",-9810007));
})], 0));

re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword("breaking-point.core","landscape?","breaking-point.core/landscape?",1510790059),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"<-","<-",760412998),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("breaking-point.core","orientation","breaking-point.core/orientation",1892289812)], null),(function (orientation,_){
return cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(orientation,new cljs.core.Keyword(null,"landscape","landscape",1519839253));
})], 0));

var screen_keys = (function (){var G__44741 = breakpoints;
var G__44741__$1 = (((G__44741 == null))?null:cljs.core.map_indexed.cljs$core$IFn$_invoke$arity$2(cljs.core.vector,G__44741));
var G__44741__$2 = (((G__44741__$1 == null))?null:cljs.core.filter.cljs$core$IFn$_invoke$arity$2((function (p__44742){
var vec__44743 = p__44742;
var i = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__44743,(0),null);
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__44743,(1),null);
return cljs.core.even_QMARK_(i);
}),G__44741__$1));
if((G__44741__$2 == null)){
return null;
} else {
return cljs.core.mapv.cljs$core$IFn$_invoke$arity$2(cljs.core.second,G__44741__$2);
}
})();
var seq__44746 = cljs.core.seq(screen_keys);
var chunk__44747 = null;
var count__44748 = (0);
var i__44749 = (0);
while(true){
if((i__44749 < count__44748)){
var screen_key = chunk__44747.cljs$core$IIndexed$_nth$arity$2(null,i__44749);
re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(cljs.core.keyword.cljs$core$IFn$_invoke$arity$2("breaking-point.core",[cljs.core.name(screen_key),"?"].join('')),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"<-","<-",760412998),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("breaking-point.core","screen","breaking-point.core/screen",834517405)], null),((function (seq__44746,chunk__44747,count__44748,i__44749,screen_key,screen_keys){
return (function (screen,_){
return cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(screen,screen_key);
});})(seq__44746,chunk__44747,count__44748,i__44749,screen_key,screen_keys))
], 0));


var G__44777 = seq__44746;
var G__44778 = chunk__44747;
var G__44779 = count__44748;
var G__44780 = (i__44749 + (1));
seq__44746 = G__44777;
chunk__44747 = G__44778;
count__44748 = G__44779;
i__44749 = G__44780;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__44746);
if(temp__5753__auto__){
var seq__44746__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__44746__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__44746__$1);
var G__44781 = cljs.core.chunk_rest(seq__44746__$1);
var G__44782 = c__4679__auto__;
var G__44783 = cljs.core.count(c__4679__auto__);
var G__44784 = (0);
seq__44746 = G__44781;
chunk__44747 = G__44782;
count__44748 = G__44783;
i__44749 = G__44784;
continue;
} else {
var screen_key = cljs.core.first(seq__44746__$1);
re_frame.core.reg_sub.cljs$core$IFn$_invoke$arity$variadic(cljs.core.keyword.cljs$core$IFn$_invoke$arity$2("breaking-point.core",[cljs.core.name(screen_key),"?"].join('')),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"<-","<-",760412998),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("breaking-point.core","screen","breaking-point.core/screen",834517405)], null),((function (seq__44746,chunk__44747,count__44748,i__44749,screen_key,seq__44746__$1,temp__5753__auto__,screen_keys){
return (function (screen,_){
return cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(screen,screen_key);
});})(seq__44746,chunk__44747,count__44748,i__44749,screen_key,seq__44746__$1,temp__5753__auto__,screen_keys))
], 0));


var G__44785 = cljs.core.next(seq__44746__$1);
var G__44786 = null;
var G__44787 = (0);
var G__44788 = (0);
seq__44746 = G__44785;
chunk__44747 = G__44786;
count__44748 = G__44787;
i__44749 = G__44788;
continue;
}
} else {
return null;
}
}
break;
}
});
breaking_point.core.set_breakpoints = (function breaking_point$core$set_breakpoints(p__44750){
var map__44751 = p__44750;
var map__44751__$1 = cljs.core.__destructure_map(map__44751);
var opts = map__44751__$1;
var breakpoints = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44751__$1,new cljs.core.Keyword(null,"breakpoints","breakpoints",1018731739));
var debounce_ms = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__44751__$1,new cljs.core.Keyword(null,"debounce-ms","debounce-ms",-1127263167));
breaking_point.core.register_subs(breakpoints);

re_frame.core.dispatch(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("breaking-point.core","set-screen-dimensions","breaking-point.core/set-screen-dimensions",1622393691)], null));

return window.addEventListener("resize",(function (){
if(cljs.core.truth_(debounce_ms)){
return re_frame.core.dispatch(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("breaking-point.core","set-screen-dimensions-debounced","breaking-point.core/set-screen-dimensions-debounced",807683602),debounce_ms], null));
} else {
return re_frame.core.dispatch(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("breaking-point.core","set-screen-dimensions","breaking-point.core/set-screen-dimensions",1622393691)], null));
}
}),true);
});
re_frame.core.reg_fx(new cljs.core.Keyword("breaking-point.core","set-breakpoints","breaking-point.core/set-breakpoints",607629013),breaking_point.core.set_breakpoints);

//# sourceMappingURL=breaking_point.core.js.map
