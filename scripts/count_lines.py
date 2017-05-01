counter = 0
with open("story.csv", mode='r', encoding = 'utf-8') as input:
	for line in input:
		counter += 1
print(counter)