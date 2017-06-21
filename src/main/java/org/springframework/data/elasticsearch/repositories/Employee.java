package org.springframework.data.elasticsearch.repositories;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "employee", type = "employee", shards = 1, replicas = 0, refreshInterval = "-1", indexStoreType = "memory")
public class Employee {



    @Id
    private String pkeyEmployeeID;
    private String strLastName;
    private String strFirstName;
    private String strTitle;

    public String getPkeyEmployeeID() {
        return pkeyEmployeeID;
    }

    public void setPkeyEmployeeID(String pkeyEmployeeID) {
        this.pkeyEmployeeID = pkeyEmployeeID;
    }

    public String getStrLastName() {
        return strLastName;
    }

    public void setStrLastName(String strLastName) {
        this.strLastName = strLastName;
    }

    public String getStrFirstName() {
        return strFirstName;
    }

    public void setStrFirstName(String strFirstName) {
        this.strFirstName = strFirstName;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }


    public void callSetter(String column, String value) {

        if (column.equals("pkeyEmployeeID")) {
            this.setPkeyEmployeeID(value);
        } else if (column.equals("strLastName")) {
            this.setStrLastName(value);
        } else if (column.equals("strFirstName")) {
            this.setStrFirstName(value);
        } else if (column.equals("strTitle")) {
            this.setStrTitle(value);
        }


    }

}
