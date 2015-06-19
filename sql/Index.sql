Use passport_agricultural
CREATE UNIQUE INDEX IND_cadastr 
   ON dbo.field_table (cadastr_number)
   WHERE  cadastr_number IS NOT NULL;
GO