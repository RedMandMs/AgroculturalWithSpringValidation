Use passport_agricultural
create table authorities (
    username nvarchar(16) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);