insert into groups(group_id, name) values (1, 'grupa testowa');
insert into users(user_id, username, password, first_name, last_name, email, enabled, group_id) 
values(1, 'admin', 's3cret', 'Marcin', 'Frankowski', 'marcinfrank1@gmail.com',1, 1);
insert into user_roles(role, role_description,user_id) values ('ROLE_ADMIN', 'test admin', 1);
insert into production_plans (name, group_id) values ('Plan produkcji', 1);
insert into products(product_id, name, description, group_id) 
values(1, 'rower', 'rower_szosowy', 1);
insert into resource_types(resource_type_id, name, group_id) values (1, 'tokarki', 1);
insert into resources (name, description, cost_per_hour, efficiency, resource_type_id, group_id)
values('Zasób', 'Opis zasobu', 1, 1, 1, 1);
insert into items (production_plan_id, start, end, resource_id, group_id) 
values (1, '2016-01-01 00:00:00', '2016-01-01 00:00:10', 1, 1);
insert into orders(name, description, production_plan_id, group_id) 
values ('Zamówienie', 'Opis zamówienia', 1, 1);
insert into order_products values (1, 1, 3, 1);
insert into product_operations (name, description, product_id, resource_type_id, group_id) 
values ('Operacja', 'Opis operaracji', 1, 1, 1);