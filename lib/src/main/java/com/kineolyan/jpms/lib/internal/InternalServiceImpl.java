package com.kineolyan.jpms.lib.internal;

import com.kineolyan.jpms.sdk.internal.intf.InternalService;

public class InternalServiceImpl implements InternalService {
    @Override
    public long query(long a, long b) {
        return a * b;
    }
}
