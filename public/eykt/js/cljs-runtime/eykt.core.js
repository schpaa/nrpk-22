goog.provide('eykt.core');
eykt.core.root_element = document.getElementById("app");
eykt.core.start = (function eykt$core$start(){
return reagent.dom.render.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div","div",1057191632),"eykt"], null),eykt.core.root_element);
});
eykt.core.localstorage_key = "eykt-22";
eykt.core.kee_start = (function eykt$core$kee_start(){
return kee_frame.core.start_BANG_(new cljs.core.PersistentArrayMap(null, 6, [new cljs.core.Keyword(null,"routes","routes",457900162),eykt.spa.app_routes,new cljs.core.Keyword(null,"initial-db","initial-db",1939835102),eykt.spa.initialize(eykt.spa.start_db,eykt.core.localstorage_key),new cljs.core.Keyword(null,"root-component","root-component",-1807026475),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [eykt.spa.app_wrapper,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [eykt.spa.dispatch_main], null)], null),new cljs.core.Keyword(null,"screen","screen",1990059748),eykt.spa.screen_breakpoints,new cljs.core.Keyword(null,"hash-routing?","hash-routing?",471914732),false,new cljs.core.Keyword(null,"not-found","not-found",-629079980),"/fant-ikke"], null));
});
eykt.core.reload_BANG_ = (function eykt$core$reload_BANG_(){
console.log("Startup");

re_frame.core.clear_subscription_cache_BANG_();

return eykt.core.kee_start();
});
eykt.core.init_BANG_ = (function eykt$core$init_BANG_(){
return eykt.core.reload_BANG_();
});

//# sourceMappingURL=eykt.core.js.map
