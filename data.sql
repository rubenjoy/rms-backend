insert into ref_job_family values ('SE', 'Software Engineer', 22, 1);
insert into ref_job_family values ('ADM', 'Administration', 22, 1);
insert into ref_job_family values ('CON', 'Consultant', 22, 1);
insert into ref_job_family values ('FA', 'Finance and accounting', 22, 1);
insert into ref_job_family values ('DSG', 'Designer', 22, 1);
insert into ref_job_family values ('IT', 'IT', 22, 1);
insert into ref_job_family values ('MJF', 'Management', 22, 1);
insert into ref_job_family values ('TR', 'Trainer', 22, 1);
insert into ref_job_family values ('SQ', 'Software Quality', 22, 1);

insert into ref_division values ('CDC', 'CDC', 'SE');
insert into ref_division values ('SWDG', 'SWD Green', 'SE');
insert into ref_division values ('SWDB', 'SWD Blue', 'SE');
insert into ref_division values ('SWDR', 'SWD Red', 'SE');
insert into ref_division values ('TRD', 'Training and Development', 'TR');

insert into ref_subdivision values ('CDC-JAVA', 'Java Bootcamp', 'CDC');
insert into ref_subdivision values ('CDC-PHP', 'PHP Bootcamp', 'CDC');
insert into ref_subdivision values ('BC-1', 'Business Communication 1', 'TRD');
insert into ref_subdivision values ('BC-2', 'Business Communication 2', 'TRD');

insert into ref_jf_level values ('SE-JP', 'SE-JP', 3, 1, 'SE');
insert into ref_jf_level values ('SE-PG', 'SE-PG', 7, 4, 'SE');
insert into ref_jf_level values ('SE-AP', 'SE-AP', 13, 8, 'SE');
insert into ref_jf_level values ('SE-AN', 'SE-AN', 22, 14, 'SE');
insert into ref_jf_level values ('TR-1', 'TR-1', 3, 1, 'TR');
insert into ref_jf_level values ('TR-2', 'TR-2', 7, 4, 'TR');
insert into ref_jf_level values ('TR-3', 'TR-3', 13, 8, 'TR');
insert into ref_jf_level values ('TR-4', 'TR-4', 22, 14, 'TR');
insert into ref_jf_level values ('FA-1', 'FA-1', 3, 1, 'FA');
insert into ref_jf_level values ('FA-2', 'FA-2', 7, 4, 'FA');
insert into ref_jf_level values ('FA-3', 'FA-3', 13, 8, 'FA');
insert into ref_jf_level values ('FA-4', 'FA-4', 22, 14, 'FA');

INSERT INTO ref_office_address VALUES ('Bandung', 'Bandung', '40164', 'Jawa Barat', 'Jl Surya Sumantri no 8D');
INSERT INTO ref_office_address VALUES ('Yogyakarta', 'Umbulharjo', '55165', 'Yogyakarta', 'Jln Sidobali No 2, Muja Muju');
INSERT INTO ref_office_address VALUES ('Bali-Suwung', 'Suwung', '80223', 'Bali', 'Jln Bypass Ngurah Rai gg. Mina Utama No. 1');