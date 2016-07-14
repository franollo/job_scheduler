insert into groups(group_id, name) values (1, 'grupa testowa');
insert into users(user_id, username, password, first_name, last_name, email, group_id) 
values(1, 'admin', 's3cret', 'Marcin', 'Frankowski', 'marcinfrank1@gmail.com', 1);
insert into user_roles(role, role_description,user_id) values ('ROLE_ADMIN', 'test admin', 1);
insert into products(product_id, name, description, group_id) 
values(1, 'rower', 'rower_szosowy', 1);
insert into resource_types(resource_type_id, name, group_id) values (1, 'tokarki', 1);