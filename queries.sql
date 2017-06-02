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

/*b) Print the names of publishers who have series with all series types */
Select P.name
From Publisher P, (Select distinct P.id as id, Count(distinct S.publication_type) as num
                    From Publisher P, Series S
                    Where S.publisher_id = P.id
                    Group by P.id) MyP
Where P.id = MyP. id and MyP.num = (Select Count(distinct S.publication_Type) From Series S);

/*c) Print the 10 most-reprinted characters from Alan Moore's stories. */
Select MyTable.MyName
From (
      Select C.name as MyName, Count(C.name) as MyCount
      From Main M, Feature F, Characters C, (Select S.id as Myid
                                              From Story S, Persons P, Authors A, StoryReprint SR
                                              Where P.id = A.persons_id and Lower(P.name) like '%alan moore%' and A.story_id = S.id and S.id = SR.target_id) Stid
Where ((C.id = M.character_id  and M.story_id in Stid.Myid) OR (C.id = F.character_id and F.story_id in Stid.Myid))
Group By C.name
Order By Count(C.name) desc) MyTable
Where ROWNUM <= 10;

/*d) Print the writers of nature-related stories that have also done the pencilwork in all their nature-related stories. */
Select distinct P.name
From Persons P, Authors A, Pencils Pen, (Select S.id as id
											From Story S, Genre G, Has_Genre HG
											Where S.id = HG.story_id and HG.genre_id = G.id and LOWER(G.name) LIKE '%nature%') MyTable
Where P.id = A.persons_id and P.id = Pen.artist_id and MyTable.id = A.story_id and MyTable.id = Pen.story_id;

/*e) For each of the top-10 publishers in terms of published series, print the 3 most popular languages of their series. */
Select L.id, Count(S.id)
From Series S, Language L, (Select MyPublisher.id as id
                              From (Select P.id as id, Count(S.id)
                                    From Publisher P, IndiciaPublisher IP, Series S
                                    Where IP.publisher_id = P.id and S.publisher_id = P.id
                                    Group by P.id
                                    Order by Count(S.id) desc) MyPublisher
                              Where ROWNUM <= 10) MyTopPublisher
Where S.publisher_id = MyTopPublisher.id and S.language_id = L.id
Group by L.id
Order by Count(S.id) desc

/*f) Print the languages that have more than 10000 original stories published in magazines, along with the
number of those stories. */
Select L.name, Res.num
From (Select SE.language_id as id, Count(SE.language_id) as num
		From Story ST, Series SE, Issue I
		Where ST.issue_id = I.id and I.series_id = SE.id and SE.publication_type = 'magazine'
		Group By SE.language_id
		Having Count(*) > 10000) Res, Language L
Where L.id = Res.id;

/*g) Print all story types that have not been published as a part of Italian magazine series */
Select Type.name
From Story_Type Type
Where Type.id not in (
	Select distinct ST.type_id
	From Story ST, Series SE, Issue I, Language L
	Where ST.issue_id = I.id and I.series_id = SE.id and SE.language_id = L.id and L.name = 'Italian' and SE.publication_type = 'magazine');

/*h) Print the writers of cartoon stories who have worked as writers for more than one indicia publisher */
Select Pe.name
From (Select distinct P.name as name, P.id
		From Persons P, Authors A
		Where P.id = A.persons_id and A.story_id in(
			Select S.id
			From Story S, Story_type ST
			Where S.type_id = ST.id and ST.name = 'cartoon')) Pe, Authors A, Story S, Issue I, IndiciaPublisher IP
		Where Pe.id = A.persons_id and A.story_id = S.id and S.issue_id = I.id and I.indicia_publisher_id = IP.id
		Group by Pe.name
		Having Count(*) > 2;


/*i) Print the 10 brand groups with the highest number of indicia publishers */
Select Br.name
From(Select B.name
		From Brandgroup B, Publisher P, IndiciaPublisher IP
		Where B.id = P.id and P.id = IP.publisher_id
		Group By B.name
		Order By Count(B.name) DESC) Br
Where ROWNUM <= 10;

/*j) Print the average series length (in terms of years) per indicia publisher. */
Select IP.name, ROUND(AVG(CAST(S.year_ended+1 - S.year_began as FLOAT)), 2)
From Series S, Publisher P, IndiciaPublisher IP
Where S.publisher_id = P.id and IP.publisher_id = P.id
Group By IP.name;

/*k) Print the top 10 indicia publishers that have published the most single-issue series */
Select MyIndiciaPublisher.name
From (Select IP.name as name, Count(distinct MyUniqueIssueSeries.id)
		From Publisher P, IndiciaPublisher IP, Series S, (Select MySeries.id as id
                                        From (Select S.id as id, Count(I.id) as num
                                              From Issue I, Series S
                                              Where I.series_id = S.id
                                              Group by S.id) MySeries
                                        Where MySeries.num = 1) MyUniqueIssueSeries
		Where P.id = IP.publisher_id and MyUniqueIssueSeries.id = S.id and S.publisher_id = P.id
		Group by IP.name
		Order by Count(MyUniqueIssueSeries.id) desc) MyIndiciaPublisher
Where ROWNUM <= 10;

/*l) Print the 10 indicia publishers with the highest number of script writers in a single story. */
Select IP1.name
From (
    Select IP.name as name, Count(distinct myStory.id)
    From IndiciaPublisher IP, Issue I, Story S,
        (
        Select A.Story_id as id, Count(A.Persons_id) as num
        From Authors A
        Group By A.Story_id
        Order By Count(A.Persons_id) DESC
        ) myStory
    Where IP.id = I.INDICIA_PUBLISHER_ID AND I.id = S.ISSUE_ID AND myStory.id = S.id
    Group by IP.name
    order by Count(distinct myStory.id) DESC
    ) IP1
Where ROWNUM <= 10 ;
/*m) Print all Marvel heroes that appear in Marvel-DC story crossovers. */
Select distinct C1.name
From
(Select C.id as id, C.name as name
From Indiciapublisher IP, Characters C, Main M, Feature F, Issue I, Story S
Where Lower(IP.name) like '%marvel%' and Lower(IP.name) like '%dc%' and I.indicia_publisher_id = IP.id and S.issue_id = I.id and ((F.story_id = S.id and F.character_id = C.id)
OR (M.story_id = S.id and M.character_id = C.id))) C1,

(Select C.id as id, C.name as name
From Characters C, Main M, Feature F, Issue I, IndiciaPublisher IP, Story S
Where Lower(IP.name) like '%marvel%' and I.indicia_publisher_id = IP.id and S.issue_id = I.id and ((F.story_id = S.id and F.character_id = C.id)
OR (M.story_id = S.id and M.character_id = C.id))) C2

Where C1.id = C2.id;
/*n) Print the top 5 series with most issues */
Select S.name
From Series S, 
    (Select I.series_id as id, Count(I.id)
    From Issue I
    Group by I.series_id
    Order by Count(I.id) desc) MyIssue
Where S.id = MyIssue.id and ROWNUM <= 10

/*o) Given an issue, print its most reprinted story. */
Select S.title
From Issue I, Story S, (Select SR.origin_id as id, Count(SR.target_id)
                        From StoryReprint SR
                        Group by SR.origin_id
                        Order by Count(SR.target_id) desc) MyMostReprinted
Where I.id = elem and I.id = S.issue_id and S.id = MyMostReprinted.id and ROWNUM = 1;
