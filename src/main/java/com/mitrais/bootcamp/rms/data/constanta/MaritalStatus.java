package com.mitrais.bootcamp.rms.data.constanta;

/**
 * Enumeration of the region of California.
 *
 * Created by Mary Ellen Bowman
 */
public enum MaritalStatus {
    Single("Single"),
    Married("Married"),
    Divorced("Divorced"),
    ;

    private String label;

    MaritalStatus(String label) {
        this.label = label;
    }

    public static MaritalStatus findByLabel(String byLabel) {
        for(MaritalStatus r: MaritalStatus.values()) {
            if (r.label.equalsIgnoreCase(byLabel))
                return r;
        }
        return null;
    }
}
