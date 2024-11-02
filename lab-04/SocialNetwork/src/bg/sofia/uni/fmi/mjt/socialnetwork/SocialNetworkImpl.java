package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.SocialFeedPost;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.Interest;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SocialNetworkImpl implements SocialNetwork {
    private final Set<UserProfile> userProfiles;
    private final Map<UserProfile, Set<Post>> posts;

    public SocialNetworkImpl() {
        userProfiles = new HashSet<>();
        posts = new HashMap<>();
    }

    @Override
    public void registerUser(UserProfile userProfile) throws UserRegistrationException {
        checkIsNull(userProfile);

        if (userProfiles.contains(userProfile)) {
            throw new UserRegistrationException("The user profile is already registered");
        }

        userProfiles.add(userProfile);
    }

    @Override
    public Set<UserProfile> getAllUsers() {
        return Set.copyOf(userProfiles);
    }

    @Override
    public Post post(UserProfile userProfile, String content) throws UserRegistrationException {
        checkIsNull(content);
        checkIsEmpty(content);

        checkIsNull(userProfile);
        checkUserIsNotRegistered(userProfile);

        Post post = new SocialFeedPost(userProfile, content);

        posts.putIfAbsent(userProfile, new HashSet<>());
        posts.get(userProfile).add(post);

        return post;
    }

    @Override
    public Collection<Post> getPosts() {
        Set<Post> allPosts = new HashSet<>();

        for (Set<Post> p : posts.values()) {
            allPosts.addAll(p);
        }

        return Set.copyOf(allPosts);
    }

    @Override
    public Set<UserProfile> getReachedUsers(Post post) {
        checkIsNull(post);

        UserProfile start = post.getAuthor();

        Set<UserProfile> visited = new HashSet<>();

        Set<UserProfile> result = new HashSet<>();

        dfs(start, visited, result);

        return result;
    }

    private static void dfs(UserProfile cur, Set<UserProfile> visited, Set<UserProfile> result) {
        if (visited.contains(cur)) {
            return;
        }

        visited.add(cur);

        for (UserProfile up : cur.getFriends()) {

            if (!visited.contains(up)) {
                Set<Interest> commonInterests = new HashSet<>(up.getInterests());
                commonInterests.retainAll(cur.getInterests());

                if (!commonInterests.isEmpty()) {
                    result.add(up);
                }

                dfs(up, visited, result);
            }

        }
    }

    @Override
    public Set<UserProfile> getMutualFriends(UserProfile userProfile1, UserProfile userProfile2)
            throws UserRegistrationException {

        checkIsNull(userProfile1);
        checkIsNull(userProfile2);

        checkUserIsNotRegistered(userProfile1);
        checkUserIsNotRegistered(userProfile2);

        Set<UserProfile> mutualFriends = new HashSet<>(userProfile1.getFriends());

        mutualFriends.retainAll(userProfile2.getFriends());

        return Collections.unmodifiableSet(mutualFriends);
    }

    @Override
    public SortedSet<UserProfile> getAllProfilesSortedByFriendsCount() {
        SortedSet<UserProfile> sorted = new TreeSet<>();

        sorted.addAll(userProfiles);

        return Collections.unmodifiableSortedSet(sorted);
    }

    private static void checkIsNull(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("The user profile is null");
        }
    }

    private static void checkIsNull(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("The provided post is null");
        }
    }

    private static void checkIsNull(String s) {
        if (s == null) {
            throw new IllegalArgumentException("The content is null");
        }
    }

    private static void checkIsEmpty(String s) {
        if (s.isEmpty() || s.isBlank()) {
            throw new IllegalArgumentException("The content is empty");
        }
    }

    private void checkUserIsNotRegistered(UserProfile userProfile) throws UserRegistrationException {
        if (!userProfiles.contains(userProfile)) {
            throw new UserRegistrationException("The user profile is not registered");
        }
    }
}
