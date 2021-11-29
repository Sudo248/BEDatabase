const authMethod = require('../util/AuthMethods');


module.exports.isAuth = async(req, res, next) => {

    try {
        
        const accessTokenFromHeader = req.headers.access_token;
        
        // console.log("header:", JSON.stringify(req.headers))
    
        if(!accessTokenFromHeader){
            return res.status(401).json({message:"You must put access token to header"});
        }

        const secretKey = process.env.JWT_KEY;

        const verified = await authMethod.verifyToken(
            accessTokenFromHeader,
            secretKey,
        );

        if(!verified){
            return res.status(401).json({message:"You can't access"})
        }

        return next();

    } catch (error) {
        next(error)
    }

}