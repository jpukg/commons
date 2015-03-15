package com.samenea.common.security.service.impl;


import com.samenea.common.security.service.AclEntryService;
import com.samenea.common.security.service.AclService;
import com.samenea.commons.component.model.Entity;
import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 9/4/12
 * Time: 1:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Service

public class AclServiceImpl implements AclService {
    private static final Logger logger = LoggerFactory.getLogger(AclServiceImpl.class);

    @Autowired
    @Qualifier("aclService")
    private MutableAclService mutableAclService;

    @Autowired
    private AclEntryService aclEntryService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void assignAclGrantedAuthorityToEntity(List<? extends GrantedAuthority> selectedGrantedAuthorityList, List<? extends Entity> entities, Permission permission) {


        for (Entity entity : entities) {
            ObjectIdentity oid =
                    createObjectIdentity(entity);
            MutableAcl acl = null;
            try {
                acl = (MutableAcl) readAclByObjectIdentity(oid);
                int num = aclEntryService.findNumberOfRoleAces(acl.getObjectIdentity());
                for (int i = 0; i < num && acl.getEntries().size() > 0; i++) {
                    acl.deleteAce(0);
                }
            } catch (NotFoundException nfe) {
                acl = mutableAclService.createAcl(oid);
            }
            for (GrantedAuthority grantedAuthority : selectedGrantedAuthorityList) {
                acl.insertAce(0, permission,
                        new GrantedAuthoritySid(grantedAuthority.getAuthority()), true);
            }
            mutableAclService.updateAcl(acl);
        }

    }

    public Acl readAclByObjectIdentity(ObjectIdentity oid) throws NotFoundException {
        return mutableAclService.readAclById(oid);
    }

    public Acl readAclByEntity(Entity entity) throws NotFoundException {
        return mutableAclService.readAclById(createObjectIdentity(entity));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void revokeGrant(String username, Entity entity, Permission permission) {
        ObjectIdentity oid = createObjectIdentity(entity);
        MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);

        // Remove all permissions associated with this particular recipient (string equality used to keep things simple)
        List<AccessControlEntry> entries = acl.getEntries();
        final PrincipalSid principalSid = new PrincipalSid(username);

        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getSid().equals(principalSid) && entries.get(i).getPermission().equals(permission)) {
                acl.deleteAce(i);
            }
        }

        mutableAclService.updateAcl(acl);

        logger.debug("Deleted securedObject " + entity.toString() + " ACL permissions for recipient " + username);
    }

    public ObjectIdentity createObjectIdentity(Entity entity) {
        return new ObjectIdentityImpl(entity.getClass(), entity.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void assignAclUserToEntity(List<? extends UserDetails> selectedUserList, List<? extends Entity> entities, Permission permission) {
        for (Entity entity : entities) {
            for (UserDetails user : selectedUserList) {
                assignAclToEntity(user.getUsername(), entity, permission);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void assignAclToEntity(String username, Entity entity, Permission permission) {
        ObjectIdentity oid = createObjectIdentity(entity);
        final PrincipalSid sid = new PrincipalSid(username);

        MutableAcl acl = null;
        try {
            acl = (MutableAcl) mutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = mutableAclService.createAcl(oid);
        }

        if (aceExist(permission, sid, acl)) {
            logger.info("Permission Already exist (So doing nothing): " + permission + " for Sid " + username + " securedObject " + entity.toString());
            return;
        }

        acl.insertAce(acl.getEntries().size(), permission, sid, true);
        mutableAclService.updateAcl(acl);
        logger.info("Added permission " + permission + " for Sid " + username + " securedObject " + entity.toString());
    }

    private boolean aceExist(Permission permission, PrincipalSid oid, MutableAcl acl) {
        for (AccessControlEntry entry : acl.getEntries()) {
            if (entry.getPermission().equals(permission) && entry.getSid().equals(oid)) {
                return true;
            }
        }
        return false;
    }


}
