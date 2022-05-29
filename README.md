# jpms-visibility

Prototyping on exposing a public facade to library users `UserService` while dealing internal with another
interface `InternalService`.

This proves the possibility of an annotation-based approach to extract the internal interface.

The SDK only has to expose the internal interface and the annotation.<br>
See for example that the getter is not available in `LibService#createApplication`.

The backdoor `BackdoorGetter` is capable of invoking the marked method in this project because the
class `UserServiceImpl` is public. Would it be private, which is unlikely as we want users to see it, we would have to
add `opens` for the backdoor as follows:

```java
module com.kineolyan.jpms.lib {
    // ... unchanged
    // The following is new.
    opens com.kineolyan.jpms.lib.api to com.kineolyan.jpms.sdk;
}
```

The _core_ module can use the backdoor to access the internal service and build its `Calculator`.

The final _app_ module can build an `Application` using the exposed public API and gets compile errors when trying to
access the inner interfaces and classes.