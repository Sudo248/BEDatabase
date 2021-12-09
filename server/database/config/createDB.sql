
CREATE DATABASE bedb;
USE bedb;

CREATE TABLE 
accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(50),
    password VARCHAR(50)
);

CREATE TABLE 
users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50),
    path_image TEXT
);
    

CREATE TABLE 
group_engs (
    group_id INT AUTO_INCREMENT PRIMARY KEY,
    name NVARCHAR(50),
    description NVARCHAR(255)
);
    

CREATE TABLE 
engs (
    eng_id INT AUTO_INCREMENT PRIMARY KEY,
    group_id INT ,
    pronunciation VARCHAR(255),
    content VARCHAR(255),
    type VARCHAR(255),
    path_image TEXT,
    FOREIGN KEY (group_id) REFERENCES group_engs(group_id)
);


CREATE TABLE
vns(
    vn_id INT AUTO_INCREMENT PRIMARY KEY,
    eng_id INT ,
    content NVARCHAR(255),
    FOREIGN KEY (eng_id) REFERENCES engs(eng_id)
);
    

CREATE TABLE
junction_user_eng(
    user_id INT,
    eng_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (eng_id) REFERENCES engs(eng_id)
);
    
