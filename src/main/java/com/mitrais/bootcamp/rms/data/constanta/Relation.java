package com.mitrais.bootcamp.rms.data.constanta;

/**
 * Enumeration of the region of California.
 *
 * Created by Mary Ellen Bowman
 */
public enum Relation {
    Husband("Husband"),
    Wife("Wife"),
    Daughter("Daughter"),
    Son("Son")
    ;

    private String label;

    Relation(String label) {
        this.label = label;
    }

    public static Relation findByLabel(String byLabel) {
        for(Relation r: Relation.values()) {
            if (r.label.equalsIgnoreCase(byLabel))
                return r;
        }
        return null;
    }
}
