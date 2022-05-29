package com.kineolyan.jpms.hlib.api;

import com.kineolyan.jpms.core.Calculator;
import com.kineolyan.jpms.hlib.internal.OtherInternalServiceImpl;

public final class IntfOnlyService {

  private IntfOnlyService() {}

  public static Application createApplication() {
    final HiddenUserServiceImpl userService =
        new HiddenUserServiceImpl(new OtherInternalServiceImpl());
    return new Application(userService, Calculator.from(userService));
  }

  public record Application(HiddenUserServiceImpl service, Calculator calculator) {}
}
