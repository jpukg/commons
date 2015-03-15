package com.samenea.common.security.repository;

import com.samenea.common.security.model.acl.AclEntry;
import com.samenea.commons.component.model.BasicRepository;
import org.springframework.security.acls.model.ObjectIdentity;

import java.util.List;

public interface AclEntryRepository extends BasicRepository<AclEntry, Long> {
    List<AclEntry> findByObjectIdentity(ObjectIdentity objectIdentity);
}
