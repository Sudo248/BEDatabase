const UserDB = require('../models/UserDB');
const EngDB = require('../models/EngDB');
const VnDB = require('../models/VnDB');
const GroupEngDB = require('../models/GroupEngDB');


const User = require('../entities/User')
const Eng = require('../entities/Eng');
const GroupEng = require('../entities/GroupEng');

EngDB.prototype.toEng = function() {
    // const listVN = await VnDB.getVnByEngId(this.eng_id);
    
    return new Eng(
        this.eng_id, 
        this.group_id, 
        this.pronunciation,
        this.content,
        this.type,
        this.path_image,
    );
}

Eng.prototype.toEngDB = function() {
    // for(const vn of this.listVN){
    //     const vndb = new VnDB(null, this.eng_id, vn.content);
    //     await vndb.insert();
    // }

    return new EngDB(
        null,
        this.group_id,
        this.pronunciation,
        this.content,
        this.type,
        this.path_image
    );

}

UserDB.prototype.toUser = function() {
    return new User(
        this.user_id,
        this.user_name,
        this.path_image
    );
}

User.prototype.toUserDB = function() {
    return new UserDB(
        null,
        this.user_name,
        this.path_image
    );
}

GroupEngDB.prototype.toGroupEng = function() {
    return new GroupEng(
        this.group_id,
        this.name,
        this.description
    );
}

GroupEng.prototype.toGroupEngDB = function(){
    return new GroupEngDB(
        null,
        this.name,
        this.description
    );
}