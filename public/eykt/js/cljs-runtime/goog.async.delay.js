goog.provide("goog.async.Delay");
goog.require("goog.Disposable");
goog.require("goog.Timer");
goog.async.Delay = function(listener, opt_interval, opt_handler) {
  goog.async.Delay.base(this, "constructor");
  this.listener_ = listener;
  this.interval_ = opt_interval || 0;
  this.handler_ = opt_handler;
  this.callback_ = goog.bind(this.doAction_, this);
};
goog.inherits(goog.async.Delay, goog.Disposable);
goog.async.Delay.prototype.id_ = 0;
goog.async.Delay.prototype.disposeInternal = function() {
  goog.async.Delay.base(this, "disposeInternal");
  this.stop();
  delete this.listener_;
  delete this.handler_;
};
goog.async.Delay.prototype.start = function(opt_interval) {
  this.stop();
  this.id_ = goog.Timer.callOnce(this.callback_, opt_interval !== undefined ? opt_interval : this.interval_);
};
goog.async.Delay.prototype.startIfNotActive = function(opt_interval) {
  if (!this.isActive()) {
    this.start(opt_interval);
  }
};
goog.async.Delay.prototype.stop = function() {
  if (this.isActive()) {
    goog.Timer.clear(this.id_);
  }
  this.id_ = 0;
};
goog.async.Delay.prototype.fire = function() {
  this.stop();
  this.doAction_();
};
goog.async.Delay.prototype.fireIfActive = function() {
  if (this.isActive()) {
    this.fire();
  }
};
goog.async.Delay.prototype.isActive = function() {
  return this.id_ != 0;
};
goog.async.Delay.prototype.doAction_ = function() {
  this.id_ = 0;
  if (this.listener_) {
    this.listener_.call(this.handler_);
  }
};

//# sourceMappingURL=goog.async.delay.js.map
