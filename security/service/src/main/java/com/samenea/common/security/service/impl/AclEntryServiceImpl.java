package com.samenea.common.security.service.impl;

import com.samenea.common.security.model.acl.AclEntry;
import com.samenea.common.security.repository.AclEntryRepository;
import com.samenea.common.security.service.AclEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service("aclEntryService")
public class AclEntryServiceImpl implements AclEntryService {
    @Autowired
    private AclEntryRepository aclEntryRepository;


    public int findNumberOfRoleAces(ObjectIdentity objectIdentity) {
        List<AclEntry> list = aclEntryRepository.findByObjectIdentity(objectIdentity);
        int num = 0;

        Iterator<AclEntry> iterator = list.iterator();
        while (iterator.hasNext() && !iterator.next().getSid().isPrincipal()) {
            num++;
        }

        return num;
    }

    //todo implement via databases
    public int findNumberOfUserAces(ObjectIdentity objectIdentity) {
        List<AclEntry> list = aclEntryRepository.findByObjectIdentity(objectIdentity);
        int num = 0;

        Iterator<AclEntry> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getSid().isPrincipal()) {
                num++;
            }
        }

        return num;
    }
}
