package com.zuiniuwang.android.guardthief.config;

import com.zuiniuwang.android.guardthief.util.JsonUtil;


public class UserConfiglBean {
    public String userName;
    public String basicTag;
    public String mailTag;

    public UserConfiglBean() {

    }

    public UserConfiglBean(String userName, String tag, String smtp         ) {
        this.userName = userName;
        this.basicTag = tag;
        this.mailTag = smtp;

    }

    public static UserConfiglBean fromJson(String json) {
        return JsonUtil.fromJson(UserConfiglBean.class, json);
    }

    public String toJson() {
        return JsonUtil.toJson(UserConfiglBean.class, this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((userName == null) ? 0 : userName.hashCode());
        result = prime * result
                + ((basicTag == null) ? 0 : basicTag.hashCode());
        result = prime * result + ((mailTag == null) ? 0 : mailTag.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserConfiglBean other = (UserConfiglBean) obj;
        return (userName != null ? userName.equals(other.userName)
                : other.userName == null)
                && (basicTag != null ? basicTag.equals(other.basicTag)
                        : other.basicTag == null)
                && (mailTag != null ? mailTag.equals(other.mailTag)
                        : other.mailTag == null)
              ;
    }

    @Override
    public String toString() {
        return "UserConfiglBean [username=" + userName + ", basicTag=" + basicTag
                + ", mailTag=" + mailTag +  "]";
    }
}
