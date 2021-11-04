
module.exports = class User{
    constructor(
        user_id,
        user_name,
        path_image,
        listEngs = []
    ){
        this.user_id = user_id;
        this.user_name = user_name;
        this.path_image = path_image;
        this.listEngs = listEngs;
    }


}