module com.kineolyan.jpms.hlib {
  requires static lombok;
  requires com.kineolyan.jpms.sdk;
  requires com.kineolyan.jpms.core;

  exports com.kineolyan.jpms.hlib.api;

  opens com.kineolyan.jpms.hlib.api to com.kineolyan.jpms.sdk;
}
