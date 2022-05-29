package com.kineolyan.jpms.app;

import com.kineolyan.jpms.core.Calculator;
import com.kineolyan.jpms.lib.api.LibService;
import com.kineolyan.jpms.lib.api.UserServiceImpl;

public class Application {

  public static void main(final String[] args) {
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
  }
}
