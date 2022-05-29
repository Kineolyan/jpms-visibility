package com.kineolyan.jpms.lib.api;

import com.kineolyan.jpms.core.Calculator;
import com.kineolyan.jpms.lib.internal.InternalServiceImpl;

public final class LibService {

  private LibService() {}

  public static LibService bootstrap() {
    return new LibService();
  }

  public Application createApplication() {
    final UserServiceImpl userService = new UserServiceImpl(new InternalServiceImpl());
    return new Application(userService, Calculator.from(userService));
  }

  public record Application(UserServiceImpl service, Calculator calculator) {}
}
