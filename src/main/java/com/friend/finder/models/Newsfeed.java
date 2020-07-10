package com.friend.finder.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Newsfeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Account account;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "newsfeed_post",
                joinColumns = @JoinColumn(name = "newsfeed_id"),
                inverseJoinColumns = @JoinColumn(name = "post_id"))
    private Set<Post> postSet;
}