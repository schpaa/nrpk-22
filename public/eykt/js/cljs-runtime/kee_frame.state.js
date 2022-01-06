goog.provide('kee_frame.state');
kee_frame.state.controllers = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(re_frame.interop.empty_queue);
kee_frame.state.router = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(null);
kee_frame.state.navigator = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(null);
kee_frame.state.breakpoints_initialized_QMARK_ = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(false);
kee_frame.state.reset_state_BANG_ = (function kee_frame$state$reset_state_BANG_(){
cljs.core.reset_BANG_(kee_frame.state.controllers,re_frame.interop.empty_queue);

cljs.core.reset_BANG_(kee_frame.state.router,null);

return cljs.core.reset_BANG_(kee_frame.state.navigator,null);
});

//# sourceMappingURL=kee_frame.state.js.map
