const db = require('../database/db');

module.exports = class JunctionUserEng{
    constructor(
        user_id,
        eng_id
    ){
        this.user_id = user_id;
        this.eng_id = eng_id;
    }

    insert(){
        const sql = `
        INSERT INTO junction_user_eng 
        VALUES(
            ${this.user_id},
            ${this.eng_id}
        );
        `;
        return db.execute(sql);
    }

    updateJunctionByUserId(){
        const sql = `
        UPDATE junction_user_eng
        SET 
            eng_id = ${this.eng_id}
        WHERE  user_id = ${this.user_id};
        `;
        return db.execute(sql);
    }

    updateJunctionByEngId(){
        const sql = `
        UPDATE junction_user_eng
        SET 
            user_id = ${this.user_id}
        WHERE eng_id = ${this.eng_id};
        `;
        return db.execute(sql);
    }

    static deleteJunctionByUserId(id){
        const sql = `
        DELETE FROM junction_user_eng
        WHERE  user_id = ${id};
        `;
        return db.execute(sql);
    }

    static deleteJunctionByEngId(id){
        const sql = `
        DELETE FROM junction_user_eng
        WHERE  eng_id = ${id};
        `;
        return db.execute(sql);
    }

    static getAllJunction(){

    }
    static getJunctionByUserId(user_id){

        const sql = `
        SELLECT * FROM junction_user_eng
        WHERE user_id = ${user_id};
        `;
        return db.execute(sql);

    }
    static getJuctionByEngId(eng_id){
        const sql = `
        SELLECT * FROM junction_user_eng
        WHERE eng_id = ${eng_id};
        `;
        return db.execute(sql);
    }

    
}