import sys, re

def write_relation(name, col2, relation):
	out = open(str(name) + ".csv", mode='w')
	out.write('storyid,' + col2 + '\n')

	for (x,y) in relation:
		out.write(str(x) + ',' + str(y) + '\n')
		
	out.close()
	
def add_to_list(value, mylist):
	if not (value.strip() == '?'):
		if not (value in mylist):
			mylist.append(value)
	
	
path1 = sys.argv[1]
path2 = sys.argv[2]

rel = set()

with open(path2, mode='r', encoding='utf-8') as file:
	for line in file:
		rel.add((line.split(',')[0], line.split(',')[1]))

mylist = list()

first = True

out  = open("res.csv", mode='w', encoding='utf-8')


with open(path1, mode='r', encoding='utf-8') as file:
	for line in file:
		
		if first or not line:
		first = False
		continue
		
		artistid = line.split(',')[0]
		artist = line.split(',')[1]
		
		tokens = artist.split(' ')
		
		if re.search("^\? \(", artist) or re.search("^\(", artist) or re.search("^\{", artist):
			for (x,y) in rel:
				if y == artistid:
					rel.discard((x,y)
		else
			out.write(artistid + ',' + artist + '\n')

write_relation("resrel", "editingid", rel)