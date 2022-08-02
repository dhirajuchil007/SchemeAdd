import java.util.ArrayList;

public class SchemeEnableModel {
    private String adminPassword;
    private String schemeMasterId;
    private String sipSchemeMasterId;
    private boolean enableOnetime;
    private boolean enableSIP;
    private boolean enableInApp;
    ArrayList< Object > bucketIds = new ArrayList < Object > ();


    // Getter Methods

    public String getAdminPassword() {
        return adminPassword;
    }

    public String getSchemeMasterId() {
        return schemeMasterId;
    }

    public String getSipSchemeMasterId() {
        return sipSchemeMasterId;
    }

    public boolean getEnableOnetime() {
        return enableOnetime;
    }

    public boolean getEnableSIP() {
        return enableSIP;
    }

    public boolean getEnableInApp() {
        return enableInApp;
    }

    // Setter Methods

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public void setSchemeMasterId(String schemeMasterId) {
        this.schemeMasterId = schemeMasterId;
    }

    public void setSipSchemeMasterId(String sipSchemeMasterId) {
        this.sipSchemeMasterId = sipSchemeMasterId;
    }

    public void setEnableOnetime(boolean enableOnetime) {
        this.enableOnetime = enableOnetime;
    }

    public void setEnableSIP(boolean enableSIP) {
        this.enableSIP = enableSIP;
    }

    public void setEnableInApp(boolean enableInApp) {
        this.enableInApp = enableInApp;
    }

    public ArrayList<Object> getBucketIds() {
        return bucketIds;
    }

    public void setBucketIds(ArrayList<Object> bucketIds) {
        this.bucketIds = bucketIds;
    }
}