package com.zuiniuwang.android.guardthief.mail;

import java.util.List;
import java.util.Random;

import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.bean.PhotoBean;
import com.zuiniuwang.android.guardthief.config.MailBean;
import com.zuiniuwang.android.guardthief.util.LogUtil;

/**
 * 邮件管理类
 * 
 * @author guoruiliang
 * 
 */
public class MailManager {
	private SimpleMailSender mailSender;
	private MailSenderInfo mailInfo = new MailSenderInfo();

	/**
	 * 构造函数
	 * 
	 * @param address
	 *            地址
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @param photos
	 *            附件
	 */
	public MailManager(String address, String subject, String content,
			List<PhotoBean> photos, MailBean mailBean) {
		intiMail(mailBean);

		// 收件地址
		mailInfo.setToAddress(address);
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		mailInfo.setPhotoBeans(photos);
		mailSender = new SimpleMailSender(mailInfo);
	}

	public void setCcAddress(String cc) {
		mailInfo.setCcAddress(cc);
	}

	public void setBccAddress(String bcc) {
		mailInfo.setBccAddress(bcc);
	}

	private void intiMail(MailBean mailBean) {
		mailInfo.setMailServerHost(mailBean.smtp);
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		// 设置发件邮箱及密码

//		if (mailBean.userNames != null) {
//			
//			String username = "";
//			if (mailBean.userNames.size() > 0) {
//				username = mailBean.userNames.get(new Random()
//						.nextInt(mailBean.userNames.size()));
//			}
//			mailInfo.setUserName(username);
//			String pass = GuardApplication.decrypt(mailBean.password);// 解密
//			mailInfo.setPassword(pass);
//			mailInfo.setFromAddress(username + mailBean.emailSuffix);
//			
//			LogUtil.logI("intiMail:"+" userName:"+username+" mailBean:"+mailBean);
//		}
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setUserName("zuiniuyihao100");
		mailInfo.setPassword("abcd1234");
		mailInfo.setFromAddress("zuiniuyihao100@163.com");
	}

	/**
	 * 发送邮件
	 */
	public boolean sendHtmlMail() {
		return mailSender.sendHtmlMail();
	}

	public boolean sendTextMail() {
		return mailSender.sendTextMail();
	}

}
