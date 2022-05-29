module com.kineolyan.jpms.core {
    requires static lombok;

    requires com.kineolyan.jpms.sdk;

    exports com.kineolyan.jpms.core to com.kineolyan.jpms.app, com.kineolyan.jpms.lib;
}