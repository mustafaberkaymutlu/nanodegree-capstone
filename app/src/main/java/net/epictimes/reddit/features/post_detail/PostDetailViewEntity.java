package net.epictimes.reddit.features.post_detail;

import net.epictimes.reddit.data.model.post.Post;

class PostDetailViewEntity {

    private final Post post;

    PostDetailViewEntity(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

}
