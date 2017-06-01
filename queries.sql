/*PART 2*/

/*a) Print the brand group names with the highest number of Belgian indicia publishers */
SELECT BG.name  
FROM BrandGroup BG, 
  (	SELECT coupid.pid AS topid
	FROM (	SELECT IP.publisher_id AS pid
			FROM IndiciaPublisher IP, Country C
			WHERE IP.country_id = C.id and C.name = 'Belgium'
			GROUP BY IP.publisher_id
			ORDER BY COUNT(IP.id) DESC) coupid
  WHERE ROWNUM = 1) toppub
WHERE BG.publisher_id = toppub.topid


/*b) Print the ids and names of publishers of Danish book series*/
SELECT P.id, P.name
FROM Publisher P, Series S, Country C
WHERE S.country_id = C.id and C.name = 'Denmark' and S.publisher_id = P.id and S.publication_type = 'book'

/*c) Print the names of all Swiss series that have been published in magazines */
SELECT S.name
FROM Series S, Country C
WHERE S.publication_type = 'magazine' and S.country_id = C.id and C.name = 'Switzerland'

/*d) Starting from 1990, print the number of issues published each year*/
Select I.publication_date, count(I.id)
From Issue I
Where I.publication_date >= 1990
Group By I.publication_date
Order By I.publication_date asc

/*e) Print the number of series of each indicia publisher whose names resembles 'DC comics'*/
Select IP.name, count(S.id)
From IndiciaPublisher IP, Series S, Issue I
Where IP.name LIKE '%DC Comics%' and (I.id = S.first_issue_id or I.id = S.last_issue_id) and I.Indicia_publisher_id = IP.id
Group By IP.name

/*f) Print the titles of the 10 most reprinted stories */
Select title
From (
  Select S.title AS title, COUNT(DISTINCT SR.origin_id) AS count_reprint
  From Story S, StoryReprint SR
  Where S.id = SR.origin_id
  Group By S.id, S.title
  Order By count_reprint DESC)
Where ROWNUM <= 10

/*g) Print the artists that have scripted, drawn, and colored at least one of the stories they where involved in */
Select P.name
From Persons P, Inks I, Colors C, Pencils Pen
Where P.id = I.person_id and P.id = C.person_id and P.id = Pen.artist_id and I.story_id = C.story_id and I.story_id = Pen.story_id
Group By P.name

/*h) Print all non-reprinted stories involving Batman as a non-featured character */
SELECT S.title
FROM Characters C, Feature F, Main M, Story S, (
  SELECT S2.id
  FROM Story S2, StoryReprint SR
  WHERE NOT EXISTS (Select S3.id
                    From Story S3
                    Where SR.origin_id = S3.id
                    Group By S3.id)
  ) noreprint
WHERE LOWER(C.name) LIKE '%batman%' AND
                    C.id = M.character_id AND
                    C.id <> F.character_id AND
                    noreprint.id = M.story_id
					
/*PART 3*/

/*a) Print the series names that have the highest number of issues which contain a story whose type is not the one occurring most frequently in the database */
SELECT SE.name
FROM SERIES SE,
	(SELECT DISTINCT I.name, I.series_id
	FROM ISSUE I, STORY S,
		(SELECT mycount.typeid as id
		FROM (SELECT ST.id AS typeid
				FROM STORY_TYPE ST, STORY S
				WHERE S.type_id = ST.id
				GROUP BY S.type_id
				ORDER BY COUNT(S.id) DESC) mycount
		WHERE ROWNUM = 1) maxtype
	WHERE S.issue_id = I.id AND S.type_id <> maxtype.id) myissues
WHERE myissues.series_id = SE.id AND ROWNUM = 1
GROUP BY myissues.series_id
ORDER BY COUNT(myissues.series_id) DESC



























