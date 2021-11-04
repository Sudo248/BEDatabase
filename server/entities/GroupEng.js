
module.exports = class GroupEng{
    constructor(
        group_id,
        name,
        description,
        listEng = []
    ){
        this.group_id = group_id;
        this.name = name;
        this.description = description;
        this.listEng = listEng;
    }
}