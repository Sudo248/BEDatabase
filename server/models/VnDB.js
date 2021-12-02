const db = require('../database/db');

module.exports = class VnDB{
    constructor(
        vn_id,
        eng_id,
        content
    ){
        this.vn_id = vn_id;
        this.eng_id = eng_id;
        this.content = content;
    }

    insert(){
        const sql = `
        INSERT INTO vns 
        VALUES(
            ${this.vn_id},
            '${this.eng_id}',
            '${this.content}'
        );
        `;
        return db.execute(sql);
    }

    update(){
        const sql = `
        UPDATE vns
        SET 
            eng_id = '${this.eng_id}',
            content = '${this.content}'
        WHERE vn_id = ${this.vn_id};
        `;
        return db.execute(sql);
    }

    static deleteVnById(id){
        const sql = `
        DELETE FROM vns
        WHERE vn_id = ${id};
        `;
        return db.execute(sql);
    }

    static getAllVn(){
        const sql = `
        SELECT * FROM vns;
        `;
        return db.execute(sql);
    }

    static getVnById(id){
        const sql = `
        SELECT * FROM vns
        WHERE vn_id = ${id} LIMIT 1;
        `;
        return db.execute(sql);
    }

    static getVnByEngId(eng_id){
        const sql = `
        SELECT * FROM vns
        WHERE eng_id = ${eng_id};
        `;
        return db.execute(sql);
    }

    static deleteVnByEngId(eng_id){
        const sql = `
        DELETE FROM vns
        WHERE eng_id = ${id};
        `;
        return db.execute(sql);
    }


    
}