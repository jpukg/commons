package com.samenea.common.security.service;

import com.samenea.commons.component.model.Entity;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 9/4/12
 * Time: 1:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AclService {
    void assignAclGrantedAuthorityToEntity(List<? extends GrantedAuthority> selectedGrantedAuthorityList, List<? extends Entity> entities, Permission permission);

    void assignAclUserToEntity(List<? extends UserDetails> selectedUserList, List<? extends Entity> entities, Permission permission);

    void assignAclToEntity(String username, Entity entity, Permission permission);

    ObjectIdentity createObjectIdentity(Entity entity) throws NotFoundException;

    Acl readAclByObjectIdentity(ObjectIdentity oid) throws NotFoundException;

    Acl readAclByEntity(Entity entity);

    void revokeGrant(String username, Entity entity, Permission permission);
}
