package com.scprojekt.infrastructure.repositories;

import com.scprojekt.domain.model.user.User;
import com.scprojekt.domain.model.user.UserNumber;
import com.scprojekt.domain.model.user.UserType;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@TransactionScoped
class InfrastructureUserRepositoryTest {

    @Inject
    private InfrastructureUserRepository infrastructureUserRepository;

    @BeforeEach
    public void setUp() {
        User user = createTestUser();
        infrastructureUserRepository.createEntity(user);
    }

    @Test
    @Transactional
    void findAll() {
        List<User> result = infrastructureUserRepository.findAll();
        assertNotNull(result);
        assertEquals("Testuser",result.get(0).getUserName());
        assertEquals(1, result.get(0).getUserType().get(0).getUserTypeId());
    }

    @Test
    void createUser() {
        UUID uuid1 = UUID.fromString("35fa10da-594a-4601-a7b7-0a707a3c1ce7");
        User user1 = createTestUser();
        user1.setUserName("Insertuser");
        user1.setUserNumber(new UserNumber(uuid1));
        infrastructureUserRepository.createEntity(user1);

        List<User> result = infrastructureUserRepository.findAll();
        assertNotNull(result);
        assertEquals(2,result.size());
        assertEquals(uuid1, result.get(1).getUserNumber().getUuid());
        assertEquals("Insertuser",result.get(1).getUserName());
    }

    private User createTestUser() {
        User user = new User();
        UserType userType = new UserType();
        List<UserType> userTypeList = new ArrayList<>();

        userType.setUserTypeId(1);
        userType.setUserRoleType("testrole");
        userType.setUserTypeDescription("Testuser");
        userTypeList.add(userType);

        user.setUserName("Testuser");
        user.setUserNumber(new UserNumber(UUID.fromString("586c2084-d545-4fac-b7d3-2319382df14f")));
        user.setUserType(userTypeList);

        return user;
    }
}