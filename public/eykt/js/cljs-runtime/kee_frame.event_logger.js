goog.provide('kee_frame.event_logger');
kee_frame.event_logger.interceptor = re_frame.interceptor.__GT_interceptor.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"id","id",-1388402092),new cljs.core.Keyword(null,"event-logger","event-logger",-24704467),new cljs.core.Keyword(null,"after","after",594996914),(function (context){
var event = re_frame.interceptor.get_coeffect.cljs$core$IFn$_invoke$arity$2(context,new cljs.core.Keyword(null,"event","event",301435442));
var orig_db = re_frame.interceptor.get_coeffect.cljs$core$IFn$_invoke$arity$2(context,new cljs.core.Keyword(null,"db","db",993250759));
var new_db = re_frame.interceptor.get_effect.cljs$core$IFn$_invoke$arity$3(context,new cljs.core.Keyword(null,"db","db",993250759),new cljs.core.Keyword("kee-frame.event-logger","not-found","kee-frame.event-logger/not-found",1442366090));
var effects = cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(re_frame.interceptor.get_effect.cljs$core$IFn$_invoke$arity$1(context),new cljs.core.Keyword(null,"db","db",993250759));
taoensso.timbre._log_BANG_.cljs$core$IFn$_invoke$arity$11(taoensso.timbre._STAR_config_STAR_,new cljs.core.Keyword(null,"debug","debug",-1608172596),"kee-frame.event-logger",null,15,new cljs.core.Keyword(null,"p","p",151049309),new cljs.core.Keyword(null,"auto","auto",-566279492),(new cljs.core.Delay((function (){
return new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [cljs.core.merge.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"event","event",301435442),event], null),((cljs.core.seq(effects))?new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"side-effects","side-effects",851436977),effects], null):null),((cljs.core.not_EQ_.cljs$core$IFn$_invoke$arity$2(new_db,new cljs.core.Keyword("kee-frame.event-logger","not-found","kee-frame.event-logger/not-found",1442366090)))?(function (){var vec__48413 = clojure.data.diff(orig_db,new_db);
var only_before = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__48413,(0),null);
var only_after = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__48413,(1),null);
var db_changed_QMARK_ = (((!((only_before == null)))) || ((!((only_after == null)))));
if(db_changed_QMARK_){
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"db-diff","db-diff",1372538101),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"only-before","only-before",738369676),only_before,new cljs.core.Keyword(null,"only-after","only-after",676451840),only_after], null)], null);
} else {
return null;
}
})():null)], 0))], null);
}),null)),null,-920524731,null);

return context;
})], 0));

//# sourceMappingURL=kee_frame.event_logger.js.map
