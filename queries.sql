/*a) Print the brand group names with the highest number of Belgian indicia publishers */
Select BG.name
From BrandGroup BG, (
  Select COUNT(distinct *)
  From Publisher P, (
    Select *
    From IndiciaPublisher IP, Country C
    Where IP.country_id = C.id and C.name = 'Belgium')
  Where P.id = IP.publisher_id)
Where BG.publisher_id = P.id and P.ROWNUM = 1


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
Select S
From Characters C, Has_charaters HC (
  Select S
  From Story S, StoryReprint SR
  Where not exists (select * from  StoryReprint SR where SR.origin_id = S.id)
)
Where C.name = 'Batman' and C.id = HC.character_id and S.id = HC.story_id
