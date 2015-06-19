Use passport_agricultural
CREATE TABLE users
(
id int PRIMARY KEY NOT NULL IDENTITY(1,1),
username nvarchar(16) NOT NULL UNIQUE,
password nvarchar(64) NOT NULL,
role nvarchar(16) NOT NULL DEFAULT('USER'),
enabled int NOT NULL DEFAULT(1),
id_organization int,
CONSTRAINT FK_user_organization FOREIGN KEY (id_organization) 
REFERENCES organization_table (id)
)
GO