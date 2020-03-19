package com.ftd.bpm;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.flowable.idm.api.IdmIdentityService;
import org.flowable.idm.api.Privilege;
import org.flowable.idm.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Log4j2
public class UserProvisioning {
    private IdmIdentityService idmIdentityService;
    private final List<BpmUser> bpmUsers = Stream.of(
            new BpmUser("rest", "ROLE_REST"),
            new BpmUser("actuator", "ROLE_ACTUATOR"),
            new BpmUser("bpmuser", "ROLE_REST"))
            .collect(Collectors.toList());

    @Autowired
    public void setIdmIdentityService(IdmIdentityService idmIdentityService) {
        this.idmIdentityService = idmIdentityService;
    }

    @PostConstruct
    public void init() {
        bpmUsers.forEach(this::createBpmUser);
    }

    private void createBpmUser(BpmUser bpmUser) {
        if (idmIdentityService.createUserQuery().userId(bpmUser.username).count() == 0) {
            User user = idmIdentityService.newUser(bpmUser.username);
            user.setPassword("test");
            idmIdentityService.saveUser(user);
            log.info("user " + bpmUser.username + " created");
        }

        List<Privilege> privileges = idmIdentityService.createPrivilegeQuery().privilegeName(bpmUser.role).list();
        Privilege privilege = !privileges.isEmpty()? privileges.get(0) : idmIdentityService.createPrivilege(bpmUser.role);
        idmIdentityService.addUserPrivilegeMapping(privilege.getId(), bpmUser.username);
        log.info("user " + bpmUser.username + " authorized for " + bpmUser.role);
    }

    @AllArgsConstructor
    class BpmUser {
        public String username;
        public String role;
    }
}
