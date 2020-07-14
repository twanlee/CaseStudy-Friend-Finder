package com.friend.finder.controllers.normal_controller;

import com.friend.finder.models.Account;
import com.friend.finder.models.FriendRequest;
import com.friend.finder.models.Profile;
import com.friend.finder.services.AccountService;
import com.friend.finder.services.FriendRequestService;
import com.friend.finder.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
public class FriendController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private ProfileService profileService;

    @PostMapping("/app/search-friend")
    public ModelAndView addFriend(@ModelAttribute("username") String username, Principal principal){
        List<Profile> profileList = profileService.findAllByFirstNameContainingOrLastNameContaining(username, username);
        List<Account> resultList = new ArrayList<>();
        if (!profileList.isEmpty() && profileList != null) {
            for (Profile profile : profileList) {
                resultList.add(profile.getAccount());
            }
        }

        List<Account> friends = new ArrayList<>();
        List<Account> noneFriends = new ArrayList<>();

        Account currentAccount = accountService.findAccountByUserName(principal.getName());
        ModelAndView modelAndView = new ModelAndView("friends-searching");
        for (int i=0; i<resultList.size(); i++) {
            boolean isFriend = accountService.checkFriend(currentAccount, resultList.get(i));
            if (isFriend) friends.add(resultList.get(i));
            else noneFriends.add(resultList.get(i));
        }
        modelAndView.addObject("account",currentAccount);
        modelAndView.addObject("friends",friends);
        modelAndView.addObject("noneFriends",noneFriends);
        return modelAndView;
    }


    @GetMapping("/app/my-friends")
    public ModelAndView getFriendsList(Principal principal, @PageableDefault(size = 8) Pageable pageable){
        Account account = accountService.findAccountByUserName(principal.getName());
        List<Account> friends = account.getFriends();
        Page<Account> friendList = new PageImpl<>(friends, pageable, friends.size());

        ModelAndView modelAndView = new ModelAndView("my-friends");
        modelAndView.addObject("account", account);
        modelAndView.addObject("list", friendList);
        return modelAndView;
    }
}
