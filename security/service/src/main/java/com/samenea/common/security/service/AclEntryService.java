package com.samenea.common.security.service;

import org.springframework.security.acls.model.ObjectIdentity;

public interface AclEntryService {
    int findNumberOfRoleAces(ObjectIdentity objectIdentity);

    int findNumberOfUserAces(ObjectIdentity objectIdentity);
}
