DO
$body$
BEGIN
   IF EXISTS (
      SELECT *
      FROM   pg_catalog.pg_user
      WHERE  usename = 'ekosmiec') THEN
    drop owned by ekosmiec cascade;
    drop user if exists ekosmiec;
    drop schema if exists ekosmiec cascade;
   END IF;
     
    create user ekosmiec with password 'jestemFilatelista';
    create schema authorization ekosmiec;
     
    --rodzaje odpadow
    create table ekosmiec.rodzaje_odpadow(
        id int primary key,
        nazwa text,
        przelicznik real default 1, -- ilosc odpadow w m3 * przelicznik = ilosc odpadow w tonach
        opis text
        );
 
    --grupa kontenerow, ich rodzaj odpadow, czas wywiezienia smieci z grupy/sektora(mozliwe, ze wyleci)
    --do zrobienia: dodac geometry jesli dajemy przelozenie grupy na mape bez okreslenia tego na podstawie wprowadzonych punktow
    create table ekosmiec.grupy(
        id serial primary key,
        nazwa text,
        ref_rodzaj_odpadow int references ekosmiec.rodzaje_odpadow(id),
        czas_wywozu int, --to pole przy odpowiednich zalozeniach bedzie mozna olac
        wstepna_czestotliwosc real, -- domyslna czestotliwosc wywozu smieci w tygodniu
        min_czestotliwosc real, --minimalna czestotliwoliwosc z jaka nalezy odbierac smieci (niezaleznie od automatyczego harmonogramu)
        autoharmonogram boolean,
        poczatek_historii date,
        opis text
        );
 
    --dostepne kosze/kontenery i ich pojemnosci
    create table ekosmiec.rodzaje_kontenerow(
        id serial primary key,
        nazwa text,
        pojemnosc real, -- a moze int?
        opis text
        );
 
 
    --ulokowanie kontenera (określonego rodzaju) w odpowiednim miejscu i grupie. Grupa przechowuje informacje dot. rodzaju odpadów!
    create table ekosmiec.kontenery(
        id serial primary key,
        ref_grupa int references ekosmiec.grupy(id),
        ref_rodzaj_kontenera int references ekosmiec.rodzaje_kontenerow(id),
        lokalizacja geometry,
        opis text
        );
 
    --wygenerowany harmonogram odbioru odpadow
    create table ekosmiec.harmonogram(
        id serial primary key,
        ref_grupa int references ekosmiec.grupy(id),
        data date
        );
 
    --dni pracujace
    create table ekosmiec.dni_robocze(
        id serial primary key,
        dzien_tygodnia int,
        ilosc int default 480
        );

    create table ekosmiec.dni_wolne(
        id serial primary key,
        data date
        );
 
    --raport dot. odebranych smieci
    create table ekosmiec.historia(
        id serial primary key,
        ref_grupa int references ekosmiec.grupy(id),
        data timestamp,
        laczna_pojemnosc real, --a moze int?
        odebrano real, --to samo tu
        opis text
        );
 
    create or replace view ekosmiec.pokaz_kontenery as(
        select
            g.nazwa "grupa",
            ro.nazwa "rodzaj odpadów",
            rk.pojemnosc,
            k.lokalizacja
        from
            ekosmiec.rodzaje_odpadow ro,
            ekosmiec.grupy g,
            ekosmiec.rodzaje_kontenerow rk,
            ekosmiec.kontenery k
        where
            g.ref_rodzaj_odpadow = ro.id and
            k.ref_grupa = g.id and
            k.ref_rodzaj_kontenera = rk.id 
    );
 
    create or replace view ekosmiec.pokaz_harmonogram as(
        select
            g.nazwa "grupa",
            ro.nazwa "rodzaj odpadów",
            h.data
        from
            ekosmiec.rodzaje_odpadow ro,
            ekosmiec.grupy g,
            ekosmiec.harmonogram h
        where
            g.ref_rodzaj_odpadow = ro.id and
            h.ref_grupa = g.id
    );
 
    insert into ekosmiec.rodzaje_odpadow values (1, 'Surowce', 1, 'Papier, plastik, szkło');
    insert into ekosmiec.rodzaje_odpadow values (2, 'Mokre-BIO', 1, 'Odpady kuchenne (opócz mięsa)');
    insert into ekosmiec.rodzaje_odpadow values (3, 'Pozostałe', 1, 'Nie kwalifikujące się do pozostałych kategorii');
 
    insert into ekosmiec.rodzaje_kontenerow (nazwa, pojemnosc, opis) values ('60 litrowy', 60, '');
    insert into ekosmiec.rodzaje_kontenerow (nazwa, pojemnosc, opis) values ('80 litrowy', 80, '');
    insert into ekosmiec.rodzaje_kontenerow (nazwa, pojemnosc, opis) values ('120 litrowy', 120, '');
    insert into ekosmiec.rodzaje_kontenerow (nazwa, pojemnosc, opis) values ('140 litrowy', 140, '');
    insert into ekosmiec.rodzaje_kontenerow (nazwa, pojemnosc, opis) values ('180 litrowy', 180, '');
    insert into ekosmiec.rodzaje_kontenerow (nazwa, pojemnosc, opis) values ('240 litrowy', 240, '');
    insert into ekosmiec.rodzaje_kontenerow (nazwa, pojemnosc, opis) values ('370 litrowy', 370, '');
    insert into ekosmiec.rodzaje_kontenerow (nazwa, pojemnosc, opis) values ('660 litrowy', 660, '');
    insert into ekosmiec.rodzaje_kontenerow (nazwa, pojemnosc, opis) values ('770 litrowy', 770, '');
    insert into ekosmiec.rodzaje_kontenerow (nazwa, pojemnosc, opis) values ('1100 litrowy', 1100, '');
     
    grant all privileges on all tables in schema ekosmiec to ekosmiec;
    GRANT SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA ekosmiec TO ekosmiec;
 
END
$body$