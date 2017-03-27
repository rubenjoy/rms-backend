package com.mitrais.bootcamp.rms.data.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class ProjectJobDescConverter implements AttributeConverter<String[], String> {
    @Override
    public String convertToDatabaseColumn(String[] jobDesc) {

        if (jobDesc == null) return "";
        return Arrays.toString(jobDesc);
    }

    @Override
    public String[] convertToEntityAttribute(String jobDesc) {
        if (jobDesc.isEmpty()) return null;
        return jobDesc.substring(1, jobDesc.length() - 1).split(", ");
    }
}
