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

See the attempts of `MainApplication#tryingToHackAndAccessService` to access the inner classes:

```
Testing service with public visibility
Using private com.kineolyan.jpms.lib.internal.InternalServiceImpl com.kineolyan.jpms.lib.api.UserServiceImpl.getService() to access the inner service
Using service com.kineolyan.jpms.lib.api.UserServiceImpl
Creating service com.kineolyan.jpms.hlib.api.HiddenUserServiceImpl
Using public com.kineolyan.jpms.hlib.internal.OtherInternalServiceImpl com.kineolyan.jpms.hlib.api.HiddenUserServiceImpl.getSecret() to access the inner service
Service  = 13
Calculator  = 3
Can access the annotation: com.kineolyan.jpms.sdk.internal.intf.Backdoor
Found 1 annotated methods
Cannot access private com.kineolyan.jpms.lib.internal.InternalServiceImpl com.kineolyan.jpms.lib.api.UserServiceImpl.getService()

--------

Testing service with hidden visibility
Service  = -7
Calculator  = 98
Can access the annotation: com.kineolyan.jpms.sdk.internal.intf.Backdoor
Found 1 annotated methods
Cannot access public com.kineolyan.jpms.hlib.internal.OtherInternalServiceImpl com.kineolyan.jpms.hlib.api.HiddenUserServiceImpl.getSecret()
```

- we must copy-paste the explicit name of the annotation as it is not accessible
- trying to give access is not possible and would result in exception, without an explicit ad-hoc VM option
- it would require something like the
  following `--add-opens com.kineolyan.jpms.lib/com.kineolyan.jpms.lib.api=com.kineolyan.jpms.app` to give the access to
  the magical class, a task even harder with unnamed modules. Note that the previous `opens` only work for the first service, the opens not being present for the second.

```
Testing service with public visibility
Using private com.kineolyan.jpms.lib.internal.InternalServiceImpl com.kineolyan.jpms.lib.api.UserServiceImpl.getService() to access the inner service
Using service com.kineolyan.jpms.lib.api.UserServiceImpl
Creating service com.kineolyan.jpms.hlib.api.HiddenUserServiceImpl
Using public com.kineolyan.jpms.hlib.internal.OtherInternalServiceImpl com.kineolyan.jpms.hlib.api.HiddenUserServiceImpl.getSecret() to access the inner service
Service  = 13
Calculator  = 3
Can access the annotation: com.kineolyan.jpms.sdk.internal.intf.Backdoor
Found 1 annotated methods
Giving access to private com.kineolyan.jpms.lib.internal.InternalServiceImpl com.kineolyan.jpms.lib.api.UserServiceImpl.getService()
Accessing inner object com.kineolyan.jpms.lib.internal.InternalServiceImpl@5010be6

--------

Testing service with hidden visibility
Service  = -7
Calculator  = 98
Can access the annotation: com.kineolyan.jpms.sdk.internal.intf.Backdoor
Found 1 annotated methods
Cannot access public com.kineolyan.jpms.hlib.internal.OtherInternalServiceImpl com.kineolyan.jpms.hlib.api.HiddenUserServiceImpl.getSecret()
```