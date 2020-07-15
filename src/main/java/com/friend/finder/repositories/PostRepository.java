package com.friend.finder.repositories;

import com.friend.finder.models.Account;
import com.friend.finder.models.Newsfeed;
import com.friend.finder.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post,Long> {
    Page<Post> getPostsByNewsfeedSetOrderByPostTimeDesc(Newsfeed newsfeed, Pageable pageable);
    Page<Post> getPostsByAccountOrderByPostTimeDesc(Account account, Pageable pageable);
    List<Post> findAllByAccount(Account account);
}
