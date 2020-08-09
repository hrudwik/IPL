CREATE TABLE IF NOT EXISTS "user" (
                    id SERIAL PRIMARY KEY,
                    emailId varchar(100),
                    userName varchar(100),
                    phoneNumber varchar(100),
                    password varchar(100)
                    )