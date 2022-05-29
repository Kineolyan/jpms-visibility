module com.kineolyan.jpms.sdk {
  exports com.kineolyan.jpms.sdk.api;
  exports com.kineolyan.jpms.sdk.internal.access to
      com.kineolyan.jpms.core;
  exports com.kineolyan.jpms.sdk.internal.intf to
      com.kineolyan.jpms.core,
      com.kineolyan.jpms.lib,
      com.kineolyan.jpms.hlib;
}
