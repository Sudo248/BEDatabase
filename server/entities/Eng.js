
module.exports = class Eng{
    constructor(
        eng_id,
        group_id,
        pronunciation,
        content,
        type,
        path_image,
        listVN = [],
        listUser = []

    ){
        this.eng_id = eng_id;
        this.group_id = group_id;
        this.pronunciation = pronunciation;
        this.content = content;
        this.type = type;
        this.path_image = path_image;
        this.listVN = listVN;
        this.listUser = listUser;
    }
}