goog.provide('cljs.core.async');
goog.scope(function(){
  cljs.core.async.goog$module$goog$array = goog.module.get('goog.array');
});
cljs.core.async.fn_handler = (function cljs$core$async$fn_handler(var_args){
var G__46625 = arguments.length;
switch (G__46625) {
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
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async46630 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.impl.protocols.Handler}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async46630 = (function (f,blockable,meta46631){
this.f = f;
this.blockable = blockable;
this.meta46631 = meta46631;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async46630.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_46632,meta46631__$1){
var self__ = this;
var _46632__$1 = this;
return (new cljs.core.async.t_cljs$core$async46630(self__.f,self__.blockable,meta46631__$1));
}));

(cljs.core.async.t_cljs$core$async46630.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_46632){
var self__ = this;
var _46632__$1 = this;
return self__.meta46631;
}));

(cljs.core.async.t_cljs$core$async46630.prototype.cljs$core$async$impl$protocols$Handler$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async46630.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return true;
}));

(cljs.core.async.t_cljs$core$async46630.prototype.cljs$core$async$impl$protocols$Handler$blockable_QMARK_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return self__.blockable;
}));

(cljs.core.async.t_cljs$core$async46630.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return self__.f;
}));

(cljs.core.async.t_cljs$core$async46630.getBasis = (function (){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"f","f",43394975,null),new cljs.core.Symbol(null,"blockable","blockable",-28395259,null),new cljs.core.Symbol(null,"meta46631","meta46631",1378958159,null)], null);
}));

(cljs.core.async.t_cljs$core$async46630.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async46630.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async46630");

(cljs.core.async.t_cljs$core$async46630.cljs$lang$ctorPrWriter = (function (this__4491__auto__,writer__4492__auto__,opt__4493__auto__){
return cljs.core._write(writer__4492__auto__,"cljs.core.async/t_cljs$core$async46630");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async46630.
 */
cljs.core.async.__GT_t_cljs$core$async46630 = (function cljs$core$async$__GT_t_cljs$core$async46630(f__$1,blockable__$1,meta46631){
return (new cljs.core.async.t_cljs$core$async46630(f__$1,blockable__$1,meta46631));
});

}

return (new cljs.core.async.t_cljs$core$async46630(f,blockable,cljs.core.PersistentArrayMap.EMPTY));
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
var G__46639 = arguments.length;
switch (G__46639) {
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
var G__46641 = arguments.length;
switch (G__46641) {
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
var G__46646 = arguments.length;
switch (G__46646) {
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
var val_48850 = cljs.core.deref(ret);
if(cljs.core.truth_(on_caller_QMARK_)){
(fn1.cljs$core$IFn$_invoke$arity$1 ? fn1.cljs$core$IFn$_invoke$arity$1(val_48850) : fn1.call(null,val_48850));
} else {
cljs.core.async.impl.dispatch.run((function (){
return (fn1.cljs$core$IFn$_invoke$arity$1 ? fn1.cljs$core$IFn$_invoke$arity$1(val_48850) : fn1.call(null,val_48850));
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
var G__46651 = arguments.length;
switch (G__46651) {
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
var n__4741__auto___48852 = n;
var x_48853 = (0);
while(true){
if((x_48853 < n__4741__auto___48852)){
(a[x_48853] = x_48853);

var G__48856 = (x_48853 + (1));
x_48853 = G__48856;
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
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async46664 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.impl.protocols.Handler}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async46664 = (function (flag,meta46665){
this.flag = flag;
this.meta46665 = meta46665;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async46664.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_46666,meta46665__$1){
var self__ = this;
var _46666__$1 = this;
return (new cljs.core.async.t_cljs$core$async46664(self__.flag,meta46665__$1));
}));

(cljs.core.async.t_cljs$core$async46664.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_46666){
var self__ = this;
var _46666__$1 = this;
return self__.meta46665;
}));

(cljs.core.async.t_cljs$core$async46664.prototype.cljs$core$async$impl$protocols$Handler$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async46664.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.deref(self__.flag);
}));

(cljs.core.async.t_cljs$core$async46664.prototype.cljs$core$async$impl$protocols$Handler$blockable_QMARK_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return true;
}));

(cljs.core.async.t_cljs$core$async46664.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
cljs.core.reset_BANG_(self__.flag,null);

return true;
}));

(cljs.core.async.t_cljs$core$async46664.getBasis = (function (){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"flag","flag",-1565787888,null),new cljs.core.Symbol(null,"meta46665","meta46665",-172878564,null)], null);
}));

(cljs.core.async.t_cljs$core$async46664.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async46664.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async46664");

(cljs.core.async.t_cljs$core$async46664.cljs$lang$ctorPrWriter = (function (this__4491__auto__,writer__4492__auto__,opt__4493__auto__){
return cljs.core._write(writer__4492__auto__,"cljs.core.async/t_cljs$core$async46664");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async46664.
 */
cljs.core.async.__GT_t_cljs$core$async46664 = (function cljs$core$async$alt_flag_$___GT_t_cljs$core$async46664(flag__$1,meta46665){
return (new cljs.core.async.t_cljs$core$async46664(flag__$1,meta46665));
});

}

return (new cljs.core.async.t_cljs$core$async46664(flag,cljs.core.PersistentArrayMap.EMPTY));
});
cljs.core.async.alt_handler = (function cljs$core$async$alt_handler(flag,cb){
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async46671 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.impl.protocols.Handler}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async46671 = (function (flag,cb,meta46672){
this.flag = flag;
this.cb = cb;
this.meta46672 = meta46672;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async46671.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_46673,meta46672__$1){
var self__ = this;
var _46673__$1 = this;
return (new cljs.core.async.t_cljs$core$async46671(self__.flag,self__.cb,meta46672__$1));
}));

(cljs.core.async.t_cljs$core$async46671.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_46673){
var self__ = this;
var _46673__$1 = this;
return self__.meta46672;
}));

(cljs.core.async.t_cljs$core$async46671.prototype.cljs$core$async$impl$protocols$Handler$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async46671.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.active_QMARK_(self__.flag);
}));

(cljs.core.async.t_cljs$core$async46671.prototype.cljs$core$async$impl$protocols$Handler$blockable_QMARK_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return true;
}));

(cljs.core.async.t_cljs$core$async46671.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
cljs.core.async.impl.protocols.commit(self__.flag);

return self__.cb;
}));

(cljs.core.async.t_cljs$core$async46671.getBasis = (function (){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"flag","flag",-1565787888,null),new cljs.core.Symbol(null,"cb","cb",-2064487928,null),new cljs.core.Symbol(null,"meta46672","meta46672",-273497607,null)], null);
}));

(cljs.core.async.t_cljs$core$async46671.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async46671.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async46671");

(cljs.core.async.t_cljs$core$async46671.cljs$lang$ctorPrWriter = (function (this__4491__auto__,writer__4492__auto__,opt__4493__auto__){
return cljs.core._write(writer__4492__auto__,"cljs.core.async/t_cljs$core$async46671");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async46671.
 */
cljs.core.async.__GT_t_cljs$core$async46671 = (function cljs$core$async$alt_handler_$___GT_t_cljs$core$async46671(flag__$1,cb__$1,meta46672){
return (new cljs.core.async.t_cljs$core$async46671(flag__$1,cb__$1,meta46672));
});

}

return (new cljs.core.async.t_cljs$core$async46671(flag,cb,cljs.core.PersistentArrayMap.EMPTY));
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
return (function (p1__46682_SHARP_){
var G__46690 = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [p1__46682_SHARP_,wport], null);
return (fret.cljs$core$IFn$_invoke$arity$1 ? fret.cljs$core$IFn$_invoke$arity$1(G__46690) : fret.call(null,G__46690));
});})(i,val,idx,port,wport,flag,n,idxs,priority))
));
})():cljs.core.async.impl.protocols.take_BANG_(port,cljs.core.async.alt_handler(flag,((function (i,idx,port,wport,flag,n,idxs,priority){
return (function (p1__46683_SHARP_){
var G__46691 = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [p1__46683_SHARP_,port], null);
return (fret.cljs$core$IFn$_invoke$arity$1 ? fret.cljs$core$IFn$_invoke$arity$1(G__46691) : fret.call(null,G__46691));
});})(i,idx,port,wport,flag,n,idxs,priority))
)));
if(cljs.core.truth_(vbox)){
return cljs.core.async.impl.channels.box(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [cljs.core.deref(vbox),(function (){var or__4253__auto__ = wport;
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
return port;
}
})()], null));
} else {
var G__48871 = (i + (1));
i = G__48871;
continue;
}
} else {
return null;
}
break;
}
})();
var or__4253__auto__ = ret;
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
if(cljs.core.contains_QMARK_(opts,new cljs.core.Keyword(null,"default","default",-1987822328))){
var temp__5753__auto__ = (function (){var and__4251__auto__ = flag.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1(null);
if(cljs.core.truth_(and__4251__auto__)){
return flag.cljs$core$async$impl$protocols$Handler$commit$arity$1(null);
} else {
return and__4251__auto__;
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
var args__4870__auto__ = [];
var len__4864__auto___48872 = arguments.length;
var i__4865__auto___48873 = (0);
while(true){
if((i__4865__auto___48873 < len__4864__auto___48872)){
args__4870__auto__.push((arguments[i__4865__auto___48873]));

var G__48875 = (i__4865__auto___48873 + (1));
i__4865__auto___48873 = G__48875;
continue;
} else {
}
break;
}

var argseq__4871__auto__ = ((((1) < args__4870__auto__.length))?(new cljs.core.IndexedSeq(args__4870__auto__.slice((1)),(0),null)):null);
return cljs.core.async.alts_BANG_.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__4871__auto__);
});

(cljs.core.async.alts_BANG_.cljs$core$IFn$_invoke$arity$variadic = (function (ports,p__46698){
var map__46699 = p__46698;
var map__46699__$1 = cljs.core.__destructure_map(map__46699);
var opts = map__46699__$1;
throw (new Error("alts! used not in (go ...) block"));
}));

(cljs.core.async.alts_BANG_.cljs$lang$maxFixedArity = (1));

/** @this {Function} */
(cljs.core.async.alts_BANG_.cljs$lang$applyTo = (function (seq46695){
var G__46696 = cljs.core.first(seq46695);
var seq46695__$1 = cljs.core.next(seq46695);
var self__4851__auto__ = this;
return self__4851__auto__.cljs$core$IFn$_invoke$arity$variadic(G__46696,seq46695__$1);
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
var G__46705 = arguments.length;
switch (G__46705) {
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
var c__46528__auto___48880 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_46731){
var state_val_46732 = (state_46731[(1)]);
if((state_val_46732 === (7))){
var inst_46725 = (state_46731[(2)]);
var state_46731__$1 = state_46731;
var statearr_46733_48881 = state_46731__$1;
(statearr_46733_48881[(2)] = inst_46725);

(statearr_46733_48881[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46732 === (1))){
var state_46731__$1 = state_46731;
var statearr_46734_48882 = state_46731__$1;
(statearr_46734_48882[(2)] = null);

(statearr_46734_48882[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46732 === (4))){
var inst_46708 = (state_46731[(7)]);
var inst_46708__$1 = (state_46731[(2)]);
var inst_46709 = (inst_46708__$1 == null);
var state_46731__$1 = (function (){var statearr_46737 = state_46731;
(statearr_46737[(7)] = inst_46708__$1);

return statearr_46737;
})();
if(cljs.core.truth_(inst_46709)){
var statearr_46738_48883 = state_46731__$1;
(statearr_46738_48883[(1)] = (5));

} else {
var statearr_46739_48884 = state_46731__$1;
(statearr_46739_48884[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46732 === (13))){
var state_46731__$1 = state_46731;
var statearr_46747_48885 = state_46731__$1;
(statearr_46747_48885[(2)] = null);

(statearr_46747_48885[(1)] = (14));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46732 === (6))){
var inst_46708 = (state_46731[(7)]);
var state_46731__$1 = state_46731;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_46731__$1,(11),to,inst_46708);
} else {
if((state_val_46732 === (3))){
var inst_46728 = (state_46731[(2)]);
var state_46731__$1 = state_46731;
return cljs.core.async.impl.ioc_helpers.return_chan(state_46731__$1,inst_46728);
} else {
if((state_val_46732 === (12))){
var state_46731__$1 = state_46731;
var statearr_46751_48887 = state_46731__$1;
(statearr_46751_48887[(2)] = null);

(statearr_46751_48887[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46732 === (2))){
var state_46731__$1 = state_46731;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_46731__$1,(4),from);
} else {
if((state_val_46732 === (11))){
var inst_46718 = (state_46731[(2)]);
var state_46731__$1 = state_46731;
if(cljs.core.truth_(inst_46718)){
var statearr_46755_48890 = state_46731__$1;
(statearr_46755_48890[(1)] = (12));

} else {
var statearr_46756_48891 = state_46731__$1;
(statearr_46756_48891[(1)] = (13));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46732 === (9))){
var state_46731__$1 = state_46731;
var statearr_46757_48893 = state_46731__$1;
(statearr_46757_48893[(2)] = null);

(statearr_46757_48893[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46732 === (5))){
var state_46731__$1 = state_46731;
if(cljs.core.truth_(close_QMARK_)){
var statearr_46761_48894 = state_46731__$1;
(statearr_46761_48894[(1)] = (8));

} else {
var statearr_46762_48895 = state_46731__$1;
(statearr_46762_48895[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46732 === (14))){
var inst_46723 = (state_46731[(2)]);
var state_46731__$1 = state_46731;
var statearr_46763_48896 = state_46731__$1;
(statearr_46763_48896[(2)] = inst_46723);

(statearr_46763_48896[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46732 === (10))){
var inst_46715 = (state_46731[(2)]);
var state_46731__$1 = state_46731;
var statearr_46764_48899 = state_46731__$1;
(statearr_46764_48899[(2)] = inst_46715);

(statearr_46764_48899[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46732 === (8))){
var inst_46712 = cljs.core.async.close_BANG_(to);
var state_46731__$1 = state_46731;
var statearr_46765_48900 = state_46731__$1;
(statearr_46765_48900[(2)] = inst_46712);

(statearr_46765_48900[(1)] = (10));


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
var cljs$core$async$state_machine__46373__auto__ = null;
var cljs$core$async$state_machine__46373__auto____0 = (function (){
var statearr_46766 = [null,null,null,null,null,null,null,null];
(statearr_46766[(0)] = cljs$core$async$state_machine__46373__auto__);

(statearr_46766[(1)] = (1));

return statearr_46766;
});
var cljs$core$async$state_machine__46373__auto____1 = (function (state_46731){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_46731);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e46767){var ex__46376__auto__ = e46767;
var statearr_46769_48903 = state_46731;
(statearr_46769_48903[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_46731[(4)]))){
var statearr_46771_48904 = state_46731;
(statearr_46771_48904[(1)] = cljs.core.first((state_46731[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__48905 = state_46731;
state_46731 = G__48905;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$state_machine__46373__auto__ = function(state_46731){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__46373__auto____1.call(this,state_46731);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__46373__auto____0;
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__46373__auto____1;
return cljs$core$async$state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_46772 = f__46529__auto__();
(statearr_46772[(6)] = c__46528__auto___48880);

return statearr_46772;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
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
var process = (function (p__46776){
var vec__46777 = p__46776;
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__46777,(0),null);
var p = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__46777,(1),null);
var job = vec__46777;
if((job == null)){
cljs.core.async.close_BANG_(results);

return null;
} else {
var res = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$3((1),xf,ex_handler);
var c__46528__auto___48910 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_46784){
var state_val_46785 = (state_46784[(1)]);
if((state_val_46785 === (1))){
var state_46784__$1 = state_46784;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_46784__$1,(2),res,v);
} else {
if((state_val_46785 === (2))){
var inst_46781 = (state_46784[(2)]);
var inst_46782 = cljs.core.async.close_BANG_(res);
var state_46784__$1 = (function (){var statearr_46786 = state_46784;
(statearr_46786[(7)] = inst_46781);

return statearr_46786;
})();
return cljs.core.async.impl.ioc_helpers.return_chan(state_46784__$1,inst_46782);
} else {
return null;
}
}
});
return (function() {
var cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__ = null;
var cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____0 = (function (){
var statearr_46787 = [null,null,null,null,null,null,null,null];
(statearr_46787[(0)] = cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__);

(statearr_46787[(1)] = (1));

return statearr_46787;
});
var cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____1 = (function (state_46784){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_46784);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e46788){var ex__46376__auto__ = e46788;
var statearr_46789_48911 = state_46784;
(statearr_46789_48911[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_46784[(4)]))){
var statearr_46790_48912 = state_46784;
(statearr_46790_48912[(1)] = cljs.core.first((state_46784[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__48913 = state_46784;
state_46784 = G__48913;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__ = function(state_46784){
switch(arguments.length){
case 0:
return cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____1.call(this,state_46784);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____0;
cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____1;
return cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_46792 = f__46529__auto__();
(statearr_46792[(6)] = c__46528__auto___48910);

return statearr_46792;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
}));


cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$2(p,res);

return true;
}
});
var async = (function (p__46793){
var vec__46795 = p__46793;
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__46795,(0),null);
var p = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__46795,(1),null);
var job = vec__46795;
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
var n__4741__auto___48915 = n;
var __48916 = (0);
while(true){
if((__48916 < n__4741__auto___48915)){
var G__46798_48917 = type;
var G__46798_48918__$1 = (((G__46798_48917 instanceof cljs.core.Keyword))?G__46798_48917.fqn:null);
switch (G__46798_48918__$1) {
case "compute":
var c__46528__auto___48920 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run(((function (__48916,c__46528__auto___48920,G__46798_48917,G__46798_48918__$1,n__4741__auto___48915,jobs,results,process,async){
return (function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = ((function (__48916,c__46528__auto___48920,G__46798_48917,G__46798_48918__$1,n__4741__auto___48915,jobs,results,process,async){
return (function (state_46811){
var state_val_46812 = (state_46811[(1)]);
if((state_val_46812 === (1))){
var state_46811__$1 = state_46811;
var statearr_46813_48921 = state_46811__$1;
(statearr_46813_48921[(2)] = null);

(statearr_46813_48921[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46812 === (2))){
var state_46811__$1 = state_46811;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_46811__$1,(4),jobs);
} else {
if((state_val_46812 === (3))){
var inst_46809 = (state_46811[(2)]);
var state_46811__$1 = state_46811;
return cljs.core.async.impl.ioc_helpers.return_chan(state_46811__$1,inst_46809);
} else {
if((state_val_46812 === (4))){
var inst_46801 = (state_46811[(2)]);
var inst_46802 = process(inst_46801);
var state_46811__$1 = state_46811;
if(cljs.core.truth_(inst_46802)){
var statearr_46815_48926 = state_46811__$1;
(statearr_46815_48926[(1)] = (5));

} else {
var statearr_46816_48927 = state_46811__$1;
(statearr_46816_48927[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46812 === (5))){
var state_46811__$1 = state_46811;
var statearr_46817_48928 = state_46811__$1;
(statearr_46817_48928[(2)] = null);

(statearr_46817_48928[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46812 === (6))){
var state_46811__$1 = state_46811;
var statearr_46818_48932 = state_46811__$1;
(statearr_46818_48932[(2)] = null);

(statearr_46818_48932[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46812 === (7))){
var inst_46807 = (state_46811[(2)]);
var state_46811__$1 = state_46811;
var statearr_46819_48933 = state_46811__$1;
(statearr_46819_48933[(2)] = inst_46807);

(statearr_46819_48933[(1)] = (3));


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
});})(__48916,c__46528__auto___48920,G__46798_48917,G__46798_48918__$1,n__4741__auto___48915,jobs,results,process,async))
;
return ((function (__48916,switch__46372__auto__,c__46528__auto___48920,G__46798_48917,G__46798_48918__$1,n__4741__auto___48915,jobs,results,process,async){
return (function() {
var cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__ = null;
var cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____0 = (function (){
var statearr_46820 = [null,null,null,null,null,null,null];
(statearr_46820[(0)] = cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__);

(statearr_46820[(1)] = (1));

return statearr_46820;
});
var cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____1 = (function (state_46811){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_46811);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e46821){var ex__46376__auto__ = e46821;
var statearr_46822_48934 = state_46811;
(statearr_46822_48934[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_46811[(4)]))){
var statearr_46823_48935 = state_46811;
(statearr_46823_48935[(1)] = cljs.core.first((state_46811[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__48936 = state_46811;
state_46811 = G__48936;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__ = function(state_46811){
switch(arguments.length){
case 0:
return cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____1.call(this,state_46811);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____0;
cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____1;
return cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__;
})()
;})(__48916,switch__46372__auto__,c__46528__auto___48920,G__46798_48917,G__46798_48918__$1,n__4741__auto___48915,jobs,results,process,async))
})();
var state__46530__auto__ = (function (){var statearr_46825 = f__46529__auto__();
(statearr_46825[(6)] = c__46528__auto___48920);

return statearr_46825;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
});})(__48916,c__46528__auto___48920,G__46798_48917,G__46798_48918__$1,n__4741__auto___48915,jobs,results,process,async))
);


break;
case "async":
var c__46528__auto___48937 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run(((function (__48916,c__46528__auto___48937,G__46798_48917,G__46798_48918__$1,n__4741__auto___48915,jobs,results,process,async){
return (function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = ((function (__48916,c__46528__auto___48937,G__46798_48917,G__46798_48918__$1,n__4741__auto___48915,jobs,results,process,async){
return (function (state_46838){
var state_val_46839 = (state_46838[(1)]);
if((state_val_46839 === (1))){
var state_46838__$1 = state_46838;
var statearr_46840_48943 = state_46838__$1;
(statearr_46840_48943[(2)] = null);

(statearr_46840_48943[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46839 === (2))){
var state_46838__$1 = state_46838;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_46838__$1,(4),jobs);
} else {
if((state_val_46839 === (3))){
var inst_46836 = (state_46838[(2)]);
var state_46838__$1 = state_46838;
return cljs.core.async.impl.ioc_helpers.return_chan(state_46838__$1,inst_46836);
} else {
if((state_val_46839 === (4))){
var inst_46828 = (state_46838[(2)]);
var inst_46829 = async(inst_46828);
var state_46838__$1 = state_46838;
if(cljs.core.truth_(inst_46829)){
var statearr_46841_48954 = state_46838__$1;
(statearr_46841_48954[(1)] = (5));

} else {
var statearr_46842_48955 = state_46838__$1;
(statearr_46842_48955[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46839 === (5))){
var state_46838__$1 = state_46838;
var statearr_46843_48956 = state_46838__$1;
(statearr_46843_48956[(2)] = null);

(statearr_46843_48956[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46839 === (6))){
var state_46838__$1 = state_46838;
var statearr_46844_48957 = state_46838__$1;
(statearr_46844_48957[(2)] = null);

(statearr_46844_48957[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46839 === (7))){
var inst_46834 = (state_46838[(2)]);
var state_46838__$1 = state_46838;
var statearr_46845_48962 = state_46838__$1;
(statearr_46845_48962[(2)] = inst_46834);

(statearr_46845_48962[(1)] = (3));


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
});})(__48916,c__46528__auto___48937,G__46798_48917,G__46798_48918__$1,n__4741__auto___48915,jobs,results,process,async))
;
return ((function (__48916,switch__46372__auto__,c__46528__auto___48937,G__46798_48917,G__46798_48918__$1,n__4741__auto___48915,jobs,results,process,async){
return (function() {
var cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__ = null;
var cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____0 = (function (){
var statearr_46846 = [null,null,null,null,null,null,null];
(statearr_46846[(0)] = cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__);

(statearr_46846[(1)] = (1));

return statearr_46846;
});
var cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____1 = (function (state_46838){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_46838);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e46847){var ex__46376__auto__ = e46847;
var statearr_46848_48976 = state_46838;
(statearr_46848_48976[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_46838[(4)]))){
var statearr_46853_48977 = state_46838;
(statearr_46853_48977[(1)] = cljs.core.first((state_46838[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__48978 = state_46838;
state_46838 = G__48978;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__ = function(state_46838){
switch(arguments.length){
case 0:
return cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____1.call(this,state_46838);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____0;
cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____1;
return cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__;
})()
;})(__48916,switch__46372__auto__,c__46528__auto___48937,G__46798_48917,G__46798_48918__$1,n__4741__auto___48915,jobs,results,process,async))
})();
var state__46530__auto__ = (function (){var statearr_46860 = f__46529__auto__();
(statearr_46860[(6)] = c__46528__auto___48937);

return statearr_46860;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
});})(__48916,c__46528__auto___48937,G__46798_48917,G__46798_48918__$1,n__4741__auto___48915,jobs,results,process,async))
);


break;
default:
throw (new Error(["No matching clause: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(G__46798_48918__$1)].join('')));

}

var G__48982 = (__48916 + (1));
__48916 = G__48982;
continue;
} else {
}
break;
}

var c__46528__auto___48983 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_46895){
var state_val_46896 = (state_46895[(1)]);
if((state_val_46896 === (7))){
var inst_46891 = (state_46895[(2)]);
var state_46895__$1 = state_46895;
var statearr_46898_48994 = state_46895__$1;
(statearr_46898_48994[(2)] = inst_46891);

(statearr_46898_48994[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46896 === (1))){
var state_46895__$1 = state_46895;
var statearr_46900_48998 = state_46895__$1;
(statearr_46900_48998[(2)] = null);

(statearr_46900_48998[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46896 === (4))){
var inst_46872 = (state_46895[(7)]);
var inst_46872__$1 = (state_46895[(2)]);
var inst_46877 = (inst_46872__$1 == null);
var state_46895__$1 = (function (){var statearr_46901 = state_46895;
(statearr_46901[(7)] = inst_46872__$1);

return statearr_46901;
})();
if(cljs.core.truth_(inst_46877)){
var statearr_46904_49006 = state_46895__$1;
(statearr_46904_49006[(1)] = (5));

} else {
var statearr_46905_49007 = state_46895__$1;
(statearr_46905_49007[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46896 === (6))){
var inst_46881 = (state_46895[(8)]);
var inst_46872 = (state_46895[(7)]);
var inst_46881__$1 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
var inst_46882 = cljs.core.PersistentVector.EMPTY_NODE;
var inst_46883 = [inst_46872,inst_46881__$1];
var inst_46884 = (new cljs.core.PersistentVector(null,2,(5),inst_46882,inst_46883,null));
var state_46895__$1 = (function (){var statearr_46906 = state_46895;
(statearr_46906[(8)] = inst_46881__$1);

return statearr_46906;
})();
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_46895__$1,(8),jobs,inst_46884);
} else {
if((state_val_46896 === (3))){
var inst_46893 = (state_46895[(2)]);
var state_46895__$1 = state_46895;
return cljs.core.async.impl.ioc_helpers.return_chan(state_46895__$1,inst_46893);
} else {
if((state_val_46896 === (2))){
var state_46895__$1 = state_46895;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_46895__$1,(4),from);
} else {
if((state_val_46896 === (9))){
var inst_46888 = (state_46895[(2)]);
var state_46895__$1 = (function (){var statearr_46907 = state_46895;
(statearr_46907[(9)] = inst_46888);

return statearr_46907;
})();
var statearr_46908_49013 = state_46895__$1;
(statearr_46908_49013[(2)] = null);

(statearr_46908_49013[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46896 === (5))){
var inst_46879 = cljs.core.async.close_BANG_(jobs);
var state_46895__$1 = state_46895;
var statearr_46909_49014 = state_46895__$1;
(statearr_46909_49014[(2)] = inst_46879);

(statearr_46909_49014[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46896 === (8))){
var inst_46881 = (state_46895[(8)]);
var inst_46886 = (state_46895[(2)]);
var state_46895__$1 = (function (){var statearr_46910 = state_46895;
(statearr_46910[(10)] = inst_46886);

return statearr_46910;
})();
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_46895__$1,(9),results,inst_46881);
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
var cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__ = null;
var cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____0 = (function (){
var statearr_46915 = [null,null,null,null,null,null,null,null,null,null,null];
(statearr_46915[(0)] = cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__);

(statearr_46915[(1)] = (1));

return statearr_46915;
});
var cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____1 = (function (state_46895){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_46895);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e46917){var ex__46376__auto__ = e46917;
var statearr_46918_49019 = state_46895;
(statearr_46918_49019[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_46895[(4)]))){
var statearr_46923_49023 = state_46895;
(statearr_46923_49023[(1)] = cljs.core.first((state_46895[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__49024 = state_46895;
state_46895 = G__49024;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__ = function(state_46895){
switch(arguments.length){
case 0:
return cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____1.call(this,state_46895);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____0;
cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____1;
return cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_46936 = f__46529__auto__();
(statearr_46936[(6)] = c__46528__auto___48983);

return statearr_46936;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
}));


var c__46528__auto__ = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_46981){
var state_val_46982 = (state_46981[(1)]);
if((state_val_46982 === (7))){
var inst_46973 = (state_46981[(2)]);
var state_46981__$1 = state_46981;
var statearr_46992_49025 = state_46981__$1;
(statearr_46992_49025[(2)] = inst_46973);

(statearr_46992_49025[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46982 === (20))){
var state_46981__$1 = state_46981;
var statearr_46999_49026 = state_46981__$1;
(statearr_46999_49026[(2)] = null);

(statearr_46999_49026[(1)] = (21));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46982 === (1))){
var state_46981__$1 = state_46981;
var statearr_47004_49027 = state_46981__$1;
(statearr_47004_49027[(2)] = null);

(statearr_47004_49027[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46982 === (4))){
var inst_46939 = (state_46981[(7)]);
var inst_46939__$1 = (state_46981[(2)]);
var inst_46940 = (inst_46939__$1 == null);
var state_46981__$1 = (function (){var statearr_47006 = state_46981;
(statearr_47006[(7)] = inst_46939__$1);

return statearr_47006;
})();
if(cljs.core.truth_(inst_46940)){
var statearr_47008_49031 = state_46981__$1;
(statearr_47008_49031[(1)] = (5));

} else {
var statearr_47009_49032 = state_46981__$1;
(statearr_47009_49032[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46982 === (15))){
var inst_46953 = (state_46981[(8)]);
var state_46981__$1 = state_46981;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_46981__$1,(18),to,inst_46953);
} else {
if((state_val_46982 === (21))){
var inst_46968 = (state_46981[(2)]);
var state_46981__$1 = state_46981;
var statearr_47015_49033 = state_46981__$1;
(statearr_47015_49033[(2)] = inst_46968);

(statearr_47015_49033[(1)] = (13));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46982 === (13))){
var inst_46970 = (state_46981[(2)]);
var state_46981__$1 = (function (){var statearr_47021 = state_46981;
(statearr_47021[(9)] = inst_46970);

return statearr_47021;
})();
var statearr_47022_49037 = state_46981__$1;
(statearr_47022_49037[(2)] = null);

(statearr_47022_49037[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46982 === (6))){
var inst_46939 = (state_46981[(7)]);
var state_46981__$1 = state_46981;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_46981__$1,(11),inst_46939);
} else {
if((state_val_46982 === (17))){
var inst_46962 = (state_46981[(2)]);
var state_46981__$1 = state_46981;
if(cljs.core.truth_(inst_46962)){
var statearr_47037_49038 = state_46981__$1;
(statearr_47037_49038[(1)] = (19));

} else {
var statearr_47038_49042 = state_46981__$1;
(statearr_47038_49042[(1)] = (20));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46982 === (3))){
var inst_46975 = (state_46981[(2)]);
var state_46981__$1 = state_46981;
return cljs.core.async.impl.ioc_helpers.return_chan(state_46981__$1,inst_46975);
} else {
if((state_val_46982 === (12))){
var inst_46949 = (state_46981[(10)]);
var state_46981__$1 = state_46981;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_46981__$1,(14),inst_46949);
} else {
if((state_val_46982 === (2))){
var state_46981__$1 = state_46981;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_46981__$1,(4),results);
} else {
if((state_val_46982 === (19))){
var state_46981__$1 = state_46981;
var statearr_47041_49043 = state_46981__$1;
(statearr_47041_49043[(2)] = null);

(statearr_47041_49043[(1)] = (12));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46982 === (11))){
var inst_46949 = (state_46981[(2)]);
var state_46981__$1 = (function (){var statearr_47050 = state_46981;
(statearr_47050[(10)] = inst_46949);

return statearr_47050;
})();
var statearr_47051_49044 = state_46981__$1;
(statearr_47051_49044[(2)] = null);

(statearr_47051_49044[(1)] = (12));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46982 === (9))){
var state_46981__$1 = state_46981;
var statearr_47053_49045 = state_46981__$1;
(statearr_47053_49045[(2)] = null);

(statearr_47053_49045[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46982 === (5))){
var state_46981__$1 = state_46981;
if(cljs.core.truth_(close_QMARK_)){
var statearr_47054_49047 = state_46981__$1;
(statearr_47054_49047[(1)] = (8));

} else {
var statearr_47055_49048 = state_46981__$1;
(statearr_47055_49048[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46982 === (14))){
var inst_46953 = (state_46981[(8)]);
var inst_46956 = (state_46981[(11)]);
var inst_46953__$1 = (state_46981[(2)]);
var inst_46955 = (inst_46953__$1 == null);
var inst_46956__$1 = cljs.core.not(inst_46955);
var state_46981__$1 = (function (){var statearr_47056 = state_46981;
(statearr_47056[(8)] = inst_46953__$1);

(statearr_47056[(11)] = inst_46956__$1);

return statearr_47056;
})();
if(inst_46956__$1){
var statearr_47057_49049 = state_46981__$1;
(statearr_47057_49049[(1)] = (15));

} else {
var statearr_47058_49050 = state_46981__$1;
(statearr_47058_49050[(1)] = (16));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46982 === (16))){
var inst_46956 = (state_46981[(11)]);
var state_46981__$1 = state_46981;
var statearr_47059_49051 = state_46981__$1;
(statearr_47059_49051[(2)] = inst_46956);

(statearr_47059_49051[(1)] = (17));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46982 === (10))){
var inst_46946 = (state_46981[(2)]);
var state_46981__$1 = state_46981;
var statearr_47060_49052 = state_46981__$1;
(statearr_47060_49052[(2)] = inst_46946);

(statearr_47060_49052[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46982 === (18))){
var inst_46959 = (state_46981[(2)]);
var state_46981__$1 = state_46981;
var statearr_47061_49053 = state_46981__$1;
(statearr_47061_49053[(2)] = inst_46959);

(statearr_47061_49053[(1)] = (17));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_46982 === (8))){
var inst_46943 = cljs.core.async.close_BANG_(to);
var state_46981__$1 = state_46981;
var statearr_47062_49054 = state_46981__$1;
(statearr_47062_49054[(2)] = inst_46943);

(statearr_47062_49054[(1)] = (10));


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
var cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__ = null;
var cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____0 = (function (){
var statearr_47071 = [null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_47071[(0)] = cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__);

(statearr_47071[(1)] = (1));

return statearr_47071;
});
var cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____1 = (function (state_46981){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_46981);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e47072){var ex__46376__auto__ = e47072;
var statearr_47079_49055 = state_46981;
(statearr_47079_49055[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_46981[(4)]))){
var statearr_47082_49056 = state_46981;
(statearr_47082_49056[(1)] = cljs.core.first((state_46981[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__49057 = state_46981;
state_46981 = G__49057;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__ = function(state_46981){
switch(arguments.length){
case 0:
return cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____1.call(this,state_46981);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____0;
cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$pipeline_STAR__$_state_machine__46373__auto____1;
return cljs$core$async$pipeline_STAR__$_state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_47085 = f__46529__auto__();
(statearr_47085[(6)] = c__46528__auto__);

return statearr_47085;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
}));

return c__46528__auto__;
});
/**
 * Takes elements from the from channel and supplies them to the to
 *   channel, subject to the async function af, with parallelism n. af
 *   must be a function of two arguments, the first an input value and
 *   the second a channel on which to place the result(s). af must close!
 *   the channel before returning.  The presumption is that af will
 *   return immediately, having launched some asynchronous operation
 *   whose completion/callback will manipulate the result channel. Outputs
 *   will be returned in order relative to  the inputs. By default, the to
 *   channel will be closed when the from channel closes, but can be
 *   determined by the close?  parameter. Will stop consuming the from
 *   channel if the to channel closes.
 */
cljs.core.async.pipeline_async = (function cljs$core$async$pipeline_async(var_args){
var G__47089 = arguments.length;
switch (G__47089) {
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
var G__47094 = arguments.length;
switch (G__47094) {
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
var G__47107 = arguments.length;
switch (G__47107) {
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
var c__46528__auto___49063 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_47135){
var state_val_47136 = (state_47135[(1)]);
if((state_val_47136 === (7))){
var inst_47131 = (state_47135[(2)]);
var state_47135__$1 = state_47135;
var statearr_47137_49064 = state_47135__$1;
(statearr_47137_49064[(2)] = inst_47131);

(statearr_47137_49064[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47136 === (1))){
var state_47135__$1 = state_47135;
var statearr_47138_49065 = state_47135__$1;
(statearr_47138_49065[(2)] = null);

(statearr_47138_49065[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47136 === (4))){
var inst_47110 = (state_47135[(7)]);
var inst_47110__$1 = (state_47135[(2)]);
var inst_47111 = (inst_47110__$1 == null);
var state_47135__$1 = (function (){var statearr_47140 = state_47135;
(statearr_47140[(7)] = inst_47110__$1);

return statearr_47140;
})();
if(cljs.core.truth_(inst_47111)){
var statearr_47142_49072 = state_47135__$1;
(statearr_47142_49072[(1)] = (5));

} else {
var statearr_47143_49083 = state_47135__$1;
(statearr_47143_49083[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47136 === (13))){
var state_47135__$1 = state_47135;
var statearr_47144_49084 = state_47135__$1;
(statearr_47144_49084[(2)] = null);

(statearr_47144_49084[(1)] = (14));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47136 === (6))){
var inst_47110 = (state_47135[(7)]);
var inst_47116 = (p.cljs$core$IFn$_invoke$arity$1 ? p.cljs$core$IFn$_invoke$arity$1(inst_47110) : p.call(null,inst_47110));
var state_47135__$1 = state_47135;
if(cljs.core.truth_(inst_47116)){
var statearr_47147_49086 = state_47135__$1;
(statearr_47147_49086[(1)] = (9));

} else {
var statearr_47150_49087 = state_47135__$1;
(statearr_47150_49087[(1)] = (10));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47136 === (3))){
var inst_47133 = (state_47135[(2)]);
var state_47135__$1 = state_47135;
return cljs.core.async.impl.ioc_helpers.return_chan(state_47135__$1,inst_47133);
} else {
if((state_val_47136 === (12))){
var state_47135__$1 = state_47135;
var statearr_47151_49090 = state_47135__$1;
(statearr_47151_49090[(2)] = null);

(statearr_47151_49090[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47136 === (2))){
var state_47135__$1 = state_47135;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_47135__$1,(4),ch);
} else {
if((state_val_47136 === (11))){
var inst_47110 = (state_47135[(7)]);
var inst_47121 = (state_47135[(2)]);
var state_47135__$1 = state_47135;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_47135__$1,(8),inst_47121,inst_47110);
} else {
if((state_val_47136 === (9))){
var state_47135__$1 = state_47135;
var statearr_47152_49092 = state_47135__$1;
(statearr_47152_49092[(2)] = tc);

(statearr_47152_49092[(1)] = (11));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47136 === (5))){
var inst_47113 = cljs.core.async.close_BANG_(tc);
var inst_47114 = cljs.core.async.close_BANG_(fc);
var state_47135__$1 = (function (){var statearr_47153 = state_47135;
(statearr_47153[(8)] = inst_47113);

return statearr_47153;
})();
var statearr_47154_49094 = state_47135__$1;
(statearr_47154_49094[(2)] = inst_47114);

(statearr_47154_49094[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47136 === (14))){
var inst_47129 = (state_47135[(2)]);
var state_47135__$1 = state_47135;
var statearr_47162_49095 = state_47135__$1;
(statearr_47162_49095[(2)] = inst_47129);

(statearr_47162_49095[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47136 === (10))){
var state_47135__$1 = state_47135;
var statearr_47163_49096 = state_47135__$1;
(statearr_47163_49096[(2)] = fc);

(statearr_47163_49096[(1)] = (11));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47136 === (8))){
var inst_47123 = (state_47135[(2)]);
var state_47135__$1 = state_47135;
if(cljs.core.truth_(inst_47123)){
var statearr_47164_49097 = state_47135__$1;
(statearr_47164_49097[(1)] = (12));

} else {
var statearr_47165_49098 = state_47135__$1;
(statearr_47165_49098[(1)] = (13));

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
var cljs$core$async$state_machine__46373__auto__ = null;
var cljs$core$async$state_machine__46373__auto____0 = (function (){
var statearr_47167 = [null,null,null,null,null,null,null,null,null];
(statearr_47167[(0)] = cljs$core$async$state_machine__46373__auto__);

(statearr_47167[(1)] = (1));

return statearr_47167;
});
var cljs$core$async$state_machine__46373__auto____1 = (function (state_47135){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_47135);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e47168){var ex__46376__auto__ = e47168;
var statearr_47169_49103 = state_47135;
(statearr_47169_49103[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_47135[(4)]))){
var statearr_47170_49104 = state_47135;
(statearr_47170_49104[(1)] = cljs.core.first((state_47135[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__49107 = state_47135;
state_47135 = G__49107;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$state_machine__46373__auto__ = function(state_47135){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__46373__auto____1.call(this,state_47135);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__46373__auto____0;
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__46373__auto____1;
return cljs$core$async$state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_47171 = f__46529__auto__();
(statearr_47171[(6)] = c__46528__auto___49063);

return statearr_47171;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
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
var c__46528__auto__ = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_47195){
var state_val_47196 = (state_47195[(1)]);
if((state_val_47196 === (7))){
var inst_47191 = (state_47195[(2)]);
var state_47195__$1 = state_47195;
var statearr_47198_49117 = state_47195__$1;
(statearr_47198_49117[(2)] = inst_47191);

(statearr_47198_49117[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47196 === (1))){
var inst_47173 = init;
var inst_47174 = inst_47173;
var state_47195__$1 = (function (){var statearr_47199 = state_47195;
(statearr_47199[(7)] = inst_47174);

return statearr_47199;
})();
var statearr_47200_49124 = state_47195__$1;
(statearr_47200_49124[(2)] = null);

(statearr_47200_49124[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47196 === (4))){
var inst_47177 = (state_47195[(8)]);
var inst_47177__$1 = (state_47195[(2)]);
var inst_47179 = (inst_47177__$1 == null);
var state_47195__$1 = (function (){var statearr_47201 = state_47195;
(statearr_47201[(8)] = inst_47177__$1);

return statearr_47201;
})();
if(cljs.core.truth_(inst_47179)){
var statearr_47202_49125 = state_47195__$1;
(statearr_47202_49125[(1)] = (5));

} else {
var statearr_47203_49126 = state_47195__$1;
(statearr_47203_49126[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47196 === (6))){
var inst_47174 = (state_47195[(7)]);
var inst_47182 = (state_47195[(9)]);
var inst_47177 = (state_47195[(8)]);
var inst_47182__$1 = (f.cljs$core$IFn$_invoke$arity$2 ? f.cljs$core$IFn$_invoke$arity$2(inst_47174,inst_47177) : f.call(null,inst_47174,inst_47177));
var inst_47183 = cljs.core.reduced_QMARK_(inst_47182__$1);
var state_47195__$1 = (function (){var statearr_47205 = state_47195;
(statearr_47205[(9)] = inst_47182__$1);

return statearr_47205;
})();
if(inst_47183){
var statearr_47206_49129 = state_47195__$1;
(statearr_47206_49129[(1)] = (8));

} else {
var statearr_47207_49130 = state_47195__$1;
(statearr_47207_49130[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47196 === (3))){
var inst_47193 = (state_47195[(2)]);
var state_47195__$1 = state_47195;
return cljs.core.async.impl.ioc_helpers.return_chan(state_47195__$1,inst_47193);
} else {
if((state_val_47196 === (2))){
var state_47195__$1 = state_47195;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_47195__$1,(4),ch);
} else {
if((state_val_47196 === (9))){
var inst_47182 = (state_47195[(9)]);
var inst_47174 = inst_47182;
var state_47195__$1 = (function (){var statearr_47210 = state_47195;
(statearr_47210[(7)] = inst_47174);

return statearr_47210;
})();
var statearr_47212_49131 = state_47195__$1;
(statearr_47212_49131[(2)] = null);

(statearr_47212_49131[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47196 === (5))){
var inst_47174 = (state_47195[(7)]);
var state_47195__$1 = state_47195;
var statearr_47214_49133 = state_47195__$1;
(statearr_47214_49133[(2)] = inst_47174);

(statearr_47214_49133[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47196 === (10))){
var inst_47189 = (state_47195[(2)]);
var state_47195__$1 = state_47195;
var statearr_47219_49134 = state_47195__$1;
(statearr_47219_49134[(2)] = inst_47189);

(statearr_47219_49134[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47196 === (8))){
var inst_47182 = (state_47195[(9)]);
var inst_47185 = cljs.core.deref(inst_47182);
var state_47195__$1 = state_47195;
var statearr_47224_49135 = state_47195__$1;
(statearr_47224_49135[(2)] = inst_47185);

(statearr_47224_49135[(1)] = (10));


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
var cljs$core$async$reduce_$_state_machine__46373__auto__ = null;
var cljs$core$async$reduce_$_state_machine__46373__auto____0 = (function (){
var statearr_47233 = [null,null,null,null,null,null,null,null,null,null];
(statearr_47233[(0)] = cljs$core$async$reduce_$_state_machine__46373__auto__);

(statearr_47233[(1)] = (1));

return statearr_47233;
});
var cljs$core$async$reduce_$_state_machine__46373__auto____1 = (function (state_47195){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_47195);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e47238){var ex__46376__auto__ = e47238;
var statearr_47239_49136 = state_47195;
(statearr_47239_49136[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_47195[(4)]))){
var statearr_47240_49137 = state_47195;
(statearr_47240_49137[(1)] = cljs.core.first((state_47195[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__49144 = state_47195;
state_47195 = G__49144;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$reduce_$_state_machine__46373__auto__ = function(state_47195){
switch(arguments.length){
case 0:
return cljs$core$async$reduce_$_state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$reduce_$_state_machine__46373__auto____1.call(this,state_47195);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$reduce_$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$reduce_$_state_machine__46373__auto____0;
cljs$core$async$reduce_$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$reduce_$_state_machine__46373__auto____1;
return cljs$core$async$reduce_$_state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_47241 = f__46529__auto__();
(statearr_47241[(6)] = c__46528__auto__);

return statearr_47241;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
}));

return c__46528__auto__;
});
/**
 * async/reduces a channel with a transformation (xform f).
 *   Returns a channel containing the result.  ch must close before
 *   transduce produces a result.
 */
cljs.core.async.transduce = (function cljs$core$async$transduce(xform,f,init,ch){
var f__$1 = (xform.cljs$core$IFn$_invoke$arity$1 ? xform.cljs$core$IFn$_invoke$arity$1(f) : xform.call(null,f));
var c__46528__auto__ = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_47248){
var state_val_47249 = (state_47248[(1)]);
if((state_val_47249 === (1))){
var inst_47243 = cljs.core.async.reduce(f__$1,init,ch);
var state_47248__$1 = state_47248;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_47248__$1,(2),inst_47243);
} else {
if((state_val_47249 === (2))){
var inst_47245 = (state_47248[(2)]);
var inst_47246 = (f__$1.cljs$core$IFn$_invoke$arity$1 ? f__$1.cljs$core$IFn$_invoke$arity$1(inst_47245) : f__$1.call(null,inst_47245));
var state_47248__$1 = state_47248;
return cljs.core.async.impl.ioc_helpers.return_chan(state_47248__$1,inst_47246);
} else {
return null;
}
}
});
return (function() {
var cljs$core$async$transduce_$_state_machine__46373__auto__ = null;
var cljs$core$async$transduce_$_state_machine__46373__auto____0 = (function (){
var statearr_47253 = [null,null,null,null,null,null,null];
(statearr_47253[(0)] = cljs$core$async$transduce_$_state_machine__46373__auto__);

(statearr_47253[(1)] = (1));

return statearr_47253;
});
var cljs$core$async$transduce_$_state_machine__46373__auto____1 = (function (state_47248){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_47248);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e47254){var ex__46376__auto__ = e47254;
var statearr_47255_49155 = state_47248;
(statearr_47255_49155[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_47248[(4)]))){
var statearr_47256_49156 = state_47248;
(statearr_47256_49156[(1)] = cljs.core.first((state_47248[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__49157 = state_47248;
state_47248 = G__49157;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$transduce_$_state_machine__46373__auto__ = function(state_47248){
switch(arguments.length){
case 0:
return cljs$core$async$transduce_$_state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$transduce_$_state_machine__46373__auto____1.call(this,state_47248);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$transduce_$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$transduce_$_state_machine__46373__auto____0;
cljs$core$async$transduce_$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$transduce_$_state_machine__46373__auto____1;
return cljs$core$async$transduce_$_state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_47258 = f__46529__auto__();
(statearr_47258[(6)] = c__46528__auto__);

return statearr_47258;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
}));

return c__46528__auto__;
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
var G__47260 = arguments.length;
switch (G__47260) {
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
var c__46528__auto__ = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_47286){
var state_val_47287 = (state_47286[(1)]);
if((state_val_47287 === (7))){
var inst_47268 = (state_47286[(2)]);
var state_47286__$1 = state_47286;
var statearr_47288_49165 = state_47286__$1;
(statearr_47288_49165[(2)] = inst_47268);

(statearr_47288_49165[(1)] = (6));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47287 === (1))){
var inst_47262 = cljs.core.seq(coll);
var inst_47263 = inst_47262;
var state_47286__$1 = (function (){var statearr_47290 = state_47286;
(statearr_47290[(7)] = inst_47263);

return statearr_47290;
})();
var statearr_47291_49166 = state_47286__$1;
(statearr_47291_49166[(2)] = null);

(statearr_47291_49166[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47287 === (4))){
var inst_47263 = (state_47286[(7)]);
var inst_47266 = cljs.core.first(inst_47263);
var state_47286__$1 = state_47286;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_47286__$1,(7),ch,inst_47266);
} else {
if((state_val_47287 === (13))){
var inst_47280 = (state_47286[(2)]);
var state_47286__$1 = state_47286;
var statearr_47292_49167 = state_47286__$1;
(statearr_47292_49167[(2)] = inst_47280);

(statearr_47292_49167[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47287 === (6))){
var inst_47271 = (state_47286[(2)]);
var state_47286__$1 = state_47286;
if(cljs.core.truth_(inst_47271)){
var statearr_47297_49168 = state_47286__$1;
(statearr_47297_49168[(1)] = (8));

} else {
var statearr_47302_49169 = state_47286__$1;
(statearr_47302_49169[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47287 === (3))){
var inst_47284 = (state_47286[(2)]);
var state_47286__$1 = state_47286;
return cljs.core.async.impl.ioc_helpers.return_chan(state_47286__$1,inst_47284);
} else {
if((state_val_47287 === (12))){
var state_47286__$1 = state_47286;
var statearr_47308_49170 = state_47286__$1;
(statearr_47308_49170[(2)] = null);

(statearr_47308_49170[(1)] = (13));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47287 === (2))){
var inst_47263 = (state_47286[(7)]);
var state_47286__$1 = state_47286;
if(cljs.core.truth_(inst_47263)){
var statearr_47313_49171 = state_47286__$1;
(statearr_47313_49171[(1)] = (4));

} else {
var statearr_47318_49172 = state_47286__$1;
(statearr_47318_49172[(1)] = (5));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47287 === (11))){
var inst_47277 = cljs.core.async.close_BANG_(ch);
var state_47286__$1 = state_47286;
var statearr_47319_49173 = state_47286__$1;
(statearr_47319_49173[(2)] = inst_47277);

(statearr_47319_49173[(1)] = (13));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47287 === (9))){
var state_47286__$1 = state_47286;
if(cljs.core.truth_(close_QMARK_)){
var statearr_47320_49176 = state_47286__$1;
(statearr_47320_49176[(1)] = (11));

} else {
var statearr_47321_49177 = state_47286__$1;
(statearr_47321_49177[(1)] = (12));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47287 === (5))){
var inst_47263 = (state_47286[(7)]);
var state_47286__$1 = state_47286;
var statearr_47322_49178 = state_47286__$1;
(statearr_47322_49178[(2)] = inst_47263);

(statearr_47322_49178[(1)] = (6));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47287 === (10))){
var inst_47282 = (state_47286[(2)]);
var state_47286__$1 = state_47286;
var statearr_47323_49185 = state_47286__$1;
(statearr_47323_49185[(2)] = inst_47282);

(statearr_47323_49185[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47287 === (8))){
var inst_47263 = (state_47286[(7)]);
var inst_47273 = cljs.core.next(inst_47263);
var inst_47263__$1 = inst_47273;
var state_47286__$1 = (function (){var statearr_47325 = state_47286;
(statearr_47325[(7)] = inst_47263__$1);

return statearr_47325;
})();
var statearr_47326_49187 = state_47286__$1;
(statearr_47326_49187[(2)] = null);

(statearr_47326_49187[(1)] = (2));


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
var cljs$core$async$state_machine__46373__auto__ = null;
var cljs$core$async$state_machine__46373__auto____0 = (function (){
var statearr_47327 = [null,null,null,null,null,null,null,null];
(statearr_47327[(0)] = cljs$core$async$state_machine__46373__auto__);

(statearr_47327[(1)] = (1));

return statearr_47327;
});
var cljs$core$async$state_machine__46373__auto____1 = (function (state_47286){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_47286);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e47328){var ex__46376__auto__ = e47328;
var statearr_47329_49189 = state_47286;
(statearr_47329_49189[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_47286[(4)]))){
var statearr_47330_49190 = state_47286;
(statearr_47330_49190[(1)] = cljs.core.first((state_47286[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__49191 = state_47286;
state_47286 = G__49191;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$state_machine__46373__auto__ = function(state_47286){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__46373__auto____1.call(this,state_47286);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__46373__auto____0;
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__46373__auto____1;
return cljs$core$async$state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_47332 = f__46529__auto__();
(statearr_47332[(6)] = c__46528__auto__);

return statearr_47332;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
}));

return c__46528__auto__;
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
var G__47334 = arguments.length;
switch (G__47334) {
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

var cljs$core$async$Mux$muxch_STAR_$dyn_49200 = (function (_){
var x__4550__auto__ = (((_ == null))?null:_);
var m__4551__auto__ = (cljs.core.async.muxch_STAR_[goog.typeOf(x__4550__auto__)]);
if((!((m__4551__auto__ == null)))){
return (m__4551__auto__.cljs$core$IFn$_invoke$arity$1 ? m__4551__auto__.cljs$core$IFn$_invoke$arity$1(_) : m__4551__auto__.call(null,_));
} else {
var m__4549__auto__ = (cljs.core.async.muxch_STAR_["_"]);
if((!((m__4549__auto__ == null)))){
return (m__4549__auto__.cljs$core$IFn$_invoke$arity$1 ? m__4549__auto__.cljs$core$IFn$_invoke$arity$1(_) : m__4549__auto__.call(null,_));
} else {
throw cljs.core.missing_protocol("Mux.muxch*",_);
}
}
});
cljs.core.async.muxch_STAR_ = (function cljs$core$async$muxch_STAR_(_){
if((((!((_ == null)))) && ((!((_.cljs$core$async$Mux$muxch_STAR_$arity$1 == null)))))){
return _.cljs$core$async$Mux$muxch_STAR_$arity$1(_);
} else {
return cljs$core$async$Mux$muxch_STAR_$dyn_49200(_);
}
});


/**
 * @interface
 */
cljs.core.async.Mult = function(){};

var cljs$core$async$Mult$tap_STAR_$dyn_49211 = (function (m,ch,close_QMARK_){
var x__4550__auto__ = (((m == null))?null:m);
var m__4551__auto__ = (cljs.core.async.tap_STAR_[goog.typeOf(x__4550__auto__)]);
if((!((m__4551__auto__ == null)))){
return (m__4551__auto__.cljs$core$IFn$_invoke$arity$3 ? m__4551__auto__.cljs$core$IFn$_invoke$arity$3(m,ch,close_QMARK_) : m__4551__auto__.call(null,m,ch,close_QMARK_));
} else {
var m__4549__auto__ = (cljs.core.async.tap_STAR_["_"]);
if((!((m__4549__auto__ == null)))){
return (m__4549__auto__.cljs$core$IFn$_invoke$arity$3 ? m__4549__auto__.cljs$core$IFn$_invoke$arity$3(m,ch,close_QMARK_) : m__4549__auto__.call(null,m,ch,close_QMARK_));
} else {
throw cljs.core.missing_protocol("Mult.tap*",m);
}
}
});
cljs.core.async.tap_STAR_ = (function cljs$core$async$tap_STAR_(m,ch,close_QMARK_){
if((((!((m == null)))) && ((!((m.cljs$core$async$Mult$tap_STAR_$arity$3 == null)))))){
return m.cljs$core$async$Mult$tap_STAR_$arity$3(m,ch,close_QMARK_);
} else {
return cljs$core$async$Mult$tap_STAR_$dyn_49211(m,ch,close_QMARK_);
}
});

var cljs$core$async$Mult$untap_STAR_$dyn_49217 = (function (m,ch){
var x__4550__auto__ = (((m == null))?null:m);
var m__4551__auto__ = (cljs.core.async.untap_STAR_[goog.typeOf(x__4550__auto__)]);
if((!((m__4551__auto__ == null)))){
return (m__4551__auto__.cljs$core$IFn$_invoke$arity$2 ? m__4551__auto__.cljs$core$IFn$_invoke$arity$2(m,ch) : m__4551__auto__.call(null,m,ch));
} else {
var m__4549__auto__ = (cljs.core.async.untap_STAR_["_"]);
if((!((m__4549__auto__ == null)))){
return (m__4549__auto__.cljs$core$IFn$_invoke$arity$2 ? m__4549__auto__.cljs$core$IFn$_invoke$arity$2(m,ch) : m__4549__auto__.call(null,m,ch));
} else {
throw cljs.core.missing_protocol("Mult.untap*",m);
}
}
});
cljs.core.async.untap_STAR_ = (function cljs$core$async$untap_STAR_(m,ch){
if((((!((m == null)))) && ((!((m.cljs$core$async$Mult$untap_STAR_$arity$2 == null)))))){
return m.cljs$core$async$Mult$untap_STAR_$arity$2(m,ch);
} else {
return cljs$core$async$Mult$untap_STAR_$dyn_49217(m,ch);
}
});

var cljs$core$async$Mult$untap_all_STAR_$dyn_49229 = (function (m){
var x__4550__auto__ = (((m == null))?null:m);
var m__4551__auto__ = (cljs.core.async.untap_all_STAR_[goog.typeOf(x__4550__auto__)]);
if((!((m__4551__auto__ == null)))){
return (m__4551__auto__.cljs$core$IFn$_invoke$arity$1 ? m__4551__auto__.cljs$core$IFn$_invoke$arity$1(m) : m__4551__auto__.call(null,m));
} else {
var m__4549__auto__ = (cljs.core.async.untap_all_STAR_["_"]);
if((!((m__4549__auto__ == null)))){
return (m__4549__auto__.cljs$core$IFn$_invoke$arity$1 ? m__4549__auto__.cljs$core$IFn$_invoke$arity$1(m) : m__4549__auto__.call(null,m));
} else {
throw cljs.core.missing_protocol("Mult.untap-all*",m);
}
}
});
cljs.core.async.untap_all_STAR_ = (function cljs$core$async$untap_all_STAR_(m){
if((((!((m == null)))) && ((!((m.cljs$core$async$Mult$untap_all_STAR_$arity$1 == null)))))){
return m.cljs$core$async$Mult$untap_all_STAR_$arity$1(m);
} else {
return cljs$core$async$Mult$untap_all_STAR_$dyn_49229(m);
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
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async47339 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.Mult}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.async.Mux}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async47339 = (function (ch,cs,meta47340){
this.ch = ch;
this.cs = cs;
this.meta47340 = meta47340;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async47339.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_47341,meta47340__$1){
var self__ = this;
var _47341__$1 = this;
return (new cljs.core.async.t_cljs$core$async47339(self__.ch,self__.cs,meta47340__$1));
}));

(cljs.core.async.t_cljs$core$async47339.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_47341){
var self__ = this;
var _47341__$1 = this;
return self__.meta47340;
}));

(cljs.core.async.t_cljs$core$async47339.prototype.cljs$core$async$Mux$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async47339.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return self__.ch;
}));

(cljs.core.async.t_cljs$core$async47339.prototype.cljs$core$async$Mult$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async47339.prototype.cljs$core$async$Mult$tap_STAR_$arity$3 = (function (_,ch__$1,close_QMARK_){
var self__ = this;
var ___$1 = this;
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(self__.cs,cljs.core.assoc,ch__$1,close_QMARK_);

return null;
}));

(cljs.core.async.t_cljs$core$async47339.prototype.cljs$core$async$Mult$untap_STAR_$arity$2 = (function (_,ch__$1){
var self__ = this;
var ___$1 = this;
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(self__.cs,cljs.core.dissoc,ch__$1);

return null;
}));

(cljs.core.async.t_cljs$core$async47339.prototype.cljs$core$async$Mult$untap_all_STAR_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
cljs.core.reset_BANG_(self__.cs,cljs.core.PersistentArrayMap.EMPTY);

return null;
}));

(cljs.core.async.t_cljs$core$async47339.getBasis = (function (){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"ch","ch",1085813622,null),new cljs.core.Symbol(null,"cs","cs",-117024463,null),new cljs.core.Symbol(null,"meta47340","meta47340",-305613295,null)], null);
}));

(cljs.core.async.t_cljs$core$async47339.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async47339.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async47339");

(cljs.core.async.t_cljs$core$async47339.cljs$lang$ctorPrWriter = (function (this__4491__auto__,writer__4492__auto__,opt__4493__auto__){
return cljs.core._write(writer__4492__auto__,"cljs.core.async/t_cljs$core$async47339");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async47339.
 */
cljs.core.async.__GT_t_cljs$core$async47339 = (function cljs$core$async$mult_$___GT_t_cljs$core$async47339(ch__$1,cs__$1,meta47340){
return (new cljs.core.async.t_cljs$core$async47339(ch__$1,cs__$1,meta47340));
});

}

return (new cljs.core.async.t_cljs$core$async47339(ch,cs,cljs.core.PersistentArrayMap.EMPTY));
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
var c__46528__auto___49241 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_47478){
var state_val_47479 = (state_47478[(1)]);
if((state_val_47479 === (7))){
var inst_47474 = (state_47478[(2)]);
var state_47478__$1 = state_47478;
var statearr_47480_49242 = state_47478__$1;
(statearr_47480_49242[(2)] = inst_47474);

(statearr_47480_49242[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (20))){
var inst_47378 = (state_47478[(7)]);
var inst_47390 = cljs.core.first(inst_47378);
var inst_47391 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_47390,(0),null);
var inst_47392 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_47390,(1),null);
var state_47478__$1 = (function (){var statearr_47482 = state_47478;
(statearr_47482[(8)] = inst_47391);

return statearr_47482;
})();
if(cljs.core.truth_(inst_47392)){
var statearr_47483_49246 = state_47478__$1;
(statearr_47483_49246[(1)] = (22));

} else {
var statearr_47484_49247 = state_47478__$1;
(statearr_47484_49247[(1)] = (23));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (27))){
var inst_47420 = (state_47478[(9)]);
var inst_47427 = (state_47478[(10)]);
var inst_47346 = (state_47478[(11)]);
var inst_47422 = (state_47478[(12)]);
var inst_47427__$1 = cljs.core._nth(inst_47420,inst_47422);
var inst_47428 = cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$3(inst_47427__$1,inst_47346,done);
var state_47478__$1 = (function (){var statearr_47485 = state_47478;
(statearr_47485[(10)] = inst_47427__$1);

return statearr_47485;
})();
if(cljs.core.truth_(inst_47428)){
var statearr_47486_49251 = state_47478__$1;
(statearr_47486_49251[(1)] = (30));

} else {
var statearr_47487_49252 = state_47478__$1;
(statearr_47487_49252[(1)] = (31));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (1))){
var state_47478__$1 = state_47478;
var statearr_47488_49253 = state_47478__$1;
(statearr_47488_49253[(2)] = null);

(statearr_47488_49253[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (24))){
var inst_47378 = (state_47478[(7)]);
var inst_47397 = (state_47478[(2)]);
var inst_47398 = cljs.core.next(inst_47378);
var inst_47355 = inst_47398;
var inst_47356 = null;
var inst_47357 = (0);
var inst_47358 = (0);
var state_47478__$1 = (function (){var statearr_47489 = state_47478;
(statearr_47489[(13)] = inst_47397);

(statearr_47489[(14)] = inst_47357);

(statearr_47489[(15)] = inst_47355);

(statearr_47489[(16)] = inst_47356);

(statearr_47489[(17)] = inst_47358);

return statearr_47489;
})();
var statearr_47491_49254 = state_47478__$1;
(statearr_47491_49254[(2)] = null);

(statearr_47491_49254[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (39))){
var state_47478__$1 = state_47478;
var statearr_47495_49255 = state_47478__$1;
(statearr_47495_49255[(2)] = null);

(statearr_47495_49255[(1)] = (41));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (4))){
var inst_47346 = (state_47478[(11)]);
var inst_47346__$1 = (state_47478[(2)]);
var inst_47347 = (inst_47346__$1 == null);
var state_47478__$1 = (function (){var statearr_47496 = state_47478;
(statearr_47496[(11)] = inst_47346__$1);

return statearr_47496;
})();
if(cljs.core.truth_(inst_47347)){
var statearr_47497_49256 = state_47478__$1;
(statearr_47497_49256[(1)] = (5));

} else {
var statearr_47498_49257 = state_47478__$1;
(statearr_47498_49257[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (15))){
var inst_47357 = (state_47478[(14)]);
var inst_47355 = (state_47478[(15)]);
var inst_47356 = (state_47478[(16)]);
var inst_47358 = (state_47478[(17)]);
var inst_47373 = (state_47478[(2)]);
var inst_47374 = (inst_47358 + (1));
var tmp47492 = inst_47357;
var tmp47493 = inst_47355;
var tmp47494 = inst_47356;
var inst_47355__$1 = tmp47493;
var inst_47356__$1 = tmp47494;
var inst_47357__$1 = tmp47492;
var inst_47358__$1 = inst_47374;
var state_47478__$1 = (function (){var statearr_47499 = state_47478;
(statearr_47499[(14)] = inst_47357__$1);

(statearr_47499[(15)] = inst_47355__$1);

(statearr_47499[(18)] = inst_47373);

(statearr_47499[(16)] = inst_47356__$1);

(statearr_47499[(17)] = inst_47358__$1);

return statearr_47499;
})();
var statearr_47501_49258 = state_47478__$1;
(statearr_47501_49258[(2)] = null);

(statearr_47501_49258[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (21))){
var inst_47401 = (state_47478[(2)]);
var state_47478__$1 = state_47478;
var statearr_47505_49260 = state_47478__$1;
(statearr_47505_49260[(2)] = inst_47401);

(statearr_47505_49260[(1)] = (18));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (31))){
var inst_47427 = (state_47478[(10)]);
var inst_47431 = m.cljs$core$async$Mult$untap_STAR_$arity$2(null,inst_47427);
var state_47478__$1 = state_47478;
var statearr_47506_49261 = state_47478__$1;
(statearr_47506_49261[(2)] = inst_47431);

(statearr_47506_49261[(1)] = (32));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (32))){
var inst_47420 = (state_47478[(9)]);
var inst_47419 = (state_47478[(19)]);
var inst_47422 = (state_47478[(12)]);
var inst_47421 = (state_47478[(20)]);
var inst_47433 = (state_47478[(2)]);
var inst_47434 = (inst_47422 + (1));
var tmp47502 = inst_47420;
var tmp47503 = inst_47419;
var tmp47504 = inst_47421;
var inst_47419__$1 = tmp47503;
var inst_47420__$1 = tmp47502;
var inst_47421__$1 = tmp47504;
var inst_47422__$1 = inst_47434;
var state_47478__$1 = (function (){var statearr_47507 = state_47478;
(statearr_47507[(9)] = inst_47420__$1);

(statearr_47507[(21)] = inst_47433);

(statearr_47507[(19)] = inst_47419__$1);

(statearr_47507[(12)] = inst_47422__$1);

(statearr_47507[(20)] = inst_47421__$1);

return statearr_47507;
})();
var statearr_47509_49271 = state_47478__$1;
(statearr_47509_49271[(2)] = null);

(statearr_47509_49271[(1)] = (25));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (40))){
var inst_47447 = (state_47478[(22)]);
var inst_47451 = m.cljs$core$async$Mult$untap_STAR_$arity$2(null,inst_47447);
var state_47478__$1 = state_47478;
var statearr_47510_49272 = state_47478__$1;
(statearr_47510_49272[(2)] = inst_47451);

(statearr_47510_49272[(1)] = (41));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (33))){
var inst_47437 = (state_47478[(23)]);
var inst_47440 = cljs.core.chunked_seq_QMARK_(inst_47437);
var state_47478__$1 = state_47478;
if(inst_47440){
var statearr_47511_49274 = state_47478__$1;
(statearr_47511_49274[(1)] = (36));

} else {
var statearr_47512_49275 = state_47478__$1;
(statearr_47512_49275[(1)] = (37));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (13))){
var inst_47367 = (state_47478[(24)]);
var inst_47370 = cljs.core.async.close_BANG_(inst_47367);
var state_47478__$1 = state_47478;
var statearr_47513_49276 = state_47478__$1;
(statearr_47513_49276[(2)] = inst_47370);

(statearr_47513_49276[(1)] = (15));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (22))){
var inst_47391 = (state_47478[(8)]);
var inst_47394 = cljs.core.async.close_BANG_(inst_47391);
var state_47478__$1 = state_47478;
var statearr_47515_49277 = state_47478__$1;
(statearr_47515_49277[(2)] = inst_47394);

(statearr_47515_49277[(1)] = (24));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (36))){
var inst_47437 = (state_47478[(23)]);
var inst_47442 = cljs.core.chunk_first(inst_47437);
var inst_47443 = cljs.core.chunk_rest(inst_47437);
var inst_47444 = cljs.core.count(inst_47442);
var inst_47419 = inst_47443;
var inst_47420 = inst_47442;
var inst_47421 = inst_47444;
var inst_47422 = (0);
var state_47478__$1 = (function (){var statearr_47516 = state_47478;
(statearr_47516[(9)] = inst_47420);

(statearr_47516[(19)] = inst_47419);

(statearr_47516[(12)] = inst_47422);

(statearr_47516[(20)] = inst_47421);

return statearr_47516;
})();
var statearr_47517_49278 = state_47478__$1;
(statearr_47517_49278[(2)] = null);

(statearr_47517_49278[(1)] = (25));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (41))){
var inst_47437 = (state_47478[(23)]);
var inst_47453 = (state_47478[(2)]);
var inst_47454 = cljs.core.next(inst_47437);
var inst_47419 = inst_47454;
var inst_47420 = null;
var inst_47421 = (0);
var inst_47422 = (0);
var state_47478__$1 = (function (){var statearr_47518 = state_47478;
(statearr_47518[(9)] = inst_47420);

(statearr_47518[(19)] = inst_47419);

(statearr_47518[(12)] = inst_47422);

(statearr_47518[(25)] = inst_47453);

(statearr_47518[(20)] = inst_47421);

return statearr_47518;
})();
var statearr_47519_49281 = state_47478__$1;
(statearr_47519_49281[(2)] = null);

(statearr_47519_49281[(1)] = (25));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (43))){
var state_47478__$1 = state_47478;
var statearr_47520_49282 = state_47478__$1;
(statearr_47520_49282[(2)] = null);

(statearr_47520_49282[(1)] = (44));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (29))){
var inst_47462 = (state_47478[(2)]);
var state_47478__$1 = state_47478;
var statearr_47521_49285 = state_47478__$1;
(statearr_47521_49285[(2)] = inst_47462);

(statearr_47521_49285[(1)] = (26));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (44))){
var inst_47471 = (state_47478[(2)]);
var state_47478__$1 = (function (){var statearr_47523 = state_47478;
(statearr_47523[(26)] = inst_47471);

return statearr_47523;
})();
var statearr_47524_49286 = state_47478__$1;
(statearr_47524_49286[(2)] = null);

(statearr_47524_49286[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (6))){
var inst_47411 = (state_47478[(27)]);
var inst_47410 = cljs.core.deref(cs);
var inst_47411__$1 = cljs.core.keys(inst_47410);
var inst_47412 = cljs.core.count(inst_47411__$1);
var inst_47413 = cljs.core.reset_BANG_(dctr,inst_47412);
var inst_47418 = cljs.core.seq(inst_47411__$1);
var inst_47419 = inst_47418;
var inst_47420 = null;
var inst_47421 = (0);
var inst_47422 = (0);
var state_47478__$1 = (function (){var statearr_47525 = state_47478;
(statearr_47525[(9)] = inst_47420);

(statearr_47525[(28)] = inst_47413);

(statearr_47525[(27)] = inst_47411__$1);

(statearr_47525[(19)] = inst_47419);

(statearr_47525[(12)] = inst_47422);

(statearr_47525[(20)] = inst_47421);

return statearr_47525;
})();
var statearr_47526_49289 = state_47478__$1;
(statearr_47526_49289[(2)] = null);

(statearr_47526_49289[(1)] = (25));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (28))){
var inst_47437 = (state_47478[(23)]);
var inst_47419 = (state_47478[(19)]);
var inst_47437__$1 = cljs.core.seq(inst_47419);
var state_47478__$1 = (function (){var statearr_47527 = state_47478;
(statearr_47527[(23)] = inst_47437__$1);

return statearr_47527;
})();
if(inst_47437__$1){
var statearr_47528_49290 = state_47478__$1;
(statearr_47528_49290[(1)] = (33));

} else {
var statearr_47529_49292 = state_47478__$1;
(statearr_47529_49292[(1)] = (34));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (25))){
var inst_47422 = (state_47478[(12)]);
var inst_47421 = (state_47478[(20)]);
var inst_47424 = (inst_47422 < inst_47421);
var inst_47425 = inst_47424;
var state_47478__$1 = state_47478;
if(cljs.core.truth_(inst_47425)){
var statearr_47530_49293 = state_47478__$1;
(statearr_47530_49293[(1)] = (27));

} else {
var statearr_47531_49298 = state_47478__$1;
(statearr_47531_49298[(1)] = (28));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (34))){
var state_47478__$1 = state_47478;
var statearr_47533_49299 = state_47478__$1;
(statearr_47533_49299[(2)] = null);

(statearr_47533_49299[(1)] = (35));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (17))){
var state_47478__$1 = state_47478;
var statearr_47534_49300 = state_47478__$1;
(statearr_47534_49300[(2)] = null);

(statearr_47534_49300[(1)] = (18));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (3))){
var inst_47476 = (state_47478[(2)]);
var state_47478__$1 = state_47478;
return cljs.core.async.impl.ioc_helpers.return_chan(state_47478__$1,inst_47476);
} else {
if((state_val_47479 === (12))){
var inst_47406 = (state_47478[(2)]);
var state_47478__$1 = state_47478;
var statearr_47535_49301 = state_47478__$1;
(statearr_47535_49301[(2)] = inst_47406);

(statearr_47535_49301[(1)] = (9));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (2))){
var state_47478__$1 = state_47478;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_47478__$1,(4),ch);
} else {
if((state_val_47479 === (23))){
var state_47478__$1 = state_47478;
var statearr_47536_49302 = state_47478__$1;
(statearr_47536_49302[(2)] = null);

(statearr_47536_49302[(1)] = (24));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (35))){
var inst_47460 = (state_47478[(2)]);
var state_47478__$1 = state_47478;
var statearr_47537_49303 = state_47478__$1;
(statearr_47537_49303[(2)] = inst_47460);

(statearr_47537_49303[(1)] = (29));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (19))){
var inst_47378 = (state_47478[(7)]);
var inst_47382 = cljs.core.chunk_first(inst_47378);
var inst_47383 = cljs.core.chunk_rest(inst_47378);
var inst_47384 = cljs.core.count(inst_47382);
var inst_47355 = inst_47383;
var inst_47356 = inst_47382;
var inst_47357 = inst_47384;
var inst_47358 = (0);
var state_47478__$1 = (function (){var statearr_47539 = state_47478;
(statearr_47539[(14)] = inst_47357);

(statearr_47539[(15)] = inst_47355);

(statearr_47539[(16)] = inst_47356);

(statearr_47539[(17)] = inst_47358);

return statearr_47539;
})();
var statearr_47540_49304 = state_47478__$1;
(statearr_47540_49304[(2)] = null);

(statearr_47540_49304[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (11))){
var inst_47355 = (state_47478[(15)]);
var inst_47378 = (state_47478[(7)]);
var inst_47378__$1 = cljs.core.seq(inst_47355);
var state_47478__$1 = (function (){var statearr_47541 = state_47478;
(statearr_47541[(7)] = inst_47378__$1);

return statearr_47541;
})();
if(inst_47378__$1){
var statearr_47542_49305 = state_47478__$1;
(statearr_47542_49305[(1)] = (16));

} else {
var statearr_47543_49306 = state_47478__$1;
(statearr_47543_49306[(1)] = (17));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (9))){
var inst_47408 = (state_47478[(2)]);
var state_47478__$1 = state_47478;
var statearr_47544_49307 = state_47478__$1;
(statearr_47544_49307[(2)] = inst_47408);

(statearr_47544_49307[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (5))){
var inst_47353 = cljs.core.deref(cs);
var inst_47354 = cljs.core.seq(inst_47353);
var inst_47355 = inst_47354;
var inst_47356 = null;
var inst_47357 = (0);
var inst_47358 = (0);
var state_47478__$1 = (function (){var statearr_47545 = state_47478;
(statearr_47545[(14)] = inst_47357);

(statearr_47545[(15)] = inst_47355);

(statearr_47545[(16)] = inst_47356);

(statearr_47545[(17)] = inst_47358);

return statearr_47545;
})();
var statearr_47546_49312 = state_47478__$1;
(statearr_47546_49312[(2)] = null);

(statearr_47546_49312[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (14))){
var state_47478__$1 = state_47478;
var statearr_47548_49313 = state_47478__$1;
(statearr_47548_49313[(2)] = null);

(statearr_47548_49313[(1)] = (15));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (45))){
var inst_47468 = (state_47478[(2)]);
var state_47478__$1 = state_47478;
var statearr_47549_49314 = state_47478__$1;
(statearr_47549_49314[(2)] = inst_47468);

(statearr_47549_49314[(1)] = (44));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (26))){
var inst_47411 = (state_47478[(27)]);
var inst_47464 = (state_47478[(2)]);
var inst_47465 = cljs.core.seq(inst_47411);
var state_47478__$1 = (function (){var statearr_47550 = state_47478;
(statearr_47550[(29)] = inst_47464);

return statearr_47550;
})();
if(inst_47465){
var statearr_47551_49319 = state_47478__$1;
(statearr_47551_49319[(1)] = (42));

} else {
var statearr_47552_49320 = state_47478__$1;
(statearr_47552_49320[(1)] = (43));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (16))){
var inst_47378 = (state_47478[(7)]);
var inst_47380 = cljs.core.chunked_seq_QMARK_(inst_47378);
var state_47478__$1 = state_47478;
if(inst_47380){
var statearr_47553_49324 = state_47478__$1;
(statearr_47553_49324[(1)] = (19));

} else {
var statearr_47554_49325 = state_47478__$1;
(statearr_47554_49325[(1)] = (20));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (38))){
var inst_47457 = (state_47478[(2)]);
var state_47478__$1 = state_47478;
var statearr_47556_49327 = state_47478__$1;
(statearr_47556_49327[(2)] = inst_47457);

(statearr_47556_49327[(1)] = (35));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (30))){
var state_47478__$1 = state_47478;
var statearr_47557_49328 = state_47478__$1;
(statearr_47557_49328[(2)] = null);

(statearr_47557_49328[(1)] = (32));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (10))){
var inst_47356 = (state_47478[(16)]);
var inst_47358 = (state_47478[(17)]);
var inst_47366 = cljs.core._nth(inst_47356,inst_47358);
var inst_47367 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_47366,(0),null);
var inst_47368 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_47366,(1),null);
var state_47478__$1 = (function (){var statearr_47558 = state_47478;
(statearr_47558[(24)] = inst_47367);

return statearr_47558;
})();
if(cljs.core.truth_(inst_47368)){
var statearr_47559_49332 = state_47478__$1;
(statearr_47559_49332[(1)] = (13));

} else {
var statearr_47560_49333 = state_47478__$1;
(statearr_47560_49333[(1)] = (14));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (18))){
var inst_47404 = (state_47478[(2)]);
var state_47478__$1 = state_47478;
var statearr_47561_49334 = state_47478__$1;
(statearr_47561_49334[(2)] = inst_47404);

(statearr_47561_49334[(1)] = (12));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (42))){
var state_47478__$1 = state_47478;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_47478__$1,(45),dchan);
} else {
if((state_val_47479 === (37))){
var inst_47437 = (state_47478[(23)]);
var inst_47346 = (state_47478[(11)]);
var inst_47447 = (state_47478[(22)]);
var inst_47447__$1 = cljs.core.first(inst_47437);
var inst_47448 = cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$3(inst_47447__$1,inst_47346,done);
var state_47478__$1 = (function (){var statearr_47563 = state_47478;
(statearr_47563[(22)] = inst_47447__$1);

return statearr_47563;
})();
if(cljs.core.truth_(inst_47448)){
var statearr_47564_49344 = state_47478__$1;
(statearr_47564_49344[(1)] = (39));

} else {
var statearr_47565_49345 = state_47478__$1;
(statearr_47565_49345[(1)] = (40));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47479 === (8))){
var inst_47357 = (state_47478[(14)]);
var inst_47358 = (state_47478[(17)]);
var inst_47360 = (inst_47358 < inst_47357);
var inst_47361 = inst_47360;
var state_47478__$1 = state_47478;
if(cljs.core.truth_(inst_47361)){
var statearr_47566_49346 = state_47478__$1;
(statearr_47566_49346[(1)] = (10));

} else {
var statearr_47567_49347 = state_47478__$1;
(statearr_47567_49347[(1)] = (11));

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
var cljs$core$async$mult_$_state_machine__46373__auto__ = null;
var cljs$core$async$mult_$_state_machine__46373__auto____0 = (function (){
var statearr_47568 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_47568[(0)] = cljs$core$async$mult_$_state_machine__46373__auto__);

(statearr_47568[(1)] = (1));

return statearr_47568;
});
var cljs$core$async$mult_$_state_machine__46373__auto____1 = (function (state_47478){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_47478);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e47569){var ex__46376__auto__ = e47569;
var statearr_47570_49354 = state_47478;
(statearr_47570_49354[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_47478[(4)]))){
var statearr_47572_49355 = state_47478;
(statearr_47572_49355[(1)] = cljs.core.first((state_47478[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__49361 = state_47478;
state_47478 = G__49361;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$mult_$_state_machine__46373__auto__ = function(state_47478){
switch(arguments.length){
case 0:
return cljs$core$async$mult_$_state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$mult_$_state_machine__46373__auto____1.call(this,state_47478);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$mult_$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$mult_$_state_machine__46373__auto____0;
cljs$core$async$mult_$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$mult_$_state_machine__46373__auto____1;
return cljs$core$async$mult_$_state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_47573 = f__46529__auto__();
(statearr_47573[(6)] = c__46528__auto___49241);

return statearr_47573;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
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
var G__47575 = arguments.length;
switch (G__47575) {
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

var cljs$core$async$Mix$admix_STAR_$dyn_49372 = (function (m,ch){
var x__4550__auto__ = (((m == null))?null:m);
var m__4551__auto__ = (cljs.core.async.admix_STAR_[goog.typeOf(x__4550__auto__)]);
if((!((m__4551__auto__ == null)))){
return (m__4551__auto__.cljs$core$IFn$_invoke$arity$2 ? m__4551__auto__.cljs$core$IFn$_invoke$arity$2(m,ch) : m__4551__auto__.call(null,m,ch));
} else {
var m__4549__auto__ = (cljs.core.async.admix_STAR_["_"]);
if((!((m__4549__auto__ == null)))){
return (m__4549__auto__.cljs$core$IFn$_invoke$arity$2 ? m__4549__auto__.cljs$core$IFn$_invoke$arity$2(m,ch) : m__4549__auto__.call(null,m,ch));
} else {
throw cljs.core.missing_protocol("Mix.admix*",m);
}
}
});
cljs.core.async.admix_STAR_ = (function cljs$core$async$admix_STAR_(m,ch){
if((((!((m == null)))) && ((!((m.cljs$core$async$Mix$admix_STAR_$arity$2 == null)))))){
return m.cljs$core$async$Mix$admix_STAR_$arity$2(m,ch);
} else {
return cljs$core$async$Mix$admix_STAR_$dyn_49372(m,ch);
}
});

var cljs$core$async$Mix$unmix_STAR_$dyn_49380 = (function (m,ch){
var x__4550__auto__ = (((m == null))?null:m);
var m__4551__auto__ = (cljs.core.async.unmix_STAR_[goog.typeOf(x__4550__auto__)]);
if((!((m__4551__auto__ == null)))){
return (m__4551__auto__.cljs$core$IFn$_invoke$arity$2 ? m__4551__auto__.cljs$core$IFn$_invoke$arity$2(m,ch) : m__4551__auto__.call(null,m,ch));
} else {
var m__4549__auto__ = (cljs.core.async.unmix_STAR_["_"]);
if((!((m__4549__auto__ == null)))){
return (m__4549__auto__.cljs$core$IFn$_invoke$arity$2 ? m__4549__auto__.cljs$core$IFn$_invoke$arity$2(m,ch) : m__4549__auto__.call(null,m,ch));
} else {
throw cljs.core.missing_protocol("Mix.unmix*",m);
}
}
});
cljs.core.async.unmix_STAR_ = (function cljs$core$async$unmix_STAR_(m,ch){
if((((!((m == null)))) && ((!((m.cljs$core$async$Mix$unmix_STAR_$arity$2 == null)))))){
return m.cljs$core$async$Mix$unmix_STAR_$arity$2(m,ch);
} else {
return cljs$core$async$Mix$unmix_STAR_$dyn_49380(m,ch);
}
});

var cljs$core$async$Mix$unmix_all_STAR_$dyn_49384 = (function (m){
var x__4550__auto__ = (((m == null))?null:m);
var m__4551__auto__ = (cljs.core.async.unmix_all_STAR_[goog.typeOf(x__4550__auto__)]);
if((!((m__4551__auto__ == null)))){
return (m__4551__auto__.cljs$core$IFn$_invoke$arity$1 ? m__4551__auto__.cljs$core$IFn$_invoke$arity$1(m) : m__4551__auto__.call(null,m));
} else {
var m__4549__auto__ = (cljs.core.async.unmix_all_STAR_["_"]);
if((!((m__4549__auto__ == null)))){
return (m__4549__auto__.cljs$core$IFn$_invoke$arity$1 ? m__4549__auto__.cljs$core$IFn$_invoke$arity$1(m) : m__4549__auto__.call(null,m));
} else {
throw cljs.core.missing_protocol("Mix.unmix-all*",m);
}
}
});
cljs.core.async.unmix_all_STAR_ = (function cljs$core$async$unmix_all_STAR_(m){
if((((!((m == null)))) && ((!((m.cljs$core$async$Mix$unmix_all_STAR_$arity$1 == null)))))){
return m.cljs$core$async$Mix$unmix_all_STAR_$arity$1(m);
} else {
return cljs$core$async$Mix$unmix_all_STAR_$dyn_49384(m);
}
});

var cljs$core$async$Mix$toggle_STAR_$dyn_49385 = (function (m,state_map){
var x__4550__auto__ = (((m == null))?null:m);
var m__4551__auto__ = (cljs.core.async.toggle_STAR_[goog.typeOf(x__4550__auto__)]);
if((!((m__4551__auto__ == null)))){
return (m__4551__auto__.cljs$core$IFn$_invoke$arity$2 ? m__4551__auto__.cljs$core$IFn$_invoke$arity$2(m,state_map) : m__4551__auto__.call(null,m,state_map));
} else {
var m__4549__auto__ = (cljs.core.async.toggle_STAR_["_"]);
if((!((m__4549__auto__ == null)))){
return (m__4549__auto__.cljs$core$IFn$_invoke$arity$2 ? m__4549__auto__.cljs$core$IFn$_invoke$arity$2(m,state_map) : m__4549__auto__.call(null,m,state_map));
} else {
throw cljs.core.missing_protocol("Mix.toggle*",m);
}
}
});
cljs.core.async.toggle_STAR_ = (function cljs$core$async$toggle_STAR_(m,state_map){
if((((!((m == null)))) && ((!((m.cljs$core$async$Mix$toggle_STAR_$arity$2 == null)))))){
return m.cljs$core$async$Mix$toggle_STAR_$arity$2(m,state_map);
} else {
return cljs$core$async$Mix$toggle_STAR_$dyn_49385(m,state_map);
}
});

var cljs$core$async$Mix$solo_mode_STAR_$dyn_49391 = (function (m,mode){
var x__4550__auto__ = (((m == null))?null:m);
var m__4551__auto__ = (cljs.core.async.solo_mode_STAR_[goog.typeOf(x__4550__auto__)]);
if((!((m__4551__auto__ == null)))){
return (m__4551__auto__.cljs$core$IFn$_invoke$arity$2 ? m__4551__auto__.cljs$core$IFn$_invoke$arity$2(m,mode) : m__4551__auto__.call(null,m,mode));
} else {
var m__4549__auto__ = (cljs.core.async.solo_mode_STAR_["_"]);
if((!((m__4549__auto__ == null)))){
return (m__4549__auto__.cljs$core$IFn$_invoke$arity$2 ? m__4549__auto__.cljs$core$IFn$_invoke$arity$2(m,mode) : m__4549__auto__.call(null,m,mode));
} else {
throw cljs.core.missing_protocol("Mix.solo-mode*",m);
}
}
});
cljs.core.async.solo_mode_STAR_ = (function cljs$core$async$solo_mode_STAR_(m,mode){
if((((!((m == null)))) && ((!((m.cljs$core$async$Mix$solo_mode_STAR_$arity$2 == null)))))){
return m.cljs$core$async$Mix$solo_mode_STAR_$arity$2(m,mode);
} else {
return cljs$core$async$Mix$solo_mode_STAR_$dyn_49391(m,mode);
}
});

cljs.core.async.ioc_alts_BANG_ = (function cljs$core$async$ioc_alts_BANG_(var_args){
var args__4870__auto__ = [];
var len__4864__auto___49397 = arguments.length;
var i__4865__auto___49398 = (0);
while(true){
if((i__4865__auto___49398 < len__4864__auto___49397)){
args__4870__auto__.push((arguments[i__4865__auto___49398]));

var G__49399 = (i__4865__auto___49398 + (1));
i__4865__auto___49398 = G__49399;
continue;
} else {
}
break;
}

var argseq__4871__auto__ = ((((3) < args__4870__auto__.length))?(new cljs.core.IndexedSeq(args__4870__auto__.slice((3)),(0),null)):null);
return cljs.core.async.ioc_alts_BANG_.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),argseq__4871__auto__);
});

(cljs.core.async.ioc_alts_BANG_.cljs$core$IFn$_invoke$arity$variadic = (function (state,cont_block,ports,p__47585){
var map__47586 = p__47585;
var map__47586__$1 = cljs.core.__destructure_map(map__47586);
var opts = map__47586__$1;
var statearr_47588_49400 = state;
(statearr_47588_49400[(1)] = cont_block);


var temp__5753__auto__ = cljs.core.async.do_alts((function (val){
var statearr_47589_49401 = state;
(statearr_47589_49401[(2)] = val);


return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state);
}),ports,opts);
if(cljs.core.truth_(temp__5753__auto__)){
var cb = temp__5753__auto__;
var statearr_47590_49402 = state;
(statearr_47590_49402[(2)] = cljs.core.deref(cb));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
return null;
}
}));

(cljs.core.async.ioc_alts_BANG_.cljs$lang$maxFixedArity = (3));

/** @this {Function} */
(cljs.core.async.ioc_alts_BANG_.cljs$lang$applyTo = (function (seq47581){
var G__47582 = cljs.core.first(seq47581);
var seq47581__$1 = cljs.core.next(seq47581);
var G__47583 = cljs.core.first(seq47581__$1);
var seq47581__$2 = cljs.core.next(seq47581__$1);
var G__47584 = cljs.core.first(seq47581__$2);
var seq47581__$3 = cljs.core.next(seq47581__$2);
var self__4851__auto__ = this;
return self__4851__auto__.cljs$core$IFn$_invoke$arity$variadic(G__47582,G__47583,G__47584,seq47581__$3);
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
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async47593 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.async.Mix}
 * @implements {cljs.core.async.Mux}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async47593 = (function (change,solo_mode,pick,cs,calc_state,out,changed,solo_modes,attrs,meta47594){
this.change = change;
this.solo_mode = solo_mode;
this.pick = pick;
this.cs = cs;
this.calc_state = calc_state;
this.out = out;
this.changed = changed;
this.solo_modes = solo_modes;
this.attrs = attrs;
this.meta47594 = meta47594;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async47593.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_47595,meta47594__$1){
var self__ = this;
var _47595__$1 = this;
return (new cljs.core.async.t_cljs$core$async47593(self__.change,self__.solo_mode,self__.pick,self__.cs,self__.calc_state,self__.out,self__.changed,self__.solo_modes,self__.attrs,meta47594__$1));
}));

(cljs.core.async.t_cljs$core$async47593.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_47595){
var self__ = this;
var _47595__$1 = this;
return self__.meta47594;
}));

(cljs.core.async.t_cljs$core$async47593.prototype.cljs$core$async$Mux$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async47593.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return self__.out;
}));

(cljs.core.async.t_cljs$core$async47593.prototype.cljs$core$async$Mix$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async47593.prototype.cljs$core$async$Mix$admix_STAR_$arity$2 = (function (_,ch){
var self__ = this;
var ___$1 = this;
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(self__.cs,cljs.core.assoc,ch,cljs.core.PersistentArrayMap.EMPTY);

return (self__.changed.cljs$core$IFn$_invoke$arity$0 ? self__.changed.cljs$core$IFn$_invoke$arity$0() : self__.changed.call(null));
}));

(cljs.core.async.t_cljs$core$async47593.prototype.cljs$core$async$Mix$unmix_STAR_$arity$2 = (function (_,ch){
var self__ = this;
var ___$1 = this;
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(self__.cs,cljs.core.dissoc,ch);

return (self__.changed.cljs$core$IFn$_invoke$arity$0 ? self__.changed.cljs$core$IFn$_invoke$arity$0() : self__.changed.call(null));
}));

(cljs.core.async.t_cljs$core$async47593.prototype.cljs$core$async$Mix$unmix_all_STAR_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
cljs.core.reset_BANG_(self__.cs,cljs.core.PersistentArrayMap.EMPTY);

return (self__.changed.cljs$core$IFn$_invoke$arity$0 ? self__.changed.cljs$core$IFn$_invoke$arity$0() : self__.changed.call(null));
}));

(cljs.core.async.t_cljs$core$async47593.prototype.cljs$core$async$Mix$toggle_STAR_$arity$2 = (function (_,state_map){
var self__ = this;
var ___$1 = this;
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(self__.cs,cljs.core.partial.cljs$core$IFn$_invoke$arity$2(cljs.core.merge_with,cljs.core.merge),state_map);

return (self__.changed.cljs$core$IFn$_invoke$arity$0 ? self__.changed.cljs$core$IFn$_invoke$arity$0() : self__.changed.call(null));
}));

(cljs.core.async.t_cljs$core$async47593.prototype.cljs$core$async$Mix$solo_mode_STAR_$arity$2 = (function (_,mode){
var self__ = this;
var ___$1 = this;
if(cljs.core.truth_((self__.solo_modes.cljs$core$IFn$_invoke$arity$1 ? self__.solo_modes.cljs$core$IFn$_invoke$arity$1(mode) : self__.solo_modes.call(null,mode)))){
} else {
throw (new Error(["Assert failed: ",["mode must be one of: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(self__.solo_modes)].join(''),"\n","(solo-modes mode)"].join('')));
}

cljs.core.reset_BANG_(self__.solo_mode,mode);

return (self__.changed.cljs$core$IFn$_invoke$arity$0 ? self__.changed.cljs$core$IFn$_invoke$arity$0() : self__.changed.call(null));
}));

(cljs.core.async.t_cljs$core$async47593.getBasis = (function (){
return new cljs.core.PersistentVector(null, 10, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"change","change",477485025,null),new cljs.core.Symbol(null,"solo-mode","solo-mode",2031788074,null),new cljs.core.Symbol(null,"pick","pick",1300068175,null),new cljs.core.Symbol(null,"cs","cs",-117024463,null),new cljs.core.Symbol(null,"calc-state","calc-state",-349968968,null),new cljs.core.Symbol(null,"out","out",729986010,null),new cljs.core.Symbol(null,"changed","changed",-2083710852,null),new cljs.core.Symbol(null,"solo-modes","solo-modes",882180540,null),new cljs.core.Symbol(null,"attrs","attrs",-450137186,null),new cljs.core.Symbol(null,"meta47594","meta47594",717882110,null)], null);
}));

(cljs.core.async.t_cljs$core$async47593.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async47593.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async47593");

(cljs.core.async.t_cljs$core$async47593.cljs$lang$ctorPrWriter = (function (this__4491__auto__,writer__4492__auto__,opt__4493__auto__){
return cljs.core._write(writer__4492__auto__,"cljs.core.async/t_cljs$core$async47593");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async47593.
 */
cljs.core.async.__GT_t_cljs$core$async47593 = (function cljs$core$async$mix_$___GT_t_cljs$core$async47593(change__$1,solo_mode__$1,pick__$1,cs__$1,calc_state__$1,out__$1,changed__$1,solo_modes__$1,attrs__$1,meta47594){
return (new cljs.core.async.t_cljs$core$async47593(change__$1,solo_mode__$1,pick__$1,cs__$1,calc_state__$1,out__$1,changed__$1,solo_modes__$1,attrs__$1,meta47594));
});

}

return (new cljs.core.async.t_cljs$core$async47593(change,solo_mode,pick,cs,calc_state,out,changed,solo_modes,attrs,cljs.core.PersistentArrayMap.EMPTY));
})()
;
var c__46528__auto___49424 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_47666){
var state_val_47667 = (state_47666[(1)]);
if((state_val_47667 === (7))){
var inst_47625 = (state_47666[(2)]);
var state_47666__$1 = state_47666;
if(cljs.core.truth_(inst_47625)){
var statearr_47668_49425 = state_47666__$1;
(statearr_47668_49425[(1)] = (8));

} else {
var statearr_47669_49427 = state_47666__$1;
(statearr_47669_49427[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (20))){
var inst_47618 = (state_47666[(7)]);
var state_47666__$1 = state_47666;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_47666__$1,(23),out,inst_47618);
} else {
if((state_val_47667 === (1))){
var inst_47601 = calc_state();
var inst_47602 = cljs.core.__destructure_map(inst_47601);
var inst_47603 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_47602,new cljs.core.Keyword(null,"solos","solos",1441458643));
var inst_47604 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_47602,new cljs.core.Keyword(null,"mutes","mutes",1068806309));
var inst_47605 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_47602,new cljs.core.Keyword(null,"reads","reads",-1215067361));
var inst_47606 = inst_47601;
var state_47666__$1 = (function (){var statearr_47670 = state_47666;
(statearr_47670[(8)] = inst_47604);

(statearr_47670[(9)] = inst_47606);

(statearr_47670[(10)] = inst_47605);

(statearr_47670[(11)] = inst_47603);

return statearr_47670;
})();
var statearr_47672_49431 = state_47666__$1;
(statearr_47672_49431[(2)] = null);

(statearr_47672_49431[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (24))){
var inst_47609 = (state_47666[(12)]);
var inst_47606 = inst_47609;
var state_47666__$1 = (function (){var statearr_47673 = state_47666;
(statearr_47673[(9)] = inst_47606);

return statearr_47673;
})();
var statearr_47674_49439 = state_47666__$1;
(statearr_47674_49439[(2)] = null);

(statearr_47674_49439[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (4))){
var inst_47618 = (state_47666[(7)]);
var inst_47620 = (state_47666[(13)]);
var inst_47617 = (state_47666[(2)]);
var inst_47618__$1 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_47617,(0),null);
var inst_47619 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_47617,(1),null);
var inst_47620__$1 = (inst_47618__$1 == null);
var state_47666__$1 = (function (){var statearr_47675 = state_47666;
(statearr_47675[(7)] = inst_47618__$1);

(statearr_47675[(13)] = inst_47620__$1);

(statearr_47675[(14)] = inst_47619);

return statearr_47675;
})();
if(cljs.core.truth_(inst_47620__$1)){
var statearr_47676_49443 = state_47666__$1;
(statearr_47676_49443[(1)] = (5));

} else {
var statearr_47677_49444 = state_47666__$1;
(statearr_47677_49444[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (15))){
var inst_47639 = (state_47666[(15)]);
var inst_47610 = (state_47666[(16)]);
var inst_47639__$1 = cljs.core.empty_QMARK_(inst_47610);
var state_47666__$1 = (function (){var statearr_47678 = state_47666;
(statearr_47678[(15)] = inst_47639__$1);

return statearr_47678;
})();
if(inst_47639__$1){
var statearr_47679_49445 = state_47666__$1;
(statearr_47679_49445[(1)] = (17));

} else {
var statearr_47680_49446 = state_47666__$1;
(statearr_47680_49446[(1)] = (18));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (21))){
var inst_47609 = (state_47666[(12)]);
var inst_47606 = inst_47609;
var state_47666__$1 = (function (){var statearr_47682 = state_47666;
(statearr_47682[(9)] = inst_47606);

return statearr_47682;
})();
var statearr_47683_49447 = state_47666__$1;
(statearr_47683_49447[(2)] = null);

(statearr_47683_49447[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (13))){
var inst_47632 = (state_47666[(2)]);
var inst_47633 = calc_state();
var inst_47606 = inst_47633;
var state_47666__$1 = (function (){var statearr_47684 = state_47666;
(statearr_47684[(9)] = inst_47606);

(statearr_47684[(17)] = inst_47632);

return statearr_47684;
})();
var statearr_47685_49448 = state_47666__$1;
(statearr_47685_49448[(2)] = null);

(statearr_47685_49448[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (22))){
var inst_47660 = (state_47666[(2)]);
var state_47666__$1 = state_47666;
var statearr_47686_49452 = state_47666__$1;
(statearr_47686_49452[(2)] = inst_47660);

(statearr_47686_49452[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (6))){
var inst_47619 = (state_47666[(14)]);
var inst_47623 = cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(inst_47619,change);
var state_47666__$1 = state_47666;
var statearr_47687_49453 = state_47666__$1;
(statearr_47687_49453[(2)] = inst_47623);

(statearr_47687_49453[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (25))){
var state_47666__$1 = state_47666;
var statearr_47688_49454 = state_47666__$1;
(statearr_47688_49454[(2)] = null);

(statearr_47688_49454[(1)] = (26));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (17))){
var inst_47619 = (state_47666[(14)]);
var inst_47611 = (state_47666[(18)]);
var inst_47641 = (inst_47611.cljs$core$IFn$_invoke$arity$1 ? inst_47611.cljs$core$IFn$_invoke$arity$1(inst_47619) : inst_47611.call(null,inst_47619));
var inst_47642 = cljs.core.not(inst_47641);
var state_47666__$1 = state_47666;
var statearr_47690_49455 = state_47666__$1;
(statearr_47690_49455[(2)] = inst_47642);

(statearr_47690_49455[(1)] = (19));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (3))){
var inst_47664 = (state_47666[(2)]);
var state_47666__$1 = state_47666;
return cljs.core.async.impl.ioc_helpers.return_chan(state_47666__$1,inst_47664);
} else {
if((state_val_47667 === (12))){
var state_47666__$1 = state_47666;
var statearr_47691_49456 = state_47666__$1;
(statearr_47691_49456[(2)] = null);

(statearr_47691_49456[(1)] = (13));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (2))){
var inst_47609 = (state_47666[(12)]);
var inst_47606 = (state_47666[(9)]);
var inst_47609__$1 = cljs.core.__destructure_map(inst_47606);
var inst_47610 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_47609__$1,new cljs.core.Keyword(null,"solos","solos",1441458643));
var inst_47611 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_47609__$1,new cljs.core.Keyword(null,"mutes","mutes",1068806309));
var inst_47612 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_47609__$1,new cljs.core.Keyword(null,"reads","reads",-1215067361));
var state_47666__$1 = (function (){var statearr_47692 = state_47666;
(statearr_47692[(12)] = inst_47609__$1);

(statearr_47692[(16)] = inst_47610);

(statearr_47692[(18)] = inst_47611);

return statearr_47692;
})();
return cljs.core.async.ioc_alts_BANG_(state_47666__$1,(4),inst_47612);
} else {
if((state_val_47667 === (23))){
var inst_47650 = (state_47666[(2)]);
var state_47666__$1 = state_47666;
if(cljs.core.truth_(inst_47650)){
var statearr_47693_49458 = state_47666__$1;
(statearr_47693_49458[(1)] = (24));

} else {
var statearr_47695_49462 = state_47666__$1;
(statearr_47695_49462[(1)] = (25));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (19))){
var inst_47645 = (state_47666[(2)]);
var state_47666__$1 = state_47666;
var statearr_47696_49463 = state_47666__$1;
(statearr_47696_49463[(2)] = inst_47645);

(statearr_47696_49463[(1)] = (16));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (11))){
var inst_47619 = (state_47666[(14)]);
var inst_47629 = cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(cs,cljs.core.dissoc,inst_47619);
var state_47666__$1 = state_47666;
var statearr_47697_49464 = state_47666__$1;
(statearr_47697_49464[(2)] = inst_47629);

(statearr_47697_49464[(1)] = (13));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (9))){
var inst_47636 = (state_47666[(19)]);
var inst_47619 = (state_47666[(14)]);
var inst_47610 = (state_47666[(16)]);
var inst_47636__$1 = (inst_47610.cljs$core$IFn$_invoke$arity$1 ? inst_47610.cljs$core$IFn$_invoke$arity$1(inst_47619) : inst_47610.call(null,inst_47619));
var state_47666__$1 = (function (){var statearr_47698 = state_47666;
(statearr_47698[(19)] = inst_47636__$1);

return statearr_47698;
})();
if(cljs.core.truth_(inst_47636__$1)){
var statearr_47699_49465 = state_47666__$1;
(statearr_47699_49465[(1)] = (14));

} else {
var statearr_47700_49466 = state_47666__$1;
(statearr_47700_49466[(1)] = (15));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (5))){
var inst_47620 = (state_47666[(13)]);
var state_47666__$1 = state_47666;
var statearr_47702_49467 = state_47666__$1;
(statearr_47702_49467[(2)] = inst_47620);

(statearr_47702_49467[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (14))){
var inst_47636 = (state_47666[(19)]);
var state_47666__$1 = state_47666;
var statearr_47704_49468 = state_47666__$1;
(statearr_47704_49468[(2)] = inst_47636);

(statearr_47704_49468[(1)] = (16));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (26))){
var inst_47656 = (state_47666[(2)]);
var state_47666__$1 = state_47666;
var statearr_47705_49469 = state_47666__$1;
(statearr_47705_49469[(2)] = inst_47656);

(statearr_47705_49469[(1)] = (22));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (16))){
var inst_47647 = (state_47666[(2)]);
var state_47666__$1 = state_47666;
if(cljs.core.truth_(inst_47647)){
var statearr_47706_49470 = state_47666__$1;
(statearr_47706_49470[(1)] = (20));

} else {
var statearr_47707_49471 = state_47666__$1;
(statearr_47707_49471[(1)] = (21));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (10))){
var inst_47662 = (state_47666[(2)]);
var state_47666__$1 = state_47666;
var statearr_47708_49472 = state_47666__$1;
(statearr_47708_49472[(2)] = inst_47662);

(statearr_47708_49472[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (18))){
var inst_47639 = (state_47666[(15)]);
var state_47666__$1 = state_47666;
var statearr_47709_49473 = state_47666__$1;
(statearr_47709_49473[(2)] = inst_47639);

(statearr_47709_49473[(1)] = (19));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47667 === (8))){
var inst_47618 = (state_47666[(7)]);
var inst_47627 = (inst_47618 == null);
var state_47666__$1 = state_47666;
if(cljs.core.truth_(inst_47627)){
var statearr_47710_49474 = state_47666__$1;
(statearr_47710_49474[(1)] = (11));

} else {
var statearr_47711_49475 = state_47666__$1;
(statearr_47711_49475[(1)] = (12));

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
var cljs$core$async$mix_$_state_machine__46373__auto__ = null;
var cljs$core$async$mix_$_state_machine__46373__auto____0 = (function (){
var statearr_47714 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_47714[(0)] = cljs$core$async$mix_$_state_machine__46373__auto__);

(statearr_47714[(1)] = (1));

return statearr_47714;
});
var cljs$core$async$mix_$_state_machine__46373__auto____1 = (function (state_47666){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_47666);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e47715){var ex__46376__auto__ = e47715;
var statearr_47716_49476 = state_47666;
(statearr_47716_49476[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_47666[(4)]))){
var statearr_47717_49477 = state_47666;
(statearr_47717_49477[(1)] = cljs.core.first((state_47666[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__49478 = state_47666;
state_47666 = G__49478;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$mix_$_state_machine__46373__auto__ = function(state_47666){
switch(arguments.length){
case 0:
return cljs$core$async$mix_$_state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$mix_$_state_machine__46373__auto____1.call(this,state_47666);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$mix_$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$mix_$_state_machine__46373__auto____0;
cljs$core$async$mix_$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$mix_$_state_machine__46373__auto____1;
return cljs$core$async$mix_$_state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_47718 = f__46529__auto__();
(statearr_47718[(6)] = c__46528__auto___49424);

return statearr_47718;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
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

var cljs$core$async$Pub$sub_STAR_$dyn_49480 = (function (p,v,ch,close_QMARK_){
var x__4550__auto__ = (((p == null))?null:p);
var m__4551__auto__ = (cljs.core.async.sub_STAR_[goog.typeOf(x__4550__auto__)]);
if((!((m__4551__auto__ == null)))){
return (m__4551__auto__.cljs$core$IFn$_invoke$arity$4 ? m__4551__auto__.cljs$core$IFn$_invoke$arity$4(p,v,ch,close_QMARK_) : m__4551__auto__.call(null,p,v,ch,close_QMARK_));
} else {
var m__4549__auto__ = (cljs.core.async.sub_STAR_["_"]);
if((!((m__4549__auto__ == null)))){
return (m__4549__auto__.cljs$core$IFn$_invoke$arity$4 ? m__4549__auto__.cljs$core$IFn$_invoke$arity$4(p,v,ch,close_QMARK_) : m__4549__auto__.call(null,p,v,ch,close_QMARK_));
} else {
throw cljs.core.missing_protocol("Pub.sub*",p);
}
}
});
cljs.core.async.sub_STAR_ = (function cljs$core$async$sub_STAR_(p,v,ch,close_QMARK_){
if((((!((p == null)))) && ((!((p.cljs$core$async$Pub$sub_STAR_$arity$4 == null)))))){
return p.cljs$core$async$Pub$sub_STAR_$arity$4(p,v,ch,close_QMARK_);
} else {
return cljs$core$async$Pub$sub_STAR_$dyn_49480(p,v,ch,close_QMARK_);
}
});

var cljs$core$async$Pub$unsub_STAR_$dyn_49482 = (function (p,v,ch){
var x__4550__auto__ = (((p == null))?null:p);
var m__4551__auto__ = (cljs.core.async.unsub_STAR_[goog.typeOf(x__4550__auto__)]);
if((!((m__4551__auto__ == null)))){
return (m__4551__auto__.cljs$core$IFn$_invoke$arity$3 ? m__4551__auto__.cljs$core$IFn$_invoke$arity$3(p,v,ch) : m__4551__auto__.call(null,p,v,ch));
} else {
var m__4549__auto__ = (cljs.core.async.unsub_STAR_["_"]);
if((!((m__4549__auto__ == null)))){
return (m__4549__auto__.cljs$core$IFn$_invoke$arity$3 ? m__4549__auto__.cljs$core$IFn$_invoke$arity$3(p,v,ch) : m__4549__auto__.call(null,p,v,ch));
} else {
throw cljs.core.missing_protocol("Pub.unsub*",p);
}
}
});
cljs.core.async.unsub_STAR_ = (function cljs$core$async$unsub_STAR_(p,v,ch){
if((((!((p == null)))) && ((!((p.cljs$core$async$Pub$unsub_STAR_$arity$3 == null)))))){
return p.cljs$core$async$Pub$unsub_STAR_$arity$3(p,v,ch);
} else {
return cljs$core$async$Pub$unsub_STAR_$dyn_49482(p,v,ch);
}
});

var cljs$core$async$Pub$unsub_all_STAR_$dyn_49483 = (function() {
var G__49484 = null;
var G__49484__1 = (function (p){
var x__4550__auto__ = (((p == null))?null:p);
var m__4551__auto__ = (cljs.core.async.unsub_all_STAR_[goog.typeOf(x__4550__auto__)]);
if((!((m__4551__auto__ == null)))){
return (m__4551__auto__.cljs$core$IFn$_invoke$arity$1 ? m__4551__auto__.cljs$core$IFn$_invoke$arity$1(p) : m__4551__auto__.call(null,p));
} else {
var m__4549__auto__ = (cljs.core.async.unsub_all_STAR_["_"]);
if((!((m__4549__auto__ == null)))){
return (m__4549__auto__.cljs$core$IFn$_invoke$arity$1 ? m__4549__auto__.cljs$core$IFn$_invoke$arity$1(p) : m__4549__auto__.call(null,p));
} else {
throw cljs.core.missing_protocol("Pub.unsub-all*",p);
}
}
});
var G__49484__2 = (function (p,v){
var x__4550__auto__ = (((p == null))?null:p);
var m__4551__auto__ = (cljs.core.async.unsub_all_STAR_[goog.typeOf(x__4550__auto__)]);
if((!((m__4551__auto__ == null)))){
return (m__4551__auto__.cljs$core$IFn$_invoke$arity$2 ? m__4551__auto__.cljs$core$IFn$_invoke$arity$2(p,v) : m__4551__auto__.call(null,p,v));
} else {
var m__4549__auto__ = (cljs.core.async.unsub_all_STAR_["_"]);
if((!((m__4549__auto__ == null)))){
return (m__4549__auto__.cljs$core$IFn$_invoke$arity$2 ? m__4549__auto__.cljs$core$IFn$_invoke$arity$2(p,v) : m__4549__auto__.call(null,p,v));
} else {
throw cljs.core.missing_protocol("Pub.unsub-all*",p);
}
}
});
G__49484 = function(p,v){
switch(arguments.length){
case 1:
return G__49484__1.call(this,p);
case 2:
return G__49484__2.call(this,p,v);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
G__49484.cljs$core$IFn$_invoke$arity$1 = G__49484__1;
G__49484.cljs$core$IFn$_invoke$arity$2 = G__49484__2;
return G__49484;
})()
;
cljs.core.async.unsub_all_STAR_ = (function cljs$core$async$unsub_all_STAR_(var_args){
var G__47722 = arguments.length;
switch (G__47722) {
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
return cljs$core$async$Pub$unsub_all_STAR_$dyn_49483(p);
}
}));

(cljs.core.async.unsub_all_STAR_.cljs$core$IFn$_invoke$arity$2 = (function (p,v){
if((((!((p == null)))) && ((!((p.cljs$core$async$Pub$unsub_all_STAR_$arity$2 == null)))))){
return p.cljs$core$async$Pub$unsub_all_STAR_$arity$2(p,v);
} else {
return cljs$core$async$Pub$unsub_all_STAR_$dyn_49483(p,v);
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
var G__47734 = arguments.length;
switch (G__47734) {
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
var or__4253__auto__ = cljs.core.get.cljs$core$IFn$_invoke$arity$2(cljs.core.deref(mults),topic);
if(cljs.core.truth_(or__4253__auto__)){
return or__4253__auto__;
} else {
return cljs.core.get.cljs$core$IFn$_invoke$arity$2(cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$2(mults,(function (p1__47726_SHARP_){
if(cljs.core.truth_((p1__47726_SHARP_.cljs$core$IFn$_invoke$arity$1 ? p1__47726_SHARP_.cljs$core$IFn$_invoke$arity$1(topic) : p1__47726_SHARP_.call(null,topic)))){
return p1__47726_SHARP_;
} else {
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(p1__47726_SHARP_,topic,cljs.core.async.mult(cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((buf_fn.cljs$core$IFn$_invoke$arity$1 ? buf_fn.cljs$core$IFn$_invoke$arity$1(topic) : buf_fn.call(null,topic)))));
}
})),topic);
}
});
var p = (function (){
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async47736 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.Pub}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.async.Mux}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async47736 = (function (ch,topic_fn,buf_fn,mults,ensure_mult,meta47737){
this.ch = ch;
this.topic_fn = topic_fn;
this.buf_fn = buf_fn;
this.mults = mults;
this.ensure_mult = ensure_mult;
this.meta47737 = meta47737;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async47736.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_47738,meta47737__$1){
var self__ = this;
var _47738__$1 = this;
return (new cljs.core.async.t_cljs$core$async47736(self__.ch,self__.topic_fn,self__.buf_fn,self__.mults,self__.ensure_mult,meta47737__$1));
}));

(cljs.core.async.t_cljs$core$async47736.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_47738){
var self__ = this;
var _47738__$1 = this;
return self__.meta47737;
}));

(cljs.core.async.t_cljs$core$async47736.prototype.cljs$core$async$Mux$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async47736.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return self__.ch;
}));

(cljs.core.async.t_cljs$core$async47736.prototype.cljs$core$async$Pub$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async47736.prototype.cljs$core$async$Pub$sub_STAR_$arity$4 = (function (p,topic,ch__$1,close_QMARK_){
var self__ = this;
var p__$1 = this;
var m = (self__.ensure_mult.cljs$core$IFn$_invoke$arity$1 ? self__.ensure_mult.cljs$core$IFn$_invoke$arity$1(topic) : self__.ensure_mult.call(null,topic));
return cljs.core.async.tap.cljs$core$IFn$_invoke$arity$3(m,ch__$1,close_QMARK_);
}));

(cljs.core.async.t_cljs$core$async47736.prototype.cljs$core$async$Pub$unsub_STAR_$arity$3 = (function (p,topic,ch__$1){
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

(cljs.core.async.t_cljs$core$async47736.prototype.cljs$core$async$Pub$unsub_all_STAR_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.reset_BANG_(self__.mults,cljs.core.PersistentArrayMap.EMPTY);
}));

(cljs.core.async.t_cljs$core$async47736.prototype.cljs$core$async$Pub$unsub_all_STAR_$arity$2 = (function (_,topic){
var self__ = this;
var ___$1 = this;
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(self__.mults,cljs.core.dissoc,topic);
}));

(cljs.core.async.t_cljs$core$async47736.getBasis = (function (){
return new cljs.core.PersistentVector(null, 6, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"ch","ch",1085813622,null),new cljs.core.Symbol(null,"topic-fn","topic-fn",-862449736,null),new cljs.core.Symbol(null,"buf-fn","buf-fn",-1200281591,null),new cljs.core.Symbol(null,"mults","mults",-461114485,null),new cljs.core.Symbol(null,"ensure-mult","ensure-mult",1796584816,null),new cljs.core.Symbol(null,"meta47737","meta47737",-94613037,null)], null);
}));

(cljs.core.async.t_cljs$core$async47736.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async47736.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async47736");

(cljs.core.async.t_cljs$core$async47736.cljs$lang$ctorPrWriter = (function (this__4491__auto__,writer__4492__auto__,opt__4493__auto__){
return cljs.core._write(writer__4492__auto__,"cljs.core.async/t_cljs$core$async47736");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async47736.
 */
cljs.core.async.__GT_t_cljs$core$async47736 = (function cljs$core$async$__GT_t_cljs$core$async47736(ch__$1,topic_fn__$1,buf_fn__$1,mults__$1,ensure_mult__$1,meta47737){
return (new cljs.core.async.t_cljs$core$async47736(ch__$1,topic_fn__$1,buf_fn__$1,mults__$1,ensure_mult__$1,meta47737));
});

}

return (new cljs.core.async.t_cljs$core$async47736(ch,topic_fn,buf_fn,mults,ensure_mult,cljs.core.PersistentArrayMap.EMPTY));
})()
;
var c__46528__auto___49503 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_47834){
var state_val_47835 = (state_47834[(1)]);
if((state_val_47835 === (7))){
var inst_47830 = (state_47834[(2)]);
var state_47834__$1 = state_47834;
var statearr_47840_49504 = state_47834__$1;
(statearr_47840_49504[(2)] = inst_47830);

(statearr_47840_49504[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (20))){
var state_47834__$1 = state_47834;
var statearr_47841_49505 = state_47834__$1;
(statearr_47841_49505[(2)] = null);

(statearr_47841_49505[(1)] = (21));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (1))){
var state_47834__$1 = state_47834;
var statearr_47843_49506 = state_47834__$1;
(statearr_47843_49506[(2)] = null);

(statearr_47843_49506[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (24))){
var inst_47812 = (state_47834[(7)]);
var inst_47822 = cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(mults,cljs.core.dissoc,inst_47812);
var state_47834__$1 = state_47834;
var statearr_47844_49507 = state_47834__$1;
(statearr_47844_49507[(2)] = inst_47822);

(statearr_47844_49507[(1)] = (25));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (4))){
var inst_47760 = (state_47834[(8)]);
var inst_47760__$1 = (state_47834[(2)]);
var inst_47761 = (inst_47760__$1 == null);
var state_47834__$1 = (function (){var statearr_47848 = state_47834;
(statearr_47848[(8)] = inst_47760__$1);

return statearr_47848;
})();
if(cljs.core.truth_(inst_47761)){
var statearr_47849_49508 = state_47834__$1;
(statearr_47849_49508[(1)] = (5));

} else {
var statearr_47850_49509 = state_47834__$1;
(statearr_47850_49509[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (15))){
var inst_47806 = (state_47834[(2)]);
var state_47834__$1 = state_47834;
var statearr_47852_49511 = state_47834__$1;
(statearr_47852_49511[(2)] = inst_47806);

(statearr_47852_49511[(1)] = (12));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (21))){
var inst_47827 = (state_47834[(2)]);
var state_47834__$1 = (function (){var statearr_47854 = state_47834;
(statearr_47854[(9)] = inst_47827);

return statearr_47854;
})();
var statearr_47855_49512 = state_47834__$1;
(statearr_47855_49512[(2)] = null);

(statearr_47855_49512[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (13))){
var inst_47785 = (state_47834[(10)]);
var inst_47789 = cljs.core.chunked_seq_QMARK_(inst_47785);
var state_47834__$1 = state_47834;
if(inst_47789){
var statearr_47859_49513 = state_47834__$1;
(statearr_47859_49513[(1)] = (16));

} else {
var statearr_47860_49514 = state_47834__$1;
(statearr_47860_49514[(1)] = (17));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (22))){
var inst_47819 = (state_47834[(2)]);
var state_47834__$1 = state_47834;
if(cljs.core.truth_(inst_47819)){
var statearr_47862_49515 = state_47834__$1;
(statearr_47862_49515[(1)] = (23));

} else {
var statearr_47864_49516 = state_47834__$1;
(statearr_47864_49516[(1)] = (24));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (6))){
var inst_47760 = (state_47834[(8)]);
var inst_47814 = (state_47834[(11)]);
var inst_47812 = (state_47834[(7)]);
var inst_47812__$1 = (topic_fn.cljs$core$IFn$_invoke$arity$1 ? topic_fn.cljs$core$IFn$_invoke$arity$1(inst_47760) : topic_fn.call(null,inst_47760));
var inst_47813 = cljs.core.deref(mults);
var inst_47814__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_47813,inst_47812__$1);
var state_47834__$1 = (function (){var statearr_47868 = state_47834;
(statearr_47868[(11)] = inst_47814__$1);

(statearr_47868[(7)] = inst_47812__$1);

return statearr_47868;
})();
if(cljs.core.truth_(inst_47814__$1)){
var statearr_47869_49517 = state_47834__$1;
(statearr_47869_49517[(1)] = (19));

} else {
var statearr_47870_49518 = state_47834__$1;
(statearr_47870_49518[(1)] = (20));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (25))){
var inst_47824 = (state_47834[(2)]);
var state_47834__$1 = state_47834;
var statearr_47872_49519 = state_47834__$1;
(statearr_47872_49519[(2)] = inst_47824);

(statearr_47872_49519[(1)] = (21));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (17))){
var inst_47785 = (state_47834[(10)]);
var inst_47797 = cljs.core.first(inst_47785);
var inst_47798 = cljs.core.async.muxch_STAR_(inst_47797);
var inst_47799 = cljs.core.async.close_BANG_(inst_47798);
var inst_47800 = cljs.core.next(inst_47785);
var inst_47770 = inst_47800;
var inst_47771 = null;
var inst_47772 = (0);
var inst_47773 = (0);
var state_47834__$1 = (function (){var statearr_47873 = state_47834;
(statearr_47873[(12)] = inst_47799);

(statearr_47873[(13)] = inst_47773);

(statearr_47873[(14)] = inst_47772);

(statearr_47873[(15)] = inst_47771);

(statearr_47873[(16)] = inst_47770);

return statearr_47873;
})();
var statearr_47876_49524 = state_47834__$1;
(statearr_47876_49524[(2)] = null);

(statearr_47876_49524[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (3))){
var inst_47832 = (state_47834[(2)]);
var state_47834__$1 = state_47834;
return cljs.core.async.impl.ioc_helpers.return_chan(state_47834__$1,inst_47832);
} else {
if((state_val_47835 === (12))){
var inst_47808 = (state_47834[(2)]);
var state_47834__$1 = state_47834;
var statearr_47878_49529 = state_47834__$1;
(statearr_47878_49529[(2)] = inst_47808);

(statearr_47878_49529[(1)] = (9));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (2))){
var state_47834__$1 = state_47834;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_47834__$1,(4),ch);
} else {
if((state_val_47835 === (23))){
var state_47834__$1 = state_47834;
var statearr_47884_49530 = state_47834__$1;
(statearr_47884_49530[(2)] = null);

(statearr_47884_49530[(1)] = (25));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (19))){
var inst_47760 = (state_47834[(8)]);
var inst_47814 = (state_47834[(11)]);
var inst_47817 = cljs.core.async.muxch_STAR_(inst_47814);
var state_47834__$1 = state_47834;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_47834__$1,(22),inst_47817,inst_47760);
} else {
if((state_val_47835 === (11))){
var inst_47785 = (state_47834[(10)]);
var inst_47770 = (state_47834[(16)]);
var inst_47785__$1 = cljs.core.seq(inst_47770);
var state_47834__$1 = (function (){var statearr_47888 = state_47834;
(statearr_47888[(10)] = inst_47785__$1);

return statearr_47888;
})();
if(inst_47785__$1){
var statearr_47890_49532 = state_47834__$1;
(statearr_47890_49532[(1)] = (13));

} else {
var statearr_47894_49533 = state_47834__$1;
(statearr_47894_49533[(1)] = (14));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (9))){
var inst_47810 = (state_47834[(2)]);
var state_47834__$1 = state_47834;
var statearr_47896_49534 = state_47834__$1;
(statearr_47896_49534[(2)] = inst_47810);

(statearr_47896_49534[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (5))){
var inst_47767 = cljs.core.deref(mults);
var inst_47768 = cljs.core.vals(inst_47767);
var inst_47769 = cljs.core.seq(inst_47768);
var inst_47770 = inst_47769;
var inst_47771 = null;
var inst_47772 = (0);
var inst_47773 = (0);
var state_47834__$1 = (function (){var statearr_47900 = state_47834;
(statearr_47900[(13)] = inst_47773);

(statearr_47900[(14)] = inst_47772);

(statearr_47900[(15)] = inst_47771);

(statearr_47900[(16)] = inst_47770);

return statearr_47900;
})();
var statearr_47901_49538 = state_47834__$1;
(statearr_47901_49538[(2)] = null);

(statearr_47901_49538[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (14))){
var state_47834__$1 = state_47834;
var statearr_47906_49539 = state_47834__$1;
(statearr_47906_49539[(2)] = null);

(statearr_47906_49539[(1)] = (15));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (16))){
var inst_47785 = (state_47834[(10)]);
var inst_47792 = cljs.core.chunk_first(inst_47785);
var inst_47793 = cljs.core.chunk_rest(inst_47785);
var inst_47794 = cljs.core.count(inst_47792);
var inst_47770 = inst_47793;
var inst_47771 = inst_47792;
var inst_47772 = inst_47794;
var inst_47773 = (0);
var state_47834__$1 = (function (){var statearr_47910 = state_47834;
(statearr_47910[(13)] = inst_47773);

(statearr_47910[(14)] = inst_47772);

(statearr_47910[(15)] = inst_47771);

(statearr_47910[(16)] = inst_47770);

return statearr_47910;
})();
var statearr_47912_49540 = state_47834__$1;
(statearr_47912_49540[(2)] = null);

(statearr_47912_49540[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (10))){
var inst_47773 = (state_47834[(13)]);
var inst_47772 = (state_47834[(14)]);
var inst_47771 = (state_47834[(15)]);
var inst_47770 = (state_47834[(16)]);
var inst_47779 = cljs.core._nth(inst_47771,inst_47773);
var inst_47780 = cljs.core.async.muxch_STAR_(inst_47779);
var inst_47781 = cljs.core.async.close_BANG_(inst_47780);
var inst_47782 = (inst_47773 + (1));
var tmp47903 = inst_47772;
var tmp47904 = inst_47771;
var tmp47905 = inst_47770;
var inst_47770__$1 = tmp47905;
var inst_47771__$1 = tmp47904;
var inst_47772__$1 = tmp47903;
var inst_47773__$1 = inst_47782;
var state_47834__$1 = (function (){var statearr_47916 = state_47834;
(statearr_47916[(13)] = inst_47773__$1);

(statearr_47916[(14)] = inst_47772__$1);

(statearr_47916[(15)] = inst_47771__$1);

(statearr_47916[(16)] = inst_47770__$1);

(statearr_47916[(17)] = inst_47781);

return statearr_47916;
})();
var statearr_47918_49543 = state_47834__$1;
(statearr_47918_49543[(2)] = null);

(statearr_47918_49543[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (18))){
var inst_47803 = (state_47834[(2)]);
var state_47834__$1 = state_47834;
var statearr_47921_49544 = state_47834__$1;
(statearr_47921_49544[(2)] = inst_47803);

(statearr_47921_49544[(1)] = (15));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_47835 === (8))){
var inst_47773 = (state_47834[(13)]);
var inst_47772 = (state_47834[(14)]);
var inst_47775 = (inst_47773 < inst_47772);
var inst_47776 = inst_47775;
var state_47834__$1 = state_47834;
if(cljs.core.truth_(inst_47776)){
var statearr_47923_49545 = state_47834__$1;
(statearr_47923_49545[(1)] = (10));

} else {
var statearr_47925_49546 = state_47834__$1;
(statearr_47925_49546[(1)] = (11));

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
var cljs$core$async$state_machine__46373__auto__ = null;
var cljs$core$async$state_machine__46373__auto____0 = (function (){
var statearr_47928 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_47928[(0)] = cljs$core$async$state_machine__46373__auto__);

(statearr_47928[(1)] = (1));

return statearr_47928;
});
var cljs$core$async$state_machine__46373__auto____1 = (function (state_47834){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_47834);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e47929){var ex__46376__auto__ = e47929;
var statearr_47932_49547 = state_47834;
(statearr_47932_49547[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_47834[(4)]))){
var statearr_47934_49549 = state_47834;
(statearr_47934_49549[(1)] = cljs.core.first((state_47834[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__49550 = state_47834;
state_47834 = G__49550;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$state_machine__46373__auto__ = function(state_47834){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__46373__auto____1.call(this,state_47834);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__46373__auto____0;
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__46373__auto____1;
return cljs$core$async$state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_47936 = f__46529__auto__();
(statearr_47936[(6)] = c__46528__auto___49503);

return statearr_47936;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
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
var G__47941 = arguments.length;
switch (G__47941) {
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
var G__47947 = arguments.length;
switch (G__47947) {
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
var G__47953 = arguments.length;
switch (G__47953) {
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
var c__46528__auto___49554 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_48011){
var state_val_48012 = (state_48011[(1)]);
if((state_val_48012 === (7))){
var state_48011__$1 = state_48011;
var statearr_48016_49555 = state_48011__$1;
(statearr_48016_49555[(2)] = null);

(statearr_48016_49555[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48012 === (1))){
var state_48011__$1 = state_48011;
var statearr_48018_49556 = state_48011__$1;
(statearr_48018_49556[(2)] = null);

(statearr_48018_49556[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48012 === (4))){
var inst_47967 = (state_48011[(7)]);
var inst_47966 = (state_48011[(8)]);
var inst_47969 = (inst_47967 < inst_47966);
var state_48011__$1 = state_48011;
if(cljs.core.truth_(inst_47969)){
var statearr_48022_49557 = state_48011__$1;
(statearr_48022_49557[(1)] = (6));

} else {
var statearr_48023_49558 = state_48011__$1;
(statearr_48023_49558[(1)] = (7));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48012 === (15))){
var inst_47996 = (state_48011[(9)]);
var inst_48001 = cljs.core.apply.cljs$core$IFn$_invoke$arity$2(f,inst_47996);
var state_48011__$1 = state_48011;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_48011__$1,(17),out,inst_48001);
} else {
if((state_val_48012 === (13))){
var inst_47996 = (state_48011[(9)]);
var inst_47996__$1 = (state_48011[(2)]);
var inst_47997 = cljs.core.some(cljs.core.nil_QMARK_,inst_47996__$1);
var state_48011__$1 = (function (){var statearr_48028 = state_48011;
(statearr_48028[(9)] = inst_47996__$1);

return statearr_48028;
})();
if(cljs.core.truth_(inst_47997)){
var statearr_48030_49563 = state_48011__$1;
(statearr_48030_49563[(1)] = (14));

} else {
var statearr_48031_49564 = state_48011__$1;
(statearr_48031_49564[(1)] = (15));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48012 === (6))){
var state_48011__$1 = state_48011;
var statearr_48034_49565 = state_48011__$1;
(statearr_48034_49565[(2)] = null);

(statearr_48034_49565[(1)] = (9));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48012 === (17))){
var inst_48003 = (state_48011[(2)]);
var state_48011__$1 = (function (){var statearr_48042 = state_48011;
(statearr_48042[(10)] = inst_48003);

return statearr_48042;
})();
var statearr_48043_49567 = state_48011__$1;
(statearr_48043_49567[(2)] = null);

(statearr_48043_49567[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48012 === (3))){
var inst_48009 = (state_48011[(2)]);
var state_48011__$1 = state_48011;
return cljs.core.async.impl.ioc_helpers.return_chan(state_48011__$1,inst_48009);
} else {
if((state_val_48012 === (12))){
var _ = (function (){var statearr_48049 = state_48011;
(statearr_48049[(4)] = cljs.core.rest((state_48011[(4)])));

return statearr_48049;
})();
var state_48011__$1 = state_48011;
var ex48040 = (state_48011__$1[(2)]);
var statearr_48051_49574 = state_48011__$1;
(statearr_48051_49574[(5)] = ex48040);


if((ex48040 instanceof Object)){
var statearr_48059_49575 = state_48011__$1;
(statearr_48059_49575[(1)] = (11));

(statearr_48059_49575[(5)] = null);

} else {
throw ex48040;

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48012 === (2))){
var inst_47965 = cljs.core.reset_BANG_(dctr,cnt);
var inst_47966 = cnt;
var inst_47967 = (0);
var state_48011__$1 = (function (){var statearr_48069 = state_48011;
(statearr_48069[(11)] = inst_47965);

(statearr_48069[(7)] = inst_47967);

(statearr_48069[(8)] = inst_47966);

return statearr_48069;
})();
var statearr_48070_49580 = state_48011__$1;
(statearr_48070_49580[(2)] = null);

(statearr_48070_49580[(1)] = (4));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48012 === (11))){
var inst_47975 = (state_48011[(2)]);
var inst_47976 = cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$2(dctr,cljs.core.dec);
var state_48011__$1 = (function (){var statearr_48072 = state_48011;
(statearr_48072[(12)] = inst_47975);

return statearr_48072;
})();
var statearr_48073_49581 = state_48011__$1;
(statearr_48073_49581[(2)] = inst_47976);

(statearr_48073_49581[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48012 === (9))){
var inst_47967 = (state_48011[(7)]);
var _ = (function (){var statearr_48083 = state_48011;
(statearr_48083[(4)] = cljs.core.cons((12),(state_48011[(4)])));

return statearr_48083;
})();
var inst_47982 = (chs__$1.cljs$core$IFn$_invoke$arity$1 ? chs__$1.cljs$core$IFn$_invoke$arity$1(inst_47967) : chs__$1.call(null,inst_47967));
var inst_47983 = (done.cljs$core$IFn$_invoke$arity$1 ? done.cljs$core$IFn$_invoke$arity$1(inst_47967) : done.call(null,inst_47967));
var inst_47984 = cljs.core.async.take_BANG_.cljs$core$IFn$_invoke$arity$2(inst_47982,inst_47983);
var ___$1 = (function (){var statearr_48086 = state_48011;
(statearr_48086[(4)] = cljs.core.rest((state_48011[(4)])));

return statearr_48086;
})();
var state_48011__$1 = state_48011;
var statearr_48087_49584 = state_48011__$1;
(statearr_48087_49584[(2)] = inst_47984);

(statearr_48087_49584[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48012 === (5))){
var inst_47994 = (state_48011[(2)]);
var state_48011__$1 = (function (){var statearr_48092 = state_48011;
(statearr_48092[(13)] = inst_47994);

return statearr_48092;
})();
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_48011__$1,(13),dchan);
} else {
if((state_val_48012 === (14))){
var inst_47999 = cljs.core.async.close_BANG_(out);
var state_48011__$1 = state_48011;
var statearr_48096_49585 = state_48011__$1;
(statearr_48096_49585[(2)] = inst_47999);

(statearr_48096_49585[(1)] = (16));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48012 === (16))){
var inst_48007 = (state_48011[(2)]);
var state_48011__$1 = state_48011;
var statearr_48101_49586 = state_48011__$1;
(statearr_48101_49586[(2)] = inst_48007);

(statearr_48101_49586[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48012 === (10))){
var inst_47967 = (state_48011[(7)]);
var inst_47987 = (state_48011[(2)]);
var inst_47988 = (inst_47967 + (1));
var inst_47967__$1 = inst_47988;
var state_48011__$1 = (function (){var statearr_48104 = state_48011;
(statearr_48104[(14)] = inst_47987);

(statearr_48104[(7)] = inst_47967__$1);

return statearr_48104;
})();
var statearr_48105_49587 = state_48011__$1;
(statearr_48105_49587[(2)] = null);

(statearr_48105_49587[(1)] = (4));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48012 === (8))){
var inst_47992 = (state_48011[(2)]);
var state_48011__$1 = state_48011;
var statearr_48109_49589 = state_48011__$1;
(statearr_48109_49589[(2)] = inst_47992);

(statearr_48109_49589[(1)] = (5));


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
var cljs$core$async$state_machine__46373__auto__ = null;
var cljs$core$async$state_machine__46373__auto____0 = (function (){
var statearr_48115 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_48115[(0)] = cljs$core$async$state_machine__46373__auto__);

(statearr_48115[(1)] = (1));

return statearr_48115;
});
var cljs$core$async$state_machine__46373__auto____1 = (function (state_48011){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_48011);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e48116){var ex__46376__auto__ = e48116;
var statearr_48117_49590 = state_48011;
(statearr_48117_49590[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_48011[(4)]))){
var statearr_48122_49591 = state_48011;
(statearr_48122_49591[(1)] = cljs.core.first((state_48011[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__49592 = state_48011;
state_48011 = G__49592;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$state_machine__46373__auto__ = function(state_48011){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__46373__auto____1.call(this,state_48011);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__46373__auto____0;
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__46373__auto____1;
return cljs$core$async$state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_48124 = f__46529__auto__();
(statearr_48124[(6)] = c__46528__auto___49554);

return statearr_48124;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
}));


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
var G__48135 = arguments.length;
switch (G__48135) {
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
var c__46528__auto___49594 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_48190){
var state_val_48192 = (state_48190[(1)]);
if((state_val_48192 === (7))){
var inst_48161 = (state_48190[(7)]);
var inst_48162 = (state_48190[(8)]);
var inst_48161__$1 = (state_48190[(2)]);
var inst_48162__$1 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_48161__$1,(0),null);
var inst_48163 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_48161__$1,(1),null);
var inst_48165 = (inst_48162__$1 == null);
var state_48190__$1 = (function (){var statearr_48203 = state_48190;
(statearr_48203[(9)] = inst_48163);

(statearr_48203[(7)] = inst_48161__$1);

(statearr_48203[(8)] = inst_48162__$1);

return statearr_48203;
})();
if(cljs.core.truth_(inst_48165)){
var statearr_48204_49595 = state_48190__$1;
(statearr_48204_49595[(1)] = (8));

} else {
var statearr_48205_49599 = state_48190__$1;
(statearr_48205_49599[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48192 === (1))){
var inst_48149 = cljs.core.vec(chs);
var inst_48150 = inst_48149;
var state_48190__$1 = (function (){var statearr_48207 = state_48190;
(statearr_48207[(10)] = inst_48150);

return statearr_48207;
})();
var statearr_48208_49600 = state_48190__$1;
(statearr_48208_49600[(2)] = null);

(statearr_48208_49600[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48192 === (4))){
var inst_48150 = (state_48190[(10)]);
var state_48190__$1 = state_48190;
return cljs.core.async.ioc_alts_BANG_(state_48190__$1,(7),inst_48150);
} else {
if((state_val_48192 === (6))){
var inst_48185 = (state_48190[(2)]);
var state_48190__$1 = state_48190;
var statearr_48209_49601 = state_48190__$1;
(statearr_48209_49601[(2)] = inst_48185);

(statearr_48209_49601[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48192 === (3))){
var inst_48187 = (state_48190[(2)]);
var state_48190__$1 = state_48190;
return cljs.core.async.impl.ioc_helpers.return_chan(state_48190__$1,inst_48187);
} else {
if((state_val_48192 === (2))){
var inst_48150 = (state_48190[(10)]);
var inst_48154 = cljs.core.count(inst_48150);
var inst_48155 = (inst_48154 > (0));
var state_48190__$1 = state_48190;
if(cljs.core.truth_(inst_48155)){
var statearr_48215_49602 = state_48190__$1;
(statearr_48215_49602[(1)] = (4));

} else {
var statearr_48219_49603 = state_48190__$1;
(statearr_48219_49603[(1)] = (5));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48192 === (11))){
var inst_48150 = (state_48190[(10)]);
var inst_48178 = (state_48190[(2)]);
var tmp48210 = inst_48150;
var inst_48150__$1 = tmp48210;
var state_48190__$1 = (function (){var statearr_48221 = state_48190;
(statearr_48221[(10)] = inst_48150__$1);

(statearr_48221[(11)] = inst_48178);

return statearr_48221;
})();
var statearr_48222_49604 = state_48190__$1;
(statearr_48222_49604[(2)] = null);

(statearr_48222_49604[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48192 === (9))){
var inst_48162 = (state_48190[(8)]);
var state_48190__$1 = state_48190;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_48190__$1,(11),out,inst_48162);
} else {
if((state_val_48192 === (5))){
var inst_48183 = cljs.core.async.close_BANG_(out);
var state_48190__$1 = state_48190;
var statearr_48231_49605 = state_48190__$1;
(statearr_48231_49605[(2)] = inst_48183);

(statearr_48231_49605[(1)] = (6));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48192 === (10))){
var inst_48181 = (state_48190[(2)]);
var state_48190__$1 = state_48190;
var statearr_48232_49606 = state_48190__$1;
(statearr_48232_49606[(2)] = inst_48181);

(statearr_48232_49606[(1)] = (6));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48192 === (8))){
var inst_48150 = (state_48190[(10)]);
var inst_48163 = (state_48190[(9)]);
var inst_48161 = (state_48190[(7)]);
var inst_48162 = (state_48190[(8)]);
var inst_48167 = (function (){var cs = inst_48150;
var vec__48157 = inst_48161;
var v = inst_48162;
var c = inst_48163;
return (function (p1__48128_SHARP_){
return cljs.core.not_EQ_.cljs$core$IFn$_invoke$arity$2(c,p1__48128_SHARP_);
});
})();
var inst_48173 = cljs.core.filterv(inst_48167,inst_48150);
var inst_48150__$1 = inst_48173;
var state_48190__$1 = (function (){var statearr_48237 = state_48190;
(statearr_48237[(10)] = inst_48150__$1);

return statearr_48237;
})();
var statearr_48238_49607 = state_48190__$1;
(statearr_48238_49607[(2)] = null);

(statearr_48238_49607[(1)] = (2));


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
var cljs$core$async$state_machine__46373__auto__ = null;
var cljs$core$async$state_machine__46373__auto____0 = (function (){
var statearr_48239 = [null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_48239[(0)] = cljs$core$async$state_machine__46373__auto__);

(statearr_48239[(1)] = (1));

return statearr_48239;
});
var cljs$core$async$state_machine__46373__auto____1 = (function (state_48190){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_48190);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e48241){var ex__46376__auto__ = e48241;
var statearr_48242_49608 = state_48190;
(statearr_48242_49608[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_48190[(4)]))){
var statearr_48243_49612 = state_48190;
(statearr_48243_49612[(1)] = cljs.core.first((state_48190[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__49613 = state_48190;
state_48190 = G__49613;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$state_machine__46373__auto__ = function(state_48190){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__46373__auto____1.call(this,state_48190);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__46373__auto____0;
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__46373__auto____1;
return cljs$core$async$state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_48248 = f__46529__auto__();
(statearr_48248[(6)] = c__46528__auto___49594);

return statearr_48248;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
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
var G__48258 = arguments.length;
switch (G__48258) {
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
var c__46528__auto___49622 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_48290){
var state_val_48291 = (state_48290[(1)]);
if((state_val_48291 === (7))){
var inst_48272 = (state_48290[(7)]);
var inst_48272__$1 = (state_48290[(2)]);
var inst_48273 = (inst_48272__$1 == null);
var inst_48274 = cljs.core.not(inst_48273);
var state_48290__$1 = (function (){var statearr_48292 = state_48290;
(statearr_48292[(7)] = inst_48272__$1);

return statearr_48292;
})();
if(inst_48274){
var statearr_48293_49626 = state_48290__$1;
(statearr_48293_49626[(1)] = (8));

} else {
var statearr_48294_49627 = state_48290__$1;
(statearr_48294_49627[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48291 === (1))){
var inst_48267 = (0);
var state_48290__$1 = (function (){var statearr_48295 = state_48290;
(statearr_48295[(8)] = inst_48267);

return statearr_48295;
})();
var statearr_48296_49628 = state_48290__$1;
(statearr_48296_49628[(2)] = null);

(statearr_48296_49628[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48291 === (4))){
var state_48290__$1 = state_48290;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_48290__$1,(7),ch);
} else {
if((state_val_48291 === (6))){
var inst_48285 = (state_48290[(2)]);
var state_48290__$1 = state_48290;
var statearr_48297_49629 = state_48290__$1;
(statearr_48297_49629[(2)] = inst_48285);

(statearr_48297_49629[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48291 === (3))){
var inst_48287 = (state_48290[(2)]);
var inst_48288 = cljs.core.async.close_BANG_(out);
var state_48290__$1 = (function (){var statearr_48302 = state_48290;
(statearr_48302[(9)] = inst_48287);

return statearr_48302;
})();
return cljs.core.async.impl.ioc_helpers.return_chan(state_48290__$1,inst_48288);
} else {
if((state_val_48291 === (2))){
var inst_48267 = (state_48290[(8)]);
var inst_48269 = (inst_48267 < n);
var state_48290__$1 = state_48290;
if(cljs.core.truth_(inst_48269)){
var statearr_48303_49636 = state_48290__$1;
(statearr_48303_49636[(1)] = (4));

} else {
var statearr_48304_49637 = state_48290__$1;
(statearr_48304_49637[(1)] = (5));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48291 === (11))){
var inst_48267 = (state_48290[(8)]);
var inst_48277 = (state_48290[(2)]);
var inst_48278 = (inst_48267 + (1));
var inst_48267__$1 = inst_48278;
var state_48290__$1 = (function (){var statearr_48306 = state_48290;
(statearr_48306[(10)] = inst_48277);

(statearr_48306[(8)] = inst_48267__$1);

return statearr_48306;
})();
var statearr_48307_49638 = state_48290__$1;
(statearr_48307_49638[(2)] = null);

(statearr_48307_49638[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48291 === (9))){
var state_48290__$1 = state_48290;
var statearr_48308_49642 = state_48290__$1;
(statearr_48308_49642[(2)] = null);

(statearr_48308_49642[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48291 === (5))){
var state_48290__$1 = state_48290;
var statearr_48309_49643 = state_48290__$1;
(statearr_48309_49643[(2)] = null);

(statearr_48309_49643[(1)] = (6));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48291 === (10))){
var inst_48282 = (state_48290[(2)]);
var state_48290__$1 = state_48290;
var statearr_48310_49644 = state_48290__$1;
(statearr_48310_49644[(2)] = inst_48282);

(statearr_48310_49644[(1)] = (6));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48291 === (8))){
var inst_48272 = (state_48290[(7)]);
var state_48290__$1 = state_48290;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_48290__$1,(11),out,inst_48272);
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
var cljs$core$async$state_machine__46373__auto__ = null;
var cljs$core$async$state_machine__46373__auto____0 = (function (){
var statearr_48312 = [null,null,null,null,null,null,null,null,null,null,null];
(statearr_48312[(0)] = cljs$core$async$state_machine__46373__auto__);

(statearr_48312[(1)] = (1));

return statearr_48312;
});
var cljs$core$async$state_machine__46373__auto____1 = (function (state_48290){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_48290);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e48314){var ex__46376__auto__ = e48314;
var statearr_48315_49646 = state_48290;
(statearr_48315_49646[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_48290[(4)]))){
var statearr_48316_49647 = state_48290;
(statearr_48316_49647[(1)] = cljs.core.first((state_48290[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__49648 = state_48290;
state_48290 = G__49648;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$state_machine__46373__auto__ = function(state_48290){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__46373__auto____1.call(this,state_48290);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__46373__auto____0;
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__46373__auto____1;
return cljs$core$async$state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_48317 = f__46529__auto__();
(statearr_48317[(6)] = c__46528__auto___49622);

return statearr_48317;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
}));


return out;
}));

(cljs.core.async.take.cljs$lang$maxFixedArity = 3);

/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.map_LT_ = (function cljs$core$async$map_LT_(f,ch){
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async48319 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.impl.protocols.Channel}
 * @implements {cljs.core.async.impl.protocols.WritePort}
 * @implements {cljs.core.async.impl.protocols.ReadPort}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async48319 = (function (f,ch,meta48320){
this.f = f;
this.ch = ch;
this.meta48320 = meta48320;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async48319.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_48321,meta48320__$1){
var self__ = this;
var _48321__$1 = this;
return (new cljs.core.async.t_cljs$core$async48319(self__.f,self__.ch,meta48320__$1));
}));

(cljs.core.async.t_cljs$core$async48319.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_48321){
var self__ = this;
var _48321__$1 = this;
return self__.meta48320;
}));

(cljs.core.async.t_cljs$core$async48319.prototype.cljs$core$async$impl$protocols$Channel$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async48319.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.close_BANG_(self__.ch);
}));

(cljs.core.async.t_cljs$core$async48319.prototype.cljs$core$async$impl$protocols$Channel$closed_QMARK_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.closed_QMARK_(self__.ch);
}));

(cljs.core.async.t_cljs$core$async48319.prototype.cljs$core$async$impl$protocols$ReadPort$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async48319.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){
var self__ = this;
var ___$1 = this;
var ret = cljs.core.async.impl.protocols.take_BANG_(self__.ch,(function (){
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async48322 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.impl.protocols.Handler}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async48322 = (function (f,ch,meta48320,_,fn1,meta48323){
this.f = f;
this.ch = ch;
this.meta48320 = meta48320;
this._ = _;
this.fn1 = fn1;
this.meta48323 = meta48323;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async48322.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_48324,meta48323__$1){
var self__ = this;
var _48324__$1 = this;
return (new cljs.core.async.t_cljs$core$async48322(self__.f,self__.ch,self__.meta48320,self__._,self__.fn1,meta48323__$1));
}));

(cljs.core.async.t_cljs$core$async48322.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_48324){
var self__ = this;
var _48324__$1 = this;
return self__.meta48323;
}));

(cljs.core.async.t_cljs$core$async48322.prototype.cljs$core$async$impl$protocols$Handler$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async48322.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (___$1){
var self__ = this;
var ___$2 = this;
return cljs.core.async.impl.protocols.active_QMARK_(self__.fn1);
}));

(cljs.core.async.t_cljs$core$async48322.prototype.cljs$core$async$impl$protocols$Handler$blockable_QMARK_$arity$1 = (function (___$1){
var self__ = this;
var ___$2 = this;
return true;
}));

(cljs.core.async.t_cljs$core$async48322.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (___$1){
var self__ = this;
var ___$2 = this;
var f1 = cljs.core.async.impl.protocols.commit(self__.fn1);
return (function (p1__48318_SHARP_){
var G__48325 = (((p1__48318_SHARP_ == null))?null:(self__.f.cljs$core$IFn$_invoke$arity$1 ? self__.f.cljs$core$IFn$_invoke$arity$1(p1__48318_SHARP_) : self__.f.call(null,p1__48318_SHARP_)));
return (f1.cljs$core$IFn$_invoke$arity$1 ? f1.cljs$core$IFn$_invoke$arity$1(G__48325) : f1.call(null,G__48325));
});
}));

(cljs.core.async.t_cljs$core$async48322.getBasis = (function (){
return new cljs.core.PersistentVector(null, 6, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"f","f",43394975,null),new cljs.core.Symbol(null,"ch","ch",1085813622,null),new cljs.core.Symbol(null,"meta48320","meta48320",1075549740,null),cljs.core.with_meta(new cljs.core.Symbol(null,"_","_",-1201019570,null),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"tag","tag",-1290361223),new cljs.core.Symbol("cljs.core.async","t_cljs$core$async48319","cljs.core.async/t_cljs$core$async48319",562788153,null)], null)),new cljs.core.Symbol(null,"fn1","fn1",895834444,null),new cljs.core.Symbol(null,"meta48323","meta48323",-1972337329,null)], null);
}));

(cljs.core.async.t_cljs$core$async48322.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async48322.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async48322");

(cljs.core.async.t_cljs$core$async48322.cljs$lang$ctorPrWriter = (function (this__4491__auto__,writer__4492__auto__,opt__4493__auto__){
return cljs.core._write(writer__4492__auto__,"cljs.core.async/t_cljs$core$async48322");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async48322.
 */
cljs.core.async.__GT_t_cljs$core$async48322 = (function cljs$core$async$map_LT__$___GT_t_cljs$core$async48322(f__$1,ch__$1,meta48320__$1,___$2,fn1__$1,meta48323){
return (new cljs.core.async.t_cljs$core$async48322(f__$1,ch__$1,meta48320__$1,___$2,fn1__$1,meta48323));
});

}

return (new cljs.core.async.t_cljs$core$async48322(self__.f,self__.ch,self__.meta48320,___$1,fn1,cljs.core.PersistentArrayMap.EMPTY));
})()
);
if(cljs.core.truth_((function (){var and__4251__auto__ = ret;
if(cljs.core.truth_(and__4251__auto__)){
return (!((cljs.core.deref(ret) == null)));
} else {
return and__4251__auto__;
}
})())){
return cljs.core.async.impl.channels.box((function (){var G__48328 = cljs.core.deref(ret);
return (self__.f.cljs$core$IFn$_invoke$arity$1 ? self__.f.cljs$core$IFn$_invoke$arity$1(G__48328) : self__.f.call(null,G__48328));
})());
} else {
return ret;
}
}));

(cljs.core.async.t_cljs$core$async48319.prototype.cljs$core$async$impl$protocols$WritePort$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async48319.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.put_BANG_(self__.ch,val,fn1);
}));

(cljs.core.async.t_cljs$core$async48319.getBasis = (function (){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"f","f",43394975,null),new cljs.core.Symbol(null,"ch","ch",1085813622,null),new cljs.core.Symbol(null,"meta48320","meta48320",1075549740,null)], null);
}));

(cljs.core.async.t_cljs$core$async48319.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async48319.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async48319");

(cljs.core.async.t_cljs$core$async48319.cljs$lang$ctorPrWriter = (function (this__4491__auto__,writer__4492__auto__,opt__4493__auto__){
return cljs.core._write(writer__4492__auto__,"cljs.core.async/t_cljs$core$async48319");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async48319.
 */
cljs.core.async.__GT_t_cljs$core$async48319 = (function cljs$core$async$map_LT__$___GT_t_cljs$core$async48319(f__$1,ch__$1,meta48320){
return (new cljs.core.async.t_cljs$core$async48319(f__$1,ch__$1,meta48320));
});

}

return (new cljs.core.async.t_cljs$core$async48319(f,ch,cljs.core.PersistentArrayMap.EMPTY));
});
/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.map_GT_ = (function cljs$core$async$map_GT_(f,ch){
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async48331 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.impl.protocols.Channel}
 * @implements {cljs.core.async.impl.protocols.WritePort}
 * @implements {cljs.core.async.impl.protocols.ReadPort}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async48331 = (function (f,ch,meta48332){
this.f = f;
this.ch = ch;
this.meta48332 = meta48332;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async48331.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_48333,meta48332__$1){
var self__ = this;
var _48333__$1 = this;
return (new cljs.core.async.t_cljs$core$async48331(self__.f,self__.ch,meta48332__$1));
}));

(cljs.core.async.t_cljs$core$async48331.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_48333){
var self__ = this;
var _48333__$1 = this;
return self__.meta48332;
}));

(cljs.core.async.t_cljs$core$async48331.prototype.cljs$core$async$impl$protocols$Channel$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async48331.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.close_BANG_(self__.ch);
}));

(cljs.core.async.t_cljs$core$async48331.prototype.cljs$core$async$impl$protocols$ReadPort$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async48331.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.take_BANG_(self__.ch,fn1);
}));

(cljs.core.async.t_cljs$core$async48331.prototype.cljs$core$async$impl$protocols$WritePort$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async48331.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.put_BANG_(self__.ch,(self__.f.cljs$core$IFn$_invoke$arity$1 ? self__.f.cljs$core$IFn$_invoke$arity$1(val) : self__.f.call(null,val)),fn1);
}));

(cljs.core.async.t_cljs$core$async48331.getBasis = (function (){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"f","f",43394975,null),new cljs.core.Symbol(null,"ch","ch",1085813622,null),new cljs.core.Symbol(null,"meta48332","meta48332",-1580037196,null)], null);
}));

(cljs.core.async.t_cljs$core$async48331.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async48331.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async48331");

(cljs.core.async.t_cljs$core$async48331.cljs$lang$ctorPrWriter = (function (this__4491__auto__,writer__4492__auto__,opt__4493__auto__){
return cljs.core._write(writer__4492__auto__,"cljs.core.async/t_cljs$core$async48331");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async48331.
 */
cljs.core.async.__GT_t_cljs$core$async48331 = (function cljs$core$async$map_GT__$___GT_t_cljs$core$async48331(f__$1,ch__$1,meta48332){
return (new cljs.core.async.t_cljs$core$async48331(f__$1,ch__$1,meta48332));
});

}

return (new cljs.core.async.t_cljs$core$async48331(f,ch,cljs.core.PersistentArrayMap.EMPTY));
});
/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.filter_GT_ = (function cljs$core$async$filter_GT_(p,ch){
if((typeof cljs !== 'undefined') && (typeof cljs.core !== 'undefined') && (typeof cljs.core.async !== 'undefined') && (typeof cljs.core.async.t_cljs$core$async48336 !== 'undefined')){
} else {

/**
* @constructor
 * @implements {cljs.core.async.impl.protocols.Channel}
 * @implements {cljs.core.async.impl.protocols.WritePort}
 * @implements {cljs.core.async.impl.protocols.ReadPort}
 * @implements {cljs.core.IMeta}
 * @implements {cljs.core.IWithMeta}
*/
cljs.core.async.t_cljs$core$async48336 = (function (p,ch,meta48337){
this.p = p;
this.ch = ch;
this.meta48337 = meta48337;
this.cljs$lang$protocol_mask$partition0$ = 393216;
this.cljs$lang$protocol_mask$partition1$ = 0;
});
(cljs.core.async.t_cljs$core$async48336.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_48338,meta48337__$1){
var self__ = this;
var _48338__$1 = this;
return (new cljs.core.async.t_cljs$core$async48336(self__.p,self__.ch,meta48337__$1));
}));

(cljs.core.async.t_cljs$core$async48336.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_48338){
var self__ = this;
var _48338__$1 = this;
return self__.meta48337;
}));

(cljs.core.async.t_cljs$core$async48336.prototype.cljs$core$async$impl$protocols$Channel$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async48336.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.close_BANG_(self__.ch);
}));

(cljs.core.async.t_cljs$core$async48336.prototype.cljs$core$async$impl$protocols$Channel$closed_QMARK_$arity$1 = (function (_){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.closed_QMARK_(self__.ch);
}));

(cljs.core.async.t_cljs$core$async48336.prototype.cljs$core$async$impl$protocols$ReadPort$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async48336.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){
var self__ = this;
var ___$1 = this;
return cljs.core.async.impl.protocols.take_BANG_(self__.ch,fn1);
}));

(cljs.core.async.t_cljs$core$async48336.prototype.cljs$core$async$impl$protocols$WritePort$ = cljs.core.PROTOCOL_SENTINEL);

(cljs.core.async.t_cljs$core$async48336.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){
var self__ = this;
var ___$1 = this;
if(cljs.core.truth_((self__.p.cljs$core$IFn$_invoke$arity$1 ? self__.p.cljs$core$IFn$_invoke$arity$1(val) : self__.p.call(null,val)))){
return cljs.core.async.impl.protocols.put_BANG_(self__.ch,val,fn1);
} else {
return cljs.core.async.impl.channels.box(cljs.core.not(cljs.core.async.impl.protocols.closed_QMARK_(self__.ch)));
}
}));

(cljs.core.async.t_cljs$core$async48336.getBasis = (function (){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"p","p",1791580836,null),new cljs.core.Symbol(null,"ch","ch",1085813622,null),new cljs.core.Symbol(null,"meta48337","meta48337",708202194,null)], null);
}));

(cljs.core.async.t_cljs$core$async48336.cljs$lang$type = true);

(cljs.core.async.t_cljs$core$async48336.cljs$lang$ctorStr = "cljs.core.async/t_cljs$core$async48336");

(cljs.core.async.t_cljs$core$async48336.cljs$lang$ctorPrWriter = (function (this__4491__auto__,writer__4492__auto__,opt__4493__auto__){
return cljs.core._write(writer__4492__auto__,"cljs.core.async/t_cljs$core$async48336");
}));

/**
 * Positional factory function for cljs.core.async/t_cljs$core$async48336.
 */
cljs.core.async.__GT_t_cljs$core$async48336 = (function cljs$core$async$filter_GT__$___GT_t_cljs$core$async48336(p__$1,ch__$1,meta48337){
return (new cljs.core.async.t_cljs$core$async48336(p__$1,ch__$1,meta48337));
});

}

return (new cljs.core.async.t_cljs$core$async48336(p,ch,cljs.core.PersistentArrayMap.EMPTY));
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
var G__48347 = arguments.length;
switch (G__48347) {
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
var c__46528__auto___49685 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_48372){
var state_val_48373 = (state_48372[(1)]);
if((state_val_48373 === (7))){
var inst_48368 = (state_48372[(2)]);
var state_48372__$1 = state_48372;
var statearr_48377_49686 = state_48372__$1;
(statearr_48377_49686[(2)] = inst_48368);

(statearr_48377_49686[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48373 === (1))){
var state_48372__$1 = state_48372;
var statearr_48378_49687 = state_48372__$1;
(statearr_48378_49687[(2)] = null);

(statearr_48378_49687[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48373 === (4))){
var inst_48354 = (state_48372[(7)]);
var inst_48354__$1 = (state_48372[(2)]);
var inst_48355 = (inst_48354__$1 == null);
var state_48372__$1 = (function (){var statearr_48379 = state_48372;
(statearr_48379[(7)] = inst_48354__$1);

return statearr_48379;
})();
if(cljs.core.truth_(inst_48355)){
var statearr_48380_49688 = state_48372__$1;
(statearr_48380_49688[(1)] = (5));

} else {
var statearr_48381_49689 = state_48372__$1;
(statearr_48381_49689[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48373 === (6))){
var inst_48354 = (state_48372[(7)]);
var inst_48359 = (p.cljs$core$IFn$_invoke$arity$1 ? p.cljs$core$IFn$_invoke$arity$1(inst_48354) : p.call(null,inst_48354));
var state_48372__$1 = state_48372;
if(cljs.core.truth_(inst_48359)){
var statearr_48382_49690 = state_48372__$1;
(statearr_48382_49690[(1)] = (8));

} else {
var statearr_48383_49691 = state_48372__$1;
(statearr_48383_49691[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48373 === (3))){
var inst_48370 = (state_48372[(2)]);
var state_48372__$1 = state_48372;
return cljs.core.async.impl.ioc_helpers.return_chan(state_48372__$1,inst_48370);
} else {
if((state_val_48373 === (2))){
var state_48372__$1 = state_48372;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_48372__$1,(4),ch);
} else {
if((state_val_48373 === (11))){
var inst_48362 = (state_48372[(2)]);
var state_48372__$1 = state_48372;
var statearr_48391_49692 = state_48372__$1;
(statearr_48391_49692[(2)] = inst_48362);

(statearr_48391_49692[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48373 === (9))){
var state_48372__$1 = state_48372;
var statearr_48398_49693 = state_48372__$1;
(statearr_48398_49693[(2)] = null);

(statearr_48398_49693[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48373 === (5))){
var inst_48357 = cljs.core.async.close_BANG_(out);
var state_48372__$1 = state_48372;
var statearr_48405_49694 = state_48372__$1;
(statearr_48405_49694[(2)] = inst_48357);

(statearr_48405_49694[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48373 === (10))){
var inst_48365 = (state_48372[(2)]);
var state_48372__$1 = (function (){var statearr_48406 = state_48372;
(statearr_48406[(8)] = inst_48365);

return statearr_48406;
})();
var statearr_48407_49697 = state_48372__$1;
(statearr_48407_49697[(2)] = null);

(statearr_48407_49697[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48373 === (8))){
var inst_48354 = (state_48372[(7)]);
var state_48372__$1 = state_48372;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_48372__$1,(11),out,inst_48354);
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
var cljs$core$async$state_machine__46373__auto__ = null;
var cljs$core$async$state_machine__46373__auto____0 = (function (){
var statearr_48408 = [null,null,null,null,null,null,null,null,null];
(statearr_48408[(0)] = cljs$core$async$state_machine__46373__auto__);

(statearr_48408[(1)] = (1));

return statearr_48408;
});
var cljs$core$async$state_machine__46373__auto____1 = (function (state_48372){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_48372);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e48409){var ex__46376__auto__ = e48409;
var statearr_48414_49698 = state_48372;
(statearr_48414_49698[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_48372[(4)]))){
var statearr_48418_49700 = state_48372;
(statearr_48418_49700[(1)] = cljs.core.first((state_48372[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__49704 = state_48372;
state_48372 = G__49704;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$state_machine__46373__auto__ = function(state_48372){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__46373__auto____1.call(this,state_48372);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__46373__auto____0;
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__46373__auto____1;
return cljs$core$async$state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_48422 = f__46529__auto__();
(statearr_48422[(6)] = c__46528__auto___49685);

return statearr_48422;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
}));


return out;
}));

(cljs.core.async.filter_LT_.cljs$lang$maxFixedArity = 3);

/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.remove_LT_ = (function cljs$core$async$remove_LT_(var_args){
var G__48427 = arguments.length;
switch (G__48427) {
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
var c__46528__auto__ = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_48493){
var state_val_48494 = (state_48493[(1)]);
if((state_val_48494 === (7))){
var inst_48489 = (state_48493[(2)]);
var state_48493__$1 = state_48493;
var statearr_48495_49713 = state_48493__$1;
(statearr_48495_49713[(2)] = inst_48489);

(statearr_48495_49713[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (20))){
var inst_48459 = (state_48493[(7)]);
var inst_48470 = (state_48493[(2)]);
var inst_48471 = cljs.core.next(inst_48459);
var inst_48445 = inst_48471;
var inst_48446 = null;
var inst_48447 = (0);
var inst_48448 = (0);
var state_48493__$1 = (function (){var statearr_48500 = state_48493;
(statearr_48500[(8)] = inst_48446);

(statearr_48500[(9)] = inst_48445);

(statearr_48500[(10)] = inst_48448);

(statearr_48500[(11)] = inst_48470);

(statearr_48500[(12)] = inst_48447);

return statearr_48500;
})();
var statearr_48501_49715 = state_48493__$1;
(statearr_48501_49715[(2)] = null);

(statearr_48501_49715[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (1))){
var state_48493__$1 = state_48493;
var statearr_48502_49721 = state_48493__$1;
(statearr_48502_49721[(2)] = null);

(statearr_48502_49721[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (4))){
var inst_48434 = (state_48493[(13)]);
var inst_48434__$1 = (state_48493[(2)]);
var inst_48435 = (inst_48434__$1 == null);
var state_48493__$1 = (function (){var statearr_48505 = state_48493;
(statearr_48505[(13)] = inst_48434__$1);

return statearr_48505;
})();
if(cljs.core.truth_(inst_48435)){
var statearr_48506_49726 = state_48493__$1;
(statearr_48506_49726[(1)] = (5));

} else {
var statearr_48507_49727 = state_48493__$1;
(statearr_48507_49727[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (15))){
var state_48493__$1 = state_48493;
var statearr_48512_49729 = state_48493__$1;
(statearr_48512_49729[(2)] = null);

(statearr_48512_49729[(1)] = (16));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (21))){
var state_48493__$1 = state_48493;
var statearr_48514_49730 = state_48493__$1;
(statearr_48514_49730[(2)] = null);

(statearr_48514_49730[(1)] = (23));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (13))){
var inst_48446 = (state_48493[(8)]);
var inst_48445 = (state_48493[(9)]);
var inst_48448 = (state_48493[(10)]);
var inst_48447 = (state_48493[(12)]);
var inst_48455 = (state_48493[(2)]);
var inst_48456 = (inst_48448 + (1));
var tmp48508 = inst_48446;
var tmp48509 = inst_48445;
var tmp48510 = inst_48447;
var inst_48445__$1 = tmp48509;
var inst_48446__$1 = tmp48508;
var inst_48447__$1 = tmp48510;
var inst_48448__$1 = inst_48456;
var state_48493__$1 = (function (){var statearr_48515 = state_48493;
(statearr_48515[(14)] = inst_48455);

(statearr_48515[(8)] = inst_48446__$1);

(statearr_48515[(9)] = inst_48445__$1);

(statearr_48515[(10)] = inst_48448__$1);

(statearr_48515[(12)] = inst_48447__$1);

return statearr_48515;
})();
var statearr_48516_49738 = state_48493__$1;
(statearr_48516_49738[(2)] = null);

(statearr_48516_49738[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (22))){
var state_48493__$1 = state_48493;
var statearr_48517_49744 = state_48493__$1;
(statearr_48517_49744[(2)] = null);

(statearr_48517_49744[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (6))){
var inst_48434 = (state_48493[(13)]);
var inst_48443 = (f.cljs$core$IFn$_invoke$arity$1 ? f.cljs$core$IFn$_invoke$arity$1(inst_48434) : f.call(null,inst_48434));
var inst_48444 = cljs.core.seq(inst_48443);
var inst_48445 = inst_48444;
var inst_48446 = null;
var inst_48447 = (0);
var inst_48448 = (0);
var state_48493__$1 = (function (){var statearr_48518 = state_48493;
(statearr_48518[(8)] = inst_48446);

(statearr_48518[(9)] = inst_48445);

(statearr_48518[(10)] = inst_48448);

(statearr_48518[(12)] = inst_48447);

return statearr_48518;
})();
var statearr_48519_49746 = state_48493__$1;
(statearr_48519_49746[(2)] = null);

(statearr_48519_49746[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (17))){
var inst_48459 = (state_48493[(7)]);
var inst_48463 = cljs.core.chunk_first(inst_48459);
var inst_48464 = cljs.core.chunk_rest(inst_48459);
var inst_48465 = cljs.core.count(inst_48463);
var inst_48445 = inst_48464;
var inst_48446 = inst_48463;
var inst_48447 = inst_48465;
var inst_48448 = (0);
var state_48493__$1 = (function (){var statearr_48520 = state_48493;
(statearr_48520[(8)] = inst_48446);

(statearr_48520[(9)] = inst_48445);

(statearr_48520[(10)] = inst_48448);

(statearr_48520[(12)] = inst_48447);

return statearr_48520;
})();
var statearr_48521_49751 = state_48493__$1;
(statearr_48521_49751[(2)] = null);

(statearr_48521_49751[(1)] = (8));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (3))){
var inst_48491 = (state_48493[(2)]);
var state_48493__$1 = state_48493;
return cljs.core.async.impl.ioc_helpers.return_chan(state_48493__$1,inst_48491);
} else {
if((state_val_48494 === (12))){
var inst_48479 = (state_48493[(2)]);
var state_48493__$1 = state_48493;
var statearr_48522_49752 = state_48493__$1;
(statearr_48522_49752[(2)] = inst_48479);

(statearr_48522_49752[(1)] = (9));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (2))){
var state_48493__$1 = state_48493;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_48493__$1,(4),in$);
} else {
if((state_val_48494 === (23))){
var inst_48487 = (state_48493[(2)]);
var state_48493__$1 = state_48493;
var statearr_48525_49756 = state_48493__$1;
(statearr_48525_49756[(2)] = inst_48487);

(statearr_48525_49756[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (19))){
var inst_48474 = (state_48493[(2)]);
var state_48493__$1 = state_48493;
var statearr_48526_49760 = state_48493__$1;
(statearr_48526_49760[(2)] = inst_48474);

(statearr_48526_49760[(1)] = (16));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (11))){
var inst_48459 = (state_48493[(7)]);
var inst_48445 = (state_48493[(9)]);
var inst_48459__$1 = cljs.core.seq(inst_48445);
var state_48493__$1 = (function (){var statearr_48527 = state_48493;
(statearr_48527[(7)] = inst_48459__$1);

return statearr_48527;
})();
if(inst_48459__$1){
var statearr_48528_49761 = state_48493__$1;
(statearr_48528_49761[(1)] = (14));

} else {
var statearr_48529_49762 = state_48493__$1;
(statearr_48529_49762[(1)] = (15));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (9))){
var inst_48481 = (state_48493[(2)]);
var inst_48482 = cljs.core.async.impl.protocols.closed_QMARK_(out);
var state_48493__$1 = (function (){var statearr_48530 = state_48493;
(statearr_48530[(15)] = inst_48481);

return statearr_48530;
})();
if(cljs.core.truth_(inst_48482)){
var statearr_48531_49763 = state_48493__$1;
(statearr_48531_49763[(1)] = (21));

} else {
var statearr_48533_49764 = state_48493__$1;
(statearr_48533_49764[(1)] = (22));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (5))){
var inst_48437 = cljs.core.async.close_BANG_(out);
var state_48493__$1 = state_48493;
var statearr_48534_49765 = state_48493__$1;
(statearr_48534_49765[(2)] = inst_48437);

(statearr_48534_49765[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (14))){
var inst_48459 = (state_48493[(7)]);
var inst_48461 = cljs.core.chunked_seq_QMARK_(inst_48459);
var state_48493__$1 = state_48493;
if(inst_48461){
var statearr_48539_49766 = state_48493__$1;
(statearr_48539_49766[(1)] = (17));

} else {
var statearr_48540_49768 = state_48493__$1;
(statearr_48540_49768[(1)] = (18));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (16))){
var inst_48477 = (state_48493[(2)]);
var state_48493__$1 = state_48493;
var statearr_48541_49770 = state_48493__$1;
(statearr_48541_49770[(2)] = inst_48477);

(statearr_48541_49770[(1)] = (12));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48494 === (10))){
var inst_48446 = (state_48493[(8)]);
var inst_48448 = (state_48493[(10)]);
var inst_48453 = cljs.core._nth(inst_48446,inst_48448);
var state_48493__$1 = state_48493;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_48493__$1,(13),out,inst_48453);
} else {
if((state_val_48494 === (18))){
var inst_48459 = (state_48493[(7)]);
var inst_48468 = cljs.core.first(inst_48459);
var state_48493__$1 = state_48493;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_48493__$1,(20),out,inst_48468);
} else {
if((state_val_48494 === (8))){
var inst_48448 = (state_48493[(10)]);
var inst_48447 = (state_48493[(12)]);
var inst_48450 = (inst_48448 < inst_48447);
var inst_48451 = inst_48450;
var state_48493__$1 = state_48493;
if(cljs.core.truth_(inst_48451)){
var statearr_48542_49778 = state_48493__$1;
(statearr_48542_49778[(1)] = (10));

} else {
var statearr_48543_49779 = state_48493__$1;
(statearr_48543_49779[(1)] = (11));

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
var cljs$core$async$mapcat_STAR__$_state_machine__46373__auto__ = null;
var cljs$core$async$mapcat_STAR__$_state_machine__46373__auto____0 = (function (){
var statearr_48544 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_48544[(0)] = cljs$core$async$mapcat_STAR__$_state_machine__46373__auto__);

(statearr_48544[(1)] = (1));

return statearr_48544;
});
var cljs$core$async$mapcat_STAR__$_state_machine__46373__auto____1 = (function (state_48493){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_48493);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e48545){var ex__46376__auto__ = e48545;
var statearr_48546_49785 = state_48493;
(statearr_48546_49785[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_48493[(4)]))){
var statearr_48547_49786 = state_48493;
(statearr_48547_49786[(1)] = cljs.core.first((state_48493[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__49788 = state_48493;
state_48493 = G__49788;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$mapcat_STAR__$_state_machine__46373__auto__ = function(state_48493){
switch(arguments.length){
case 0:
return cljs$core$async$mapcat_STAR__$_state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$mapcat_STAR__$_state_machine__46373__auto____1.call(this,state_48493);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$mapcat_STAR__$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$mapcat_STAR__$_state_machine__46373__auto____0;
cljs$core$async$mapcat_STAR__$_state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$mapcat_STAR__$_state_machine__46373__auto____1;
return cljs$core$async$mapcat_STAR__$_state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_48548 = f__46529__auto__();
(statearr_48548[(6)] = c__46528__auto__);

return statearr_48548;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
}));

return c__46528__auto__;
});
/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.mapcat_LT_ = (function cljs$core$async$mapcat_LT_(var_args){
var G__48550 = arguments.length;
switch (G__48550) {
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
var G__48552 = arguments.length;
switch (G__48552) {
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
var G__48561 = arguments.length;
switch (G__48561) {
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
var c__46528__auto___49796 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_48590){
var state_val_48591 = (state_48590[(1)]);
if((state_val_48591 === (7))){
var inst_48585 = (state_48590[(2)]);
var state_48590__$1 = state_48590;
var statearr_48592_49797 = state_48590__$1;
(statearr_48592_49797[(2)] = inst_48585);

(statearr_48592_49797[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48591 === (1))){
var inst_48567 = null;
var state_48590__$1 = (function (){var statearr_48593 = state_48590;
(statearr_48593[(7)] = inst_48567);

return statearr_48593;
})();
var statearr_48594_49802 = state_48590__$1;
(statearr_48594_49802[(2)] = null);

(statearr_48594_49802[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48591 === (4))){
var inst_48570 = (state_48590[(8)]);
var inst_48570__$1 = (state_48590[(2)]);
var inst_48571 = (inst_48570__$1 == null);
var inst_48572 = cljs.core.not(inst_48571);
var state_48590__$1 = (function (){var statearr_48595 = state_48590;
(statearr_48595[(8)] = inst_48570__$1);

return statearr_48595;
})();
if(inst_48572){
var statearr_48596_49803 = state_48590__$1;
(statearr_48596_49803[(1)] = (5));

} else {
var statearr_48597_49804 = state_48590__$1;
(statearr_48597_49804[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48591 === (6))){
var state_48590__$1 = state_48590;
var statearr_48598_49805 = state_48590__$1;
(statearr_48598_49805[(2)] = null);

(statearr_48598_49805[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48591 === (3))){
var inst_48587 = (state_48590[(2)]);
var inst_48588 = cljs.core.async.close_BANG_(out);
var state_48590__$1 = (function (){var statearr_48599 = state_48590;
(statearr_48599[(9)] = inst_48587);

return statearr_48599;
})();
return cljs.core.async.impl.ioc_helpers.return_chan(state_48590__$1,inst_48588);
} else {
if((state_val_48591 === (2))){
var state_48590__$1 = state_48590;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_48590__$1,(4),ch);
} else {
if((state_val_48591 === (11))){
var inst_48570 = (state_48590[(8)]);
var inst_48579 = (state_48590[(2)]);
var inst_48567 = inst_48570;
var state_48590__$1 = (function (){var statearr_48600 = state_48590;
(statearr_48600[(7)] = inst_48567);

(statearr_48600[(10)] = inst_48579);

return statearr_48600;
})();
var statearr_48602_49809 = state_48590__$1;
(statearr_48602_49809[(2)] = null);

(statearr_48602_49809[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48591 === (9))){
var inst_48570 = (state_48590[(8)]);
var state_48590__$1 = state_48590;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_48590__$1,(11),out,inst_48570);
} else {
if((state_val_48591 === (5))){
var inst_48567 = (state_48590[(7)]);
var inst_48570 = (state_48590[(8)]);
var inst_48574 = cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(inst_48570,inst_48567);
var state_48590__$1 = state_48590;
if(inst_48574){
var statearr_48607_49811 = state_48590__$1;
(statearr_48607_49811[(1)] = (8));

} else {
var statearr_48608_49814 = state_48590__$1;
(statearr_48608_49814[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48591 === (10))){
var inst_48582 = (state_48590[(2)]);
var state_48590__$1 = state_48590;
var statearr_48609_49815 = state_48590__$1;
(statearr_48609_49815[(2)] = inst_48582);

(statearr_48609_49815[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48591 === (8))){
var inst_48567 = (state_48590[(7)]);
var tmp48606 = inst_48567;
var inst_48567__$1 = tmp48606;
var state_48590__$1 = (function (){var statearr_48610 = state_48590;
(statearr_48610[(7)] = inst_48567__$1);

return statearr_48610;
})();
var statearr_48612_49822 = state_48590__$1;
(statearr_48612_49822[(2)] = null);

(statearr_48612_49822[(1)] = (2));


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
var cljs$core$async$state_machine__46373__auto__ = null;
var cljs$core$async$state_machine__46373__auto____0 = (function (){
var statearr_48616 = [null,null,null,null,null,null,null,null,null,null,null];
(statearr_48616[(0)] = cljs$core$async$state_machine__46373__auto__);

(statearr_48616[(1)] = (1));

return statearr_48616;
});
var cljs$core$async$state_machine__46373__auto____1 = (function (state_48590){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_48590);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e48617){var ex__46376__auto__ = e48617;
var statearr_48618_49839 = state_48590;
(statearr_48618_49839[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_48590[(4)]))){
var statearr_48619_49840 = state_48590;
(statearr_48619_49840[(1)] = cljs.core.first((state_48590[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__49846 = state_48590;
state_48590 = G__49846;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$state_machine__46373__auto__ = function(state_48590){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__46373__auto____1.call(this,state_48590);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__46373__auto____0;
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__46373__auto____1;
return cljs$core$async$state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_48620 = f__46529__auto__();
(statearr_48620[(6)] = c__46528__auto___49796);

return statearr_48620;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
}));


return out;
}));

(cljs.core.async.unique.cljs$lang$maxFixedArity = 2);

/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.partition = (function cljs$core$async$partition(var_args){
var G__48622 = arguments.length;
switch (G__48622) {
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
var c__46528__auto___49855 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_48662){
var state_val_48663 = (state_48662[(1)]);
if((state_val_48663 === (7))){
var inst_48658 = (state_48662[(2)]);
var state_48662__$1 = state_48662;
var statearr_48667_49872 = state_48662__$1;
(statearr_48667_49872[(2)] = inst_48658);

(statearr_48667_49872[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48663 === (1))){
var inst_48623 = (new Array(n));
var inst_48624 = inst_48623;
var inst_48625 = (0);
var state_48662__$1 = (function (){var statearr_48668 = state_48662;
(statearr_48668[(7)] = inst_48624);

(statearr_48668[(8)] = inst_48625);

return statearr_48668;
})();
var statearr_48669_49879 = state_48662__$1;
(statearr_48669_49879[(2)] = null);

(statearr_48669_49879[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48663 === (4))){
var inst_48629 = (state_48662[(9)]);
var inst_48629__$1 = (state_48662[(2)]);
var inst_48630 = (inst_48629__$1 == null);
var inst_48631 = cljs.core.not(inst_48630);
var state_48662__$1 = (function (){var statearr_48670 = state_48662;
(statearr_48670[(9)] = inst_48629__$1);

return statearr_48670;
})();
if(inst_48631){
var statearr_48671_49892 = state_48662__$1;
(statearr_48671_49892[(1)] = (5));

} else {
var statearr_48672_49902 = state_48662__$1;
(statearr_48672_49902[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48663 === (15))){
var inst_48652 = (state_48662[(2)]);
var state_48662__$1 = state_48662;
var statearr_48673_49903 = state_48662__$1;
(statearr_48673_49903[(2)] = inst_48652);

(statearr_48673_49903[(1)] = (14));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48663 === (13))){
var state_48662__$1 = state_48662;
var statearr_48674_49905 = state_48662__$1;
(statearr_48674_49905[(2)] = null);

(statearr_48674_49905[(1)] = (14));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48663 === (6))){
var inst_48625 = (state_48662[(8)]);
var inst_48648 = (inst_48625 > (0));
var state_48662__$1 = state_48662;
if(cljs.core.truth_(inst_48648)){
var statearr_48675_49908 = state_48662__$1;
(statearr_48675_49908[(1)] = (12));

} else {
var statearr_48676_49909 = state_48662__$1;
(statearr_48676_49909[(1)] = (13));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48663 === (3))){
var inst_48660 = (state_48662[(2)]);
var state_48662__$1 = state_48662;
return cljs.core.async.impl.ioc_helpers.return_chan(state_48662__$1,inst_48660);
} else {
if((state_val_48663 === (12))){
var inst_48624 = (state_48662[(7)]);
var inst_48650 = cljs.core.vec(inst_48624);
var state_48662__$1 = state_48662;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_48662__$1,(15),out,inst_48650);
} else {
if((state_val_48663 === (2))){
var state_48662__$1 = state_48662;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_48662__$1,(4),ch);
} else {
if((state_val_48663 === (11))){
var inst_48642 = (state_48662[(2)]);
var inst_48643 = (new Array(n));
var inst_48624 = inst_48643;
var inst_48625 = (0);
var state_48662__$1 = (function (){var statearr_48677 = state_48662;
(statearr_48677[(7)] = inst_48624);

(statearr_48677[(10)] = inst_48642);

(statearr_48677[(8)] = inst_48625);

return statearr_48677;
})();
var statearr_48678_49920 = state_48662__$1;
(statearr_48678_49920[(2)] = null);

(statearr_48678_49920[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48663 === (9))){
var inst_48624 = (state_48662[(7)]);
var inst_48640 = cljs.core.vec(inst_48624);
var state_48662__$1 = state_48662;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_48662__$1,(11),out,inst_48640);
} else {
if((state_val_48663 === (5))){
var inst_48624 = (state_48662[(7)]);
var inst_48625 = (state_48662[(8)]);
var inst_48629 = (state_48662[(9)]);
var inst_48635 = (state_48662[(11)]);
var inst_48634 = (inst_48624[inst_48625] = inst_48629);
var inst_48635__$1 = (inst_48625 + (1));
var inst_48636 = (inst_48635__$1 < n);
var state_48662__$1 = (function (){var statearr_48679 = state_48662;
(statearr_48679[(12)] = inst_48634);

(statearr_48679[(11)] = inst_48635__$1);

return statearr_48679;
})();
if(cljs.core.truth_(inst_48636)){
var statearr_48680_49928 = state_48662__$1;
(statearr_48680_49928[(1)] = (8));

} else {
var statearr_48681_49933 = state_48662__$1;
(statearr_48681_49933[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48663 === (14))){
var inst_48655 = (state_48662[(2)]);
var inst_48656 = cljs.core.async.close_BANG_(out);
var state_48662__$1 = (function (){var statearr_48683 = state_48662;
(statearr_48683[(13)] = inst_48655);

return statearr_48683;
})();
var statearr_48684_49939 = state_48662__$1;
(statearr_48684_49939[(2)] = inst_48656);

(statearr_48684_49939[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48663 === (10))){
var inst_48646 = (state_48662[(2)]);
var state_48662__$1 = state_48662;
var statearr_48685_49952 = state_48662__$1;
(statearr_48685_49952[(2)] = inst_48646);

(statearr_48685_49952[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48663 === (8))){
var inst_48624 = (state_48662[(7)]);
var inst_48635 = (state_48662[(11)]);
var tmp48682 = inst_48624;
var inst_48624__$1 = tmp48682;
var inst_48625 = inst_48635;
var state_48662__$1 = (function (){var statearr_48686 = state_48662;
(statearr_48686[(7)] = inst_48624__$1);

(statearr_48686[(8)] = inst_48625);

return statearr_48686;
})();
var statearr_48687_49966 = state_48662__$1;
(statearr_48687_49966[(2)] = null);

(statearr_48687_49966[(1)] = (2));


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
var cljs$core$async$state_machine__46373__auto__ = null;
var cljs$core$async$state_machine__46373__auto____0 = (function (){
var statearr_48688 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_48688[(0)] = cljs$core$async$state_machine__46373__auto__);

(statearr_48688[(1)] = (1));

return statearr_48688;
});
var cljs$core$async$state_machine__46373__auto____1 = (function (state_48662){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_48662);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e48689){var ex__46376__auto__ = e48689;
var statearr_48690_49981 = state_48662;
(statearr_48690_49981[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_48662[(4)]))){
var statearr_48691_49983 = state_48662;
(statearr_48691_49983[(1)] = cljs.core.first((state_48662[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__49985 = state_48662;
state_48662 = G__49985;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$state_machine__46373__auto__ = function(state_48662){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__46373__auto____1.call(this,state_48662);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__46373__auto____0;
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__46373__auto____1;
return cljs$core$async$state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_48692 = f__46529__auto__();
(statearr_48692[(6)] = c__46528__auto___49855);

return statearr_48692;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
}));


return out;
}));

(cljs.core.async.partition.cljs$lang$maxFixedArity = 3);

/**
 * Deprecated - this function will be removed. Use transducer instead
 */
cljs.core.async.partition_by = (function cljs$core$async$partition_by(var_args){
var G__48694 = arguments.length;
switch (G__48694) {
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
var c__46528__auto___50001 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
cljs.core.async.impl.dispatch.run((function (){
var f__46529__auto__ = (function (){var switch__46372__auto__ = (function (state_48740){
var state_val_48741 = (state_48740[(1)]);
if((state_val_48741 === (7))){
var inst_48736 = (state_48740[(2)]);
var state_48740__$1 = state_48740;
var statearr_48742_50015 = state_48740__$1;
(statearr_48742_50015[(2)] = inst_48736);

(statearr_48742_50015[(1)] = (3));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48741 === (1))){
var inst_48695 = [];
var inst_48696 = inst_48695;
var inst_48697 = new cljs.core.Keyword("cljs.core.async","nothing","cljs.core.async/nothing",-69252123);
var state_48740__$1 = (function (){var statearr_48743 = state_48740;
(statearr_48743[(7)] = inst_48697);

(statearr_48743[(8)] = inst_48696);

return statearr_48743;
})();
var statearr_48744_50020 = state_48740__$1;
(statearr_48744_50020[(2)] = null);

(statearr_48744_50020[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48741 === (4))){
var inst_48700 = (state_48740[(9)]);
var inst_48700__$1 = (state_48740[(2)]);
var inst_48701 = (inst_48700__$1 == null);
var inst_48702 = cljs.core.not(inst_48701);
var state_48740__$1 = (function (){var statearr_48745 = state_48740;
(statearr_48745[(9)] = inst_48700__$1);

return statearr_48745;
})();
if(inst_48702){
var statearr_48746_50030 = state_48740__$1;
(statearr_48746_50030[(1)] = (5));

} else {
var statearr_48747_50035 = state_48740__$1;
(statearr_48747_50035[(1)] = (6));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48741 === (15))){
var inst_48696 = (state_48740[(8)]);
var inst_48728 = cljs.core.vec(inst_48696);
var state_48740__$1 = state_48740;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_48740__$1,(18),out,inst_48728);
} else {
if((state_val_48741 === (13))){
var inst_48723 = (state_48740[(2)]);
var state_48740__$1 = state_48740;
var statearr_48751_50044 = state_48740__$1;
(statearr_48751_50044[(2)] = inst_48723);

(statearr_48751_50044[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48741 === (6))){
var inst_48696 = (state_48740[(8)]);
var inst_48725 = inst_48696.length;
var inst_48726 = (inst_48725 > (0));
var state_48740__$1 = state_48740;
if(cljs.core.truth_(inst_48726)){
var statearr_48752_50045 = state_48740__$1;
(statearr_48752_50045[(1)] = (15));

} else {
var statearr_48753_50046 = state_48740__$1;
(statearr_48753_50046[(1)] = (16));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48741 === (17))){
var inst_48733 = (state_48740[(2)]);
var inst_48734 = cljs.core.async.close_BANG_(out);
var state_48740__$1 = (function (){var statearr_48754 = state_48740;
(statearr_48754[(10)] = inst_48733);

return statearr_48754;
})();
var statearr_48755_50058 = state_48740__$1;
(statearr_48755_50058[(2)] = inst_48734);

(statearr_48755_50058[(1)] = (7));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48741 === (3))){
var inst_48738 = (state_48740[(2)]);
var state_48740__$1 = state_48740;
return cljs.core.async.impl.ioc_helpers.return_chan(state_48740__$1,inst_48738);
} else {
if((state_val_48741 === (12))){
var inst_48696 = (state_48740[(8)]);
var inst_48715 = cljs.core.vec(inst_48696);
var state_48740__$1 = state_48740;
return cljs.core.async.impl.ioc_helpers.put_BANG_(state_48740__$1,(14),out,inst_48715);
} else {
if((state_val_48741 === (2))){
var state_48740__$1 = state_48740;
return cljs.core.async.impl.ioc_helpers.take_BANG_(state_48740__$1,(4),ch);
} else {
if((state_val_48741 === (11))){
var inst_48700 = (state_48740[(9)]);
var inst_48704 = (state_48740[(11)]);
var inst_48696 = (state_48740[(8)]);
var inst_48712 = inst_48696.push(inst_48700);
var tmp48756 = inst_48696;
var inst_48696__$1 = tmp48756;
var inst_48697 = inst_48704;
var state_48740__$1 = (function (){var statearr_48761 = state_48740;
(statearr_48761[(7)] = inst_48697);

(statearr_48761[(8)] = inst_48696__$1);

(statearr_48761[(12)] = inst_48712);

return statearr_48761;
})();
var statearr_48762_50081 = state_48740__$1;
(statearr_48762_50081[(2)] = null);

(statearr_48762_50081[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48741 === (9))){
var inst_48697 = (state_48740[(7)]);
var inst_48708 = cljs.core.keyword_identical_QMARK_(inst_48697,new cljs.core.Keyword("cljs.core.async","nothing","cljs.core.async/nothing",-69252123));
var state_48740__$1 = state_48740;
var statearr_48763_50084 = state_48740__$1;
(statearr_48763_50084[(2)] = inst_48708);

(statearr_48763_50084[(1)] = (10));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48741 === (5))){
var inst_48700 = (state_48740[(9)]);
var inst_48697 = (state_48740[(7)]);
var inst_48704 = (state_48740[(11)]);
var inst_48705 = (state_48740[(13)]);
var inst_48704__$1 = (f.cljs$core$IFn$_invoke$arity$1 ? f.cljs$core$IFn$_invoke$arity$1(inst_48700) : f.call(null,inst_48700));
var inst_48705__$1 = cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(inst_48704__$1,inst_48697);
var state_48740__$1 = (function (){var statearr_48764 = state_48740;
(statearr_48764[(11)] = inst_48704__$1);

(statearr_48764[(13)] = inst_48705__$1);

return statearr_48764;
})();
if(inst_48705__$1){
var statearr_48765_50089 = state_48740__$1;
(statearr_48765_50089[(1)] = (8));

} else {
var statearr_48766_50094 = state_48740__$1;
(statearr_48766_50094[(1)] = (9));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48741 === (14))){
var inst_48700 = (state_48740[(9)]);
var inst_48704 = (state_48740[(11)]);
var inst_48717 = (state_48740[(2)]);
var inst_48719 = [];
var inst_48720 = inst_48719.push(inst_48700);
var inst_48696 = inst_48719;
var inst_48697 = inst_48704;
var state_48740__$1 = (function (){var statearr_48767 = state_48740;
(statearr_48767[(7)] = inst_48697);

(statearr_48767[(14)] = inst_48720);

(statearr_48767[(8)] = inst_48696);

(statearr_48767[(15)] = inst_48717);

return statearr_48767;
})();
var statearr_48768_50102 = state_48740__$1;
(statearr_48768_50102[(2)] = null);

(statearr_48768_50102[(1)] = (2));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48741 === (16))){
var state_48740__$1 = state_48740;
var statearr_48769_50108 = state_48740__$1;
(statearr_48769_50108[(2)] = null);

(statearr_48769_50108[(1)] = (17));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48741 === (10))){
var inst_48710 = (state_48740[(2)]);
var state_48740__$1 = state_48740;
if(cljs.core.truth_(inst_48710)){
var statearr_48770_50115 = state_48740__$1;
(statearr_48770_50115[(1)] = (11));

} else {
var statearr_48771_50116 = state_48740__$1;
(statearr_48771_50116[(1)] = (12));

}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48741 === (18))){
var inst_48730 = (state_48740[(2)]);
var state_48740__$1 = state_48740;
var statearr_48772_50125 = state_48740__$1;
(statearr_48772_50125[(2)] = inst_48730);

(statearr_48772_50125[(1)] = (17));


return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else {
if((state_val_48741 === (8))){
var inst_48705 = (state_48740[(13)]);
var state_48740__$1 = state_48740;
var statearr_48773_50126 = state_48740__$1;
(statearr_48773_50126[(2)] = inst_48705);

(statearr_48773_50126[(1)] = (10));


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
var cljs$core$async$state_machine__46373__auto__ = null;
var cljs$core$async$state_machine__46373__auto____0 = (function (){
var statearr_48774 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];
(statearr_48774[(0)] = cljs$core$async$state_machine__46373__auto__);

(statearr_48774[(1)] = (1));

return statearr_48774;
});
var cljs$core$async$state_machine__46373__auto____1 = (function (state_48740){
while(true){
var ret_value__46374__auto__ = (function (){try{while(true){
var result__46375__auto__ = switch__46372__auto__(state_48740);
if(cljs.core.keyword_identical_QMARK_(result__46375__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
continue;
} else {
return result__46375__auto__;
}
break;
}
}catch (e48775){var ex__46376__auto__ = e48775;
var statearr_48776_50135 = state_48740;
(statearr_48776_50135[(2)] = ex__46376__auto__);


if(cljs.core.seq((state_48740[(4)]))){
var statearr_48777_50139 = state_48740;
(statearr_48777_50139[(1)] = cljs.core.first((state_48740[(4)])));

} else {
throw ex__46376__auto__;
}

return new cljs.core.Keyword(null,"recur","recur",-437573268);
}})();
if(cljs.core.keyword_identical_QMARK_(ret_value__46374__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268))){
var G__50147 = state_48740;
state_48740 = G__50147;
continue;
} else {
return ret_value__46374__auto__;
}
break;
}
});
cljs$core$async$state_machine__46373__auto__ = function(state_48740){
switch(arguments.length){
case 0:
return cljs$core$async$state_machine__46373__auto____0.call(this);
case 1:
return cljs$core$async$state_machine__46373__auto____1.call(this,state_48740);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$0 = cljs$core$async$state_machine__46373__auto____0;
cljs$core$async$state_machine__46373__auto__.cljs$core$IFn$_invoke$arity$1 = cljs$core$async$state_machine__46373__auto____1;
return cljs$core$async$state_machine__46373__auto__;
})()
})();
var state__46530__auto__ = (function (){var statearr_48778 = f__46529__auto__();
(statearr_48778[(6)] = c__46528__auto___50001);

return statearr_48778;
})();
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__46530__auto__);
}));


return out;
}));

(cljs.core.async.partition_by.cljs$lang$maxFixedArity = 3);


//# sourceMappingURL=cljs.core.async.js.map
