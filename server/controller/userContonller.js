const UserDB = require('../models/UserDB');
const EngDB = require('../models/EngDB');
const VnDB = require('../models/VnDB');

module.exports.getUser = async(req, res, next) => {
    try {
        const query = req.query;

        if(query.user_name){
            const [user, _] = await UserDB.getUserByUserName(query.user_name);
            res.status(200).json({user});
        }else{
            const [users, _] = await UserDB.getAllUser();
            res.status(200).json({length:users.length, users});
        }

    } catch (error) {
        next(error);
    }
}

module.exports.getUserById = async(req, res, next) => {
    try {
        const id = req.params.id;

        const [userDB, _] = await UserDB.getUserById(id);

        if(userDB){
            res.status(200).json({userDB});
        }else{
            res.status(409).json({message:"User invalid"})
        }

        // // get user by id
        // const [userDB, _] = await UserDB.getUserById(id);
        // // get eng by user_id
        // const [listEngDB,_] = await EngDB.getEngByUserId(id);
        // // get vn by eng_id
        // let listEng = [];
        // for(let engDB of listEngDB){
        //     const eng = engDB.toENG();
        //     const [listVn, _] = await VnDB.getVnByEngId(engDB.eng_id);
        //     eng.listVn = listVn;
        //     listEng.push(eng);
        // }
        // const user = userDB.toUser();
        // user.listEng = listEng;
        // res.status(200).json({user});
    
    } catch (error) {
        next(error);
    }
}

module.exports.postUser = async(req, res, next) => {
    try {
        const {
            user_name,
            path_image
        } = req.body;

        const user = new UserDB(null, user_name, path_image);

        await user.insert();

        res.status(201).json({message: "Insert user successs!"})

    } catch (error) {
        next(error);
    }
}

module.exports.putUser = async(req, res, next) => {
    try {
        
        const {
            user_id,
            user_name,
            path_image
        } = req.body;

        const user = new UserDB(user_id, user_name, path_image);

        await user.update();

        res.status(201).json({message: "Update user successs!",user})

    } catch (error) {
        next(error);
    }
}
