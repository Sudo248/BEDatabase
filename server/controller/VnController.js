const VnDB = require('../models/VnDB');

module.exports.postVns = async(req, res, next) => {

    try{

        const {
            eng_id,
            contents
        } = req.body;

        for(const content of contents){
            const vn = new VnDB(
                null,
                eng_id,
                content
            )

            await vn.insert()
        }

        res.status(200).json({message:"Add Vn for Eng success", eng_id})

    }catch(error){
        next(error)
    }

}

module.exports.putVns = async(req, res, next) => {

    try {

        const {
            eng_id,
            vns
        } = req.body;

        for(const vn of vns){

            const updateVn = new VnDB(
                vn.vn_id,
                eng_id,
                vn.content
            )

            await updateVn.update()

        }

        res.status(200).json({message:"Update Vn for Eng success", eng_id})
        
    } catch (error) {
        next(error)
    }

}

module.exports.deleteVn = async(req, res, next) => {

    try {

        const vn_id = req.params.id

        await VnDB.deleteVnById(vn_id)

        res.status(200).json({message:"Delete Vn success"})
        
    } catch (error) {
        next(error)
    }

}