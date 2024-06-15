package com.nexia.nexus.api.interfaces;

import com.nexia.nexus.api.util.Identifier;
import com.nexia.nexus.api.util.ImplementationUtils;

public interface Namespaced {
    default Identifier getNamespaceId() {
        return ImplementationUtils.getInstance().getIdentifier(this);
    }
}
