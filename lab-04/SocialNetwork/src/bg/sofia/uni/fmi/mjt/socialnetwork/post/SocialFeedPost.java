package bg.sofia.uni.fmi.mjt.socialnetwork.post;

import bg.sofia.uni.fmi.mjt.socialnetwork.profile.DefaultUserProfile;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class SocialFeedPost implements Post {
    private final String uniqueId;
    private final UserProfile author;
    private final LocalDateTime publishedOn;
    private final String content;
    private final Map<ReactionType, Set<UserProfile>> reactions;

    public SocialFeedPost(UserProfile author, String content) {
        uniqueId = UUID.randomUUID().toString();
        publishedOn = LocalDateTime.now();

        reactions = new EnumMap<>(ReactionType.class);

        this.author = author;
        this.content = content;
    }

    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    @Override
    public UserProfile getAuthor() {
        return author;
    }

    @Override
    public LocalDateTime getPublishedOn() {
        return publishedOn;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean addReaction(UserProfile userProfile, ReactionType reactionType) {
        if (userProfile == null) {
            throw new IllegalArgumentException("The user profile is null");
        }

        if (reactionType == null) {
            throw new IllegalArgumentException("The reaction type is null");
        }

        boolean isFound = false;
        Iterator<Map.Entry<ReactionType, Set<UserProfile>>> iterator = reactions.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<ReactionType, Set<UserProfile>> cur = iterator.next();

            isFound = cur.getValue().contains(userProfile);

            cur.getValue().remove(userProfile);

            if (cur.getValue().isEmpty()) {
                iterator.remove();
            }
        }

        reactions.putIfAbsent(reactionType, new HashSet<>());

        reactions.get(reactionType).add(userProfile);

        return !isFound;
    }

    @Override
    public boolean removeReaction(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("The user profile is null");
        }

        boolean isRemoved = false;

        for (Map.Entry<ReactionType, Set<UserProfile>> userProfiles : reactions.entrySet()) {
            isRemoved = userProfiles.getValue().remove(userProfile);

            if (userProfiles.getValue().isEmpty()) {
                reactions.remove(userProfiles.getKey());
            }
        }

        return isRemoved;
    }

    @Override
    public Map<ReactionType, Set<UserProfile>> getAllReactions() {

        for (Map.Entry<ReactionType, Set<UserProfile>> entries : reactions.entrySet()) {
            if (entries.getValue() == null || entries.getKey() == null) {
                reactions.remove(entries.getKey());
            }
        }

        return Map.copyOf(reactions);
    }

    @Override
    public int getReactionCount(ReactionType reactionType) {
        if (reactionType == null) {
            throw new IllegalArgumentException("The reaction type is null");
        }

        if (!reactions.containsKey(reactionType)) {
            return 0;
        }

        return reactions.get(reactionType).size();
    }

    @Override
    public int totalReactionsCount() {
        int reactionsCount = 0;

        for (Set<UserProfile> profiles : reactions.values()) {
            reactionsCount += profiles.size();
        }

        return reactionsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocialFeedPost that = (SocialFeedPost) o;
        return Objects.equals(uniqueId, that.uniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId);
    }

    public static void main(String[] args) {
        UserProfile user1 = new DefaultUserProfile("kaka");
        UserProfile user2 = new DefaultUserProfile("aaka");
        UserProfile user3 = new DefaultUserProfile("laka");

        user1.addFriend(user2);

        user2.unfriend(user1);

        for (UserProfile up : user2.getFriends()) {
            System.out.println(up);
        }

        Post post1 = new SocialFeedPost(user1, "I am user1");

        boolean isReact;
        isReact = post1.addReaction(user1, ReactionType.LAUGH);
        System.out.println(isReact);
        isReact = post1.addReaction(user1, ReactionType.LIKE);
        System.out.println(isReact);

    }
}
