set schema 'ipl';
DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS userscorecard;
DROP TABLE IF EXISTS userprediction;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS matchschedule;
DROP TABLE IF EXISTS verificationtoken;
delete from flyway_schema_history;