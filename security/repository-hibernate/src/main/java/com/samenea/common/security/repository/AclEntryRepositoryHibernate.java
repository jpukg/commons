package com.samenea.common.security.repository;

import com.samenea.common.security.model.acl.AclEntry;
import com.samenea.commons.model.repository.BasicRepositoryHibernate;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("aclEntryDao")
public class AclEntryRepositoryHibernate extends BasicRepositoryHibernate<AclEntry, Long> implements AclEntryRepository {

    public AclEntryRepositoryHibernate() {
        super(AclEntry.class);
    }

    public List<AclEntry> findByObjectIdentity(ObjectIdentity objectIdentity) {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("objectIdIdentity",objectIdentity.getIdentifier());
        params.put("objectClassName",objectIdentity.getType());
        return findByNamedQuery("findByObjectIdentity", params);
    }
}
