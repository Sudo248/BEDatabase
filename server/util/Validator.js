const {validator} = require('express-validator');

module.exports.validateAccount = () => {
    const {
        email,
        password
    } = req.body;

    return [
        validator(email, 'Invalid does not Empty').not().isEmpty(),
        validator(email, 'Invalid email').isEmail(),
        validator(password, 'password more than 6 degits').isLength({ min: 6 })
    ];
}


