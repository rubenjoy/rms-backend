package com.mitrais.bootcamp.rms.data.constanta;

/**
 * Enumeration of the region of California.
 *
 * Created by Mary Ellen Bowman
 */
public enum Gender {
    Male("Male"),
    Female("Female")
    ;

    private String label;

    Gender(String label) {
        this.label = label;
    }

    public static Gender findByLabel(String byLabel) {
        for(Gender r: Gender.values()) {
            if (r.label.equalsIgnoreCase(byLabel))
                return r;
        }
        return null;
    }
}
