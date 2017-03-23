package com.mitrais.bootcamp.rms.data.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class ProjectJobDescConverter implements AttributeConverter<String[], String> {
    @Override
    public String convertToDatabaseColumn(String[] jobDesc) {
        return Arrays.toString(jobDesc);
    }

    @Override
    public String[] convertToEntityAttribute(String jobDesc) {
        return jobDesc.substring(1, jobDesc.length() - 1).split(", ");
    }
}
