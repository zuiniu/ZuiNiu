package com.zuiniuwang.android.guardthief.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
     
/**
 * 邮件验证类
 * @author guoruiliang
 *
 */
public class MyAuthenticator extends Authenticator{   
    String userName=null;   
    String password=null;   
        
    public MyAuthenticator(){   
    }   
    public MyAuthenticator(String username, String password) {    
        this.userName = username;    
        this.password = password;    
    }    
    protected PasswordAuthentication getPasswordAuthentication(){   
        return new PasswordAuthentication(userName, password);   
    }   
}   
   