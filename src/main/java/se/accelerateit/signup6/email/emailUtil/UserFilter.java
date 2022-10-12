package se.accelerateit.signup6.email.emailUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.accelerateit.signup6.dao.MembershipMapper;
import se.accelerateit.signup6.dao.ParticipationMapper;
import se.accelerateit.signup6.dao.UserMapper;
import se.accelerateit.signup6.model.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserFilter {

    private final MembershipMapper membershipMapper;
    private final UserMapper userMapper;
    private final ParticipationMapper participationMapper;


    @Autowired
    public UserFilter(MembershipMapper membershipMapper, UserMapper userMapper, ParticipationMapper participationMapper) {
        this.membershipMapper = membershipMapper;
        this.userMapper = userMapper;
        this.participationMapper = participationMapper;
    }


    public List<User> getUsersToRemind(Event eventToRemind){
        Group groupToRemind = getGroupToRemind(eventToRemind);
        List<Participation> eventParticipations = getEventParticipations(eventToRemind);
        List<User> usersWithParticipation = getAllUsersFromParticipations(eventParticipations);
        List<User> groupMembers = getAllUsersFromGroup(groupToRemind);
        List<User> usersToRemind = new ArrayList<>();

        for (Participation part : eventParticipations) {
            if(part.getStatus().equals(ParticipationStatus.Maybe)){
                User userToAdd = getUserFromParticipation(part);
                usersToRemind.add(userToAdd);
            }
        }

        for (User user : groupMembers) {
            if (!usersWithParticipation.contains(user)){
                usersToRemind.add(user);
            }
        }

        return usersToRemind;
    }

    public List<User> getAllUsersFromGroup(Group group){
        List<Membership> memberships = membershipMapper.findUsersByGroup(group.getId());
        List<User> usersToReturn = new ArrayList<>();

        for (Membership ms : memberships) {
            usersToReturn.add(userMapper.findById(ms.getUserId()).get());
        }
        return usersToReturn;
    }

    public List<User> getAllUsersFromParticipations(List<Participation> participationList){
        List<User> usersToReturn = new ArrayList<>();

        for (Participation part : participationList) {
            usersToReturn.add(getUserFromParticipation(part));
        }

        return usersToReturn;
    }

    public User getUserFromParticipation(Participation part){
        return userMapper.findById(part.getUserId()).get();
    }

    public Group getGroupToRemind(Event eventToRemind){
        return eventToRemind.getGroup();
    }

    public List<Participation> getEventParticipations(Event eventToRemind){
        return participationMapper.findByEventId(eventToRemind.getId());
    }

}
