const db = require('../database/db');

module.exports = class AccountDB{
    constructor(
        account_id,
        email,
        password
    ){
        this.account_id = account_id;
        this.email = email;
        this.password = password;
    }

    insert(){
        const sql = `
        INSERT INTO accounts 
        VALUES(
            ${this.account_id},
            '${this.email}',
            '${this.password}'
        );
        `;
        return db.execute(sql);
    }

    update(){
        const sql = `
        UPDATE accounts
        SET 
            email = '${this.email}',
            password = '${this.password}'
        WHERE  account_id = ${this.account_id};
        `;
        return db.execute(sql);
    }

    static deleteAccountById(id){
        const sql = `
        DELETE FROM accounts
        WHERE  account_id = ${id};
        `;
        return db.execute(sql);
    }

    static getAllAccount(){
        const sql = `
        SELECT * FROM accounts;
        `;
        return db.execute(sql);
    }

    static getAccountById(id){
        const sql = `
        SELLECT * FROM accounts
        WHERE  account_id = ${id} LIMIT 1;
        `;
        return db.execute(sql);
    }

    static getAccountByEmail(email){
        const sql = `
        SELLECT * FROM accounts
        WHERE email = ${email} LIMIT 1;
        `;
        return db.execute(sql);
    }


}