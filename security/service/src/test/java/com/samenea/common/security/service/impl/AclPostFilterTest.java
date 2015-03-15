package com.samenea.common.security.service.impl;

import com.samenea.common.security.model.User;
import com.samenea.common.security.service.AclService;
import com.samenea.common.security.service.RoleService;
import com.samenea.common.security.service.UserService;
import com.samenea.commons.component.utils.CollectionUtils;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import test.annotation.DataSets;

/*import java.util.List;

*//**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 9/4/12
 * Time: 1:32 PM
 * To change this template use File | Settings | File Templates.
 *//*
@Ignore
@ContextConfiguration(locations = { "classpath:/applicationContext-smsPanel-service-test.xml" })
@TestExecutionListeners({SmsPanelTestExecutionListener.class})
@DataSets(setUpDataSet = "/com/samenea/smspanel/send/service/acl/acl-post-filter-sample-data.xml")
public class AclPostFilterTest extends SmsPanelBaseServiceTest {
    @Autowired
    private AclService aclService;
    @Autowired
    private FilterService filterService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    
    @Before
    public void setUp() throws Exception {
        User role_admin = userService.findUsersByRole("ROLE_USER").get(0);
        TestingAuthenticationToken authentication = new TestingAuthenticationToken(role_admin, role_admin.getAuthorities());
        authentication.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(
                authentication);

    }


    @Test
    public void testPostFilter() throws Exception {
        List<Filter> allFilters = filterService.getAllFilters();
        Assert.assertEquals(allFilters.size(), 5);
        allFilters = filterService.findAllPermittedFilters();
        Assert.assertEquals(allFilters.size(), 1);
        Assert.assertEquals(allFilters.get(0).getId(), Long.valueOf(31));
        aclService.assignAclUserToEntity(userService.findUsersByRole("ROLE_USER"), CollectionUtils.paramsAsList(filterService.get(33l)), BasePermission.ADMINISTRATION);
        allFilters = filterService.findAllPermittedFilters();
        Assert.assertEquals(allFilters.size(), 2);
    }


}
*/