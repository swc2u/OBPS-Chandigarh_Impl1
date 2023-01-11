/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
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
 */
package org.egov.bpa.transaction.service.messaging;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CANCELLED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REGISTERED;
import static org.egov.bpa.utils.BpaConstants.BUILDINGPERMITFILENAME;
import static org.egov.bpa.utils.BpaConstants.CREATEDLETTERTOPARTY;
import static org.egov.bpa.utils.BpaConstants.DEMANDNOCFILENAME;
import static org.egov.bpa.utils.BpaConstants.EGMODULE_NAME;
import static org.egov.bpa.utils.BpaConstants.NO;
import static org.egov.bpa.utils.BpaConstants.SENDEMAILFORBPA;
import static org.egov.bpa.utils.BpaConstants.SENDSMSFORBPA;
import static org.egov.bpa.utils.BpaConstants.SMSEMAILTYPELETTERTOPARTY;
import static org.egov.bpa.utils.BpaConstants.SMSEMAILTYPENEWBPAREGISTERED;
import static org.egov.bpa.utils.BpaConstants.YES;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import org.egov.bpa.master.entity.StakeHolder;
import org.egov.bpa.master.entity.enums.StakeHolderStatus;
import org.egov.bpa.transaction.entity.Applicant;
import org.egov.bpa.transaction.entity.ApplicationStakeHolder;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaAppointmentSchedule;
import org.egov.bpa.transaction.entity.BpaNocApplication;
import org.egov.bpa.transaction.entity.InspectionApplication;
import org.egov.bpa.transaction.entity.InspectionAppointmentSchedule;
import org.egov.bpa.transaction.entity.PermitNocApplication;
import org.egov.bpa.transaction.entity.SlotApplication;
import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.bpa.transaction.entity.oc.OccupancyNocApplication;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.collection.integration.models.BillReceiptInfo;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.notification.service.NotificationService;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BPASmsAndEmailService {
    private static final String MSG_KEY_SMS_STAKEHOLDER_NEW = "msg.newstakeholder.sms";
    private static final String SUBJECT_KEY_EMAIL_STAKEHOLDER_NEW = "msg.newstakeholder.email.subject";
    private static final String BODY_KEY_EMAIL_STAKEHOLDER_NEW = "msg.newstakeholder.email.body";
    private static final String MSG_KEY_SMS_BPA_APPLN_NEW = "msg.bpa.newappln.sms";
    private static final String MSG_KEY_SMS_BPA_APPLN_NEW_PWD = "msg.bpa.newappln.sms.pwd";
    private static final String SUBJECT_KEY_EMAIL_BPA_APPLN_NEW = "msg.bpa.newappln.email.subject";
    private static final String BODY_KEY_EMAIL_BPA_APPLN_NEW = "msg.bpa.newappln.email.body";
    private static final String BODY_KEY_EMAIL_BPA_APPLN_NEW_PAWD = "msg.bpa.newappln.email.body.pawd";
    private static final String BODY_KEY_EMAIL_BPA_APPLN_NEW_PWD = "msg.bpa.newappln.email.body.pwd";
    private static final String BODY_KEY_EMAIL_BPA_APPLN_STC_FALSE = "msg.bpa.newappln.email.body.stc.false";
    private static final String BODY_KEY_EMAIL_BPA_APPLN_STC_SUBMIT = "msg.bpa.newappln.email.body.stc.submit";
    private static final String MSG_KEY_SMS_BPA_APPLN_STC_TRUE = "msg.bpa.newappln.sms.stc.true";
    private static final String MSG_KEY_SMS_BPA_APPLN_STC_SUBMIT = "msg.bpa.newappln.sms.stc.submit";
    private static final String MSG_KEY_SMS_BPA_APPLN_STC_FALSE = "msg.bpa.newappln.sms.stc.false";

    private static final String MSG_KEY_SMS_BPA_DOC_SCRUTINY = "msg.bpa.doc.scruty.schedule.sms";
    private static final String BODY_KEY_EMAIL_BPA_DOC_SCRUTINY = "msg.bpa.doc.scruty.schedule.email.body";
    private static final String MSG_KEY_SMS_BPA_DOC_SCRUTINY_RESCHE = "msg.bpa.doc.scruty.reschedule.sms";
    private static final String BODY_KEY_EMAIL_BPA_DOC_SCRUTINY_RESCHE = "msg.bpa.doc.scruty.reschedule.email.body";
    private static final String MSG_KEY_SMS_BPA_DOC_SCRUTINY_PENDING_FOR_RESCHEDULING = "msg.bpa.doc.scruty.reschedule.pending.sms";
    private static final String BODY_KEY_EMAIL_BPA_DOC_SCRUTINY_PENDING_FOR_RESCHEDULING = "msg.bpa.doc.scruty.reschedule.pending.email.body";
    private static final String MSG_KEY_SMS_BPA_DOC_SCRUTINY_CANCELLED = "msg.bpa.doc.scruty.cancel.sms";
    private static final String BODY_KEY_EMAIL_BPA_DOC_SCRUTINY_CANCELLED = "msg.bpa.doc.scruty.cancel.email.body";
    private static final String SUB_KEY_EMAIL_BPA_DOCUMENT_SCRUTINY = "msg.bpa.doc.scruty.email.sub";
    private static final String MSG_KEY_SMS_BPA_FIELD_INS = "msg.bpa.field.ins.schedule.sms";
    private static final String SUBJECT_KEY_EMAIL_BPA_FIELD_INS = "msg.bpa.field.ins.schedule.email.subject";
    private static final String BODY_KEY_EMAIL_BPA_FIELD_INS = "msg.bpa.field.ins.schedule.email.body";
    private static final String MSG_KEY_SMS_BPA_FIELD_INS_RESCHE = "msg.bpa.field.ins.reschedule.sms";
    private static final String SUBJECT_KEY_EMAIL_BPA_FIELD_INS_RESCHE = "msg.bpa.field.ins.reschedule.email.subject";
    private static final String BODY_KEY_EMAIL_BPA_FIELD_INS_RESCHE = "msg.bpa.field.ins.reschedule.email.body";

    private static final String MSG_KEY_SMS_LETTERTOPARTY = "msg.bpa.lettertoparty.sms";
    private static final String SUBJECT_KEY_EMAIL_LETTERTOPARTY = "msg.bpa.lettertoparty.email.subject";
    private static final String BODY_KEY_EMAIL_LETTERTOPARTY = "msg.bpa.lettertoparty.email.body";
    private static final String MSG_KEY_SMS_CANCELL_APPLN = "msg.bpa.cancel.appln.sms";
    private static final String SUBJECT_KEY_EMAIL_CANCELL_APPLN = "msg.bpa.cancel.appln.email.subject";
    private static final String BODY_KEY_EMAIL_CANCELL_APPLN = "msg.bpa.cancel.appln.email.body";
    private static final String SMS_STK_CTZ = "msg.newstakeholder.sms.citizen";
    private static final String EMLB_STK_CTZ = "msg.newstakeholder.email.body.citizen";
    private static final String EMLS_STK_CTZ = "msg.newstakeholder.email.subject.citizen";
    private static final String SMS_STK_RJCT = "msg.stakeholder.rejection.sms";
    private static final String EMLB_STK_RJCT = "msg.stakeholder.rejection.email.body";
    private static final String EMLS_STK_RJCT = "msg.stakeholder.rejection.email.subject";
    private static final String SMS_KEY_APRVL = "msg.application.approval.sms";
    private static final String EMLB_KEY_APRVL = "msg.application.approval.email.body";

    private static final String SMS_KEY_APRVL_WITHOUT_PERMITFEE = "msg.application.approval.sms.without.permitfee";
    private static final String EMLB_KEY_APRVL_WITHOUT_PERMITFEE = "msg.application.approval.email.body.without.permitfee";

    private static final String EMLS_KEY_APRVL = "msg.application.approval.email.subject";
    private static final String EMLB_KEY_PO = "msg.permitorder.generation.email.body";
    private static final String SMS_KEY_PO = "msg.permitorder.generation.sms";
    private static final String EMLS_KEY_PO = "msg.permitorder.generation.email.subject";

    private static final String APP_PDF = "application/pdf";
    private static final String PDFEXTN = ".pdf";
    private static final String SMS_KEY_CLCTN = "msg.collection.sms";
    private static final String MSG_KEY_SMS_STAKEHOLDER_NEW_PP = "msg.newstakeholder.paymentpending.sms";
    private static final String SUBJECT_KEY_EMAIL_STAKEHOLDER_NEW_PP = "msg.newstakeholder.email.paymentpending.subject";
    private static final String BODY_KEY_EMAIL_STAKEHOLDER_NEW_PP = "msg.newstakeholder.email.paymentpending.body";
    private static final String STAKEHOLDER_PAYMENT_CONFIM_SMS = "msg.newstakeholder.paymentconfirmation.sms";
    private static final String STAKEHOLDER_PAYMENT_CONFIM_EMAIL_SUB = "msg.newstakeholder.paymentconfirmation.email.subject";
    private static final String STAKEHOLDER_PAYMENT_CONFIM_EMAIL_BODY = "msg.newstakeholder.paymentconfirmation.email.body";
    private static final String SMS_BPA_NOC_DEEMED_APPROVE = "msg.bpa.noc.deemed.approve.sms";
    private static final String SUB_BPA_NOC_DEEMED_APPROVE = "msg.bpa.noc.deemed.approve.email.sub";
    private static final String BODY_BPA_NOC_DEEMED_APPROVE = "msg.bpa.noc.deemed.approve.email.body";
    private static final String SMS_BPA_NOC_INITIATION = "msg.bpa.noc.initiation.sms";
    private static final String SUB_BPA_NOC_INITIATION = "msg.bpa.noc.initiation.email.sub";
    private static final String BODY_BPA_NOC_INITIATION = "msg.bpa.noc.initiation.email.body";
    private static final String SMS_BPA_NOC_APPROVE = "msg.bpa.noc.approve.sms";
    private static final String SUB_BPA_NOC_APPROVE = "msg.bpa.noc.approve.email.sub";
    private static final String BODY_BPA_NOC_APPROVE = "msg.bpa.noc.approve.email.body";
    private static final String SMS_BPA_NOC_REJECT = "msg.bpa.noc.reject.sms";
    private static final String SUB_BPA_NOC_REJECT = "msg.bpa.noc.reject.email.sub";
    private static final String BODY_BPA_NOC_REJECT = "msg.bpa.noc.reject.email.body";
    private static final String SMS_BPA_NOC_SEND_OBSERVATIONS = "msg.bpa.noc.send_observations.email.body";
    private static final String SUB_BPA_NOC_SEND_OBSERVATIONS = "msg.bpa.noc.send_observations.email.sub";
    private static final String BODY_BPA_NOC_SEND_OBSERVATIONS = "msg.bpa.noc.reject.email.body";
    private static final String SMS_BPA_NOC_RE_INITIATION = "msg.bpa.noc.re.initiation.sms";
    private static final String SUB_BPA_NOC_RE_INITIATION = "msg.bpa.noc.re.initiation.email.sub";
    private static final String BODY_BPA_NOC_RE_INITIATION = "msg.bpa.noc.re.initiation.email.body";

    @Autowired
    private NotificationService notificationService;
    @Autowired
    @Qualifier("parentMessageSource")
    private MessageSource bpaMessageSource;
    @Autowired
    private AppConfigValueService appConfigValuesService;
    @Autowired
    private BpaUtils bpaUtils;

    public String getMunicipalityName() {
        return ApplicationThreadLocals.getMunicipalityName();
    }

    public void sendSMSForStakeHolder(final StakeHolder stakeHolder, Boolean isCitizenCrtn) {
        String message;

        if (isCitizenCrtn) {
            message = bpaMessageSource.getMessage(SMS_STK_CTZ,
                    new String[] { stakeHolder.getStakeHolderType().getName(), stakeHolder.getCode() }, null);
        } else {
            if (stakeHolder.getStatus().equals(StakeHolderStatus.PAYMENT_PENDING)) {
                message = bpaMessageSource.getMessage(MSG_KEY_SMS_STAKEHOLDER_NEW_PP,
                        new String[] { stakeHolder.getCode(), stakeHolder.getStakeHolderType().getName() }, null);

            } else {
                message = bpaMessageSource.getMessage(MSG_KEY_SMS_STAKEHOLDER_NEW, new String[] {
                        stakeHolder.getStakeHolderType().getName(), stakeHolder.getUsername(), "demo" },
                        null);
            }
        }
        if (isSmsEnabled() && stakeHolder.getMobileNumber() != null) {
            notificationService.sendSMS(stakeHolder.getMobileNumber(), message);
        }
    }

    public void sendSMSToStkHldrForRejection(final StakeHolder stakeHolder) {
        String msgKey = SMS_STK_RJCT;
        if (isSmsEnabled() && stakeHolder.getMobileNumber() != null) {
            String message = bpaMessageSource.getMessage(msgKey, new String[] { stakeHolder.getCode(),
                    stakeHolder.getStakeHolderType().getName(), stakeHolder.getComments(), getMunicipalityName() },
                    null);
            notificationService.sendSMS(stakeHolder.getMobileNumber(), message);
        }
    }

    public void sendEmailToStkHldrForRejection(StakeHolder stakeHolder) {
        String msgKeyMail = EMLB_STK_RJCT;
        String msgKeyMailSubject = EMLS_STK_RJCT;
        if (isEmailEnabled() && stakeHolder.getEmailId() != null) {
            final String message = bpaMessageSource.getMessage(msgKeyMail,
                    new String[] { stakeHolder.getName(), stakeHolder.getStakeHolderType().getName(),
                            stakeHolder.getCode(), stakeHolder.getComments(), getMunicipalityName(),
                            getMunicipalityName() },
                    null);
            final String subject = bpaMessageSource.getMessage(msgKeyMailSubject, null, null);
            notificationService.sendEmail(stakeHolder.getEmailId(), subject, message);
        }
    }

    public void sendEmailForStakeHolder(final StakeHolder stakeHolder, Boolean isCitizen) {
        String msgKeyMailSubject = "";
        String message;

        if (isCitizen) {
            msgKeyMailSubject = EMLS_STK_CTZ;
            message = bpaMessageSource
                    .getMessage(EMLB_STK_CTZ,
                            new String[] { stakeHolder.getName(),
                                    stakeHolder.getStakeHolderType().getName(), stakeHolder.getCode(),
                                    getMunicipalityName() },
                            null);
        } else {
            if (stakeHolder.getStatus().equals(StakeHolderStatus.PAYMENT_PENDING)) {
                msgKeyMailSubject = SUBJECT_KEY_EMAIL_STAKEHOLDER_NEW_PP;
                message = bpaMessageSource.getMessage(BODY_KEY_EMAIL_STAKEHOLDER_NEW_PP,
                        new String[] { stakeHolder.getName(), stakeHolder.getCode(),
                                stakeHolder.getStakeHolderType().getName(), getMunicipalityName() },
                        null);
            } else {
                msgKeyMailSubject = SUBJECT_KEY_EMAIL_STAKEHOLDER_NEW;
                message = bpaMessageSource.getMessage(BODY_KEY_EMAIL_STAKEHOLDER_NEW,
                        new String[] { stakeHolder.getName(), stakeHolder.getStakeHolderType().getName(),
                                stakeHolder.getUsername(), "demo", ApplicationThreadLocals.getDomainURL(),
                                getMunicipalityName() },
                        null);
            }
        }
        if (isEmailEnabled() && stakeHolder.getEmailId() != null) {
            final String subject = bpaMessageSource.getMessage(msgKeyMailSubject, null, null);
            notificationService.sendEmail(stakeHolder.getEmailId(), subject, message);
        }
    }

    public void sendSmsAndEmailOnBlockAndUnblock(final StakeHolder stakeHolder) {
        String msgKey = EMPTY;
        String bodyKey = EMPTY;
        String subjectKey = EMPTY;
        if (StakeHolderStatus.BLOCKED.equals(stakeHolder.getStatus())) {
            msgKey = "msg.stakeholder.block.sms";
            subjectKey = "msg.stakeholder.block.email.subject";
            bodyKey = "msg.stakeholder.block.email.body";
        } else if (StakeHolderStatus.UNBLOCKED.equals(stakeHolder.getStatus())) {
            msgKey = "msg.stakeholder.unblock.sms";
            subjectKey = "msg.stakeholder.unblock.email.subject";
            bodyKey = "msg.stakeholder.unblock.email.body";
        }
        if (isSmsEnabled() && stakeHolder.getMobileNumber() != null) {
            String message = bpaMessageSource.getMessage(msgKey, new String[] { getMunicipalityName() },
                    null);
            notificationService.sendSMS(stakeHolder.getMobileNumber(), message);
        }
        if (isEmailEnabled() && stakeHolder.getEmailId() != null) {
            final String body = bpaMessageSource.getMessage(bodyKey,
                    new String[] { stakeHolder.getName(), getMunicipalityName(), stakeHolder.getComments() },
                    null);
            final String subject = bpaMessageSource.getMessage(subjectKey, new String[] { getMunicipalityName() }, null);
            notificationService.sendEmail(stakeHolder.getEmailId(), subject, body);
        }
    }

    public void sendSMSAndEmail(final BpaApplication bpaApplication, ReportOutput reportOutput, String fileName) {
        String mobileNo;
        String email;
        String applicantName;
        String loginUserName;
        String password;
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = bpaApplication.getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant applicant = applnStakeHolder.getApplication().getOwner();
                applicantName = applicant.getName();
                email = applicant.getEmailId();
                mobileNo = applicant.getUser().getMobileNumber();
                loginUserName = applicant.getUser().getUsername();
                password = mobileNo;
                buildSmsAndEmailForBPANewAppln(bpaApplication, applicantName, mobileNo, email, loginUserName, password,
                        reportOutput, fileName);
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                applicantName = applnStakeHolder.getStakeHolder().getName();
                email = applnStakeHolder.getStakeHolder().getEmailId();
                mobileNo = applnStakeHolder.getStakeHolder().getMobileNumber();
                loginUserName = applnStakeHolder.getStakeHolder().getUsername();
                password = EMPTY;
                buildSmsAndEmailForBPANewAppln(bpaApplication, applicantName, mobileNo, email, loginUserName, password,
                        reportOutput, fileName);
            }
        }
    }

    public void sendSMSAndEmailToscheduleAppointment(final BpaAppointmentSchedule scheduleDetails,
            final BpaApplication bpaApplication) {
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = bpaApplication.getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                buildSmsAndEmailForScheduleAppointment(scheduleDetails, bpaApplication, owner.getName(),
                        owner.getUser().getMobileNumber(), owner.getEmailId());
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                buildSmsAndEmailForScheduleAppointment(scheduleDetails, bpaApplication, stakeHolder.getName(),
                        stakeHolder.getMobileNumber(), stakeHolder.getEmailId());
            }

        }
    }

    public void sendSMSAndEmailToApplicantForLettertoparty(final BpaApplication bpaApplication) {
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = bpaApplication.getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                buildSmsAndEmailForBPANewAppln(bpaApplication, owner.getName(),
                        owner.getUser().getMobileNumber(), owner.getEmailId(), EMPTY, EMPTY, null, null);
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                buildSmsAndEmailForBPANewAppln(bpaApplication, stakeHolder.getName(),
                        stakeHolder.getMobileNumber(), stakeHolder.getEmailId(), EMPTY, EMPTY, null, null);
            }
        }
    }

    private void buildSmsAndEmailForBPANewAppln(final BpaApplication bpaApplication, final String applicantName,
            final String mobileNo, final String email, final String loginUserName, final String password,
            ReportOutput reportOutput, String fileName) {
        String smsMsg = EMPTY;
        String body = EMPTY;
        String subject = EMPTY;
        String smsCode;
        String mailCode;
        if ((APPLICATION_STATUS_REGISTERED).equalsIgnoreCase(bpaApplication.getStatus().getCode())) {
            if (bpaApplication.isMailPwdRequired() && isNotBlank(password)) {
                smsCode = MSG_KEY_SMS_BPA_APPLN_STC_FALSE;
                mailCode = BODY_KEY_EMAIL_BPA_APPLN_STC_FALSE;
            } else {
                smsCode = MSG_KEY_SMS_BPA_APPLN_STC_SUBMIT;
                mailCode = BODY_KEY_EMAIL_BPA_APPLN_NEW;
            }
            smsMsg = smsBodyByCodeAndArgsWithType(smsCode, applicantName, bpaApplication,
                    SMSEMAILTYPENEWBPAREGISTERED, loginUserName, password);
            body = emailBodyByCodeAndArgsWithType(mailCode, applicantName,
                    bpaApplication, SMSEMAILTYPENEWBPAREGISTERED, loginUserName, password);
            subject = emailSubjectforEmailByCodeAndArgs(SUBJECT_KEY_EMAIL_BPA_APPLN_NEW, bpaApplication.getApplicationNumber());
        } else if (CREATEDLETTERTOPARTY.equalsIgnoreCase(bpaApplication.getStatus().getCode())) {
            smsMsg = smsBodyByCodeAndArgsWithType(MSG_KEY_SMS_LETTERTOPARTY, applicantName,
                    bpaApplication, SMSEMAILTYPELETTERTOPARTY, EMPTY, EMPTY);
            body = emailBodyByCodeAndArgsWithType(BODY_KEY_EMAIL_LETTERTOPARTY, applicantName,
                    bpaApplication, SMSEMAILTYPELETTERTOPARTY, EMPTY, EMPTY);
            subject = emailSubjectforEmailByCodeAndArgs(SUBJECT_KEY_EMAIL_LETTERTOPARTY, bpaApplication.getApplicationNumber());
        } else if (APPLICATION_STATUS_CANCELLED.equalsIgnoreCase(bpaApplication.getStatus().getCode())) {
            smsMsg = smsBodyByCodeAndArgsWithType(MSG_KEY_SMS_CANCELL_APPLN, applicantName,
                    bpaApplication, APPLICATION_STATUS_CANCELLED, EMPTY, EMPTY);
            body = emailBodyByCodeAndArgsWithType(BODY_KEY_EMAIL_CANCELL_APPLN, applicantName,
                    bpaApplication, APPLICATION_STATUS_CANCELLED, EMPTY, EMPTY);
            subject = emailSubjectforEmailByCodeAndArgs(SUBJECT_KEY_EMAIL_CANCELL_APPLN, bpaApplication.getApplicationNumber());
        } else if (BpaConstants.APPLICATION_STATUS_CREATED.equalsIgnoreCase(bpaApplication.getStatus().getCode())
        			|| BpaConstants.APPLICATION_STATUS_SUBMITTED.equalsIgnoreCase(bpaApplication.getStatus().getCode())) {
            smsMsg = smsBodyByCodeAndArgsWithType(MSG_KEY_SMS_BPA_APPLN_STC_TRUE, applicantName, bpaApplication,
                    SMSEMAILTYPENEWBPAREGISTERED, loginUserName, password);
            body = emailBodyByCodeAndArgsWithType(BODY_KEY_EMAIL_BPA_APPLN_NEW_PAWD, applicantName,
                    bpaApplication, SMSEMAILTYPENEWBPAREGISTERED, loginUserName, password);
            subject = emailSubjectforEmailByCodeAndArgs(SUBJECT_KEY_EMAIL_BPA_APPLN_NEW,
                    bpaApplication.getApplicationNumber());
        }
        if (mobileNo != null && smsMsg != null)
            notificationService.sendSMS(mobileNo, smsMsg);
        if (email != null && body != null && reportOutput != null && fileName != null) {
            notificationService.sendEmailWithAttachment(email, subject, body, APP_PDF, fileName,
                    reportOutput.getReportOutputData());
        } else if (email != null && body != null) {
            notificationService.sendEmail(email, subject, body);
        }
    }

    private String emailSubjectforEmailForScheduleAppointmentForInspection(final BpaAppointmentSchedule scheduleDetails,
            final BpaApplication bpaApplication, String msgKey) {
        final Locale locale = LocaleContextHolder.getLocale();
        return bpaMessageSource.getMessage(msgKey,
                new String[] { DateUtils.toDefaultDateFormat(scheduleDetails.getAppointmentDate()),
                        scheduleDetails.getAppointmentTime(), bpaApplication.getApplicationNumber() },
                locale);
    }

    private String emailSubjectforEmailByCodeAndArgs(String code, String applicationNumber) {
        final Locale locale = LocaleContextHolder.getLocale();
        return bpaMessageSource.getMessage(code, new String[] { applicationNumber }, locale);
    }

    private String emailBodyByCodeAndArgsWithType(String code, String applicantName, BpaApplication bpaApplication,
            String type, String loginUserName, String password) {
        String body = EMPTY;
        if (SMSEMAILTYPENEWBPAREGISTERED.equalsIgnoreCase(type)) {
            if (password != EMPTY)
                body = bpaMessageSource.getMessage(code,
                        new String[] { applicantName, bpaApplication.getApplicationNumber(), loginUserName, password,
                                getMunicipalityName(), ApplicationThreadLocals.getDomainURL() },
                        null);
            else
                body = bpaMessageSource.getMessage(code,
                        new String[] { applicantName, bpaApplication.getApplicationNumber(), loginUserName,
                                getMunicipalityName() },
                        null);
        } else if (SMSEMAILTYPELETTERTOPARTY.equalsIgnoreCase(type))
            body = bpaMessageSource.getMessage(code,
                    new String[] { applicantName, bpaApplication.getApplicationNumber(), getMunicipalityName() }, null);
        else if (APPLICATION_STATUS_CANCELLED.equalsIgnoreCase(type)) {
            body = getCancelApplnMessage(code, applicantName, bpaApplication);
        }
        return body;
    }

    private String getCancelApplnMessage(String code, String applicantName, BpaApplication bpaApplication) {
        StateHistory<Position> stateHistory = bpaUtils.getRejectionComments(bpaApplication.getStateHistory());
        return bpaMessageSource.getMessage(code,
                new String[] { applicantName, bpaApplication.getApplicationNumber(),
                        stateHistory != null && isNotBlank(stateHistory.getComments()) ? stateHistory.getComments() : EMPTY,
                        getMunicipalityName() },
                null);
    }

    private String smsBodyByCodeAndArgsWithType(String code, String applicantName, BpaApplication bpaApplication,
            String type, String loginUserName, String password) {
        String smsMsg = EMPTY;
        if (SMSEMAILTYPENEWBPAREGISTERED.equalsIgnoreCase(type)) {
            if (isNotBlank(password))
                smsMsg = bpaMessageSource.getMessage(code,
                        new String[] { applicantName, bpaApplication.getApplicationNumber(), loginUserName, password,
                                getMunicipalityName(), ApplicationThreadLocals.getDomainURL() },
                        null);
            else
                smsMsg = bpaMessageSource.getMessage(code,
                        new String[] { applicantName, bpaApplication.getApplicationNumber(), loginUserName,
                                getMunicipalityName() },
                        null);
        } else if (SMSEMAILTYPELETTERTOPARTY.equalsIgnoreCase(type))
            smsMsg = bpaMessageSource.getMessage(code,
                    new String[] { applicantName, bpaApplication.getApplicationNumber(), getMunicipalityName() }, null);
        else if (APPLICATION_STATUS_CANCELLED.equalsIgnoreCase(type)) {

            smsMsg = getCancelApplnMessage(code, applicantName, bpaApplication);
        }
        return smsMsg;
    }

    public Boolean isSmsEnabled() {
        return getAppConfigValueByPassingModuleAndType(EGMODULE_NAME, SENDSMSFORBPA);
    }

    public Boolean getAppConfigValueByPassingModuleAndType(String moduleName, String sendsmsoremail) {
        final List<AppConfigValues> appConfigValue = appConfigValuesService.getConfigValuesByModuleAndKey(moduleName,
                sendsmsoremail);
        return YES.equalsIgnoreCase(
                appConfigValue != null && !appConfigValue.isEmpty() ? appConfigValue.get(0).getValue() : NO);
    }

    public Boolean isEmailEnabled() {
        return getAppConfigValueByPassingModuleAndType(EGMODULE_NAME, SENDEMAILFORBPA);
    }

    // for scrunity submitted
    public void sendSMSAndEmailForDocumentScrutiny(SlotApplication slotApplication) {
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = slotApplication.getApplication().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                buildSmsAndEmailForDocumentScrutiny(slotApplication, owner.getName(), owner.getUser().getMobileNumber(),
                        owner.getEmailId());
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                buildSmsAndEmailForDocumentScrutiny(slotApplication, stakeHolder.getName(), stakeHolder.getMobileNumber(),
                        stakeHolder.getEmailId());
            }
        }
    }

    private void buildSmsAndEmailForDocumentScrutiny(SlotApplication slotApplication,
            String name, String mobileNumber, String emailId) {
        String smsMsg = EMPTY;
        String body = EMPTY;
        String subject = EMPTY;
        if (slotApplication.getApplication().getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_SCHEDULED)) {
            smsMsg = buildMessageDetailsForSchAppForDocumentScrutiny(slotApplication, name,
                    MSG_KEY_SMS_BPA_DOC_SCRUTINY);
            body = buildMessageDetailsForSchAppForDocumentScrutiny(slotApplication, name,
                    BODY_KEY_EMAIL_BPA_DOC_SCRUTINY);
            subject = emailSubjectforEmailForScheduleAppointmentForScrutiny(SUB_KEY_EMAIL_BPA_DOCUMENT_SCRUTINY);
        } else if (slotApplication.getApplication().getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_RESCHEDULED)) {
            smsMsg = buildMessageDetailsForSchAppForDocumentScrutiny(slotApplication, name,
                    MSG_KEY_SMS_BPA_DOC_SCRUTINY_RESCHE);
            body = buildMessageDetailsForSchAppForDocumentScrutiny(slotApplication, name,
                    BODY_KEY_EMAIL_BPA_DOC_SCRUTINY_RESCHE);
            subject = emailSubjectforEmailForScheduleAppointmentForScrutiny(SUB_KEY_EMAIL_BPA_DOCUMENT_SCRUTINY);
        } else if (slotApplication.getApplication().getStatus().getCode()
                .equals(BpaConstants.APPLICATION_STATUS_PENDING_FOR_RESCHEDULING)) {
            smsMsg = buildMessageDtlsFrPendingForRescheduling(slotApplication,
                    name, MSG_KEY_SMS_BPA_DOC_SCRUTINY_PENDING_FOR_RESCHEDULING);
            body = buildMessageDtlsFrPendingForRescheduling(slotApplication,
                    name, BODY_KEY_EMAIL_BPA_DOC_SCRUTINY_PENDING_FOR_RESCHEDULING);
            subject = emailSubjectforEmailForScheduleAppointmentForScrutiny(SUB_KEY_EMAIL_BPA_DOCUMENT_SCRUTINY);
        } else if (slotApplication.getApplication().getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_CANCELLED)) {
            smsMsg = buildMessageDtlsFrCancellation(slotApplication,
                    name, MSG_KEY_SMS_BPA_DOC_SCRUTINY_CANCELLED);
            body = buildMessageDtlsFrCancellation(slotApplication,
                    name, BODY_KEY_EMAIL_BPA_DOC_SCRUTINY_CANCELLED);
            subject = emailSubjectforEmailForScheduleAppointmentForScrutiny(SUB_KEY_EMAIL_BPA_DOCUMENT_SCRUTINY);
        }
        if (isNotBlank(mobileNumber) && isNotBlank(smsMsg))
            notificationService.sendSMS(mobileNumber, smsMsg);
        if (isNotBlank(emailId) && isNotBlank(body))
            notificationService.sendEmail(emailId, subject, body);
    }

    private String buildMessageDtlsFrPendingForRescheduling(SlotApplication slotApplication,
            String name, String msgKey) {
        return bpaMessageSource.getMessage(msgKey, new String[] { name, slotApplication.getApplication().getApplicationNumber(),
                getMunicipalityName() }, null);
    }

    private String emailSubjectforEmailForScheduleAppointmentForScrutiny(String msgKey) {
        return bpaMessageSource.getMessage(msgKey, new String[] { getMunicipalityName() }, null);
    }

    private String buildMessageDtlsFrCancellation(
            SlotApplication slotApplication, String name, String msgKey) {

        return bpaMessageSource.getMessage(msgKey, new String[] { name, slotApplication.getApplication().getApplicationNumber(),
                ApplicationThreadLocals.getDomainURL(), getMunicipalityName() }, null);
    }

    private String buildMessageDetailsForSchAppForDocumentScrutiny(SlotApplication slotApplication,
            String name, String msgKey) {
        String message;
        if (slotApplication.getApplication().getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_SCHEDULED)) {
            message = bpaMessageSource.getMessage(msgKey,
                    new String[] { name,
                            DateUtils.toDefaultDateFormat(
                                    slotApplication.getSlotDetail().getSlot().getAppointmentDate()),
                            slotApplication.getSlotDetail().getAppointmentTime(),
                            slotApplication.getSlotDetail().getSlot().getZone().getName(),
                            getAppconfigValueByKeyNameForHelpLineNumber(),
                            slotApplication.getApplication().getApplicationNumber(),
                            ApplicationThreadLocals.getDomainURL(),
                            getMunicipalityName()
                    },
                    null);
        } else {
            message = bpaMessageSource.getMessage(msgKey,
                    new String[] { name, slotApplication.getApplication().getApplicationNumber(),
                            DateUtils.toDefaultDateFormat(
                                    slotApplication.getSlotDetail().getSlot().getAppointmentDate()),
                            slotApplication.getSlotDetail().getAppointmentTime(),
                            slotApplication.getSlotDetail().getSlot().getZone().getName(),
                            getAppconfigValueByKeyNameForHelpLineNumber(),
                            ApplicationThreadLocals.getDomainURL(),
                            getMunicipalityName()
                    },
                    null);
        }
        return message;
    }

    public String getAppconfigValueByKeyNameForHelpLineNumber() {
        List<AppConfigValues> appConfigValueList = appConfigValuesService
                .getConfigValuesByModuleAndKey(APPLICATION_MODULE_TYPE, "HELPLINENUMBER");
        return !appConfigValueList.isEmpty() ? appConfigValueList.get(0).getValue() : "";
    }

    public void sendSmsAndEmailOnApplicationApproval(BpaApplication application, ReportOutput reportOutput) {
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = application.getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                buildSmsAndEmailOnApplicationApproval(application, owner.getName(), owner.getUser().getMobileNumber(),
                        owner.getEmailId(), reportOutput);
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                buildSmsAndEmailOnApplicationApproval(application, stakeHolder.getName(), stakeHolder.getMobileNumber(),
                        stakeHolder.getEmailId(), reportOutput);
            }
            buildSmsAndEmailOnApplicationApproval(application, application.getOwner().getName(),
                    application.getOwner().getUser().getMobileNumber(), application.getOwner().getEmailId(),
                    reportOutput);
        }

    }

    private void buildSmsAndEmailOnApplicationApproval(BpaApplication application, String name, String mobileNumber,
            String emailId, ReportOutput reportOutput) {
        String smsMsg = "";
        String body = "";

        Boolean isPermitFeeExist = application.getDemand().getBaseDemand().subtract(application.getDemand().getAmtCollected())
                .compareTo(BigDecimal.ZERO) > 0 ? Boolean.TRUE : Boolean.FALSE;

        if (isPermitFeeExist) {
            smsMsg = buildMsgDetailsOnApplicationApproval(application, name, SMS_KEY_APRVL);
            body = buildMsgDetailsOnApplicationApproval(application, name, EMLB_KEY_APRVL);
        } else {
            smsMsg = buildMsgDetailsOnApplicationApproval(application, name, SMS_KEY_APRVL_WITHOUT_PERMITFEE);
            body = buildMsgDetailsOnApplicationApproval(application, name, EMLB_KEY_APRVL_WITHOUT_PERMITFEE);
        }
        String subject = buildMsgDetailsForEmailSubjectOnApplicationApproval(application, EMLS_KEY_APRVL);
        if (isNotBlank(mobileNumber) && isNotBlank(smsMsg)) {
            notificationService.sendSMS(mobileNumber, smsMsg);
        }
        if (isNotBlank(emailId) && isNotBlank(body))
            if (isPermitFeeExist)
                notificationService.sendEmailWithAttachment(emailId, subject, body, APP_PDF,
                        DEMANDNOCFILENAME + PDFEXTN, reportOutput.getReportOutputData());
            else
                notificationService.sendEmail(emailId, subject, body);
    }

    private String buildMsgDetailsForEmailSubjectOnApplicationApproval(BpaApplication application, String msgKey) {
        return bpaMessageSource.getMessage(msgKey, new String[] { application.getApplicationNumber() }, null);
    }

    private String buildMsgDetailsOnApplicationApproval(BpaApplication application, String name, String msgKey) {
        BigDecimal amtToRemit = application.getDemand().getBaseDemand()
                .subtract(application.getDemand().getAmtCollected());
        String msg = bpaMessageSource.getMessage(msgKey,
                new String[] { name, application.getApplicationNumber(), amtToRemit.toString(), getMunicipalityName() },
                null);
        return msg;
    }

    public void sendSmsAndEmailOnPermitOrderGeneration(BpaApplication application, ReportOutput reportOutput) {
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = application.getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                buildSmsAndEmailOnPermitOrderGeneration(application, owner.getName(), owner.getUser().getMobileNumber(),
                        owner.getEmailId(), reportOutput);
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                buildSmsAndEmailOnPermitOrderGeneration(application, stakeHolder.getName(), stakeHolder.getMobileNumber(),
                        stakeHolder.getEmailId(), reportOutput);
            }
        }
    }

    private void buildSmsAndEmailOnPermitOrderGeneration(BpaApplication application, String name, String mobileNumber,
            String emailId, ReportOutput reportOutput) {
        String smsMsg = buildMsgDetailsOnPermitOrderGeneration(application, name, SMS_KEY_PO);
        String body = buildMsgDetailsOnPermitOrderGeneration(application, name, EMLB_KEY_PO);
        String subject = buildEmailSubjectOnPermitOrderGeneration(application, EMLS_KEY_PO);
        if (isNotBlank(mobileNumber) && isNotBlank(smsMsg)) {
            notificationService.sendSMS(mobileNumber, smsMsg);
        }
        if (isNotBlank(emailId) && isNotBlank(body)) {
            notificationService.sendEmailWithAttachment(emailId, subject, body, APP_PDF,
                    BUILDINGPERMITFILENAME + "order" + PDFEXTN, reportOutput.getReportOutputData());
        }
    }

    private String buildEmailSubjectOnPermitOrderGeneration(BpaApplication application, String msgKey) {
        return bpaMessageSource.getMessage(msgKey, new String[] { application.getApplicationNumber() }, null);
    }

    private String buildMsgDetailsOnPermitOrderGeneration(BpaApplication application, String name, String msgKey) {
        String msg = bpaMessageSource.getMessage(msgKey,
                new String[] { name, application.getApplicationNumber(), getMunicipalityName() }, null);
        return msg;
    }

    //////

    public void sendSmsForCollection(BigDecimal totalAmount, BpaApplication application, BillReceiptInfo billRcptInfo) {
        if (isSmsEnabled()) {
            String msg = buildSmsMsgDetailsForCollection(totalAmount, application, billRcptInfo);
            ApplicationStakeHolder applnStakeHolder = application.getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                if (isNotBlank(owner.getUser().getMobileNumber()) && isNotBlank(msg)) {
                    notificationService.sendSMS(owner.getUser().getMobileNumber(), msg);
                }
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                if (isNotBlank(stakeHolder.getMobileNumber()) && isNotBlank(msg)) {
                    notificationService.sendSMS(stakeHolder.getMobileNumber(), msg);
                }
                if (isEmailEnabled() && isNotBlank(stakeHolder.getEmailId()))
                    notificationService.sendEmail(stakeHolder.getEmailId(),
                            bpaMessageSource.getMessage("msg.email.subject.collection",
                                    new String[] { billRcptInfo.getServiceName() }, null),
                            buildEmailBodyForCollection(totalAmount, application, billRcptInfo));
            }
        }
    }

    public void sendSmsEmailForStakeholderCollection(BigDecimal totalAmount, StakeHolder stakeHolder,
            BillReceiptInfo billRcptInfo) {
        if (isSmsEnabled()) {
            String smsMsg = bpaMessageSource.getMessage(STAKEHOLDER_PAYMENT_CONFIM_SMS,
                    new String[] { totalAmount.toString(), stakeHolder.getCode(), stakeHolder.getUsername(),
                            ApplicationThreadLocals.getDomainURL() },
                    null);
            if (isNotBlank(stakeHolder.getMobileNumber()) && isNotBlank(smsMsg)) {
                notificationService.sendSMS(stakeHolder.getMobileNumber(), smsMsg);
            }
        }
        if (isEmailEnabled() && isNotBlank(stakeHolder.getEmailId())) {
            String emailMsg = bpaMessageSource.getMessage(STAKEHOLDER_PAYMENT_CONFIM_EMAIL_BODY,
                    new String[] { stakeHolder.getName(), totalAmount.toString(), stakeHolder.getCode(),
                            billRcptInfo.getReceiptDate().toString(), stakeHolder.getUsername(),
                            getMunicipalityName() },
                    null);
            notificationService.sendEmail(stakeHolder.getEmailId(),
                    bpaMessageSource.getMessage(STAKEHOLDER_PAYMENT_CONFIM_EMAIL_SUB,
                            new String[] { billRcptInfo.getServiceName() }, null),
                    emailMsg);
        }
    }

    private String buildSmsMsgDetailsForCollection(BigDecimal totalAmount, BpaApplication application,
            BillReceiptInfo billRcptInfo) {
        String msg = bpaMessageSource.getMessage(SMS_KEY_CLCTN,
                new String[] { totalAmount.toString(), billRcptInfo.getServiceName(),
                        application.getApplicationNumber(), billRcptInfo.getReceiptDate().toString() },
                null);
        return msg;
    }

    private String buildEmailBodyForCollection(BigDecimal totalAmount, BpaApplication application,
            BillReceiptInfo billRcptInfo) {
        String msg = bpaMessageSource.getMessage("msg.email.body.collection",
                new String[] { getMunicipalityName(), totalAmount.toString(), billRcptInfo.getServiceName(),
                        application.getApplicationNumber(), billRcptInfo.getReceiptDate().toString(), getMunicipalityName() },
                null);
        return msg;
    }

    public void sendSMSAndEmailForDeemedApprovalNoc(PermitNocApplication permitNoc) {
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = permitNoc.getBpaApplication().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                buildSmsAndEmailForDeemedApproveNoc(permitNoc.getBpaNocApplication(), owner.getName(),
                        owner.getUser().getMobileNumber(), owner.getEmailId());

            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                buildSmsAndEmailForDeemedApproveNoc(permitNoc.getBpaNocApplication(), stakeHolder.getName(),
                        stakeHolder.getMobileNumber(), stakeHolder.getEmailId());
            }
        }
    }

    public void sendSMSAndEmailForDeemedApprovalOCNoc(OccupancyNocApplication ocNoc) {
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = ocNoc.getOc().getParent().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                buildSmsAndEmailForDeemedApproveNoc(ocNoc.getBpaNocApplication(), owner.getName(),
                        owner.getUser().getMobileNumber(), owner.getEmailId());

            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                buildSmsAndEmailForDeemedApproveNoc(ocNoc.getBpaNocApplication(), stakeHolder.getName(),
                        stakeHolder.getMobileNumber(), stakeHolder.getEmailId());
            }
        }
    }

    private void buildSmsAndEmailForDeemedApproveNoc(BpaNocApplication noc,
            String name, String mobileNumber, String emailId) {
        String smsMsg = EMPTY;
        String body = EMPTY;
        String subject = EMPTY;
        smsMsg = bpaMessageSource.getMessage(SMS_BPA_NOC_DEEMED_APPROVE, new String[] { noc.getNocType() }, null);
        body = bpaMessageSource.getMessage(BODY_BPA_NOC_DEEMED_APPROVE,
                new String[] { name, noc.getNocType(), noc.getNocType(), getMunicipalityName() }, null);
        subject = bpaMessageSource.getMessage(SUB_BPA_NOC_DEEMED_APPROVE, new String[] { getMunicipalityName() }, null);
        if (isNotBlank(mobileNumber) && isNotBlank(smsMsg))
            notificationService.sendSMS(mobileNumber, smsMsg);
        if (isNotBlank(emailId) && isNotBlank(body))
            notificationService.sendEmail(emailId, subject, body);
    }

    private void buildSmsAndEmailForScheduleAppointment(final BpaAppointmentSchedule scheduleDetails,
            final BpaApplication bpaApplication, final String applicantName, final String mobileNo, final String email) {
        String smsMsg = EMPTY;
        String body = EMPTY;
        String subject = EMPTY;
        if (AppointmentSchedulePurpose.INSPECTION.equals(scheduleDetails.getPurpose())) {
            if (!scheduleDetails.isPostponed()) {
                smsMsg = buildMessageDetailsForScheduleAppointment(scheduleDetails, bpaApplication, applicantName,
                        MSG_KEY_SMS_BPA_FIELD_INS);
                body = buildMessageDetailsForScheduleAppointment(scheduleDetails, bpaApplication, applicantName,
                        BODY_KEY_EMAIL_BPA_FIELD_INS);
                subject = emailSubjectforEmailForScheduleAppointmentForInspection(scheduleDetails, bpaApplication,
                        SUBJECT_KEY_EMAIL_BPA_FIELD_INS);
            } else {
                smsMsg = buildMessageDetailsForScheduleAppointment(scheduleDetails, bpaApplication, applicantName,
                        MSG_KEY_SMS_BPA_FIELD_INS_RESCHE);
                body = buildMessageDetailsForScheduleAppointment(scheduleDetails, bpaApplication, applicantName,
                        BODY_KEY_EMAIL_BPA_FIELD_INS_RESCHE);
                subject = emailSubjectforEmailForScheduleAppointmentForInspection(scheduleDetails, bpaApplication,
                        SUBJECT_KEY_EMAIL_BPA_FIELD_INS_RESCHE);
            }
        }
        if (isNotBlank(mobileNo) && isNotBlank(smsMsg))
            notificationService.sendSMS(mobileNo, smsMsg);
        if (isNotBlank(email) && isNotBlank(body))
            notificationService.sendEmail(email, subject, body);
    }

    public void sendInsSMSAndEmail(final InspectionApplication inspectionApplication, final BpaApplication bpaApplication,
            ReportOutput reportOutput, String fileName) {
        String mobileNo;
        String email;
        String applicantName;
        String loginUserName;
        String password;
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = bpaApplication.getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant applicant = applnStakeHolder.getApplication().getOwner();
                applicantName = applicant.getName();
                email = applicant.getEmailId();
                mobileNo = applicant.getUser().getMobileNumber();
                loginUserName = applicant.getUser().getUsername();
                password = mobileNo;
                buildSmsAndEmailForBPANewAppln(bpaApplication, applicantName, mobileNo, email, loginUserName, password,
                        reportOutput, fileName);
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                applicantName = applnStakeHolder.getStakeHolder().getName();
                email = applnStakeHolder.getStakeHolder().getEmailId();
                mobileNo = applnStakeHolder.getStakeHolder().getMobileNumber();
                loginUserName = applnStakeHolder.getStakeHolder().getUsername();
                password = EMPTY;
                buildSmsAndEmailForBPANewAppln(bpaApplication, applicantName, mobileNo, email, loginUserName, password,
                        reportOutput, fileName);
            }
        }
    }

    public void sendSMSAndEmailToInsscheduleAppointment(final InspectionAppointmentSchedule scheduleDetails,
            final InspectionApplication insApplication,
            final BpaApplication bpaApplication) {
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = bpaApplication.getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                buildSmsAndEmailForInsScheduleAppointment(scheduleDetails, insApplication, owner.getName(),
                        owner.getUser().getMobileNumber(), owner.getEmailId());
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                buildSmsAndEmailForInsScheduleAppointment(scheduleDetails, insApplication, stakeHolder.getName(),
                        stakeHolder.getMobileNumber(), stakeHolder.getEmailId());
            }
        }
    }

    private void buildSmsAndEmailForInsScheduleAppointment(final InspectionAppointmentSchedule scheduleDetails,
            final InspectionApplication inspectionApplication,
            final String applicantName, final String mobileNo, final String email) {
        String smsMsg = EMPTY;
        String body = EMPTY;
        String subject = EMPTY;
        if (AppointmentSchedulePurpose.INSPECTION.equals(scheduleDetails.getAppointmentScheduleCommon().getPurpose())) {
            if (!scheduleDetails.getAppointmentScheduleCommon().isPostponed()) {
                smsMsg = buildMessageDetailsForInsScheduleAppointment(scheduleDetails, inspectionApplication, applicantName,
                        MSG_KEY_SMS_BPA_FIELD_INS);
                body = buildMessageDetailsForInsScheduleAppointment(scheduleDetails, inspectionApplication, applicantName,
                        BODY_KEY_EMAIL_BPA_FIELD_INS);
                subject = emailSubjectforEmailForInsScheduleAppointmentForInspection(scheduleDetails, inspectionApplication,
                        SUBJECT_KEY_EMAIL_BPA_FIELD_INS);
            } else {
                smsMsg = buildMessageDetailsForInsScheduleAppointment(scheduleDetails, inspectionApplication, applicantName,
                        MSG_KEY_SMS_BPA_FIELD_INS_RESCHE);
                body = buildMessageDetailsForInsScheduleAppointment(scheduleDetails, inspectionApplication, applicantName,
                        BODY_KEY_EMAIL_BPA_FIELD_INS_RESCHE);
                subject = emailSubjectforEmailForInsScheduleAppointmentForInspection(scheduleDetails, inspectionApplication,
                        SUBJECT_KEY_EMAIL_BPA_FIELD_INS_RESCHE);
            }
        }
        if (isNotBlank(mobileNo) && isNotBlank(smsMsg))
            notificationService.sendSMS(mobileNo, smsMsg);
        if (isNotBlank(email) && isNotBlank(body))
            notificationService.sendEmail(email, subject, body);
    }

    private String buildMessageDetailsForInsScheduleAppointment(final InspectionAppointmentSchedule scheduleDetails,
            final InspectionApplication insApplication, String applicantName, String msgKey) {
        String mesg = EMPTY;
        if (AppointmentSchedulePurpose.INSPECTION.equals(scheduleDetails.getAppointmentScheduleCommon().getPurpose())) {

            if (!scheduleDetails.getAppointmentScheduleCommon().isPostponed()) {
                mesg = bpaMessageSource.getMessage(msgKey,
                        new String[] { applicantName,
                                DateUtils
                                        .toDefaultDateFormat(scheduleDetails.getAppointmentScheduleCommon().getAppointmentDate()),
                                scheduleDetails.getAppointmentScheduleCommon().getAppointmentTime(),
                                insApplication.getApplicationNumber(), getMunicipalityName() },
                        null);
            } else {
                mesg = bpaMessageSource.getMessage(msgKey,
                        new String[] { applicantName,
                                DateUtils
                                        .toDefaultDateFormat(scheduleDetails.getAppointmentScheduleCommon().getAppointmentDate()),
                                scheduleDetails.getAppointmentScheduleCommon().getAppointmentTime(),
                                insApplication.getApplicationNumber(),
                                scheduleDetails.getAppointmentScheduleCommon().getPostponementReason(), getMunicipalityName() },
                        null);
            }
        }
        return mesg;
    }

    private String buildMessageDetailsForScheduleAppointment(final BpaAppointmentSchedule scheduleDetails,
            final BpaApplication bpaApplication, String applicantName, String msgKey) {
        String mesg = EMPTY;
        if (AppointmentSchedulePurpose.INSPECTION.equals(scheduleDetails.getPurpose())) {

            if (!scheduleDetails.isPostponed()) {
                mesg = bpaMessageSource.getMessage(msgKey,
                        new String[] { applicantName, DateUtils.toDefaultDateFormat(scheduleDetails.getAppointmentDate()),
                                scheduleDetails.getAppointmentTime(),
                                bpaApplication.getApplicationNumber(), getMunicipalityName() },
                        null);
            } else {
                mesg = bpaMessageSource.getMessage(msgKey,
                        new String[] { applicantName,
                                DateUtils.toDefaultDateFormat(scheduleDetails.getAppointmentDate()),
                                scheduleDetails.getAppointmentTime(),
                                bpaApplication.getApplicationNumber(),
                                scheduleDetails.getPostponementReason(), getMunicipalityName() },
                        null);
            }
        }
        return mesg;
    }

    public void sendSMSAndEmailForInspection(final InspectionApplication inspectionApplication,
            final BpaApplication bpaApplication, ReportOutput reportOutput, String fileName) {
        String mobileNo;
        String email;
        String applicantName;
        String loginUserName;
        String password;
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = bpaApplication.getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant applicant = applnStakeHolder.getApplication().getOwner();
                applicantName = applicant.getName();
                email = applicant.getEmailId();
                mobileNo = applicant.getUser().getMobileNumber();
                loginUserName = applicant.getUser().getUsername();
                password = mobileNo;
                buildSmsAndEmailForInsAppln(inspectionApplication, bpaApplication, applicantName, mobileNo, email, loginUserName,
                        password, reportOutput, fileName);
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()
                    && (bpaUtils.isCitizenAcceptanceRequired() && bpaApplication.isCitizenAccepted())) {
                applicantName = applnStakeHolder.getStakeHolder().getName();
                email = applnStakeHolder.getStakeHolder().getEmailId();
                mobileNo = applnStakeHolder.getStakeHolder().getMobileNumber();
                loginUserName = applnStakeHolder.getStakeHolder().getUsername();
                password = EMPTY;
                buildSmsAndEmailForInsAppln(inspectionApplication, bpaApplication, applicantName, mobileNo, email, loginUserName,
                        password, reportOutput, fileName);
            }
        }
    }

    private void buildSmsAndEmailForInsAppln(final InspectionApplication inspectionApplication,
            final BpaApplication bpaApplication, final String applicantName,
            final String mobileNo, final String email, final String loginUserName, final String password,
            ReportOutput reportOutput, String fileName) {
        String smsMsg = EMPTY;
        String body = EMPTY;
        String subject = EMPTY;
        String smsCode;
        String mailCode;
        if ((BpaConstants.INITIATEINSPECTION).equalsIgnoreCase(inspectionApplication.getStatus().getCode())) {
            if (!bpaUtils.logedInuserIsBusinessUser()) {
                smsCode = "msg.stkhldr.ins.initiation.sms.body";
                mailCode = "msg.stkhldr.ins.initiation.email.body";
                smsMsg = insStakeholderSmsBody(smsCode, applicantName, inspectionApplication.getApplicationNumber(),
                        bpaApplication.getPlanPermissionNumber(), loginUserName, password);
                body = insStakeholderEmailBody(mailCode, applicantName, inspectionApplication.getApplicationNumber(),
                        bpaApplication.getPlanPermissionNumber(), loginUserName, password);
            } else {
                smsCode = "msg.citizen.ins.initiation.sms.body";
                mailCode = "msg.citizen.ins.initiation.email.body";
                smsMsg = insSmsBodyByCodeAndArgsWithType(smsCode, applicantName, inspectionApplication.getApplicationNumber(),
                        bpaApplication.getPlanPermissionNumber(), loginUserName, password);
                body = insEmailBodyByCodeAndArgsWithType(mailCode, applicantName, inspectionApplication.getApplicationNumber(),
                        bpaApplication.getPlanPermissionNumber(), loginUserName, password);
            }
            subject = emailSubjectforEmailByCodeAndArgs("msg.inspection.app.email.sub",
                    inspectionApplication.getApplicationNumber());
        } else if (BpaConstants.APPROVED.equalsIgnoreCase(inspectionApplication.getStatus().getCode())) {
            smsCode = "msg.inspection.approved.sms.body";
            mailCode = "msg.inspection.approved.email.body";
            smsMsg = insSmsBodyByCodeAndArgsWithType(smsCode, applicantName,
                    inspectionApplication.getApplicationNumber(), bpaApplication.getPlanPermissionNumber(), EMPTY, EMPTY);
            body = insEmailBodyByCodeAndArgsWithType(mailCode, applicantName,
                    inspectionApplication.getApplicationNumber(), bpaApplication.getPlanPermissionNumber(), EMPTY, EMPTY);
            subject = emailSubjectforEmailByCodeAndArgs("msg.inspection.app.email.sub", bpaApplication.getApplicationNumber());
        } else if (BpaConstants.APPLICATION_STATUS_REVOKED.equalsIgnoreCase(inspectionApplication.getStatus().getCode())) {
            smsCode = "msg.inspection.revoked.sms.body";
            mailCode = "msg.inspection.revoked.email.body";
            smsMsg = insSmsBodyByCodeAndArgsWithType(smsCode, applicantName,
                    inspectionApplication.getApplicationNumber(), bpaApplication.getPlanPermissionNumber(), EMPTY, EMPTY);
            body = insEmailBodyByCodeAndArgsWithType(mailCode, applicantName,
                    inspectionApplication.getApplicationNumber(), bpaApplication.getPlanPermissionNumber(), EMPTY, EMPTY);
            subject = emailSubjectforEmailByCodeAndArgs("msg.inspection.app.email.sub", bpaApplication.getApplicationNumber());
        }
        if (mobileNo != null && smsMsg != null)
            notificationService.sendSMS(mobileNo, smsMsg);
        if (email != null && body != null && reportOutput != null && fileName != null) {
            notificationService.sendEmailWithAttachment(email, subject, body, APP_PDF, fileName,
                    reportOutput.getReportOutputData());
        } else if (email != null && body != null) {
            notificationService.sendEmail(email, subject, body);
        }
    }

    private String insSmsBodyByCodeAndArgsWithType(String code, String applicantName, String applicationNumber,
            String planPermissionNumber, String loginUserName, String password) {
        return bpaMessageSource.getMessage(code,
                new String[] { planPermissionNumber, applicationNumber, getMunicipalityName(),
                        ApplicationThreadLocals.getDomainURL() },
                null);
    }

    private String insEmailBodyByCodeAndArgsWithType(String code, String applicantName, String applicationNumber,
            String planPermissionNumber,
            String loginUserName, String password) {
        return bpaMessageSource.getMessage(code, new String[] { applicantName, planPermissionNumber, applicationNumber,
                getMunicipalityName(), ApplicationThreadLocals.getDomainURL() }, null);

    }

    private String insStakeholderSmsBody(String code, String applicantName, String applicationNumber,
            String planPermissionNumber, String loginUserName, String password) {

        return bpaMessageSource.getMessage(code,
                new String[] { applicantName, bpaUtils.getCurrentUser().getName(), planPermissionNumber, applicationNumber,
                        getMunicipalityName(), ApplicationThreadLocals.getDomainURL() },
                null);

    }

    private String insStakeholderEmailBody(String code, String applicantName, String applicationNumber,
            String planPermissionNumber,
            String loginUserName, String password) {
        return bpaMessageSource.getMessage(code,
                new String[] { applicantName,bpaUtils.getCurrentUser().getName(), planPermissionNumber, applicationNumber,
                        getMunicipalityName(), ApplicationThreadLocals.getDomainURL() },
                null);
    }

    private String emailSubjectforEmailForInsScheduleAppointmentForInspection(
            final InspectionAppointmentSchedule inspectionAppointmentSchedule,
            final InspectionApplication inspectionApplication, String msgKey) {
        final Locale locale = LocaleContextHolder.getLocale();
        return bpaMessageSource.getMessage(msgKey,
                new String[] {
                        DateUtils.toDefaultDateFormat(
                                inspectionAppointmentSchedule.getAppointmentScheduleCommon().getAppointmentDate()),
                        inspectionAppointmentSchedule.getAppointmentScheduleCommon().getAppointmentTime(),
                        inspectionApplication.getApplicationNumber() },
                locale);
    }
    
    public void sendSMSAndEmailForNocProcess(String status,PermitNocApplication permitNoc) {
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = permitNoc.getBpaApplication().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
				if (status.equalsIgnoreCase(BpaConstants.NOC_INITIATED)) {
					buildSmsAndEmailForNocInitiation(permitNoc.getBpaApplication().getApplicationNumber(),
							permitNoc.getBpaNocApplication(), owner.getName(), owner.getUser().getMobileNumber(),
							owner.getEmailId());
				}else if (status.equalsIgnoreCase(BpaConstants.NOC_RE_INITIATED)) {
					buildSmsAndEmailForNocReInitiation(permitNoc.getBpaApplication().getApplicationNumber(),
							permitNoc.getBpaNocApplication(), owner.getName(), owner.getUser().getMobileNumber(),
							owner.getEmailId());
				}else if (status.equalsIgnoreCase(BpaConstants.NOC_APPROVED)) {
					buildSmsAndEmailForNocApproval(permitNoc.getBpaApplication().getApplicationNumber(),
							permitNoc.getBpaNocApplication(), owner.getName(), owner.getUser().getMobileNumber(),
							owner.getEmailId());
				}else if (status.equalsIgnoreCase(BpaConstants.NOC_REJECTED)) {
					buildSmsAndEmailForNocRejection(permitNoc.getBpaApplication().getApplicationNumber(),
							permitNoc.getBpaNocApplication(), owner.getName(), owner.getUser().getMobileNumber(),
							owner.getEmailId());
				} else if (status.equalsIgnoreCase(BpaConstants.NOC_SEND_OBSERVATIONS)) {
					buildSmsAndEmailForNocSendObservations(permitNoc.getBpaApplication().getApplicationNumber(),
							permitNoc.getBpaNocApplication(), owner.getName(), owner.getUser().getMobileNumber(),
							owner.getEmailId());
				}

            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
				if (status.equalsIgnoreCase(BpaConstants.NOC_INITIATED)) {
					buildSmsAndEmailForNocInitiation(permitNoc.getBpaApplication().getApplicationNumber(),
							permitNoc.getBpaNocApplication(), stakeHolder.getName(), stakeHolder.getMobileNumber(),
							stakeHolder.getEmailId());
				}else if (status.equalsIgnoreCase(BpaConstants.NOC_APPROVED)) {
					buildSmsAndEmailForNocApproval(permitNoc.getBpaApplication().getApplicationNumber(),
							permitNoc.getBpaNocApplication(), stakeHolder.getName(), stakeHolder.getMobileNumber(),
							stakeHolder.getEmailId());
				}else if (status.equalsIgnoreCase(BpaConstants.NOC_REJECTED)) {
					buildSmsAndEmailForNocRejection(permitNoc.getBpaApplication().getApplicationNumber(),
							permitNoc.getBpaNocApplication(), stakeHolder.getName(), stakeHolder.getMobileNumber(),
							stakeHolder.getEmailId());
				}
            }
        }
    }

    private void buildSmsAndEmailForNocInitiation(String bpaApplno,BpaNocApplication noc,
            String name, String mobileNumber, String emailId) {
        String smsMsg = EMPTY;
        String body = EMPTY;
        String subject = EMPTY;
        smsMsg = bpaMessageSource.getMessage(SMS_BPA_NOC_INITIATION, new String[] { name,noc.getNocApplicationNumber(),bpaApplno , getMunicipalityName() }, null);
        body = bpaMessageSource.getMessage(BODY_BPA_NOC_INITIATION,
                new String[] { name,noc.getNocApplicationNumber(),bpaApplno , getMunicipalityName() }, null);
        subject = bpaMessageSource.getMessage(SUB_BPA_NOC_INITIATION, new String[] { getMunicipalityName() }, null);
        if (isNotBlank(mobileNumber) && isNotBlank(smsMsg))
            notificationService.sendSMS(mobileNumber, smsMsg);
        if (isNotBlank(emailId) && isNotBlank(body))
            notificationService.sendEmail(emailId, subject, body);
    }

    private void buildSmsAndEmailForNocApproval(String bpaApplno,BpaNocApplication noc,
            String name, String mobileNumber, String emailId) {
        String smsMsg = EMPTY;
        String body = EMPTY;
        String subject = EMPTY;
        smsMsg = bpaMessageSource.getMessage(SMS_BPA_NOC_APPROVE, new String[] { name,noc.getNocApplicationNumber(),bpaApplno ,getMunicipalityName() }, null);
        body = bpaMessageSource.getMessage(BODY_BPA_NOC_APPROVE,
                new String[] { name,noc.getNocApplicationNumber(),bpaApplno , getMunicipalityName() }, null);
        subject = bpaMessageSource.getMessage(SUB_BPA_NOC_APPROVE, new String[] { getMunicipalityName() }, null);
        if (isNotBlank(mobileNumber) && isNotBlank(smsMsg))
            notificationService.sendSMS(mobileNumber, smsMsg);
        if (isNotBlank(emailId) && isNotBlank(body))
            notificationService.sendEmail(emailId, subject, body);
    }

    private void buildSmsAndEmailForNocRejection(String bpaApplno,BpaNocApplication noc,
            String name, String mobileNumber, String emailId) {
        String smsMsg = EMPTY;
        String body = EMPTY;
        String subject = EMPTY;
        smsMsg = bpaMessageSource.getMessage(SMS_BPA_NOC_REJECT, new String[] { name,noc.getNocApplicationNumber(),bpaApplno,noc.getRemarks(),noc.getNocType(),getMunicipalityName() }, null);
        body = bpaMessageSource.getMessage(BODY_BPA_NOC_REJECT,
                new String[] { name,noc.getNocApplicationNumber(),bpaApplno ,noc.getRemarks(),noc.getNocType(),getMunicipalityName() }, null);
        subject = bpaMessageSource.getMessage(SUB_BPA_NOC_REJECT, new String[] { getMunicipalityName() }, null);
        if (isNotBlank(mobileNumber) && isNotBlank(smsMsg))
            notificationService.sendSMS(mobileNumber, smsMsg);
        if (isNotBlank(emailId) && isNotBlank(body))
            notificationService.sendEmail(emailId, subject, body);
    }
    
    private void buildSmsAndEmailForNocSendObservations(String bpaApplno, BpaNocApplication noc,
            String name, String mobileNumber, String emailId) {
        String smsMsg = EMPTY;
        String body = EMPTY;
        String subject = EMPTY;
        smsMsg = bpaMessageSource.getMessage(SMS_BPA_NOC_SEND_OBSERVATIONS, new String[] { name,noc.getNocApplicationNumber(),bpaApplno,noc.getRemarks(),noc.getNocType(),getMunicipalityName() }, null);
        body = bpaMessageSource.getMessage(BODY_BPA_NOC_SEND_OBSERVATIONS,
                new String[] { name,noc.getNocApplicationNumber(),bpaApplno ,noc.getRemarks(),noc.getNocType(),getMunicipalityName() }, null);
        subject = bpaMessageSource.getMessage(SUB_BPA_NOC_SEND_OBSERVATIONS, new String[] { getMunicipalityName() }, null);
        if (isNotBlank(mobileNumber) && isNotBlank(smsMsg))
            notificationService.sendSMS(mobileNumber, smsMsg);
        if (isNotBlank(emailId) && isNotBlank(body))
            notificationService.sendEmail(emailId, subject, body);
    }
    
    private void buildSmsAndEmailForNocReInitiation(String bpaApplno,BpaNocApplication noc,
            String name, String mobileNumber, String emailId) {
        String smsMsg = EMPTY;
        String body = EMPTY;
        String subject = EMPTY;
        smsMsg = bpaMessageSource.getMessage(SMS_BPA_NOC_RE_INITIATION, new String[] { name,noc.getNocApplicationNumber(),bpaApplno , getMunicipalityName() }, null);
        body = bpaMessageSource.getMessage(BODY_BPA_NOC_RE_INITIATION,
                new String[] { name,noc.getNocApplicationNumber(),bpaApplno , getMunicipalityName() }, null);
        subject = bpaMessageSource.getMessage(SUB_BPA_NOC_RE_INITIATION, new String[] { getMunicipalityName() }, null);
        if (isNotBlank(mobileNumber) && isNotBlank(smsMsg))
            notificationService.sendSMS(mobileNumber, smsMsg);
        if (isNotBlank(emailId) && isNotBlank(body))
            notificationService.sendEmail(emailId, subject, body);
    }
}