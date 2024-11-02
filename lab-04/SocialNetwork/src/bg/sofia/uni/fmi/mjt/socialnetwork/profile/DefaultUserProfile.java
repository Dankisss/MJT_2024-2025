package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DefaultUserProfile implements UserProfile, Comparable<UserProfile> {
    private final String username;
    private final Set<Interest> interests;
    private final Set<UserProfile> friends;

    public DefaultUserProfile(String username) {
        this.username = username;

        interests = new HashSet<>();

        friends = new HashSet<>();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<Interest> getInterests() {
        return Collections.unmodifiableSet(interests);
    }

    @Override
    public boolean addInterest(Interest interest) {
        checkIsNull(interest);

        return interests.add(interest);
    }

    @Override
    public boolean removeInterest(Interest interest) {
        checkIsNull(interest);

        return interests.remove(interest);
    }

    @Override
    public Collection<UserProfile> getFriends() {
        return Collections.unmodifiableSet(friends);
    }

    @Override
    public boolean addFriend(UserProfile userProfile) {
        checkIsNull(userProfile);

        checkForEquality(userProfile, username);

        if (isFriend(userProfile)) {
            return false;
        }

        boolean isAdded = friends.add(userProfile);

        userProfile.addFriend(this);

        return isAdded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultUserProfile that = (DefaultUserProfile) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public boolean unfriend(UserProfile userProfile) {
        checkIsNull(userProfile);

        if (!isFriend(userProfile)) {
            return false;
        }

        boolean isUnfriended = friends.remove(userProfile);

        userProfile.unfriend(this);

        return isUnfriended;
    }

    @Override
    public boolean isFriend(UserProfile userProfile) {
        checkIsNull(userProfile);

        return friends.contains(userProfile);
    }

    private static void checkIsNull(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("The user profile is null");
        }
    }

    private static void checkIsNull(Interest interest) {
        if (interest == null) {
            throw new IllegalArgumentException("The user profile is null");
        }
    }

    private static void checkForEquality(UserProfile userProfile, String username) {
        if (userProfile.getUsername().equals(username)) {
            throw new IllegalArgumentException("The user is trying to add themselves as a friend");
        }
    }

    @Override
    public int compareTo(UserProfile o) {
        int friendsCompare = Integer.compare(o.getFriends().size(), friends.size());

        if (friendsCompare == 0) {
            return o.getUsername().compareTo(username);
        }

        return friendsCompare;
    }

    @Override
    public String toString() {
        return username;
    }
}
