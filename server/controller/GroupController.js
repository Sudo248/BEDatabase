const GroupEngDB = require('../models/GroupEngDB');

module.exports.getAllGroup = async(req, res, next) => {
    try {
        
        const [groups, _] = await GroupEngDB.getAllGroup();

        res.status(200).json({length:groups.length, groups});

    } catch (error) {
        next(error);
    }
}

module.exports.getGroupById = async(req, res, next) => {
    try {
        
        const id = req.params.id;
        const [group, _] = await GroupEngDB.getGroupById(id);
        res.status(200).json({group});
    
    } catch (error) {
        next(error);
    }
}

module.exports.postGroup = async(req, res, next) => {
    try {
        
        const {
            name,
            description
        } = req.body;

        const group = new GroupEngDB(null, name, description);

        await group.insert();

        res.status(201).json({message: "Insert group successs!"})

    } catch (error) {
        next(error);
    }
}

module.exports.putGroup = async(req, res, next) => {
    try {
        
        const {
            group_id,
            name,
            description
        } = req.body;

        const group = new GroupEngDB(group_id, name, description);

        await group.update();

        res.status(201).json({message: "Update group successs!"})

    } catch (error) {
        next(error);
    }
}

module.exports.deleteGroupById = async(req, res, next) => {
    try {
        
        const id = req.params.id;
        await GroupEngDB.deleteGroupById(id);
        res.status(200).json({message:"Delete group success!"});
    
    } catch (error) {
        next(error);
    }
}

