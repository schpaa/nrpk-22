goog.provide('cljs.core.async');
goog.scope(function(){
  cljs.core.async.goog$module$goog$array = goog.module.get('goog.array');
});
cljs.core.async.fn_handler = (function cljs$core$async$fn_handler(var_args){
var G__65631 = arguments.length;
switch (G__65631) {
case 1:
return cljs.core.async.fn_handler.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljs.core.async.fn_handler.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.fn_handler.cljs$core$IFn$_invoke$arity$1 = (function (f){
return cljs.core.async.fn_handler.cljs$core$IFn$_invoke$arity$2(f,true);
}));

(cljs.core.async.fn_handler.cljs$core$IFn$_invoke$arity$2 = (function (f,blockable){
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async65635 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.impl.protocols.Handler}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async65635 = (function (f,blockable,meta65636){
this.f = f;
this.blockable = blockable;
this.meta65636 = meta65636;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async65635.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_65637,meta65636__$1){
var self__ = this;
var _65637__$1 = this;
return (new cljs.core.async.t_cljs$core$async65635(self__.f,self__.blockable,meta65636__$1));
}));

(cljs.core.async.t_cljs$core$async65635.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_65637){
var self__ = this;
var _65637__$1 = this;
return self__.meta65636;
}));

(cljs.core.async.t_cljs$core$async65635.prototype.cljs$core$async$impl$protocols$Handler$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async65635.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return true;
}));

(cljs.core.async.t_cljs$core$async65635.prototype.cljs$core$async$impl$protocols$Handler$blockable_QMARK_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return self__.blockable;
}));

(cljs.core.async.t_cljs$core$async65635.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return self__.f;
}));

(cljs.core.async.t_cljs$core$async65635.getBasis = (function (){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"f","f",43394975,null),new cljs.core.Symbol(null,"blockable","blockable",-28395259,null),new cljs.core.Symbol(null,"meta65636","meta65636",1527807563,null)], null);
}));

(cljs.core.async.t_cljs$core$async65635.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async65635.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async65635");

(cljs.core.async.t_cljs$core$async65635.cljs$lang$ctorPrWriter = (function (this__5327__auto__,writer__5328__auto__,opt__5329__auto__){
return cljs.core._write(writer__5328__auto__,"cljs.core.async/t_cljs$core$async65635");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async65635.
 */
cljs.core.async.__GT_t_cljs$core$async65635 = (function cljs$core$async$__GT_t_cljs$core$async65635(f__$1,blockable__$1,meta65636){
return (new cljs.core.async.t_cljs$core$async65635(f__$1,blockable__$1,meta65636));
});

}

return (new cljs.core.async.t_cljs$core$async65635(f,blockable,cljs.core.PersistentArrayMap.EMPTY));
}));

(cljs.core.async.fn_handler.cljs$lang$maxFixedArity = 2);

/**
 * Returns a fixed buffer of size n. When full, puts will block/park.
 */
cljs.core.async.buffer = (function cljs$core$async$buffer(n){
return cljs.core.async.impl.buffers.fixed_buffer(n);
});
/**
 * Returns a buffer of size n. When full, puts will complete but
 *   val will be dropped (no transfer).
 */
cljs.core.async.dropping_buffer = (function cljs$core$async$dropping_buffer(n){
return cljs.core.async.impl.buffers.dropping_buffer(n);
});
/**
 * Returns a buffer of size n. When full, puts will complete, and be
 *   buffered, but oldest elements in buffer will be dropped (not
 *   transferred).
 */
cljs.core.async.sliding_buffer = (function cljs$core$async$sliding_buffer(n){
return cljs.core.async.impl.buffers.sliding_buffer(n);
});
/**
 * Returns true if a channel created with buff will never block. That is to say,
 * puts into this buffer will never cause the buffer to be full. 
 */
cljs.core.async.unblocking_buffer_QMARK_ = (function cljs$core$async$unblocking_buffer_QMARK_(buff){
if((!((buff == null)))){
if(((false) || ((cljs.core.PROTOCOL_SENTINEL === buff.cljs$core$async$impl$protocols$UnblockingBuffer$)))){
return true;
} else {
if((!buff.cljs$lang$protocol_mask$partition$)){
return cljs.core.native_satisfies_QMARK_(cljs.core.async.impl.protocols.UnblockingBuffer,buff);
} else {
return false;
}
}
} else {
return cljs.core.native_satisfies_QMARK_(cljs.core.async.impl.protocols.UnblockingBuffer,buff);
}
});
/**
 * Creates a channel with an optional buffer, an optional transducer (like (map f),
 *   (filter p) etc or a composition thereof), and an optional exception handler.
 *   If buf-or-n is a number, will create and use a fixed buffer of that size. If a
 *   transducer is supplied a buffer must be specified. ex-handler must be a
 *   fn of one argument - if an exception occurs during transformation it will be called
 *   with the thrown value as an argument, and any non-nil return value will be placed
 *   in the channel.
 */
cljs.core.async.chan = (function cljs$core$async$chan(var_args){
var G__65651 = arguments.length;
switch (G__65651) {
case 0:
return cljs.core.async.chan.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljs.core.async.chan.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljs.core.async.chan.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.chan.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(null);
}));

(cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1 = (function (buf_or_n){
return cljs.core.async.chan.cljs$core$IFn$_invoke$arity$3(buf_or_n,null,null);
}));

(cljs.core.async.chan.cljs$core$IFn$_invoke$arity$2 = (function (buf_or_n,xform){
return cljs.core.async.chan.cljs$core$IFn$_invoke$arity$3(buf_or_n,xform,null);
}));

(cljs.core.async.chan.cljs$core$IFn$_invoke$arity$3 = (function (buf_or_n,xform,ex_handler){
var buf_or_n__$1 = ((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(buf_or_n,(0)))?null:buf_or_n);
if(cljs.core.truth_(xform)){
if(cljs.core.truth_(buf_or_n__$1)){
} else {
throw (new Error(["Assert failed: ","buffer must be supplied when transducer is","\n","buf-or-n"].join('')));
}
} else {
}

return cljs.core.async.impl.channels.chan.cljs$core$IFn$_invoke$arity$3(((typeof buf_or_n__$1 === 'number')?cljs.core.async.buffer(buf_or_n__$1):buf_or_n__$1),xform,ex_handler);
}));

(cljs.core.async.chan.cljs$lang$maxFixedArity = 3);

/**
 * Creates a promise channel with an optional transducer, and an optional
 *   exception-handler. A promise channel can take exactly one value that consumers
 *   will receive. Once full, puts complete but val is dropped (no transfer).
 *   Consumers will block until either a value is placed in the channel or the
 *   channel is closed. See chan for the semantics of xform and ex-handler.
 */
cljs.core.async.promise_chan = (function cljs$core$async$promise_chan(var_args){
var G__65656 = arguments.length;
switch (G__65656) {
case 0:
return cljs.core.async.promise_chan.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return cljs.core.async.promise_chan.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljs.core.async.promise_chan.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.promise_chan.cljs$core$IFn$_invoke$arity$0 = (function (){
return cljs.core.async.promise_chan.cljs$core$IFn$_invoke$arity$1(null);
}));

(cljs.core.async.promise_chan.cljs$core$IFn$_invoke$arity$1 = (function (xform){
return cljs.core.async.promise_chan.cljs$core$IFn$_invoke$arity$2(xform,null);
}));

(cljs.core.async.promise_chan.cljs$core$IFn$_invoke$arity$2 = (function (xform,ex_handler){
return cljs.core.async.chan.cljs$core$IFn$_invoke$arity$3(cljs.core.async.impl.buffers.promise_buffer(),xform,ex_handler);
}));

(cljs.core.async.promise_chan.cljs$lang$maxFixedArity = 2);

/**
 * Returns a channel that will close after msecs
 */
cljs.core.async.timeout = (function cljs$core$async$timeout(msecs){
return cljs.core.async.impl.timers.timeout(msecs);
});
/**
 * takes a val from port. Must be called inside a (go ...) block. Will
 *   return nil if closed. Will park if nothing is available.
 *   Returns true unless port is already closed
 */
cljs.core.async._LT__BANG_ = (function cljs$core$async$_LT__BANG_(port){
throw (new Error("<! used not in (go ...) block"));
});
/**
 * Asynchronously takes a val from port, passing to fn1. Will pass nil
 * if closed. If on-caller? (default true) is true, and value is
 * immediately available, will call fn1 on calling thread.
 * Returns nil.
 */
cljs.core.async.take_BANG_ = (function cljs$core$async$take_BANG_(var_args){
var G__65706 = arguments.length;
switch (G__65706) {
case 2:
return cljs.core.async.take_BANG_.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljs.core.async.take_BANG_.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.take_BANG_.cljs$core$IFn$_invoke$arity$2 = (function (port,fn1){
return cljs.core.async.take_BANG_.cljs$core$IFn$_invoke$arity$3(port,fn1,true);
}));

(cljs.core.async.take_BANG_.cljs$core$IFn$_invoke$arity$3 = (function (port,fn1,on_caller_QMARK_){
var ret = cljs.core.async.impl.protocols.take_BANG_(port,cljs.core.async.fn_handler.cljs$core$IFn$_invoke$arity$1(fn1));
if(cljs.core.truth_(ret)){
var val_67892 = cljs.core.deref(ret);
if(cljs.core.truth_(on_caller_QMARK_)){
(fn1.cljs$core$IFn$_invoke$arity$1 ? fn1.cljs$core$IFn$_invoke$arity$1(val_67892) : fn1.call(null,val_67892));
} else {
cljs.core.async.impl.dispatch.run((function (){
return (fn1.cljs$core$IFn$_invoke$arity$1 ? fn1.cljs$core$IFn$_invoke$arity$1(val_67892) : fn1.call(null,val_67892));
}));
}
} else {
}

return null;
}));

(cljs.core.async.take_BANG_.cljs$lang$maxFixedArity = 3);

cljs.core.async.nop = (function cljs$core$async$nop(_){
return null;
});
cljs.core.async.fhnop = cljs.core.async.fn_handler.cljs$core$IFn$_invoke$arity$1(cljs.core.async.nop);
/**
 * puts a val into port. nil values are not allowed. Must be called
 *   inside a (go ...) block. Will park if no buffer space is available.
 *   Returns true unless port is already closed.
 */
cljs.core.async._GT__BANG_ = (function cljs$core$async$_GT__BANG_(port,val){
throw (new Error(">! used not in (go ...) block"));
});
/**
 * Asynchronously puts a val into port, calling fn1 (if supplied) when
 * complete. nil values are not allowed. Will throw if closed. If
 * on-caller? (default true) is true, and the put is immediately
 * accepted, will call fn1 on calling thread.  Returns nil.
 */
cljs.core.async.put_BANG_ = (function cljs$core$async$put_BANG_(var_args){
var G__65718 = arguments.length;
switch (G__65718) {
case 2:
return cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 4:
return cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$4((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$2 = (function (port,val){
var temp__5751__auto__ = cljs.core.async.impl.protocols.put_BANG_(port,val,cljs.core.async.fhnop);
if(cljs.core.truth_(temp__5751__auto__)){
var ret = temp__5751__auto__;
return cljs.core.deref(ret);
} else {
return true;
}
}));

(cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$3 = (function (port,val,fn1){
return cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$4(port,val,fn1,true);
}));

(cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$4 = (function (port,val,fn1,on_caller_QMARK_){
var temp__5751__auto__ = cljs.core.async.impl.protocols.put_BANG_(port,val,cljs.core.async.fn_handler.cljs$core$IFn$_invoke$arity$1(fn1));
if(cljs.core.truth_(temp__5751__auto__)){
var retb = temp__5751__auto__;
var ret = cljs.core.deref(retb);
if(cljs.core.truth_(on_caller_QMARK_)){
(fn1.cljs$core$IFn$_invoke$arity$1 ? fn1.cljs$core$IFn$_invoke$arity$1(ret) : fn1.call(null,ret));
} else {
cljs.core.async.impl.dispatch.run((function (){
return (fn1.cljs$core$IFn$_invoke$arity$1 ? fn1.cljs$core$IFn$_invoke$arity$1(ret) : fn1.call(null,ret));
}));
}

return ret;
} else {
return true;
}
}));

(cljs.core.async.put_BANG_.cljs$lang$maxFixedArity = 4);

cljs.core.async.close_BANG_ = (function cljs$core$async$close_BANG_(port){
return cljs.core.async.impl.protocols.close_BANG_(port);
});
cljs.core.async.random_array = (function cljs$core$async$random_array(n){
var a = (new Array(n));
var n__5633__auto___67899 = n;
var x_67900 = (0);
while(true){
if((x_67900 < n__5633__auto___67899)){
(a[x_67900] = x_67900);

var G__67901 = (x_67900 + (1));
x_67900 = G__67901;
continue;
} else {
}
break;
}

cljs.core.async.goog$module$goog$array.shuffle(a);

return a;
});
cljs.core.async.alt_flag = (function cljs$core$async$alt_flag(){
var flag = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(true);
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async65734 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.impl.protocols.Handler}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async65734 = (function (flag,meta65735){
this.flag = flag;
this.meta65735 = meta65735;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async65734.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_65736,meta65735__$1){
var self__ = this;
var _65736__$1 = this;
return (new cljs.core.async.t_cljs$core$async65734(self__.flag,meta65735__$1));
}));

(cljs.core.async.t_cljs$core$async65734.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_65736){
var self__ = this;
var _65736__$1 = this;
return self__.meta65735;
}));

(cljs.core.async.t_cljs$core$async65734.prototype.cljs$core$async$impl$protocols$Handler$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async65734.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.deref(self__.flag);
}));

(cljs.core.async.t_cljs$core$async65734.prototype.cljs$core$async$impl$protocols$Handler$blockable_QMARK_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return true;
}));

(cljs.core.async.t_cljs$core$async65734.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
cljs.core.reset_BANG_(self__.flag,null);

return true;
}));

(cljs.core.async.t_cljs$core$async65734.getBasis = (function (){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"flag","flag",-1565787888,null),new cljs.core.Symbol(null,"meta65735","meta65735",838808480,null)], null);
}));

(cljs.core.async.t_cljs$core$async65734.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async65734.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async65734");

(cljs.core.async.t_cljs$core$async65734.cljs$lang$ctorPrWriter = (function (this__5327__auto__,writer__5328__auto__,opt__5329__auto__){
return cljs.core._write(writer__5328__auto__,"cljs.core.async/t_cljs$core$async65734");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async65734.
 */
cljs.core.async.__GT_t_cljs$core$async65734 = (function cljs$core$async$alt_flag_$___GT_t_cljs$core$async65734(flag__$1,meta65735){
return (new cljs.core.async.t_cljs$core$async65734(flag__$1,meta65735));
});

}

return (new cljs.core.async.t_cljs$core$async65734(flag,cljs.core.PersistentArrayMap.EMPTY));
});
cljs.core.async.alt_handler = (function cljs$core$async$alt_handler(flag,cb){
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async65761 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.impl.protocols.Handler}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async65761 = (function (flag,cb,meta65762){
this.flag = flag;
this.cb = cb;
this.meta65762 = meta65762;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async65761.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_65763,meta65762__$1){
var self__ = this;
var _65763__$1 = this;
return (new cljs.core.async.t_cljs$core$async65761(self__.flag,self__.cb,meta65762__$1));
}));

(cljs.core.async.t_cljs$core$async65761.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_65763){
var self__ = this;
var _65763__$1 = this;
return self__.meta65762;
}));

(cljs.core.async.t_cljs$core$async65761.prototype.cljs$core$async$impl$protocols$Handler$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async65761.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.active_QMARK_(self__.flag);
}));

(cljs.core.async.t_cljs$core$async65761.prototype.cljs$core$async$impl$protocols$Handler$blockable_QMARK_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return true;
}));

(cljs.core.async.t_cljs$core$async65761.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
cljs.core.async.impl.protocols.commit(self__.flag);

return self__.cb;
}));

(cljs.core.async.t_cljs$core$async65761.getBasis = (function (){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"flag","flag",-1565787888,null),new cljs.core.Symbol(null,"cb","cb",-2064487928,null),new cljs.core.Symbol(null,"meta65762","meta65762",-547752669,null)], null);
}));

(cljs.core.async.t_cljs$core$async65761.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async65761.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async65761");

(cljs.core.async.t_cljs$core$async65761.cljs$lang$ctorPrWriter = (function (this__5327__auto__,writer__5328__auto__,opt__5329__auto__){
return cljs.core._write(writer__5328__auto__,"cljs.core.async/t_cljs$core$async65761");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async65761.
 */
cljs.core.async.__GT_t_cljs$core$async65761 = (function cljs$core$async$alt_handler_$___GT_t_cljs$core$async65761(flag__$1,cb__$1,meta65762){
return (new cljs.core.async.t_cljs$core$async65761(flag__$1,cb__$1,meta65762));
});

}

return (new cljs.core.async.t_cljs$core$async65761(flag,cb,cljs.core.PersistentArrayMap.EMPTY));
});
/**
 * returns derefable [val port] if immediate, nil if enqueued
 */
cljs.core.async.do_alts = (function cljs$core$async$do_alts(fret,ports,opts){
if((cljs.core.count(ports) > (0))){
} else {
throw (new Error(["Assert failed: ","alts must have at least one channel operation","\n","(pos? (count ports))"].join('')));
}

var flag = cljs.core.async.alt_flag();
var n = cljs.core.count(ports);
var idxs = cljs.core.async.random_array(n);
var priority = new cljs.core.Keyword(null,"priority","priority",1431093715).cljs$core$IFn$_invoke$arity$1(opts);
var ret = (function (){var i = (0);
while(true){
if((i < n)){
var idx = (cljs.core.truth_(priority)?i:(idxs[i]));
var port = cljs.core.nth.cljs$core$IFn$_invoke$arity$2(ports,idx);
var wport = ((cljs.core.vector_QMARK_(port))?(port.cljs$core$IFn$_invoke$arity$1 ? port.cljs$core$IFn$_invoke$arity$1((0)) : port.call(null,(0))):null);
var vbox = (cljs.core.truth_(wport)?(function (){var val = (port.cljs$core$IFn$_invoke$arity$1 ? port.cljs$core$IFn$_invoke$arity$1((1)) : port.call(null,(1)));
return cljs.core.async.impl.protocols.put_BANG_(wport,val,cljs.core.async.alt_handler(flag,((function (i,val,idx,port,wport,flag,n,idxs,priority){
return (function (p1__65783_SHARP_){
var G__65789 = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [p1__65783_SHARP_,wport], null);
return (fret.cljs$core$IFn$_invoke$arity$1 ? fret.cljs$core$IFn$_invoke$arity$1(G__65789) : fret.call(null,G__65789));
});})(i,val,idx,port,wport,flag,n,idxs,priority))
));
})():cljs.core.async.impl.protocols.take_BANG_(port,cljs.core.async.alt_handler(flag,((function (i,idx,port,wport,flag,n,idxs,priority){
return (function (p1__65784_SHARP_){
var G__65790 = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [p1__65784_SHARP_,port], null);
return (fret.cljs$core$IFn$_invoke$arity$1 ? fret.cljs$core$IFn$_invoke$arity$1(G__65790) : fret.call(null,G__65790));
});})(i,idx,port,wport,flag,n,idxs,priority))
)));
if(cljs.core.truth_(vbox)){
return cljs.core.async.impl.channels.box(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [cljs.core.deref(vbox),(function (){var or__5043__auto__ = wport;
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
return port;
}
})()], null));
} else {
var G__67905 = (i + (1));
i = G__67905;
continue;
}
} else {
return null;
}
break;
}
})();
var or__5043__auto__ = ret;
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
if(cljs.core.contains_QMARK_(opts,new cljs.core.Keyword(null,"default","default",-1987822328))){
var temp__5753__auto__ = (function (){var and__5041__auto__ = flag.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1(null);
if(cljs.core.truth_(and__5041__auto__)){
return flag.cljs$core$async$impl$protocols$Handler$commit$arity$1(null);
} else {
return and__5041__auto__;
}
})();
if(cljs.core.truth_(temp__5753__auto__)){
var got = temp__5753__auto__;
return cljs.core.async.impl.channels.box(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"default","default",-1987822328).cljs$core$IFn$_invoke$arity$1(opts),new cljs.core.Keyword(null,"default","default",-1987822328)], null));
} else {
return null;
}
} else {
return null;
}
}
});
/**
 * Completes at most one of several channel operations. Must be called
 * inside a (go ...) block. ports is a vector of channel endpoints,
 * which can be either a channel to take from or a vector of
 *   [channel-to-put-to val-to-put], in any combination. Takes will be
 *   made as if by <!, and puts will be made as if by >!. Unless
 *   the :priority option is true, if more than one port operation is
 *   ready a non-deterministic choice will be made. If no operation is
 *   ready and a :default value is supplied, [default-val :default] will
 *   be returned, otherwise alts! will park until the first operation to
 *   become ready completes. Returns [val port] of the completed
 *   operation, where val is the value taken for takes, and a
 *   boolean (true unless already closed, as per put!) for puts.
 * 
 *   opts are passed as :key val ... Supported options:
 * 
 *   :default val - the value to use if none of the operations are immediately ready
 *   :priority true - (default nil) when true, the operations will be tried in order.
 * 
 *   Note: there is no guarantee that the port exps or val exprs will be
 *   used, nor in what order should they be, so they should not be
 *   depended upon for side effects.
 */
cljs.core.async.alts_BANG_ = (function cljs$core$async$alts_BANG_(var_args){
var args__5772__auto__ = [];
var len__5766__auto___67906 = arguments.length;
var i__5767__auto___67907 = (0);
while(true){
if((i__5767__auto___67907 < len__5766__auto___67906)){
args__5772__auto__.push((arguments[i__5767__auto___67907]));

var G__67908 = (i__5767__auto___67907 + (1));
i__5767__auto___67907 = G__67908;
continue;
} else {
}
break;
}

var argseq__5773__auto__ = ((((1) < args__5772__auto__.length))?(new cljs.core.IndexedSeq(args__5772__auto__.slice((1)),(0),null)):null);
return cljs.core.async.alts_BANG_.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__5773__auto__);
});

(cljs.core.async.alts_BANG_.cljs$core$IFn$_invoke$arity$variadic = (function (ports,p__65793){
var map__65794 = p__65793;
var map__65794__$1 = cljs.core.__destructure_map(map__65794);
var opts = map__65794__$1;
throw (new Error("alts! used not in (go ...) block"));
}));

(cljs.core.async.alts_BANG_.cljs$lang$maxFixedArity = (1));

/** @this {Function} */
(cljs.core.async.alts_BANG_.cljs$lang$applyTo = (function (seq65791){
var G__65792 = cljs.core.first(seq65791);
var seq65791__$1 = cljs.core.next(seq65791);
var self__5751__auto__ = this;
return self__5751__auto__.cljs$core$IFn$_invoke$arity$variadic(G__65792,seq65791__$1);
}));

/**
 * Puts a val into port if it's possible to do so immediately.
 *   nil values are not allowed. Never blocks. Returns true if offer succeeds.
 */
cljs.core.async.offer_BANG_ = (function cljs$core$async$offer_BANG_(port,val){
var ret = cljs.core.async.impl.protocols.put_BANG_(port,val,cljs.core.async.fn_handler.cljs$core$IFn$_invoke$arity$2(cljs.core.async.nop,false));
if(cljs.core.truth_(ret)){
return cljs.core.deref(ret);
} else {
return null;
}
});
/**
 * Takes a val from port if it's possible to do so immediately.
 *   Never blocks. Returns value if successful, nil otherwise.
 */
cljs.core.async.poll_BANG_ = (function cljs$core$async$poll_BANG_(port){
var ret = cljs.core.async.impl.protocols.take_BANG_(port,cljs.core.async.fn_handler.cljs$core$IFn$_invoke$arity$2(cljs.core.async.nop,false));
if(cljs.core.truth_(ret)){
return cljs.core.deref(ret);
} else {
return null;
}
});
/**
 * Takes elements from the from channel and supplies them to the to
 * channel. By default, the to channel will be closed when the from
 * channel closes, but can be determined by the close?  parameter. Will
 * stop consuming the from channel if the to channel closes
 */
cljs.core.async.pipe = (function cljs$core$async$pipe(var_args){
var G__65797 = arguments.length;
switch (G__65797) {
case 2:
return cljs.core.async.pipe.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljs.core.async.pipe.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.pipe.cljs$core$IFn$_invoke$arity$2 = (function (from,to){
return cljs.core.async.pipe.cljs$core$IFn$_invoke$arity$3(from,to,true);
}));

(cljs.core.async.pipe.cljs$core$IFn$_invoke$arity$3 = (function (from,to,close_QMARK_){
var c__35508__auto___67920 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_65848){
var state_val_65849 = (state_65848[(1)]);
if((state_val_65849 === (7))){
var inst_65844 = (state_65848[(2)]);
var state_65848__$1 = state_65848;
var statearr_65850_67922 = state_65848__$1;
(statearr_65850_67922[(2)] = inst_65844);

(statearr_65850_67922[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65849 === (1))){
var state_65848__$1 = state_65848;
var statearr_65851_67923 = state_65848__$1;
(statearr_65851_67923[(2)] = null);

(statearr_65851_67923[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65849 === (4))){
var inst_65824 = (state_65848[(7)]);
var inst_65824__$1 = (state_65848[(2)]);
var inst_65828 = (inst_65824__$1 == null);
var state_65848__$1 = (function (){var statearr_65852 = state_65848;
(statearr_65852[(7)] = inst_65824__$1);

return statearr_65852;
})();
if(cljs.core.truth_(inst_65828)){
var statearr_65853_67924 = state_65848__$1;
(statearr_65853_67924[(1)] = (5));

} else {
var statearr_65854_67925 = state_65848__$1;
(statearr_65854_67925[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65849 === (13))){
var state_65848__$1 = state_65848;
var statearr_65855_67926 = state_65848__$1;
(statearr_65855_67926[(2)] = null);

(statearr_65855_67926[(1)] = (14));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65849 === (6))){
var inst_65824 = (state_65848[(7)]);
var state_65848__$1 = state_65848;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_65848__$1,(11),to,inst_65824);
} else {
if((state_val_65849 === (3))){
var inst_65846 = (state_65848[(2)]);
var state_65848__$1 = state_65848;
return cljs.core.async.impl.ioc_helpers.return_chan(state_65848__$1,inst_65846);
} else {
if((state_val_65849 === (12))){
var state_65848__$1 = state_65848;
var statearr_65856_67927 = state_65848__$1;
(statearr_65856_67927[(2)] = null);

(statearr_65856_67927[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65849 === (2))){
var state_65848__$1 = state_65848;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_65848__$1,(4),from);
} else {
if((state_val_65849 === (11))){
var inst_65837 = (state_65848[(2)]);
var state_65848__$1 = state_65848;
if(cljs.core.truth_(inst_65837)){
var statearr_65857_67928 = state_65848__$1;
(statearr_65857_67928[(1)] = (12));

} else {
var statearr_65858_67929 = state_65848__$1;
(statearr_65858_67929[(1)] = (13));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65849 === (9))){
var state_65848__$1 = state_65848;
var statearr_65863_67930 = state_65848__$1;
(statearr_65863_67930[(2)] = null);

(statearr_65863_67930[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65849 === (5))){
var state_65848__$1 = state_65848;
if(cljs.core.truth_(close_QMARK_)){
var statearr_65864_67932 = state_65848__$1;
(statearr_65864_67932[(1)] = (8));

} else {
var statearr_65865_67933 = state_65848__$1;
(statearr_65865_67933[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65849 === (14))){
var inst_65842 = (state_65848[(2)]);
var state_65848__$1 = state_65848;
var statearr_65866_67934 = state_65848__$1;
(statearr_65866_67934[(2)] = inst_65842);

(statearr_65866_67934[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65849 === (10))){
var inst_65834 = (state_65848[(2)]);
var state_65848__$1 = state_65848;
var statearr_65867_67936 = state_65848__$1;
(statearr_65867_67936[(2)] = inst_65834);

(statearr_65867_67936[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65849 === (8))){
var inst_65831 = cljs.core.async.close_BANG_(to);
var state_65848__$1 = state_65848;
var statearr_65868_67937 = state_65848__$1;
(statearr_65868_67937[(2)] = inst_65831);

(statearr_65868_67937[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$state_machine__35424__auto__ = null;
var cljs$core$async$state_machine__35424__auto____0 = (function (){
var statearr_65869 = [null,null,null,null,null,null,null,null];
(statearr_65869[(0)] = cljs$core$async$state_machine__35424__auto__);

(statearr_65869[(1)] = (1));

return statearr_65869;
});
var cljs$core$async$state_machine__35424__auto____1 = (function (state_65848){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_65848);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e65870){var ex__35427__auto__ = e65870;
var statearr_65871_67939 = state_65848;
(statearr_65871_67939[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_65848[(4)]))){
var statearr_65872_67940 = state_65848;
(statearr_65872_67940[(1)] = cljs.core.first((state_65848[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__67941 = state_65848;
state_65848 = G__67941;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$state_machine__35424__auto__ = function(state_65848){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__35424__auto____1.call(this,state_65848);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__35424__auto____0;
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__35424__auto____1;
return cljs$core$async$state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_65873 = f__35509__auto__();
(statearr_65873[(6)] = c__35508__auto___67920);

return statearr_65873;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));


return to;
}));

(cljs.core.async.pipe.cljs$lang$maxFixedArity = 3);

cljs.core.async.pipeline_STAR_ = (function cljs$core$async$pipeline_STAR_(n,to,xf,from,close_QMARK_,ex_handler,type){
if((n > (0))){
} else {
throw (new Error("Assert failed: (pos? n)"));
}

var jobs = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(n);
var results = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(n);
var process__$1 = (function (p__65874){
var vec__65875 = p__65874;
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__65875,(0),null);
var p = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__65875,(1),null);
var job = vec__65875;
if((job == null)){
cljs.core.async.close_BANG_(results);

return null;
} else {
var res = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$3((1),xf,ex_handler);
var c__35508__auto___67946 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_65882){
var state_val_65883 = (state_65882[(1)]);
if((state_val_65883 === (1))){
var state_65882__$1 = state_65882;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_65882__$1,(2),res,v);
} else {
if((state_val_65883 === (2))){
var inst_65879 = (state_65882[(2)]);
var inst_65880 = cljs.core.async.close_BANG_(res);
var state_65882__$1 = (function (){var statearr_65884 = state_65882;
(statearr_65884[(7)] = inst_65879);

return statearr_65884;
})();
return cljs.core.async.impl.ioc_helpers.return_chan(state_65882__$1,inst_65880);
} else {
return null;
}
}
});
return (function() {
var cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__ = null;
var cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____0 = (function (){
var statearr_65886 = [null,null,null,null,null,null,null,null];
(statearr_65886[(0)] = cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__);

(statearr_65886[(1)] = (1));

return statearr_65886;
});
var cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____1 = (function (state_65882){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_65882);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e65888){var ex__35427__auto__ = e65888;
var statearr_65889_67949 = state_65882;
(statearr_65889_67949[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_65882[(4)]))){
var statearr_65890_67950 = state_65882;
(statearr_65890_67950[(1)] = cljs.core.first((state_65882[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__67951 = state_65882;
state_65882 = G__67951;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__ = function(state_65882){
switch(arguments.length){
case 0:
return cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____1.call(this,state_65882);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____0;
cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____1;
return cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_65891 = f__35509__auto__();
(statearr_65891[(6)] = c__35508__auto___67946);

return statearr_65891;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));


cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$2(p,res);

return true;
}
});
var async = (function (p__65892){
var vec__65893 = p__65892;
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__65893,(0),null);
var p = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__65893,(1),null);
var job = vec__65893;
if((job == null)){
cljs.core.async.close_BANG_(results);

return null;
} else {
var res = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
(xf.cljs$core$IFn$_invoke$arity$2 ? xf.cljs$core$IFn$_invoke$arity$2(v,res) : xf.call(null,v,res));

cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$2(p,res);

return true;
}
});
var n__5633__auto___67952 = n;
var __67953 = (0);
while(true){
if((__67953 < n__5633__auto___67952)){
var G__65896_67954 = type;
var G__65896_67955__$1 = (((G__65896_67954 instanceof cljs.core.Keyword))?G__65896_67954.fqn:null);
switch (G__65896_67955__$1) {
case "compute":
var c__35508__auto___67957 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run(((function (__67953,c__35508__auto___67957,G__65896_67954,G__65896_67955__$1,n__5633__auto___67952,jobs,results,process__$1,async){
return (function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = ((function (__67953,c__35508__auto___67957,G__65896_67954,G__65896_67955__$1,n__5633__auto___67952,jobs,results,process__$1,async){
return (function (state_65909){
var state_val_65910 = (state_65909[(1)]);
if((state_val_65910 === (1))){
var state_65909__$1 = state_65909;
var statearr_65911_67958 = state_65909__$1;
(statearr_65911_67958[(2)] = null);

(statearr_65911_67958[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65910 === (2))){
var state_65909__$1 = state_65909;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_65909__$1,(4),jobs);
} else {
if((state_val_65910 === (3))){
var inst_65907 = (state_65909[(2)]);
var state_65909__$1 = state_65909;
return cljs.core.async.impl.ioc_helpers.return_chan(state_65909__$1,inst_65907);
} else {
if((state_val_65910 === (4))){
var inst_65899 = (state_65909[(2)]);
var inst_65900 = process__$1(inst_65899);
var state_65909__$1 = state_65909;
if(cljs.core.truth_(inst_65900)){
var statearr_65912_67959 = state_65909__$1;
(statearr_65912_67959[(1)] = (5));

} else {
var statearr_65913_67960 = state_65909__$1;
(statearr_65913_67960[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65910 === (5))){
var state_65909__$1 = state_65909;
var statearr_65914_67961 = state_65909__$1;
(statearr_65914_67961[(2)] = null);

(statearr_65914_67961[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65910 === (6))){
var state_65909__$1 = state_65909;
var statearr_65915_67962 = state_65909__$1;
(statearr_65915_67962[(2)] = null);

(statearr_65915_67962[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65910 === (7))){
var inst_65905 = (state_65909[(2)]);
var state_65909__$1 = state_65909;
var statearr_65916_67963 = state_65909__$1;
(statearr_65916_67963[(2)] = inst_65905);

(statearr_65916_67963[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}
}
}
}
}
}
});})(__67953,c__35508__auto___67957,G__65896_67954,G__65896_67955__$1,n__5633__auto___67952,jobs,results,process__$1,async))
;
return ((function (__67953,switch__35423__auto__,c__35508__auto___67957,G__65896_67954,G__65896_67955__$1,n__5633__auto___67952,jobs,results,process__$1,async){
return (function() {
var cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__ = null;
var cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____0 = (function (){
var statearr_65917 = [null,null,null,null,null,null,null];
(statearr_65917[(0)] = cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__);

(statearr_65917[(1)] = (1));

return statearr_65917;
});
var cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____1 = (function (state_65909){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_65909);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e65918){var ex__35427__auto__ = e65918;
var statearr_65919_67965 = state_65909;
(statearr_65919_67965[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_65909[(4)]))){
var statearr_65920_67967 = state_65909;
(statearr_65920_67967[(1)] = cljs.core.first((state_65909[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__67968 = state_65909;
state_65909 = G__67968;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__ = function(state_65909){
switch(arguments.length){
case 0:
return cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____1.call(this,state_65909);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____0;
cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____1;
return cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__;
})()
;})(__67953,switch__35423__auto__,c__35508__auto___67957,G__65896_67954,G__65896_67955__$1,n__5633__auto___67952,jobs,results,process__$1,async))
})();
var state__35510__auto__ = (function (){var statearr_65921 = f__35509__auto__();
(statearr_65921[(6)] = c__35508__auto___67957);

return statearr_65921;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
});})(__67953,c__35508__auto___67957,G__65896_67954,G__65896_67955__$1,n__5633__auto___67952,jobs,results,process__$1,async))
);


break;
case "async":
var c__35508__auto___67969 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run(((function (__67953,c__35508__auto___67969,G__65896_67954,G__65896_67955__$1,n__5633__auto___67952,jobs,results,process__$1,async){
return (function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = ((function (__67953,c__35508__auto___67969,G__65896_67954,G__65896_67955__$1,n__5633__auto___67952,jobs,results,process__$1,async){
return (function (state_65938){
var state_val_65939 = (state_65938[(1)]);
if((state_val_65939 === (1))){
var state_65938__$1 = state_65938;
var statearr_65940_67970 = state_65938__$1;
(statearr_65940_67970[(2)] = null);

(statearr_65940_67970[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65939 === (2))){
var state_65938__$1 = state_65938;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_65938__$1,(4),jobs);
} else {
if((state_val_65939 === (3))){
var inst_65936 = (state_65938[(2)]);
var state_65938__$1 = state_65938;
return cljs.core.async.impl.ioc_helpers.return_chan(state_65938__$1,inst_65936);
} else {
if((state_val_65939 === (4))){
var inst_65928 = (state_65938[(2)]);
var inst_65929 = async(inst_65928);
var state_65938__$1 = state_65938;
if(cljs.core.truth_(inst_65929)){
var statearr_65941_67971 = state_65938__$1;
(statearr_65941_67971[(1)] = (5));

} else {
var statearr_65942_67972 = state_65938__$1;
(statearr_65942_67972[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65939 === (5))){
var state_65938__$1 = state_65938;
var statearr_65943_67973 = state_65938__$1;
(statearr_65943_67973[(2)] = null);

(statearr_65943_67973[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65939 === (6))){
var state_65938__$1 = state_65938;
var statearr_65944_67974 = state_65938__$1;
(statearr_65944_67974[(2)] = null);

(statearr_65944_67974[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65939 === (7))){
var inst_65934 = (state_65938[(2)]);
var state_65938__$1 = state_65938;
var statearr_65945_67975 = state_65938__$1;
(statearr_65945_67975[(2)] = inst_65934);

(statearr_65945_67975[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}
}
}
}
}
}
});})(__67953,c__35508__auto___67969,G__65896_67954,G__65896_67955__$1,n__5633__auto___67952,jobs,results,process__$1,async))
;
return ((function (__67953,switch__35423__auto__,c__35508__auto___67969,G__65896_67954,G__65896_67955__$1,n__5633__auto___67952,jobs,results,process__$1,async){
return (function() {
var cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__ = null;
var cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____0 = (function (){
var statearr_65946 = [null,null,null,null,null,null,null];
(statearr_65946[(0)] = cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__);

(statearr_65946[(1)] = (1));

return statearr_65946;
});
var cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____1 = (function (state_65938){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_65938);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e65947){var ex__35427__auto__ = e65947;
var statearr_65948_67976 = state_65938;
(statearr_65948_67976[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_65938[(4)]))){
var statearr_65949_67978 = state_65938;
(statearr_65949_67978[(1)] = cljs.core.first((state_65938[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__67979 = state_65938;
state_65938 = G__67979;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__ = function(state_65938){
switch(arguments.length){
case 0:
return cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____1.call(this,state_65938);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____0;
cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____1;
return cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__;
})()
;})(__67953,switch__35423__auto__,c__35508__auto___67969,G__65896_67954,G__65896_67955__$1,n__5633__auto___67952,jobs,results,process__$1,async))
})();
var state__35510__auto__ = (function (){var statearr_65950 = f__35509__auto__();
(statearr_65950[(6)] = c__35508__auto___67969);

return statearr_65950;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
});})(__67953,c__35508__auto___67969,G__65896_67954,G__65896_67955__$1,n__5633__auto___67952,jobs,results,process__$1,async))
);


break;
default:
throw (new Error(["No matching clause: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(G__65896_67955__$1)].join('')));

}

var G__67980 = (__67953 + (1));
__67953 = G__67980;
continue;
} else {
}
break;
}

var c__35508__auto___67981 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_65976){
var state_val_65977 = (state_65976[(1)]);
if((state_val_65977 === (7))){
var inst_65968 = (state_65976[(2)]);
var state_65976__$1 = state_65976;
var statearr_65978_67982 = state_65976__$1;
(statearr_65978_67982[(2)] = inst_65968);

(statearr_65978_67982[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65977 === (1))){
var state_65976__$1 = state_65976;
var statearr_65979_67984 = state_65976__$1;
(statearr_65979_67984[(2)] = null);

(statearr_65979_67984[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65977 === (4))){
var inst_65953 = (state_65976[(7)]);
var inst_65953__$1 = (state_65976[(2)]);
var inst_65954 = (inst_65953__$1 == null);
var state_65976__$1 = (function (){var statearr_65980 = state_65976;
(statearr_65980[(7)] = inst_65953__$1);

return statearr_65980;
})();
if(cljs.core.truth_(inst_65954)){
var statearr_65981_67988 = state_65976__$1;
(statearr_65981_67988[(1)] = (5));

} else {
var statearr_65982_67989 = state_65976__$1;
(statearr_65982_67989[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65977 === (6))){
var inst_65953 = (state_65976[(7)]);
var inst_65958 = (state_65976[(8)]);
var inst_65958__$1 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
var inst_65959 = cljs.core.PersistentVector.EMPTY_NODE;
var inst_65960 = [inst_65953,inst_65958__$1];
var inst_65961 = (new cljs.core.PersistentVector(null,2,(5),inst_65959,inst_65960,null));
var state_65976__$1 = (function (){var statearr_65983 = state_65976;
(statearr_65983[(8)] = inst_65958__$1);

return statearr_65983;
})();
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_65976__$1,(8),jobs,inst_65961);
} else {
if((state_val_65977 === (3))){
var inst_65970 = (state_65976[(2)]);
var state_65976__$1 = state_65976;
return cljs.core.async.impl.ioc_helpers.return_chan(state_65976__$1,inst_65970);
} else {
if((state_val_65977 === (2))){
var state_65976__$1 = state_65976;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_65976__$1,(4),from);
} else {
if((state_val_65977 === (9))){
var inst_65965 = (state_65976[(2)]);
var state_65976__$1 = (function (){var statearr_65984 = state_65976;
(statearr_65984[(9)] = inst_65965);

return statearr_65984;
})();
var statearr_65985_67990 = state_65976__$1;
(statearr_65985_67990[(2)] = null);

(statearr_65985_67990[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65977 === (5))){
var inst_65956 = cljs.core.async.close_BANG_(jobs);
var state_65976__$1 = state_65976;
var statearr_65986_67995 = state_65976__$1;
(statearr_65986_67995[(2)] = inst_65956);

(statearr_65986_67995[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_65977 === (8))){
var inst_65958 = (state_65976[(8)]);
var inst_65963 = (state_65976[(2)]);
var state_65976__$1 = (function (){var statearr_65987 = state_65976;
(statearr_65987[(10)] = inst_65963);

return statearr_65987;
})();
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_65976__$1,(9),results,inst_65958);
} else {
return null;
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__ = null;
var cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____0 = (function (){
var statearr_65994 = [null,null,null,null,null,null,null,null,null,null,null];
(statearr_65994[(0)] = cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__);

(statearr_65994[(1)] = (1));

return statearr_65994;
});
var cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____1 = (function (state_65976){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_65976);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e65995){var ex__35427__auto__ = e65995;
var statearr_65996_68000 = state_65976;
(statearr_65996_68000[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_65976[(4)]))){
var statearr_65997_68001 = state_65976;
(statearr_65997_68001[(1)] = cljs.core.first((state_65976[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68002 = state_65976;
state_65976 = G__68002;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__ = function(state_65976){
switch(arguments.length){
case 0:
return cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____1.call(this,state_65976);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____0;
cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____1;
return cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_65998 = f__35509__auto__();
(statearr_65998[(6)] = c__35508__auto___67981);

return statearr_65998;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));


var c__35508__auto__ = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_66038){
var state_val_66039 = (state_66038[(1)]);
if((state_val_66039 === (7))){
var inst_66034 = (state_66038[(2)]);
var state_66038__$1 = state_66038;
var statearr_66042_68003 = state_66038__$1;
(statearr_66042_68003[(2)] = inst_66034);

(statearr_66042_68003[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66039 === (20))){
var state_66038__$1 = state_66038;
var statearr_66044_68004 = state_66038__$1;
(statearr_66044_68004[(2)] = null);

(statearr_66044_68004[(1)] = (21));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66039 === (1))){
var state_66038__$1 = state_66038;
var statearr_66045_68005 = state_66038__$1;
(statearr_66045_68005[(2)] = null);

(statearr_66045_68005[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66039 === (4))){
var inst_66001 = (state_66038[(7)]);
var inst_66001__$1 = (state_66038[(2)]);
var inst_66002 = (inst_66001__$1 == null);
var state_66038__$1 = (function (){var statearr_66047 = state_66038;
(statearr_66047[(7)] = inst_66001__$1);

return statearr_66047;
})();
if(cljs.core.truth_(inst_66002)){
var statearr_66048_68006 = state_66038__$1;
(statearr_66048_68006[(1)] = (5));

} else {
var statearr_66049_68007 = state_66038__$1;
(statearr_66049_68007[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66039 === (15))){
var inst_66015 = (state_66038[(8)]);
var state_66038__$1 = state_66038;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_66038__$1,(18),to,inst_66015);
} else {
if((state_val_66039 === (21))){
var inst_66029 = (state_66038[(2)]);
var state_66038__$1 = state_66038;
var statearr_66050_68008 = state_66038__$1;
(statearr_66050_68008[(2)] = inst_66029);

(statearr_66050_68008[(1)] = (13));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66039 === (13))){
var inst_66031 = (state_66038[(2)]);
var state_66038__$1 = (function (){var statearr_66051 = state_66038;
(statearr_66051[(9)] = inst_66031);

return statearr_66051;
})();
var statearr_66052_68009 = state_66038__$1;
(statearr_66052_68009[(2)] = null);

(statearr_66052_68009[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66039 === (6))){
var inst_66001 = (state_66038[(7)]);
var state_66038__$1 = state_66038;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_66038__$1,(11),inst_66001);
} else {
if((state_val_66039 === (17))){
var inst_66024 = (state_66038[(2)]);
var state_66038__$1 = state_66038;
if(cljs.core.truth_(inst_66024)){
var statearr_66053_68010 = state_66038__$1;
(statearr_66053_68010[(1)] = (19));

} else {
var statearr_66054_68012 = state_66038__$1;
(statearr_66054_68012[(1)] = (20));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66039 === (3))){
var inst_66036 = (state_66038[(2)]);
var state_66038__$1 = state_66038;
return cljs.core.async.impl.ioc_helpers.return_chan(state_66038__$1,inst_66036);
} else {
if((state_val_66039 === (12))){
var inst_66012 = (state_66038[(10)]);
var state_66038__$1 = state_66038;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_66038__$1,(14),inst_66012);
} else {
if((state_val_66039 === (2))){
var state_66038__$1 = state_66038;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_66038__$1,(4),results);
} else {
if((state_val_66039 === (19))){
var state_66038__$1 = state_66038;
var statearr_66055_68014 = state_66038__$1;
(statearr_66055_68014[(2)] = null);

(statearr_66055_68014[(1)] = (12));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66039 === (11))){
var inst_66012 = (state_66038[(2)]);
var state_66038__$1 = (function (){var statearr_66056 = state_66038;
(statearr_66056[(10)] = inst_66012);

return statearr_66056;
})();
var statearr_66057_68015 = state_66038__$1;
(statearr_66057_68015[(2)] = null);

(statearr_66057_68015[(1)] = (12));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66039 === (9))){
var state_66038__$1 = state_66038;
var statearr_66058_68016 = state_66038__$1;
(statearr_66058_68016[(2)] = null);

(statearr_66058_68016[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66039 === (5))){
var state_66038__$1 = state_66038;
if(cljs.core.truth_(close_QMARK_)){
var statearr_66059_68017 = state_66038__$1;
(statearr_66059_68017[(1)] = (8));

} else {
var statearr_66060_68018 = state_66038__$1;
(statearr_66060_68018[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66039 === (14))){
var inst_66015 = (state_66038[(8)]);
var inst_66017 = (state_66038[(11)]);
var inst_66015__$1 = (state_66038[(2)]);
var inst_66016 = (inst_66015__$1 == null);
var inst_66017__$1 = cljs.core.not(inst_66016);
var state_66038__$1 = (function (){var statearr_66061 = state_66038;
(statearr_66061[(8)] = inst_66015__$1);

(statearr_66061[(11)] = inst_66017__$1);

return statearr_66061;
})();
if(inst_66017__$1){
var statearr_66063_68019 = state_66038__$1;
(statearr_66063_68019[(1)] = (15));

} else {
var statearr_66064_68020 = state_66038__$1;
(statearr_66064_68020[(1)] = (16));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66039 === (16))){
var inst_66017 = (state_66038[(11)]);
var state_66038__$1 = state_66038;
var statearr_66066_68021 = state_66038__$1;
(statearr_66066_68021[(2)] = inst_66017);

(statearr_66066_68021[(1)] = (17));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66039 === (10))){
var inst_66008 = (state_66038[(2)]);
var state_66038__$1 = state_66038;
var statearr_66067_68023 = state_66038__$1;
(statearr_66067_68023[(2)] = inst_66008);

(statearr_66067_68023[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66039 === (18))){
var inst_66020 = (state_66038[(2)]);
var state_66038__$1 = state_66038;
var statearr_66068_68025 = state_66038__$1;
(statearr_66068_68025[(2)] = inst_66020);

(statearr_66068_68025[(1)] = (17));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66039 === (8))){
var inst_66005 = cljs.core.async.close_BANG_(to);
var state_66038__$1 = state_66038;
var statearr_66069_68026 = state_66038__$1;
(statearr_66069_68026[(2)] = inst_66005);

(statearr_66069_68026[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__ = null;
var cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____0 = (function (){
var statearr_66070 = [null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_66070[(0)] = cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__);

(statearr_66070[(1)] = (1));

return statearr_66070;
});
var cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____1 = (function (state_66038){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_66038);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e66071){var ex__35427__auto__ = e66071;
var statearr_66072_68027 = state_66038;
(statearr_66072_68027[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_66038[(4)]))){
var statearr_66073_68028 = state_66038;
(statearr_66073_68028[(1)] = cljs.core.first((state_66038[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68029 = state_66038;
state_66038 = G__68029;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__ = function(state_66038){
switch(arguments.length){
case 0:
return cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____1.call(this,state_66038);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____0;
cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$pipeline_STAR__$_state_machine__35424__auto____1;
return cljs$core$async$pipeline_STAR__$_state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_66075 = f__35509__auto__();
(statearr_66075[(6)] = c__35508__auto__);

return statearr_66075;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));

return c__35508__auto__;
});
/**
 * Takes elements from the from channel and supplies them to the to
 *   channel, subject to the async function af, with parallelism n. af
 *   must be a function of two arguments, the first an input value and
 *   the second a channel on which to place the result(s). The
 *   presumption is that af will return immediately, having launched some
 *   asynchronous operation whose completion/callback will put results on
 *   the channel, then close! it. Outputs will be returned in order
 *   relative to the inputs. By default, the to channel will be closed
 *   when the from channel closes, but can be determined by the close?
 *   parameter. Will stop consuming the from channel if the to channel
 *   closes. See also pipeline, pipeline-blocking.
 */
cljs.core.async.pipeline_async = (function cljs$core$async$pipeline_async(var_args){
var G__66078 = arguments.length;
switch (G__66078) {
case 4:
return cljs.core.async.pipeline_async.cljs$core$IFn$_invoke$arity$4((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]));

break;
case 5:
return cljs.core.async.pipeline_async.cljs$core$IFn$_invoke$arity$5((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]),(arguments[(4)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.pipeline_async.cljs$core$IFn$_invoke$arity$4 = (function (n,to,af,from){
return cljs.core.async.pipeline_async.cljs$core$IFn$_invoke$arity$5(n,to,af,from,true);
}));

(cljs.core.async.pipeline_async.cljs$core$IFn$_invoke$arity$5 = (function (n,to,af,from,close_QMARK_){
return cljs.core.async.pipeline_STAR_(n,to,af,from,close_QMARK_,null,new cljs.core.Keyword(null,"async","async",1050769601));
}));

(cljs.core.async.pipeline_async.cljs$lang$maxFixedArity = 5);

/**
 * Takes elements from the from channel and supplies them to the to
 *   channel, subject to the transducer xf, with parallelism n. Because
 *   it is parallel, the transducer will be applied independently to each
 *   element, not across elements, and may produce zero or more outputs
 *   per input.  Outputs will be returned in order relative to the
 *   inputs. By default, the to channel will be closed when the from
 *   channel closes, but can be determined by the close?  parameter. Will
 *   stop consuming the from channel if the to channel closes.
 * 
 *   Note this is supplied for API compatibility with the Clojure version.
 *   Values of N > 1 will not result in actual concurrency in a
 *   single-threaded runtime.
 */
cljs.core.async.pipeline = (function cljs$core$async$pipeline(var_args){
var G__66090 = arguments.length;
switch (G__66090) {
case 4:
return cljs.core.async.pipeline.cljs$core$IFn$_invoke$arity$4((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]));

break;
case 5:
return cljs.core.async.pipeline.cljs$core$IFn$_invoke$arity$5((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]),(arguments[(4)]));

break;
case 6:
return cljs.core.async.pipeline.cljs$core$IFn$_invoke$arity$6((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]),(arguments[(4)]),(arguments[(5)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.pipeline.cljs$core$IFn$_invoke$arity$4 = (function (n,to,xf,from){
return cljs.core.async.pipeline.cljs$core$IFn$_invoke$arity$5(n,to,xf,from,true);
}));

(cljs.core.async.pipeline.cljs$core$IFn$_invoke$arity$5 = (function (n,to,xf,from,close_QMARK_){
return cljs.core.async.pipeline.cljs$core$IFn$_invoke$arity$6(n,to,xf,from,close_QMARK_,null);
}));

(cljs.core.async.pipeline.cljs$core$IFn$_invoke$arity$6 = (function (n,to,xf,from,close_QMARK_,ex_handler){
return cljs.core.async.pipeline_STAR_(n,to,xf,from,close_QMARK_,ex_handler,new cljs.core.Keyword(null,"compute","compute",1555393130));
}));

(cljs.core.async.pipeline.cljs$lang$maxFixedArity = 6);

/**
 * Takes a predicate and a source channel and returns a vector of two
 *   channels, the first of which will contain the values for which the
 *   predicate returned true, the second those for which it returned
 *   false.
 * 
 *   The out channels will be unbuffered by default, or two buf-or-ns can
 *   be supplied. The channels will close after the source channel has
 *   closed.
 */
cljs.core.async.split = (function cljs$core$async$split(var_args){
var G__66098 = arguments.length;
switch (G__66098) {
case 2:
return cljs.core.async.split.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 4:
return cljs.core.async.split.cljs$core$IFn$_invoke$arity$4((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.split.cljs$core$IFn$_invoke$arity$2 = (function (p,ch){
return cljs.core.async.split.cljs$core$IFn$_invoke$arity$4(p,ch,null,null);
}));

(cljs.core.async.split.cljs$core$IFn$_invoke$arity$4 = (function (p,ch,t_buf_or_n,f_buf_or_n){
var tc = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(t_buf_or_n);
var fc = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(f_buf_or_n);
var c__35508__auto___68043 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_66126){
var state_val_66127 = (state_66126[(1)]);
if((state_val_66127 === (7))){
var inst_66122 = (state_66126[(2)]);
var state_66126__$1 = state_66126;
var statearr_66131_68044 = state_66126__$1;
(statearr_66131_68044[(2)] = inst_66122);

(statearr_66131_68044[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66127 === (1))){
var state_66126__$1 = state_66126;
var statearr_66132_68045 = state_66126__$1;
(statearr_66132_68045[(2)] = null);

(statearr_66132_68045[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66127 === (4))){
var inst_66101 = (state_66126[(7)]);
var inst_66101__$1 = (state_66126[(2)]);
var inst_66102 = (inst_66101__$1 == null);
var state_66126__$1 = (function (){var statearr_66133 = state_66126;
(statearr_66133[(7)] = inst_66101__$1);

return statearr_66133;
})();
if(cljs.core.truth_(inst_66102)){
var statearr_66134_68046 = state_66126__$1;
(statearr_66134_68046[(1)] = (5));

} else {
var statearr_66135_68047 = state_66126__$1;
(statearr_66135_68047[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66127 === (13))){
var state_66126__$1 = state_66126;
var statearr_66136_68048 = state_66126__$1;
(statearr_66136_68048[(2)] = null);

(statearr_66136_68048[(1)] = (14));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66127 === (6))){
var inst_66101 = (state_66126[(7)]);
var inst_66108 = (p.cljs$core$IFn$_invoke$arity$1 ? p.cljs$core$IFn$_invoke$arity$1(inst_66101) : p.call(null,inst_66101));
var state_66126__$1 = state_66126;
if(cljs.core.truth_(inst_66108)){
var statearr_66137_68049 = state_66126__$1;
(statearr_66137_68049[(1)] = (9));

} else {
var statearr_66138_68050 = state_66126__$1;
(statearr_66138_68050[(1)] = (10));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66127 === (3))){
var inst_66124 = (state_66126[(2)]);
var state_66126__$1 = state_66126;
return cljs.core.async.impl.ioc_helpers.return_chan(state_66126__$1,inst_66124);
} else {
if((state_val_66127 === (12))){
var state_66126__$1 = state_66126;
var statearr_66139_68054 = state_66126__$1;
(statearr_66139_68054[(2)] = null);

(statearr_66139_68054[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66127 === (2))){
var state_66126__$1 = state_66126;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_66126__$1,(4),ch);
} else {
if((state_val_66127 === (11))){
var inst_66101 = (state_66126[(7)]);
var inst_66113 = (state_66126[(2)]);
var state_66126__$1 = state_66126;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_66126__$1,(8),inst_66113,inst_66101);
} else {
if((state_val_66127 === (9))){
var state_66126__$1 = state_66126;
var statearr_66140_68055 = state_66126__$1;
(statearr_66140_68055[(2)] = tc);

(statearr_66140_68055[(1)] = (11));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66127 === (5))){
var inst_66105 = cljs.core.async.close_BANG_(tc);
var inst_66106 = cljs.core.async.close_BANG_(fc);
var state_66126__$1 = (function (){var statearr_66141 = state_66126;
(statearr_66141[(8)] = inst_66105);

return statearr_66141;
})();
var statearr_66142_68056 = state_66126__$1;
(statearr_66142_68056[(2)] = inst_66106);

(statearr_66142_68056[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66127 === (14))){
var inst_66120 = (state_66126[(2)]);
var state_66126__$1 = state_66126;
var statearr_66144_68057 = state_66126__$1;
(statearr_66144_68057[(2)] = inst_66120);

(statearr_66144_68057[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66127 === (10))){
var state_66126__$1 = state_66126;
var statearr_66145_68058 = state_66126__$1;
(statearr_66145_68058[(2)] = fc);

(statearr_66145_68058[(1)] = (11));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66127 === (8))){
var inst_66115 = (state_66126[(2)]);
var state_66126__$1 = state_66126;
if(cljs.core.truth_(inst_66115)){
var statearr_66147_68062 = state_66126__$1;
(statearr_66147_68062[(1)] = (12));

} else {
var statearr_66148_68063 = state_66126__$1;
(statearr_66148_68063[(1)] = (13));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$state_machine__35424__auto__ = null;
var cljs$core$async$state_machine__35424__auto____0 = (function (){
var statearr_66149 = [null,null,null,null,null,null,null,null,null];
(statearr_66149[(0)] = cljs$core$async$state_machine__35424__auto__);

(statearr_66149[(1)] = (1));

return statearr_66149;
});
var cljs$core$async$state_machine__35424__auto____1 = (function (state_66126){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_66126);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e66150){var ex__35427__auto__ = e66150;
var statearr_66151_68064 = state_66126;
(statearr_66151_68064[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_66126[(4)]))){
var statearr_66152_68065 = state_66126;
(statearr_66152_68065[(1)] = cljs.core.first((state_66126[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68066 = state_66126;
state_66126 = G__68066;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$state_machine__35424__auto__ = function(state_66126){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__35424__auto____1.call(this,state_66126);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__35424__auto____0;
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__35424__auto____1;
return cljs$core$async$state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_66153 = f__35509__auto__();
(statearr_66153[(6)] = c__35508__auto___68043);

return statearr_66153;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));


return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [tc,fc], null);
}));

(cljs.core.async.split.cljs$lang$maxFixedArity = 4);

/**
 * f should be a function of 2 arguments. Returns a channel containing
 *   the single result of applying f to init and the first item from the
 *   channel, then applying f to that result and the 2nd item, etc. If
 *   the channel closes without yielding items, returns init and f is not
 *   called. ch must close before reduce produces a result.
 */
cljs.core.async.reduce = (function cljs$core$async$reduce(f,init,ch){
var c__35508__auto__ = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_66176){
var state_val_66177 = (state_66176[(1)]);
if((state_val_66177 === (7))){
var inst_66172 = (state_66176[(2)]);
var state_66176__$1 = state_66176;
var statearr_66178_68070 = state_66176__$1;
(statearr_66178_68070[(2)] = inst_66172);

(statearr_66178_68070[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66177 === (1))){
var inst_66155 = init;
var inst_66156 = inst_66155;
var state_66176__$1 = (function (){var statearr_66179 = state_66176;
(statearr_66179[(7)] = inst_66156);

return statearr_66179;
})();
var statearr_66180_68071 = state_66176__$1;
(statearr_66180_68071[(2)] = null);

(statearr_66180_68071[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66177 === (4))){
var inst_66159 = (state_66176[(8)]);
var inst_66159__$1 = (state_66176[(2)]);
var inst_66160 = (inst_66159__$1 == null);
var state_66176__$1 = (function (){var statearr_66181 = state_66176;
(statearr_66181[(8)] = inst_66159__$1);

return statearr_66181;
})();
if(cljs.core.truth_(inst_66160)){
var statearr_66182_68076 = state_66176__$1;
(statearr_66182_68076[(1)] = (5));

} else {
var statearr_66183_68080 = state_66176__$1;
(statearr_66183_68080[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66177 === (6))){
var inst_66156 = (state_66176[(7)]);
var inst_66163 = (state_66176[(9)]);
var inst_66159 = (state_66176[(8)]);
var inst_66163__$1 = (f.cljs$core$IFn$_invoke$arity$2 ? f.cljs$core$IFn$_invoke$arity$2(inst_66156,inst_66159) : f.call(null,inst_66156,inst_66159));
var inst_66164 = cljs.core.reduced_QMARK_(inst_66163__$1);
var state_66176__$1 = (function (){var statearr_66184 = state_66176;
(statearr_66184[(9)] = inst_66163__$1);

return statearr_66184;
})();
if(inst_66164){
var statearr_66185_68081 = state_66176__$1;
(statearr_66185_68081[(1)] = (8));

} else {
var statearr_66186_68082 = state_66176__$1;
(statearr_66186_68082[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66177 === (3))){
var inst_66174 = (state_66176[(2)]);
var state_66176__$1 = state_66176;
return cljs.core.async.impl.ioc_helpers.return_chan(state_66176__$1,inst_66174);
} else {
if((state_val_66177 === (2))){
var state_66176__$1 = state_66176;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_66176__$1,(4),ch);
} else {
if((state_val_66177 === (9))){
var inst_66163 = (state_66176[(9)]);
var inst_66156 = inst_66163;
var state_66176__$1 = (function (){var statearr_66188 = state_66176;
(statearr_66188[(7)] = inst_66156);

return statearr_66188;
})();
var statearr_66189_68086 = state_66176__$1;
(statearr_66189_68086[(2)] = null);

(statearr_66189_68086[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66177 === (5))){
var inst_66156 = (state_66176[(7)]);
var state_66176__$1 = state_66176;
var statearr_66190_68087 = state_66176__$1;
(statearr_66190_68087[(2)] = inst_66156);

(statearr_66190_68087[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66177 === (10))){
var inst_66170 = (state_66176[(2)]);
var state_66176__$1 = state_66176;
var statearr_66191_68088 = state_66176__$1;
(statearr_66191_68088[(2)] = inst_66170);

(statearr_66191_68088[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66177 === (8))){
var inst_66163 = (state_66176[(9)]);
var inst_66166 = cljs.core.deref(inst_66163);
var state_66176__$1 = state_66176;
var statearr_66192_68092 = state_66176__$1;
(statearr_66192_68092[(2)] = inst_66166);

(statearr_66192_68092[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$reduce_$_state_machine__35424__auto__ = null;
var cljs$core$async$reduce_$_state_machine__35424__auto____0 = (function (){
var statearr_66193 = [null,null,null,null,null,null,null,null,null,null];
(statearr_66193[(0)] = cljs$core$async$reduce_$_state_machine__35424__auto__);

(statearr_66193[(1)] = (1));

return statearr_66193;
});
var cljs$core$async$reduce_$_state_machine__35424__auto____1 = (function (state_66176){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_66176);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e66194){var ex__35427__auto__ = e66194;
var statearr_66195_68093 = state_66176;
(statearr_66195_68093[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_66176[(4)]))){
var statearr_66196_68094 = state_66176;
(statearr_66196_68094[(1)] = cljs.core.first((state_66176[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68095 = state_66176;
state_66176 = G__68095;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$reduce_$_state_machine__35424__auto__ = function(state_66176){
switch(arguments.length){
case 0:
return cljs$core$async$reduce_$_state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$reduce_$_state_machine__35424__auto____1.call(this,state_66176);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$reduce_$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$reduce_$_state_machine__35424__auto____0;
cljs$core$async$reduce_$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$reduce_$_state_machine__35424__auto____1;
return cljs$core$async$reduce_$_state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_66197 = f__35509__auto__();
(statearr_66197[(6)] = c__35508__auto__);

return statearr_66197;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));

return c__35508__auto__;
});
/**
 * async/reduces a channel with a transformation (xform f).
 *   Returns a channel containing the result.  ch must close before
 *   transduce produces a result.
 */
cljs.core.async.transduce = (function cljs$core$async$transduce(xform,f,init,ch){
var f__$1 = (xform.cljs$core$IFn$_invoke$arity$1 ? xform.cljs$core$IFn$_invoke$arity$1(f) : xform.call(null,f));
var c__35508__auto__ = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_66203){
var state_val_66204 = (state_66203[(1)]);
if((state_val_66204 === (1))){
var inst_66198 = cljs.core.async.reduce(f__$1,init,ch);
var state_66203__$1 = state_66203;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_66203__$1,(2),inst_66198);
} else {
if((state_val_66204 === (2))){
var inst_66200 = (state_66203[(2)]);
var inst_66201 = (f__$1.cljs$core$IFn$_invoke$arity$1 ? f__$1.cljs$core$IFn$_invoke$arity$1(inst_66200) : f__$1.call(null,inst_66200));
var state_66203__$1 = state_66203;
return cljs.core.async.impl.ioc_helpers.return_chan(state_66203__$1,inst_66201);
} else {
return null;
}
}
});
return (function() {
var cljs$core$async$transduce_$_state_machine__35424__auto__ = null;
var cljs$core$async$transduce_$_state_machine__35424__auto____0 = (function (){
var statearr_66228 = [null,null,null,null,null,null,null];
(statearr_66228[(0)] = cljs$core$async$transduce_$_state_machine__35424__auto__);

(statearr_66228[(1)] = (1));

return statearr_66228;
});
var cljs$core$async$transduce_$_state_machine__35424__auto____1 = (function (state_66203){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_66203);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e66229){var ex__35427__auto__ = e66229;
var statearr_66230_68099 = state_66203;
(statearr_66230_68099[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_66203[(4)]))){
var statearr_66231_68100 = state_66203;
(statearr_66231_68100[(1)] = cljs.core.first((state_66203[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68101 = state_66203;
state_66203 = G__68101;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$transduce_$_state_machine__35424__auto__ = function(state_66203){
switch(arguments.length){
case 0:
return cljs$core$async$transduce_$_state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$transduce_$_state_machine__35424__auto____1.call(this,state_66203);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$transduce_$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$transduce_$_state_machine__35424__auto____0;
cljs$core$async$transduce_$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$transduce_$_state_machine__35424__auto____1;
return cljs$core$async$transduce_$_state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_66233 = f__35509__auto__();
(statearr_66233[(6)] = c__35508__auto__);

return statearr_66233;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));

return c__35508__auto__;
});
/**
 * Puts the contents of coll into the supplied channel.
 * 
 *   By default the channel will be closed after the items are copied,
 *   but can be determined by the close? parameter.
 * 
 *   Returns a channel which will close after the items are copied.
 */
cljs.core.async.onto_chan_BANG_ = (function cljs$core$async$onto_chan_BANG_(var_args){
var G__66235 = arguments.length;
switch (G__66235) {
case 2:
return cljs.core.async.onto_chan_BANG_.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljs.core.async.onto_chan_BANG_.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.onto_chan_BANG_.cljs$core$IFn$_invoke$arity$2 = (function (ch,coll){
return cljs.core.async.onto_chan_BANG_.cljs$core$IFn$_invoke$arity$3(ch,coll,true);
}));

(cljs.core.async.onto_chan_BANG_.cljs$core$IFn$_invoke$arity$3 = (function (ch,coll,close_QMARK_){
var c__35508__auto__ = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_66262){
var state_val_66263 = (state_66262[(1)]);
if((state_val_66263 === (7))){
var inst_66244 = (state_66262[(2)]);
var state_66262__$1 = state_66262;
var statearr_66265_68103 = state_66262__$1;
(statearr_66265_68103[(2)] = inst_66244);

(statearr_66265_68103[(1)] = (6));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66263 === (1))){
var inst_66238 = cljs.core.seq(coll);
var inst_66239 = inst_66238;
var state_66262__$1 = (function (){var statearr_66266 = state_66262;
(statearr_66266[(7)] = inst_66239);

return statearr_66266;
})();
var statearr_66271_68104 = state_66262__$1;
(statearr_66271_68104[(2)] = null);

(statearr_66271_68104[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66263 === (4))){
var inst_66239 = (state_66262[(7)]);
var inst_66242 = cljs.core.first(inst_66239);
var state_66262__$1 = state_66262;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_66262__$1,(7),ch,inst_66242);
} else {
if((state_val_66263 === (13))){
var inst_66256 = (state_66262[(2)]);
var state_66262__$1 = state_66262;
var statearr_66289_68105 = state_66262__$1;
(statearr_66289_68105[(2)] = inst_66256);

(statearr_66289_68105[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66263 === (6))){
var inst_66247 = (state_66262[(2)]);
var state_66262__$1 = state_66262;
if(cljs.core.truth_(inst_66247)){
var statearr_66290_68106 = state_66262__$1;
(statearr_66290_68106[(1)] = (8));

} else {
var statearr_66291_68107 = state_66262__$1;
(statearr_66291_68107[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66263 === (3))){
var inst_66260 = (state_66262[(2)]);
var state_66262__$1 = state_66262;
return cljs.core.async.impl.ioc_helpers.return_chan(state_66262__$1,inst_66260);
} else {
if((state_val_66263 === (12))){
var state_66262__$1 = state_66262;
var statearr_66292_68110 = state_66262__$1;
(statearr_66292_68110[(2)] = null);

(statearr_66292_68110[(1)] = (13));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66263 === (2))){
var inst_66239 = (state_66262[(7)]);
var state_66262__$1 = state_66262;
if(cljs.core.truth_(inst_66239)){
var statearr_66302_68111 = state_66262__$1;
(statearr_66302_68111[(1)] = (4));

} else {
var statearr_66304_68112 = state_66262__$1;
(statearr_66304_68112[(1)] = (5));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66263 === (11))){
var inst_66253 = cljs.core.async.close_BANG_(ch);
var state_66262__$1 = state_66262;
var statearr_66316_68113 = state_66262__$1;
(statearr_66316_68113[(2)] = inst_66253);

(statearr_66316_68113[(1)] = (13));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66263 === (9))){
var state_66262__$1 = state_66262;
if(cljs.core.truth_(close_QMARK_)){
var statearr_66317_68114 = state_66262__$1;
(statearr_66317_68114[(1)] = (11));

} else {
var statearr_66318_68115 = state_66262__$1;
(statearr_66318_68115[(1)] = (12));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66263 === (5))){
var inst_66239 = (state_66262[(7)]);
var state_66262__$1 = state_66262;
var statearr_66324_68116 = state_66262__$1;
(statearr_66324_68116[(2)] = inst_66239);

(statearr_66324_68116[(1)] = (6));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66263 === (10))){
var inst_66258 = (state_66262[(2)]);
var state_66262__$1 = state_66262;
var statearr_66332_68117 = state_66262__$1;
(statearr_66332_68117[(2)] = inst_66258);

(statearr_66332_68117[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66263 === (8))){
var inst_66239 = (state_66262[(7)]);
var inst_66249 = cljs.core.next(inst_66239);
var inst_66239__$1 = inst_66249;
var state_66262__$1 = (function (){var statearr_66342 = state_66262;
(statearr_66342[(7)] = inst_66239__$1);

return statearr_66342;
})();
var statearr_66343_68118 = state_66262__$1;
(statearr_66343_68118[(2)] = null);

(statearr_66343_68118[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$state_machine__35424__auto__ = null;
var cljs$core$async$state_machine__35424__auto____0 = (function (){
var statearr_66344 = [null,null,null,null,null,null,null,null];
(statearr_66344[(0)] = cljs$core$async$state_machine__35424__auto__);

(statearr_66344[(1)] = (1));

return statearr_66344;
});
var cljs$core$async$state_machine__35424__auto____1 = (function (state_66262){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_66262);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e66345){var ex__35427__auto__ = e66345;
var statearr_66346_68119 = state_66262;
(statearr_66346_68119[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_66262[(4)]))){
var statearr_66347_68120 = state_66262;
(statearr_66347_68120[(1)] = cljs.core.first((state_66262[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68121 = state_66262;
state_66262 = G__68121;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$state_machine__35424__auto__ = function(state_66262){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__35424__auto____1.call(this,state_66262);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__35424__auto____0;
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__35424__auto____1;
return cljs$core$async$state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_66348 = f__35509__auto__();
(statearr_66348[(6)] = c__35508__auto__);

return statearr_66348;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));

return c__35508__auto__;
}));

(cljs.core.async.onto_chan_BANG_.cljs$lang$maxFixedArity = 3);

/**
 * Creates and returns a channel which contains the contents of coll,
 *   closing when exhausted.
 */
cljs.core.async.to_chan_BANG_ = (function cljs$core$async$to_chan_BANG_(coll){
var ch = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(cljs.core.bounded_count((100),coll));
cljs.core.async.onto_chan_BANG_.cljs$core$IFn$_invoke$arity$2(ch,coll);

return ch;
});
/**
 * Deprecated - use onto-chan!
 */
cljs.core.async.onto_chan = (function cljs$core$async$onto_chan(var_args){
var G__66356 = arguments.length;
switch (G__66356) {
case 2:
return cljs.core.async.onto_chan.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljs.core.async.onto_chan.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.onto_chan.cljs$core$IFn$_invoke$arity$2 = (function (ch,coll){
return cljs.core.async.onto_chan_BANG_.cljs$core$IFn$_invoke$arity$3(ch,coll,true);
}));

(cljs.core.async.onto_chan.cljs$core$IFn$_invoke$arity$3 = (function (ch,coll,close_QMARK_){
return cljs.core.async.onto_chan_BANG_.cljs$core$IFn$_invoke$arity$3(ch,coll,close_QMARK_);
}));

(cljs.core.async.onto_chan.cljs$lang$maxFixedArity = 3);

/**
 * Deprecated - use to-chan!
 */
cljs.core.async.to_chan = (function cljs$core$async$to_chan(coll){
return cljs.core.async.to_chan_BANG_(coll);
});

/**
 * @interface
 */
cljs.core.async.Mux = function(){};

var cljs$core$async$Mux$muxch_STAR_$dyn_68126 = (function (_){
var x__5390__auto__ = (((_ == null))?null:_);
var m__5391__auto__ = (cljs.core.async.muxch_STAR_[goog.typeOf(x__5390__auto__)]);
if((!((m__5391__auto__ == null)))){
return (m__5391__auto__.cljs$core$IFn$_invoke$arity$1 ? m__5391__auto__.cljs$core$IFn$_invoke$arity$1(_) : m__5391__auto__.call(null,_));
} else {
var m__5389__auto__ = (cljs.core.async.muxch_STAR_["_"]);
if((!((m__5389__auto__ == null)))){
return (m__5389__auto__.cljs$core$IFn$_invoke$arity$1 ? m__5389__auto__.cljs$core$IFn$_invoke$arity$1(_) : m__5389__auto__.call(null,_));
} else {
throw cljs.core.missing_protocol("Mux.muxch*",_);
}
}
});
cljs.core.async.muxch_STAR_ = (function cljs$core$async$muxch_STAR_(_){
if((((!((_ == null)))) && ((!((_.cljs$core$async$Mux$muxch_STAR_$arity$1 == null)))))){
return _.cljs$core$async$Mux$muxch_STAR_$arity$1(_);
} else {
return cljs$core$async$Mux$muxch_STAR_$dyn_68126(_);
}
});


/**
 * @interface
 */
cljs.core.async.Mult = function(){};

var cljs$core$async$Mult$tap_STAR_$dyn_68127 = (function (m,ch,close_QMARK_){
var x__5390__auto__ = (((m == null))?null:m);
var m__5391__auto__ = (cljs.core.async.tap_STAR_[goog.typeOf(x__5390__auto__)]);
if((!((m__5391__auto__ == null)))){
return (m__5391__auto__.cljs$core$IFn$_invoke$arity$3 ? m__5391__auto__.cljs$core$IFn$_invoke$arity$3(m,ch,close_QMARK_) : m__5391__auto__.call(null,m,ch,close_QMARK_));
} else {
var m__5389__auto__ = (cljs.core.async.tap_STAR_["_"]);
if((!((m__5389__auto__ == null)))){
return (m__5389__auto__.cljs$core$IFn$_invoke$arity$3 ? m__5389__auto__.cljs$core$IFn$_invoke$arity$3(m,ch,close_QMARK_) : m__5389__auto__.call(null,m,ch,close_QMARK_));
} else {
throw cljs.core.missing_protocol("Mult.tap*",m);
}
}
});
cljs.core.async.tap_STAR_ = (function cljs$core$async$tap_STAR_(m,ch,close_QMARK_){
if((((!((m == null)))) && ((!((m.cljs$core$async$Mult$tap_STAR_$arity$3 == null)))))){
return m.cljs$core$async$Mult$tap_STAR_$arity$3(m,ch,close_QMARK_);
} else {
return cljs$core$async$Mult$tap_STAR_$dyn_68127(m,ch,close_QMARK_);
}
});

var cljs$core$async$Mult$untap_STAR_$dyn_68128 = (function (m,ch){
var x__5390__auto__ = (((m == null))?null:m);
var m__5391__auto__ = (cljs.core.async.untap_STAR_[goog.typeOf(x__5390__auto__)]);
if((!((m__5391__auto__ == null)))){
return (m__5391__auto__.cljs$core$IFn$_invoke$arity$2 ? m__5391__auto__.cljs$core$IFn$_invoke$arity$2(m,ch) : m__5391__auto__.call(null,m,ch));
} else {
var m__5389__auto__ = (cljs.core.async.untap_STAR_["_"]);
if((!((m__5389__auto__ == null)))){
return (m__5389__auto__.cljs$core$IFn$_invoke$arity$2 ? m__5389__auto__.cljs$core$IFn$_invoke$arity$2(m,ch) : m__5389__auto__.call(null,m,ch));
} else {
throw cljs.core.missing_protocol("Mult.untap*",m);
}
}
});
cljs.core.async.untap_STAR_ = (function cljs$core$async$untap_STAR_(m,ch){
if((((!((m == null)))) && ((!((m.cljs$core$async$Mult$untap_STAR_$arity$2 == null)))))){
return m.cljs$core$async$Mult$untap_STAR_$arity$2(m,ch);
} else {
return cljs$core$async$Mult$untap_STAR_$dyn_68128(m,ch);
}
});

var cljs$core$async$Mult$untap_all_STAR_$dyn_68129 = (function (m){
var x__5390__auto__ = (((m == null))?null:m);
var m__5391__auto__ = (cljs.core.async.untap_all_STAR_[goog.typeOf(x__5390__auto__)]);
if((!((m__5391__auto__ == null)))){
return (m__5391__auto__.cljs$core$IFn$_invoke$arity$1 ? m__5391__auto__.cljs$core$IFn$_invoke$arity$1(m) : m__5391__auto__.call(null,m));
} else {
var m__5389__auto__ = (cljs.core.async.untap_all_STAR_["_"]);
if((!((m__5389__auto__ == null)))){
return (m__5389__auto__.cljs$core$IFn$_invoke$arity$1 ? m__5389__auto__.cljs$core$IFn$_invoke$arity$1(m) : m__5389__auto__.call(null,m));
} else {
throw cljs.core.missing_protocol("Mult.untap-all*",m);
}
}
});
cljs.core.async.untap_all_STAR_ = (function cljs$core$async$untap_all_STAR_(m){
if((((!((m == null)))) && ((!((m.cljs$core$async$Mult$untap_all_STAR_$arity$1 == null)))))){
return m.cljs$core$async$Mult$untap_all_STAR_$arity$1(m);
} else {
return cljs$core$async$Mult$untap_all_STAR_$dyn_68129(m);
}
});

/**
 * Creates and returns a mult(iple) of the supplied channel. Channels
 *   containing copies of the channel can be created with 'tap', and
 *   detached with 'untap'.
 * 
 *   Each item is distributed to all taps in parallel and synchronously,
 *   i.e. each tap must accept before the next item is distributed. Use
 *   buffering/windowing to prevent slow taps from holding up the mult.
 * 
 *   Items received when there are no taps get dropped.
 * 
 *   If a tap puts to a closed channel, it will be removed from the mult.
 */
cljs.core.async.mult = (function cljs$core$async$mult(ch){
var cs = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(cljs.core.PersistentArrayMap.EMPTY);
var m = (function (){
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async66390 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.Mult}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.async.Mux}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async66390 = (function (ch,cs,meta66391){
this.ch = ch;
this.cs = cs;
this.meta66391 = meta66391;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async66390.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_66393,meta66391__$1){
var self__ = this;
var _66393__$1 = this;
return (new cljs.core.async.t_cljs$core$async66390(self__.ch,self__.cs,meta66391__$1));
}));

(cljs.core.async.t_cljs$core$async66390.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_66393){
var self__ = this;
var _66393__$1 = this;
return self__.meta66391;
}));

(cljs.core.async.t_cljs$core$async66390.prototype.cljs$core$async$Mux$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async66390.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return self__.ch;
}));

(cljs.core.async.t_cljs$core$async66390.prototype.cljs$core$async$Mult$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async66390.prototype.cljs$core$async$Mult$tap_STAR_$arity$3 = (function (_,ch__$1,close_QMARK_){
var self__ = this;
var ___$1 = this;
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(self__.cs,cljs.core.assoc,ch__$1,close_QMARK_);

return null;
}));

(cljs.core.async.t_cljs$core$async66390.prototype.cljs$core$async$Mult$untap_STAR_$arity$2 = (function (_,ch__$1){
var self__ = this;
var ___$1 = this;
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(self__.cs,cljs.core.dissoc,ch__$1);

return null;
}));

(cljs.core.async.t_cljs$core$async66390.prototype.cljs$core$async$Mult$untap_all_STAR_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
cljs.core.reset_BANG_(self__.cs,cljs.core.PersistentArrayMap.EMPTY);

return null;
}));

(cljs.core.async.t_cljs$core$async66390.getBasis = (function (){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"ch","ch",1085813622,null),new cljs.core.Symbol(null,"cs","cs",-117024463,null),new cljs.core.Symbol(null,"meta66391","meta66391",983412649,null)], null);
}));

(cljs.core.async.t_cljs$core$async66390.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async66390.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async66390");

(cljs.core.async.t_cljs$core$async66390.cljs$lang$ctorPrWriter = (function (this__5327__auto__,writer__5328__auto__,opt__5329__auto__){
return cljs.core._write(writer__5328__auto__,"cljs.core.async/t_cljs$core$async66390");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async66390.
 */
cljs.core.async.__GT_t_cljs$core$async66390 = (function cljs$core$async$mult_$___GT_t_cljs$core$async66390(ch__$1,cs__$1,meta66391){
return (new cljs.core.async.t_cljs$core$async66390(ch__$1,cs__$1,meta66391));
});

}

return (new cljs.core.async.t_cljs$core$async66390(ch,cs,cljs.core.PersistentArrayMap.EMPTY));
})()
;
var dchan = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
var dctr = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(null);
var done = (function (_){
if((cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$2(dctr,cljs.core.dec) === (0))){
return cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$2(dchan,true);
} else {
return null;
}
});
var c__35508__auto___68140 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_66545){
var state_val_66546 = (state_66545[(1)]);
if((state_val_66546 === (7))){
var inst_66541 = (state_66545[(2)]);
var state_66545__$1 = state_66545;
var statearr_66551_68141 = state_66545__$1;
(statearr_66551_68141[(2)] = inst_66541);

(statearr_66551_68141[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (20))){
var inst_66442 = (state_66545[(7)]);
var inst_66454 = cljs.core.first(inst_66442);
var inst_66455 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_66454,(0),null);
var inst_66456 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_66454,(1),null);
var state_66545__$1 = (function (){var statearr_66552 = state_66545;
(statearr_66552[(8)] = inst_66455);

return statearr_66552;
})();
if(cljs.core.truth_(inst_66456)){
var statearr_66553_68143 = state_66545__$1;
(statearr_66553_68143[(1)] = (22));

} else {
var statearr_66554_68145 = state_66545__$1;
(statearr_66554_68145[(1)] = (23));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (27))){
var inst_66484 = (state_66545[(9)]);
var inst_66486 = (state_66545[(10)]);
var inst_66491 = (state_66545[(11)]);
var inst_66404 = (state_66545[(12)]);
var inst_66491__$1 = cljs.core._nth(inst_66484,inst_66486);
var inst_66493 = cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$3(inst_66491__$1,inst_66404,done);
var state_66545__$1 = (function (){var statearr_66555 = state_66545;
(statearr_66555[(11)] = inst_66491__$1);

return statearr_66555;
})();
if(cljs.core.truth_(inst_66493)){
var statearr_66562_68146 = state_66545__$1;
(statearr_66562_68146[(1)] = (30));

} else {
var statearr_66563_68147 = state_66545__$1;
(statearr_66563_68147[(1)] = (31));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (1))){
var state_66545__$1 = state_66545;
var statearr_66565_68148 = state_66545__$1;
(statearr_66565_68148[(2)] = null);

(statearr_66565_68148[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (24))){
var inst_66442 = (state_66545[(7)]);
var inst_66461 = (state_66545[(2)]);
var inst_66462 = cljs.core.next(inst_66442);
var inst_66413 = inst_66462;
var inst_66414 = null;
var inst_66415 = (0);
var inst_66416 = (0);
var state_66545__$1 = (function (){var statearr_66566 = state_66545;
(statearr_66566[(13)] = inst_66414);

(statearr_66566[(14)] = inst_66461);

(statearr_66566[(15)] = inst_66415);

(statearr_66566[(16)] = inst_66413);

(statearr_66566[(17)] = inst_66416);

return statearr_66566;
})();
var statearr_66568_68149 = state_66545__$1;
(statearr_66568_68149[(2)] = null);

(statearr_66568_68149[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (39))){
var state_66545__$1 = state_66545;
var statearr_66573_68150 = state_66545__$1;
(statearr_66573_68150[(2)] = null);

(statearr_66573_68150[(1)] = (41));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (4))){
var inst_66404 = (state_66545[(12)]);
var inst_66404__$1 = (state_66545[(2)]);
var inst_66405 = (inst_66404__$1 == null);
var state_66545__$1 = (function (){var statearr_66574 = state_66545;
(statearr_66574[(12)] = inst_66404__$1);

return statearr_66574;
})();
if(cljs.core.truth_(inst_66405)){
var statearr_66575_68151 = state_66545__$1;
(statearr_66575_68151[(1)] = (5));

} else {
var statearr_66577_68152 = state_66545__$1;
(statearr_66577_68152[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (15))){
var inst_66414 = (state_66545[(13)]);
var inst_66415 = (state_66545[(15)]);
var inst_66413 = (state_66545[(16)]);
var inst_66416 = (state_66545[(17)]);
var inst_66438 = (state_66545[(2)]);
var inst_66439 = (inst_66416 + (1));
var tmp66569 = inst_66414;
var tmp66570 = inst_66415;
var tmp66571 = inst_66413;
var inst_66413__$1 = tmp66571;
var inst_66414__$1 = tmp66569;
var inst_66415__$1 = tmp66570;
var inst_66416__$1 = inst_66439;
var state_66545__$1 = (function (){var statearr_66578 = state_66545;
(statearr_66578[(13)] = inst_66414__$1);

(statearr_66578[(18)] = inst_66438);

(statearr_66578[(15)] = inst_66415__$1);

(statearr_66578[(16)] = inst_66413__$1);

(statearr_66578[(17)] = inst_66416__$1);

return statearr_66578;
})();
var statearr_66579_68153 = state_66545__$1;
(statearr_66579_68153[(2)] = null);

(statearr_66579_68153[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (21))){
var inst_66465 = (state_66545[(2)]);
var state_66545__$1 = state_66545;
var statearr_66583_68154 = state_66545__$1;
(statearr_66583_68154[(2)] = inst_66465);

(statearr_66583_68154[(1)] = (18));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (31))){
var inst_66491 = (state_66545[(11)]);
var inst_66497 = m.cljs$core$async$Mult$untap_STAR_$arity$2(null,inst_66491);
var state_66545__$1 = state_66545;
var statearr_66584_68156 = state_66545__$1;
(statearr_66584_68156[(2)] = inst_66497);

(statearr_66584_68156[(1)] = (32));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (32))){
var inst_66484 = (state_66545[(9)]);
var inst_66483 = (state_66545[(19)]);
var inst_66486 = (state_66545[(10)]);
var inst_66485 = (state_66545[(20)]);
var inst_66499 = (state_66545[(2)]);
var inst_66500 = (inst_66486 + (1));
var tmp66580 = inst_66484;
var tmp66581 = inst_66483;
var tmp66582 = inst_66485;
var inst_66483__$1 = tmp66581;
var inst_66484__$1 = tmp66580;
var inst_66485__$1 = tmp66582;
var inst_66486__$1 = inst_66500;
var state_66545__$1 = (function (){var statearr_66586 = state_66545;
(statearr_66586[(9)] = inst_66484__$1);

(statearr_66586[(21)] = inst_66499);

(statearr_66586[(19)] = inst_66483__$1);

(statearr_66586[(10)] = inst_66486__$1);

(statearr_66586[(20)] = inst_66485__$1);

return statearr_66586;
})();
var statearr_66587_68162 = state_66545__$1;
(statearr_66587_68162[(2)] = null);

(statearr_66587_68162[(1)] = (25));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (40))){
var inst_66512 = (state_66545[(22)]);
var inst_66516 = m.cljs$core$async$Mult$untap_STAR_$arity$2(null,inst_66512);
var state_66545__$1 = state_66545;
var statearr_66588_68173 = state_66545__$1;
(statearr_66588_68173[(2)] = inst_66516);

(statearr_66588_68173[(1)] = (41));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (33))){
var inst_66503 = (state_66545[(23)]);
var inst_66505 = cljs.core.chunked_seq_QMARK_(inst_66503);
var state_66545__$1 = state_66545;
if(inst_66505){
var statearr_66592_68180 = state_66545__$1;
(statearr_66592_68180[(1)] = (36));

} else {
var statearr_66601_68181 = state_66545__$1;
(statearr_66601_68181[(1)] = (37));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (13))){
var inst_66432 = (state_66545[(24)]);
var inst_66435 = cljs.core.async.close_BANG_(inst_66432);
var state_66545__$1 = state_66545;
var statearr_66614_68182 = state_66545__$1;
(statearr_66614_68182[(2)] = inst_66435);

(statearr_66614_68182[(1)] = (15));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (22))){
var inst_66455 = (state_66545[(8)]);
var inst_66458 = cljs.core.async.close_BANG_(inst_66455);
var state_66545__$1 = state_66545;
var statearr_66615_68183 = state_66545__$1;
(statearr_66615_68183[(2)] = inst_66458);

(statearr_66615_68183[(1)] = (24));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (36))){
var inst_66503 = (state_66545[(23)]);
var inst_66507 = cljs.core.chunk_first(inst_66503);
var inst_66508 = cljs.core.chunk_rest(inst_66503);
var inst_66509 = cljs.core.count(inst_66507);
var inst_66483 = inst_66508;
var inst_66484 = inst_66507;
var inst_66485 = inst_66509;
var inst_66486 = (0);
var state_66545__$1 = (function (){var statearr_66616 = state_66545;
(statearr_66616[(9)] = inst_66484);

(statearr_66616[(19)] = inst_66483);

(statearr_66616[(10)] = inst_66486);

(statearr_66616[(20)] = inst_66485);

return statearr_66616;
})();
var statearr_66617_68197 = state_66545__$1;
(statearr_66617_68197[(2)] = null);

(statearr_66617_68197[(1)] = (25));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (41))){
var inst_66503 = (state_66545[(23)]);
var inst_66518 = (state_66545[(2)]);
var inst_66519 = cljs.core.next(inst_66503);
var inst_66483 = inst_66519;
var inst_66484 = null;
var inst_66485 = (0);
var inst_66486 = (0);
var state_66545__$1 = (function (){var statearr_66619 = state_66545;
(statearr_66619[(9)] = inst_66484);

(statearr_66619[(19)] = inst_66483);

(statearr_66619[(10)] = inst_66486);

(statearr_66619[(25)] = inst_66518);

(statearr_66619[(20)] = inst_66485);

return statearr_66619;
})();
var statearr_66620_68204 = state_66545__$1;
(statearr_66620_68204[(2)] = null);

(statearr_66620_68204[(1)] = (25));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (43))){
var state_66545__$1 = state_66545;
var statearr_66621_68205 = state_66545__$1;
(statearr_66621_68205[(2)] = null);

(statearr_66621_68205[(1)] = (44));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (29))){
var inst_66528 = (state_66545[(2)]);
var state_66545__$1 = state_66545;
var statearr_66622_68206 = state_66545__$1;
(statearr_66622_68206[(2)] = inst_66528);

(statearr_66622_68206[(1)] = (26));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (44))){
var inst_66538 = (state_66545[(2)]);
var state_66545__$1 = (function (){var statearr_66623 = state_66545;
(statearr_66623[(26)] = inst_66538);

return statearr_66623;
})();
var statearr_66624_68215 = state_66545__$1;
(statearr_66624_68215[(2)] = null);

(statearr_66624_68215[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (6))){
var inst_66475 = (state_66545[(27)]);
var inst_66474 = cljs.core.deref(cs);
var inst_66475__$1 = cljs.core.keys(inst_66474);
var inst_66476 = cljs.core.count(inst_66475__$1);
var inst_66477 = cljs.core.reset_BANG_(dctr,inst_66476);
var inst_66482 = cljs.core.seq(inst_66475__$1);
var inst_66483 = inst_66482;
var inst_66484 = null;
var inst_66485 = (0);
var inst_66486 = (0);
var state_66545__$1 = (function (){var statearr_66626 = state_66545;
(statearr_66626[(9)] = inst_66484);

(statearr_66626[(28)] = inst_66477);

(statearr_66626[(19)] = inst_66483);

(statearr_66626[(10)] = inst_66486);

(statearr_66626[(20)] = inst_66485);

(statearr_66626[(27)] = inst_66475__$1);

return statearr_66626;
})();
var statearr_66627_68222 = state_66545__$1;
(statearr_66627_68222[(2)] = null);

(statearr_66627_68222[(1)] = (25));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (28))){
var inst_66503 = (state_66545[(23)]);
var inst_66483 = (state_66545[(19)]);
var inst_66503__$1 = cljs.core.seq(inst_66483);
var state_66545__$1 = (function (){var statearr_66628 = state_66545;
(statearr_66628[(23)] = inst_66503__$1);

return statearr_66628;
})();
if(inst_66503__$1){
var statearr_66629_68229 = state_66545__$1;
(statearr_66629_68229[(1)] = (33));

} else {
var statearr_66630_68230 = state_66545__$1;
(statearr_66630_68230[(1)] = (34));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (25))){
var inst_66486 = (state_66545[(10)]);
var inst_66485 = (state_66545[(20)]);
var inst_66488 = (inst_66486 < inst_66485);
var inst_66489 = inst_66488;
var state_66545__$1 = state_66545;
if(cljs.core.truth_(inst_66489)){
var statearr_66635_68231 = state_66545__$1;
(statearr_66635_68231[(1)] = (27));

} else {
var statearr_66640_68232 = state_66545__$1;
(statearr_66640_68232[(1)] = (28));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (34))){
var state_66545__$1 = state_66545;
var statearr_66645_68233 = state_66545__$1;
(statearr_66645_68233[(2)] = null);

(statearr_66645_68233[(1)] = (35));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (17))){
var state_66545__$1 = state_66545;
var statearr_66654_68234 = state_66545__$1;
(statearr_66654_68234[(2)] = null);

(statearr_66654_68234[(1)] = (18));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (3))){
var inst_66543 = (state_66545[(2)]);
var state_66545__$1 = state_66545;
return cljs.core.async.impl.ioc_helpers.return_chan(state_66545__$1,inst_66543);
} else {
if((state_val_66546 === (12))){
var inst_66470 = (state_66545[(2)]);
var state_66545__$1 = state_66545;
var statearr_66655_68235 = state_66545__$1;
(statearr_66655_68235[(2)] = inst_66470);

(statearr_66655_68235[(1)] = (9));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (2))){
var state_66545__$1 = state_66545;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_66545__$1,(4),ch);
} else {
if((state_val_66546 === (23))){
var state_66545__$1 = state_66545;
var statearr_66656_68238 = state_66545__$1;
(statearr_66656_68238[(2)] = null);

(statearr_66656_68238[(1)] = (24));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (35))){
var inst_66525 = (state_66545[(2)]);
var state_66545__$1 = state_66545;
var statearr_66657_68239 = state_66545__$1;
(statearr_66657_68239[(2)] = inst_66525);

(statearr_66657_68239[(1)] = (29));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (19))){
var inst_66442 = (state_66545[(7)]);
var inst_66446 = cljs.core.chunk_first(inst_66442);
var inst_66447 = cljs.core.chunk_rest(inst_66442);
var inst_66448 = cljs.core.count(inst_66446);
var inst_66413 = inst_66447;
var inst_66414 = inst_66446;
var inst_66415 = inst_66448;
var inst_66416 = (0);
var state_66545__$1 = (function (){var statearr_66659 = state_66545;
(statearr_66659[(13)] = inst_66414);

(statearr_66659[(15)] = inst_66415);

(statearr_66659[(16)] = inst_66413);

(statearr_66659[(17)] = inst_66416);

return statearr_66659;
})();
var statearr_66661_68240 = state_66545__$1;
(statearr_66661_68240[(2)] = null);

(statearr_66661_68240[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (11))){
var inst_66442 = (state_66545[(7)]);
var inst_66413 = (state_66545[(16)]);
var inst_66442__$1 = cljs.core.seq(inst_66413);
var state_66545__$1 = (function (){var statearr_66663 = state_66545;
(statearr_66663[(7)] = inst_66442__$1);

return statearr_66663;
})();
if(inst_66442__$1){
var statearr_66664_68248 = state_66545__$1;
(statearr_66664_68248[(1)] = (16));

} else {
var statearr_66665_68249 = state_66545__$1;
(statearr_66665_68249[(1)] = (17));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (9))){
var inst_66472 = (state_66545[(2)]);
var state_66545__$1 = state_66545;
var statearr_66667_68250 = state_66545__$1;
(statearr_66667_68250[(2)] = inst_66472);

(statearr_66667_68250[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (5))){
var inst_66411 = cljs.core.deref(cs);
var inst_66412 = cljs.core.seq(inst_66411);
var inst_66413 = inst_66412;
var inst_66414 = null;
var inst_66415 = (0);
var inst_66416 = (0);
var state_66545__$1 = (function (){var statearr_66668 = state_66545;
(statearr_66668[(13)] = inst_66414);

(statearr_66668[(15)] = inst_66415);

(statearr_66668[(16)] = inst_66413);

(statearr_66668[(17)] = inst_66416);

return statearr_66668;
})();
var statearr_66669_68251 = state_66545__$1;
(statearr_66669_68251[(2)] = null);

(statearr_66669_68251[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (14))){
var state_66545__$1 = state_66545;
var statearr_66671_68252 = state_66545__$1;
(statearr_66671_68252[(2)] = null);

(statearr_66671_68252[(1)] = (15));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (45))){
var inst_66535 = (state_66545[(2)]);
var state_66545__$1 = state_66545;
var statearr_66672_68253 = state_66545__$1;
(statearr_66672_68253[(2)] = inst_66535);

(statearr_66672_68253[(1)] = (44));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (26))){
var inst_66475 = (state_66545[(27)]);
var inst_66530 = (state_66545[(2)]);
var inst_66531 = cljs.core.seq(inst_66475);
var state_66545__$1 = (function (){var statearr_66673 = state_66545;
(statearr_66673[(29)] = inst_66530);

return statearr_66673;
})();
if(inst_66531){
var statearr_66674_68254 = state_66545__$1;
(statearr_66674_68254[(1)] = (42));

} else {
var statearr_66675_68255 = state_66545__$1;
(statearr_66675_68255[(1)] = (43));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (16))){
var inst_66442 = (state_66545[(7)]);
var inst_66444 = cljs.core.chunked_seq_QMARK_(inst_66442);
var state_66545__$1 = state_66545;
if(inst_66444){
var statearr_66676_68256 = state_66545__$1;
(statearr_66676_68256[(1)] = (19));

} else {
var statearr_66677_68257 = state_66545__$1;
(statearr_66677_68257[(1)] = (20));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (38))){
var inst_66522 = (state_66545[(2)]);
var state_66545__$1 = state_66545;
var statearr_66678_68258 = state_66545__$1;
(statearr_66678_68258[(2)] = inst_66522);

(statearr_66678_68258[(1)] = (35));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (30))){
var state_66545__$1 = state_66545;
var statearr_66680_68259 = state_66545__$1;
(statearr_66680_68259[(2)] = null);

(statearr_66680_68259[(1)] = (32));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (10))){
var inst_66414 = (state_66545[(13)]);
var inst_66416 = (state_66545[(17)]);
var inst_66431 = cljs.core._nth(inst_66414,inst_66416);
var inst_66432 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_66431,(0),null);
var inst_66433 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_66431,(1),null);
var state_66545__$1 = (function (){var statearr_66681 = state_66545;
(statearr_66681[(24)] = inst_66432);

return statearr_66681;
})();
if(cljs.core.truth_(inst_66433)){
var statearr_66682_68263 = state_66545__$1;
(statearr_66682_68263[(1)] = (13));

} else {
var statearr_66683_68264 = state_66545__$1;
(statearr_66683_68264[(1)] = (14));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (18))){
var inst_66468 = (state_66545[(2)]);
var state_66545__$1 = state_66545;
var statearr_66684_68265 = state_66545__$1;
(statearr_66684_68265[(2)] = inst_66468);

(statearr_66684_68265[(1)] = (12));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (42))){
var state_66545__$1 = state_66545;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_66545__$1,(45),dchan);
} else {
if((state_val_66546 === (37))){
var inst_66503 = (state_66545[(23)]);
var inst_66404 = (state_66545[(12)]);
var inst_66512 = (state_66545[(22)]);
var inst_66512__$1 = cljs.core.first(inst_66503);
var inst_66513 = cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$3(inst_66512__$1,inst_66404,done);
var state_66545__$1 = (function (){var statearr_66686 = state_66545;
(statearr_66686[(22)] = inst_66512__$1);

return statearr_66686;
})();
if(cljs.core.truth_(inst_66513)){
var statearr_66687_68266 = state_66545__$1;
(statearr_66687_68266[(1)] = (39));

} else {
var statearr_66688_68267 = state_66545__$1;
(statearr_66688_68267[(1)] = (40));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66546 === (8))){
var inst_66415 = (state_66545[(15)]);
var inst_66416 = (state_66545[(17)]);
var inst_66420 = (inst_66416 < inst_66415);
var inst_66421 = inst_66420;
var state_66545__$1 = state_66545;
if(cljs.core.truth_(inst_66421)){
var statearr_66689_68268 = state_66545__$1;
(statearr_66689_68268[(1)] = (10));

} else {
var statearr_66690_68269 = state_66545__$1;
(statearr_66690_68269[(1)] = (11));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$mult_$_state_machine__35424__auto__ = null;
var cljs$core$async$mult_$_state_machine__35424__auto____0 = (function (){
var statearr_66692 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_66692[(0)] = cljs$core$async$mult_$_state_machine__35424__auto__);

(statearr_66692[(1)] = (1));

return statearr_66692;
});
var cljs$core$async$mult_$_state_machine__35424__auto____1 = (function (state_66545){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_66545);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e66693){var ex__35427__auto__ = e66693;
var statearr_66694_68276 = state_66545;
(statearr_66694_68276[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_66545[(4)]))){
var statearr_66695_68277 = state_66545;
(statearr_66695_68277[(1)] = cljs.core.first((state_66545[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68278 = state_66545;
state_66545 = G__68278;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$mult_$_state_machine__35424__auto__ = function(state_66545){
switch(arguments.length){
case 0:
return cljs$core$async$mult_$_state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$mult_$_state_machine__35424__auto____1.call(this,state_66545);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$mult_$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$mult_$_state_machine__35424__auto____0;
cljs$core$async$mult_$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$mult_$_state_machine__35424__auto____1;
return cljs$core$async$mult_$_state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_66696 = f__35509__auto__();
(statearr_66696[(6)] = c__35508__auto___68140);

return statearr_66696;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));


return m;
});
/**
 * Copies the mult source onto the supplied channel.
 * 
 *   By default the channel will be closed when the source closes,
 *   but can be determined by the close? parameter.
 */
cljs.core.async.tap = (function cljs$core$async$tap(var_args){
var G__66699 = arguments.length;
switch (G__66699) {
case 2:
return cljs.core.async.tap.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljs.core.async.tap.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.tap.cljs$core$IFn$_invoke$arity$2 = (function (mult,ch){
return cljs.core.async.tap.cljs$core$IFn$_invoke$arity$3(mult,ch,true);
}));

(cljs.core.async.tap.cljs$core$IFn$_invoke$arity$3 = (function (mult,ch,close_QMARK_){
cljs.core.async.tap_STAR_(mult,ch,close_QMARK_);

return ch;
}));

(cljs.core.async.tap.cljs$lang$maxFixedArity = 3);

/**
 * Disconnects a target channel from a mult
 */
cljs.core.async.untap = (function cljs$core$async$untap(mult,ch){
return cljs.core.async.untap_STAR_(mult,ch);
});
/**
 * Disconnects all target channels from a mult
 */
cljs.core.async.untap_all = (function cljs$core$async$untap_all(mult){
return cljs.core.async.untap_all_STAR_(mult);
});

/**
 * @interface
 */
cljs.core.async.Mix = function(){};

var cljs$core$async$Mix$admix_STAR_$dyn_68280 = (function (m,ch){
var x__5390__auto__ = (((m == null))?null:m);
var m__5391__auto__ = (cljs.core.async.admix_STAR_[goog.typeOf(x__5390__auto__)]);
if((!((m__5391__auto__ == null)))){
return (m__5391__auto__.cljs$core$IFn$_invoke$arity$2 ? m__5391__auto__.cljs$core$IFn$_invoke$arity$2(m,ch) : m__5391__auto__.call(null,m,ch));
} else {
var m__5389__auto__ = (cljs.core.async.admix_STAR_["_"]);
if((!((m__5389__auto__ == null)))){
return (m__5389__auto__.cljs$core$IFn$_invoke$arity$2 ? m__5389__auto__.cljs$core$IFn$_invoke$arity$2(m,ch) : m__5389__auto__.call(null,m,ch));
} else {
throw cljs.core.missing_protocol("Mix.admix*",m);
}
}
});
cljs.core.async.admix_STAR_ = (function cljs$core$async$admix_STAR_(m,ch){
if((((!((m == null)))) && ((!((m.cljs$core$async$Mix$admix_STAR_$arity$2 == null)))))){
return m.cljs$core$async$Mix$admix_STAR_$arity$2(m,ch);
} else {
return cljs$core$async$Mix$admix_STAR_$dyn_68280(m,ch);
}
});

var cljs$core$async$Mix$unmix_STAR_$dyn_68281 = (function (m,ch){
var x__5390__auto__ = (((m == null))?null:m);
var m__5391__auto__ = (cljs.core.async.unmix_STAR_[goog.typeOf(x__5390__auto__)]);
if((!((m__5391__auto__ == null)))){
return (m__5391__auto__.cljs$core$IFn$_invoke$arity$2 ? m__5391__auto__.cljs$core$IFn$_invoke$arity$2(m,ch) : m__5391__auto__.call(null,m,ch));
} else {
var m__5389__auto__ = (cljs.core.async.unmix_STAR_["_"]);
if((!((m__5389__auto__ == null)))){
return (m__5389__auto__.cljs$core$IFn$_invoke$arity$2 ? m__5389__auto__.cljs$core$IFn$_invoke$arity$2(m,ch) : m__5389__auto__.call(null,m,ch));
} else {
throw cljs.core.missing_protocol("Mix.unmix*",m);
}
}
});
cljs.core.async.unmix_STAR_ = (function cljs$core$async$unmix_STAR_(m,ch){
if((((!((m == null)))) && ((!((m.cljs$core$async$Mix$unmix_STAR_$arity$2 == null)))))){
return m.cljs$core$async$Mix$unmix_STAR_$arity$2(m,ch);
} else {
return cljs$core$async$Mix$unmix_STAR_$dyn_68281(m,ch);
}
});

var cljs$core$async$Mix$unmix_all_STAR_$dyn_68282 = (function (m){
var x__5390__auto__ = (((m == null))?null:m);
var m__5391__auto__ = (cljs.core.async.unmix_all_STAR_[goog.typeOf(x__5390__auto__)]);
if((!((m__5391__auto__ == null)))){
return (m__5391__auto__.cljs$core$IFn$_invoke$arity$1 ? m__5391__auto__.cljs$core$IFn$_invoke$arity$1(m) : m__5391__auto__.call(null,m));
} else {
var m__5389__auto__ = (cljs.core.async.unmix_all_STAR_["_"]);
if((!((m__5389__auto__ == null)))){
return (m__5389__auto__.cljs$core$IFn$_invoke$arity$1 ? m__5389__auto__.cljs$core$IFn$_invoke$arity$1(m) : m__5389__auto__.call(null,m));
} else {
throw cljs.core.missing_protocol("Mix.unmix-all*",m);
}
}
});
cljs.core.async.unmix_all_STAR_ = (function cljs$core$async$unmix_all_STAR_(m){
if((((!((m == null)))) && ((!((m.cljs$core$async$Mix$unmix_all_STAR_$arity$1 == null)))))){
return m.cljs$core$async$Mix$unmix_all_STAR_$arity$1(m);
} else {
return cljs$core$async$Mix$unmix_all_STAR_$dyn_68282(m);
}
});

var cljs$core$async$Mix$toggle_STAR_$dyn_68288 = (function (m,state_map){
var x__5390__auto__ = (((m == null))?null:m);
var m__5391__auto__ = (cljs.core.async.toggle_STAR_[goog.typeOf(x__5390__auto__)]);
if((!((m__5391__auto__ == null)))){
return (m__5391__auto__.cljs$core$IFn$_invoke$arity$2 ? m__5391__auto__.cljs$core$IFn$_invoke$arity$2(m,state_map) : m__5391__auto__.call(null,m,state_map));
} else {
var m__5389__auto__ = (cljs.core.async.toggle_STAR_["_"]);
if((!((m__5389__auto__ == null)))){
return (m__5389__auto__.cljs$core$IFn$_invoke$arity$2 ? m__5389__auto__.cljs$core$IFn$_invoke$arity$2(m,state_map) : m__5389__auto__.call(null,m,state_map));
} else {
throw cljs.core.missing_protocol("Mix.toggle*",m);
}
}
});
cljs.core.async.toggle_STAR_ = (function cljs$core$async$toggle_STAR_(m,state_map){
if((((!((m == null)))) && ((!((m.cljs$core$async$Mix$toggle_STAR_$arity$2 == null)))))){
return m.cljs$core$async$Mix$toggle_STAR_$arity$2(m,state_map);
} else {
return cljs$core$async$Mix$toggle_STAR_$dyn_68288(m,state_map);
}
});

var cljs$core$async$Mix$solo_mode_STAR_$dyn_68290 = (function (m,mode){
var x__5390__auto__ = (((m == null))?null:m);
var m__5391__auto__ = (cljs.core.async.solo_mode_STAR_[goog.typeOf(x__5390__auto__)]);
if((!((m__5391__auto__ == null)))){
return (m__5391__auto__.cljs$core$IFn$_invoke$arity$2 ? m__5391__auto__.cljs$core$IFn$_invoke$arity$2(m,mode) : m__5391__auto__.call(null,m,mode));
} else {
var m__5389__auto__ = (cljs.core.async.solo_mode_STAR_["_"]);
if((!((m__5389__auto__ == null)))){
return (m__5389__auto__.cljs$core$IFn$_invoke$arity$2 ? m__5389__auto__.cljs$core$IFn$_invoke$arity$2(m,mode) : m__5389__auto__.call(null,m,mode));
} else {
throw cljs.core.missing_protocol("Mix.solo-mode*",m);
}
}
});
cljs.core.async.solo_mode_STAR_ = (function cljs$core$async$solo_mode_STAR_(m,mode){
if((((!((m == null)))) && ((!((m.cljs$core$async$Mix$solo_mode_STAR_$arity$2 == null)))))){
return m.cljs$core$async$Mix$solo_mode_STAR_$arity$2(m,mode);
} else {
return cljs$core$async$Mix$solo_mode_STAR_$dyn_68290(m,mode);
}
});

cljs.core.async.ioc_alts_BANG_ = (function cljs$core$async$ioc_alts_BANG_(var_args){
var args__5772__auto__ = [];
var len__5766__auto___68291 = arguments.length;
var i__5767__auto___68292 = (0);
while(true){
if((i__5767__auto___68292 < len__5766__auto___68291)){
args__5772__auto__.push((arguments[i__5767__auto___68292]));

var G__68293 = (i__5767__auto___68292 + (1));
i__5767__auto___68292 = G__68293;
continue;
} else {
}
break;
}

var argseq__5773__auto__ = ((((3) < args__5772__auto__.length))?(new cljs.core.IndexedSeq(args__5772__auto__.slice((3)),(0),null)):null);
return cljs.core.async.ioc_alts_BANG_.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),argseq__5773__auto__);
});

(cljs.core.async.ioc_alts_BANG_.cljs$core$IFn$_invoke$arity$variadic = (function (state,cont_block,ports,p__66708){
var map__66709 = p__66708;
var map__66709__$1 = cljs.core.__destructure_map(map__66709);
var opts = map__66709__$1;
var statearr_66710_68294 = state;
(statearr_66710_68294[(1)] = cont_block);


var temp__5753__auto__ = cljs.core.async.do_alts((function (val){
var statearr_66711_68295 = state;
(statearr_66711_68295[(2)] = val);


return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state);
}),ports,opts);
if(cljs.core.truth_(temp__5753__auto__)){
var cb = temp__5753__auto__;
var statearr_66713_68296 = state;
(statearr_66713_68296[(2)] = cljs.core.deref(cb));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}));

(cljs.core.async.ioc_alts_BANG_.cljs$lang$maxFixedArity = (3));

/** @this {Function} */
(cljs.core.async.ioc_alts_BANG_.cljs$lang$applyTo = (function (seq66704){
var G__66705 = cljs.core.first(seq66704);
var seq66704__$1 = cljs.core.next(seq66704);
var G__66706 = cljs.core.first(seq66704__$1);
var seq66704__$2 = cljs.core.next(seq66704__$1);
var G__66707 = cljs.core.first(seq66704__$2);
var seq66704__$3 = cljs.core.next(seq66704__$2);
var self__5751__auto__ = this;
return self__5751__auto__.cljs$core$IFn$_invoke$arity$variadic(G__66705,G__66706,G__66707,seq66704__$3);
}));

/**
 * Creates and returns a mix of one or more input channels which will
 *   be put on the supplied out channel. Input sources can be added to
 *   the mix with 'admix', and removed with 'unmix'. A mix supports
 *   soloing, muting and pausing multiple inputs atomically using
 *   'toggle', and can solo using either muting or pausing as determined
 *   by 'solo-mode'.
 * 
 *   Each channel can have zero or more boolean modes set via 'toggle':
 * 
 *   :solo - when true, only this (ond other soloed) channel(s) will appear
 *        in the mix output channel. :mute and :pause states of soloed
 *        channels are ignored. If solo-mode is :mute, non-soloed
 *        channels are muted, if :pause, non-soloed channels are
 *        paused.
 * 
 *   :mute - muted channels will have their contents consumed but not included in the mix
 *   :pause - paused channels will not have their contents consumed (and thus also not included in the mix)
 */
cljs.core.async.mix = (function cljs$core$async$mix(out){
var cs = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(cljs.core.PersistentArrayMap.EMPTY);
var solo_modes = new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"pause","pause",-2095325672),null,new cljs.core.Keyword(null,"mute","mute",1151223646),null], null), null);
var attrs = cljs.core.conj.cljs$core$IFn$_invoke$arity$2(solo_modes,new cljs.core.Keyword(null,"solo","solo",-316350075));
var solo_mode = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"mute","mute",1151223646));
var change = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(cljs.core.async.sliding_buffer((1)));
var changed = (function (){
return cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$2(change,true);
});
var pick = (function (attr,chs){
return cljs.core.reduce_kv((function (ret,c,v){
if(cljs.core.truth_((attr.cljs$core$IFn$_invoke$arity$1 ? attr.cljs$core$IFn$_invoke$arity$1(v) : attr.call(null,v)))){
return cljs.core.conj.cljs$core$IFn$_invoke$arity$2(ret,c);
} else {
return ret;
}
}),cljs.core.PersistentHashSet.EMPTY,chs);
});
var calc_state = (function (){
var chs = cljs.core.deref(cs);
var mode = cljs.core.deref(solo_mode);
var solos = pick(new cljs.core.Keyword(null,"solo","solo",-316350075),chs);
var pauses = pick(new cljs.core.Keyword(null,"pause","pause",-2095325672),chs);
return new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"solos","solos",1441458643),solos,new cljs.core.Keyword(null,"mutes","mutes",1068806309),pick(new cljs.core.Keyword(null,"mute","mute",1151223646),chs),new cljs.core.Keyword(null,"reads","reads",-1215067361),cljs.core.conj.cljs$core$IFn$_invoke$arity$2(((((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(mode,new cljs.core.Keyword(null,"pause","pause",-2095325672))) && ((!(cljs.core.empty_QMARK_(solos))))))?cljs.core.vec(solos):cljs.core.vec(cljs.core.remove.cljs$core$IFn$_invoke$arity$2(pauses,cljs.core.keys(chs)))),change)], null);
});
var m = (function (){
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async66716 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.async.Mix}
 * @implements {cljs.core.async.Mux}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async66716 = (function (change,solo_mode,pick,cs,calc_state,out,changed,solo_modes,attrs,meta66717){
this.change = change;
this.solo_mode = solo_mode;
this.pick = pick;
this.cs = cs;
this.calc_state = calc_state;
this.out = out;
this.changed = changed;
this.solo_modes = solo_modes;
this.attrs = attrs;
this.meta66717 = meta66717;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async66716.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_66718,meta66717__$1){
var self__ = this;
var _66718__$1 = this;
return (new cljs.core.async.t_cljs$core$async66716(self__.change,self__.solo_mode,self__.pick,self__.cs,self__.calc_state,self__.out,self__.changed,self__.solo_modes,self__.attrs,meta66717__$1));
}));

(cljs.core.async.t_cljs$core$async66716.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_66718){
var self__ = this;
var _66718__$1 = this;
return self__.meta66717;
}));

(cljs.core.async.t_cljs$core$async66716.prototype.cljs$core$async$Mux$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async66716.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return self__.out;
}));

(cljs.core.async.t_cljs$core$async66716.prototype.cljs$core$async$Mix$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async66716.prototype.cljs$core$async$Mix$admix_STAR_$arity$2 = (function (_,ch){
var self__ = this;
var ___$1 = this;
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(self__.cs,cljs.core.assoc,ch,cljs.core.PersistentArrayMap.EMPTY);

return (self__.changed.cljs$core$IFn$_invoke$arity$0 ? self__.changed.cljs$core$IFn$_invoke$arity$0() : self__.changed.call(null));
}));

(cljs.core.async.t_cljs$core$async66716.prototype.cljs$core$async$Mix$unmix_STAR_$arity$2 = (function (_,ch){
var self__ = this;
var ___$1 = this;
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(self__.cs,cljs.core.dissoc,ch);

return (self__.changed.cljs$core$IFn$_invoke$arity$0 ? self__.changed.cljs$core$IFn$_invoke$arity$0() : self__.changed.call(null));
}));

(cljs.core.async.t_cljs$core$async66716.prototype.cljs$core$async$Mix$unmix_all_STAR_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
cljs.core.reset_BANG_(self__.cs,cljs.core.PersistentArrayMap.EMPTY);

return (self__.changed.cljs$core$IFn$_invoke$arity$0 ? self__.changed.cljs$core$IFn$_invoke$arity$0() : self__.changed.call(null));
}));

(cljs.core.async.t_cljs$core$async66716.prototype.cljs$core$async$Mix$toggle_STAR_$arity$2 = (function (_,state_map){
var self__ = this;
var ___$1 = this;
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(self__.cs,cljs.core.partial.cljs$core$IFn$_invoke$arity$2(cljs.core.merge_with,cljs.core.merge),state_map);

return (self__.changed.cljs$core$IFn$_invoke$arity$0 ? self__.changed.cljs$core$IFn$_invoke$arity$0() : self__.changed.call(null));
}));

(cljs.core.async.t_cljs$core$async66716.prototype.cljs$core$async$Mix$solo_mode_STAR_$arity$2 = (function (_,mode){
var self__ = this;
var ___$1 = this;
if(cljs.core.truth_((self__.solo_modes.cljs$core$IFn$_invoke$arity$1 ? self__.solo_modes.cljs$core$IFn$_invoke$arity$1(mode) : self__.solo_modes.call(null,mode)))){
} else {
throw (new Error(["Assert failed: ",["mode must be one of: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(self__.solo_modes)].join(''),"\n","(solo-modes mode)"].join('')));
}

cljs.core.reset_BANG_(self__.solo_mode,mode);

return (self__.changed.cljs$core$IFn$_invoke$arity$0 ? self__.changed.cljs$core$IFn$_invoke$arity$0() : self__.changed.call(null));
}));

(cljs.core.async.t_cljs$core$async66716.getBasis = (function (){
return new cljs.core.PersistentVector(null, 10, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"change","change",477485025,null),new cljs.core.Symbol(null,"solo-mode","solo-mode",2031788074,null),new cljs.core.Symbol(null,"pick","pick",1300068175,null),new cljs.core.Symbol(null,"cs","cs",-117024463,null),new cljs.core.Symbol(null,"calc-state","calc-state",-349968968,null),new cljs.core.Symbol(null,"out","out",729986010,null),new cljs.core.Symbol(null,"changed","changed",-2083710852,null),new cljs.core.Symbol(null,"solo-modes","solo-modes",882180540,null),new cljs.core.Symbol(null,"attrs","attrs",-450137186,null),new cljs.core.Symbol(null,"meta66717","meta66717",901580687,null)], null);
}));

(cljs.core.async.t_cljs$core$async66716.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async66716.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async66716");

(cljs.core.async.t_cljs$core$async66716.cljs$lang$ctorPrWriter = (function (this__5327__auto__,writer__5328__auto__,opt__5329__auto__){
return cljs.core._write(writer__5328__auto__,"cljs.core.async/t_cljs$core$async66716");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async66716.
 */
cljs.core.async.__GT_t_cljs$core$async66716 = (function cljs$core$async$mix_$___GT_t_cljs$core$async66716(change__$1,solo_mode__$1,pick__$1,cs__$1,calc_state__$1,out__$1,changed__$1,solo_modes__$1,attrs__$1,meta66717){
return (new cljs.core.async.t_cljs$core$async66716(change__$1,solo_mode__$1,pick__$1,cs__$1,calc_state__$1,out__$1,changed__$1,solo_modes__$1,attrs__$1,meta66717));
});

}

return (new cljs.core.async.t_cljs$core$async66716(change,solo_mode,pick,cs,calc_state,out,changed,solo_modes,attrs,cljs.core.PersistentArrayMap.EMPTY));
})()
;
var c__35508__auto___68298 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_66789){
var state_val_66790 = (state_66789[(1)]);
if((state_val_66790 === (7))){
var inst_66748 = (state_66789[(2)]);
var state_66789__$1 = state_66789;
if(cljs.core.truth_(inst_66748)){
var statearr_66791_68299 = state_66789__$1;
(statearr_66791_68299[(1)] = (8));

} else {
var statearr_66792_68300 = state_66789__$1;
(statearr_66792_68300[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (20))){
var inst_66741 = (state_66789[(7)]);
var state_66789__$1 = state_66789;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_66789__$1,(23),out,inst_66741);
} else {
if((state_val_66790 === (1))){
var inst_66724 = calc_state();
var inst_66725 = cljs.core.__destructure_map(inst_66724);
var inst_66726 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_66725,new cljs.core.Keyword(null,"solos","solos",1441458643));
var inst_66727 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_66725,new cljs.core.Keyword(null,"mutes","mutes",1068806309));
var inst_66728 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_66725,new cljs.core.Keyword(null,"reads","reads",-1215067361));
var inst_66729 = inst_66724;
var state_66789__$1 = (function (){var statearr_66794 = state_66789;
(statearr_66794[(8)] = inst_66729);

(statearr_66794[(9)] = inst_66728);

(statearr_66794[(10)] = inst_66726);

(statearr_66794[(11)] = inst_66727);

return statearr_66794;
})();
var statearr_66795_68313 = state_66789__$1;
(statearr_66795_68313[(2)] = null);

(statearr_66795_68313[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (24))){
var inst_66732 = (state_66789[(12)]);
var inst_66729 = inst_66732;
var state_66789__$1 = (function (){var statearr_66796 = state_66789;
(statearr_66796[(8)] = inst_66729);

return statearr_66796;
})();
var statearr_66797_68314 = state_66789__$1;
(statearr_66797_68314[(2)] = null);

(statearr_66797_68314[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (4))){
var inst_66741 = (state_66789[(7)]);
var inst_66743 = (state_66789[(13)]);
var inst_66740 = (state_66789[(2)]);
var inst_66741__$1 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_66740,(0),null);
var inst_66742 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_66740,(1),null);
var inst_66743__$1 = (inst_66741__$1 == null);
var state_66789__$1 = (function (){var statearr_66798 = state_66789;
(statearr_66798[(7)] = inst_66741__$1);

(statearr_66798[(14)] = inst_66742);

(statearr_66798[(13)] = inst_66743__$1);

return statearr_66798;
})();
if(cljs.core.truth_(inst_66743__$1)){
var statearr_66799_68318 = state_66789__$1;
(statearr_66799_68318[(1)] = (5));

} else {
var statearr_66800_68319 = state_66789__$1;
(statearr_66800_68319[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (15))){
var inst_66763 = (state_66789[(15)]);
var inst_66733 = (state_66789[(16)]);
var inst_66763__$1 = cljs.core.empty_QMARK_(inst_66733);
var state_66789__$1 = (function (){var statearr_66801 = state_66789;
(statearr_66801[(15)] = inst_66763__$1);

return statearr_66801;
})();
if(inst_66763__$1){
var statearr_66803_68320 = state_66789__$1;
(statearr_66803_68320[(1)] = (17));

} else {
var statearr_66804_68321 = state_66789__$1;
(statearr_66804_68321[(1)] = (18));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (21))){
var inst_66732 = (state_66789[(12)]);
var inst_66729 = inst_66732;
var state_66789__$1 = (function (){var statearr_66805 = state_66789;
(statearr_66805[(8)] = inst_66729);

return statearr_66805;
})();
var statearr_66806_68322 = state_66789__$1;
(statearr_66806_68322[(2)] = null);

(statearr_66806_68322[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (13))){
var inst_66756 = (state_66789[(2)]);
var inst_66757 = calc_state();
var inst_66729 = inst_66757;
var state_66789__$1 = (function (){var statearr_66807 = state_66789;
(statearr_66807[(17)] = inst_66756);

(statearr_66807[(8)] = inst_66729);

return statearr_66807;
})();
var statearr_66808_68323 = state_66789__$1;
(statearr_66808_68323[(2)] = null);

(statearr_66808_68323[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (22))){
var inst_66783 = (state_66789[(2)]);
var state_66789__$1 = state_66789;
var statearr_66809_68324 = state_66789__$1;
(statearr_66809_68324[(2)] = inst_66783);

(statearr_66809_68324[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (6))){
var inst_66742 = (state_66789[(14)]);
var inst_66746 = cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(inst_66742,change);
var state_66789__$1 = state_66789;
var statearr_66810_68325 = state_66789__$1;
(statearr_66810_68325[(2)] = inst_66746);

(statearr_66810_68325[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (25))){
var state_66789__$1 = state_66789;
var statearr_66811_68326 = state_66789__$1;
(statearr_66811_68326[(2)] = null);

(statearr_66811_68326[(1)] = (26));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (17))){
var inst_66742 = (state_66789[(14)]);
var inst_66734 = (state_66789[(18)]);
var inst_66765 = (inst_66734.cljs$core$IFn$_invoke$arity$1 ? inst_66734.cljs$core$IFn$_invoke$arity$1(inst_66742) : inst_66734.call(null,inst_66742));
var inst_66766 = cljs.core.not(inst_66765);
var state_66789__$1 = state_66789;
var statearr_66812_68327 = state_66789__$1;
(statearr_66812_68327[(2)] = inst_66766);

(statearr_66812_68327[(1)] = (19));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (3))){
var inst_66787 = (state_66789[(2)]);
var state_66789__$1 = state_66789;
return cljs.core.async.impl.ioc_helpers.return_chan(state_66789__$1,inst_66787);
} else {
if((state_val_66790 === (12))){
var state_66789__$1 = state_66789;
var statearr_66814_68328 = state_66789__$1;
(statearr_66814_68328[(2)] = null);

(statearr_66814_68328[(1)] = (13));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (2))){
var inst_66732 = (state_66789[(12)]);
var inst_66729 = (state_66789[(8)]);
var inst_66732__$1 = cljs.core.__destructure_map(inst_66729);
var inst_66733 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_66732__$1,new cljs.core.Keyword(null,"solos","solos",1441458643));
var inst_66734 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_66732__$1,new cljs.core.Keyword(null,"mutes","mutes",1068806309));
var inst_66735 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_66732__$1,new cljs.core.Keyword(null,"reads","reads",-1215067361));
var state_66789__$1 = (function (){var statearr_66815 = state_66789;
(statearr_66815[(12)] = inst_66732__$1);

(statearr_66815[(18)] = inst_66734);

(statearr_66815[(16)] = inst_66733);

return statearr_66815;
})();
return cljs.core.async.ioc_alts_BANG_(state_66789__$1,(4),inst_66735);
} else {
if((state_val_66790 === (23))){
var inst_66774 = (state_66789[(2)]);
var state_66789__$1 = state_66789;
if(cljs.core.truth_(inst_66774)){
var statearr_66816_68330 = state_66789__$1;
(statearr_66816_68330[(1)] = (24));

} else {
var statearr_66817_68332 = state_66789__$1;
(statearr_66817_68332[(1)] = (25));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (19))){
var inst_66769 = (state_66789[(2)]);
var state_66789__$1 = state_66789;
var statearr_66818_68333 = state_66789__$1;
(statearr_66818_68333[(2)] = inst_66769);

(statearr_66818_68333[(1)] = (16));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (11))){
var inst_66742 = (state_66789[(14)]);
var inst_66753 = cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(cs,cljs.core.dissoc,inst_66742);
var state_66789__$1 = state_66789;
var statearr_66819_68334 = state_66789__$1;
(statearr_66819_68334[(2)] = inst_66753);

(statearr_66819_68334[(1)] = (13));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (9))){
var inst_66742 = (state_66789[(14)]);
var inst_66760 = (state_66789[(19)]);
var inst_66733 = (state_66789[(16)]);
var inst_66760__$1 = (inst_66733.cljs$core$IFn$_invoke$arity$1 ? inst_66733.cljs$core$IFn$_invoke$arity$1(inst_66742) : inst_66733.call(null,inst_66742));
var state_66789__$1 = (function (){var statearr_66821 = state_66789;
(statearr_66821[(19)] = inst_66760__$1);

return statearr_66821;
})();
if(cljs.core.truth_(inst_66760__$1)){
var statearr_66822_68335 = state_66789__$1;
(statearr_66822_68335[(1)] = (14));

} else {
var statearr_66823_68336 = state_66789__$1;
(statearr_66823_68336[(1)] = (15));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (5))){
var inst_66743 = (state_66789[(13)]);
var state_66789__$1 = state_66789;
var statearr_66824_68337 = state_66789__$1;
(statearr_66824_68337[(2)] = inst_66743);

(statearr_66824_68337[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (14))){
var inst_66760 = (state_66789[(19)]);
var state_66789__$1 = state_66789;
var statearr_66825_68338 = state_66789__$1;
(statearr_66825_68338[(2)] = inst_66760);

(statearr_66825_68338[(1)] = (16));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (26))){
var inst_66779 = (state_66789[(2)]);
var state_66789__$1 = state_66789;
var statearr_66826_68339 = state_66789__$1;
(statearr_66826_68339[(2)] = inst_66779);

(statearr_66826_68339[(1)] = (22));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (16))){
var inst_66771 = (state_66789[(2)]);
var state_66789__$1 = state_66789;
if(cljs.core.truth_(inst_66771)){
var statearr_66827_68341 = state_66789__$1;
(statearr_66827_68341[(1)] = (20));

} else {
var statearr_66828_68342 = state_66789__$1;
(statearr_66828_68342[(1)] = (21));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (10))){
var inst_66785 = (state_66789[(2)]);
var state_66789__$1 = state_66789;
var statearr_66830_68344 = state_66789__$1;
(statearr_66830_68344[(2)] = inst_66785);

(statearr_66830_68344[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (18))){
var inst_66763 = (state_66789[(15)]);
var state_66789__$1 = state_66789;
var statearr_66831_68345 = state_66789__$1;
(statearr_66831_68345[(2)] = inst_66763);

(statearr_66831_68345[(1)] = (19));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66790 === (8))){
var inst_66741 = (state_66789[(7)]);
var inst_66750 = (inst_66741 == null);
var state_66789__$1 = state_66789;
if(cljs.core.truth_(inst_66750)){
var statearr_66832_68346 = state_66789__$1;
(statearr_66832_68346[(1)] = (11));

} else {
var statearr_66833_68347 = state_66789__$1;
(statearr_66833_68347[(1)] = (12));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$mix_$_state_machine__35424__auto__ = null;
var cljs$core$async$mix_$_state_machine__35424__auto____0 = (function (){
var statearr_66834 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_66834[(0)] = cljs$core$async$mix_$_state_machine__35424__auto__);

(statearr_66834[(1)] = (1));

return statearr_66834;
});
var cljs$core$async$mix_$_state_machine__35424__auto____1 = (function (state_66789){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_66789);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e66835){var ex__35427__auto__ = e66835;
var statearr_66836_68348 = state_66789;
(statearr_66836_68348[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_66789[(4)]))){
var statearr_66837_68349 = state_66789;
(statearr_66837_68349[(1)] = cljs.core.first((state_66789[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68350 = state_66789;
state_66789 = G__68350;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$mix_$_state_machine__35424__auto__ = function(state_66789){
switch(arguments.length){
case 0:
return cljs$core$async$mix_$_state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$mix_$_state_machine__35424__auto____1.call(this,state_66789);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$mix_$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$mix_$_state_machine__35424__auto____0;
cljs$core$async$mix_$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$mix_$_state_machine__35424__auto____1;
return cljs$core$async$mix_$_state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_66838 = f__35509__auto__();
(statearr_66838[(6)] = c__35508__auto___68298);

return statearr_66838;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));


return m;
});
/**
 * Adds ch as an input to the mix
 */
cljs.core.async.admix = (function cljs$core$async$admix(mix,ch){
return cljs.core.async.admix_STAR_(mix,ch);
});
/**
 * Removes ch as an input to the mix
 */
cljs.core.async.unmix = (function cljs$core$async$unmix(mix,ch){
return cljs.core.async.unmix_STAR_(mix,ch);
});
/**
 * removes all inputs from the mix
 */
cljs.core.async.unmix_all = (function cljs$core$async$unmix_all(mix){
return cljs.core.async.unmix_all_STAR_(mix);
});
/**
 * Atomically sets the state(s) of one or more channels in a mix. The
 *   state map is a map of channels -> channel-state-map. A
 *   channel-state-map is a map of attrs -> boolean, where attr is one or
 *   more of :mute, :pause or :solo. Any states supplied are merged with
 *   the current state.
 * 
 *   Note that channels can be added to a mix via toggle, which can be
 *   used to add channels in a particular (e.g. paused) state.
 */
cljs.core.async.toggle = (function cljs$core$async$toggle(mix,state_map){
return cljs.core.async.toggle_STAR_(mix,state_map);
});
/**
 * Sets the solo mode of the mix. mode must be one of :mute or :pause
 */
cljs.core.async.solo_mode = (function cljs$core$async$solo_mode(mix,mode){
return cljs.core.async.solo_mode_STAR_(mix,mode);
});

/**
 * @interface
 */
cljs.core.async.Pub = function(){};

var cljs$core$async$Pub$sub_STAR_$dyn_68351 = (function (p,v,ch,close_QMARK_){
var x__5390__auto__ = (((p == null))?null:p);
var m__5391__auto__ = (cljs.core.async.sub_STAR_[goog.typeOf(x__5390__auto__)]);
if((!((m__5391__auto__ == null)))){
return (m__5391__auto__.cljs$core$IFn$_invoke$arity$4 ? m__5391__auto__.cljs$core$IFn$_invoke$arity$4(p,v,ch,close_QMARK_) : m__5391__auto__.call(null,p,v,ch,close_QMARK_));
} else {
var m__5389__auto__ = (cljs.core.async.sub_STAR_["_"]);
if((!((m__5389__auto__ == null)))){
return (m__5389__auto__.cljs$core$IFn$_invoke$arity$4 ? m__5389__auto__.cljs$core$IFn$_invoke$arity$4(p,v,ch,close_QMARK_) : m__5389__auto__.call(null,p,v,ch,close_QMARK_));
} else {
throw cljs.core.missing_protocol("Pub.sub*",p);
}
}
});
cljs.core.async.sub_STAR_ = (function cljs$core$async$sub_STAR_(p,v,ch,close_QMARK_){
if((((!((p == null)))) && ((!((p.cljs$core$async$Pub$sub_STAR_$arity$4 == null)))))){
return p.cljs$core$async$Pub$sub_STAR_$arity$4(p,v,ch,close_QMARK_);
} else {
return cljs$core$async$Pub$sub_STAR_$dyn_68351(p,v,ch,close_QMARK_);
}
});

var cljs$core$async$Pub$unsub_STAR_$dyn_68352 = (function (p,v,ch){
var x__5390__auto__ = (((p == null))?null:p);
var m__5391__auto__ = (cljs.core.async.unsub_STAR_[goog.typeOf(x__5390__auto__)]);
if((!((m__5391__auto__ == null)))){
return (m__5391__auto__.cljs$core$IFn$_invoke$arity$3 ? m__5391__auto__.cljs$core$IFn$_invoke$arity$3(p,v,ch) : m__5391__auto__.call(null,p,v,ch));
} else {
var m__5389__auto__ = (cljs.core.async.unsub_STAR_["_"]);
if((!((m__5389__auto__ == null)))){
return (m__5389__auto__.cljs$core$IFn$_invoke$arity$3 ? m__5389__auto__.cljs$core$IFn$_invoke$arity$3(p,v,ch) : m__5389__auto__.call(null,p,v,ch));
} else {
throw cljs.core.missing_protocol("Pub.unsub*",p);
}
}
});
cljs.core.async.unsub_STAR_ = (function cljs$core$async$unsub_STAR_(p,v,ch){
if((((!((p == null)))) && ((!((p.cljs$core$async$Pub$unsub_STAR_$arity$3 == null)))))){
return p.cljs$core$async$Pub$unsub_STAR_$arity$3(p,v,ch);
} else {
return cljs$core$async$Pub$unsub_STAR_$dyn_68352(p,v,ch);
}
});

var cljs$core$async$Pub$unsub_all_STAR_$dyn_68355 = (function() {
var G__68356 = null;
var G__68356__1 = (function (p){
var x__5390__auto__ = (((p == null))?null:p);
var m__5391__auto__ = (cljs.core.async.unsub_all_STAR_[goog.typeOf(x__5390__auto__)]);
if((!((m__5391__auto__ == null)))){
return (m__5391__auto__.cljs$core$IFn$_invoke$arity$1 ? m__5391__auto__.cljs$core$IFn$_invoke$arity$1(p) : m__5391__auto__.call(null,p));
} else {
var m__5389__auto__ = (cljs.core.async.unsub_all_STAR_["_"]);
if((!((m__5389__auto__ == null)))){
return (m__5389__auto__.cljs$core$IFn$_invoke$arity$1 ? m__5389__auto__.cljs$core$IFn$_invoke$arity$1(p) : m__5389__auto__.call(null,p));
} else {
throw cljs.core.missing_protocol("Pub.unsub-all*",p);
}
}
});
var G__68356__2 = (function (p,v){
var x__5390__auto__ = (((p == null))?null:p);
var m__5391__auto__ = (cljs.core.async.unsub_all_STAR_[goog.typeOf(x__5390__auto__)]);
if((!((m__5391__auto__ == null)))){
return (m__5391__auto__.cljs$core$IFn$_invoke$arity$2 ? m__5391__auto__.cljs$core$IFn$_invoke$arity$2(p,v) : m__5391__auto__.call(null,p,v));
} else {
var m__5389__auto__ = (cljs.core.async.unsub_all_STAR_["_"]);
if((!((m__5389__auto__ == null)))){
return (m__5389__auto__.cljs$core$IFn$_invoke$arity$2 ? m__5389__auto__.cljs$core$IFn$_invoke$arity$2(p,v) : m__5389__auto__.call(null,p,v));
} else {
throw cljs.core.missing_protocol("Pub.unsub-all*",p);
}
}
});
G__68356 = function(p,v){
switch(arguments.length){
case 1:
return G__68356__1.call(this,p);
case 2:
return G__68356__2.call(this,p,v);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
G__68356.cljs$core$IFn$_invoke$arity$1 = G__68356__1;
G__68356.cljs$core$IFn$_invoke$arity$2 = G__68356__2;
return G__68356;
})()
;
cljs.core.async.unsub_all_STAR_ = (function cljs$core$async$unsub_all_STAR_(var_args){
var G__66844 = arguments.length;
switch (G__66844) {
case 1:
return cljs.core.async.unsub_all_STAR_.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljs.core.async.unsub_all_STAR_.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.unsub_all_STAR_.cljs$core$IFn$_invoke$arity$1 = (function (p){
if((((!((p == null)))) && ((!((p.cljs$core$async$Pub$unsub_all_STAR_$arity$1 == null)))))){
return p.cljs$core$async$Pub$unsub_all_STAR_$arity$1(p);
} else {
return cljs$core$async$Pub$unsub_all_STAR_$dyn_68355(p);
}
}));

(cljs.core.async.unsub_all_STAR_.cljs$core$IFn$_invoke$arity$2 = (function (p,v){
if((((!((p == null)))) && ((!((p.cljs$core$async$Pub$unsub_all_STAR_$arity$2 == null)))))){
return p.cljs$core$async$Pub$unsub_all_STAR_$arity$2(p,v);
} else {
return cljs$core$async$Pub$unsub_all_STAR_$dyn_68355(p,v);
}
}));

(cljs.core.async.unsub_all_STAR_.cljs$lang$maxFixedArity = 2);


/**
 * Creates and returns a pub(lication) of the supplied channel,
 *   partitioned into topics by the topic-fn. topic-fn will be applied to
 *   each value on the channel and the result will determine the 'topic'
 *   on which that value will be put. Channels can be subscribed to
 *   receive copies of topics using 'sub', and unsubscribed using
 *   'unsub'. Each topic will be handled by an internal mult on a
 *   dedicated channel. By default these internal channels are
 *   unbuffered, but a buf-fn can be supplied which, given a topic,
 *   creates a buffer with desired properties.
 * 
 *   Each item is distributed to all subs in parallel and synchronously,
 *   i.e. each sub must accept before the next item is distributed. Use
 *   buffering/windowing to prevent slow subs from holding up the pub.
 * 
 *   Items received when there are no matching subs get dropped.
 * 
 *   Note that if buf-fns are used then each topic is handled
 *   asynchronously, i.e. if a channel is subscribed to more than one
 *   topic it should not expect them to be interleaved identically with
 *   the source.
 */
cljs.core.async.pub = (function cljs$core$async$pub(var_args){
var G__66849 = arguments.length;
switch (G__66849) {
case 2:
return cljs.core.async.pub.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljs.core.async.pub.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.pub.cljs$core$IFn$_invoke$arity$2 = (function (ch,topic_fn){
return cljs.core.async.pub.cljs$core$IFn$_invoke$arity$3(ch,topic_fn,cljs.core.constantly(null));
}));

(cljs.core.async.pub.cljs$core$IFn$_invoke$arity$3 = (function (ch,topic_fn,buf_fn){
var mults = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(cljs.core.PersistentArrayMap.EMPTY);
var ensure_mult = (function (topic){
var or__5043__auto__ = cljs.core.get.cljs$core$IFn$_invoke$arity$2(cljs.core.deref(mults),topic);
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
return cljs.core.get.cljs$core$IFn$_invoke$arity$2(cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$2(mults,(function (p1__66846_SHARP_){
if(cljs.core.truth_((p1__66846_SHARP_.cljs$core$IFn$_invoke$arity$1 ? p1__66846_SHARP_.cljs$core$IFn$_invoke$arity$1(topic) : p1__66846_SHARP_.call(null,topic)))){
return p1__66846_SHARP_;
} else {
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(p1__66846_SHARP_,topic,cljs.core.async.mult(cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((buf_fn.cljs$core$IFn$_invoke$arity$1 ? buf_fn.cljs$core$IFn$_invoke$arity$1(topic) : buf_fn.call(null,topic)))));
}
})),topic);
}
});
var p = (function (){
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async66850 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.Pub}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.async.Mux}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async66850 = (function (ch,topic_fn,buf_fn,mults,ensure_mult,meta66851){
this.ch = ch;
this.topic_fn = topic_fn;
this.buf_fn = buf_fn;
this.mults = mults;
this.ensure_mult = ensure_mult;
this.meta66851 = meta66851;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async66850.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_66852,meta66851__$1){
var self__ = this;
var _66852__$1 = this;
return (new cljs.core.async.t_cljs$core$async66850(self__.ch,self__.topic_fn,self__.buf_fn,self__.mults,self__.ensure_mult,meta66851__$1));
}));

(cljs.core.async.t_cljs$core$async66850.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_66852){
var self__ = this;
var _66852__$1 = this;
return self__.meta66851;
}));

(cljs.core.async.t_cljs$core$async66850.prototype.cljs$core$async$Mux$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async66850.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return self__.ch;
}));

(cljs.core.async.t_cljs$core$async66850.prototype.cljs$core$async$Pub$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async66850.prototype.cljs$core$async$Pub$sub_STAR_$arity$4 = (function (p,topic,ch__$1,close_QMARK_){
var self__ = this;
var p__$1 = this;
var m = (self__.ensure_mult.cljs$core$IFn$_invoke$arity$1 ? self__.ensure_mult.cljs$core$IFn$_invoke$arity$1(topic) : self__.ensure_mult.call(null,topic));
return cljs.core.async.tap.cljs$core$IFn$_invoke$arity$3(m,ch__$1,close_QMARK_);
}));

(cljs.core.async.t_cljs$core$async66850.prototype.cljs$core$async$Pub$unsub_STAR_$arity$3 = (function (p,topic,ch__$1){
var self__ = this;
var p__$1 = this;
var temp__5753__auto__ = cljs.core.get.cljs$core$IFn$_invoke$arity$2(cljs.core.deref(self__.mults),topic);
if(cljs.core.truth_(temp__5753__auto__)){
var m = temp__5753__auto__;
return cljs.core.async.untap(m,ch__$1);
} else {
return null;
}
}));

(cljs.core.async.t_cljs$core$async66850.prototype.cljs$core$async$Pub$unsub_all_STAR_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.reset_BANG_(self__.mults,cljs.core.PersistentArrayMap.EMPTY);
}));

(cljs.core.async.t_cljs$core$async66850.prototype.cljs$core$async$Pub$unsub_all_STAR_$arity$2 = (function (_,topic){
var self__ = this;
var ___$1 = this;
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(self__.mults,cljs.core.dissoc,topic);
}));

(cljs.core.async.t_cljs$core$async66850.getBasis = (function (){
return new cljs.core.PersistentVector(null, 6, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"ch","ch",1085813622,null),new cljs.core.Symbol(null,"topic-fn","topic-fn",-862449736,null),new cljs.core.Symbol(null,"buf-fn","buf-fn",-1200281591,null),new cljs.core.Symbol(null,"mults","mults",-461114485,null),new cljs.core.Symbol(null,"ensure-mult","ensure-mult",1796584816,null),new cljs.core.Symbol(null,"meta66851","meta66851",-108684743,null)], null);
}));

(cljs.core.async.t_cljs$core$async66850.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async66850.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async66850");

(cljs.core.async.t_cljs$core$async66850.cljs$lang$ctorPrWriter = (function (this__5327__auto__,writer__5328__auto__,opt__5329__auto__){
return cljs.core._write(writer__5328__auto__,"cljs.core.async/t_cljs$core$async66850");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async66850.
 */
cljs.core.async.__GT_t_cljs$core$async66850 = (function cljs$core$async$__GT_t_cljs$core$async66850(ch__$1,topic_fn__$1,buf_fn__$1,mults__$1,ensure_mult__$1,meta66851){
return (new cljs.core.async.t_cljs$core$async66850(ch__$1,topic_fn__$1,buf_fn__$1,mults__$1,ensure_mult__$1,meta66851));
});

}

return (new cljs.core.async.t_cljs$core$async66850(ch,topic_fn,buf_fn,mults,ensure_mult,cljs.core.PersistentArrayMap.EMPTY));
})()
;
var c__35508__auto___68367 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_66929){
var state_val_66930 = (state_66929[(1)]);
if((state_val_66930 === (7))){
var inst_66924 = (state_66929[(2)]);
var state_66929__$1 = state_66929;
var statearr_66931_68368 = state_66929__$1;
(statearr_66931_68368[(2)] = inst_66924);

(statearr_66931_68368[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (20))){
var state_66929__$1 = state_66929;
var statearr_66932_68369 = state_66929__$1;
(statearr_66932_68369[(2)] = null);

(statearr_66932_68369[(1)] = (21));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (1))){
var state_66929__$1 = state_66929;
var statearr_66933_68370 = state_66929__$1;
(statearr_66933_68370[(2)] = null);

(statearr_66933_68370[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (24))){
var inst_66907 = (state_66929[(7)]);
var inst_66916 = cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(mults,cljs.core.dissoc,inst_66907);
var state_66929__$1 = state_66929;
var statearr_66935_68372 = state_66929__$1;
(statearr_66935_68372[(2)] = inst_66916);

(statearr_66935_68372[(1)] = (25));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (4))){
var inst_66858 = (state_66929[(8)]);
var inst_66858__$1 = (state_66929[(2)]);
var inst_66859 = (inst_66858__$1 == null);
var state_66929__$1 = (function (){var statearr_66936 = state_66929;
(statearr_66936[(8)] = inst_66858__$1);

return statearr_66936;
})();
if(cljs.core.truth_(inst_66859)){
var statearr_66937_68373 = state_66929__$1;
(statearr_66937_68373[(1)] = (5));

} else {
var statearr_66938_68374 = state_66929__$1;
(statearr_66938_68374[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (15))){
var inst_66901 = (state_66929[(2)]);
var state_66929__$1 = state_66929;
var statearr_66939_68375 = state_66929__$1;
(statearr_66939_68375[(2)] = inst_66901);

(statearr_66939_68375[(1)] = (12));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (21))){
var inst_66921 = (state_66929[(2)]);
var state_66929__$1 = (function (){var statearr_66940 = state_66929;
(statearr_66940[(9)] = inst_66921);

return statearr_66940;
})();
var statearr_66941_68376 = state_66929__$1;
(statearr_66941_68376[(2)] = null);

(statearr_66941_68376[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (13))){
var inst_66882 = (state_66929[(10)]);
var inst_66885 = cljs.core.chunked_seq_QMARK_(inst_66882);
var state_66929__$1 = state_66929;
if(inst_66885){
var statearr_66942_68377 = state_66929__$1;
(statearr_66942_68377[(1)] = (16));

} else {
var statearr_66943_68378 = state_66929__$1;
(statearr_66943_68378[(1)] = (17));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (22))){
var inst_66913 = (state_66929[(2)]);
var state_66929__$1 = state_66929;
if(cljs.core.truth_(inst_66913)){
var statearr_66944_68379 = state_66929__$1;
(statearr_66944_68379[(1)] = (23));

} else {
var statearr_66946_68384 = state_66929__$1;
(statearr_66946_68384[(1)] = (24));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (6))){
var inst_66909 = (state_66929[(11)]);
var inst_66907 = (state_66929[(7)]);
var inst_66858 = (state_66929[(8)]);
var inst_66907__$1 = (topic_fn.cljs$core$IFn$_invoke$arity$1 ? topic_fn.cljs$core$IFn$_invoke$arity$1(inst_66858) : topic_fn.call(null,inst_66858));
var inst_66908 = cljs.core.deref(mults);
var inst_66909__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_66908,inst_66907__$1);
var state_66929__$1 = (function (){var statearr_66947 = state_66929;
(statearr_66947[(11)] = inst_66909__$1);

(statearr_66947[(7)] = inst_66907__$1);

return statearr_66947;
})();
if(cljs.core.truth_(inst_66909__$1)){
var statearr_66948_68385 = state_66929__$1;
(statearr_66948_68385[(1)] = (19));

} else {
var statearr_66949_68386 = state_66929__$1;
(statearr_66949_68386[(1)] = (20));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (25))){
var inst_66918 = (state_66929[(2)]);
var state_66929__$1 = state_66929;
var statearr_66950_68387 = state_66929__$1;
(statearr_66950_68387[(2)] = inst_66918);

(statearr_66950_68387[(1)] = (21));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (17))){
var inst_66882 = (state_66929[(10)]);
var inst_66892 = cljs.core.first(inst_66882);
var inst_66893 = cljs.core.async.muxch_STAR_(inst_66892);
var inst_66894 = cljs.core.async.close_BANG_(inst_66893);
var inst_66895 = cljs.core.next(inst_66882);
var inst_66868 = inst_66895;
var inst_66869 = null;
var inst_66870 = (0);
var inst_66871 = (0);
var state_66929__$1 = (function (){var statearr_66951 = state_66929;
(statearr_66951[(12)] = inst_66869);

(statearr_66951[(13)] = inst_66894);

(statearr_66951[(14)] = inst_66871);

(statearr_66951[(15)] = inst_66870);

(statearr_66951[(16)] = inst_66868);

return statearr_66951;
})();
var statearr_66952_68388 = state_66929__$1;
(statearr_66952_68388[(2)] = null);

(statearr_66952_68388[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (3))){
var inst_66926 = (state_66929[(2)]);
var state_66929__$1 = state_66929;
return cljs.core.async.impl.ioc_helpers.return_chan(state_66929__$1,inst_66926);
} else {
if((state_val_66930 === (12))){
var inst_66903 = (state_66929[(2)]);
var state_66929__$1 = state_66929;
var statearr_66954_68389 = state_66929__$1;
(statearr_66954_68389[(2)] = inst_66903);

(statearr_66954_68389[(1)] = (9));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (2))){
var state_66929__$1 = state_66929;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_66929__$1,(4),ch);
} else {
if((state_val_66930 === (23))){
var state_66929__$1 = state_66929;
var statearr_66955_68390 = state_66929__$1;
(statearr_66955_68390[(2)] = null);

(statearr_66955_68390[(1)] = (25));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (19))){
var inst_66909 = (state_66929[(11)]);
var inst_66858 = (state_66929[(8)]);
var inst_66911 = cljs.core.async.muxch_STAR_(inst_66909);
var state_66929__$1 = state_66929;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_66929__$1,(22),inst_66911,inst_66858);
} else {
if((state_val_66930 === (11))){
var inst_66882 = (state_66929[(10)]);
var inst_66868 = (state_66929[(16)]);
var inst_66882__$1 = cljs.core.seq(inst_66868);
var state_66929__$1 = (function (){var statearr_66956 = state_66929;
(statearr_66956[(10)] = inst_66882__$1);

return statearr_66956;
})();
if(inst_66882__$1){
var statearr_66957_68391 = state_66929__$1;
(statearr_66957_68391[(1)] = (13));

} else {
var statearr_66958_68392 = state_66929__$1;
(statearr_66958_68392[(1)] = (14));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (9))){
var inst_66905 = (state_66929[(2)]);
var state_66929__$1 = state_66929;
var statearr_66959_68393 = state_66929__$1;
(statearr_66959_68393[(2)] = inst_66905);

(statearr_66959_68393[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (5))){
var inst_66865 = cljs.core.deref(mults);
var inst_66866 = cljs.core.vals(inst_66865);
var inst_66867 = cljs.core.seq(inst_66866);
var inst_66868 = inst_66867;
var inst_66869 = null;
var inst_66870 = (0);
var inst_66871 = (0);
var state_66929__$1 = (function (){var statearr_66960 = state_66929;
(statearr_66960[(12)] = inst_66869);

(statearr_66960[(14)] = inst_66871);

(statearr_66960[(15)] = inst_66870);

(statearr_66960[(16)] = inst_66868);

return statearr_66960;
})();
var statearr_66961_68394 = state_66929__$1;
(statearr_66961_68394[(2)] = null);

(statearr_66961_68394[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (14))){
var state_66929__$1 = state_66929;
var statearr_66966_68398 = state_66929__$1;
(statearr_66966_68398[(2)] = null);

(statearr_66966_68398[(1)] = (15));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (16))){
var inst_66882 = (state_66929[(10)]);
var inst_66887 = cljs.core.chunk_first(inst_66882);
var inst_66888 = cljs.core.chunk_rest(inst_66882);
var inst_66889 = cljs.core.count(inst_66887);
var inst_66868 = inst_66888;
var inst_66869 = inst_66887;
var inst_66870 = inst_66889;
var inst_66871 = (0);
var state_66929__$1 = (function (){var statearr_66967 = state_66929;
(statearr_66967[(12)] = inst_66869);

(statearr_66967[(14)] = inst_66871);

(statearr_66967[(15)] = inst_66870);

(statearr_66967[(16)] = inst_66868);

return statearr_66967;
})();
var statearr_66968_68399 = state_66929__$1;
(statearr_66968_68399[(2)] = null);

(statearr_66968_68399[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (10))){
var inst_66869 = (state_66929[(12)]);
var inst_66871 = (state_66929[(14)]);
var inst_66870 = (state_66929[(15)]);
var inst_66868 = (state_66929[(16)]);
var inst_66876 = cljs.core._nth(inst_66869,inst_66871);
var inst_66877 = cljs.core.async.muxch_STAR_(inst_66876);
var inst_66878 = cljs.core.async.close_BANG_(inst_66877);
var inst_66879 = (inst_66871 + (1));
var tmp66962 = inst_66869;
var tmp66963 = inst_66870;
var tmp66964 = inst_66868;
var inst_66868__$1 = tmp66964;
var inst_66869__$1 = tmp66962;
var inst_66870__$1 = tmp66963;
var inst_66871__$1 = inst_66879;
var state_66929__$1 = (function (){var statearr_66969 = state_66929;
(statearr_66969[(12)] = inst_66869__$1);

(statearr_66969[(14)] = inst_66871__$1);

(statearr_66969[(15)] = inst_66870__$1);

(statearr_66969[(17)] = inst_66878);

(statearr_66969[(16)] = inst_66868__$1);

return statearr_66969;
})();
var statearr_66970_68404 = state_66929__$1;
(statearr_66970_68404[(2)] = null);

(statearr_66970_68404[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (18))){
var inst_66898 = (state_66929[(2)]);
var state_66929__$1 = state_66929;
var statearr_66971_68408 = state_66929__$1;
(statearr_66971_68408[(2)] = inst_66898);

(statearr_66971_68408[(1)] = (15));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_66930 === (8))){
var inst_66871 = (state_66929[(14)]);
var inst_66870 = (state_66929[(15)]);
var inst_66873 = (inst_66871 < inst_66870);
var inst_66874 = inst_66873;
var state_66929__$1 = state_66929;
if(cljs.core.truth_(inst_66874)){
var statearr_66972_68409 = state_66929__$1;
(statearr_66972_68409[(1)] = (10));

} else {
var statearr_66973_68410 = state_66929__$1;
(statearr_66973_68410[(1)] = (11));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$state_machine__35424__auto__ = null;
var cljs$core$async$state_machine__35424__auto____0 = (function (){
var statearr_66974 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_66974[(0)] = cljs$core$async$state_machine__35424__auto__);

(statearr_66974[(1)] = (1));

return statearr_66974;
});
var cljs$core$async$state_machine__35424__auto____1 = (function (state_66929){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_66929);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e66976){var ex__35427__auto__ = e66976;
var statearr_66977_68414 = state_66929;
(statearr_66977_68414[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_66929[(4)]))){
var statearr_66978_68415 = state_66929;
(statearr_66978_68415[(1)] = cljs.core.first((state_66929[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68416 = state_66929;
state_66929 = G__68416;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$state_machine__35424__auto__ = function(state_66929){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__35424__auto____1.call(this,state_66929);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__35424__auto____0;
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__35424__auto____1;
return cljs$core$async$state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_66979 = f__35509__auto__();
(statearr_66979[(6)] = c__35508__auto___68367);

return statearr_66979;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));


return p;
}));

(cljs.core.async.pub.cljs$lang$maxFixedArity = 3);

/**
 * Subscribes a channel to a topic of a pub.
 * 
 *   By default the channel will be closed when the source closes,
 *   but can be determined by the close? parameter.
 */
cljs.core.async.sub = (function cljs$core$async$sub(var_args){
var G__66981 = arguments.length;
switch (G__66981) {
case 3:
return cljs.core.async.sub.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
case 4:
return cljs.core.async.sub.cljs$core$IFn$_invoke$arity$4((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),(arguments[(3)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.sub.cljs$core$IFn$_invoke$arity$3 = (function (p,topic,ch){
return cljs.core.async.sub.cljs$core$IFn$_invoke$arity$4(p,topic,ch,true);
}));

(cljs.core.async.sub.cljs$core$IFn$_invoke$arity$4 = (function (p,topic,ch,close_QMARK_){
return cljs.core.async.sub_STAR_(p,topic,ch,close_QMARK_);
}));

(cljs.core.async.sub.cljs$lang$maxFixedArity = 4);

/**
 * Unsubscribes a channel from a topic of a pub
 */
cljs.core.async.unsub = (function cljs$core$async$unsub(p,topic,ch){
return cljs.core.async.unsub_STAR_(p,topic,ch);
});
/**
 * Unsubscribes all channels from a pub, or a topic of a pub
 */
cljs.core.async.unsub_all = (function cljs$core$async$unsub_all(var_args){
var G__66984 = arguments.length;
switch (G__66984) {
case 1:
return cljs.core.async.unsub_all.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljs.core.async.unsub_all.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.unsub_all.cljs$core$IFn$_invoke$arity$1 = (function (p){
return cljs.core.async.unsub_all_STAR_(p);
}));

(cljs.core.async.unsub_all.cljs$core$IFn$_invoke$arity$2 = (function (p,topic){
return cljs.core.async.unsub_all_STAR_(p,topic);
}));

(cljs.core.async.unsub_all.cljs$lang$maxFixedArity = 2);

/**
 * Takes a function and a collection of source channels, and returns a
 *   channel which contains the values produced by applying f to the set
 *   of first items taken from each source channel, followed by applying
 *   f to the set of second items from each channel, until any one of the
 *   channels is closed, at which point the output channel will be
 *   closed. The returned channel will be unbuffered by default, or a
 *   buf-or-n can be supplied
 */
cljs.core.async.map = (function cljs$core$async$map(var_args){
var G__66987 = arguments.length;
switch (G__66987) {
case 2:
return cljs.core.async.map.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljs.core.async.map.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.map.cljs$core$IFn$_invoke$arity$2 = (function (f,chs){
return cljs.core.async.map.cljs$core$IFn$_invoke$arity$3(f,chs,null);
}));

(cljs.core.async.map.cljs$core$IFn$_invoke$arity$3 = (function (f,chs,buf_or_n){
var chs__$1 = cljs.core.vec(chs);
var out = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);
var cnt = cljs.core.count(chs__$1);
var rets = cljs.core.object_array.cljs$core$IFn$_invoke$arity$1(cnt);
var dchan = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
var dctr = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(null);
var done = cljs.core.mapv.cljs$core$IFn$_invoke$arity$2((function (i){
return (function (ret){
(rets[i] = ret);

if((cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$2(dctr,cljs.core.dec) === (0))){
return cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$2(dchan,rets.slice((0)));
} else {
return null;
}
});
}),cljs.core.range.cljs$core$IFn$_invoke$arity$1(cnt));
if((cnt === (0))){
cljs.core.async.close_BANG_(out);
} else {
var c__35508__auto___68443 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_67032){
var state_val_67033 = (state_67032[(1)]);
if((state_val_67033 === (7))){
var state_67032__$1 = state_67032;
var statearr_67035_68444 = state_67032__$1;
(statearr_67035_68444[(2)] = null);

(statearr_67035_68444[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67033 === (1))){
var state_67032__$1 = state_67032;
var statearr_67036_68448 = state_67032__$1;
(statearr_67036_68448[(2)] = null);

(statearr_67036_68448[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67033 === (4))){
var inst_66992 = (state_67032[(7)]);
var inst_66991 = (state_67032[(8)]);
var inst_66994 = (inst_66992 < inst_66991);
var state_67032__$1 = state_67032;
if(cljs.core.truth_(inst_66994)){
var statearr_67037_68453 = state_67032__$1;
(statearr_67037_68453[(1)] = (6));

} else {
var statearr_67038_68454 = state_67032__$1;
(statearr_67038_68454[(1)] = (7));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67033 === (15))){
var inst_67018 = (state_67032[(9)]);
var inst_67023 = cljs.core.apply.cljs$core$IFn$_invoke$arity$2(f,inst_67018);
var state_67032__$1 = state_67032;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_67032__$1,(17),out,inst_67023);
} else {
if((state_val_67033 === (13))){
var inst_67018 = (state_67032[(9)]);
var inst_67018__$1 = (state_67032[(2)]);
var inst_67019 = cljs.core.some(cljs.core.nil_QMARK_,inst_67018__$1);
var state_67032__$1 = (function (){var statearr_67040 = state_67032;
(statearr_67040[(9)] = inst_67018__$1);

return statearr_67040;
})();
if(cljs.core.truth_(inst_67019)){
var statearr_67041_68458 = state_67032__$1;
(statearr_67041_68458[(1)] = (14));

} else {
var statearr_67043_68459 = state_67032__$1;
(statearr_67043_68459[(1)] = (15));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67033 === (6))){
var state_67032__$1 = state_67032;
var statearr_67044_68460 = state_67032__$1;
(statearr_67044_68460[(2)] = null);

(statearr_67044_68460[(1)] = (9));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67033 === (17))){
var inst_67025 = (state_67032[(2)]);
var state_67032__$1 = (function (){var statearr_67046 = state_67032;
(statearr_67046[(10)] = inst_67025);

return statearr_67046;
})();
var statearr_67047_68461 = state_67032__$1;
(statearr_67047_68461[(2)] = null);

(statearr_67047_68461[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67033 === (3))){
var inst_67030 = (state_67032[(2)]);
var state_67032__$1 = state_67032;
return cljs.core.async.impl.ioc_helpers.return_chan(state_67032__$1,inst_67030);
} else {
if((state_val_67033 === (12))){
var _ = (function (){var statearr_67048 = state_67032;
(statearr_67048[(4)] = cljs.core.rest((state_67032[(4)])));

return statearr_67048;
})();
var state_67032__$1 = state_67032;
var ex67045 = (state_67032__$1[(2)]);
var statearr_67049_68463 = state_67032__$1;
(statearr_67049_68463[(5)] = ex67045);


if((ex67045 instanceof Object)){
var statearr_67050_68464 = state_67032__$1;
(statearr_67050_68464[(1)] = (11));

(statearr_67050_68464[(5)] = null);

} else {
throw ex67045;

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67033 === (2))){
var inst_66990 = cljs.core.reset_BANG_(dctr,cnt);
var inst_66991 = cnt;
var inst_66992 = (0);
var state_67032__$1 = (function (){var statearr_67053 = state_67032;
(statearr_67053[(7)] = inst_66992);

(statearr_67053[(8)] = inst_66991);

(statearr_67053[(11)] = inst_66990);

return statearr_67053;
})();
var statearr_67054_68469 = state_67032__$1;
(statearr_67054_68469[(2)] = null);

(statearr_67054_68469[(1)] = (4));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67033 === (11))){
var inst_66997 = (state_67032[(2)]);
var inst_66998 = cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$2(dctr,cljs.core.dec);
var state_67032__$1 = (function (){var statearr_67055 = state_67032;
(statearr_67055[(12)] = inst_66997);

return statearr_67055;
})();
var statearr_67056_68474 = state_67032__$1;
(statearr_67056_68474[(2)] = inst_66998);

(statearr_67056_68474[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67033 === (9))){
var inst_66992 = (state_67032[(7)]);
var _ = (function (){var statearr_67057 = state_67032;
(statearr_67057[(4)] = cljs.core.cons((12),(state_67032[(4)])));

return statearr_67057;
})();
var inst_67004 = (chs__$1.cljs$core$IFn$_invoke$arity$1 ? chs__$1.cljs$core$IFn$_invoke$arity$1(inst_66992) : chs__$1.call(null,inst_66992));
var inst_67005 = (done.cljs$core$IFn$_invoke$arity$1 ? done.cljs$core$IFn$_invoke$arity$1(inst_66992) : done.call(null,inst_66992));
var inst_67006 = cljs.core.async.take_BANG_.cljs$core$IFn$_invoke$arity$2(inst_67004,inst_67005);
var ___$1 = (function (){var statearr_67058 = state_67032;
(statearr_67058[(4)] = cljs.core.rest((state_67032[(4)])));

return statearr_67058;
})();
var state_67032__$1 = state_67032;
var statearr_67059_68478 = state_67032__$1;
(statearr_67059_68478[(2)] = inst_67006);

(statearr_67059_68478[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67033 === (5))){
var inst_67016 = (state_67032[(2)]);
var state_67032__$1 = (function (){var statearr_67060 = state_67032;
(statearr_67060[(13)] = inst_67016);

return statearr_67060;
})();
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_67032__$1,(13),dchan);
} else {
if((state_val_67033 === (14))){
var inst_67021 = cljs.core.async.close_BANG_(out);
var state_67032__$1 = state_67032;
var statearr_67061_68486 = state_67032__$1;
(statearr_67061_68486[(2)] = inst_67021);

(statearr_67061_68486[(1)] = (16));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67033 === (16))){
var inst_67028 = (state_67032[(2)]);
var state_67032__$1 = state_67032;
var statearr_67062_68487 = state_67032__$1;
(statearr_67062_68487[(2)] = inst_67028);

(statearr_67062_68487[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67033 === (10))){
var inst_66992 = (state_67032[(7)]);
var inst_67009 = (state_67032[(2)]);
var inst_67010 = (inst_66992 + (1));
var inst_66992__$1 = inst_67010;
var state_67032__$1 = (function (){var statearr_67063 = state_67032;
(statearr_67063[(7)] = inst_66992__$1);

(statearr_67063[(14)] = inst_67009);

return statearr_67063;
})();
var statearr_67064_68488 = state_67032__$1;
(statearr_67064_68488[(2)] = null);

(statearr_67064_68488[(1)] = (4));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67033 === (8))){
var inst_67014 = (state_67032[(2)]);
var state_67032__$1 = state_67032;
var statearr_67067_68489 = state_67032__$1;
(statearr_67067_68489[(2)] = inst_67014);

(statearr_67067_68489[(1)] = (5));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$state_machine__35424__auto__ = null;
var cljs$core$async$state_machine__35424__auto____0 = (function (){
var statearr_67068 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_67068[(0)] = cljs$core$async$state_machine__35424__auto__);

(statearr_67068[(1)] = (1));

return statearr_67068;
});
var cljs$core$async$state_machine__35424__auto____1 = (function (state_67032){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_67032);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e67069){var ex__35427__auto__ = e67069;
var statearr_67070_68492 = state_67032;
(statearr_67070_68492[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_67032[(4)]))){
var statearr_67071_68493 = state_67032;
(statearr_67071_68493[(1)] = cljs.core.first((state_67032[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68494 = state_67032;
state_67032 = G__68494;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$state_machine__35424__auto__ = function(state_67032){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__35424__auto____1.call(this,state_67032);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__35424__auto____0;
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__35424__auto____1;
return cljs$core$async$state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_67072 = f__35509__auto__();
(statearr_67072[(6)] = c__35508__auto___68443);

return statearr_67072;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));

}

return out;
}));

(cljs.core.async.map.cljs$lang$maxFixedArity = 3);

/**
 * Takes a collection of source channels and returns a channel which
 *   contains all values taken from them. The returned channel will be
 *   unbuffered by default, or a buf-or-n can be supplied. The channel
 *   will close after all the source channels have closed.
 */
cljs.core.async.merge = (function cljs$core$async$merge(var_args){
var G__67075 = arguments.length;
switch (G__67075) {
case 1:
return cljs.core.async.merge.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljs.core.async.merge.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.merge.cljs$core$IFn$_invoke$arity$1 = (function (chs){
return cljs.core.async.merge.cljs$core$IFn$_invoke$arity$2(chs,null);
}));

(cljs.core.async.merge.cljs$core$IFn$_invoke$arity$2 = (function (chs,buf_or_n){
var out = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);
var c__35508__auto___68496 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_67112){
var state_val_67113 = (state_67112[(1)]);
if((state_val_67113 === (7))){
var inst_67087 = (state_67112[(7)]);
var inst_67086 = (state_67112[(8)]);
var inst_67086__$1 = (state_67112[(2)]);
var inst_67087__$1 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_67086__$1,(0),null);
var inst_67088 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_67086__$1,(1),null);
var inst_67089 = (inst_67087__$1 == null);
var state_67112__$1 = (function (){var statearr_67118 = state_67112;
(statearr_67118[(9)] = inst_67088);

(statearr_67118[(7)] = inst_67087__$1);

(statearr_67118[(8)] = inst_67086__$1);

return statearr_67118;
})();
if(cljs.core.truth_(inst_67089)){
var statearr_67119_68497 = state_67112__$1;
(statearr_67119_68497[(1)] = (8));

} else {
var statearr_67120_68498 = state_67112__$1;
(statearr_67120_68498[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67113 === (1))){
var inst_67076 = cljs.core.vec(chs);
var inst_67077 = inst_67076;
var state_67112__$1 = (function (){var statearr_67121 = state_67112;
(statearr_67121[(10)] = inst_67077);

return statearr_67121;
})();
var statearr_67123_68499 = state_67112__$1;
(statearr_67123_68499[(2)] = null);

(statearr_67123_68499[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67113 === (4))){
var inst_67077 = (state_67112[(10)]);
var state_67112__$1 = state_67112;
return cljs.core.async.ioc_alts_BANG_(state_67112__$1,(7),inst_67077);
} else {
if((state_val_67113 === (6))){
var inst_67106 = (state_67112[(2)]);
var state_67112__$1 = state_67112;
var statearr_67127_68500 = state_67112__$1;
(statearr_67127_68500[(2)] = inst_67106);

(statearr_67127_68500[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67113 === (3))){
var inst_67108 = (state_67112[(2)]);
var state_67112__$1 = state_67112;
return cljs.core.async.impl.ioc_helpers.return_chan(state_67112__$1,inst_67108);
} else {
if((state_val_67113 === (2))){
var inst_67077 = (state_67112[(10)]);
var inst_67079 = cljs.core.count(inst_67077);
var inst_67080 = (inst_67079 > (0));
var state_67112__$1 = state_67112;
if(cljs.core.truth_(inst_67080)){
var statearr_67130_68501 = state_67112__$1;
(statearr_67130_68501[(1)] = (4));

} else {
var statearr_67131_68502 = state_67112__$1;
(statearr_67131_68502[(1)] = (5));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67113 === (11))){
var inst_67077 = (state_67112[(10)]);
var inst_67099 = (state_67112[(2)]);
var tmp67128 = inst_67077;
var inst_67077__$1 = tmp67128;
var state_67112__$1 = (function (){var statearr_67134 = state_67112;
(statearr_67134[(10)] = inst_67077__$1);

(statearr_67134[(11)] = inst_67099);

return statearr_67134;
})();
var statearr_67136_68507 = state_67112__$1;
(statearr_67136_68507[(2)] = null);

(statearr_67136_68507[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67113 === (9))){
var inst_67087 = (state_67112[(7)]);
var state_67112__$1 = state_67112;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_67112__$1,(11),out,inst_67087);
} else {
if((state_val_67113 === (5))){
var inst_67104 = cljs.core.async.close_BANG_(out);
var state_67112__$1 = state_67112;
var statearr_67141_68508 = state_67112__$1;
(statearr_67141_68508[(2)] = inst_67104);

(statearr_67141_68508[(1)] = (6));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67113 === (10))){
var inst_67102 = (state_67112[(2)]);
var state_67112__$1 = state_67112;
var statearr_67142_68509 = state_67112__$1;
(statearr_67142_68509[(2)] = inst_67102);

(statearr_67142_68509[(1)] = (6));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67113 === (8))){
var inst_67077 = (state_67112[(10)]);
var inst_67088 = (state_67112[(9)]);
var inst_67087 = (state_67112[(7)]);
var inst_67086 = (state_67112[(8)]);
var inst_67093 = (function (){var cs = inst_67077;
var vec__67082 = inst_67086;
var v = inst_67087;
var c = inst_67088;
return (function (p1__67073_SHARP_){
return cljs.core.not_EQ_.cljs$core$IFn$_invoke$arity$2(c,p1__67073_SHARP_);
});
})();
var inst_67095 = cljs.core.filterv(inst_67093,inst_67077);
var inst_67077__$1 = inst_67095;
var state_67112__$1 = (function (){var statearr_67145 = state_67112;
(statearr_67145[(10)] = inst_67077__$1);

return statearr_67145;
})();
var statearr_67147_68510 = state_67112__$1;
(statearr_67147_68510[(2)] = null);

(statearr_67147_68510[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$state_machine__35424__auto__ = null;
var cljs$core$async$state_machine__35424__auto____0 = (function (){
var statearr_67149 = [null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_67149[(0)] = cljs$core$async$state_machine__35424__auto__);

(statearr_67149[(1)] = (1));

return statearr_67149;
});
var cljs$core$async$state_machine__35424__auto____1 = (function (state_67112){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_67112);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e67151){var ex__35427__auto__ = e67151;
var statearr_67152_68511 = state_67112;
(statearr_67152_68511[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_67112[(4)]))){
var statearr_67154_68512 = state_67112;
(statearr_67154_68512[(1)] = cljs.core.first((state_67112[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68513 = state_67112;
state_67112 = G__68513;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$state_machine__35424__auto__ = function(state_67112){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__35424__auto____1.call(this,state_67112);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__35424__auto____0;
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__35424__auto____1;
return cljs$core$async$state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_67157 = f__35509__auto__();
(statearr_67157[(6)] = c__35508__auto___68496);

return statearr_67157;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));


return out;
}));

(cljs.core.async.merge.cljs$lang$maxFixedArity = 2);

/**
 * Returns a channel containing the single (collection) result of the
 *   items taken from the channel conjoined to the supplied
 *   collection. ch must close before into produces a result.
 */
cljs.core.async.into = (function cljs$core$async$into(coll,ch){
return cljs.core.async.reduce(cljs.core.conj,coll,ch);
});
/**
 * Returns a channel that will return, at most, n items from ch. After n items
 * have been returned, or ch has been closed, the return chanel will close.
 * 
 *   The output channel is unbuffered by default, unless buf-or-n is given.
 */
cljs.core.async.take = (function cljs$core$async$take(var_args){
var G__67164 = arguments.length;
switch (G__67164) {
case 2:
return cljs.core.async.take.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljs.core.async.take.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.take.cljs$core$IFn$_invoke$arity$2 = (function (n,ch){
return cljs.core.async.take.cljs$core$IFn$_invoke$arity$3(n,ch,null);
}));

(cljs.core.async.take.cljs$core$IFn$_invoke$arity$3 = (function (n,ch,buf_or_n){
var out = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);
var c__35508__auto___68519 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_67196){
var state_val_67197 = (state_67196[(1)]);
if((state_val_67197 === (7))){
var inst_67176 = (state_67196[(7)]);
var inst_67176__$1 = (state_67196[(2)]);
var inst_67178 = (inst_67176__$1 == null);
var inst_67179 = cljs.core.not(inst_67178);
var state_67196__$1 = (function (){var statearr_67205 = state_67196;
(statearr_67205[(7)] = inst_67176__$1);

return statearr_67205;
})();
if(inst_67179){
var statearr_67207_68521 = state_67196__$1;
(statearr_67207_68521[(1)] = (8));

} else {
var statearr_67208_68522 = state_67196__$1;
(statearr_67208_68522[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67197 === (1))){
var inst_67169 = (0);
var state_67196__$1 = (function (){var statearr_67212 = state_67196;
(statearr_67212[(8)] = inst_67169);

return statearr_67212;
})();
var statearr_67213_68523 = state_67196__$1;
(statearr_67213_68523[(2)] = null);

(statearr_67213_68523[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67197 === (4))){
var state_67196__$1 = state_67196;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_67196__$1,(7),ch);
} else {
if((state_val_67197 === (6))){
var inst_67190 = (state_67196[(2)]);
var state_67196__$1 = state_67196;
var statearr_67217_68524 = state_67196__$1;
(statearr_67217_68524[(2)] = inst_67190);

(statearr_67217_68524[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67197 === (3))){
var inst_67192 = (state_67196[(2)]);
var inst_67193 = cljs.core.async.close_BANG_(out);
var state_67196__$1 = (function (){var statearr_67220 = state_67196;
(statearr_67220[(9)] = inst_67192);

return statearr_67220;
})();
return cljs.core.async.impl.ioc_helpers.return_chan(state_67196__$1,inst_67193);
} else {
if((state_val_67197 === (2))){
var inst_67169 = (state_67196[(8)]);
var inst_67171 = (inst_67169 < n);
var state_67196__$1 = state_67196;
if(cljs.core.truth_(inst_67171)){
var statearr_67222_68525 = state_67196__$1;
(statearr_67222_68525[(1)] = (4));

} else {
var statearr_67223_68526 = state_67196__$1;
(statearr_67223_68526[(1)] = (5));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67197 === (11))){
var inst_67169 = (state_67196[(8)]);
var inst_67182 = (state_67196[(2)]);
var inst_67183 = (inst_67169 + (1));
var inst_67169__$1 = inst_67183;
var state_67196__$1 = (function (){var statearr_67226 = state_67196;
(statearr_67226[(10)] = inst_67182);

(statearr_67226[(8)] = inst_67169__$1);

return statearr_67226;
})();
var statearr_67228_68528 = state_67196__$1;
(statearr_67228_68528[(2)] = null);

(statearr_67228_68528[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67197 === (9))){
var state_67196__$1 = state_67196;
var statearr_67229_68529 = state_67196__$1;
(statearr_67229_68529[(2)] = null);

(statearr_67229_68529[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67197 === (5))){
var state_67196__$1 = state_67196;
var statearr_67231_68530 = state_67196__$1;
(statearr_67231_68530[(2)] = null);

(statearr_67231_68530[(1)] = (6));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67197 === (10))){
var inst_67187 = (state_67196[(2)]);
var state_67196__$1 = state_67196;
var statearr_67232_68531 = state_67196__$1;
(statearr_67232_68531[(2)] = inst_67187);

(statearr_67232_68531[(1)] = (6));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67197 === (8))){
var inst_67176 = (state_67196[(7)]);
var state_67196__$1 = state_67196;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_67196__$1,(11),out,inst_67176);
} else {
return null;
}
}
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$state_machine__35424__auto__ = null;
var cljs$core$async$state_machine__35424__auto____0 = (function (){
var statearr_67237 = [null,null,null,null,null,null,null,null,null,null,null];
(statearr_67237[(0)] = cljs$core$async$state_machine__35424__auto__);

(statearr_67237[(1)] = (1));

return statearr_67237;
});
var cljs$core$async$state_machine__35424__auto____1 = (function (state_67196){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_67196);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e67239){var ex__35427__auto__ = e67239;
var statearr_67240_68536 = state_67196;
(statearr_67240_68536[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_67196[(4)]))){
var statearr_67241_68537 = state_67196;
(statearr_67241_68537[(1)] = cljs.core.first((state_67196[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68538 = state_67196;
state_67196 = G__68538;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$state_machine__35424__auto__ = function(state_67196){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__35424__auto____1.call(this,state_67196);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__35424__auto____0;
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__35424__auto____1;
return cljs$core$async$state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_67246 = f__35509__auto__();
(statearr_67246[(6)] = c__35508__auto___68519);

return statearr_67246;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));


return out;
}));

(cljs.core.async.take.cljs$lang$maxFixedArity = 3);

/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.map_LT_ = (function cljs$core$async$map_LT_(f,ch){
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async67252 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.impl.protocols.Channel}
 * @implements {cljs.core.async.impl.protocols.WritePort}
 * @implements {cljs.core.async.impl.protocols.ReadPort}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async67252 = (function (f,ch,meta67253){
this.f = f;
this.ch = ch;
this.meta67253 = meta67253;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async67252.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_67254,meta67253__$1){
var self__ = this;
var _67254__$1 = this;
return (new cljs.core.async.t_cljs$core$async67252(self__.f,self__.ch,meta67253__$1));
}));

(cljs.core.async.t_cljs$core$async67252.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_67254){
var self__ = this;
var _67254__$1 = this;
return self__.meta67253;
}));

(cljs.core.async.t_cljs$core$async67252.prototype.cljs$core$async$impl$protocols$Channel$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async67252.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.close_BANG_(self__.ch);
}));

(cljs.core.async.t_cljs$core$async67252.prototype.cljs$core$async$impl$protocols$Channel$closed_QMARK_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.closed_QMARK_(self__.ch);
}));

(cljs.core.async.t_cljs$core$async67252.prototype.cljs$core$async$impl$protocols$ReadPort$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async67252.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){
var self__ = this;
var ___$1 = this;
var ret = cljs.core.async.impl.protocols.take_BANG_(self__.ch,(function (){
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async67264 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.impl.protocols.Handler}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async67264 = (function (f,ch,meta67253,_,fn1,meta67265){
this.f = f;
this.ch = ch;
this.meta67253 = meta67253;
this._ = _;
this.fn1 = fn1;
this.meta67265 = meta67265;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async67264.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_67266,meta67265__$1){
var self__ = this;
var _67266__$1 = this;
return (new cljs.core.async.t_cljs$core$async67264(self__.f,self__.ch,self__.meta67253,self__._,self__.fn1,meta67265__$1));
}));

(cljs.core.async.t_cljs$core$async67264.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_67266){
var self__ = this;
var _67266__$1 = this;
return self__.meta67265;
}));

(cljs.core.async.t_cljs$core$async67264.prototype.cljs$core$async$impl$protocols$Handler$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async67264.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (___$1){
var self__ = this;
var ___$2 = this;
return cljs.core.async.impl.protocols.active_QMARK_(self__.fn1);
}));

(cljs.core.async.t_cljs$core$async67264.prototype.cljs$core$async$impl$protocols$Handler$blockable_QMARK_$arity$1 = (function (___$1){
var self__ = this;
var ___$2 = this;
return true;
}));

(cljs.core.async.t_cljs$core$async67264.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (___$1){
var self__ = this;
var ___$2 = this;
var f1 = cljs.core.async.impl.protocols.commit(self__.fn1);
return (function (p1__67248_SHARP_){
var G__67280 = (((p1__67248_SHARP_ == null))?null:(self__.f.cljs$core$IFn$_invoke$arity$1 ? self__.f.cljs$core$IFn$_invoke$arity$1(p1__67248_SHARP_) : self__.f.call(null,p1__67248_SHARP_)));
return (f1.cljs$core$IFn$_invoke$arity$1 ? f1.cljs$core$IFn$_invoke$arity$1(G__67280) : f1.call(null,G__67280));
});
}));

(cljs.core.async.t_cljs$core$async67264.getBasis = (function (){
return new cljs.core.PersistentVector(null, 6, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"f","f",43394975,null),new cljs.core.Symbol(null,"ch","ch",1085813622,null),new cljs.core.Symbol(null,"meta67253","meta67253",411659634,null),cljs.core.with_meta(new cljs.core.Symbol(null,"_","_",-1201019570,null),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"tag","tag",-1290361223),new cljs.core.Symbol("cljs.core.async","t_cljs$core$async67252","cljs.core.async/t_cljs$core$async67252",-2097866052,null)], null)),new cljs.core.Symbol(null,"fn1","fn1",895834444,null),new cljs.core.Symbol(null,"meta67265","meta67265",-1591098181,null)], null);
}));

(cljs.core.async.t_cljs$core$async67264.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async67264.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async67264");

(cljs.core.async.t_cljs$core$async67264.cljs$lang$ctorPrWriter = (function (this__5327__auto__,writer__5328__auto__,opt__5329__auto__){
return cljs.core._write(writer__5328__auto__,"cljs.core.async/t_cljs$core$async67264");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async67264.
 */
cljs.core.async.__GT_t_cljs$core$async67264 = (function cljs$core$async$map_LT__$___GT_t_cljs$core$async67264(f__$1,ch__$1,meta67253__$1,___$2,fn1__$1,meta67265){
return (new cljs.core.async.t_cljs$core$async67264(f__$1,ch__$1,meta67253__$1,___$2,fn1__$1,meta67265));
});

}

return (new cljs.core.async.t_cljs$core$async67264(self__.f,self__.ch,self__.meta67253,___$1,fn1,cljs.core.PersistentArrayMap.EMPTY));
})()
);
if(cljs.core.truth_((function (){var and__5041__auto__ = ret;
if(cljs.core.truth_(and__5041__auto__)){
return (!((cljs.core.deref(ret) == null)));
} else {
return and__5041__auto__;
}
})())){
return cljs.core.async.impl.channels.box((function (){var G__67286 = cljs.core.deref(ret);
return (self__.f.cljs$core$IFn$_invoke$arity$1 ? self__.f.cljs$core$IFn$_invoke$arity$1(G__67286) : self__.f.call(null,G__67286));
})());
} else {
return ret;
}
}));

(cljs.core.async.t_cljs$core$async67252.prototype.cljs$core$async$impl$protocols$WritePort$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async67252.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.put_BANG_(self__.ch,val,fn1);
}));

(cljs.core.async.t_cljs$core$async67252.getBasis = (function (){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"f","f",43394975,null),new cljs.core.Symbol(null,"ch","ch",1085813622,null),new cljs.core.Symbol(null,"meta67253","meta67253",411659634,null)], null);
}));

(cljs.core.async.t_cljs$core$async67252.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async67252.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async67252");

(cljs.core.async.t_cljs$core$async67252.cljs$lang$ctorPrWriter = (function (this__5327__auto__,writer__5328__auto__,opt__5329__auto__){
return cljs.core._write(writer__5328__auto__,"cljs.core.async/t_cljs$core$async67252");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async67252.
 */
cljs.core.async.__GT_t_cljs$core$async67252 = (function cljs$core$async$map_LT__$___GT_t_cljs$core$async67252(f__$1,ch__$1,meta67253){
return (new cljs.core.async.t_cljs$core$async67252(f__$1,ch__$1,meta67253));
});

}

return (new cljs.core.async.t_cljs$core$async67252(f,ch,cljs.core.PersistentArrayMap.EMPTY));
});
/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.map_GT_ = (function cljs$core$async$map_GT_(f,ch){
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async67298 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.impl.protocols.Channel}
 * @implements {cljs.core.async.impl.protocols.WritePort}
 * @implements {cljs.core.async.impl.protocols.ReadPort}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async67298 = (function (f,ch,meta67299){
this.f = f;
this.ch = ch;
this.meta67299 = meta67299;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async67298.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_67300,meta67299__$1){
var self__ = this;
var _67300__$1 = this;
return (new cljs.core.async.t_cljs$core$async67298(self__.f,self__.ch,meta67299__$1));
}));

(cljs.core.async.t_cljs$core$async67298.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_67300){
var self__ = this;
var _67300__$1 = this;
return self__.meta67299;
}));

(cljs.core.async.t_cljs$core$async67298.prototype.cljs$core$async$impl$protocols$Channel$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async67298.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.close_BANG_(self__.ch);
}));

(cljs.core.async.t_cljs$core$async67298.prototype.cljs$core$async$impl$protocols$ReadPort$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async67298.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.take_BANG_(self__.ch,fn1);
}));

(cljs.core.async.t_cljs$core$async67298.prototype.cljs$core$async$impl$protocols$WritePort$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async67298.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.put_BANG_(self__.ch,(self__.f.cljs$core$IFn$_invoke$arity$1 ? self__.f.cljs$core$IFn$_invoke$arity$1(val) : self__.f.call(null,val)),fn1);
}));

(cljs.core.async.t_cljs$core$async67298.getBasis = (function (){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"f","f",43394975,null),new cljs.core.Symbol(null,"ch","ch",1085813622,null),new cljs.core.Symbol(null,"meta67299","meta67299",-1027409019,null)], null);
}));

(cljs.core.async.t_cljs$core$async67298.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async67298.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async67298");

(cljs.core.async.t_cljs$core$async67298.cljs$lang$ctorPrWriter = (function (this__5327__auto__,writer__5328__auto__,opt__5329__auto__){
return cljs.core._write(writer__5328__auto__,"cljs.core.async/t_cljs$core$async67298");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async67298.
 */
cljs.core.async.__GT_t_cljs$core$async67298 = (function cljs$core$async$map_GT__$___GT_t_cljs$core$async67298(f__$1,ch__$1,meta67299){
return (new cljs.core.async.t_cljs$core$async67298(f__$1,ch__$1,meta67299));
});

}

return (new cljs.core.async.t_cljs$core$async67298(f,ch,cljs.core.PersistentArrayMap.EMPTY));
});
/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.filter_GT_ = (function cljs$core$async$filter_GT_(p,ch){
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async67321 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.impl.protocols.Channel}
 * @implements {cljs.core.async.impl.protocols.WritePort}
 * @implements {cljs.core.async.impl.protocols.ReadPort}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async67321 = (function (p,ch,meta67322){
this.p = p;
this.ch = ch;
this.meta67322 = meta67322;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async67321.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_67323,meta67322__$1){
var self__ = this;
var _67323__$1 = this;
return (new cljs.core.async.t_cljs$core$async67321(self__.p,self__.ch,meta67322__$1));
}));

(cljs.core.async.t_cljs$core$async67321.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_67323){
var self__ = this;
var _67323__$1 = this;
return self__.meta67322;
}));

(cljs.core.async.t_cljs$core$async67321.prototype.cljs$core$async$impl$protocols$Channel$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async67321.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.close_BANG_(self__.ch);
}));

(cljs.core.async.t_cljs$core$async67321.prototype.cljs$core$async$impl$protocols$Channel$closed_QMARK_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.closed_QMARK_(self__.ch);
}));

(cljs.core.async.t_cljs$core$async67321.prototype.cljs$core$async$impl$protocols$ReadPort$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async67321.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.take_BANG_(self__.ch,fn1);
}));

(cljs.core.async.t_cljs$core$async67321.prototype.cljs$core$async$impl$protocols$WritePort$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async67321.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){
var self__ = this;
var ___$1 = this;
if(cljs.core.truth_((self__.p.cljs$core$IFn$_invoke$arity$1 ? self__.p.cljs$core$IFn$_invoke$arity$1(val) : self__.p.call(null,val)))){
return cljs.core.async.impl.protocols.put_BANG_(self__.ch,val,fn1);
} else {
return cljs.core.async.impl.channels.box(cljs.core.not(cljs.core.async.impl.protocols.closed_QMARK_(self__.ch)));
}
}));

(cljs.core.async.t_cljs$core$async67321.getBasis = (function (){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"p","p",1791580836,null),new cljs.core.Symbol(null,"ch","ch",1085813622,null),new cljs.core.Symbol(null,"meta67322","meta67322",-297398791,null)], null);
}));

(cljs.core.async.t_cljs$core$async67321.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async67321.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async67321");

(cljs.core.async.t_cljs$core$async67321.cljs$lang$ctorPrWriter = (function (this__5327__auto__,writer__5328__auto__,opt__5329__auto__){
return cljs.core._write(writer__5328__auto__,"cljs.core.async/t_cljs$core$async67321");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async67321.
 */
cljs.core.async.__GT_t_cljs$core$async67321 = (function cljs$core$async$filter_GT__$___GT_t_cljs$core$async67321(p__$1,ch__$1,meta67322){
return (new cljs.core.async.t_cljs$core$async67321(p__$1,ch__$1,meta67322));
});

}

return (new cljs.core.async.t_cljs$core$async67321(p,ch,cljs.core.PersistentArrayMap.EMPTY));
});
/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.remove_GT_ = (function cljs$core$async$remove_GT_(p,ch){
return cljs.core.async.filter_GT_(cljs.core.complement(p),ch);
});
/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.filter_LT_ = (function cljs$core$async$filter_LT_(var_args){
var G__67351 = arguments.length;
switch (G__67351) {
case 2:
return cljs.core.async.filter_LT_.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljs.core.async.filter_LT_.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.filter_LT_.cljs$core$IFn$_invoke$arity$2 = (function (p,ch){
return cljs.core.async.filter_LT_.cljs$core$IFn$_invoke$arity$3(p,ch,null);
}));

(cljs.core.async.filter_LT_.cljs$core$IFn$_invoke$arity$3 = (function (p,ch,buf_or_n){
var out = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);
var c__35508__auto___68559 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_67377){
var state_val_67378 = (state_67377[(1)]);
if((state_val_67378 === (7))){
var inst_67373 = (state_67377[(2)]);
var state_67377__$1 = state_67377;
var statearr_67383_68560 = state_67377__$1;
(statearr_67383_68560[(2)] = inst_67373);

(statearr_67383_68560[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67378 === (1))){
var state_67377__$1 = state_67377;
var statearr_67384_68561 = state_67377__$1;
(statearr_67384_68561[(2)] = null);

(statearr_67384_68561[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67378 === (4))){
var inst_67358 = (state_67377[(7)]);
var inst_67358__$1 = (state_67377[(2)]);
var inst_67359 = (inst_67358__$1 == null);
var state_67377__$1 = (function (){var statearr_67388 = state_67377;
(statearr_67388[(7)] = inst_67358__$1);

return statearr_67388;
})();
if(cljs.core.truth_(inst_67359)){
var statearr_67389_68562 = state_67377__$1;
(statearr_67389_68562[(1)] = (5));

} else {
var statearr_67390_68563 = state_67377__$1;
(statearr_67390_68563[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67378 === (6))){
var inst_67358 = (state_67377[(7)]);
var inst_67363 = (p.cljs$core$IFn$_invoke$arity$1 ? p.cljs$core$IFn$_invoke$arity$1(inst_67358) : p.call(null,inst_67358));
var state_67377__$1 = state_67377;
if(cljs.core.truth_(inst_67363)){
var statearr_67392_68564 = state_67377__$1;
(statearr_67392_68564[(1)] = (8));

} else {
var statearr_67393_68565 = state_67377__$1;
(statearr_67393_68565[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67378 === (3))){
var inst_67375 = (state_67377[(2)]);
var state_67377__$1 = state_67377;
return cljs.core.async.impl.ioc_helpers.return_chan(state_67377__$1,inst_67375);
} else {
if((state_val_67378 === (2))){
var state_67377__$1 = state_67377;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_67377__$1,(4),ch);
} else {
if((state_val_67378 === (11))){
var inst_67367 = (state_67377[(2)]);
var state_67377__$1 = state_67377;
var statearr_67397_68566 = state_67377__$1;
(statearr_67397_68566[(2)] = inst_67367);

(statearr_67397_68566[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67378 === (9))){
var state_67377__$1 = state_67377;
var statearr_67401_68567 = state_67377__$1;
(statearr_67401_68567[(2)] = null);

(statearr_67401_68567[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67378 === (5))){
var inst_67361 = cljs.core.async.close_BANG_(out);
var state_67377__$1 = state_67377;
var statearr_67405_68568 = state_67377__$1;
(statearr_67405_68568[(2)] = inst_67361);

(statearr_67405_68568[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67378 === (10))){
var inst_67370 = (state_67377[(2)]);
var state_67377__$1 = (function (){var statearr_67407 = state_67377;
(statearr_67407[(8)] = inst_67370);

return statearr_67407;
})();
var statearr_67414_68569 = state_67377__$1;
(statearr_67414_68569[(2)] = null);

(statearr_67414_68569[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67378 === (8))){
var inst_67358 = (state_67377[(7)]);
var state_67377__$1 = state_67377;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_67377__$1,(11),out,inst_67358);
} else {
return null;
}
}
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$state_machine__35424__auto__ = null;
var cljs$core$async$state_machine__35424__auto____0 = (function (){
var statearr_67424 = [null,null,null,null,null,null,null,null,null];
(statearr_67424[(0)] = cljs$core$async$state_machine__35424__auto__);

(statearr_67424[(1)] = (1));

return statearr_67424;
});
var cljs$core$async$state_machine__35424__auto____1 = (function (state_67377){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_67377);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e67425){var ex__35427__auto__ = e67425;
var statearr_67426_68570 = state_67377;
(statearr_67426_68570[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_67377[(4)]))){
var statearr_67427_68571 = state_67377;
(statearr_67427_68571[(1)] = cljs.core.first((state_67377[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68573 = state_67377;
state_67377 = G__68573;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$state_machine__35424__auto__ = function(state_67377){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__35424__auto____1.call(this,state_67377);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__35424__auto____0;
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__35424__auto____1;
return cljs$core$async$state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_67428 = f__35509__auto__();
(statearr_67428[(6)] = c__35508__auto___68559);

return statearr_67428;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));


return out;
}));

(cljs.core.async.filter_LT_.cljs$lang$maxFixedArity = 3);

/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.remove_LT_ = (function cljs$core$async$remove_LT_(var_args){
var G__67434 = arguments.length;
switch (G__67434) {
case 2:
return cljs.core.async.remove_LT_.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljs.core.async.remove_LT_.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.remove_LT_.cljs$core$IFn$_invoke$arity$2 = (function (p,ch){
return cljs.core.async.remove_LT_.cljs$core$IFn$_invoke$arity$3(p,ch,null);
}));

(cljs.core.async.remove_LT_.cljs$core$IFn$_invoke$arity$3 = (function (p,ch,buf_or_n){
return cljs.core.async.filter_LT_.cljs$core$IFn$_invoke$arity$3(cljs.core.complement(p),ch,buf_or_n);
}));

(cljs.core.async.remove_LT_.cljs$lang$maxFixedArity = 3);

cljs.core.async.mapcat_STAR_ = (function cljs$core$async$mapcat_STAR_(f,in$,out){
var c__35508__auto__ = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_67512){
var state_val_67513 = (state_67512[(1)]);
if((state_val_67513 === (7))){
var inst_67508 = (state_67512[(2)]);
var state_67512__$1 = state_67512;
var statearr_67515_68575 = state_67512__$1;
(statearr_67515_68575[(2)] = inst_67508);

(statearr_67515_68575[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (20))){
var inst_67473 = (state_67512[(7)]);
var inst_67485 = (state_67512[(2)]);
var inst_67489 = cljs.core.next(inst_67473);
var inst_67456 = inst_67489;
var inst_67457 = null;
var inst_67458 = (0);
var inst_67459 = (0);
var state_67512__$1 = (function (){var statearr_67516 = state_67512;
(statearr_67516[(8)] = inst_67459);

(statearr_67516[(9)] = inst_67457);

(statearr_67516[(10)] = inst_67458);

(statearr_67516[(11)] = inst_67456);

(statearr_67516[(12)] = inst_67485);

return statearr_67516;
})();
var statearr_67517_68576 = state_67512__$1;
(statearr_67517_68576[(2)] = null);

(statearr_67517_68576[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (1))){
var state_67512__$1 = state_67512;
var statearr_67518_68577 = state_67512__$1;
(statearr_67518_68577[(2)] = null);

(statearr_67518_68577[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (4))){
var inst_67444 = (state_67512[(13)]);
var inst_67444__$1 = (state_67512[(2)]);
var inst_67446 = (inst_67444__$1 == null);
var state_67512__$1 = (function (){var statearr_67523 = state_67512;
(statearr_67523[(13)] = inst_67444__$1);

return statearr_67523;
})();
if(cljs.core.truth_(inst_67446)){
var statearr_67527_68579 = state_67512__$1;
(statearr_67527_68579[(1)] = (5));

} else {
var statearr_67528_68581 = state_67512__$1;
(statearr_67528_68581[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (15))){
var state_67512__$1 = state_67512;
var statearr_67533_68582 = state_67512__$1;
(statearr_67533_68582[(2)] = null);

(statearr_67533_68582[(1)] = (16));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (21))){
var state_67512__$1 = state_67512;
var statearr_67534_68583 = state_67512__$1;
(statearr_67534_68583[(2)] = null);

(statearr_67534_68583[(1)] = (23));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (13))){
var inst_67459 = (state_67512[(8)]);
var inst_67457 = (state_67512[(9)]);
var inst_67458 = (state_67512[(10)]);
var inst_67456 = (state_67512[(11)]);
var inst_67466 = (state_67512[(2)]);
var inst_67470 = (inst_67459 + (1));
var tmp67529 = inst_67457;
var tmp67530 = inst_67458;
var tmp67531 = inst_67456;
var inst_67456__$1 = tmp67531;
var inst_67457__$1 = tmp67529;
var inst_67458__$1 = tmp67530;
var inst_67459__$1 = inst_67470;
var state_67512__$1 = (function (){var statearr_67535 = state_67512;
(statearr_67535[(8)] = inst_67459__$1);

(statearr_67535[(9)] = inst_67457__$1);

(statearr_67535[(10)] = inst_67458__$1);

(statearr_67535[(11)] = inst_67456__$1);

(statearr_67535[(14)] = inst_67466);

return statearr_67535;
})();
var statearr_67536_68588 = state_67512__$1;
(statearr_67536_68588[(2)] = null);

(statearr_67536_68588[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (22))){
var state_67512__$1 = state_67512;
var statearr_67540_68589 = state_67512__$1;
(statearr_67540_68589[(2)] = null);

(statearr_67540_68589[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (6))){
var inst_67444 = (state_67512[(13)]);
var inst_67454 = (f.cljs$core$IFn$_invoke$arity$1 ? f.cljs$core$IFn$_invoke$arity$1(inst_67444) : f.call(null,inst_67444));
var inst_67455 = cljs.core.seq(inst_67454);
var inst_67456 = inst_67455;
var inst_67457 = null;
var inst_67458 = (0);
var inst_67459 = (0);
var state_67512__$1 = (function (){var statearr_67542 = state_67512;
(statearr_67542[(8)] = inst_67459);

(statearr_67542[(9)] = inst_67457);

(statearr_67542[(10)] = inst_67458);

(statearr_67542[(11)] = inst_67456);

return statearr_67542;
})();
var statearr_67543_68592 = state_67512__$1;
(statearr_67543_68592[(2)] = null);

(statearr_67543_68592[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (17))){
var inst_67473 = (state_67512[(7)]);
var inst_67478 = cljs.core.chunk_first(inst_67473);
var inst_67479 = cljs.core.chunk_rest(inst_67473);
var inst_67480 = cljs.core.count(inst_67478);
var inst_67456 = inst_67479;
var inst_67457 = inst_67478;
var inst_67458 = inst_67480;
var inst_67459 = (0);
var state_67512__$1 = (function (){var statearr_67548 = state_67512;
(statearr_67548[(8)] = inst_67459);

(statearr_67548[(9)] = inst_67457);

(statearr_67548[(10)] = inst_67458);

(statearr_67548[(11)] = inst_67456);

return statearr_67548;
})();
var statearr_67549_68593 = state_67512__$1;
(statearr_67549_68593[(2)] = null);

(statearr_67549_68593[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (3))){
var inst_67510 = (state_67512[(2)]);
var state_67512__$1 = state_67512;
return cljs.core.async.impl.ioc_helpers.return_chan(state_67512__$1,inst_67510);
} else {
if((state_val_67513 === (12))){
var inst_67497 = (state_67512[(2)]);
var state_67512__$1 = state_67512;
var statearr_67553_68594 = state_67512__$1;
(statearr_67553_68594[(2)] = inst_67497);

(statearr_67553_68594[(1)] = (9));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (2))){
var state_67512__$1 = state_67512;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_67512__$1,(4),in$);
} else {
if((state_val_67513 === (23))){
var inst_67506 = (state_67512[(2)]);
var state_67512__$1 = state_67512;
var statearr_67555_68595 = state_67512__$1;
(statearr_67555_68595[(2)] = inst_67506);

(statearr_67555_68595[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (19))){
var inst_67492 = (state_67512[(2)]);
var state_67512__$1 = state_67512;
var statearr_67556_68597 = state_67512__$1;
(statearr_67556_68597[(2)] = inst_67492);

(statearr_67556_68597[(1)] = (16));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (11))){
var inst_67456 = (state_67512[(11)]);
var inst_67473 = (state_67512[(7)]);
var inst_67473__$1 = cljs.core.seq(inst_67456);
var state_67512__$1 = (function (){var statearr_67558 = state_67512;
(statearr_67558[(7)] = inst_67473__$1);

return statearr_67558;
})();
if(inst_67473__$1){
var statearr_67559_68598 = state_67512__$1;
(statearr_67559_68598[(1)] = (14));

} else {
var statearr_67560_68599 = state_67512__$1;
(statearr_67560_68599[(1)] = (15));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (9))){
var inst_67499 = (state_67512[(2)]);
var inst_67500 = cljs.core.async.impl.protocols.closed_QMARK_(out);
var state_67512__$1 = (function (){var statearr_67561 = state_67512;
(statearr_67561[(15)] = inst_67499);

return statearr_67561;
})();
if(cljs.core.truth_(inst_67500)){
var statearr_67562_68600 = state_67512__$1;
(statearr_67562_68600[(1)] = (21));

} else {
var statearr_67567_68601 = state_67512__$1;
(statearr_67567_68601[(1)] = (22));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (5))){
var inst_67448 = cljs.core.async.close_BANG_(out);
var state_67512__$1 = state_67512;
var statearr_67571_68602 = state_67512__$1;
(statearr_67571_68602[(2)] = inst_67448);

(statearr_67571_68602[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (14))){
var inst_67473 = (state_67512[(7)]);
var inst_67476 = cljs.core.chunked_seq_QMARK_(inst_67473);
var state_67512__$1 = state_67512;
if(inst_67476){
var statearr_67573_68613 = state_67512__$1;
(statearr_67573_68613[(1)] = (17));

} else {
var statearr_67574_68617 = state_67512__$1;
(statearr_67574_68617[(1)] = (18));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (16))){
var inst_67495 = (state_67512[(2)]);
var state_67512__$1 = state_67512;
var statearr_67575_68618 = state_67512__$1;
(statearr_67575_68618[(2)] = inst_67495);

(statearr_67575_68618[(1)] = (12));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67513 === (10))){
var inst_67459 = (state_67512[(8)]);
var inst_67457 = (state_67512[(9)]);
var inst_67464 = cljs.core._nth(inst_67457,inst_67459);
var state_67512__$1 = state_67512;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_67512__$1,(13),out,inst_67464);
} else {
if((state_val_67513 === (18))){
var inst_67473 = (state_67512[(7)]);
var inst_67483 = cljs.core.first(inst_67473);
var state_67512__$1 = state_67512;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_67512__$1,(20),out,inst_67483);
} else {
if((state_val_67513 === (8))){
var inst_67459 = (state_67512[(8)]);
var inst_67458 = (state_67512[(10)]);
var inst_67461 = (inst_67459 < inst_67458);
var inst_67462 = inst_67461;
var state_67512__$1 = state_67512;
if(cljs.core.truth_(inst_67462)){
var statearr_67583_68619 = state_67512__$1;
(statearr_67583_68619[(1)] = (10));

} else {
var statearr_67584_68620 = state_67512__$1;
(statearr_67584_68620[(1)] = (11));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$mapcat_STAR__$_state_machine__35424__auto__ = null;
var cljs$core$async$mapcat_STAR__$_state_machine__35424__auto____0 = (function (){
var statearr_67586 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_67586[(0)] = cljs$core$async$mapcat_STAR__$_state_machine__35424__auto__);

(statearr_67586[(1)] = (1));

return statearr_67586;
});
var cljs$core$async$mapcat_STAR__$_state_machine__35424__auto____1 = (function (state_67512){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_67512);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e67591){var ex__35427__auto__ = e67591;
var statearr_67592_68622 = state_67512;
(statearr_67592_68622[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_67512[(4)]))){
var statearr_67593_68623 = state_67512;
(statearr_67593_68623[(1)] = cljs.core.first((state_67512[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68624 = state_67512;
state_67512 = G__68624;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$mapcat_STAR__$_state_machine__35424__auto__ = function(state_67512){
switch(arguments.length){
case 0:
return cljs$core$async$mapcat_STAR__$_state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$mapcat_STAR__$_state_machine__35424__auto____1.call(this,state_67512);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$mapcat_STAR__$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$mapcat_STAR__$_state_machine__35424__auto____0;
cljs$core$async$mapcat_STAR__$_state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$mapcat_STAR__$_state_machine__35424__auto____1;
return cljs$core$async$mapcat_STAR__$_state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_67594 = f__35509__auto__();
(statearr_67594[(6)] = c__35508__auto__);

return statearr_67594;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));

return c__35508__auto__;
});
/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.mapcat_LT_ = (function cljs$core$async$mapcat_LT_(var_args){
var G__67597 = arguments.length;
switch (G__67597) {
case 2:
return cljs.core.async.mapcat_LT_.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljs.core.async.mapcat_LT_.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.mapcat_LT_.cljs$core$IFn$_invoke$arity$2 = (function (f,in$){
return cljs.core.async.mapcat_LT_.cljs$core$IFn$_invoke$arity$3(f,in$,null);
}));

(cljs.core.async.mapcat_LT_.cljs$core$IFn$_invoke$arity$3 = (function (f,in$,buf_or_n){
var out = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);
cljs.core.async.mapcat_STAR_(f,in$,out);

return out;
}));

(cljs.core.async.mapcat_LT_.cljs$lang$maxFixedArity = 3);

/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.mapcat_GT_ = (function cljs$core$async$mapcat_GT_(var_args){
var G__67610 = arguments.length;
switch (G__67610) {
case 2:
return cljs.core.async.mapcat_GT_.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljs.core.async.mapcat_GT_.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.mapcat_GT_.cljs$core$IFn$_invoke$arity$2 = (function (f,out){
return cljs.core.async.mapcat_GT_.cljs$core$IFn$_invoke$arity$3(f,out,null);
}));

(cljs.core.async.mapcat_GT_.cljs$core$IFn$_invoke$arity$3 = (function (f,out,buf_or_n){
var in$ = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);
cljs.core.async.mapcat_STAR_(f,in$,out);

return in$;
}));

(cljs.core.async.mapcat_GT_.cljs$lang$maxFixedArity = 3);

/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.unique = (function cljs$core$async$unique(var_args){
var G__67617 = arguments.length;
switch (G__67617) {
case 1:
return cljs.core.async.unique.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return cljs.core.async.unique.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.unique.cljs$core$IFn$_invoke$arity$1 = (function (ch){
return cljs.core.async.unique.cljs$core$IFn$_invoke$arity$2(ch,null);
}));

(cljs.core.async.unique.cljs$core$IFn$_invoke$arity$2 = (function (ch,buf_or_n){
var out = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);
var c__35508__auto___68628 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_67645){
var state_val_67646 = (state_67645[(1)]);
if((state_val_67646 === (7))){
var inst_67640 = (state_67645[(2)]);
var state_67645__$1 = state_67645;
var statearr_67652_68629 = state_67645__$1;
(statearr_67652_68629[(2)] = inst_67640);

(statearr_67652_68629[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67646 === (1))){
var inst_67622 = null;
var state_67645__$1 = (function (){var statearr_67653 = state_67645;
(statearr_67653[(7)] = inst_67622);

return statearr_67653;
})();
var statearr_67654_68630 = state_67645__$1;
(statearr_67654_68630[(2)] = null);

(statearr_67654_68630[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67646 === (4))){
var inst_67625 = (state_67645[(8)]);
var inst_67625__$1 = (state_67645[(2)]);
var inst_67626 = (inst_67625__$1 == null);
var inst_67627 = cljs.core.not(inst_67626);
var state_67645__$1 = (function (){var statearr_67655 = state_67645;
(statearr_67655[(8)] = inst_67625__$1);

return statearr_67655;
})();
if(inst_67627){
var statearr_67656_68631 = state_67645__$1;
(statearr_67656_68631[(1)] = (5));

} else {
var statearr_67657_68632 = state_67645__$1;
(statearr_67657_68632[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67646 === (6))){
var state_67645__$1 = state_67645;
var statearr_67658_68633 = state_67645__$1;
(statearr_67658_68633[(2)] = null);

(statearr_67658_68633[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67646 === (3))){
var inst_67642 = (state_67645[(2)]);
var inst_67643 = cljs.core.async.close_BANG_(out);
var state_67645__$1 = (function (){var statearr_67659 = state_67645;
(statearr_67659[(9)] = inst_67642);

return statearr_67659;
})();
return cljs.core.async.impl.ioc_helpers.return_chan(state_67645__$1,inst_67643);
} else {
if((state_val_67646 === (2))){
var state_67645__$1 = state_67645;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_67645__$1,(4),ch);
} else {
if((state_val_67646 === (11))){
var inst_67625 = (state_67645[(8)]);
var inst_67634 = (state_67645[(2)]);
var inst_67622 = inst_67625;
var state_67645__$1 = (function (){var statearr_67661 = state_67645;
(statearr_67661[(7)] = inst_67622);

(statearr_67661[(10)] = inst_67634);

return statearr_67661;
})();
var statearr_67662_68634 = state_67645__$1;
(statearr_67662_68634[(2)] = null);

(statearr_67662_68634[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67646 === (9))){
var inst_67625 = (state_67645[(8)]);
var state_67645__$1 = state_67645;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_67645__$1,(11),out,inst_67625);
} else {
if((state_val_67646 === (5))){
var inst_67622 = (state_67645[(7)]);
var inst_67625 = (state_67645[(8)]);
var inst_67629 = cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(inst_67625,inst_67622);
var state_67645__$1 = state_67645;
if(inst_67629){
var statearr_67665_68635 = state_67645__$1;
(statearr_67665_68635[(1)] = (8));

} else {
var statearr_67666_68637 = state_67645__$1;
(statearr_67666_68637[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67646 === (10))){
var inst_67637 = (state_67645[(2)]);
var state_67645__$1 = state_67645;
var statearr_67667_68638 = state_67645__$1;
(statearr_67667_68638[(2)] = inst_67637);

(statearr_67667_68638[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67646 === (8))){
var inst_67622 = (state_67645[(7)]);
var tmp67664 = inst_67622;
var inst_67622__$1 = tmp67664;
var state_67645__$1 = (function (){var statearr_67668 = state_67645;
(statearr_67668[(7)] = inst_67622__$1);

return statearr_67668;
})();
var statearr_67669_68639 = state_67645__$1;
(statearr_67669_68639[(2)] = null);

(statearr_67669_68639[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$state_machine__35424__auto__ = null;
var cljs$core$async$state_machine__35424__auto____0 = (function (){
var statearr_67670 = [null,null,null,null,null,null,null,null,null,null,null];
(statearr_67670[(0)] = cljs$core$async$state_machine__35424__auto__);

(statearr_67670[(1)] = (1));

return statearr_67670;
});
var cljs$core$async$state_machine__35424__auto____1 = (function (state_67645){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_67645);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e67671){var ex__35427__auto__ = e67671;
var statearr_67672_68645 = state_67645;
(statearr_67672_68645[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_67645[(4)]))){
var statearr_67673_68646 = state_67645;
(statearr_67673_68646[(1)] = cljs.core.first((state_67645[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68647 = state_67645;
state_67645 = G__68647;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$state_machine__35424__auto__ = function(state_67645){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__35424__auto____1.call(this,state_67645);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__35424__auto____0;
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__35424__auto____1;
return cljs$core$async$state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_67674 = f__35509__auto__();
(statearr_67674[(6)] = c__35508__auto___68628);

return statearr_67674;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));


return out;
}));

(cljs.core.async.unique.cljs$lang$maxFixedArity = 2);

/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.partition = (function cljs$core$async$partition(var_args){
var G__67676 = arguments.length;
switch (G__67676) {
case 2:
return cljs.core.async.partition.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljs.core.async.partition.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.partition.cljs$core$IFn$_invoke$arity$2 = (function (n,ch){
return cljs.core.async.partition.cljs$core$IFn$_invoke$arity$3(n,ch,null);
}));

(cljs.core.async.partition.cljs$core$IFn$_invoke$arity$3 = (function (n,ch,buf_or_n){
var out = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);
var c__35508__auto___68650 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_67714){
var state_val_67715 = (state_67714[(1)]);
if((state_val_67715 === (7))){
var inst_67710 = (state_67714[(2)]);
var state_67714__$1 = state_67714;
var statearr_67716_68651 = state_67714__$1;
(statearr_67716_68651[(2)] = inst_67710);

(statearr_67716_68651[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67715 === (1))){
var inst_67677 = (new Array(n));
var inst_67678 = inst_67677;
var inst_67679 = (0);
var state_67714__$1 = (function (){var statearr_67717 = state_67714;
(statearr_67717[(7)] = inst_67679);

(statearr_67717[(8)] = inst_67678);

return statearr_67717;
})();
var statearr_67718_68653 = state_67714__$1;
(statearr_67718_68653[(2)] = null);

(statearr_67718_68653[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67715 === (4))){
var inst_67682 = (state_67714[(9)]);
var inst_67682__$1 = (state_67714[(2)]);
var inst_67683 = (inst_67682__$1 == null);
var inst_67684 = cljs.core.not(inst_67683);
var state_67714__$1 = (function (){var statearr_67719 = state_67714;
(statearr_67719[(9)] = inst_67682__$1);

return statearr_67719;
})();
if(inst_67684){
var statearr_67720_68654 = state_67714__$1;
(statearr_67720_68654[(1)] = (5));

} else {
var statearr_67721_68655 = state_67714__$1;
(statearr_67721_68655[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67715 === (15))){
var inst_67704 = (state_67714[(2)]);
var state_67714__$1 = state_67714;
var statearr_67722_68656 = state_67714__$1;
(statearr_67722_68656[(2)] = inst_67704);

(statearr_67722_68656[(1)] = (14));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67715 === (13))){
var state_67714__$1 = state_67714;
var statearr_67724_68657 = state_67714__$1;
(statearr_67724_68657[(2)] = null);

(statearr_67724_68657[(1)] = (14));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67715 === (6))){
var inst_67679 = (state_67714[(7)]);
var inst_67700 = (inst_67679 > (0));
var state_67714__$1 = state_67714;
if(cljs.core.truth_(inst_67700)){
var statearr_67726_68658 = state_67714__$1;
(statearr_67726_68658[(1)] = (12));

} else {
var statearr_67727_68659 = state_67714__$1;
(statearr_67727_68659[(1)] = (13));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67715 === (3))){
var inst_67712 = (state_67714[(2)]);
var state_67714__$1 = state_67714;
return cljs.core.async.impl.ioc_helpers.return_chan(state_67714__$1,inst_67712);
} else {
if((state_val_67715 === (12))){
var inst_67678 = (state_67714[(8)]);
var inst_67702 = cljs.core.vec(inst_67678);
var state_67714__$1 = state_67714;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_67714__$1,(15),out,inst_67702);
} else {
if((state_val_67715 === (2))){
var state_67714__$1 = state_67714;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_67714__$1,(4),ch);
} else {
if((state_val_67715 === (11))){
var inst_67694 = (state_67714[(2)]);
var inst_67695 = (new Array(n));
var inst_67678 = inst_67695;
var inst_67679 = (0);
var state_67714__$1 = (function (){var statearr_67728 = state_67714;
(statearr_67728[(7)] = inst_67679);

(statearr_67728[(10)] = inst_67694);

(statearr_67728[(8)] = inst_67678);

return statearr_67728;
})();
var statearr_67729_68660 = state_67714__$1;
(statearr_67729_68660[(2)] = null);

(statearr_67729_68660[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67715 === (9))){
var inst_67678 = (state_67714[(8)]);
var inst_67692 = cljs.core.vec(inst_67678);
var state_67714__$1 = state_67714;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_67714__$1,(11),out,inst_67692);
} else {
if((state_val_67715 === (5))){
var inst_67687 = (state_67714[(11)]);
var inst_67679 = (state_67714[(7)]);
var inst_67682 = (state_67714[(9)]);
var inst_67678 = (state_67714[(8)]);
var inst_67686 = (inst_67678[inst_67679] = inst_67682);
var inst_67687__$1 = (inst_67679 + (1));
var inst_67688 = (inst_67687__$1 < n);
var state_67714__$1 = (function (){var statearr_67730 = state_67714;
(statearr_67730[(11)] = inst_67687__$1);

(statearr_67730[(12)] = inst_67686);

return statearr_67730;
})();
if(cljs.core.truth_(inst_67688)){
var statearr_67731_68662 = state_67714__$1;
(statearr_67731_68662[(1)] = (8));

} else {
var statearr_67732_68663 = state_67714__$1;
(statearr_67732_68663[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67715 === (14))){
var inst_67707 = (state_67714[(2)]);
var inst_67708 = cljs.core.async.close_BANG_(out);
var state_67714__$1 = (function (){var statearr_67736 = state_67714;
(statearr_67736[(13)] = inst_67707);

return statearr_67736;
})();
var statearr_67737_68665 = state_67714__$1;
(statearr_67737_68665[(2)] = inst_67708);

(statearr_67737_68665[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67715 === (10))){
var inst_67698 = (state_67714[(2)]);
var state_67714__$1 = state_67714;
var statearr_67738_68672 = state_67714__$1;
(statearr_67738_68672[(2)] = inst_67698);

(statearr_67738_68672[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67715 === (8))){
var inst_67687 = (state_67714[(11)]);
var inst_67678 = (state_67714[(8)]);
var tmp67734 = inst_67678;
var inst_67678__$1 = tmp67734;
var inst_67679 = inst_67687;
var state_67714__$1 = (function (){var statearr_67739 = state_67714;
(statearr_67739[(7)] = inst_67679);

(statearr_67739[(8)] = inst_67678__$1);

return statearr_67739;
})();
var statearr_67740_68673 = state_67714__$1;
(statearr_67740_68673[(2)] = null);

(statearr_67740_68673[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$state_machine__35424__auto__ = null;
var cljs$core$async$state_machine__35424__auto____0 = (function (){
var statearr_67741 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_67741[(0)] = cljs$core$async$state_machine__35424__auto__);

(statearr_67741[(1)] = (1));

return statearr_67741;
});
var cljs$core$async$state_machine__35424__auto____1 = (function (state_67714){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_67714);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e67742){var ex__35427__auto__ = e67742;
var statearr_67743_68675 = state_67714;
(statearr_67743_68675[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_67714[(4)]))){
var statearr_67745_68676 = state_67714;
(statearr_67745_68676[(1)] = cljs.core.first((state_67714[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68677 = state_67714;
state_67714 = G__68677;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$state_machine__35424__auto__ = function(state_67714){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__35424__auto____1.call(this,state_67714);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__35424__auto____0;
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__35424__auto____1;
return cljs$core$async$state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_67747 = f__35509__auto__();
(statearr_67747[(6)] = c__35508__auto___68650);

return statearr_67747;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));


return out;
}));

(cljs.core.async.partition.cljs$lang$maxFixedArity = 3);

/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.partition_by = (function cljs$core$async$partition_by(var_args){
var G__67749 = arguments.length;
switch (G__67749) {
case 2:
return cljs.core.async.partition_by.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return cljs.core.async.partition_by.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(cljs.core.async.partition_by.cljs$core$IFn$_invoke$arity$2 = (function (f,ch){
return cljs.core.async.partition_by.cljs$core$IFn$_invoke$arity$3(f,ch,null);
}));

(cljs.core.async.partition_by.cljs$core$IFn$_invoke$arity$3 = (function (f,ch,buf_or_n){
var out = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);
var c__35508__auto___68688 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__35509__auto__ = (function (){var switch__35423__auto__ = (function (state_67794){
var state_val_67795 = (state_67794[(1)]);
if((state_val_67795 === (7))){
var inst_67790 = (state_67794[(2)]);
var state_67794__$1 = state_67794;
var statearr_67798_68689 = state_67794__$1;
(statearr_67798_68689[(2)] = inst_67790);

(statearr_67798_68689[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67795 === (1))){
var inst_67750 = [];
var inst_67751 = inst_67750;
var inst_67752 = new cljs.core.Keyword("cljs.core.async","nothing","cljs.core.async/nothing",-69252123);
var state_67794__$1 = (function (){var statearr_67799 = state_67794;
(statearr_67799[(7)] = inst_67752);

(statearr_67799[(8)] = inst_67751);

return statearr_67799;
})();
var statearr_67800_68690 = state_67794__$1;
(statearr_67800_68690[(2)] = null);

(statearr_67800_68690[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67795 === (4))){
var inst_67755 = (state_67794[(9)]);
var inst_67755__$1 = (state_67794[(2)]);
var inst_67756 = (inst_67755__$1 == null);
var inst_67757 = cljs.core.not(inst_67756);
var state_67794__$1 = (function (){var statearr_67801 = state_67794;
(statearr_67801[(9)] = inst_67755__$1);

return statearr_67801;
})();
if(inst_67757){
var statearr_67802_68695 = state_67794__$1;
(statearr_67802_68695[(1)] = (5));

} else {
var statearr_67803_68696 = state_67794__$1;
(statearr_67803_68696[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67795 === (15))){
var inst_67751 = (state_67794[(8)]);
var inst_67782 = cljs.core.vec(inst_67751);
var state_67794__$1 = state_67794;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_67794__$1,(18),out,inst_67782);
} else {
if((state_val_67795 === (13))){
var inst_67777 = (state_67794[(2)]);
var state_67794__$1 = state_67794;
var statearr_67808_68697 = state_67794__$1;
(statearr_67808_68697[(2)] = inst_67777);

(statearr_67808_68697[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67795 === (6))){
var inst_67751 = (state_67794[(8)]);
var inst_67779 = inst_67751.length;
var inst_67780 = (inst_67779 > (0));
var state_67794__$1 = state_67794;
if(cljs.core.truth_(inst_67780)){
var statearr_67809_68698 = state_67794__$1;
(statearr_67809_68698[(1)] = (15));

} else {
var statearr_67810_68699 = state_67794__$1;
(statearr_67810_68699[(1)] = (16));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67795 === (17))){
var inst_67787 = (state_67794[(2)]);
var inst_67788 = cljs.core.async.close_BANG_(out);
var state_67794__$1 = (function (){var statearr_67812 = state_67794;
(statearr_67812[(10)] = inst_67787);

return statearr_67812;
})();
var statearr_67813_68707 = state_67794__$1;
(statearr_67813_68707[(2)] = inst_67788);

(statearr_67813_68707[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67795 === (3))){
var inst_67792 = (state_67794[(2)]);
var state_67794__$1 = state_67794;
return cljs.core.async.impl.ioc_helpers.return_chan(state_67794__$1,inst_67792);
} else {
if((state_val_67795 === (12))){
var inst_67751 = (state_67794[(8)]);
var inst_67770 = cljs.core.vec(inst_67751);
var state_67794__$1 = state_67794;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_67794__$1,(14),out,inst_67770);
} else {
if((state_val_67795 === (2))){
var state_67794__$1 = state_67794;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_67794__$1,(4),ch);
} else {
if((state_val_67795 === (11))){
var inst_67755 = (state_67794[(9)]);
var inst_67751 = (state_67794[(8)]);
var inst_67759 = (state_67794[(11)]);
var inst_67767 = inst_67751.push(inst_67755);
var tmp67815 = inst_67751;
var inst_67751__$1 = tmp67815;
var inst_67752 = inst_67759;
var state_67794__$1 = (function (){var statearr_67819 = state_67794;
(statearr_67819[(7)] = inst_67752);

(statearr_67819[(12)] = inst_67767);

(statearr_67819[(8)] = inst_67751__$1);

return statearr_67819;
})();
var statearr_67820_68709 = state_67794__$1;
(statearr_67820_68709[(2)] = null);

(statearr_67820_68709[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67795 === (9))){
var inst_67752 = (state_67794[(7)]);
var inst_67763 = cljs.core.keyword_identical_QMARK_(inst_67752,new cljs.core.Keyword("cljs.core.async","nothing","cljs.core.async/nothing",-69252123));
var state_67794__$1 = state_67794;
var statearr_67824_68714 = state_67794__$1;
(statearr_67824_68714[(2)] = inst_67763);

(statearr_67824_68714[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67795 === (5))){
var inst_67752 = (state_67794[(7)]);
var inst_67755 = (state_67794[(9)]);
var inst_67759 = (state_67794[(11)]);
var inst_67760 = (state_67794[(13)]);
var inst_67759__$1 = (f.cljs$core$IFn$_invoke$arity$1 ? f.cljs$core$IFn$_invoke$arity$1(inst_67755) : f.call(null,inst_67755));
var inst_67760__$1 = cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(inst_67759__$1,inst_67752);
var state_67794__$1 = (function (){var statearr_67825 = state_67794;
(statearr_67825[(11)] = inst_67759__$1);

(statearr_67825[(13)] = inst_67760__$1);

return statearr_67825;
})();
if(inst_67760__$1){
var statearr_67826_68715 = state_67794__$1;
(statearr_67826_68715[(1)] = (8));

} else {
var statearr_67827_68716 = state_67794__$1;
(statearr_67827_68716[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67795 === (14))){
var inst_67755 = (state_67794[(9)]);
var inst_67759 = (state_67794[(11)]);
var inst_67772 = (state_67794[(2)]);
var inst_67773 = [];
var inst_67774 = inst_67773.push(inst_67755);
var inst_67751 = inst_67773;
var inst_67752 = inst_67759;
var state_67794__$1 = (function (){var statearr_67828 = state_67794;
(statearr_67828[(7)] = inst_67752);

(statearr_67828[(8)] = inst_67751);

(statearr_67828[(14)] = inst_67774);

(statearr_67828[(15)] = inst_67772);

return statearr_67828;
})();
var statearr_67829_68721 = state_67794__$1;
(statearr_67829_68721[(2)] = null);

(statearr_67829_68721[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67795 === (16))){
var state_67794__$1 = state_67794;
var statearr_67830_68722 = state_67794__$1;
(statearr_67830_68722[(2)] = null);

(statearr_67830_68722[(1)] = (17));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67795 === (10))){
var inst_67765 = (state_67794[(2)]);
var state_67794__$1 = state_67794;
if(cljs.core.truth_(inst_67765)){
var statearr_67831_68725 = state_67794__$1;
(statearr_67831_68725[(1)] = (11));

} else {
var statearr_67835_68726 = state_67794__$1;
(statearr_67835_68726[(1)] = (12));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67795 === (18))){
var inst_67784 = (state_67794[(2)]);
var state_67794__$1 = state_67794;
var statearr_67840_68727 = state_67794__$1;
(statearr_67840_68727[(2)] = inst_67784);

(statearr_67840_68727[(1)] = (17));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_67795 === (8))){
var inst_67760 = (state_67794[(13)]);
var state_67794__$1 = state_67794;
var statearr_67847_68728 = state_67794__$1;
(statearr_67847_68728[(2)] = inst_67760);

(statearr_67847_68728[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});
return (function() {
var cljs$core$async$state_machine__35424__auto__ = null;
var cljs$core$async$state_machine__35424__auto____0 = (function (){
var statearr_67848 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_67848[(0)] = cljs$core$async$state_machine__35424__auto__);

(statearr_67848[(1)] = (1));

return statearr_67848;
});
var cljs$core$async$state_machine__35424__auto____1 = (function (state_67794){
while(true){
var ret_value__35425__auto__ = (function (){try{while(true){
var result__35426__auto__ = switch__35423__auto__(state_67794);
if(cljs.core.keyword_identical_QMARK_(result__35426__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__35426__auto__;
}
break;
}
}catch (e67855){var ex__35427__auto__ = e67855;
var statearr_67856_68736 = state_67794;
(statearr_67856_68736[(2)] = ex__35427__auto__);


if(cljs.core.seq((state_67794[(4)]))){
var statearr_67857_68737 = state_67794;
(statearr_67857_68737[(1)] = cljs.core.first((state_67794[(4)])));

} else {
throw ex__35427__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__35425__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__68738 = state_67794;
state_67794 = G__68738;
continue;
} else {
return ret_value__35425__auto__;
}
break;
}
});
cljs$core$async$state_machine__35424__auto__ = function(state_67794){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__35424__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__35424__auto____1.call(this,state_67794);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__35424__auto____0;
cljs$core$async$state_machine__35424__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__35424__auto____1;
return cljs$core$async$state_machine__35424__auto__;
})()
})();
var state__35510__auto__ = (function (){var statearr_67858 = f__35509__auto__();
(statearr_67858[(6)] = c__35508__auto___68688);

return statearr_67858;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__35510__auto__);
}));


return out;
}));

(cljs.core.async.partition_by.cljs$lang$maxFixedArity = 3);


//# sourceMappingURL=cljs.core.async.js.map
