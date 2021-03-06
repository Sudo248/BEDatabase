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
            res.status(200).json(users);
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
            res.status(200).json(userDB[0]);
        }else{
            res.status(409).send("User invalid");
        }
    
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
        
        console.log(req.body)

        const user = new UserDB(user_id, user_name, path_image);

        await user.update();
        console.log("Update user successs!")
        res.status(201).json({message: "Update user successs!"})

    } catch (error) {
        next(error);
    }
}
