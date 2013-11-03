package com.zuiniuwang.android.guardthief.mail;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;
import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.bean.PhotoBean;
import com.zuiniuwang.android.guardthief.bll.CameraStrategy;
import com.zuiniuwang.android.guardthief.bll.TimeStrategy;
import com.zuiniuwang.android.guardthief.util.LogUtil;

/**
 * 简单邮件发送器
 * 
 * @author guoruiliang
 * 
 */
public class SimpleMailSender {

	public String className = getClass().getSimpleName();
	private MailSenderInfo mailInfo;
	private Session sendMailSession;

	public SimpleMailSender(MailSenderInfo mailInfo) {
		this.mailInfo = mailInfo;
		this.sendMailSession = getSession();
	}

	private Session getSession() {
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}

		clearSession();// 清空session

		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);

		return sendMailSession;
	}

	/** 设置Session defaultSession为null,因为是单例，所以这样设置后更换帐户才可以有效， */
	private void clearSession() {
		try {
			Field sessionField = Session.class
					.getDeclaredField("defaultSession");
			sessionField.setAccessible(true);
			sessionField.set(null, null);
		} catch (Exception e) {
		}

	}

	/**
	 * 发送普通邮件
	 */
	public boolean sendTextMail() {
		try {
			Message mailMessage = getMailMessage();
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {

			LogUtil.logE(className + " sendMail error:" + ex.getMessage());
		}
		return false;
	}

	private Message getMailMessage() throws AddressException,
			MessagingException {
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		// 创建邮件发送者地址
		Address from = new InternetAddress(mailInfo.getFromAddress());
		// 设置邮件消息的发送者
		mailMessage.setFrom(from);
		// 创建邮件的接收者地址，并设置到邮件消息中
		Address to = new InternetAddress(mailInfo.getToAddress());
		mailMessage.setRecipient(Message.RecipientType.TO, to);

		if (!TextUtils.isEmpty(mailInfo.getCcAddress())) {
			mailMessage.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(mailInfo.getCcAddress()));
		}
		// 设置抄送人
		if (!TextUtils.isEmpty(mailInfo.getBccAddress())) {
			// 设置暗送人
			mailMessage.setRecipients(Message.RecipientType.BCC,
					InternetAddress.parse(mailInfo.getBccAddress()));
		}

		// 设置邮件消息的主题
		mailMessage.setSubject(mailInfo.getSubject());
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());

		// 设置邮件消息的主要内容
		String mailContent = mailInfo.getContent();
		mailMessage.setText(mailContent);
		return mailMessage;
	}

	/**
	 * 以HTML格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件信息
	 */
	public boolean sendHtmlMail() {
		Message mailMessage = null;
		// 判断是否需要身份认证
		try {
			// 根据session创建一个邮件消息
			mailMessage = getMailMessage();
			// 将邮件中各个部分组合到一个"mixed"型的 MimeMultipart 对象
			Multipart mainPart = new MimeMultipart("mixed");

			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			String text = getHtmlTextFromFile();
			html.setContent(text, "text/html; charset=utf-8");
			mainPart.addBodyPart(html);

			MimeBodyPart filePart = createAllFileAttach();

			mainPart.addBodyPart(filePart);

			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);

			mailMessage.saveChanges();
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (Exception ex) {
			LogUtil.logE(className + " sendHtmlMail error:"
					+ String.valueOf(ex));
			try {
				if (mailInfo != null) {

					String date = new SimpleDateFormat(
							"yyyy年MM月dd日 HH时mm分ss秒 E ").format(System
							.currentTimeMillis());
					MobclickAgent.reportError(
							GuardApplication.appContext,
							String.valueOf(ex) + " user:"
									+ mailInfo.getUserName() + " date:" + date);
				}

			} catch (Exception e) {
			}

		}
		return false;
	}

	/** 获取正文内容 */
	private String getHtmlTextFromFile() {
		List<PhotoBean> photos = mailInfo.getPhotoBeans();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<font color=red>最牛一号" + Const.VERSION + "拍到的照片:"
				+ String.valueOf(photos.size()) + "张 </font> <br>");
		int count = 1;
		for (PhotoBean photo : photos) {
			if (!TextUtils.isEmpty(photo.fileUrl)) {
				buffer.append("序号：" + count + "<br>");
				buffer.append("拍摄时间："
						+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ")
								.format(photo.time) + "<br>");
				buffer.append("拍摄地址：" + photo.address + "<br>");
				buffer.append("拍摄时机："
						+ TimeStrategy
								.getStrategyDescById(photo.timeStrategyId)
						+ "<br>");
				buffer.append("拍摄策略："
						+ CameraStrategy
								.getStrategyDescById(photo.cameraStragegyId)
						+ "<br>");
				buffer.append("文件名称："
						+ photo.fileUrl.substring(photo.fileUrl
								.lastIndexOf("/") + 1) + "<br>");


				count++;
			}

		}

		return buffer.toString();
	}

	/** 获取所有附件的部分 */
	private MimeBodyPart createAllFileAttach() throws MessagingException,
			Exception {
		MimeBodyPart allFileBodyPart = new MimeBodyPart();

		Multipart multipart = new MimeMultipart();
		// 设置邮件的文本内容
		// BodyPart contentPart = new MimeBodyPart();
		// contentPart.setText(mailInfo.getContent());
		// multipart.addBodyPart(contentPart);

		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		List<PhotoBean> photos = mailInfo.getPhotoBeans();
		for (PhotoBean photo : photos) {
			if (!TextUtils.isEmpty(photo.fileUrl)) {
				BodyPart bodyPart = createAttachment(photo.fileUrl);
				multipart.addBodyPart(bodyPart);
			}

		}

		allFileBodyPart.setContent(multipart);
		return allFileBodyPart;
	}

	/**
	 * 根据传入的文件路径创建附件并返回
	 */
	private MimeBodyPart createAttachment(String file)
			throws MessagingException, Exception {
		MimeBodyPart bodyPart = new MimeBodyPart();
		FileDataSource dataSource = new FileDataSource(file);

		bodyPart.setDataHandler(new DataHandler(dataSource));
		bodyPart.setFileName(MimeUtility.encodeText(dataSource.getName()));
		bodyPart.setContentID(file);
		return bodyPart;
	}

}
