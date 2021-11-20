const jwt = require('jsonwebtoken');

const promisify = require('util').promisify

const sign = promisify(jwt.sign).bind(jwt);

const verify = promisify(jwt.verify).bind(jwt);


module.exports.genToken = async(payload, secretKey, tokenLife) => {
    
    try {

        return await sign(
            {
                payload: payload,
            },
            secretKey,
            {
                algorithm: 'HS256',
				expiresIn: tokenLife,
            }
        );
        
    } catch (error) {
        console.log(`Error while gen token ${error}`);
        return null;
    }

}

module.exports.verifyToken = async(token, secretKey) => {

    try {
        
        return await verify(
            token,
            secretKey
        );

    } catch (error) {
        console.log(`Error when verify `)
        return null;
    }

}

module.exports.decodeToken = async(token, secretKey) => {

    try {
        
        return await verify(
            token,
            secretKey,
            {
                ignoreExpiration: true,
            }
        );

    } catch (error) {
        console.log(`Error when decode token ${error}`);
        return null;
    }

}