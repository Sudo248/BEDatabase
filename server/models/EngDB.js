const db = require('../database/db');

module.exports = class EngDB{
    constructor(
        eng_id,
        group_id,
        pronunciation,
        content,
        type,
        path_image
    ){
        this.eng_id = eng_id;
        this.group_id = group_id;
        this.pronunciation = pronunciation;
        this.content = content;
        this.type = type;
        this.path_image = path_image;
    }

    insert(){
        const sql = `
        INSERT INTO engs
        VALUES(
            ${this.eng_id},
            ${this.group_id},
            '${this.pronunciation}',
            '${this.content}',
            '${this.type}',
            '${this.path_image}'
        );
        `;
        return db.execute(sql);
    }

    update(){
        const sql = `
        UPDATE engs
        SET
            group_id = ${this.group_id},
            pronunciation = '${this.pronunciation}',
            content = '${this.content}',
            type = '${this.type}',
            path_image = '${this.path_image}'
        WHERE eng_id = ${this.eng_id};
        `;
        return db.execute(sql);
    }

    static deleteEngById(id){
        const sql = `
        DELETE FROM engs
        WHERE  eng_id = ${id};
        `;
        return db.execute(sql);
    }

    static getAllEng(){
        const sql = `
        SELECT * FROM engs;
        `;
        return db.execute(sql);
    }

    static getEngById(id){
        const sql = `
        SELECT * FROM engs
        WHERE  eng_id = ${id};
        `;
        return db.execute(sql);
    }

    static getEngByGroupId(group_id){
        const sql = `
        SELLECT * FROM engs
        WHERE  group_id = ${group_id};
        `;
        return db.execute(sql);
    }

    static getEngByType(type){
        const sql = `
        SELLECT * FROM engs
        WHERE type = ${type};
        `;
        return db.execute(sql);
    }

    static getEngByUserId(user_id){
        const sql = `
        SELECT e.* 
        FROM engs e
        INNER JOIN junction_user_eng jue ON e.eng_id = jue.eng_id
        WHERE jue.user_id = ${user_id}
        ORDER BY e.content;
        `;
        return db.execute(sql);
    }

}