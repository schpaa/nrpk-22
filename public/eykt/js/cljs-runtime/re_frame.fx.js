goog.provide('re_frame.fx');
re_frame.fx.kind = new cljs.core.Keyword(null,"fx","fx",-1237829572);
if(cljs.core.truth_((re_frame.registrar.kinds.cljs$core$IFn$_invoke$arity$1 ? re_frame.registrar.kinds.cljs$core$IFn$_invoke$arity$1(re_frame.fx.kind) : re_frame.registrar.kinds.call(null,re_frame.fx.kind)))){
} else {
throw (new Error("Assert failed: (re-frame.registrar/kinds kind)"));
}
re_frame.fx.reg_fx = (function re_frame$fx$reg_fx(id,handler){
return re_frame.registrar.register_handler(re_frame.fx.kind,id,handler);
});
/**
 * An interceptor whose `:after` actions the contents of `:effects`. As a result,
 *   this interceptor is Domino 3.
 * 
 *   This interceptor is silently added (by reg-event-db etc) to the front of
 *   interceptor chains for all events.
 * 
 *   For each key in `:effects` (a map), it calls the registered `effects handler`
 *   (see `reg-fx` for registration of effect handlers).
 * 
 *   So, if `:effects` was:
 *    {:dispatch  [:hello 42]
 *     :db        {...}
 *     :undo      "set flag"}
 * 
 *   it will call the registered effect handlers for each of the map's keys:
 *   `:dispatch`, `:undo` and `:db`. When calling each handler, provides the map
 *   value for that key - so in the example above the effect handler for :dispatch
 *   will be given one arg `[:hello 42]`.
 * 
 *   You cannot rely on the ordering in which effects are executed, other than that
 *   `:db` is guaranteed to be executed first.
 */
re_frame.fx.do_fx = re_frame.interceptor.__GT_interceptor.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"id","id",-1388402092),new cljs.core.Keyword(null,"do-fx","do-fx",1194163050),new cljs.core.Keyword(null,"after","after",594996914),(function re_frame$fx$do_fx_after(context){
if(re_frame.trace.is_trace_enabled_QMARK_()){
var _STAR_current_trace_STAR__orig_val__53125 = re_frame.trace._STAR_current_trace_STAR_;
var _STAR_current_trace_STAR__temp_val__53126 = re_frame.trace.start_trace(new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"op-type","op-type",-1636141668),new cljs.core.Keyword("event","do-fx","event/do-fx",1357330452)], null));
(re_frame.trace._STAR_current_trace_STAR_ = _STAR_current_trace_STAR__temp_val__53126);

try{try{var effects = new cljs.core.Keyword(null,"effects","effects",-282369292).cljs$core$IFn$_invoke$arity$1(context);
var effects_without_db = cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(effects,new cljs.core.Keyword(null,"db","db",993250759));
var temp__5753__auto___53215 = new cljs.core.Keyword(null,"db","db",993250759).cljs$core$IFn$_invoke$arity$1(effects);
if(cljs.core.truth_(temp__5753__auto___53215)){
var new_db_53216 = temp__5753__auto___53215;
var fexpr__53129_53217 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,new cljs.core.Keyword(null,"db","db",993250759),false);
(fexpr__53129_53217.cljs$core$IFn$_invoke$arity$1 ? fexpr__53129_53217.cljs$core$IFn$_invoke$arity$1(new_db_53216) : fexpr__53129_53217.call(null,new_db_53216));
} else {
}

var seq__53131 = cljs.core.seq(effects_without_db);
var chunk__53132 = null;
var count__53133 = (0);
var i__53134 = (0);
while(true){
if((i__53134 < count__53133)){
var vec__53145 = chunk__53132.cljs$core$IIndexed$_nth$arity$2(null,i__53134);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53145,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53145,(1),null);
var temp__5751__auto___53218 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5751__auto___53218)){
var effect_fn_53219 = temp__5751__auto___53218;
(effect_fn_53219.cljs$core$IFn$_invoke$arity$1 ? effect_fn_53219.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_53219.call(null,effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: no handler registered for effect:",effect_key,". Ignoring."], 0));
}


var G__53220 = seq__53131;
var G__53221 = chunk__53132;
var G__53222 = count__53133;
var G__53223 = (i__53134 + (1));
seq__53131 = G__53220;
chunk__53132 = G__53221;
count__53133 = G__53222;
i__53134 = G__53223;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__53131);
if(temp__5753__auto__){
var seq__53131__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__53131__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__53131__$1);
var G__53224 = cljs.core.chunk_rest(seq__53131__$1);
var G__53225 = c__4679__auto__;
var G__53226 = cljs.core.count(c__4679__auto__);
var G__53227 = (0);
seq__53131 = G__53224;
chunk__53132 = G__53225;
count__53133 = G__53226;
i__53134 = G__53227;
continue;
} else {
var vec__53149 = cljs.core.first(seq__53131__$1);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53149,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53149,(1),null);
var temp__5751__auto___53228 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5751__auto___53228)){
var effect_fn_53229 = temp__5751__auto___53228;
(effect_fn_53229.cljs$core$IFn$_invoke$arity$1 ? effect_fn_53229.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_53229.call(null,effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: no handler registered for effect:",effect_key,". Ignoring."], 0));
}


var G__53230 = cljs.core.next(seq__53131__$1);
var G__53231 = null;
var G__53232 = (0);
var G__53233 = (0);
seq__53131 = G__53230;
chunk__53132 = G__53231;
count__53133 = G__53232;
i__53134 = G__53233;
continue;
}
} else {
return null;
}
}
break;
}
}finally {if(re_frame.trace.is_trace_enabled_QMARK_()){
var end__52743__auto___53234 = re_frame.interop.now();
var duration__52744__auto___53235 = (end__52743__auto___53234 - new cljs.core.Keyword(null,"start","start",-355208981).cljs$core$IFn$_invoke$arity$1(re_frame.trace._STAR_current_trace_STAR_));
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(re_frame.trace.traces,cljs.core.conj,cljs.core.assoc.cljs$core$IFn$_invoke$arity$variadic(re_frame.trace._STAR_current_trace_STAR_,new cljs.core.Keyword(null,"duration","duration",1444101068),duration__52744__auto___53235,cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"end","end",-268185958),re_frame.interop.now()], 0)));

re_frame.trace.run_tracing_callbacks_BANG_(end__52743__auto___53234);
} else {
}
}}finally {(re_frame.trace._STAR_current_trace_STAR_ = _STAR_current_trace_STAR__orig_val__53125);
}} else {
var effects = new cljs.core.Keyword(null,"effects","effects",-282369292).cljs$core$IFn$_invoke$arity$1(context);
var effects_without_db = cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(effects,new cljs.core.Keyword(null,"db","db",993250759));
var temp__5753__auto___53236 = new cljs.core.Keyword(null,"db","db",993250759).cljs$core$IFn$_invoke$arity$1(effects);
if(cljs.core.truth_(temp__5753__auto___53236)){
var new_db_53237 = temp__5753__auto___53236;
var fexpr__53152_53238 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,new cljs.core.Keyword(null,"db","db",993250759),false);
(fexpr__53152_53238.cljs$core$IFn$_invoke$arity$1 ? fexpr__53152_53238.cljs$core$IFn$_invoke$arity$1(new_db_53237) : fexpr__53152_53238.call(null,new_db_53237));
} else {
}

var seq__53153 = cljs.core.seq(effects_without_db);
var chunk__53154 = null;
var count__53155 = (0);
var i__53156 = (0);
while(true){
if((i__53156 < count__53155)){
var vec__53166 = chunk__53154.cljs$core$IIndexed$_nth$arity$2(null,i__53156);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53166,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53166,(1),null);
var temp__5751__auto___53239 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5751__auto___53239)){
var effect_fn_53240 = temp__5751__auto___53239;
(effect_fn_53240.cljs$core$IFn$_invoke$arity$1 ? effect_fn_53240.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_53240.call(null,effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: no handler registered for effect:",effect_key,". Ignoring."], 0));
}


var G__53241 = seq__53153;
var G__53242 = chunk__53154;
var G__53243 = count__53155;
var G__53244 = (i__53156 + (1));
seq__53153 = G__53241;
chunk__53154 = G__53242;
count__53155 = G__53243;
i__53156 = G__53244;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__53153);
if(temp__5753__auto__){
var seq__53153__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__53153__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__53153__$1);
var G__53245 = cljs.core.chunk_rest(seq__53153__$1);
var G__53246 = c__4679__auto__;
var G__53247 = cljs.core.count(c__4679__auto__);
var G__53248 = (0);
seq__53153 = G__53245;
chunk__53154 = G__53246;
count__53155 = G__53247;
i__53156 = G__53248;
continue;
} else {
var vec__53170 = cljs.core.first(seq__53153__$1);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53170,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53170,(1),null);
var temp__5751__auto___53249 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5751__auto___53249)){
var effect_fn_53250 = temp__5751__auto___53249;
(effect_fn_53250.cljs$core$IFn$_invoke$arity$1 ? effect_fn_53250.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_53250.call(null,effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: no handler registered for effect:",effect_key,". Ignoring."], 0));
}


var G__53251 = cljs.core.next(seq__53153__$1);
var G__53252 = null;
var G__53253 = (0);
var G__53254 = (0);
seq__53153 = G__53251;
chunk__53154 = G__53252;
count__53155 = G__53253;
i__53156 = G__53254;
continue;
}
} else {
return null;
}
}
break;
}
}
})], 0));
re_frame.fx.dispatch_later = (function re_frame$fx$dispatch_later(p__53177){
var map__53182 = p__53177;
var map__53182__$1 = cljs.core.__destructure_map(map__53182);
var effect = map__53182__$1;
var ms = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__53182__$1,new cljs.core.Keyword(null,"ms","ms",-1152709733));
var dispatch = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__53182__$1,new cljs.core.Keyword(null,"dispatch","dispatch",1319337009));
if(((cljs.core.empty_QMARK_(dispatch)) || ((!(typeof ms === 'number'))))){
return re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"error","error",-978969032),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: ignoring bad :dispatch-later value:",effect], 0));
} else {
return re_frame.interop.set_timeout_BANG_((function (){
return re_frame.router.dispatch(dispatch);
}),ms);
}
});
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"dispatch-later","dispatch-later",291951390),(function (value){
if(cljs.core.map_QMARK_(value)){
return re_frame.fx.dispatch_later(value);
} else {
var seq__53187 = cljs.core.seq(cljs.core.remove.cljs$core$IFn$_invoke$arity$2(cljs.core.nil_QMARK_,value));
var chunk__53188 = null;
var count__53189 = (0);
var i__53190 = (0);
while(true){
if((i__53190 < count__53189)){
var effect = chunk__53188.cljs$core$IIndexed$_nth$arity$2(null,i__53190);
re_frame.fx.dispatch_later(effect);


var G__53255 = seq__53187;
var G__53256 = chunk__53188;
var G__53257 = count__53189;
var G__53258 = (i__53190 + (1));
seq__53187 = G__53255;
chunk__53188 = G__53256;
count__53189 = G__53257;
i__53190 = G__53258;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__53187);
if(temp__5753__auto__){
var seq__53187__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__53187__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__53187__$1);
var G__53260 = cljs.core.chunk_rest(seq__53187__$1);
var G__53261 = c__4679__auto__;
var G__53262 = cljs.core.count(c__4679__auto__);
var G__53263 = (0);
seq__53187 = G__53260;
chunk__53188 = G__53261;
count__53189 = G__53262;
i__53190 = G__53263;
continue;
} else {
var effect = cljs.core.first(seq__53187__$1);
re_frame.fx.dispatch_later(effect);


var G__53265 = cljs.core.next(seq__53187__$1);
var G__53266 = null;
var G__53267 = (0);
var G__53268 = (0);
seq__53187 = G__53265;
chunk__53188 = G__53266;
count__53189 = G__53267;
i__53190 = G__53268;
continue;
}
} else {
return null;
}
}
break;
}
}
}));
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"fx","fx",-1237829572),(function (seq_of_effects){
if((!(cljs.core.sequential_QMARK_(seq_of_effects)))){
return re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: \":fx\" effect expects a seq, but was given ",cljs.core.type(seq_of_effects)], 0));
} else {
var seq__53191 = cljs.core.seq(cljs.core.remove.cljs$core$IFn$_invoke$arity$2(cljs.core.nil_QMARK_,seq_of_effects));
var chunk__53192 = null;
var count__53193 = (0);
var i__53194 = (0);
while(true){
if((i__53194 < count__53193)){
var vec__53201 = chunk__53192.cljs$core$IIndexed$_nth$arity$2(null,i__53194);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53201,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53201,(1),null);
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"db","db",993250759),effect_key)){
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: \":fx\" effect should not contain a :db effect"], 0));
} else {
}

var temp__5751__auto___53269 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5751__auto___53269)){
var effect_fn_53270 = temp__5751__auto___53269;
(effect_fn_53270.cljs$core$IFn$_invoke$arity$1 ? effect_fn_53270.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_53270.call(null,effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: in \":fx\" effect found ",effect_key," which has no associated handler. Ignoring."], 0));
}


var G__53271 = seq__53191;
var G__53272 = chunk__53192;
var G__53273 = count__53193;
var G__53274 = (i__53194 + (1));
seq__53191 = G__53271;
chunk__53192 = G__53272;
count__53193 = G__53273;
i__53194 = G__53274;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__53191);
if(temp__5753__auto__){
var seq__53191__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__53191__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__53191__$1);
var G__53275 = cljs.core.chunk_rest(seq__53191__$1);
var G__53276 = c__4679__auto__;
var G__53277 = cljs.core.count(c__4679__auto__);
var G__53278 = (0);
seq__53191 = G__53275;
chunk__53192 = G__53276;
count__53193 = G__53277;
i__53194 = G__53278;
continue;
} else {
var vec__53204 = cljs.core.first(seq__53191__$1);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53204,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__53204,(1),null);
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"db","db",993250759),effect_key)){
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: \":fx\" effect should not contain a :db effect"], 0));
} else {
}

var temp__5751__auto___53279 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5751__auto___53279)){
var effect_fn_53280 = temp__5751__auto___53279;
(effect_fn_53280.cljs$core$IFn$_invoke$arity$1 ? effect_fn_53280.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_53280.call(null,effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: in \":fx\" effect found ",effect_key," which has no associated handler. Ignoring."], 0));
}


var G__53281 = cljs.core.next(seq__53191__$1);
var G__53282 = null;
var G__53283 = (0);
var G__53284 = (0);
seq__53191 = G__53281;
chunk__53192 = G__53282;
count__53193 = G__53283;
i__53194 = G__53284;
continue;
}
} else {
return null;
}
}
break;
}
}
}));
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"dispatch","dispatch",1319337009),(function (value){
if((!(cljs.core.vector_QMARK_(value)))){
return re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"error","error",-978969032),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: ignoring bad :dispatch value. Expected a vector, but got:",value], 0));
} else {
return re_frame.router.dispatch(value);
}
}));
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"dispatch-n","dispatch-n",-504469236),(function (value){
if((!(cljs.core.sequential_QMARK_(value)))){
return re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"error","error",-978969032),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: ignoring bad :dispatch-n value. Expected a collection, but got:",value], 0));
} else {
var seq__53207 = cljs.core.seq(cljs.core.remove.cljs$core$IFn$_invoke$arity$2(cljs.core.nil_QMARK_,value));
var chunk__53208 = null;
var count__53209 = (0);
var i__53210 = (0);
while(true){
if((i__53210 < count__53209)){
var event = chunk__53208.cljs$core$IIndexed$_nth$arity$2(null,i__53210);
re_frame.router.dispatch(event);


var G__53287 = seq__53207;
var G__53288 = chunk__53208;
var G__53289 = count__53209;
var G__53290 = (i__53210 + (1));
seq__53207 = G__53287;
chunk__53208 = G__53288;
count__53209 = G__53289;
i__53210 = G__53290;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__53207);
if(temp__5753__auto__){
var seq__53207__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__53207__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__53207__$1);
var G__53291 = cljs.core.chunk_rest(seq__53207__$1);
var G__53292 = c__4679__auto__;
var G__53293 = cljs.core.count(c__4679__auto__);
var G__53294 = (0);
seq__53207 = G__53291;
chunk__53208 = G__53292;
count__53209 = G__53293;
i__53210 = G__53294;
continue;
} else {
var event = cljs.core.first(seq__53207__$1);
re_frame.router.dispatch(event);


var G__53295 = cljs.core.next(seq__53207__$1);
var G__53296 = null;
var G__53297 = (0);
var G__53298 = (0);
seq__53207 = G__53295;
chunk__53208 = G__53296;
count__53209 = G__53297;
i__53210 = G__53298;
continue;
}
} else {
return null;
}
}
break;
}
}
}));
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"deregister-event-handler","deregister-event-handler",-1096518994),(function (value){
var clear_event = cljs.core.partial.cljs$core$IFn$_invoke$arity$2(re_frame.registrar.clear_handlers,re_frame.events.kind);
if(cljs.core.sequential_QMARK_(value)){
var seq__53211 = cljs.core.seq(value);
var chunk__53212 = null;
var count__53213 = (0);
var i__53214 = (0);
while(true){
if((i__53214 < count__53213)){
var event = chunk__53212.cljs$core$IIndexed$_nth$arity$2(null,i__53214);
clear_event(event);


var G__53299 = seq__53211;
var G__53300 = chunk__53212;
var G__53301 = count__53213;
var G__53302 = (i__53214 + (1));
seq__53211 = G__53299;
chunk__53212 = G__53300;
count__53213 = G__53301;
i__53214 = G__53302;
continue;
} else {
var temp__5753__auto__ = cljs.core.seq(seq__53211);
if(temp__5753__auto__){
var seq__53211__$1 = temp__5753__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__53211__$1)){
var c__4679__auto__ = cljs.core.chunk_first(seq__53211__$1);
var G__53303 = cljs.core.chunk_rest(seq__53211__$1);
var G__53304 = c__4679__auto__;
var G__53305 = cljs.core.count(c__4679__auto__);
var G__53306 = (0);
seq__53211 = G__53303;
chunk__53212 = G__53304;
count__53213 = G__53305;
i__53214 = G__53306;
continue;
} else {
var event = cljs.core.first(seq__53211__$1);
clear_event(event);


var G__53307 = cljs.core.next(seq__53211__$1);
var G__53308 = null;
var G__53309 = (0);
var G__53310 = (0);
seq__53211 = G__53307;
chunk__53212 = G__53308;
count__53213 = G__53309;
i__53214 = G__53310;
continue;
}
} else {
return null;
}
}
break;
}
} else {
return clear_event(value);
}
}));
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"db","db",993250759),(function (value){
if((!((cljs.core.deref(re_frame.db.app_db) === value)))){
return cljs.core.reset_BANG_(re_frame.db.app_db,value);
} else {
return null;
}
}));

//# sourceMappingURL=re_frame.fx.js.map
