package com.mitrais.bootcamp.rms.service;

import com.mitrais.bootcamp.rms.data.repository.references.JobFamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobFamilyService {
    private JobFamilyRepository jobFamilyRepository;

    @Autowired
    public JobFamilyService(JobFamilyRepository jobFamilyRepository) {
        this.jobFamilyRepository = jobFamilyRepository;
    }

//    public void initJobFamily() {
//        JobFamily jfSE = new JobFamily();
//        jfSE.setJobFamily("Software Engineer");
//        jfSE.setJfCode("SE");
//        jfSE.setMaxDs(22);
//        jfSE.setMinDs(1);
//        Set<Division> divSE = new HashSet<>();
//        jfSE.setDivisions(divSE);
//
//        Division divCDC = new Division();
//        divCDC.setDivCode("CDC");
//        divCDC.setDivision("CDC");
//        Set<SubDivision> sdCDC = new HashSet<>();
//        divCDC.setSubDivisions(sdCDC);
//        divSE.add(divCDC);
//
//        SubDivision sdJava = new SubDivision();
//        sdJava.setSubDivCode("CDC-JAVA");
//        sdJava.setSubDivision("Java Bootcamp");
//        sdCDC.add(sdJava);
//
//        SubDivision sdPhp = new SubDivision();
//        sdPhp.setSubDivCode("CDC-PHP");
//        sdPhp.setSubDivision("PHP Bootcamp");
//        sdCDC.add(sdPhp);
//
//        Division divSWDBlue = new Division();
//        divSWDBlue.setDivCode("SWD-Blue");
//        divSWDBlue.setDivision("SWD Blue");
//        Set<SubDivision> sdSWDB = new HashSet<>();
//        divSWDBlue.setSubDivisions(sdSWDB);
//        divSE.add(divSWDBlue);
//
//        Division divSWDRed = new Division();
//        divSWDRed.setDivCode("SWD-RED");
//        divSWDRed.setDivision("SWD RED");
//        Set<SubDivision> sdSWDRed = new HashSet<>();
//        divSWDRed.setSubDivisions(sdSWDRed);
//        divSE.add(divSWDRed);
//
//        this.jobFamilyRepository.save(jfSE);
//    }
}
