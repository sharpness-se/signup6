package se.accelerateit.signup6.integrationtest.dao;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.dao.GroupMapper;
import se.accelerateit.signup6.dao.MembershipMapper;
import se.accelerateit.signup6.integrationtest.SignupDbTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
public class MembershipMapperTest extends SignupDbTest{

    static final Logger logger = LoggerFactory.getLogger(GroupMapperTest.class);

    private final MembershipMapper membershipMapper;

    @Autowired
    MembershipMapperTest(MembershipMapper membershipMapper) {
        this.membershipMapper = membershipMapper;
    }


    @Test
    void checkMembership() {

        assertEquals(-1L, membershipMapper.findMembershipForEvent(-1L, -2L).orElseThrow(), "User -1 should be member of group where event -2 is planned");
        assertEquals(-2L, membershipMapper.findMembershipForEvent(-2L, -2L).orElseThrow(), "User -2 should be member of group where event -2 is planned");
        assertEquals(-3L, membershipMapper.findMembershipForEvent(-3L, -3L).orElseThrow(), "User -3 should be member of group where event -3 is planned");

        assertFalse(membershipMapper.findMembershipForEvent(-5L, -2L).isPresent(), "User -5 should NOT be member of group where event -2 is planned");
        assertFalse(membershipMapper.findMembershipForEvent(-1L, -222L).isPresent(), "User -1 should NOT be member of group for a non-existent event -222");
        assertFalse(membershipMapper.findMembershipForEvent(-111L, -2L).isPresent(), "Non-existing user -111 NOT be member of group where event -2 is planned");
        assertFalse(membershipMapper.findMembershipForEvent(-111L, -222L).isPresent(), "Non-existing user -111 NOT be member of group for a non-existent event -222");

    }

}
