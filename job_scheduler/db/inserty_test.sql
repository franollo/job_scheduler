use db_scheduler_test;
insert into groups(group_id, name) values (1, 'test1');
insert into groups(group_id, name) values (2, 'test2');
insert into users(user_id, username, password, first_name, last_name, email, enabled, group_id) 
values(1, 'test1', 'test', 'test', 'test', 'test',1, 1);
insert into users(user_id, username, password, first_name, last_name, email, enabled, group_id) 
values(2, 'test2', 'test', 'test', 'test', 'test',1, 2);
insert into user_roles(role, role_description,user_id) values ('ROLE_ADMIN', 'test admin', 1);
insert into production_plans (production_plan_id, name, group_id) values (1, 'Plan produkcji', 1);
insert into products(product_id, name, description, group_id) 
values(1, 'test1', 'test', 1);
insert into products(product_id, name, description, group_id) 
values(2, 'test2', 'test', 1);
insert into products(product_id, name, description, group_id) 
values(3, 'test3', 'test', 1);
insert into resource_types(resource_type_id, name, group_id) values (1, 'tokarki', 1);
insert into resources (resource_id, name, description, cost_per_hour, efficiency, resource_type_id, group_id)
values(1, 'test1', 'Opis zasobu', 1, 1, 1, 1);
insert into resources (resource_id, name, description, cost_per_hour, efficiency, resource_type_id, group_id)
values(2, 'test2', 'Opis zasobu', 1, 1, 1, 1);
insert into resources (resource_id, name, description, cost_per_hour, efficiency, resource_type_id, group_id)
values(3, 'test3', 'Opis zasobu', 1, 1, 1, 1);
insert into items (production_plan_id, start, end, resource_id, group_id, product_id) 
values (1, '2016-01-01 00:00:00', '2016-01-01 00:00:10', 1, 1, 1);
insert into items (production_plan_id, start, end, resource_id, group_id, product_id) 
values (1, '2016-01-01 00:00:00', '2016-01-01 00:00:10', 1, 1, 1);
insert into orders(order_id, name, description, production_plan_id, group_id) 
values (1, 'test1', 'Opis zamówienia', 1, 1);
insert into orders(order_id, name, description, production_plan_id, group_id) 
values (2, 'test2', 'Opis zamówienia', 1, 1);
insert into order_products values (1, 1, 3, 1);
insert into product_operations (name, description, duration, operation_number, sequential, product_id, resource_id, group_id) 
values ('Operacja', 'Opis operaracji', 600, 1, true, 1, 1, 1);
insert into product_operations (name, description, duration, operation_number, sequential, product_id, resource_id, group_id) 
values ('Operacja', 'Opis operaracji', 1200, 2, true, 1, 2, 1);
insert into product_operations (name, description, duration, operation_number, sequential, product_id, resource_id, group_id) 
values ('Operacja', 'Opis operaracji', 900, 3, true, 1, 3, 1);
insert into product_operations (name, description, duration, operation_number, sequential, product_id, resource_id, group_id) 
values ('Operacja', 'Opis operaracji', 600, 1, true, 2, 1, 1);
insert into product_operations (name, description, duration, operation_number, sequential, product_id, resource_id, group_id) 
values ('Operacja', 'Opis operaracji', 1200, 2, true, 2, 2, 1);
insert into product_operations (name, description, duration, operation_number, sequential, product_id, resource_id, group_id) 
values ('Operacja', 'Opis operaracji', 900, 1, true, 3, 3, 1);

