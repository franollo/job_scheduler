-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema scheduler_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `scheduler_db` ;

-- -----------------------------------------------------
-- Schema scheduler_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `scheduler_db` DEFAULT CHARACTER SET utf8 ;
USE `scheduler_db` ;

-- -----------------------------------------------------
-- Table `scheduler_db`.`PERSON`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler_db`.`PERSON` ;

CREATE TABLE IF NOT EXISTS `scheduler_db`.`PERSON` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(50) NULL DEFAULT NULL,
  `EMAIL` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `scheduler_db`.`groups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler_db`.`groups` ;

CREATE TABLE IF NOT EXISTS `scheduler_db`.`groups` (
  `group_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`group_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `scheduler_db`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler_db`.`orders` ;

CREATE TABLE IF NOT EXISTS `scheduler_db`.`orders` (
  `order_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `description` VARCHAR(200) NULL DEFAULT NULL,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NULL DEFAULT NULL,
  `creation_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 53
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `scheduler_db`.`jobs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler_db`.`jobs` ;

CREATE TABLE IF NOT EXISTS `scheduler_db`.`jobs` (
  `job_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  `order` TINYINT(4) NOT NULL DEFAULT '0',
  `in_order` TINYINT(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`job_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `scheduler_db`.`resources`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler_db`.`resources` ;

CREATE TABLE IF NOT EXISTS `scheduler_db`.`resources` (
  `resource_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '			',
  `name` VARCHAR(20) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`resource_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `scheduler_db`.`tasks`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler_db`.`tasks` ;

CREATE TABLE IF NOT EXISTS `scheduler_db`.`tasks` (
  `resource_id` INT(11) NOT NULL,
  `job_id` INT(11) NOT NULL,
  `duration` INT(11) NULL DEFAULT NULL,
  `order` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`resource_id`, `job_id`),
  UNIQUE INDEX `order_UNIQUE` (`order` ASC, `job_id` ASC),
  INDEX `fk_tasks_resources1_idx` (`resource_id` ASC),
  INDEX `fk_tasks_jobs1` (`job_id` ASC),
  CONSTRAINT `fk_tasks_jobs1`
    FOREIGN KEY (`job_id`)
    REFERENCES `scheduler_db`.`jobs` (`job_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tasks_resources1`
    FOREIGN KEY (`resource_id`)
    REFERENCES `scheduler_db`.`resources` (`resource_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `scheduler_db`.`items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler_db`.`items` ;

CREATE TABLE IF NOT EXISTS `scheduler_db`.`items` (
  `item_id` INT(11) NOT NULL COMMENT '					',
  `start_date` DATETIME NULL DEFAULT NULL,
  `end_date` DATETIME NULL DEFAULT NULL,
  `resource_id` INT(11) NOT NULL,
  `job_id` INT(11) NOT NULL,
  `order_id` INT(11) NOT NULL,
  `color` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`item_id`, `order_id`),
  INDEX `fk_items_tasks1_idx` (`resource_id` ASC, `job_id` ASC),
  INDEX `fk_items_orders1_idx` (`order_id` ASC),
  CONSTRAINT `fk_items_orders1`
    FOREIGN KEY (`order_id`)
    REFERENCES `scheduler_db`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_items_tasks1`
    FOREIGN KEY (`resource_id` , `job_id`)
    REFERENCES `scheduler_db`.`tasks` (`resource_id` , `job_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `scheduler_db`.`persistent_logins`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler_db`.`persistent_logins` ;

CREATE TABLE IF NOT EXISTS `scheduler_db`.`persistent_logins` (
  `username` VARCHAR(64) NOT NULL,
  `series` VARCHAR(64) NOT NULL,
  `token` VARCHAR(64) NOT NULL,
  `last_used` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `scheduler_db`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler_db`.`users` ;

CREATE TABLE IF NOT EXISTS `scheduler_db`.`users` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '	',
  `username` VARCHAR(25) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `first_name` VARCHAR(20) NULL DEFAULT NULL,
  `surname` VARCHAR(20) NULL DEFAULT NULL,
  `enabled` TINYINT(4) NOT NULL,
  `in_use` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `scheduler_db`.`user_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler_db`.`user_roles` ;

CREATE TABLE IF NOT EXISTS `scheduler_db`.`user_roles` (
  `user_role_id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC, `role` ASC),
  CONSTRAINT `fk_user_roles_users1`
    FOREIGN KEY (`username`)
    REFERENCES `scheduler_db`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `scheduler_db`.`users_groups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler_db`.`users_groups` ;

CREATE TABLE IF NOT EXISTS `scheduler_db`.`users_groups` (
  `user_id` INT(11) NOT NULL,
  `group_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `group_id`),
  INDEX `fk_users_grops_users_idx` (`user_id` ASC),
  INDEX `fk_users_grops_groups1_idx` (`group_id` ASC),
  CONSTRAINT `fk_users_grops_groups1`
    FOREIGN KEY (`group_id`)
    REFERENCES `scheduler_db`.`groups` (`group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_grops_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `scheduler_db`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `scheduler_db`.`users_jobs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler_db`.`users_jobs` ;

CREATE TABLE IF NOT EXISTS `scheduler_db`.`users_jobs` (
  `access_type` VARCHAR(45) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `job_id` INT(11) NOT NULL,
  PRIMARY KEY (`job_id`, `user_id`),
  INDEX `fk_users_jobs_users1_idx` (`user_id` ASC),
  INDEX `fk_users_jobs_jobs1_idx` (`job_id` ASC),
  CONSTRAINT `fk_users_jobs_jobs1`
    FOREIGN KEY (`job_id`)
    REFERENCES `scheduler_db`.`jobs` (`job_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_jobs_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `scheduler_db`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `scheduler_db`.`users_orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler_db`.`users_orders` ;

CREATE TABLE IF NOT EXISTS `scheduler_db`.`users_orders` (
  `access_type` VARCHAR(45) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `order_id` INT(11) NOT NULL,
  PRIMARY KEY (`order_id`, `user_id`),
  INDEX `fk_users_orders_users_idx` (`user_id` ASC),
  INDEX `fk_users_orders_orders1_idx` (`order_id` ASC),
  CONSTRAINT `fk_users_orders_orders1`
    FOREIGN KEY (`order_id`)
    REFERENCES `scheduler_db`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_orders_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `scheduler_db`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `scheduler_db`.`users_resources`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler_db`.`users_resources` ;

CREATE TABLE IF NOT EXISTS `scheduler_db`.`users_resources` (
  `access_type` VARCHAR(45) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `resource_id` INT(11) NOT NULL,
  PRIMARY KEY (`resource_id`, `user_id`),
  INDEX `fk_users_resources_users1_idx` (`user_id` ASC),
  INDEX `fk_users_resources_resources1_idx` (`resource_id` ASC),
  CONSTRAINT `fk_users_resources_resources1`
    FOREIGN KEY (`resource_id`)
    REFERENCES `scheduler_db`.`resources` (`resource_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_resources_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `scheduler_db`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `scheduler_db` ;

-- -----------------------------------------------------
-- procedure create_item
-- -----------------------------------------------------

USE `scheduler_db`;
DROP procedure IF EXISTS `scheduler_db`.`create_item`;

DELIMITER $$
USE `scheduler_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_item`(
					start_date varchar(20),
                    end_date varchar(20),
                    resource_id int(11),
                    job_id int(11),
                    order_id int(11),
					color varchar(20)
                    )
BEGIN
	
    select max(i.item_id)
    into @max_id
    from items i
    where i.order_id = order_id;
    
    if(@max_id is null) then
		set @max_id = 1;
	else
		set @max_id = @max_id + 1;
	end if;

	insert into items 
    values(@max_id, start_date, end_date, resource_id, job_id, order_id, color);

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure create_job
-- -----------------------------------------------------

USE `scheduler_db`;
DROP procedure IF EXISTS `scheduler_db`.`create_job`;

DELIMITER $$
USE `scheduler_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_job`(job_name varchar(45), 
						description varchar(45), 
                        in_order tinyint(4),
                        username varchar(45),
                        out job_id int(11))
BEGIN

	insert into 
    jobs(name, description, in_order)
    values(job_name, description, in_order);
    
    set job_id = last_insert_id();
    
    insert into
    users_jobs values('OWNER', (select user_id from users u where u.username = username), job_id);

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure create_order
-- -----------------------------------------------------

USE `scheduler_db`;
DROP procedure IF EXISTS `scheduler_db`.`create_order`;

DELIMITER $$
USE `scheduler_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_order`(order_name varchar(45), 
						description varchar(45),
						start_date datetime,
                        username varchar(45),
                        out order_id int(11))
BEGIN

	insert into 
    orders(name, description, start_date)
    values(order_name, description, start_date);
    
    set order_id = last_insert_id();
    
    insert into
    users_orders values('OWNER', (select user_id from users u where u.username = username), order_id);
    
    update users u
    set in_use = order_id
    where u.username = username;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure create_resource
-- -----------------------------------------------------

USE `scheduler_db`;
DROP procedure IF EXISTS `scheduler_db`.`create_resource`;

DELIMITER $$
USE `scheduler_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_resource`(resource_name varchar(45), 
						description varchar(45), 
                        username varchar(45),
                        out resource_id int(11))
BEGIN

	insert into 
    resources(name, description)
    values(resource_name, description);
    
    set resource_id = last_insert_id();
    
    insert into
    users_resources values('OWNER', (select user_id from users u where u.username = username), resource_id);

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure create_task
-- -----------------------------------------------------

USE `scheduler_db`;
DROP procedure IF EXISTS `scheduler_db`.`create_task`;

DELIMITER $$
USE `scheduler_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_task`(resource_id int(11), 
						job_id int(11), 
                        duration int(11),
                        `order` int(11))
BEGIN

	insert into 
    tasks
    values(resource_id, job_id, duration, `order`);

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure delete_job
-- -----------------------------------------------------

USE `scheduler_db`;
DROP procedure IF EXISTS `scheduler_db`.`delete_job`;

DELIMITER $$
USE `scheduler_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_job`(
				job_id int(11),
                name varchar(20),
                desciption varchar(40),
                username varchar(20))
BEGIN
	
    select uj.access_type
    into @access
    from users_jobs uj
    where user_id in (
		select user_id from users u where u.username = username
	)
    and uj.job_id = job_id;
    
    if (@access = 'OWNER') then
		update jobs j
        set j.name = name,
        j.description = description
        where j.job_id = job_id;
	end if;
        
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure delete_resource
-- -----------------------------------------------------

USE `scheduler_db`;
DROP procedure IF EXISTS `scheduler_db`.`delete_resource`;

DELIMITER $$
USE `scheduler_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_resource`(
				resource_id int(11),
                username varchar(20))
BEGIN
	
    select ur.access_type
    into @access
    from users_resources ur
    where ur.user_id in (
		select user_id from users u where u.username = username
	)
    and ur.resource_id = resource_id;
    
    if (@access = 'OWNER') then
		delete from users_resources where users_resources.resource_id = resource_id;
        delete from items where items.resource_id = resource_id;
        delete from tasks where tasks.resource_id = resource_id;
        delete from resources where resources.resource_id = resource_id;
	end if;
        
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure get_max_date
-- -----------------------------------------------------

USE `scheduler_db`;
DROP procedure IF EXISTS `scheduler_db`.`get_max_date`;

DELIMITER $$
USE `scheduler_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_max_date`(
					username varchar(20),
					out max_date DATETIME)
BEGIN
	
    select max(start_date)
    into max_date
    from items
    where order_id in (
		select u.in_use from users u where u.username = username
	);
    
    if(max_date is null) then
		select start_date 
        into max_date
        from orders where order_id in (
			select u.in_use from users u where u.username = username
		);
	end if;
    
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure set_in_use
-- -----------------------------------------------------

USE `scheduler_db`;
DROP procedure IF EXISTS `scheduler_db`.`set_in_use`;

DELIMITER $$
USE `scheduler_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `set_in_use`(username varchar(20), order_id int(11))
BEGIN
    select user_id into @_user_id from users u where username = u.username;
    update users u set u.in_use = 
    (select order_id from users_orders uo where uo.user_id = @_user_id and uo.order_id = order_id)
    where user_id = @_user_id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure update_item
-- -----------------------------------------------------

USE `scheduler_db`;
DROP procedure IF EXISTS `scheduler_db`.`update_item`;

DELIMITER $$
USE `scheduler_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_item`(
							start_date varchar(20), 
                            end_date varchar(20), 
                            name varchar(20),
                            item_id int(11))
BEGIN

	select in_use 
    into @order_id 
    from users 
    where username = name;
    
    select start_date
    into @order_start
    from orders
    where order_id = @order_id;
    
    update items i set
    i.start_date = start_date,
    i.end_date = end_date
    where i.order_id = @order_id
    and i.item_id = item_id;
    
    select min(i.start_date) 
    into @min_date
    from items i 
    where i.order_id = @order_id;
    
    if(@min_date != @order_start) then
		update orders set start_date = @min_date where order_id = @order_id;
	end if;
    
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure update_job
-- -----------------------------------------------------

USE `scheduler_db`;
DROP procedure IF EXISTS `scheduler_db`.`update_job`;

DELIMITER $$
USE `scheduler_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_job`(
				job_id int(11),
                name varchar(20),
                description varchar(40),
                username varchar(20))
BEGIN
	
    select uj.access_type
    into @access
    from users_jobs uj
    where user_id in (
		select user_id from users u where u.username = username
	)
    and uj.job_id = job_id;
    
    if (@access = 'OWNER') then
		update jobs j
        set j.name = name,
        j.description = description
        where j.job_id = job_id;
	end if;
        
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure update_order
-- -----------------------------------------------------

USE `scheduler_db`;
DROP procedure IF EXISTS `scheduler_db`.`update_order`;

DELIMITER $$
USE `scheduler_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_order`(
				order_id int(11),
				name varchar(45),
                description varchar(45),
                start_date varchar(45),
                username varchar(45))
BEGIN

	select order_id
    into @order_id
    from users_orders uo 
    where uo.order_id = order_id 
    and uo.user_id in (select user_id from users u where u.username = username)
    and access_type = 'OWNER';
    
    if(@order_id is not null) then
		update orders o
        set o.order_id = order_id,
        o.name = name,
        o.description = description,
        o.start_date = start_date
        where o.order_id = @order_id;
        select TIMESTAMPDIFF(SECOND, (select min(i.start_date) from items i where i.order_id = @order_id),start_date)
        into @diff;
        if(@diff is not null and @diff != 0) then
			update items i
			set i.start_date = DATE_ADD(i.start_date, INTERVAL @diff SECOND),
			i.end_date = DATE_ADD(i.end_date, INTERVAL @diff SECOND)
			where i.order_id = @order_id;
		end if;
    end if;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure update_resource
-- -----------------------------------------------------

USE `scheduler_db`;
DROP procedure IF EXISTS `scheduler_db`.`update_resource`;

DELIMITER $$
USE `scheduler_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_resource`(
				resource_id int(11),
                name varchar(20),
                description varchar(40),
                username varchar(20))
BEGIN
	
    select ur.access_type
    into @access
    from users_resources ur
    where ur.user_id in (
		select user_id from users u where u.username = username
	)
    and ur.resource_id = resource_id;
    
    if (@access = 'OWNER') then
		update resources r
        set r.name = name,
        r.description = description
        where r.resource_id = resource_id;
	end if;
        
END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
