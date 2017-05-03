/*a) Print the brand group names with the highest number of Belgian indicia publishers */
SELECT BG.NAME
FROM BrandGroup BG, IndiciaPublisher IP, Publisher P, Country C
WHERE
/*b) Print the ids and names of publishers of Danish book series*/
SELECT P.id, P.name
FROM Publisher P, Series S, Country C
WHERE S.country_id = C.id and C.name = "Danemark" and S.publisher_id = P.id
/*c) Print the names of all Swiss series that have been published in magazines */
SELECT S.name
FROM Series S, Country C, Serie_type T, Has_Serie_Type H
WHERE H.serie_id = H.type_id and Serie_type.name = "magazine" and S.country_id = C.id and C.name = "Switzerland"
/*d) Starting from 1990, print the number of issues published each year*/
/*e) Print the number of series of each indicia publisher whose names resembles 'DC comics'*/
/*f) Print the titles of the 10 most reprinted stories */
/*g) Print the artists that have scripted, drawn, and colored at least one of the stories they where involved in */
/*h) Print all non-reprinted stories involving Batman as a non-featured character */

b) Print the ids and names of publishers of Danish book series.
Select P.id, P.name
From Publisher P, Series S, Country C
Where S.country_id = C.id and C.name = "Danemark" and S.publisher_id = P.id

c) Print the names of all Swiss series that have been published in magazines.
Select S.name
From Series S, Country C, Serie_Type T, Has_Serie_Type H
Where H.serie_id = and Serie_type.name = "magazine" and S.country_id = C.id and C.name = "Switzerland"

d) Starting from 1990, print the number of issues published each year.
Select *
From Issue I
Group By I.publication_date
Where I.publication_date >= 1990

e) Print the number of series for each indicia publisher whose name resembles ‘DC comics’.
Select Count(distinct *)
From Issue I, Series S
Where (I.id = S.first_issue_id or I.id = S.last_issue_id) and I.Indiciapublisher_id IN(
Select IP.id
From IndiciaPublisher IP
Where IP.name = "DC comics")

f) Print the titles of the 10 most reprinted stories


g) Print the artists that have scripted, drawn, and colored at least one of the stories they were involved in.


h) Print all non-reprinted stories involving Batman as a non-featured character.
