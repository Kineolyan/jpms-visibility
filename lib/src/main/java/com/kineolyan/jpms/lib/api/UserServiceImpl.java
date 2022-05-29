package com.kineolyan.jpms.lib.api;

import com.kineolyan.jpms.lib.internal.InternalServiceImpl;
import com.kineolyan.jpms.sdk.api.UserService;
import com.kineolyan.jpms.sdk.internal.intf.Backdoor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.PrintStream;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserServiceImpl implements UserService {

  @Getter(
      value = AccessLevel.PRIVATE,
      onMethod_ = {@Backdoor})
  private final InternalServiceImpl service;

  @Override
  public long compute() {
    return 13;
  }

  public void describe(final PrintStream output) {
    output.println("Using service " + getClass().getName());
  }
}
