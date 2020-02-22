package com.bestaone.hiauth.domain;

import com.bestaone.hiauth.core.entity.BaseEntity;
import com.bestaone.hiauth.domain.enums.AccountType;

import java.util.Date;

public class Account extends BaseEntity<Long> {

    private Long id;
    private User user;
    private AccountType type;
    private String openid;
    private String accessToken;
    private String refreshToken;
    private Date expireTime;
    private Date createTime;
    private Date lastLoginTime;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
