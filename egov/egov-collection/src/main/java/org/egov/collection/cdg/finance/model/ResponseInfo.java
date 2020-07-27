package org.egov.collection.cdg.finance.model;

public class ResponseInfo {

    private String apiId;

    private String ver;

    private String ts;

    private String resMsgId;

    private String msgId;

    private String status;
    
    
    public ResponseInfo() {
	}

	public ResponseInfo(String apiId, String ver, String ts, String resMsgId, String msgId, String status) {
        this.apiId = apiId;
        this.ver = ver;
        this.ts = ts;
        this.resMsgId = resMsgId;
        this.msgId = msgId;
        this.status = status;
    }

    public String getApiId() {
        return apiId;
    }

    public String getVer() {
        return ver;
    }

    public String getTs() {
        return ts;
    }

    public String getResMsgId() {
        return resMsgId;
    }

    public String getMsgId() {
        return msgId;
    }

    public String getStatus() {
        return status;
    }
}