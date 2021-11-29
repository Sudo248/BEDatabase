

module.exports = class EngResponse{
    constructor(
        eng,
        vns
    ){
        this.eng_id = eng.eng_id;
        this.group_id = eng.group_id;
        this.pronunciation = eng.pronunciation;
        this.content = eng.content;
        this.type = eng.type;
        this.path_image = eng.path_image;
        this.vns = vns
    }
}