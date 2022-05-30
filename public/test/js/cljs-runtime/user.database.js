goog.provide('user.database');
user.database.mark_account_for_removal = (function user$database$mark_account_for_removal(uid){
var removal_date = tick.core._GT__GT_(tick.core.date.cljs$core$IFn$_invoke$arity$0(),tick.core.new_period((14),new cljs.core.Keyword(null,"days","days",-1394072564)));
var path = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, ["beskjeder",uid], null);
var G__413028 = new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"path","path",-188191168),path,new cljs.core.Keyword(null,"value","value",305978217),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"removal-date","removal-date",-62541303),cljs.core.str.cljs$core$IFn$_invoke$arity$1(removal_date)], null)], null);
return (db.core.database_update.cljs$core$IFn$_invoke$arity$1 ? db.core.database_update.cljs$core$IFn$_invoke$arity$1(G__413028) : db.core.database_update.call(null,G__413028));
});
user.database.mark_account_for_restore = (function user$database$mark_account_for_restore(uid){
var removal_date = tick.core._GT__GT_(tick.core.date.cljs$core$IFn$_invoke$arity$0(),tick.core.new_period((14),new cljs.core.Keyword(null,"days","days",-1394072564)));
var path = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, ["beskjeder",uid], null);
var G__413030 = new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"path","path",-188191168),path,new cljs.core.Keyword(null,"value","value",305978217),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"removal-date","removal-date",-62541303),null], null)], null);
return (db.core.database_update.cljs$core$IFn$_invoke$arity$1 ? db.core.database_update.cljs$core$IFn$_invoke$arity$1(G__413030) : db.core.database_update.call(null,G__413030));
});
user.database.lookup_userinfo = (function user$database$lookup_userinfo(uid){
var temp__5755__auto__ = cljs.core.deref(re_frame.core.subscribe.cljs$core$IFn$_invoke$arity$1(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("db.core","user-auth","db.core/user-auth",-1883386069)], null)));
if((temp__5755__auto__ == null)){
return null;
} else {
var _ = temp__5755__auto__;
return cljs.core.deref((function (){var G__413032 = new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"path","path",-188191168),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, ["users",uid], null)], null);
return (db.core.on_value_reaction.cljs$core$IFn$_invoke$arity$1 ? db.core.on_value_reaction.cljs$core$IFn$_invoke$arity$1(G__413032) : db.core.on_value_reaction.call(null,G__413032));
})());
}
});
user.database._PLUS_memo_lookup_username = (function user$database$_PLUS_memo_lookup_username(uid){
var temp__5755__auto__ = cljs.core.deref(re_frame.core.subscribe.cljs$core$IFn$_invoke$arity$1(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("db.core","user-auth","db.core/user-auth",-1883386069)], null)));
if((temp__5755__auto__ == null)){
return null;
} else {
var _ = temp__5755__auto__;
return new cljs.core.Keyword(null,"navn","navn",1985693629).cljs$core$IFn$_invoke$arity$1(cljs.core.deref((function (){var G__413036 = new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"path","path",-188191168),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, ["users",uid], null)], null);
return (db.core.on_value_reaction.cljs$core$IFn$_invoke$arity$1 ? db.core.on_value_reaction.cljs$core$IFn$_invoke$arity$1(G__413036) : db.core.on_value_reaction.call(null,G__413036));
})()));
}
});
user.database.lookup_username = (function user$database$lookup_username(uid){
var temp__5757__auto__ = cljs.core.deref(re_frame.core.subscribe.cljs$core$IFn$_invoke$arity$1(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("db.core","user-auth","db.core/user-auth",-1883386069)], null)));
if((temp__5757__auto__ == null)){
return null;
} else {
var _ = temp__5757__auto__;
return new cljs.core.Keyword(null,"navn","navn",1985693629).cljs$core$IFn$_invoke$arity$1(cljs.core.deref((function (){var G__413040 = new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"path","path",-188191168),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, ["users",uid], null)], null);
return (db.core.on_value_reaction.cljs$core$IFn$_invoke$arity$1 ? db.core.on_value_reaction.cljs$core$IFn$_invoke$arity$1(G__413040) : db.core.on_value_reaction.call(null,G__413040));
})()));
}
});
user.database.lookup_by_litteralkeyid = (function user$database$lookup_by_litteralkeyid(id){
var temp__5757__auto__ = cljs.core.deref(re_frame.core.subscribe.cljs$core$IFn$_invoke$arity$1(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("db.core","user-auth","db.core/user-auth",-1883386069)], null)));
if((temp__5757__auto__ == null)){
return null;
} else {
var _ = temp__5757__auto__;
return cljs.core.get.cljs$core$IFn$_invoke$arity$2(cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,cljs.core.map.cljs$core$IFn$_invoke$arity$2((function (p__413048){
var vec__413049 = p__413048;
var a = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413049,(0),null);
var b = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413049,(1),null);
var c = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413049,(2),null);
var d = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413049,(3),null);
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [a,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [b,c,d], null)], null);
}),cljs.core.map.cljs$core$IFn$_invoke$arity$2(cljs.core.comp.cljs$core$IFn$_invoke$arity$2(cljs.core.juxt.cljs$core$IFn$_invoke$arity$variadic(cljs.core.comp.cljs$core$IFn$_invoke$arity$3((function (p1__413043_SHARP_){
return cljs.core.subs.cljs$core$IFn$_invoke$arity$3(p1__413043_SHARP_,(4),(7));
}),cljs.core.str,new cljs.core.Keyword(null,"n\u00F8kkelnummer","n\u00F8kkelnummer",222939941)),new cljs.core.Keyword(null,"navn","navn",1985693629),new cljs.core.Keyword(null,"telefon","telefon",1235686342),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"uid","uid",-1447769400)], 0)),cljs.core.val),cljs.core.filter.cljs$core$IFn$_invoke$arity$2((function (p__413052){
var vec__413053 = p__413052;
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413053,(0),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413053,(1),null);
var and__5041__auto__ = new cljs.core.Keyword(null,"godkjent","godkjent",113733040).cljs$core$IFn$_invoke$arity$1(v);
if(cljs.core.truth_(and__5041__auto__)){
return ((cljs.core.not(new cljs.core.Keyword(null,"utmeldt","utmeldt",1526078323).cljs$core$IFn$_invoke$arity$1(v))) && (cljs.core.seq(new cljs.core.Keyword(null,"n\u00F8kkelnummer","n\u00F8kkelnummer",222939941).cljs$core$IFn$_invoke$arity$1(v))));
} else {
return and__5041__auto__;
}
}),cljs.core.deref((function (){var G__413056 = new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"path","path",-188191168),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, ["users"], null)], null);
return (db.core.on_value_reaction.cljs$core$IFn$_invoke$arity$1 ? db.core.on_value_reaction.cljs$core$IFn$_invoke$arity$1(G__413056) : db.core.on_value_reaction.call(null,G__413056));
})()))))),id);
}
});
user.database.lookup_alias = (function user$database$lookup_alias(uid){
var temp__5757__auto__ = cljs.core.deref(re_frame.core.subscribe.cljs$core$IFn$_invoke$arity$1(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("db.core","user-auth","db.core/user-auth",-1883386069)], null)));
if((temp__5757__auto__ == null)){
return null;
} else {
var _ = temp__5757__auto__;
var u = cljs.core.deref((function (){var G__413061 = new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"path","path",-188191168),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, ["users",uid], null)], null);
return (db.core.on_value_reaction.cljs$core$IFn$_invoke$arity$1 ? db.core.on_value_reaction.cljs$core$IFn$_invoke$arity$1(G__413061) : db.core.on_value_reaction.call(null,G__413061));
})());
var alias = ((cljs.core.empty_QMARK_(new cljs.core.Keyword(null,"alias","alias",-2039751630).cljs$core$IFn$_invoke$arity$1(u)))?null:new cljs.core.Keyword(null,"alias","alias",-2039751630).cljs$core$IFn$_invoke$arity$1(u));
var navn = ((cljs.core.empty_QMARK_(new cljs.core.Keyword(null,"navn","navn",1985693629).cljs$core$IFn$_invoke$arity$1(u)))?null:new cljs.core.Keyword(null,"navn","navn",1985693629).cljs$core$IFn$_invoke$arity$1(u));
var or__5043__auto__ = alias;
if(cljs.core.truth_(or__5043__auto__)){
return or__5043__auto__;
} else {
var or__5043__auto____$1 = navn;
if(cljs.core.truth_(or__5043__auto____$1)){
return or__5043__auto____$1;
} else {
var or__5043__auto____$2 = uid;
if(cljs.core.truth_(or__5043__auto____$2)){
return or__5043__auto____$2;
} else {
return ["failed<lookup-alias>'",cljs.core.str.cljs$core$IFn$_invoke$arity$1(uid),"'"].join('');
}
}
}
}
});
user.database.lookup_phone = (function user$database$lookup_phone(phone){
var temp__5755__auto__ = cljs.core.deref(re_frame.core.subscribe.cljs$core$IFn$_invoke$arity$1(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("db.core","user-auth","db.core/user-auth",-1883386069)], null)));
if((temp__5755__auto__ == null)){
return null;
} else {
var _ = temp__5755__auto__;
return cljs.core.second(cljs.core.first(cljs.core.filter.cljs$core$IFn$_invoke$arity$2((function (p__413071){
var vec__413073 = p__413071;
var a = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413073,(0),null);
var b = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413073,(1),null);
return cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(cljs.core.str.cljs$core$IFn$_invoke$arity$1(a),cljs.core.str.cljs$core$IFn$_invoke$arity$1(phone));
}),cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,cljs.core.map.cljs$core$IFn$_invoke$arity$2((function (p__413078){
var vec__413079 = p__413078;
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413079,(0),null);
var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413079,(1),null);
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"telefon","telefon",1235686342).cljs$core$IFn$_invoke$arity$1(v),new cljs.core.Keyword(null,"uid","uid",-1447769400).cljs$core$IFn$_invoke$arity$1(v)], null);
}),cljs.core.deref((function (){var G__413082 = new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"path","path",-188191168),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, ["users"], null)], null);
return (db.core.on_value_reaction.cljs$core$IFn$_invoke$arity$1 ? db.core.on_value_reaction.cljs$core$IFn$_invoke$arity$1(G__413082) : db.core.on_value_reaction.call(null,G__413082));
})()))))));
}
});
user.database.lookup_phone("92491949");
user.database.request_booking_xf = cljs.core.filter.cljs$core$IFn$_invoke$arity$1((function (p__413088){
var vec__413090 = p__413088;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413090,(0),null);
var map__413093 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413090,(1),null);
var map__413093__$1 = cljs.core.__destructure_map(map__413093);
var request_booking = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__413093__$1,new cljs.core.Keyword(null,"request-booking","request-booking",575321311));
return (!((request_booking == null)));
}));
user.database.not_yet_accepted_booking_xf = cljs.core.filter.cljs$core$IFn$_invoke$arity$1((function (p__413094){
var vec__413095 = p__413094;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413095,(0),null);
var map__413099 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413095,(1),null);
var map__413099__$1 = cljs.core.__destructure_map(map__413099);
var booking_godkjent = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__413099__$1,new cljs.core.Keyword(null,"booking-godkjent","booking-godkjent",-545896118));
return booking_godkjent === true;
}));
user.database.accepted_booking_xf = cljs.core.filter.cljs$core$IFn$_invoke$arity$1((function (p__413104){
var vec__413107 = p__413104;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413107,(0),null);
var map__413110 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413107,(1),null);
var map__413110__$1 = cljs.core.__destructure_map(map__413110);
var booking_godkjent = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__413110__$1,new cljs.core.Keyword(null,"booking-godkjent","booking-godkjent",-545896118));
return booking_godkjent === false;
}));
user.database.booking_users = (function user$database$booking_users(var_args){
var args__5772__auto__ = [];
var len__5766__auto___413185 = arguments.length;
var i__5767__auto___413186 = (0);
while(true){
if((i__5767__auto___413186 < len__5766__auto___413185)){
args__5772__auto__.push((arguments[i__5767__auto___413186]));

var G__413188 = (i__5767__auto___413186 + (1));
i__5767__auto___413186 = G__413188;
continue;
} else {
}
break;
}

var argseq__5773__auto__ = ((((0) < args__5772__auto__.length))?(new cljs.core.IndexedSeq(args__5772__auto__.slice((0)),(0),null)):null);
return user.database.booking_users.cljs$core$IFn$_invoke$arity$variadic(argseq__5773__auto__);
});

(user.database.booking_users.cljs$core$IFn$_invoke$arity$variadic = (function (xf){
return cljs.core.transduce.cljs$core$IFn$_invoke$arity$4(cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.comp,xf),cljs.core.conj,cljs.core.PersistentVector.EMPTY,cljs.core.deref((function (){var G__413116 = new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"path","path",-188191168),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, ["users"], null)], null);
return (db.core.on_value_reaction.cljs$core$IFn$_invoke$arity$1 ? db.core.on_value_reaction.cljs$core$IFn$_invoke$arity$1(G__413116) : db.core.on_value_reaction.call(null,G__413116));
})()));
}));

(user.database.booking_users.cljs$lang$maxFixedArity = (0));

/** @this {Function} */
(user.database.booking_users.cljs$lang$applyTo = (function (seq413113){
var self__5752__auto__ = this;
return self__5752__auto__.cljs$core$IFn$_invoke$arity$variadic(cljs.core.seq(seq413113));
}));

user.database.locked = (function user$database$locked(field,icon_fn,path_fn,k,v){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [schpaa.style.button.round_normal_listitem,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"on-click","on-click",1632826543),(function (){
var G__413119 = new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"path","path",-188191168),(function (){var G__413120 = cljs.core.name(k);
return (path_fn.cljs$core$IFn$_invoke$arity$1 ? path_fn.cljs$core$IFn$_invoke$arity$1(G__413120) : path_fn.call(null,G__413120));
})(),new cljs.core.Keyword(null,"value","value",305978217),cljs.core.PersistentArrayMap.createAsIfByAssoc([field,cljs.core.not((field.cljs$core$IFn$_invoke$arity$1 ? field.cljs$core$IFn$_invoke$arity$1(v) : field.call(null,v)))])], null);
return (db.core.database_update.cljs$core$IFn$_invoke$arity$1 ? db.core.database_update.cljs$core$IFn$_invoke$arity$1(G__413119) : db.core.database_update.call(null,G__413119));
})], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [cljs.core.apply,schpaa.style.ornament.icon,(function (){var G__413124 = (field.cljs$core$IFn$_invoke$arity$1 ? field.cljs$core$IFn$_invoke$arity$1(v) : field.call(null,v));
return (icon_fn.cljs$core$IFn$_invoke$arity$1 ? icon_fn.cljs$core$IFn$_invoke$arity$1(G__413124) : icon_fn.call(null,G__413124));
})()], null)], null);
});
user.database.listitem = lambdaisland.ornament.styled(new cljs.core.Symbol("user.database","listitem","user.database/listitem",444108538,null),"user_database__listitem",new cljs.core.Keyword(null,"div","div",1057191632),null,null);
user.database.booking_users_listitem = (function user$database$booking_users_listitem(p__413148){
var vec__413149 = p__413148;
var k = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413149,(0),null);
var map__413152 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__413149,(1),null);
var map__413152__$1 = cljs.core.__destructure_map(map__413152);
var v = map__413152__$1;
var navn = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__413152__$1,new cljs.core.Keyword(null,"navn","navn",1985693629));
var ekspert = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__413152__$1,new cljs.core.Keyword(null,"ekspert","ekspert",722545482));
var våttkort = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__413152__$1,new cljs.core.Keyword(null,"v\u00E5ttkort","v\u00E5ttkort",1623361819));
var booking_godkjent = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__413152__$1,new cljs.core.Keyword(null,"booking-godkjent","booking-godkjent",-545896118));
var telefon = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__413152__$1,new cljs.core.Keyword(null,"telefon","telefon",1235686342));
var våttkortnr = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__413152__$1,new cljs.core.Keyword(null,"v\u00E5ttkortnr","v\u00E5ttkortnr",1839325733));
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [user.database.listitem,new cljs.core.PersistentVector(null, 8, 5, cljs.core.PersistentVector.EMPTY_NODE, [schpaa.style.ornament.row_std,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"style","style",-496642736),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"align-items","align-items",-267946462),new cljs.core.Keyword(null,"center","center",-748944368)], null)], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [schpaa.style.button.round_normal_listitem,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"on-click","on-click",1632826543),(function (){
return re_frame.core.dispatch(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("lab","show-userinfo","lab/show-userinfo",-686262136),v], null));
})], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [schpaa.style.ornament.icon,booking.ico.showdetails], null)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [schpaa.style.ornament.text1,navn], null),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div.grow","div.grow",-1895960225)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [schpaa.style.ornament.text1,telefon], null),new cljs.core.PersistentVector(null, 6, 5, cljs.core.PersistentVector.EMPTY_NODE, [user.database.locked,new cljs.core.Keyword(null,"booking-godkjent","booking-godkjent",-545896118),(function (p1__413141_SHARP_){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"style","style",-496642736),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"color","color",1011675173),(cljs.core.truth_(p1__413141_SHARP_)?"var(--button)":new cljs.core.Keyword(null,"blue","blue",-622100620))], null)], null),(cljs.core.truth_(p1__413141_SHARP_)?booking.ico.member:booking.ico.member)], null);
}),(function (p1__413142_SHARP_){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, ["users",p1__413142_SHARP_], null);
}),k,v], null),new cljs.core.PersistentVector(null, 6, 5, cljs.core.PersistentVector.EMPTY_NODE, [user.database.locked,new cljs.core.Keyword(null,"ekspert","ekspert",722545482),(function (p1__413143_SHARP_){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"style","style",-496642736),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"color","color",1011675173),(cljs.core.truth_(p1__413143_SHARP_)?"var(--button)":new cljs.core.Keyword(null,"red","red",-969428204))], null)], null),(cljs.core.truth_(p1__413143_SHARP_)?booking.ico.admin:booking.ico.admin)], null);
}),(function (p1__413144_SHARP_){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, ["users",p1__413144_SHARP_], null);
}),k,v], null)], null)], null);
});

//# sourceMappingURL=user.database.js.map
