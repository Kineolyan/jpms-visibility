# jpms-visibility

Prototyping on exposing a public facade to library users `UserService` while dealing internal with another
interface `InternalService`.

This proves the possibility of an annotation-based approach to extract the internal interface.

The SDK only has to expose the internal interface and the annotation.<br>
See for example that the getter is not available in `LibService#createApplication`.

The backdoor `BackdoorGetter` is capable of invoking the marked method in this project because the module explicitly
gives access to `UserServiceImpl`. Otherwise, as the method is private, it is not possible to use it.

```java
module com.kineolyan.jpms.lib {
    // ... 
    // The following line is very important
    opens com.kineolyan.jpms.lib.api to com.kineolyan.jpms.sdk;
}
```

Would the method be public, we could also restrict the access of the class. This is demonstrated by yet another module _
hlib_. Its `HiddenUserServiceImpl` is only exposed via `IntfOnlyService` and
not visible from _app_.

Without the `opens ...`, running `MainApplication` generates the following exception.

```
Using public com.kineolyan.jpms.hlib.internal.OtherInternalServiceImpl com.kineolyan.jpms.hlib.api.HiddenUserServiceImpl.getSecret() to access the inner service
Exception in thread "main" java.lang.reflect.InaccessibleObjectException: Unable to make public com.kineolyan.jpms.hlib.internal.OtherInternalServiceImpl com.kineolyan.jpms.hlib.api.HiddenUserServiceImpl.getSecret() accessible: module com.kineolyan.jpms.hlib does not "opens com.kineolyan.jpms.hlib.api" to module com.kineolyan.jpms.sdk
```

The _core_ module can use the backdoor to access the internal service and build its `Calculator`.

The final _app_ module can build an `Application` using the exposed public API and gets compile errors when trying to
access the inner interfaces and classes.