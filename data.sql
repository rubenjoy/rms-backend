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
