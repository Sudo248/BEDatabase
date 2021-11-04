const Eng = require('../database/models/EngDB');
const EngDB = require('../models/EngDB');
const VnDB = require('../models/VnDB');

module.exports.getAllEng = async(req, res, next) => {
    try {

        const [engDB,_] = await EngDB.getAllEng();

        res.status(200).json({engDB});

    } catch (error) {
        next(error)
    }
}

module.exports.getEngByGroupId = async(req, res, next) => {
    try {
        
        const group_id = req.params.group_id;

        const [engDB,_] = await EngDB.getEngByGroupId(group_id);

        res.status(200).json({engDB});

    } catch (error) {
        next(error)
    }
}

module.exports.getEngById = async(req, res, next) => {
    try {

        const id = req.params.id;

        const [engDB,_] = await EngDB.getEngById(id);

        res.status(200).json({engDB});
        
    } catch (error) {
        next(error);
    }
}

module.exports.getEngByType = async(req, res, next) => {
    try {

        const type = req.params.type;

        const [engDB,_] = await EngDB.getEngByType(type);

        res.status(200).json({engDB});
        
    } catch (error) {
        next(error);
    }
}

module.exports.postEng = async(req, res, next) => {
    try {

        const{

        } = req.body;
        
    } catch (error) {
        next(error);
    }
}