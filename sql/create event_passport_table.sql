CREATE TABLE event_passport_table
   (id int PRIMARY KEY NOT NULL IDENTITY(1,1),
    id_passport int NOT NULL,
	CONSTRAINT FK_event_passport FOREIGN KEY (id_passport) 
    REFERENCES field_table (id),
	id_organization int NOT NULL,
	CONSTRAINT FK_event_organization FOREIGN KEY (id_organization) 
    REFERENCES organization_table (id),
	message_event nvarchar(100) NOT NULL,
    date_time_event datetime NOT NULL, 
	type_event varchar(20) NOT NULL)
GO