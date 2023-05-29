package se.accelerateit.signup6.integrationtest.dao;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.dao.MembershipMapper;
import se.accelerateit.signup6.integrationtest.SignupDbTest;
import se.accelerateit.signup6.model.Membership;
import se.accelerateit.signup6.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
public class MembershipMapperTest extends SignupDbTest{


    private final MembershipMapper membershipMapper;

    @Autowired
    MembershipMapperTest(MembershipMapper membershipMapper) {
        this.membershipMapper = membershipMapper;
    }

    @Test
    void findMembershipForEvent() {

        assertEquals(-1L, membershipMapper.findMembershipForEvent(-1L, -2L).orElseThrow(), "User -1 should be member of group where event -2 is planned");
        assertEquals(-2L, membershipMapper.findMembershipForEvent(-2L, -2L).orElseThrow(), "User -2 should be member of group where event -2 is planned");
        assertEquals(-3L, membershipMapper.findMembershipForEvent(-3L, -3L).orElseThrow(), "User -3 should be member of group where event -3 is planned");

        assertFalse(membershipMapper.findMembershipForEvent(-5L, -2L).isPresent(), "User -5 should NOT be member of group where event -2 is planned");
        assertFalse(membershipMapper.findMembershipForEvent(-1L, -222L).isPresent(), "User -1 should NOT be member of group for a non-existent event -222");
        assertFalse(membershipMapper.findMembershipForEvent(-111L, -2L).isPresent(), "Non-existing user -111 NOT be member of group where event -2 is planned");
        assertFalse(membershipMapper.findMembershipForEvent(-111L, -222L).isPresent(), "Non-existing user -111 NOT be member of group for a non-existent event -222");

    }

    @Test
    void findByUserAndGroup() {
        Optional<Membership> dbResponse = membershipMapper.findByUserAndGroup(-1L, -1L);
        assertTrue(dbResponse.isPresent());
        log.info("Existing user and group = {}", dbResponse);
        Membership membership = dbResponse.get();

        assertEquals(-1L,membership.getId());
        assertEquals(-1L, membership.getUserId());
        assertEquals(-1L, membership.getGroupId());

        Optional<Membership> nonExistingGroup = membershipMapper.findByUserAndGroup(-1L, -5000000L);
        assertFalse(nonExistingGroup.isPresent());
        log.info("Non existing group = {}", nonExistingGroup);

        Optional<Membership> nonExistingUser = membershipMapper.findByUserAndGroup(-1000000000L, -1L);
        assertFalse(nonExistingUser.isPresent());
        log.info("Non existing user = {}", nonExistingUser);

        Optional<Membership> nonExistingUserAndGroup = membershipMapper.findByUserAndGroup(-1000000000L, -1000000000L);
        assertFalse(nonExistingUserAndGroup.isPresent());
        log.info("Non existing user and group = {}", nonExistingUserAndGroup);
    }

    @Test
    void findUsersByGroup() {
        List<User> dbResponse = membershipMapper.findUsersByGroup(-2L);
        assertFalse(dbResponse.isEmpty());

        List<User> dbResponse2 = membershipMapper.findUsersByGroup(-9L);
        assertFalse(dbResponse2.isEmpty());

        User userOne = dbResponse.get(0);
        log.info("User one = {}", userOne);
        assertEquals(-4L, userOne.getId());

        User userTwo = dbResponse.get(1);
        log.info("User two = {}", userTwo);
        assertEquals(-3L, userTwo.getId());

        User user2_1 = dbResponse2.get(0);
        log.info("User 2_1 = {}", user2_1);
        assertEquals(-9L, user2_1.getId());

        List<User> nonExistingGroup = membershipMapper.findUsersByGroup(-2222222L);
        assertTrue(nonExistingGroup.isEmpty());
    }
}

