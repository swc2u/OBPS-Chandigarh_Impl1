/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 *
 */

package org.egov.infra.notification.service;

import org.egov.infra.exception.ApplicationRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.sendgrid.Attachments;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

@Service
public class EmailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendEmail(String toEmail, String subject, String mailBody) {
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setFrom(mailSender.getUsername());
//        mailMessage.setTo(toEmail);
//        mailMessage.setSubject(subject);
//        mailMessage.setText(mailBody);
//        mailSender.send(mailMessage);
        try {
        	mail(toEmail, subject, mailBody);
        }catch (Exception e) {
        	e.printStackTrace();
		}
    }

    public void mail(String toEmail, String subject, String mailBody) throws IOException {
	    Email from = new Email(mailSender.getUsername());
	    Email to = new Email(toEmail);
		Content content = new Content("text/html", mailBody);
	    Mail mail = new Mail(from, subject, to, content);
	   
	    SendGrid sg = new SendGrid(mailSender.getPassword());
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = sg.api(request);
	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	    } catch (IOException ex) {
	      throw ex;
	    }
	  }
    
    
    public void sendEmail(String toEmail, String subject, String mailBody, String fileType, String fileName,
                          byte[] attachment) {
//        MimeMessage message = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
//            mimeMessageHelper.setFrom(mailSender.getUsername());
//            mimeMessageHelper.setTo(toEmail);
//            mimeMessageHelper.setSubject(subject);
//            mimeMessageHelper.setText(mailBody);
//            ByteArrayDataSource source = new ByteArrayDataSource(attachment, fileType);
//            mimeMessageHelper.addAttachment(fileName, source);
//        } catch (MessagingException | IllegalArgumentException e) {
//            throw new ApplicationRuntimeException("Error occurred while sending email with attachment", e);
//        }
//        mailSender.send(message);
    	sendEmailNew(toEmail, subject, mailBody, fileType, fileName, attachment);
    }
    
    public void sendEmailNew(String toEmail, String subject, String mailBody, String fileType, String fileName,
            byte[] attachment) {
    	
    	Email from = new Email(mailSender.getUsername());
	    Email to = new Email(toEmail);
		Content content = new Content("text/html", mailBody);
	    Mail mail = new Mail(from, subject, to, content);
	    
	    Attachments attachments = new Attachments();
	    attachments.setContent(attachment.toString());
	    attachments.setType(fileType);
	    attachments.setFilename(fileName);
	 //   attachments.setDisposition("attachment");
	    //attachments.setContentId("Balance Sheet");
	    mail.addAttachments(attachments);
	    
	   
	    SendGrid sg = new SendGrid(mailSender.getPassword());
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = sg.api(request);
	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	    } catch (IOException ex) {
	      ex.printStackTrace();
	    }
    	
    }
}
