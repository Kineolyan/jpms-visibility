package com.kineolyan.jpms.core;

import com.kineolyan.jpms.sdk.api.UserService;
import com.kineolyan.jpms.sdk.internal.BackdoorGetter;
import com.kineolyan.jpms.sdk.internal.InternalService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Calculator {

  private final InternalService service;

  public static Calculator from(final UserService service) {
    return new Calculator(BackdoorGetter.access(service));
  }

  public long compute() {
    return this.service.query(1, 3);
  }
}
