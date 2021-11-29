const EngDB = require('../models/EngDB');
const JunctionUserEng = require('../models/JunctionUserEng');
const VnDB = require('../models/VnDB');
const EngRes = require('../models/EngResponse')

module.exports.getAllEng = async(req, res, next) => {
    try {
        let engs = []

        const [engDBs,_] = await EngDB.getAllEng();

        for(const engDB of engDBs){

            const [vnDBs,__] = await VnDB.getVnByEngId(engDB.eng_id)

            engs.push(new EngRes(engDB, vnDBs))
        }

        res.status(200).json(engs);

    } catch (error) {
        next(error)
    }
}

module.exports.getEngByUserId = async(req, res, next) => {
    try {
        let engs = []
        
        const user_id = req.params.user_id;

        const [engDBs,_] = await EngDB.getEngByUserId(user_id);

        for(const engDB of engDBs){

            const [vnDBs, __] = await VnDB.getVnByEngId(engDB.eng_id)

            engs.push(new EngRes(engDB, vnDBs))
        }

        res.status(200).json(engs);

    } catch (error) {
        next(error)
    }
}

module.exports.getEngByGroupId = async(req, res, next) => {
    try {
        let engs = []
        
        const group_id = req.params.group_id;

        const [engDBs,_] = await EngDB.getEngByGroupId(group_id);

        for(const engDB of engDBs){

            const [vnDBs,__] = await VnDB.getVnByEngId(engDB.eng_id)

            engs.push(new EngRes(engDB, vnDBs))
        }

        res.status(200).json(engs);

    } catch (error) {
        next(error)
    }
}

module.exports.getEngById = async(req, res, next) => {
    try {

        const id = req.params.id;

        const [engDB,_] = await EngDB.getEngById(id);

        const [vnDBs,__] = await VnDB.getVnByEngId(id)

        res.status(200).json(new EngRes(engDB[0], vnDBs));
        
    } catch (error) {
        next(error);
    }
}

module.exports.getEngByType = async(req, res, next) => {
    try {

        let engs = []
        
        const type = req.params.type;

        const [engDBs,_] = await EngDB.getEngByType(type);

        for(const engDB of engDBs){

            const [vnDBs, __] = await VnDB.getVnByEngId(engDB.eng_id)

            engs.push(new EngRes(engDB, vnDBs))
        }

        res.status(200).json(engs);
        
    } catch (error) {
        next(error);
    }
}

module.exports.postEng = async(req, res, next) => {
    try {

        const{
            user_id,
            group_id,
            pronunciation,
            content,
            type,
            path_image
        } = req.body;

        const eng = new EngDB(
            null,
            group_id,
            pronunciation,
            content,
            type,
            path_image
        )
        
        const [newEng,_] = await eng.insert();

        console.log("New eng after insert ", newEng[0].eng_id)

        // for(const vn of vns){

        //     const newVn = new VnDB(
        //         null,
        //         newEng[0].eng_id,
        //         vn
        //     )
        //     await newVn.insert();
        // }

        const junctionUserEng = new JunctionUserEng(user_id, eng_id);

        await junctionUserEng.insert();

        res.status(200).json({id: newEng[0]})
        
    } catch (error) {
        next(error);
    }
}

module.exports.putEng = async(req, res, next) => {
    try {

        const{
            eng_id,
            group_id,
            pronunciation,
            content,
            type,
            path_image

        } = req.body;

        const eng = new EngDB(
            eng_id,
            group_id,
            pronunciation,
            content,
            type,
            path_image
        )
        await eng.update()
        res.status(200).json({id: eng.eng_id})
        
    } catch (error) {
        next(error);
    }
}

module.exports.deleteEngById = async(req, res, next) => {
    try {

        const id = req.params.id
        await EngDB.deleteEngById(id)
        await VnDB.deleteVnByEngId(id)

        res.status(200).json({message:"Delete Eng success"})
        
    } catch (error) {
        next(error);
    }
}