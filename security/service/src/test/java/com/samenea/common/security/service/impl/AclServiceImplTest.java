package com.samenea.common.security.service.impl;

import com.samenea.common.security.model.Role;
import com.samenea.common.security.model.User;
import com.samenea.common.security.service.AclService;
import com.samenea.common.security.service.RoleService;
import com.samenea.common.security.service.UserService;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import test.annotation.DataSets;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;
//
///**
// * Created with IntelliJ IDEA.
// * User: javaee
// * Date: 9/4/12
// * Time: 1:32 PM
// * To change this template use File | Settings | File Templates.
// */
//@Ignore
//@ContextConfiguration(locations = { "classpath:/applicationContext-smsPanel-service-test.xml" })
//@TestExecutionListeners({SmsPanelTestExecutionListener.class})
//@DataSets(setUpDataSet = "/com/samenea/smspanel/send/service/acl/acl-sample-data.xml")
//public class AclServiceImplTest extends SmsPanelBaseServiceTest {
//    @Autowired
//    private AclService aclService;
//    @Autowired
//    private FilterService filterService;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private RoleService roleService;
//
//    @Before
//    public void setUp() throws Exception {
//        User role_admin = userService.findUsersByRole("ROLE_ADMIN").get(0);
//        TestingAuthenticationToken authentication = new TestingAuthenticationToken(role_admin, role_admin.getAuthorities());
//        authentication.setAuthenticated(true);
//        SecurityContextHolder.getContext().setAuthentication(
//                authentication);
//
//    }
//
//    @Test
//    public void testAssignAclGrantedAuthorityToEntity() throws Exception {
//        Role role = roleService.getRoleByName("ROLE_USER");
//        Role role2 = roleService.getRoleByName("ROLE_ADMIN");
//        Role role3 = roleService.getRoleByName("ROLE_REQUEST");
//        ArrayList<Role> roles = new ArrayList<Role>();
//        roles.add(role);
//        roles.add(role2);
//        List<Filter> allFilters = filterService.getAllFilters();
//        aclService.assignAclGrantedAuthorityToEntity(roles, allFilters.subList(0,1), BasePermission.ADMINISTRATION);
//        Acl acl = aclService.readAclByEntity(allFilters.get(0));
//        List<AccessControlEntry> entries = acl.getEntries();
//        Assert.assertEquals(entries.size(), roles.size());
//        for (AccessControlEntry entry : entries) {
//            Assert.assertEquals(entry.getPermission(), BasePermission.ADMINISTRATION);
//            String sid = entry.getSid().toString();
//            Assert.assertTrue(sid.contains("ROLE_USER") || sid.contains("ROLE_ADMIN"));
//        }
//        roles.add(role3);
//        aclService.assignAclGrantedAuthorityToEntity(roles, allFilters.subList(0,1) , BasePermission.ADMINISTRATION);
//        acl = aclService.readAclByEntity(allFilters.get(0));
//        entries = acl.getEntries();
//        Assert.assertEquals(entries.size(), roles.size());
//        for (AccessControlEntry entry : entries) {
//            Assert.assertEquals(entry.getPermission(), BasePermission.ADMINISTRATION);
//            String sid = entry.getSid().toString();
//            Assert.assertTrue(sid.contains("ROLE_USER") || sid.contains("ROLE_ADMIN") || sid.contains("ROLE_REQUEST"));
//        }
//
//    }
//
//    @Test
//    public void testAssignAclUserToEntity() throws Exception {
//        List<User> users = new ArrayList<User>();
//        User message = userService.findUsersByUsername("message");
//        User user = userService.findUsersByUsername("user");
//        User admin = userService.findUsersByUsername("admin");
//        users.add(message);
//        users.add(admin);
//        List<Filter> allFilters = filterService.getAllFilters();
//        aclService.assignAclUserToEntity(users, allFilters.subList(1,2), BasePermission.READ);
//        Acl acl = aclService.readAclByEntity(allFilters.get(1));
//        List<AccessControlEntry> entries = acl.getEntries();
//        Assert.assertEquals(entries.size(), users.size());
//        for (AccessControlEntry entry : entries) {
//            Assert.assertEquals(entry.getPermission(), BasePermission.READ);
//            String sid = entry.getSid().toString();
//            Assert.assertTrue(sid.contains("message") || sid.contains("admin"));
//        }
//        users.add(user);
//        aclService.assignAclUserToEntity(users, allFilters.subList(1,2), BasePermission.READ);
//        acl = aclService.readAclByEntity(allFilters.get(1));
//        entries = acl.getEntries();
//        Assert.assertEquals(entries.size(), users.size());
//        for (AccessControlEntry entry : entries) {
//            Assert.assertEquals(entry.getPermission(), BasePermission.READ);
//            String sid = entry.getSid().toString();
//            Assert.assertTrue(sid.contains("message") || sid.contains("admin") || sid.contains("user"));
//        }
//
//    }
//}
