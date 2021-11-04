const jwt = require('jsonwebtoken');

module.exports = function genToken(user_id){
    const token = jwt.sign(
        {user_id : user_id},
        process.env.JWT_KEY,
        {expiresIn: "5h"}
    );
    return token;
}