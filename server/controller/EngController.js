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
        
        const user_id = req.query.user_id;

        const [engDBs,_] = await EngDB.getEngByUserId(user_id);

        for(const engDB of engDBs){

            const [vnDBs, __] = await VnDB.getVnByEngId(engDB.eng_id)

            engs.push(new EngRes(engDB, vnDBs))
        }

        console.log("getEngByUserId",engs)

        res.status(200).json(engs);

    } catch (error) {
        next(error)
    }
}

module.exports.getEngByGroupId = async(req, res, next) => {
    try {
        let engs = []
        
        const group_id = req.query.group_id;

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
        
        const type = req.query.type;

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
        
        console.log("post Eng", req.body)

        const{
            user_id,
            group_id,
            pronunciation,
            content,
            type,
            path_image,
            vns
        } = req.body;

        const eng = new EngDB(
            null,
            group_id,
            pronunciation,
            content,
            type,
            path_image
        )
        
        await eng.insert();

        const [newEngIds, _] = await EngDB.getCurrentEngId()
        
        const newEngId = newEngIds[0].MAX_ID

        console.log("New eng after insert ", newEngId)

        for(vn of vns){            
            const newVn = new VnDB(null, newEngId, vn.content)
            newVn.insert()
        }
        
        const junctionUserEng = new JunctionUserEng(user_id, newEngId);

        await junctionUserEng.insert();

        console.log("Add new word success")

        res.status(200).json({id: newEngId})
        
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
            path_image,
            vns
        } = req.body;

        console.log(req.body)

        const eng = new EngDB(
            eng_id,
            group_id,
            pronunciation,
            content,
            type,
            path_image
        )
        
        for(vn of vns){
            const newVn = new VnDB(vn.vn_id, vn.eng_id, vn.content)
            newVn.update()
        }
        await eng.update()

        console.log(`update eng ${eng.eng_id} success`)
        console.log(eng)
        res.status(200).json({id: eng.eng_id})
        
    } catch (error) {
        next(error);
    }
}

module.exports.deleteEngById = async(req, res, next) => {
    try {

        const id = req.params.id
        console.log("Delete eng", id)
        await VnDB.deleteVnByEngId(id)
        await JunctionUserEng.deleteJunctionByEngId(id)
        await EngDB.deleteEngById(id)
        res.status(200).json({message:"Delete Eng success"})
        
    } catch (error) {
        next(error);
    }
}
