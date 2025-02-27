CREATE TABLE IF NOT EXISTS "user" (
                    id SERIAL PRIMARY KEY,
                    emailId varchar(50),
                    userName varchar(50),
                    phoneNumber varchar(20),
                    password varchar(20),
                    enabled boolean,
                    token varchar(100)
                    );

CREATE TABLE IF NOT EXISTS verificationtoken (
                    id SERIAL PRIMARY KEY,
                    token varchar(50),
                    emailId varchar(50),
                    expiryDate TIMESTAMP
                    );

CREATE TABLE IF NOT EXISTS userscorecard (
                    userId INT PRIMARY KEY,
                    emailId varchar(50),
                    userName varchar(50),
                    points INT,
                    money INT
                    );

CREATE TABLE IF NOT EXISTS userprediction (
                    emailId varchar(50),
                    matchId INT,
                    bestBatsmen varchar(50),
                    bestBowler varchar(50),
                    manOfTheMatch varchar(50),
                    winner varchar(50),
                    points INT,
                    bet INT,
                    winnings INT
                    );

CREATE TABLE IF NOT EXISTS players (
                    playerId SERIAL PRIMARY KEY,
                    playerName varchar(100),
                    teamName varchar(100)
                    );

CREATE TABLE IF NOT EXISTS matchschedule (
                    matchId SERIAL PRIMARY KEY,
                    teamName1 varchar(100),
                    teamName2 varchar(100),
                    scheduleDate TIMESTAMP,
                    bestBatsmen varchar(50),
                    bestBowler varchar(50),
                    manOfTheMatch varchar(50),
                    winner varchar(50)
                    );