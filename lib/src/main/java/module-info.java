module com.kineolyan.jpms.lib {
  requires static lombok;
  requires com.kineolyan.jpms.sdk;
  requires com.kineolyan.jpms.core;

  exports com.kineolyan.jpms.lib.api;
}
