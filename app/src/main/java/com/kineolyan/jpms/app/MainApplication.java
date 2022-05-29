package com.kineolyan.jpms.app;

import com.kineolyan.jpms.core.Calculator;
import com.kineolyan.jpms.hlib.api.IntfOnlyService;
import com.kineolyan.jpms.lib.api.LibService;
import com.kineolyan.jpms.lib.api.UserServiceImpl;
import com.kineolyan.jpms.sdk.api.UserService;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class MainApplication {

  public static void main(final String[] args) {
    System.out.println("Testing service with public visibility");
    runVisibleService();
    System.out.printf("%n--------%n%n");
    System.out.println("Testing service with hidden visibility");
    runIntfOnlyService();
  }

  static void runVisibleService() {
    final var application = LibService.bootstrap().createApplication();
    final UserServiceImpl service = application.service();
    // Not working internal service not visible
    //    final InternalService innerIntfSvc = null;
    // Not working as the impl is hidden
    //    final com.kineolyan.jpms.lib.internal.InternalServiceImpl innerImplSvc = null;
    final Calculator calculator = application.calculator();

    service.describe(System.err);
    System.out.println("Service  = " + service.compute());
    System.out.println("Calculator  = " + calculator.compute());

    tryingToHackAndAccessService(service);
  }

  static void runIntfOnlyService() {
    final var application = IntfOnlyService.createApplication();
    final UserService service = application.service();
    final Calculator calculator = application.calculator();

    System.out.println("Service  = " + service.compute());
    System.out.println("Calculator  = " + calculator.compute());

    tryingToHackAndAccessService(service);
  }

  static void tryingToHackAndAccessService(final UserService service) {
    try {
      // Backdoor annotation not visible, we must use its plain name
      final var annotationClass =
          (Class<? extends Annotation>)
              Class.forName("com.kineolyan.jpms.sdk.internal.intf.Backdoor");
      System.out.println("Can access the annotation: " + annotationClass.getName());
      final var methods =
          Arrays.stream(service.getClass().getDeclaredMethods())
              .filter(m -> m.isAnnotationPresent(annotationClass))
              .toList();
      System.out.println("Found " + methods.size() + " annotated methods");
      for (final var method : methods) {
        if (!method.canAccess(service)) {
          // Trying to give access
          if (method.trySetAccessible()) {
            System.out.println("Giving access to " + method);

            try {
              final var object = method.invoke(service);
              System.out.println("Accessing inner object " + object);
            } catch (IllegalAccessException | InvocationTargetException e) {
              System.out.println("Cannot invoke method " + method);
            }
          } else {
            System.out.println("Cannot access " + method);
          }
        } else {
          System.out.println("Already access to " + method);

          try {
            final var object = method.invoke(service);
            System.out.println("Accessing inner object " + object);
          } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("Cannot invoke method " + method);
          }
        }
      }
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
