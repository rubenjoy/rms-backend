insert into const_job_family values ("SE", "Software Engineer", 1, 22);
insert into const_job_family values ("ADM", "Administration", 1, 22);
insert into const_job_family values ("CON", "Consultant", 1, 22);
insert into const_job_family values ("FA", "Finance and accounting", 1, 22);
insert into const_job_family values ("DSG", "Designer", 1, 22);
insert into const_job_family values ("IT", "IT", 1, 22);
insert into const_job_family values ("MJF", "Management", 1, 22);
insert into const_job_family values ("TR", "Trainer", 1, 22);
insert into const_job_family values ("SQ", "Software Quality", 1, 22);

insert into const_division values ("CDC", "CDC");
insert into const_division values ("SWDG", "SWD Green");
insert into const_division values ("SWDB", "SWD Blue");
insert into const_division values ("SWDR", "SWD Red");
insert into const_division values ("TRD", "Training and Development");

insert into const_job_family_divisions values ("SE", "CDC");
insert into const_job_family_divisions values ("SE", "SWDG");
insert into const_job_family_divisions values ("SE", "SWDB");
insert into const_job_family_divisions values ("SE", "SWDR");
insert into const_job_family_divisions values ("TR", "TRD");

insert into const_subdivision values ("CDC-JAVA", "Java Bootcamp");
insert into const_subdivision values ("CDC-PHP", "PHP Bootcamp");
insert into const_subdivision values ("BC-1", "Business Communication 1");
insert into const_subdivision values ("BC-2", "Business Communication 2");

insert into const_division_sub_divisions values ("CDC", "CDC-JAVA");
insert into const_division_sub_divisions values ("CDC", "CDC-PHP");
insert into const_division_sub_divisions values ("TRD", "BC-1");
insert into const_division_sub_divisions values ("TRD", "BC-2");

insert into const_jf_level values ("SE-JP", "SE-JP", 3, 1);
insert into const_jf_level values ("SE-PG", "SE-PG", 7, 4);
insert into const_jf_level values ("SE-AP", "SE-AP", 13, 8);
insert into const_jf_level values ("SE-AN", "SE-AN", 22, 14);
insert into const_jf_level values ("TR-1", "TR-1", 3, 1);
insert into const_jf_level values ("TR-2", "TR-2", 7, 4);
insert into const_jf_level values ("TR-3", "TR-3", 13, 8);
insert into const_jf_level values ("TR-4", "TR-4", 22, 14);
insert into const_jf_level values ("FA-1", "FA-1", 3, 1);
insert into const_jf_level values ("FA-2", "FA-2", 7, 4);
insert into const_jf_level values ("FA-3", "FA-3", 13, 8);
insert into const_jf_level values ("FA-4", "FA-4", 22, 14);

insert into const_job_family_jf_levels values ("SE", "SE-JP");
insert into const_job_family_jf_levels values ("SE", "SE-PG");
insert into const_job_family_jf_levels values ("SE", "SE-AP");
insert into const_job_family_jf_levels values ("SE", "SE-AN");
insert into const_job_family_jf_levels values ("TR", "TR-1");
insert into const_job_family_jf_levels values ("TR", "TR-2");
insert into const_job_family_jf_levels values ("TR", "TR-3");
insert into const_job_family_jf_levels values ("TR", "TR-4");
insert into const_job_family_jf_levels values ("FA", "FA-1");
insert into const_job_family_jf_levels values ("FA", "FA-2");
insert into const_job_family_jf_levels values ("FA", "FA-3");
insert into const_job_family_jf_levels values ("FA", "FA-4");
