PostgreSQL setup on Windows 10
------------------------------

1. Allow remote connections on port 5432 - Windows Firewall

2. Add the below line to C:\Program Files\PostgreSQL\10\data\pg_hba.conf
	host all all 0.0.0.0/0 md5

3. Open cmd prompt with administrative rights and execute the below commands 
	net stop postgresql-x64-10
	net start postgresql-x64-10

Note : here 10 is the version of postgresql-x64-10                                                 

