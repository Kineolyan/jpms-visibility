package com.kineolyan.jpms.hlib.api;

import com.kineolyan.jpms.hlib.internal.OtherInternalServiceImpl;
import com.kineolyan.jpms.sdk.api.UserService;
import com.kineolyan.jpms.sdk.internal.intf.Backdoor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
final class HiddenUserServiceImpl implements UserService {

  {
    System.err.println("Creating service " + getClass().getName());
  }

  @Getter(
      value = AccessLevel.PUBLIC,
      onMethod_ = {@Backdoor})
  private final OtherInternalServiceImpl secret;

  @Override
  public long compute() {
    return -7;
  }
}
