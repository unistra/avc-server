# This script is only used when separate medias encodage is true

import psycopg2 as dbapi2
db = dbapi2.connect("host='yourhosturl' dbname='univrav' user='sqluser' password='yourpassword'")
cur = db.cursor()


jobid = ""
courseid = ""
status = ""
mediatype = ""
coursetype = ""
mediapath = ""
extension = ""

cur.execute ("SELECT * FROM job WHERE status='waiting' ORDER BY jobid asc LIMIT 1;")
rows = cur.fetchall()
for i, row in enumerate(rows):
	jobid = str(row[0])
	courseid = str(row[1])
	status = str(row[2])
	mediatype = str(row[3])
	coursetype = str(row[4])
	mediapath = str(row[5])
	extension = str(row[6])	


if (jobid != ""):
	cur.execute("UPDATE job SET status='processing' WHERE jobid = %s;",(jobid,))


db.commit()
cur.close()
db.close()

if (jobid != ""):
	print courseid+":"+status+":"+mediatype+":"+coursetype+":"+mediapath+":"+extension
else:
	print ""
