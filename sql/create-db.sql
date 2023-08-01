CREATE TABLE user (
                      id int not null auto_increment,
                      email varchar(255),
                      address varchar(255),
                      PRIMARY KEY (id)
);
CREATE TABLE pizza (
                       id int not null auto_increment,
                       type varchar(255),
                       PRIMARY KEY (id)
);
CREATE TABLE `pizza_order` (
                               id int not null auto_increment,
                               user_id int,
                               pizza_id int,
                               PRIMARY KEY (id),
                               FOREIGN KEY (user_id) REFERENCES user(id),
                               FOREIGN KEY (pizza_id) REFERENCES pizza(id)
);