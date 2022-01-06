goog.provide('eykt.spa');
eykt.spa.app_routes = new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, ["/",new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"name","name",1843675177),new cljs.core.Keyword(null,"r.common","r.common",484247494),new cljs.core.Keyword(null,"header","header",119441134),"Forsiden"], null)], null)], null);
eykt.spa.initialize = (function eykt$spa$initialize(db,ls_key){
return cljs.core.conj.cljs$core$IFn$_invoke$arity$2(db,cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.sorted_map(),(function (){var G__50000 = localStorage.getItem(ls_key);
if((G__50000 == null)){
return null;
} else {
return cljs.reader.read_string.cljs$core$IFn$_invoke$arity$1(G__50000);
}
})()));
});
eykt.spa.screen_breakpoints = new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"breakpoints","breakpoints",1018731739),new cljs.core.PersistentVector(null, 7, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"mobile","mobile",1403078170),(640),new cljs.core.Keyword(null,"tablet","tablet",-318585919),(992),new cljs.core.Keyword(null,"small-monitor","small-monitor",-1851699481),(1200),new cljs.core.Keyword(null,"large-monitor","large-monitor",-1142074365)], null),new cljs.core.Keyword(null,"debounce-ms","debounce-ms",-1127263167),(166)], null);
eykt.spa.start_db = new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"startup","startup",1974425703),true,new cljs.core.Keyword("app","show-help","app/show-help",381903216),new cljs.core.PersistentArrayMap(null, 5, [(1),false,(2),false,(5),false,(6),false,(9),true], null)], null);
eykt.spa.route_table = new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"r.common","r.common",484247494),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div","div",1057191632),"EYKT home"], null)], null);
eykt.spa.dispatch_main = (function eykt$spa$dispatch_main(){
var route = re_frame.core.subscribe.cljs$core$IFn$_invoke$arity$1(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("kee-frame","route","kee-frame/route",-106555640)], null));
var route_name = new cljs.core.Keyword(null,"name","name",1843675177).cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"data","data",-232669377).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(route)));
var web_content = (function (){var temp__5753__auto__ = cljs.core.get.cljs$core$IFn$_invoke$arity$2(eykt.spa.route_table,route_name);
if(cljs.core.truth_(temp__5753__auto__)){
var page = temp__5753__auto__;
return kee_frame.router.make_route_component(page,cljs.core.deref(route));
} else {
return null;
}
})();
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div","div",1057191632),web_content], null);
});
/**
 * takes care of light/dark-mode and loading-states
 */
eykt.spa.app_wrapper = (function eykt$spa$app_wrapper(content){
var user_screenmode = new cljs.core.Keyword(null,"dark","dark",1818973999);
var html = (document.getElementsByTagName("html")[(0)]);
var body = (document.getElementsByTagName("body")[(0)]);
html.setAttribute("class",((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"dark","dark",1818973999),user_screenmode))?"dark":""));

body.setAttribute("class","font-sans inter bg-gray-100 dark:bg-gray-800 min-h-screen");

return content;
});

//# sourceMappingURL=eykt.spa.js.map
