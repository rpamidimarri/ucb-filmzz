import psycopg2
import sys

def main(argv):
        maxCount = 0
        conn = psycopg2.connect(database="filmzz", user="postgres", password="pass", host="localhost", port="5432")
        cur = conn.cursor()
        cur.execute("SELECT max(executioncount) from ActiveMovie")
        result=cur.fetchone()
        if result != None:
            maxCount=result[0]

        print('Existing maxCount is %d' % (maxCount))
        cur.execute("SELECT tmdbid, tmdbtitle FROM ActiveMovie WHERE executioncount=%s", [maxCount])
        records = cur.fetchall()
	titlemap = {}
        for rec in records:
	    titlemap[rec[0]]=rec[1]
            #print ("(%s, %s)" % (rec[0], rec[1]))

	for keys,values in titlemap.items():
    	    print(keys)
    	    print(values)

if __name__ == "__main__":
   main(sys.argv[1:])
