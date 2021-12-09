const db = require('../database/db');

module.exports = class GroupEng{
    constructor(
        group_id,
        name,
        description
    ){
        this.group_id = group_id;
        this.name = name;
        this.description = description;
    }

    insert(){
        const sql = `
        INSERT INTO group_engs 
        VALUES(
            ${this.group_id},
            '${this.name}',
            '${this.description}'
        );
        `;
        return db.execute(sql);
    }

    update(){
        const sql = `
        UPDATE group_engs
        SET 
            name = '${this.name}',
            description = '${this.description}'
        WHERE  group_id = ${this.group_id};
        `;
        return db.execute(sql);
    }

    static deleteGroupById(id){
        const sql = `
        DELETE FROM group_engs
        WHERE  group_id = ${id};
        `;
        return db.execute(sql);
    }

    static getAllGroup(){
        const sql = `
        SELECT * FROM group_engs;
        `;
        return db.execute(sql);
    }

    static getGroupById(id){
        const sql = `
        SELECT * FROM group_engs
        WHERE  group_id = ${id} LIMIT 1;
        `;
        return db.execute(sql);
    }
}