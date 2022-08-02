import java.util.ArrayList;

public class SchemeRemoveModel {
    private String adminPassword;
    private String schemeMasterId;
    private String sipSchemeMasterId;
    private boolean removeOneTime;
    private boolean removeSIP;
    private boolean forPanel;
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

    public boolean getRemoveOneTime() {
        return removeOneTime;
    }

    public boolean getRemoveSIP() {
        return removeSIP;
    }

    public boolean getForPanel() {
        return forPanel;
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

    public void setRemoveOneTime(boolean removeOneTime) {
        this.removeOneTime = removeOneTime;
    }

    public void setRemoveSIP(boolean removeSIP) {
        this.removeSIP = removeSIP;
    }

    public void setForPanel(boolean forPanel) {
        this.forPanel = forPanel;
    }

    public ArrayList<Object> getBucketIds() {
        return bucketIds;
    }

    public void setBucketIds(ArrayList<Object> bucketIds) {
        this.bucketIds = bucketIds;
    }
}