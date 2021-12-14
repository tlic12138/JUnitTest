create table IF NOT EXISTS t_user(
	id int(10) primary key auto_increment,
	username varchar(200),
	password varchar(200),
	nickname varchar(200)
)