module com.kineolyan.jpms.lib {
  requires static lombok;
  requires com.kineolyan.jpms.sdk;
  requires com.kineolyan.jpms.core;

  exports com.kineolyan.jpms.lib.api;

// Not needed as classes are visible
//  opens com.kineolyan.jpms.lib.api to com.kineolyan.jpms.sdk;
}
