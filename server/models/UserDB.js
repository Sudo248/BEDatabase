const db = require('../database/db');

module.exports = class UserDB{
    constructor(
        user_id,
        user_name,
        path_image
    ){
        this.user_id = user_id;
        this.user_name = user_name;
        this.path_image = path_image;
    }

    insert(){
        const sql = `
        INSERT INTO users 
        VALUES(
            ${this.user_id},
            '${this.user_name}',
            '${this.path_image}'
        );
        `;
        return db.execute(sql);
    }

    update(){
        const sql = `
        UPDATE users
        SET 
            user_name = '${this.user_name}',
            path_image = '${this.path_image}'
        WHERE  user_id = ${this.user_id};
        `;
        return db.execute(sql);
    }

    static deleteUserById(id){
        const sql = `
        DELETE FROM accounts
        WHERE  user_id = ${id};
        `;
        return db.execute(sql);
    }

    static getAllUser(){
        const sql = `
        SELECT * FROM users;
        `;
        return db.execute(sql);
    }

    static getUserById(id){
        const sql = `
        SELLECT * FROM users
        WHERE  user_id = ${id} LIMIT 1;
        `;
        return db.execute(sql);
    }

    static getUserByUserName(user_name){
        const sql = `
        SELLECT * FROM users
        WHERE user_name = ${user_name} LIMIT 1;
        `;
        return db.execute(sql);
    }

    static getUserByEngId(eng_id){
        const sql = `
        SELECT u.* 
        FROM users u
        INNER JOIN junction_user_eng jue ON u.user_id = jue.user_id
        WHERE jue.eng_id = ${eng_id}
        ORDER BY u.name;
        `;
        return db.execute(sql);
    }

}