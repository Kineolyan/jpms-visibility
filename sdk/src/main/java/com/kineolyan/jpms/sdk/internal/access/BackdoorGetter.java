package com.kineolyan.jpms.sdk.internal.access;

import com.kineolyan.jpms.sdk.api.UserService;
import com.kineolyan.jpms.sdk.internal.intf.Backdoor;
import com.kineolyan.jpms.sdk.internal.intf.InternalService;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class BackdoorGetter {

    public static InternalService access(final UserService facade) {
        final var methods = Arrays.stream(facade.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Backdoor.class))
                .toList();
        final var method = switch (methods.size()) {
            case 0 -> throw new IllegalStateException("No method");
            case 1 -> methods.get(0);
            default -> throw new IllegalStateException("Too many methods");
        };
        System.err.println("Using " + method + " to access the inner service");
        method.setAccessible(true);
        try {
            return (InternalService) method.invoke(facade);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Cannot invoke", e);
        }
    }

}
