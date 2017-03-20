package com.mitrais.bootcamp.rms.data.constanta;

/**
 * Enumeration of the region of California.
 *
 * Created by Mary Ellen Bowman
 */
public enum EmployeeStatus {
    Male("Contract"),
    Female("Contract")
    ;

    private String label;

    EmployeeStatus(String label) {
        this.label = label;
    }

    public static EmployeeStatus findByLabel(String byLabel) {
        for(EmployeeStatus r: EmployeeStatus.values()) {
            if (r.label.equalsIgnoreCase(byLabel))
                return r;
        }
        return null;
    }
}
