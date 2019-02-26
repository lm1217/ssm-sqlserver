package com.iyeed.core.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Calendar;
import java.util.Properties;

public class MailSender {

    private static MailSender sender = null;


    public static MailSender getInstance() {
        if (sender == null) {
            sender = new MailSender();
        }
        return sender;
    }

    /**
     * 以文本格式发送邮件
     *
     * @param mailInfo 邮件信息
     * @param mailType 邮件类型 1-error；2-warning；3-notify；
     * @return
     * @throws Exception
     */
    public boolean sendTextMail(MailInfo mailInfo, int mailType) throws Exception {

        // 需要身份认证，创建一个密码验证器
        MailAuthenticator authenticator = new MailAuthenticator(mailInfo.getUsername(), mailInfo.getPassword());

        Properties prop = mailInfo.getProperties();
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(prop, authenticator);

        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getSenderMail());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);

            // 创建邮件的接收者地址 to：发送；cc：抄送
            Address[][] maillToArr = getMailToAddress(mailInfo, mailType);

            // 设置邮件消息的接收者，发送，抄送
            if (maillToArr != null && maillToArr[0] != null && maillToArr[0].length > 0) {
                mailMessage.setRecipients(Message.RecipientType.TO, maillToArr[0]);
            }
            if (maillToArr != null && maillToArr[1] != null && maillToArr[1].length > 0) {
                mailMessage.setRecipients(Message.RecipientType.CC, maillToArr[1]);
            }

            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(Calendar.getInstance().getTime());
            // 设置邮件消息的主要内容
            mailMessage.setText(mailInfo.getContent());

            // 发送邮件
            Transport.send(mailMessage);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 以HTML格式发送邮件
     *
     * @param mailInfo 邮件信息
     * @param mailType 邮件类型 1-error；2-warning；（默认）3-notify；
     * @return
     * @throws Exception
     */
    public boolean sendHtmlMail(MailInfo mailInfo, int mailType) throws Exception {

        // 需要身份认证，创建一个密码验证器
        MailAuthenticator authenticator = new MailAuthenticator(mailInfo.getUsername(), mailInfo.getPassword());

        Properties prop = mailInfo.getProperties();
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(prop, authenticator);


        // 根据session创建一个邮件消息
        Message mailMessage = new MimeMessage(sendMailSession);
        // 创建邮件发送者地址
        Address from = new InternetAddress(mailInfo.getSenderMail());
        // 设置邮件消息的发送者
        mailMessage.setFrom(from);

        // 创建邮件的接收者地址 to：发送；cc：抄送
        Address[][] maillToArr = getMailToAddress(mailInfo, mailType);

        // 设置邮件消息的接收者，发送，抄送
        if (maillToArr != null && maillToArr[0] != null && maillToArr[0].length > 0) {
            mailMessage.setRecipients(Message.RecipientType.TO, maillToArr[0]);
        }
        if (maillToArr != null && maillToArr[1] != null && maillToArr[1].length > 0) {
            mailMessage.setRecipients(Message.RecipientType.CC, maillToArr[1]);
        }

        // 设置邮件消息的主题
        mailMessage.setSubject(MimeUtility.encodeText(mailInfo.getSubject(),"UTF-8", "B"));
        // 设置邮件消息发送的时间
        mailMessage.setSentDate(Calendar.getInstance().getTime());

        // MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart multiPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart bodyPart = new MimeBodyPart();
        // 设置html邮件消息内容
        bodyPart.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
        multiPart.addBodyPart(bodyPart);

        //添加附件
        if (mailInfo.getAttachFileNames().length != 0) {
            for (String attachFile : mailInfo.getAttachFileNames()) {
                bodyPart = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(attachFile); //得到数据源
                bodyPart.setDataHandler(new DataHandler(fds)); //得到附件本身并放入BodyPart
                bodyPart.setFileName(MimeUtility.encodeText(fds.getName()));  //得到文件名并编码（防止中文文件名乱码）同样放入BodyPart
                multiPart.addBodyPart(bodyPart);
            }
        }

        // 设置邮件消息的主要内容
        mailMessage.setContent(multiPart);

        // 发送邮件
        Transport.send(mailMessage);
        return true;
    }

    /**
     * 创建发送邮件列表地址对象
     *
     * @param mailInfo 邮件信息
     * @param mailType 邮件类型 1-error；2-warning；（默认）3-notify；
     * @return Address[0]：发送地址数组；Address[1]：抄送地址数组
     */
    private Address[][] getMailToAddress(MailInfo mailInfo, int mailType) throws AddressException {
        Address[] toAdds = null;
        Address[] ccAdds = null;

        String[] toMails = mailInfo.getNotifyTo().split(";");
        toAdds = new InternetAddress[toMails.length];
        for (int index = 0; index < toMails.length; index++) {
            toAdds[index] = new InternetAddress(toMails[index]);
        }
        String[] ccMails;
        if (mailInfo.getNotifyCc() != null) {
            ccMails = mailInfo.getNotifyCc().split(";");
            ccAdds = new InternetAddress[ccMails.length];
            for (int index = 0; index < ccMails.length; index++) {
                ccAdds[index] = new InternetAddress(ccMails[index]);
            }
        }

        Address[][] result = {toAdds, ccAdds};
        return result;
    }

    /**
     * 构建MailInfo对象
     *
     * @return
     */
    public MailInfo getMailInfo() {
        MailInfo info = new MailInfo();
        return info;
    }

    public static void main(String[] args) throws Exception {
        MailSender mailSender = MailSender.getInstance();
        MailInfo mailInfo = new MailInfo();
        mailInfo.setNotifyTo("112538201@qq.com");
        mailInfo.setSubject("[Tester Control Tower]库存初始化审批");
        String strHtml = "<html>尊敬的用户您好：<br/>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;门店“上海梅龙镇伊势丹”，店号：16，品牌：la mer 正在做库存初始化，需要您审批，请登录<a href=\"http://demo.iyeed.com.cn:8060/tct/\" target=\"_blank\">http://demo.iyeed.com.cn:8060/tct/</a>  审批。</html>";
        mailInfo.setContent(strHtml);
        mailInfo.setAttachFileNames(new String[]{});//添加附件
        mailSender.sendHtmlMail(mailInfo, 3);
    }
}