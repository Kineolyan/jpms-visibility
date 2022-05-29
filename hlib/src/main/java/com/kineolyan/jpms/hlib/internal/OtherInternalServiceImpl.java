package com.kineolyan.jpms.hlib.internal;

import com.kineolyan.jpms.sdk.internal.intf.InternalService;

public final class OtherInternalServiceImpl implements InternalService {
  @Override
  public long query(long a, long b) {
    return 100 + a - b;
  }
}
