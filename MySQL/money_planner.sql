create database money_planner;

use money_planner;


SET GLOBAL max_allowed_packet=67108864;

-- 1. Users Table
create table users (
	user_id bigint primary key auto_increment,
    username varchar(255) not null unique,
    email varchar(255) not null unique,
    user_password varchar(255) not null,
    created_at timestamp default current_timestamp
) ENGINE=InnoDB;

ALTER TABLE users
ADD COLUMN profile_photo_url VARCHAR(255) DEFAULT NULL,
ADD COLUMN profile_photo_name VARCHAR(255) DEFAULT NULL;

UPDATE users 
SET profile_photo_url = NULL, profile_photo_name = NULL 
WHERE user_id = 11;  -- Replace ? with your user ID

ALTER TABLE users MODIFY COLUMN profile_photo_url LONGTEXT;

-- 2. Incomesources Table
create table incomesources (
	source_id bigint primary key auto_increment,
    user_id_FK bigint not null,
    source_name varchar(255) not null,
    amount decimal(10, 2) not null,
    created_at timestamp default current_timestamp,
	FOREIGN KEY (user_id_FK) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 3. Categories Table
CREATE TABLE categories (
    category_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id_FK BIGINT NOT NULL,
    category_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (category_id),
    FOREIGN KEY (user_id_FK) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB;


-- 4. Expenses Table
create table expenses (
	expense_id bigint primary key auto_increment,
    user_id_FK bigint not null,
    category_id_FK bigint not null,
    amount decimal (10, 2) not null,
    expense_date date not null,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id_FK) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id_FK) REFERENCES categories(category_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 5. Budgets Table
create table budgets (
	budget_id bigint primary key auto_increment,
    user_id_FK bigint not null,
    category_id_FK bigint not null,
    amount decimal (10, 2) not null,
    start_date date not null,
    end_date date not null,
	FOREIGN KEY (user_id_FK) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id_FK) REFERENCES categories(category_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 6. Goals Table
create table goals ( 
	goal_id bigint primary key auto_increment,
    user_id_FK bigint not null,
    goal_name varchar(255) not null,
    goal_amount decimal (10, 2) not null,
    current_amount DECIMAL(10, 2) DEFAULT 0.00,
    deadline DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (user_id_FK) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB;


-- 7. transactions_view
CREATE OR REPLACE VIEW transactions_view AS
SELECT 
    source_id as id,
    'INCOME' as type,
    source_name as name,
    amount,
    created_at,
    user_id_FK as user_id
FROM incomesources
UNION ALL
SELECT 
    e.expense_id as id,
    'EXPENSE' as type,
    c.category_name as name,
    e.amount,
    e.created_at,
    e.user_id_FK as user_id
FROM expenses e
JOIN categories c ON e.category_id_FK = c.category_id
ORDER BY created_at DESC;

-- Database Test
show tables;


use money_planner;
select * from users; -- done
select * from categories; -- done
select * from goals; -- done
select * from budgets; -- done
select * from expenses; -- done
select * from incomesources; -- done
select * from transactions_view;
select * from transactions;


