package com.zuiniuwang.android.guardthief.config;

import java.util.ArrayList;
import java.util.List;

import com.zuiniuwang.android.guardthief.util.JsonUtil;


public class MailBean {
    public String password;
    public String smtp;
    public String emailSuffix;
    public List<String> userNames=new ArrayList<String>();
    
    
    public MailBean() {

    }

    public MailBean(List<String> userNames, String password, String smtp,
            String emailSuffix) {
        this.userNames = userNames;
        this.password = password;
        this.smtp = smtp;
        this.emailSuffix = emailSuffix;

    }

    public static MailBean fromJson(String json) {
        return JsonUtil.fromJson(MailBean.class, json);
    }

    public String toJson() {
        return JsonUtil.toJson(MailBean.class, this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((userNames == null) ? 0 : userNames.hashCode());
        result = prime * result
                + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((smtp == null) ? 0 : smtp.hashCode());
        result = prime * result
                + ((emailSuffix == null) ? 0 : emailSuffix.hashCode());

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
        MailBean other = (MailBean) obj;
        return (userNames != null ? userNames.equals(other.userNames)
                : other.userNames == null)
                && (password != null ? password.equals(other.password)
                        : other.password == null)
                && (smtp != null ? smtp.equals(other.smtp)
                        : other.smtp == null)
                && (emailSuffix != null ? emailSuffix
                        .equals(other.emailSuffix)
                        : other.emailSuffix == null);
    }

    @Override
    public String toString() {
        return "MailBean [usernames=" + userNames + ", password=" + password
                + ", smtp=" + smtp + ", emailSuffix=" + emailSuffix + "]";
    }
}
