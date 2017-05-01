def write_table(name, col2, values):
	out = open(str(name) + ".csv", mode='w')
	out.write("storyid," + col2 + '\n')
	
	for id in range(0,len(values)):
		out.write(str(id) + ',' + values[id] + '\n')
		
	out.close()

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
			
def add_to_relation(value, id, relation, mylist):
	if value in mylist:
		vid = mylist.index(value)
	else:
		print("Error: value not in list")
	relation.add((id, vid))

def process_new(values, id, relation, mylist):
	for value in values.strip().split(';'):
		value = value.strip()
		if not value:
			return
		if (value == '?' or value == '??' or value == '???'):
			return
	
		add_to_list(value, mylist)
		add_to_relation(value, id, relation, mylist)
	
ids = list()
titles = list()
features = list()
issue_ids = list()
synopsi = list()
reprint_notei = list()
notei = list()
type_ids = list()
	
scriptl = list()
artistsl = list()
lettersl = list()
editingl = list()
genrel = list()
charactersl = list()

scriptr = set()
pencilsr = set()
inksr = set()
colorsr = set()
lettersr = set()
editingr = set()
genrer = set()
charactersr = set()

counter = 0
first = True

with open("story.csv", mode='r', encoding = 'utf-8') as input:
	for line in input:
		if first:
			first = False
			continue
			
		tokens = line.split(',')
		
		if len(tokens) != 16:
			print("error at line" + str(counter))
			
		id = tokens[0]
		ids.append(id)
		titles.append(tokens[1])
		features.append(tokens[2])
		issue_ids.append(tokens[3])
		synopsi.append(tokens[12])
		reprint_notei.append(tokens[13])
		notei.append(tokens[14])
		type_ids.append(tokens[15])
		
		process_new(tokens[4], id, scriptr, scriptl)
		process_new(tokens[5], id, pencilsr, artistsl)
		process_new(tokens[6], id, inksr, artistsl)
		process_new(tokens[7], id, colorsr, artistsl)
		process_new(tokens[8], id, lettersr, lettersl)
		process_new(tokens[9], id, editingr, editingl)
		process_new(tokens[10], id, genrer, genrel)
		process_new(tokens[11], id, charactersr, charactersl)
			
		print("processed line: " + str(counter))
		counter += 1
		
		if counter > 20000:
			break
			
print("writing tables")
write_table("script", "scriptid", scriptl)
write_table("artists", "artistid", artistsl)
write_table("letters", "lettersid", lettersl)
write_table("editing", "editingid", editingl)
write_table("genre", "genreid", genrel)
write_table("characters", "characterid", charactersl)

print("writing relations")
write_relation("wrote_script", "scriptid", scriptr)
write_relation("pencilsr", "artistid", pencilsr)
write_relation("inksr", "artistid", inksr)
write_relation("colorsr", "artistid", colorsr)
write_relation("lettersr", "lettersid", lettersr)
write_relation("editingr", "editingid", editingr)
write_relation("genrer", "genreid", genrer)
write_relation("charactersr", "characterid", charactersr)



print("writing new story.csv")
final_out = open("new_story.csv", mode='w', encoding='utf-8')
final_out.write("id,title,feature,issue_id,synopsis,reprint_notes,notes,type_id\n")
for i in range(0, counter):
	final_out.write(str(ids[i]) + ',' + str(titles[i]) + ',' + str(features[i]) + ',' + str(issue_ids[i]) + ',' + str(synopsi[i]) + ',' + str(reprint_notei[i]) + ',' + str(notei[i]) + ',' + str(type_ids[i]) + '\n')
final_out.close()



			